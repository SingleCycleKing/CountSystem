package com.unique.countsystem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.unique.countsystem.adapter.PagerAdapter;
import com.unique.countsystem.database.DbHelper;
import com.unique.countsystem.fragment.FinishedFragment;
import com.unique.countsystem.fragment.RollCallFragment;
import com.unique.countsystem.utils.BaseUtils;
import com.unique.countsystem.utils.DebugLog;
import com.unique.countsystem.view.CustomViewPager;

import java.util.ArrayList;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NamedActivity extends ActionBarActivity {
    @InjectView(R.id.my_toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.named_view_pager)
    CustomViewPager mViewPager;
    public int number;
    private ArrayList<Class<?>> classes;
    public static long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_named);

        ButterKnife.inject(this);
        BaseUtils.setToolbar(mToolbar, this);

        number = getIntent().getIntExtra("number", 0);
        if (number != 0) {
            SharedPreferences sharedPreferences = this.getSharedPreferences("Count", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("number", number);
            editor.apply();

            classes = new ArrayList<>();
            classes.add(RollCallFragment.class);
            PagerAdapter mPagerAdapter = new PagerAdapter(this, classes);
            mViewPager.setAdapter(mPagerAdapter);
            mViewPager.isAlterable(false);
        } else if (-1 != getIntent().getIntExtra("SummaryId", -1)) {
            classes.add(FinishedFragment.class);
            PagerAdapter mPagerAdapter = new PagerAdapter(this, classes);
            mViewPager.setAdapter(mPagerAdapter);
            mViewPager.isAlterable(false);
        }
    }


    @Override
    public Intent getSupportParentActivityIntent() {
        finish();
        return super.getSupportParentActivityIntent();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(namedReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BaseUtils.HAS_FINISHED_CALLING_ROLL);
        registerReceiver(namedReceiver, intentFilter);
    }

    private BroadcastReceiver namedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BaseUtils.HAS_FINISHED_CALLING_ROLL)) {
                classes.clear();
                classes.add(FinishedFragment.class);
                mViewPager.getAdapter().notifyDataSetChanged();
            }
        }
    };
}
