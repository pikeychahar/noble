package com.square.pos.activity.health;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.activity.PrivacyActivity;
import com.square.pos.activity.travel.GeographicalActivity;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.UserManager;
import com.square.pos.model.SendOtp;
import com.square.pos.utils.AppUtils;

import java.util.Objects;

public class HealthViewActivity extends AbstractActivity implements onRequestCompleteCallBackListener {

    Context mContext;
    EditText edtName, edtEmail, edtPhone, edtOtp;
    String name, email, phone, mbData, userId, otp, healthTravel;
    Bundle mBundle;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_view);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        String name, mobile, email;

        name = preferences.getString(AppUtils.NAME, "");
        mobile = preferences.getString(AppUtils.MOBILE, "");
        email = preferences.getString(AppUtils.EMAIL, "");
        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        healthTravel = preferences.getString(AppUtils.TRAVEL_HEALTH, "");

        mContext = this;
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtOtp = findViewById(R.id.edtOtp);

        mBundle = new Bundle();

        edtName.setText(name);
        edtPhone.setText(mobile);
        edtEmail.setText(email);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onTermsClick(View view) {
        startActivity(new Intent(mContext, PrivacyActivity.class));
    }


    private boolean isValid() {
        name = edtName.getText().toString();
        phone = edtPhone.getText().toString();
        email = edtEmail.getText().toString();
        if (TextUtils.isEmpty(name)) {
            edtName.setError("Could not be empty");
            edtName.requestFocus();
            return false;
        } else if (!AppUtils.validateName(name)) {
            edtName.setError(getString(R.string.invalid_name));
            edtName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(phone)) {
            edtPhone.setError("Could not be empty");
            edtPhone.requestFocus();
            return false;
        }
        if (!AppUtils.isValidMobile(phone)) {
            edtPhone.setError("Invalid Number");
            edtPhone.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Could not be empty");
            edtEmail.requestFocus();
            return false;
        }
        if (!AppUtils.isValidMail(email)) {
            edtEmail.setError("Invalid Email");
            edtEmail.requestFocus();
            return false;
        }
        return true;
    }

    public void onHealthContinue(View view) {
        if (isValid()) {

            Intent intent = new Intent();
            if (healthTravel.equals("health"))
                intent = new Intent(mContext, CitySumActivity.class);
            else
                intent = new Intent(mContext, GeographicalActivity.class);

            mBundle.putString(AppUtils.HL_Name, name);
            mBundle.putString(AppUtils.HL_PHONE, phone);
            mBundle.putString(AppUtils.HL_EMAIL, email);

            intent.putExtras(mBundle);

            if (!TextUtils.isEmpty(userId)) {
                startActivity(intent);
            } else {
                if (TextUtils.isEmpty(otp))
                    getOtp();
                else {
                    String mOtp = edtOtp.getText().toString();
                    if (mbData.equals(phone)) {
                        if (mOtp.equals(otp))
                            startActivity(intent);
                        else
                            edtOtp.setError("OTP does not match");
                    } else if (isValid())
                        getOtp();
                }
            }
        }
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof SendOtp) {
            SendOtp vResponse = (SendOtp) response;
            if (vResponse.getStatus() == 1) {
                otp = String.valueOf(vResponse.getOtp());
                findViewById(R.id.txtOtp).setVisibility(View.VISIBLE);
                edtOtp.setText("");
                mbData = phone;
            }
        }
    }

    public void getOtp() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().changePassOtp(mContext, phone);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

}
