package com.unique.countsystem.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unique.countsystem.R;

public class AboutUsFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View overView = inflater.inflate(R.layout.about_us_layout, container, false);
        init();
        return overView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void init() {

    }

}
