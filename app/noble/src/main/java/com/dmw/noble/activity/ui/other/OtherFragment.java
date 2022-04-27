package com.dmw.noble.activity.ui.other;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.dmw.noble.R;
import com.dmw.noble.activity.AboutUsActivity;
import com.dmw.noble.activity.ContactActivity;
import com.dmw.noble.activity.MainActivity;
import com.dmw.noble.activity.PrivacyActivity;
import com.dmw.noble.manager.ApiManager;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.model.BasicResponse;
import com.dmw.noble.model.SendOtp;
import com.dmw.noble.model_pos.AgentDetail;
import com.dmw.noble.network.ApiClient;
import com.dmw.noble.network.ApiInterface;
import com.dmw.noble.utils.AppUtils;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OtherFragment extends Fragment {

    TextView lblLogout, lblAboutUs, lblShare, lblHelp, lblAccount, lblCp, lblPrivacy,
            txtWhatsApp, lblRate, lblVersion, txtRmName, txtRmContact;

    Context mContext;
    Bundle mBundle;
    SharedPreferences preferences;
    String validOtp, newPassword, confirmPassword, mobile, otp, oldPassword,
            userType, userId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        mContext = getActivity();
        mBundle = new Bundle();

        preferences = mContext.getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        mobile = preferences.getString(AppUtils.MOBILE, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");
        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        oldPassword = preferences.getString(AppUtils.USER_PASS, "");

        lblLogout = root.findViewById(R.id.lblLogout);
        lblAboutUs = root.findViewById(R.id.lblAboutUs);
        lblShare = root.findViewById(R.id.lblShare);
        lblHelp = root.findViewById(R.id.lblHelp);
        lblAccount = root.findViewById(R.id.lblAccount);
        lblCp = root.findViewById(R.id.lblCp);
        lblPrivacy = root.findViewById(R.id.lblPP);
        lblRate = root.findViewById(R.id.lblRate);
        lblVersion = root.findViewById(R.id.lblVersion);
        txtWhatsApp = root.findViewById(R.id.txtWhatsApp);

        txtRmName = root.findViewById(R.id.tvRmName);
        txtRmContact = root.findViewById(R.id.tvRmContact);

//        lblVersion.setText("App Version " + BuildConfig.VERSION_CODE);
        String userType = preferences.getString(AppUtils.USER_TYPE, "");

        if (TextUtils.isEmpty(userType)) {
            lblLogout.setVisibility(View.GONE);
        }

        lblLogout.setOnClickListener(v -> logoutDialog());

        lblAboutUs.setOnClickListener(v -> startActivity(new Intent(mContext, AboutUsActivity.class)));
        lblHelp.setOnClickListener(v -> startActivity(new Intent(mContext, ContactActivity.class)));
        lblCp.setOnClickListener(v -> {

            if (!TextUtils.isEmpty(mobile)) {
                getOtp();
            }

            final Dialog dialog = new Dialog(mContext, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog);
            dialog.setTitle("Change Password");

            // set the custom dialog components - text, image and button
            final EditText oldPass = dialog.findViewById(R.id.edtOld);
            final EditText newPass = dialog.findViewById(R.id.edtNewPass);
            final EditText confirmPass = dialog.findViewById(R.id.edtConPass);
            final TextView txtMsg = dialog.findViewById(R.id.txtOtpMsg);

            txtMsg.setText("Enter OTP sent to " + mobile);

            Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
            Button close = dialog.findViewById(R.id.dialogButtonClose);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(v1 -> {
                mobile = preferences.getString(AppUtils.MOBILE, "");
                validOtp = oldPass.getText().toString();
                newPassword = newPass.getText().toString();
                confirmPassword = confirmPass.getText().toString();
                int pk = 0;
                if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(mContext, "Some thing went wrong!!!",
                            Toast.LENGTH_SHORT).show();
                    pk++;
                } else if (TextUtils.isEmpty(validOtp)) {
                    oldPass.setError("Empty");
                    pk++;
                } else if (TextUtils.isEmpty(newPassword)) {
                    newPass.setError("Empty");
                    pk++;
                } else if (!(confirmPassword.equals(newPassword))) {
                    confirmPass.setError("Password must be same");
                    Toast.makeText(mContext, "Password must be same",
                            Toast.LENGTH_SHORT).show();
                    pk++;
                } else if (newPassword.length() < 8) {
                    newPass.setError("Minimum 8 Characters");
                    newPass.requestFocus();
                    pk++;
                } else if (!validOtp.equals(otp)) {
                    oldPass.setError("Invalid Otp");
                    pk++;
                }
                if (pk == 0) {
                    changePassword();
//                    if (flag)
                    dialog.dismiss();

                }
            });
            close.setOnClickListener(view -> dialog.dismiss());

            dialog.show();
        });

        lblPrivacy.setOnClickListener(v -> startActivity(new Intent(mContext, PrivacyActivity.class)));

        lblShare.setOnClickListener(v -> {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                String msg = AppUtils.SHARE_MESSAGE
                        + "https://play.google.com/store/apps/details?id=";

                shareIntent.putExtra(Intent.EXTRA_TEXT, msg);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }
        });

        lblRate.setOnClickListener(v -> {

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
        });

        txtWhatsApp.setOnClickListener(v -> {
            String smsNumber = "918441990000";
            boolean isWhatsappInstalled = whatsappInstalledOrNot();
            if (isWhatsappInstalled) {

                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(smsNumber) + "@s.whatsapp.net");

                startActivity(sendIntent);
            } else {
                Uri uri = Uri.parse("market://details?id=com.whatsapp");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                Toast.makeText(mContext, "WhatsApp not Installed",
                        Toast.LENGTH_SHORT).show();
                startActivity(goToMarket);
            }

        });

        AgentDetail agentDetailObj = UserManager.getAgentDetail();
        String rmName = agentDetailObj.getRmName();
        String rmContact = agentDetailObj.getRmContact();
        if (!TextUtils.isEmpty(rmName)) {
            txtRmName.setText(rmName);
            txtRmContact.setText(rmContact);
        } else root.findViewById(R.id.rlRm).setVisibility(View.GONE);
        return root;
    }

    private boolean whatsappInstalledOrNot() {
        PackageManager pm = mContext.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return app_installed;
    }

    private void logoutDialog() {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle((R.string.app_name));
        alertDialogBuilder.setIcon(R.drawable.logo_app);
        alertDialogBuilder.setMessage("Do you want to logout!");
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton(R.string.str_yes,
                        (dialog, id) -> {
                            preferences.edit().clear().apply();
                            AgentDetail agentDetail = new AgentDetail();
                            UserManager.getInstance().setAgentDetail(agentDetail);
                            preferences.edit().putBoolean(AppUtils.IS_FIRST_TIME,
                                    false).apply();
                            startActivity(new Intent(mContext, MainActivity.class));
                            ApiManager.getInstance().removeGcmId(mContext, userId, userType);
                            requireActivity().finishAffinity();
                        })
                .setNegativeButton(R.string.no, null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void getOtp() {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<SendOtp> call = apiService.changePassOtp(mobile);
            call.enqueue(new Callback<SendOtp>() {
                @Override
                public void onResponse(@NotNull Call<SendOtp> call,
                                       @NotNull Response<SendOtp> response) {
                    if (response.isSuccessful()) {
                        SendOtp vResponse = response.body();
                        assert vResponse != null;
                        if (vResponse.getStatus() == 1) {
                            otp = String.valueOf(vResponse.getOtp());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<SendOtp> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changePassword() {

        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<BasicResponse> call = apiService.changePassword(mobile, validOtp, newPassword,
                    oldPassword, userId, userType);

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(@NotNull Call<BasicResponse> call,
                                       @NotNull Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        BasicResponse commonResponse = response.body();

                        assert commonResponse != null;
                        if (commonResponse.getStatus() == 1)
                            Toast.makeText(mContext, "Password has been updated",
                                    Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<BasicResponse> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}