package com.unique.countsystem.fragment;

import android.content.Intent;
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

import com.unique.countsystem.R;
import com.unique.countsystem.NamedActivity;
import com.unique.countsystem.database.DbHelper;
import com.unique.countsystem.utils.DebugLog;
import com.unique.countsystem.view.ColorfulPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class NamedFragment extends Fragment {

    public static String _class;

    @InjectView(R.id.named_course)
    Spinner mSpinner;
    @InjectView(R.id.named_number)
    ColorfulPicker number;
    @InjectView(R.id.named_time)
    TextView time;

    @OnClick(R.id.named_start)
    public void start() {
        if (null != DbHelper.getInstance().getAllClassList()) {
            Date date = new Date();
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
            _classes = DbHelper.getInstance().getAllClassList();
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item, _classes);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinner.setAdapter(dataAdapter);
            assert _classes != null;
            _class = _classes.get(0);

            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    _class = _classes.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }
}
