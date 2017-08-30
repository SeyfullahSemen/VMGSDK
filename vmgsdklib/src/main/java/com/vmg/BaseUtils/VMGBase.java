/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 * <p>
 * this is a class to make everything more efficient it is our very own fragment that the client
 * can use whenever he makes a new fragment or layout he just needs to add a couple of simple
 * methods
 */
package com.vmg.BaseUtils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.vmg.ConfigVMG.VMGConfig;
import com.vmg.ConfigVMG.VMGUrlBuilder;
import com.vmg.Events.VMGEvents;
import com.vmg.Events.ViewEvents;
import com.vmg.VMGParser.Parser;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Map;


/**
 * Created by Seyfullah Semen  on 4-8-2017.
 */

@SuppressLint("ViewConstructor")
public class VMGBase extends RelativeLayout {


    private static final String TAG = "VMGBaseFragment";


    private boolean isViewable;
    private boolean isClosing;


    private final static int LOADING = 0;
    private final static int DEFAULT = 1;
    private final static int EXPANDED = 2;
    private final static int RESIZED = 3;
    private final static int HIDDEN = 4;
    private final int orientationLocking;


    private VMGResizeProperties resizeProperties;
    private ViewEvents listener;
    private VMGEvents events;
    private VMGWebviewClient vmgClient;
    private RelativeLayout resizedView;
    private DisplayMetrics displayMetrics;
    private Handler handler;

    private WebView webView;

    private int defaultAddWidth = 340;
    private int defaultAddHeight = 255;

    private Context context;


    private int state;


    // this is an empty constructor
    public VMGBase(Context context, WebView webView) {
        super(context);
        this.context = context;
        this.webView = webView;
        resizeProperties = new VMGResizeProperties();


        if (context instanceof Activity) {
            orientationLocking = ((Activity) context).getRequestedOrientation();
        } else {
            orientationLocking = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
        }
        vmgClient = new VMGWebviewClient();

        handler = new Handler(Looper.getMainLooper());

    }

    public void setAddWidth(int addWidth) {
        this.defaultAddWidth = addWidth;
        resizeProperties.width = this.defaultAddWidth;

    }

    public void setAddHeight(int addHeight) {
        this.defaultAddHeight = addHeight;
        resizeProperties.height = this.defaultAddHeight;

    }

    public int getAddWidth() {
        resizeProperties.width = this.defaultAddWidth;
        return resizeProperties.width;
    }

    public int getAddHeight() {
        resizeProperties.height = this.defaultAddHeight;
        return resizeProperties.height;
    }

    private void useJavascript(String javascript) {
        useJavascript(webView, javascript);
    }

