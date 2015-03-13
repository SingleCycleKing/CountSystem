package com.unique.countsystem;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.unique.countsystem.R;
import com.unique.countsystem.fragment.PieChartFrag;
import com.unique.countsystem.utils.BaseUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChartActivity extends ActionBarActivity {

    @InjectView(R.id.my_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ButterKnife.inject(this);
        BaseUtils.setToolbar(mToolbar, this);

        init();
    }

    private void init() {

        getSupportFragmentManager().beginTransaction().
                replace(R.id.chart_content, new PieChartFrag()).commit();

    }

    @Override
    public Intent getSupportParentActivityIntent() {
        finish();
        return super.getSupportParentActivityIntent();
    }

}
