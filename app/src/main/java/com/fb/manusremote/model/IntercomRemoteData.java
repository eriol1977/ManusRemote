package com.fb.manusremote.model;

/**
 * Created by Francesco on 15/01/2015.
 */
public class IntercomRemoteData {

    private final String ringTimeout;

    public IntercomRemoteData(String ringTimeout) {
        this.ringTimeout = ringTimeout;
    }

    public String getRingTimeout() {
        return ringTimeout;
    }
}
