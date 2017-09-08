package com.vmg.MobileInfo;

import android.annotation.SuppressLint;

import android.content.Context;

import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;


import android.text.format.Formatter;
import android.util.Log;

import com.vmg.vmgsdklib.BuildConfig;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

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
     * this method checks whether the internet is turned on in the android manifest.xml
     * if that is the state than we can check the ip address of the user else we just return a null value
     *
     * @return the mobile ip address of the user
     */
    private String getMobileIpAddress() {
        String wifiPermission = "android.permission.ACCESS_NETWORK_STATE";
        int wifi = context.checkCallingOrSelfPermission(wifiPermission);
        if (wifi == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission network state is enabled");
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                     en.hasMoreElements(); ) {
                    NetworkInterface networkinterface = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = networkinterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            return inetAddress.getHostAddress().toString();
                        }
                    }
                }
            } catch (Exception ex) {
                Log.e("Current IP", ex.toString());
            }
        }

        return null;
    }// end of getMobileIpAddress();

    /**
     * this method checks the Ip address of the WIFI if the wifi  network state is turned on in the androidManifest.xml
     * If so than it gets the IP address of the WIFI
     *
     * @return wifi Ip Address
     */
    @SuppressLint("WifiManagerPotentialLeak")
    @SuppressWarnings("deprecation")
    private String getWifiIpAddress() {
        String wifiPermission = "android.permission.ACCESS_NETWORK_STATE";
        int wifi = context.checkCallingOrSelfPermission(wifiPermission);
        if (wifi == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission network state is enabled");
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);


            String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

            return ip;
        } else {
            return null;
        }

    }// end of getWifiIpAddress()

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

    /**
     * this will return the version of  the library
     *
     * @return SDK version
     */
    private String getOurSdkVersion() {

        String version = "Version name: " + BuildConfig.VERSION_NAME + " version code: " + BuildConfig.VERSION_CODE;
        return version;
    }// end of getOurSdkVersion();

    /**
     * @return the information of the mobile device of the user
     */
    @Override
    public String toString() {
        return " \nandroid mobile version:  "
                + getAndroidVersion() + "\n SDK version:  " + getOurSdkVersion() + "\n"
                + (getWifiIpAddress().equals("0.0.0.0") ? " Mobile Ip: " + getMobileIpAddress() : " Wifi Ip: " + getWifiIpAddress());
    }// end of toString()

}
