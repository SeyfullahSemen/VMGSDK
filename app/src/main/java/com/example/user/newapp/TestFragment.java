package com.example.user.newapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.user.newapp.BaseFrag.VMGBaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends VMGBaseFragment {
    private WebView webView2;

    public TestFragment (){

    }



     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_test, container, false);
         webView2 = (WebView) v.findViewById(R.id.webView2);


         super.addMraid(webView2);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
