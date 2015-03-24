package com.unique.countsystem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.unique.countsystem.database.DbHelper;
import com.unique.countsystem.fragment.FinishedFragment;
import com.unique.countsystem.fragment.QuizCallFragment;
import com.unique.countsystem.fragment.RollCallFragment;
import com.unique.countsystem.utils.BaseUtils;
import com.unique.countsystem.utils.DebugLog;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class QuizActivity extends ActionBarActivity {

    @InjectView(R.id.my_toolbar)
    Toolbar mToolbar;

    public int number;
    public long id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ButterKnife.inject(this);
        BaseUtils.setToolbar(mToolbar, this);


        number = getIntent().getIntExtra("number", 0);
        if (number != 0) {
            id = DbHelper.getInstance().insertOrReplaceRecordTime(new Date());

            QuizCallFragment quizCallFragment = new QuizCallFragment();
            Bundle bundle = new Bundle();
            bundle.putLong("id", id);
            bundle.putInt("number",number);
            DebugLog.e("number" +number);
            bundle.putStringArrayList("classes",getIntent().getStringArrayListExtra("classes"));
            quizCallFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.quiz_layout, quizCallFragment);
            transaction.commit();

        } else if (-1 != getIntent().getLongExtra("SummaryId", -1)) {
            id = getIntent().getLongExtra("SummaryId", -1);

            //  need
            FinishedFragment finishedFragment = new FinishedFragment();
            Bundle bundle = new Bundle();
            bundle.putLong("id", id);
            finishedFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.quiz_layout, finishedFragment);
            transaction.commit();
            overridePendingTransition(R.anim.move_right_in ,R.anim.move_left_out) ;
        }


    }

    @Override
    public Intent getSupportParentActivityIntent() {
        finish();
        return super.getSupportParentActivityIntent();
    }

    @Override
    public void finish() {
        super.finish();
        Intent intent=new Intent();
        intent.setAction(BaseUtils.CALLING_ROLL_BACK);
        sendBroadcast(intent);
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
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
