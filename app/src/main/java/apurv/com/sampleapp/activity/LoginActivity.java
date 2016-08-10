package apurv.com.sampleapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.user.User;

import apurv.com.sampleapp.R;
import apurv.com.sampleapp.helper.UserOperationHelper;
import apurv.com.sampleapp.utilities.DialogUtilities;

/**
 * Created by apurv on 10/8/16.
 * E-mail : apurv.pandey@rocketmail.com
 * Contact : +91-8377887369
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private TextView textViewSignUp, textViewForgotPassword;
    private EditText editTextUserName, editTextPassword;
    private Button buttonProceed;
    private TextInputLayout textInputLayoutUserName, textInputLayoutPassword;
    private String mUserName, mPassword;
    private UserOperationHelper userOperationHelper;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mProgressDialog = DialogUtilities.progressBarDialog(this, "", getResources().getString(R.string.dialog_message_please_wait), false);
        initialiseView();
    }

    private void initialiseView() {


        textViewSignUp = (TextView) findViewById(R.id.textview_signup);
        textViewForgotPassword = (TextView) findViewById(R.id.textview_forgot_password);
        editTextUserName = (EditText) findViewById(R.id.edittext_username);
        editTextPassword = (EditText) findViewById(R.id.edittext_password);
        buttonProceed = (Button) findViewById(R.id.button_proceed);
        textInputLayoutUserName = (TextInputLayout) findViewById(R.id.textInputLayout_username);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textinputlayout_password);
        textViewSignUp.setClickable(true);
        textViewForgotPassword.setClickable(true);
        editTextUserName.setOnFocusChangeListener(this);
        editTextPassword.setOnFocusChangeListener(this);
        editTextPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() != KeyEvent.ACTION_UP)
                    return false;
                if (keyCode == event.KEYCODE_ENTER) {
                    if (dataValidation()) {
                        loginProcess();
                    }
                }
                return false;
            }
        });
        buttonProceed.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);
        textViewForgotPassword.setOnClickListener(this);
    }

    private Boolean dataValidation() {
        boolean flag = true;
        if (TextUtils.isEmpty(editTextUserName.getText().toString())) {
            textInputLayoutPassword.setErrorEnabled(false);
            textInputLayoutUserName.setErrorEnabled(true);
            textInputLayoutUserName.setError(getString(R.string.error_edittext_username));
            flag = false;
            return flag;
        }

        if (TextUtils.isEmpty(editTextPassword.getText().toString())) {
            textInputLayoutUserName.setErrorEnabled(false);
            textInputLayoutPassword.setErrorEnabled(true);
            textInputLayoutPassword.setError(getString(R.string.error_edittext_password));
            flag = false;
            return flag;
        }

        return flag;
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        switch (view.getId()) {
            case R.id.edittext_username:
                if (!hasFocus && (editTextUserName.getText().toString().isEmpty())) {
                    textInputLayoutUserName.setErrorEnabled(true);
                    textInputLayoutUserName.setError(getString(R.string.error_edittext_username));
                } else
                    textInputLayoutUserName.setErrorEnabled(false);
                break;

            case R.id.edittext_password:
                if (!hasFocus && (editTextPassword.getText().toString().isEmpty())) {
                    textInputLayoutPassword.setErrorEnabled(true);
                    textInputLayoutPassword.setError(getString(R.string.error_edittext_password));
                } else
                    textInputLayoutPassword.setErrorEnabled(false);
                break;
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_proceed:
                if (dataValidation()) {
                    loginProcess();
                }
                break;

            case R.id.textview_signup:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;

            case R.id.textview_forgot_password:
                break;
        }
    }

    private void loginProcess() {
        mProgressDialog.show();
        mUserName = editTextUserName.getText().toString();
        mPassword = editTextPassword.getText().toString();
        userOperationHelper = new UserOperationHelper(this);
        userOperationHelper.loginMerchant(mUserName, mPassword, new App42CallBack() {
            @Override
            public void onSuccess(Object o) {
                User user = (User) o;

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                mProgressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "You have successfully logged in", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onException(Exception e) {

                editTextUserName.setText("");
                editTextPassword.setText("");
                editTextPassword.clearFocus();
                mProgressDialog.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
}
