package com.example.user.newapp;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.user.newapp.BaseFrag.VMGBaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends VMGBaseFragment {
    private WebView webView2;
    private String baseUrl = "http://vmg.host/";
    private String HTML = "index.html";


    public TestFragment (){
    super();
    }



     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_test, container, false);
         webView2 = (WebView) v.findViewById(R.id.webView2);

         webView2.setBackgroundColor(Color.TRANSPARENT); // set the background to transparent
         WebSettings settings = webView2.getSettings(); // this is for enabling the javascript
         settings.setJavaScriptEnabled(true); // set javascript enabled

         // set debugging on for debugging on google chrome
         webView2.setWebContentsDebuggingEnabled(true); // this is for debugging within google chrome

        super.openWeb(webView2);
         super.addMraid(webView2);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
