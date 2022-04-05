package com.dmw.noble.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dmw.noble.R;
import com.dmw.noble.utils.AppUtils;

import java.util.Objects;

/**
 * Created by Prahalad Kumar Chahar
 */
public class PrivacyActivity extends AbstractActivity {
    ProgressDialog pd;
    Context mContext;
    String url = "https://www.squareinsurance.in/Privacypolicy/privacypolicyApp";
    WebView mWebView;
    Bundle mBundle;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        this.setTitle("Privacy and Policy");
        mContext = this;
        String link;
        Intent intent = getIntent();
        if (intent != null) {
            link = intent.getStringExtra(AppUtils.TERM_LINK);
            if (!TextUtils.isEmpty(link)) {
                url = link;
                this.setTitle("Terms and Conditions");
            } else url = "https://www.squareinsurance.in/Privacypolicy/privacypolicyApp";
        }

        mBundle = Objects.requireNonNull(getIntent()).getExtras();
        if (mBundle != null) {
            String type = mBundle.getString(AppUtils.TYPE, "1");
            if (!TextUtils.isEmpty(type)) {
                switch (type) {
                    case "2":
                        this.setTitle("Terms And Conditions");
                        url = mBundle.getString(AppUtils.TERM);
                        break;
                    case "3":
                        this.setTitle("Grievance Redress Policy");
                        url = mBundle.getString(AppUtils.GRIEVANCE);
                        break;
                    case "4":
                        this.setTitle("Refund And Cancellation Policy");
                        url = mBundle.getString(AppUtils.REFUND);
                        break;
                    default:
                        this.setTitle("Privacy and Policy");
                        break;
                }
            }
        }

        mWebView = findViewById(R.id.webView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mContext = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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

                                            super.onProgressChanged(view, newProgress);
                                        }
                                    }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}