package com.fb.manusremote.view;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

import com.fb.manusremote.R;
import com.fb.manusremote.model.Validator;

/**
 * Created by Francesco on 15/01/2015.
 */
public class VOIPConfigAbstractActivity extends ActionBarActivity {

    protected EditText nameField;
    protected EditText ipField;
    protected EditText portField;
    protected EditText usernameField;
    protected EditText passwordField;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(0,0);
        setContentView(R.layout.activity_voip_config);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        nameField = (EditText) findViewById(R.id.intercomNameEdit);
        nameField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateName();
                }
            }
        });

        ipField = (EditText) findViewById(R.id.intercomIpEdit);
        ipField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateIp();
                }
            }
        });

        portField = (EditText) findViewById(R.id.intercomPortEdit);
        portField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validatePort();
                }
            }
        });

        usernameField = (EditText) findViewById(R.id.intercomUsernameEdit);
        usernameField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateUsername();
                }
            }
        });

        passwordField = (EditText) findViewById(R.id.intercomPasswordEdit);
        passwordField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validatePassword();
                }
            }
        });
    }

    protected boolean validateForm() {
        return validateName() && validateIp() && validatePort() && validateUsername() && validatePassword();
    }

    private boolean validatePassword() {
        String message;
        final String password = passwordField.getText().toString();
        message = Validator.validatePassword(password, this);
        if (!message.isEmpty()) {
            passwordField.setError(message);
            return false;
        } else {
            passwordField.setError(null);
        }
        return true;
    }

    private boolean validateUsername() {
        String message;
        final String username = usernameField.getText().toString();
        message = Validator.validateUsername(username, this);
        if (!message.isEmpty()) {
            usernameField.setError(message);
            return false;
        } else {
            usernameField.setError(null);
        }
        return true;
    }

    private boolean validatePort() {
        String message;
        final String port = portField.getText().toString();
        message = Validator.validatePort(port, this);
        if (!message.isEmpty()) {
            portField.setError(message);
            return false;
        } else {
            portField.setError(null);
        }
        return true;
    }

    private boolean validateIp() {
        String message;
        final String ip = ipField.getText().toString();
        message = Validator.validateIp(ip, this);
        if (!message.isEmpty()) {
            ipField.setError(message);
            return false;
        } else {
            ipField.setError(null);
        }
        return true;
    }

    private boolean validateName() {
        final String name = nameField.getText().toString();
        String message = Validator.validateName(name, this);
        if (!message.isEmpty()) {
            nameField.setError(message);
            return false;
        } else {
            nameField.setError(null);
        }
        return true;
    }
}
