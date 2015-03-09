package com.unique.countsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.unique.countsystem.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Unique Studio on 15/3/6.
 * adapter
 */
public class MenuAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private String[] menus;

    public MenuAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        menus = context.getResources().getStringArray(R.array.menu);
    }

    @Override
    public int getCount() {
        return menus.length;
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
            convertView = mInflater.inflate(R.layout.menu_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.title.setText(menus[position]);
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.menu_title)
        TextView title;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
