package com.unique.countsystem.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.unique.countsystem.NamedActivity;
import com.unique.countsystem.R;
import com.unique.countsystem.Record;
import com.unique.countsystem.Student;
import com.unique.countsystem.adapter.InfoAdapter;
import com.unique.countsystem.database.model.absenceType;
import com.unique.countsystem.utils.BaseUtils;
import com.unique.countsystem.utils.DebugLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RollCallFragment extends Fragment {

    private ArrayList<String> mList;
    private float x;
    private int position = 0;
    private InfoAdapter adapter;
    private boolean isFlying = false;
    private SpeechSynthesizer mSynthesizer;
    private ArrayList<Student> rolls;

    @InjectView(R.id.roll_name)
    TextView name;
    @InjectView(R.id.roll_info)
    ListView mListView;
    @InjectView(R.id.roll_info_layout)
    RelativeLayout infoLayout;

    @OnClick(R.id.roll_arrived)
    public void arrive() {
        if (!isFlying)
            dataHandler();
    }

    @OnClick(R.id.roll_leave)
    public void leave() {
        if (!isFlying)
            dataHandler();
    }

    @OnClick(R.id.roll_truancy)
    public void truancy() {
        if (!isFlying)
            dataHandler();
    }

//    private DbHelper dbHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        rolls = BaseUtils.getStudent(NamedActivity.number);
        mList = new ArrayList<>();
        setData();
        adapter = new InfoAdapter(getActivity(), mList);
        mListView.setAdapter(adapter);
        mSynthesizer = SpeechSynthesizer.createSynthesizer(getActivity(), null);
        mSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        mSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        mSynthesizer.setParameter(SpeechConstant.SPEED, "50");
        mSynthesizer.setParameter(SpeechConstant.VOLUME, "80");
        int code = mSynthesizer.startSpeaking("卧槽", mListener);
        DebugLog.e("fuck" + code);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_roll_call, container, false);
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
            if (record.getAbsenceType().equals(absenceType.VACATE.toInteger())) vacate++;
            if (record.getAbsenceType().equals(absenceType.ABSENCE.toInteger())) absence++;
        }
        mList.add("已缺勤次数：" + vacate);
        mList.add("已请假次数：" + absence);
    }

    private void dataHandler() {
        if (position == 0)
            x = infoLayout.getX();
        if (position < rolls.size()) {
            position++;
            final ObjectAnimator fade = BaseUtils.moveAnim(500, infoLayout, x, x - 1000, BaseUtils.FADE_ANIM, "x");
            final ObjectAnimator appear = BaseUtils.moveAnim(500, infoLayout, x + 1000, x, BaseUtils.APPEAR_ANIM, "x");
            appear.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isFlying = false;
                    mSynthesizer.startSpeaking(rolls.get(position).getName(), mListener);
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
        } else {
            Intent intent = new Intent();
            intent.setAction(BaseUtils.HAS_FINISHED_CALLING_ROLL);
            getActivity().sendBroadcast(intent);
        }
    }

    private SynthesizerListener mListener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {
            DebugLog.e("read");
        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {
            DebugLog.e("buffer");
        }

        @Override
        public void onSpeakPaused() {
            DebugLog.e("pause");
        }

        @Override
        public void onSpeakResumed() {
            DebugLog.e("resume");
        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {
            DebugLog.e("progress");
        }

        @Override
        public void onCompleted(SpeechError speechError) {
            DebugLog.e("fuck");
            speechError.printStackTrace();
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };
}
