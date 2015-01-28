package com.fb.manusremote.camera.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.fb.manusremote.camera.model.Camera;

/**
 * Created by Francesco on 14/01/2015.
 */
public class CameraListItemListener implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        final Context context = view.getContext();

        final Camera camera = (Camera) parent.getItemAtPosition(position);

        final Intent intent = new Intent();
        intent.setClass(context, CameraConfigActivity.class);
        intent.putExtra(CameraConfigActivity.CAMERA, camera);
        context.startActivity(intent);
    }

}
