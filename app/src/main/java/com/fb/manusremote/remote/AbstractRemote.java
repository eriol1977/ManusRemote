package com.fb.manusremote.remote;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.fb.manusremote.R;
import com.fb.manusremote.model.VOIPAdapter;
import com.fb.manusremote.view.AbstractRemoteActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by Francesco on 28/01/2015.
 */
public abstract class AbstractRemote {

    private final static String TRUE_VALUE = "1";
    private final static String FALSE_VALUE = "0";

    protected final AbstractRemoteActivity activity;

    private final VOIPAdapter adapter;

    public AbstractRemote(final VOIPAdapter adapter, final AbstractRemoteActivity activity) {
        this.adapter = adapter;
        this.activity = activity;
    }

    public abstract void load();

    public abstract void save();

    protected boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                activity.getSystemService(Context.CONNECTIVITY_SERVICE);
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
            Toast.makeText(activity, activity.getString(R.string.parameter_reading_error) + field, Toast.LENGTH_SHORT).show();
            return false;
        }
        return stringValue.equals(TRUE_VALUE);
    }

    protected String buildURL(final String command) {
        final StringBuilder sb = new StringBuilder();
        sb.append("http://").append(adapter.getIp()).append(":").append(adapter.getPort());
        sb.append("/").append(command);
        return sb.toString();
    }

    protected void doNetworkRequest(final String command, final Response.Listener listener) {
        if (!isNetworkAvailable()) {
            Toast.makeText(activity, activity.getString(R.string.internet_connection_required), LENGTH_LONG).show();
            activity.showErrorMessage();
            return;
        }

        final RequestQueue queue = Volley.newRequestQueue(activity);

        final String url = buildURL(command);

        // Request a string response from the provided URL.
        final AuthRequest stringRequest = new AuthRequest(this, activity,
                adapter.getUsername(), adapter.getPassword(), Request.Method.GET, url,
                listener);
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
