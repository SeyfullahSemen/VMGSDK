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
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.vmg.ConfigVMG.VMGConfig;
import com.vmg.ConfigVMG.VMGUrlBuilder;
import com.vmg.Events.ViewEvents;
import com.vmg.MobileInfo.UserInfoMobile;
import com.vmg.VMGParser.Parser;
import com.vmg.vmgsdklib.R;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Map;

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
    private VMGWebviewClient vmgClient;
    private RelativeLayout resizedView;
    private DisplayMetrics displayMetrics;
    private Handler handler;
    private WebView webView;
    private int addWidth = 340;
    private int addHeight = 255;
    private Context context;
    private UserInfoMobile mobileInfo;
    private int state;


    /**
     * instantiate the variables
     * the things we need to instantiate is a couple of things to make our app
     * work properly we need to give it a context and make a new instance
     * I also instantiate the things we need to mak our app work in order to get no
     * nullpointerException
     *
     * @param context
     * @param webView
     */
    @SuppressLint("NewApi")
    public VMGBase(Context context, WebView webView) {
        super(context);

        this.context = context;
        this.webView = webView;

        resizeProperties = new VMGResizeProperties();
        displayMetrics = new DisplayMetrics();
        resizedView = new RelativeLayout(context);

        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        if (context instanceof Activity) {
            orientationLocking = ((Activity) context).getRequestedOrientation();
        } else {
            orientationLocking = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
        }

        LinearLayout.LayoutParams layoutWebview =
                new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutWebview.gravity = Gravity.CENTER; // sets the webview to the center
        this.webView.setElevation(16); // this sets the elevation
        this.webView.setLayoutParams(layoutWebview); // set the params

        vmgClient = new VMGWebviewClient();
        mobileInfo = new UserInfoMobile(context);
        handler = new Handler(Looper.getMainLooper());

    }

    /**
     * sets the add width
     *
     * @param addWidth
     */
    public void setAddWidth(int addWidth) {
        this.addWidth = addWidth;
        resizeProperties.width = this.addWidth;
    }

    /**
     * sets the add height
     *
     * @param addHeight
     */
    public void setAdHeight(int addHeight) {
        this.addHeight = addHeight;
        resizeProperties.height = this.addHeight;
    }

    /**
     * gets the add width that is entered
     *
     * @return
     */
    public int getAdWidth() {
        return resizeProperties.width;
    }

    /**
     * gets the add height that is entered
     *
     * @return
     */
    public int getAdHeight() {
        return addWidth;
    }

    /**
     * @param javascript
     */
    private void useJavascript(String javascript) {
        useJavascript(webView, javascript);
    }

    /**
     * @param webview
     * @param javascript
     */
    @SuppressLint("NewApi")
    private void useJavascript(WebView webview, String javascript) {
        if (!javascript.isEmpty()) {
            webview.evaluateJavascript(javascript, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                }
            });
        } else {
            webview.loadUrl("javascript:" + javascript);
        }
    }

    /**
     * this method can be used when the user has a nestedscrollview or a scrollview
     *
     * @param scrollY
     * @param scrollX
     * @param view
     */
    public void VMGScrollEvent(float scrollY, float scrollX, ViewGroup view) {
        int[] location = {0, 0}; // save the locations x and y of the sroll
        double layoutH = view.getMeasuredHeight(); // get the height of the layout where the webview is saved in

        double topOffset = (double) VMGConfig.getVMGInstance(context).retrieveSpecific("topOffset");
        double bottomOffset = (double) VMGConfig.getVMGInstance(context).retrieveSpecific("bottomOffset");

        if (scrollY - webView.getY() > (getAdHeight() * topOffset)) {
            isViewable = false;
            useJavascript("mraid.fireViewableChangeEvent(" + isViewable + ");");
            useJavascript("mraid.isViewable();");
        } else if (scrollY + layoutH < webView.getY() + (getAdHeight() * bottomOffset)) {
            isViewable = false;
            useJavascript("mraid.fireViewableChangeEvent(" + isViewable + ");");
            useJavascript("mraid.isViewable();");
        } else {
            isViewable = true;
            useJavascript("mraid.fireViewableChangeEvent(" + isViewable + ");");
            useJavascript("mraid.isViewable();");
        }
    }

    /**
     * this is where the webview gets filled with the mraid file and it opens the webview
     * so the user just needs to add this method to get the full functionallity
     */
    @SuppressLint("NewApi")
    public void startVMG() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebContentsDebuggingEnabled(true);
        webView.setWebViewClient(vmgClient);
        VMGConfig.getVMGInstance(context);
        webView.loadUrl(VMGUrlBuilder.getPlacementUrl());
    }

    /**
     * this will open up a new browser when the user clicks on the add
     * it wil start a whole new browser when clicked on it
     *
     * @param url
     */
    public void openBrowser(String url) {
        getContext().startActivity(
                new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    /**
     * @param url this method will make sure that we can open our new browser when clicked on the add
     *            it needs a url this url will be decoded
     */
    private void openUrl(String url) {
        try {
            url = URLDecoder.decode(url, "UTF-8");
            Log.d(TAG, " " + url);
            openBrowser(url);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            Log.d(TAG, "Cannot open " + ex.getMessage());
        }
    }

    /**
     * @param commandUrl this method  is responsible for parsing the command that come in from our mraid
     *                   it parses the String that we gave as parameter
     */
    private void parseUrl(String commandUrl) {
        Parser parser = new Parser();
        Map<String, String> commandMap = parser.parseCommandUrl(commandUrl);
        String command = commandMap.get("command");

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
            Log.d(TAG, "Somthing went wrong: " + e.getMessage());
        }
    }


    /**
     * this will make sure that the right add size will be set when the add
     * is loaded
     *
     * @param properties
     */
    private void setResizeProperties(Map<String, String> properties) {
        int width = Integer.parseInt(properties.get("width"));
        int height = Integer.parseInt(properties.get("height"));
        int offsetX = Integer.parseInt(properties.get("offsetX"));
        int offsetY = Integer.parseInt(properties.get("offsetY"));

        String customClosePosition = properties.get("customClosePosition");
        boolean allowOffscreen = Boolean.parseBoolean(properties.get("allowOffscreen"));

        resizeProperties.width = width;
        resizeProperties.height = height;
        resizeProperties.offsetX = offsetX;
        resizeProperties.offsetY = offsetY;
        resizeProperties.customClosePosition = VMGResizeProperties.customClosePositionFromString(customClosePosition);
        resizeProperties.allowOffscreen = allowOffscreen;
    }

    /**
     * this is the resize method which makes it possible to resize the size of the add
     * automattically this method also talks to the Parser class which parser the command
     */
    private void resize() {
        if (listener == null) {
            return;
        }

        boolean isReadyToResize = listener.mraidViewResize(this, resizeProperties.width, resizeProperties.height, resizeProperties.offsetX, resizeProperties.offsetY);

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

        setResizedSize();

        handler.post(new Runnable() {
            @Override
            public void run() {
                fireStateChangeEvent();
            }
        });
    }

    /**
     * this is a method which will set the add back to the resized size
     */
    private void setResizedSize() {
        int wDip = resizeProperties.width;
        int hDip = resizeProperties.height;

        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, wDip, displayMetrics);
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, hDip, displayMetrics);

        FrameLayout.LayoutParams parameters = new FrameLayout.LayoutParams(width, height);
        resizedView.setLayoutParams(parameters);
    }


    /**
     * this method will close our add and will give a notification to mraid that it is closed
     */
    private void close() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (state == LOADING || state == DEFAULT || state == HIDDEN) {
                    return;
                } else if (state == RESIZED) {
                    closeResized();
                    Log.d(TAG, " Closing from resized");
                }
            }
        });
    }

    /**
     * this will take care of closing the resized view
     */
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
    }

    /**
     * this gets rid of the view that has been resized
     */
    private void removeResizeView() {
        resizedView.removeAllViews();
        FrameLayout rootView = (FrameLayout) ((Activity) context).findViewById(android.R.id.content);
        rootView.removeView(resizedView);
        resizedView = null;

    }

    private void setMaxSize() {
        useJavascript("mraid.setMaxSize('" + getAdWidth() + "','" + getAdHeight() + "');");
    }

    /**
     * @return state of the ad
     */
    private int getState() {
        return state;
    }

    /**
     * this will be fired when the add changes its viewable state  this is to check
     * whether the state is viewable or not
     */
    private void fireViewableChangeEvent() {
        useJavascript("mraid.fireViewableChangeEvent('" + isViewable + "');");
    }

    /**
     * this will fire the state to ready
     */
    private void fireReadyChangeEvent() {
        useJavascript("mraid.fireReadyEvent();");
    }

    /**
     * this will be called when the states need to change
     */
    private void fireStateChangeEvent() {
        String[] states = {"loading", "default", "expanded", "resized", "hidden"};
        useJavascript("mraid.setState('" + states[state] + "');");
    }


    /**
     * this is our own webviewclient  wihich takes care when the add is loaded
     * and when the user clicks on our add and needs to parse the urls that start with mraid://
     */
    private class VMGWebviewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            Animation slideAnimation =
                    AnimationUtils.loadAnimation(context,
                            R.anim.slide_in);

            if (state == LOADING) {
                state = DEFAULT;
                fireStateChangeEvent();
                setMaxSize();
                fireReadyChangeEvent();
                fireViewableChangeEvent();
                view.setAnimation(slideAnimation);

            }
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("mraid://")) {
                parseUrl(url);
                return true;
            } else {
                openUrl(url);
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
                openUrl(URL);
                return true;
            }
        }
    }
}


