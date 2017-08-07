package com.example.user.newapp.BaseFrag;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.user.newapp.EncodedBase;
import com.example.user.newapp.Interfaces.JavascriptInterfaceVMG;
import com.example.user.newapp.R;

import java.io.IOException;
import java.io.InputStream;

import static com.example.user.newapp.R.id.webView;

/**
 * Created by Seyfullah Semen  on 4-8-2017.
 */

public abstract class VMGBaseFragment extends Fragment {
    public  final String HTML = "index.html";
    public  final String baseUrl = "http://vmg.host/";
    private WebView webview;
    private String mraidJs;

    public VMGBaseFragment() {


    }




    public  void addJavascript(WebView webView, String javascript) {
        if (!javascript.isEmpty()) {
            webView.evaluateJavascript(javascript, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    Log.i("Info: ", "Evaluation done " + s);
                }
            });
        } else {
            System.out.println("Loading " + javascript);
            webView.loadUrl("javascript:" + javascript);
        }
    }

    public void addMraid(WebView webview) {
        if (TextUtils.isEmpty(mraidJs)) {
            byte[] mraidArray = Base64.decode(EncodedBase.mraidFile, Base64.DEFAULT);
            mraidJs = new String(mraidArray);

        }
        System.out.println("adding mraid is oke and ready to go " + mraidJs.length());
        webview.loadData("<html></html>", "text/html", "UTF-8");
        webview.evaluateJavascript(mraidJs, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                Log.i("Info", "Mraiding" + mraidJs);
            }
        });
        openWeb(webview);

    }

    public void openWeb(WebView webView) {
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

        webView.loadDataWithBaseURL(this.baseUrl, text, "text/html", "UTF-8", "");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}


