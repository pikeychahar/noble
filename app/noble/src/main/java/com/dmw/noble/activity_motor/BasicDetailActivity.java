package com.dmw.noble.activity_motor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.activity.PrivacyActivity;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.ApiManager;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.model.SendOtp;
import com.dmw.noble.model.VehicleQuote;
import com.dmw.noble.utils.AppUtils;
import com.dmw.noble.utils.SmsBroadcastReceiver;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasicDetailActivity extends AbstractActivity implements
        onRequestCompleteCallBackListener {
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

    private static final int REQ_USER_CONSENT = 200;
    SmsBroadcastReceiver smsBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_detail);
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

        if (mBundle != null) {
            vehicleType = mBundle.getString(AppUtils.VEHICLE_TYPE);
            registrationNumber = mBundle.getString(AppUtils.REGISTRATION_NUMBER);
            newVehicle = mBundle.getString(AppUtils.NEW_VEHICLE);
            make = mBundle.getString(AppUtils.MAKE);
            model = mBundle.getString(AppUtils.MODEL);
            variant = mBundle.getString(AppUtils.VARIANT);
            regYear = mBundle.getString(AppUtils.REGISTRATION_YEAR);
            previousInsurer = mBundle.getString(AppUtils.INSURER);
            policyType = mBundle.getString(AppUtils.POLICY_TYPE);
            policyExpiry = mBundle.getString(AppUtils.POLICY_EXPIRY);
            rto = mBundle.getString(AppUtils.RTO_CODE);
            fuelType = mBundle.getString(AppUtils.FUEL_TYPE);
            prePolicy = mBundle.getString(AppUtils.IS_PREVIOUS, "0");
            pcvCompany = mBundle.getString(AppUtils.PCV_COMPANY);
            pcvType = mBundle.getString(AppUtils.PCV_TYPE);
        }

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

    public void onSearchQuote(View view) {
        if (isValid()) {
            preferences.edit().putString(AppUtils.MOBILE, userPhone).apply();
            preferences.edit().putString(AppUtils.EMAIL, userEmail).apply();

            if (mbData.equals(userPhone)) {
                String mOtp = edtOtp.getText().toString().trim();
                if (mOtp.equals(otp))
                    getQuotationId();
                else
                    Toast.makeText(mContext, "Invalid Otp", Toast.LENGTH_SHORT).show();
            } else if (isValid())
                getOtp();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        try {
            if (response instanceof VehicleQuote) {
                VehicleQuote vehicleQuote = (VehicleQuote) response;
                if (vehicleQuote.getSuccess().equalsIgnoreCase("1")) {

                    String quotationId = vehicleQuote.getQuotationId();
                    String insertId = vehicleQuote.getInsertId();
                    String vehicle = make + " " + model + " " + variant;
                    Intent intent = new Intent(mContext, DetailedVehicleActivity.class);
                    mBundle.putString(AppUtils.QUOTATION_ID, quotationId);
                    mBundle.putString(AppUtils.INSERT_ID, insertId);
                    mBundle.putString(AppUtils.NEW_VEHICLE, newVehicle);
                    mBundle.putString(AppUtils.VEHICLE_FULL, vehicle);

                    intent.putExtras(mBundle);
                    startActivity(intent);
                    progressDialog.show();
                } else
                    Toast.makeText(mContext, "" + vehicleQuote.getMsg(), Toast.LENGTH_SHORT).show();
            }
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getQuotationId() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            if (!TextUtils.isEmpty(policyType))
                policyType = policyType.toLowerCase();
            String ipAddress = "";

            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            if (wifiManager != null)
                ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

            try {
                ApiManager.getInstance().initQuotationId(mContext, ipAddress, userEmail, userPhone,
                        policyExpiry, policyType, previousInsurer, regYear, variant, model, make,
                        vehicleType, registrationNumber, userName, newVehicle, "",
                        fuelType, prePolicy, pcvCompany, pcvType, agentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_USER_CONSENT) {
            if ((resultCode == RESULT_OK) && (data != null)) {
                //That gives all message to us.
                // We need to get the code from inside with regex
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                edtOtp.setText(
                        String.format("%s - %s", getString(R.string.received_message), message));

                getOtpFromMessage(message);
            }
        }
    }

    private void getOtpFromMessage(String message) {
        // This will match any 6 digit number in the message
        Pattern pattern = Pattern.compile("(|^)\\d{6}");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            edtOtp.setText(matcher.group(0));
        }
    }

    private void registerBroadcastReceiver() {
        smsBroadcastReceiver = new SmsBroadcastReceiver();
        smsBroadcastReceiver.smsBroadcastReceiverListener =
                new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, REQ_USER_CONSENT);
                    }

                    @Override
                    public void onFailure() {

                    }
                };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(smsBroadcastReceiver);
    }
}
