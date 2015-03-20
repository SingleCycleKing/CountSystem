package com.unique.countsystem.fragment;

import android.content.Intent;
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
import com.unique.countsystem.R;
import com.unique.countsystem.adapter.ClassAdapter;
import com.unique.countsystem.database.DbHelper;
import com.unique.countsystem.view.ColorfulPicker;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class NamedFragment extends Fragment {
    private ClassAdapter mAdapter;

    @InjectView(R.id.named_classes)
    RecyclerView mListView;
    @InjectView(R.id.named_number)
    ColorfulPicker number;
    @InjectView(R.id.named_time)
    TextView time;

    @OnClick(R.id.named_start)
    public void start() {
        if (null == DbHelper.getInstance().getAllClassList())
            Toast.makeText(getActivity(), "数据库里并不存在资料,请添加", Toast.LENGTH_SHORT).show();
        else if (0 == mAdapter.getClasses().size())
            Toast.makeText(getActivity(), "请选择班级", Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent(new Intent(getActivity(), NamedActivity.class));
            intent.putStringArrayListExtra("classes",mAdapter.getClasses());
            intent.putExtra("number", number.getNumber());
            startActivity(intent);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_named, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Calendar calendar = Calendar.getInstance();
        time.setText(calendar.get(Calendar.MONTH) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日");
        mAdapter = new ClassAdapter(getActivity(), number);
        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListView.setAdapter(mAdapter);
    }
}
