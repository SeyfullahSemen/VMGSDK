package com.example.user.newapp.ConfigVMG;
/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 * <p>
 */



import android.content.Context;

import com.example.user.newapp.BaseFrag.VMGBaseFragment;

/**
 * Created by Seyfullah Semen on 11-8-2017.
 */

public class VMGBuilder  {
    private static String TAG = "VMGBuilder";
    private String placementId;


    public VMGBuilder(Context context , String placementId) {
        this.placementId = placementId;
        VMGConfig.geVMGInstance();

    }


}
