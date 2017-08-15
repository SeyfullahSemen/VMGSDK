package com.example.user.newapp.VMGCustom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.audiofx.BassBoost;
import android.text.TextPaint;
import android.util.AttributeSet;

import android.widget.LinearLayout;


/**
 * TODO: document your custom view class.
 */
public class VMGCustomView extends LinearLayout {


    public VMGCustomView(Context context) {
        super(context);
//        init(context,null, 0);
    }

    public VMGCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);



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
