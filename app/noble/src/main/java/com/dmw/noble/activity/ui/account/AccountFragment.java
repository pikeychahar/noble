package com.dmw.noble.activity.ui.account;

import static android.content.Context.MODE_PRIVATE;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.dmw.noble.R;
import com.dmw.noble.activity.MainActivity;
import com.dmw.noble.activity_crm.CancellationActivity;
import com.dmw.noble.activity_crm.ClaimActivity;
import com.dmw.noble.activity_crm.EarningActivity;
import com.dmw.noble.activity_crm.EndorsementActivity;
import com.dmw.noble.activity_crm.MobilePosActivity;
import com.dmw.noble.activity_crm.OfflineQuoteActivity;
import com.dmw.noble.activity_crm.RenewalActivity;
import com.dmw.noble.activity_crm.SurveyActivity;
import com.dmw.noble.activity_crm.TicketActivity;
import com.dmw.noble.activity_docs.DocsAllActivity;
import com.dmw.noble.activity_motor.InspectionListActivity;
import com.dmw.noble.activity_pos.CustomerActivity;
import com.dmw.noble.activity_pos.LatestQuoteActivity;
import com.dmw.noble.activity_pos.LeadsActivity;
import com.dmw.noble.activity_pos.MyPoliciesActivity;
import com.dmw.noble.activity_pos.QuotationListActivity;
import com.dmw.noble.activity_profile.AgentActivity;
import com.dmw.noble.activity_profile.BankActivity;
import com.dmw.noble.activity_profile.DocumentsActivity;
import com.dmw.noble.activity_profile.ProfileActivity;
import com.dmw.noble.utils.AppUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;


/**
 * Created by Prahalad Kumar Chahar
 */
public class AccountFragment extends Fragment {

    //    DashboardViewModel dashboardViewModel;
    TextView rlQuote, rlPolicy, rlProfile, rlInspection, rlBank, rlDoc, rlLead, rlDocument,
            txtLogin, rlAddPos, rlViewPos, txtClaim, txtTicket, txtEarning, txtEndorsement,
            txtCancellation, txtRenewal, txtOffline, txtMobile, txtInspection, rlLatestQuote;
    Context mContext;
    boolean typeFlag;
    String userType, posStatus, name, email, phone, agentCode, agentId, spId, permission,
            userBadge, badgeColor, mobilePermission;
    SharedPreferences preferences;
    boolean isAccess = true;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        mContext = getActivity();

        txtLogin = root.findViewById(R.id.txtLogin);

        txtEarning = root.findViewById(R.id.txtEarning);
        txtClaim = root.findViewById(R.id.txtClaim);
        txtTicket = root.findViewById(R.id.txtTicket);
        txtEndorsement = root.findViewById(R.id.txtEndorsement);
        txtCancellation = root.findViewById(R.id.txtCancellation);
        txtRenewal = root.findViewById(R.id.txtRenewal);
        txtOffline = root.findViewById(R.id.txtOffline);
        txtMobile = root.findViewById(R.id.txtMobile);
        txtInspection = root.findViewById(R.id.txtInspection);
        rlLatestQuote = root.findViewById(R.id.rlLatestQuote);

        rlQuote = root.findViewById(R.id.rlQuote);
        rlInspection = root.findViewById(R.id.rlInspection);
        rlPolicy = root.findViewById(R.id.rlPolicy);
        rlDoc = root.findViewById(R.id.rlDoc);
        rlLead = root.findViewById(R.id.rlLead);
        rlProfile = root.findViewById(R.id.rlProfile);
        rlBank = root.findViewById(R.id.rlBank);
        rlDocument = root.findViewById(R.id.rlDocument);
        rlAddPos = root.findViewById(R.id.rlAddCustomer);
        rlViewPos = root.findViewById(R.id.rlViewCustomer);

        TextView tvName = root.findViewById(R.id.tvName);
        TextView tvMobile = root.findViewById(R.id.tvMobile);
        TextView tvEmail = root.findViewById(R.id.tvMail);
        TextView tvCode = root.findViewById(R.id.tvDes);
        CardView cb = root.findViewById(R.id.cb);

        preferences = mContext.getSharedPreferences(String.valueOf(R.string.app_name),
                MODE_PRIVATE);

        userType = preferences.getString(AppUtils.USER_TYPE, "");
        posStatus = preferences.getString(AppUtils.POS_STATUS, "");

        agentCode = preferences.getString(AppUtils.AGENT_ID, "");
        name = preferences.getString(AppUtils.NAME, "");
        agentId = preferences.getString(AppUtils.PRIMARY_ID, "");
        email = preferences.getString(AppUtils.EMAIL, "");
        phone = preferences.getString(AppUtils.MOBILE, "");
        spId = preferences.getString(AppUtils.SP_ID, "");
        permission = preferences.getString(AppUtils.POS_PERMISSION, "");
        mobilePermission = preferences.getString(AppUtils.MOBILE_PERMISSION, "");

        System.out.println("Mobile Permission--- " + mobilePermission);

        badgeColor = preferences.getString(AppUtils.BADGE_COLOR, "#2196F3");
        userBadge = preferences.getString(AppUtils.USER_BADGE, "");

