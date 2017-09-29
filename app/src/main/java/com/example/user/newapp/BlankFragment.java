
package com.example.user.newapp;
/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.VMG.SDKDemo.R;
import com.vmg.BaseUtils.VMGBase;
import com.yalantis.phoenix.PullToRefreshView;


/**
 * Created by Seyfullah Semen
 */
public class BlankFragment extends Fragment {
    private WebView webView;
    private VMGBase frag;
    private NestedScrollView scroll;
    private RelativeLayout rela;
    private PullToRefreshView refreshScrollview;

    public BlankFragment() {
    }

    /**
     * this is the method where the webview is filled with our index file
     * and tests are done to see whether the functions work correctly or not
     *
     * @param inflater this will infate the layout
     * @param container the container will get the view
     * @param savedInstanceState check if there is savedinstances
     * @return return the inflated xml file
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_blank, container, false);
        scroll = v.findViewById(R.id.scroll);
        rela = v.findViewById(R.id.rela);
        webView = v.findViewById(R.id.webView);
        refreshScrollview = v.findViewById(R.id.refresScrollview);
        refreshScrollview.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshScrollview.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        refreshScrollview.setRefreshing(false);
                        frag = new VMGBase(getActivity(), webView);

                        frag.startVMG(6340 ); // this will start everything that you need to load inside the view
                    }
                }, 2000);

            }
        });
        frag = new VMGBase(getActivity(), webView);

        frag.startVMG(6340 ); // this will start everything that you need to load inside the view
        scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                frag.VMGScrollEvent(scrollY, scrollX, rela);
            }
        });


        return v; // return the view
    }
}
