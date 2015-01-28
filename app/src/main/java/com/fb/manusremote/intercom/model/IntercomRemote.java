package com.fb.manusremote.intercom.model;

import com.fb.manusremote.intercom.view.IntercomRemoteActivity;
import com.fb.manusremote.remote.AbstractRemote;

/**
 * Created by Francesco on 15/01/2015.
 */
public class IntercomRemote extends AbstractRemote {

    private final Intercom intercom;

    private final IntercomRemoteActivity activity;

    private String ringTimeout;

    public IntercomRemote(final Intercom intercom, final IntercomRemoteActivity activity) {
        super(activity);
        this.intercom = intercom;
        this.activity = activity;
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
        setRingTimeout("60");
        activity.loadFields(this);
    }

    @Override
    public void save() {
        // TODO
    }
}
