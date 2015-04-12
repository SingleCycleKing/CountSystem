
package com.unique.countsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class WelcomeActivity extends ActionBarActivity {

    private String name;

    private boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();
    }

    private void init() {
        SharedPreferences sharedPreferences1 = getSharedPreferences(name, MODE_PRIVATE);
        isFirst = sharedPreferences1.getBoolean("isFirst", true);
        if (!isFirst) {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            isFirst = false;
            SharedPreferences.Editor editor = sharedPreferences1.edit();
            editor.putBoolean("isFirst", isFirst);
            editor.commit();

                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();



        }
    }


}
