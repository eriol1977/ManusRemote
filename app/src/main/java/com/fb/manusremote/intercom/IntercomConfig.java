package com.fb.manusremote.intercom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.fb.manusremote.PersistenceManager;
import com.fb.manusremote.R;
import com.fb.manusremote.model.Intercom;


public class IntercomConfig extends IntercomAbstractActivity {

    public static final String INTERCOM = "intercom";

    private Intercom intercom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intercom_config);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        intercom = (Intercom) getIntent().getSerializableExtra(INTERCOM);
        if (intercom != null) {
            final EditText intercomNameEdit = (EditText) findViewById(R.id.intercomNameEdit);
            intercomNameEdit.setText(intercom.getName());

            final EditText intercomIpEdit = (EditText) findViewById(R.id.intercomIpEdit);
            intercomIpEdit.setText(intercom.getIp());

            final EditText intercomPortEdit = (EditText) findViewById(R.id.intercomPortEdit);
            intercomPortEdit.setText(intercom.getPort());

            final EditText intercomUsernameEdit = (EditText) findViewById(R.id.intercomUsernameEdit);
            intercomUsernameEdit.setText(intercom.getUsername());

            final EditText intercomPasswordEdit = (EditText) findViewById(R.id.intercomPasswordEdit);
            intercomPasswordEdit.setText(intercom.getPassword());
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
                final String name = ((EditText) findViewById(R.id.intercomNameEdit)).getText().toString();
                final String ip = ((EditText) findViewById(R.id.intercomIpEdit)).getText().toString();
                final String port = ((EditText) findViewById(R.id.intercomPortEdit)).getText().toString();
                final String username = ((EditText) findViewById(R.id.intercomUsernameEdit)).getText().toString();
                final String password = ((EditText) findViewById(R.id.intercomPasswordEdit)).getText().toString();
                PersistenceManager.updateIntercom(name, ip, port, username, password,
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
                        final String name = ((EditText) findViewById(R.id.intercomNameEdit)).getText().toString();
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
