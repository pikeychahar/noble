package com.square.pos.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.square.pos.R;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.UserManager;
import com.square.pos.utils.AppUtils;


/**
 * Created by Prahalad Kumar Chahar
 */

public class SplashActivity extends AbstractActivity implements onRequestCompleteCallBackListener {
    private ProgressBar progressBar;
    private int progressStatus = 0;
    Handler handler = new Handler();
    static int SPLASH_TIME_OUT = 3000;
    private SharedPreferences preferences;
    private Context mContext;
    String id, userType;

    int p = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;
        this.preferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
//        Objects.requireNonNull(getSupportActionBar()).hide();
        progressBar = findViewById(R.id.progressBar1);

        id = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");

        startScreen();
        getRto();
        getPreInsurer();

        if (!TextUtils.isEmpty(id))
            getAgentDetail();

        ImageView logoImage = findViewById(R.id.logoImage);

        ObjectAnimator animY = ObjectAnimator.ofFloat(logoImage, "translationY", -90f, -10f);
        animY.setDuration(2000);//1sec
        animY.setInterpolator(new BounceInterpolator());
        animY.setRepeatCount(2);
        animY.start();

    }

    public void getAgentDetail() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getAgentDetail(mContext, id, userType);
            } catch (Exception e) {
                e.printStackTrace();

            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void splashTimeoutComplete() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                try {
                    boolean isFirstTime = preferences.getBoolean(AppUtils.IS_FIRST_TIME, true);
                    if (isFirstTime && p == 1) {
                        Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
                        startActivity(intent);
                        preferences.edit().putBoolean(AppUtils.IS_FIRST_TIME, false).apply();
                        ++p;
                    } else {
                        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
                        String emailId, mobileNumber;
                        mobileNumber = preferences.getString(AppUtils.NAME, "");
                        emailId = preferences.getString(AppUtils.EMAIL, "");
                        String password = preferences.getString(AppUtils.USER_PASS, "");

                        if (!TextUtils.isEmpty(emailId) && !TextUtils.isEmpty(mobileNumber) && !TextUtils.isEmpty(password) && p == 1) {
                            Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            ++p;
                        } else {
                            if (p == 1) {
                                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                startActivity(intent);
                                ++p;
                            }
                        }
                    }
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, SPLASH_TIME_OUT);
    }


    @Override
    protected void onResume() {
        super.onResume();
        splashTimeoutComplete();
    }

    public void startScreen() {
        new Thread(() -> {
            while (progressStatus < 100) {
                progressStatus += 5;
                handler.post(() -> progressBar.setProgress(progressStatus));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void getStateList() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getState(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getBankList() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getBanks(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getCities() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getCities(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }


    public void getRto() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getRto(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getPreInsurer() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getInsurer(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Object response) {
    }

}
