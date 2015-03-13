package com.unique.countsystem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.unique.countsystem.database.DbHelper;
import com.unique.countsystem.fragment.FinishedFragment;
import com.unique.countsystem.fragment.RollCallFragment;
import com.unique.countsystem.utils.BaseUtils;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NamedActivity extends ActionBarActivity {
    @InjectView(R.id.my_toolbar)
    Toolbar mToolbar;

    public int number;
    public long id;

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

            id = DbHelper.getInstance().insertOrReplaceRecordTime(new Date());

            RollCallFragment rollCallFragment = new RollCallFragment();
            Bundle bundle = new Bundle();
            bundle.putLong("id", id);
            rollCallFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.named_layout, rollCallFragment);
            transaction.commit();

        } else if (-1 != getIntent().getLongExtra("SummaryId", -1)) {
            id = getIntent().getLongExtra("SummaryId", -1);
            FinishedFragment finishedFragment = new FinishedFragment();
            Bundle bundle = new Bundle();
            bundle.putLong("id", id);
            finishedFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.named_layout, finishedFragment);
            transaction.commit();
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
                FinishedFragment finishedFragment = new FinishedFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("id", id);
                finishedFragment.setArguments(bundle);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.named_layout, finishedFragment);
                transaction.commit();
            }
        }
    };
}
