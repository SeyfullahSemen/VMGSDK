package com.example.user.newapp;

/**
 *Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 */

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.newapp.BaseFrag.VMGBaseFragment;
import com.example.user.newapp.ConfigVMG.VMGConfig;
import com.example.user.newapp.Interfaces.VMGMraidEvents;


/**
 * A simple {@link Fragment} subclass.
 * Created by Seyfullah Semen
 */
public class AboutVMGFragment extends VMGBaseFragment implements VMGMraidEvents {
    private static final String TAG = "About fragment";
    private ImageView img_logo_about;
    private TextView text_about;
    private WebView webView;
    private boolean  isViewable ;
    private NestedScrollView about__scroll;
    private LinearLayout layout_about;

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
        webView = (WebView) v.findViewById(R.id.web_about);
        about__scroll = (NestedScrollView) v.findViewById(R.id.scroll__about);
        layout_about = (LinearLayout) v.findViewById(R.id.layout_about);

        webView.setBackgroundColor(Color.TRANSPARENT); // set the background to transparent
        WebSettings settings = webView.getSettings(); // this is for enabling the javascript
        settings.setJavaScriptEnabled(true); // set javascript enabled

        // set debugging on for debugging on google chrome
        webView.setWebContentsDebuggingEnabled(true); // this is for debugging within google chrome


        about__scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                AboutVMGFragment.super.VMGScrollEvent(scrollX,scrollY,layout_about,webView);

            }
        });


        super.openWeb(webView);
        super.addMraid(webView);

        fireReadyEvent();

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

    @Override
    public void fireReadyEvent() {
        Log.i(TAG, " fired the ready event");
        super.addJavascript(webView,"mraid.fireReadyEvent()");

    }

    @Override
    public void getScreenSize() {

    }

    @Override
    public void isViewable() {
        Log.i(TAG, " VIEWABLE");
        super.addJavascript(webView,"mraid.isViewable()");

    }

    @Override
    public void getDefaultPosition() {

    }

    @Override
    public void getState() {

    }

    @Override
    public void removeEventListener() {

    }

    @Override
    public void fireViewableChangeEvent() {
        Log.i("INFORMATION", "fireViewableChangeEvent");
        super.addJavascript(webView, "mraid.fireViewableChangeEvent(" + isViewable + ");");

    }

//    /**
//     *
//     * @param scrollY
//     * @param scrollX
//     * @param rela
//     * @param webView
//     */
//    private void scrollEventVMG(float scrollY, float scrollX, ViewGroup rela,WebView webView) {
//        int[] location = {0, 0}; // save the locations x and y of the sroll
//
//        int heightOfContent = webView.getContentHeight(); // get the heigth of the webview
//
//
//        double layoutH = rela.getMeasuredHeight(); // get the height of the layout where the webview is saved in
//        int width = webView.getWidth(); // get the width of the webview
//        int heightWeb = webView.getHeight(); // get the height of the webview
//
//        Log.i("content Height", "" + heightOfContent);
//        Log.i("widthWeb ", "" + width);
//        Log.i("heightWeb ", "" + heightWeb);
////        int all = heightOfContent + location[1];
//        if (scrollY - webView.getY() > (heightOfContent * (double) VMGConfig.geVMGInstance().retrieveSpecific("Percentage_up"))) {
//            isViewable = false;
//            super.addJavascript(webView, "mraid.fireViewableChangeEvent(" + isViewable + ");");
//            Log.i("Viewer", " " + isViewable );
//            super.addJavascript(webView, "mraid.isViewable();");
//        } else if (scrollY + layoutH < webView.getY() + (heightOfContent * (double) VMGConfig.geVMGInstance().retrieveSpecific("Percentage_under"))) {
//            isViewable = false;
//            super.addJavascript(webView, "mraid.fireViewableChangeEvent(" + isViewable + ");");
//            Log.i("Viewer", " " + isViewable );
//            super.addJavascript(webView, "mraid.isViewable();");
//        } else {
//
//            isViewable = true;
//            super.addJavascript(webView, "mraid.fireViewableChangeEvent(" + isViewable + ");");
//            Log.i("Viewer", " " + isViewable);
//            super.addJavascript(webView, "mraid.isViewable();");
//            fireReadyEvent(); // fire the ready event
//        }
//
//
//        Log.i("sdhg ", "" + scrollY + " " + scrollX + " " + location[0] + " " + location[1]);
//
//    }



}
