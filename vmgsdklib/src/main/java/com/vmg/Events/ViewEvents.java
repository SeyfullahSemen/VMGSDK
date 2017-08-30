package com.vmg.Events;

import com.vmg.BaseUtils.VMGBase;

/**
 * Created by User on 30-8-2017.
 */

public interface ViewEvents {


     void mraidViewLoaded(VMGBase mraidView);

    void mraidViewExpand(VMGBase mraidView);

     void mraidViewClose(VMGBase mraidView);

     boolean mraidViewResize(VMGBase mraidView, int width, int height, int offsetX, int offsetY);

}
