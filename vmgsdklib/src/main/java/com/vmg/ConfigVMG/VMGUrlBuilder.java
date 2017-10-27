package com.vmg.ConfigVMG;
/*
  Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
  <p>
  this is our URL builder this is a class where we define our URL with the help of our
  config class
  </p>
 */

/*
 * Created by Seyfullah Semen on 21-8-2017.
 */
public class VMGUrlBuilder {
    private  static String baseUrl = "https://vmg.host";
    static String getConfigUrl(int appId) {
        return "" + baseUrl + "/adServ/config/id/\\" + appId + "";
    }

    public static String getPlacementUrl(int placementId) {
        return "" + baseUrl + "/adServ/placement/id\\" + placementId + "";
    }
}
