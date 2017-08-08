/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 *
 * this is a class to make everything more efficient it is our very own fragment that the client
 * can use whenever he makes a new fragment or layout he just needs to add a couple of simple
 * methods
 */
package com.example.user.newapp.BaseFrag;

import android.content.res.AssetManager;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;


import com.example.user.newapp.encodedFiles.EncodedBase;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Seyfullah Semen  on 4-8-2017.
 */

public abstract class VMGBaseFragment extends Fragment {
    /**
     * here we define a couple of our needed information
     * we need to have an html file with the name  of the file
     * we need a baseUrl and we make it private
     */
    public final String HTML = "index.html";
    public final String baseUrl = "http://vmg.host/";
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
    public void addMraid(WebView webview) {
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
    public void openWeb(WebView webView) {
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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}


