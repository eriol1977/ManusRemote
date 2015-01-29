package com.fb.manusremote.remote;

import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fb.manusremote.R;
import com.fb.manusremote.view.AbstractRemoteActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Francesco on 28/01/2015.
 */
public class AuthRequest extends StringRequest {

    private final AbstractRemote remote;

    private final AbstractRemoteActivity activity;

    private final String username;

    private final String password;

    public AuthRequest(final AbstractRemote remote, final AbstractRemoteActivity activity, final String username, final String password, int method, String url, final Response.Listener listener) {
        super(method, url, listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, activity.getString(R.string.remote_config_loading_error), Toast.LENGTH_SHORT).show();
                        activity.showErrorMessage();
                    }
                });
        this.remote = remote;
        this.activity = activity;
        this.username = username;
        this.password = password;
    }

    public AuthRequest(final AbstractRemote remote, final AbstractRemoteActivity activity, final String username, final String password, String url, final Response.Listener listener) {
        super(url, listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity, activity.getString(R.string.remote_config_loading_error), Toast.LENGTH_SHORT).show();
                        activity.showErrorMessage();
                    }
                });
        this.remote = remote;
        this.activity = activity;
        this.username = username;
        this.password = password;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return createBasicAuthHeader(username, password);
    }

    Map<String, String> createBasicAuthHeader(String username, String password) {
        Map<String, String> headerMap = new HashMap<String, String>();

        String credentials = username + ":" + password;
        String encodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        headerMap.put("Authorization", "Basic " + encodedCredentials);

        return headerMap;
    }

    private Response.ErrorListener getErrorResponseListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, activity.getString(R.string.remote_config_loading_error), Toast.LENGTH_SHORT).show();
                activity.showErrorMessage();
            }
        };
    }
}