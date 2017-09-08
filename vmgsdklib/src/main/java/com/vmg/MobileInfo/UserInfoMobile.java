package com.vmg.MobileInfo;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import android.support.v4.os.BuildCompat;
import android.text.TextUtils;
import android.util.Log;

import com.vmg.vmgsdklib.BuildConfig;

/**
 * this will be a singleton class in which we can determine the state and the version etc
 * about the used mobile phone.
 * Created by Seyfullah Semen on 4-9-2017.
 */

public final class UserInfoMobile {
    private static Context context;
    private static final String TAG = "UserInfoMobile";
    private static UserInfoMobile userInstance = null;

    private UserInfoMobile(Context context) {
        this.context = context;
    }

    public static UserInfoMobile getUserInfoMobile(Context context) {
        if (userInstance == null) {
            Class userInfo = UserInfoMobile.class;
            synchronized (userInfo) {
                userInstance = new UserInfoMobile(context);

            }
        }

        return userInstance;
    }

    /**
     * this method will check the network state and check if the permission is
     * granted in the app if that is the case than we can get the information of the network state
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.N)
    @SuppressLint("WifiManagerPotentialLeak")

    private boolean getWifiInformation() {
        String wifiPermission = "android.permission.ACCESS_NETWORK_STATE";
        int wifi = context.checkCallingOrSelfPermission(wifiPermission);
        if (wifi == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission network state is enabled");
            String networkState = null;
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            if (networkInfo.isConnected()) {
                final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                if (connectionInfo != null && !TextUtils.isEmpty(connectionInfo.getSSID())) {
                    networkState = connectionInfo.getSSID() + " Ip adress :  " + connectionInfo.getIpAddress();
                }
                Log.i(TAG, "  " + networkState);
            }
            return true;
        } else {
            return false;
        }

    }// end of getWifiInformation(Context context)

    /**
     * @return the build version of the mobile used
     */
    @SuppressLint("NewApi")
    private String getAndroidVersion() {
        String buildVersion = "Release version: " + Build.VERSION.RELEASE
                + "  build Version:  " + android.os.Build.VERSION.SDK_INT;
        Log.i(TAG, buildVersion);
        return buildVersion;
    }// end of getAndroidVersion();

    private String getOurSdkVersion() {

        String version = "Version name: " + BuildConfig.VERSION_NAME + " version code: " + BuildConfig.VERSION_CODE;
        return version;
    }

    @Override
    public String toString() {
        return "Network info:  " + getWifiInformation() + " \n\nandroid mobile version:  "
                + getAndroidVersion() + "\n \nSDK version:  " + getOurSdkVersion();
    }

}
