package com.unique.countsystem.utils;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

import com.unique.countsystem.R;
import com.unique.countsystem.Record;
import com.unique.countsystem.Student;
import com.unique.countsystem.database.DbHelper;
import com.unique.countsystem.database.model.absenceType;

import java.util.ArrayList;
import java.util.List;


public class BaseUtils {
    public static int FADE_ANIM = 0;
    public static int APPEAR_ANIM = 1;
    public static String HAS_FINISHED_CALLING_ROLL = "finish";
    public static String FINISHED_UPDATE_DATA = "finished_update_data";
    public static String CALLING_ROLL_BACK = "call_roll_back";

    public static void setToolbar(Toolbar mToolbar, ActionBarActivity activity) {
        activity.setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(activity.getResources().getColor(R.color.white));
        mToolbar.setTitle(activity.getTitle());
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static ArrayList<Student> getStudent(int allNumber, List<Student> mStudents) {
        if (0 != allNumber) {
            ArrayList<String> ids = new ArrayList<>();
            ArrayList<Student> roll = new ArrayList<>();
            for (int i = 0; i < mStudents.size(); i++) {
                List<Record> records = mStudents.get(i).getAbsenceRecords();
                int absence = 0;
                for (Record record : records) {
                    if (record.getAbsenceType() != absenceType.NORMAL.toInteger()) absence++;
                }
                for (int j = 0; j < absence + 1; j++) {
                    ids.add(mStudents.get(i).getStudentId());
                }
            }
            for (int i = 0; i < allNumber; i++) {
                if (0 != ids.size()) {
                    Student student = DbHelper.getInstance().getStudentByStudentId(ids.get(getRandomNumber(ids.size())));
                    for (int k = 0; k < ids.size(); k++) {
                        DebugLog.e("id" + student.getStudentId());
                        if (ids.get(k).equals(student.getStudentId())) {
                            ids.remove(k);
                            DebugLog.e("k" + k);
                        }
                    }
                    roll.add(student);
                }
            }
            return roll;
        } else return null;
    }

    public static int getRandomNumber(int allNumber) {
        return (int) (Math.random() * allNumber);
    }

    public static ObjectAnimator scaleAnim(long duration, View view, int from, int to, int type) {
        PropertyValuesHolder propertyValuesHolderX = PropertyValuesHolder.ofFloat("scaleX", from, to);
        PropertyValuesHolder propertyValuesHolderY = PropertyValuesHolder.ofFloat("scaleY", from, to);
        Interpolator interpolator = null;
        if (APPEAR_ANIM == type) {
            interpolator = new OvershootInterpolator();
        } else if (FADE_ANIM == type) {
            interpolator = new AnticipateInterpolator();
        }
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, propertyValuesHolderX, propertyValuesHolderY).setDuration(duration);
        animator.setInterpolator(interpolator);
        return animator;
    }

    public static ObjectAnimator moveAnim(long duration, View view, float from, float to, int type, String s) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, s, from, to);
        Interpolator interpolator = null;
        animator.setDuration(duration);
        if (APPEAR_ANIM == type) {
            interpolator = new OvershootInterpolator();
        } else if (FADE_ANIM == type) {
            interpolator = new AnticipateInterpolator();
        }
        animator.setInterpolator(interpolator);
        return animator;
    }


}
