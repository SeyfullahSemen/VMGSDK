package com.example.user.newapp;

/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 */

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.newapp.BaseFrag.VMGBaseFragment;
import com.example.user.newapp.ConfigVMG.VMGConfig;
import com.example.user.newapp.Interfaces.VMGMraidEvents;
import com.example.user.newapp.VMGCustom.VMGCustomView;


/**
 * A simple {@link Fragment} subclass.
 * Created by Seyfullah Semen
 */
public class AboutVMGFragment extends VMGBaseFragment implements VMGMraidEvents {
    private static final String TAG = "About fragment";
    private ImageView img_logo_about;
    private TextView text_about;
    private VMGCustomView view;
    private boolean isViewable;
    private NestedScrollView about__scroll;
    private LinearLayout layout_about;

    public AboutVMGFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_about_vmg, container, false);
        img_logo_about = (ImageView) v.findViewById(R.id.img_logo_about);
        text_about = (TextView) v.findViewById(R.id.text_about);
        view = (VMGCustomView) v.findViewById(R.id.web_about);
        about__scroll = (NestedScrollView) v.findViewById(R.id.scroll__about);
        layout_about = (LinearLayout) v.findViewById(R.id.layout_about);


        super.startVMG(view);

        about__scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                AboutVMGFragment.super.VMGScrollEvent(scrollY, scrollX, layout_about, view);

            }
        });


        fireReadyEvent();

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void fireReadyEvent() {
        Log.i(TAG, " fired the ready event");
        super.addJavascript(view, "mraid.fireReadyEvent()");

    }

    @Override
    public void getScreenSize() {

    }

    @Override
    public void isViewable() {
        Log.i(TAG, " VIEWABLE");
        super.addJavascript(view, "mraid.isViewable()");

    }

    @Override
    public void getDefaultPosition() {

    }

    @Override
    public void getState() {

    }

    @Override
    public void removeEventListener() {

    }

    @Override
    public void fireViewableChangeEvent() {
        Log.i("INFORMATION", "fireViewableChangeEvent");
        super.addJavascript(view, "mraid.fireViewableChangeEvent(" + isViewable + ");");

    }


}
