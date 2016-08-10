package apurv.com.sampleapp.helper;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.App42Exception;
import com.shephertz.app42.paas.sdk.android.App42Response;
import com.shephertz.app42.paas.sdk.android.user.User;
import com.shephertz.app42.paas.sdk.android.user.UserService;

import java.util.HashMap;

import apurv.com.sampleapp.dto.ResponseCallback;
import apurv.com.sampleapp.dto.ResponseUserException;
import apurv.com.sampleapp.utilities.AppUtilities;

/**
 * Created by apurv on 10/8/16.
 * E-mail : apurv.pandey@rocketmail.com
 * Contact : +91-8377887369
 */


public class UserOperationHelper {

    final String TAG = UserOperationHelper.class.getSimpleName();
    private final int USER_LOGIN = 11;
    private final int USER_CREATE = 12;
    private final int USER_DELETE = 13;
    private final int USER_EXCEPTION = 14;
    private final int USER_LOGOUT = 15;
    private UserService userService;
    private Activity activity;

    Handler handlerUpdateUI = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object response = msg.obj;
            ResponseCallback responseCallback = (ResponseCallback) response;
            Object objectResponse = responseCallback.getObjectResponse();
            App42CallBack app42CallBack = responseCallback.getApp42CallBack();
            switch (msg.what) {
                case USER_LOGIN:
                    User user = (User) objectResponse;
                    if (user.getUserName() == null) {
                        AppUtilities.showExceptionInDialog(activity, new ResponseUserException
                                ("Offline : UserName/Password did not match. Authentication " +
                                        "Failed."));
                        if (app42CallBack != null) {
                            app42CallBack.onException(null);
                        }
                        return;
                    }
                    if (app42CallBack != null) {
                        app42CallBack.onSuccess(user);
                    }

                    break;
                case USER_LOGOUT:
                    App42Response app42Response = (App42Response) objectResponse;
                    if (app42CallBack != null) {
                        app42CallBack.onSuccess(app42Response);
                    }
                    break;
                case USER_CREATE:
                    User createdUser = (User) objectResponse;

                    if (app42CallBack != null) {
                        app42CallBack.onSuccess(createdUser);
                    }
                    break;
                case USER_DELETE:
                    App42Response app42response = (App42Response) objectResponse;

                    if (app42CallBack != null) {
                        app42CallBack.onSuccess(app42response);
                    }
                    break;
                case USER_EXCEPTION:
                    ResponseUserException responseUserException = (ResponseUserException) objectResponse;
                    AppUtilities.showExceptionInDialog(activity, responseUserException);
                    if (app42CallBack != null) {
                        app42CallBack.onException(responseUserException);
                    }
                    break;

                default:
            }
        }
    };


    public UserOperationHelper(Activity activity) {
        this.activity = activity;
        userService = App42API.buildUserService();
    }


    public void loginMerchant(String userName, String password, final App42CallBack app42CallBack) {

        userService.authenticate(userName, password, new App42CallBack() {
            public void onSuccess(Object response) {
                Message message = new Message();
                message.what = USER_LOGIN;
                message.obj = new ResponseCallback(response, app42CallBack);
                handlerUpdateUI.sendMessage(message);
            }

            public void onException(Exception ex) {
                handleException(ex, app42CallBack);
            }
        });
    }


    public void logoutMerchant(Activity activity, String sessionId, final App42CallBack app42CallBack) {
        userService.logout(sessionId, new App42CallBack() {
            public void onSuccess(Object response) {
                Message message = new Message();
                message.what = USER_LOGOUT;
                message.obj = new ResponseCallback(response, app42CallBack);
                handlerUpdateUI.sendMessage(message);
            }

            public void onException(Exception ex) {
                handleException(ex, app42CallBack);
            }
        });
    }


    public void deleteMerchant(String userName, boolean isPermanent, final App42CallBack app42CallBack) {
        if (isPermanent) {
            HashMap<String, String> otherMetaHeaders = new HashMap<String, String>();
            otherMetaHeaders.put("deletePermanent", "true");
            userService.setOtherMetaHeaders(otherMetaHeaders);
        }
        userService.deleteUser(userName, new App42CallBack() {

            @Override
            public void onSuccess(Object o) {
                Message message = new Message();
                message.what = USER_DELETE;
                message.obj = new ResponseCallback(o, app42CallBack);
                handlerUpdateUI.sendMessage(message);
            }

            @Override
            public void onException(Exception e) {
                handleException(e, app42CallBack);
            }
        });
    }

    public void createMerchant(String userName, String emailId, String password, final App42CallBack app42CallBack) {

        userService.createUser(userName, password, emailId, new App42CallBack() {
            @Override
            public void onSuccess(Object response) {
                Message message = new Message();
                message.what = USER_CREATE;
                message.obj = new ResponseCallback(response, app42CallBack);
                handlerUpdateUI.sendMessage(message);
            }

            @Override
            public void onException(Exception e) {
                handleException(e, app42CallBack);
            }
        });
    }


    public void createOrUpdateProfile(User user, final App42CallBack app42CallBack) {
        userService.createOrUpdateProfile(user, new App42CallBack() {
            public void onSuccess(Object response) {
                Message message = new Message();
                message.what = USER_LOGIN;
                message.obj = new ResponseCallback(response, app42CallBack);
                handlerUpdateUI.sendMessage(message);
            }

            public void onException(Exception ex) {
                handleException(ex, app42CallBack);
            }
        });

    }

    private void handleException(Exception e, App42CallBack app42CallBack) {
        App42Exception exception = (App42Exception) e;
        String errorMessage = exception.getMessage();
        ResponseUserException responseUserException = new ResponseUserException();

        Message message = new Message();
        message.what = USER_EXCEPTION;
        message.obj = new ResponseCallback(responseUserException, app42CallBack);
        handlerUpdateUI.sendMessage(message);


    }

}
