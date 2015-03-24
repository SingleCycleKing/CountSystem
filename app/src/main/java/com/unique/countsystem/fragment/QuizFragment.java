package com.unique.countsystem.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.unique.countsystem.NamedActivity;
import com.unique.countsystem.QuizActivity;
import com.unique.countsystem.R;
import com.unique.countsystem.adapter.ClassAdapter;
import com.unique.countsystem.adapter.QuizAdapter;
import com.unique.countsystem.database.DbHelper;
import com.unique.countsystem.view.ColorfulPicker;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class QuizFragment extends Fragment {

    private QuizAdapter mAdapter;
    @InjectView(R.id.quiz_classes)
    RecyclerView mListView;
    @InjectView(R.id.quiz_number)
    ColorfulPicker number;
    @InjectView(R.id.quiz_time)
    TextView time;

    @OnClick(R.id.quiz_start)
    public void start() {
        if (null == DbHelper.getInstance().getAllClassList())
            Toast.makeText(getActivity(), "数据库里并不存在资料,请添加", Toast.LENGTH_SHORT).show();
        else if (0 == mAdapter.getClasses().size())
            Toast.makeText(getActivity(), "请选择班级", Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent(new Intent(getActivity(), QuizActivity.class));
            intent.putStringArrayListExtra("classes", mAdapter.getClasses());

            // for the number of people
            //intent.putExtra("number", number.getNumber());
            intent.putExtra("number" ,number.getNumberAll());
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.move_right_in, R.anim.move_left_out);
        }
    }


    public QuizFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onStart() {

        super.onStart();
        Calendar calendar = Calendar.getInstance();
        time.setText((calendar.get(Calendar.MONTH)+1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日");
        mAdapter = new QuizAdapter(getActivity(), number);
        mAdapter = new QuizAdapter(getActivity());
        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListView.setAdapter(mAdapter);
    }
}
