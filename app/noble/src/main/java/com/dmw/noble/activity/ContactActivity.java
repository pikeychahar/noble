package com.dmw.noble.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.dmw.noble.R;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.model.BasicResponse;
import com.dmw.noble.model.CommonResponse;
import com.dmw.noble.utils.AppUtils;

import java.util.Objects;

public class ContactActivity extends AbstractActivity implements onRequestCompleteCallBackListener {
    Context mContext;
    EditText edtName, edtPhone, edtEmail, edtMsg;
    String name, email, mobile, remarks, leadType, queryType, userType, userId;
    ProgressDialog progressdialog;
    Bundle mBundle;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mContext = this;
        SharedPreferences preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");
        mBundle = new Bundle();

        edtName = findViewById(R.id.edtUserName);
        edtPhone = findViewById(R.id.edtUserMobile);
        edtEmail = findViewById(R.id.edtUserEmail);
        edtMsg = findViewById(R.id.edtRemark);
        intent = new Intent(mContext, PrivacyActivity.class);

        Intent intent = getIntent();
        if (intent != null)
            queryType = intent.getStringExtra(AppUtils.QUERY_TYPE);
        if (!TextUtils.isEmpty(queryType)) {
            TextView label = findViewById(R.id.txtOrg);
            label.setText("Enter Your Basic Detail");
            if (queryType.equals("life")) {
                leadType = queryType;
                this.setTitle(leadType.toUpperCase());
            } else if (queryType.equals("travel")) {
                leadType = queryType;
                this.setTitle(leadType.toUpperCase());
            }
        }
    }

    public void onSubmitClick(View view) {
        if (isValid())
            if (!TextUtils.isEmpty(leadType))
                raiseLifeTravelQuery();
            else
                submitData();

    }

    public void onLinkedInClick(View view) {
        String url = "https://in.linkedin.com/company/square-insurance-brokers";
        openChrome(url);
    }

    public void onInstaClick(View view) {
        String url = "https://www.instagram.com/insurancesquare/";
        openChrome(url);
    }

    public void onTwitterClick(View view) {
        String url = "https://twitter.com/sibpl_gic";
        openChrome(url);
    }

    public void onFacebookClick(View view) {
        String url = "https://www.facebook.com/squareinsurance/";
        openChrome(url);
    }


    public void onWhatsApp(View view) {
        String smsNumber = "918441990000";
        boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
        if (isWhatsappInstalled) {

            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp",
                    "com.whatsapp.Conversation"));

            sendIntent.putExtra("jid",
                    PhoneNumberUtils.stripSeparators(smsNumber) + "@s.whatsapp.net");

            startActivity(sendIntent);
        } else {
            Uri uri = Uri.parse("market://details?id=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            Toast.makeText(this, "WhatsApp not Installed",
                    Toast.LENGTH_SHORT).show();
            startActivity(goToMarket);
        }
    }

    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public void onAddressClick(View view) {
        String url = "https://maps.app.goo.gl/gP5P7HykwnC5bcuz7";
        openChrome(url);
    }

    public void onWebClick(View view) {
        String url = "https://www.squareinsurance.in";
        openChrome(url);

    }

    public void onYoutubeClick(View view) {
        String url = "https://www.youtube.com/channel/UCnX-8X14onYFRjNz_S5XCJA";
        openChrome(url);
    }
    private void openChrome(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    public void onPhoneCall(View view) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:18001205430"));
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

    public void onEmail(View view) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("mailto:" + "info@squareinsurance.in"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "");
            intent.putExtra(Intent.EXTRA_TEXT, "");
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            //TODO smth
        }
    }

    private boolean isValid() {

        name = edtName.getText().toString().trim();
        mobile = edtPhone.getText().toString().trim();
        email = edtEmail.getText().toString().trim();
        remarks = edtMsg.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            edtName.setError("Field cannot be Empty");
            return false;
        } else if (TextUtils.isEmpty(mobile)) {
            edtPhone.setError("Field cannot be Empty");
            return false;
        } else if (!(AppUtils.isValidMobile(mobile))) {
            edtPhone.setError("Invalid Phone Number");
            edtPhone.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Field cannot be Empty");
            return false;
        } else if (!(AppUtils.isValidMail(email))) {
            edtEmail.setError("Invalid Email");
            edtEmail.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(remarks)) {
            edtMsg.setError("Field cannot be Empty");
            return false;
        }

        return true;
    }

    public void submitData() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().getQuery(mContext, name,
                        mobile, email, remarks);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void raiseLifeTravelQuery() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().raiseLifeTravelQuery(mContext, name, mobile, email,
                        userId, userType, remarks, leadType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void submitDialog() {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle((R.string.app_name));
        alertDialogBuilder.setIcon(R.drawable.app_logo);
        alertDialogBuilder.setMessage("Your Request saved successfully!");
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.ok,
                        (dialog, id) -> {
                            edtName.getText().clear();
                            edtEmail.getText().clear();
                            edtPhone.getText().clear();
                            edtMsg.getText().clear();
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof CommonResponse) {
            CommonResponse response1 = (CommonResponse) response;
            if (response1.getSuccess().equals("1")) {
                submitDialog();
            } else {
                if (!TextUtils.isEmpty(response1.getMsg()))
                    Toast.makeText(mContext, "" + (response1).getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
        if (response instanceof BasicResponse) {
            BasicResponse response1 = (BasicResponse) response;
            if (response1.getStatus().equals(1)) {
                submitDialog();
            } else {
                if (!TextUtils.isEmpty(response1.getMsg()))
                    Toast.makeText(mContext, "" + (response1).getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
        progressdialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onPrivacyClick(View view) {

        mBundle.putString(AppUtils.PRIVACY, AppUtils.PRIVACY_LINK);
        mBundle.putString(AppUtils.TYPE, "1");
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    public void onTermsClick(View view) {
        mBundle.putString(AppUtils.TERM, AppUtils.TERM_LINK);
        mBundle.putString(AppUtils.TYPE, "2");
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    public void onGrievanceClick(View view) {
        mBundle.putString(AppUtils.GRIEVANCE, AppUtils.GRIEVANCE_LINK);
        mBundle.putString(AppUtils.TYPE, "3");
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    public void onRefundClick(View view) {
        mBundle.putString(AppUtils.REFUND, AppUtils.REFUND_LINK);
        mBundle.putString(AppUtils.TYPE, "4");
        intent.putExtras(mBundle);
        startActivity(intent);
    }

}