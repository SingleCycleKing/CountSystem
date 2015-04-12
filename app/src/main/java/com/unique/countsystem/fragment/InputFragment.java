package com.unique.countsystem.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.unique.countsystem.ExDialog;
import com.unique.countsystem.MainActivity;
import com.unique.countsystem.R;
import com.unique.countsystem.adapter.InputAdapter;
import com.unique.countsystem.adapter.QuizAdapter;
import com.unique.countsystem.database.ExcelHandler;
import com.unique.countsystem.utils.DebugLog;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;


public class InputFragment extends Fragment {

    private InputAdapter mAdapter ;
    @InjectView(R.id.text_classes)
    RecyclerView mListView ;

    @OnClick(R.id.excel_add)
    void add() {
        Intent start = new Intent(getActivity(), ExDialog.class);
        start.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath())), "*/xls");
        startActivityForResult(start, 0);
    }

    @OnClick(R.id.excel_delete)
    void delete() {
        Toast.makeText(getActivity(), "开发中ing", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.excel_output)
    void output() {
        try {
            ExcelHandler.getInstance().WriteExcel(getActivity());
        } catch (IOException e) {
            Toast.makeText(getActivity(), "文件打开错误", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (BiffException e) {
            Toast.makeText(getActivity(), "Excel格式错误", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (WriteException e) {
            Toast.makeText(getActivity(), "文件写入错误", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        Toast.makeText(getActivity(), "写入成功，储存在/sdcard/countSystem文件夹", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        mAdapter = new InputAdapter(getActivity());
        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListView.setAdapter(mAdapter);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case MainActivity.RESULT_OK:
                String path = data.getData().getPath();
                try {
                    ExcelHandler.getInstance().ReadExcel(new File(path));
                } catch (IOException | BiffException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "导入失败", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getActivity(), "导入成功", Toast.LENGTH_SHORT).show();
                break;
            case MainActivity.RESULT_CANCELED:
                Toast.makeText(getActivity(), "取消导入", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
