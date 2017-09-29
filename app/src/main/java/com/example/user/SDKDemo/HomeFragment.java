package com.example.user.SDKDemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.SDKDemo.R;

public class HomeFragment extends Fragment {
    private TextView txt_home;
    private ImageView img_home;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_home, container, false);
        txt_home = v.findViewById(R.id.txt_home);
        img_home = v.findViewById(R.id.img_home);

        return v;
    }
}
