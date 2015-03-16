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
import android.widget.Toast;

import com.unique.countsystem.adapter.SummaryAdapter;
import com.unique.countsystem.database.DbHelper;
import com.unique.countsystem.utils.BaseUtils;
import com.unique.countsystem.utils.OnRecyclerItemClickListener;
import com.unique.countsystem.utils.OnRecyclerItemLongClickListener;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.me.lewisdeane.ldialogs.CustomDialog;

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
    }

    private void init() {
        summaryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        summaryRecyclerView.setAdapter(new SummaryAdapter(this));

        summaryRecyclerView.addOnItemTouchListener(new OnRecyclerItemLongClickListener(this, summaryRecyclerView, new OnRecyclerItemLongClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(SummaryActivity.this, NamedActivity.class);
                intent.putExtra("SummaryId", DbHelper.getInstance().getAllRecordTimeDescOrdered().get(position).getId());
                startActivity(intent);
                overridePendingTransition(R.anim.move_right_in, R.anim.move_left_out);
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                CustomDialog.Builder builder = new CustomDialog.Builder(SummaryActivity.this, "确认删除吗？", "确定");
                builder.content("");
                builder.darkTheme(false);
                CustomDialog customDialog = builder.build();
                customDialog.show();
                customDialog.setClickListener(new CustomDialog.ClickListener() {
                    @Override
                    public void onConfirmClick() {
                        RecordTime mRecordTime = DbHelper.getInstance().getAllRecordTimeDescOrdered().get(position);
                        DbHelper.getInstance().deleteAbsenceRecord(mRecordTime.getAbsenceTimes());
                        mRecordTime.delete();
                        summaryRecyclerView.getAdapter().notifyDataSetChanged();
                        summaryRecyclerView.setAdapter(new SummaryAdapter(SummaryActivity.this));
                    }

                    @Override
                    public void onCancelClick() {

                    }
                });

            }
        }));
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
