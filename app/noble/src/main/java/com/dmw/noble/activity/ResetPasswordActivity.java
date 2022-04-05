package com.dmw.noble.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dmw.noble.R;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.model.BasicResponse;
import com.dmw.noble.model.Otp;
import com.dmw.noble.model.SendOtp;
import com.dmw.noble.utils.AppUtils;

import java.util.Objects;

public class ResetPasswordActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {
    private Context mContext;
    private Button btnSubmit, btnLogin;
    private EditText editUserId, edtPass, edtConfirmPass;
    private ProgressDialog progressdialog;
    private String mobile, otp, password, confirmPassword;
    private boolean isFlag;
    private TextView lblChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        editUserId = findViewById(R.id.userName);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnLogin = findViewById(R.id.btnLogin2);
        lblChange = findViewById(R.id.lblGyan);

        edtPass = findViewById(R.id.editPass);
        edtConfirmPass = findViewById(R.id.editConfirmPass);

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");
    }

    public void onEmailSubmit(View view) {
        if (!isFlag) {
            mobile = editUserId.getText().toString().trim();
            if (AppUtils.isValidMobile(mobile))
                sendOtp();
            else
                editUserId.setError("Enter Valid mobile number");

        } else {
            EditText edtOtp = findViewById(R.id.userOtp);
            otp = edtOtp.getText().toString().trim();
            if (!TextUtils.isEmpty(otp)) {
                verifyOtp();
            } else edtOtp.setError("Enter Valid Code");
        }

    }

    private void resendCounter() {
        new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                btnLogin.setText("Resent in: " + millisUntilFinished / 1000);
                btnLogin.setClickable(false);
                btnLogin.setAlpha((float) 0.7);
            }

            public void onFinish() {
                btnLogin.setText("Resend");
                btnLogin.setClickable(true);
                btnLogin.setAlpha((float) 1);
            }

        }.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onSecond(View view) {
        sendOtp();
        resendCounter();
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof SendOtp) {
            SendOtp sResponse = (SendOtp) response;
            if (sResponse.getStatus().equals(1)) {
                initOtpUi();
                resendCounter();
            }
            Toast.makeText(mContext, "" + sResponse.getMsg(), Toast.LENGTH_SHORT).show();
        }
        if (response instanceof Otp) {
            Otp oResponse = (Otp) response;
            if (oResponse.getStatus().equals(1)) {
                Toast.makeText(mContext, "" + oResponse.getId(), Toast.LENGTH_SHORT).show();
                findViewById(R.id.resetPassLayout).setVisibility(View.GONE);
                findViewById(R.id.newPassLayout).setVisibility(View.VISIBLE);
            } else {
                editUserId.setError("Invalid OTP code");
                editUserId.requestFocus();
            }
        }
        if (response instanceof BasicResponse) {
            BasicResponse sResponse = (BasicResponse) response;
            if (sResponse.getStatus().equals(1)) {
                startActivity(new Intent(mContext, MainActivity.class));
                finishAffinity();
            }
            Toast.makeText(mContext, "" + sResponse.getMsg(), Toast.LENGTH_SHORT).show();
        }
        progressdialog.dismiss();
    }

    private void initOtpUi() {
        btnSubmit.setClickable(true);
        isFlag = true;
//        editUserId.setHint("Enter the code");
//        editUserId.setText("");
        findViewById(R.id.lyOtp).setVisibility(View.VISIBLE);
        findViewById(R.id.text_input_layout).setVisibility(View.GONE);
        lblChange.setText("Otp sent to "+mobile);
        editUserId.setInputType(InputType.TYPE_CLASS_PHONE);
        btnSubmit.setText("Submit");
        btnLogin.setVisibility(View.GONE);
        btnLogin.setVisibility(View.VISIBLE);
    }

    public void sendOtp() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().sendOtp(mContext, mobile);
            } catch (Exception e) {
                e.printStackTrace();
                progressdialog.dismiss();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void verifyOtp() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().verifyOtp(mContext, mobile, otp);
            } catch (Exception e) {
                e.printStackTrace();
                progressdialog.dismiss();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void updatePassword() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().updatePassword(mContext, mobile, password);
            } catch (Exception e) {
                e.printStackTrace();
                progressdialog.dismiss();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void onUpdatePassword(View view) {
        if (checkValidation())
            updatePassword();
    }

    private boolean checkValidation() {
        password = edtPass.getText().toString();
        confirmPassword = edtConfirmPass.getText().toString();
        if (TextUtils.isEmpty(password)) {
            edtPass.setError("Require Field");
            edtPass.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            edtConfirmPass.setError("Require Field");
            edtConfirmPass.requestFocus();
            return false;
        }
        if (password.length() < 8) {
            edtPass.setError("Minimum 8 Characters");
            edtPass.requestFocus();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            edtConfirmPass.setError("Password must be same");
            edtConfirmPass.requestFocus();
            return false;
        }

        return true;
    }
}
