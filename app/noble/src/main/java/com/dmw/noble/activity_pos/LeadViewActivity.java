package com.dmw.noble.activity_pos;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.activity.ImageActivity;
import com.dmw.noble.model_pos.Lead;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LeadViewActivity extends AbstractActivity {

    private Context mContext;
    private SharedPreferences preferences;
    private Lead leadObj;
    private TextView txtName, txtMail, txtPhone, txtAssign, txtVehicle, txtBusinessType,
            txtPreInsuranceType, txtPrePolicyExp, txtCompanyName, txtInsuranceType, txtCityName,
            txtPlanType, txtSumAssured, txtPincode, txtStatus, txtRegistration, txtDoc1, txtDoc2,
            txtPolicyPremium, txtInsurer, txtRejectionReason, txtMappedBranch, txtMappedEmp,
            txtInProgressStatus, txtInProgressTime, txtAssignTo, txtAssignToNumber;

    private String leadType, name, email, phone, assignTo, vehicle, businessTpe, preInsType,
            prePolicyExpDate, companyName, insuranceType, cityName, planType, sumAssured, pincode,
            status, registration, policyPremium, insurer, policyPdf, rejection, kyc, otherDoc,
            mappedBranch, mappedEmployee, inProgressStatus, inProgressTime, assignToName,
            assignToNumber;

    private ImageView docImage1, docImage2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_view);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);

        txtName = findViewById(R.id.txtName);
        txtMail = findViewById(R.id.txtRmContact);
        txtPhone = findViewById(R.id.txtRmName);
        txtAssign = findViewById(R.id.txtAssign);
        txtVehicle = findViewById(R.id.txtVehicle);
        txtBusinessType = findViewById(R.id.txtBusinessType);
        txtPreInsuranceType = findViewById(R.id.txtPreInsuranceType);
        txtPrePolicyExp = findViewById(R.id.txtPrePolicyExp);
        txtCompanyName = findViewById(R.id.txtCompanyName);
        txtInsuranceType = findViewById(R.id.txtInsuranceType);
        txtCityName = findViewById(R.id.txtCityName);
        txtPlanType = findViewById(R.id.txtPlanType);
        txtSumAssured = findViewById(R.id.txtSumAssured);
        txtPincode = findViewById(R.id.txtPincode);
        txtStatus = findViewById(R.id.txtStatus);
        txtRegistration = findViewById(R.id.txtRegistration);
        txtDoc1 = findViewById(R.id.txtDoc1);
        txtDoc2 = findViewById(R.id.txtDoc2);
        txtPolicyPremium = findViewById(R.id.txtPolicyPremium);
        txtInsurer = findViewById(R.id.txtInsurer);

        txtRejectionReason = findViewById(R.id.txtRejectionReason);
        txtMappedBranch = findViewById(R.id.txtMappedBranch);
        txtMappedEmp = findViewById(R.id.txtMappedEmp);
        txtInProgressStatus = findViewById(R.id.txtInProgressStatus);
        txtInProgressTime = findViewById(R.id.txtInProgressTime);
        txtAssignTo = findViewById(R.id.txtAssignTo);
        txtAssignToNumber = findViewById(R.id.txtAssignToNumber);

        docImage1 = findViewById(R.id.docImage1);
        docImage2 = findViewById(R.id.docImage2);


        final Intent intent = getIntent();
        leadObj = (Lead) intent.getSerializableExtra("lead");
