package com.square.pos.activity_motor;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.activity.MobileVerifyActivity;
import com.square.pos.activity.PrivacyActivity;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.ApiManager;
import com.square.pos.manager.CrmManager;
import com.square.pos.manager.UserManager;
import com.square.pos.model.CommonResponse;
import com.square.pos.model.Review;
import com.square.pos.model.SendOtp;
import com.square.pos.model.Session;
import com.square.pos.model_crm.RaiseTicket;
import com.square.pos.utils.AppUtils;
import com.square.pos.utils.MyTagHandler;
import com.square.pos.utils.RealPathUtil;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class OverViewActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {

    Context mContext;
    SharedPreferences preferences;
    Bundle mBundle;

    TextView txtIDV, txtNetPremium, textTotal, txtGst, uploadInvoice, uploadPrePolicy,
            uploadRCF, uploadRCB, commonTextView, txtQuoteId, txtError, txtVehiclePolicy;

    final CharSequence[] items = {"Take Photo", "Photos", "Document"};
    File photoFile = null;

    EditText edtOName, edtOAddress, edtOMobile, edtNName, edtNRelation, edtNAge,
            edtPolicyType, edtAddOn, edtRegistration, edtEngine, edtChassis, edtVehicleModel,
            edtPolicyStartDate, edtPolicyEndDate;

    String quotationId, ownerName, ownerAddress, ownerMobile, nomineeName, nomineeRelation,
            nomineeAge, policyType, addOn, registrationNumber, engine, chassis, vehicle,
            policyStartDate, policyEndDate, idv, netPremium, gst, company_Name, isNewVehicle,
            mCurrentPhotoPath, agentId, invoice, rcFront, rcBack, businessType, policyPdf,
            prePolicy, ownedBy, url, vehicleType, imgPath, userType, tpOnly, breakingAllowed,
            inspectionRaised, remark;

    ProgressDialog progressdialog;
    ImageView imgInsureLogo, imgStatus;
    Button btnPay;
    int flag = 1;
    boolean isDocUpdated, isDocUpdatedNew;
    RelativeLayout rlMainDoc;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    boolean isCameraPermitted = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_view);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        mBundle = getIntent().getExtras();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        agentId = preferences.getString(AppUtils.PRIMARY_ID, "");
        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");
        businessType = "RO";
        tpOnly = "";

        userType = preferences.getString(AppUtils.USER_TYPE, "");
        if (!TextUtils.isEmpty(agentId))
            ApiManager.getInstance().userAuthentication(mContext, agentId, userType);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        textTotal = findViewById(R.id.total);
        txtIDV = findViewById(R.id.edtIdv);
        txtNetPremium = findViewById(R.id.net);
        txtGst = findViewById(R.id.gst);
        uploadInvoice = findViewById(R.id.uploadFile);
        uploadPrePolicy = findViewById(R.id.uploadPrevious);
        uploadRCF = findViewById(R.id.fileRCFront);
        uploadRCB = findViewById(R.id.fileRCBack);
        btnPay = findViewById(R.id.btnPay);
        txtVehiclePolicy = findViewById(R.id.txtVehiclePolicy);
        rlMainDoc = findViewById(R.id.rlMainDoc);

        txtQuoteId = findViewById(R.id.txtQuoteId);
        txtError = findViewById(R.id.txtError);

        edtOName = findViewById(R.id.edtOName);
        edtOAddress = findViewById(R.id.edtOAddress);
        edtOMobile = findViewById(R.id.edtOMobile);
        edtNName = findViewById(R.id.edtNName);
        edtNRelation = findViewById(R.id.edtNRelation);
        edtNAge = findViewById(R.id.edtNAge);
        edtPolicyType = findViewById(R.id.edtPolicyType);
        edtAddOn = findViewById(R.id.edtAddOn);
        edtRegistration = findViewById(R.id.edtRegistration);
        edtEngine = findViewById(R.id.edtEngine);
        edtChassis = findViewById(R.id.edtChassis);
        edtVehicleModel = findViewById(R.id.edtVehicleModel);
        edtPolicyStartDate = findViewById(R.id.edtPolicyStart);
        edtPolicyEndDate = findViewById(R.id.txtPolicyEnd);

        imgInsureLogo = findViewById(R.id.imgInsureLogo);
        imgStatus = findViewById(R.id.imgStatus);

        if (mBundle != null) {
            quotationId = mBundle.getString(AppUtils.QUOTATION_ID);
            getSupportActionBar().setSubtitle(quotationId);

            ownerName = mBundle.getString(AppUtils.OWNER_NAME);
            ownerAddress = mBundle.getString(AppUtils.OWNER_ADDRESS);
            ownerMobile = mBundle.getString(AppUtils.OWNER_PHONE);
            nomineeName = mBundle.getString(AppUtils.NOMINEE_NAME);
            nomineeRelation = mBundle.getString(AppUtils.NOMINEE_RELATION);
            nomineeAge = mBundle.getString(AppUtils.NOMINEE_AGE);
            policyType = mBundle.getString(AppUtils.POLICY_TYPE);
            addOn = mBundle.getString(AppUtils.ADDON_COVER);
            registrationNumber = mBundle.getString(AppUtils.REGISTRATION_NUMBER);
            engine = mBundle.getString(AppUtils.ENGINE);
            chassis = mBundle.getString(AppUtils.CHASSIS);
            policyStartDate = mBundle.getString(AppUtils.POLICY_START_DATE);
            policyEndDate = mBundle.getString(AppUtils.POLICY_END_DATE);
            idv = mBundle.getString(AppUtils.IDV);
            netPremium = mBundle.getString(AppUtils.NET_PREMIUM);
            gst = mBundle.getString(AppUtils.GST);
            vehicle = mBundle.getString(AppUtils.VEHICLE_FULL);
            company_Name = mBundle.getString(AppUtils.COMPANY_NAME);
            isNewVehicle = mBundle.getString(AppUtils.NEW_VEHICLE);
            vehicleType = mBundle.getString(AppUtils.VEHICLE_TYPE);
            prePolicy = mBundle.getString(AppUtils.IS_PREVIOUS);
            ownedBy = mBundle.getString(AppUtils.OWNED_BY);
            url = mBundle.getString(AppUtils.ACKO_PAYMENT);
            imgPath = mBundle.getString(AppUtils.IMG_PATH);

            txtQuoteId.setText(quotationId);
            getReviewData();

            if (!TextUtils.isEmpty(vehicleType)) {
                switch (vehicleType) {
                    case "1":
                        txtVehiclePolicy.setText("TWO WHEELER INSURANCE");
                        break;
                    case "2":
                        txtVehiclePolicy.setText("PVT CAR INSURANCE");
                        break;
                    case "3":
                        txtVehiclePolicy.setText("PCV INSURANCE");
                        break;
                    case "4":
                        txtVehiclePolicy.setText("GCV INSURANCE");
                        break;
                    case "5":
                        txtVehiclePolicy.setText("MISD INSURANCE");
                        break;
                }
            }

            if (isNewVehicle != null) {
                if (isNewVehicle.equals("new_gadi")) {
                    findViewById(R.id.rlRO).setVisibility(View.GONE);
                    findViewById(R.id.rlInvoice).setVisibility(View.VISIBLE);
                    businessType = "NB";
                }
            } else {
                findViewById(R.id.rlRO).setVisibility(View.VISIBLE);
                findViewById(R.id.rlInvoice).setVisibility(View.GONE);

            }

            if (!TextUtils.isEmpty(prePolicy)) {
                if (prePolicy.equals("1")) {
                    findViewById(R.id.rlPrevious).setVisibility(View.GONE);
                }
            }

            Glide.with(mContext)
                    .load(imgPath)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(imgInsureLogo);

            if (!TextUtils.isEmpty(ownerName))
                edtOName.setText(ownerName);

            if (!TextUtils.isEmpty(idv)) {
                txtIDV.setText(idv);
                if (idv.equals("1")) {
                    txtIDV.setVisibility(View.GONE);
                }
            } else {
                txtIDV.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(netPremium))
                txtNetPremium.setText(netPremium);

            if (!TextUtils.isEmpty(gst))
                txtGst.setText(gst);

            if (!TextUtils.isEmpty(ownerAddress))
                edtOAddress.setText(ownerAddress);

            if (!TextUtils.isEmpty(ownerMobile))
                edtOMobile.setText(ownerMobile);

            if (!TextUtils.isEmpty(nomineeName))
                edtNName.setText(nomineeName);

            if (!TextUtils.isEmpty(nomineeRelation))
                edtNRelation.setText(nomineeRelation);

            if (!TextUtils.isEmpty(nomineeAge))
                edtNAge.setText(nomineeAge);

            if (!TextUtils.isEmpty(policyType))
                edtPolicyType.setText(policyType);

            if (!TextUtils.isEmpty(addOn)) {
                if (addOn.equals("1"))
                    edtAddOn.setText("Zero Dep");
                else edtAddOn.setText("NA");

                edtAddOn.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(registrationNumber))
                edtRegistration.setText(registrationNumber);
            else edtRegistration.setText("New Vehicle");

            if (!TextUtils.isEmpty(engine))
                edtEngine.setText(engine);

            if (!TextUtils.isEmpty(chassis))
                edtChassis.setText(chassis);

            if (!TextUtils.isEmpty(policyStartDate))
                edtPolicyStartDate.setText(policyStartDate);

            if (!TextUtils.isEmpty(policyEndDate))
                edtPolicyEndDate.setText(policyEndDate);

            if (!TextUtils.isEmpty(vehicle))
                edtVehicleModel.setText(vehicle);
        }

        btnPay.setOnClickListener(v -> {
            txtError.setVisibility(View.GONE);
            flag = 1;
            if (isDocUpdated) {
                if (TextUtils.isEmpty(invoice)
                        && TextUtils.isEmpty(policyPdf)
                        && TextUtils.isEmpty(rcFront)
                        && TextUtils.isEmpty(rcBack)) {
                    proposal();
                } else if (isDocUpdatedNew) {
                    proposal();
                } else {
                    if (isFileUpdated())
                        uploadDocument();
                }
            } else {
                if (isFileUpdated())
                    uploadDocument();
            }
        });

        if (!TextUtils.isEmpty(ownedBy))
            if (ownedBy.equals("Individual") && !TextUtils.isEmpty(nomineeName))
                findViewById(R.id.rlNominee).setVisibility(View.VISIBLE);
            else
                findViewById(R.id.rlNominee).setVisibility(View.GONE);

        checkCameraPermission();
    }

    private void proposal() {
        progressdialog.setTitle("Redirecting to Payment..");
        getPaymentLink();
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    MY_CAMERA_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isCameraPermitted = true;

            } else {
                isCameraPermitted = false;
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void bounceAnim() {
        ObjectAnimator animY = ObjectAnimator.ofFloat(imgStatus, "translationX", -90f, 0);
        animY.setDuration(1000);//1sec
        animY.setInterpolator(new BounceInterpolator());
        animY.setRepeatCount(1);
        animY.start();
    }

    public void getReviewData() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getReviewData(mContext, quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ref_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_refresh:
                getReviewData();
                return true;
            case R.id.action_share:
                onShareLink(txtGst);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initReviewData(Review data) {
        String logo, idv, net, gst, finalPremium, startDate, endDate, fullName, address, mobile,
                chassis, engine, nmName, nmRelation, nmDob, vFull, docStatus, newV;

        docStatus = data.getDocStatus();

        if (!TextUtils.isEmpty(docStatus))
            if (docStatus.equals("1")) {
                imgStatus.setVisibility(View.VISIBLE);
                bounceAnim();
                isDocUpdated = true;
            } else {
                imgStatus.setVisibility(View.GONE);
                isDocUpdated = false;
            }

        logo = data.getLogo();
        idv = data.getIdv();
        gst = data.getTax();
        net = data.getNetPremium();
        finalPremium = data.getPremium();
        startDate = data.getPolicyStartDate();
        endDate = data.getPolicyEndDate();
        tpOnly = data.getTpOnly();
        fullName = data.getFullName();
        address = data.getAddress();
        company_Name = data.getCompany();
        mobile = data.getMobileProposal();
        chassis = data.getChassiesNo();
        engine = data.getEngineNo();
        nmName = data.getNomineeName();
        nmRelation = data.getNomineeRelation();
        nmDob = data.getNomineeDob();
        vFull = data.getVehicle();
        vehicleType = data.getVehicleType();
        newV = data.getNewVehicle();
        registrationNumber = data.getRegistrationNo();

        breakingAllowed = data.getBreakingAllowed();
        inspectionRaised = data.getInspectionRaised();

        if (!TextUtils.isEmpty(breakingAllowed) && !vehicleType.equals("1"))
            if (breakingAllowed.equals("1")) {
                if (inspectionRaised.equals("0"))
                    btnPay.setText("Raise Inspection");
                else if (inspectionRaised.equals("1")) {
                    btnPay.setText("Inspection Raised");
                    getPaymentLink();
                }
            }

        if (!TextUtils.isEmpty(logo))
            imgPath = logo;

        try {
            Glide.with(mContext)
                    .load(imgPath)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(imgInsureLogo);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (!TextUtils.isEmpty(fullName))
            edtOName.setText(fullName);

        if (!TextUtils.isEmpty(tpOnly)) {
            if (tpOnly.equals("1")) {
                findViewById(R.id.txtIdv).setVisibility(View.GONE);
                policyType = "Third Party";
                findViewById(R.id.rlPrevious).setVisibility(View.GONE);
            } else {
                if (tpOnly.equals("2"))
                    policyType = "StandAlone OD";
                else policyType = "Comprehensive";

                if (!TextUtils.isEmpty(idv)) {
                    if (idv.equals("0") || idv.equals("1"))
                        findViewById(R.id.txtIdv).setVisibility(View.GONE);
                    else
                        txtIDV.setText(idv);

                } else findViewById(R.id.txtIdv).setVisibility(View.GONE);
            }
        }

        if (!TextUtils.isEmpty(net)) {
            netPremium = net;
            txtNetPremium.setText(AppUtils.round(net));
        }

        if (!TextUtils.isEmpty(gst)) {
            this.gst = gst;
            txtGst.setText(gst);
        }

        if (!TextUtils.isEmpty(registrationNumber))
            edtRegistration.setText(registrationNumber);

        if (!TextUtils.isEmpty(address))
            edtOAddress.setText(address);

        if (!TextUtils.isEmpty(mobile))
            edtOMobile.setText(mobile);

        if (!TextUtils.isEmpty(nmName))
            edtNName.setText(nmName);

        if (!TextUtils.isEmpty(nmRelation))
            edtNRelation.setText(nmRelation);

        if (!TextUtils.isEmpty(nmDob))
            edtNAge.setText(nmDob);

        if (!TextUtils.isEmpty(policyType))
            edtPolicyType.setText(policyType);

        if (!TextUtils.isEmpty(engine))
            edtEngine.setText(engine);

        if (!TextUtils.isEmpty(chassis))
            edtChassis.setText(chassis);

        if (!TextUtils.isEmpty(startDate))
            edtPolicyStartDate.setText(startDate);

        if (!TextUtils.isEmpty(endDate))
            edtPolicyEndDate.setText(endDate);

        if (!TextUtils.isEmpty(vFull))
            edtVehicleModel.setText(vFull);

        if (!TextUtils.isEmpty(finalPremium))
            textTotal.setText(AppUtils.RUPEE + AppUtils.round(finalPremium));

        if (!TextUtils.isEmpty(vehicleType)) {
            switch (vehicleType) {
                case "1":
                    txtVehiclePolicy.setText("TWO WHEELER INSURANCE");
                    break;
                case "2":
                    txtVehiclePolicy.setText("PVT CAR INSURANCE");
                    break;
                case "3":
                    txtVehiclePolicy.setText("PCV INSURANCE");
                    break;
                case "4":
                    txtVehiclePolicy.setText("GCV INSURANCE");
                    break;
                case "5":
                    txtVehiclePolicy.setText("MISD INSURANCE");
                    break;
            }
        }

        this.isNewVehicle = newV;
        if (isNewVehicle != null) {
            if (isNewVehicle.equalsIgnoreCase("new_gadi")) {
                findViewById(R.id.rlRO).setVisibility(View.GONE);
                findViewById(R.id.rlInvoice).setVisibility(View.VISIBLE);
                businessType = "NB";
            }
        } else {
            findViewById(R.id.rlRO).setVisibility(View.VISIBLE);
            findViewById(R.id.rlInvoice).setVisibility(View.GONE);
        }
    }

    @Override
    public void onResponse(Object response) {
        try {

            if (response instanceof CommonResponse) {
                CommonResponse nResponse = (CommonResponse) response;
                if (nResponse.getSuccess().equals("1")) {
                    imgStatus.setVisibility(View.VISIBLE);
//                rlMainDoc.setVisibility(View.GONE);
                    bounceAnim();
                    isDocUpdated = true;
                    isDocUpdatedNew = true;
                    if (flag == 1)
                        proposal();
                    else {
                        uploadRCF.setError(null);
                        uploadRCB.setError(null);
                        progressdialog.dismiss();
                    }
                }
            }

            if (response instanceof Review) {
                Review tempResponse = (Review) response;
                if (tempResponse.getSuccess().equals("1"))
                    initReviewData(tempResponse);
            }

            if (response instanceof Session) {
                boolean isShow;
                Session nResponse = (Session) response;
                if (nResponse.getStatus() != null && nResponse.getStatus().equals("1")) {
                    Intent intent = new Intent(mContext, PaymentActivity.class);
                    url = nResponse.getURL();
                    if (!TextUtils.isEmpty(url)) {
                        mBundle.putString(AppUtils.ACKO_PAYMENT, url);
                        intent.putExtras(mBundle);
                        if (TextUtils.isEmpty(agentId))
                            verifyMobile();
                        else
                            startActivity(intent);
                    } else {
//                    rlMainDoc.setVisibility(View.VISIBLE);
                        txtError.setVisibility(View.VISIBLE);
                        txtError.setText(Html.fromHtml(nResponse.getMsg(), null,
                                new MyTagHandler()));
                        AppUtils.showToast(mContext, "" + nResponse.getMsg());
                    }
                } else {
                    if (nResponse.getMsg() != null) {
                        AppUtils.showToast(mContext, "" + nResponse.getMsg());
                        String strData = String.valueOf(Html.fromHtml(nResponse.getMsg(),
                                null, new MyTagHandler()));
                        isShow = nResponse.getSuccessMsg().equalsIgnoreCase("danger");
                        basicDialog(quotationId, strData, isShow);
                    }
                }
                progressdialog.dismiss();
            }

            if (response instanceof SendOtp) {
                SendOtp nResponse = (SendOtp) response;
                if (nResponse.getStatus().equals(1)) {
                    Intent intent = new Intent(mContext, MobileVerifyActivity.class);
                    mBundle.putString(AppUtils.OTP, nResponse.getOtp().toString());
                    intent.putExtras(mBundle);
                    startActivity(intent);
                    progressdialog.dismiss();
                }
            }

            if (response instanceof RaiseTicket) {
                RaiseTicket nResponse = (RaiseTicket) response;
                if (nResponse.getStatus()) {
                    AppUtils.showToast(mContext, "" + nResponse.getMessage());
                } else AppUtils.showToast(mContext, "" + nResponse.getMessage());

                progressdialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void basicDialog(String title, String msg, boolean isRaise) {
        androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder =
                new androidx.appcompat.app.AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg);

        if (isRaise)
            remark = msg + " - " + company_Name;
            alertDialogBuilder.setPositiveButton(R.string.raise_ticket, (dialog, which) -> {
                reviewPageTicket();
            });
        alertDialogBuilder.setCancelable(false).setNegativeButton(R.string.close,
                (dialog, which) -> {
                });
        try {
            Glide.with(mContext)
                    .load(imgPath)
                    .into(new SimpleTarget<GlideDrawable>(100, 80) {
                        @Override
                        public void onResourceReady(GlideDrawable resource,
                                                    GlideAnimation<? super GlideDrawable>
                                                            glideAnimation) {
                            alertDialogBuilder.setIcon(resource);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            isDocUpdatedNew = false;
            isDocUpdated = false;
            switch (requestCode) {
                case AppUtils.REQUEST_CODE_CAMERA:
                    if (true) {//intent != null) {
                        commonTextView.setText(photoFile.getName());
                        if (commonTextView == uploadInvoice)
                            invoice = photoFile.getAbsolutePath();
                        else if (commonTextView == uploadRCF)
                            rcFront = photoFile.getAbsolutePath();
                        else if (commonTextView == uploadRCB)
                            rcBack = photoFile.getAbsolutePath();
                        else if (commonTextView == uploadPrePolicy)
                            policyPdf = photoFile.getAbsolutePath();
                        else
                            AppUtils.showToast(mContext, "Something went wrong");
                    }
                    break;
                case AppUtils.REQUEST_CODE_GALLERY:
                    if (data != null) {
                        try {
                            Uri selectedImage = data.getData();
                            if (selectedImage != null) {
                                Uri selectedImageUri = data.getData();
                                commonTextView.setText(AppUtils.getFileName(selectedImageUri, mContext));
                                if (commonTextView == uploadInvoice) {
                                    invoice = getRealPathFromURI(selectedImage);
                                } else if (commonTextView == uploadRCF)
                                    rcFront = getRealPathFromURI(selectedImage);
                                else if (commonTextView == uploadRCB)
                                    rcBack = getRealPathFromURI(selectedImage);
                                else if (commonTextView == uploadPrePolicy)
                                    policyPdf = getRealPathFromURI(selectedImage);
                                else
                                    AppUtils.showToast(mContext,"Some thing went wrong...");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case AppUtils.REQUEST_CODE_FILES:
                    Uri data1 = data.getData();
                    if (data1 != null) {
                        try {
                            if (commonTextView == uploadInvoice) {
                                invoice = RealPathUtil.getRealPath(mContext, data1);
                            } else if (commonTextView == uploadRCF)
                                rcFront = RealPathUtil.getRealPath(mContext, data1);
                            else if (commonTextView == uploadRCB)
                                rcBack = RealPathUtil.getRealPath(mContext, data1);
                            else if (commonTextView == uploadPrePolicy)
                                policyPdf = RealPathUtil.getRealPath(mContext, data1);
                            else {
                                AppUtils.showToast(mContext, "This File Not Supported");
                            }
                            if (!TextUtils.isEmpty(policyPdf))
                                commonTextView.setText(AppUtils.getFileName(data1, mContext));
                        } catch (Exception e) {
                            AppUtils.showToast(mContext, "This File Not Supported");
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null,
                null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToLast();
        return cursor.getString(column_index);
    }

    public void onUploadDoc(View view) {
        commonTextView = (TextView) view;
        commonTextView.setError(null);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Upload File!");

        builder.setItems(items, this::onDialogClick);

        builder.show();
    }

    private void onDialogClick(DialogInterface dialog, int item) {
        switch (item) {
            case 0:
                if (isCameraPermitted)
                    dispatchTakePictureIntent();
                else AppUtils.showToast(mContext, "Camera Permission Denied");
                break;
            case 1:
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, AppUtils.REQUEST_CODE_GALLERY);
                break;
            case 2:
                String path = String.valueOf(Environment.getExternalStorageDirectory());
                File file = new File(path);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setDataAndType(Uri.fromFile(file), "*/*");

                try {
                    startActivityForResult(Intent.createChooser(intent, "Select File"),
                            AppUtils.REQUEST_CODE_FILES);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                dialog.dismiss();
                break;
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                try {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.square.pos.provider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(takePictureIntent, AppUtils.REQUEST_CODE_CAMERA);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image;

        if (commonTextView == uploadInvoice) {
            image = File.createTempFile(
                    "INVOICE_",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } else if (commonTextView == uploadRCF) {
            image = File.createTempFile(
                    "RC_Front_",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } else if (commonTextView == uploadRCB) {
            image = File.createTempFile(
                    "RC_Back_",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } else {
            image = File.createTempFile(
                    "Img_",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        }
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        image.renameTo(storageDir);
        return image;
    }

    public void onUploadFile(View view) {
        uploadPrePolicy.setError(null);
        String path = String.valueOf(Environment.getExternalStorageDirectory());
        File file = new File(path);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setDataAndType(Uri.fromFile(file), "*/*");

        try {
            startActivityForResult(Intent.createChooser(intent, "Select File"), AppUtils.REQUEST_CODE_FILES);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public void uploadDocument() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.setTitle("Uploading Documents...");
            progressdialog.show();
            try {
                UserManager.getInstance().uploadPolicyDocuments(mContext, agentId, quotationId,
                        businessType, invoice, policyPdf, rcFront, rcBack);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void verifyMobile() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().verifyMobile(mContext, mobile);
            } catch (Exception e) {
                e.printStackTrace();
                progressdialog.dismiss();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isFileUpdated() {

        if (!TextUtils.isEmpty(isNewVehicle) && isNewVehicle.equals("new_gadi")) {
            if (TextUtils.isEmpty(invoice)) {
                uploadInvoice.setError("Cannot be Empty");
                return false;
            } else return true;
        } else {
            if (!TextUtils.isEmpty(prePolicy)) {
                if (!prePolicy.equals("1"))
                    if ((TextUtils.isEmpty(policyPdf))) {
                        uploadPrePolicy.setError("Cannot be Empty");
                        findViewById(R.id.rlPrevious).setVisibility(View.VISIBLE);
                        Toast.makeText(mContext, "select document", Toast.LENGTH_SHORT).show();
                        return false;
                    }
            }

            if (TextUtils.isEmpty(rcFront)) {
                uploadRCF.setError("Cannot be Empty");
                Toast.makeText(mContext, "select document", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (TextUtils.isEmpty(rcBack)) {
                uploadRCB.setError("Cannot be Empty");
                Toast.makeText(mContext, "select document", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    public void onShareLink(View view) {
        double total = (Double.parseDouble(netPremium) + Double.parseDouble(gst));
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String cName = company_Name;
            if (!TextUtils.isEmpty(cName))
                cName = cName.toUpperCase();
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            url = "Dear Customer Your " + vehicle + " Insurance initiated with " + cName +
                    " with total Premium " + AppUtils.round(String.valueOf(total))
                    + "\n Kindly payment now- " + AppUtils.SHARE_LINK + quotationId;
            shareIntent.putExtra(Intent.EXTRA_TEXT, url);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    public void onTermsClick(View view) {
        startActivity(new Intent(mContext, PrivacyActivity.class));
    }

    public void getPaymentLink() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.setTitle("Redirecting to payment..");
            progressdialog.show();
            try {

                UserManager.getInstance().getMotorPaymentLink(mContext, quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }

    public void reviewPageTicket() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.setTitle("Please Wait..");
            progressdialog.show();
            try {
                String pageType = "Review Page";
                String pageUrl =
                        "https://www.squareinsurance.in/review_pay/index/"
                                + quotationId;

                CrmManager.getInstance().reviewPageTicket(mContext, agentId, userType, pageType,
                        "5", pageUrl, quotationId, remark);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }

    public void onUploadDocuments(View view) {
        flag = 2;
        if (isFileUpdated())
            uploadDocument();
    }

    public void onHidShowDocLayout(View view) {
        if (rlMainDoc.getVisibility() == View.VISIBLE)
            rlMainDoc.setVisibility(View.GONE);
        else rlMainDoc.setVisibility(View.VISIBLE);
    }
}
