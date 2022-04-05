package com.dmw.noble.activity_motor;

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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.adaptor.InspectionAdapter;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.model_pos.Quotation;
import com.dmw.noble.model_pos.QuotationList;
import com.dmw.noble.utils.AppUtils;

import java.util.ArrayList;
import java.util.Objects;

public class InspectionListActivity extends AbstractActivity implements
        onRequestCompleteCallBackListener, InspectionAdapter.OnProceedClick {

    ArrayList<Quotation> quotationList = new ArrayList<>();
    ArrayList<Quotation> searchList = new ArrayList<>();
    InspectionAdapter inspectionAdapter;
    Context mContext;
    RecyclerView recyclerView;
    String agentId, userType;
    RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences preferences;
    ProgressDialog progressdialog;
    Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        mContext = this;
        mBundle = new Bundle();

        agentId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");

        if (!TextUtils.isEmpty(userType))
            if (userType.equals("agent"))
                userType = "2";

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");
        recyclerView = findViewById(R.id.quotationList);
        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);

        getInspection();
        inspectionAdapter = new InspectionAdapter(mContext, quotationList);
        recyclerView.setAdapter(inspectionAdapter);
        inspectionAdapter.notifyDataSetChanged();
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
        try {
            if (response instanceof QuotationList) {
                QuotationList policyResponse = (QuotationList) response;
                if (policyResponse.getStatus() == 1) {
                    if (quotationList.size() > 0)
                        quotationList.clear();
                    searchList.clear();
                    if (policyResponse.getPolicy() != null) {
                        quotationList.addAll(policyResponse.getPolicy());
                        searchList.addAll(quotationList);
                        if (quotationList.size() == 0) {
                            findViewById(R.id.rlNoPolicies).setVisibility(View.VISIBLE);
                        } else findViewById(R.id.rlNoPolicies).setVisibility(View.GONE);
                        inspectionAdapter.notifyDataSetChanged();
                    }
                }
                if (quotationList.size() == 0) {
                    findViewById(R.id.rlNoPolicies).setVisibility(View.VISIBLE);
                } else findViewById(R.id.rlNoPolicies).setVisibility(View.GONE);
            }
            progressdialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getInspection() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().getInspection(mContext, userType, agentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void OnProceedListener(int pos) {

        String qId = quotationList.get(pos).getQuotationId();
        String insId = quotationList.get(pos).getLastInsertId();
        String newVehicle = quotationList.get(pos).getNewGadi();
        String regYear = quotationList.get(pos).getRegistrationYear();
        String vehicleType = quotationList.get(pos).getVehicleType();
        String vehicle = quotationList.get(pos).getVehicle();
        String reg = quotationList.get(pos).getRegistrationNo();
        String prePolicy = quotationList.get(pos).getIsPrevious();
        String prePolicyName = quotationList.get(pos).getPolicyType();

        String purchaseDate = quotationList.get(pos).getPurchaseDate();
        String ManufactureDate = quotationList.get(pos).getManufactureDate();
        String registrationDate = quotationList.get(pos).getRegistrationDate();
        String policyExpDate = quotationList.get(pos).getPolicyExpieryDate();
        String preNcb = quotationList.get(pos).getNcbOld();
        String preClaim = quotationList.get(pos).getClaimExpiringPolicy();
        String ownerChange = quotationList.get(pos).getOwnerChange();
        String tpOnly = quotationList.get(pos).getTpOnly();
        String controlId = quotationList.get(pos).getControllId();

        String saodInsurer = quotationList.get(pos).getTpExpirePolicyCpmpany();
        String saodDate = quotationList.get(pos).getTpPolicyExpireDate();
        String saodPolicyNo = quotationList.get(pos).getTpExpirePolicyNumber();
        String previousInsurer = quotationList.get(pos).getPreviousInsurer();
        String pcvCompany = quotationList.get(pos).getPcvCompany();
        String make = quotationList.get(pos).getManufacture();
        String model = quotationList.get(pos).getModel();
        String fuel = quotationList.get(pos).getFuelType();
        String variant = quotationList.get(pos).getVariant();
        String policyExp = quotationList.get(pos).getPolicyExpire();

        Intent intent = new Intent(mContext, OverViewActivity.class);
        mBundle.putString(AppUtils.QUOTATION_ID, qId);
        mBundle.putString(AppUtils.INSERT_ID, insId);
        mBundle.putString(AppUtils.REGISTRATION_YEAR, regYear);
        mBundle.putInt(AppUtils.REG_YEAR, Integer.parseInt(regYear));
        mBundle.putString(AppUtils.NEW_VEHICLE, newVehicle);
        mBundle.putString(AppUtils.VEHICLE_TYPE, vehicleType);
        mBundle.putString(AppUtils.VEHICLE_FULL, vehicle);
        mBundle.putString(AppUtils.REGISTRATION_NUMBER, reg);
        mBundle.putString(AppUtils.IS_PREVIOUS, prePolicy);
        mBundle.putString(AppUtils.PRE_POLICY_TYPE, prePolicyName);
        mBundle.putString(AppUtils.PURCHASE_DATE, purchaseDate);
        mBundle.putString(AppUtils.REGISTRATION_DATE, registrationDate);
        mBundle.putString(AppUtils.MANUFACTURE_DATE, ManufactureDate);
        mBundle.putString(AppUtils.POLICY_EXPIRY_DATE, policyExpDate);
        mBundle.putString(AppUtils.POLICY_EXPIRY, policyExp);
        mBundle.putString(AppUtils.PRE_NCB, preNcb);
        mBundle.putString(AppUtils.CLAIM_EXP, preClaim);
        mBundle.putString(AppUtils.OWNER_CHANGE, ownerChange);
        mBundle.putString(AppUtils.TP_ONLY, tpOnly);
        mBundle.putBoolean(AppUtils.OLD_QUOTE, true);
        mBundle.putString(AppUtils.CONTROL_ID, controlId);
        mBundle.putString(AppUtils.SAOD_INSURER, saodInsurer);
        mBundle.putString(AppUtils.SAOD_POLICY_NO, saodPolicyNo);
        mBundle.putString(AppUtils.SAOD_TP_EXP_DATE, saodDate);
        mBundle.putString(AppUtils.INSURER, previousInsurer);
        mBundle.putString(AppUtils.PCV_COMPANY, pcvCompany);
        mBundle.putString(AppUtils.MAKE, make);
        mBundle.putString(AppUtils.MODEL, model);
        mBundle.putString(AppUtils.FUEL_TYPE, fuel);
        mBundle.putString(AppUtils.VARIANT, variant);
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
                Quotation assessment = searchList.get(i);
                String userName = "";
                if (!TextUtils.isEmpty(assessment.getRegistrationNo()))
                    userName = assessment.getRegistrationNo().toLowerCase();
                String contactNo = "";
                if (!TextUtils.isEmpty(assessment.getCompany()))
                    contactNo = assessment.getCompany();
                if (textLength > 0) {
                    if (userName.contains(searchString) || contactNo.contains(searchString)) {
                        quotationList.add(assessment);
                    }
                }
            }
            inspectionAdapter.notifyDataSetChanged();
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
                inspectionAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return true;
    }
}