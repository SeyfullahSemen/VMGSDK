package com.example.user.newapp.Interfaces;

import android.webkit.WebView;

/**
 * Created by Seyfullah Semen on 26-7-2017.
 */

public interface JavascriptInterface {

     void addMraid (WebView webView);
     void addJavaScript(String javaScript);
     void addJavaScript(WebView webView, String javaScript);
}
