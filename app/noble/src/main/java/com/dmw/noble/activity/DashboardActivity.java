package com.dmw.noble.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.dmw.noble.R;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.ApiManager;
import com.dmw.noble.manager.CrmManager;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.model.Login;
import com.dmw.noble.model_pos.AgentDetail;
import com.dmw.noble.utils.AppUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;

import java.util.Objects;

public class DashboardActivity extends AbstractActivity implements
        onRequestCompleteCallBackListener {

    private Context mContext;
    private static final int REQ_CODE_VERSION_UPDATE = 530;
    String email, userName, userId, agentName, agentEmail, agentId, loginId;
    private String currentVersion;
    private AppUpdateManager appUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener;
    Bundle mBundle;
    String userType, spId, password;
    String[] permissionRationale = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA};
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).hide();
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();

        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController,
                appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        mContext = this;
        preferences = getSharedPreferences(String.valueOf(R.string.app_name),
                MODE_PRIVATE);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        if (checkPermission())
            System.out.println("Permission Granted...");
        else {
            takePermission();
        }

        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");
        password = preferences.getString(AppUtils.USER_PASS, "");
        loginId = preferences.getString(AppUtils.AGENT_ID, "");
        if (TextUtils.isEmpty(loginId))
            loginId = preferences.getString(AppUtils.SP_ID, "");

        try {
            CrmManager.getInstance().getShareUrl(mContext, userId, loginId, userType);
        } catch (Exception e) {
            e.printStackTrace();
        }
