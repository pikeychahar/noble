package com.dmw.noble.activity_crm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.fragment.OtherFragment;
import com.dmw.noble.fragment.OwnerFragment;
import com.dmw.noble.fragment.PagerAdapter;
import com.dmw.noble.fragment.VehicleFragment;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.utils.AppUtils;

import org.json.JSONObject;

import java.util.Objects;

public class NewEndorsementActivity extends AbstractActivity implements
        onRequestCompleteCallBackListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    Context mContext;
    ProgressDialog progressDialog;
    String userId, userType, srId, srNo, nameUpdateReason, ncbUpdateReason;
    SharedPreferences preferences;
    JSONObject formJson, oldFormData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ne_endorsement);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");

        mContext = this;
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait...");

        tabLayout = findViewById(R.id.mTabLayout);
        viewPager = findViewById(R.id.viewPager);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        //attaching fragments to adapter
        pagerAdapter.addFragment(new OwnerFragment(), "Owner");
        pagerAdapter.addFragment(new VehicleFragment(), "Vehicle");
        pagerAdapter.addFragment(new OtherFragment(), "Other");

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //raiseEndorsement
    @Override
    public void onResponse(Object response) {

    }
}