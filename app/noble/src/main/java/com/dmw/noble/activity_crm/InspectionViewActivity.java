package com.dmw.noble.activity_crm;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.adaptor.crm.ClaimDataViewAdaptor;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.CrmManager;
import com.dmw.noble.model_crm.ClaimObj;
import com.dmw.noble.model_crm.SingleSurvey;
import com.dmw.noble.utils.AppUtils;

import java.util.ArrayList;
import java.util.Objects;

public class InspectionViewActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {

    Context mContext;
    RecyclerView rcInspection;
    Bundle mBundle;
    String surveyId;
    ClaimDataViewAdaptor adaptor;

    ArrayList<ClaimObj> claimDataList = new ArrayList<ClaimObj>();
    ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_view);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        mBundle = getIntent().getExtras();
        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait...");
        rcInspection = findViewById(R.id.rcInspection);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rcInspection.setLayoutManager(mLayoutManager);

        adaptor = new ClaimDataViewAdaptor(mContext, claimDataList);
        rcInspection.setAdapter(adaptor);
        adaptor.notifyDataSetChanged();

        if (mBundle != null) {
            surveyId = mBundle.getString(AppUtils.CLAIM_ID);
            if (!TextUtils.isEmpty(surveyId)) {
                singleSurveyData();
                this.setTitle(surveyId);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void singleSurveyData() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                CrmManager.getInstance().singleSurveyData(mContext, surveyId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof SingleSurvey) {
            SingleSurvey cResponse = (SingleSurvey) response;
            if (cResponse.getStatus() != null) {
                claimDataList.clear();

                if (!TextUtils.isEmpty(cResponse.getCustomerName())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Customer Name");
                    claimObj.setValue(cResponse.getCustomerName());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getCustomerEmail())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Customer Email");
                    claimObj.setValue(cResponse.getCustomerEmail());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getCustomerMobile())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Customer Contact No");
                    claimObj.setValue(cResponse.getCustomerMobile());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getCustomerMobileAlt())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Customer Alt Contact No");
                    claimObj.setValue(cResponse.getCustomerMobileAlt());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getCustomerAddress())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Customer Address");
                    claimObj.setValue(cResponse.getCustomerAddress());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getPincode())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Pincode");
                    claimObj.setValue(cResponse.getPincode());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getCity())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("City");
                    claimObj.setValue(cResponse.getCity());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getState())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("State");
                    claimObj.setValue(cResponse.getState());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getVehicleLocation())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Vehicle Location");
                    claimObj.setValue(cResponse.getVehicleLocation());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getEmployeeName())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Employee Name");
                    claimObj.setValue(cResponse.getEmployeeName());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getEmployeeMobile())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Employee Mobile");
                    claimObj.setValue(cResponse.getEmployeeMobile());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getAgentName())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Partner Name");
                    claimObj.setValue(cResponse.getAgentName());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getAgentMobile())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Partner Mobile");
                    claimObj.setValue(cResponse.getAgentMobile());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getMake())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Manufacture");
                    claimObj.setValue(cResponse.getMake());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getModel())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Model");
                    claimObj.setValue(cResponse.getModel());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getVariant())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Variant");
                    claimObj.setValue(cResponse.getVariant());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getRegistrationNo())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Registration No");
                    claimObj.setValue(cResponse.getRegistrationNo());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getRto())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("RTO Name");
                    claimObj.setValue(cResponse.getRto());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getManufactureYear())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Manufacturer year");
                    claimObj.setValue(cResponse.getManufactureYear());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getEngineNo())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Engine Number");
                    claimObj.setValue(cResponse.getEngineNo());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getChassisNo())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Chassis Number");
                    claimObj.setValue(cResponse.getChassisNo());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getCurrentRemark())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Remark");
                    claimObj.setValue(cResponse.getCurrentRemark());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getAddedBy())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Added By");
                    claimObj.setValue(cResponse.getAddedBy());
                    claimDataList.add(claimObj);
                }
                if (!TextUtils.isEmpty(cResponse.getMappedTo())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Added To");
                    claimObj.setValue(cResponse.getMappedTo());
                    claimDataList.add(claimObj);
                }
                if (!TextUtils.isEmpty(cResponse.getCurrentRemark())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Remark");
                    claimObj.setValue(cResponse.getCurrentRemark());
                    claimDataList.add(claimObj);
                }
                adaptor.notifyDataSetChanged();
            }
            progressdialog.dismiss();
        }
    }
}