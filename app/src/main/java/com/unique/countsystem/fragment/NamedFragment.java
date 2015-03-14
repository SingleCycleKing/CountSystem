package com.unique.countsystem.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.unique.countsystem.NamedActivity;
import com.unique.countsystem.R;
import com.unique.countsystem.database.DbHelper;
import com.unique.countsystem.view.ColorfulPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class NamedFragment extends Fragment {

    public static int _class;

    @InjectView(R.id.named_course)
    Spinner mSpinner;
    @InjectView(R.id.named_number)
    ColorfulPicker number;
    @InjectView(R.id.named_time)
    TextView time;

    @OnClick(R.id.named_start)
    public void start() {
        if (null != DbHelper.getInstance().getAllClassList()) {

            Intent intent = new Intent(new Intent(getActivity(), NamedActivity.class));
            intent.putExtra("number", number.getNumber());
            startActivity(intent);
        } else Toast.makeText(getActivity(), "数据库里并不存在资料,请添加", Toast.LENGTH_SHORT).show();
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
        final List<String> _classes;
        if (null != DbHelper.getInstance().getAllClassList()) {
            _classes = new ArrayList<>();
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("xlsNumber", Context.MODE_PRIVATE);
            if (sharedPreferences.getInt("number", 0) == 1)
                _classes.add("软工1～3班");
            else if (sharedPreferences.getInt("number", 0) == 1) {
                _classes.add("软工1～3班");
                _classes.add("软工4～6班及数媒班");
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item, _classes);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(dataAdapter);
            _class = 0;
            number.setMax(DbHelper.getInstance().getAllStudents().size());
            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    _class = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }
}
