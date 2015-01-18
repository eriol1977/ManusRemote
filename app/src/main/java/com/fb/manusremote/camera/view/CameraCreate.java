package com.fb.manusremote.camera.view;

import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.fb.manusremote.R;
import com.fb.manusremote.infra.PersistenceManager;
import com.fb.manusremote.view.VOIPConfigAbstractActivity;

public class CameraCreate extends VOIPConfigAbstractActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_voip_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save_voip) {
            if (validateForm()) {
                final String name = nameField.getText().toString();
                final String ip = ipField.getText().toString();
                final String port = portField.getText().toString();
                final String username = usernameField.getText().toString();
                final String password = passwordField.getText().toString();

                PersistenceManager.addCamera(name, ip, port, username, password,
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

                NavUtils.navigateUpFromSameTask(this);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
