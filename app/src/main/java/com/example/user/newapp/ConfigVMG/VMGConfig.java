package com.example.user.newapp.ConfigVMG;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import android.content.Context;

import android.util.Log;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import java.util.Map;

/**
 * Created by Seyfullah Semen on 9-8-2017.
 */
public class VMGConfig {


    public static String baseUrl = "http://staging.vmg.host";
    public static String placementId = "6194";
    private static final String TAG = "VMGConfig";
    // make a static instance of the Singleton
    private static VMGConfig VMGInstance = null;
    // hashmap for the key value pairs
    private static HashMap<String, Object> VMGValues = new HashMap<>();
    private static HashMap<String, Object> JSONVals = new HashMap<>();
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
    public static VMGConfig getVMGInstance(Context context) {
        if (VMGInstance == null) {
            Class ConfigClass = VMGConfig.class;
            synchronized (ConfigClass) {
                if (VMGInstance == null) {
                    VMGInstance = new VMGConfig(context);
                    getVMGObject(context);
                    VMGValues.put("topOffset", 0.6);
                    VMGValues.put("bottomOffset", 0.4);
                    VMGValues.put("slideInOnStart", true);
                    VMGValues.put("slideInOnClose", true);
                    VMGValues.put("fadeInOnStart", false);
                    VMGValues.put("fadeOutOnClose", false);
                    VMGValues.put("debug", false);
                    VMGValues.put("active", true);
                    VMGValues.put("modDate", 1503180000);
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
        if (JSONVals.isEmpty()) {
            Log.i(TAG, "There are no values");
            return null;
        } else {
            for (Map.Entry<String, Object> entry : JSONVals.entrySet()) {
                Log.i(TAG, " " + entry.getKey() + "  " + entry.getValue());
            }
            return JSONVals;
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
        if (!JSONVals.containsKey(key)) {
            Log.i(TAG, " No value found");
        } else {
            val = JSONVals.get(key);
        }
        return val;
    }

    /**
     * @param context
     */
    private static void getVMGObject(final Context context) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                VMGUrlBuilder.getConfigUrl(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject config = response.getJSONObject("config");
                    boolean slideInOnStart = config.getBoolean("slideInOnStart");
                    boolean slideInOnClose = config.getBoolean("slideInOnClose");
                    boolean fadeInOnStart = config.getBoolean("fadeInOnStart");
                    boolean fadeOutOnClose = config.getBoolean("fadeOutOnClose");
                    JSONObject meta = config.getJSONObject("meta");
                    boolean debug = meta.getBoolean("debug");
                    boolean active = meta.getBoolean("active");
                    long modDate = meta.getLong("modDate");
                    JSONObject viewable = config.getJSONObject("viewable");
                    double topOffset = viewable.getDouble("topOffset");
                    double bottomOffset = viewable.getDouble("bottomOffset");
                    JSONObject trackers = config.getJSONObject("trackers");
                    String launcher = trackers.getString("launch");
                    String jsonResponse = "";
                    jsonResponse += "slideInOnStart: " + slideInOnStart + "\n";
                    jsonResponse += "slideInOnClose:  " + slideInOnClose + "\n";
                    jsonResponse += "fadeInOnstart:  " + fadeInOnStart + "\n";
                    jsonResponse += "fadeOutOnClose: " + fadeOutOnClose + "\n\n";
                    jsonResponse += "debug: " + debug + "\n\n";
                    jsonResponse += "active: " + active + "\n\n";
                    jsonResponse += "modDate: " + modDate + "\n\n";
                    jsonResponse += "topOffset: " + topOffset + "\n\n";
                    jsonResponse += "bottomOffset: " + bottomOffset + "\n\n";
                    jsonResponse += "launcher: " + launcher + "\n\n";
                    Log.i(TAG, " " + config.getBoolean("slideInOnStart"));

                    JSONVals.put("slideInOnStart", slideInOnStart);
                    JSONVals.put("slideInOnClose", slideInOnClose);
                    JSONVals.put("fadeInOnStart", fadeInOnStart);
                    JSONVals.put("fadeOutOnClose", fadeOutOnClose);
                    JSONVals.put("debug", debug);
                    JSONVals.put("active", active);
                    JSONVals.put("modDate", modDate);
                    JSONVals.put("topOffset", topOffset);
                    JSONVals.put("bottomOffset", bottomOffset);
                    JSONVals.put("launcher", launcher);
                    for (Map.Entry<String, Object> entry : JSONVals.entrySet()) {
                        Log.i(TAG, "" + entry.getKey() + " " + entry.getValue());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
            }
        });
        // Adding request to request queue
        VMGConfig.getVMGInstance(context).addToRequestQueue(jsonObjReq);
    }
}