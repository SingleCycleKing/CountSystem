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
import com.unique.countsystem.R;
import com.unique.countsystem.adapter.InfoAdapter;
import com.unique.countsystem.utils.BaseUtils;
import com.unique.countsystem.utils.DebugLog;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RollCallFragment extends Fragment {

    private String[] classes = {"软工三班", "软工三班", "软工三班", "软工三班", "软工三班"};
    private String[] names = {"张三", "李四", "王五", "赵六", "雷丹雄"};
    private String[] ids = {"U201317490", "U201317491", "U201317492", "U201317493", "U201317494"};
    private ArrayList<String> mList;
    private float x;
    private int position = 0;
    private InfoAdapter adapter;
    private boolean isFlying = false;
    private SpeechSynthesizer mSynthesizer;

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
        name.setText(names[position]);
        mList.add("学号：" + ids[position]);
        mList.add("班级：" + classes[position]);
        mList.add("已逃课次数：0");
        mList.add("已请假次数：0");
    }

    private void dataHandler() {
        if (position == 0)
            x = infoLayout.getX();
        if (position < 4) {
            position++;
            final ObjectAnimator fade = BaseUtils.moveAnim(500, infoLayout, x, x - 1000, BaseUtils.FADE_ANIM);
            final ObjectAnimator appear = BaseUtils.moveAnim(500, infoLayout, x + 1000, x, BaseUtils.APPEAR_ANIM);
            appear.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isFlying = false;
                    mSynthesizer.startSpeaking(names[position], mListener);
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
