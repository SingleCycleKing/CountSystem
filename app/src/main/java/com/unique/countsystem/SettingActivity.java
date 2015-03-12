package com.unique.countsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.unique.countsystem.utils.BaseUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class SettingActivity extends ActionBarActivity {
    @InjectView(R.id.my_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);
        BaseUtils.setToolbar(mToolbar, this);
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        finish();
        return super.getSupportParentActivityIntent();
    }
}
