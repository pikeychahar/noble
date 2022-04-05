package com.square.pos.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.square.pos.R;
import com.square.pos.activity_motor.OwnerDetailActivity;
import com.square.pos.utils.AppUtils;
/**
 * Created by Prahalad Kumar Chahar
 */
public abstract class AbstractActivity extends AppCompatActivity {
    public SharedPreferences preferences;
    public ProgressDialog progressdialog;
    public String posStatus, name, mobile;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        progressdialog = new ProgressDialog(this);
        progressdialog.setTitle("Please wait..");
        posStatus = preferences.getString(AppUtils.POS_STATUS, "");

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    public void onPhoneCall(View view) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:18001205430"));
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            return;
        }
        startActivity(callIntent);
    }

    public void onOwnerDetail(View view) {
        startActivity(new Intent(getBaseContext(), OwnerDetailActivity.class));
    }

    public void onMessage(View view) {
        Uri sms_uri = Uri.parse("smsto:8441990000");
        Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
        sms_intent.putExtra("sms_body", "");
        startActivity(sms_intent);
    }

    public void onEmail(View view) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "info@squareinsurance.in"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "");
            intent.putExtra(Intent.EXTRA_TEXT, "");
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            //TODO smth
        }
    }

    public void onWhatsApp(View view) {
        String smsNumber = "918441990000";
        boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
        if (isWhatsappInstalled) {

            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(smsNumber) + "@s.whatsapp.net");//phone number without "+" prefix

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
}
