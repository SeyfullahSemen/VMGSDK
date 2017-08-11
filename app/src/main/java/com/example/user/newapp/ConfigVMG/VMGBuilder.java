package com.example.user.newapp.ConfigVMG;

import android.util.Log;

/**
 * Created by User on 11-8-2017.
 */

public class VMGBuilder {
    private static String TAG  =  "VMGBuilder";
    private String placementId;
    private VMGConfig config;

    public VMGBuilder(String placementId){
        this.placementId = placementId;
        config = VMGConfig.geVMGInstance();
        config.setValue("test",0.6);

        Log.i("vals",""+config.getValues());



        Log.i(TAG,""+config.retrieveSpecific("test"));


    }
}
