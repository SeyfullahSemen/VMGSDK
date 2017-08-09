package com.example.user.newapp.ConfigVMG;

import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Seyfullah Semen on 9-8-2017.
 */

public class VMGConfig {
    private static VMGConfig VMGInstance = null;
    private static HashMap<String, Object> VMGValues = new HashMap<>();

    private VMGConfig() {
    }

    /**
     * create a lazy lazy initialization, this version is thread safe
     *
     * @return
     */
    public static VMGConfig geVMGInstance() {
        if (VMGInstance == null) {
            Class ConfigClass = VMGConfig.class;
            synchronized (ConfigClass) {
                if (VMGInstance == null) {
                    VMGInstance = new VMGConfig();
                    VMGValues.put("Percentage_up", 0.6);
                    VMGValues.put("Percentage_under", 0.4);

                }
            }
        }
        return VMGInstance;
    }

    public double getPercentageUp() {

        double percentage_up = (double)VMGValues.get("Percentage_up");
        Log.i("up ", " " + percentage_up);
        return percentage_up;
    }

    public double getPercentageDown() {

        double percentage_down = (double)VMGValues.get("Percentage_under");
        Log.i("down ", " " + percentage_down);
        return percentage_down;
    }

    public void showValues() {
        Log.i("Config ", " we are Singleton");


        for (Map.Entry<String, Object> entry : VMGValues.entrySet()) {
            Log.i("Values", " " + entry.getKey() + "    " + entry.getValue());
        }


    }


}
