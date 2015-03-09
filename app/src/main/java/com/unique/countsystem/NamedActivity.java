package com.unique.countsystem;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.unique.countsystem.R;
import com.unique.countsystem.utils.BaseUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NamedActivity extends ActionBarActivity {
    @InjectView(R.id.my_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_named);

        ButterKnife.inject(this);
        BaseUtils.setToolbar(mToolbar, this);
    }


}
