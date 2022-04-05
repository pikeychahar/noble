package com.dmw.noble.activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.dmw.noble.R;
import com.dmw.noble.activity_profile.AgentActivity;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.CrmManager;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.model.Login;
import com.dmw.noble.model.SendOtp;
import com.dmw.noble.utils.AppUtils;

import java.util.Objects;

/**
 * Created by Prahalad Kumar Chahar
 */
public class MainActivity extends AbstractActivity implements onRequestCompleteCallBackListener {

    Context mContext;
    EditText edtMobile, edtPassword;
    String mobile, password, otp, mbData;
    TextView skipText;
    ProgressDialog progressdialog;
    SharedPreferences preferences;
    Boolean isInspection, isOtp = true;
    boolean isAgentOn, otpFlag = true;
    Button btnOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        mContext = this;
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");

        edtMobile = findViewById(R.id.edtMobile);
        edtPassword = findViewById(R.id.edtPassword);
        skipText = findViewById(R.id.skipText);
        btnOtp = findViewById(R.id.btnOtp);

        isAgentOn = preferences.getBoolean(AppUtils.IS_AGENT, false);
        isInspection = preferences.getBoolean(AppUtils.IS_INSPECTION, false);

        skipText.setPaintFlags(skipText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        skipText.setOnClickListener(v -> {
            startActivity(new Intent(mContext, DashboardActivity.class));
            finish();
        });

        if (isInspection)
            skipText.setVisibility(View.GONE);
    }

    public void onLogin(View view) {
        AppUtils.checkSoftKeyboard(this);
        if (isEmpty()) {
            if (isOtp) {
                if (mbData.equals(mobile)) {
                    String mOtp = edtPassword.getText().toString().trim();
                    if (mOtp.equals(otp))
                        postLogin();
                    else
                        Toast.makeText(mContext, "Invalid Otp", Toast.LENGTH_SHORT).show();
                } else if (isEmpty())
                    getOtp();
            } else
                postLogin();
        }
    }

    public void getOtp() {
        AppUtils.checkSoftKeyboard(this);
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                CrmManager.getInstance().getLoginOtp(mContext, mobile);
            } catch (Exception e) {
                e.printStackTrace();

            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }


    public void onSignUp(View view) {
        startActivity(new Intent(mContext, AgentActivity.class));
    }

    public void postLogin() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().postLogin(mContext, mobile, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isEmpty() {
        mobile = edtMobile.getText().toString().trim();
        password = edtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
            edtMobile.setError("Required Field");
            return false;
        }
        if (!isOtp && TextUtils.isEmpty(password)) {
            edtPassword.setError("Required Field");
            return false;
        }
        return true;
    }

