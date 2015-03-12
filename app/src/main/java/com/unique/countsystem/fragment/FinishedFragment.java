package com.unique.countsystem.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unique.countsystem.R;
import com.unique.countsystem.adapter.FinishedAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FinishedFragment extends Fragment {
    @InjectView(R.id.finished_list)
    RecyclerView mRecyclerView;
    private String[] names = {"张三", "李四", "王五", "赵六", "雷丹雄"};
    private Integer[] type = {0, 0, 1, 2, 1};

    @Override
    public void onStart() {
        super.onStart();
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        FinishedAdapter adapter = new FinishedAdapter(getActivity(), new ArrayList<>(Arrays.asList(names)), new ArrayList<>(Arrays.asList(type)));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finished, container, false);
        ButterKnife.inject(this, view);
        return view;
    }


}
