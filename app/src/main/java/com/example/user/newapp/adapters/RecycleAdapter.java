/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 * <p>
 * Created by Seyfullah Semen
 */

package com.example.user.newapp.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebView;
import android.widget.TextView;

import com.example.user.newapp.BaseFrag.VMGBaseFragment;
import com.example.user.newapp.Interfaces.VMGMraidEvents;
import com.example.user.newapp.R;

import java.util.List;

/**
 * Created by User on 8-8-2017.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private List<String> mDataset;
    private RecyclerView mRecyclerView;


    public RecycleAdapter(List<String> dataset, RecyclerView recyclerView) {
        mDataset = dataset;
        mRecyclerView = recyclerView;
    }

    @Override
    public RecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecycleAdapter.ViewHolder holder, int position) {
        final String name = mDataset.get(position);

        holder.textView.setText(name);


    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;


        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.listViewText);

        }
    }
}
