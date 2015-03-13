package com.unique.countsystem.adapter;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private Context mContext;
    private ArrayList<Class<?>> mClasses = new ArrayList<>();

    public PagerAdapter(ActionBarActivity activity, ArrayList<Class<?>> mClasses) {
        super(activity.getFragmentManager());
        this.mClasses = mClasses;
        mContext = activity;
    }

    public String getName(int i) {
        return mClasses.get(i).getName();
    }

    @Override
    public Fragment getItem(int i) {
         return Fragment.instantiate(mContext, mClasses.get(i).getName());
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mClasses.size();
    }


}
