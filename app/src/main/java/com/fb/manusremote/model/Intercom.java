package com.fb.manusremote.model;

/**
 * Created by Francesco on 14/01/2015.
 */
public class Intercom extends VOIPAdapter {

    public Intercom(String name, String ip,String port, String username, String password) {
        super(name, ip, port, username, password);
    }

    public Intercom(final String loadedString) {
        super(loadedString);
    }

    @Override
    public AdapterKind getKind() {
        return AdapterKind.INTERCOM;
    }


}
