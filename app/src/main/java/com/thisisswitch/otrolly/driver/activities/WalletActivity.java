package com.thisisswitch.otrolly.driver.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.thisisswitch.otrolly.driver.R;
import com.thisisswitch.otrolly.driver.adapters.WalletPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WalletActivity extends AppCompatActivity {

    @Bind(R.id.wallet_tablayout)
    TabLayout walletTablayout;
    @Bind(R.id.wallet_viewpager)
    ViewPager walletViewpager;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    WalletPagerAdapter walletPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wallet);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        setUpToolBar();
        walletPagerAdapter = new WalletPagerAdapter(this.getSupportFragmentManager(), this);
        walletViewpager.setAdapter(walletPagerAdapter);
        walletTablayout.setupWithViewPager(walletViewpager);

        walletViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(walletTablayout));
        walletTablayout.setTabsFromPagerAdapter(walletPagerAdapter);

        walletViewpager.setOffscreenPageLimit(1); // if you use 3 tabs

    }


    private void setUpToolBar() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.nav_item_wallet));

        //updating status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.main));
        }
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
