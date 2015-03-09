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


public class OverViewAdapter extends RecyclerView.Adapter<OverViewAdapter.TextViewHolder> {


    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private String[] myDataset;

    public OverViewAdapter(Context context) {

        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = mLayoutInflater.inflate(R.layout.item_layout, parent, false);
        TextViewHolder textViewHolder = new TextViewHolder(v);
        return textViewHolder;
    }

    @Override
    public void onBindViewHolder(TextViewHolder holder, int position) {

        holder.mTextView.setText(myDataset[position]);
    }


    @Override
    public int getItemCount() {
        return myDataset == null ? 0 : myDataset.length;
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.info_text)
        TextView mTextView;

        TextViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TextViewHolder", "onClick--> position = " + getPosition());
                }
            });
        }
    }
}
