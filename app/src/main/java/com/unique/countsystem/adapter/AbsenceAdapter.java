package com.unique.countsystem.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unique.countsystem.R;
import com.unique.countsystem.database.DbHelper;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class AbsenceAdapter extends RecyclerView.Adapter<AbsenceAdapter.ViewHolder> {


    private final LayoutInflater mLayoutInflater;
    private List<String> mList;

    public AbsenceAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mList = DbHelper.getInstance().getAllClassList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = mLayoutInflater.inflate(R.layout.summary_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder._class.setText(mList.get(position));
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.summary_class)
        TextView _class;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}