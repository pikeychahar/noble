package com.square.pos.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.square.pos.BuildConfig;
import com.square.pos.R;
import com.square.pos.activity.health.HealthViewActivity;
import com.square.pos.activity.ui.TravelInsuranceActivity;
import com.square.pos.activity_docs.DocsAllActivity;
import com.square.pos.activity_motor.BikeRegistrationActivity;
import com.square.pos.activity_crm.EarningActivity;
import com.square.pos.activity_pos.LeadsActivity;
import com.square.pos.activity_pos.MyPoliciesActivity;
import com.square.pos.activity_pos.QuotationListActivity;
import com.square.pos.adaptor.BlogAdaptor;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.UserManager;
import com.square.pos.model.blog.Blog;
import com.square.pos.model.blog.BlogList;
import com.square.pos.model_pos.AgentDetail;
import com.square.pos.training.TrainingActivity;
import com.square.pos.utils.AppUtils;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Created by Prahalad Kumar Chahar
 */
public class HomeActivity extends AbstractActivity
        implements NavigationView.OnNavigationItemSelectedListener, onRequestCompleteCallBackListener, BlogAdaptor.OnBlogClickListener {

    private static final int REQ_CODE_VERSION_UPDATE = 530;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 100;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000; // time in milliseconds between successive task executions.
    //    private ViewPager mViewPager;
    private Context mContext;
    private SharedPreferences preferences;
    private String email, userName, userId, agentName, agentEmail, agentId;
    private TextView txtEmail, txtUserName, lblLogin;
    private ImageView userProfile;
    private String currentVersion;
    private AppUpdateManager appUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener;
    private RecyclerView recyclerBlog;
    private RecyclerView.LayoutManager mLayoutManager;
    private BlogAdaptor blogAdaptor;
    private List<Blog> blogList = new ArrayList<>();
    private Bundle mBundle;
    String userType, posStatus, spId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = this;
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        mBundle = new Bundle();
        int versionCode = BuildConfig.VERSION_CODE;
        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        checkForAppUpdate();
//        checkNewAppVersionState();

        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        userName = preferences.getString(AppUtils.NAME, "");
        email = preferences.getString(AppUtils.EMAIL, "");
        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        agentName = preferences.getString(AppUtils.NAME, "");
        agentEmail = preferences.getString(AppUtils.AGENT_EMAIL, "");
        agentId = preferences.getString(AppUtils.AGENT_ID, "");
        spId = preferences.getString(AppUtils.SP_ID, "");


        userType = preferences.getString(AppUtils.USER_TYPE, "");
        posStatus = preferences.getString(AppUtils.POS_STATUS, "");

        if (!TextUtils.isEmpty(userType))
            if (userType.equalsIgnoreCase("agent")) {
                if (posStatus.equals("4")) {
                    examDialog();
                }
            }
//                isValidUser = !posStatus.equals("4");

        new GetVersionCode().execute();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setItemIconTintList(null);
        if (TextUtils.isEmpty(email)) {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
        }

        View headerView = navigationView.getHeaderView(0);

        txtEmail = headerView.findViewById(R.id.txtRmContact);
        txtUserName = headerView.findViewById(R.id.txtUser);
        lblLogin = headerView.findViewById(R.id.txtLogin);
        userProfile = headerView.findViewById(R.id.userProfile);

        recyclerBlog = findViewById(R.id.recyclerBlog);

        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerBlog.setLayoutManager(mLayoutManager);
        recyclerBlog.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));


