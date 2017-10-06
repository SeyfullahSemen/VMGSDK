package com.example.user.SDKDemo;
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

import com.example.user.SDKDemo.R;
import com.example.user.SDKDemo.adapters.RecycleAdapter;
import com.vmg.BaseUtils.VMGBase;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;

public class RecyclerFragment extends Fragment {
    protected RecyclerView mRecyclerView;
    private WebView webbs;
    private VMGBase frag;
    private PullToRefreshView refreshRecyclerview;

    public RecyclerFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_recycler, container, false);
        mRecyclerView = v.findViewById(R.id.recycler);
        webbs = v.findViewById(R.id.webbs); // get the id of the webview
        refreshRecyclerview = v.findViewById(R.id.refreshRecyclerview);
        refreshRecyclerview.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshRecyclerview.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshRecyclerview.setRefreshing(false);
                        frag = new VMGBase(getActivity(), webbs, 6342);
                        //frag.startVMG(6342);

                    }
                }, 2000);
            }
        });

        frag = new VMGBase(getActivity(), webbs, 6342);
        // frag.startVMG(6342);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
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
