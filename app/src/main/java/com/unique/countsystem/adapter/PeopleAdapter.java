package com.unique.countsystem.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unique.countsystem.R;
import com.unique.countsystem.Record;
import com.unique.countsystem.Student;
import com.unique.countsystem.database.DbHelper;
import com.unique.countsystem.database.model.absenceType;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {


    private final LayoutInflater mLayoutInflater;
    private List<Student> mList;

    public PeopleAdapter(Context context, String _class) {
        mLayoutInflater = LayoutInflater.from(context);
        mList = DbHelper.getInstance().getAllStudentListWithClass(_class);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.people_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mList.get(position).getName());
        holder.id.setText(mList.get(position).getStudentId());
        holder._class.setText(mList.get(position).get_class());
        int absence = 0, vacate = 0;
        for (Record record : mList.get(position).getAbsenceRecords()) {
            if (record.getAbsenceType() == absenceType.ABSENCE.toInteger()) absence++;
            if (record.getAbsenceType() == absenceType.VACATE.toInteger()) vacate++;
        }
        holder.absence.setText("请假次数：" + absence);
        holder.vacate.setText("缺勤次数：" + vacate);
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
        @InjectView(R.id.people_name)
        TextView name;
        @InjectView(R.id.people_id)
        TextView id;
        @InjectView(R.id.people_class)
        TextView _class;
        @InjectView(R.id.people_absence)
        TextView absence;
        @InjectView(R.id.people_vacate)
        TextView vacate;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}