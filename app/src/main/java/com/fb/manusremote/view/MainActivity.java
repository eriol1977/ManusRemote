package com.fb.manusremote.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fb.manusremote.R;
import com.fb.manusremote.camera.view.CamerasActivity;
import com.fb.manusremote.intercom.view.IntercomsActivity;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(0,0);
        setContentView(R.layout.activity_main);

        final Button btnIntercoms = (Button) findViewById(R.id.btnIntercoms);
        btnIntercoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intercomsIntent = new Intent();
                intercomsIntent.setClass(MainActivity.this, IntercomsActivity.class);
                startActivity(intercomsIntent);
            }
        });

        final Button btnCameras = (Button) findViewById(R.id.btnCameras);
        btnCameras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent camerasIntent = new Intent();
                camerasIntent.setClass(MainActivity.this, CamerasActivity.class);
                startActivity(camerasIntent);
            }
        });

    }

}
