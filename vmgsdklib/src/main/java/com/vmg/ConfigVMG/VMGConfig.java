package com.vmg.ConfigVMG;


import android.content.Context;
import android.util.SparseArray;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vmg.LoggerPack.VMGLogs;

import org.json.JSONException;
import org.json.JSONObject;


public final class VMGConfig {

    private static final String TAG = "VMGConfig";
    private static final ThreadLocal<VMGConfig> VMGInstance = new ThreadLocal<>();
    private static SparseArray<Object> JSONVals = new SparseArray<>();
    private Context context;
    private RequestQueue requestQueue;

    private VMGConfig(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    /**
     * @param context the context of the specific page will be given
     * @param appId   the id will be given of the ad that needs to load
     */
    public static void loadConfig(Context context, int appId) {
        getVMGInstance(context);
        getVMGObject(context, appId);
    }

    /**
     * create a lazy initialization, this version is thread safe
     *
     * @return VMGInstance
     */
    public static VMGConfig getVMGInstance(Context context) {
        if (VMGInstance.get() == null) {
            try {
                Class ConfigClass = VMGConfig.class;
                synchronized (ConfigClass) {
                    if (VMGInstance.get() == null) {
                        VMGInstance.set(new VMGConfig(context));
                    }
                }
            } catch (Exception ex) {
                VMGLogs.fatal("Something went wrong in VMGConfig method getVMGInstance :   " + ex.getMessage());
            }
        }
        return VMGInstance.get();
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    private <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    /**
     * get a specific value out of the hashmap
     *
     * @param key the key that needs to get retrieved
     * @return the value that is asked for
     */
    public Object retrieveSpecific(int key, String value) {
        Object val = null;
        try {
            if (JSONVals.size() == 0) {
                return 0;
            } else {
                val = JSONVals.get(key, value);
            }
        } catch (Exception ex) {
            VMGLogs.fatal("something went wrong in retrieving the a value Method retrieveSpecific:  " + ex.getMessage());
        }
        return val;
    }

    /**
     * @param context the context of the page will be given
     */
    private static void getVMGObject(final Context context, int appId) {

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                VMGUrlBuilder.getConfigUrl(appId), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VMGLogs.StandardLog(" " + response);
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
                        JSONVals.put(1, slideInOnStart);
                    }

                    if (config.has("slideInOnClose")) {
                        slideInOnClose = config.getBoolean("slideInOnClose");
                        JSONVals.put(2, slideInOnClose);
                    }

                    if (config.has("fadeInOnStart")) {
                        fadeInOnStart = config.getBoolean("fadeInOnStart");
                        JSONVals.put(3, fadeInOnStart);
                    }

                    if (config.has("fadeOutOnClose")) {
                        fadeOutOnClose = config.getBoolean("fadeOutOnClose");
                        JSONVals.put(4, fadeOutOnClose);
                    }

                    JSONObject meta = config.getJSONObject("meta");
                    if (meta.has("debug")) {
                        debug = meta.getBoolean("debug");
                        JSONVals.put(5, debug);
                    }

                    if (meta.has("active")) {
                        active = meta.getBoolean("active");
                        JSONVals.put(6, active);
                    }

                    if (meta.has("modDate")) {
                        modDate = meta.getLong("modDate");
                        JSONVals.put(7, modDate);
                    }

                    JSONObject viewable = config.getJSONObject("viewable");
                    if (viewable.has("topOffset")) {
                        topOffset = viewable.getDouble("topOffset");
                        JSONVals.put(8, topOffset);
                    }

                    if (viewable.has("bottomOffset")) {
                        bottomOffset = viewable.getDouble("bottomOffset");
                        JSONVals.put(9, bottomOffset);
                    }

                    JSONObject trackers = config.getJSONObject("trackers");
                    if (trackers.has("launch")) {
                        launcher = trackers.getString("launch");
                        JSONVals.put(10, launcher);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                VMGLogs.fatal("Someting went wrong in the method getVMGObject:  " + error.getMessage());
            }
        });

        VMGConfig.getVMGInstance(context).addToRequestQueue(jsonObjReq);
    }
}