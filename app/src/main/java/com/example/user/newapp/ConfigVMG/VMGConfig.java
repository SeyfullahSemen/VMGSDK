package com.example.user.newapp.ConfigVMG;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Seyfullah Semen on 9-8-2017.
 */

public class VMGConfig {

    private static final String TAG = "VMGConfig";
    // make a static instance of the Singleton
    private static VMGConfig VMGInstance = null;
    // hashmap for the key value pairs
    private static HashMap<String, Object> VMGValues = new HashMap<>();
    private static Context context;
    private RequestQueue requestQueue;


    private VMGConfig(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    /**
     * create a lazy initialization, this version is thread safe
     *
     * @return
     */
    public static VMGConfig geVMGInstance(Context context) {
        if (VMGInstance == null) {
            Class ConfigClass = VMGConfig.class;
            synchronized (ConfigClass) {
                if (VMGInstance == null) {
                    VMGInstance = new VMGConfig(context);
                    VMGValues.put("topOffset", 0.6);
                    VMGValues.put("bottomOffset", 0.4);
                    VMGValues.put("slideInOnStart", true);
                    VMGValues.put("slideInOnClose", true);
                    VMGValues.put("fadeInOnStart", false);
                    VMGValues.put("fadeOutOnClose", false);
                    VMGValues.put("debug", false);

                }
            }
        }
        return VMGInstance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    /**
     * set a new value in the hashmap
     *
     * @param key
     * @param value
     * @param <T>
     * @param <E>
     */
    public <T extends String, E> void setValue(T key, E value) {
        if (VMGValues.containsKey(key)) {
            VMGValues.remove(key);
            VMGValues.put(key, value);
        } else {
            VMGValues.put(key, value);
        }

    }

    /**
     * get the values of the hashmap
     *
     * @return
     */
    public HashMap<String, Object> getValues() {
        if (VMGValues.isEmpty()) {
            Log.i(TAG, "There are no values");
            return null;
        } else {
            for (Map.Entry<String, Object> entry : VMGValues.entrySet()) {
                Log.i(TAG, " " + entry.getKey() + "  " + entry.getValue());
            }
            return VMGValues;
        }

    }

    /**
     * get a specific value out of the hashmap
     *
     * @param key
     * @return
     */
    public Object retrieveSpecific(Object key) {
        Object val = null;
        if (!VMGValues.containsKey(key)) {
            Log.i(TAG, " No value found");
        } else {
            val = VMGValues.get(key);
        }
        return val;
    }

}