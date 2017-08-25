package com.example.user.newapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.newapp.BaseUtils.VMGBase;

import com.example.user.newapp.ConfigVMG.VMGConfig;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewFragment extends Fragment  {
    private ListView listView;
    private WebView webber;
    VMGBase frag ;

    private boolean isViewable;


    public ListViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_view, container, false);
        listView = (ListView) v.findViewById(R.id.listView);

        webber = (WebView) v.findViewById(R.id.webber); // get the id of the custom webview
        frag = new VMGBase(getActivity());




        return v;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Set ListView basic adapter
        setValues(listView);
        VMGConfig.getVMGInstance(getActivity());
        frag.startVMG(getActivity(),webber);

    }

    private void setValues(ListView listView) {
        int size = 50;
        String[] vals = new String[size];

        for (int i = 0; i < vals.length; i++) {
            vals[i] = "VMG Demo " + i;
        }


        ArrayAdapter adapter = new ArrayAdapter<>(getActivity(),
                R.layout.list_row, R.id.listViewText, vals);
        listView.setAdapter(adapter);
    }


    /**
     * the methods below is for checking if java can communicate with Javascript
     * we accomplish this with simple getters to check everything
     */



}
