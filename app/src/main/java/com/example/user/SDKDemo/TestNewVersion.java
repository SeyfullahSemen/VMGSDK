package com.example.user.SDKDemo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.vmg.BaseUtils.VMGBase;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestNewVersion extends Fragment {
    private LinearLayout linearlayout;
    private NestedScrollView nested;
    private RelativeLayout relative;
    private VMGBase base;

    public TestNewVersion() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_test_new_version, container, false);
        linearlayout = view.findViewById(R.id.linearlayout);
        relative = view.findViewById(R.id.relative);
        nested = view.findViewById(R.id.nested);

        base = new VMGBase(getActivity(), linearlayout, 6370);
        nested.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                base.VMGScrollEvent(scrollY,nested,linearlayout);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
