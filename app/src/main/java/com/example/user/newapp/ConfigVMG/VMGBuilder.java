package com.example.user.newapp.ConfigVMG;
/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 * <p>
 */



import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.user.newapp.BaseFrag.VMGBaseFragment;

import org.json.JSONObject;

/**
 * Created by Seyfullah Semen on 11-8-2017.
 */

public class VMGBuilder  {
    private static String TAG = "VMGBuilder";
    private String placementId;
    private String url = "http://staging.vmg.host/adServ/config/id/6164";

    public VMGBuilder(Context context ) {


        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Response: " , response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
        // Get a RequestQueue
        RequestQueue queue = VMGConfig.geVMGInstance(context).
                getRequestQueue();


// Add a request (in this example, called stringRequest) to your RequestQueue.
        VMGConfig.geVMGInstance(context).addToRequestQueue(jsObjRequest);

    }

    public void setPlacementId(String placementId){
        this.placementId = placementId;
    }

    public String getPlacementId(){
        return  this.placementId;
    }


}
