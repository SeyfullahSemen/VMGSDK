package com.example.user.newapp;
/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 */


import android.annotation.SuppressLint;

import android.graphics.Color;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebView;
import android.widget.RelativeLayout;


import com.example.user.newapp.BaseFrag.VMGBaseFragment;
import com.example.user.newapp.ConfigVMG.VMGBuilder;

import com.example.user.newapp.Interfaces.VMGMraidEvents;
import com.example.user.newapp.VMGCustom.VMGCustomView;

/**
 * Created by Seyfullah Semen
 */

public class BlankFragment extends VMGBaseFragment implements VMGMraidEvents {
    private static final String TAG = "BlankFragment";
    // create the variables
    private VMGCustomView webView;
    private boolean isViewable;
    private boolean isPageFinished = true;
    private boolean isLaidOut = true;
    private NestedScrollView scroll;
    private RelativeLayout rela;
    private VMGBuilder vmg;


    // this is a default constructor this is required in a fragment
    public BlankFragment() {

        Log.i(TAG, "CONST");
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
        webView = (VMGCustomView) v.findViewById(R.id.webView);


        scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                BlankFragment.super.VMGScrollEvent(scrollY, scrollX, rela, webView);


            }
        });

        getScreenSize();
        super.addJavascript(webView, "mraid.isViewable();");
        super.addJavascript(webView, "mraid.getState();");// get the state of our mraid
        return v; // return the view
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        super.startVMG(webView); // this will start everything that you need to load inside the view
        vmg = new VMGBuilder(getActivity(), "6178");


    }

    /**
     * the methods below is for checking if java can communicate with Javascript
     * we accomplish this with simple getters to check everything
     */
    @Override
    public void fireReadyEvent() {
        super.addJavascript(webView, "mraid.fireReadyEvent();");
        Log.i(TAG, "READYYYY  TO GOOO");


    }

    @Override
    public void getScreenSize() {
        super.addJavascript(webView, "mraid.getScreenSize();");
        Log.i(TAG, "Screen size is working");
    }

    @Override
    public void isViewable() {
        super.addJavascript(webView, "mraid.isViewable();");
        Log.i(TAG, " Viewable works");

    }

    @Override
    public void getDefaultPosition() {
        super.addJavascript(webView, "mraid.getDefaultPosition();");
        Log.i(TAG, " Default position is working");
    }

    @Override
    public void getState() {
        super.addJavascript(webView, "mraid.getState();");
        Log.i(TAG, " State is working");
    }

    @Override
    public void removeEventListener() {
        super.addJavascript(webView, "mraid.removeEventListener();");
        Log.i(TAG, " Removing");
    }

    @Override
    public void fireViewableChangeEvent() {
        Log.i(TAG, "fireViewableChangeEvent");
        super.addJavascript(webView, "mraid.fireViewableChangeEvent(" + isViewable + ");");


    }


//    /**
//     * this is our own WebViewClient this will make sure that a new browser will open without closing our app
//     */
//    private class VMGWebViewClient extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//            startActivity(intent);
//            return true;
//        }
//
//
//    }


}


