package com.fb.manusremote.intercom.model;

import android.widget.Toast;

import com.fb.manusremote.R;
import com.fb.manusremote.intercom.view.IntercomRemoteActivity;
import com.fb.manusremote.remote.AbstractRemote;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by Francesco on 15/01/2015.
 */
public class IntercomRemote extends AbstractRemote {

    private String ringTimeout;

    public IntercomRemote(final Intercom intercom, final IntercomRemoteActivity activity) {
        super(intercom, activity);
    }

    public String getRingTimeout() {
        return ringTimeout;
    }

    public void setRingTimeout(String ringTimeout) {
        this.ringTimeout = ringTimeout;
    }

    @Override
    public void load() {
        // TODO
        Toast.makeText(activity, "Leitura dos dados não implementada ainda! O valor é fake", LENGTH_LONG).show();
        setRingTimeout("60");
        activity.loadFields();
    }

    @Override
    public void save() {
        // TODO
        Toast.makeText(activity, "Update não implementado ainda! Não salvou nada em remoto", LENGTH_LONG).show();
        activity.goBack();
    }
}
