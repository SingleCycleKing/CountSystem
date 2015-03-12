package com.unique.countsystem.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.unique.countsystem.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ExcelAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        return null;
    }

    static class ViewHolder {
        @InjectView(R.id.excel_radio)
        RadioButton mRadioButton;
        @InjectView(R.id.excel_name)
        TextView mTextView;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
