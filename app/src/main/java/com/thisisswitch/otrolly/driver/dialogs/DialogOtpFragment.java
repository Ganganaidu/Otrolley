package com.thisisswitch.otrolly.driver.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.thisisswitch.otrolly.driver.MyApplication;
import com.thisisswitch.otrolly.driver.R;
import com.thisisswitch.otrolly.driver.events.DropLocStatusCodes;
import com.thisisswitch.otrolly.driver.events.OtpCheckEvent;
import com.thisisswitch.otrolly.driver.listeners.RequestListener;
import com.thisisswitch.otrolly.driver.models.DropLocation;
import com.thisisswitch.otrolly.driver.models.TripRequest;
import com.thisisswitch.otrolly.driver.network.RestAPIRequest;
import com.thisisswitch.otrolly.driver.network.RestRequestInterface;
import com.thisisswitch.otrolly.driver.network.SocketSession;
import com.thisisswitch.otrolly.driver.utils.AppPreferences;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by Office on 4/30/2016.
 */
public class DialogOtpFragment extends DialogFragment {

    private static final String TAG = "DialogOtpFragment";

    @Bind(R.id.otp_code_editText)
    EditText otpCodeEditText;
    @Bind(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;
    @Bind(R.id.dialog_cancel)
    Button dialogCancel;
    @Bind(R.id.dialog_done)
    Button dialogDone;

    TripRequest tripRequest;

    public DialogOtpFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_otp, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.dialog_cancel, R.id.dialog_done})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_cancel:
                if (tripRequest != null && tripRequest.getDroplocations() != null) {
                    String dropId = tripRequest.getDroplocations().get(0).getId();
                    SocketSession.getInstance().dropLocationStatus(AppPreferences.getInstance().getTripId(), dropId, DropLocStatusCodes.cancelled);
                }
                dismiss();
                break;
            case R.id.dialog_done:
                String otpText = otpCodeEditText.getText().toString().trim();
                Log.e(TAG,"otpText:"+tripRequest.getDroplocations().get(0).getOtp());

                //TODO need to add this
                if (otpText.length() > 0 && otpText.equals(tripRequest.getDroplocations().get(0).getOtp())) {
                    sendDropEndRequest("1003", true, otpText);
                    dismiss();
                } else {
                    MyApplication.getInstance().showToast("Invalid OTP number");
                    otpCodeEditText.setError("Enter OTP Number");
                }
                break;
        }
    }

    public void setTripOtpNumber(TripRequest tripOtpNumber) {
        tripRequest = tripOtpNumber;
        //reached drop location
        if (tripRequest != null && tripRequest.getDroplocations() != null) {
            String dropId = tripRequest.getDroplocations().get(0).getId();
            SocketSession.getInstance().dropLocationStatus(AppPreferences.getInstance().getTripId(), dropId, DropLocStatusCodes.reachedLocation);
        }
    }

    public void sendDropEndRequest(String status, final boolean isProgress, final String otpText) {
        final DropLocation dropLocation = tripRequest.getDroplocations().get(0);
        dropLocation.setStatus(status);

        if (isProgress) {
            MyApplication.getInstance().showProgress(getActivity(), "Checking OTP number please wait...");
        }

        SocketSession.getInstance().dropLocationStatus(AppPreferences.getInstance().getTripId(), dropLocation.getId(),
                DropLocStatusCodes.verified);

        RestRequestInterface dropLocReq = RestAPIRequest.getRetrofit().create(RestRequestInterface.class);
        Call<DropLocation> call = dropLocReq.sendDropEndStatus(AppPreferences.getInstance().getAccessToken(),
                dropLocation, AppPreferences.getInstance().getTripId(), dropLocation.getId());
        RestAPIRequest.getInstance().doRequest(call, new RequestListener<DropLocation>() {
            @Override
            public void onResponse(DropLocation response) {
                Log.e(TAG, "response:" + response);
                if (isProgress) {
                    MyApplication.getInstance().hideProgress();
                }
                if (response != null) {
                    //listener.onOtpChecked(otpText);
                    MyApplication.getBus().post(new OtpCheckEvent());
                }
            }

            @Override
            public void onDisplayError(String errorMsg) {
                MyApplication.getInstance().hideProgress();
                Log.e(TAG, "errorMsg:" + errorMsg);
            }
        });
    }
}