package com.unique.countsystem.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unique.countsystem.R;
import com.unique.countsystem.adapter.FinishedAdapter;
import com.unique.countsystem.utils.BaseUtils;
import com.unique.countsystem.utils.DebugLog;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FinishedFragment extends Fragment {
    @InjectView(R.id.finished_list)
    RecyclerView mRecyclerView;
    private String[] names = {"张三", "李四", "王五", "赵六", "雷丹雄"};
    private Integer[] type = {0, 2, 1, 2, 1};
    private ArrayList<Integer> typeList;
    private ArrayList<String> nameList;

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BaseUtils.FINISHED_UPDATE_DATA);
        getActivity().registerReceiver(mReceiver, intentFilter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        nameList = new ArrayList<>(Arrays.asList(names));
        typeList = new ArrayList<>(Arrays.asList(type));
        mRecyclerView.setAdapter(new FinishedAdapter(getActivity(), nameList, typeList));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finished, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BaseUtils.FINISHED_UPDATE_DATA)) {
                typeList.set(intent.getIntExtra("position", -1), intent.getIntExtra("type", -1));
                mRecyclerView.setAdapter(new FinishedAdapter(getActivity(), nameList, typeList));

            }
        }
    };
}
