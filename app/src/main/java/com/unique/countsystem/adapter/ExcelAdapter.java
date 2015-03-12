package com.unique.countsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.unique.countsystem.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ExcelAdapter extends BaseAdapter {
    private LayoutInflater mInflater;

    public ExcelAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

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
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.excel_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.mTextView.setText("软工" + position + "班");
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.excel_checkbox)
        CheckBox mCheckBox;
        @InjectView(R.id.excel_name)
        TextView mTextView;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                }
            });
        }
    }
}
