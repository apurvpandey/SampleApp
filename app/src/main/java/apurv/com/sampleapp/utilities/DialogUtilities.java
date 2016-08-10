package apurv.com.sampleapp.utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.view.View;

import apurv.com.sampleapp.R;

/**
 * Created by apurv on 10/8/16.
 * E-mail : apurv.pandey@rocketmail.com
 * Contact : +91-8377887369
 */

public class DialogUtilities {

    public static AlertDialog getDialog(final Activity activity,
                                        String message, View view,
                                        String title, String lableOK, String lableCancel,
                                        String lableNeutral, final AlertID alertID,
                                        final AlertButtonRequired alertButtonRequired,
                                        DialogInterface.OnClickListener positiveButtonClickListener,
                                        DialogInterface.OnClickListener negativeButtonClickListener,
                                        DialogInterface.OnClickListener neutralButtonClickListener) {
        Resources resources = activity.getResources();
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        if (message != null) {
            builder.setMessage(message);
        }
        if (view != null) {
            builder.setView(view);
        }

        if (title != null)
            builder.setTitle(title);

        if (alertButtonRequired == AlertButtonRequired.OK)
            builder.setPositiveButton(getButtonLable(ButtonText.OK, lableOK, resources), getDialogPositiveClick(activity, alertID, positiveButtonClickListener));
        else if (alertButtonRequired == AlertButtonRequired.OK_CANCEL) {
            builder.setPositiveButton(getButtonLable(ButtonText.OK, lableOK, resources), getDialogPositiveClick(activity, alertID, positiveButtonClickListener));
            builder.setNegativeButton(getButtonLable(ButtonText.CANCEL, lableCancel, resources), negativeButtonClickListener);
        } else if (alertButtonRequired == AlertButtonRequired.OK_CANCEL_NEUTRAL) {
            builder.setPositiveButton(getButtonLable(ButtonText.OK, lableOK, resources), getDialogPositiveClick(activity, alertID, positiveButtonClickListener));
            builder.setNegativeButton(getButtonLable(ButtonText.CANCEL, lableCancel, resources), negativeButtonClickListener);
            builder.setNeutralButton(getButtonLable(ButtonText.NEUTRAL, lableNeutral, resources), neutralButtonClickListener);
        }
        AlertDialog dialog = builder.create();
        return dialog;
    }


    public static AlertDialog getDialogWithCustomView(final Activity activity,
                                                      View view, String title, String buttonOk, String buttonCancel,
                                                      final AlertButtonRequired alertButtonRequired,
                                                      DialogInterface.OnClickListener positiveButtonClickListener,
                                                      DialogInterface.OnClickListener negativeButtonClickListener) {
        return getDialog(activity, null, view, title, buttonOk, buttonCancel, null, AlertID.GENERAL_ALERT, alertButtonRequired, positiveButtonClickListener, negativeButtonClickListener, null);
    }


    public static AlertDialog getDialog(final Activity activity,
                                        String msg, String title,
                                        final AlertID alertID,
                                        final AlertButtonRequired alertButtonRequired,
                                        DialogInterface.OnClickListener positiveButtonClickListener,
                                        DialogInterface.OnClickListener negativeButtonClickListener) {

        return getDialog(activity, msg, null, title, null, null, null, alertID, alertButtonRequired, positiveButtonClickListener, negativeButtonClickListener, null);
    }

    public static AlertDialog getDialog(final Activity activity,
                                        String msg, String title,
                                        final AlertID alertID) {
        return getDialog(activity, msg, title, alertID, AlertButtonRequired.OK, null, null);
    }

    public static AlertDialog getDialog(final Activity activity, String msg, String title) {
        return getDialog(activity, msg, title, AlertID.GENERAL_ALERT);
    }

    public static AlertDialog getLogoutDialog(final Activity activity, String msg, String title) {
        return getDialog(activity, msg, title, AlertID.LOGOUT);
    }


    private static String getButtonLable(ButtonText buttonText, String buttonLable, Resources resources) {
        if (buttonLable != null) {
            return buttonLable;
        }
        String string = "";
        switch (buttonText) {
            case OK:
                string = resources.getString(R.string.dialog_label_alert_ok);
                break;
            case CANCEL:
                string = resources.getString(R.string.dialog_label_alert_cancel);
                break;
            case NEUTRAL:
                string = resources.getString(R.string.dialog_label_alert_neutral);
                break;
        }
        return string;
    }

    private static DialogInterface.OnClickListener getDialogPositiveClick(final Activity activity, final AlertID alertID, final DialogInterface.OnClickListener positiveButtonClickListener) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (alertID) {
                    case SESSION_TOKEN_EXPIRE:
                        LogoutServiceHit(activity);
                        break;
                    case LOGOUT:
                        LogoutServiceHit(activity);
                        break;
                }
                if (positiveButtonClickListener != null)
                    positiveButtonClickListener.onClick(dialogInterface, i);
            }
        };
    }


    private static void LogoutServiceHit(final Activity activity) {
        //use to logout from the service
    }

    public static ProgressDialog progressBarDialog(Activity activity, String title, String message, boolean cancelOnTouchOutside) {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCanceledOnTouchOutside(cancelOnTouchOutside);

        return progressDialog;
    }


    public enum AlertID {GENERAL_ALERT, NO_INTERNET_CONNECTION, SESSION_TOKEN_EXPIRE, LOGOUT}

    public enum AlertButtonRequired {OK, OK_CANCEL, OK_CANCEL_NEUTRAL}

    private enum ButtonText {OK, CANCEL, NEUTRAL}
}
