package com.vmg.ConfigVMG;

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

public class VMGConfig {
    public static String baseUrl = "http://staging.vmg.host";
    public static String placementId = "6194";
    private static final String TAG = "VMGConfig";
    private static VMGConfig VMGInstance = null;
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
     * @return VMGInstance
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
            return 0;
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

                try {
                    boolean slideInOnStart;
                    boolean slideInOnClose;
                    boolean fadeInOnStart;
                    boolean fadeOutOnClose;

                    boolean debug;
                    boolean active;
                    long modDate;

                    double topOffset;
                    double bottomOffset;

                    String launcher;

                    JSONObject config = response.getJSONObject("config");
                    if (config.has("slideInOnStart")) {
                        slideInOnStart = config.getBoolean("slideInOnStart");
                        JSONVals.put("slideInOnStart", slideInOnStart);
                    }

                    if (config.has("slideInOnClose")) {
                        slideInOnClose = config.getBoolean("slideInOnClose");
                        JSONVals.put("slideInOnClose", slideInOnClose);
                    }

                    if (config.has("fadeInOnStart")) {
                        fadeInOnStart = config.getBoolean("fadeInOnStart");
                        JSONVals.put("fadeInOnStart", fadeInOnStart);
                    }

                    if (config.has("fadeOutOnClose")) {
                        fadeOutOnClose = config.getBoolean("fadeOutOnClose");
                        JSONVals.put("fadeOutOnClose", fadeOutOnClose);
                    }

                    JSONObject meta = config.getJSONObject("meta");
                    if (meta.has("debug")) {
                        debug = meta.getBoolean("debug");
                        JSONVals.put("debug", debug);
                    }

                    if (meta.has("active")) {
                        active = meta.getBoolean("active");
                        JSONVals.put("active", active);
                    }

                    if (meta.has("modDate")) {
                        modDate = meta.getLong("modDate");
                        JSONVals.put("modDate", modDate);
                    }

                    JSONObject viewable = config.getJSONObject("viewable");
                    if (viewable.has("topOffset")) {
                        topOffset = viewable.getDouble("topOffset");
                        JSONVals.put("topOffset", topOffset);
                    }

                    if (viewable.has("bottomOffset")) {
                        bottomOffset = viewable.getDouble("bottomOffset");
                        JSONVals.put("bottomOffset", bottomOffset);
                    }

                    JSONObject trackers = config.getJSONObject("trackers");
                    if (trackers.has("launch")) {
                        launcher = trackers.getString("launch");
                        JSONVals.put("launcher", launcher);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        VMGConfig.getVMGInstance(context).addToRequestQueue(jsonObjReq);
    }
}