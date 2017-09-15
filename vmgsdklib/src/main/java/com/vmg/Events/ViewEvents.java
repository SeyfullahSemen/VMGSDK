package com.vmg.Events;

import com.vmg.BaseUtils.VMGBase;

/**
 * Created by Seyfullah Semen on 30-8-2017.
 */

public interface ViewEvents {

    void mraidViewClose(VMGBase vmgView);

    boolean mraidViewResize(VMGBase vmgView, int width, int height, int offsetX, int offsetY);

}
