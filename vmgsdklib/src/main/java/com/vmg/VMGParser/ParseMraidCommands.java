package com.vmg.VMGParser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Seyfullah Semen on 30-8-2017.
 */

public class ParseMraidCommands {

    public Map<String, String> parseMraidUrl(String commandUrl) {
        String s = commandUrl.substring(8);
        String commandMraid;

        Map<String, String> params = new HashMap<>();

        int indexQuestionMark = s.indexOf('?');
        if (indexQuestionMark != -1) {
            commandMraid = s.substring(0, indexQuestionMark);
            String paramStr = s.substring(indexQuestionMark + 1);
            String[] paramArray = paramStr.split("&");

            for (String param : paramArray) {
                indexQuestionMark = param.indexOf('=');
                String key = param.substring(0, indexQuestionMark);
                String val = param.substring(indexQuestionMark + 1);
                params.put(key, val);
            }
        } else {
            commandMraid = s;
        }

        if (!checkCommands(commandMraid)) {
            return null;
        }

        if (!checkTheParameters(commandMraid, params)) {
            return null;
        }

        HashMap<String, String> MraidCommands = new HashMap<>();
        MraidCommands.put("command", commandMraid);
        MraidCommands.putAll(params);
        return MraidCommands;
    }

    private boolean checkCommands(String command) {
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
        return Arrays.asList(commands).contains(command);
    }

    private boolean checkTheParameters(String command, Map<String, String> params) {
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
