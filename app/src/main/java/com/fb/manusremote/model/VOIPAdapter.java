package com.fb.manusremote.model;

import java.io.Serializable;

/**
 * Created by Francesco on 14/01/2015.
 */
public abstract class VOIPAdapter implements Serializable {

    private final String name;

    private final String ip;

    private final String port;

    private final String username;

    private final String password;

    private final static String SEPARATOR = ":";

    public VOIPAdapter(String name, String ip, String port, String username, String password) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    /**
     * @param loadedString name:ip:port:username:password
     */
    public VOIPAdapter(final String loadedString)
    {
        final String[] loaded = loadedString.split(SEPARATOR);
        this.name = loaded[0];
        this.ip = loaded[1];
        this.port = loaded[2];
        this.username = loaded[3];
        this.password = loaded[4];
    }

    public abstract AdapterKind getKind();

    /**
     * @return name:ip:port:username:password/
     */
    public String toSaveFormat() {
        final StringBuilder sb = new StringBuilder();
        sb.append(name).append(SEPARATOR);
        sb.append(ip).append(SEPARATOR);
        sb.append(port).append(SEPARATOR);
        sb.append(username).append(SEPARATOR);
        sb.append(password); // FIXME criptare?
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
