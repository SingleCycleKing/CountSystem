package com.unique.countsystem;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExDialog extends ListActivity {
    private List<Map<String, Object>> mData;
    @SuppressLint("SdCardPath")
    private final String SDCARD = "/mnt/sdcard";
    private String mDir = SDCARD;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = this.getIntent();
        Uri uri = intent.getData();
        mDir = uri.getPath();

        mData = getData();
        MyAdapter adapter = new MyAdapter(this);
        setListAdapter(adapter);

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        LayoutParams p = getWindow().getAttributes();
        p.height = d.getHeight();
        p.width = d.getWidth();
        getWindow().setAttributes(p);
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map;
        File f = new File(mDir);
        File[] files = f.listFiles();

        if (!mDir.equals(SDCARD)) {
            map = new HashMap<>();
            map.put("title", "..");
            map.put("info", f.getParent());
            map.put("img", R.mipmap.ex_folder);
            list.add(map);
        }
        if (files != null) {
            for (File file : files) {
                map = new HashMap<>();
                map.put("title", file.getName());
                map.put("info", file.getPath());
                if (file.isDirectory())
                    map.put("img", R.mipmap.ex_folder);
                else
                    map.put("img", R.mipmap.ex_doc);
                list.add(map);
            }
        }
        return list;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        if ((Integer) mData.get(position).get("img") == R.mipmap.ex_folder) {
            mDir = (String) mData.get(position).get("info");
            mData = getData();
            MyAdapter adapter = new MyAdapter(this);
            setListAdapter(adapter);
        } else {
            finishWithResult((String) mData.get(position).get("info"));
        }
    }

    public final class ViewHolder {
        public ImageView img;
        public TextView title;
        public TextView info;
    }

    public class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return mData.size();
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.explorer_list, null);
                holder.img = (ImageView) convertView.findViewById(R.id.img);
                holder.title = (TextView) convertView.findViewById(R.id.title);
                holder.info = (TextView) convertView.findViewById(R.id.info);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.img.setBackgroundResource((Integer) mData.get(position).get(
                    "img"));
            holder.title.setText((String) mData.get(position).get("title"));
            holder.info.setText((String) mData.get(position).get("info"));
            return convertView;
        }
    }

    private void finishWithResult(String path) {
        Bundle conData = new Bundle();
        conData.putString("results", "Thanks Thanks");
        Intent intent = new Intent();
        intent.putExtras(conData);
        Uri startDir = Uri.fromFile(new File(path));
        intent.setDataAndType(startDir,
                "vnd.android.cursor.dir/lysesoft.andexplorer.file");
        setResult(RESULT_OK, intent);
        finish();
    }
}
