package com.vmg.MobileInfo;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;

import com.vmg.LoggerPack.VMGLogs;
import com.vmg.vmgsdklib.BuildConfig;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.TimeZone;

public final class UserInfoMobile {
    private Context context;
    private static final String TAG = "UserInfoMobile";

    /**
     * @param context this is the context of the activity
     */
    public UserInfoMobile(Context context) {
        this.context = context;
    }

    /**
     * If app has permissions, gets IP address Mobile
     *
     * @return the mobile ip address of the user
     */
    private String getMobileIpAddress() {
        String wifiPermission = "android.permission.ACCESS_NETWORK_STATE";
        int wifi = context.checkCallingOrSelfPermission(wifiPermission);
        if (wifi == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission network state is enabled");
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                    NetworkInterface networkinterface = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = networkinterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            return inetAddress.getHostAddress();
                        }
                    }
                }
            } catch (Exception ex) {
                VMGLogs.debug("Current IP " + ex.toString());
            }
        }

        return null;
    }

    /**
     * If app has permissions, gets IP address WIFI
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

            return Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        } else {
            return null;
        }
    }


    /**
     * @return the build version of the mobile used
     */
    @SuppressLint("NewApi")
    private static String getAndroidVersion() {
        String buildVersion = "Release version: " + Build.VERSION.RELEASE
                + "  build Version:  " + android.os.Build.VERSION.SDK_INT;
        return buildVersion;
    }

    /**
     * this will return the version of  the library
     *
     * @return SDK version
     */
    private static String getOurSdkVersion() {
        String version = "Version name: " + BuildConfig.VERSION_NAME + " version code: " + BuildConfig.VERSION_CODE;
        return version;
    }

    /**
     * @return our library name
     */
    private String getPackageName() {
        return context.getPackageName();
    }

    /**
     * @return the model
     */
    private static String getModel() {
        return Build.MODEL;
    }

    /**
     * @return the brand
     */
    private static String getBrand() {
        return Build.BRAND;
    }

    /**
     * @return the type
     */
    private static String getType() {
        return Build.TYPE;
    }

    /**
     * @return the time
     */
    private static Date getTime() {
        return Calendar.getInstance().getTime();
    }

    /**
     * @return the hardware
     */
    private static String getHardware() {
        return Build.HARDWARE;
    }

    /**
     * @return the timezone that the mobile is used
     */
    private static String getTimeZone() {
        return TimeZone.getDefault().getID();
    }

    /**
     * @return the language that is used
     */
    private static String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * @return the height and width of device
     */
    private int getDeviceWidth() {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * @return the height of the device
     */
    private int getDeviceHeight() {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    /**
     * @return the information of the mobile device of the user
     */
    public String mobileInfo() {
        return " \nandroid mobile version:  "
                + getAndroidVersion() + "\n SDK version:  " + getOurSdkVersion() + "\n"
                + (getWifiIpAddress().equals("0.0.0.0") ? " Mobile Ip: " + getMobileIpAddress() : " Wifi Ip: " + getWifiIpAddress())
                + "\n" + " App name: " + getPackageName()
                + "\n" + " brand of device: " + getBrand()
                + "\n" + " Hardware Info: " + getHardware()
                + "\n" + " Device Language: " + getLanguage()
                + "\n" + " Model of device: " + getModel()
                + "\n" + " Type of the device: " + getType()
                + "\n" + " Time zone of device: " + getTimeZone()
                + "\n" + "  Time: " + getTime()
                + "\n" + " Device  width : " + getDeviceWidth()
                + "\n" + "  Device height : " + getDeviceHeight();
    }

}
