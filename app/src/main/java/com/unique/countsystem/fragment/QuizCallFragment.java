
package com.unique.countsystem.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.unique.countsystem.R;
import com.unique.countsystem.Record;
import com.unique.countsystem.Student;
import com.unique.countsystem.adapter.InfoAdapter;
import com.unique.countsystem.database.DbHelper;
import com.unique.countsystem.database.model.absenceType;
import com.unique.countsystem.utils.BaseUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class QuizCallFragment extends Fragment {

    private ArrayList<String> mList;

    private float x;

    private int position = 0;

    private InfoAdapter adapter;

    private boolean isFlying = false;

    private boolean isVoice = false;

    private SpeechSynthesizer mSynthesizer;

    private ArrayList<Student> rolls;

    private long id;

    private Handler handler;

    @InjectView(R.id.quiz_roll_number)
    TextView number;

    @InjectView(R.id.quiz_roll_name)
    TextView name;

    @InjectView(R.id.quiz_roll_info)
    ListView mListView;

    @InjectView(R.id.quiz_roll_info_layout)
    RelativeLayout infoLayout;

    @InjectView(R.id.quiz_voice)
    TextView voiceView;

    public QuizCallFragment() {

    }

    @OnClick(R.id.quiz_voice)
    public void quizVoice() {
        if (!isVoice) {
            handler.removeCallbacks(mRunnable);
            mSynthesizer.pauseSpeaking();
            isVoice = true;

            voiceView.setBackgroundResource(R.mipmap.quiz_stop_voice);
        }
//        else {
//            backVoice();
//            mSynthesizer.resumeSpeaking();
//        }
    }

    @OnClick(R.id.quiz_roll_next)
    public void quizArrive() {
        if (!isFlying) {
            handler.removeCallbacks(mRunnable);
            mSynthesizer.stopSpeaking();
            DbHelper.getInstance().insertOrReplaceAbsenceRecord(
                    DbHelper.getInstance().createAbsenceRecordModel(absenceType.NORMAL,
                            rolls.get(position), id), rolls.get(position));
            dataHandler();
        }
        backVoice();
    }

    @OnClick(R.id.quiz_roll_finish)
    public void quizLeave() {
        if (!isFlying) {
            mSynthesizer.stopSpeaking();
            getActivity().finish();
        }
        backVoice();
    }

    @OnClick(R.id.quiz_roll_truancy)
    public void truancy() {
        if (!isFlying) {
            handler.removeCallbacks(mRunnable);
            mSynthesizer.stopSpeaking();
            DbHelper.getInstance().insertOrReplaceAbsenceRecord(
                    DbHelper.getInstance().createAbsenceRecordModel(absenceType.VACATE,
                            rolls.get(position), id), rolls.get(position));
            dataHandler();
        }
        backVoice();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        mSynthesizer = SpeechSynthesizer.createSynthesizer(getActivity(), null);
        mSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        mSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        mSynthesizer.setParameter(SpeechConstant.SPEED, "50");
        mSynthesizer.setParameter(SpeechConstant.VOLUME, "80");
        id = this.getArguments().getLong("id");

    }

    @Override
    public void onStart() {
        super.onStart();
        rolls = new ArrayList<>();
        ArrayList<String> classes = this.getArguments().getStringArrayList("classes");
        List<Student> students = new ArrayList<>();
        for (String s : classes) {
            students.addAll(DbHelper.getInstance().getAllStudentListWithClass(s));
        }
        rolls = BaseUtils.getStudent(students.size(), students);
        mList = new ArrayList<>();
        setData();
        number.setText("第" + (position + 1) + "个");
        mRunnable.run();
        adapter = new InfoAdapter(getActivity(), mList);
        mListView.setAdapter(adapter);
        IntentFilter filter = new IntentFilter();
        filter.addAction(BaseUtils.CALLING_ROLL_BACK);
        getActivity().registerReceiver(mBroadcastReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSynthesizer.stopSpeaking();
        handler.removeCallbacks(mRunnable);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_call, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    private void setData() {
        name.setText(rolls.get(position).getName());
        mList.add("学号：" + rolls.get(position).getStudentId());
        mList.add("班级：" + rolls.get(position).get_class());
        List<Record> records = rolls.get(position).getAbsenceRecords();
        int vacate = 0;
        int absence = 0;
        for (Record record : records) {
            if (record.getAbsenceType().equals(absenceType.VACATE.toInteger()))
                vacate++;
            if (record.getAbsenceType().equals(absenceType.ABSENCE.toInteger()))
                absence++;
        }
        mList.add("已缺勤次数：" + vacate);
        mList.add("已请假次数：" + absence);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSynthesizer.stopSpeaking();
    }

    @Override
    public void onStop() {
        mSynthesizer.stopSpeaking();
        super.onStop();
        getActivity().unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mSynthesizer.stopSpeaking();
    }

    private void dataHandler() {
        if (position == 0)
            x = infoLayout.getX();
        position++;
        number.setText("第" + (position + 1));
        final ObjectAnimator fade = BaseUtils.moveAnim(500, infoLayout, x, x - 1000,
                BaseUtils.FADE_ANIM, "x");
        final ObjectAnimator appear = BaseUtils.moveAnim(500, infoLayout, x + 1000, x,
                BaseUtils.APPEAR_ANIM, "x");
        appear.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isFlying = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        fade.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isFlying = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mList.clear();
                setData();
                adapter.notifyDataSetChanged();
                mRunnable.run();
                appear.start();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        fade.start();

    }

    private SynthesizerListener mListener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        @Override
        public void onCompleted(SpeechError speechError) {
            handler.postDelayed(mRunnable, 2000);
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

    public void backVoice() {
        isVoice = false ;
        voiceView.setBackgroundResource(R.mipmap.quiz_voice);
    }
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mSynthesizer.startSpeaking(rolls.get(position).getName(), mListener);
        }
    };

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BaseUtils.CALLING_ROLL_BACK)) {
                handler.removeCallbacks(mRunnable);
                mSynthesizer.stopSpeaking();
                mSynthesizer.destroy();
            }
        }
    };
}
