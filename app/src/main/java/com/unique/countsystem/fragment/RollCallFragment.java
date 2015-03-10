package com.unique.countsystem.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.unique.countsystem.R;
import com.unique.countsystem.database.DbHelper;
import com.unique.countsystem.student;
import com.unique.countsystem.utils.BaseUtils;
import com.unique.countsystem.utils.DebugLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RollCallFragment extends Fragment {
    @InjectView(R.id.sliding_bar)
    SeekBar mSeekBar;
    @InjectView(R.id.roll_id)
    TextView id;
    @InjectView(R.id.roll_name)
    TextView name;
    @InjectView(R.id.roll_absence_number)
    TextView absenceNumber;
    @InjectView(R.id.roll_leave_number)
    TextView leaveNumber;
    @InjectView(R.id.roll_info_layout)
    RelativeLayout infoLayout;

    private String[] classes = {"软工三班", "软工三班", "软工三班", "软工三班", "软工三班"};
    private String[] names = {"张三", "李四", "王五", "赵六", "雷丹雄"};
    private String[] ids = {"U201317490", "U201317491", "U201317492", "U201317493", "U201317494"};

    private List<student> students = new ArrayList<>();
    private int position = 0;
//    private DbHelper dbHelper;

    @OnClick(R.id.roll_arrived)
    public void arrive() {
        if (position < 4) {
            position++;
            ObjectAnimator fade = BaseUtils.scaleAnim(500, infoLayout, 1, 0, BaseUtils.SCALE_FADE_ANIM);
            fade.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    setData();
                    BaseUtils.scaleAnim(500, infoLayout, 0, 1, BaseUtils.SCALE_APPEAR_ANIM).start();
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
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mSeekBar.setMax(100);
        mSeekBar.setProgress(50);
        setData();
//
//        dbHelper = DbHelper.getInstance();
//        for (int i = 0; i < 5; i++) {
//            students.add(DbHelper.createStudentModel(names[i], ids[i], classes[i]));
//        }
//        dbHelper.insertStudentsList(students);
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
        id.setText("学号：" + ids[position]);
        absenceNumber.setText("已逃课次数：0");
        leaveNumber.setText("已请假次数：0");
    }
}
