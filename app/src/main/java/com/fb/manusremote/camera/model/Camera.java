package com.fb.manusremote.camera.model;

import com.fb.manusremote.model.AdapterKind;
import com.fb.manusremote.model.VOIPAdapter;

/**
 * Created by Francesco on 14/01/2015.
 */
public class Camera extends VOIPAdapter {

    public Camera(String name, String ip, String port, String username, String password) {
        super(name, ip, port, username, password);
    }

    public Camera(final String loadedString) {
        super(loadedString);
    }

    @Override
    public AdapterKind getKind() {
        return AdapterKind.CAMERA;
    }

}
