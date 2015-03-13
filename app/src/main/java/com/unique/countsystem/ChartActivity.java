package com.unique.countsystem;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.unique.countsystem.adapter.ChartAdapter;
import com.unique.countsystem.fragment.PieChartFrag;
import com.unique.countsystem.utils.BaseUtils;
import com.unique.countsystem.view.DepthPageTransformer;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChartActivity extends ActionBarActivity {

    @InjectView(R.id.my_toolbar)
    Toolbar mToolbar;
    private ChartAdapter chartAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ButterKnife.inject(this);
        BaseUtils.setToolbar(mToolbar, this);

        init();
    }

    private void init() {

        viewPager = (ViewPager) findViewById(R.id.view_pager) ;

        getSupportFragmentManager().beginTransaction().
                replace(R.id.view_pager, new PieChartFrag()).commit();

        FragmentManager fm = getSupportFragmentManager();

        chartAdapter = new ChartAdapter(fm);
        viewPager.setPageTransformer(true,new DepthPageTransformer());
        viewPager.setAdapter(chartAdapter);
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        finish();
        return super.getSupportParentActivityIntent();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

}
