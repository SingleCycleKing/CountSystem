package com.unique.countsystem.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.unique.countsystem.R;

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


    private int position = 0;

    @OnClick(R.id.roll_arrived)
    private void arrive() {
        while (position < 10) {
            setData();
            position++;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_roll_call, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    private void setData() {

    }
}
