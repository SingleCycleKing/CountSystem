package com.unique.countsystem;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.unique.countsystem.adapter.PagerAdapter;
import com.unique.countsystem.fragment.RollCallFragment;
import com.unique.countsystem.utils.BaseUtils;
import com.unique.countsystem.view.CustomViewPager;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NamedActivity extends ActionBarActivity {
    @InjectView(R.id.my_toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.named_view_pager)
    CustomViewPager mViewPager;

    private ArrayList<Class<?>> classes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_named);

        ButterKnife.inject(this);
        BaseUtils.setToolbar(mToolbar, this);

        classes = new ArrayList<>();
        classes.add(RollCallFragment.class);
        PagerAdapter mPagerAdapter = new PagerAdapter(this, classes);
        mViewPager.setAdapter(mPagerAdapter);


    }
}