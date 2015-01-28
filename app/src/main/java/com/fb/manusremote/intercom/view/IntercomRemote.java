package com.fb.manusremote.intercom.view;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.fb.manusremote.R;
import com.fb.manusremote.infra.RemoteManager;
import com.fb.manusremote.intercom.model.Intercom;
import com.fb.manusremote.intercom.model.IntercomRemoteData;
import com.fb.manusremote.model.Validator;

// TODO varie correzioni basate sull'implementazione di CameraRemote
public class IntercomRemote extends ActionBarActivity {

    private Intercom intercom;
    private EditText ringTimeoutField;

    private RemoteManager remoteManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intercom_remote);

        remoteManager = new RemoteManager();

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

        intercom = (Intercom) getIntent().getSerializableExtra(IntercomConfig.INTERCOM);
        if (intercom != null) {
            final IntercomRemoteData remoteData = remoteManager.loadIntercomRemoteData(intercom);
            ringTimeoutField.setText(remoteData.getRingTimeout());
        }
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
            if (validateForm()) {
                final String ringTimeout = ((EditText) findViewById(R.id.intercomRingTimeoutEdit)).getText().toString();
                remoteManager.saveIntercomRemoteData(ringTimeout);
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
