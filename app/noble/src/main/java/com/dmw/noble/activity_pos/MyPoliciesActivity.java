package com.dmw.noble.activity_pos;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.adaptor.PolicyAdaptor;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.ApiManager;
import com.dmw.noble.manager.CrmManager;
import com.dmw.noble.model.Item;
import com.dmw.noble.model_crm.DetailedPolicyList;
import com.dmw.noble.model_crm.PolicyData;
import com.dmw.noble.utils.AppUtils;
import com.dmw.noble.utils.MultiSelectionSpinner;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Prahalad Kumar Chahar
 */
public class MyPoliciesActivity extends AbstractActivity implements
        onRequestCompleteCallBackListener, PolicyAdaptor.OnDownloadClickListener {

    Context mContext;
    String userType, url, type, userId;
    SharedPreferences preferences;
    RecyclerView recyclerMotor;
    RecyclerView.LayoutManager mLayoutManager;
    PolicyAdaptor policyAdaptor;
    ArrayList<PolicyData> policyLists = new ArrayList<>();
    ArrayList<PolicyData> searchList = new ArrayList<>();
    ProgressDialog progressdialog;
    Bundle mBundle;
    int total;
    Spinner spnFinancialYear;
    TextView txtDateRange;
    MultiSelectionSpinner spnSource, spnLob, spnBusinessType, spnPolicyType, spnProductType,
            spnInsurer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policies_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");
        posStatus = preferences.getString(AppUtils.POS_STATUS, "");

        mBundle = new Bundle();
        url = "/account/cases/policies";
        type = "Policy";

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("wait a moment...");

        if (!TextUtils.isEmpty(userId))
            ApiManager.getInstance().userAuthentication(mContext, userId, userType);

        recyclerMotor = findViewById(R.id.policyList);
        spnFinancialYear = findViewById(R.id.spnFinancialYear);
        txtDateRange = findViewById(R.id.txtDateRange);
        spnSource = findViewById(R.id.spnSource);
        spnLob = findViewById(R.id.spnLob);
        spnBusinessType = findViewById(R.id.spnBusinessType);
        spnPolicyType = findViewById(R.id.spnPolicyType);
        spnProductType = findViewById(R.id.spnProductType);
        spnInsurer = findViewById(R.id.spnInsurer);

        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerMotor.setLayoutManager(mLayoutManager);
        getPolicyList();

        policyAdaptor = new PolicyAdaptor(mContext, policyLists);
        recyclerMotor.setAdapter(policyAdaptor);
        policyAdaptor.notifyDataSetChanged();

        ArrayList<Item> items = new ArrayList<>();
        Item i1 = new Item();
        i1.setName("Offline");
        i1.setValue(false);
        items.add(i1);

        i1 = new Item();
        i1.setName("Online");
        i1.setValue(false);
        items.add(i1);

        i1 = new Item();
        i1.setName("Excel");
        i1.setValue(false);
        items.add(i1);
        spnSource.setItems(items);

        ArrayList<Item> lobItems = new ArrayList<>();
        Item l1 = new Item();
        l1.setName("Motor");
        l1.setValue(false);
        lobItems.add(l1);

        l1 = new Item();
        l1.setName("Health");
        l1.setValue(false);
        lobItems.add(l1);

        l1 = new Item();
        l1.setName("Non Motor");
        l1.setValue(false);
        lobItems.add(l1);

        l1 = new Item();
        l1.setName("Life");
        l1.setValue(false);
        lobItems.add(l1);
        spnLob.setItems(lobItems);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (item.getItemId() == R.id.action_filter) {
            if (findViewById(R.id.cv1).getVisibility() == View.VISIBLE) {
                findViewById(R.id.cv1).setVisibility(View.GONE);
            } else findViewById(R.id.cv1).setVisibility(View.VISIBLE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof DetailedPolicyList) {
            DetailedPolicyList policyResponse = (DetailedPolicyList) response;
            if (policyResponse.isStatus()) {
                if (policyLists.size() > 0) {
                    policyLists.clear();
                    searchList.clear();
                }
                if (policyResponse.getData() != null) {
                    policyLists.addAll(policyResponse.getData());
                    searchList.addAll(policyLists);

                    total = policyLists.size();
                    this.setTitle("Policies (" + total + ")");

                    if (policyLists.size() == 0) {
                        findViewById(R.id.rr12).setVisibility(View.VISIBLE);
                    } else findViewById(R.id.rr12).setVisibility(View.GONE);
                    policyAdaptor.notifyDataSetChanged();
                }
            } else findViewById(R.id.rr12).setVisibility(View.VISIBLE);
            progressdialog.dismiss();
        }
    }

    public void getPolicyList() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                CrmManager.getInstance().getDetailedPolicy(mContext, userId, userType, type, url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getCommon() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                CrmManager.getInstance().getCommonFilters(mContext, userId, userType);
            } catch (Exception e) {
                e.printStackTrace();
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
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        MenuItem filterViewItem = menu.findItem(R.id.action_filter);
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
                policyLists.clear();
                policyLists.addAll(searchList);
                return false;
            }
        });

//        filterViewItem.setVisible(true);
        return true;
    }

    private synchronized void searchForTextInList(String searchString) {
        int textLength = searchString.length();
        policyLists.clear();
        if (searchString.trim().length() == 0) {
            policyLists.addAll(searchList);
        } else {
            searchString = searchString.replaceAll("^\\s+", "");

            for (int i = 0; i < searchList.size(); i++) {
                PolicyData assessment = searchList.get(i);

                String name = "";
                if (!TextUtils.isEmpty(assessment.getCustomerName()))
                    name = assessment.getCustomerName().toLowerCase();

                String regNo = "";
                if (!TextUtils.isEmpty(assessment.getVehicleNo()))
                    regNo = assessment.getVehicleNo().toLowerCase();

                String company = "";
                if (!TextUtils.isEmpty(assessment.getCompany()))
                    company = assessment.getCompany().toLowerCase();

                if (textLength > 0) {
                    if (regNo.contains(searchString) || company.contains(searchString) || name.contains(searchString)) {
                        policyLists.add(assessment);
                    }
                }
            }
            policyAdaptor.notifyDataSetChanged();
        }
    }

    public void onHideClick(View view) {
        findViewById(R.id.cv1).setVisibility(View.GONE);
    }

    public void onResetClick(View view) {
        startActivity(Intent.makeRestartActivityTask(this.getIntent().getComponent()));
    }

    public void onSearchClick(View view) {

    }

    public void onDateClick(View view) {
        try {
            MaterialDatePicker datePicker = MaterialDatePicker.Builder.dateRangePicker()
                    .setSelection(new Pair(MaterialDatePicker.thisMonthInUtcMilliseconds(),
                            MaterialDatePicker.todayInUtcMilliseconds()))
                    .setTitleText("Select a date range")
                    .setTheme(R.style.MaterialCalendarTheme)
                    .build();
            datePicker.show(getSupportFragmentManager(), "date");
            datePicker.addOnPositiveButtonClickListener((MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>) selection -> {
                Long startDate = selection.first;
                Long endDate = selection.second;
                String startDateString = DateFormat.format("dd/MM/yyyy", new Date(startDate)).toString();
                String endDateString = DateFormat.format("dd/MM/yyyy", new Date(endDate)).toString();
                String date = startDateString + " - " + endDateString;
                txtDateRange.setText(date);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}