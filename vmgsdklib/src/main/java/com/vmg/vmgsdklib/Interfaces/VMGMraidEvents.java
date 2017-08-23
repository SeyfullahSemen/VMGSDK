package com.vmg.vmgsdklib.Interfaces;

/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 * <p>
 *    the mraid events that you can add to your app
 * </p>
 *
 */

/**
 * Created by Seyfullah Semen on 2-8-2017.
 */

public interface VMGMraidEvents {

    void fireReadyEvent();

    void getScreenSize();

    void isViewable();

    void getDefaultPosition();

    void getState();

    void removeEventListener();

    void fireViewableChangeEvent();



}
