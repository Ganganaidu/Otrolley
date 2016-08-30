package com.thisisswitch.otrolly.driver.dialogs;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thisisswitch.otrolly.driver.MyApplication;
import com.thisisswitch.otrolly.driver.R;
import com.thisisswitch.otrolly.driver.events.TripAcceptEvent;
import com.thisisswitch.otrolly.driver.network.SocketSession;
import com.thisisswitch.otrolly.driver.utils.AppPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Office on 5/1/2016.
 */
public class DialogTripAcceptFragment extends DialogFragment {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.text)
    TextView text;
    @Bind(R.id.accept_text)
    TextView acceptText;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.seconds)
    TextView seconds;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.timerLayout)
    RelativeLayout timerLayout;
    @Bind(R.id.accept)
    Button accept;
    @Bind(R.id.reject)
    Button reject;

    JSONObject object;
    private String tripId = "";
    private MyHandler handler;
    private Timer _timer;
    private int _index;
    private int totalCount = 30;

    public DialogTripAcceptFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_request_accept, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this, view);

        handler = new MyHandler(seconds, progressBar);
        _index = 0;
        _timer = new Timer();
        _timer.schedule(new TickClass(), 0, 1000);

        String area = "";

        try {
            area = object.getString("area");
            tripId = object.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String areaToDisplay = getString(R.string.incoming_request).replace("^", area);
        text.setText(areaToDisplay);
        AppPreferences.getInstance().saveTripId(tripId);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.accept, R.id.reject})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.accept:
                //listener.onTripAccepted(tripId);
                SocketSession.isTrackingStarted = true;
                TripAcceptEvent tripAcceptEvent = new TripAcceptEvent();
                tripAcceptEvent.tripId = tripId;
                MyApplication.getBus().post(tripAcceptEvent);

                dismiss();
                break;
            case R.id.reject:

                SocketSession.getInstance().rejectTrip(AppPreferences.getInstance().getUserId(), AppPreferences.getInstance().getTripId());
                //start broadcasting lat, lng
                SocketSession.isTrackingStarted = true;
                dismiss();

                break;
        }
    }

    public void setTripJsonResponse(JSONObject object) {
        this.object = object;
    }

//    public void setTripAcceptListener(OnTripAcceptCallback listener) {
//        this.listener = listener;
//    }

//    private OnTripAcceptCallback listener;
//
//    public interface OnTripAcceptCallback {
//        void onTripAccepted(String tripId);
//    }

    private class TickClass extends TimerTask {
        TickClass() {
        }

        @Override
        public void run() {
            handler.sendEmptyMessage(_index);
            _index++;
            if (_index == totalCount) {
                dismiss();
                _timer.cancel();
                _timer.purge();
            }
        }
    }

    private class MyHandler extends Handler {
        TextView seconds;
        ProgressBar progress;

        MyHandler(View seconds, View progress) {
            this.seconds = (TextView) seconds;
            this.progress = (ProgressBar) progress;

        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            seconds.setText("" + (totalCount - _index));
            progress.setProgress(totalCount - _index);
        }
    }
}