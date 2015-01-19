package com.fb.manusremote.camera.model;

/**
 * Created by Francesco on 15/01/2015.
 */
public class CameraRemoteData {

    private final boolean motionDetection;

    private final String callNumber;

    private final boolean recordVideo;

    private final boolean takePhoto;

    public CameraRemoteData(final boolean motionDetection, final String callNumber, final boolean recordVideo, final boolean takePhoto) {
        this.motionDetection = motionDetection;
        this.callNumber = callNumber;
        this.recordVideo = recordVideo;
        this.takePhoto = takePhoto;
    }

    public boolean getMotionDetection() {
        return motionDetection;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public boolean getRecordVideo() {
        return recordVideo;
    }

    public boolean getTakePhoto() {
        return takePhoto;
    }
}
