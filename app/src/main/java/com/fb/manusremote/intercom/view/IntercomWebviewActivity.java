package com.fb.manusremote.intercom.view;

import android.os.Bundle;
import android.webkit.WebView;

import com.fb.manusremote.intercom.model.Intercom;
import com.fb.manusremote.view.WebviewActivity;

public class IntercomWebviewActivity extends WebviewActivity {

    private final static String URL = "http://%s:%s/cgi-bin/config_a1";

    private final static String AUTOMATIC_LOGIN_JAVASCRIPT = "javascript: var useless = document.loginForm.P2.value = '%s'; document.loginForm.submit();";

    private Intercom intercom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        intercom = (Intercom) getIntent().getSerializableExtra(IntercomConfigActivity.INTERCOM);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getInitialURL() {
        return String.format(URL, intercom.getIp(), intercom.getPort());
    }

    @Override
    protected void processAfterURLloading(WebView view, String url) {
        view.loadUrl(String.format(AUTOMATIC_LOGIN_JAVASCRIPT, intercom.getPassword()));
    }

}
