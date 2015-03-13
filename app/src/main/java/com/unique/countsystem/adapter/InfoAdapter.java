package com.unique.countsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.unique.countsystem.R;
import com.unique.countsystem.utils.DebugLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class InfoAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<String> mList;

    public InfoAdapter(Context context, ArrayList<String> mList) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return null == mList ? 0 : mList.size();
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
            convertView = mLayoutInflater.inflate(R.layout.info_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.mTextView.setText(mList.get(position));
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.info_text)
        TextView mTextView;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
