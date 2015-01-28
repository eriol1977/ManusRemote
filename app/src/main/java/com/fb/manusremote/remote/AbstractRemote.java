package com.fb.manusremote.remote;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.fb.manusremote.R;
import com.fb.manusremote.model.VOIPAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Francesco on 28/01/2015.
 */
public abstract class AbstractRemote {

    private final static String TRUE_VALUE = "1";
    private final static String FALSE_VALUE = "0";

    private final Context context;

    public AbstractRemote(final Context context) {
        this.context = context;
    }

    public abstract void load();

    public abstract void save();

    protected boolean isNetworkAvailable() {
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

    protected Map<String, String> buildConfigurationMap(final String httpResponse) {
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

    protected boolean getBooleanValue(final Map<String, String> fieldMap, final String field) {
        final String stringValue = fieldMap.get(field);
        if (stringValue == null) {
            Toast.makeText(context, context.getString(R.string.parameter_reading_error) + field, Toast.LENGTH_SHORT).show();
            return false;
        }
        return stringValue.equals(TRUE_VALUE);
    }

    protected String buildURL(final VOIPAdapter adapter, final String command) {
        final StringBuilder sb = new StringBuilder();
        sb.append("http://").append(adapter.getIp()).append(":").append(adapter.getPort());
        sb.append("/").append(command);
        return sb.toString();
    }
}
