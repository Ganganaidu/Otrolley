package com.thisisswitch.otrolly.driver.utils;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.akexorcist.googledirection.constant.RequestResult;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.thisisswitch.otrolly.driver.MyApplication;
import com.thisisswitch.otrolly.driver.R;
import com.thisisswitch.otrolly.driver.listeners.OnDirectionCallback;
import com.thisisswitch.otrolly.driver.models.RoutePath;
import com.thisisswitch.otrolly.driver.network.RestAPIRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Office on 4/25/2016.
 */
public class GoogleMapDirections {
    static PolylineOptions lineOptions;

    public static PolylineOptions loadTripDirections(final Context context, LatLng pickLatLng, LatLng dropLatLng, final GoogleMap mGoogleMap) {
        RestAPIRequest.getInstance().getDirectionGoogleAPI(context.getResources().getString(R.string.google_server_key),
                pickLatLng, dropLatLng, new OnDirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        List<RoutePath> routePath = new ArrayList<>();

                        String status = direction.getStatus();
                        if (status.equals(RequestResult.OK)) {

                            Route route = direction.getRouteList().get(0);
                            Leg leg = route.getLegList().get(0);

                            double distance = Double.parseDouble(leg.getDistance().getValue().trim()); // KM
                            double duration = Double.parseDouble(leg.getDuration().getValue().trim()); //min

                            distance = Math.round(Utils.calculateKilometers(distance));
                            duration = Math.round(Utils.secondsToMin(duration));

                            float newDistance = (float) (AppPreferences.getInstance().getTotalDistance() + distance);
                            AppPreferences.getInstance().saveTotalDistance(newDistance);

                            float newDuration = (float) (AppPreferences.getInstance().getTotalDuration() + duration);
                            AppPreferences.getInstance().saveTotalDuration(newDuration);

                            List<List<HashMap<String, String>>> routes = new ArrayList<>();
                            for (int i = 0; i < direction.getRouteList().size(); i++) {
                                List<HashMap<String, String>> path = new ArrayList<>();

                                for (int j = 0; j < leg.getStepList().size(); j++) {
                                    List<LatLng> list = leg.getStepList().get(j).getPolyline().getPointList();

                                    for (int l = 0; l < list.size(); l++) {
                                        HashMap<String, String> hm = new HashMap<>();
                                        hm.put("lat", Double.toString((list.get(l)).latitude));
                                        hm.put("lng", Double.toString((list.get(l)).longitude));
                                        path.add(hm);
                                    }
                                }
                                routes.add(path);
                            }
                            ArrayList<LatLng> points;
                            PolylineOptions lineOptions = null;

                            // Traversing through all the routes
                            for (int i = 0; i < routes.size(); i++) {
                                points = new ArrayList<>();
                                lineOptions = new PolylineOptions();

                                // Fetching i-th route
                                List<HashMap<String, String>> path = routes.get(i);

                                // Fetching all the points in i-th route
                                for (int j = 0; j < path.size(); j++) {
                                    HashMap<String, String> point = path.get(j);

                                    double lat = Double.parseDouble(point.get("lat"));
                                    double lng = Double.parseDouble(point.get("lng"));
                                    LatLng position = new LatLng(lat, lng);

                                    routePath.add(new RoutePath(point.get("lat"), point.get("lng")));
                                    points.add(position);
                                }

                                // Adding all the points in the route to LineOptions
                                lineOptions.addAll(points);
                                lineOptions.width(10);
                                lineOptions.color(Color.parseColor("#45abee"));
                            }

                            //MyApplication.getInstance().setRoutePath(direction);

                            mGoogleMap.addPolyline(lineOptions);
                            MyApplication.getInstance().hideProgress();
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        MyApplication.getInstance().hideProgress();
                        Log.d("demo", "demo:" + t.getMessage());
                    }
                }
        );
        return lineOptions;
    }

    /**
     * Method Courtesy :
     * /decoding-polylines-from-google-maps-direction-api-with-java
     */
    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }
}
