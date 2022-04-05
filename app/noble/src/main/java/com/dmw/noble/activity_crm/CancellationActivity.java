package com.dmw.noble.activity_crm;

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

import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.adaptor.crm.CancellationAdaptor;
import com.dmw.noble.adaptor.crm.CancelledAdaptor;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.CrmManager;
import com.dmw.noble.model_crm.Cancellation;
import com.dmw.noble.model_crm.CancellationList;
import com.dmw.noble.model_crm.CancelledData;
import com.dmw.noble.model_crm.MyCancelled;
import com.dmw.noble.utils.AppUtils;

import java.util.ArrayList;
import java.util.Objects;

public class CancellationActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener, CancellationAdaptor.OnDownloadClickListener,
        CancellationAdaptor.OnCreateClaimListener, CancelledAdaptor.OnDownloadClickListener,
        CancelledAdaptor.OnInfoClickListener {

    RecyclerView rcAllCancellation, rcCreateCancellation;
    TextView txtAllCancellations, txtCreateCancellation;
    Context mContext;
    String userId, userType, searchData;
    RelativeLayout rlSearchCancellation;
    EditText edtSearch;

    ProgressDialog progressDialog;
    SharedPreferences preferences;

    ArrayList<Cancellation> allCancellationList = new ArrayList<>();
    ArrayList<CancelledData> cancelledList = new ArrayList<>();

    CancelledAdaptor cancelledAdaptor;
    CancellationAdaptor cancellationAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancellation);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);

        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait..");

        rcAllCancellation = findViewById(R.id.rcAllCancellation);
        rcCreateCancellation = findViewById(R.id.rcCreateCancellation);
        txtCreateCancellation = findViewById(R.id.txtCreateCancellation);
        txtAllCancellations = findViewById(R.id.txtAllCancellations);
        rlSearchCancellation = findViewById(R.id.rlSearchCancellation);
        edtSearch = findViewById(R.id.edtSearch);

        if (!TextUtils.isEmpty(userId)) {
            getCancelledList();
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rcCreateCancellation.setLayoutManager(mLayoutManager);

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(mContext);
        rcAllCancellation.setLayoutManager(mLayoutManager1);

        cancelledAdaptor = new CancelledAdaptor(mContext, cancelledList);
        rcCreateCancellation.setAdapter(cancelledAdaptor);
        cancelledAdaptor.notifyDataSetChanged();

        cancellationAdaptor = new CancellationAdaptor(mContext, allCancellationList);
        rcAllCancellation.setAdapter(cancellationAdaptor);
        cancellationAdaptor.notifyDataSetChanged();
    }

    public void getCancelledList() {
        if (AppUtils.isOnline(mContext)) {
            try {
                CrmManager.getInstance().getCancelledList(mContext, userId, userType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getCancellationList() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                CrmManager.getInstance().getCancellationList(mContext, userId, userType, searchData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof CancellationList) {
            CancellationList policyResponse = (CancellationList) response;
            if (policyResponse.getStatus()) {
                if (allCancellationList.size() > 0)
                    allCancellationList.clear();
                if (policyResponse.getData() != null) {
                    allCancellationList.addAll(policyResponse.getData());
                    cancellationAdaptor.notifyDataSetChanged();
                }

            } else AppUtils.showToast(mContext, "No Data Found with " + searchData);
            progressDialog.dismiss();
        }

        if (response instanceof MyCancelled) {
            MyCancelled policyResponse = (MyCancelled) response;
            if (policyResponse.getStatus() != null && policyResponse.getStatus()) {
                if (cancelledList.size() > 0)
                    cancelledList.clear();
                if (policyResponse.getData() != null) {
                    cancelledList.addAll(policyResponse.getData());
                    cancelledAdaptor.notifyDataSetChanged();
                }
            }
        }
        if (cancelledList.size() > 0)
            findViewById(R.id.rlEmpty).setVisibility(View.GONE);
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
        Cancellation cancelObj = allCancellationList.get(position);
        Intent intent = new Intent(mContext, NewCancellationActivity.class);
        intent.putExtra("cancellation", cancelObj);
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

    public void onSearchCancellation(View view) {
        AppUtils.checkSoftKeyboard(this);
        searchData = edtSearch.getText().toString();
        if (TextUtils.isEmpty(searchData)) {
            edtSearch.setError(getString(R.string.field_cannot));
            edtSearch.requestFocus();
        } else if (searchData.length() < 4) {
            AppUtils.showToast(mContext, "Minimum 4 Characters");
        } else {
            getCancellationList();
        }
    }

    public void onAllCancellationsClick(View view) {
        rcCreateCancellation.setVisibility(View.GONE);
        findViewById(R.id.rlEmpty).setVisibility(View.GONE);
        rlSearchCancellation.setVisibility(View.VISIBLE);
        txtAllCancellations.setTextColor(getColor(R.color.colorGray));
        txtCreateCancellation.setTextColor(getColor(R.color.colorPrimaryDark));
    }

    public void onCreateCancellationClick(View view) {
        txtCreateCancellation.setTextColor(getColor(R.color.colorGray));
        rlSearchCancellation.setVisibility(View.GONE);
        rcCreateCancellation.setVisibility(View.VISIBLE);
        txtAllCancellations.setTextColor(getColor(R.color.colorPrimaryDark));

        if (cancelledList.size() == 0)
            findViewById(R.id.rlEmpty).setVisibility(View.VISIBLE);
        else findViewById(R.id.rlEmpty).setVisibility(View.GONE);
    }

    @Override
    public void onInfo(int position) {
        Bundle mBundle = new Bundle();
        CancelledData clamObj = cancelledList.get(position);
        Intent intent = new Intent(mContext, CancellationViewActivity.class);
        mBundle.putString(AppUtils.CLAIM_NO, clamObj.getSrId());
        mBundle.putString(AppUtils.CLAIM_ID, clamObj.getCancelId());
        intent.putExtras(mBundle);
        startActivity(intent);
    }
}