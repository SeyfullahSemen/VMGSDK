package com.vmg.BaseUtils;

import java.util.Arrays;
import java.util.List;

final class VMGResizeProperties {


    private static final int CUSTOM_CLOSE_POSITION_TOP_RIGHT = 0;

    int width;
    public int height;
    int offsetX;
    int offsetY;
    int customClosePosition;

    boolean allowOffscreen;

    VMGResizeProperties() {
        this(0, 0, 0, 0, CUSTOM_CLOSE_POSITION_TOP_RIGHT, true);
    }

    private VMGResizeProperties(int width, int height, int offsetX, int offsetY, int customClosePosition, boolean allowOffscreen) {
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.customClosePosition = customClosePosition;
        this.allowOffscreen = allowOffscreen;
    }

    static int customClosePositionFromString(String name) {
        final List<String> positions = Arrays.asList(
                "top-left",
                "top-center",
                "top-right",
                "center",
                "bottom-left",
                "bottom-center",
                "bottom-right"
        );

        int positionIndex = positions.indexOf(name);

        if (positionIndex != -1) {
            return positionIndex;
        }
        return CUSTOM_CLOSE_POSITION_TOP_RIGHT;
    }


}
