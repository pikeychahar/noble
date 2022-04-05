package com.square.pos.life;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.activity.PrivacyActivity;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.UserManager;
import com.square.pos.model.SendOtp;
import com.square.pos.utils.AppUtils;

import java.util.Objects;

public class BasicLifeActivity extends AbstractActivity implements onRequestCompleteCallBackListener {
    private Context mContext;
    private SharedPreferences preferences;
    private String userName, userPhone, userEmail, vehicleType, registrationNumber, make, model,
            variant, regYear, previousInsurer, policyType, policyExpiry, newVehicle, agentId, rto,
            prePolicy, fuelType, pcvCompany, pcvType, otp, mbData;
    private EditText edtName, edtPhone, edtEmail, edtOtp;
    private CheckBox chkPrivacy;
    private Button btnContinue;
    private Bundle mBundle;
    private ProgressDialog progressDialog;
    Button btnOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_life);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;

        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        agentId = preferences.getString(AppUtils.PRIMARY_ID, "");
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);
        edtOtp = findViewById(R.id.edtOtp);
        btnOtp = findViewById(R.id.btnOtp);

        chkPrivacy = findViewById(R.id.chkTerm);
        btnContinue = findViewById(R.id.btnContinue);

        userName = preferences.getString(AppUtils.NAME, "");
        userPhone = preferences.getString(AppUtils.MOBILE, "");
        userEmail = preferences.getString(AppUtils.EMAIL, "");
        mBundle = getIntent().getExtras();

        mBundle = new Bundle();

        if (!chkPrivacy.isChecked()) {
            btnContinue.setAlpha((float) 0.6);
            btnContinue.setClickable(false);
        }
        if (!TextUtils.isEmpty(userName))
            edtName.setText(userName);

        if (!TextUtils.isEmpty(userPhone))
            edtPhone.setText(userPhone);

        if (!TextUtils.isEmpty(userEmail))
            edtEmail.setText(userEmail);

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("One Moment...");
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
        userName = edtName.getText().toString().trim();
        userPhone = edtPhone.getText().toString();
        userEmail = edtEmail.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            edtName.setError("Could not be empty");
            edtName.requestFocus();
            return false;
        }
        if (!AppUtils.validateName(userName)) {
            edtName.setError("Invalid Name");
            edtName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(userPhone)) {
            edtPhone.setError("Could not be empty");
            edtPhone.requestFocus();
            return false;
        }
        if (!AppUtils.isValidMobile(userPhone)) {
            edtPhone.setError("Invalid Number");
            edtPhone.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(userEmail)) {
            edtEmail.setError("Could not be empty");
            edtEmail.requestFocus();
            return false;
        }
        if (!AppUtils.isValidMail(userEmail)) {
            edtEmail.setError("Invalid Email");
            edtEmail.requestFocus();
            return false;
        }
        if (!AppUtils.validateName(userName)) {
            edtName.setError("Invalid Name");
            edtName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(registrationNumber))
            registrationNumber = rto;

        return true;
    }

    private void resendCounter() {

        new CountDownTimer(50000, 1000) {
            public void onTick(long millisUntilFinished) {
                btnOtp.setText("Resent in: " + millisUntilFinished / 1000);
                btnOtp.setClickable(false);
                btnOtp.setAlpha((float) 0.7);
            }

            public void onFinish() {
                btnOtp.setText("Resend");
                btnOtp.setClickable(true);
                btnOtp.setAlpha((float) 1);
            }

        }.start();
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof SendOtp) {
            SendOtp vResponse = (SendOtp) response;
            if (vResponse.getStatus() == 1) {
                otp = String.valueOf(vResponse.getOtp());
                findViewById(R.id.txtOtp).setVisibility(View.VISIBLE);
                findViewById(R.id.btnContinue).setVisibility(View.VISIBLE);
                edtOtp.setText("");
                mbData = userPhone;
                resendCounter();
            }
        }
        progressDialog.dismiss();
    }

    public void getOtp() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                UserManager.getInstance().changePassOtp(mContext, userPhone);
            } catch (Exception e) {
                e.printStackTrace();

            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void onGetOtp(View view) {
        if (isValid()) {
            getOtp();
        }
    }

    public void onSearchQuote(View view) {
        if (isValid()) {
            preferences.edit().putString(AppUtils.MOBILE, userPhone).apply();
            preferences.edit().putString(AppUtils.EMAIL, userEmail).apply();

            if (mbData.equals(userPhone)) {
                String mOtp = edtOtp.getText().toString().trim();
                if (mOtp.equals(otp)) {
                    Intent intent = new Intent(mContext, PreQuoteLifeActivity.class);
                    mBundle.putString(AppUtils.NAME, userName);
                    mBundle.putString(AppUtils.MOBILE, userPhone);
                    mBundle.putString(AppUtils.EMAIL, userEmail);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                } else
                    Toast.makeText(mContext, "Invalid Otp", Toast.LENGTH_SHORT).show();
            } else if (isValid())
                getOtp();
        }
    }

    public void onPrivacy(View view) {
        startActivity(new Intent(mContext, PrivacyActivity.class));
    }

    public void onPrivacyTerm(View view) {
        if (chkPrivacy.isChecked()) {
            btnContinue.setAlpha((float) 1);
            btnContinue.setClickable(true);
        } else {
            btnContinue.setAlpha((float) 0.6);
            btnContinue.setClickable(false);
        }
    }
}