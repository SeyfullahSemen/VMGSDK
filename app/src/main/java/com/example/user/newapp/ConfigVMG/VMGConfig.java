package com.example.user.newapp.ConfigVMG;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

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


    private VMGConfig() {
    }

    /**
     * create a lazy initialization, this version is thread safe
     *
     * @return
     */
    public static VMGConfig geVMGInstance() {
        if (VMGInstance == null) {
            Class ConfigClass = VMGConfig.class;
            synchronized (ConfigClass) {
                if (VMGInstance == null) {
                    VMGInstance = new VMGConfig();
                    VMGValues.put("Percentage_up", 0.5);
                    VMGValues.put("Percentage_under", 0.5);
                    VMGValues.put("Placement_id", "6178");

                }
            }
        }
        return VMGInstance;
    }

    /**
     *  set a new value in the hashmap
     * @param key
     * @param value
     * @param <T>
     * @param <E>
     */
    public   < T extends  String , E > void setValue(T key , E value){
        if (VMGValues.containsKey(key)){
            VMGValues.remove(key);
            VMGValues.put(key,value);
        }else {
            VMGValues.put(key, value);
        }

    }

    /**
     *  get the values of the hashmap
     * @return
     */
    public  HashMap<String, Object> getValues(){
        if (VMGValues.isEmpty()){
            Log.i(TAG, "There are no values");
            return null;
        }else {
            for (Map.Entry<String, Object> entry : VMGValues.entrySet()) {
                Log.i(TAG, " " + entry.getKey() + "  " + entry.getValue());
            }
            return VMGValues;
        }

    }

    /**
     *  get a specific value out of the hashmap
     * @param key
     * @return
     */
    public Object retrieveSpecific (Object key){
        Object val = null;
        if (!VMGValues.containsKey(key)){
            Log.i(TAG," No value found");
        }else {
            val = VMGValues.get(key);
        }
       return val;
    }




}
