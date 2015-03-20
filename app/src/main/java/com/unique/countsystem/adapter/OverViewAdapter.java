package com.unique.countsystem.adapter;


import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unique.countsystem.R;

import butterknife.ButterKnife;
import butterknife.InjectView;



public class OverViewAdapter extends RecyclerView.Adapter<OverViewAdapter.TextViewHolder> {


    private final LayoutInflater mLayoutInflater;
    private String[] myDataset = {
            "历史记录", "人员总览","图表"
    };

    public OverViewAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = mLayoutInflater.inflate(R.layout.item_layout, parent, false);
        return new TextViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TextViewHolder holder, int position) {

        holder.mTextView.setText(myDataset[position]);
    }


    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);

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
        }
    }
}