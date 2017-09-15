package com.vmg.ConfigVMG;
/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 * <p>
 * this is our URL builder this is a class where we define our URL with the help of our
 * config class
 * </p>
 */

/**
 * Created by Seyfullah on 21-8-2017.
 */
public class VMGUrlBuilder {

    public static String getConfigUrl(int appId) {
        String configURL = "" + VMGConfig.baseUrl + "/adServ/config/id/\\" + appId + "";
        return configURL;
    }

    public static String getPlacementUrl(int placementId) {
        String placementUrl = "" + VMGConfig.baseUrl + "/adServ/placement/id\\" + placementId + "";
        return placementUrl;
    }
}
