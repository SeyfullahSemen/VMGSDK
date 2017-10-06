package com.vmg.ConfigVMG;

import android.annotation.SuppressLint;
import android.content.Context;

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

import java.util.HashMap;


public class VMGConfig {
    static String baseUrl = "http://staging.vmg.host";
    private static final String TAG = "VMGConfig";
    @SuppressLint("StaticFieldLeak")
    private static VMGConfig VMGInstance = null;
    private static HashMap<String, Object> JSONVals = new HashMap<>();
    @SuppressLint("StaticFieldLeak")
    private static Context context;
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
        if (VMGInstance == null) {
            try {
                Class ConfigClass = VMGConfig.class;
                synchronized (ConfigClass) {
                    if (VMGInstance == null) {
                        VMGInstance = new VMGConfig(context);
                    }
                }
            } catch (Exception ex) {
                VMGLogs.fatal("Something went wrong in VMGConfig method getVMGInstance :   " + ex.getMessage());
            }
        }
        return VMGInstance;
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
    public Object retrieveSpecific(Object key) {
        Object val = null;
        try {
            if (!JSONVals.containsKey(key)) {
                return 0;
            } else {
                val = JSONVals.get(key);
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
                VMGLogs.fatal("Someting went wrong in the method getVMGObject:  " + error.getMessage());
            }
        });

        VMGConfig.getVMGInstance(context).addToRequestQueue(jsonObjReq);
    }
}