package com.unique.countsystem.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unique.countsystem.R;
import com.unique.countsystem.database.DbHelper;
import com.unique.countsystem.utils.BaseUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class FinishedAdapter extends RecyclerView.Adapter<FinishedAdapter.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<String> mList;
    private List<Integer> types;
    private Context mContext;

    public FinishedAdapter(Context context, ArrayList<String> mList, ArrayList<Integer> types) {
        this.mList = mList;
        this.types = types;
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.finished_item, parent, false);
        return new ViewHolder(v, mContext);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mList.get(position));
        holder.name.setTag(position);
        switch (types.get(position)) {
            case 0:
                holder.arrive.setImageResource(R.mipmap.selected);
                break;
            case 1:
                holder.truancy.setImageResource(R.mipmap.selected);
                break;
            case 2:
                holder.leave.setImageResource(R.mipmap.selected);
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


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @InjectView(R.id.finished_name)
        TextView name;
        @InjectView(R.id.finished_arrive)
        ImageView arrive;
        @InjectView(R.id.finished_leave)
        ImageView leave;
        @InjectView(R.id.finished_truancy)
        ImageView truancy;
        private Context context;


        ViewHolder(View view, Context context) {
            super(view);
            ButterKnife.inject(this, view);
            arrive.setOnClickListener(this);
            leave.setOnClickListener(this);
            truancy.setOnClickListener(this);
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setAction(BaseUtils.FINISHED_UPDATE_DATA);
            intent.putExtra("position", (int) name.getTag());
            switch (v.getId()) {
                case R.id.finished_arrive:
                    intent.putExtra("type", 0);
                    break;
                case R.id.finished_truancy:
                    intent.putExtra("type", 1);
                    break;
                case R.id.finished_leave:
                    intent.putExtra("type", 2);
                    break;
            }
            context.sendBroadcast(intent);
        }
    }
}