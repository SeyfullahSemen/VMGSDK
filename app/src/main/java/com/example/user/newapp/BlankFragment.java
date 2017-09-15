
package com.example.user.newapp;
/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 */


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.vmg.BaseUtils.VMGBase;


/**
 * Created by Seyfullah Semen
 */
public class BlankFragment extends Fragment {
    private WebView webView;
    private boolean isViewable;
    private VMGBase frag;
    private NestedScrollView scroll;
    private RelativeLayout rela;

    public BlankFragment() {
    }

    /**
     * this is the method where the webview is filled with our index file
     * and tests are done to see whether the functions work correctly or not
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_blank, container, false);
        scroll = (NestedScrollView) v.findViewById(R.id.scroll);
        rela = (RelativeLayout) v.findViewById(R.id.rela);
        webView = (WebView) v.findViewById(R.id.webView);

        frag = new VMGBase(getActivity(), webView);

        frag.startVMG(6194); // this will start everything that you need to load inside the view
        scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                frag.VMGScrollEvent(scrollY, scrollX, rela);
            }
        });


        return v; // return the view
    }
}
