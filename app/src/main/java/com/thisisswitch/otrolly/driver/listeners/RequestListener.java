package com.thisisswitch.otrolly.driver.listeners;

/**
 * Created by Office on 3/15/2016.
 */
public interface RequestListener<T> {

    void onResponse(T response);

    void onDisplayError(String errorMsg);
}