        if (!TextUtils.isEmpty(permission) && (permission.equals("1") || permission.equals("2"))) {
            root.findViewById(R.id.cv0).setVisibility(View.VISIBLE);
            root.findViewById(R.id.cv1).setVisibility(View.VISIBLE);
        } else {
            root.findViewById(R.id.cv0).setVisibility(View.GONE);
            root.findViewById(R.id.cv1).setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(mobilePermission) && mobilePermission.equals("0"))
            txtMobile.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(userType)) {
            if (userType.equalsIgnoreCase("agent")) {
                if (posStatus.equals("4")) {
                    isAccess = false;
                    root.findViewById(R.id.cb).setVisibility(View.GONE);
                    root.findViewById(R.id.rlTraining).setVisibility(View.VISIBLE);
                }
                typeFlag = true;
            } else if (userType.equalsIgnoreCase("subpos")) {
                txtEarning.setVisibility(View.GONE);
            }
        } else {
            root.findViewById(R.id.cb).setVisibility(View.GONE);
            root.findViewById(R.id.rlGuest).setVisibility(View.VISIBLE);
        }

        rlPolicy.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(userType))
                startActivity(new Intent(mContext, MyPoliciesActivity.class));
            else {
                moveToLogin();
            }
        });

        rlQuote.setOnClickListener(v -> {

            if (!TextUtils.isEmpty(userType))
                startActivity(new Intent(mContext, QuotationListActivity.class));
            else {
                moveToLogin();
            }
        });

        rlQuote.setOnClickListener(v -> {

            if (!TextUtils.isEmpty(userType))
                startActivity(new Intent(mContext, InspectionListActivity.class));
            else {
                moveToLogin();
            }
        });

        rlLatestQuote.setOnClickListener(v -> {

            if (!TextUtils.isEmpty(userType))
                startActivity(new Intent(mContext, LatestQuoteActivity.class));
            else {
                moveToLogin();
            }
        });

        rlLead.setOnClickListener(v -> {

            if (!TextUtils.isEmpty(userType))
                startActivity(new Intent(mContext, LeadsActivity.class));
            else {
                moveToLogin();
            }
        });

        rlDoc.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(userType))
                startActivity(new Intent(mContext, DocsAllActivity.class));
            else {
                moveToLogin();
            }
        });
        rlAddPos.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(userType))
                startActivity(new Intent(mContext, AgentActivity.class));
            else {
                moveToLogin();
            }
        });

        rlViewPos.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(userType))
                startActivity(new Intent(mContext, CustomerActivity.class));
            else {
                moveToLogin();
            }
        });

        rlProfile.setOnClickListener(v -> {

            if (typeFlag)
                startActivity(new Intent(mContext, ProfileActivity.class));
            else Toast.makeText(mContext, "Access denied", Toast.LENGTH_SHORT).show();
        });

        rlBank.setOnClickListener(v -> {

            if (typeFlag)
                startActivity(new Intent(mContext, BankActivity.class));
            else Toast.makeText(mContext, "Access denied", Toast.LENGTH_SHORT).show();
        });

        rlDocument.setOnClickListener(v -> {
            if (typeFlag)
                startActivity(new Intent(mContext, DocumentsActivity.class));
            else Toast.makeText(mContext, "Access denied", Toast.LENGTH_SHORT).show();
        });

        txtLogin.setOnClickListener(v -> {

            startActivity(new Intent(mContext, MainActivity.class));
            requireActivity().finish();
        });

        txtClaim.setOnClickListener(v -> {
            startActivity(new Intent(mContext, ClaimActivity.class));
        });

        txtTicket.setOnClickListener(v -> {
            startActivity(new Intent(mContext, TicketActivity.class));
        });

        txtEarning.setOnClickListener(v -> {
            startActivity(new Intent(mContext, EarningActivity.class));
        });

        txtEndorsement.setOnClickListener(v -> {
            startActivity(new Intent(mContext, EndorsementActivity.class));
        });

        txtCancellation.setOnClickListener(v -> {
            startActivity(new Intent(mContext, CancellationActivity.class));
        });

        txtRenewal.setOnClickListener(v -> {
            startActivity(new Intent(mContext, RenewalActivity.class));
        });

        txtMobile.setOnClickListener(v -> {
            startActivity(new Intent(mContext, MobilePosActivity.class));
        });

        txtInspection.setOnClickListener(v -> {
            startActivity(new Intent(mContext, SurveyActivity.class));
        });

        txtOffline.setOnClickListener(v -> {
            startActivity(new Intent(mContext, OfflineQuoteActivity.class));
        });

        tvName.setText(name);
        tvMobile.setText(phone);
        tvEmail.setText(email);
        tvCode.setText(agentCode);
        cb.setOnClickListener(v1 -> takeScreenShot(cb));

        return root;
    }

    private void moveToLogin() {
        Toast.makeText(mContext, "Login Required !!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(mContext, MainActivity.class));
        requireActivity().finish();
    }

    private void takeScreenShot(View view) {
        Date date = new Date();
        CharSequence format = DateFormat.format("MM-dd-yyyy_hh:mm:ss", date);

        try {
            File mainDir = new File(
                    getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "FilShare");
            if (!mainDir.exists()) {
                boolean mkdir = mainDir.mkdir();
            }
            String path = mainDir + "/" + "Square" + "-" + format + ".jpeg";
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File imageFile = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            shareScreenShot(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shareScreenShot(File imageFile) {
        Uri uri = FileProvider.getUriForFile(getActivity(), AppUtils.AUTHORITY,
                imageFile);

        //Explicit intent
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        try {
            this.startActivity(Intent.createChooser(intent, "Share With"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mContext, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }
}