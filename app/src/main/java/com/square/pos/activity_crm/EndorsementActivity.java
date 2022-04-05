package com.square.pos.activity_crm;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.adaptor.crm.EndorsementAdaptor;
import com.square.pos.adaptor.crm.EndorsementCreatedAdaptor;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.CrmManager;
import com.square.pos.model_crm.EndorseCreatedList;
import com.square.pos.model_crm.EndorseData;
import com.square.pos.model_crm.EndorseViewData;
import com.square.pos.model_crm.EndorsementCreatedData;
import com.square.pos.model_crm.EndorsementList;
import com.square.pos.utils.AppUtils;

import java.util.ArrayList;
import java.util.Objects;

public class EndorsementActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener, EndorsementAdaptor.OnDownloadClickListener,
        EndorsementAdaptor.OnCreateClaimListener, EndorsementCreatedAdaptor.OnChatListener {

    RecyclerView rcAllEndorse, rcCreateEndorse;
    TextView txtAllEndorse, txtCreateEndorse;
    Context mContext;
    String userId, userType, searchData, eId;
    RelativeLayout rlAllEndorse;
    EditText edtSearch;

    ProgressDialog progressDialog;
    SharedPreferences preferences;

    ArrayList<EndorseData> endorsementList = new ArrayList<>();
    ArrayList<EndorsementCreatedData> endorsementCreatedList = new ArrayList<>();

    EndorsementCreatedAdaptor endorsementCreatedAdaptor;
    EndorsementAdaptor endorsementAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endorsement);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);

        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait..");

        rcAllEndorse = findViewById(R.id.rcAllEndorse);
        rcCreateEndorse = findViewById(R.id.rcCreateEndorse);
        txtCreateEndorse = findViewById(R.id.txtCreateEndorse);
        txtAllEndorse = findViewById(R.id.txtAllEndorse);
        rlAllEndorse = findViewById(R.id.rlAllEndorse);
        edtSearch = findViewById(R.id.edtSearch);

        if (!TextUtils.isEmpty(userId)) {
            getCreatedEndorse();
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rcCreateEndorse.setLayoutManager(mLayoutManager);

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(mContext);
        rcAllEndorse.setLayoutManager(mLayoutManager1);

        endorsementCreatedAdaptor = new EndorsementCreatedAdaptor(mContext, endorsementCreatedList);
        rcCreateEndorse.setAdapter(endorsementCreatedAdaptor);
        endorsementCreatedAdaptor.notifyDataSetChanged();

        endorsementAdaptor = new EndorsementAdaptor(mContext, endorsementList);
        rcAllEndorse.setAdapter(endorsementAdaptor);
        endorsementAdaptor.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getCreatedEndorse() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                CrmManager.getInstance().getCreatedEndorsement(mContext, userId, userType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getAllEndorse() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                CrmManager.getInstance().getAllEndorsement(mContext, userId, userType, searchData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof EndorsementList) {
            EndorsementList policyResponse = (EndorsementList) response;
            if (policyResponse.getStatus()) {
                if (endorsementList.size() > 0)
                    endorsementList.clear();
                if (policyResponse.getData() != null) {
                    endorsementList.addAll(policyResponse.getData());
                    endorsementAdaptor.notifyDataSetChanged();
                }
            }
            progressDialog.dismiss();
        }

        if (response instanceof EndorseCreatedList) {
            EndorseCreatedList policyResponse = (EndorseCreatedList) response;
            if (policyResponse.getStatus()) {
                if (endorsementCreatedList.size() > 0)
                    endorsementCreatedList.clear();
                if (policyResponse.getData() != null) {
                    endorsementCreatedList.addAll(policyResponse.getData());
                    endorsementCreatedAdaptor.notifyDataSetChanged();
                }
            }
            progressDialog.dismiss();
        }

        if (response instanceof EndorseViewData) {
            EndorseViewData endorseViewData = (EndorseViewData) response;
            if (endorseViewData.getSuccess()) {
                CrmManager.getInstance().setEndorseViewData(endorseViewData);
                Intent intent = new Intent(mContext, RaiseEndorsementActivity.class);
                intent.putExtra("EndorseViewData", endorseViewData);
                startActivity(intent);
                progressDialog.dismiss();
            }
        }

    }

    @Override
    public void OnDownloadClicked(int position, String url) {
        try {
            Intent i = new Intent("android.intent.action.MAIN");
            i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
            i.addCategory("android.intent.category.LAUNCHER");
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            // Chrome is not installed
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(i);
        }
    }

    @Override
    public void onCreateClaim(int position) {
        EndorseData eObj = endorsementList.get(position);

        eId = eObj.getSRNo();
        getEndorseView();
    }

    public void getEndorseView() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                CrmManager.getInstance().getEndorseView(mContext, AppUtils.encode(eId));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onChatClick(int position) {
        Bundle mBundle = new Bundle();
        EndorsementCreatedData clamObj = endorsementCreatedList.get(position);
        Intent intent = new Intent(mContext, ChatActivity.class);
        mBundle.putString(AppUtils.CLAIM_NO, clamObj.getEndosmentId());
        mBundle.putString(AppUtils.CLAIM_ID, clamObj.getId());
        mBundle.putString(AppUtils.CLAIM_MANAGER, clamObj.getAssignedTo());
        mBundle.putString(AppUtils.CHAT_TYPE, "Endorsement");
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    public void onAllEndorseClick(View view) {
        rcCreateEndorse.setVisibility(View.GONE);
        rlAllEndorse.setVisibility(View.VISIBLE);
        txtAllEndorse.setTextColor(getColor(R.color.colorGray));
        txtCreateEndorse.setTextColor(getColor(R.color.colorPrimaryDark));
        findViewById(R.id.rlEmptyClaim).setVisibility(View.GONE);
    }

    public void onCreateEndorseClick(View view) {
        txtCreateEndorse.setTextColor(getColor(R.color.colorGray));
        rlAllEndorse.setVisibility(View.GONE);
        rcCreateEndorse.setVisibility(View.VISIBLE);
        txtAllEndorse.setTextColor(getColor(R.color.colorPrimaryDark));
        findViewById(R.id.rlEmptyClaim).setVisibility(View.GONE);
        if (endorsementCreatedList.size() == 0)
            findViewById(R.id.rlEmptyClaim).setVisibility(View.VISIBLE);
    }

    public void onSearchEndorse(View view) {
        AppUtils.checkSoftKeyboard(this);
        searchData = edtSearch.getText().toString();
        if (TextUtils.isEmpty(searchData)) {
            edtSearch.setError(getString(R.string.field_cannot));
            edtSearch.requestFocus();
        } else if (searchData.length() < 4) {
            AppUtils.showToast(mContext, "Minimum 4 Characters");
        } else {
            getAllEndorse();
        }
    }
}