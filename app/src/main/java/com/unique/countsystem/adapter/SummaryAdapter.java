package com.unique.countsystem.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unique.countsystem.R;
import com.unique.countsystem.Record;
import com.unique.countsystem.RecordTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.ViewHolder> {


    private final LayoutInflater mLayoutInflater;
    private List<RecordTime> mList;

    public SummaryAdapter(Context context, List<RecordTime> mList) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.summary_item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Date date = mList.get(position).getTime();
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日 '\n' HH:mm:ss");
        holder.time.setText(format.format(date));
        List<Record> records = mList.get(position).getAbsenceTimes();
        for (Record record : records) {
            if (null != record.getStudent())
                holder._class.setText(record.getStudent().get_class());
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.summary_time)
        TextView time;
        @InjectView(R.id.summary_class)
        TextView _class;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}