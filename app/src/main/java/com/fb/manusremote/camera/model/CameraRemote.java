package com.fb.manusremote.camera.model;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.fb.manusremote.R;
import com.fb.manusremote.camera.view.CameraRemoteActivity;
import com.fb.manusremote.remote.AuthRequest;
import com.fb.manusremote.remote.AbstractRemote;

import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by Francesco on 15/01/2015.
 */
public class CameraRemote extends AbstractRemote {

    public final static String MOTION_DETECTION = "md.active.enable";
    public final static String RECORD_VIDEO = "md.record.uploadftp";
    public final static String TAKE_PHOTO = "md.snapshot.enable";
    public final static String CALL_ENABLED = "md.sipphone.enable";

    public final static String COMMAND_GET_MOTION_DETECTION_CONFIG = "goform/motiondetect?cmd=get";

    private final CameraRemoteActivity activity;

    private final Camera camera;

    private boolean motionDetection;

    private String callNumber;

    private boolean recordVideo;

    private boolean takePhoto;

    public CameraRemote(final Camera camera, final CameraRemoteActivity activity) {
        super(activity);
        this.activity = activity;
        this.camera = camera;
    }

    public void load() {
        if (!isNetworkAvailable()) {
            Toast.makeText(activity, activity.getString(R.string.internet_connection_required), LENGTH_LONG).show();
            activity.showErrorMessage();
            return;
        }

        final RequestQueue queue = Volley.newRequestQueue(activity);

        final String url = buildURL(camera, COMMAND_GET_MOTION_DETECTION_CONFIG);

        // Request a string response from the provided URL.
        final AuthRequest stringRequest = new AuthRequest(camera.getUsername(), camera.getPassword(), Request.Method.GET, url,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        final Map<String, String> motionDetectionConfiguration =
                                buildConfigurationMap((String) response);
                        motionDetection = getBooleanValue(motionDetectionConfiguration, MOTION_DETECTION);
                        // TODO: ricavare il numero di telefono
                        final boolean callEnabled = getBooleanValue(motionDetectionConfiguration, CALL_ENABLED);
                        callNumber = callEnabled ? "555" : "";
                        recordVideo = getBooleanValue(motionDetectionConfiguration, RECORD_VIDEO);
                        takePhoto = getBooleanValue(motionDetectionConfiguration, TAKE_PHOTO);
                        activity.loadFields(CameraRemote.this);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, activity.getString(R.string.remote_config_loading_error), Toast.LENGTH_SHORT).show();
                activity.showErrorMessage();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    public void save() {
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
