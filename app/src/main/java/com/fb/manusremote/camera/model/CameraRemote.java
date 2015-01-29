package com.fb.manusremote.camera.model;

import com.android.volley.Response;
import com.fb.manusremote.camera.view.CameraRemoteActivity;
import com.fb.manusremote.remote.AbstractRemote;

import java.util.Map;

/**
 * Created by Francesco on 15/01/2015.
 */
public class CameraRemote extends AbstractRemote {

    public final static String MOTION_DETECTION = "md.active.enable";
    public final static String RECORD_VIDEO = "md.record.uploadftp";
    public final static String TAKE_PHOTO = "md.snapshot.enable";
    public final static String CALL_ENABLED = "md.sipphone.enable";
    public final static String CALL_NUMBER = "phone.number";

    public final static String COMMAND_GET_MOTION_DETECTION_CONFIG = "goform/motiondetect?cmd=get";

    public final static String COMMAND_GET_PHONEBOOK_CONFIG = "goform/sip?cmd=get";

    private boolean motionDetection;

    private String callNumber;

    private boolean recordVideo;

    private boolean takePhoto;

    private Map<String, String> motionDetectionConfiguration;

    private Map<String, String> phonebookConfiguration;

    public CameraRemote(final Camera camera, final CameraRemoteActivity activity) {
        super(camera, activity);
    }

    public void load() {
        doNetworkRequest(COMMAND_GET_MOTION_DETECTION_CONFIG,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        motionDetectionConfiguration = buildConfigurationMap((String) response);
                        final boolean callEnabled = getBooleanValue(motionDetectionConfiguration, CALL_ENABLED);
                        if (callEnabled) {
                            loadPhonebookConfig();
                        } else {
                            fillProperties(false);
                        }
                    }
                });
    }

    private void loadPhonebookConfig() {
        doNetworkRequest(COMMAND_GET_PHONEBOOK_CONFIG,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        phonebookConfiguration = buildConfigurationMap((String) response);
                        fillProperties(true);
                    }
                });
    }

    private void fillProperties(final boolean callEnabled) {
        motionDetection = getBooleanValue(motionDetectionConfiguration, MOTION_DETECTION);
        recordVideo = getBooleanValue(motionDetectionConfiguration, RECORD_VIDEO);
        takePhoto = getBooleanValue(motionDetectionConfiguration, TAKE_PHOTO);

        if (callEnabled)
            callNumber = phonebookConfiguration.get(CALL_NUMBER);
        else
            callNumber = "";

        activity.loadFields();
    }

    @Override
    public void save() {
        final String motionDetectionToSend = motionDetection ? "1" : "0";

        // TODO

    }

    public boolean getMotionDetection() {
        return motionDetection;
    }

    public void setMotionDetection(boolean motionDetection) {
        this.motionDetection = motionDetection;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public boolean getRecordVideo() {
        return recordVideo;
    }

    public void setRecordVideo(boolean recordVideo) {
        this.recordVideo = recordVideo;
    }

    public boolean getTakePhoto() {
        return takePhoto;
    }

    public void setTakePhoto(boolean takePhoto) {
        this.takePhoto = takePhoto;
    }

}
