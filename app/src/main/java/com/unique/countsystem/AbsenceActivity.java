package com.unique.countsystem;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


import com.unique.countsystem.adapter.AbsenceAdapter;
import com.unique.countsystem.utils.BaseUtils;
import com.unique.countsystem.utils.OnRecyclerItemClickListener;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class AbsenceActivity extends ActionBarActivity {

    @InjectView(R.id.absence_recycler_view)
    RecyclerView absenceRecyclerView;
    @InjectView(R.id.my_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absence);
        ButterKnife.inject(this);
        BaseUtils.setToolbar(mToolbar, this);
        init();
    }


    @Override
    public void onStart() {
        super.onStart();
        absenceRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(this,
                new OnRecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        switch (position) {
                            case 0:

                                break;
                            case 1:

                                break;
                            default:

                                break;
                        }

                    }
                }));
    }

    private void init() {

        absenceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        absenceRecyclerView.setAdapter(new AbsenceAdapter(this));

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
