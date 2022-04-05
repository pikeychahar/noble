package com.square.pos.activity_crm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.adaptor.crm.MobileAdapter;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.CrmManager;
import com.square.pos.model_crm.MobilePos;
import com.square.pos.model_crm.MobilePosList;
import com.square.pos.utils.AppUtils;

import java.util.ArrayList;
import java.util.Objects;

public class MobilePosActivity extends AbstractActivity implements
        onRequestCompleteCallBackListener {

    Context mContext;
    RecyclerView rcAllMobile;
    String userId, userType;
    ProgressDialog progressDialog;
    SharedPreferences preferences;
    ArrayList<MobilePos> mobileList = new ArrayList<>();
    MobileAdapter mobileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_pos);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);

        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait..");

        rcAllMobile = findViewById(R.id.rcAllMobile);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rcAllMobile.setLayoutManager(mLayoutManager);
        getAllMobilePos();

        mobileAdapter = new MobileAdapter(mContext, mobileList);
        rcAllMobile.setAdapter(mobileAdapter);
        mobileAdapter.notifyDataSetChanged();
    }

    public void onCreateMobile(View view) {
        Intent intent = new Intent(mContext, NewMobileActivity.class);
        startActivityForResult(intent, 555);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK)
            getAllMobilePos();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getAllMobilePos() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                CrmManager.getInstance().getMobilePosData(mContext, userId, userType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof MobilePosList) {
            MobilePosList policyResponse = (MobilePosList) response;
            if (policyResponse.getStatus()) {
                if (mobileList.size() > 0)
                    mobileList.clear();
                if (policyResponse.getData() != null) {
                    findViewById(R.id.rlNoLeads).setVisibility(View.GONE);
                    mobileList.addAll(policyResponse.getData());
                    mobileAdapter.notifyDataSetChanged();
                } else findViewById(R.id.rlNoLeads).setVisibility(View.VISIBLE);
            } else findViewById(R.id.rlNoLeads).setVisibility(View.VISIBLE);
        }
        progressDialog.dismiss();
    }
}