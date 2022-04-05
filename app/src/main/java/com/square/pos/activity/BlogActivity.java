package com.square.pos.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.square.pos.R;
import com.square.pos.utils.AppUtils;

import java.util.Objects;

public class BlogActivity extends AbstractActivity {
    private ProgressDialog pd;
    Context mContext;
    WebView mWebView;
    Bundle mBundle;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        Objects.requireNonNull(getSupportActionBar()).hide();

        mWebView = findViewById(R.id.webView);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        mContext = this;
        mBundle = getIntent().getExtras();
        if (mBundle != null) {
            url = mBundle.getString(AppUtils.BLOG_URL);
        }
        pd = new ProgressDialog(mContext);
        pd.setMessage("Please Wait....");
        pd.show();

        mWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description,
                                        String failingUrl) {
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
    }

}
