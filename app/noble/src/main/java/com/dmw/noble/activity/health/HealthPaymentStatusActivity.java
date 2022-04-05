package com.dmw.noble.activity.health;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.activity.DashboardActivity;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.Health.HealthManager;
import com.dmw.noble.model_health.v2.HealthPaymentTrack;
import com.dmw.noble.utils.AppUtils;
import com.dmw.noble.utils.DownloadTask;

public class HealthPaymentStatusActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {

    private Context mContext;
    String pdfUrl, quotationId;
    ProgressDialog progressdialog;
    Bundle mBundle;
    int delayMillis = 3000;
    TextView txtPaymentStatus, txtQId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_payment_status);
        mContext = this;
        this.setTitle("Payment Status");

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Policy PDF Generating...");
        progressdialog.setCancelable(true);

        mBundle = getIntent().getExtras();
        txtPaymentStatus = findViewById(R.id.txtPaymentStatus);
        txtQId = findViewById(R.id.txtQId);

        if (mBundle != null) {
            quotationId = mBundle.getString(AppUtils.QUOTATION_ID_1);
            if (!TextUtils.isEmpty(quotationId)) {
                delayTime();
                txtQId.setText(quotationId);
            }
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

    public void onDownloadPolicy(View view) {
        if (!TextUtils.isEmpty(pdfUrl)) {
            new DownloadTask(mContext, pdfUrl);
        }
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof HealthPaymentTrack) {
            HealthPaymentTrack nResponse = (HealthPaymentTrack) response;

            if (!TextUtils.isEmpty(nResponse.getMessage())) {
                pdfUrl = nResponse.getUrl();
                if (TextUtils.isEmpty(pdfUrl))
                    findViewById(R.id.btnDownload).setVisibility(View.GONE);
                else findViewById(R.id.btnDownload).setVisibility(View.VISIBLE);

                String paymentStatus = nResponse.getPaymentStatus();
                if ((!TextUtils.isEmpty(paymentStatus)) && paymentStatus.equalsIgnoreCase("1"))
                    txtPaymentStatus.setText("Payment Success");
                else txtPaymentStatus.setText(nResponse.getMessage());
            }
            progressdialog.dismiss();
        }
    }

    public void getPaymentStatus() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                HealthManager.getInstance().getPaymentHealthTrack(mContext, quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }

    private void delayTime() {
        progressdialog.show();
        final Handler handler = new Handler();
        handler.postDelayed(() -> getPaymentStatus(), delayMillis);
    }
}
