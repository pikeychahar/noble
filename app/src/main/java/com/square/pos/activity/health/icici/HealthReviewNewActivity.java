package com.square.pos.activity.health.icici;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.activity.health.HealthPaymentActivity;
import com.square.pos.adaptor.InsuredAdapter;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.Health.HealthManager;
import com.square.pos.model.Session;
import com.square.pos.model_health.v2.InsuredMember;
import com.square.pos.utils.AppUtils;

import java.util.ArrayList;
import java.util.Objects;

public class HealthReviewNewActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {

    RecyclerView recyclerInsured;
    Context mContext;
    Bundle mBundle;
    ImageView imgInsurer;
    ArrayList<InsuredMember> memberList = new ArrayList<>();
    TextView txtPlanName, txtQuoteId, txtPlanType, txtTenure, txtNetPremium, txtGstPremium,
            txtTotal;
    EditText edtProposerName, edtProposerAddress, edtProposerMobile, edtTotalNo, edtNomineeName,
            edtNomineeRelation, edtNomineeMobile;

    String nomineeName, nomineeRelation, nomineeMobile, quotationId, netPremium, serviceTax,
            grossPremium, planName, planType, imgPath, companyName, proposerAddress1,
            proposerAddress2;

    ArrayList<String> firstName, gender, dob;
    InsuredAdapter insuredAdapter;
    boolean isSpouse, isFather, isMother;
    int totalSon, totalDaughter;
    private ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_review_new);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mContext = this;
        mBundle = getIntent().getExtras();

        recyclerInsured = findViewById(R.id.recyclerInsured);

        imgInsurer = findViewById(R.id.imgInsureLogo);
        txtPlanName = findViewById(R.id.txtPlanName);
        txtQuoteId = findViewById(R.id.txtQuoteId);
        txtPlanType = findViewById(R.id.txtPlanType);
        txtTenure = findViewById(R.id.txtTenure);
        txtNetPremium = findViewById(R.id.net);
        txtGstPremium = findViewById(R.id.gst);
        txtTotal = findViewById(R.id.txtTotal);
        edtProposerName = findViewById(R.id.edtProposerName);
        edtProposerAddress = findViewById(R.id.edtProposerAddress);
        edtProposerMobile = findViewById(R.id.edtProposerMobile);
        edtTotalNo = findViewById(R.id.edtTotalNo);
        edtNomineeName = findViewById(R.id.edtNomineeName);
        edtNomineeRelation = findViewById(R.id.edtNomineeRelation);
        edtNomineeMobile = findViewById(R.id.edtNomineeMobile);
        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerInsured.setLayoutManager(mLayoutManager);
        insuredAdapter = new InsuredAdapter(mContext, memberList);

        if (mBundle != null) {

            quotationId = mBundle.getString(AppUtils.QUOTATION_ID_1);
            imgPath = mBundle.getString(AppUtils.HEALTH_INSURER_IMG_PATH);
            companyName = mBundle.getString(AppUtils.HL_COMPANY);

            nomineeName = mBundle.getString(AppUtils.NOMINEE_NAME);
            nomineeRelation = mBundle.getString(AppUtils.NOMINEE_RELATION);
            nomineeMobile = mBundle.getString(AppUtils.NOMINEE_MOBILE);

            proposerAddress1 = mBundle.getString(AppUtils.HL_PROPOSER_ADDRESS1);
            proposerAddress2 = mBundle.getString(AppUtils.HL_PROPOSER_ADDRESS2);

            firstName = mBundle.getStringArrayList(AppUtils.HL_INSURED_NAME);
            gender = mBundle.getStringArrayList(AppUtils.HL_INSURED_GENDER_VALUE);
            dob = mBundle.getStringArrayList(AppUtils.HL_INSURED_DOB_VALUE);

            totalSon = mBundle.getInt(AppUtils.TOTAL_SON);
            totalDaughter = mBundle.getInt(AppUtils.TOTAL_DAUGHTER);

            netPremium = mBundle.getString(AppUtils.HEALTH_NET_PREMIUM);
            grossPremium = mBundle.getString(AppUtils.HEALTH_TOTAL_PREMIUM);
            serviceTax = mBundle.getString(AppUtils.HEALTH_GST_PREMIUM);
            planName = mBundle.getString(AppUtils.HEALTH_PLAN_NAME);
            planType = mBundle.getString(AppUtils.PLAN_TYPE);

            isSpouse = mBundle.getBoolean(AppUtils.IS_SPOUSE);
            isMother = mBundle.getBoolean(AppUtils.IS_MOTHER);
            isFather = mBundle.getBoolean(AppUtils.IS_FATHER);

            if (firstName.size() > 0) {
                for (int sq = 0; sq < firstName.size(); sq++) {
                    InsuredMember insuredMember = new InsuredMember();
                    insuredMember.setName(firstName.get(sq));
                    insuredMember.setDob(dob.get(sq));
                    insuredMember.setGender(gender.get(sq));

                    if (sq == 0) {
                        insuredMember.setInsuredPerson("Self");
                    } else if (sq == 1) {
                        if (isSpouse)
                            insuredMember.setInsuredPerson("Spouse");
                        else if (isFather)
                            insuredMember.setInsuredPerson("Spouse");
                        else if (isMother)
                            insuredMember.setInsuredPerson("Mother");
                        else insuredMember.setInsuredPerson("Child 1");
                    } else if (sq == 2) {
                        if ((isMother || isFather || isSpouse)
                                && (totalDaughter > 0 || totalSon > 0)) {
                            insuredMember.setInsuredPerson("Child 1");
                        } else if (totalDaughter > 1 || totalSon > 1) {
                            insuredMember.setInsuredPerson("Child 2");
                        }
                    } else if (sq == 3) {
                        if ((isMother || isFather || isSpouse)
                                && (totalDaughter > 0 || totalSon > 0)) {
                            insuredMember.setInsuredPerson("Child 2");
                        } else if (totalDaughter > 2 || totalSon > 2) {
                            insuredMember.setInsuredPerson("Child 3");
                        }
                    } else if (sq == 4) {
                        if ((isMother || isFather || isSpouse)
                                && (totalDaughter > 0 || totalSon > 0)) {
                            insuredMember.setInsuredPerson("Child 3");
                        } else if (totalDaughter > 2 || totalSon > 2) {
                            insuredMember.setInsuredPerson("Child 4");
                        }
                    } else if (sq == 5) {
                        if ((isMother || isFather || isSpouse)
                                && (totalDaughter > 0 || totalSon > 0)) {
                            insuredMember.setInsuredPerson("Child 4");
                        }
                    }
                    memberList.add(insuredMember);
                }
            }
        }

        txtNetPremium.setText(netPremium);
        txtGstPremium.setText(serviceTax);
        txtTotal.setText(grossPremium);
        txtPlanName.setText(planName);
        txtPlanType.setText(planType);

        Glide.with(mContext)
                .load(imgPath)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .animate(android.R.anim.fade_in)
                .into(imgInsurer);

        txtQuoteId.setText(quotationId);
        edtNomineeName.setText(nomineeName);
        edtNomineeMobile.setText(nomineeMobile);
        edtNomineeRelation.setText(nomineeRelation);
        edtProposerName.setText(firstName.get(0));
        edtProposerAddress.setText(String.format("%s %s", proposerAddress1, proposerAddress2));

        if (memberList.size() > 0) {
            insuredAdapter.notifyDataSetChanged();
            recyclerInsured.setAdapter(insuredAdapter);
        }
        String total = String.valueOf(memberList.size());
        edtTotalNo.setText(total);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onShareLinkHealth(View view) {

        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Square Insurance");
            String msg = " Thank you for showing interest in Square Insurance Brokers Pvt Ltd for your Health insurance . You are just one step far from your policy . Just click on the link and make payment to generate your health policy\n";
            String url = msg + AppUtils.HEALTH_PROPOSAL_LINK + quotationId;

            shareIntent.putExtra(Intent.EXTRA_TEXT, url);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    public void onPaymentClick(View view) {
        getPaymentUrl();
    }

    public void getPaymentUrl() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                HealthManager.getInstance().getHealthPayment(mContext, quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof Session) {
            Session cResponse = (Session) response;

            if (cResponse.getStatus() != null)
                if (cResponse.getStatus().equalsIgnoreCase("1")) {

                    Intent intent = new Intent(mContext, HealthPaymentActivity.class);
                    mBundle.putString(AppUtils.HEALTH_LINK, cResponse.getURL());
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }
        }
        progressdialog.dismiss();
    }
}