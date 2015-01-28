package com.fb.manusremote.intercom.view;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.fb.manusremote.R;
import com.fb.manusremote.intercom.model.Intercom;
import com.fb.manusremote.intercom.model.IntercomRemote;
import com.fb.manusremote.model.Validator;

public class IntercomRemoteActivity extends ActionBarActivity {

    private Intercom intercom;

    private IntercomRemote remoteData;

    private EditText ringTimeoutField;

    private ProgressBar spinner;

    private RelativeLayout content;

    private boolean errorLoading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(0,0);
        setContentView(R.layout.activity_intercom_remote);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        ringTimeoutField = (EditText) findViewById(R.id.intercomRingTimeoutEdit);
        ringTimeoutField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateRingTimeout();
                }
            }
        });

        content = (RelativeLayout) findViewById(R.id.intercomRemoteContent);
        content.setVisibility(View.INVISIBLE);
        spinner = (ProgressBar) findViewById(R.id.intercomRemoteSpinner);

        intercom = (Intercom) getIntent().getSerializableExtra(IntercomConfigActivity.INTERCOM);
        if (intercom != null) {
            remoteData = new IntercomRemote(intercom, this);
            remoteData.load();
        }
    }

    public void loadFields(final IntercomRemote remoteData) {
        spinner.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
        errorLoading = false;

        ringTimeoutField.setText(remoteData.getRingTimeout());
    }

    public void showErrorMessage() {
        spinner.setVisibility(View.GONE);
        findViewById(R.id.intercomRemoteError).setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_voip_remote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save_voip) {

            if (!errorLoading) {
                NavUtils.navigateUpFromSameTask(this);
                return true;
            }

            if (validateForm()) {
                final String ringTimeout = ((EditText) findViewById(R.id.intercomRingTimeoutEdit)).getText().toString();
                remoteData.setRingTimeout(ringTimeout);
                remoteData.save();

                NavUtils.navigateUpFromSameTask(this);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean validateForm() {
        return validateRingTimeout();
    }

    private boolean validateRingTimeout() {
        final String ringTimeout = ringTimeoutField.getText().toString();
        String message = Validator.validateRingTimeout(ringTimeout, this);
        if (!message.isEmpty()) {
            ringTimeoutField.setError(message);
            return false;
        } else {
            ringTimeoutField.setError(null);
        }
        return true;
    }
}
