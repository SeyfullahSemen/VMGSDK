/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 * <p>
 * this is a class to make everything more efficient it is our very own fragment that the client
 * can use whenever he makes a new fragment or layout he just needs to add a couple of simple
 * methods
 */
package com.example.user.newapp.BaseUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;


import com.example.user.newapp.ConfigVMG.VMGConfig;
import com.example.user.newapp.ConfigVMG.VMGUrlBuilder;
import com.example.user.newapp.Interfaces.VMGMraidEvents;

import java.util.Map;

/**
 * Created by Seyfullah Semen  on 4-8-2017.
 */

public class VMGBase extends RelativeLayout {


    private static final String TAG = "VMGBaseFragment";


    private boolean isViewable;
    private RelativeLayout resizedView;
    private final static int LOADING = 0;
    private final static int DEFAULT = 1;
    private final static int EXPANDED = 2;
    private final static int RESIZED = 3;
    private final static int HIDDEN = 4;


    private VMGResizeProperties resizeProperties;
    private VMGMraidEvents events;

    private WebView webView;
    private WebView currentWeb;

    private int defaultAddWidth = 340;
    private int defaultAddHeight = 255;
    private DisplayMetrics displayMetrics;
    private Context context;


    private int state;


    // this is an empty constructor
    public VMGBase(Context context) {
        super(context);
        this.context = context;
        resizeProperties = new VMGResizeProperties();
        displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        webView = currentWeb;
    }

    public void setAddWidth(int addWidth) {
        this.defaultAddWidth = addWidth;
        resizeProperties.width = this.defaultAddWidth;
    }

    public void setAddHeight(int addHeight) {
        this.defaultAddHeight = addHeight;
        resizeProperties.height = this.defaultAddHeight;
    }

    public int getAddWidth() {
        resizeProperties.width = this.defaultAddWidth;
        return resizeProperties.width;
    }

    public int getAddHeight() {
        resizeProperties.height = this.defaultAddHeight;
        return resizeProperties.height;
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
    public void VMGScrollEvent(float scrollY, float scrollX, ViewGroup view, WebView custom, Context context) {
        int[] location = {0, 0}; // save the locations x and y of the sroll

        int heightOfContent = custom.getContentHeight(); // get the heigth of the webview

        double layoutH = view.getMeasuredHeight(); // get the height of the layout where the webview is saved in

        double topOffset = (double) VMGConfig.getVMGInstance(context).retrieveSpecific("topOffset");
        double bottomOffset = (double) VMGConfig.getVMGInstance(context).retrieveSpecific("bottomOffset");


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
    public void startVMG(Context context, WebView custom) {

        WebSettings settings = custom.getSettings();


        custom.setWebViewClient(new VMGWebviewClient());
        settings.setJavaScriptEnabled(true); // set javascript enabled
        // set debugging on for debugging on google chrome

        custom.setWebContentsDebuggingEnabled(true); // this is for debugging within google chrome
        VMGConfig.getVMGInstance(context);
        openWeb(custom);


    }// end of startVMG();

    /**
     *
     * @param custom
     */
    private void resize(WebView custom) {
        Log.d(TAG, "resize");

        // We need the cooperation of the app in order to do a resize.
        if (events == null) {
            return;
        }
        boolean isReadyToResize = events.resizeAd(this,
                resizeProperties.width, resizeProperties.height, resizeProperties.offsetX, resizeProperties.offsetY);
        if (!isReadyToResize) {
            return;
        }

        state = RESIZED;

        if (resizedView == null) {
            resizedView = new RelativeLayout(context);
            removeAllViews();
            resizedView.addView(custom);

            FrameLayout rootView = (FrameLayout) getRootView().findViewById(android.R.id.content);
            rootView.addView(resizedView);
        }

        setResizedViewSize();


        fireStateChangeEvent(custom);


    }// end of resize();

    /**
     *
     */
    private void setResizedViewSize() {
        Log.d(TAG, "setResizedViewSize");
        int width = resizeProperties.width;
        int height = resizeProperties.height;
        Log.d(TAG, "setResizedViewSize " + width + "x" + height);
        int widthToDip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, displayMetrics);
        int heightToDip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, displayMetrics);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(widthToDip, heightToDip);
        resizedView.setLayoutParams(params);
    }// end of setResizedViewSize();

    /**
     *
     * @param custom
     */
    private void setMaxSize(WebView custom) {

        useJavascript(custom, "mraid.setMaxSize('" + getAddWidth() + "','" + getAddHeight() + "');");
    }// end of setMaxSize();

    /**
     *
     * @return
     */
    private int getState() {
        return state;
    }

    /**
     *
     * @param custom
     */
    private void fireViewableChangeEvent(WebView custom) {
        isViewable = true;
        useJavascript(custom, "mraid.fireViewableChangeEvent('" + isViewable + "');");
    }// end of fireViewableChangeEvent();

    /**
     *
     * @param custom
     */
    private void fireReadyChangeEvent(WebView custom) {
        useJavascript(custom, "mraid.fireReadyEvent();");
    }// end of fireReadyChangeEvent();

    /**
     *
     * @param custom
     */
    private void fireStateChangeEvent(WebView custom) {
        String[] states = {"loading", "default", "expanded", "resized", "hidden"};
        useJavascript(custom, "mraid.fireStateChangeEvent('" + states[state] + "');");
    }// end of fireStateChangeEvent();

    /**
     *
     */
    private class VMGWebviewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (state == LOADING) {
                state = DEFAULT;
                fireStateChangeEvent(view);
                setMaxSize(view);
                fireReadyChangeEvent(view);

                fireViewableChangeEvent(view);

            }

        }
    }// end of VMGWebViewClient();

}


