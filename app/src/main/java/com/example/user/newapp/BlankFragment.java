
package com.example.user.newapp;
/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 */


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.example.user.newapp.BaseUtils.VMGBase;


/**
 * Created by Seyfullah Semen
 */
public class BlankFragment extends Fragment {
    private static final String TAG = "BlankFragment";
    // create the variables
    private WebView webView;
    private boolean isViewable;
    VMGBase frag;


    private NestedScrollView scroll;

    private RelativeLayout rela;

    // this is a default constructor this is required in a fragment
    public BlankFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * this is the method where the webview is filled with our index file
     * and tests are done to see whether the functions work correctly or not
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_blank, container, false);
        Log.d(TAG, "We zijn er "); // this is for debugging reasons
        scroll = (NestedScrollView) v.findViewById(R.id.scroll);
        rela = (RelativeLayout) v.findViewById(R.id.rela);
        webView = (WebView) v.findViewById(R.id.webView);

        frag = new VMGBase(getActivity(), webView);

        frag.startVMG(); // this will start everything that you need to load inside the view
        scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                frag.VMGScrollEvent(scrollY, scrollX, rela);
            }
        });

        return v; // return the view
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // just add these 2 lines of code to load your ad


        ///////////////////////////////////////////////////////////////////////////////////////
    }


}
