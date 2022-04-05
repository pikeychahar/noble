package com.dmw.noble.activity.health;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dmw.noble.R;
import com.dmw.noble.activity.DashboardActivity;
import com.dmw.noble.utils.AppUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HealthPaymentActivity extends AppCompatActivity {
    private ProgressDialog pd;
    private Context mContext;
    private WebView mWebView;
    private Bundle mBundle;
    String url, quotationId;
    int flag = 0;

    public static final int REQUEST_CODE_LOLIPOP = 1;
    private final static int RESULT_CODE_ICE_CREAM = 2;
    private ValueCallback<Uri[]> mFilePathCallback;
    private String mCameraPhotoPath;
    private ValueCallback<Uri> mUploadMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_payment);

        mWebView = findViewById(R.id.webView);
        WebSettings settings = mWebView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        WebView.setWebContentsDebuggingEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());

        mContext = this;
        mBundle = getIntent().getExtras();

        if (mBundle != null) {
            url = mBundle.getString(AppUtils.HEALTH_LINK);
        }

        pd = new ProgressDialog(mContext);
        pd.setMessage("Please Wait....");

        mWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                pd.show();
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
                                            if (mWebView.getUrl().contains("https://www.squareinsurance.in/health/success/")) {
                                                Intent intent = new Intent(mContext, HealthPaymentStatusActivity.class);
                                                intent.putExtras(mBundle);
                                                startActivity(intent);
                                                finish();
                                            } else if (mWebView.getUrl().contains("https://www.squareinsurance.in/health/failed/")) {
                                                Intent intent = new Intent(mContext, HealthPaymentStatusActivity.class);
                                                intent.putExtras(mBundle);
                                                startActivity(intent);
                                                finish();
                                            }
                                            super.onProgressChanged(view, newProgress);
                                        }
                                    }
        );
        mWebView.setWebChromeClient(new WebChromeClient() {
            private String TAG;

            public boolean onShowFileChooser(
                    WebView webView, ValueCallback<Uri[]> filePathCallback,
                    FileChooserParams fileChooserParams) {
                if (mFilePathCallback != null) {
                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = filePathCallback;
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(HealthPaymentActivity.this.getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        Log.e(TAG, "Unable to create Image File", ex);
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                    } else {
                        takePictureIntent = null;
                    }
                }
                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("image/*");
                Intent[] intentArray;
                if (takePictureIntent != null) {
                    intentArray = new Intent[]{takePictureIntent};
                } else {
                    intentArray = new Intent[0];
                }
                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

                startActivityForResult(chooserIntent, REQUEST_CODE_LOLIPOP);

                return true;
            }
        });
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_CODE_ICE_CREAM:
                Uri uri = null;
                if (data != null) {
                    uri = data.getData();
                }
                mUploadMessage.onReceiveValue(uri);
                mUploadMessage = null;
                break;
            case REQUEST_CODE_LOLIPOP:
                Uri[] results = null;
                // Check that the response is a good one
                if (resultCode == Activity.RESULT_OK) {
                    if (data == null) {
                        // If there is not data, then we may have taken a photo
                        if (mCameraPhotoPath != null) {
                            results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                        }
                    } else {
                        String dataString = data.getDataString();
                        if (dataString != null) {
                            results = new Uri[]{Uri.parse(dataString)};
                        }
                    }
                }
                mFilePathCallback.onReceiveValue(results);
                mFilePathCallback = null;
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (itemId == R.id.action_home) {
            exitActivity();
            return true;
        } else if (itemId == R.id.action_share) {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Square Insurance");
                String msg = mWebView.getUrl();
                shareIntent.putExtra(Intent.EXTRA_TEXT, msg);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }
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
