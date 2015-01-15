package com.fb.manusremote.intercom;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.fb.manusremote.PersistenceManager;
import com.fb.manusremote.R;

public class IntercomCreate extends IntercomAbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intercom_config);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_intercom_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save_intercom) {
            if(validateForm()) {
                final String name = ((EditText) findViewById(R.id.intercomNameEdit)).getText().toString();
                final String ip = ((EditText) findViewById(R.id.intercomIpEdit)).getText().toString();
                final String port = ((EditText) findViewById(R.id.intercomPortEdit)).getText().toString();
                final String username = ((EditText) findViewById(R.id.intercomUsernameEdit)).getText().toString();
                final String password = ((EditText) findViewById(R.id.intercomPasswordEdit)).getText().toString();

                PersistenceManager.addIntercom(name, ip, port, username, password,
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

                NavUtils.navigateUpFromSameTask(this);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
