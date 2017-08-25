package com.example.user.newapp.BaseUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by User on 25-8-2017.
 */

public final class VMGResizeProperties {
    public static final int CUSTOM_CLOSE_POSITION_TOP_LEFT      = 0;
    public static final int CUSTOM_CLOSE_POSITION_TOP_CENTER    = 1;
    public static final int CUSTOM_CLOSE_POSITION_TOP_RIGHT     = 2;
    public static final int CUSTOM_CLOSE_POSITION_CENTER        = 3;
    public static final int CUSTOM_CLOSE_POSITION_BOTTOM_LEFT   = 4;
    public static final int CUSTOM_CLOSE_POSITION_BOTTOM_CENTER = 5;
    public static final int CUSTOM_CLOSE_POSITION_BOTTOM_RIGHT  = 6;

    public int width;
    public int height;
    public int offsetX;
    public int offsetY;


    public VMGResizeProperties() {
        this(0, 0, 0, 0);
    }

    public VMGResizeProperties(
            int width,
            int height,
            int offsetX,
            int offsetY
            ) {
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;

    }

    static public int customClosePositionFromString(String name) {
        final List<String> names = Arrays.asList(
                "top-left",
                "top-center",
                "top-right",
                "center",
                "bottom-left",
                "bottom-center",
                "bottom-right"
        );
        int idx = names.indexOf(name);
        if (idx != -1) {
            return idx;
        }
        // Use top-right for the default value.
        return CUSTOM_CLOSE_POSITION_TOP_RIGHT;
    }
}
