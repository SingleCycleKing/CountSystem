package utils;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.unique.countsystem.R;

/**
 * Created by Unique Studio on 15/3/7.
 */
public  class BaseUtils {
    public static void setToolbar(Toolbar mToolbar,ActionBarActivity activity){
        activity.setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(activity.getResources().getColor(R.color.white));
        mToolbar.setTitle(activity.getTitle());
        activity.setSupportActionBar(mToolbar);
    }
}
