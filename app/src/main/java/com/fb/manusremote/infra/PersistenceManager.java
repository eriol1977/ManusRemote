package com.fb.manusremote.infra;

import android.content.SharedPreferences;

import com.fb.manusremote.intercom.model.Intercom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francesco on 15/01/2015.
 */
public class PersistenceManager {

    public final static String INTERCOMS = "intercoms";

    private final static String NAME_SEPARATOR = ",";

    private final static String INTERCOM_PREFIX = "ic_";

    public static List<Intercom> loadIntercoms(final SharedPreferences preferences) {
        final List<Intercom> intercoms = new ArrayList<>();
        final String intercomsString = preferences.getString(INTERCOMS, null);
        if (intercomsString != null) {
            final String[] intercomNames = intercomsString.split(NAME_SEPARATOR);
            String loadedString = null;
            for (final String name : intercomNames) {
                loadedString = preferences.getString(name, null);
                if (loadedString != null)
                    intercoms.add(new Intercom(loadedString));
            }
        }
        return intercoms;
    }

    public static void addIntercom(String name, String ip, String port, String username, String password, final SharedPreferences preferences) {
        final String saveName = INTERCOM_PREFIX + name;
        final SharedPreferences.Editor editor = preferences.edit();
        String intercomsString = preferences.getString(INTERCOMS, "");
        if (!intercomsString.isEmpty())
            intercomsString += NAME_SEPARATOR;
        intercomsString += saveName;
        editor.putString(INTERCOMS, intercomsString);
        editor.putString(saveName, new Intercom(name, ip, port, username, password).toSaveFormat());
        editor.commit();
    }

    public static void removeIntercom(final String name, final SharedPreferences preferences) {
        final String saveName = INTERCOM_PREFIX + name;
        final SharedPreferences.Editor editor = preferences.edit();
        String intercomsString = preferences.getString(INTERCOMS, "");
        final String[] intercomNames = intercomsString.split(NAME_SEPARATOR);
        String newIntercomsString = "";
        for (final String intercomName : intercomNames) {
            if (!intercomName.equals(saveName))
                newIntercomsString += intercomName + NAME_SEPARATOR;
        }
        if (!newIntercomsString.isEmpty())
            newIntercomsString = newIntercomsString.substring(0, newIntercomsString.length() - 1);
        editor.putString(INTERCOMS, newIntercomsString);
        editor.remove(saveName);
        editor.commit();
    }

    public static void updateIntercom(String oldName, String name, String ip, String port,
                                      String username, String password, SharedPreferences preferences) {
        final SharedPreferences.Editor editor = preferences.edit();
        if (oldName != name) {
            removeIntercom(oldName, preferences);
            addIntercom(name, ip, port, username, password, preferences);
        } else {
            editor.putString(INTERCOM_PREFIX + name, new Intercom(name, ip, port, username, password).toSaveFormat());
            editor.commit();
        }
    }
}
