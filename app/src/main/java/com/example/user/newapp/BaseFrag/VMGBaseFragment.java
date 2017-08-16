/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 * <p>
 * this is a class to make everything more efficient it is our very own fragment that the client
 * can use whenever he makes a new fragment or layout he just needs to add a couple of simple
 * methods
 */
package com.example.user.newapp.BaseFrag;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;


import com.example.user.newapp.ConfigVMG.VMGBuilder;
import com.example.user.newapp.ConfigVMG.VMGConfig;
import com.example.user.newapp.Interfaces.VMGMraidEvents;
import com.example.user.newapp.MainActivity;
import com.example.user.newapp.R;
import com.example.user.newapp.VMGCustom.VMGCustomView;
import com.example.user.newapp.encodedFiles.EncodedBase;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Seyfullah Semen  on 4-8-2017.
 */

public abstract class VMGBaseFragment extends Fragment {
    /**
     * here we define a couple of our needed information
     * we need to have an html file with the name  of the file
     * we need a baseUrl and we make it private
     */
    public final String HTML = "index.html";
    public final String baseUrl = "http://vmg.host/";
    private boolean isViewable;
    private String mraidJs;


    // this is an empty constructor
    public VMGBaseFragment() {


    }


    /**
     * @param custom
     * @param javascript
     */
    public void addJavascript(VMGCustomView custom, String javascript) {
        if (!javascript.isEmpty()) {
            custom.evaluateJavascript(javascript, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    Log.i("Info: ", "Evaluation done " + s);
                }
            });
        } else {
            System.out.println("Loading " + javascript);
            custom.loadUrl("javascript:" + javascript);
        }
    }



    /**
     * @param custom
     */
    private void addMraid(VMGCustomView custom) {
        if (TextUtils.isEmpty(mraidJs)) {
            byte[] mraidArray = Base64.decode(EncodedBase.mraidFile, Base64.DEFAULT);
            mraidJs = new String(mraidArray);

        }
        System.out.println("adding mraid is oke and ready to go " + mraidJs.length());
        custom.loadData("<html></html>", "text/html", "UTF-8");
        custom.evaluateJavascript(mraidJs, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                Log.i("Info", "Mraiding" + mraidJs);
            }
        });
        openWeb(custom);

    }

    /**
     * @param custom
     */
    private void openWeb(VMGCustomView custom) {
        AssetManager assetManager = getActivity().getAssets();
        String text = "";
        try {
            InputStream input = assetManager.open(this.HTML);
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            text = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        custom.loadDataWithBaseURL(this.baseUrl, text, "text/html", "UTF-8", "");
    }

    /**
     * this method can be used when the user has a nestedscrollview or a scrollview
     *
     * @param scrollY
     * @param scrollX
     * @param view
     * @param custom
     */
    public void VMGScrollEvent(float scrollY, float scrollX, ViewGroup view, VMGCustomView custom) {
        int[] location = {0, 0}; // save the locations x and y of the sroll

        int heightOfContent = custom.getContentHeight(); // get the heigth of the webview


        double layoutH = view.getMeasuredHeight(); // get the height of the layout where the webview is saved in
        int width = custom.getWidth(); // get the width of the webview
        int heightWeb = custom.getHeight(); // get the height of the webview

        Log.i("content Height", "" + heightOfContent);
        Log.i("widthWeb ", "" + width);
        Log.i("heightWeb ", "" + heightWeb);
//        int all = heightOfContent + location[1];
        if (scrollY - custom.getY() > (heightOfContent * (double) VMGConfig.geVMGInstance().retrieveSpecific("Percentage_up"))) {
            isViewable = false;
            addJavascript(custom, "mraid.fireViewableChangeEvent(" + isViewable + ");");
            Log.i("Viewer", " " + isViewable);
            addJavascript(custom, "mraid.isViewable();");
        } else if (scrollY + layoutH < custom.getY() + (heightOfContent * (double) VMGConfig.geVMGInstance().retrieveSpecific("Percentage_under"))) {
            isViewable = false;
            addJavascript(custom, "mraid.fireViewableChangeEvent(" + isViewable + ");");
            Log.i("Viewer", " " + isViewable);
            addJavascript(custom, "mraid.isViewable();");
        } else {

            isViewable = true;
            addJavascript(custom, "mraid.fireViewableChangeEvent(" + isViewable + ");");
            Log.i("Viewer", " " + isViewable);
            addJavascript(custom, "mraid.isViewable();");

        }


        Log.i("sdhg ", "" + scrollY + " " + scrollX + " " + location[0] + " " + location[1]);
    }// end of VMGScrollEvent();


    /**
     * this is where the webview gets filled with the mraid file and it opens the webview
     * so the user just needs to add this method to get the full functionallity
     *
     * @param custom
     */
    public void startVMG(VMGCustomView custom) {

        WebSettings settings = custom.getSettings();


        settings.setJavaScriptEnabled(true); // set javascript enabled
        // set debugging on for debugging on google chrome

        custom.setWebContentsDebuggingEnabled(true); // this is for debugging within google chrome

        openWeb(custom);
        addMraid(custom);


    }// end of startVMG();


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}


