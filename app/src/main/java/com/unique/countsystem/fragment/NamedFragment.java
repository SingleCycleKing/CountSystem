package com.unique.countsystem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.unique.countsystem.R;
import com.unique.countsystem.NamedActivity;
import com.unique.countsystem.view.ColorfulPicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class NamedFragment extends Fragment {
    @InjectView(R.id.named_course)
    Spinner mSpinner;
    @InjectView(R.id.named_number)
    ColorfulPicker number;

    @OnClick(R.id.named_start)
    public void start() {
        Intent intent = new Intent(new Intent(getActivity(), NamedActivity.class));
        intent.putExtra("number", number.getNumber());
        startActivity(intent);
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
        List<String> list = new ArrayList<>();
        list.add("1~3班");
        list.add("4~6班");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(dataAdapter);
    }
}
