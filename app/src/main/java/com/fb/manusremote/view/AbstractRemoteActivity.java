package com.fb.manusremote.view;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.fb.manusremote.R;
import com.fb.manusremote.camera.model.CameraRemote;
import com.fb.manusremote.model.VOIPAdapter;
import com.fb.manusremote.remote.AbstractRemote;

public abstract class AbstractRemoteActivity extends ActionBarActivity {

    protected VOIPAdapter adapter;

    protected AbstractRemote remote;

    private final int layoutId;

    private final int contentId;

    private final int spinnerId;

    private final int errorLabelId;

    private ProgressBar spinner;

    private RelativeLayout content;

    private boolean errorLoading = true;

    protected AbstractRemoteActivity(int layoutId, int contentId, int spinnerId, int errorLabelId) {
        this.layoutId = layoutId;
        this.contentId = contentId;
        this.spinnerId = spinnerId;
        this.errorLabelId = errorLabelId;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(0, 0);
        setContentView(layoutId);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        content = (RelativeLayout) findViewById(contentId);
        content.setVisibility(View.INVISIBLE);

        spinner = (ProgressBar) findViewById(spinnerId);

        initFields();

        initAdapterAndRemote();
    }

    protected abstract void initFields();

    protected abstract void initAdapterAndRemote();

    /**
     * Called asynchronously by AbstractRemote itself, after connecting and retrieving data
     */
    public void loadFields() {

        spinner.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
        errorLoading = false;

        loadSpecificFields();
    }

    protected abstract void loadSpecificFields();

    public void showErrorMessage() {
        spinner.setVisibility(View.GONE);
        findViewById(errorLabelId).setVisibility(View.VISIBLE);
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
                updateRemote();
                remote.save();

                NavUtils.navigateUpFromSameTask(this);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    protected abstract void updateRemote();

    protected abstract boolean validateForm();
}
