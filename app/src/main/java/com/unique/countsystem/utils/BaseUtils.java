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

import java.util.List;
import java.util.Random;


public class BaseUtils {
    public static int SCALE_FADE_ANIM = 0;
    public static int SCALE_APPEAR_ANIM = 1;

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

    public static ObjectAnimator scaleAnim(long duration, View view, int from, int to, int type) {
        PropertyValuesHolder propertyValuesHolderX = PropertyValuesHolder.ofFloat("scaleX", from, to);
        PropertyValuesHolder propertyValuesHolderY = PropertyValuesHolder.ofFloat("scaleY", from, to);
        Interpolator interpolator = null;
        if (SCALE_APPEAR_ANIM == type) {
            interpolator = new OvershootInterpolator();
        } else if (SCALE_FADE_ANIM == type) {
            interpolator = new AnticipateInterpolator();
        }
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, propertyValuesHolderX, propertyValuesHolderY).setDuration(duration);
        animator.setInterpolator(interpolator);
        return animator;
    }
}
