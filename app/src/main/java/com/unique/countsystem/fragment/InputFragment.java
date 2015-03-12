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

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class InputFragment extends Fragment {
    @InjectView(R.id.excel_list)
    ListView mListView;

    @OnClick(R.id.excel_add)
    void add(TextView add) {
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
        mListView.setAdapter(new ExcelAdapter(getActivity()));
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
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

                break;

            default:
                break;
        }
    }

}