//        mBundle = new Bundle();

        if (leadObj != null) {
            getLeadValues();

            if (leadType.equalsIgnoreCase("3")) {
                findViewById(R.id.cvLH).setVisibility(View.VISIBLE);

                txtPlanType.setText(planType);
                txtSumAssured.setText(sumAssured);
                txtPincode.setText(pincode);

            } else if (leadType.equalsIgnoreCase("4")) {
                findViewById(R.id.cvLC).setVisibility(View.VISIBLE);
                findViewById(R.id.llDocs1).setVisibility(View.GONE);

                txtCompanyName.setText(companyName);
                txtInsuranceType.setText(insuranceType);
                txtCityName.setText(cityName);

            } else {
                findViewById(R.id.cvLM).setVisibility(View.VISIBLE);
                txtVehicle.setText(vehicle);
                txtBusinessType.setText(businessTpe);
                txtPreInsuranceType.setText(preInsType);
                txtPrePolicyExp.setText(prePolicyExpDate);
            }

            if (TextUtils.isEmpty(mappedBranch)) {
                findViewById(R.id.rlMappedBranch).setVisibility(View.GONE);
            } else {
                findViewById(R.id.rlMappedBranch).setVisibility(View.VISIBLE);
                txtMappedBranch.setText(mappedBranch);
            }
            if (TextUtils.isEmpty(mappedEmployee)) {
                findViewById(R.id.rlMappedEmp).setVisibility(View.GONE);
            } else {
                findViewById(R.id.rlMappedEmp).setVisibility(View.VISIBLE);
                txtMappedEmp.setText(mappedEmployee);
            }

            if (TextUtils.isEmpty(assignToName)) {
                findViewById(R.id.rlAssignTo).setVisibility(View.GONE);
            } else {
                findViewById(R.id.rlAssignTo).setVisibility(View.VISIBLE);
                txtAssignTo.setText(assignToName);
                txtAssignToNumber.setText(assignToNumber);
            }

            if (TextUtils.isEmpty(inProgressStatus)) {
                findViewById(R.id.rlInProgress).setVisibility(View.GONE);
            } else {
                findViewById(R.id.rlInProgress).setVisibility(View.VISIBLE);
                txtInProgressStatus.setText(inProgressStatus);
                txtInProgressTime.setText(inProgressTime);
            }


            if (status.equalsIgnoreCase("4"))
                txtStatus.setText(R.string.assigned);
            else if (status.equalsIgnoreCase("3")) {
                txtRejectionReason.setText(rejection);
                txtStatus.setText(R.string.lost);
                findViewById(R.id.rlLost).setVisibility(View.VISIBLE);
            } else if (status.equalsIgnoreCase("2")) {
                txtStatus.setText(R.string.closed);
                txtPolicyPremium.setText(policyPremium);
                txtInsurer.setText(insurer);
                findViewById(R.id.rlClose).setVisibility(View.VISIBLE);

            }
//            else if (status.equalsIgnoreCase("5")) {
//                findViewById(R.id.rlAction).setVisibility(View.VISIBLE);
//                txtStatus.setText("Quote Released");
//            } else if (status.equalsIgnoreCase("6"))
//                txtStatus.setText("Proceed");
//            else if (status.equalsIgnoreCase("7"))
//                txtStatus.setText("Postponed");

            else
                txtStatus.setText(R.string.pending);
            if (leadType.equalsIgnoreCase("3")) {
                if (!TextUtils.isEmpty(kyc)) {
                    findViewById(R.id.llDocs1).setVisibility(View.VISIBLE);
                    txtDoc1.setText(R.string.kyc);
                    if (kyc.contains(".pdf")) {
                        docImage1.setImageDrawable(getResources().getDrawable(R.drawable.pdf));
                        docImage1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        docImage1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    Intent i = new Intent("android.intent.action.MAIN");
                                    i.setComponent(ComponentName.unflattenFromString("com.android" +
                                            ".chrome/com.android.chrome.Main"));
                                    i.addCategory("android.intent.category.LAUNCHER");
                                    i.setData(Uri.parse(kyc));
                                    startActivity(i);
                                } catch (ActivityNotFoundException e) {
                                    // Chrome is not installed
                                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(kyc));
                                    startActivity(i);
                                }
                            }
                        });

                    } else {
                        Glide.with(mContext)
                                .load(kyc)
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .into(docImage1);

                        docImage1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent1 = new Intent(mContext, ImageActivity.class);
                                intent1.putExtra("img", kyc);
                                startActivity(intent1);
                            }
                        });
                    }
                }
                if (!TextUtils.isEmpty(otherDoc)) {
                    findViewById(R.id.llDocs1).setVisibility(View.VISIBLE);

                    txtDoc2.setText("Other Document");
                    if (otherDoc.contains(".pdf")) {
                        docImage2.setImageDrawable(getResources().getDrawable(R.drawable.pdf));
                        docImage2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        docImage2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    Intent i = new Intent("android.intent.action.MAIN");
                                    i.setComponent(ComponentName.unflattenFromString("com.android" +
                                            ".chrome/com.android.chrome.Main"));
                                    i.addCategory("android.intent.category.LAUNCHER");
                                    i.setData(Uri.parse(otherDoc));
                                    startActivity(i);
                                } catch (ActivityNotFoundException e) {
                                    // Chrome is not installed
                                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(otherDoc));
                                    startActivity(i);
                                }
                            }
                        });

                    } else {
                        Glide.with(mContext)
                                .load(otherDoc)
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .into(docImage2);

                        docImage2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent1 = new Intent(mContext, ImageActivity.class);
                                intent1.putExtra("img", otherDoc);
                                startActivity(intent1);
                            }
                        });
                    }
                } else findViewById(R.id.rlImage2).setVisibility(View.GONE);
            } else if (!TextUtils.isEmpty(otherDoc)) {
                findViewById(R.id.llDocs1).setVisibility(View.VISIBLE);
                txtDoc2.setText("Other Document");
                if (otherDoc.contains(".pdf")) {
                    docImage1.setImageDrawable(getResources().getDrawable(R.drawable.pdf));
                    docImage1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    findViewById(R.id.rlImage2).setVisibility(View.GONE);

                    docImage1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Intent i = new Intent("android.intent.action.MAIN");
                                i.setComponent(ComponentName.unflattenFromString("com.android" +
                                        ".chrome/com.android.chrome.Main"));
                                i.addCategory("android.intent.category.LAUNCHER");
                                i.setData(Uri.parse(otherDoc));
                                startActivity(i);
                            } catch (ActivityNotFoundException e) {
                                // Chrome is not installed
                                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(otherDoc));
                                startActivity(i);
                            }
                        }
                    });

                } else {
                    Glide.with(mContext)
                            .load(otherDoc)
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .into(docImage1);

                    docImage1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent1 = new Intent(mContext, ImageActivity.class);
                            intent1.putExtra("img", otherDoc);
                            startActivity(intent1);
                        }
                    });
                }
            } else {

            }

            if (businessTpe.equalsIgnoreCase("new")) {

                findViewById(R.id.lblPP).setVisibility(View.GONE);
                findViewById(R.id.txtPreInsuranceType).setVisibility(View.GONE);
                findViewById(R.id.lblPE).setVisibility(View.GONE);
                findViewById(R.id.txtPrePolicyExp).setVisibility(View.GONE);
                final String invoice;
                invoice = leadObj.getInvoiceImage();

                if (!TextUtils.isEmpty(invoice)) {
                    findViewById(R.id.llDocs1).setVisibility(View.VISIBLE);
                    txtDoc1.setText(R.string.invoice);
                    if (invoice.contains(".pdf")) {
                        docImage1.setImageDrawable(getResources().getDrawable(R.drawable.pdf));
                        docImage1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        findViewById(R.id.rlImage2).setVisibility(View.GONE);
                        docImage1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    Intent i = new Intent("android.intent.action.MAIN");
                                    i.setComponent(ComponentName.unflattenFromString("com.android" +
                                            ".chrome/com.android.chrome.Main"));
                                    i.addCategory("android.intent.category.LAUNCHER");
                                    i.setData(Uri.parse(invoice));
                                    startActivity(i);
                                } catch (ActivityNotFoundException e) {
                                    // Chrome is not installed
                                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(invoice));
                                    startActivity(i);
                                }
                            }
                        });

                    } else {
                        Glide.with(mContext)
                                .load(invoice)
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .into(docImage1);

                        docImage1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent1 = new Intent(mContext, ImageActivity.class);
                                intent1.putExtra("img", invoice);
                                startActivity(intent1);
                            }
                        });
                    }
                    findViewById(R.id.rlImage2).setVisibility(View.GONE);
                }
            } else if (businessTpe.equalsIgnoreCase("rollover")) {
                List<PreviousPolicyDocument> documentList = new ArrayList<>();
                documentList = leadObj.getPreviousPolicyDocuments();
                if (documentList.size() > 0
                        && (!TextUtils.isEmpty(documentList.get(0).getImage()))) {

                    final String docImg = documentList.get(0).getImage();

                    if (!TextUtils.isEmpty(docImg)) {
                        txtDoc1.setText(R.string.doc);
                        findViewById(R.id.llDocs1).setVisibility(View.VISIBLE);
                        if (docImg.contains(".pdf")) {
                            docImage1.setImageDrawable(getResources().getDrawable(R.drawable.pdf));
                            docImage1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            docImage1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        Intent i = new Intent("android.intent.action.MAIN");
                                        i.setComponent(ComponentName.unflattenFromString("com" +
                                                ".android.chrome/com.android.chrome.Main"));
                                        i.addCategory("android.intent.category.LAUNCHER");
                                        i.setData(Uri.parse(docImg));
                                        startActivity(i);
                                    } catch (ActivityNotFoundException e) {
                                        // Chrome is not installed
                                        Intent i = new Intent(Intent.ACTION_VIEW,
                                                Uri.parse(docImg));
                                        startActivity(i);
                                    }
                                }
                            });
                        } else {

                            Glide.with(mContext)
                                    .load(docImg)
                                    .placeholder(R.drawable.placeholder)
                                    .error(R.drawable.placeholder)
                                    .into(docImage1);

                            docImage1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent1 = new Intent(mContext, ImageActivity.class);
                                    intent1.putExtra("img", docImg);
                                    startActivity(intent1);
                                }
                            });
                        }
                    }
                    if (documentList.size() >= 2) {
                        final String docImg1 = documentList.get(1).getImage();
                        if (!TextUtils.isEmpty(docImg1)) {
                            findViewById(R.id.llDocs1).setVisibility(View.VISIBLE);
                            txtDoc2.setText(R.string.doc);
                            if (docImg1.contains(".pdf")) {
                                docImage2.setImageDrawable(getResources()
                                        .getDrawable(R.drawable.pdf));
                                docImage2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                                docImage2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {
                                            Intent i = new Intent("android.intent.action" +
                                                    ".MAIN");
                                            i.setComponent(ComponentName.unflattenFromString("com" +
                                                    ".android.chrome/com.android.chrome.Main"));
                                            i.addCategory("android.intent.category.LAUNCHER");
                                            i.setData(Uri.parse(docImg1));
                                            startActivity(i);
                                        } catch (ActivityNotFoundException e) {
                                            // Chrome is not installed
                                            Intent i = new Intent(Intent.ACTION_VIEW,
                                                    Uri.parse(docImg1));
                                            startActivity(i);
                                        }
                                    }
                                });
                            } else {
                                Glide.with(mContext)
                                        .load(docImg1)
                                        .placeholder(R.drawable.placeholder)
                                        .error(R.drawable.placeholder)
                                        .into(docImage2);
                                docImage2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent1 = new Intent(mContext, ImageActivity.class);
                                        intent1.putExtra("img", docImg1);
                                        startActivity(intent1);
                                    }
                                });
                            }
                        } else findViewById(R.id.rlImage2).setVisibility(View.GONE);
                    } else findViewById(R.id.rlImage2).setVisibility(View.GONE);

                } else {
                    final String rcFront, rcBack;
                    rcFront = leadObj.getRcFrontImage();
                    rcBack = leadObj.getRcBackImage();

                    if (!TextUtils.isEmpty(rcFront)) {
                        findViewById(R.id.llDocs1).setVisibility(View.VISIBLE);
                        txtDoc1.setText(R.string.rcf);
                        if (rcFront.contains(".pdf")) {
                            docImage1.setImageDrawable(getResources().getDrawable(R.drawable.pdf));
                            docImage1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            docImage1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        Intent i = new Intent("android.intent.action.MAIN");
                                        i.setComponent(ComponentName.unflattenFromString("com" +
                                                ".android.chrome/com.android.chrome.Main"));
                                        i.addCategory("android.intent.category.LAUNCHER");
                                        i.setData(Uri.parse(rcFront));
                                        startActivity(i);
                                    } catch (ActivityNotFoundException e) {
                                        // Chrome is not installed
                                        Intent i = new Intent(Intent.ACTION_VIEW,
                                                Uri.parse(rcFront));
                                        startActivity(i);
                                    }
                                }
                            });
                        } else {
                            Glide.with(mContext)
                                    .load(rcFront)
                                    .placeholder(R.drawable.placeholder)
                                    .error(R.drawable.placeholder)
                                    .into(docImage1);
                            docImage1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent1 = new Intent(mContext, ImageActivity.class);
                                    intent1.putExtra("img", rcFront);
                                    startActivity(intent1);
                                }
                            });
                        }
                    }

                    if (!TextUtils.isEmpty(rcBack)) {
                        txtDoc2.setText(R.string.rcb);
                        if (rcBack.contains(".pdf") || rcBack.contains(".doc")) {
                            findViewById(R.id.llDocs1).setVisibility(View.VISIBLE);
                            docImage2.setImageDrawable(getResources().getDrawable(R.drawable.pdf));
                            docImage2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            docImage2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        Intent i = new Intent("android.intent.action.MAIN");
                                        i.setComponent(ComponentName.unflattenFromString("com" +
                                                ".android.chrome/com.android.chrome.Main"));
                                        i.addCategory("android.intent.category.LAUNCHER");
                                        i.setData(Uri.parse(rcBack));
                                        startActivity(i);
                                    } catch (ActivityNotFoundException e) {
                                        // Chrome is not installed
                                        Intent i = new Intent(Intent.ACTION_VIEW,
                                                Uri.parse(rcBack));
                                        startActivity(i);
                                    }
                                }
                            });
                        } else {
                            Glide.with(mContext)
                                    .load(rcBack)
                                    .placeholder(R.drawable.placeholder)
                                    .error(R.drawable.placeholder)
                                    .into(docImage2);
                            docImage2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent1 = new Intent(mContext, ImageActivity.class);
                                    intent1.putExtra("img", rcBack);
                                    startActivity(intent1);
                                }
                            });
                        }
                    }
                }
            }

        }
