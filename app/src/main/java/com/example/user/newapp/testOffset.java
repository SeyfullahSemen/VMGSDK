package com.example.user.newapp;


import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class testOffset extends Fragment {
    private TextView tekst1;
    private RelativeLayout main;
    private NestedScrollView scrolls;



    public testOffset() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_offset, container, false);
        tekst1 = (TextView) view.findViewById(R.id.tekst1);
        main = (RelativeLayout) view.findViewById(R.id.main);
        scrolls = (NestedScrollView) view.findViewById(R.id.scrolls);
        return view;
    }

}
