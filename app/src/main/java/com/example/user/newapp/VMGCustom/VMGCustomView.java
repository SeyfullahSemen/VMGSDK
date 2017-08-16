package com.example.user.newapp.VMGCustom;

import android.content.Context;

import android.graphics.Canvas;


import android.graphics.Color;
import android.util.AttributeSet;


import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;


import com.example.user.newapp.R;


public class VMGCustomView extends WebView {


    public VMGCustomView(Context context) {
        super(context);
//        init(context,null, 0);
    }

    public VMGCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);


        WebView webView = (WebView) findViewById(R.id.our_web);
        setBackgroundColor(Color.TRANSPARENT); // set the background to transparent

        animate();
    }

    public VMGCustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        init(context,attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;


    }


}
