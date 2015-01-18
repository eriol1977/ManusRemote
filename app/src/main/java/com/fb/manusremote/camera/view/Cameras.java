package com.fb.manusremote.camera.view;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.fb.manusremote.R;
import com.fb.manusremote.infra.PersistenceManager;
import com.fb.manusremote.model.VOIPAdapter;
import com.fb.manusremote.view.VOIPListArrayAdapterItem;

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
        getMenuInflater().inflate(R.menu.menu_camera_list, menu);
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
        } else if (id == R.id.action_view_camera) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName cn = new ComponentName("com.rcreations.ipcamviewer", "com.rcreations.ipcamviewer.WebCamViewerActivity");
            intent.setComponent(cn);
            intent.putExtra("selectView", "MATRIX_VIEW");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
