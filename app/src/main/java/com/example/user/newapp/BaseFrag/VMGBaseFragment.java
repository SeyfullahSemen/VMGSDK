/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 * <p>
 * this is a class to make everything more efficient it is our very own fragment that the client
 * can use whenever he makes a new fragment or layout he just needs to add a couple of simple
 * methods
 */
package com.example.user.newapp.BaseFrag;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.user.newapp.ConfigVMG.VMGBuilder;
import com.example.user.newapp.ConfigVMG.VMGConfig;
import com.example.user.newapp.ConfigVMG.VMGUrlBuilder;
import com.example.user.newapp.Interfaces.VMGMraidEvents;
import com.example.user.newapp.MainActivity;
import com.example.user.newapp.R;
import com.example.user.newapp.VMGCustom.VMGCustomView;
import com.example.user.newapp.encodedFiles.EncodedBase;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Seyfullah Semen  on 4-8-2017.
 */

public abstract class VMGBaseFragment extends Fragment {


    private static final String TAG = "VMGBaseFragment";


    private boolean isViewable;


    // this is an empty constructor
    public VMGBaseFragment() {


    }


    /**
     * @param custom
     * @param javascript
     */
    public void useJavascript(VMGCustomView custom, String javascript) {
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
    private void openWeb(VMGCustomView custom) {
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
    public void VMGScrollEvent(float scrollY, float scrollX, ViewGroup view, VMGCustomView custom) {
        int[] location = {0, 0}; // save the locations x and y of the sroll

        int heightOfContent = custom.getContentHeight(); // get the heigth of the webview


        double layoutH = view.getMeasuredHeight(); // get the height of the layout where the webview is saved in
        int width = custom.getWidth(); // get the width of the webview
        int heightWeb = custom.getHeight(); // get the height of the webview


        Log.i("content Height", "" + heightOfContent);
        Log.i("widthWeb ", "" + width);
        Log.i("heightWeb ", "" + heightWeb);

        if (scrollY - custom.getY() > (heightOfContent * (double) VMGConfig.getVMGInstance(getActivity()).retrieveSpecific("topOffset"))) {
            isViewable = false;
            useJavascript(custom, "mraid.fireViewableChangeEvent(" + isViewable + ");");
            Log.i("Viewer", " " + isViewable + "  ");
            useJavascript(custom, "mraid.isViewable();");
        } else if (scrollY + layoutH < custom.getY() + (heightOfContent * (double) VMGConfig.getVMGInstance(getActivity()).retrieveSpecific("bottomOffset"))) {
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
    public void startVMG(VMGCustomView custom) {

        WebSettings settings = custom.getSettings();


        settings.setJavaScriptEnabled(true); // set javascript enabled
        // set debugging on for debugging on google chrome

        custom.setWebContentsDebuggingEnabled(true); // this is for debugging within google chrome

        openWeb(custom);


    }// end of startVMG();


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}


