package com.thisisswitch.otrolly.driver.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thisisswitch.otrolly.driver.R;



public class WalletTabFragment extends Fragment {



    public static WalletTabFragment getInstance(int position){
        WalletTabFragment walletTabFragment = new WalletTabFragment();
        return walletTabFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallet_tab, container, false);
    }
}
