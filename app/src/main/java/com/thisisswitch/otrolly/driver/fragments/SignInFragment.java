package com.thisisswitch.otrolly.driver.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.otto.Subscribe;
import com.thisisswitch.otrolly.driver.MyApplication;
import com.thisisswitch.otrolly.driver.R;
import com.thisisswitch.otrolly.driver.activities.MainActivity;
import com.thisisswitch.otrolly.driver.events.DriverLoginEvent;
import com.thisisswitch.otrolly.driver.network.OTrolleyAPIRequest;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Raja Gopal Reddy P
 */
public class SignInFragment extends Fragment {

    private static final String TAG = "SignInFragment";

    @Bind(R.id.registrationNumber)
    EditText registrationNumberText;
    @Bind(R.id.phone)
    EditText phoneEditText;
    @Bind(R.id.password)
    EditText passwordEditText;
    @Bind(R.id.loginButton)
    Button loginButton;

    public SignInFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_signin, container, false);

        ButterKnife.bind(this, view);
        setDummyData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MyApplication.getBus().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MyApplication.getBus().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void setDummyData() {
        registrationNumberText.setText("Test01");
        phoneEditText.setText("8019131322");
        passwordEditText.setText("12345");
    }

    @OnClick(R.id.loginButton)
    public void onClick() {

        final String registrationNumber = registrationNumberText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        MyApplication.getInstance().showProgress(getActivity(), "Contacting server please wait...");
        OTrolleyAPIRequest.getInstance().loginUser(getActivity(), phone, password, registrationNumber);
    }

    @Subscribe
    public void loginUser(DriverLoginEvent event) {
        if (event.success) {
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
