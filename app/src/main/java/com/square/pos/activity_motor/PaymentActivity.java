package com.square.pos.activity_motor;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AlertDialog;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.activity.DashboardActivity;
import com.square.pos.utils.AppUtils;

public class PaymentActivity extends AbstractActivity {

    private ProgressDialog pd;
    private Context mContext;
    private WebView mWebView;
    private Bundle mBundle;
    private String url, quotationId, responseUrl;
    int flag = 0;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mWebView = findViewById(R.id.webView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        mContext = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mBundle = getIntent().getExtras();
        if (mBundle != null) {
            url = mBundle.getString(AppUtils.ACKO_PAYMENT);
            quotationId = mBundle.getString(AppUtils.QUOTATION_ID);
        }

        pd = new ProgressDialog(mContext);
        pd.setMessage("Please Wait....");
        pd.show();

        mWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {


            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pd.dismiss();
            }

        });

        mWebView.loadUrl(url);
        mWebView.setWebChromeClient(new WebChromeClient() {
                                        @Override
                                        public void onProgressChanged(WebView view, int newProgress) {
                                            if (mWebView.getUrl().contains("https://www.squareinsurance.in/payment")) {
                                                responseUrl = mWebView.getUrl();
                                                String cmp = responseUrl.substring(0, 38);
                                                if (cmp.contains("https://www.squareinsurance.in/payment")) {
                                                    Intent intent = new Intent(mContext, PaymentStatusActivity.class);
                                                    mBundle.putString(AppUtils.QUOTATION_ID,quotationId);
                                                    mBundle.putString(AppUtils.RESPONSE_URL,responseUrl);
                                                    intent.putExtras(mBundle);
                                                    if (flag==0) {
                                                        startActivity(intent);
                                                        ++flag;
                                                    }
                                                }
                                            }
                                            super.onProgressChanged(view, newProgress);
                                        }
                                    });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.payment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_home:
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
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(mContext, DashboardActivity.class));
                                finishAffinity();
                            }
                        })
                .setNegativeButton(R.string.no, null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