//        spnAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                mBundle.putInt("action", position);
//                Intent intent = new Intent(mContext, LeadActionActivity.class);
//                intent.putExtras(mBundle);
//                intent.putExtra("lead", leadObj);
//
//                if (position != 0) {
//                    startActivity(intent);
//                    finish();
//                }
//            }

//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//
//            }
//
//        });
    }

    private void getLeadValues() {
        leadType = leadObj.getType();
        name = leadObj.getName();

        txtName.setText(name);

        email = leadObj.getEmail();

        if (!TextUtils.isEmpty(email))
            email = email.toLowerCase();
        txtMail.setText(email);

        phone = leadObj.getMobile();
        txtPhone.setText(phone);

        assignTo = leadObj.getMappedEmployee();
        txtAssign.setText(assignTo);

        vehicle = leadObj.getMake() + " " + leadObj.getModel() + " " + leadObj.getVariant();
        businessTpe = leadObj.getFileType();
        preInsType = leadObj.getPolicyType();
        prePolicyExpDate = leadObj.getExpiryDate();
        companyName = leadObj.getOrganisationName();
        insuranceType = leadObj.getCorporateInsType();
        cityName = leadObj.getCity();
        planType = leadObj.getPlanType();
        sumAssured = leadObj.getSumAssured();
        pincode = leadObj.getPincode();
        status = leadObj.getStatus();
        rejection = leadObj.getRejectionReason();
        policyPremium = leadObj.getPolicyPremium();
        policyPdf = leadObj.getPolicyPdf();
        insurer = leadObj.getPolicyCompanyName();
        registration = leadObj.getGadiNo();
        txtRegistration.setText(registration);

        kyc = leadObj.getKycDocuments();
        otherDoc = leadObj.getOtherDocuments();

        mappedBranch = leadObj.getMappedBranch();
        mappedEmployee = leadObj.getMappedEmployee();

        inProgressStatus = leadObj.getInProcessStatus();
        inProgressTime = leadObj.getInProcessTime();

        assignToName = leadObj.getAssignedToName();
        assignToNumber = leadObj.getAssignedToMobile();
//
//        txtLeadId.setText(leadObj.getLeadId());
//        txtRemark.setText(leadObj.getRemarks());

    }

    public void onDownloadPdf(View view) {
        if (!TextUtils.isEmpty(policyPdf)) {
            try {
                Intent i = new Intent("android.intent.action.MAIN");
                i.setComponent(ComponentName.unflattenFromString("com" +
                        ".android.chrome/com.android.chrome.Main"));
                i.addCategory("android.intent.category.LAUNCHER");
                i.setData(Uri.parse(policyPdf));
                startActivity(i);
            } catch (ActivityNotFoundException e) {
                // Chrome is not installed
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(policyPdf));
                startActivity(i);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (itemId == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onUploadFile(View view) {
    }

    public void onDatePicker(View view) {

    }

}