package com.vmg.MobileInfo;


import android.Manifest;
import android.annotation.SuppressLint;

import android.content.Context;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Build;


import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;

import com.vmg.vmgsdklib.BuildConfig;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public final class UserInfoMobile {
    private static Context context;
    private static final String TAG = "UserInfoMobile";

    /**
     * @param context
     */
    public UserInfoMobile(Context context) {
        this.context = context;
    }

    /**
     * If app has permissions, gets IP address Mobile
     *
     * @return the mobile ip address of the user
     */
    private static String getMobileIpAddress() {
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
                            return inetAddress.getHostAddress().toString();
                        }
                    }
                }
            } catch (Exception ex) {
                Log.e("Current IP", ex.toString());
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
    private static String getWifiIpAddress() {
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
    }

    /**
     * @return the location of the user
     * LocationLatLngTextView
     */
    @SuppressLint("NewApi")
    private static String getLocationOfUser() {
        String accesFineLocation = "android.permission.ACCESS_FINE_LOCATION";
        String accesCoarseLocation = "android.permission.ACCESS_COARSE_LOCATION";
        int fineLocation = context.checkCallingOrSelfPermission(accesFineLocation);
        int coarseLocation = context.checkCallingOrSelfPermission(accesCoarseLocation);
        if (fineLocation == PackageManager.PERMISSION_GRANTED && coarseLocation == PackageManager.PERMISSION_GRANTED) {
            LocationManager lm = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
            return "" + lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } else {
            return "NOTHING " + fineLocation + " " + coarseLocation;
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
    private static String getPackageName() {
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
        Date currentTime;
        return currentTime = Calendar.getInstance().getTime();
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
    private static int getDeviceWidth() {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        return width;
    }

    /**
     * @return the height of the device
     */
    private static int getDeviceHeight() {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;
        return height;
    }

    /**
     * @return the information of the mobile device of the user
     */
    public static String mobileInfo() {
        return " \nandroid mobile version:  "
                + getAndroidVersion() + "\n SDK version:  " + getOurSdkVersion() + "\n"
                + (getWifiIpAddress().equals("0.0.0.0") ? " Mobile Ip: " + getMobileIpAddress() : " Wifi Ip: " + getWifiIpAddress())
                + "\n" + " Location:  " + getLocationOfUser()
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
