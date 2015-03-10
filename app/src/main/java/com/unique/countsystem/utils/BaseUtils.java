package com.unique.countsystem.utils;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.unique.countsystem.R;

import java.util.List;
import java.util.Random;


public class BaseUtils {
    public static void setToolbar(Toolbar mToolbar, ActionBarActivity activity) {
        activity.setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(activity.getResources().getColor(R.color.white));
        mToolbar.setTitle(activity.getTitle());
        activity.setSupportActionBar(mToolbar);
    }
//    public static List<Integer> getStudent() {
//        int id = studentIds.get(position);
//        for (int i = 0; i < Math.pow(2, absenceNumber); i++) {
//            studentIds.add(id);
//        }
//         getRandomNumber(studentIds.size());
//    }

    public static int getRandomNumber(int allNumber) {
        return (int) (Math.random() * allNumber);
    }
}
