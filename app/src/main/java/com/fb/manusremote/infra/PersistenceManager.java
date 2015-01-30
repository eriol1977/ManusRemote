package com.fb.manusremote.infra;

import android.content.SharedPreferences;

import com.fb.manusremote.camera.model.Camera;
import com.fb.manusremote.intercom.model.Intercom;
import com.fb.manusremote.model.VOIPAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francesco on 15/01/2015.
 */
public class PersistenceManager {

    public final static String INTERCOMS = "intercoms";

    public final static String CAMERAS = "cameras";

    private final static String NAME_SEPARATOR = ",";

    private final static String INTERCOM_PREFIX = "ic_";

    private final static String CAMERA_PREFIX = "ca_";

    private final static String CLONE_SUFFIX = "_clone";

    //// INTERCOMS

    public static List<VOIPAdapter> loadIntercoms(final SharedPreferences preferences) {
        final List<VOIPAdapter> intercoms = new ArrayList<>();
        final String intercomsString = preferences.getString(INTERCOMS, null);
        if (intercomsString != null) {
            final String[] intercomNames = intercomsString.split(NAME_SEPARATOR);
            String loadedString = null;
            for (final String name : intercomNames) {
                loadedString = preferences.getString(name, null);
                if (loadedString != null)
                    intercoms.add(new Intercom(loadedString));
            }
        }
        return intercoms;
    }

    public static void cloneIntercom(final Intercom intercom, final SharedPreferences preferences) {
        addIntercom(intercom.getName() + CLONE_SUFFIX, intercom.getIp(), intercom.getPort(),
                intercom.getUsername(), intercom.getPassword(), preferences);
    }

    public static void addIntercom(String name, String ip, String port, String username, String password, final SharedPreferences preferences) {
        final String saveName = INTERCOM_PREFIX + name;
        final SharedPreferences.Editor editor = preferences.edit();
        String intercomsString = preferences.getString(INTERCOMS, "");
        if (!intercomsString.isEmpty())
            intercomsString += NAME_SEPARATOR;
        intercomsString += saveName;
        editor.putString(INTERCOMS, intercomsString);
        editor.putString(saveName, new Intercom(name, ip, port, username, password).toSaveFormat());
        editor.commit();
    }

    public static void removeIntercom(final String name, final SharedPreferences preferences) {
        final String saveName = INTERCOM_PREFIX + name;
        final SharedPreferences.Editor editor = preferences.edit();
        String intercomsString = preferences.getString(INTERCOMS, "");
        final String[] intercomNames = intercomsString.split(NAME_SEPARATOR);
        String newIntercomsString = "";
        for (final String intercomName : intercomNames) {
            if (!intercomName.equals(saveName))
                newIntercomsString += intercomName + NAME_SEPARATOR;
        }
        if (!newIntercomsString.isEmpty())
            newIntercomsString = newIntercomsString.substring(0, newIntercomsString.length() - 1);
        editor.putString(INTERCOMS, newIntercomsString);
        editor.remove(saveName);
        editor.commit();
    }

    public static void updateIntercom(String oldName, String name, String ip, String port,
                                      String username, String password, SharedPreferences preferences) {
        final SharedPreferences.Editor editor = preferences.edit();
        if (oldName != name) {
            removeIntercom(oldName, preferences);
            addIntercom(name, ip, port, username, password, preferences);
        } else {
            editor.putString(INTERCOM_PREFIX + name, new Intercom(name, ip, port, username, password).toSaveFormat());
            editor.commit();
        }
    }

    //// CAMERAS

    public static List<VOIPAdapter> loadCameras(final SharedPreferences preferences) {
        final List<VOIPAdapter> cameras = new ArrayList<>();
        final String camerasString = preferences.getString(CAMERAS, null);
        if (camerasString != null) {
            final String[] camerasNames = camerasString.split(NAME_SEPARATOR);
            String loadedString = null;
            for (final String name : camerasNames) {
                loadedString = preferences.getString(name, null);
                if (loadedString != null)
                    cameras.add(new Camera(loadedString));
            }
        }
        return cameras;
    }

    public static void cloneCamera(final Camera camera, final SharedPreferences preferences) {
        addCamera(camera.getName() + CLONE_SUFFIX, camera.getIp(), camera.getPort(),
                camera.getUsername(), camera.getPassword(), preferences);
    }

    public static void addCamera(String name, String ip, String port, String username, String password, final SharedPreferences preferences) {
        final String saveName = CAMERA_PREFIX + name;
        final SharedPreferences.Editor editor = preferences.edit();
        String camerasString = preferences.getString(CAMERAS, "");
        if (!camerasString.isEmpty())
            camerasString += NAME_SEPARATOR;
        camerasString += saveName;
        editor.putString(CAMERAS, camerasString);
        editor.putString(saveName, new Camera(name, ip, port, username, password).toSaveFormat());
        editor.commit();
    }

    public static void removeCamera(final String name, final SharedPreferences preferences) {
        final String saveName = CAMERA_PREFIX + name;
        final SharedPreferences.Editor editor = preferences.edit();
        String camerasString = preferences.getString(CAMERAS, "");
        final String[] cameraNames = camerasString.split(NAME_SEPARATOR);
        String newCamerasString = "";
        for (final String cameraName : cameraNames) {
            if (!cameraName.equals(saveName))
                newCamerasString += cameraName + NAME_SEPARATOR;
        }
        if (!newCamerasString.isEmpty())
            newCamerasString = newCamerasString.substring(0, newCamerasString.length() - 1);
        editor.putString(CAMERAS, newCamerasString);
        editor.remove(saveName);
        editor.commit();
    }

    public static void updateCamera(String oldName, String name, String ip, String port,
                                    String username, String password, SharedPreferences preferences) {
        final SharedPreferences.Editor editor = preferences.edit();
        if (oldName != name) {
            removeCamera(oldName, preferences);
            addCamera(name, ip, port, username, password, preferences);
        } else {
            editor.putString(CAMERA_PREFIX + name, new Camera(name, ip, port, username, password).toSaveFormat());
            editor.commit();
        }
    }
}
