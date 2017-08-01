package com.example.user.newapp;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.io.InputStream;


public class BlankFragment extends Fragment implements com.example.user.newapp.Interfaces.JavascriptInterface {
    // create the variables
    private WebView webView;
    private Context context;
    private String mraidjs;
    private String baseUrl;
    private String HTMLName = "index.html";
    private boolean isViewable;
    private boolean isPageFinished = true;
    private boolean isLaidOut = true;


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

        // get the webView that we created in our XML file
        webView = (WebView) v.findViewById(R.id.webView); // get the id of the webview
        webView.setBackgroundColor(Color.TRANSPARENT); // set the background to transparent
        WebSettings settings = webView.getSettings(); // this is for enabling the javascript
        settings.setJavaScriptEnabled(true); // set javascript enabled

        // set debugging on for debugging on google chrome
        webView.setWebContentsDebuggingEnabled(true); // this is for debugging within google chrome
        // we use our own webViewClient so we have more control over our webView
//        webView.setWebViewClient(new VMGWebViewClient());
        // here we add our mraid file
        addMraid(webView);
        // here we test a couple of things to check whether mraid is working with java or not

        fireReadyEvent(); // fire the ready event
        getScreenSize();
        addJavaScript("mraid.isViewable();");


//        removeEventListener();
        addJavaScript("mraid.getState();");// get the state of our mraid
        return v; // return the view
    }

    /**
     * @param webView
     */
    @SuppressLint("newApi")
    @Override
    public void addMraid(WebView webView) { // we expect a webview to load our mraid in
        if (TextUtils.isEmpty(mraidjs)) { // we made a string to save our mraid file in
            byte[] mraidArray = Base64.decode(EncodedBase.mraidFile, Base64.DEFAULT); // decode the file that we made in our Asset Class
            mraidjs = new String(mraidArray); // save what we decoded in the String mraidJs
        }
        System.out.println("adding mraid is oke and ready to goo " + mraidjs.length()); // check the length of our mraid

        webView.loadData("<html></html>", "text/html", "UTF-8");
        webView.evaluateJavascript(mraidjs, new ValueCallback<String>() { // make a callback to see if the mraid file is correctly loaded
            @Override
            public void onReceiveValue(String s) {
                Log.i("Info", "Mraiding " + mraidjs);
            }

        });
        openWeb("http://vmg.host/", HTMLName); // make a call to the method that we made to open our HTML file with mraid injected

//       webView.loadUrl("javascript:" + mraidjs);
//        Log.i("IMPORTANTE ",mraidjs);
    }

    @Override
    public void addJavaScript(String javaScript) {
        addJavaScript(webView, javaScript);
    }

    @SuppressLint("newApi")
    @Override
    public void addJavaScript(WebView webView, String javaScript) {
        if (!javaScript.isEmpty()) {

            webView.evaluateJavascript(javaScript, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    Log.i("info:", "Evalution done " + s);
                }
            });
        } else {
            System.out.println("Loading" + javaScript);
            webView.loadUrl("javascript:" + javaScript);

        }


    }

    /**
     * we just need to add the baseUrl and the HTML file name
     *
     * @param baseUrl
     * @param HTMLName
     */
    private void openWeb(String baseUrl, String HTMLName) { // this is the method that will make sure that we can open a HTML file with

        AssetManager assetManager = getActivity().getAssets(); // this wil get the HTML file that we mad e
        // byte buffer into a string
        String text = "";
        try {
            InputStream input = assetManager.open(HTMLName); // this is will get our file named index
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            text = new String(buffer);

        } catch (IOException e) { // catch any exceptions
            e.printStackTrace();
        }
        Log.i("info", "HTML file " + text); // this is for debugging reasons

        webView.loadDataWithBaseURL(baseUrl, text, "text/html", "UTF-8", ""); // this will load the HTML file with the baseURL


    }

    /**
     * the methods below is for checking if java can communicate with Javascript
     * we accomplish this with simple getters to check everything
     */
    private void fireReadyEvent() {
        addJavaScript("mraid.fireReadyEvent();");
        Log.i("info", "READYYYY NIFFFOOOO");


    }

    private void getScreenSize() {
        addJavaScript("mraid.getScreenSize();");
        Log.i("Info about Screen Size ", "Screen size is working");
    }

    private void isViewable() {
        addJavaScript("mraid.isViewable();");
        Log.i("info about viewable ", " Viewable works");

    }

    private void getDefaultPosition() {
        addJavaScript("mraid.getDefaultPosition();");
        Log.i("Info default Position ", " Default position is working");
    }

    private void getState() {
        addJavaScript("mraid.getState();");
        Log.i("info State ", " State is working");
    }

    private void removeEventListener() {
        addJavaScript("mraid.removeEventListener();");
        Log.i("info Remove", " Removing");
    }


    private void fireViewableChangeEvent() {
        Log.i("INFORMATION", "fireViewableChangeEvent");
        addJavaScript("mraid.fireViewableChangeEvent(" + isViewable + ");");


    }

    public void setViewable(int visibility) {
        boolean isCurrentlyViewable = (visibility == View.VISIBLE);
        if (isCurrentlyViewable != isViewable) {
            isViewable = isCurrentlyViewable;
            if (isPageFinished && isLaidOut) {
                fireViewableChangeEvent();
                Log.i("INFOTJE","yes it is visible");
            }
        }
        }

        public void Scrollding(float scrollY, float scrollX){
            int [] location =  {0,0};
            int height = 255;
            webView.getLocationOnScreen(location);
            int topper = webView.getTop();
            int all = height + location[1];
            if (all < 0){
                isViewable = false;
                addJavaScript("mraid.fireViewableChangeEvent("+isViewable+");");
                Log.i("Viewer"," "+isViewable);
                addJavaScript("mraid.isViewable();");
            }else {
                isViewable = true;
                addJavaScript("mraid.fireViewableChangeEvent("+isViewable+");");
                Log.i("Viewert"," "+isViewable);
                addJavaScript("mraid.isViewable();");

            }
            Log.i("sdhg ",""+scrollY+" "+scrollX+" "+location[0]+" "+location[1] );

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


