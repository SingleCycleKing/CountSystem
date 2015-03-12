package com.unique.countsystem.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unique.countsystem.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class FinishedAdapter extends RecyclerView.Adapter<FinishedAdapter.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<String> mList;
    private List<Integer> types;

    public FinishedAdapter(Context context, ArrayList<String> mList, ArrayList<Integer> infos) {
        this.mList = mList;
        this.types = infos;
        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.finished_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mList.get(position));
        switch (types.get(position)) {
            case 0:
                holder.arrive.setImageResource(R.mipmap.selected);
                break;
            case 1:
                holder.leave.setImageResource(R.mipmap.selected);
                break;
            case 2:
                holder.truancy.setImageResource(R.mipmap.selected);
                break;
        }
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.finished_name)
        TextView name;
        @InjectView(R.id.finished_arrive)
        ImageView arrive;
        @InjectView(R.id.finished_leave)
        ImageView leave;
        @InjectView(R.id.finished_truancy)
        ImageView truancy;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);

        }
    }
}