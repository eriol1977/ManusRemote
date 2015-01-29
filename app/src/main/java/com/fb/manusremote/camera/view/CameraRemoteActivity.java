package com.fb.manusremote.camera.view;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import com.fb.manusremote.R;
import com.fb.manusremote.camera.model.Camera;
import com.fb.manusremote.camera.model.CameraRemote;
import com.fb.manusremote.model.Validator;
import com.fb.manusremote.view.AbstractRemoteActivity;

public class CameraRemoteActivity extends AbstractRemoteActivity {

    private RadioButton motionDetectionYes;

    private RadioButton motionDetectionNo;

    private CheckBox callCheckbox;

    private EditText callNumber;

    private CheckBox recordVideo;

    private CheckBox takePhoto;

    public CameraRemoteActivity() {
        super(R.layout.activity_camera_remote, R.id.cameraRemoteContent,
                R.id.cameraRemoteSpinner, R.id.cameraRemoteError);
    }

    @Override
    protected void initFields() {
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
    }

    @Override
    protected void initAdapterAndRemote() {
        adapter = (Camera) getIntent().getSerializableExtra(CameraConfigActivity.CAMERA);

        if (adapter != null) {
            remote = new CameraRemote((Camera) adapter, this);
            remote.load();
        }
    }

    @Override
    protected void loadSpecificFields() {
        final CameraRemote cameraRemote = (CameraRemote) remote;

        if (cameraRemote.getMotionDetection())
            motionDetectionYes.setChecked(true);
        else
            motionDetectionNo.setChecked(true);

        if (!cameraRemote.getCallNumber().isEmpty()) {
            callCheckbox.setChecked(true);
            callNumber.setText(cameraRemote.getCallNumber());
        }

        recordVideo.setChecked(cameraRemote.getRecordVideo());

        takePhoto.setChecked(cameraRemote.getTakePhoto());
    }

    @Override
    protected void updateRemote() {
        CameraRemote cameraRemote = (CameraRemote) remote;
        cameraRemote.setMotionDetection(motionDetectionYes.isChecked());
        cameraRemote.setCallNumber(callNumber.getText().toString());
        cameraRemote.setRecordVideo(recordVideo.isChecked());
        cameraRemote.setTakePhoto(takePhoto.isChecked());
    }

    protected boolean validateForm() {
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
