package com.thisisswitch.otrolly.driver.listeners;
import com.akexorcist.googledirection.model.Direction;

/**
 * Created by Office on 3/20/2016.
 */
public interface OnDirectionCallback {

    void onDirectionSuccess(Direction direction, String rawBody);

    void onDirectionFailure(Throwable t);
}