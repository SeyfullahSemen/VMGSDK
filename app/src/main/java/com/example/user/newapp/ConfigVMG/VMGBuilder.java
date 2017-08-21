package com.example.user.newapp.ConfigVMG;
/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 * <p>
 */


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.user.newapp.BaseFrag.VMGBaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Seyfullah Semen on 11-8-2017.
 */

public class VMGBuilder {
    private static String TAG = "VMGBuilder";
    private String placementId;
    private static String url = "http://staging.vmg.host/adServ/config/id/6194";
    private HashMap<String, JSONObject> map = new HashMap<>();

    public VMGBuilder(Context context, String placementId) {




    }



}



