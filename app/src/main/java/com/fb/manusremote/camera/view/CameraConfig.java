package com.fb.manusremote.camera.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.fb.manusremote.R;
import com.fb.manusremote.camera.model.Camera;
import com.fb.manusremote.infra.PersistenceManager;
import com.fb.manusremote.view.VOIPConfigAbstractActivity;


public class CameraConfig extends VOIPConfigAbstractActivity {

    public static final String CAMERA = "camera";

    private Camera camera;

    private String oldName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        camera = (Camera) getIntent().getSerializableExtra(CAMERA);
        if (camera != null) {
            oldName = camera.getName();
            nameField.setText(camera.getName());
            ipField.setText(camera.getIp());
            portField.setText(camera.getPort());
            usernameField.setText(camera.getUsername());
            passwordField.setText(camera.getPassword());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_voip_config, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete_voip) {
            createDeleteDialog().show();
            return true;
        } else if (id == R.id.action_save_voip) {
            if (validateForm()) {
                final String name = nameField.getText().toString();
                final String ip = ipField.getText().toString();
                final String port = portField.getText().toString();
                final String username = usernameField.getText().toString();
                final String password = passwordField.getText().toString();
                PersistenceManager.updateCamera(oldName, name, ip, port, username, password,
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
                NavUtils.navigateUpFromSameTask(CameraConfig.this);
                return true;
            }
        } else if (id == R.id.action_config_voip) {
            final Intent intent = new Intent();
            intent.setClass(this, CameraRemote.class);
            intent.putExtra(CAMERA, camera);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Dialog createDeleteDialog() {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.delete_camera))
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final String name = nameField.getText().toString();
                        PersistenceManager.removeCamera(name, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
                        NavUtils.navigateUpFromSameTask(CameraConfig.this);
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
