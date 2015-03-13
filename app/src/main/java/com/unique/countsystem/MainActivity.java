package com.unique.countsystem;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.unique.countsystem.adapter.MenuAdapter;
import com.unique.countsystem.database.DbHelper;
import com.unique.countsystem.utils.BaseUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {
    @InjectView(R.id.my_toolbar)
    Toolbar mToolbar;

    @InjectView(R.id.menu_list)
    ListView menuList;

    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;

    private boolean isSelected = false;

    private int mPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        BaseUtils.setToolbar(mToolbar, this);

        DbHelper.preInitHelper(this);
        init();
    }

    private void init() {
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=54faf98f");

        final String[] fragments = getResources().getStringArray(R.array.fragment);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, Fragment.instantiate(MainActivity.this, fragments[0])).commit();

        menuList.setAdapter(new MenuAdapter(this));
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (3 != position)
                    mToolbar.setTitle(getResources().getStringArray(R.array.menu)[position]);
                switch (position) {
                    case 0:
                        mPosition = 0;
                        isSelected = true;
                        break;
                    case 1:
                        mPosition = 1;
                        isSelected = true;
                        break;
                    case 2:
                        mPosition = 2;
                        isSelected = true;
                        break;
                    case 3:
                        mPosition = 3;
                        isSelected = true;
                        break;
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
                if (isSelected) {
                    if (3 != mPosition) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.content, Fragment.instantiate(MainActivity.this, fragments[mPosition])).commit();
                    } else startActivity(new Intent(MainActivity.this, SettingActivity.class));
                    isSelected = false;
                }
            }

            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                invalidateOptionsMenu();
            }

            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item);
    }
}


