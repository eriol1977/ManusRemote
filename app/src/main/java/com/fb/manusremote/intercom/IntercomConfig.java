package com.fb.manusremote.intercom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.fb.manusremote.PersistenceManager;
import com.fb.manusremote.R;
import com.fb.manusremote.model.Intercom;


public class IntercomConfig extends IntercomAbstractActivity {

    public static final String INTERCOM = "intercom";

    private Intercom intercom;

    private String oldName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intercom = (Intercom) getIntent().getSerializableExtra(INTERCOM);
        if (intercom != null) {
            oldName = intercom.getName();
            nameField.setText(intercom.getName());
            ipField.setText(intercom.getIp());
            portField.setText(intercom.getPort());
            usernameField.setText(intercom.getUsername());
            passwordField.setText(intercom.getPassword());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_intercom_config, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete_intercom) {
            createDeleteDialog().show();
            return true;
        } else if (id == R.id.action_save_intercom) {
            if (validateForm()) {
                final String name = nameField.getText().toString();
                final String ip = ipField.getText().toString();
                final String port = portField.getText().toString();
                final String username = usernameField.getText().toString();
                final String password = passwordField.getText().toString();
                PersistenceManager.updateIntercom(oldName,name, ip, port, username, password,
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
                NavUtils.navigateUpFromSameTask(IntercomConfig.this);
                return true;
            }
        } else if (id == R.id.action_config_intercom) {
            final Intent intent = new Intent();
            intent.setClass(this, IntercomRemote.class);
            intent.putExtra(INTERCOM, intercom);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Dialog createDeleteDialog() {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.delete_intercom))
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final String name = nameField.getText().toString();
                        PersistenceManager.removeIntercom(name, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
                        NavUtils.navigateUpFromSameTask(IntercomConfig.this);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // do nothing
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
