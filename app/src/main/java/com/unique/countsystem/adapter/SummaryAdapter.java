package com.unique.countsystem.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unique.countsystem.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by chen on 15-3-8.
 * adapter
 */


public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.TextViewHolder> {


    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    String[] students = {
            "王二", "张三", "李四", "赵六", "雷七", "周八", "汪九"
    };

    public SummaryAdapter(Context context) {

        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = mLayoutInflater.inflate(R.layout.summary_item_layout, parent, false);
        TextViewHolder textViewHolder = new TextViewHolder(v);
        return textViewHolder;
    }

    @Override
    public void onBindViewHolder(TextViewHolder holder, int position) {

        holder.mTextView.setText(students[position]);
    }


    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);

    }

    @Override
    public int getItemCount() {
        return students == null ? 0 : students.length;
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.summary_info_text)
        TextView mTextView;
        int position;

        TextViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position = getPosition();
                    Log.d("TextViewHolder", "onClick--> position = " + getPosition());
                }
            });
        }
    }
}