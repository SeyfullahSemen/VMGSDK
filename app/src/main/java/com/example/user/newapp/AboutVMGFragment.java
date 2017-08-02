package com.example.user.newapp;

/**
 *Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutVMGFragment extends Fragment {

    private ImageView img_logo_about;
    private TextView text_about;


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
}
