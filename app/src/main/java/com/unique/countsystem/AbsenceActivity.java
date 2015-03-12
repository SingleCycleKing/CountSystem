package com.unique.countsystem;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.unique.countsystem.adapter.AbsenceAdapter;
import com.unique.countsystem.utils.OnRecyclerItemClickListener;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class AbsenceActivity extends ActionBarActivity {

    @InjectView(R.id.absence_recycler_view)
    RecyclerView absenceRecyclerView;
    @InjectView(R.id.absence_image_view)
    ImageView absenceImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absence);
        ButterKnife.inject(this) ;
        init() ;
    }


    @Override
    public void onStart() {
        super.onStart();
        absenceRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(this,
                new OnRecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        switch (position) {
                            case 0:

                                break;
                            case 1:

                                break;
                            default:

                                break;
                        }

                    }
                }));
    }


    private void init() {


        absenceImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish() ;
            }
        });

        absenceRecyclerView.setLayoutManager(new LinearLayoutManager(this)) ;
        absenceRecyclerView.setAdapter(new AbsenceAdapter(this)) ;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_absence, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
