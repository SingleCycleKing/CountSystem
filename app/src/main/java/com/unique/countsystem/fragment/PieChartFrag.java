package com.unique.countsystem.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.data.Entry;
import com.unique.countsystem.R;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;

public class PieChartFrag extends SimpleFragment {

    public static Fragment newInstance() {
        return new PieChartFrag();
    }

    private PieChart mChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_simple_pie, container, false);

        mChart = (PieChart) v.findViewById(R.id.pieChart1);


        mChart.setDescription("");

        //mChart.setCenterTextTypeface(Typeface.createFromAsset(getActivity().getAssets()));
        mChart.setCenterText("各班到勤率");
        mChart.setCenterTextSize(22f);

        mChart.setHoleRadius(45f);
        mChart.setTransparentCircleRadius(50f);
        mChart.setDrawCenterText(true);
        mChart.setDrawHoleEnabled(true);
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        mChart.setUsePercentValues(true);
        mChart.setTouchEnabled(false) ;
        mChart.setData(generatePieData());
        mChart.setCenterTextSize(20f);

        Legend l = mChart.getLegend();
        l.setPosition(LegendPosition.RIGHT_OF_CHART);

        return v;

    }
}