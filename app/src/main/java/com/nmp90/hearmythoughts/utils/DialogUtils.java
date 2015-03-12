package com.nmp90.hearmythoughts.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import com.nmp90.hearmythoughts.R;

/**
 * Created by nmp on 15-3-12.
 */
public class DialogUtils {
    private static ProgressDialog progressDialog;

    /**
     * Show ProgressDialog when getting data from the server.
     *
     * @param context
     */
    public static void showLoadingDialog(Context context, DialogInterface.OnCancelListener listener) {
        progressDialog = ProgressDialog.show(context, context.getString(R.string.please_wait),
                context.getString(R.string.loading_));
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(listener);
    }


    /**
     * Hides loading dialog if there is shown.
     */
    public static void hideLoadingDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * Check whether the loading dialog is showing.
     *
     * @return true if the loading Dialog is showing; false otherwise.
     */
    public static boolean isProgressShowing() {
        return progressDialog != null ? progressDialog.isShowing() : false;
    }

    /**
     * Dialog for no connection
     */
    public static void showNoConnectionDialog(final Context context) {
        new AlertDialog.Builder(context).setTitle(R.string.error)
                .setMessage(R.string.no_internet_connection_)
                .setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    /**
     * Shows {@link AlertDialog} with one 'OK' button and custom message
     *
     * @param errorMessage String for error message
     */
    public static void showErrorDialog(Context context, String errorMessage) {
        showDialog(context, R.string.error, errorMessage);
    }

    /**
     * Shows {@link AlertDialog} with one 'OK' button and custom message
     *
     * @param errorMessage integer for error message resource
     */
    public static void showErrorDialog(Context context, int errorMessage) {
        showDialog(context, R.string.error, errorMessage);
    }

    /**
     * Shows {@link AlertDialog} with one 'OK' button and custom title and message
     *
     * @param context
     * @param title   integer for title string resource
     * @param message integer for message string resource
     */
    public static void showDialog(Context context, int title, int message) {
        showDialog(context, title, context.getString(message));
    }

    /**
     * Shows {@link AlertDialog} with one 'OK' button and custom title and message
     *
     * @param context
     * @param title   integer for title string resource
     * @param message String for dialog message
     */
    public static void showDialog(Context context, int title, String message) {
        new AlertDialog.Builder(context).setTitle(title)
                .setMessage(message)
                .setNeutralButton(R.string.ok, null)
                .show();
        // Tracks error messages which the user will see in AlertDialog
    }

    public static void showServerErrorToast(Context context) {
        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT)
                .show();
    }

}
