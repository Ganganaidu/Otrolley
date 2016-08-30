package com.thisisswitch.otrolly.driver.network;

import com.thisisswitch.otrolly.driver.models.CouponDetails;
import com.thisisswitch.otrolly.driver.models.DropLocation;
import com.thisisswitch.otrolly.driver.models.TripEnd;
import com.thisisswitch.otrolly.driver.models.TripHistoryModel;
import com.thisisswitch.otrolly.driver.models.TripRequest;
import com.thisisswitch.otrolly.driver.models.TrpStatusUpdate;
import com.thisisswitch.otrolly.driver.models.User;
import com.thisisswitch.otrolly.driver.models.UserProfile;
import com.thisisswitch.otrolly.driver.models.Vehicletype;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Office on 3/16/2016.
 */
public interface RestRequestInterface {

    /*Login call*/
    @FormUrlEncoded
    @POST("drivers/login")
    Call<User> loginUser(@Field("username") String username,
                         @Field("password") String password,
                         @Field("vehicleid") String vehicleid);

    @GET("trips/{id}")
    Call<TripRequest> getTripDetails(
            @Header("Authorization") String authorization,
            @Path("id") String tripID, @Query("filter[include][user]") String user);

    @GET("drivers/{id}/trips")
    Call<List<TripHistoryModel>> getTripHistory(@Header("Authorization") String authorization, @Path("id") String driverId);

    @GET("drivers/{id}")
    Call<UserProfile> getDriverDetails(@Header("Authorization") String authorization, @Path("id") String driverId);
    //@GET("trips")
    //Call<List<TripHistoryModel>> getTripHistory(@Header("Authorization") String authorization, @Query("[where][driver_id]") String tripId);

    //access_token
    @PUT("trips/{tripid}")
    Call<TripEnd> sendTripDetails(@Header("Authorization") String authorization, @Body TripEnd user, @Path("tripid") String tripId);

    @PUT("trips/{tripid}")
    Call<TrpStatusUpdate> sendTripStatus(@Header("Authorization") String authorization, @Body TrpStatusUpdate tripStatus, @Path("tripid") String tripId);

    @PUT("trips/{tripid}/droplocations/{droplocationid}")
    Call<DropLocation> sendDropEndStatus(@Header("Authorization") String authorization, @Body DropLocation dropLocation,
                                         @Path("tripid") String tripId, @Path("droplocationid") String droplocationid);

    @POST("drivers/logout")
    Call<String> logOutUser(@Header("Authorization") String authorization);


    @GET("vehicles/{id}/vehicletype")
    Call<Vehicletype> getVehicleDetails(@Header("Authorization") String authorization, @Path("id") String userID);

    @FormUrlEncoded
    @POST("trips/validateCoupon")
    Call<CouponDetails> validateCoupon(@Header("Authorization") String authorization, @Field("couponCode") String couponCode,
                                       @Field("net") String net,
                                       @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("trips/{id}/routepaths")
    Call<String> sendTripRoutePath(@Header("Authorization") String authorization, @Field("id") String userId, @Field("routepaths") String routePath);
}

