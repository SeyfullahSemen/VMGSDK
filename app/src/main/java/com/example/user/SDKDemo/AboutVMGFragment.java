package com.example.user.SDKDemo;

/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 */


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vmg.BaseUtils.VMGBase;


public class AboutVMGFragment extends Fragment {
    private ImageView img_logo_about;
    private TextView text_about;
    private WebView view;
    private NestedScrollView about__scroll;
    private LinearLayout layout_about;
    private VMGBase frag;

    public AboutVMGFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_about_vmg, container, false);
        img_logo_about = v.findViewById(R.id.img_logo_about);
        text_about = v.findViewById(R.id.text_about);
        view = v.findViewById(R.id.web_about);
        about__scroll = v.findViewById(R.id.scroll__about);
        layout_about = v.findViewById(R.id.layout_about);

        frag = new VMGBase(getActivity(), view);

        about__scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                frag.VMGScrollEvent(scrollY, scrollX, layout_about);
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        frag.startVMG(6194);
    }

}
