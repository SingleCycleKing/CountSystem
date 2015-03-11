package com.unique.countsystem;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.unique.countsystem.R;
import com.unique.countsystem.adapter.SummaryAdapter;
import com.unique.countsystem.fragment.OverviewFragment;
import com.unique.countsystem.utils.OnRecyclerItemClickListener;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SummaryActivity extends ActionBarActivity {

    @InjectView(R.id.summary_recycler_view)
    RecyclerView summaryRecyclerView;
    @InjectView(R.id.summary_image_view)
    ImageView summaryImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        summaryRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(this,
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

        summaryImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(SummaryActivity.this, MainActivity.class);
//                intent.putExtra("id",1) ;
//                startActivity(intent);
                finish();
            }
        });


        summaryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        summaryRecyclerView.setAdapter(new SummaryAdapter(this));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_summary, menu);
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
