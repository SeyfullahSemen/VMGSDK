package com.example.user.SDKDemo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vmg.BaseUtils.VMGBase;

public class InPageScrollWithLayout extends Fragment {
    private ConstraintLayout constraint;
    private NestedScrollView nested;
    private VMGBase base;

    public InPageScrollWithLayout() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.in_page_scroll_with_layout, container, false);
        constraint = view.findViewById(R.id.constraint);
        nested = view.findViewById(R.id.nested);

        base = new VMGBase(getActivity(), constraint, 6194);
        nested.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                base.VMGScrollEvent(nested, constraint);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
