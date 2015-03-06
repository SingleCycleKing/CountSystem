package com.unique.countsystem;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import view.CustomViewPager;


public class MainActivity extends ActionBarActivity {
    @InjectView(R.id.view_page)
    CustomViewPager mViewPager;
    @InjectView(R.id.my_toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.left_drawer)
    ListView menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        mToolbar.setTitle(getTitle());

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // TODO Handle the menu item
                return true;
            }
        });

    }


}
