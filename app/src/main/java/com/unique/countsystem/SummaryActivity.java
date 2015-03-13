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

import com.unique.countsystem.adapter.SummaryAdapter;
import com.unique.countsystem.utils.BaseUtils;
import com.unique.countsystem.utils.OnRecyclerItemClickListener;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SummaryActivity extends ActionBarActivity {

    @InjectView(R.id.summary_recycler_view)
    RecyclerView summaryRecyclerView;
    @InjectView(R.id.my_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        ButterKnife.inject(this);
        BaseUtils.setToolbar(mToolbar, this);
        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        summaryRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(this,
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

        summaryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        summaryRecyclerView.setAdapter(new SummaryAdapter(this));

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
