package com.example.user.newapp;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.newapp.BaseFrag.VMGBaseFragment;
import com.example.user.newapp.Interfaces.VMGMraidEvents;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewFragment extends VMGBaseFragment implements VMGMraidEvents {
    private ListView listView;
    private WebView webber;

    private boolean isViewable;
    private boolean isPageFinished = true;
    private boolean isLaidOut = true;

    public ListViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_view, container, false);
        listView = (ListView) v.findViewById(R.id.listView);

        webber = (WebView) v.findViewById(R.id.webber); // get the id of the webview
      super.startVMG(webber);

        fireReadyEvent(); // fire the ready event
        getScreenSize();
        super.addJavascript(webber, "mraid.isViewable();");


        // here we test a couple of things to check whether mraid is working with java or not


        return v;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Set ListView basic adapter
        setValues(listView);


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
        super.addJavascript(webber, "mraid.fireReadyEvent();");
        Log.i("info", "READYYYY NIFFFOOOO");


    }

    @Override
    public void getScreenSize() {
        super.addJavascript(webber, "mraid.getScreenSize();");
        Log.i("Info about Screen Size ", "Screen size is working");
    }

    @Override
    public void isViewable() {
        super.addJavascript(webber, "mraid.isViewable();");
        Log.i("info about viewable ", " Viewable works");

    }

    @Override
    public void getDefaultPosition() {
        super.addJavascript(webber, "mraid.getDefaultPosition();");
        Log.i("Info default Position ", " Default position is working");
    }

    @Override
    public void getState() {
        super.addJavascript(webber, "mraid.getState();");
        Log.i("info State ", " State is working");
    }

    @Override
    public void removeEventListener() {
        super.addJavascript(webber, "mraid.removeEventListener();");
        Log.i("info Remove", " Removing");
    }

    @Override
    public void fireViewableChangeEvent() {
        Log.i("INFORMATION", "fireViewableChangeEvent");
        super.addJavascript(webber, "mraid.fireViewableChangeEvent(" + isViewable + ");");


    }






}