//        getBlogList();
//        blogAdaptor = new BlogAdaptor(mContext, blogList,Blo);
//        recyclerBlog.setAdapter(blogAdaptor);
//        blogAdaptor.notifyDataSetChanged();

        if (TextUtils.isEmpty(agentId))
            agentId = "";
        if (!TextUtils.isEmpty(userName))
            txtUserName.setText(userName);

        if (!TextUtils.isEmpty(userType))
            if (userType.equalsIgnoreCase("agent")) {
                if (!TextUtils.isEmpty(agentName))
                    txtUserName.setText(agentName + " - " + agentId);
            }

        if (!TextUtils.isEmpty(email))
            txtEmail.setText(email);
        else if (!TextUtils.isEmpty(agentEmail))
            txtEmail.setText(agentEmail);

        if ((!TextUtils.isEmpty(email)) && !(TextUtils.isEmpty(userName))) {
            lblLogin.setVisibility(View.GONE);
            txtUserName.setVisibility(View.VISIBLE);
            txtEmail.setVisibility(View.VISIBLE);

        } else if (!TextUtils.isEmpty(agentEmail) && !TextUtils.isEmpty(agentName)) {
            lblLogin.setVisibility(View.GONE);
            txtUserName.setVisibility(View.VISIBLE);
            txtEmail.setVisibility(View.VISIBLE);
        } else {
            lblLogin.setVisibility(View.VISIBLE);
            txtUserName.setVisibility(View.GONE);
            txtEmail.setVisibility(View.GONE);
        }
        lblLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, MainActivity.class));
                finishAffinity();
            }
        });

        navigationView.setNavigationItemSelectedListener(this);

        if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(agentEmail)) {
            findViewById(R.id.rlWelcome).setVisibility(View.VISIBLE);
            findViewById(R.id.btnAL).setVisibility(View.GONE);
//            TextView txtAgentName = findViewById(R.id.txtAgentName);
//            txtAgentName.setText(agentName);

        } else {
            findViewById(R.id.rlWelcome).setVisibility(View.GONE);
            findViewById(R.id.btnAL).setVisibility(View.VISIBLE);
        }


    }

    boolean doubleBackToExitPressedOnce = false;

    //Handle back handle on Main page
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if ((!TextUtils.isEmpty(userName)) || (!TextUtils.isEmpty(userId))) {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    return;
                }

                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please click BACK again to exit",
                        Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            } else {
                super.onBackPressed();
                finishAffinity();
            }
        }
    }

    // Add menu bar on this page
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            logoutDialog();
            return true;
        }
        if (id == R.id.action_login) {
            preferences.edit().clear().apply();
            startActivity(new Intent(mContext, MainActivity.class));
            finishAffinity();
            return true;
        }
        if (id == R.id.action_notification) {
            startActivity(new Intent(mContext, NotificationActivity.class));
            return true;
        }

        if (id == R.id.action_earning) {
            startActivity(new Intent(mContext, EarningActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(agentEmail)) {
            MenuItem register = menu.findItem(R.id.action_login);
            register.setVisible(false);
            return true;
        } else {
            MenuItem register = menu.findItem(R.id.action_logout);
            register.setVisible(false);
            MenuItem profile = menu.findItem(R.id.action_my_account);
            profile.setVisible(false);
            return true;
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_bike) {
            preferences.edit().putString(AppUtils.VEHICLE_TYPE, "1").apply();
            startActivity(new Intent(mContext, BikeRegistrationActivity.class));

        } else if (id == R.id.nav_car) {
            preferences.edit().putString(AppUtils.VEHICLE_TYPE, "2").apply();
            startActivity(new Intent(mContext, BikeRegistrationActivity.class));

        } else if (id == R.id.nav_cv) {
            preferences.edit().putString(AppUtils.VEHICLE_TYPE, "3").apply();
            startActivity(new Intent(mContext, BikeRegistrationActivity.class));

        } else if (id == R.id.nav_travel) {
            startActivity(new Intent(mContext, TravelInsuranceActivity.class));

        } else if (id == R.id.nav_health) {
            startActivity(new Intent(mContext, HealthViewActivity.class));

        } else if (id == R.id.nav_share) {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                AppUtils.SHARE_MESSAGE = AppUtils.SHARE_MESSAGE
                        + "https://play.google.com/store/apps/details?id="
                        + BuildConfig.APPLICATION_ID;
                shareIntent.putExtra(Intent.EXTRA_TEXT, AppUtils.SHARE_MESSAGE);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }

        } else if (id == R.id.nav_about) {
            startActivity(new Intent(mContext, AboutUsActivity.class));
        } else if (id == R.id.nav_rate) {

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
        } else if (id == R.id.nav_privacy) {
            startActivity(new Intent(mContext, PrivacyActivity.class));
        } else if (id == R.id.nav_logout) {
            logoutDialog();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClickAboutUs(View view) {
        startActivity(new Intent(mContext, AboutUsActivity.class));
    }

    public void onBikeInsurance(View view) {
        preferences.edit().putString(AppUtils.VEHICLE_TYPE, "1").apply();
        startActivity(new Intent(mContext, BikeRegistrationActivity.class));

    }

    public void onTravel(View view) {
        startActivity(new Intent(mContext, TravelInsuranceActivity.class));
    }

    public void onHealth(View view) {
        startActivity(new Intent(mContext, HealthViewActivity.class));
    }


    public void onCarInsurance(View view) {
        preferences.edit().putString(AppUtils.VEHICLE_TYPE, "2").apply();
        startActivity(new Intent(mContext, BikeRegistrationActivity.class));
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
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                preferences.edit().clear().apply();
                                AgentDetail agentDetail = new AgentDetail();
                                UserManager.getInstance().setAgentDetail(agentDetail);
                                preferences.edit().putBoolean(AppUtils.IS_FIRST_TIME,
                                        false).apply();
                                startActivity(new Intent(mContext, MainActivity.class));
                                finishAffinity();
                            }
                        })
                .setNegativeButton(R.string.no, null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void examDialog() {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(("PoSP Training and Exam"));
        alertDialogBuilder.setIcon(R.drawable.logo_app);
        alertDialogBuilder.setMessage("Kindly click on start button to continue the training!");
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton(R.string.start,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(mContext, TrainingActivity.class));
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(mContext, MainActivity.class));
        finishAffinity();
    }

    public void onPolicyList(View view) {
        startActivity(new Intent(mContext, MyPoliciesActivity.class));
    }

    public void onQuotationsList(View view) {
        startActivity(new Intent(mContext, QuotationListActivity.class));
    }

    public void onLeads(View view) {
        startActivity(new Intent(mContext, LeadsActivity.class));
    }

    public void onRenewal(View view) {
        startActivity(new Intent(mContext, DashboardActivity.class));
    }

    public void onWalletClick(View view) {
        if (!TextUtils.isEmpty(userId))
            startActivity(new Intent(mContext, DocsAllActivity.class));
        else {
            startActivity(new Intent(mContext, MainActivity.class));
            Toast.makeText(mContext, "Login Required ", Toast.LENGTH_SHORT).show();
            finishAffinity();
        }
    }

    public void onPrivacy(View view) {
        startActivity(new Intent(mContext, PrivacyActivity.class));

    }

    @Override
    public void onBlogClicked(int position, String url) {
        if (!TextUtils.isEmpty(url)) {
            Intent intent = new Intent(mContext, BlogActivity.class);
            mBundle.putString(AppUtils.BLOG_URL, url);
            intent.putExtras(mBundle);
            startActivity(intent);
        }
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof BlogList) {
            BlogList policyResponse = (BlogList) response;
            if (policyResponse.getStatus() == 1) {
                if (blogList.size() > 0)
                    blogList.clear();
                if (policyResponse.getBlogs() != null) {
                    findViewById(R.id.rlBlog).setVisibility(View.VISIBLE);
                    blogList.addAll(policyResponse.getBlogs());
                    blogAdaptor.notifyDataSetChanged();
                }
            }
        }

    }

    public void onCommercialInsuranceGcv(View view) {
        preferences.edit().putString(AppUtils.VEHICLE_TYPE, "4").apply();
        startActivity(new Intent(mContext, BikeRegistrationActivity.class));
    }


    //Thread used for check update on Play store
    private class GetVersionCode extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String newVersion = null;
            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + HomeActivity.this.getPackageName() + "&hl=it")
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
                    showUpdateDialog();
                }
            }
        }
    }

    public void onCommercialInsurance(View view) {
        preferences.edit().putString(AppUtils.VEHICLE_TYPE, "3").apply();
        startActivity(new Intent(mContext, BikeRegistrationActivity.class));
    }

    // Update Dialog
    private void showUpdateDialog() {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle((R.string.app_name));
        alertDialogBuilder.setIcon(R.drawable.logo_app);
        alertDialogBuilder.setMessage("Update is Available !");
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton(R.string.update,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Uri uri = Uri.parse("market://details?id="
                                        + mContext.getPackageName());
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

                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        unregisterInstallStateUpdListener();
        super.onDestroy();
    }

    private void checkForAppUpdate() {
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(mContext);

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Create a listener to track request state updates.
        installStateUpdatedListener = new InstallStateUpdatedListener() {
            @Override
            public void onStateUpdate(InstallState installState) {
                // Show module progress, log state, or install the update.
                if (installState.installStatus() == InstallStatus.DOWNLOADED) {

                }
                // After the update is downloaded, show a notification
                // and request user confirmation to restart the app.
                //  popupSnackbarForCompleteUpdateAndUnregister();
            }
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
                        HomeActivity.this.startAppUpdateFlexible(appUpdateInfo);
                    } else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                        // Start an update.
                        HomeActivity.this.startAppUpdateImmediate(appUpdateInfo);
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
                    HomeActivity.REQ_CODE_VERSION_UPDATE);
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
                    HomeActivity.REQ_CODE_VERSION_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
            unregisterInstallStateUpdListener();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNewAppVersionState();
    }

    private void checkNewAppVersionState() {
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        new OnSuccessListener<AppUpdateInfo>() {
                            @Override
                            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                                //FLEXIBLE:
                                // If the update is downloaded but not installed,
                                // notify the user to complete the update.
                                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                                    // popupSnackbarForCompleteUpdateAndUnregister();
                                }

                                //IMMEDIATE:
                                if (appUpdateInfo.updateAvailability()
                                        == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                    // If an in-app update is already running, resume the update.
                                    HomeActivity.this.startAppUpdateImmediate(appUpdateInfo);
                                }
                            }
                        });
    }

    private void unregisterInstallStateUpdListener() {
        if (appUpdateManager != null && installStateUpdatedListener != null)
            appUpdateManager.unregisterListener(installStateUpdatedListener);
    }

    public void getBlogList() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getBlogList(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

}
