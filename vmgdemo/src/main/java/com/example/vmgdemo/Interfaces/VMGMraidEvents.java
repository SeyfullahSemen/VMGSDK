package com.example.vmgdemo.Interfaces;

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
