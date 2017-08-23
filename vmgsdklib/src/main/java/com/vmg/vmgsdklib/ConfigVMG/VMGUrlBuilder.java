package com.vmg.vmgsdklib.ConfigVMG;

/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 * <p>
 *     this is our URL builder this is a class where we define our URL with the help of our
 *     config class
 * </p>
 */


/**
 * Created by Seyfullah Semen on 21-8-2017.
 */

public class VMGUrlBuilder {

    public static String getConfigUrl() {
        String configURL = "" + VMGConfig.baseUrl + "/adServ/config/id/\\" + VMGConfig.placementId + "";
        return configURL;
    }// end of getConfigUrl()

    public static String getPlacementUrl() {
       String  placementUrl = "" + VMGConfig.baseUrl + "/adServ/placement/id\\" + VMGConfig.placementId + "";
        return placementUrl;
    }// end of getPlacementUrl();


}
