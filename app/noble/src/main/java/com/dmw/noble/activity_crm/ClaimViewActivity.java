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
import com.dmw.noble.model_crm.ClaimView;
import com.dmw.noble.utils.AppUtils;

import java.util.ArrayList;
import java.util.Objects;

public class ClaimViewActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {
    Context mContext;
    RecyclerView recyclerClaimDetail;
    Bundle mBundle;
    String claimNo;
    ClaimDataViewAdaptor claimDataViewAdaptor;

    ArrayList<ClaimObj> claimDataList = new ArrayList<ClaimObj>();
    ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_view);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        mBundle = getIntent().getExtras();
        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait...");
        recyclerClaimDetail = findViewById(R.id.recyclerClaimDetail);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerClaimDetail.setLayoutManager(mLayoutManager);

        claimDataViewAdaptor = new ClaimDataViewAdaptor(mContext, claimDataList);
        recyclerClaimDetail.setAdapter(claimDataViewAdaptor);
        claimDataViewAdaptor.notifyDataSetChanged();

        if (mBundle != null) {
            claimNo = mBundle.getString(AppUtils.CLAIM_NO);
            if (!TextUtils.isEmpty(claimNo)) {
                getClaimData();
                this.setTitle(claimNo);
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

    public void getClaimData() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                CrmManager.getInstance().getClaimViewData(mContext, claimNo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof ClaimView) {
            ClaimView cResponse = (ClaimView) response;
            if (cResponse.getStatus() != null) {
                claimDataList.clear();

                if (!TextUtils.isEmpty(cResponse.getIntimatedToInsurer())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Intimated To Insurer");
                    claimObj.setValue(cResponse.getIntimatedToInsurer());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getClaimIntimatedBy())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Claim Intimated By");
                    claimObj.setValue(cResponse.getClaimIntimatedBy());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getIntimationDateTime())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Intimation Date Time");
                    claimObj.setValue(cResponse.getIntimationDateTime());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getClaimIntimationNo())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Claim Intimation No");
                    claimObj.setValue(cResponse.getClaimIntimationNo());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getIntimatorName())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Intimator Name");
                    claimObj.setValue(cResponse.getIntimatorName());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getIntimatorContactNo())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Intimator Contact No");
                    claimObj.setValue(cResponse.getIntimatorContactNo());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getAlternateNo())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Alternate No");
                    claimObj.setValue(cResponse.getAlternateNo());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getWhatsAppNo())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("WhatsApp No");
                    claimObj.setValue(cResponse.getWhatsAppNo());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getMailId())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Mail Id");
                    claimObj.setValue(cResponse.getMailId());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getLossType())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Loss Type");
                    claimObj.setValue(cResponse.getLossType());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getCauseOfLossType())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Cause Of Loss Type");
                    claimObj.setValue(cResponse.getCauseOfLossType());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getDateTimeLose())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Date Time Lose");
                    claimObj.setValue(cResponse.getDateTimeLose());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getEstimatedAmount())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Estimated Amount");
                    claimObj.setValue(cResponse.getEstimatedAmount());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getAccidentGarageName())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Accident Garage Name");
                    claimObj.setValue(cResponse.getAccidentGarageName());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getAccidentGarageNearLandMark())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Accident Near LandMark");
                    claimObj.setValue(cResponse.getAccidentGarageNearLandMark());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getGaragePincode())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Garage Pincode");
                    claimObj.setValue(cResponse.getGaragePincode());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getGarageStateId())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Garage State");
                    claimObj.setValue(cResponse.getGarageStateId());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getGarageDistrictId())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Garage District");
                    claimObj.setValue(cResponse.getGarageDistrictId());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getGarageCityId())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Garage City");
                    claimObj.setValue(cResponse.getGarageCityId());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getFirStatus())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Fir Status");
                    claimObj.setValue(cResponse.getFirStatus());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getTpStatus())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Tp Loss");
                    claimObj.setValue(cResponse.getTpStatus());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getDriverName())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Driver Detail");
                    claimObj.setValue(cResponse.getDriverName());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getDriverDLNo())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Driver DL No");
                    claimObj.setValue(cResponse.getDriverDLNo());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getDriverContactNo())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Driver Contact No");
                    claimObj.setValue(cResponse.getDriverContactNo());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getReasonDelayIntimation())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Reason Of delay Intimation");
                    claimObj.setValue(cResponse.getReasonDelayIntimation());
                    claimDataList.add(claimObj);
                }
                claimDataViewAdaptor.notifyDataSetChanged();
            }
            progressdialog.dismiss();
        }
    }
}