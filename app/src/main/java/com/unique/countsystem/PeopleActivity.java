package com.unique.countsystem;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.unique.countsystem.adapter.PeopleAdapter;
import com.unique.countsystem.database.DbHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class PeopleActivity extends ActionBarActivity {
    @InjectView(R.id.people_recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        ButterKnife.inject(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new PeopleAdapter(this, DbHelper.getInstance().getAllClassList().get(getIntent().getIntExtra("position", -1))));
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
