package com.vmg.VMGParser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
 * Created by Seyfullah Semen on 30-8-2017.
 */

public class ParseMraidCommands {

    public HashMap<String, String> parseMraidUrl(String commandUrl) {
        String command = commandUrl.substring(8);
        String commandMraid;

        HashMap<String, String> params = new HashMap<>();

        int indexQuestionMark = command.indexOf('?');
        if (indexQuestionMark != -1) {
            commandMraid = command.substring(0, indexQuestionMark);
            String paramStr = command.substring(indexQuestionMark + 1);
            String[] paramArray = paramStr.split("&");

            for (String param : paramArray) {
                indexQuestionMark = param.indexOf('=');
                String key = param.substring(0, indexQuestionMark);
                String val = param.substring(indexQuestionMark + 1);
                params.put(key, val);
            }
        } else {
            commandMraid = command;
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
        switch (command) {
            case "createCalendarEvent":
                return params.containsKey("eventJSON");
            case "open":
            case "playVideo":
            case "storePicture":
                return params.containsKey("url");
            case "setOrientationProperties":
                return params.containsKey("allowOrientationChange") &&
                        params.containsKey("forceOrientation");
            case "setResizeProperties":
                return params.containsKey("width") &&
                        params.containsKey("height") &&
                        params.containsKey("offsetX") &&
                        params.containsKey("offsetY") &&
                        params.containsKey("customClosePosition") &&
                        params.containsKey("allowOffscreen");
            case "useCustomClose":
                return params.containsKey("useCustomClose");
        }

        return true;
    }

}
