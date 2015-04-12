package com.unique.countsystem.adapter;

import com.unique.countsystem.R;
import com.unique.countsystem.database.DbHelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

import java.util.ArrayList;
import java.util.List;

public class InputAdapter extends RecyclerView.Adapter<InputAdapter.ViewHolder> {
    private List<String> mData;
    private LayoutInflater mInflater;
    private ArrayList<String> classes;

    public InputAdapter(Context context) {
        mData = DbHelper.getInstance().getAllClassList();
        mInflater = LayoutInflater.from(context);
        classes = new ArrayList<>();
    }

    @Override
    public InputAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.input_class, parent, false);
        return new ViewHolder(view);
    }

    public ArrayList<String> getClasses() {
        return classes;
    }

    @Override
    public void onBindViewHolder(InputAdapter.ViewHolder holder, final int position) {
        holder.name.setText(mData.get(position));
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) classes.add(mData.get(position));
                else classes.remove(mData.get(position));
                if (0 != classes.size()) {
                    int number = 0;
                    for (String s : classes) {
                        number += DbHelper.getInstance().getAllStudentListWithClass(s).size();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == mData ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.class_name)
        TextView name;
        @InjectView(R.id.class_check)
        CheckBox mCheckBox;
        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
