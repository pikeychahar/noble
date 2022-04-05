package com.square.pos.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.square.pos.R;

import java.util.Objects;

public class ViewPdfActivity extends AbstractActivity {
    String path;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);
        Objects.requireNonNull(getSupportActionBar()).hide();
        mContext = this;

        Intent intent = getIntent();
        path = intent.getStringExtra("path");

        if (!TextUtils.isEmpty(path)) {
            System.out.println(path);

            WebView mWebView = (WebView) findViewById(R.id.mWebView);
            ProgressDialog pd = new ProgressDialog(mContext);
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

            String url = "http://docs.google.com/gview?embedded=true&url=" + path;
            mWebView.loadUrl(url);
        }
    }
}