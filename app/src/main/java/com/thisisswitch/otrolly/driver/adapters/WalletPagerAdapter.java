package com.thisisswitch.otrolly.driver.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.thisisswitch.otrolly.driver.R;
import com.thisisswitch.otrolly.driver.fragments.WalletTabFragment;

/**
 * Created by syarlagadda on 4/17/16.
 */
public class WalletPagerAdapter  extends FragmentStatePagerAdapter{

Context mContext;


    public WalletPagerAdapter(FragmentManager fm, Context mContext) {
        super(fm);

        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return WalletTabFragment.getInstance(position);
        } else if (position == 1) {
            return WalletTabFragment.getInstance(position);
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = " ";
        switch (position) {
            case 0:
                title = mContext.getResources().getString(R.string.summary);
                break;
            case 1:
                title = mContext.getResources().getString(R.string.deposits);
                break;
        }
        return title;    }
}
