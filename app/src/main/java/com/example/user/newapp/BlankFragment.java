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
import android.webkit.WebSettings;
import android.webkit.WebView;


import com.example.user.newapp.BaseFrag.VMGBaseFragment;
import com.example.user.newapp.Interfaces.VMGMraidEvents;

/**
 * Created by Seyfullah Semen
 */

public class BlankFragment extends VMGBaseFragment implements VMGMraidEvents {
    // create the variables
    private WebView webView;
    private boolean isViewable;
    private boolean isPageFinished = true;
    private boolean isLaidOut = true;
    private NestedScrollView scroll;


    // this is a default constructor this is required in a fragment
    public BlankFragment() {

        Log.i("info", "CONST");
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
    @SuppressLint("newApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_blank, container, false);
        Log.d("info", "We zijn er "); // this is for debugging reasons
        scroll = (NestedScrollView) v.findViewById(R.id.scroll);

        // get the webView that we created in our XML file
        webView = (WebView) v.findViewById(R.id.webView); // get the id of the webview
        webView.setBackgroundColor(Color.TRANSPARENT); // set the background to transparent
        WebSettings settings = webView.getSettings(); // this is for enabling the javascript
        settings.setJavaScriptEnabled(true); // set javascript enabled

        // set debugging on for debugging on google chrome
        webView.setWebContentsDebuggingEnabled(true); // this is for debugging within google chrome
        // we use our own webViewClient so we have more control over our webView
//     webView.setWebViewClient(new VMGWebViewClient());
        super.openWeb(webView);
        super.addMraid(webView);
        // here we test a couple of things to check whether mraid is working with java or not

        fireReadyEvent(); // fire the ready event
        getScreenSize();
        super.addJavascript(webView, "mraid.isViewable();");


        scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                scrollEventVMG(scrollY, scrollX); // we roepen hier de methode aan die we hebben gemaakt in onze BlankFragment


            }
        });


        super.addJavascript(webView, "mraid.getState();");// get the state of our mraid
        return v; // return the view
    }

    /**
     * the methods below is for checking if java can communicate with Javascript
     * we accomplish this with simple getters to check everything
     */
    @Override
    public void fireReadyEvent() {
        super.addJavascript(webView, "mraid.fireReadyEvent();");
        Log.i("info", "READYYYY  TO GOOO");


    }

    @Override
    public void getScreenSize() {
        super.addJavascript(webView, "mraid.getScreenSize();");
        Log.i("Info about Screen Size ", "Screen size is working");
    }

    @Override
    public void isViewable() {
        super.addJavascript(webView, "mraid.isViewable();");
        Log.i("info about viewable ", " Viewable works");

    }

    @Override
    public void getDefaultPosition() {
        super.addJavascript(webView, "mraid.getDefaultPosition();");
        Log.i("Info default Position ", " Default position is working");
    }

    @Override
    public void getState() {
        super.addJavascript(webView, "mraid.getState();");
        Log.i("info State ", " State is working");
    }

    @Override
    public void removeEventListener() {
        super.addJavascript(webView, "mraid.removeEventListener();");
        Log.i("info Remove", " Removing");
    }

    @Override
    public void fireViewableChangeEvent() {
        Log.i("INFORMATION", "fireViewableChangeEvent");
        super.addJavascript(webView, "mraid.fireViewableChangeEvent(" + isViewable + ");");


    }


    private void scrollEventVMG (float scrollY, float scrollX){
        int[] location = {0, 0};
        int height = 255;
        webView.getLocationOnScreen(location);
        int topper = webView.getTop();
        int all = height + location[1];
        int half = all/2;
        if (all < 0 || all > 1430) {
            isViewable = false;
            super.addJavascript(webView, "mraid.fireViewableChangeEvent(" + isViewable + ");");
            Log.i("Viewer", " " + isViewable);
            super.addJavascript(webView, "mraid.isViewable();");
        } else {
            isViewable = true;
            super. addJavascript(webView, "mraid.fireViewableChangeEvent(" + isViewable + ");");
            Log.i("Viewer", " " + isViewable);
            super. addJavascript(webView, "mraid.isViewable();");
        }
        Log.i("sdhg ", "" + scrollY + " " + scrollX + " " + location[0] + " " + location[1]);

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


