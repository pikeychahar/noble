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
import com.square.pos.adaptor.crm.ClaimAdaptor;
import com.square.pos.adaptor.crm.ClaimViewAdaptor;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.CrmManager;
import com.square.pos.model_crm.Claim;
import com.square.pos.model_crm.ClaimDatum;
import com.square.pos.model_crm.ClaimList;
import com.square.pos.model_crm.MyClaim;
import com.square.pos.utils.AppUtils;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Prahalad Kumar Chahar
 */

public class ClaimActivity extends AbstractActivity implements onRequestCompleteCallBackListener,
        ClaimViewAdaptor.OnDownloadClickListener, ClaimViewAdaptor.OnCreateClaimListener,
        ClaimAdaptor.OnChatListener, ClaimAdaptor.OnInfoListener {
    RecyclerView rcAllClaim, rcCreateClaim;
    TextView txtAllClaims, txtCreateClaim;
    Context mContext;
    String userId, userType, searchData;
    RelativeLayout rlSearchClaim;
    EditText edtSearch;

    ProgressDialog progressDialog;
    SharedPreferences preferences;

    ArrayList<Claim> allClaimList = new ArrayList<>();
    ArrayList<ClaimDatum> claimList = new ArrayList<>();

    ClaimAdaptor claimAdaptor;
    ClaimViewAdaptor claimAllAdaptor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);

        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait..");

        rcAllClaim = findViewById(R.id.rcAllClaim);
        rcCreateClaim = findViewById(R.id.rcCreateClaim);
        txtCreateClaim = findViewById(R.id.txtCreateClaim);
        txtAllClaims = findViewById(R.id.txtAllClaims);
        rlSearchClaim = findViewById(R.id.rlSearchClaim);
        edtSearch = findViewById(R.id.edtSearch);

        if (!TextUtils.isEmpty(userId)) {
            getClaimRequest();
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rcCreateClaim.setLayoutManager(mLayoutManager);

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(mContext);
        rcAllClaim.setLayoutManager(mLayoutManager1);

        claimAdaptor = new ClaimAdaptor(mContext, claimList);
        rcCreateClaim.setAdapter(claimAdaptor);
        claimAdaptor.notifyDataSetChanged();

        claimAllAdaptor = new ClaimViewAdaptor(mContext, allClaimList);
        rcAllClaim.setAdapter(claimAllAdaptor);
        claimAllAdaptor.notifyDataSetChanged();
    }

    public void onAllClaimsClick(View view) {
        rcCreateClaim.setVisibility(View.GONE);
        rlSearchClaim.setVisibility(View.VISIBLE);
        txtAllClaims.setTextColor(getColor(R.color.colorGray));
        txtCreateClaim.setTextColor(getColor(R.color.colorPrimaryDark));
    }

    public void onCreateClaimClick(View view) {
        txtCreateClaim.setTextColor(getColor(R.color.colorGray));
        rlSearchClaim.setVisibility(View.GONE);
        rcCreateClaim.setVisibility(View.VISIBLE);
        txtAllClaims.setTextColor(getColor(R.color.colorPrimaryDark));
    }

    public void getClaimRequest() {
        if (AppUtils.isOnline(mContext)) {
            try {
                CrmManager.getInstance().getClaimRequest(mContext, userId, userType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getAllClaim() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                CrmManager.getInstance().getAllClaim(mContext, userId, userType, searchData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof ClaimList) {
            ClaimList policyResponse = (ClaimList) response;
            if (policyResponse.getStatus()) {
                if (allClaimList.size() > 0)
                    allClaimList.clear();
                if (policyResponse.getData() != null) {
                    allClaimList.addAll(policyResponse.getData());
                    claimAllAdaptor.notifyDataSetChanged();
                }
            }
            progressDialog.dismiss();
        }

        if (response instanceof MyClaim) {
            MyClaim policyResponse = (MyClaim) response;
            if (policyResponse.getStatus() != null && policyResponse.getStatus()) {
                if (claimList.size() > 0)
                    claimList.clear();
                if (policyResponse.getData() != null) {
                    claimList.addAll(policyResponse.getData());
                    claimAdaptor.notifyDataSetChanged();
                }
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
        Claim clamObj = allClaimList.get(position);
        Intent intent = new Intent(mContext, NewClaimActivity.class);
        intent.putExtra("claim", clamObj);
        startActivity(intent);
    }

    @Override
    public void onChatClick(int position) {
        Bundle mBundle = new Bundle();
        ClaimDatum clamObj = claimList.get(position);
        Intent intent = new Intent(mContext, ChatActivity.class);
        mBundle.putString(AppUtils.CLAIM_NO, clamObj.getClaimId());
        mBundle.putString(AppUtils.CLAIM_MANAGER, clamObj.getClaimManager());
        mBundle.putString(AppUtils.CHAT_TYPE, "Claim");
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSearchClaim(View view) {
        AppUtils.checkSoftKeyboard(this);
        searchData = edtSearch.getText().toString();
        if (TextUtils.isEmpty(searchData)) {
            edtSearch.setError(getString(R.string.field_cannot));
            edtSearch.requestFocus();
        } else if (searchData.length() < 4) {
            AppUtils.showToast(mContext, "Minimum 4 Characters");
        } else {
            getAllClaim();
        }
    }

    @Override
    public void onInfoClick(int position) {
        Bundle mBundle = new Bundle();
        ClaimDatum clamObj = claimList.get(position);
        Intent intent = new Intent(mContext, ClaimViewActivity.class);
        mBundle.putString(AppUtils.CLAIM_NO, clamObj.getClaimId());
        mBundle.putString(AppUtils.CLAIM_MANAGER, clamObj.getClaimManager());
        mBundle.putString(AppUtils.CHAT_TYPE, "Claim");
        intent.putExtras(mBundle);
        startActivity(intent);
    }
}