    /**
     * @param custom
     * @param javascript
     */
    @SuppressLint("NewApi")
    private void useJavascript(WebView custom, String javascript) {
        if (!javascript.isEmpty()) {
            custom.evaluateJavascript(javascript, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    Log.i("Mraiding event : ", "Done " + s);
                }
            });
        } else {
            Log.d(TAG, " " + javascript);
            custom.loadUrl("javascript:" + javascript);
        }
    }// end of useJavaScript();


    /**
     *
     */
    private void openWeb() {

        webView.loadUrl(VMGUrlBuilder.getPlacementUrl());


    }// end of openWeb();

    /**
     * this method can be used when the user has a nestedscrollview or a scrollview
     *
     * @param scrollY
     * @param scrollX
     * @param view
     */
    public void VMGScrollEvent(float scrollY, float scrollX, ViewGroup view) {
        int[] location = {0, 0}; // save the locations x and y of the sroll

        int heightOfContent = webView.getContentHeight(); // get the heigth of the webview

        double layoutH = view.getMeasuredHeight(); // get the height of the layout where the webview is saved in

        double topOffset = (double) VMGConfig.getVMGInstance(context).retrieveSpecific("topOffset");
        double bottomOffset = (double) VMGConfig.getVMGInstance(context).retrieveSpecific("bottomOffset");


        if (scrollY - webView.getY() > (heightOfContent * topOffset)) {
            isViewable = false;
            useJavascript("mraid.fireViewableChangeEvent(" + isViewable + ");");
            Log.i("Viewer", " " + isViewable + "  ");
            useJavascript("mraid.isViewable();");
        } else if (scrollY + layoutH< getAddHeight() + (heightOfContent * bottomOffset)) {
            isViewable = false;
            useJavascript("mraid.fireViewableChangeEvent(" + isViewable + ");");
            Log.i("Viewer", " " + isViewable);
            useJavascript("mraid.isViewable();");
        } else {

            isViewable = true;
            useJavascript("mraid.fireViewableChangeEvent(" + isViewable + ");");
            Log.i("Viewer", " " + isViewable);
            useJavascript("mraid.isViewable();");

        }


        Log.i("sdhg ", "" + scrollY + " " + scrollX + " " + location[0] + " " + location[1]);
    }// end of VMGScrollEvent();


    /**
     * this is where the webview gets filled with the mraid file and it opens the webview
     * so the user just needs to add this method to get the full functionallity
     */
    @SuppressLint("NewApi")
    public void startVMG() {

        WebSettings settings = webView.getSettings();


        settings.setJavaScriptEnabled(true); // set javascript enabled
        // set debugging on for debugging on google chrome

        webView.setWebContentsDebuggingEnabled(true); // this is for debugging within google chrome


        webView.setWebViewClient(vmgClient);
        VMGConfig.getVMGInstance(context);
        openWeb();

    }// end of startVMG();


    /**
     * @param commandUrl
     * this method  is responsible for parsing the command that come in from our mraid
     * it parses the String that we gave as parameter
     */
    private void parseUrl(String commandUrl) {
        Log.d(TAG, "parseCommandUrl " + commandUrl); // this is for debugging reasons

        Parser parser = new Parser(); // make a new instance of the class parser
        Map<String, String> commandMap = parser.parseCommandUrl(commandUrl); // make a new Map and set it equal to the method parseCommandUrl

        String command = commandMap.get("command"); // get the object command so we can parse the information that is inside this object

        final String[] NoParameter = {
                "close",
                "resize",
        };

        final String[] WithString = {
                "createCalendarEvent",
                "expand",
                "open",
                "playVideo",
                "storePicture",
                "useCustomClose",
        };

        final String[] WithMap = {
                "setOrientationProperties",
                "setResizeProperties",
        };
/////////////////////{End of the arrays}
        /**
         * this code block right here converts the String arrays to a list and checks whether the list contains
         * the command if so then the right method will be called and executed
         */
        try {
            if (Arrays.asList(NoParameter).contains(command)) {
                Method method = getClass().getDeclaredMethod(command);
                method.invoke(this);
            } else if (Arrays.asList(WithString).contains(command)) {
                Method method = getClass().getDeclaredMethod(command, String.class);
                String key;
                if (command.equals("createCalendarEvent")) {
                    key = "eventJSON";
                } else if (command.equals("useCustomClose")) {
                    key = "useCustomClose";
                } else {
                    key = "url";
                }
                String val = commandMap.get(key);
                method.invoke(this, val);
            } else if (Arrays.asList(WithMap).contains(command)) {
                Method method = getClass().getDeclaredMethod(command, Map.class);
                method.invoke(this, commandMap);
            }
        } catch (Exception e) {
            Log.d(TAG, "Somthing wen wong " + e.getMessage());

        }
    }// end of parseURL();

    /**
     *
     * @param url
     * this method will make sure that we can open our new browser when clicked on the add
     * it needs a url this url will be decoded
     */
    private void open(String url) {
        try {
            url = URLDecoder.decode(url, "UTF-8");
            Log.d(TAG, " " + url);
            events.openBrowser(url);
        } catch (UnsupportedEncodingException ex) {
            System.err.println(ex.getMessage());
        }
    }// emd of open()

    /**
     * this will make sure that the right add size will be set when the add
     * is loaded
     * @param properties
     */
    private void setResizeProperties(Map<String, String> properties) {
        int width = Integer.parseInt(properties.get("width"));
        int height = Integer.parseInt(properties.get("height"));
        int offsetX = Integer.parseInt(properties.get("offsetX"));
        int offsetY = Integer.parseInt(properties.get("offsetY"));
        String customClosePosition = properties.get("customClosePosition");
        boolean allowOffscreen = Boolean.parseBoolean(properties.get("allowOffscreen"));
        Log.d(TAG, " " + "setResizeProperties "
                + width + " " + height + " "
                + offsetX + " " + offsetY + " "
                + customClosePosition + " " + allowOffscreen);
        resizeProperties.width = width;
        resizeProperties.height = height;
        resizeProperties.offsetX = offsetX;
        resizeProperties.offsetY = offsetY;
        resizeProperties.customClosePosition =
                VMGResizeProperties.customClosePositionFromString(customClosePosition);
        resizeProperties.allowOffscreen = allowOffscreen;
    }// end of setResizeProperties();


    private void resize() {
        Log.d(TAG, "resize");


        if (listener == null) {
            return;
        }
        boolean isReadyToResize = listener.mraidViewResize(this,
                resizeProperties.width, resizeProperties.height, resizeProperties.offsetX, resizeProperties.offsetY);
        if (!isReadyToResize) {
            return;
        }

        state = RESIZED;

        if (resizedView == null) {
            resizedView = new RelativeLayout(context);
            removeAllViews();
            resizedView.addView(webView);

            FrameLayout rootView = (FrameLayout) getRootView().findViewById(android.R.id.content);
            rootView.addView(resizedView);
        }
//        setCloseRegionPosition(resizedView);
        setResizedSize();
        //setResizedViewPosition();

        handler.post(new Runnable() {
            @Override
            public void run() {
                fireStateChangeEvent();
            }
        });
    }// end of resize();

    private void setResizedSize() {
        Log.d(TAG, "setResizedViewSize");
        int wDip = resizeProperties.width;
        int hDip = resizeProperties.height;

        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, wDip, displayMetrics);
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, hDip, displayMetrics);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        resizedView.setLayoutParams(params);
    }// end of setResizedSize();

    private void close() {
        Log.d(TAG, "closing the add");
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (state == LOADING || (state == DEFAULT) || state == HIDDEN) {
                    // do nothing
                    return;
                } else if (state == RESIZED) {
                    closeResized();
                }
            }
        });
    }// end of close();

    private void closeResized() {
        state = DEFAULT;
        isClosing = true;
        removeResizeView();
        addView(webView);
        handler.post(new Runnable() {
            @Override
            public void run() {
                fireStateChangeEvent();
                if (listener != null) {
                    listener.mraidViewClose(VMGBase.this);
                }
            }
        });
    }// end of closeResized();

    private void removeResizeView() {
        resizedView.removeAllViews();
        FrameLayout rootView = (FrameLayout) ((Activity) context).findViewById(android.R.id.content);
        rootView.removeView(resizedView);
        resizedView = null;

    }// end of removeResizeView();

    /**
     *
     *
     */
    private void setMaxSize() {


        useJavascript("mraid.setMaxSize('" + getAddWidth() + "','" + getAddHeight() + "');");

    }// end of setMaxSize();

    /**
     * @return
     */
    private int getState() {
        return state;
    }

    /**
     *
     *
     */
    private void fireViewableChangeEvent() {
        isViewable = true;
        useJavascript("mraid.fireViewableChangeEvent('" + isViewable + "');");
    }// end of fireViewableChangeEvent();

    /**
     *
     *
     */
    private void fireReadyChangeEvent() {
        useJavascript("mraid.fireReadyEvent();");
    }// end of fireReadyChangeEvent();

    /**
     *
     *
     */
    private void fireStateChangeEvent() {
        String[] states = {"loading", "default", "expanded", "resized", "hidden"};
        useJavascript("mraid.setState('" + states[state] + "');");
        Log.d(TAG, "" + getState());
    }// end of fireStateChangeEvent();


    /**
     *
     */
    private class VMGWebviewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (state == LOADING) {

                state = DEFAULT;
                fireStateChangeEvent();
                setMaxSize();
                fireReadyChangeEvent();

                fireViewableChangeEvent();


            }

        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("mraid://")) {
                parseUrl(url);
                return true;
            } else {
                open(url);
                return true;
            }

        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            final String URL = request.getUrl().toString();
            Log.d(TAG, "shouldOverrideUrlLoading: " + URL);
            if (URL.startsWith("mraid://")) {
                parseUrl(URL);
                return true;
            } else {
                open(URL);
                return true;
            }
        }


    }// end of VMGWebViewClient();


}