    @Override
    public void onResponse(Object response) {
        try {
            if (response instanceof Login) {
                Login loginResponse = (Login) response;
                if (loginResponse.getSuccess().equals("1")) {
                    String userType = loginResponse.getType();
                    if (!TextUtils.isEmpty(userType)) {
                        userType = userType.toLowerCase();
                        if (userType.equals("agent") || userType.equals("subpos")) {

                            String mobile = loginResponse.getNumber();
                            String name = loginResponse.getName();
                            String email = loginResponse.getEmail().toLowerCase();
                            String agentId = loginResponse.getAgentId();
                            String userId = loginResponse.getId();
                            String referenceId = loginResponse.getReferenceId();
                            String addPosPermission = loginResponse.getCreatePosPermission();
                            String isAgreed = loginResponse.getPosAgreement();
                            String posStatus = loginResponse.getPosStatus();
                            String userBadge = loginResponse.getPosBadge();
                            String badgeColor = loginResponse.getBadgeColor();
                            String mobilePermission = loginResponse.getCreateMobilePermission();
                            String token = loginResponse.getToken();

                            preferences.edit().putString(AppUtils.MOBILE, mobile).apply();
                            preferences.edit().putString(AppUtils.NAME, name).apply();
                            preferences.edit().putString(AppUtils.EMAIL, email).apply();
                            preferences.edit().putString(AppUtils.AGENT_ID, agentId).apply();
                            preferences.edit().putString(AppUtils.USER_TYPE, userType).apply();
                            preferences.edit().putString(AppUtils.USER_PASS, password).apply();
                            preferences.edit().putString(AppUtils.REFERENCE_ID, referenceId).apply();
                            preferences.edit().putString(AppUtils.POS_PERMISSION, addPosPermission).apply();
                            preferences.edit().putString(AppUtils.USER_BADGE, userBadge).apply();
                            preferences.edit().putString(AppUtils.BADGE_COLOR, badgeColor).apply();
                            preferences.edit().putString(AppUtils.IS_AGREEMENT_POS, isAgreed).apply();
                            preferences.edit().putString(AppUtils.MOBILE_PERMISSION, mobilePermission).apply();

                            preferences.edit().putString(AppUtils.PRIMARY_ID, userId).apply();
                            preferences.edit().putString(AppUtils.POS_STATUS, posStatus).apply();
                            preferences.edit().putString(AppUtils.LOGIN_TOKEN, token).apply();

                            if (!isInspection)
                                startActivity(new Intent(mContext, DashboardActivity.class));
                            preferences.edit().putBoolean(AppUtils.IS_INSPECTION, false).apply();

                            finish();
                        } else if (loginResponse.getType().equals("sp")) {
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("market://details?id=com.square.squareinsurance")));
                            } catch (ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("https://play.google.com/store/apps/details?id=com.square.squareinsurance")));
                            }
                        } else showError("Not Found");
                    }
                } else showError("Invalid UserId Or Password!!");
            }
            if (response instanceof SendOtp) {
                SendOtp vResponse = (SendOtp) response;
                if (vResponse.getStatus() == 1) {
                    otp = String.valueOf(vResponse.getOtp());
                    findViewById(R.id.text_input_pass).setVisibility(View.VISIBLE);
                    findViewById(R.id.btnContinue).setVisibility(View.VISIBLE);
                    edtPassword.setText("");
                    mbData = mobile;
                    resendCounter();
                } else {
                    AppUtils.showToast(mContext, "" + vResponse.getMsg());
                }
            }
            progressdialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void forgetPassword(View view) {
        startActivity(new Intent(mContext, ResetPasswordActivity.class));
    }

    boolean doubleBackToExitPressedOnce = false;


    private void resendCounter() {
        new CountDownTimer(45000, 1000) {
            public void onTick(long millisUntilFinished) {
                btnOtp.setText("Wait: " + millisUntilFinished / 1000);
                btnOtp.setClickable(false);
                btnOtp.setAlpha((float) 0.7);
                otpFlag = false;
            }

            public void onFinish() {
                otpFlag = true;
                btnOtp.setText("Resend");
                btnOtp.setClickable(true);
                btnOtp.setAlpha((float) 1);
            }

        }.start();
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit",
                Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void showError(String errorMsg) {
        try {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout);
            TextView txtTitle = bottomSheetDialog.findViewById(R.id.txtTitle);
            TextView txtMsg = bottomSheetDialog.findViewById(R.id.txtMsg);
            Button btnClose = bottomSheetDialog.findViewById(R.id.btnClose);

            txtTitle.setText("Attention");
            txtMsg.setText(errorMsg);

            btnClose.setOnClickListener(v -> {
                bottomSheetDialog.dismiss();
            });
            bottomSheetDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSwitch(View view) {
        Button btn = (Button) view;
        final TextInputLayout txtPass = findViewById(R.id.text_input_pass);

        if (findViewById(R.id.forget).getVisibility() == View.VISIBLE) {
            isOtp = true;
            edtPassword.setText("");
            txtPass.setHint("OTP");
            findViewById(R.id.text_input_pass).setVisibility(View.GONE);
            findViewById(R.id.forget).setVisibility(View.GONE);
            findViewById(R.id.btnOtp).setVisibility(View.VISIBLE);
            btn.setText("Switch To Password Login");
            if (!TextUtils.isEmpty(otp)) {
                findViewById(R.id.btnContinue).setVisibility(View.VISIBLE);
                findViewById(R.id.text_input_pass).setVisibility(View.VISIBLE);
            } else findViewById(R.id.btnContinue).setVisibility(View.GONE);
        } else {
            isOtp = false;
            edtPassword.setText("");
            findViewById(R.id.text_input_pass).setVisibility(View.VISIBLE);
            findViewById(R.id.forget).setVisibility(View.VISIBLE);
            findViewById(R.id.btnOtp).setVisibility(View.GONE);
            findViewById(R.id.btnContinue).setVisibility(View.VISIBLE);
            btn.setText("Switch To OTP Login");
            txtPass.setHint("Password");
        }
    }

    public void onGetOtp(View view) {
        if (isEmpty()) {
            getOtp();
        }
    }
}
