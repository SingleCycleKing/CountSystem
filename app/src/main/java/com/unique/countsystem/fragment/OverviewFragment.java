package com.unique.countsystem.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unique.countsystem.AbsenceActivity;
import com.unique.countsystem.ChartActivity;
import com.unique.countsystem.R;
import com.unique.countsystem.SummaryActivity;
import com.unique.countsystem.adapter.OverViewAdapter;
import com.unique.countsystem.utils.OnRecyclerItemClickListener;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class OverviewFragment extends Fragment {
    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View overView = inflater.inflate(R.layout.fragment_overview, container, false);
        ButterKnife.inject(this, overView);
        init();
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
                                Intent intentSummary = new Intent(getActivity(), SummaryActivity.class);
                                startActivity(intentSummary);
                                getActivity().overridePendingTransition(R.anim.move_right_in, R.anim.move_left_out);
                                break;
                            case 1:
                                Intent intentAbsence = new Intent(getActivity(), AbsenceActivity.class);
                                startActivity(intentAbsence);
                                getActivity().overridePendingTransition(R.anim.move_right_in, R.anim.move_left_out);
                                break;
                            case 2:
                                Intent intentChart = new Intent(getActivity(), ChartActivity.class);
                                startActivity(intentChart);
                                getActivity().overridePendingTransition(R.anim.move_right_in, R.anim.move_left_out);
                                break;
                            default:

                                break;
                        }

                    }
                }));
    }

    private void init() {
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new OverViewAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);

    }

}
