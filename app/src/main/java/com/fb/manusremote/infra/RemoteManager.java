package com.fb.manusremote.infra;

import com.fb.manusremote.camera.model.Camera;
import com.fb.manusremote.camera.model.CameraRemoteData;
import com.fb.manusremote.intercom.model.Intercom;
import com.fb.manusremote.intercom.model.IntercomRemoteData;

/**
 * Created by Francesco on 15/01/2015.
 */
public class RemoteManager {

    public static IntercomRemoteData loadIntercomRemoteData(final Intercom intercom) {
        // TODO
        return new IntercomRemoteData("60");
    }

    public static void saveIntercomRemoteData(final String ringTimeout) {
        // TODO
    }

    public static CameraRemoteData loadCameraRemoteData(final Camera camera) {
        // TODO
        return new CameraRemoteData("60");
    }

    public static void saveCameraRemoteData(final String ringTimeout) {
        // TODO
    }
}
