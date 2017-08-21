package com.example.user.newapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.newapp.BaseFrag.VMGBaseFragment;
import com.example.user.newapp.ConfigVMG.VMGConfig;
import com.example.user.newapp.Interfaces.VMGMraidEvents;
import com.example.user.newapp.VMGCustom.VMGCustomView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewFragment extends VMGBaseFragment implements VMGMraidEvents {
    private ListView listView;
    private VMGCustomView webber;

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

        webber = (VMGCustomView) v.findViewById(R.id.webber); // get the id of the custom webview


        fireReadyEvent(); // fire the ready event
        getScreenSize();


        return v;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Set ListView basic adapter
        setValues(listView);
        VMGConfig.getVMGInstance(getActivity());
        super.startVMG(webber);

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
    @Override
    public void fireReadyEvent() {
        super.useJavascript(webber, "mraid.fireReadyEvent();");
        Log.i("info", "READYYYY TO GOO");


    }

    @Override
    public void getScreenSize() {
        super.useJavascript(webber, "mraid.getScreenSize();");
        Log.i("Info about Screen Size ", "Screen size is working");
    }

    @Override
    public void isViewable() {
        super.useJavascript(webber, "mraid.isViewable();");
        Log.i("info about viewable ", " Viewable works");

    }

    @Override
    public void getDefaultPosition() {
        super.useJavascript(webber, "mraid.getDefaultPosition();");
        Log.i("Info default Position ", " Default position is working");
    }

    @Override
    public void getState() {
        super.useJavascript(webber, "mraid.getState();");
        Log.i("info State ", " State is working");
    }

    @Override
    public void removeEventListener() {
        super.useJavascript(webber, "mraid.removeEventListener();");
        Log.i("info Remove", " Removing");
    }

    @Override
    public void fireViewableChangeEvent() {
        Log.i("INFORMATION", "fireViewableChangeEvent");
        super.useJavascript(webber, "mraid.fireViewableChangeEvent(" + isViewable + ");");


    }


}
