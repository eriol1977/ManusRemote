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

    public final static String COMMAND_SET_MOTION_DETECTION_CONFIG = "goform/motiondetect?cmd=set&md.active.enable=%s&md.record.uploadftp=%s&md.snapshot.enable=%s&md.sipphone.enable=%s";

    public final static String COMMAND_REMOVE_PHONE_NUMBER = "goform/sip?cmd=remove&phone.index=1";

    public final static String COMMAND_ADD_PHONE_NUMBER = "goform/sip?cmd=add&phone.name=VoiceAlarm&phone.number=%s";

    private boolean motionDetection;

    private boolean callEnabled;

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
                        loadPhonebookConfig();
                    }
                });
    }

    private void loadPhonebookConfig() {
        doNetworkRequest(COMMAND_GET_PHONEBOOK_CONFIG,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        phonebookConfiguration = buildConfigurationMap((String) response);
                        fillProperties();
                    }
                });
    }

    private void fillProperties() {
        motionDetection = getBooleanValue(motionDetectionConfiguration, MOTION_DETECTION);
        recordVideo = getBooleanValue(motionDetectionConfiguration, RECORD_VIDEO);
        takePhoto = getBooleanValue(motionDetectionConfiguration, TAKE_PHOTO);
        callEnabled = getBooleanValue(motionDetectionConfiguration, CALL_ENABLED);
        callNumber = phonebookConfiguration.get(CALL_NUMBER);

        activity.loadFields();
    }

    @Override
    public void save() {
        final String motionDetectionToSend = motionDetection ? "1" : "0";
        final String recordVideoToSend = recordVideo ? "1" : "0";
        final String takePhotoToSend = takePhoto ? "1" : "0";
        final String callEnabledToSend = callEnabled ? "1" : "0";

        // salva os dados do motion detection
        doNetworkRequest(String.format(COMMAND_SET_MOTION_DETECTION_CONFIG, motionDetectionToSend,
                        recordVideoToSend, takePhotoToSend, callEnabledToSend),
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        // salva também o número de telefone, se o voice alarm estiver ativado
                        if (callEnabled) {
                            savePhoneNumber();
                        } else {
                            showSuccess();
                        }
                    }
                });

    }

    /**
     * Não havendo "set", mas somente "add" e "remove" de números de telefone na SIP,
     * este método remove o primeiro número da lista e adiciona um novo com o nome 'VoiceAlarm'
     */
    private void savePhoneNumber() {
        doNetworkRequest(COMMAND_REMOVE_PHONE_NUMBER,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        doNetworkRequest(String.format(COMMAND_ADD_PHONE_NUMBER, callNumber),
                                new Response.Listener() {
                                    @Override
                                    public void onResponse(Object response) {
                                        showSuccess();
                                    }
                                });
                    }
                });
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

    public boolean getCallEnabled() {
        return callEnabled;
    }

    public void setCallEnabled(boolean callEnabled) {
        this.callEnabled = callEnabled;
    }
}
