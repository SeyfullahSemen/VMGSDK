package com.example.user.newapp.VMGCustom;
/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 * <p>
 *     this is our custom webview here we define our own webview
 *     and implement the things that our webview needs to have
 * </p>
 *
 */
import android.content.Context;

import android.graphics.Canvas;


import android.graphics.Color;
import android.util.AttributeSet;


import android.webkit.WebView;


import com.example.user.newapp.R;


public class VMGCustomView extends WebView {


    public VMGCustomView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public VMGCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);


    }

    public VMGCustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        WebView webView = (WebView) findViewById(R.id.our_web);
        setBackgroundColor(Color.TRANSPARENT); // set the background to transparent

        setTranslationZ(16);
        setElevation(10);
        animate();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;


    }


}
