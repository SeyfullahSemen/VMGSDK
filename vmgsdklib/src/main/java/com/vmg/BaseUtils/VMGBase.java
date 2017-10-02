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
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.autofill.AutofillValue;
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
import com.vmg.LoggerPack.VMGLogs;
import com.vmg.MobileInfo.UserInfoMobile;
import com.vmg.VMGParser.ParseMraidCommands;
import com.vmg.vmgsdklib.R;

import java.net.URLDecoder;
import java.util.HashMap;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;


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
    private Context context;
    private int state;


    /**
     * instantiate the variables
     * the things we need to instantiate is a couple of things to make our app
     * work properly we need to give it a context and make a new instance
     * I also instantiate the things we need to mak our app work in order to get no
     * nullpointerException
     *
     * @param context this is the context of the given page
     * @param webView this is the webview to load the ad in
     */
    @SuppressLint("NewApi")
    public VMGBase(Context context, WebView webView) {
        super(context);

        this.context = context;
        this.webView = webView;

        resizeProperties = new VMGResizeProperties();
        displayMetrics = new DisplayMetrics();


        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        if (context instanceof Activity) {
            orientationLocking = ((Activity) context).getRequestedOrientation();
        } else {
            orientationLocking = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
        }


        vmgClient = new VMGWebviewClient();
        UserInfoMobile mobile = new UserInfoMobile(context);
        handler = new Handler(Looper.getMainLooper());
        VMGLogs.Information(mobile.mobileInfo());
    }

    /**
     * calculates and sets the ad width
     *
     * @return the ad width
     */
    @SuppressLint("NewApi")
    private double getAdWidth() {
        double dpi = displayMetrics.densityDpi;
        webView.setInitialScale((int) dpi);
        double answer = dpi / 100;
        double addWidth = webView.getMeasuredWidth() / answer;

        return addWidth;
    }

    /**
     * @param javascript this is for the input of the javascript command
     */
    private void useJavascript(String javascript) {
        useJavascript(webView, javascript);
    }

    /**
     * @param webview    this will be the webview that needs to get manipulated bij the javascript code
     * @param javascript enter the javascript command
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
     * @param scrollY this value is the Y
     * @param scrollX this value is the X
     * @param view    this is the view to get the whole size of
     */
    public void VMGScrollEvent(float scrollY, float scrollX, ViewGroup view) {
        double layoutH = view.getMeasuredHeight(); // get the height of the layout where the webview is saved in

        double topOffset = (double) VMGConfig.getVMGInstance(context).retrieveSpecific("topOffset");
        double bottomOffset = (double) VMGConfig.getVMGInstance(context).retrieveSpecific("bottomOffset");

        if (scrollY - webView.getY() > (resizeProperties.height * topOffset)) { // top
            isViewable = false;
            useJavascript("mraid.fireViewableChangeEvent(" + isViewable + ");");
            useJavascript("mraid.isViewable();");
        } else if (scrollY + layoutH < webView.getY() + (resizeProperties.height * bottomOffset)) { // bottom
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
    @SuppressLint({"NewApi", "SetJavaScriptEnabled"})
    public void startVMG(int placementId) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        WebView.setWebContentsDebuggingEnabled(true);

        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setWebViewClient(vmgClient);
        webView.loadUrl(VMGUrlBuilder.getPlacementUrl(placementId));
    }

    /**
     * this will open up a new browser when the user clicks on the add
     * it wil start a whole new browser when clicked on it
     *
     * @param url the given URL will be opened
     */
    private void openBrowser(String url) {
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
            VMGLogs.Information(url);
            openBrowser(url);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            VMGLogs.fatal("cannot open URL " + ex.getMessage());
        }
    }

    /**
     * @param commandUrl this method  is responsible for parsing the command that come in from our mraid
     *                   it parses the String that we gave as parameter
     */
    private void parseUrl(String commandUrl) {
        ParseMraidCommands parser = new ParseMraidCommands();
        HashMap<String, String> commandMap = parser.parseMraidUrl(commandUrl);
        String command = commandMap.get("command");
        VMGLogs.debug(command);
        try {
            if (command.equals("open")) {
                openUrl(commandMap.get("url"));
                VMGLogs.Information("Opening url " + commandMap.get("url"));
            }
            if (command.equals("resize")) {
                resize();
            }
            if (command.equals("close")) {
                close();
                VMGLogs.Information("closing " + commandMap.get("close"));
            }
            if (command.equals("setResizeProperties")) {
                setResizeProperties(commandMap);
                VMGLogs.Information("resize properties  " + " width: " + commandMap.get("width") +
                        " height: " + resizeProperties.height + " offsetX: " + commandMap.get("offsetX") + " offsetY:" + commandMap.get("offsetY"));
            }
        } catch (Exception e) {
            VMGLogs.fatal("Error while getting the method in VMGBase:" + e.getMessage());
        }


    }


    /**
     * this will make sure that the right add size will be set when the add
     * is loaded
     *
     * @param properties this are the proper
     */
    private void setResizeProperties(HashMap<String, String> properties) {
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
     * automattically this method also talks to the ParseMraidCommands class which parser the command
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

            FrameLayout rootView = getRootView().findViewById(android.R.id.content);
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
     * this is a method which will set the ad back to the resized size
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
                    VMGLogs.Information("closing from state: " + getState());

                } else if (state == RESIZED) {
                    closeResized();
                    VMGLogs.Information("closing from resized ");
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
        FrameLayout rootView = ((Activity) context).findViewById(android.R.id.content);
        rootView.removeView(resizedView);
        resizedView = null;

    }

    private void setMaxSize() {
        useJavascript("mraid.setMaxSize('" + getAdWidth() + "','" + resizeProperties.height + "');");
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
        useJavascript("mraid.setState('" + getState() + "');");
        VMGLogs.debug("state of the ad " + getState());
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


