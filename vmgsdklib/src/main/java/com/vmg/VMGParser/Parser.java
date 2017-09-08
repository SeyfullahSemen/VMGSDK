package com.vmg.VMGParser;

import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 30-8-2017.
 */

public class Parser {

    private final String TAG = "Parser";

    public Map<String, String> parseCommandUrl(String commandUrl) {

        Log.d(TAG, "parseCommandUrl " + commandUrl);


        String s = commandUrl.substring(8);

        String command;
        Map<String, String> params = new HashMap<>();


        int idx = s.indexOf('?');
        if (idx != -1) {
            command = s.substring(0, idx);
            String paramStr = s.substring(idx + 1);
            String[] paramArray = paramStr.split("&");
            for (String param : paramArray) {
                idx = param.indexOf('=');
                String key = param.substring(0, idx);
                String val = param.substring(idx + 1);
                params.put(key, val);
            }
        } else {
            command = s;
        }


        if (!isValid(command)) {
            Log.w("command ", " " + command + " is unknown");
            return null;
        }


        // Check for valid parameters for the given command.
        if (!checkParamsForCommand(command, params)) {
            Log.w("command URL ", commandUrl + " is missing parameters");
            return null;
        }


        Map<String, String> commandMap = new HashMap<String, String>();
        commandMap.put("command", command);
        commandMap.putAll(params);
        return commandMap;
    }

    private boolean isValid(String command) {
        final String[] commands = {
                "close",
                "createCalendarEvent",
                "expand",
                "open",
                "playVideo",
                "resize",
                "setOrientationProperties",
                "setResizeProperties",
                "storePicture",
                "useCustomClose"
        };
        return (Arrays.asList(commands).contains(command));
    }

    private boolean checkParamsForCommand(String command, Map<String, String> params) {
        if (command.equals("createCalendarEvent")) {
            return params.containsKey("eventJSON");
        } else if (command.equals("open") || command.equals("playVideo") || command.equals("storePicture")) {
            return params.containsKey("url");
        } else if (command.equals("setOrientationProperties")) {
            return params.containsKey("allowOrientationChange") &&
                    params.containsKey("forceOrientation");
        } else if (command.equals("setResizeProperties")) {
            return params.containsKey("width") &&
                    params.containsKey("height") &&
                    params.containsKey("offsetX") &&
                    params.containsKey("offsetY") &&
                    params.containsKey("customClosePosition") &&
                    params.containsKey("allowOffscreen");
        } else if (command.equals("useCustomClose")) {
            return params.containsKey("useCustomClose");
        }

        return true;
    }

}
