package apurv.com.sampleapp.dto;

import com.shephertz.app42.paas.sdk.android.App42CallBack;

/**
 * Created by apurv on 10/8/16.
 * E-mail : apurv.pandey@rocketmail.com
 * Contact : +91-8377887369
 */

public class ResponseCallback {
    private Object objectResponse;
    private App42CallBack app42CallBack;

    public ResponseCallback(Object objectResponse, App42CallBack app42CallBack) {
        this.objectResponse = objectResponse;
        this.app42CallBack = app42CallBack;
    }

    public Object getObjectResponse() {
        return objectResponse;
    }

    public App42CallBack getApp42CallBack() {
        return app42CallBack;
    }
}
