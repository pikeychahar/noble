package com.dmw.noble.activity_crm;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.CrmManager;
import com.dmw.noble.model_crm.ChildPosOtp;
import com.dmw.noble.model_crm.ResponseBool;
import com.dmw.noble.utils.AppUtils;

import java.util.Objects;

public class NewMobileActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {

    Context mContext;
    SharedPreferences preferences;
    String userId, userType, mobileData, otp;
    int count = 0;
    ProgressDialog progressDialog;

    public EditText edtAddMobile, edtOtp;
    Button btnSubmit;
    TextInputLayout txtInput, txtOtp;
    boolean sentOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mobile);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        preferences = getSharedPreferences(String.valueOf(R.string.app_name),
                MODE_PRIVATE);

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");

        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");
        edtAddMobile = findViewById(R.id.edtAddMobile);
        btnSubmit = findViewById(R.id.btnSubmit);
        txtInput = findViewById(R.id.txtInput);
        txtOtp = findViewById(R.id.txtOtp);
        edtOtp = findViewById(R.id.edtOtp);

        edtAddMobile.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {
                String mobile = edtAddMobile.getText().toString();
                if (!(AppUtils.isValidMobile(mobile))) {
                    txtInput.setErrorEnabled(true);
                    txtInput.setError("Invalid Phone Number");
                } else {
                    txtInput.setErrorEnabled(false);
                }
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof ResponseBool) {
            ResponseBool policyResponse = (ResponseBool) response;
            if (policyResponse.getStatus()) {
                progressDialog.dismiss();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                AppUtils.showToast(mContext, "" + policyResponse.getMessage());
                finish();
            } else AppUtils.showToast(mContext, "" + policyResponse.getMessage());
        }

        if (response instanceof ChildPosOtp) {
            ChildPosOtp policyResponse = (ChildPosOtp) response;
            if (policyResponse.getStatus()) {
                sentOtp = true;
                edtAddMobile.setFocusable(false);
                txtOtp.setVisibility(View.VISIBLE);
                btnSubmit.setText("Submit");
                AppUtils.showToast(mContext, "" + policyResponse.getMessage());
            } else AppUtils.showToast(mContext, "" + policyResponse.getMessage());
        }
        progressDialog.dismiss();
    }

    public void raiseMobile() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                CrmManager.getInstance().newChildPos(mContext, userId, userType, mobileData, otp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getChildPosOtp() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                CrmManager.getInstance().getChildPosOtp(mContext, userId, userType, mobileData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void onMobileSubmit(View view) {

        AppUtils.checkSoftKeyboard(this);
        mobileData = edtAddMobile.getText().toString();
        otp = edtOtp.getText().toString();

        if (!TextUtils.isEmpty(mobileData)) {
            if (!(AppUtils.isValidMobile(mobileData))) {
                txtInput.setErrorEnabled(true);
                txtInput.setError("Invalid Phone Number");
            } else if (sentOtp) {
                if (TextUtils.isEmpty(otp) || otp.length() != 6) {
                    edtOtp.setError(getString(R.string.invalid_otp));
                    edtOtp.requestFocus();
                } else
                    raiseMobile();
            } else
                getChildPosOtp();
        } else
            edtAddMobile.setError(getString(R.string.field_cannot));
    }
}