package com.fb.manusremote.camera.model;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.fb.manusremote.R;
import com.fb.manusremote.camera.view.CameraRemote;
import com.fb.manusremote.infra.AuthRequest;
import com.fb.manusremote.infra.RemoteManager;

import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by Francesco on 15/01/2015.
 */
public class CameraRemoteData {

    public final static String MOTION_DETECTION = "md.active.enable";
    public final static String RECORD_VIDEO = "md.record.uploadftp";
    public final static String TAKE_PHOTO = "md.snapshot.enable";
    public final static String CALL_ENABLED = "md.sipphone.enable";

    public final static String COMMAND_GET_MOTION_DETECTION_CONFIG = "goform/motiondetect?cmd=get";

    private final CameraRemote activity;

    private final Camera camera;

    private boolean motionDetection;

    private String callNumber;

    private boolean recordVideo;

    private boolean takePhoto;

    public CameraRemoteData(final Camera camera, final CameraRemote activity) {
        this.activity = activity;
        this.camera = camera;
    }

    public void loadFields() {
        if (!RemoteManager.isNetworkAvailable(activity)) {
            Toast.makeText(activity, activity.getString(R.string.internet_connection_required), LENGTH_LONG).show();
            activity.showErrorMessage();
            return;
        }

        final RequestQueue queue = Volley.newRequestQueue(activity);

        final String url = RemoteManager.buildURL(camera, COMMAND_GET_MOTION_DETECTION_CONFIG);

        // Request a string response from the provided URL.
        final AuthRequest stringRequest = new AuthRequest(camera.getUsername(), camera.getPassword(), Request.Method.GET, url,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        final Map<String, String> motionDetectionConfiguration =
                                RemoteManager.buildConfigurationMap((String) response);
                        motionDetection = RemoteManager.getBooleanValue(motionDetectionConfiguration, MOTION_DETECTION, activity);
                        // TODO: ricavare il numero di telefono
                        final boolean callEnabled = RemoteManager.getBooleanValue(motionDetectionConfiguration, CALL_ENABLED, activity);
                        callNumber = callEnabled ? "555" : "";
                        recordVideo = RemoteManager.getBooleanValue(motionDetectionConfiguration, RECORD_VIDEO, activity);
                        takePhoto = RemoteManager.getBooleanValue(motionDetectionConfiguration, TAKE_PHOTO, activity);
                        activity.loadFields(CameraRemoteData.this);
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
