package com.thisisswitch.otrolly.driver.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thisisswitch.otrolly.driver.R;
import com.thisisswitch.otrolly.driver.adapters.WalletPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Raja Gopal Reddy P
 */
public class WalletFragment extends Fragment {

    @Bind(R.id.wallet_tablayout)
    TabLayout walletTablayout;
    @Bind(R.id.wallet_viewpager)
    ViewPager walletViewpager;

    WalletPagerAdapter walletPagerAdapter;

    public WalletFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        ButterKnife.bind(this, view);

        walletPagerAdapter = new WalletPagerAdapter(getActivity().getSupportFragmentManager(), getActivity());
        walletViewpager.setAdapter(walletPagerAdapter);
        walletTablayout.setupWithViewPager(walletViewpager);

        walletViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(walletTablayout));
        walletTablayout.setTabsFromPagerAdapter(walletPagerAdapter);

        walletViewpager.setOffscreenPageLimit(1); // if you use 3 tabs

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
