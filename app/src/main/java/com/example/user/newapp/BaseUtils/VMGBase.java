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
    private Handler handler;

    private WebView webView;
    private WebView currentWeb;

    private int defaultAddWidth = 340;
    private int defaultAddHeight = 255;
    private DisplayMetrics displayMetrics;
    private Context context;


    private int state;


    // this is an empty constructor
    public VMGBase(Context context,WebView webView) {
        super(context);
        this.context = context;
        resizeProperties = new VMGResizeProperties();
        displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        handler = new Handler(Looper.getMainLooper());
        this.webView = webView;
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

private void useJavascript(String javascript){
    useJavascript(webView,javascript);
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
     *
     */
    private void openWeb() {

        webView.loadUrl(VMGUrlBuilder.getPlacementUrl());


    }// end of openWeb();

    /**
     * this method can be used when the user has a nestedscrollview or a scrollview
     *
     * @param scrollY
     * @param scrollX
     * @param view
     */
    public void VMGScrollEvent(float scrollY, float scrollX, ViewGroup view,  Context context) {
        int[] location = {0, 0}; // save the locations x and y of the sroll

        int heightOfContent = webView.getContentHeight(); // get the heigth of the webview

        double layoutH = view.getMeasuredHeight(); // get the height of the layout where the webview is saved in

        double topOffset = (double) VMGConfig.getVMGInstance(context).retrieveSpecific("topOffset");
        double bottomOffset = (double) VMGConfig.getVMGInstance(context).retrieveSpecific("bottomOffset");


        if (scrollY - webView.getY() > (heightOfContent * topOffset)) {
            isViewable = false;
            useJavascript("mraid.fireViewableChangeEvent(" + isViewable + ");");
            Log.i("Viewer", " " + isViewable + "  ");
            useJavascript("mraid.isViewable();");
        } else if (scrollY + layoutH < webView.getY() + (heightOfContent * bottomOffset)) {
            isViewable = false;
            useJavascript("mraid.fireViewableChangeEvent(" + isViewable + ");");
            Log.i("Viewer", " " + isViewable);
            useJavascript("mraid.isViewable();");
        } else {

            isViewable = true;
            useJavascript("mraid.fireViewableChangeEvent(" + isViewable + ");");
            Log.i("Viewer", " " + isViewable);
            useJavascript("mraid.isViewable();");

        }


        Log.i("sdhg ", "" + scrollY + " " + scrollX + " " + location[0] + " " + location[1]);
    }// end of VMGScrollEvent();


    /**
     * this is where the webview gets filled with the mraid file and it opens the webview
     * so the user just needs to add this method to get the full functionallity
     *
     * @param context
     */
    public void startVMG(Context context) {

        WebSettings settings = webView.getSettings();


        webView.setWebViewClient(new VMGWebviewClient());
        settings.setJavaScriptEnabled(true); // set javascript enabled
        // set debugging on for debugging on google chrome

        webView.setWebContentsDebuggingEnabled(true); // this is for debugging within google chrome
        VMGConfig.getVMGInstance(context);
        openWeb();


    }// end of startVMG();

    /**
     *
     *
     */
    private void resize() {
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
            resizedView.addView(webView);

            FrameLayout rootView = (FrameLayout) getRootView().findViewById(android.R.id.content);
            rootView.addView(resizedView);
        }

        setResizedViewSize();


        fireStateChangeEvent();


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
     *
     */
    private void setMaxSize() {

        handler.post(new Runnable() {
            @Override
            public void run() {
                useJavascript("mraid.setMaxSize('" + getAddWidth() + "','" + getAddHeight() + "');");
            }
        });
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
     *
     */
    private void fireViewableChangeEvent() {
        isViewable = true;
        useJavascript("mraid.fireViewableChangeEvent('" + isViewable + "');");
    }// end of fireViewableChangeEvent();

    /**
     *
     *
     */
    private void fireReadyChangeEvent() {
        useJavascript("mraid.fireReadyEvent();");
    }// end of fireReadyChangeEvent();

    /**
     *
     *
     */
    private void fireStateChangeEvent() {
        String[] states = {"loading", "default", "expanded", "resized", "hidden"};
        useJavascript("mraid.fireStateChangeEvent('" + states[state] + "');");
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
                fireStateChangeEvent();
                setMaxSize();
                fireReadyChangeEvent();

                fireViewableChangeEvent();

            }

        }
    }// end of VMGWebViewClient();

}


