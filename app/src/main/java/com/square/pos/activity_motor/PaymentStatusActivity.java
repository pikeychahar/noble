package com.square.pos.activity_motor;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.activity.DashboardActivity;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.UserManager;
import com.square.pos.model.PaymentTrack;
import com.square.pos.utils.AppUtils;
import com.square.pos.utils.DownloadTask;

public class PaymentStatusActivity extends AbstractActivity implements onRequestCompleteCallBackListener {
    TextView txtName, txtStatus, txtTime, txtCompany;
    Context mContext;
    Bundle mBundle;
    ProgressDialog progressdialog;
    String quotationId, pdfUrl, time, name, email, paymentStatus, responseUrl;
    int delayMillis = 1000;
    String company;
    Handler handler;
    com.airbnb.lottie.LottieAnimationView viewStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_status);

        mContext = this;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setBackgroundDrawable(new ColorDrawable(getColor(R.color.colorPrimaryDark)));
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
            TextView textView = new TextView(this);
            textView.setText(R.string.payment_status);
            textView.setTextSize(20);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(getResources().getColor(android.R.color.white, null));
            mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            mActionBar.setCustomView(textView);
        }

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Policy PDF Generating...");
        progressdialog.setCancelable(true);

        mBundle = getIntent().getExtras();
        txtName = findViewById(R.id.txtName);
//        txtEmail = findViewById(R.id.txtEmail);
        txtStatus = findViewById(R.id.txtStatus);
        txtTime = findViewById(R.id.txtTime);
        txtCompany = findViewById(R.id.txtCompany);
        viewStatus = findViewById(R.id.viewStatus);

        TextView txtQuoteId = findViewById(R.id.txtQuoteId);

        if (mBundle != null) {

            quotationId = mBundle.getString(AppUtils.QUOTATION_ID);
            responseUrl = mBundle.getString(AppUtils.RESPONSE_URL);
            company = mBundle.getString(AppUtils.COMPANY_NAME);
            txtQuoteId.setText(quotationId);
            if (!TextUtils.isEmpty(company)) {
                txtCompany.setText(company.toUpperCase());
                if (company.equalsIgnoreCase("digit")
                        || company.equalsIgnoreCase("hdfc"))
                    delayMillis = 8000;
            }

        }
        delayTime();
    }

    public void onDownloadPolicy(View view) {

        if (!TextUtils.isEmpty(pdfUrl)) {
            if (company.equalsIgnoreCase("iffco")) {

                AlertDialog.Builder alertDialogBuilder =
                        new AlertDialog.Builder(mContext);
                alertDialogBuilder.setTitle("Attention !!");
                alertDialogBuilder.setIcon(R.drawable.logo_app);
                alertDialogBuilder.setMessage("Dear Customer you will get Iffco policy via mail and you can download the policy from account after 10 minutes.");
                alertDialogBuilder
                        .setCancelable(true)
                        .setPositiveButton(R.string.menu_home,
                                (dialog, id) -> {
                                    startActivity(new Intent(mContext, DashboardActivity.class));
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            } else
                new DownloadTask(mContext, pdfUrl);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.payment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.action_home) {
            exitActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void exitActivity() {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle((R.string.app_name));
        alertDialogBuilder.setIcon(R.drawable.logo_app);
        alertDialogBuilder.setMessage("Do you want to Exit from here!");
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton(R.string.yes,
                        (dialog, id) -> {
                            startActivity(new Intent(mContext, DashboardActivity.class));
                            finishAffinity();
                        })
                .setNegativeButton(R.string.no, null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onResponse(Object response) {
        try {
            if (response instanceof PaymentTrack) {
                PaymentTrack nResponse = (PaymentTrack) response;
                if (!TextUtils.isEmpty(nResponse.getStatus())) {
                    name = nResponse.getFullName();
                    email = nResponse.getEmail();
                    time = nResponse.getPaymentTime();
                    paymentStatus = nResponse.getPaymentStatus();
                    pdfUrl = nResponse.getPolicyDocument();
                    setValues();
                    progressdialog.hide();
                }
            }
        } catch (Exception e) {
            AppUtils.showToast(mContext, "Something went wrong");
            e.printStackTrace();
        }
    }

    private void setValues() {
        txtName.setText(name);
        txtTime.setText(time);
        if (!TextUtils.isEmpty(paymentStatus))
            if (paymentStatus.equalsIgnoreCase("1")) {
                viewStatus.setAnimationFromUrl("https://assets1.lottiefiles.com/packages/lf20_sO9js3.json");
                viewStatus.setRepeatCount(2);
                txtStatus.setText(getString(R.string.success));

            } else {
                viewStatus.setAnimationFromUrl("https://assets6.lottiefiles.com/packages/lf20_imrP4H.json");
                viewStatus.setRepeatCount(2);
                txtStatus.setText(getString(R.string.failed));
            }

        if (!TextUtils.isEmpty(pdfUrl)) {
            findViewById(R.id.rlMail).setVisibility(View.GONE);
            findViewById(R.id.rlDp).setVisibility(View.VISIBLE);
            findViewById(R.id.txtRetry).setVisibility(View.GONE);

        } else findViewById(R.id.txtRetry).setVisibility(View.VISIBLE);
    }

    private void delayTime() {
        progressdialog.show();
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(this::getPaymentStatus, delayMillis);
    }

    public void getPaymentStatus() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().getPaymentTrack(mContext, quotationId, responseUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }

    public void onHomeClick(View view) {
        exitActivity();
    }

    public void onRateUS(View view) {
        Uri uri = Uri.parse("market://details?id=" + mContext.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id="
                            + mContext.getPackageName())));
        }
    }

    public void onRetryClick(View view) {
        getPaymentStatus();
    }

    public void onRaiseMail(View view) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("mailto:" + "support@squareinsurance.in"));
            intent.putExtra(Intent.EXTRA_SUBJECT, quotationId);
            intent.putExtra(Intent.EXTRA_TEXT, "");
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mContext, "Something went wrong!!", Toast.LENGTH_SHORT).show();
        }
    }

}
