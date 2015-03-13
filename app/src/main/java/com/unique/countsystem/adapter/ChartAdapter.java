package com.unique.countsystem.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.unique.countsystem.fragment.LineChartFragment;
import com.unique.countsystem.fragment.PieChartFrag;

/**
 * Created by chen on 15-3-13.
 * adapter
 */
public class ChartAdapter extends FragmentPagerAdapter {

    public ChartAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 1 ;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                return new PieChartFrag();

//            case 1:
//
//                return new LineChartFragment();

            default:
                return null;
        }
    }

}
