package com.unique.countsystem.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.unique.countsystem.ExDialog;
import com.unique.countsystem.MainActivity;
import com.unique.countsystem.R;
import com.unique.countsystem.adapter.ExcelAdapter;
import com.unique.countsystem.database.ExcelHandler;
import com.unique.countsystem.utils.DebugLog;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import jxl.read.biff.BiffException;


public class InputFragment extends Fragment {
    @InjectView(R.id.excel_list)
    ListView mListView;
    @InjectView(R.id.excel_add)
    TextView add;
    @InjectView(R.id.excel_delete)
    TextView delete;
    @InjectView(R.id.excel_output)
    TextView output;


    @OnClick(R.id.excel_add)
    void add() {
        Intent start = new Intent(getActivity(), ExDialog.class);
        start.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath())), "*/*");
        startActivityForResult(start, 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        final ExcelAdapter excelAdapter = new ExcelAdapter(getActivity());
        mListView.setAdapter(excelAdapter);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                excelAdapter.editList(true);
//                float y = add.getY();
//                final ObjectAnimator add_fade = BaseUtils.moveAnim(500, add, y, y + 1000, BaseUtils.FADE_ANIM, "y");
//                add_fade.start();
////                final ObjectAnimator add_appear = BaseUtils.moveAnim(500, add, add.getY(), add.getY() - 1000, BaseUtils.FADE_ANIM, "y");
////                final ObjectAnimator delete_fade = BaseUtils.moveAnim(500, add, add.getY(), add.getY() + 1000, BaseUtils.FADE_ANIM, "y");
//                final ObjectAnimator delete_appear = BaseUtils.moveAnim(500, output, y + 1000, y, BaseUtils.FADE_ANIM, "y");
//                add_fade.addListener(new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        delete_appear.start();
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
//
//                    }
//                });
                return true;
            }
        });

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
                DebugLog.e(path);
                try {
                    ExcelHandler.getInstance().ReadExcel(new File(path));
                } catch (IOException | BiffException e) {
                    e.printStackTrace();
                }

                break;

            default:
                break;
        }
    }

}
