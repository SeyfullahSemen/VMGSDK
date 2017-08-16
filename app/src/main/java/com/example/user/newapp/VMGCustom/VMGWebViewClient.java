package com.example.user.newapp.VMGCustom;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * Created by User on 16-8-2017.
 */

public class VMGWebViewClient extends WebViewClient {

    @SuppressLint("newApi")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {


        return super.shouldOverrideUrlLoading(view, request);

    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }
}