//
//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(task -> {
//                    if (!task.isSuccessful()) {
//                        Log.w("TAG", "Fetching FCM registration token failed",
//                                task.getException());
//                        return;
//                    }
//
//                    // Get new FCM registration token
//                    String token = task.getResult();
//
//                    // Log and toast
//                    String msg = "TOKEN@@@" + token;
//                    Log.d("TAG", msg);
//                    ApiManager.getInstance().storeGcmId(mContext, userId, userType, token);
//                });
//

        mBundle = new Bundle();
        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0)
                    .versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        checkForAppUpdate();

        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        userName = preferences.getString(AppUtils.NAME, "");
        email = preferences.getString(AppUtils.EMAIL, "");
        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        agentName = preferences.getString(AppUtils.NAME, "");
        agentEmail = preferences.getString(AppUtils.AGENT_EMAIL, "");
        agentId = preferences.getString(AppUtils.PRIMARY_ID, "");
        spId = preferences.getString(AppUtils.SP_ID, "");

        if (!TextUtils.isEmpty(agentId))
            getAgentDetail();
        new GetVersionCode().execute();
    }

    public void getAgentDetail() {
        if (AppUtils.isOnline(mContext)) {

            try {
                UserManager.getInstance().getAgentDetail(mContext, agentId, userType);
            } catch (Exception e) {
                e.printStackTrace();

            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof Login) {
            Login loginResponse = (Login) response;
            if (loginResponse.getSuccess().equals("1")) {
                if (loginResponse.getType().equalsIgnoreCase("agent")) {

                    String mobile = loginResponse.getNumber();
                    String name = loginResponse.getName();
                    String email = loginResponse.getEmail().toLowerCase();
                    String agentId = loginResponse.getAgentId();
                    String userId = loginResponse.getId();
                    String spId = loginResponse.getSpId();
                    String empId = loginResponse.getEmployeeId();
                    String userType = loginResponse.getType();
                    String referenceId = loginResponse.getReferenceId();
                    String addPosPermission = loginResponse.getCreatePosPermission();
                    String isAgreed = loginResponse.getPosAgreement();
                    String posStatus = loginResponse.getPosStatus();
                    String userBadge = loginResponse.getPosBadge();
                    String badgeColor = loginResponse.getBadgeColor();
                    String mobilePermission = loginResponse.getCreateMobilePermission();

                    preferences.edit().putString(AppUtils.MOBILE, mobile).apply();
                    preferences.edit().putString(AppUtils.NAME, name).apply();
                    preferences.edit().putString(AppUtils.EMAIL, email).apply();
                    preferences.edit().putString(AppUtils.AGENT_ID, agentId).apply();
                    preferences.edit().putString(AppUtils.SP_ID, spId).apply();
                    preferences.edit().putString(AppUtils.USER_TYPE, userType).apply();
                    preferences.edit().putString(AppUtils.USER_PASS, password).apply();
                    preferences.edit().putString(AppUtils.REFERENCE_ID, referenceId).apply();
                    preferences.edit().putString(AppUtils.EMP_ID, empId).apply();
                    preferences.edit().putString(AppUtils.POS_PERMISSION, addPosPermission).apply();
                    preferences.edit().putString(AppUtils.IS_AGREEMENT_POS, isAgreed).apply();
                    preferences.edit().putString(AppUtils.USER_BADGE, userBadge).apply();
                    preferences.edit().putString(AppUtils.BADGE_COLOR, badgeColor).apply();
                    preferences.edit().putString(AppUtils.MOBILE_PERMISSION, mobilePermission).apply();

                    preferences.edit().putString(AppUtils.PRIMARY_ID, userId).apply();
                    preferences.edit().putString(AppUtils.POS_STATUS, posStatus).apply();
                } else if (loginResponse.getType().equalsIgnoreCase("sp")){

                    preferences.edit().clear().apply();
                    AgentDetail agentDetail = new AgentDetail();
                    UserManager.getInstance().setAgentDetail(agentDetail);
                    preferences.edit().putBoolean(AppUtils.IS_FIRST_TIME,
                            false).apply();
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=com.square.squareinsurance")));
                    } catch (ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=com.square.squareinsurance")));
                    }

                } else Toast.makeText(mContext, "Invalid Login Credential", Toast.LENGTH_LONG).show();
            } else {
                preferences.edit().clear().apply();
                AgentDetail agentDetail = new AgentDetail();
                UserManager.getInstance().setAgentDetail(agentDetail);
                preferences.edit().putBoolean(AppUtils.IS_FIRST_TIME,
                        false).apply();
                startActivity(new Intent(mContext, MainActivity.class));
                ApiManager.getInstance().removeGcmId(mContext, userId, userType);
                finishAffinity();
            }
        }
    }

    private void checkForAppUpdate() {
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(mContext);

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Create a listener to track request state updates.
        installStateUpdatedListener = installState -> {
            // Show module progress, log state, or install the update.
            if (installState.installStatus() == InstallStatus.DOWNLOADED) {

            }
            // After the update is downloaded, show a notification
            // and request user confirmation to restart the app.
            //  popupSnackbarForCompleteUpdateAndUnregister();
        };

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                    // Request the update.
                    if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {

                        // Before starting an update, register a listener for updates.
                        appUpdateManager.registerListener(installStateUpdatedListener);
                        // Start an update.
                        DashboardActivity.this.startAppUpdateFlexible(appUpdateInfo);
                    } else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                        // Start an update.
                        DashboardActivity.this.startAppUpdateImmediate(appUpdateInfo);
                    }
                }
            }
        });
    }

    private void startAppUpdateImmediate(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    DashboardActivity.REQ_CODE_VERSION_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    private void startAppUpdateFlexible(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.FLEXIBLE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    DashboardActivity.REQ_CODE_VERSION_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
            unregisterInstallStateUpdListener();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNewAppVersionState();
        requestStoragePermission();
    }

    private void checkNewAppVersionState() {
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        new OnSuccessListener<AppUpdateInfo>() {
                            @Override
                            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                                    // popupSnackbarForCompleteUpdateAndUnregister();
                                }

                                //IMMEDIATE:
                                if (appUpdateInfo.updateAvailability() == UpdateAvailability
                                        .DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                    // If an in-app update is already running, resume the update.
                                    DashboardActivity.this.startAppUpdateImmediate(appUpdateInfo);
                                }
                            }
                        });
    }

    private void unregisterInstallStateUpdListener() {
        if (appUpdateManager != null && installStateUpdatedListener != null)
            appUpdateManager.unregisterListener(installStateUpdatedListener);
    }

    //Thread used for check update on Play store
    private class GetVersionCode extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String newVersion = null;
            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id="
                        + DashboardActivity.this.getPackageName() + "&hl=it")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select(".IxB2fe .hAyfc:nth-child(4) .htlgb span")
                        .get(0)
                        .ownText();
                return newVersion;
            } catch (Exception e) {
                return newVersion;
            }
        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);
            if (onlineVersion != null && !onlineVersion.isEmpty()) {
                if (!currentVersion.equals(onlineVersion)) {
                    if (!((Activity) mContext).isFinishing())
                        showUpdateDialog();
                }
            }
        }
    }

    // Update Dialog
    private void showUpdateDialog() {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle((R.string.app_name));
        alertDialogBuilder.setIcon(R.drawable.logo_app);
        alertDialogBuilder.setMessage("Better, Faster Update is Available," +
                "\nYou cannot proceed without update!");
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton(R.string.update,
                        (dialog, id) -> {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(
                                    "https://play.google.com/store/apps/details?id="
                                            + mContext.getPackageName()));
                            intent.setPackage("com.android.vending");
                            startActivity(intent);
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, permissionRationale,
                        AppUtils.REQUEST_PERMISSION_STORAGE);
            } else {
                ActivityCompat.requestPermissions(this, permissionRationale,
                        AppUtils.REQUEST_PERMISSION_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull @NotNull String[] permissions,
                                           @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            if (requestCode == 101) {
                boolean readExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (readExternalStorage)
                    System.out.println("Permission Granted...");
                else {
                    takePermission();
                }
            }

        }
    }

    private boolean checkPermission() {
        int per = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        return per == PackageManager.PERMISSION_GRANTED;
    }

    private void takePermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE
        }, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()) {
                        System.out.println("Permission Granted...");
                    } else {
                        takePermission();
                    }
                }
            }

        }
    }
}