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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.activity.ImageActivity;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.CrmManager;
import com.dmw.noble.model_crm.OfflineView;
import com.dmw.noble.utils.AppUtils;

import java.util.Objects;

public class ViewOfflineActivity extends AbstractActivity implements onRequestCompleteCallBackListener {
    String userId, userType;
    SharedPreferences preferences;
    ProgressDialog progressdialog;
    Bundle mBundle;
    Context mContext;
    String quotationId, invoice, rcFront, rcBack, prePolicy, other;
    EditText edtQuoteId, edtVehType, edtRegNo, edtRegDate, edtVehStatus, edtPolicyType, edtPypExpDt,
            edtPreviousInsurer, edtClaim, edtOwner, edtNcbProtection, edtNcb;
    Button btnDoc1, btnDoc2, btnDoc3, btnDoc4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_offline);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");

        edtQuoteId = findViewById(R.id.edtQuoteId);
        edtVehType = findViewById(R.id.edtVehType);
        edtRegNo = findViewById(R.id.edtRegno);
        edtRegDate = findViewById(R.id.edtRegDt);
        edtVehStatus = findViewById(R.id.edtVehStatus);
        edtPolicyType = findViewById(R.id.edtPolicyType);
        edtPreviousInsurer = findViewById(R.id.edtPreviousInsurer);
        edtPypExpDt = findViewById(R.id.edtPypExpDt);
        edtClaim = findViewById(R.id.edtClaim);
        edtOwner = findViewById(R.id.edtOwner);
        edtNcbProtection = findViewById(R.id.edtNcbProtection);
        edtNcb = findViewById(R.id.edtNcb);

        btnDoc1 = findViewById(R.id.btnDoc1);
        btnDoc2 = findViewById(R.id.btnDoc2);
        btnDoc3 = findViewById(R.id.btnDoc3);
        btnDoc4 = findViewById(R.id.btnDoc4);

        mContext = this;
        mBundle = getIntent().getExtras();

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");

        if (mBundle != null) {
            quotationId = mBundle.getString(AppUtils.QUOTATION_ID);
            getOfflineQuoteDetail();
        }
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof OfflineView) {
            OfflineView oResponse = (OfflineView) response;
            if (oResponse.getStatus()) {
                edtQuoteId.setText(oResponse.getData().getQuotation());

                if (!TextUtils.isEmpty(oResponse.getData().getProductType()))
                    edtVehType.setText(oResponse.getData().getProductType());
                else findViewById(R.id.txtVehType).setVisibility(View.GONE);

                if (!TextUtils.isEmpty(oResponse.getData().getRegistrationNo()))
                    edtRegNo.setText(oResponse.getData().getRegistrationNo());
                else findViewById(R.id.txtRegno).setVisibility(View.GONE);

                if (!TextUtils.isEmpty(oResponse.getData().getRegistrationDate()))
                    edtRegDate.setText(oResponse.getData().getRegistrationDate());
                else findViewById(R.id.txtRegDate).setVisibility(View.GONE);

                if (!TextUtils.isEmpty(oResponse.getData().getPlanType()))
                    edtPolicyType.setText(oResponse.getData().getPlanType());
                else findViewById(R.id.txtPolicyType).setVisibility(View.GONE);

                if (!TextUtils.isEmpty(oResponse.getData().getPreviousInsurer()))
                    edtPreviousInsurer.setText(oResponse.getData().getPreviousInsurer());
                else findViewById(R.id.txtPreviousInsurer).setVisibility(View.GONE);

                if (!TextUtils.isEmpty(oResponse.getData().getPreviousExpiryDate()))
                    edtPypExpDt.setText(oResponse.getData().getPreviousExpiryDate());
                else findViewById(R.id.txtPypExpDt).setVisibility(View.GONE);

                if (!TextUtils.isEmpty(oResponse.getData().getClaimStatus()))
                    edtClaim.setText(oResponse.getData().getClaimStatus());
                else findViewById(R.id.txtClaim).setVisibility(View.GONE);

                if (!TextUtils.isEmpty(oResponse.getData().getOwnerChange()))
                    edtOwner.setText(oResponse.getData().getOwnerChange());
                else findViewById(R.id.txtOwner).setVisibility(View.GONE);

                if (!TextUtils.isEmpty(oResponse.getData().getNcbProtection()))
                    edtNcbProtection.setText(oResponse.getData().getNcbProtection());
                else findViewById(R.id.txtNcbProtection).setVisibility(View.GONE);

                if (!TextUtils.isEmpty(oResponse.getData().getLastYearNcb()))
                    edtNcb.setText(oResponse.getData().getLastYearNcb());
                else findViewById(R.id.txtNcb).setVisibility(View.GONE);

                if (!TextUtils.isEmpty(oResponse.getData().getFileType())) {
                    edtVehStatus.setText(oResponse.getData().getFileType());
                    if (oResponse.getData().getFileType().equalsIgnoreCase("new")) {

                        findViewById(R.id.txtPypExpDt).setVisibility(View.GONE);

                        invoice = oResponse.getData().getInvoice();
                        other = oResponse.getData().getOtherDocument();

                        if (!TextUtils.isEmpty(invoice) && !invoice.equals("0")) {
                            btnDoc1.setVisibility(View.VISIBLE);
                            btnDoc1.setText("Invoice");

                        }

                        if (!TextUtils.isEmpty(other) && !other.equals("0")) {
                            btnDoc2.setVisibility(View.VISIBLE);
                            btnDoc2.setText("Other Doc");
                        }
                    } else {
                        rcFront = oResponse.getData().getRCFront();
                        rcBack = oResponse.getData().getRCBack();
                        prePolicy = oResponse.getData().getPreviousPolicy();
                        other = oResponse.getData().getOtherDocument();

                        if (!TextUtils.isEmpty(rcFront) && !rcFront.equals("0")) {
                            btnDoc1.setVisibility(View.VISIBLE);
                            btnDoc1.setText("RC Front");
                        }

                        if (!TextUtils.isEmpty(rcBack) && !rcBack.equals("0")) {
                            btnDoc2.setVisibility(View.VISIBLE);
                            btnDoc2.setText("RC Back");
                        }

                        if (!TextUtils.isEmpty(prePolicy) && !prePolicy.equals("0")) {
                            btnDoc3.setVisibility(View.VISIBLE);
                            btnDoc3.setText("Pre. Policy");
                        }

                        if (!TextUtils.isEmpty(other) && !other.equals("0")) {
                            btnDoc4.setVisibility(View.VISIBLE);
                            btnDoc4.setText("Other Doc");
                        }
                    }
                } else findViewById(R.id.txtVehStatus).setVisibility(View.GONE);

                progressdialog.dismiss();
            }
        }
    }

    public void getOfflineQuoteDetail() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                CrmManager.getInstance().getOfflineQuoteDetail(mContext, userId, userType,
                        quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void onOfflineDocView(View view) {
        Button btn = (Button) view;
        String str = btn.getText().toString();
        String pdfUrl = "";

        switch (str) {
            case "Invoice":
                pdfUrl = invoice;
                break;
            case "RC Front":
                pdfUrl = rcFront;
                break;
            case "RC Back":
                pdfUrl = rcBack;
                break;
            case "Pre. Policy":
                pdfUrl = prePolicy;
                break;
            case "Other Doc":
                pdfUrl = other;
                break;
        }

        if (pdfUrl.contains("pdf")) {
            try {
                Intent i = new Intent("android.intent.action.MAIN");
                i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                i.addCategory("android.intent.category.LAUNCHER");
                i.setData(Uri.parse(pdfUrl));
                startActivity(i);
            } catch (ActivityNotFoundException e) {
                // Chrome is not installed
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl));
                startActivity(i);
            }
        } else {
            Intent intent1 = new Intent(mContext, ImageActivity.class);
            intent1.putExtra("img", pdfUrl);
            startActivity(intent1);
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
}