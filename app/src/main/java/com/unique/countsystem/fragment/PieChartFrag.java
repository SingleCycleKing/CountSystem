package com.unique.countsystem.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.unique.countsystem.R;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;

import java.util.ArrayList;

public class PieChartFrag extends SimpleFragment {

    public static Fragment newInstance() {
        return new PieChartFrag();
    }

    private PieChart mChart;

    protected String[] mParties = new String[]{
            "软工1班", "软工2班", "软工3班", "软工4班", "软工5班", "软工6班"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_simple_pie, container, false);

        init(v);
        return v;

    }

    private void init(View view) {

        mChart = (PieChart) view.findViewById(R.id.pieChart1);
        mChart.setDescription("");
        mChart.setCenterText("各班缺勤率");
        mChart.setCenterTextSize(22f);
        mChart.setHoleRadius(45f);
        mChart.setTransparentCircleRadius(50f);
        mChart.setDrawCenterText(true);
        mChart.setDrawHoleEnabled(true);
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        mChart.setUsePercentValues(true);
        mChart.setTouchEnabled(false);
        //mChart.setData(generatePieData());
        mChart.setCenterTextSize(20f);
        setData(5);
        Legend l = mChart.getLegend();
        l.setPosition(LegendPosition.RIGHT_OF_CHART);

    }

    private void setData(int count) {

        float number = 100;
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        for (int i = 0; i < count + 1; i++) {
            yVals1.add(new Entry((float) (Math.random() * number) + number / 5, i));

        }

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < count + 1; i++) {

            xVals.add(mParties[i % mParties.length]);
        }
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3f);

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);

        mChart.setData(data);
        mChart.highlightValues(null);

        mChart.invalidate();
    }


}