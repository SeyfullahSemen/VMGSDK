package com.example.user.newapp;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.example.user.newapp.BaseFrag.VMGBaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends VMGBaseFragment {
    private WebView webView2;
    private NestedScrollView nestedTest;
    private RelativeLayout rela_test;





    public TestFragment (){
    super();
    }



     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_test, container, false);
         webView2 = (WebView) v.findViewById(R.id.webView2);
         nestedTest = (NestedScrollView) v.findViewById(R.id.nestedTest);
         rela_test = (RelativeLayout) v.findViewById(R.id.rela_test);
         super.startVMG(webView2);

         nestedTest.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
             @Override
             public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                 TestFragment.super.VMGScrollEvent(scrollY,scrollX,rela_test,webView2);
             }
         });





        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
