package com.example.user.newapp;


import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.newapp.Interfaces.JavascriptInterfaceVMG;
import com.example.user.newapp.Interfaces.VMGInternal;
import com.example.user.newapp.Interfaces.VMGMraidEvents;

import java.io.IOException;
import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewFragment extends Fragment implements JavascriptInterfaceVMG, VMGMraidEvents, VMGInternal {
    private ListView listView;
    private WebView webber;
    private String mraidjs;
    private String baseUrl;
    private String HTMLName = "index.html";
    private boolean isViewable;
    private boolean isPageFinished = true;
    private boolean isLaidOut = true;

    public ListViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_view, container, false);
        listView = (ListView) v.findViewById(R.id.listView);

        webber = (WebView) v.findViewById(R.id.webber); // get the id of the webview
        webber.setBackgroundColor(Color.TRANSPARENT); // set the background to transparent
        WebSettings settings = webber.getSettings(); // this is for enabling the javascript
        settings.setJavaScriptEnabled(true); // set javascript enabled

        // set debugging on for debugging on google chrome
        webber.setWebContentsDebuggingEnabled(true); // this is for debugging within google chrome
        // we use our own webViewClient so we have more control over our webView
//     webView.setWebViewClient(new VMGWebViewClient());
        // here we add our mraid file
        addMraid(webber);
        // here we test a couple of things to check whether mraid is working with java or not

        fireReadyEvent(); // fire the ready event
        getScreenSize();
        addJavaScript("mraid.isViewable();");


        // here we test a couple of things to check whether mraid is working with java or not


        return v;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Set ListView basic adapter
        setValues(listView);


    }

    private void setValues(ListView listView) {
        int size = 50;
        String[] vals = new String[size];

        for (int i = 0; i < vals.length; i++) {
            vals[i] = "VMG Demo " + i;
        }


        ArrayAdapter adapter = new ArrayAdapter<>(getActivity(),
                R.layout.list_row, R.id.listViewText, vals);
        listView.setAdapter(adapter);
    }


    /**
     * @param webView
     */
    @SuppressLint("newApi")
    @Override
    public void addMraid(WebView webView) { // we expect a webview to load our mraid in
        if (TextUtils.isEmpty(mraidjs)) { // we made a string to save our mraid file in
            byte[] mraidArray = Base64.decode(EncodedBase.mraidFile, Base64.DEFAULT); // decode the file that we made in our Asset Class
            mraidjs = new String(mraidArray); // save what we decoded in the String mraidJs
        }
        System.out.println("adding mraid is oke and ready to goo " + mraidjs.length()); // check the length of our mraid

        webView.loadData("<html></html>", "text/html", "UTF-8");
        webView.evaluateJavascript(mraidjs, new ValueCallback<String>() { // make a callback to see if the mraid file is correctly loaded
            @Override
            public void onReceiveValue(String s) {
                Log.i("Info", "Mraiding " + mraidjs);
            }

        });
        openWeb("http://vmg.host/", HTMLName); // make a call to the method that we made to open our HTML file with mraid injected

//       webView.loadUrl("javascript:" + mraidjs);
//        Log.i("IMPORTANTE ",mraidjs);
    }

    @Override
    public void addJavaScript(String javaScript) {
        addJavaScript(webber, javaScript);
    }

    @SuppressLint("newApi")
    @Override
    public void addJavaScript(WebView webView, String javaScript) {
        if (!javaScript.isEmpty()) {

            webView.evaluateJavascript(javaScript, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    Log.i("info:", "Evalution done " + s);
                }
            });
        } else {
            System.out.println("Loading" + javaScript);
            webView.loadUrl("javascript:" + javaScript);

        }


    }

    /**
     * we just need to add the baseUrl and the HTML file name
     *
     * @param baseUrl
     * @param HTMLName
     */
    @Override
    public void openWeb(String baseUrl, String HTMLName) { // this is the method that will make sure that we can open a HTML file with

        AssetManager assetManager = getActivity().getAssets(); // this wil get the HTML file that we mad e
        // byte buffer into a string
        String text = "";
        try {
            InputStream input = assetManager.open(HTMLName); // this is will get our file named index
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            text = new String(buffer);

        } catch (IOException e) { // catch any exceptions
            e.printStackTrace();
        }
        Log.i("info", "HTML file " + text); // this is for debugging reasons

        webber.loadDataWithBaseURL(baseUrl, text, "text/html", "UTF-8", ""); // this will load the HTML file with the baseURL


    }

    /**
     * the methods below is for checking if java can communicate with Javascript
     * we accomplish this with simple getters to check everything
     */
    @Override
    public void fireReadyEvent() {
        addJavaScript("mraid.fireReadyEvent();");
        Log.i("info", "READYYYY NIFFFOOOO");


    }

    @Override
    public void getScreenSize() {
        addJavaScript("mraid.getScreenSize();");
        Log.i("Info about Screen Size ", "Screen size is working");
    }

    @Override
    public void isViewable() {
        addJavaScript("mraid.isViewable();");
        Log.i("info about viewable ", " Viewable works");

    }

    @Override
    public void getDefaultPosition() {
        addJavaScript("mraid.getDefaultPosition();");
        Log.i("Info default Position ", " Default position is working");
    }

    @Override
    public void getState() {
        addJavaScript("mraid.getState();");
        Log.i("info State ", " State is working");
    }

    @Override
    public void removeEventListener() {
        addJavaScript("mraid.removeEventListener();");
        Log.i("info Remove", " Removing");
    }

    @Override
    public void fireViewableChangeEvent() {
        Log.i("INFORMATION", "fireViewableChangeEvent");
        addJavaScript("mraid.fireViewableChangeEvent(" + isViewable + ");");


    }

    @Override
    public void ScrollEventVMG(float scrollY, float scrollX) {
        int[] location = {0, 0};
        int height = 255;
        webber.getLocationOnScreen(location);
        int topper = webber.getTop();
        int all = height + location[1];
        if (all < 0) {
            isViewable = false;
            addJavaScript("mraid.fireViewableChangeEvent(" + isViewable + ");");
            Log.i("Viewer", " " + isViewable);
            addJavaScript("mraid.isViewable();");
        } else {
            isViewable = true;
            addJavaScript("mraid.fireViewableChangeEvent(" + isViewable + ");");
            Log.i("Viewert", " " + isViewable);
            addJavaScript("mraid.isViewable();");

        }
        Log.i("sdhg ", "" + scrollY + " " + scrollX + " " + location[0] + " " + location[1]);

    }


}
