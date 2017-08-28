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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


import com.example.user.newapp.BaseUtils.VMGBase;

import com.example.user.newapp.ConfigVMG.VMGConfig;
import com.example.user.newapp.adapters.RecycleAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerFragment extends Fragment  {
    protected RecyclerView mRecyclerView;
    private WebView webbs;
    VMGBase frag ;

    public RecyclerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recycler, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler);
        webbs = (WebView) v.findViewById(R.id.webbs); // get the id of the webview

        frag = new VMGBase(getActivity(),webbs);


        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Set ListView basic adapter

        frag.startVMG();
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




}
