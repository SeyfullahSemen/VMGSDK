package com.example.user.newapp.ConfigVMG;

import android.content.Context;

/**
 * Created by User on 21-8-2017.
 */

public class VMGUrlBuilder {

    public static String getConfigUrl (){
        String configURL = ""+VMGConfig.baseUrl+"/adServ/config/id/\\"+ VMGConfig.placementId+"" ;
        return configURL;
    }

    public static String getPlacementUrl(){
        String placementUrl = ""+VMGConfig.baseUrl+"/adServ/placement/id\\"+VMGConfig.placementId+"";
        return  placementUrl;
    }


}
