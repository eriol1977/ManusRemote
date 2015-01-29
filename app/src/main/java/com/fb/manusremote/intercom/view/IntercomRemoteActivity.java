package com.fb.manusremote.intercom.view;

import android.view.View;
import android.widget.EditText;

import com.fb.manusremote.R;
import com.fb.manusremote.intercom.model.Intercom;
import com.fb.manusremote.intercom.model.IntercomRemote;
import com.fb.manusremote.model.Validator;
import com.fb.manusremote.view.AbstractRemoteActivity;

public class IntercomRemoteActivity extends AbstractRemoteActivity {

    private EditText ringTimeoutField;

    public IntercomRemoteActivity() {
        super(R.layout.activity_intercom_remote, R.id.intercomRemoteContent,
                R.id.intercomRemoteSpinner, R.id.intercomRemoteError);
    }

    @Override
    protected void initFields() {
        ringTimeoutField = (EditText) findViewById(R.id.intercomRingTimeoutEdit);
        ringTimeoutField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateRingTimeout();
                }
            }
        });
    }

    @Override
    protected void initAdapterAndRemote() {
        adapter = (Intercom) getIntent().getSerializableExtra(IntercomConfigActivity.INTERCOM);
        if (adapter != null) {
            remote = new IntercomRemote((Intercom) adapter, this);
            remote.load();
        }
    }

    @Override
    protected void loadSpecificFields() {
        ringTimeoutField.setText(((IntercomRemote) remote).getRingTimeout());
    }

    @Override
    protected void updateRemote() {
        final String ringTimeout = ((EditText) findViewById(R.id.intercomRingTimeoutEdit)).getText().toString();
        ((IntercomRemote) remote).setRingTimeout(ringTimeout);
    }

    protected boolean validateForm() {
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
