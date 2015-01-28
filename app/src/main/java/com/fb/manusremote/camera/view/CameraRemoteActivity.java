package com.fb.manusremote.camera.view;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.fb.manusremote.R;
import com.fb.manusremote.camera.model.Camera;
import com.fb.manusremote.camera.model.CameraRemote;
import com.fb.manusremote.model.Validator;

public class CameraRemoteActivity extends ActionBarActivity {

    private Camera camera;

    private CameraRemote cameraRemote;

    private RadioButton motionDetectionYes;

    private RadioButton motionDetectionNo;

    private CheckBox callCheckbox;

    private EditText callNumber;

    private CheckBox recordVideo;

    private CheckBox takePhoto;

    private ProgressBar spinner;

    private RelativeLayout content;

    private boolean errorLoading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(0,0);
        setContentView(R.layout.activity_camera_remote);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        content = (RelativeLayout) findViewById(R.id.cameraRemoteContent);
        content.setVisibility(View.INVISIBLE);

        motionDetectionYes = (RadioButton) findViewById(R.id.cameraMotionDetectionRadioYes);
        motionDetectionNo = (RadioButton) findViewById(R.id.cameraMotionDetectionRadioNo);
        motionDetectionYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    callCheckbox.setVisibility(View.VISIBLE);
                    callNumber.setVisibility(View.VISIBLE);
                    recordVideo.setVisibility(View.VISIBLE);
                    takePhoto.setVisibility(View.VISIBLE);
                }
            }
        });
        motionDetectionNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    callCheckbox.setVisibility(View.INVISIBLE);
                    callNumber.setVisibility(View.INVISIBLE);
                    callCheckbox.setChecked(false);
                    recordVideo.setVisibility(View.INVISIBLE);
                    recordVideo.setChecked(false);
                    takePhoto.setVisibility(View.INVISIBLE);
                    takePhoto.setChecked(false);
                }
            }
        });

        callCheckbox = (CheckBox) findViewById(R.id.cameraCallCheckbox);
        callNumber = (EditText) findViewById(R.id.cameraCallEdit);
        callCheckbox.setVisibility(View.INVISIBLE);
        callNumber.setVisibility(View.INVISIBLE);
        callNumber.setEnabled(false);
        callCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    callNumber.setEnabled(true);
                } else {
                    callNumber.setError(null);
                    callNumber.setText("");
                    callNumber.setEnabled(false);
                }
            }
        });

        recordVideo = (CheckBox) findViewById(R.id.cameraRecordVideo);
        recordVideo.setVisibility(View.INVISIBLE);

        takePhoto = (CheckBox) findViewById(R.id.cameraTakePhoto);
        takePhoto.setVisibility(View.INVISIBLE);

        camera = (Camera) getIntent().getSerializableExtra(CameraConfigActivity.CAMERA);

        spinner = (ProgressBar) findViewById(R.id.cameraRemoteSpinner);

        if (camera != null) {
            cameraRemote = new CameraRemote(camera, this);
            cameraRemote.load();
        }
    }

    /**
     * Called asynchronously by CameraRemoteData itself, after connecting and retrieving data
     *
     * @param remoteData
     */
    public void loadFields(final CameraRemote remoteData) {

        spinner.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
        errorLoading = false;

        if (remoteData.getMotionDetection())
            motionDetectionYes.setChecked(true);
        else
            motionDetectionNo.setChecked(true);

        if (!remoteData.getCallNumber().isEmpty()) {
            callCheckbox.setChecked(true);
            callNumber.setText(remoteData.getCallNumber());
        }

        recordVideo.setChecked(remoteData.getRecordVideo());

        takePhoto.setChecked(remoteData.getTakePhoto());
    }

    public void showErrorMessage() {
        spinner.setVisibility(View.GONE);
        findViewById(R.id.cameraRemoteError).setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_voip_remote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save_voip) {

            if (!errorLoading) {
                NavUtils.navigateUpFromSameTask(this);
                return true;
            }

            if (validateForm()) {
                cameraRemote.setMotionDetection(motionDetectionYes.isChecked());
                cameraRemote.setCallNumber(callNumber.getText().toString());
                cameraRemote.setRecordVideo(recordVideo.isChecked());
                cameraRemote.setTakePhoto(takePhoto.isChecked());
                cameraRemote.save();

                NavUtils.navigateUpFromSameTask(this);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean validateForm() {
        return validateCallNumber();
    }

    private boolean validateCallNumber() {
        if (callCheckbox.isChecked()) {
            final String call = callNumber.getText().toString();
            String message = Validator.validatePhoneNumber(call, this);
            if (!message.isEmpty()) {
                callNumber.setError(message);
                return false;
            } else {
                callNumber.setError(null);
            }
        }
        return true;
    }
}
