/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 * <p>
 * this is a class to make everything more efficient it is our very own fragment that the client
 * can use whenever he makes a new fragment or layout he just needs to add a couple of simple
 * methods
 */
package com.example.user.newapp.BaseFrag;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.example.user.newapp.ConfigVMG.VMGConfig;
import com.example.user.newapp.ConfigVMG.VMGUrlBuilder;

/**
 * Created by Seyfullah Semen  on 4-8-2017.
 */

public abstract class VMGBaseFragment extends Fragment {


    private static final String TAG = "VMGBaseFragment";


    private boolean isViewable;
    private final static int LOADING = 0;
    private final static int DEFAULT = 1;
    private final static int EXPANDED = 2;
    private final static int RESIZED = 3;
    private final static int HIDDEN = 4;


    ;

    private int state;


    // this is an empty constructor
    public VMGBaseFragment() {


    }


    /**
     * @param custom
     * @param javascript
     */
    private void useJavascript(WebView custom, String javascript) {
        if (!javascript.isEmpty()) {
            custom.evaluateJavascript(javascript, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    Log.i("Info: ", "Evaluation done " + s);
                }
            });
        } else {
            System.out.println("Loading " + javascript);
            custom.loadUrl("javascript:" + javascript);
        }
    }// end of useJavaScript();


    /**
     * @param custom
     */
    private void openWeb(WebView custom) {

        custom.loadUrl(VMGUrlBuilder.getPlacementUrl());


    }// end of openWeb();

    /**
     * this method can be used when the user has a nestedscrollview or a scrollview
     *
     * @param scrollY
     * @param scrollX
     * @param view
     * @param custom
     */
    public void VMGScrollEvent(float scrollY, float scrollX, ViewGroup view, WebView custom) {
        int[] location = {0, 0}; // save the locations x and y of the sroll

        int heightOfContent = custom.getContentHeight(); // get the heigth of the webview

        double layoutH = view.getMeasuredHeight(); // get the height of the layout where the webview is saved in

        double topOffset = (double) VMGConfig.getVMGInstance(getActivity()).retrieveSpecific("topOffset");
        double bottomOffset = (double) VMGConfig.getVMGInstance(getActivity()).retrieveSpecific("bottomOffset");


        if (scrollY - custom.getY() > (heightOfContent * topOffset)) {
            isViewable = false;
            useJavascript(custom, "mraid.fireViewableChangeEvent(" + isViewable + ");");
            Log.i("Viewer", " " + isViewable + "  ");
            useJavascript(custom, "mraid.isViewable();");
        } else if (scrollY + layoutH < custom.getY() + (heightOfContent * bottomOffset)) {
            isViewable = false;
            useJavascript(custom, "mraid.fireViewableChangeEvent(" + isViewable + ");");
            Log.i("Viewer", " " + isViewable);
            useJavascript(custom, "mraid.isViewable();");
        } else {

            isViewable = true;
            useJavascript(custom, "mraid.fireViewableChangeEvent(" + isViewable + ");");
            Log.i("Viewer", " " + isViewable);
            useJavascript(custom, "mraid.isViewable();");

        }


        Log.i("sdhg ", "" + scrollY + " " + scrollX + " " + location[0] + " " + location[1]);
    }// end of VMGScrollEvent();


    /**
     * this is where the webview gets filled with the mraid file and it opens the webview
     * so the user just needs to add this method to get the full functionallity
     *
     * @param custom
     */
    public void startVMG(WebView custom) {
        state = LOADING;
        WebSettings settings = custom.getSettings();



            custom.setWebViewClient(new VMGWebviewClient());
            settings.setJavaScriptEnabled(true); // set javascript enabled
            // set debugging on for debugging on google chrome

            custom.setWebContentsDebuggingEnabled(true); // this is for debugging within google chrome
            VMGConfig.getVMGInstance(getActivity());
            openWeb(custom);


    }// end of startVMG();



    private void setMaxSize(WebView custom) {



        useJavascript(custom, "mraid.setMaxSize('" + 500 + "'),('" + 600+ "');");
    }


    private int getState() {
        return state;
    }


    private void removeEventListener() {

    }

    private void fireViewableChangeEvent(WebView custom) {
        isViewable = true;
        useJavascript(custom, "mraid.fireViewableChangeEvent('" + isViewable + "');");
    }

    private void fireReadyChangeEvent(WebView custom) {
        useJavascript(custom, "mraid.fireReadyEvent();");
    }

    private void fireStateChangeEvent(WebView custom) {
        String[] states = {"loading", "default", "expanded", "resized", "hidden"};
        useJavascript(custom, "mraid.fireStateChangeEvent('" + states[state] + "');");
    }

    private void setState() {

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private class VMGWebviewClient extends WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (state == LOADING){
                state = DEFAULT;
                fireStateChangeEvent(view);
                setMaxSize(view);
                fireReadyChangeEvent(view);

                fireViewableChangeEvent(view);

            }

        }
    }

}


