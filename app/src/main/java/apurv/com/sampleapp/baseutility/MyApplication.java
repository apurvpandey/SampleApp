package apurv.com.sampleapp.baseutility;

import android.app.Application;

import com.shephertz.app42.paas.sdk.android.App42API;

import apurv.com.sampleapp.constant.Constant;

/**
 * Created by apurv on 10/8/16.
 * E-mail : apurv.pandey@rocketmail.com
 * Contact : +91-8377887369
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initialiseApp42();
    }

    private void initialiseApp42() {
        App42API.initialize(this, Constant.APP42_API_KEY, Constant.APP42_SECRET_KEY);
    }
}
