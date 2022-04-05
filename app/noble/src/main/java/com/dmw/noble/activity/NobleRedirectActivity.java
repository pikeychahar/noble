package com.dmw.noble.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.dmw.noble.R;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.model.Login;
import com.dmw.noble.utils.AppUtils;

import java.util.Objects;

public class NobleRedirectActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {

    Context mContext;
    String mobile, encodedId;
    ProgressDialog progressdialog;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noble_redirect);
        Objects.requireNonNull(getSupportActionBar()).hide();

        mContext = this;
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");

        Intent intent = getIntent();
        if (intent != null) {
            encodedId = intent.getStringExtra("ENCODED_ID");//POS Certified
            mobile = intent.getStringExtra("MOBILE");//SUB POS

            AppUtils.showToast(mContext, encodedId);
            if (TextUtils.isEmpty(mobile))
                postLogin();
            else subPosLogin();
        }
    }

    public void postLogin() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().nobleLogin(mContext, encodedId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void subPosLogin() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().nobleSubPosLogin(mContext, encodedId, mobile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
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
                            String email = loginResponse.getEmail();
                            String agentId = loginResponse.getAgentId();
                            String userId = loginResponse.getId();
                            String spId = loginResponse.getSpId();
                            String empId = loginResponse.getEmployeeId();
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
                            preferences.edit().putString(AppUtils.SP_ID, spId).apply();
                            preferences.edit().putString(AppUtils.USER_TYPE, userType).apply();
                            preferences.edit().putString(AppUtils.USER_PASS, "").apply();
                            preferences.edit().putString(AppUtils.REFERENCE_ID, referenceId).apply();
                            preferences.edit().putString(AppUtils.EMP_ID, empId).apply();
                            preferences.edit().putString(AppUtils.POS_PERMISSION, addPosPermission).apply();
                            preferences.edit().putString(AppUtils.USER_BADGE, userBadge).apply();
                            preferences.edit().putString(AppUtils.BADGE_COLOR, badgeColor).apply();
                            preferences.edit().putString(AppUtils.IS_AGREEMENT_POS, isAgreed).apply();
                            preferences.edit().putString(AppUtils.MOBILE_PERMISSION, mobilePermission).apply();
                            preferences.edit().putString(AppUtils.PRIMARY_ID, userId).apply();
                            preferences.edit().putString(AppUtils.POS_STATUS, posStatus).apply();
                            preferences.edit().putString(AppUtils.LOGIN_TOKEN, token).apply();
                            finish();
                        }
                    }
                }
            }
            progressdialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
