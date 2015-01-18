package com.fb.manusremote.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.fb.manusremote.R;

/**
 * Created by Francesco on 18/01/2015.
 */
public class IpCamViewerUtil {

    private final static ComponentName liteVersion = new ComponentName("com.rcreations.ipcamviewer", "com.rcreations.ipcamviewer.WebCamViewerActivity");

    private final static ComponentName basicVersion = new ComponentName("com.rcreations.ipcamviewerBasic", "com.rcreations.ipcamviewerBasic.WebCamViewerActivity");

    private final static ComponentName proVersion = new ComponentName("com.rcreations.WebCamViewerPaid", "com.rcreations.WebCamViewerPaid.IpCamViewerActivity");

    public static void viewAllCameras(final Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("selectView", "MATRIX_VIEW");
        tryVersions(intent, context);
    }

    public static void viewCamera(final String cameraName, final Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("selectView", "GALLERY_VIEW");
        intent.putExtra("selectCameraName", cameraName);
        tryVersions(intent, context);
    }

    private static void tryVersions(final Intent intent, final Context context) {
        boolean result = tryLiteVersion(intent, context);
        if (!result) {
            result = tryBasicVersion(intent, context);
            if (!result) {
                result = tryProVersion(intent, context);
                if (!result) {
                    createDownloadIpCamViewerDialog(context).show();
                }
            }
        }
    }

    private static boolean tryLiteVersion(final Intent intent, final Context context) {
        intent.setComponent(liteVersion);
        try {
            context.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    private static boolean tryBasicVersion(final Intent intent, final Context context) {
        intent.setComponent(basicVersion);
        try {
            context.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    private static boolean tryProVersion(final Intent intent, final Context context) {
        intent.setComponent(proVersion);
        try {
            context.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    public static Dialog createDownloadIpCamViewerDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getResources().getString(R.string.ip_cam_viewer_not_installed) + " " +
                context.getResources().getString(R.string.do_you_wish_to_install) + " " +
                context.getResources().getString(R.string.every_camera_name))
                .setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final String appPackageName = "com.rcreations.ipcamviewer";
                        try {
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }
                })
                .setNegativeButton(context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(context, R.string.no_camera_access, Toast.LENGTH_LONG).show();
                    }
                });
        return builder.create();
    }

    // tip taken from: http://hit-mob.com/forums/viewtopic.php?f=8&t=697

//    // launch matrix mode of paid version
//    Intent intent = new Intent( Intent.ACTION_MAIN );
//    intent.addCategory( Intent.CATEGORY_LAUNCHER );
//    intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
//    ComponentName cn = new ComponentName( "com.rcreations.WebCamViewerPaid", "com.rcreations.WebCamViewerPaid.IpCamViewerActivity" );
//    intent.setComponent( cn );
//    intent.putExtra( "selectView", "MATRIX_VIEW" );
//    startActivity( intent );
//
//    // launch matrix mode of paid version for specific group name (v5.2.5 or newer)
//    Intent intent = new Intent( Intent.ACTION_MAIN );
//    intent.addCategory( Intent.CATEGORY_LAUNCHER );
//    intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
//    ComponentName cn = new ComponentName( "com.rcreations.WebCamViewerPaid", "com.rcreations.WebCamViewerPaid.IpCamViewerActivity" );
//    intent.setComponent( cn );
//    intent.putExtra( "selectView", "MATRIX_VIEW" );
//    intent.putExtra( "selectGroupName", "group name" );
//    startActivity( intent );
//
//    // launch gallery mode of paid version
//    Intent intent = new Intent( Intent.ACTION_MAIN );
//    intent.addCategory( Intent.CATEGORY_LAUNCHER );
//    intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
//    ComponentName cn = new ComponentName( "com.rcreations.WebCamViewerPaid", "com.rcreations.WebCamViewerPaid.IpCamViewerActivity" );
//    intent.setComponent( cn );
//    intent.putExtra( "selectView", "GALLERY_VIEW" );
//    startActivity( intent );
//
//    // launch gallery mode of paid version for specific camera
//    Intent intent = new Intent( Intent.ACTION_MAIN );
//    intent.addCategory( Intent.CATEGORY_LAUNCHER );
//    intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
//    ComponentName cn = new ComponentName( "com.rcreations.WebCamViewerPaid", "com.rcreations.WebCamViewerPaid.IpCamViewerActivity" );
//    intent.setComponent( cn );
//    intent.putExtra( "selectView", "GALLERY_VIEW" );
//    intent.putExtra( "selectCameraName", "my cam name" );
//    startActivity( intent );
//
//    // for the Lite version of the app, use the following ComponentName:
//    ComponentName cn = new ComponentName( "com.rcreations.ipcamviewer", "com.rcreations.ipcamviewer.WebCamViewerActivity" );
//
//    // for the Basic version of the app, use the following ComponentName:
//    ComponentName cn = new ComponentName( "com.rcreations.ipcamviewerBasic", "com.rcreations.ipcamviewerBasic.WebCamViewerActivity" );
}
