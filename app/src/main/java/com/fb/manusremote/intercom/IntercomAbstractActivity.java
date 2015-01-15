package com.fb.manusremote.intercom;

import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;

import com.fb.manusremote.R;
import com.fb.manusremote.model.Validator;

/**
 * Created by Francesco on 15/01/2015.
 */
public class IntercomAbstractActivity extends ActionBarActivity {

    protected boolean validateForm()
    {
        final EditText nameField = (EditText) findViewById(R.id.intercomNameEdit);
        final String name = nameField.getText().toString();
        String message = Validator.validateName(name, this);
        if(!message.isEmpty())
        {
            nameField.setError(message);
            return false;
        }else{
            nameField.setError(null);
        }

        final EditText ipField = (EditText) findViewById(R.id.intercomIpEdit);
        final String ip = ipField.getText().toString();
        message = Validator.validateIp(ip, this);
        if(!message.isEmpty())
        {
            ipField.setError(message);
            return false;
        }else{
            ipField.setError(null);
        }

        final EditText portField = (EditText) findViewById(R.id.intercomPortEdit);
        final String port = portField.getText().toString();
        message = Validator.validatePort(port, this);
        if(!message.isEmpty())
        {
            portField.setError(message);
            return false;
        }else{
            portField.setError(null);
        }

        final EditText usernameField = (EditText) findViewById(R.id.intercomUsernameEdit);
        final String username = usernameField.getText().toString();
        message = Validator.validateUsername(username, this);
        if(!message.isEmpty())
        {
            usernameField.setError(message);
            return false;
        }else{
            usernameField.setError(null);
        }

        final EditText passwordField = (EditText) findViewById(R.id.intercomPasswordEdit);
        final String password = passwordField.getText().toString();
        message = Validator.validatePassword(password, this);
        if(!message.isEmpty())
        {
            passwordField.setError(message);
            return false;
        }else{
            passwordField.setError(null);
        }

        return true;
    }
}
