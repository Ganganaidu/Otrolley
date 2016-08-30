package com.thisisswitch.otrolly.driver.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thisisswitch.otrolly.driver.R;
import com.thisisswitch.otrolly.driver.adapters.TripHistoryAdapter;
import com.thisisswitch.otrolly.driver.listeners.RequestListener;
import com.thisisswitch.otrolly.driver.models.TripHistoryModel;
import com.thisisswitch.otrolly.driver.network.RestAPIRequest;
import com.thisisswitch.otrolly.driver.network.RestRequestInterface;
import com.thisisswitch.otrolly.driver.utils.AppPreferences;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;

public class TripHistoryFragment extends Fragment {

    @Bind(R.id.trackHistoryRecycle)
    RecyclerView trackHistoryRecycle;
    // private List<FeedItem> feedsList;
    private TripHistoryAdapter adapter;
    private static final String TAG = "TripHistoryFragment";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String accessToken = AppPreferences.getInstance().getAccessToken();
        RestRequestInterface myLogin = RestAPIRequest.getRetrofit().create(RestRequestInterface.class);
        Call<List<TripHistoryModel>> call = myLogin.getTripHistory(accessToken, AppPreferences.getInstance().getUserId());
        RestAPIRequest.getInstance().doRequest(call, new RequestListener<List<TripHistoryModel>>() {
            @Override
            public void onResponse(List<TripHistoryModel> response) {
                Log.e(TAG, "response:" + response);
                if (response != null) {
                    trackHistoryRecycle.setAdapter(new TripHistoryAdapter(getActivity(), response));
                }
            }

            @Override
            public void onDisplayError(String errorMsg) {
                Log.e(TAG, "errorMsg:" + errorMsg);
            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_trip_history, container, false);
        ButterKnife.bind(this, view);
        trackHistoryRecycle = (RecyclerView) view.findViewById(R.id.trackHistoryRecycle);
        trackHistoryRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}


