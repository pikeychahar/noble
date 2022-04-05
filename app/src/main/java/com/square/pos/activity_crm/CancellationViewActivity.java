package com.square.pos.activity_crm;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.adaptor.crm.ClaimDataViewAdaptor;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.CrmManager;
import com.square.pos.model_crm.CancellationData;
import com.square.pos.model_crm.ClaimObj;
import com.square.pos.utils.AppUtils;

import java.util.ArrayList;
import java.util.Objects;

public class CancellationViewActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {
    Context mContext;
    RecyclerView recyclerClaimDetail;
    Bundle mBundle;
    String cancellationId;
    ClaimDataViewAdaptor adaptor;

    ArrayList<ClaimObj> claimDataList = new ArrayList<ClaimObj>();
    ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancellation_view);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        mBundle = getIntent().getExtras();
        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait...");
        recyclerClaimDetail = findViewById(R.id.recyclerCancellationDetail);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerClaimDetail.setLayoutManager(mLayoutManager);

        adaptor = new ClaimDataViewAdaptor(mContext, claimDataList);
        recyclerClaimDetail.setAdapter(adaptor);
        adaptor.notifyDataSetChanged();

        if (mBundle != null) {
            cancellationId = mBundle.getString(AppUtils.CLAIM_NO);
            String titleId = mBundle.getString(AppUtils.CLAIM_ID);
            if (!TextUtils.isEmpty(cancellationId)) {
                getCancellationSingle();
                if (!TextUtils.isEmpty(titleId))
                this.setTitle(titleId);
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

    public void getCancellationSingle() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                CrmManager.getInstance().getCancellationSingle(mContext, cancellationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof CancellationData) {
            CancellationData cResponse = (CancellationData) response;
            if (cResponse.getStatus() != null) {
                claimDataList.clear();

                if (!TextUtils.isEmpty(cResponse.getData().getBrokerName())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Broker Name");
                    claimObj.setValue(cResponse.getData().getBrokerName());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getData().getRMName())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("RM Name");
                    claimObj.setValue(cResponse.getData().getRMName());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getData().getProductId())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Product");
                    claimObj.setValue(cResponse.getData().getProductId());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getData().getInsuranceCompanyName())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Insurance Company");
                    claimObj.setValue(cResponse.getData().getInsuranceCompanyName());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getData().getCustomerName())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Customer Name");
                    claimObj.setValue(cResponse.getData().getCustomerName());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getData().getPolicyStartDateOD())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Policy (OD) Start/End Date");
                    claimObj.setValue(cResponse.getData().getPolicyStartDateOD());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getData().getPolicyStartDateTP())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Policy (TP) Start/End Date");
                    claimObj.setValue(cResponse.getData().getPolicyStartDateTP());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getData().getPolicyNo())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Policy No");
                    claimObj.setValue(cResponse.getData().getPolicyNo());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getData().getPaymentTowards())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Payment Favour");
                    claimObj.setValue(cResponse.getData().getPaymentTowards());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getData().getModeOfPayment())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Mode Of Payment");
                    claimObj.setValue(cResponse.getData().getModeOfPayment());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getData().getNetPremium())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Net Premium");
                    claimObj.setValue(cResponse.getData().getNetPremium());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getData().getEstimatedGrossPremium())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Gross Premium");
                    claimObj.setValue(cResponse.getData().getEstimatedGrossPremium());
                    claimDataList.add(claimObj);
                }
                if (!TextUtils.isEmpty(cResponse.getData().getTerrorismPremium())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Terrorism Premium");
                    claimObj.setValue(cResponse.getData().getEstimatedGrossPremium());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getData().getIdv())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("IDV");
                    claimObj.setValue(cResponse.getData().getIdv());
                    claimDataList.add(claimObj);
                }

                if (!TextUtils.isEmpty(cResponse.getData().getNcb())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("NCB");
                    claimObj.setValue(cResponse.getData().getNcb());
                    claimDataList.add(claimObj);
                }
                if (!TextUtils.isEmpty(cResponse.getData().getDiscount())) {
                    ClaimObj claimObj = new ClaimObj();
                    claimObj.setLabel("Discount");
                    claimObj.setValue(cResponse.getData().getDiscount());
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