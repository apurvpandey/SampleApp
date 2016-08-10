package apurv.com.sampleapp.utilities;

import android.app.Activity;

import com.shephertz.app42.paas.sdk.android.App42Exception;

import apurv.com.sampleapp.R;
import apurv.com.sampleapp.dto.ResponseUserException;

/**
 * Created by apurv on 10/8/16.
 * E-mail : apurv.pandey@rocketmail.com
 * Contact : +91-8377887369
 */

public class AppUtilities {

    public static ResponseUserException getResponseUserException(Exception e) {
        App42Exception exception = (App42Exception) e;
        String errorMessage = exception.getMessage();
        ResponseUserException responseUserException = new ResponseUserException();
        return responseUserException;
    }


    public static void showExceptionInDialog(Activity activity, ResponseUserException ex) {
        if (activity == null)
            return;
        String dialogTitle = activity.getString(R.string.dialog_title_error);

        DialogUtilities.getDialog(activity, ex.getMessageDetail(), dialogTitle, DialogUtilities.AlertID
                .GENERAL_ALERT).show();
    }

}
