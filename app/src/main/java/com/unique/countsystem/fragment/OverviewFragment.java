package com.unique.countsystem.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unique.countsystem.R;

import com.unique.countsystem.adapter.OverViewAdapter;
import com.unique.countsystem.utils.DebugLog;
import com.unique.countsystem.utils.OnRecyclerItemClickListener;
import com.unique.countsystem.ChartActivity;
import com.unique.countsystem.SummaryActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class OverviewFragment extends Fragment {

    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public OverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View overView = inflater.inflate(R.layout.fragment_overview, container, false);

        //in fragment use this ,in activity use ButterKnife.inject(activity.this)；
        ButterKnife.inject(this, overView);
        init(overView);
        return overView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(getActivity(),
                new OnRecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                switch (position) {
                    case 0:

                        Intent intentChart = new Intent(getActivity(), ChartActivity.class);
                        startActivity(intentChart);
                        //getActivity().overridePendingTransition(R.drawable.go_out,R.drawable.go_in);
                        break;
                    case 1:
                        Intent intentSummary = new Intent(getActivity(), SummaryActivity.class);
                        startActivity(intentSummary);
                        //getActivity().overridePendingTransition(R.drawable.go_in, R.drawable.go_out);
                        break;
                    default:

                        break;
                }

            }
        }));
    }

    private void init(View overView) {

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView

        mRecyclerView.setHasFixedSize(true);


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new OverViewAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);

    }

}
