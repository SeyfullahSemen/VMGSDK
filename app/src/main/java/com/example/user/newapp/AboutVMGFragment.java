package com.example.user.newapp;

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

import com.example.user.newapp.BaseFrag.VMGBaseFragment;
import com.example.user.newapp.ConfigVMG.VMGConfig;


/**
 * A simple {} subclass.
 * Created by Seyfullah Semen
 */
public class AboutVMGFragment extends Fragment {// You need to extend from the VMGBaseFragment
    private static final String TAG = "About fragment";
    private ImageView img_logo_about;
    private TextView text_about;
    private WebView view;
    private boolean isViewable;
    private NestedScrollView about__scroll;
    private LinearLayout layout_about;
    VMGBaseFragment frag ;

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
        view = (WebView) v.findViewById(R.id.web_about);
        about__scroll = (NestedScrollView) v.findViewById(R.id.scroll__about);
        layout_about = (LinearLayout) v.findViewById(R.id.layout_about);
        frag = new VMGBaseFragment(getActivity());

        about__scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                frag.VMGScrollEvent(scrollY, scrollX, layout_about, view,getActivity());

            }
        });




        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // You just need to add these two lines of code to load the add
        VMGConfig.getVMGInstance(getActivity());
        frag.startVMG(getActivity(),this.view);
//////////////////////////////////////////////////////////////////
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




}
