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


        id = DbHelper.getInstance().insertOrReplaceRecordTime(new Date());

        QuizCallFragment quizCallFragment = new QuizCallFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        bundle.putStringArrayList("classes", getIntent().getStringArrayListExtra("classes"));
        quizCallFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.quiz_layout, quizCallFragment);
        transaction.commit();
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

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
