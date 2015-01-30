package com.fb.manusremote.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fb.manusremote.R;

public abstract class WebviewActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        overridePendingTransition(0, 0);

        setContentView(R.layout.activity_webview);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        final ProgressBar spinner = (ProgressBar) findViewById(R.id.webSpinner);

        final WebView webView = (WebView) findViewById(R.id.webView);
        webView.setVisibility(View.INVISIBLE);

        final Activity activity = this;

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.loadUrl(getInitialURL());

        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                processAfterURLloading(view, url);

                webView.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.GONE);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, getString(R.string.page_loading_error), Toast.LENGTH_LONG).show();
                NavUtils.navigateUpFromSameTask(activity);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
    }

    protected abstract String getInitialURL();

    protected abstract void processAfterURLloading(final WebView view, final String url);

}
