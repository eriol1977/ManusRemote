package com.fb.manusremote.model;

import android.content.Context;

import com.fb.manusremote.R;

import java.util.regex.Pattern;

/**
 * Created by Francesco on 15/01/2015.
 */
public class Validator {

    private static final String IP_ADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    public static String validateName(final String name, final Context context) {
        if (name.isEmpty())
            return context.getResources().getString(R.string.validation_field_required);

        return "";
    }

    public static String validateIp(final String ip, final Context context) {
        if (ip.isEmpty())
            return context.getResources().getString(R.string.validation_field_required);

        if (!Pattern.compile(IP_ADDRESS_PATTERN).matcher(ip).matches())
            return context.getResources().getString(R.string.validation_wrong_format);

        return "";
    }

    public static String validatePort(final String port, final Context context) {
        if (port.isEmpty())
            return context.getResources().getString(R.string.validation_field_required);

        try {
            final int portInt = Integer.parseInt(port);
            if (portInt < 0 || portInt > 65536)
                return context.getResources().getString(R.string.validation_port);
        } catch (NumberFormatException e) {
            return context.getResources().getString(R.string.validation_port);
        }

        return "";
    }

    public static String validateUsername(final String username, final Context context) {
        if (username.isEmpty())
            return context.getResources().getString(R.string.validation_field_required);

        if (username.contains(" "))
            return context.getResources().getString(R.string.validation_whitespace);

        return "";
    }

    public static String validatePassword(final String password, final Context context) {
        if (password.isEmpty())
            return context.getResources().getString(R.string.validation_field_required);

        if (password.contains(" "))
            return context.getResources().getString(R.string.validation_whitespace);

        return "";
    }

    public static String validateRingTimeout(final String ringTimeout, final Context context) {
        if (ringTimeout.isEmpty())
            return context.getResources().getString(R.string.validation_field_required);

        try {
            final int portInt = Integer.parseInt(ringTimeout);
        } catch (NumberFormatException e) {
            return context.getResources().getString(R.string.validation_ring_timeout);
        }

        return "";
    }
}
