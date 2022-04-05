package com.square.pos.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.square.pos.R;
import com.square.pos.activity_motor.PaymentActivity;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.UserManager;
import com.square.pos.model.ChangeNo;
import com.square.pos.model.CommonResponse;
import com.square.pos.model.SendOtp;
import com.square.pos.utils.AppUtils;

import java.util.Objects;

public class MobileVerifyActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {

    private EditText edtOtp, edtNewNumber;
    private String otp;
    private String mobile;
    private String quotationId, url;
    private Context mContext;
    private Bundle mBundle;
    private TextView txtMobile;
    private ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verify);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mContext = this;

        txtMobile = findViewById(R.id.txtNumber);
        edtOtp = findViewById(R.id.otp);
        edtNewNumber = findViewById(R.id.edtNewNumber);

        mBundle = getIntent().getExtras();
        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");
        if (mBundle != null) {
            mobile = mBundle.getString(AppUtils.PH_MOBILE);
            quotationId = mBundle.getString(AppUtils.QUOTATION_ID);
            otp = mBundle.getString(AppUtils.OTP);
            url = mBundle.getString(AppUtils.ACKO_PAYMENT);
        }
        if (!TextUtils.isEmpty(mobile))
            txtMobile.setText(mobile);
    }
    public void onResend(View view) {
        requestOtp();
    }

    public void onVerify(View view) {
        String myOtp = edtOtp.getText().toString();
        if (TextUtils.isEmpty(myOtp)) {
            edtOtp.setError("Field can not be Empty");
            edtOtp.requestFocus();
            return;
        }
        if (myOtp.equals(otp)) {
            Intent intent = new Intent(mContext, PaymentActivity.class);
            mBundle.putString(AppUtils.ACKO_PAYMENT, url);
            intent.putExtras(mBundle);
            startActivity(intent);
        }
        else {
            edtOtp.setError("Invalid Code");
            edtOtp.requestFocus();
        }
    }

    public void requestOtp() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {

                UserManager.getInstance().verifyMobile(mContext, quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }

    public void verifyOtp() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {

                UserManager.getInstance().verifyMobileOtp(mContext, quotationId, otp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }

    public void changeNumber() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {

                UserManager.getInstance().changeNo(mContext, mobile, quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }

    public void setSession() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {

                UserManager.getInstance().setSession(mContext, quotationId, url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof SendOtp) {
            SendOtp nResponse = (SendOtp) response;
            if (nResponse.getStatus().equals(1)) {
                mBundle.putString(AppUtils.OTP, nResponse.getOtp().toString());
                otp = nResponse.getOtp().toString();
            }
        }
        if (response instanceof CommonResponse) {
            CommonResponse nResponse = (CommonResponse) response;
            if (nResponse.getSuccess().equalsIgnoreCase("1")) {
                setSession();
            }
        }
        if (response instanceof ChangeNo) {
            ChangeNo nResponse = (ChangeNo) response;
            if (nResponse.getSuccess().equalsIgnoreCase("1")) {
                requestOtp();
                findViewById(R.id.rlOtp).setVisibility(View.VISIBLE);
                findViewById(R.id.rlNewNumber).setVisibility(View.GONE);
            }
        }
        progressdialog.dismiss();
    }

    public void onUpdateNumber(View view) {
        String newNumber = edtNewNumber.getText().toString();
        if (TextUtils.isEmpty(newNumber)) {
            edtNewNumber.requestFocus();
            edtNewNumber.setError("Field can not be Empty");
        } else if (!AppUtils.isValidMobile(newNumber)) {
            edtNewNumber.requestFocus();
            edtNewNumber.setError("Invalid Number");
        } else {
            txtMobile.setText(newNumber);
            mobile = newNumber;
            changeNumber();
        }
    }

    public void onChangeMobile(View view) {
        findViewById(R.id.rlOtp).setVisibility(View.GONE);
        findViewById(R.id.rlNewNumber).setVisibility(View.VISIBLE);
    }
}
