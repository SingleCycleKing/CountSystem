package com.unique.countsystem.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unique.countsystem.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }


}


//Error:Execution failed for task ':app:processDebugResources'.
  //      > com.android.ide.common.process.ProcessException: org.gradle.process.internal.ExecException: Process 'command '/home/chen/sdk/build-tools/21.1.2/aapt'' finished with non-zero exit value 1