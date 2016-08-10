package apurv.com.sampleapp.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.user.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import apurv.com.sampleapp.R;
import apurv.com.sampleapp.helper.UserOperationHelper;
import apurv.com.sampleapp.utilities.DialogUtilities;

/**
 * Created by apurv on 10/8/16.
 * E-mail : apurv.pandey@rocketmail.com
 * Contact : +91-8377887369
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private static final String TAG = SignUpActivity.class.getSimpleName();

    private EditText editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword;
    private Button buttonLoginFb;
    private Button buttonLoginGoogle;
    private Button buttonProceed;
    private UserOperationHelper userOperationHelper;
    private String mUserName, mEmail, mPassword;
    private TextInputLayout textInputLayoutUserName, textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword, textInputLayoutConfirmPassword;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mProgressDialog = DialogUtilities.progressBarDialog(this, "", getResources().getString(R.string.dialog_message_please_wait), false);
        initialiseView();

    }

    private void initialiseView() {
        editTextUsername = (EditText) findViewById(R.id.edittext_username);
        editTextEmail = (EditText) findViewById(R.id.edittext_email);
        editTextPassword = (EditText) findViewById(R.id.edittext_password);
        editTextConfirmPassword = (EditText) findViewById(R.id.edittext_confirm_password);
        editTextConfirmPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() != KeyEvent.ACTION_UP)
                    return false;
                if (keyCode == event.KEYCODE_ENTER) {
                    if (dataValidation()) {
                        signupProcess();
                    }
                }
                return false;
            }
        });
        buttonLoginGoogle = (Button) findViewById(R.id.button_login_google);      //google plus custom button initialised
        buttonLoginFb = (Button) findViewById(R.id.button_login_fb);              //facebook custom login button initialised
        buttonProceed = (Button) findViewById(R.id.button_proceed);
        textInputLayoutUserName = (TextInputLayout) findViewById(R.id.textinputlayout_username);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textinputlayout_email);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textinputlayout_password);
        textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.textinputlayout_confirm_password);

        buttonLoginFb.setOnClickListener(this);
        buttonLoginGoogle.setOnClickListener(this);
        buttonProceed.setOnClickListener(this);

        editTextUsername.setOnFocusChangeListener(this);
        editTextEmail.setOnFocusChangeListener(this);
        editTextPassword.setOnFocusChangeListener(this);
        editTextConfirmPassword.setOnFocusChangeListener(this);
    }

    private Boolean dataValidation() {
        boolean flag = true;
        if (TextUtils.isEmpty(editTextUsername.getText().toString())) {
            textInputLayoutPassword.setErrorEnabled(false);
            textInputLayoutEmail.setErrorEnabled(false);
            textInputLayoutConfirmPassword.setErrorEnabled(false);
            textInputLayoutUserName.setErrorEnabled(true);
            textInputLayoutUserName.setError(getString(R.string.error_edittext_username));
            flag = false;
            return flag;
        }

        if (!(isValidEmail(editTextEmail.getText().toString()))) {
            textInputLayoutUserName.setErrorEnabled(false);
            textInputLayoutPassword.setErrorEnabled(false);
            textInputLayoutConfirmPassword.setErrorEnabled(false);
            textInputLayoutEmail.setErrorEnabled(true);
            textInputLayoutEmail.setError(getString(R.string.error_edittext_email));
            flag = false;
            return flag;
        }

        if (TextUtils.isEmpty(editTextPassword.getText().toString())) {
            textInputLayoutUserName.setErrorEnabled(false);
            textInputLayoutEmail.setErrorEnabled(false);
            textInputLayoutConfirmPassword.setErrorEnabled(false);
            textInputLayoutPassword.setErrorEnabled(true);
            textInputLayoutPassword.setError(getString(R.string.error_edittext_password));
            flag = false;
            return flag;
        }

        if (TextUtils.isEmpty(editTextConfirmPassword.getText().toString())) {
            textInputLayoutUserName.clearFocus();
            textInputLayoutUserName.setErrorEnabled(false);
            textInputLayoutEmail.clearFocus();
            textInputLayoutEmail.setErrorEnabled(false);
            textInputLayoutPassword.clearFocus();
            textInputLayoutPassword.setErrorEnabled(false);
            textInputLayoutConfirmPassword.setErrorEnabled(true);
            textInputLayoutConfirmPassword.setError(getString(R.string.error_edittext_password));
            flag = false;
            return flag;
        }

        return flag;


    }

    public boolean isValidEmail(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        switch (view.getId()) {
            case R.id.edittext_username:
                if (!hasFocus && (editTextUsername.getText().toString().isEmpty())) {
                    textInputLayoutUserName.setErrorEnabled(true);
                    textInputLayoutUserName.setError(getString(R.string.error_edittext_username));
                } else
                    textInputLayoutUserName.setErrorEnabled(false);
                break;

            case R.id.edittext_email:
                if (!hasFocus && (editTextEmail.getText().toString().isEmpty())) {
                    textInputLayoutEmail.setErrorEnabled(true);
                    textInputLayoutEmail.setError(getString(R.string.error_edittext_email));
                } else
                    textInputLayoutEmail.setErrorEnabled(false);
                break;

            case R.id.edittext_password:
                if (!hasFocus && (editTextPassword.getText().toString().isEmpty())) {
                    textInputLayoutPassword.setErrorEnabled(true);
                    textInputLayoutPassword.setError(getString(R.string.error_edittext_password));
                } else
                    textInputLayoutPassword.setErrorEnabled(false);
                break;

            case R.id.edittext_confirm_password:
                if (!hasFocus && (editTextConfirmPassword.getText().toString().isEmpty())) {
                    textInputLayoutConfirmPassword.setErrorEnabled(true);
                    textInputLayoutConfirmPassword.setError(getString(R.string.error_edittext_password));
                } else
                    textInputLayoutConfirmPassword.setErrorEnabled(false);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login_fb:

                break;

            case R.id.button_login_google:
                break;

            case R.id.button_proceed:
                if (dataValidation()) {
                    signupProcess();
                }
                break;

        }
    }

    private void signupProcess() {
        mProgressDialog.show();
        mUserName = editTextUsername.getText().toString();
        mEmail = editTextEmail.getText().toString();
        mPassword = editTextPassword.getText().toString();
        if (mPassword.equals(editTextConfirmPassword.getText().toString())) {
            userOperationHelper = new UserOperationHelper(this);
            userOperationHelper.createMerchant(mUserName, mEmail, mPassword, new App42CallBack() {
                @Override
                public void onSuccess(Object o) {
                    User user = (User) o;

                    mProgressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "You have been successfully registered", Toast.LENGTH_SHORT).show();
                    finish();

                }

                @Override
                public void onException(Exception e) {
                    mProgressDialog.dismiss();
                }
            });
        } else {
            mProgressDialog.dismiss();
            editTextPassword.setText("");
            editTextConfirmPassword.clearFocus();
            editTextConfirmPassword.setText("");
            textInputLayoutPassword.setErrorEnabled(false);
            textInputLayoutConfirmPassword.setErrorEnabled(false);
            Toast.makeText(this, "Passwords did not match ", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

}
