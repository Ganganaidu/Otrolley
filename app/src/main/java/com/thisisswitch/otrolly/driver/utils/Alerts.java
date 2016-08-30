package com.thisisswitch.otrolly.driver.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.thisisswitch.otrolly.driver.R;

/**
 * Created by Office on 4/10/2016.
 */
public class Alerts {

    public static String getValue(final View view) {
        if (view instanceof EditText) {
            Editable editable = ((EditText) view).getText();
            if (editable == null) {
                return "";
            } else {
                EditText editText = (EditText) view;
                if (editText.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        || editText.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD
                        || editText.getInputType() == InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD)
                    return editable.toString();
                return editable.toString().trim();
            }
        }
        return "";
    }

    public static boolean internetAvailable(final Activity activity) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alert(activity, "No connection", 1);
            }
        });
        return false;
    }

    public static void alert(final Activity activity, final String message) {
        alert(activity, message, 0);
    }

    public static void alert(final Activity activity, final String message, final int action) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar snackbar = Snackbar.make(activity.findViewById(R.id.parent), message, Snackbar.LENGTH_SHORT);
                ViewGroup group = (ViewGroup) snackbar.getView();
                group.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.main));
//                if(action > 0) {
//                    snackbar.setDuration(Snackbar.LENGTH_LONG);
//                    snackbar.setActionTextColor(activity.getResources().getColor(R.color.bg));
//                    snackbar.setAction("RETRY", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            activity.startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
//                        }
//                    });
//                }
                snackbar.show();
                progress(activity, false);
            }
        });
    }

    public static void progress(final Activity activity, final boolean show) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.findViewById(R.id.progressLayout).setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    private void hideKeyBoard(Context context, EditText myEditText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
    }
}
