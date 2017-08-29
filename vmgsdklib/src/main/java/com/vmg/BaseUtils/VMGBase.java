/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 * <p>
 * this is a class to make everything more efficient it is our very own fragment that the client
 * can use whenever he makes a new fragment or layout he just needs to add a couple of simple
 * methods
 */
package com.vmg.BaseUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.vmg.ConfigVMG.VMGConfig;
import com.vmg.ConfigVMG.VMGUrlBuilder;

import static android.R.attr.screenSize;


/**
 * Created by Seyfullah Semen  on 4-8-2017.
 */

@SuppressLint("ViewConstructor")
public class VMGBase extends RelativeLayout {


    private static final String TAG = "VMGBaseFragment";


    private boolean isViewable;

    private final static int LOADING = 0;
    private final static int DEFAULT = 1;
    private final static int EXPANDED = 2;
    private final static int RESIZED = 3;
    private final static int HIDDEN = 4;
    private final int orientationLocking;


    private VMGResizeProperties resizeProperties;


    private Handler handler;

    private WebView webView;

    private int defaultAddWidth = 340;
    private int defaultAddHeight = 255;

    private Context context;


    private int state;


    // this is an empty constructor
    public VMGBase(Context context, WebView webView) {
        super(context);
        this.context = context;
        this.webView = webView;
        resizeProperties = new VMGResizeProperties();
        getAddWidth();
        getAddHeight();
        if (context instanceof Activity) {
            orientationLocking = ((Activity) context).getRequestedOrientation();
        } else {
            orientationLocking = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
        }

        handler = new Handler(Looper.getMainLooper());

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

    private void useJavascript(String javascript) {
        useJavascript(webView, javascript);
    }

    /**
     * @param custom
     * @param javascript
     */
    @SuppressLint("NewApi")
    private void useJavascript(WebView custom, String javascript) {
        if (!javascript.isEmpty()) {
            custom.evaluateJavascript(javascript, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    Log.i("Mraiding event : ", "Done " + s);
                }
            });
        } else {
            Log.d(TAG, " " + javascript);
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
    public void VMGScrollEvent(float scrollY, float scrollX, ViewGroup view) {
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
     */
    @SuppressLint("NewApi")
    public void startVMG() {

        WebSettings settings = webView.getSettings();


        settings.setJavaScriptEnabled(true); // set javascript enabled
        // set debugging on for debugging on google chrome

        webView.setWebContentsDebuggingEnabled(true); // this is for debugging within google chrome

        VMGConfig.getVMGInstance(context);
        openWeb();
        webView.setWebViewClient(new VMGWebviewClient());

    }// end of startVMG();


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
        useJavascript("mraid.setState('" + states[state] + "');");
        Log.d(TAG, "" + getState());
    }// end of fireStateChangeEvent();

    /**
     *
     */
    private class VMGWebviewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {

            if (state == LOADING) {
                state = DEFAULT;
                fireStateChangeEvent();
                setMaxSize();
                fireReadyChangeEvent();

                fireViewableChangeEvent();


            }
            super.onPageFinished(view, url);
        }


    }// end of VMGWebViewClient();


}


