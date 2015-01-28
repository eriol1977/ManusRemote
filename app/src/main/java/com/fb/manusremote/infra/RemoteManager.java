package com.fb.manusremote.infra;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.fb.manusremote.R;
import com.fb.manusremote.camera.model.Camera;
import com.fb.manusremote.camera.model.CameraRemoteData;
import com.fb.manusremote.camera.view.CameraRemote;
import com.fb.manusremote.intercom.model.Intercom;
import com.fb.manusremote.intercom.model.IntercomRemoteData;
import com.fb.manusremote.model.VOIPAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Francesco on 15/01/2015.
 */
public class RemoteManager {

    public final static String TRUE_VALUE = "1";
    public final static String FALSE_VALUE = "0";

    public IntercomRemoteData loadIntercomRemoteData(final Intercom intercom) {
        // TODO
        return new IntercomRemoteData("60");
    }

    public void saveIntercomRemoteData(final String ringTimeout) {
        // TODO
    }

    public void loadCameraRemoteData(final Camera camera, final CameraRemote activity) {
        new CameraRemoteData(camera, activity).loadFields();
    }

    public void saveCameraRemoteData(final boolean motionDetection, final String callNumber,
                                     final boolean recordVideo, final boolean takePhoto) {
        // TODO
    }

    public static boolean isNetworkAvailable(final Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public static Map<String, String> buildConfigurationMap(final String httpResponse) {
        final Map<String, String> config = new HashMap<>();
        String line;
        String[] lineConfig;
        Scanner scanner = new Scanner(httpResponse);
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            lineConfig = line.split("=");
            if (lineConfig.length == 2)
                config.put(lineConfig[0], lineConfig[1]);
        }
        scanner.close();
        return config;
    }

    public static boolean getBooleanValue(final Map<String, String> fieldMap, final String field, final Context context) {
        final String stringValue = fieldMap.get(field);
        if (stringValue == null) {
            Toast.makeText(context, context.getString(R.string.parameter_reading_error) + field, Toast.LENGTH_SHORT).show();
            return false;
        }
        return stringValue.equals(RemoteManager.TRUE_VALUE);
    }

    public static String buildURL(final VOIPAdapter adapter, final String command) {
        final StringBuilder sb = new StringBuilder();
        sb.append("http://").append(adapter.getIp()).append(":").append(adapter.getPort());
        sb.append("/").append(command);
        return sb.toString();
    }
}
