package com.unique.countsystem.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.unique.countsystem.ExDialog;
import com.unique.countsystem.MainActivity;
import com.unique.countsystem.R;
import com.unique.countsystem.adapter.ExcelAdapter;
import com.unique.countsystem.database.ExcelHandler;
import com.unique.countsystem.utils.BaseUtils;
import com.unique.countsystem.utils.DebugLog;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;


public class InputFragment extends Fragment {

    private ExcelAdapter excelAdapter;

    @InjectView(R.id.excel_list)
    ListView mListView;
    @InjectView(R.id.excel_add)
    TextView add;
    @InjectView(R.id.input_layout)
    RelativeLayout mLayout;

    @OnClick(R.id.excel_delete)
    void delete() {
        excelAdapter = new ExcelAdapter(getActivity());
        mListView.setAdapter(excelAdapter);
        setAnim(add, mLayout).start();
    }

    @OnClick(R.id.excel_output)
    void output() {
        try {
            ExcelHandler.getInstance().WriteExcel(getActivity());
        } catch (IOException e) {
            Toast.makeText(getActivity(),"文件打开错误",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }  catch (BiffException e) {
            Toast.makeText(getActivity(),"Excel格式错误",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (WriteException e) {
            Toast.makeText(getActivity(),"文件写入错误",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        Toast.makeText(getActivity(),"写入成功，储存在/sdcard/countSystem文件夹",Toast.LENGTH_SHORT).show();
//        excelAdapter = new ExcelAdapter(getActivity());
//        mListView.setAdapter(excelAdapter);
//        setAnim(add, mLayout).start();
    }




    @OnClick(R.id.excel_add)
    void add() {
        Intent start = new Intent(getActivity(), ExDialog.class);
        start.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath())), "*/xls");
        startActivityForResult(start, 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        excelAdapter = new ExcelAdapter(getActivity());
        mListView.setAdapter(excelAdapter);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                excelAdapter.editList(true);
                setAnim(mLayout, add).start();
                return true;
            }
        });

    }

    private ObjectAnimator setAnim(View appear, final View fade) {
        float y = fade.getY();
        appear.setY(y + 1000);
        appear.setVisibility(View.VISIBLE);
        final ObjectAnimator fadeAnim = BaseUtils.moveAnim(500, fade, y, y + 1000, BaseUtils.FADE_ANIM, "y");
        final ObjectAnimator appearAnim = BaseUtils.moveAnim(500, appear, y + 1000, y, BaseUtils.APPEAR_ANIM, "y");
        fadeAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                appearAnim.start();
                fade.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return fadeAnim;
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
