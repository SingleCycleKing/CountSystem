package com.unique.countsystem.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.unique.countsystem.R;
import com.unique.countsystem.utils.DebugLog;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ColorfulPicker extends FrameLayout {
    public ColorfulPicker(Context context) {
        this(context, null);
    }

    @InjectView(R.id.number_picker)
    NumberPicker mNumberPicker;

    public ColorfulPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = ((LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.colorful_picker, this, true);
        ButterKnife.inject(view);

        mNumberPicker.setMaxValue(20);
        mNumberPicker.setMinValue(1);
    }

    public void setMax(int max) {
        mNumberPicker.setMaxValue(max);
    }

    public int getNumber() {
        return mNumberPicker.getValue();
    }

    public void setNumber(int value) {
        mNumberPicker.setValue(value);
    }
}
