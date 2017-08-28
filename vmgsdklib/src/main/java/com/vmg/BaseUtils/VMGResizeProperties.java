package com.vmg.BaseUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by User on 25-8-2017.
 */

public final class VMGResizeProperties {

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


}
