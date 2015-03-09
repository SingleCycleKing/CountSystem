package com.unique.countsystem.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unique.countsystem.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import utils.DebugLog;
import view.CustomViewPager;

public class NamedFragment extends Fragment {
    @InjectView(R.id.named_view_pager)
    CustomViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
<<<<<<< HEAD:app/src/main/java/com/unique/countsystem/fragment/NamedFragment.java
        return inflater.inflate(R.layout.fragment_named, container, false);
=======
        View view = inflater.inflate(R.layout.fragment_named, container, false);
        ButterKnife.inject(this, view);
        return view;
>>>>>>> 55b65899548a9caa348e87bc9d61b2eecb4ca04d:app/src/main/java/fragment/NamedFragment.java
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
