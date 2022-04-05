package com.square.pos.activity_crm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;

public class PolicyFilterActivity extends AbstractActivity {

    Context mContext;
    String userType, url, type, userId;
    SharedPreferences preferences;
    ProgressDialog progressdialog;
    Bundle mBundle;
    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy_filter);

    }
}