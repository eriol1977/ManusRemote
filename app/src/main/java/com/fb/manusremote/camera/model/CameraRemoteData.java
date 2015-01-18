package com.fb.manusremote.camera.model;

/**
 * Created by Francesco on 15/01/2015.
 */
public class CameraRemoteData {

    private final String ringTimeout;

    public CameraRemoteData(String ringTimeout) {
        this.ringTimeout = ringTimeout;
    }

    public String getRingTimeout() {
        return ringTimeout;
    }
}
