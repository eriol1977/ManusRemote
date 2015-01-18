package com.fb.manusremote.camera.view;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.fb.manusremote.R;
import com.fb.manusremote.camera.model.Camera;
import com.fb.manusremote.infra.PersistenceManager;
import com.fb.manusremote.intercom.view.IntercomCreate;
import com.fb.manusremote.model.VOIPAdapter;
import com.fb.manusremote.view.VOIPListArrayAdapterItem;
import com.fb.manusremote.intercom.view.IntercomListItemListener;

import java.util.List;


public class Cameras extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cameras);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        final ListView listCameras = (ListView) findViewById(R.id.listCameras);
        final List<VOIPAdapter> cameras = PersistenceManager.loadCameras(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        listCameras.setAdapter(new VOIPListArrayAdapterItem(this, R.layout.voip_list_row, cameras));
        listCameras.setOnItemClickListener(new CameraListItemListener());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_voip_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_voip) {
            final Intent intent = new Intent();
            intent.setClass(this, CameraCreate.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
