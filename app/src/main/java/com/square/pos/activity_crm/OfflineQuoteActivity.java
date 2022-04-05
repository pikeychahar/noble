package com.square.pos.activity_crm;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.adaptor.crm.OfflineAdapter;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.CrmManager;
import com.square.pos.model_crm.OfflineData;
import com.square.pos.model_crm.OfflineList;
import com.square.pos.utils.AppUtils;

import java.util.ArrayList;
import java.util.Objects;

public class OfflineQuoteActivity extends AbstractActivity implements
        onRequestCompleteCallBackListener, OfflineAdapter.OnProceedClick, OfflineAdapter.OnChatListener {

    ArrayList<OfflineData> quotationList = new ArrayList<>();
    ArrayList<OfflineData> searchList = new ArrayList<>();
    OfflineAdapter quotationAdapter;
    Context mContext;
    RecyclerView recyclerView;
    String userId, userType;
    RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences preferences;
    ProgressDialog progressdialog;
    Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_quote);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");

        mContext = this;
        mBundle = new Bundle();

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");
        recyclerView = findViewById(R.id.recyclerOffline);
        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);

        quotationAdapter = new OfflineAdapter(mContext, quotationList);
        recyclerView.setAdapter(quotationAdapter);
        quotationAdapter.notifyDataSetChanged();

        getOfflineQuote();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof OfflineList) {
            OfflineList policyResponse = (OfflineList) response;
            if (policyResponse.getRecordsTotal() > 0) {
                if (quotationList.size() > 0)
                    quotationList.clear();
                searchList.clear();
                if (policyResponse.getData() != null) {
                    quotationList.addAll(policyResponse.getData());
                    searchList.addAll(quotationList);
                    if (quotationList.size() == 0) {
                        findViewById(R.id.rlNoPolicies).setVisibility(View.VISIBLE);
                    } else findViewById(R.id.rlNoPolicies).setVisibility(View.GONE);
                    quotationAdapter.notifyDataSetChanged();
                }
            } else findViewById(R.id.rlNoPolicies).setVisibility(View.VISIBLE);
        }
        progressdialog.dismiss();
    }

    public void getOfflineQuote() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                CrmManager.getInstance().getOfflineQuote(mContext, userId, userType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void OnProceedListener(int pos) {
        OfflineData offlineData = quotationList.get(pos);
        String quotationId = offlineData.getQuotationId();

        Intent intent = new Intent(mContext, ViewOfflineActivity.class);
        mBundle.putString(AppUtils.QUOTATION_ID, quotationId);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    private synchronized void searchForTextInList(String searchString) {
        int textLength = searchString.length();
        quotationList.clear();
        if (searchString.trim().length() == 0) {
            quotationList.addAll(searchList);
        } else {
            searchString = searchString.replaceAll("^\\s+", "");

            for (int i = 0; i < searchList.size(); i++) {
                OfflineData assessment = searchList.get(i);
                String userName = "";
                if (!TextUtils.isEmpty(assessment.getVehicleNo()))
                    userName = assessment.getVehicleNo().toLowerCase();
                String contactNo = "";
                if (!TextUtils.isEmpty(assessment.getCustomerName()))
                    contactNo = assessment.getCustomerName();
                if (textLength > 0) {
                    if (userName.contains(searchString) || contactNo.contains(searchString)) {
                        quotationList.add(assessment);
                    }
                }
            }
            quotationAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {

                searchForTextInList(newText);
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                return true;
            }
        };

        searchView.setOnQueryTextListener(queryTextListener);

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                quotationList.clear();
                quotationList.addAll(searchList);
                quotationAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return true;
    }

    public void onCreateOffline(View view) {
        Intent intent = new Intent(mContext, NewOfflineActivity.class);
        startActivityForResult(intent, 555);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK)
            getOfflineQuote();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onChatClick(int position) {
        Bundle mBundle = new Bundle();
        OfflineData clamObj = quotationList.get(position);
        Intent intent = new Intent(mContext, ChatActivity.class);
        mBundle.putString(AppUtils.CLAIM_NO, clamObj.getQuotationId());
        mBundle.putString(AppUtils.CLAIM_ID, clamObj.getId());
        mBundle.putString(AppUtils.CLAIM_MANAGER, clamObj.getAssignUser());
        mBundle.putString(AppUtils.CHAT_TYPE, "OfflineQuote");
        intent.putExtras(mBundle);
        startActivity(intent);
    }
}