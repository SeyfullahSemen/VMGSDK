package com.example.user.newapp;
/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 * <p>
 * Created by Seyfullah Semen
 */

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.user.newapp.BaseFrag.VMGBaseFragment;
import com.example.user.newapp.ConfigVMG.VMGConfig;
import com.example.user.newapp.Interfaces.VMGMraidEvents;
import com.example.user.newapp.VMGCustom.VMGCustomView;
import com.example.user.newapp.adapters.RecycleAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerFragment extends VMGBaseFragment implements VMGMraidEvents {
    protected RecyclerView mRecyclerView;
    private VMGCustomView webbs;


    public RecyclerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recycler, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler);
        webbs = (VMGCustomView) v.findViewById(R.id.webbs); // get the id of the webview

        fireReadyEvent(); // fire the ready event


        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Set ListView basic adapter
        VMGConfig.getVMGInstance(getActivity());
        super.startVMG(webbs);
        setRecyclerViewAdapter(mRecyclerView);


    }

    private void setRecyclerViewAdapter(RecyclerView recyclerView) {
        ArrayList<String> data = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            data.add("VMG " + i);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new RecycleAdapter(data, mRecyclerView));
    }


    @Override
    public void fireReadyEvent() {
        super.useJavascript(webbs, "mraid.fireReadyEvent();");
        Log.i("info", "READYYYY NIFFFOOOO");
    }

    @Override
    public void getScreenSize() {
        super.useJavascript(webbs, "mraid.getScreenSize();");
        Log.i("Info about Screen Size ", "Screen size is working");
    }

    @Override
    public void isViewable() {

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

    }


}
