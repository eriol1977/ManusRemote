package com.fb.manusremote.view;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fb.manusremote.camera.view.Cameras;
import com.fb.manusremote.R;
import com.fb.manusremote.intercom.view.Intercoms;


public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnIntercoms = (Button) findViewById(R.id.btnIntercoms);
        btnIntercoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intercomsIntent = new Intent();
                intercomsIntent.setClass(Main.this, Intercoms.class);
                startActivity(intercomsIntent);
            }
        });

        final Button btnCameras = (Button) findViewById(R.id.btnCameras);
        btnCameras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent camerasIntent = new Intent();
                camerasIntent.setClass(Main.this, Cameras.class);
                startActivity(camerasIntent);
            }
        });

        final Button btnTest = (Button) findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Intent.ACTION_MAIN );
                intent.addCategory( Intent.CATEGORY_LAUNCHER );
                intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                ComponentName cn = new ComponentName( "com.rcreations.ipcamviewer", "com.rcreations.ipcamviewer.WebCamViewerActivity" );
                intent.setComponent(cn);
                //intent.putExtra("selectView", "MATRIX_VIEW" );
                intent.putExtra("selectView", "GALLERY_VIEW" );
                intent.putExtra("selectCameraName", "Mauro" );
                startActivity( intent );
            }
        });
    }

}
