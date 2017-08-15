/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 * <p>
 * this is a class to make everything more efficient it is our very own fragment that the client
 * can use whenever he makes a new fragment or layout he just needs to add a couple of simple
 * methods
 */
package com.example.user.newapp.BaseFrag;

import android.content.res.AssetManager;
import android.graphics.Color;
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


import com.example.user.newapp.ConfigVMG.VMGBuilder;
import com.example.user.newapp.ConfigVMG.VMGConfig;
import com.example.user.newapp.Interfaces.VMGMraidEvents;
import com.example.user.newapp.MainActivity;
import com.example.user.newapp.R;
import com.example.user.newapp.encodedFiles.EncodedBase;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Seyfullah Semen  on 4-8-2017.
 */

public abstract class VMGBaseFragment extends Fragment  {
    /**
     * here we define a couple of our needed information
     * we need to have an html file with the name  of the file
     * we need a baseUrl and we make it private
     */
    public final String HTML = "index.html";
    public final String baseUrl = "http://vmg.host/";
    private boolean isViewable;
    private String mraidJs;


    // this is an empty constructor
    public VMGBaseFragment() {


    }


    /**
     * with this method we can send calls to out mraid file via java
     *
     * @param webView
     * @param javascript
     */
    public void addJavascript(WebView webView, String javascript) {
        if (!javascript.isEmpty()) {
            webView.evaluateJavascript(javascript, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    Log.i("Info: ", "Evaluation done " + s);
                }
            });
        } else {
            System.out.println("Loading " + javascript);
            webView.loadUrl("javascript:" + javascript);
        }
    }

    /**
     * here within this method we add our mraid file to our app
     *
     * @param webview
     */
    private void addMraid(WebView webview) {
        if (TextUtils.isEmpty(mraidJs)) {
            byte[] mraidArray = Base64.decode(EncodedBase.mraidFile, Base64.DEFAULT);
            mraidJs = new String(mraidArray);

        }
        System.out.println("adding mraid is oke and ready to go " + mraidJs.length());
        webview.loadData("<html></html>", "text/html", "UTF-8");
        webview.evaluateJavascript(mraidJs, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                Log.i("Info", "Mraiding" + mraidJs);
            }
        });
        openWeb(webview);

    }

    /**
     * this method opens up our webview filled with the index file and the baseUrl
     *
     * @param webView
     */
    private void openWeb(WebView webView) {
        AssetManager assetManager = getActivity().getAssets();
        String text = "";
        try {
            InputStream input = assetManager.open(this.HTML);
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            text = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        webView.loadDataWithBaseURL(this.baseUrl, text, "text/html", "UTF-8", "");
    }

    /**
     * this method can be used when the user has a nestedscrollview or a scrollview
     *
     * @param scrollY
     * @param scrollX
     * @param view
     * @param webView
     */
    public void VMGScrollEvent(float scrollY, float scrollX, ViewGroup view, WebView webView) {
        int[] location = {0, 0}; // save the locations x and y of the sroll

        int heightOfContent = webView.getContentHeight(); // get the heigth of the webview


        double layoutH = view.getMeasuredHeight(); // get the height of the layout where the webview is saved in
        int width = webView.getWidth(); // get the width of the webview
        int heightWeb = webView.getHeight(); // get the height of the webview

        Log.i("content Height", "" + heightOfContent);
        Log.i("widthWeb ", "" + width);
        Log.i("heightWeb ", "" + heightWeb);
//        int all = heightOfContent + location[1];
        if (scrollY - webView.getY() > (heightOfContent * (double) VMGConfig.geVMGInstance().retrieveSpecific("Percentage_up"))) {
            isViewable = false;
            addJavascript(webView, "mraid.fireViewableChangeEvent(" + isViewable + ");");
            Log.i("Viewer", " " + isViewable);
            addJavascript(webView, "mraid.isViewable();");
        } else if (scrollY + layoutH < webView.getY() + (heightOfContent * (double) VMGConfig.geVMGInstance().retrieveSpecific("Percentage_under"))) {
            isViewable = false;
            addJavascript(webView, "mraid.fireViewableChangeEvent(" + isViewable + ");");
            Log.i("Viewer", " " + isViewable);
            addJavascript(webView, "mraid.isViewable();");
        } else {

            isViewable = true;
            addJavascript(webView, "mraid.fireViewableChangeEvent(" + isViewable + ");");
            Log.i("Viewer", " " + isViewable);
            addJavascript(webView, "mraid.isViewable();");

        }


        Log.i("sdhg ", "" + scrollY + " " + scrollX + " " + location[0] + " " + location[1]);
    }// end of VMGScrollEvent();


    /**
     * this is where the webview gets filled with the mraid file and it opens the webview
     * so the user just needs to add this method to get the full functionallity
     * @param webView
     */
    public void startVMG(WebView webView) {

        webView.setBackgroundColor(Color.TRANSPARENT); // set the background to transparent
        WebSettings settings = webView.getSettings(); // this is for enabling the javascript

        settings.setJavaScriptEnabled(true); // set javascript enabled
        // set debugging on for debugging on google chrome

        webView.setWebContentsDebuggingEnabled(true); // this is for debugging within google chrome
        webView.animate();

        openWeb(webView);
        addMraid(webView);


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


