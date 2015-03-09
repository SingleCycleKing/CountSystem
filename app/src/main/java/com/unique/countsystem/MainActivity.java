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

import com.unique.countsystem.adapter.MenuAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.unique.countsystem.utils.BaseUtils;


public class MainActivity extends ActionBarActivity {
    @InjectView(R.id.my_toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.left_drawer)
    ListView menuList;
    @InjectView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        BaseUtils.setToolbar(mToolbar, this);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // TODO Handle the menu item
                return true;
            }
        });

        init();
    }

    private void init() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

                invalidateOptionsMenu();
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

        final String[] fragments = getResources().getStringArray(R.array.fragment);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, Fragment.instantiate(MainActivity.this, fragments[0])).commit();

        menuList.setAdapter(new MenuAdapter(this));
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mToolbar.setTitle(getResources().getStringArray(R.array.menu)[position]);
                switch (position) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content, Fragment.instantiate(MainActivity.this, fragments[0])).commit();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content, Fragment.instantiate(MainActivity.this, fragments[1])).commit();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content, Fragment.instantiate(MainActivity.this, fragments[2])).commit();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.content, Fragment.instantiate(MainActivity.this, fragments[3])).commit();
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });


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


