package com.unique.countsystem.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.unique.countsystem.R;
import com.unique.countsystem.Record;
import com.unique.countsystem.database.DbHelper;
import com.unique.countsystem.database.model.absenceType;

import java.util.ArrayList;
import java.util.List;

public class PieChartFrag extends SimpleFragment {

    private List<Record> records = DbHelper.getInstance().getAllAbsenceRecords();
    private PieChart mChart;
    protected String[] mParties = new String[]{
            "软工1303班", "软工1302班", "软工1303班", "软工1304班", "软工1305班", "软工1306班"
            , "数媒1301班"
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
        mChart.setHoleRadius(30f);
        mChart.setTransparentCircleRadius(40f);
        mChart.setDrawCenterText(true);
        mChart.setDrawHoleEnabled(true);
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        mChart.setUsePercentValues(true);
        mChart.setTouchEnabled(true);
        mChart.setCenterTextSize(20f);


        setData(mParties.length);

        Legend legend = mChart.getLegend();
        legend.setEnabled(false);
    }

    private void setData(int count) {

        float number = getSum();
        int[] numberClass = getNumberFromClass(mParties);

        ArrayList<Entry> yVals1 = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            yVals1.add(new Entry((numberClass[i] / number), i));
        }

        ArrayList<String> xVals = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            xVals.add(mParties[i % mParties.length]);

        }

        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(1f) ;

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#089d87"));
        colors.add(Color.parseColor("#cf5348"));
        colors.add(Color.parseColor("#fbcc8f"));
        colors.add(Color.parseColor("#475e88"));

        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);


        mChart.setData(data);
        mChart.highlightValues(null);
        mChart.invalidate();
    }


    private int getSum() {
        int sum = 0;
        for (Record record : records) {
            if (record.getAbsenceType() != absenceType.NORMAL.toInteger()) {
                sum++;
            }
        }
        return sum;
    }


    private int[] getNumberFromClass(String... _classes) {
        int[] result = new int[_classes.length];
        for (Record record : records) {
            String a_class = record.getStudent().get_class();
            for (int i = 0; i < _classes.length; i++) {
                if (_classes[i].equals(a_class)) {
                    result[i]++;
                }
            }
        }
        return result;
    }
}