package com.example.user.newapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.example.user.newapp.BaseFrag.VMGBaseFragment;
import com.example.user.newapp.ConfigVMG.VMGBuilder;
import com.example.user.newapp.VMGCustom.VMGCustomView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends VMGBaseFragment {

    private NestedScrollView scroller;
    private RelativeLayout rela_test;
    private VMGCustomView view;
   private  VMGBuilder build;


    public TestFragment() {
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_test, container, false);

        scroller = (NestedScrollView) v.findViewById(R.id.scroller);
        rela_test = (RelativeLayout) v.findViewById(R.id.rela_test);
        view = (VMGCustomView) v.findViewById(R.id.VMGCustomView);

        super.startVMG(view);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
