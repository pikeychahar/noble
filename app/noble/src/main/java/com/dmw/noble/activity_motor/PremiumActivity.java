package com.dmw.noble.activity_motor;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.activity.MainActivity;
import com.dmw.noble.adaptor.CustomAdapter;
import com.dmw.noble.adaptor.PremiumAdapter;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.ApiManager;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.model.BasicResponse;
import com.dmw.noble.model.NewPremiumQuote;
import com.dmw.noble.model.PremiumObj;
import com.dmw.noble.model.ProposalCompany;
import com.dmw.noble.utils.AppUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

public class PremiumActivity extends AbstractActivity implements onRequestCompleteCallBackListener,
        PremiumAdapter.OnAddonClickListener, PremiumAdapter.OnPremiumBreakClickListener,
        PremiumAdapter.OnPremiumClick {

    Context mContext;
    String quotationId, idv, vehicleType, shareVehicle, newVehicle, ownedBy, ownerChange, claimExp,
            ncbOld, policyExpDate, previousPolicyType, policyType, prePolicy, isFilter, vehicleName,
            isPreviousPolicy, registrationNumber, tpOnly, zeroDept, shareQuoteId,
            currentNcb, maxIdv, minIdv, previousInsurer, previousPolicyNo, tpPolicyExpDate,
            shareEmail, shareMobile, shareType, companyName, policyStartDate, policyEndDate, tenure,
            flag, paCoverValue, imgPath, pcvCompany, userId, userType;
    Float finalPremium, netPremium, gst, od, pa, tp;

    ProgressBar progressBar;
    Bundle mBundle;
    int isFlag = 0;
    int totalExpDay;
    boolean policyExpired, isNcb, isUpdated;

    ArrayList<PremiumObj> premiumList = new ArrayList<>();
    private RecyclerView recyclerQuote;
    private PremiumAdapter policyAdaptor;
    private static final String ZERO = "0";
    static String ZERO1 = "0.0";
    private static final String ONE = "1";
    private static final String TWO = "2";
    private androidx.swiperefreshlayout.widget.SwipeRefreshLayout swipeContainer;

    private ProgressDialog progressDialog;
    ArrayList<String> idvList = new ArrayList<>();
    SharedPreferences preferences;
    Dialog dialog;
    boolean isNewVehicle = false;
    TextView lblQuoteNo;
    ShimmerFrameLayout shimmerFrameLayout;
    ImageView imgOffline;
    static String QUOTE_VEHICLE = "pc";

    //for training POS only
    private boolean isValidUser = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(getColor(R.color.colorPrimaryDark)));

        mContext = this;
        AppUtils.verifyStoragePermission(PremiumActivity.this);
        preferences = getSharedPreferences(String.valueOf(R.string.app_name),
                MODE_PRIVATE);

        userType = preferences.getString(AppUtils.USER_TYPE, "");
        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        if (!TextUtils.isEmpty(userId))
            ApiManager.getInstance().userAuthentication(mContext, userId, userType);

        vehicleType = preferences.getString(AppUtils.VEHICLE_TYPE, "Vehicle");
        shareVehicle = "Car";

        String userType, posStatus;
        userType = preferences.getString(AppUtils.USER_TYPE, "");
        posStatus = preferences.getString(AppUtils.POS_STATUS, "");
        if (!TextUtils.isEmpty(userType))
            if (userType.equalsIgnoreCase("agent"))
                isValidUser = !posStatus.equals("4");

        mBundle = getIntent().getExtras();

        recyclerQuote = findViewById(R.id.premiumQuoteList);
        progressBar = findViewById(R.id.progressBar);
        shimmerFrameLayout = findViewById(R.id.shimmerLayout);
        progressDialog = new ProgressDialog(mContext);

        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //user_interface  started : Do not delete
        swipeContainer = findViewById(R.id.swipeContainer);
        TextView txtQuoteId = findViewById(R.id.txtQuoteId);
        lblQuoteNo = findViewById(R.id.lblQuoteNo);
        imgOffline = findViewById(R.id.imgOffline);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerQuote.setLayoutManager(mLayoutManager);
        recyclerQuote.setNestedScrollingEnabled(false);

        String str = "https://www.squareinsurance.in/uploads/offline-quotes.png";
        Glide.with(mContext)
                .load(str)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(imgOffline);

        policyAdaptor = new PremiumAdapter(mContext, premiumList);

        if (mBundle != null) {

            quotationId = mBundle.getString(AppUtils.QUOTATION_ID);
            newVehicle = mBundle.getString(AppUtils.NEW_VEHICLE);
            ownedBy = mBundle.getString(AppUtils.OWNED_BY);
            ownerChange = mBundle.getString(AppUtils.OWNER_CHANGE);
            claimExp = mBundle.getString(AppUtils.CLAIM_EXP);
            ncbOld = mBundle.getString(AppUtils.PRE_NCB);
            policyExpDate = mBundle.getString(AppUtils.POLICY_EXPIRY_DATE);
            previousPolicyType = mBundle.getString(AppUtils.PRE_POLICY_TYPE);
            totalExpDay = mBundle.getInt(AppUtils.POLICY_EXPIRY_DAYS);
            policyType = mBundle.getString(AppUtils.POLICY_TYPE);
            prePolicy = mBundle.getString(AppUtils.IS_PREVIOUS);
            isFilter = mBundle.getString(AppUtils.IS_FILTER, ZERO);
            vehicleType = mBundle.getString(AppUtils.VEHICLE_TYPE);
            vehicleName = mBundle.getString(AppUtils.VEHICLE_FULL);
            isPreviousPolicy = mBundle.getString(AppUtils.IS_PREVIOUS);
            registrationNumber = mBundle.getString(AppUtils.REGISTRATION_NUMBER);
            pcvCompany = mBundle.getString(AppUtils.PCV_COMPANY);
            policyExpired = mBundle.getBoolean(AppUtils.POLICY_EXPIRED);
            isNcb = mBundle.getBoolean(AppUtils.IS_NCB);

            if (!TextUtils.isEmpty(vehicleType)) {
                if (vehicleType.equalsIgnoreCase(TWO))
                    shareVehicle = "Car";
                else if (vehicleType.equalsIgnoreCase("3"))
                    shareVehicle = "PCV";
                else if (vehicleType.equalsIgnoreCase("4"))
                    shareVehicle = "GCV";
                else
                    shareVehicle = "MISCD";
            } else {
                vehicleType = TWO;
                shareVehicle = "Car";
            }
            if (!TextUtils.isEmpty(newVehicle)) {
                if (newVehicle.equalsIgnoreCase("new_gadi")) {
                    isNewVehicle = true;
                }
            }
            String modelName, make, variantName;

            make = mBundle.getString(AppUtils.MAKE);
            modelName = mBundle.getString(AppUtils.MODEL);
            variantName = mBundle.getString(AppUtils.VARIANT);

            if (!TextUtils.isEmpty(make)) {
                getSupportActionBar().setTitle(make);
                getSupportActionBar().setSubtitle(modelName + " " + variantName);
                vehicleName = make + " " + modelName + " " + variantName;

            } else Objects.requireNonNull(getSupportActionBar()).setTitle("Quotes");
            isUpdated = true;
        }

        if (premiumList.size() > 0)
            premiumList.clear();

        tpOnly = zeroDept = "0";

        if (policyType.equalsIgnoreCase("Comprehensive")) {
            tpOnly = "0";
        } else if (policyType.equalsIgnoreCase("Third Party")) {
            tpOnly = "1";
        } else {
            tpOnly = "2";
        }

        if (!TextUtils.isEmpty(quotationId)) {
            txtQuoteId.setText(quotationId);
            shareQuoteId = AppUtils.encode(quotationId);
            recallInsurer();
        }
        if (!TextUtils.isEmpty(ncbOld) && isNcb) {
            if (ncbOld.equalsIgnoreCase("0"))
                currentNcb = "20%";

            if (ncbOld.equalsIgnoreCase("20"))
                currentNcb = "25%";
            if (ncbOld.equalsIgnoreCase("25"))
                currentNcb = "35%";
            if (ncbOld.equalsIgnoreCase("35"))
                currentNcb = "45%";
            if (ncbOld.equalsIgnoreCase("45"))
                currentNcb = "50%";
            if (ncbOld.equalsIgnoreCase("50"))
                currentNcb = "50%";
        } else currentNcb = "";

        swipeContainer.setOnRefreshListener(() -> {

            if (!TextUtils.isEmpty(quotationId)) {
                recallInsurer();
            }
            swipeContainer.setRefreshing(false);
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    private void recallInsurer() {
        if (premiumList.size() > 0)
            premiumList.clear();

        policyAdaptor.notifyItemRangeRemoved(0, premiumList.size());
        policyAdaptor.notifyDataSetChanged();
        AppUtils.startShimmer(shimmerFrameLayout);

        if (shareVehicle.equalsIgnoreCase("PCV")) {
            if (!TextUtils.isEmpty(pcvCompany))
                getPcvData();
        } else if (shareVehicle.equalsIgnoreCase("GCV")) {
            if (!TextUtils.isEmpty(pcvCompany))
                getGcvData();
        } else if (shareVehicle.equals("MISCD")) {
            getMiscD();
        } else {
            isFlag = 0;
            getUnitedCar();
            getSbiCar();
            getLiberty();
            getUniversal();
            getHdfcCar();
            getTataCar();
            getFutureCar();
            getRelianceCar();
            getShriramCar();
            getBhartiCar();
            getIffcoCar();
            getKotak();
            getNewIndia();
            getRoyalSundaram();
            getCholamandalam();
            getDigitCar();
            getDigitCarNew();
            getBajajCar();
            getOrientalCar();
            getIcici();
            getRaheja();
            getMagma();
        }
    }

    public void getIffcoCar() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, QUOTE_VEHICLE,
                        "iffco", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getKotak() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, QUOTE_VEHICLE,
                        "kotak", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getHdfcCar() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, QUOTE_VEHICLE,
                        "hdfc", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getGcvData() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, "gcv",
                        pcvCompany.toLowerCase(), idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getPcvData() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, "pcv",
                        pcvCompany.toLowerCase(), idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getTataCar() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, QUOTE_VEHICLE,
                        "tata", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getFutureCar() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, QUOTE_VEHICLE,
                        "future", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getShriramCar() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, QUOTE_VEHICLE,
                        "shriram", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getRelianceCar() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, QUOTE_VEHICLE,
                        "reliance", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getOrientalCar() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, QUOTE_VEHICLE,
                        "oriental", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getBhartiCar() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, QUOTE_VEHICLE,
                        "bharti", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getSbiCar() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, QUOTE_VEHICLE,
                        "sbi", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getMiscD() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, "miscd",
                        pcvCompany.toLowerCase(), idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getLiberty() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, QUOTE_VEHICLE,
                        "liberty", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getNewIndia() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, QUOTE_VEHICLE,
                        "newindia", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getRoyalSundaram() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, QUOTE_VEHICLE,
                        "royalSundaram", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getCholamandalam() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, QUOTE_VEHICLE,
                        "Cholamandalam", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getIcici() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, QUOTE_VEHICLE,
                        "icici", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getUniversal() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, QUOTE_VEHICLE,
                        "universal", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getMagma() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, QUOTE_VEHICLE,
                        "magma", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getUnitedCar() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, QUOTE_VEHICLE,
                        "united", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getRaheja() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, QUOTE_VEHICLE,
                        "raheja", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getDigitCar() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, QUOTE_VEHICLE,
                        "digit", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getDigitCarNew() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, QUOTE_VEHICLE,
                        "digitnew", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getBajajCar() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);

            try {
                ApiManager.getInstance().getPremiumCar(mContext, quotationId, QUOTE_VEHICLE,
                        "bajaj", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void shareLink() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                UserManager.getInstance().shareLink(mContext, shareQuoteId, shareEmail, shareMobile,
                        shareVehicle, shareType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    public void proposalCompany() {
        if (isValidUser) {
            if (AppUtils.isOnline(mContext)) {
                progressDialog.setMessage("Wait a moment...");
                progressDialog.show();
                String com = companyName;
                if (companyName.equals("digitNew"))
                    com = "digit";
                try {
                    UserManager.getInstance().proposalCompany(mContext, com, finalPremium,
                            policyStartDate, policyEndDate, idv, tenure, ZERO, netPremium, gst,
                            od, tp, flag, paCoverValue, quotationId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        } else {
            //You are not authorized to purchase policy
            AppUtils.authDialog(mContext);
        }
    }

    public void onShareClick(View view) {

        final Dialog dialog = new Dialog(mContext, android.R.style.Theme_Light);
        dialog.setContentView(R.layout.layout_share);

        final EditText edtEmail = dialog.findViewById(R.id.edtSEmail);
        final EditText edtPhone = dialog.findViewById(R.id.edtSPhone);
        final EditText edtLink = dialog.findViewById(R.id.edtSLink);
        final String link = String.format("%s%s", AppUtils.PRE_LINK, shareQuoteId);
        edtLink.setText(link);

        final ImageView btnLink = dialog.findViewById(R.id.btnCopyLink);
        final ImageView btnEmail = dialog.findViewById(R.id.btnSendEmail);
        final ImageView imgShareBg = dialog.findViewById(R.id.imgShareBg);
        final ImageView btnPhone = dialog.findViewById(R.id.btnSendPhone);

        String img = "https://www.squareinsurance.in/assets/images/share-top-img.png";
        Glide.with(mContext).load(img).into(imgShareBg);

        btnEmail.setOnClickListener(v -> {

            shareEmail = edtEmail.getText().toString().trim();
            if (TextUtils.isEmpty(shareEmail)) {
                AppUtils.showToast(mContext, "Field can not be empty");
                edtEmail.requestFocus();
                return;
            }
            if (!AppUtils.isValidMail(shareEmail)) {
                AppUtils.showToast(mContext, "Invalid Email");
                edtEmail.requestFocus();
                return;
            }

            shareType = "email";
            shareLink();
            dialog.dismiss();

        });
        btnPhone.setOnClickListener(v -> {

            shareMobile = edtPhone.getText().toString().trim();
            if (TextUtils.isEmpty(shareMobile)) {
                AppUtils.showToast(mContext, "Field can not be empty");
                edtPhone.requestFocus();
                return;
            }
            if (!AppUtils.isValidMobile(shareMobile)) {
                AppUtils.showToast(mContext, "Invalid Phone");
                edtPhone.requestFocus();
                return;

            }
            shareType = "mobile";
            shareLink();
            dialog.dismiss();
        });

        btnLink.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context
                    .CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Square", link);
            if (clipboard != null) {
                clipboard.setPrimaryClip(clip);
                Toast.makeText(mContext, "Link Copied", Toast.LENGTH_SHORT).show();
            }
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Square Insurance");

                shareIntent.putExtra(Intent.EXTRA_TEXT, link);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }
        });

        Button close = dialog.findViewById(R.id.dialogButtonClose);
        // if button is clicked, close the custom dialog

        close.setOnClickListener(view1 -> dialog.dismiss());

        dialog.show();
    }

    @Override
    public void onResponse(Object response) {

        try {
            if (response instanceof NewPremiumQuote) {
                NewPremiumQuote dResponse = (NewPremiumQuote) response;

                if (dResponse.getCompany() != null) {

                    if (dResponse.getCompany().equalsIgnoreCase("iffco")) {
                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("iffco")) {
                                premiumList.remove(i);
                                break;
                            }
                        }
                        if (dResponse.getStatus().equalsIgnoreCase("1")) {
                            if (!dResponse.getNet().equalsIgnoreCase("0")) {
                                if (!TextUtils.isEmpty(dResponse.getNet())) {
                                    PremiumObj obj = new PremiumObj();

                                    obj.setLogo(dResponse.getLogo());
                                    obj.setNet(dResponse.getNet());
                                    obj.setIdv(dResponse.getIdv());
                                    obj.setPolicyType(dResponse.getPolicyType());
                                    obj.setTenure(dResponse.getTenure());
                                    obj.setCompany(dResponse.getCompany());

                                    obj.setTotalPremium(dResponse.getTotalPremium());
                                    obj.setEndDate(dResponse.getEndDate());
                                    obj.setStartDate(dResponse.getStartDate());
                                    obj.setNcb(dResponse.getNcb());
                                    obj.setCurrentNcb(dResponse.getCurrentNcb());
                                    obj.setPa(dResponse.getPa());

                                    obj.setTp(dResponse.getTp());
                                    obj.setInspection(dResponse.getInspection());
                                    obj.setBreakingAllowStatus(dResponse.getBreakingAllowStatus());
                                    obj.setOd(dResponse.getOd());
                                    obj.setGst(dResponse.getGst());
                                    obj.setTataFlag(dResponse.getTataFlag());
                                    obj.setZeroDept(dResponse.getZeroDept());
                                    obj.setIdvMin(dResponse.getIdvMin());
                                    obj.setIdvMax(dResponse.getIdvMax());
                                    obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                    int min = dResponse.getIdvMin();
                                    int max = dResponse.getIdvMax();

                                    if (min > 0)
                                        idvList.add(String.valueOf(min));
                                    if (max > 0)
                                        idvList.add(String.valueOf(max));

                                    ArrayList<String> arrayList = new ArrayList<>();

                                    if (!TextUtils.isEmpty(dResponse.getEmergencyCover()))
                                        if (!dResponse.getEmergencyCover().equalsIgnoreCase(ZERO) && !dResponse.getEmergencyCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Emergency Cover: " + dResponse.getEmergencyCover());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getZeroDept()))
                                        if (!dResponse.getZeroDept().equalsIgnoreCase(ZERO) && !dResponse.getZeroDept().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Zero Dep Cover: " + dResponse.getZeroDept());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityEmployee()))
                                        if (!dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Employee: " + dResponse.getLegalLiabilityEmployee());
                                            obj.setLegal_liability_employee(Float.valueOf(dResponse.getLegalLiabilityEmployee()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getImt23()))
                                        if (!dResponse.getImt23().equalsIgnoreCase(ZERO) && !dResponse.getImt23().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 23: " + dResponse.getImt23());

                                    if (!TextUtils.isEmpty(dResponse.getImt34()))
                                        if (!dResponse.getImt34().equalsIgnoreCase(ZERO) && !dResponse.getImt34().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 34: " + dResponse.getImt34());

                                    if (!TextUtils.isEmpty(dResponse.getTppdRestrictedTo()))
                                        if (!dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO) && !dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO1)) {
                                            obj.setTppd_restricted_to(Float.valueOf(dResponse.getTppdRestrictedTo()));
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityPaidDriver()))
                                        if (!dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Paid Driver: " + dResponse.getLegalLiabilityPaidDriver());
                                            obj.setLegal_liability_paid_driver(Float.valueOf(dResponse.getLegalLiabilityPaidDriver()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getConsumables()))
                                        if (!dResponse.getConsumables().equalsIgnoreCase(ZERO) && !dResponse.getConsumables().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Consumables: " + dResponse.getConsumables());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getTyreCover()))
                                        if (!dResponse.getTyreCover().equalsIgnoreCase(ZERO) && !dResponse.getTyreCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Tyre Cover: " + dResponse.getTyreCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getNcbProtection()))
                                        if (!dResponse.getNcbProtection().equalsIgnoreCase(ZERO) && !dResponse.getNcbProtection().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("NCB Protection: " + dResponse.getNcbProtection());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getEngineProtector()))
                                        if (!dResponse.getEngineProtector().equalsIgnoreCase(ZERO) && !dResponse.getEngineProtector().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Engine Cover: " + dResponse.getEngineProtector());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getReturnInvoice()))
                                        if (!dResponse.getReturnInvoice().equalsIgnoreCase(ZERO) && !dResponse.getReturnInvoice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Invoice Cover: " + dResponse.getReturnInvoice());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLossOfKey()))
                                        if (!dResponse.getLossOfKey().equalsIgnoreCase(ZERO) && !dResponse.getLossOfKey().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Loss of key: " + dResponse.getLossOfKey());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getRoadSideAssistance()))
                                        if (!dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO) && !dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Road side Assistance: " + dResponse.getRoadSideAssistance());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getPassengerAssistCover()))
                                        if (!dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO) && !dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Passenger Cover: " + dResponse.getPassengerAssistCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getHospitalCashCover()))
                                        if (!dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO) && !dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Hospital Cover: " + dResponse.getHospitalCashCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getAntiTheftDevice()))
                                        if (!dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO) && !dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Anti Theft Device: " + dResponse.getAntiTheftDevice());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getElectricalAccessory()))
                                        if (!dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Electrical Accessory: " + dResponse.getElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getNonElectricalAccessory()))
                                        if (!dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Non Electrical Accessory: " + dResponse.getNonElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKit()))
                                        if (!dResponse.getFuleKit().equalsIgnoreCase(ZERO) && !dResponse.getFuleKit().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fuel Kit: " + dResponse.getFuleKit());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKitTp()))
                                        if (!dResponse.getFuleKitTp().equalsIgnoreCase(ZERO) && !dResponse.getFuleKitTp().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Fuel Kit Tp: " + dResponse.getFuleKitTp());
                                            obj.setFuelKitTp(Float.valueOf(dResponse.getFuleKitTp()));
                                        }


                                    if (!TextUtils.isEmpty(dResponse.getFiberGlassFuelTank()))
                                        if (!dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO) && !dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fiber Glass Fuel Tank: " + dResponse.getFiberGlassFuelTank());

                                    if (!TextUtils.isEmpty(dResponse.getPacoverforUnnamedPerson()))
                                        if (!dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO) && !dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("PA Cover for Unnamed Person: " + dResponse.getPacoverforUnnamedPerson());
                                            obj.setPacoverfor_unnamed_person(Float.valueOf(dResponse.getPacoverforUnnamedPerson()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getMemberOfAai()))
                                        if (!dResponse.getMemberOfAai().equalsIgnoreCase(ZERO) && !dResponse.getMemberOfAai().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Member Of Aai: " + dResponse.getMemberOfAai());

                                    if (!TextUtils.isEmpty(dResponse.getVoluntaryExcess()))
                                        if (!dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO) && !dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Voluntary Excess: " + dResponse.getVoluntaryExcess());

                                    obj.setArrayCover(arrayList);
                                    premiumList.add(obj);

                                }
                            }
                        }
                        isFlag++;
                    }

                    if (dResponse.getCompany().equalsIgnoreCase("kotak")) {
                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("kotak")) {
                                premiumList.remove(i);
                                break;
                            }
                        }
                        if (dResponse.getStatus().equalsIgnoreCase("1")) {
                            if (!dResponse.getNet().equalsIgnoreCase("0")) {
                                if (!TextUtils.isEmpty(dResponse.getNet())) {
                                    PremiumObj obj = new PremiumObj();

                                    obj.setLogo(dResponse.getLogo());
                                    obj.setNet(dResponse.getNet());
                                    obj.setIdv(dResponse.getIdv());
                                    obj.setPolicyType(dResponse.getPolicyType());
                                    obj.setTenure(dResponse.getTenure());

                                    obj.setCompany(dResponse.getCompany());
                                    obj.setTotalPremium(dResponse.getTotalPremium());
                                    obj.setEndDate(dResponse.getEndDate());
                                    obj.setStartDate(dResponse.getStartDate());
                                    obj.setNcb(dResponse.getNcb());
                                    obj.setCurrentNcb(dResponse.getCurrentNcb());
                                    obj.setPa(dResponse.getPa());
                                    obj.setTp(dResponse.getTp());
                                    obj.setInspection(dResponse.getInspection());
                                    obj.setBreakingAllowStatus(dResponse.getBreakingAllowStatus());
                                    obj.setOd(dResponse.getOd());
                                    obj.setGst(dResponse.getGst());
                                    obj.setTataFlag(dResponse.getTataFlag());
                                    obj.setZeroDept(dResponse.getZeroDept());
                                    obj.setIdvMin(dResponse.getIdvMin());
                                    obj.setIdvMax(dResponse.getIdvMax());
                                    obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                    double min = dResponse.getIdvMin();
                                    double max = dResponse.getIdvMax();
                                    if (min > 0)
                                        idvList.add(String.valueOf(min));
                                    if (max > 0)
                                        idvList.add(String.valueOf(max));

                                    ArrayList<String> arrayList = new ArrayList<>();

                                    if (!TextUtils.isEmpty(dResponse.getEmergencyCover()))
                                        if (!dResponse.getEmergencyCover().equalsIgnoreCase(ZERO) && !dResponse.getEmergencyCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Emergency Cover: " + dResponse.getEmergencyCover());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getZeroDept()))
                                        if (!dResponse.getZeroDept().equalsIgnoreCase(ZERO) && !dResponse.getZeroDept().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Zero Dep Cover: " + dResponse.getZeroDept());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityEmployee()))
                                        if (!dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Employee: " + dResponse.getLegalLiabilityEmployee());
                                            obj.setLegal_liability_employee(Float.valueOf(dResponse.getLegalLiabilityEmployee()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getImt23()))
                                        if (!dResponse.getImt23().equalsIgnoreCase(ZERO) && !dResponse.getImt23().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 23: " + dResponse.getImt23());

                                    if (!TextUtils.isEmpty(dResponse.getImt34()))
                                        if (!dResponse.getImt34().equalsIgnoreCase(ZERO) && !dResponse.getImt34().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 34: " + dResponse.getImt34());

                                    if (!TextUtils.isEmpty(dResponse.getTppdRestrictedTo()))
                                        if (!dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO) && !dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO1)) {
                                            obj.setTppd_restricted_to(Float.valueOf(dResponse.getTppdRestrictedTo()));
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityPaidDriver()))
                                        if (!dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Paid Driver: " + dResponse.getLegalLiabilityPaidDriver());
                                            obj.setLegal_liability_paid_driver(Float.valueOf(dResponse.getLegalLiabilityPaidDriver()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getConsumables()))
                                        if (!dResponse.getConsumables().equalsIgnoreCase(ZERO) && !dResponse.getConsumables().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Consumables: " + dResponse.getConsumables());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getTyreCover()))
                                        if (!dResponse.getTyreCover().equalsIgnoreCase(ZERO) && !dResponse.getTyreCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Tyre Cover: " + dResponse.getTyreCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getNcbProtection()))
                                        if (!dResponse.getNcbProtection().equalsIgnoreCase(ZERO) && !dResponse.getNcbProtection().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("NCB Protection: " + dResponse.getNcbProtection());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getEngineProtector()))
                                        if (!dResponse.getEngineProtector().equalsIgnoreCase(ZERO) && !dResponse.getEngineProtector().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Engine Cover: " + dResponse.getEngineProtector());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getReturnInvoice()))
                                        if (!dResponse.getReturnInvoice().equalsIgnoreCase(ZERO) && !dResponse.getReturnInvoice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Invoice Cover: " + dResponse.getReturnInvoice());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLossOfKey()))
                                        if (!dResponse.getLossOfKey().equalsIgnoreCase(ZERO) && !dResponse.getLossOfKey().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Loss of key: " + dResponse.getLossOfKey());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getRoadSideAssistance()))
                                        if (!dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO) && !dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Road side Assistance: " + dResponse.getRoadSideAssistance());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getPassengerAssistCover()))
                                        if (!dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO) && !dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Passenger Cover: " + dResponse.getPassengerAssistCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getHospitalCashCover()))
                                        if (!dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO) && !dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Hospital Cover: " + dResponse.getHospitalCashCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getAntiTheftDevice()))
                                        if (!dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO) && !dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Anti Theft Device: " + dResponse.getAntiTheftDevice());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getElectricalAccessory()))
                                        if (!dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Electrical Accessory: " + dResponse.getElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getNonElectricalAccessory()))
                                        if (!dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Non Electrical Accessory: " + dResponse.getNonElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKit()))
                                        if (!dResponse.getFuleKit().equalsIgnoreCase(ZERO) && !dResponse.getFuleKit().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fuel Kit: " + dResponse.getFuleKit());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKitTp()))
                                        if (!dResponse.getFuleKitTp().equalsIgnoreCase(ZERO) && !dResponse.getFuleKitTp().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Fuel Kit Tp: " + dResponse.getFuleKitTp());
                                            obj.setFuelKitTp(Float.valueOf(dResponse.getFuleKitTp()));
                                        }


                                    if (!TextUtils.isEmpty(dResponse.getFiberGlassFuelTank()))
                                        if (!dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO) && !dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fiber Glass Fuel Tank: " + dResponse.getFiberGlassFuelTank());

                                    if (!TextUtils.isEmpty(dResponse.getPacoverforUnnamedPerson()))
                                        if (!dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO) && !dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("PA Cover for Unnamed Person: " + dResponse.getPacoverforUnnamedPerson());
                                            obj.setPacoverfor_unnamed_person(Float.valueOf(dResponse.getPacoverforUnnamedPerson()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getMemberOfAai()))
                                        if (!dResponse.getMemberOfAai().equalsIgnoreCase(ZERO) && !dResponse.getMemberOfAai().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Member Of Aai: " + dResponse.getMemberOfAai());

                                    if (!TextUtils.isEmpty(dResponse.getVoluntaryExcess()))
                                        if (!dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO) && !dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Voluntary Excess: " + dResponse.getVoluntaryExcess());

                                    obj.setArrayCover(arrayList);
                                    premiumList.add(obj);
                                }
                            }
                        }
                        isFlag++;
                    }

                    if (dResponse.getCompany().equalsIgnoreCase("sbi")) {
                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("sbi")) {
                                premiumList.remove(i);
                                break;
                            }
                        }
                        if (dResponse.getStatus().equalsIgnoreCase("1")) {
                            if (!dResponse.getNet().equalsIgnoreCase("0")) {
                                if (!TextUtils.isEmpty(dResponse.getNet())) {
                                    PremiumObj obj = new PremiumObj();

                                    obj.setLogo(dResponse.getLogo());
                                    obj.setNet(dResponse.getNet());
                                    obj.setIdv(dResponse.getIdv());
                                    obj.setPolicyType(dResponse.getPolicyType());
                                    obj.setTenure(dResponse.getTenure());

                                    obj.setCompany(dResponse.getCompany());
                                    obj.setTotalPremium(dResponse.getTotalPremium());
                                    obj.setEndDate(dResponse.getEndDate());
                                    obj.setStartDate(dResponse.getStartDate());
                                    obj.setNcb(dResponse.getNcb());
                                    obj.setCurrentNcb(dResponse.getCurrentNcb());
                                    obj.setPa(dResponse.getPa());
                                    obj.setTp(dResponse.getTp());
                                    obj.setInspection(dResponse.getInspection());
                                    obj.setBreakingAllowStatus(dResponse.getBreakingAllowStatus());
                                    obj.setOd(dResponse.getOd());
                                    obj.setGst(dResponse.getGst());
                                    obj.setTataFlag(dResponse.getTataFlag());
                                    obj.setZeroDept(dResponse.getZeroDept());
                                    obj.setIdvMin(dResponse.getIdvMin());
                                    obj.setIdvMax(dResponse.getIdvMax());
                                    obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                    int min = dResponse.getIdvMin();
                                    int max = dResponse.getIdvMax();
                                    if (min > 0)
                                        idvList.add(String.valueOf(min));
                                    if (max > 0)
                                        idvList.add(String.valueOf(max));

                                    ArrayList<String> arrayList = new ArrayList<>();

                                    if (!TextUtils.isEmpty(dResponse.getEmergencyCover()))
                                        if (!dResponse.getEmergencyCover().equalsIgnoreCase(ZERO) && !dResponse.getEmergencyCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Emergency Cover: " + dResponse.getEmergencyCover());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getZeroDept()))
                                        if (!dResponse.getZeroDept().equalsIgnoreCase(ZERO) && !dResponse.getZeroDept().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Zero Dep Cover: " + dResponse.getZeroDept());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityEmployee()))
                                        if (!dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Employee: " + dResponse.getLegalLiabilityEmployee());
                                            obj.setLegal_liability_employee(Float.valueOf(dResponse.getLegalLiabilityEmployee()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getImt23()))
                                        if (!dResponse.getImt23().equalsIgnoreCase(ZERO) && !dResponse.getImt23().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 23: " + dResponse.getImt23());

                                    if (!TextUtils.isEmpty(dResponse.getImt34()))
                                        if (!dResponse.getImt34().equalsIgnoreCase(ZERO) && !dResponse.getImt34().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 34: " + dResponse.getImt34());

                                    if (!TextUtils.isEmpty(dResponse.getTppdRestrictedTo()))
                                        if (!dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO) && !dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO1)) {
                                            obj.setTppd_restricted_to(Float.valueOf(dResponse.getTppdRestrictedTo()));
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityPaidDriver()))
                                        if (!dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Paid Driver: " + dResponse.getLegalLiabilityPaidDriver());
                                            obj.setLegal_liability_paid_driver(Float.valueOf(dResponse.getLegalLiabilityPaidDriver()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getConsumables()))
                                        if (!dResponse.getConsumables().equalsIgnoreCase(ZERO) && !dResponse.getConsumables().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Consumables: " + dResponse.getConsumables());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getTyreCover()))
                                        if (!dResponse.getTyreCover().equalsIgnoreCase(ZERO) && !dResponse.getTyreCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Tyre Cover: " + dResponse.getTyreCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getNcbProtection()))
                                        if (!dResponse.getNcbProtection().equalsIgnoreCase(ZERO) && !dResponse.getNcbProtection().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("NCB Protection: " + dResponse.getNcbProtection());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getEngineProtector()))
                                        if (!dResponse.getEngineProtector().equalsIgnoreCase(ZERO) && !dResponse.getEngineProtector().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Engine Cover: " + dResponse.getEngineProtector());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getReturnInvoice()))
                                        if (!dResponse.getReturnInvoice().equalsIgnoreCase(ZERO) && !dResponse.getReturnInvoice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Invoice Cover: " + dResponse.getReturnInvoice());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLossOfKey()))
                                        if (!dResponse.getLossOfKey().equalsIgnoreCase(ZERO) && !dResponse.getLossOfKey().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Loss of key: " + dResponse.getLossOfKey());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getRoadSideAssistance()))
                                        if (!dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO) && !dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Road side Assistance: " + dResponse.getRoadSideAssistance());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getPassengerAssistCover()))
                                        if (!dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO) && !dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Passenger Cover: " + dResponse.getPassengerAssistCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getHospitalCashCover()))
                                        if (!dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO) && !dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Hospital Cover: " + dResponse.getHospitalCashCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getAntiTheftDevice()))
                                        if (!dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO) && !dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Anti Theft Device: " + dResponse.getAntiTheftDevice());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getElectricalAccessory()))
                                        if (!dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Electrical Accessory: " + dResponse.getElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getNonElectricalAccessory()))
                                        if (!dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Non Electrical Accessory: " + dResponse.getNonElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKit()))
                                        if (!dResponse.getFuleKit().equalsIgnoreCase(ZERO) && !dResponse.getFuleKit().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fuel Kit: " + dResponse.getFuleKit());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKitTp()))
                                        if (!dResponse.getFuleKitTp().equalsIgnoreCase(ZERO) && !dResponse.getFuleKitTp().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Fuel Kit Tp: " + dResponse.getFuleKitTp());
                                            obj.setFuelKitTp(Float.valueOf(dResponse.getFuleKitTp()));
                                        }


                                    if (!TextUtils.isEmpty(dResponse.getFiberGlassFuelTank()))
                                        if (!dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO) && !dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fiber Glass Fuel Tank: " + dResponse.getFiberGlassFuelTank());

                                    if (!TextUtils.isEmpty(dResponse.getPacoverforUnnamedPerson()))
                                        if (!dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO) && !dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("PA Cover for Unnamed Person: " + dResponse.getPacoverforUnnamedPerson());
                                            obj.setPacoverfor_unnamed_person(Float.valueOf(dResponse.getPacoverforUnnamedPerson()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getMemberOfAai()))
                                        if (!dResponse.getMemberOfAai().equalsIgnoreCase(ZERO) && !dResponse.getMemberOfAai().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Member Of Aai: " + dResponse.getMemberOfAai());

                                    if (!TextUtils.isEmpty(dResponse.getVoluntaryExcess()))
                                        if (!dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO) && !dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Voluntary Excess: " + dResponse.getVoluntaryExcess());

                                    obj.setArrayCover(arrayList);
                                    premiumList.add(obj);
                                }
                            }
                        }
                        isFlag++;
                    }

                    if (dResponse.getCompany().equalsIgnoreCase("liberty")) {
                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("liberty")) {
                                premiumList.remove(i);
                                break;
                            }
                        }
                        if (dResponse.getStatus().equalsIgnoreCase("1")) {
                            if (!dResponse.getNet().equalsIgnoreCase("0")) {
                                if (!TextUtils.isEmpty(dResponse.getNet())) {
                                    PremiumObj obj = new PremiumObj();

                                    obj.setLogo(dResponse.getLogo());
                                    obj.setNet(dResponse.getNet());
                                    obj.setIdv(dResponse.getIdv());
                                    obj.setPolicyType(dResponse.getPolicyType());
                                    obj.setTenure(dResponse.getTenure());

                                    obj.setCompany(dResponse.getCompany());
                                    obj.setTotalPremium(dResponse.getTotalPremium());
                                    obj.setEndDate(dResponse.getEndDate());
                                    obj.setStartDate(dResponse.getStartDate());
                                    obj.setNcb(dResponse.getNcb());
                                    obj.setCurrentNcb(dResponse.getCurrentNcb());
                                    obj.setPa(dResponse.getPa());
                                    obj.setTp(dResponse.getTp());
                                    obj.setInspection(dResponse.getInspection());
                                    obj.setBreakingAllowStatus(dResponse.getBreakingAllowStatus());
                                    obj.setOd(dResponse.getOd());
                                    obj.setGst(dResponse.getGst());
                                    obj.setTataFlag(dResponse.getTataFlag());
                                    obj.setZeroDept(dResponse.getZeroDept());
                                    obj.setIdvMin(dResponse.getIdvMin());
                                    obj.setIdvMax(dResponse.getIdvMax());
                                    obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                    int min = dResponse.getIdvMin();
                                    int max = dResponse.getIdvMax();
                                    if (min > 0)
                                        idvList.add(String.valueOf(min));
                                    if (max > 0)
                                        idvList.add(String.valueOf(max));

                                    ArrayList<String> arrayList = new ArrayList<>();

                                    if (!TextUtils.isEmpty(dResponse.getEmergencyCover()))
                                        if (!dResponse.getEmergencyCover().equalsIgnoreCase(ZERO) && !dResponse.getEmergencyCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Emergency Cover: " + dResponse.getEmergencyCover());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getZeroDept()))
                                        if (!dResponse.getZeroDept().equalsIgnoreCase(ZERO) && !dResponse.getZeroDept().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Zero Dep Cover: " + dResponse.getZeroDept());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityEmployee()))
                                        if (!dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Employee: " + dResponse.getLegalLiabilityEmployee());
                                            obj.setLegal_liability_employee(Float.valueOf(dResponse.getLegalLiabilityEmployee()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getImt23()))
                                        if (!dResponse.getImt23().equalsIgnoreCase(ZERO) && !dResponse.getImt23().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 23: " + dResponse.getImt23());

                                    if (!TextUtils.isEmpty(dResponse.getImt34()))
                                        if (!dResponse.getImt34().equalsIgnoreCase(ZERO) && !dResponse.getImt34().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 34: " + dResponse.getImt34());

                                    if (!TextUtils.isEmpty(dResponse.getTppdRestrictedTo()))
                                        if (!dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO) && !dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO1)) {
                                            obj.setTppd_restricted_to(Float.valueOf(dResponse.getTppdRestrictedTo()));
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityPaidDriver()))
                                        if (!dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Paid Driver: " + dResponse.getLegalLiabilityPaidDriver());
                                            obj.setLegal_liability_paid_driver(Float.valueOf(dResponse.getLegalLiabilityPaidDriver()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getConsumables()))
                                        if (!dResponse.getConsumables().equalsIgnoreCase(ZERO) && !dResponse.getConsumables().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Consumables: " + dResponse.getConsumables());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getTyreCover()))
                                        if (!dResponse.getTyreCover().equalsIgnoreCase(ZERO) && !dResponse.getTyreCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Tyre Cover: " + dResponse.getTyreCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getNcbProtection()))
                                        if (!dResponse.getNcbProtection().equalsIgnoreCase(ZERO) && !dResponse.getNcbProtection().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("NCB Protection: " + dResponse.getNcbProtection());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getEngineProtector()))
                                        if (!dResponse.getEngineProtector().equalsIgnoreCase(ZERO) && !dResponse.getEngineProtector().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Engine Cover: " + dResponse.getEngineProtector());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getReturnInvoice()))
                                        if (!dResponse.getReturnInvoice().equalsIgnoreCase(ZERO) && !dResponse.getReturnInvoice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Invoice Cover: " + dResponse.getReturnInvoice());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLossOfKey()))
                                        if (!dResponse.getLossOfKey().equalsIgnoreCase(ZERO) && !dResponse.getLossOfKey().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Loss of key: " + dResponse.getLossOfKey());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getRoadSideAssistance()))
                                        if (!dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO) && !dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Road side Assistance: " + dResponse.getRoadSideAssistance());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getPassengerAssistCover()))
                                        if (!dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO) && !dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Passenger Cover: " + dResponse.getPassengerAssistCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getHospitalCashCover()))
                                        if (!dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO) && !dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Hospital Cover: " + dResponse.getHospitalCashCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getAntiTheftDevice()))
                                        if (!dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO) && !dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Anti Theft Device: " + dResponse.getAntiTheftDevice());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getElectricalAccessory()))
                                        if (!dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Electrical Accessory: " + dResponse.getElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getNonElectricalAccessory()))
                                        if (!dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Non Electrical Accessory: " + dResponse.getNonElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKit()))
                                        if (!dResponse.getFuleKit().equalsIgnoreCase(ZERO) && !dResponse.getFuleKit().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fuel Kit: " + dResponse.getFuleKit());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKitTp()))
                                        if (!dResponse.getFuleKitTp().equalsIgnoreCase(ZERO) && !dResponse.getFuleKitTp().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Fuel Kit Tp: " + dResponse.getFuleKitTp());
                                            obj.setFuelKitTp(Float.valueOf(dResponse.getFuleKitTp()));
                                        }


                                    if (!TextUtils.isEmpty(dResponse.getFiberGlassFuelTank()))
                                        if (!dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO) && !dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fiber Glass Fuel Tank: " + dResponse.getFiberGlassFuelTank());

                                    if (!TextUtils.isEmpty(dResponse.getPacoverforUnnamedPerson()))
                                        if (!dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO) && !dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("PA Cover for Unnamed Person: " + dResponse.getPacoverforUnnamedPerson());
                                            obj.setPacoverfor_unnamed_person(Float.valueOf(dResponse.getPacoverforUnnamedPerson()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getMemberOfAai()))
                                        if (!dResponse.getMemberOfAai().equalsIgnoreCase(ZERO) && !dResponse.getMemberOfAai().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Member Of Aai: " + dResponse.getMemberOfAai());

                                    if (!TextUtils.isEmpty(dResponse.getVoluntaryExcess()))
                                        if (!dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO) && !dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Voluntary Excess: " + dResponse.getVoluntaryExcess());

                                    obj.setArrayCover(arrayList);
                                    premiumList.add(obj);
                                }
                            }
                        }
                        isFlag++;
                    }

                    if (dResponse.getCompany().equalsIgnoreCase("newindia")) {
                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("newindia")) {
                                premiumList.remove(i);
                                break;
                            }
                        }
                        if (dResponse.getStatus().equalsIgnoreCase("1")) {
                            if (!dResponse.getNet().equalsIgnoreCase("0")) {
                                if (!TextUtils.isEmpty(dResponse.getNet())) {
                                    PremiumObj obj = new PremiumObj();

                                    obj.setLogo(dResponse.getLogo());
                                    obj.setNet(dResponse.getNet());
                                    obj.setIdv(dResponse.getIdv());
                                    obj.setPolicyType(dResponse.getPolicyType());
                                    obj.setTenure(dResponse.getTenure());

                                    obj.setCompany(dResponse.getCompany());
                                    obj.setTotalPremium(dResponse.getTotalPremium());
                                    obj.setEndDate(dResponse.getEndDate());
                                    obj.setStartDate(dResponse.getStartDate());
                                    obj.setNcb(dResponse.getNcb());
                                    obj.setCurrentNcb(dResponse.getCurrentNcb());
                                    obj.setPa(dResponse.getPa());
                                    obj.setTp(dResponse.getTp());
                                    obj.setInspection(dResponse.getInspection());
                                    obj.setBreakingAllowStatus(dResponse.getBreakingAllowStatus());
                                    obj.setOd(dResponse.getOd());
                                    obj.setGst(dResponse.getGst());
                                    obj.setTataFlag(dResponse.getTataFlag());
                                    obj.setZeroDept(dResponse.getZeroDept());
                                    obj.setIdvMin(dResponse.getIdvMin());
                                    obj.setIdvMax(dResponse.getIdvMax());
                                    obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                    int min = dResponse.getIdvMin();
                                    int max = dResponse.getIdvMax();
                                    if (min > 0)
                                        idvList.add(String.valueOf(min));
                                    if (max > 0)
                                        idvList.add(String.valueOf(max));

                                    ArrayList<String> arrayList = new ArrayList<>();

                                    if (!TextUtils.isEmpty(dResponse.getEmergencyCover()))
                                        if (!dResponse.getEmergencyCover().equalsIgnoreCase(ZERO) && !dResponse.getEmergencyCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Emergency Cover: " + dResponse.getEmergencyCover());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getZeroDept()))
                                        if (!dResponse.getZeroDept().equalsIgnoreCase(ZERO) && !dResponse.getZeroDept().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Zero Dep Cover: " + dResponse.getZeroDept());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityEmployee()))
                                        if (!dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Employee: " + dResponse.getLegalLiabilityEmployee());
                                            obj.setLegal_liability_employee(Float.valueOf(dResponse.getLegalLiabilityEmployee()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getImt23()))
                                        if (!dResponse.getImt23().equalsIgnoreCase(ZERO) && !dResponse.getImt23().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 23: " + dResponse.getImt23());

                                    if (!TextUtils.isEmpty(dResponse.getImt34()))
                                        if (!dResponse.getImt34().equalsIgnoreCase(ZERO) && !dResponse.getImt34().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 34: " + dResponse.getImt34());

                                    if (!TextUtils.isEmpty(dResponse.getTppdRestrictedTo()))
                                        if (!dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO) && !dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO1)) {
                                            obj.setTppd_restricted_to(Float.valueOf(dResponse.getTppdRestrictedTo()));
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityPaidDriver()))
                                        if (!dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Paid Driver: " + dResponse.getLegalLiabilityPaidDriver());
                                            obj.setLegal_liability_paid_driver(Float.valueOf(dResponse.getLegalLiabilityPaidDriver()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getConsumables()))
                                        if (!dResponse.getConsumables().equalsIgnoreCase(ZERO) && !dResponse.getConsumables().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Consumables: " + dResponse.getConsumables());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getTyreCover()))
                                        if (!dResponse.getTyreCover().equalsIgnoreCase(ZERO) && !dResponse.getTyreCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Tyre Cover: " + dResponse.getTyreCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getNcbProtection()))
                                        if (!dResponse.getNcbProtection().equalsIgnoreCase(ZERO) && !dResponse.getNcbProtection().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("NCB Protection: " + dResponse.getNcbProtection());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getEngineProtector()))
                                        if (!dResponse.getEngineProtector().equalsIgnoreCase(ZERO) && !dResponse.getEngineProtector().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Engine Cover: " + dResponse.getEngineProtector());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getReturnInvoice()))
                                        if (!dResponse.getReturnInvoice().equalsIgnoreCase(ZERO) && !dResponse.getReturnInvoice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Invoice Cover: " + dResponse.getReturnInvoice());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLossOfKey()))
                                        if (!dResponse.getLossOfKey().equalsIgnoreCase(ZERO) && !dResponse.getLossOfKey().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Loss of key: " + dResponse.getLossOfKey());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getRoadSideAssistance()))
                                        if (!dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO) && !dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Road side Assistance: " + dResponse.getRoadSideAssistance());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getPassengerAssistCover()))
                                        if (!dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO) && !dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Passenger Cover: " + dResponse.getPassengerAssistCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getHospitalCashCover()))
                                        if (!dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO) && !dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Hospital Cover: " + dResponse.getHospitalCashCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getAntiTheftDevice()))
                                        if (!dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO) && !dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Anti Theft Device: " + dResponse.getAntiTheftDevice());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getElectricalAccessory()))
                                        if (!dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Electrical Accessory: " + dResponse.getElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getNonElectricalAccessory()))
                                        if (!dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Non Electrical Accessory: " + dResponse.getNonElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKit()))
                                        if (!dResponse.getFuleKit().equalsIgnoreCase(ZERO) && !dResponse.getFuleKit().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fuel Kit: " + dResponse.getFuleKit());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKitTp()))
                                        if (!dResponse.getFuleKitTp().equalsIgnoreCase(ZERO) && !dResponse.getFuleKitTp().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Fuel Kit Tp: " + dResponse.getFuleKitTp());
                                            obj.setFuelKitTp(Float.valueOf(dResponse.getFuleKitTp()));
                                        }


                                    if (!TextUtils.isEmpty(dResponse.getFiberGlassFuelTank()))
                                        if (!dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO) && !dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fiber Glass Fuel Tank: " + dResponse.getFiberGlassFuelTank());

                                    if (!TextUtils.isEmpty(dResponse.getPacoverforUnnamedPerson()))
                                        if (!dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO) && !dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("PA Cover for Unnamed Person: " + dResponse.getPacoverforUnnamedPerson());
                                            obj.setPacoverfor_unnamed_person(Float.valueOf(dResponse.getPacoverforUnnamedPerson()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getMemberOfAai()))
                                        if (!dResponse.getMemberOfAai().equalsIgnoreCase(ZERO) && !dResponse.getMemberOfAai().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Member Of Aai: " + dResponse.getMemberOfAai());

                                    if (!TextUtils.isEmpty(dResponse.getVoluntaryExcess()))
                                        if (!dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO) && !dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Voluntary Excess: " + dResponse.getVoluntaryExcess());

                                    obj.setArrayCover(arrayList);
                                    premiumList.add(obj);
                                }
                            }
                        }
                        isFlag++;
                    }

                    if (dResponse.getCompany().equalsIgnoreCase("bajaj")) {
                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("bajaj")) {
                                premiumList.remove(i);
                                break;
                            }
                        }
                        if (dResponse.getStatus().equalsIgnoreCase("1")) {
                            if (!dResponse.getNet().equalsIgnoreCase("0")) {
                                if (!TextUtils.isEmpty(dResponse.getNet())) {
                                    PremiumObj obj = new PremiumObj();

                                    obj.setLogo(dResponse.getLogo());
                                    obj.setNet(dResponse.getNet());
                                    obj.setIdv(dResponse.getIdv());
                                    obj.setPolicyType(dResponse.getPolicyType());
                                    obj.setTenure(dResponse.getTenure());

                                    obj.setCompany(dResponse.getCompany());
                                    obj.setTotalPremium(dResponse.getTotalPremium());
                                    obj.setEndDate(dResponse.getEndDate());
                                    obj.setStartDate(dResponse.getStartDate());
                                    obj.setNcb(dResponse.getNcb());
                                    obj.setCurrentNcb(dResponse.getCurrentNcb());
                                    obj.setPa(dResponse.getPa());
                                    obj.setTp(dResponse.getTp());
                                    obj.setInspection(dResponse.getInspection());
                                    obj.setBreakingAllowStatus(dResponse.getBreakingAllowStatus());
                                    obj.setOd(dResponse.getOd());
                                    obj.setGst(dResponse.getGst());
                                    obj.setTataFlag(dResponse.getTataFlag());
                                    obj.setZeroDept(dResponse.getZeroDept());
                                    obj.setIdvMin(dResponse.getIdvMin());
                                    obj.setIdvMax(dResponse.getIdvMax());
                                    obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                    int min = dResponse.getIdvMin();
                                    int max = dResponse.getIdvMax();

                                    if (min > 0)
                                        idvList.add(String.valueOf(min));
                                    if (max > 0)
                                        idvList.add(String.valueOf(max));

                                    ArrayList<String> arrayList = new ArrayList<>();

                                    if (!TextUtils.isEmpty(dResponse.getEmergencyCover()))
                                        if (!dResponse.getEmergencyCover().equalsIgnoreCase(ZERO) && !dResponse.getEmergencyCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Emergency Cover: " + dResponse.getEmergencyCover());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getZeroDept()))
                                        if (!dResponse.getZeroDept().equalsIgnoreCase(ZERO) && !dResponse.getZeroDept().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Zero Dep Cover: " + dResponse.getZeroDept());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityEmployee()))
                                        if (!dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Employee: " + dResponse.getLegalLiabilityEmployee());
                                            obj.setLegal_liability_employee(Float.valueOf(dResponse.getLegalLiabilityEmployee()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getImt23()))
                                        if (!dResponse.getImt23().equalsIgnoreCase(ZERO) && !dResponse.getImt23().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 23: " + dResponse.getImt23());

                                    if (!TextUtils.isEmpty(dResponse.getImt34()))
                                        if (!dResponse.getImt34().equalsIgnoreCase(ZERO) && !dResponse.getImt34().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 34: " + dResponse.getImt34());

                                    if (!TextUtils.isEmpty(dResponse.getTppdRestrictedTo()))
                                        if (!dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO) && !dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO1)) {
                                            obj.setTppd_restricted_to(Float.valueOf(dResponse.getTppdRestrictedTo()));
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityPaidDriver()))
                                        if (!dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Paid Driver: " + dResponse.getLegalLiabilityPaidDriver());
                                            obj.setLegal_liability_paid_driver(Float.valueOf(dResponse.getLegalLiabilityPaidDriver()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getConsumables()))
                                        if (!dResponse.getConsumables().equalsIgnoreCase(ZERO) && !dResponse.getConsumables().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Consumables: " + dResponse.getConsumables());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getTyreCover()))
                                        if (!dResponse.getTyreCover().equalsIgnoreCase(ZERO) && !dResponse.getTyreCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Tyre Cover: " + dResponse.getTyreCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getNcbProtection()))
                                        if (!dResponse.getNcbProtection().equalsIgnoreCase(ZERO) && !dResponse.getNcbProtection().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("NCB Protection: " + dResponse.getNcbProtection());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getEngineProtector()))
                                        if (!dResponse.getEngineProtector().equalsIgnoreCase(ZERO) && !dResponse.getEngineProtector().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Engine Cover: " + dResponse.getEngineProtector());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getReturnInvoice()))
                                        if (!dResponse.getReturnInvoice().equalsIgnoreCase(ZERO) && !dResponse.getReturnInvoice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Invoice Cover: " + dResponse.getReturnInvoice());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLossOfKey()))
                                        if (!dResponse.getLossOfKey().equalsIgnoreCase(ZERO) && !dResponse.getLossOfKey().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Loss of key: " + dResponse.getLossOfKey());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getRoadSideAssistance()))
                                        if (!dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO) && !dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Road side Assistance: " + dResponse.getRoadSideAssistance());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getPassengerAssistCover()))
                                        if (!dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO) && !dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Passenger Cover: " + dResponse.getPassengerAssistCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getHospitalCashCover()))
                                        if (!dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO) && !dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Hospital Cover: " + dResponse.getHospitalCashCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getAntiTheftDevice()))
                                        if (!dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO) && !dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Anti Theft Device: " + dResponse.getAntiTheftDevice());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getElectricalAccessory()))
                                        if (!dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Electrical Accessory: " + dResponse.getElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getNonElectricalAccessory()))
                                        if (!dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Non Electrical Accessory: " + dResponse.getNonElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKit()))
                                        if (!dResponse.getFuleKit().equalsIgnoreCase(ZERO) && !dResponse.getFuleKit().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fuel Kit: " + dResponse.getFuleKit());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKitTp()))
                                        if (!dResponse.getFuleKitTp().equalsIgnoreCase(ZERO) && !dResponse.getFuleKitTp().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Fuel Kit Tp: " + dResponse.getFuleKitTp());
                                            obj.setFuelKitTp(Float.valueOf(dResponse.getFuleKitTp()));
                                        }


                                    if (!TextUtils.isEmpty(dResponse.getFiberGlassFuelTank()))
                                        if (!dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO) && !dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fiber Glass Fuel Tank: " + dResponse.getFiberGlassFuelTank());

                                    if (!TextUtils.isEmpty(dResponse.getPacoverforUnnamedPerson()))
                                        if (!dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO) && !dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("PA Cover for Unnamed Person: " + dResponse.getPacoverforUnnamedPerson());
                                            obj.setPacoverfor_unnamed_person(Float.valueOf(dResponse.getPacoverforUnnamedPerson()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getMemberOfAai()))
                                        if (!dResponse.getMemberOfAai().equalsIgnoreCase(ZERO) && !dResponse.getMemberOfAai().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Member Of Aai: " + dResponse.getMemberOfAai());

                                    if (!TextUtils.isEmpty(dResponse.getVoluntaryExcess()))
                                        if (!dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO) && !dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Voluntary Excess: " + dResponse.getVoluntaryExcess());

                                    obj.setArrayCover(arrayList);
                                    premiumList.add(obj);

                                }
                            }
                        }
                        isFlag++;
                    }

                    if (dResponse.getCompany().equalsIgnoreCase("reliance")) {
                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("reliance")) {
                                premiumList.remove(i);
                                break;
                            }
                        }
                        if (dResponse.getStatus().equalsIgnoreCase("1")) {
                            if (!dResponse.getNet().equalsIgnoreCase("0")) {
                                if (!TextUtils.isEmpty(dResponse.getNet())) {
                                    PremiumObj obj = new PremiumObj();

                                    obj.setLogo(dResponse.getLogo());
                                    obj.setNet(dResponse.getNet());
                                    obj.setIdv(dResponse.getIdv());
                                    obj.setPolicyType(dResponse.getPolicyType());
                                    obj.setTenure(dResponse.getTenure());

                                    obj.setCompany(dResponse.getCompany());
                                    obj.setTotalPremium(dResponse.getTotalPremium());
                                    obj.setEndDate(dResponse.getEndDate());
                                    obj.setStartDate(dResponse.getStartDate());
                                    obj.setNcb(dResponse.getNcb());
                                    obj.setCurrentNcb(dResponse.getCurrentNcb());
                                    obj.setPa(dResponse.getPa());
                                    obj.setTp(dResponse.getTp());
                                    obj.setInspection(dResponse.getInspection());
                                    obj.setBreakingAllowStatus(dResponse.getBreakingAllowStatus());
                                    obj.setOd(dResponse.getOd());
                                    obj.setGst(dResponse.getGst());
                                    obj.setTataFlag(dResponse.getTataFlag());
                                    obj.setZeroDept(dResponse.getZeroDept());
                                    obj.setIdvMin(dResponse.getIdvMin());
                                    obj.setIdvMax(dResponse.getIdvMax());
                                    obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                    int min = dResponse.getIdvMin();
                                    int max = dResponse.getIdvMax();

                                    if (min > 0)
                                        idvList.add(String.valueOf(min));
                                    if (max > 0)
                                        idvList.add(String.valueOf(max));
                                    ArrayList<String> arrayList = new ArrayList<>();

                                    if (!TextUtils.isEmpty(dResponse.getEmergencyCover()))
                                        if (!dResponse.getEmergencyCover().equalsIgnoreCase(ZERO)
                                                && !dResponse.getEmergencyCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Emergency Cover: " + dResponse.getEmergencyCover());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getZeroDept()))
                                        if (!dResponse.getZeroDept().equalsIgnoreCase(ZERO) && !dResponse.getZeroDept().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Zero Dep Cover: " + dResponse.getZeroDept());
                                        }

                                    try {
                                        if (!TextUtils.isEmpty(dResponse.getLegalLiabilityEmployee()))
                                            if (!dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO1)) {
                                                arrayList.add("Legal Liability Employee: " + dResponse.getLegalLiabilityEmployee());
                                                obj.setLegal_liability_employee(Float.valueOf(dResponse.getLegalLiabilityEmployee()));
                                            }

                                        if (!TextUtils.isEmpty(dResponse.getLegalLiabilityPaidDriver()))
                                            if (!dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO1)) {
                                                arrayList.add("Legal Liability Paid Driver: " + dResponse.getLegalLiabilityPaidDriver());
                                                obj.setLegal_liability_paid_driver(Float.valueOf(dResponse.getLegalLiabilityPaidDriver()));
                                            }
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                    if (!TextUtils.isEmpty(dResponse.getConsumables()))
                                        if (!dResponse.getConsumables().equalsIgnoreCase(ZERO) && !dResponse.getConsumables().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Consumables: " + dResponse.getConsumables());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getTyreCover()))
                                        if (!dResponse.getTyreCover().equalsIgnoreCase(ZERO) && !dResponse.getTyreCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Tyre Cover: " + dResponse.getTyreCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getNcbProtection()))
                                        if (!dResponse.getNcbProtection().equalsIgnoreCase(ZERO) && !dResponse.getNcbProtection().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("NCB Protection: " + dResponse.getNcbProtection());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getEngineProtector()))
                                        if (!dResponse.getEngineProtector().equalsIgnoreCase(ZERO) && !dResponse.getEngineProtector().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Engine Cover: " + dResponse.getEngineProtector());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getReturnInvoice()))
                                        if (!dResponse.getReturnInvoice().equalsIgnoreCase(ZERO) && !dResponse.getReturnInvoice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Invoice Cover: " + dResponse.getReturnInvoice());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLossOfKey()))
                                        if (!dResponse.getLossOfKey().equalsIgnoreCase(ZERO) && !dResponse.getLossOfKey().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Loss of key: " + dResponse.getLossOfKey());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getRoadSideAssistance()))
                                        if (!dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO) && !dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Road side Assistance: " + dResponse.getRoadSideAssistance());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getPassengerAssistCover()))
                                        if (!dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO) && !dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Passenger Cover: " + dResponse.getPassengerAssistCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getHospitalCashCover()))
                                        if (!dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO) && !dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Hospital Cover: " + dResponse.getHospitalCashCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getAntiTheftDevice()))
                                        if (!dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO) && !dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Anti Theft Device: " + dResponse.getAntiTheftDevice());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getElectricalAccessory()))
                                        if (!dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Electrical Accessory: " + dResponse.getElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getNonElectricalAccessory()))
                                        if (!dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Non Electrical Accessory: " + dResponse.getNonElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKit()))
                                        if (!dResponse.getFuleKit().equalsIgnoreCase(ZERO) && !dResponse.getFuleKit().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fuel Kit: " + dResponse.getFuleKit());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKitTp()))
                                        if (!dResponse.getFuleKitTp().equalsIgnoreCase(ZERO) && !dResponse.getFuleKitTp().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Fuel Kit Tp: " + dResponse.getFuleKitTp());
                                        }


                                    if (!TextUtils.isEmpty(dResponse.getFiberGlassFuelTank()))
                                        if (!dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO) && !dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fiber Glass Fuel Tank: " + dResponse.getFiberGlassFuelTank());

                                    if (!TextUtils.isEmpty(dResponse.getPacoverforUnnamedPerson()))
                                        if (!dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO) && !dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("PA Cover for Unnamed Person: " + dResponse.getPacoverforUnnamedPerson());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getMemberOfAai()))
                                        if (!dResponse.getMemberOfAai().equalsIgnoreCase(ZERO) && !dResponse.getMemberOfAai().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Member Of Aai: " + dResponse.getMemberOfAai());

                                    if (!TextUtils.isEmpty(dResponse.getVoluntaryExcess()))
                                        if (!dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO) && !dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Voluntary Excess: " + dResponse.getVoluntaryExcess());

                                    obj.setArrayCover(arrayList);
                                    premiumList.add(obj);

                                }
                            }
                        }
                        isFlag++;
                    }

                    if (dResponse.getCompany().equalsIgnoreCase("bharti")) {
                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("bharti")) {
                                premiumList.remove(i);
                                break;
                            }
                        }
                        if (dResponse.getStatus().equalsIgnoreCase("1")) {
                            if (!dResponse.getNet().equalsIgnoreCase("0")) {
                                if (!TextUtils.isEmpty(dResponse.getNet())) {
                                    PremiumObj obj = new PremiumObj();

                                    obj.setLogo(dResponse.getLogo());
                                    obj.setNet(dResponse.getNet());
                                    obj.setIdv(dResponse.getIdv());
                                    obj.setPolicyType(dResponse.getPolicyType());
                                    obj.setTenure(dResponse.getTenure());

                                    obj.setCompany(dResponse.getCompany());
                                    obj.setTotalPremium(dResponse.getTotalPremium());
                                    obj.setEndDate(dResponse.getEndDate());
                                    obj.setStartDate(dResponse.getStartDate());
                                    obj.setNcb(dResponse.getNcb());
                                    obj.setCurrentNcb(dResponse.getCurrentNcb());
                                    obj.setPa(dResponse.getPa());
                                    obj.setTp(dResponse.getTp());
                                    obj.setInspection(dResponse.getInspection());
                                    obj.setBreakingAllowStatus(dResponse.getBreakingAllowStatus());
                                    obj.setOd(dResponse.getOd());
                                    obj.setGst(dResponse.getGst());
                                    obj.setTataFlag(dResponse.getTataFlag());
                                    obj.setZeroDept(dResponse.getZeroDept());
                                    obj.setIdvMin(dResponse.getIdvMin());
                                    obj.setIdvMax(dResponse.getIdvMax());
                                    obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                    int min = dResponse.getIdvMin();
                                    int max = dResponse.getIdvMax();

                                    if (min > 0)
                                        idvList.add(String.valueOf(min));
                                    if (max > 0)
                                        idvList.add(String.valueOf(max));

                                    ArrayList<String> arrayList = new ArrayList<>();

                                    if (!TextUtils.isEmpty(dResponse.getEmergencyCover()))
                                        if (!dResponse.getEmergencyCover().equalsIgnoreCase(ZERO) && !dResponse.getEmergencyCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Emergency Cover: " + dResponse.getEmergencyCover());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getZeroDept()))
                                        if (!dResponse.getZeroDept().equalsIgnoreCase(ZERO) && !dResponse.getZeroDept().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Zero Dep Cover: " + dResponse.getZeroDept());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityEmployee()))
                                        if (!dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Employee: " + dResponse.getLegalLiabilityEmployee());
                                            obj.setLegal_liability_employee(Float.valueOf(dResponse.getLegalLiabilityEmployee()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getImt23()))
                                        if (!dResponse.getImt23().equalsIgnoreCase(ZERO) && !dResponse.getImt23().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 23: " + dResponse.getImt23());

                                    if (!TextUtils.isEmpty(dResponse.getImt34()))
                                        if (!dResponse.getImt34().equalsIgnoreCase(ZERO) && !dResponse.getImt34().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 34: " + dResponse.getImt34());

                                    if (!TextUtils.isEmpty(dResponse.getTppdRestrictedTo()))
                                        if (!dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO) && !dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO1)) {
                                            obj.setTppd_restricted_to(Float.valueOf(dResponse.getTppdRestrictedTo()));
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityPaidDriver()))
                                        if (!dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Paid Driver: " + dResponse.getLegalLiabilityPaidDriver());
                                            obj.setLegal_liability_paid_driver(Float.valueOf(dResponse.getLegalLiabilityPaidDriver()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getConsumables()))
                                        if (!dResponse.getConsumables().equalsIgnoreCase(ZERO) && !dResponse.getConsumables().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Consumables: " + dResponse.getConsumables());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getTyreCover()))
                                        if (!dResponse.getTyreCover().equalsIgnoreCase(ZERO) && !dResponse.getTyreCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Tyre Cover: " + dResponse.getTyreCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getNcbProtection()))
                                        if (!dResponse.getNcbProtection().equalsIgnoreCase(ZERO) && !dResponse.getNcbProtection().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("NCB Protection: " + dResponse.getNcbProtection());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getEngineProtector()))
                                        if (!dResponse.getEngineProtector().equalsIgnoreCase(ZERO) && !dResponse.getEngineProtector().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Engine Cover: " + dResponse.getEngineProtector());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getReturnInvoice()))
                                        if (!dResponse.getReturnInvoice().equalsIgnoreCase(ZERO) && !dResponse.getReturnInvoice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Invoice Cover: " + dResponse.getReturnInvoice());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLossOfKey()))
                                        if (!dResponse.getLossOfKey().equalsIgnoreCase(ZERO) && !dResponse.getLossOfKey().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Loss of key: " + dResponse.getLossOfKey());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getRoadSideAssistance()))
                                        if (!dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO) && !dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Road side Assistance: " + dResponse.getRoadSideAssistance());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getPassengerAssistCover()))
                                        if (!dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO) && !dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Passenger Cover: " + dResponse.getPassengerAssistCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getHospitalCashCover()))
                                        if (!dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO) && !dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Hospital Cover: " + dResponse.getHospitalCashCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getAntiTheftDevice()))
                                        if (!dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO) && !dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Anti Theft Device: " + dResponse.getAntiTheftDevice());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getElectricalAccessory()))
                                        if (!dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Electrical Accessory: " + dResponse.getElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getNonElectricalAccessory()))
                                        if (!dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Non Electrical Accessory: " + dResponse.getNonElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKit()))
                                        if (!dResponse.getFuleKit().equalsIgnoreCase(ZERO) && !dResponse.getFuleKit().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fuel Kit: " + dResponse.getFuleKit());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKitTp()))
                                        if (!dResponse.getFuleKitTp().equalsIgnoreCase(ZERO) && !dResponse.getFuleKitTp().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Fuel Kit Tp: " + dResponse.getFuleKitTp());
                                            obj.setFuelKitTp(Float.valueOf(dResponse.getFuleKitTp()));
                                        }


                                    if (!TextUtils.isEmpty(dResponse.getFiberGlassFuelTank()))
                                        if (!dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO) && !dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fiber Glass Fuel Tank: " + dResponse.getFiberGlassFuelTank());

                                    if (!TextUtils.isEmpty(dResponse.getPacoverforUnnamedPerson()))
                                        if (!dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO) && !dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("PA Cover for Unnamed Person: " + dResponse.getPacoverforUnnamedPerson());
                                            obj.setPacoverfor_unnamed_person(Float.valueOf(dResponse.getPacoverforUnnamedPerson()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getMemberOfAai()))
                                        if (!dResponse.getMemberOfAai().equalsIgnoreCase(ZERO) && !dResponse.getMemberOfAai().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Member Of Aai: " + dResponse.getMemberOfAai());

                                    if (!TextUtils.isEmpty(dResponse.getVoluntaryExcess()))
                                        if (!dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO) && !dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Voluntary Excess: " + dResponse.getVoluntaryExcess());

                                    obj.setArrayCover(arrayList);
                                    premiumList.add(obj);

                                }
                            }
                        }
                        isFlag++;
                    }

                    if (dResponse.getCompany().equalsIgnoreCase("oriental")) {
                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("oriental")) {
                                premiumList.remove(i);
                                break;
                            }
                        }
                        if (dResponse.getStatus().equalsIgnoreCase("1")) {
                            if (!dResponse.getNet().equalsIgnoreCase("0")) {
                                if (!TextUtils.isEmpty(dResponse.getNet())) {
                                    PremiumObj obj = new PremiumObj();

                                    obj.setLogo(dResponse.getLogo());
                                    obj.setNet(dResponse.getNet());
                                    obj.setIdv(dResponse.getIdv());
                                    obj.setPolicyType(dResponse.getPolicyType());
                                    obj.setTenure(dResponse.getTenure());

                                    obj.setCompany(dResponse.getCompany());
                                    obj.setTotalPremium(dResponse.getTotalPremium());
                                    obj.setEndDate(dResponse.getEndDate());
                                    obj.setStartDate(dResponse.getStartDate());
                                    obj.setNcb(dResponse.getNcb());
                                    obj.setCurrentNcb(dResponse.getCurrentNcb());
                                    obj.setPa(dResponse.getPa());
                                    obj.setTp(dResponse.getTp());
                                    obj.setInspection(dResponse.getInspection());
                                    obj.setBreakingAllowStatus(dResponse.getBreakingAllowStatus());
                                    obj.setOd(dResponse.getOd());
                                    obj.setGst(dResponse.getGst());
                                    obj.setTataFlag(dResponse.getTataFlag());
                                    obj.setZeroDept(dResponse.getZeroDept());
                                    obj.setIdvMin(dResponse.getIdvMin());
                                    obj.setIdvMax(dResponse.getIdvMax());
                                    obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                    int min = dResponse.getIdvMin();
                                    int max = dResponse.getIdvMax();

                                    if (min > 0)
                                        idvList.add(String.valueOf(min));
                                    if (max > 0)
                                        idvList.add(String.valueOf(max));

                                    ArrayList<String> arrayList = new ArrayList<>();

                                    if (!TextUtils.isEmpty(dResponse.getEmergencyCover()))
                                        if (!dResponse.getEmergencyCover().equalsIgnoreCase(ZERO) && !dResponse.getEmergencyCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Emergency Cover: " + dResponse.getEmergencyCover());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getZeroDept()))
                                        if (!dResponse.getZeroDept().equalsIgnoreCase(ZERO) && !dResponse.getZeroDept().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Zero Dep Cover: " + dResponse.getZeroDept());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityEmployee()))
                                        if (!dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Employee: " + dResponse.getLegalLiabilityEmployee());
                                            obj.setLegal_liability_employee(Float.valueOf(dResponse.getLegalLiabilityEmployee()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getImt23()))
                                        if (!dResponse.getImt23().equalsIgnoreCase(ZERO) && !dResponse.getImt23().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 23: " + dResponse.getImt23());

                                    if (!TextUtils.isEmpty(dResponse.getImt34()))
                                        if (!dResponse.getImt34().equalsIgnoreCase(ZERO) && !dResponse.getImt34().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 34: " + dResponse.getImt34());

                                    if (!TextUtils.isEmpty(dResponse.getTppdRestrictedTo()))
                                        if (!dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO) && !dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO1)) {
                                            obj.setTppd_restricted_to(Float.valueOf(dResponse.getTppdRestrictedTo()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityEmployee()))
                                        if (!dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Employee: " + dResponse.getLegalLiabilityEmployee());
                                            obj.setLegal_liability_employee(Float.valueOf(dResponse.getLegalLiabilityEmployee()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getImt23()))
                                        if (!dResponse.getImt23().equalsIgnoreCase(ZERO) && !dResponse.getImt23().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 23: " + dResponse.getImt23());

                                    if (!TextUtils.isEmpty(dResponse.getImt34()))
                                        if (!dResponse.getImt34().equalsIgnoreCase(ZERO) && !dResponse.getImt34().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 34: " + dResponse.getImt34());

                                    if (!TextUtils.isEmpty(dResponse.getTppdRestrictedTo()))
                                        if (!dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO) && !dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO1)) {
                                            obj.setTppd_restricted_to(Float.valueOf(dResponse.getTppdRestrictedTo()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityPaidDriver()))
                                        if (!dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Paid Driver: " + dResponse.getLegalLiabilityPaidDriver());
                                            obj.setLegal_liability_paid_driver(Float.valueOf(dResponse.getLegalLiabilityPaidDriver()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getConsumables()))
                                        if (!dResponse.getConsumables().equalsIgnoreCase(ZERO) && !dResponse.getConsumables().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Consumables: " + dResponse.getConsumables());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getTyreCover()))
                                        if (!dResponse.getTyreCover().equalsIgnoreCase(ZERO) && !dResponse.getTyreCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Tyre Cover: " + dResponse.getTyreCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getNcbProtection()))
                                        if (!dResponse.getNcbProtection().equalsIgnoreCase(ZERO) && !dResponse.getNcbProtection().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("NCB Protection: " + dResponse.getNcbProtection());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getEngineProtector()))
                                        if (!dResponse.getEngineProtector().equalsIgnoreCase(ZERO) && !dResponse.getEngineProtector().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Engine Cover: " + dResponse.getEngineProtector());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getReturnInvoice()))
                                        if (!dResponse.getReturnInvoice().equalsIgnoreCase(ZERO) && !dResponse.getReturnInvoice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Invoice Cover: " + dResponse.getReturnInvoice());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLossOfKey()))
                                        if (!dResponse.getLossOfKey().equalsIgnoreCase(ZERO) && !dResponse.getLossOfKey().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Loss of key: " + dResponse.getLossOfKey());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getRoadSideAssistance()))
                                        if (!dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO) && !dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Road side Assistance: " + dResponse.getRoadSideAssistance());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getPassengerAssistCover()))
                                        if (!dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO) && !dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Passenger Cover: " + dResponse.getPassengerAssistCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getHospitalCashCover()))
                                        if (!dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO) && !dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Hospital Cover: " + dResponse.getHospitalCashCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getAntiTheftDevice()))
                                        if (!dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO) && !dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Anti Theft Device: " + dResponse.getAntiTheftDevice());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getElectricalAccessory()))
                                        if (!dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Electrical Accessory: " + dResponse.getElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getNonElectricalAccessory()))
                                        if (!dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Non Electrical Accessory: " + dResponse.getNonElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKit()))
                                        if (!dResponse.getFuleKit().equalsIgnoreCase(ZERO) && !dResponse.getFuleKit().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fuel Kit: " + dResponse.getFuleKit());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKitTp()))
                                        if (!dResponse.getFuleKitTp().equalsIgnoreCase(ZERO) && !dResponse.getFuleKitTp().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Fuel Kit Tp: " + dResponse.getFuleKitTp());
                                            obj.setFuelKitTp(Float.valueOf(dResponse.getFuleKitTp()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getFiberGlassFuelTank()))
                                        if (!dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO) && !dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fiber Glass Fuel Tank: " + dResponse.getFiberGlassFuelTank());

                                    if (!TextUtils.isEmpty(dResponse.getPacoverforUnnamedPerson()))
                                        if (!dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO) && !dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("PA Cover for Unnamed Person: " + dResponse.getPacoverforUnnamedPerson());
                                            obj.setPacoverfor_unnamed_person(Float.valueOf(dResponse.getPacoverforUnnamedPerson()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getMemberOfAai()))
                                        if (!dResponse.getMemberOfAai().equalsIgnoreCase(ZERO) && !dResponse.getMemberOfAai().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Member Of Aai: " + dResponse.getMemberOfAai());

                                    if (!TextUtils.isEmpty(dResponse.getVoluntaryExcess()))
                                        if (!dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO) && !dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Voluntary Excess: " + dResponse.getVoluntaryExcess());

                                    obj.setArrayCover(arrayList);
                                    premiumList.add(obj);

                                }
                            }
                        }
                        isFlag++;
                    }

                    if (dResponse.getCompany().equalsIgnoreCase("tata")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("tata")) {
                                premiumList.remove(i);
                                break;
                            }
                        }


                        if (dResponse.getStatus().equalsIgnoreCase("1")) {
                            if (!dResponse.getNet().equalsIgnoreCase("0")) {
                                if (!TextUtils.isEmpty(dResponse.getNet())) {
                                    PremiumObj obj = new PremiumObj();

                                    obj.setLogo(dResponse.getLogo());
                                    obj.setNet(dResponse.getNet());
                                    obj.setIdv(dResponse.getIdv());
                                    obj.setPolicyType(dResponse.getPolicyType());
                                    obj.setTenure(dResponse.getTenure());

                                    obj.setCompany(dResponse.getCompany());
                                    obj.setTotalPremium(dResponse.getTotalPremium());
                                    obj.setEndDate(dResponse.getEndDate());
                                    obj.setStartDate(dResponse.getStartDate());
                                    obj.setNcb(dResponse.getNcb());
                                    obj.setCurrentNcb(dResponse.getCurrentNcb());
                                    obj.setPa(dResponse.getPa());
                                    obj.setTp(dResponse.getTp());
                                    obj.setInspection(dResponse.getInspection());
                                    obj.setBreakingAllowStatus(dResponse.getBreakingAllowStatus());
                                    obj.setOd(dResponse.getOd());
                                    obj.setGst(dResponse.getGst());
                                    obj.setTataFlag(dResponse.getTataFlag());
                                    obj.setZeroDept(dResponse.getZeroDept());
                                    obj.setIdvMin(dResponse.getIdvMin());
                                    obj.setIdvMax(dResponse.getIdvMax());
                                    obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                    int min = dResponse.getIdvMin();
                                    int max = dResponse.getIdvMax();

                                    if (min > 0)
                                        idvList.add(String.valueOf(min));
                                    if (max > 0)
                                        idvList.add(String.valueOf(max));
                                    ArrayList<String> arrayList = new ArrayList<>();

                                    if (!TextUtils.isEmpty(dResponse.getEmergencyCover()))
                                        if (!dResponse.getEmergencyCover().equalsIgnoreCase(ZERO) && !dResponse.getEmergencyCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Emergency Cover: " + dResponse.getEmergencyCover());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getZeroDept()))
                                        if (!dResponse.getZeroDept().equalsIgnoreCase(ZERO) && !dResponse.getZeroDept().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Zero Dep Cover: " + dResponse.getZeroDept());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityEmployee()))
                                        if (!dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Employee: " + dResponse.getLegalLiabilityEmployee());
                                            obj.setLegal_liability_employee(Float.valueOf(dResponse.getLegalLiabilityEmployee()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getImt23()))
                                        if (!dResponse.getImt23().equalsIgnoreCase(ZERO) && !dResponse.getImt23().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 23: " + dResponse.getImt23());

                                    if (!TextUtils.isEmpty(dResponse.getImt34()))
                                        if (!dResponse.getImt34().equalsIgnoreCase(ZERO) && !dResponse.getImt34().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 34: " + dResponse.getImt34());

                                    if (!TextUtils.isEmpty(dResponse.getTppdRestrictedTo()))
                                        if (!dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO) && !dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO1)) {
                                            obj.setTppd_restricted_to(Float.valueOf(dResponse.getTppdRestrictedTo()));
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityPaidDriver()))
                                        if (!dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Paid Driver: " + dResponse.getLegalLiabilityPaidDriver());
                                            obj.setLegal_liability_paid_driver(Float.valueOf(dResponse.getLegalLiabilityPaidDriver()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getConsumables()))
                                        if (!dResponse.getConsumables().equalsIgnoreCase(ZERO) && !dResponse.getConsumables().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Consumables: " + dResponse.getConsumables());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getTyreCover()))
                                        if (!dResponse.getTyreCover().equalsIgnoreCase(ZERO) && !dResponse.getTyreCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Tyre Cover: " + dResponse.getTyreCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getNcbProtection()))
                                        if (!dResponse.getNcbProtection().equalsIgnoreCase(ZERO) && !dResponse.getNcbProtection().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("NCB Protection: " + dResponse.getNcbProtection());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getEngineProtector()))
                                        if (!dResponse.getEngineProtector().equalsIgnoreCase(ZERO) && !dResponse.getEngineProtector().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Engine Cover: " + dResponse.getEngineProtector());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getReturnInvoice()))
                                        if (!dResponse.getReturnInvoice().equalsIgnoreCase(ZERO) && !dResponse.getReturnInvoice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Invoice Cover: " + dResponse.getReturnInvoice());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLossOfKey()))
                                        if (!dResponse.getLossOfKey().equalsIgnoreCase(ZERO) && !dResponse.getLossOfKey().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Loss of key: " + dResponse.getLossOfKey());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getRoadSideAssistance()))
                                        if (!dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO) && !dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Road side Assistance: " + dResponse.getRoadSideAssistance());
                                            obj.setRoad_side_assistance(dResponse.getRoadSideAssistance());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getPassengerAssistCover()))
                                        if (!dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO) && !dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Passenger Cover: " + dResponse.getPassengerAssistCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getHospitalCashCover()))
                                        if (!dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO) && !dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Hospital Cover: " + dResponse.getHospitalCashCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getAntiTheftDevice()))
                                        if (!dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO) && !dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Anti Theft Device: " + dResponse.getAntiTheftDevice());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getElectricalAccessory()))
                                        if (!dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Electrical Accessory: " + dResponse.getElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getNonElectricalAccessory()))
                                        if (!dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Non Electrical Accessory: " + dResponse.getNonElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKit()))
                                        if (!dResponse.getFuleKit().equalsIgnoreCase(ZERO) && !dResponse.getFuleKit().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fuel Kit: " + dResponse.getFuleKit());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKitTp()))
                                        if (!dResponse.getFuleKitTp().equalsIgnoreCase(ZERO) && !dResponse.getFuleKitTp().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Fuel Kit Tp: " + dResponse.getFuleKitTp());
                                            obj.setFuelKitTp(Float.valueOf(dResponse.getFuleKitTp()));
                                        }


                                    if (!TextUtils.isEmpty(dResponse.getPacoverforUnnamedPerson()))
                                        if (!dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO) && !dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("PA Cover for Unnamed Person: " + dResponse.getPacoverforUnnamedPerson());
                                            obj.setPacoverfor_unnamed_person(Float.valueOf(dResponse.getPacoverforUnnamedPerson()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getMemberOfAai()))
                                        if (!dResponse.getMemberOfAai().equalsIgnoreCase(ZERO) && !dResponse.getMemberOfAai().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Member Of Aai: " + dResponse.getMemberOfAai());

                                    if (!TextUtils.isEmpty(dResponse.getVoluntaryExcess()))
                                        if (!dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO) && !dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Voluntary Excess: " + dResponse.getVoluntaryExcess());

                                    obj.setArrayCover(arrayList);
                                    premiumList.add(obj);

                                }
                            }
                        }
                        isFlag++;
                    }

                    if (dResponse.getCompany().equalsIgnoreCase("hdfc")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("hdfc")) {
                                premiumList.remove(i);
                                break;
                            }
                        }

                        if (dResponse.getStatus().equalsIgnoreCase("1")) {
                            if (!dResponse.getNet().equalsIgnoreCase("0")) {
                                if (!TextUtils.isEmpty(dResponse.getNet())) {
                                    PremiumObj obj = new PremiumObj();

                                    obj.setLogo(dResponse.getLogo());
                                    obj.setNet(dResponse.getNet());
                                    obj.setIdv(dResponse.getIdv());
                                    obj.setPolicyType(dResponse.getPolicyType());
                                    obj.setTenure(dResponse.getTenure());

                                    obj.setCompany(dResponse.getCompany());
                                    obj.setTotalPremium(dResponse.getTotalPremium());
                                    obj.setEndDate(dResponse.getEndDate());
                                    obj.setStartDate(dResponse.getStartDate());
                                    obj.setNcb(dResponse.getNcb());
                                    obj.setCurrentNcb(dResponse.getCurrentNcb());
                                    obj.setPa(dResponse.getPa());
                                    obj.setTp(dResponse.getTp());
                                    obj.setInspection(dResponse.getInspection());
                                    obj.setBreakingAllowStatus(dResponse.getBreakingAllowStatus());
                                    obj.setOd(dResponse.getOd());
                                    obj.setGst(dResponse.getGst());
                                    obj.setTataFlag(dResponse.getTataFlag());
                                    obj.setZeroDept(dResponse.getZeroDept());
                                    obj.setIdvMin(dResponse.getIdvMin());
                                    obj.setIdvMax(dResponse.getIdvMax());
                                    obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                    int min = dResponse.getIdvMin();
                                    int max = dResponse.getIdvMax();

                                    if (min > 0)
                                        idvList.add(String.valueOf(min));
                                    if (max > 0)
                                        idvList.add(String.valueOf(max));
                                    ArrayList<String> arrayList = new ArrayList<>();

                                    if (!TextUtils.isEmpty(dResponse.getEmergencyCover()))
                                        if (!dResponse.getEmergencyCover().equalsIgnoreCase(ZERO) && !dResponse.getEmergencyCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Emergency Cover: " + dResponse.getEmergencyCover());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getZeroDept()))
                                        if (!dResponse.getZeroDept().equalsIgnoreCase(ZERO) && !dResponse.getZeroDept().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Zero Dep Cover: " + dResponse.getZeroDept());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityEmployee()))
                                        if (!dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Employee: " + dResponse.getLegalLiabilityEmployee());
                                            obj.setLegal_liability_employee(Float.valueOf(dResponse.getLegalLiabilityEmployee()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getImt23()))
                                        if (!dResponse.getImt23().equalsIgnoreCase(ZERO) && !dResponse.getImt23().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 23: " + dResponse.getImt23());

                                    if (!TextUtils.isEmpty(dResponse.getImt34()))
                                        if (!dResponse.getImt34().equalsIgnoreCase(ZERO) && !dResponse.getImt34().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 34: " + dResponse.getImt34());

                                    if (!TextUtils.isEmpty(dResponse.getTppdRestrictedTo()))
                                        if (!dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO) && !dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO1)) {
                                            obj.setTppd_restricted_to(Float.valueOf(dResponse.getTppdRestrictedTo()));
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityPaidDriver()))
                                        if (!dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Paid Driver: " + dResponse.getLegalLiabilityPaidDriver());
                                            obj.setLegal_liability_paid_driver(Float.valueOf(dResponse.getLegalLiabilityPaidDriver()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getConsumables()))
                                        if (!dResponse.getConsumables().equalsIgnoreCase(ZERO) && !dResponse.getConsumables().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Consumables: " + dResponse.getConsumables());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getTyreCover()))
                                        if (!dResponse.getTyreCover().equalsIgnoreCase(ZERO) && !dResponse.getTyreCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Tyre Cover: " + dResponse.getTyreCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getNcbProtection()))
                                        if (!dResponse.getNcbProtection().equalsIgnoreCase(ZERO) && !dResponse.getNcbProtection().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("NCB Protection: " + dResponse.getNcbProtection());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getEngineProtector()))
                                        if (!dResponse.getEngineProtector().equalsIgnoreCase(ZERO) && !dResponse.getEngineProtector().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Engine Cover: " + dResponse.getEngineProtector());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getReturnInvoice()))
                                        if (!dResponse.getReturnInvoice().equalsIgnoreCase(ZERO) && !dResponse.getReturnInvoice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Invoice Cover: " + dResponse.getReturnInvoice());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLossOfKey()))
                                        if (!dResponse.getLossOfKey().equalsIgnoreCase(ZERO) && !dResponse.getLossOfKey().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Loss of key: " + dResponse.getLossOfKey());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getRoadSideAssistance()))
                                        if (!dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO) && !dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Road side Assistance: " + dResponse.getRoadSideAssistance());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getPassengerAssistCover()))
                                        if (!dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO) && !dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Passenger Cover: " + dResponse.getPassengerAssistCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getHospitalCashCover()))
                                        if (!dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO) && !dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Hospital Cover: " + dResponse.getHospitalCashCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getAntiTheftDevice()))
                                        if (!dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO) && !dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Anti Theft Device: " + dResponse.getAntiTheftDevice());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getElectricalAccessory()))
                                        if (!dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Electrical Accessory: " + dResponse.getElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getNonElectricalAccessory()))
                                        if (!dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Non Electrical Accessory: " + dResponse.getNonElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKit()))
                                        if (!dResponse.getFuleKit().equalsIgnoreCase(ZERO) && !dResponse.getFuleKit().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fuel Kit: " + dResponse.getFuleKit());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKitTp()))
                                        if (!dResponse.getFuleKitTp().equalsIgnoreCase(ZERO) && !dResponse.getFuleKitTp().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Fuel Kit Tp: " + dResponse.getFuleKitTp());
                                            obj.setFuelKitTp(Float.valueOf(dResponse.getFuleKitTp()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getFiberGlassFuelTank()))
                                        if (!dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO) && !dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fiber Glass Fuel Tank: " + dResponse.getFiberGlassFuelTank());

                                    if (!TextUtils.isEmpty(dResponse.getPacoverforUnnamedPerson()))
                                        if (!dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO) && !dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("PA Cover for Unnamed Person: " + dResponse.getPacoverforUnnamedPerson());
                                            obj.setPacoverfor_unnamed_person(Float.valueOf(dResponse.getPacoverforUnnamedPerson()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getMemberOfAai()))
                                        if (!dResponse.getMemberOfAai().equalsIgnoreCase(ZERO) && !dResponse.getMemberOfAai().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Member Of Aai: " + dResponse.getMemberOfAai());

                                    if (!TextUtils.isEmpty(dResponse.getVoluntaryExcess()))
                                        if (!dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO) && !dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Voluntary Excess: " + dResponse.getVoluntaryExcess());

                                    obj.setArrayCover(arrayList);
                                    premiumList.add(obj);

                                }
                            }
                        }
                        isFlag++;

                    }

                    if (dResponse.getCompany().equalsIgnoreCase("magma")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("magma")) {
                                premiumList.remove(i);
                                break;
                            }
                        }

                        if (dResponse.getStatus().equalsIgnoreCase("1")) {
                            if (!dResponse.getNet().equalsIgnoreCase("0")) {
                                if (!TextUtils.isEmpty(dResponse.getNet())) {
                                    PremiumObj obj = new PremiumObj();

                                    obj.setLogo(dResponse.getLogo());
                                    obj.setNet(dResponse.getNet());
                                    obj.setIdv(dResponse.getIdv());
                                    obj.setPolicyType(dResponse.getPolicyType());
                                    obj.setTenure(dResponse.getTenure());

                                    obj.setCompany(dResponse.getCompany());
                                    obj.setTotalPremium(dResponse.getTotalPremium());
                                    obj.setEndDate(dResponse.getEndDate());
                                    obj.setStartDate(dResponse.getStartDate());
                                    obj.setNcb(dResponse.getNcb());
                                    obj.setCurrentNcb(dResponse.getCurrentNcb());
                                    obj.setPa(dResponse.getPa());
                                    obj.setTp(dResponse.getTp());
                                    obj.setInspection(dResponse.getInspection());
                                    obj.setBreakingAllowStatus(dResponse.getBreakingAllowStatus());
                                    obj.setOd(dResponse.getOd());
                                    obj.setGst(dResponse.getGst());
                                    obj.setTataFlag(dResponse.getTataFlag());
                                    obj.setZeroDept(dResponse.getZeroDept());
                                    obj.setIdvMin(dResponse.getIdvMin());
                                    obj.setIdvMax(dResponse.getIdvMax());
                                    obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                    int min = dResponse.getIdvMin();
                                    int max = dResponse.getIdvMax();

                                    if (min > 0)
                                        idvList.add(String.valueOf(min));
                                    if (max > 0)
                                        idvList.add(String.valueOf(max));
                                    ArrayList<String> arrayList = new ArrayList<>();

                                    if (!TextUtils.isEmpty(dResponse.getEmergencyCover()))
                                        if (!dResponse.getEmergencyCover().equalsIgnoreCase(ZERO) && !dResponse.getEmergencyCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Emergency Cover: " + dResponse.getEmergencyCover());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getZeroDept()))
                                        if (!dResponse.getZeroDept().equalsIgnoreCase(ZERO) && !dResponse.getZeroDept().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Zero Dep Cover: " + dResponse.getZeroDept());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityEmployee()))
                                        if (!dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Employee: " + dResponse.getLegalLiabilityEmployee());
                                            obj.setLegal_liability_employee(Float.valueOf(dResponse.getLegalLiabilityEmployee()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getImt23()))
                                        if (!dResponse.getImt23().equalsIgnoreCase(ZERO) && !dResponse.getImt23().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 23: " + dResponse.getImt23());

                                    if (!TextUtils.isEmpty(dResponse.getImt34()))
                                        if (!dResponse.getImt34().equalsIgnoreCase(ZERO) && !dResponse.getImt34().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 34: " + dResponse.getImt34());

                                    if (!TextUtils.isEmpty(dResponse.getTppdRestrictedTo()))
                                        if (!dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO) && !dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO1)) {
                                            obj.setTppd_restricted_to(Float.valueOf(dResponse.getTppdRestrictedTo()));
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityPaidDriver()))
                                        if (!dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Paid Driver: " + dResponse.getLegalLiabilityPaidDriver());
                                            obj.setLegal_liability_paid_driver(Float.valueOf(dResponse.getLegalLiabilityPaidDriver()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getConsumables()))
                                        if (!dResponse.getConsumables().equalsIgnoreCase(ZERO) && !dResponse.getConsumables().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Consumables: " + dResponse.getConsumables());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getTyreCover()))
                                        if (!dResponse.getTyreCover().equalsIgnoreCase(ZERO) && !dResponse.getTyreCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Tyre Cover: " + dResponse.getTyreCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getNcbProtection()))
                                        if (!dResponse.getNcbProtection().equalsIgnoreCase(ZERO) && !dResponse.getNcbProtection().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("NCB Protection: " + dResponse.getNcbProtection());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getEngineProtector()))
                                        if (!dResponse.getEngineProtector().equalsIgnoreCase(ZERO) && !dResponse.getEngineProtector().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Engine Cover: " + dResponse.getEngineProtector());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getReturnInvoice()))
                                        if (!dResponse.getReturnInvoice().equalsIgnoreCase(ZERO) && !dResponse.getReturnInvoice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Invoice Cover: " + dResponse.getReturnInvoice());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLossOfKey()))
                                        if (!dResponse.getLossOfKey().equalsIgnoreCase(ZERO) && !dResponse.getLossOfKey().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Loss of key: " + dResponse.getLossOfKey());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getRoadSideAssistance()))
                                        if (!dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO) && !dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Road side Assistance: " + dResponse.getRoadSideAssistance());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getPassengerAssistCover()))
                                        if (!dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO) && !dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Passenger Cover: " + dResponse.getPassengerAssistCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getHospitalCashCover()))
                                        if (!dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO) && !dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Hospital Cover: " + dResponse.getHospitalCashCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getAntiTheftDevice()))
                                        if (!dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO) && !dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Anti Theft Device: " + dResponse.getAntiTheftDevice());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getElectricalAccessory()))
                                        if (!dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Electrical Accessory: " + dResponse.getElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getNonElectricalAccessory()))
                                        if (!dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Non Electrical Accessory: " + dResponse.getNonElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKit()))
                                        if (!dResponse.getFuleKit().equalsIgnoreCase(ZERO) && !dResponse.getFuleKit().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fuel Kit: " + dResponse.getFuleKit());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKitTp()))
                                        if (!dResponse.getFuleKitTp().equalsIgnoreCase(ZERO) && !dResponse.getFuleKitTp().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Fuel Kit Tp: " + dResponse.getFuleKitTp());
                                            obj.setFuelKitTp(Float.valueOf(dResponse.getFuleKitTp()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getFiberGlassFuelTank()))
                                        if (!dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO) && !dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fiber Glass Fuel Tank: " + dResponse.getFiberGlassFuelTank());

                                    if (!TextUtils.isEmpty(dResponse.getPacoverforUnnamedPerson()))
                                        if (!dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO) && !dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("PA Cover for Unnamed Person: " + dResponse.getPacoverforUnnamedPerson());
                                            obj.setPacoverfor_unnamed_person(Float.valueOf(dResponse.getPacoverforUnnamedPerson()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getMemberOfAai()))
                                        if (!dResponse.getMemberOfAai().equalsIgnoreCase(ZERO) && !dResponse.getMemberOfAai().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Member Of Aai: " + dResponse.getMemberOfAai());

                                    if (!TextUtils.isEmpty(dResponse.getVoluntaryExcess()))
                                        if (!dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO) && !dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Voluntary Excess: " + dResponse.getVoluntaryExcess());

                                    obj.setArrayCover(arrayList);
                                    premiumList.add(obj);

                                }
                            }
                        }
                        isFlag++;

                    }

                    if (dResponse.getCompany().equalsIgnoreCase("icici")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("icici")) {
                                premiumList.remove(i);
                                break;
                            }
                        }

                        if (dResponse.getStatus().equalsIgnoreCase("1")) {
                            if (!dResponse.getNet().equalsIgnoreCase("0")) {
                                if (!TextUtils.isEmpty(dResponse.getNet())) {
                                    PremiumObj obj = new PremiumObj();

                                    obj.setLogo(dResponse.getLogo());
                                    obj.setNet(dResponse.getNet());
                                    obj.setIdv(dResponse.getIdv());
                                    obj.setPolicyType(dResponse.getPolicyType());
                                    obj.setTenure(dResponse.getTenure());

                                    obj.setCompany(dResponse.getCompany());
                                    obj.setTotalPremium(dResponse.getTotalPremium());
                                    obj.setEndDate(dResponse.getEndDate());
                                    obj.setStartDate(dResponse.getStartDate());
                                    obj.setNcb(dResponse.getNcb());
                                    obj.setCurrentNcb(dResponse.getCurrentNcb());
                                    obj.setPa(dResponse.getPa());
                                    obj.setTp(dResponse.getTp());
                                    obj.setInspection(dResponse.getInspection());
                                    obj.setBreakingAllowStatus(dResponse.getBreakingAllowStatus());
                                    obj.setOd(dResponse.getOd());
                                    obj.setGst(dResponse.getGst());
                                    obj.setTataFlag(dResponse.getTataFlag());
                                    obj.setZeroDept(dResponse.getZeroDept());
                                    obj.setIdvMin(dResponse.getIdvMin());
                                    obj.setIdvMax(dResponse.getIdvMax());
                                    obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                    int min = dResponse.getIdvMin();
                                    int max = dResponse.getIdvMax();

                                    if (min > 0)
                                        idvList.add(String.valueOf(min));
                                    if (max > 0)
                                        idvList.add(String.valueOf(max));
                                    ArrayList<String> arrayList = new ArrayList<>();

                                    if (!TextUtils.isEmpty(dResponse.getEmergencyCover()))
                                        if (!dResponse.getEmergencyCover().equalsIgnoreCase(ZERO) && !dResponse.getEmergencyCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Emergency Cover: " + dResponse.getEmergencyCover());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getZeroDept()))
                                        if (!dResponse.getZeroDept().equalsIgnoreCase(ZERO) && !dResponse.getZeroDept().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Zero Dep Cover: " + dResponse.getZeroDept());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityEmployee()))
                                        if (!dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Employee: " + dResponse.getLegalLiabilityEmployee());
                                            obj.setLegal_liability_employee(Float.valueOf(dResponse.getLegalLiabilityEmployee()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getImt23()))
                                        if (!dResponse.getImt23().equalsIgnoreCase(ZERO) && !dResponse.getImt23().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 23: " + dResponse.getImt23());

                                    if (!TextUtils.isEmpty(dResponse.getImt34()))
                                        if (!dResponse.getImt34().equalsIgnoreCase(ZERO) && !dResponse.getImt34().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 34: " + dResponse.getImt34());

                                    if (!TextUtils.isEmpty(dResponse.getTppdRestrictedTo()))
                                        if (!dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO) && !dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO1)) {
                                            obj.setTppd_restricted_to(Float.valueOf(dResponse.getTppdRestrictedTo()));
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityPaidDriver()))
                                        if (!dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Paid Driver: " + dResponse.getLegalLiabilityPaidDriver());
                                            obj.setLegal_liability_paid_driver(Float.valueOf(dResponse.getLegalLiabilityPaidDriver()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getConsumables()))
                                        if (!dResponse.getConsumables().equalsIgnoreCase(ZERO) && !dResponse.getConsumables().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Consumables: " + dResponse.getConsumables());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getTyreCover()))
                                        if (!dResponse.getTyreCover().equalsIgnoreCase(ZERO) && !dResponse.getTyreCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Tyre Cover: " + dResponse.getTyreCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getNcbProtection()))
                                        if (!dResponse.getNcbProtection().equalsIgnoreCase(ZERO) && !dResponse.getNcbProtection().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("NCB Protection: " + dResponse.getNcbProtection());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getEngineProtector()))
                                        if (!dResponse.getEngineProtector().equalsIgnoreCase(ZERO) && !dResponse.getEngineProtector().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Engine Cover: " + dResponse.getEngineProtector());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getReturnInvoice()))
                                        if (!dResponse.getReturnInvoice().equalsIgnoreCase(ZERO) && !dResponse.getReturnInvoice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Invoice Cover: " + dResponse.getReturnInvoice());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLossOfKey()))
                                        if (!dResponse.getLossOfKey().equalsIgnoreCase(ZERO) && !dResponse.getLossOfKey().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Loss of key: " + dResponse.getLossOfKey());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getRoadSideAssistance()))
                                        if (!dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO) && !dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Road side Assistance: " + dResponse.getRoadSideAssistance());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getPassengerAssistCover()))
                                        if (!dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO) && !dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Passenger Cover: " + dResponse.getPassengerAssistCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getHospitalCashCover()))
                                        if (!dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO) && !dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Hospital Cover: " + dResponse.getHospitalCashCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getAntiTheftDevice()))
                                        if (!dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO) && !dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Anti Theft Device: " + dResponse.getAntiTheftDevice());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getElectricalAccessory()))
                                        if (!dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Electrical Accessory: " + dResponse.getElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getNonElectricalAccessory()))
                                        if (!dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Non Electrical Accessory: " + dResponse.getNonElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKit()))
                                        if (!dResponse.getFuleKit().equalsIgnoreCase(ZERO) && !dResponse.getFuleKit().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fuel Kit: " + dResponse.getFuleKit());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKitTp()))
                                        if (!dResponse.getFuleKitTp().equalsIgnoreCase(ZERO) && !dResponse.getFuleKitTp().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Fuel Kit Tp: " + dResponse.getFuleKitTp());
                                            obj.setFuelKitTp(Float.valueOf(dResponse.getFuleKitTp()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getFiberGlassFuelTank()))
                                        if (!dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO) && !dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fiber Glass Fuel Tank: " + dResponse.getFiberGlassFuelTank());

                                    if (!TextUtils.isEmpty(dResponse.getPacoverforUnnamedPerson()))
                                        if (!dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO) && !dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("PA Cover for Unnamed Person: " + dResponse.getPacoverforUnnamedPerson());
                                            obj.setPacoverfor_unnamed_person(Float.valueOf(dResponse.getPacoverforUnnamedPerson()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getMemberOfAai()))
                                        if (!dResponse.getMemberOfAai().equalsIgnoreCase(ZERO) && !dResponse.getMemberOfAai().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Member Of Aai: " + dResponse.getMemberOfAai());

                                    if (!TextUtils.isEmpty(dResponse.getVoluntaryExcess()))
                                        if (!dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO) && !dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Voluntary Excess: " + dResponse.getVoluntaryExcess());

                                    obj.setArrayCover(arrayList);
                                    premiumList.add(obj);

                                }
                            }
                        }
                        isFlag++;
                    }

                    if (dResponse.getCompany().equalsIgnoreCase("universal")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("universal")) {
                                premiumList.remove(i);
                                break;
                            }
                        }

                        if (dResponse.getStatus().equalsIgnoreCase("1")) {
                            if (!dResponse.getNet().equalsIgnoreCase("0")) {
                                if (!TextUtils.isEmpty(dResponse.getNet())) {
                                    PremiumObj obj = new PremiumObj();

                                    obj.setLogo(dResponse.getLogo());
                                    obj.setNet(dResponse.getNet());
                                    obj.setIdv(dResponse.getIdv());
                                    obj.setPolicyType(dResponse.getPolicyType());
                                    obj.setTenure(dResponse.getTenure());

                                    obj.setCompany(dResponse.getCompany());
                                    obj.setTotalPremium(dResponse.getTotalPremium());
                                    obj.setEndDate(dResponse.getEndDate());
                                    obj.setStartDate(dResponse.getStartDate());
                                    obj.setNcb(dResponse.getNcb());
                                    obj.setCurrentNcb(dResponse.getCurrentNcb());
                                    obj.setPa(dResponse.getPa());
                                    obj.setTp(dResponse.getTp());
                                    obj.setInspection(dResponse.getInspection());
                                    obj.setBreakingAllowStatus(dResponse.getBreakingAllowStatus());
                                    obj.setOd(dResponse.getOd());
                                    obj.setGst(dResponse.getGst());
                                    obj.setTataFlag(dResponse.getTataFlag());
                                    obj.setZeroDept(dResponse.getZeroDept());
                                    obj.setIdvMin(dResponse.getIdvMin());
                                    obj.setIdvMax(dResponse.getIdvMax());
                                    obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                    int min = dResponse.getIdvMin();
                                    int max = dResponse.getIdvMax();

                                    if (min > 0)
                                        idvList.add(String.valueOf(min));
                                    if (max > 0)
                                        idvList.add(String.valueOf(max));
                                    ArrayList<String> arrayList = new ArrayList<>();

                                    if (!TextUtils.isEmpty(dResponse.getEmergencyCover()))
                                        if (!dResponse.getEmergencyCover().equalsIgnoreCase(ZERO) && !dResponse.getEmergencyCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Emergency Cover: " + dResponse.getEmergencyCover());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getZeroDept()))
                                        if (!dResponse.getZeroDept().equalsIgnoreCase(ZERO) && !dResponse.getZeroDept().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Zero Dep Cover: " + dResponse.getZeroDept());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityEmployee()))
                                        if (!dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Employee: " + dResponse.getLegalLiabilityEmployee());
                                            obj.setLegal_liability_employee(Float.valueOf(dResponse.getLegalLiabilityEmployee()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getImt23()))
                                        if (!dResponse.getImt23().equalsIgnoreCase(ZERO) && !dResponse.getImt23().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 23: " + dResponse.getImt23());

                                    if (!TextUtils.isEmpty(dResponse.getImt34()))
                                        if (!dResponse.getImt34().equalsIgnoreCase(ZERO) && !dResponse.getImt34().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 34: " + dResponse.getImt34());

                                    if (!TextUtils.isEmpty(dResponse.getTppdRestrictedTo()))
                                        if (!dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO) && !dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO1)) {
                                            obj.setTppd_restricted_to(Float.valueOf(dResponse.getTppdRestrictedTo()));
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityPaidDriver()))
                                        if (!dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Paid Driver: " + dResponse.getLegalLiabilityPaidDriver());
                                            obj.setLegal_liability_paid_driver(Float.valueOf(dResponse.getLegalLiabilityPaidDriver()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getConsumables()))
                                        if (!dResponse.getConsumables().equalsIgnoreCase(ZERO) && !dResponse.getConsumables().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Consumables: " + dResponse.getConsumables());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getTyreCover()))
                                        if (!dResponse.getTyreCover().equalsIgnoreCase(ZERO) && !dResponse.getTyreCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Tyre Cover: " + dResponse.getTyreCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getNcbProtection()))
                                        if (!dResponse.getNcbProtection().equalsIgnoreCase(ZERO) && !dResponse.getNcbProtection().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("NCB Protection: " + dResponse.getNcbProtection());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getEngineProtector()))
                                        if (!dResponse.getEngineProtector().equalsIgnoreCase(ZERO) && !dResponse.getEngineProtector().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Engine Cover: " + dResponse.getEngineProtector());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getReturnInvoice()))
                                        if (!dResponse.getReturnInvoice().equalsIgnoreCase(ZERO) && !dResponse.getReturnInvoice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Invoice Cover: " + dResponse.getReturnInvoice());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLossOfKey()))
                                        if (!dResponse.getLossOfKey().equalsIgnoreCase(ZERO) && !dResponse.getLossOfKey().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Loss of key: " + dResponse.getLossOfKey());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getRoadSideAssistance()))
                                        if (!dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO) && !dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Road side Assistance: " + dResponse.getRoadSideAssistance());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getPassengerAssistCover()))
                                        if (!dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO) && !dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Passenger Cover: " + dResponse.getPassengerAssistCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getHospitalCashCover()))
                                        if (!dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO) && !dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Hospital Cover: " + dResponse.getHospitalCashCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getAntiTheftDevice()))
                                        if (!dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO) && !dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Anti Theft Device: " + dResponse.getAntiTheftDevice());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getElectricalAccessory()))
                                        if (!dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Electrical Accessory: " + dResponse.getElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getNonElectricalAccessory()))
                                        if (!dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Non Electrical Accessory: " + dResponse.getNonElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKit()))
                                        if (!dResponse.getFuleKit().equalsIgnoreCase(ZERO) && !dResponse.getFuleKit().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fuel Kit: " + dResponse.getFuleKit());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKitTp()))
                                        if (!dResponse.getFuleKitTp().equalsIgnoreCase(ZERO) && !dResponse.getFuleKitTp().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Fuel Kit Tp: " + dResponse.getFuleKitTp());
                                            obj.setFuelKitTp(Float.valueOf(dResponse.getFuleKitTp()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getFiberGlassFuelTank()))
                                        if (!dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO) && !dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fiber Glass Fuel Tank: " + dResponse.getFiberGlassFuelTank());

                                    if (!TextUtils.isEmpty(dResponse.getPacoverforUnnamedPerson()))
                                        if (!dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO) && !dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("PA Cover for Unnamed Person: " + dResponse.getPacoverforUnnamedPerson());
                                            obj.setPacoverfor_unnamed_person(Float.valueOf(dResponse.getPacoverforUnnamedPerson()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getMemberOfAai()))
                                        if (!dResponse.getMemberOfAai().equalsIgnoreCase(ZERO) && !dResponse.getMemberOfAai().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Member Of Aai: " + dResponse.getMemberOfAai());

                                    if (!TextUtils.isEmpty(dResponse.getVoluntaryExcess()))
                                        if (!dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO) && !dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Voluntary Excess: " + dResponse.getVoluntaryExcess());

                                    obj.setArrayCover(arrayList);
                                    premiumList.add(obj);

                                }
                            }
                        }
                        isFlag++;
                    }

                    if (dResponse.getCompany().equalsIgnoreCase("united")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("united")) {
                                premiumList.remove(i);
                                break;
                            }
                        }

                        if (dResponse.getStatus().equalsIgnoreCase("1")) {
                            if (!dResponse.getNet().equalsIgnoreCase("0")) {
                                if (!TextUtils.isEmpty(dResponse.getNet())) {
                                    PremiumObj obj = new PremiumObj();

                                    obj.setLogo(dResponse.getLogo());
                                    obj.setNet(dResponse.getNet());
                                    obj.setIdv(dResponse.getIdv());
                                    obj.setPolicyType(dResponse.getPolicyType());
                                    obj.setTenure(dResponse.getTenure());

                                    obj.setCompany(dResponse.getCompany());
                                    obj.setTotalPremium(dResponse.getTotalPremium());
                                    obj.setEndDate(dResponse.getEndDate());
                                    obj.setStartDate(dResponse.getStartDate());
                                    obj.setNcb(dResponse.getNcb());
                                    obj.setCurrentNcb(dResponse.getCurrentNcb());
                                    obj.setPa(dResponse.getPa());
                                    obj.setTp(dResponse.getTp());
                                    obj.setInspection(dResponse.getInspection());
                                    obj.setBreakingAllowStatus(dResponse.getBreakingAllowStatus());
                                    obj.setOd(dResponse.getOd());
                                    obj.setGst(dResponse.getGst());
                                    obj.setTataFlag(dResponse.getTataFlag());
                                    obj.setZeroDept(dResponse.getZeroDept());
                                    obj.setIdvMin(dResponse.getIdvMin());
                                    obj.setIdvMax(dResponse.getIdvMax());
                                    obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                    int min = dResponse.getIdvMin();
                                    int max = dResponse.getIdvMax();

                                    if (min > 0)
                                        idvList.add(String.valueOf(min));
                                    if (max > 0)
                                        idvList.add(String.valueOf(max));
                                    ArrayList<String> arrayList = new ArrayList<>();

                                    if (!TextUtils.isEmpty(dResponse.getEmergencyCover()))
                                        if (!dResponse.getEmergencyCover().equalsIgnoreCase(ZERO) && !dResponse.getEmergencyCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Emergency Cover: " + dResponse.getEmergencyCover());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getZeroDept()))
                                        if (!dResponse.getZeroDept().equalsIgnoreCase(ZERO) && !dResponse.getZeroDept().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Zero Dep Cover: " + dResponse.getZeroDept());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityEmployee()))
                                        if (!dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Employee: " + dResponse.getLegalLiabilityEmployee());
                                            obj.setLegal_liability_employee(Float.valueOf(dResponse.getLegalLiabilityEmployee()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getImt23()))
                                        if (!dResponse.getImt23().equalsIgnoreCase(ZERO) && !dResponse.getImt23().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 23: " + dResponse.getImt23());

                                    if (!TextUtils.isEmpty(dResponse.getImt34()))
                                        if (!dResponse.getImt34().equalsIgnoreCase(ZERO) && !dResponse.getImt34().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 34: " + dResponse.getImt34());

                                    if (!TextUtils.isEmpty(dResponse.getTppdRestrictedTo()))
                                        if (!dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO) && !dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO1)) {
                                            obj.setTppd_restricted_to(Float.valueOf(dResponse.getTppdRestrictedTo()));
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityPaidDriver()))
                                        if (!dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Paid Driver: " + dResponse.getLegalLiabilityPaidDriver());
                                            obj.setLegal_liability_paid_driver(Float.valueOf(dResponse.getLegalLiabilityPaidDriver()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getConsumables()))
                                        if (!dResponse.getConsumables().equalsIgnoreCase(ZERO) && !dResponse.getConsumables().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Consumables: " + dResponse.getConsumables());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getTyreCover()))
                                        if (!dResponse.getTyreCover().equalsIgnoreCase(ZERO) && !dResponse.getTyreCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Tyre Cover: " + dResponse.getTyreCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getNcbProtection()))
                                        if (!dResponse.getNcbProtection().equalsIgnoreCase(ZERO) && !dResponse.getNcbProtection().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("NCB Protection: " + dResponse.getNcbProtection());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getEngineProtector()))
                                        if (!dResponse.getEngineProtector().equalsIgnoreCase(ZERO) && !dResponse.getEngineProtector().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Engine Cover: " + dResponse.getEngineProtector());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getReturnInvoice()))
                                        if (!dResponse.getReturnInvoice().equalsIgnoreCase(ZERO) && !dResponse.getReturnInvoice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Invoice Cover: " + dResponse.getReturnInvoice());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLossOfKey()))
                                        if (!dResponse.getLossOfKey().equalsIgnoreCase(ZERO) && !dResponse.getLossOfKey().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Loss of key: " + dResponse.getLossOfKey());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getRoadSideAssistance()))
                                        if (!dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO) && !dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Road side Assistance: " + dResponse.getRoadSideAssistance());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getPassengerAssistCover()))
                                        if (!dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO) && !dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Passenger Cover: " + dResponse.getPassengerAssistCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getHospitalCashCover()))
                                        if (!dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO) && !dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Hospital Cover: " + dResponse.getHospitalCashCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getAntiTheftDevice()))
                                        if (!dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO) && !dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Anti Theft Device: " + dResponse.getAntiTheftDevice());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getElectricalAccessory()))
                                        if (!dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Electrical Accessory: " + dResponse.getElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getNonElectricalAccessory()))
                                        if (!dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Non Electrical Accessory: " + dResponse.getNonElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKit()))
                                        if (!dResponse.getFuleKit().equalsIgnoreCase(ZERO) && !dResponse.getFuleKit().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fuel Kit: " + dResponse.getFuleKit());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKitTp()))
                                        if (!dResponse.getFuleKitTp().equalsIgnoreCase(ZERO) && !dResponse.getFuleKitTp().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Fuel Kit Tp: " + dResponse.getFuleKitTp());
                                            obj.setFuelKitTp(Float.valueOf(dResponse.getFuleKitTp()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getFiberGlassFuelTank()))
                                        if (!dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO) && !dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fiber Glass Fuel Tank: " + dResponse.getFiberGlassFuelTank());

                                    if (!TextUtils.isEmpty(dResponse.getPacoverforUnnamedPerson()))
                                        if (!dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO) && !dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("PA Cover for Unnamed Person: " + dResponse.getPacoverforUnnamedPerson());
                                            obj.setPacoverfor_unnamed_person(Float.valueOf(dResponse.getPacoverforUnnamedPerson()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getMemberOfAai()))
                                        if (!dResponse.getMemberOfAai().equalsIgnoreCase(ZERO) && !dResponse.getMemberOfAai().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Member Of Aai: " + dResponse.getMemberOfAai());

                                    if (!TextUtils.isEmpty(dResponse.getVoluntaryExcess()))
                                        if (!dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO) && !dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Voluntary Excess: " + dResponse.getVoluntaryExcess());

                                    obj.setArrayCover(arrayList);
                                    premiumList.add(obj);

                                }
                            }
                        }
                        isFlag++;
                    }

                    if (dResponse.getCompany().equalsIgnoreCase("raheja")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("raheja")) {
                                premiumList.remove(i);
                                break;
                            }
                        }

                        if (dResponse.getStatus().equalsIgnoreCase("1")) {
                            if (!dResponse.getNet().equalsIgnoreCase("0")) {
                                if (!TextUtils.isEmpty(dResponse.getNet())) {
                                    PremiumObj obj = new PremiumObj();

                                    obj.setLogo(dResponse.getLogo());
                                    obj.setNet(dResponse.getNet());
                                    obj.setIdv(dResponse.getIdv());
                                    obj.setPolicyType(dResponse.getPolicyType());
                                    obj.setTenure(dResponse.getTenure());

                                    obj.setCompany(dResponse.getCompany());
                                    obj.setTotalPremium(dResponse.getTotalPremium());
                                    obj.setEndDate(dResponse.getEndDate());
                                    obj.setStartDate(dResponse.getStartDate());
                                    obj.setNcb(dResponse.getNcb());
                                    obj.setCurrentNcb(dResponse.getCurrentNcb());
                                    obj.setPa(dResponse.getPa());
                                    obj.setTp(dResponse.getTp());
                                    obj.setInspection(dResponse.getInspection());
                                    obj.setBreakingAllowStatus(dResponse.getBreakingAllowStatus());
                                    obj.setOd(dResponse.getOd());
                                    obj.setGst(dResponse.getGst());
                                    obj.setTataFlag(dResponse.getTataFlag());
                                    obj.setZeroDept(dResponse.getZeroDept());
                                    obj.setIdvMin(dResponse.getIdvMin());
                                    obj.setIdvMax(dResponse.getIdvMax());
                                    obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                    int min = dResponse.getIdvMin();
                                    int max = dResponse.getIdvMax();

                                    if (min > 0)
                                        idvList.add(String.valueOf(min));
                                    if (max > 0)
                                        idvList.add(String.valueOf(max));
                                    ArrayList<String> arrayList = new ArrayList<>();

                                    if (!TextUtils.isEmpty(dResponse.getEmergencyCover()))
                                        if (!dResponse.getEmergencyCover().equalsIgnoreCase(ZERO) && !dResponse.getEmergencyCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Emergency Cover: " + dResponse.getEmergencyCover());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getZeroDept()))
                                        if (!dResponse.getZeroDept().equalsIgnoreCase(ZERO) && !dResponse.getZeroDept().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Zero Dep Cover: " + dResponse.getZeroDept());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityEmployee()))
                                        if (!dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Employee: " + dResponse.getLegalLiabilityEmployee());
                                            obj.setLegal_liability_employee(Float.valueOf(dResponse.getLegalLiabilityEmployee()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getImt23()))
                                        if (!dResponse.getImt23().equalsIgnoreCase(ZERO) && !dResponse.getImt23().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 23: " + dResponse.getImt23());

                                    if (!TextUtils.isEmpty(dResponse.getImt34()))
                                        if (!dResponse.getImt34().equalsIgnoreCase(ZERO) && !dResponse.getImt34().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 34: " + dResponse.getImt34());

                                    if (!TextUtils.isEmpty(dResponse.getTppdRestrictedTo()))
                                        if (!dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO) && !dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO1)) {
                                            obj.setTppd_restricted_to(Float.valueOf(dResponse.getTppdRestrictedTo()));
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityPaidDriver()))
                                        if (!dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Paid Driver: " + dResponse.getLegalLiabilityPaidDriver());
                                            obj.setLegal_liability_paid_driver(Float.valueOf(dResponse.getLegalLiabilityPaidDriver()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getConsumables()))
                                        if (!dResponse.getConsumables().equalsIgnoreCase(ZERO) && !dResponse.getConsumables().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Consumables: " + dResponse.getConsumables());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getTyreCover()))
                                        if (!dResponse.getTyreCover().equalsIgnoreCase(ZERO) && !dResponse.getTyreCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Tyre Cover: " + dResponse.getTyreCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getNcbProtection()))
                                        if (!dResponse.getNcbProtection().equalsIgnoreCase(ZERO) && !dResponse.getNcbProtection().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("NCB Protection: " + dResponse.getNcbProtection());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getEngineProtector()))
                                        if (!dResponse.getEngineProtector().equalsIgnoreCase(ZERO) && !dResponse.getEngineProtector().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Engine Cover: " + dResponse.getEngineProtector());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getReturnInvoice()))
                                        if (!dResponse.getReturnInvoice().equalsIgnoreCase(ZERO) && !dResponse.getReturnInvoice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Invoice Cover: " + dResponse.getReturnInvoice());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLossOfKey()))
                                        if (!dResponse.getLossOfKey().equalsIgnoreCase(ZERO) && !dResponse.getLossOfKey().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Loss of key: " + dResponse.getLossOfKey());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getRoadSideAssistance()))
                                        if (!dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO) && !dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Road side Assistance: " + dResponse.getRoadSideAssistance());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getPassengerAssistCover()))
                                        if (!dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO) && !dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Passenger Cover: " + dResponse.getPassengerAssistCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getHospitalCashCover()))
                                        if (!dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO) && !dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Hospital Cover: " + dResponse.getHospitalCashCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getAntiTheftDevice()))
                                        if (!dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO) && !dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Anti Theft Device: " + dResponse.getAntiTheftDevice());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getElectricalAccessory()))
                                        if (!dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Electrical Accessory: " + dResponse.getElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getNonElectricalAccessory()))
                                        if (!dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Non Electrical Accessory: " + dResponse.getNonElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKit()))
                                        if (!dResponse.getFuleKit().equalsIgnoreCase(ZERO) && !dResponse.getFuleKit().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fuel Kit: " + dResponse.getFuleKit());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKitTp()))
                                        if (!dResponse.getFuleKitTp().equalsIgnoreCase(ZERO) && !dResponse.getFuleKitTp().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Fuel Kit Tp: " + dResponse.getFuleKitTp());
                                            obj.setFuelKitTp(Float.valueOf(dResponse.getFuleKitTp()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getFiberGlassFuelTank()))
                                        if (!dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO) && !dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fiber Glass Fuel Tank: " + dResponse.getFiberGlassFuelTank());

                                    if (!TextUtils.isEmpty(dResponse.getPacoverforUnnamedPerson()))
                                        if (!dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO) && !dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("PA Cover for Unnamed Person: " + dResponse.getPacoverforUnnamedPerson());
                                            obj.setPacoverfor_unnamed_person(Float.valueOf(dResponse.getPacoverforUnnamedPerson()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getMemberOfAai()))
                                        if (!dResponse.getMemberOfAai().equalsIgnoreCase(ZERO) && !dResponse.getMemberOfAai().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Member Of Aai: " + dResponse.getMemberOfAai());

                                    if (!TextUtils.isEmpty(dResponse.getVoluntaryExcess()))
                                        if (!dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO) && !dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Voluntary Excess: " + dResponse.getVoluntaryExcess());

                                    obj.setArrayCover(arrayList);
                                    premiumList.add(obj);

                                }
                            }
                        }
                        isFlag++;
                    }

                    if (dResponse.getCompany().equalsIgnoreCase("shriram")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("shriram")) {
                                premiumList.remove(i);
                                break;
                            }
                        }

                        if (dResponse.getStatus().equalsIgnoreCase("1")) {
                            if (!dResponse.getNet().equalsIgnoreCase("0")) {
                                if (!TextUtils.isEmpty(dResponse.getNet())) {
                                    PremiumObj obj = new PremiumObj();

                                    obj.setLogo(dResponse.getLogo());
                                    obj.setNet(dResponse.getNet());
                                    obj.setIdv(dResponse.getIdv());
                                    obj.setPolicyType(dResponse.getPolicyType());
                                    obj.setTenure(dResponse.getTenure());

                                    obj.setCompany(dResponse.getCompany());
                                    obj.setTotalPremium(dResponse.getTotalPremium());
                                    obj.setEndDate(dResponse.getEndDate());
                                    obj.setStartDate(dResponse.getStartDate());
                                    obj.setNcb(dResponse.getNcb());
                                    obj.setCurrentNcb(dResponse.getCurrentNcb());
                                    obj.setPa(dResponse.getPa());
                                    obj.setTp(dResponse.getTp());
                                    obj.setInspection(dResponse.getInspection());
                                    obj.setBreakingAllowStatus(dResponse.getBreakingAllowStatus());
                                    obj.setOd(dResponse.getOd());
                                    obj.setGst(dResponse.getGst());
                                    obj.setTataFlag(dResponse.getTataFlag());
                                    obj.setZeroDept(dResponse.getZeroDept());
                                    obj.setIdvMin(dResponse.getIdvMin());
                                    obj.setIdvMax(dResponse.getIdvMax());
                                    obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                    int min = dResponse.getIdvMin();
                                    int max = dResponse.getIdvMax();

                                    if (min > 0)
                                        idvList.add(String.valueOf(min));
                                    if (max > 0)
                                        idvList.add(String.valueOf(max));
                                    ArrayList<String> arrayList = new ArrayList<>();

                                    if (!TextUtils.isEmpty(dResponse.getEmergencyCover()))
                                        if (!dResponse.getEmergencyCover().equalsIgnoreCase(ZERO) && !dResponse.getEmergencyCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Emergency Cover: " + dResponse.getEmergencyCover());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getZeroDept()))
                                        if (!dResponse.getZeroDept().equalsIgnoreCase(ZERO) && !dResponse.getZeroDept().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Zero Dep Cover: " + dResponse.getZeroDept());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityEmployee()))
                                        if (!dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Employee: " + dResponse.getLegalLiabilityEmployee());
                                            obj.setLegal_liability_employee(Float.valueOf(dResponse.getLegalLiabilityEmployee()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getImt23()))
                                        if (!dResponse.getImt23().equalsIgnoreCase(ZERO) && !dResponse.getImt23().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 23: " + dResponse.getImt23());

                                    if (!TextUtils.isEmpty(dResponse.getImt34()))
                                        if (!dResponse.getImt34().equalsIgnoreCase(ZERO) && !dResponse.getImt34().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 34: " + dResponse.getImt34());

                                    if (!TextUtils.isEmpty(dResponse.getTppdRestrictedTo()))
                                        if (!dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO) && !dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO1)) {
                                            obj.setTppd_restricted_to(Float.valueOf(dResponse.getTppdRestrictedTo()));
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityPaidDriver()))
                                        if (!dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Paid Driver: " + dResponse.getLegalLiabilityPaidDriver());
                                            obj.setLegal_liability_paid_driver(Float.valueOf(dResponse.getLegalLiabilityPaidDriver()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getConsumables()))
                                        if (!dResponse.getConsumables().equalsIgnoreCase(ZERO) && !dResponse.getConsumables().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Consumables: " + dResponse.getConsumables());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getTyreCover()))
                                        if (!dResponse.getTyreCover().equalsIgnoreCase(ZERO) && !dResponse.getTyreCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Tyre Cover: " + dResponse.getTyreCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getNcbProtection()))
                                        if (!dResponse.getNcbProtection().equalsIgnoreCase(ZERO) && !dResponse.getNcbProtection().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("NCB Protection: " + dResponse.getNcbProtection());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getEngineProtector()))
                                        if (!dResponse.getEngineProtector().equalsIgnoreCase(ZERO) && !dResponse.getEngineProtector().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Engine Cover: " + dResponse.getEngineProtector());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getReturnInvoice()))
                                        if (!dResponse.getReturnInvoice().equalsIgnoreCase(ZERO) && !dResponse.getReturnInvoice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Invoice Cover: " + dResponse.getReturnInvoice());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLossOfKey()))
                                        if (!dResponse.getLossOfKey().equalsIgnoreCase(ZERO) && !dResponse.getLossOfKey().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Loss of key: " + dResponse.getLossOfKey());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getRoadSideAssistance()))
                                        if (!dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO) && !dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Road side Assistance: " + dResponse.getRoadSideAssistance());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getPassengerAssistCover()))
                                        if (!dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO) && !dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Passenger Cover: " + dResponse.getPassengerAssistCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getHospitalCashCover()))
                                        if (!dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO) && !dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Hospital Cover: " + dResponse.getHospitalCashCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getAntiTheftDevice()))
                                        if (!dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO) && !dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Anti Theft Device: " + dResponse.getAntiTheftDevice());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getElectricalAccessory()))
                                        if (!dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Electrical Accessory: " + dResponse.getElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getNonElectricalAccessory()))
                                        if (!dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Non Electrical Accessory: " + dResponse.getNonElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKit()))
                                        if (!dResponse.getFuleKit().equalsIgnoreCase(ZERO) && !dResponse.getFuleKit().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fuel Kit: " + dResponse.getFuleKit());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKitTp()))
                                        if (!dResponse.getFuleKitTp().equalsIgnoreCase(ZERO) && !dResponse.getFuleKitTp().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Fuel Kit Tp: " + dResponse.getFuleKitTp());
                                            obj.setFuelKitTp(Float.valueOf(dResponse.getFuleKitTp()));
                                        }


                                    if (!TextUtils.isEmpty(dResponse.getPacoverforUnnamedPerson()))
                                        if (!dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO) && !dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("PA Cover for Unnamed Person: " + dResponse.getPacoverforUnnamedPerson());
                                            obj.setPacoverfor_unnamed_person(Float.valueOf(dResponse.getPacoverforUnnamedPerson()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getMemberOfAai()))
                                        if (!dResponse.getMemberOfAai().equalsIgnoreCase(ZERO) && !dResponse.getMemberOfAai().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Member Of Aai: " + dResponse.getMemberOfAai());

                                    if (!TextUtils.isEmpty(dResponse.getVoluntaryExcess()))
                                        if (!dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO) && !dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Voluntary Excess: " + dResponse.getVoluntaryExcess());

                                    obj.setArrayCover(arrayList);
                                    premiumList.add(obj);

                                }
                            }
                        }
                        isFlag++;
                    }

                    if (dResponse.getCompany().equalsIgnoreCase("future")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("future")) {
                                premiumList.remove(i);
                                break;
                            }
                        }

                        if (dResponse.getStatus().equalsIgnoreCase("1")) {
                            if (!dResponse.getNet().equalsIgnoreCase("0")) {
                                if (!TextUtils.isEmpty(dResponse.getNet())) {
                                    PremiumObj obj = new PremiumObj();

                                    obj.setLogo(dResponse.getLogo());
                                    obj.setNet(dResponse.getNet());
                                    obj.setIdv(dResponse.getIdv());
                                    obj.setPolicyType(dResponse.getPolicyType());
                                    obj.setTenure(dResponse.getTenure());

                                    obj.setCompany(dResponse.getCompany());
                                    obj.setTotalPremium(dResponse.getTotalPremium());
                                    obj.setEndDate(dResponse.getEndDate());
                                    obj.setStartDate(dResponse.getStartDate());
                                    obj.setNcb(dResponse.getNcb());
                                    obj.setCurrentNcb(dResponse.getCurrentNcb());
                                    obj.setPa(dResponse.getPa());
                                    obj.setTp(dResponse.getTp());
                                    obj.setInspection(dResponse.getInspection());
                                    obj.setBreakingAllowStatus(dResponse.getBreakingAllowStatus());
                                    obj.setOd(dResponse.getOd());
                                    obj.setGst(dResponse.getGst());
                                    obj.setTataFlag(dResponse.getTataFlag());
                                    obj.setZeroDept(dResponse.getZeroDept());
                                    obj.setIdvMin(dResponse.getIdvMin());
                                    obj.setIdvMax(dResponse.getIdvMax());
                                    obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                    int min = dResponse.getIdvMin();
                                    int max = dResponse.getIdvMax();

                                    if (min > 0)
                                        idvList.add(String.valueOf(min));
                                    if (max > 0)
                                        idvList.add(String.valueOf(max));
                                    ArrayList<String> arrayList = new ArrayList<>();

                                    if (!TextUtils.isEmpty(dResponse.getEmergencyCover()))
                                        if (!dResponse.getEmergencyCover().equalsIgnoreCase(ZERO) && !dResponse.getEmergencyCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Emergency Cover: " + dResponse.getEmergencyCover());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getZeroDept()))
                                        if (!dResponse.getZeroDept().equalsIgnoreCase(ZERO) && !dResponse.getZeroDept().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Zero Dep Cover: " + dResponse.getZeroDept());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityEmployee()))
                                        if (!dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Employee: " + dResponse.getLegalLiabilityEmployee());
                                            obj.setLegal_liability_employee(Float.valueOf(dResponse.getLegalLiabilityEmployee()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getImt23()))
                                        if (!dResponse.getImt23().equalsIgnoreCase(ZERO) && !dResponse.getImt23().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 23: " + dResponse.getImt23());

                                    if (!TextUtils.isEmpty(dResponse.getImt34()))
                                        if (!dResponse.getImt34().equalsIgnoreCase(ZERO) && !dResponse.getImt34().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 34: " + dResponse.getImt34());

                                    if (!TextUtils.isEmpty(dResponse.getTppdRestrictedTo()))
                                        if (!dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO) && !dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO1)) {
                                            obj.setTppd_restricted_to(Float.valueOf(dResponse.getTppdRestrictedTo()));
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityPaidDriver()))
                                        if (!dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Paid Driver: " + dResponse.getLegalLiabilityPaidDriver());
                                            obj.setLegal_liability_paid_driver(Float.valueOf(dResponse.getLegalLiabilityPaidDriver()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getConsumables()))
                                        if (!dResponse.getConsumables().equalsIgnoreCase(ZERO) && !dResponse.getConsumables().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Consumables: " + dResponse.getConsumables());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getTyreCover()))
                                        if (!dResponse.getTyreCover().equalsIgnoreCase(ZERO) && !dResponse.getTyreCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Tyre Cover: " + dResponse.getTyreCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getNcbProtection()))
                                        if (!dResponse.getNcbProtection().equalsIgnoreCase(ZERO) && !dResponse.getNcbProtection().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("NCB Protection: " + dResponse.getNcbProtection());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getEngineProtector()))
                                        if (!dResponse.getEngineProtector().equalsIgnoreCase(ZERO) && !dResponse.getEngineProtector().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Engine Cover: " + dResponse.getEngineProtector());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getReturnInvoice()))
                                        if (!dResponse.getReturnInvoice().equalsIgnoreCase(ZERO) && !dResponse.getReturnInvoice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Invoice Cover: " + dResponse.getReturnInvoice());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLossOfKey()))
                                        if (!dResponse.getLossOfKey().equalsIgnoreCase(ZERO) && !dResponse.getLossOfKey().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Loss of key: " + dResponse.getLossOfKey());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getRoadSideAssistance()))
                                        if (!dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO) && !dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Road side Assistance: " + dResponse.getRoadSideAssistance());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getPassengerAssistCover()))
                                        if (!dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO) && !dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Passenger Cover: " + dResponse.getPassengerAssistCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getHospitalCashCover()))
                                        if (!dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO) && !dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Hospital Cover: " + dResponse.getHospitalCashCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getAntiTheftDevice()))
                                        if (!dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO) && !dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Anti Theft Device: " + dResponse.getAntiTheftDevice());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getElectricalAccessory()))
                                        if (!dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Electrical Accessory: " + dResponse.getElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getNonElectricalAccessory()))
                                        if (!dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Non Electrical Accessory: " + dResponse.getNonElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKit()))
                                        if (!dResponse.getFuleKit().equalsIgnoreCase(ZERO) && !dResponse.getFuleKit().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fuel Kit: " + dResponse.getFuleKit());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKitTp()))
                                        if (!dResponse.getFuleKitTp().equalsIgnoreCase(ZERO) && !dResponse.getFuleKitTp().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Fuel Kit Tp: " + dResponse.getFuleKitTp());
                                            obj.setFuelKitTp(Float.valueOf(dResponse.getFuleKitTp()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getFiberGlassFuelTank()))
                                        if (!dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO) && !dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fiber Glass Fuel Tank: " + dResponse.getFiberGlassFuelTank());

                                    if (!TextUtils.isEmpty(dResponse.getPacoverforUnnamedPerson()))
                                        if (!dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO) && !dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("PA Cover for Unnamed Person: " + dResponse.getPacoverforUnnamedPerson());
                                            obj.setPacoverfor_unnamed_person(Float.valueOf(dResponse.getPacoverforUnnamedPerson()));
                                        }


                                    if (!TextUtils.isEmpty(dResponse.getMemberOfAai()))
                                        if (!dResponse.getMemberOfAai().equalsIgnoreCase(ZERO) && !dResponse.getMemberOfAai().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Member Of Aai: " + dResponse.getMemberOfAai());

                                    if (!TextUtils.isEmpty(dResponse.getVoluntaryExcess()))
                                        if (!dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO) && !dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Voluntary Excess: " + dResponse.getVoluntaryExcess());

                                    obj.setArrayCover(arrayList);
                                    premiumList.add(obj);

                                }
                            }
                        }
                        isFlag++;
                    }

                    if (dResponse.getCompany().equalsIgnoreCase("digitNew")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("digitNew")) {
                                premiumList.remove(i);
                                break;
                            }
                        }

                        if (dResponse.getStatus().equalsIgnoreCase("1")) {
                            if (!dResponse.getNet().equalsIgnoreCase("0")) {
                                if (!TextUtils.isEmpty(dResponse.getNet())) {
                                    PremiumObj obj = new PremiumObj();

                                    obj.setLogo(dResponse.getLogo());
                                    obj.setNet(dResponse.getNet());
                                    obj.setIdv(dResponse.getIdv());
                                    obj.setPolicyType(dResponse.getPolicyType());
                                    obj.setTenure(dResponse.getTenure());

                                    obj.setCompany(dResponse.getCompany());
                                    obj.setTotalPremium(dResponse.getTotalPremium());
                                    obj.setEndDate(dResponse.getEndDate());
                                    obj.setStartDate(dResponse.getStartDate());
                                    obj.setNcb(dResponse.getNcb());
                                    obj.setCurrentNcb(dResponse.getCurrentNcb());
                                    obj.setPa(dResponse.getPa());
                                    obj.setTp(dResponse.getTp());
                                    obj.setInspection(dResponse.getInspection());
                                    obj.setBreakingAllowStatus(dResponse.getBreakingAllowStatus());
                                    obj.setOd(dResponse.getOd());
                                    obj.setGst(dResponse.getGst());
                                    obj.setTataFlag(dResponse.getTataFlag());
                                    obj.setZeroDept(dResponse.getZeroDept());
                                    obj.setIdvMin(dResponse.getIdvMin());
                                    obj.setIdvMax(dResponse.getIdvMax());
                                    obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                    int min = dResponse.getIdvMin();
                                    int max = dResponse.getIdvMax();

                                    if (min > 0)
                                        idvList.add(String.valueOf(min));
                                    if (max > 0)
                                        idvList.add(String.valueOf(max));
                                    ArrayList<String> arrayList = new ArrayList<>();

                                    if (!TextUtils.isEmpty(dResponse.getEmergencyCover()))
                                        if (!dResponse.getEmergencyCover().equalsIgnoreCase(ZERO) && !dResponse.getEmergencyCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Emergency Cover: " + dResponse.getEmergencyCover());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getZeroDept()))
                                        if (!dResponse.getZeroDept().equalsIgnoreCase(ZERO) && !dResponse.getZeroDept().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Zero Dep Cover: " + dResponse.getZeroDept());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityEmployee()))
                                        if (!dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Employee: " + dResponse.getLegalLiabilityEmployee());
                                            obj.setLegal_liability_employee(Float.valueOf(dResponse.getLegalLiabilityEmployee()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getImt23()))
                                        if (!dResponse.getImt23().equalsIgnoreCase(ZERO) && !dResponse.getImt23().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 23: " + dResponse.getImt23());

                                    if (!TextUtils.isEmpty(dResponse.getImt34()))
                                        if (!dResponse.getImt34().equalsIgnoreCase(ZERO) && !dResponse.getImt34().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 34: " + dResponse.getImt34());

                                    if (!TextUtils.isEmpty(dResponse.getTppdRestrictedTo()))
                                        if (!dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO) && !dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO1)) {
                                            obj.setTppd_restricted_to(Float.valueOf(dResponse.getTppdRestrictedTo()));
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityPaidDriver()))
                                        if (!dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Paid Driver: " + dResponse.getLegalLiabilityPaidDriver());
                                            obj.setLegal_liability_paid_driver(Float.valueOf(dResponse.getLegalLiabilityPaidDriver()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getConsumables()))
                                        if (!dResponse.getConsumables().equalsIgnoreCase(ZERO) && !dResponse.getConsumables().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Consumables: " + dResponse.getConsumables());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getTyreCover()))
                                        if (!dResponse.getTyreCover().equalsIgnoreCase(ZERO) && !dResponse.getTyreCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Tyre Cover: " + dResponse.getTyreCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getNcbProtection()))
                                        if (!dResponse.getNcbProtection().equalsIgnoreCase(ZERO) && !dResponse.getNcbProtection().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("NCB Protection: " + dResponse.getNcbProtection());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getEngineProtector()))
                                        if (!dResponse.getEngineProtector().equalsIgnoreCase(ZERO) && !dResponse.getEngineProtector().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Engine Cover: " + dResponse.getEngineProtector());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getReturnInvoice()))
                                        if (!dResponse.getReturnInvoice().equalsIgnoreCase(ZERO) && !dResponse.getReturnInvoice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Invoice Cover: " + dResponse.getReturnInvoice());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLossOfKey()))
                                        if (!dResponse.getLossOfKey().equalsIgnoreCase(ZERO) && !dResponse.getLossOfKey().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Loss of key: " + dResponse.getLossOfKey());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getRoadSideAssistance()))
                                        if (!dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO) && !dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Road side Assistance: " + dResponse.getRoadSideAssistance());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getPassengerAssistCover()))
                                        if (!dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO) && !dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Passenger Cover: " + dResponse.getPassengerAssistCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getHospitalCashCover()))
                                        if (!dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO) && !dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Hospital Cover: " + dResponse.getHospitalCashCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getAntiTheftDevice()))
                                        if (!dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO) && !dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Anti Theft Device: " + dResponse.getAntiTheftDevice());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getElectricalAccessory()))
                                        if (!dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Electrical Accessory: " + dResponse.getElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getNonElectricalAccessory()))
                                        if (!dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Non Electrical Accessory: " + dResponse.getNonElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKit()))
                                        if (!dResponse.getFuleKit().equalsIgnoreCase(ZERO) && !dResponse.getFuleKit().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fuel Kit: " + dResponse.getFuleKit());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKitTp()))
                                        if (!dResponse.getFuleKitTp().equalsIgnoreCase(ZERO) && !dResponse.getFuleKitTp().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Fuel Kit Tp: " + dResponse.getFuleKitTp());
                                            obj.setFuelKitTp(Float.valueOf(dResponse.getFuleKitTp()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getFiberGlassFuelTank()))
                                        if (!dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO) && !dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fiber Glass Fuel Tank: " + dResponse.getFiberGlassFuelTank());

                                    if (!TextUtils.isEmpty(dResponse.getPacoverforUnnamedPerson()))
                                        if (!dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO) && !dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("PA Cover for Unnamed Person: " + dResponse.getPacoverforUnnamedPerson());
                                            obj.setPacoverfor_unnamed_person(Float.valueOf(dResponse.getPacoverforUnnamedPerson()));
                                        }


                                    if (!TextUtils.isEmpty(dResponse.getMemberOfAai()))
                                        if (!dResponse.getMemberOfAai().equalsIgnoreCase(ZERO) && !dResponse.getMemberOfAai().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Member Of Aai: " + dResponse.getMemberOfAai());

                                    if (!TextUtils.isEmpty(dResponse.getVoluntaryExcess()))
                                        if (!dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO) && !dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Voluntary Excess: " + dResponse.getVoluntaryExcess());

                                    obj.setArrayCover(arrayList);
                                    premiumList.add(obj);

                                }
                            }
                        }
                        isFlag++;
                    }

                    if (dResponse.getCompany().equalsIgnoreCase("digit")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("digit")) {
                                premiumList.remove(i);
                                break;
                            }
                        }

                        if (dResponse.getStatus().equalsIgnoreCase("1")) {
                            if (!dResponse.getNet().equalsIgnoreCase("0")) {
                                if (!TextUtils.isEmpty(dResponse.getNet())) {
                                    PremiumObj obj = new PremiumObj();

                                    obj.setLogo(dResponse.getLogo());
                                    obj.setNet(dResponse.getNet());
                                    obj.setIdv(dResponse.getIdv());

                                    obj.setCompany(dResponse.getCompany());
                                    obj.setTotalPremium(dResponse.getTotalPremium());
                                    obj.setEndDate(dResponse.getEndDate());
                                    obj.setStartDate(dResponse.getStartDate());
                                    obj.setNcb(dResponse.getNcb());
                                    obj.setCurrentNcb(dResponse.getCurrentNcb());
                                    obj.setPa(dResponse.getPa());
                                    obj.setTp(dResponse.getTp());
                                    obj.setInspection(dResponse.getInspection());
                                    obj.setBreakingAllowStatus(dResponse.getBreakingAllowStatus());
                                    obj.setOd(dResponse.getOd());
                                    obj.setGst(dResponse.getGst());
                                    obj.setTataFlag(dResponse.getTataFlag());
                                    obj.setZeroDept(dResponse.getZeroDept());
                                    obj.setIdvMin(dResponse.getIdvMin());
                                    obj.setIdvMax(dResponse.getIdvMax());
                                    obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                    int min = dResponse.getIdvMin();
                                    int max = dResponse.getIdvMax();

                                    if (min > 0)
                                        idvList.add(String.valueOf(min));
                                    if (max > 0)
                                        idvList.add(String.valueOf(max));

                                    obj.setPolicyType(dResponse.getPolicyType());
                                    obj.setTenure(dResponse.getTenure());
                                    ArrayList<String> arrayList = new ArrayList<>();

                                    if (!TextUtils.isEmpty(dResponse.getEmergencyCover()))
                                        if (!dResponse.getEmergencyCover().equalsIgnoreCase(ZERO) && !dResponse.getEmergencyCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Emergency Cover: " + dResponse.getEmergencyCover());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getZeroDept()))
                                        if (!dResponse.getZeroDept().equalsIgnoreCase(ZERO) && !dResponse.getZeroDept().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Zero Dep Cover: " + dResponse.getZeroDept());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityEmployee()))
                                        if (!dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Employee: " + dResponse.getLegalLiabilityEmployee());
                                            obj.setLegal_liability_employee(Float.valueOf(dResponse.getLegalLiabilityEmployee()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getImt23()))
                                        if (!dResponse.getImt23().equalsIgnoreCase(ZERO) && !dResponse.getImt23().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 23: " + dResponse.getImt23());

                                    if (!TextUtils.isEmpty(dResponse.getImt34()))
                                        if (!dResponse.getImt34().equalsIgnoreCase(ZERO) && !dResponse.getImt34().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 34: " + dResponse.getImt34());

                                    if (!TextUtils.isEmpty(dResponse.getTppdRestrictedTo()))
                                        if (!dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO) && !dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO1)) {
                                            obj.setTppd_restricted_to(Float.valueOf(dResponse.getTppdRestrictedTo()));
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityPaidDriver()))
                                        if (!dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Paid Driver: " + dResponse.getLegalLiabilityPaidDriver());
                                            obj.setLegal_liability_paid_driver(Float.valueOf(dResponse.getLegalLiabilityPaidDriver()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getConsumables()))
                                        if (!dResponse.getConsumables().equalsIgnoreCase(ZERO) && !dResponse.getConsumables().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Consumables: " + dResponse.getConsumables());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getTyreCover()))
                                        if (!dResponse.getTyreCover().equalsIgnoreCase(ZERO) && !dResponse.getTyreCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Tyre Cover: " + dResponse.getTyreCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getNcbProtection()))
                                        if (!dResponse.getNcbProtection().equalsIgnoreCase(ZERO) && !dResponse.getNcbProtection().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("NCB Protection: " + dResponse.getNcbProtection());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getEngineProtector()))
                                        if (!dResponse.getEngineProtector().equalsIgnoreCase(ZERO) && !dResponse.getEngineProtector().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Engine Cover: " + dResponse.getEngineProtector());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getReturnInvoice()))
                                        if (!dResponse.getReturnInvoice().equalsIgnoreCase(ZERO) && !dResponse.getReturnInvoice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Invoice Cover: " + dResponse.getReturnInvoice());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLossOfKey()))
                                        if (!dResponse.getLossOfKey().equalsIgnoreCase(ZERO) && !dResponse.getLossOfKey().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Loss of key: " + dResponse.getLossOfKey());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getRoadSideAssistance()))
                                        if (!dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO) && !dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Road side Assistance: " + dResponse.getRoadSideAssistance());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getPassengerAssistCover()))
                                        if (!dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO) && !dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Passenger Cover: " + dResponse.getPassengerAssistCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getHospitalCashCover()))
                                        if (!dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO) && !dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Hospital Cover: " + dResponse.getHospitalCashCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getAntiTheftDevice()))
                                        if (!dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO) && !dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Anti Theft Device: " + dResponse.getAntiTheftDevice());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getElectricalAccessory()))
                                        if (!dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Electrical Accessory: " + dResponse.getElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getNonElectricalAccessory()))
                                        if (!dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Non Electrical Accessory: " + dResponse.getNonElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKit()))
                                        if (!dResponse.getFuleKit().equalsIgnoreCase(ZERO) && !dResponse.getFuleKit().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fuel Kit: " + dResponse.getFuleKit());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKitTp()))
                                        if (!dResponse.getFuleKitTp().equalsIgnoreCase(ZERO) && !dResponse.getFuleKitTp().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Fuel Kit Tp: " + dResponse.getFuleKitTp());
                                            obj.setFuelKitTp(Float.valueOf(dResponse.getFuleKitTp()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getFiberGlassFuelTank()))
                                        if (!dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO) && !dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fiber Glass Fuel Tank: " + dResponse.getFiberGlassFuelTank());

                                    if (!TextUtils.isEmpty(dResponse.getPacoverforUnnamedPerson()))
                                        if (!dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO) && !dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("PA Cover for Unnamed Person: " + dResponse.getPacoverforUnnamedPerson());
                                            obj.setPacoverfor_unnamed_person(Float.valueOf(dResponse.getPacoverforUnnamedPerson()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getMemberOfAai()))
                                        if (!dResponse.getMemberOfAai().equalsIgnoreCase(ZERO) && !dResponse.getMemberOfAai().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Member Of Aai: " + dResponse.getMemberOfAai());

                                    if (!TextUtils.isEmpty(dResponse.getVoluntaryExcess()))
                                        if (!dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO) && !dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Voluntary Excess: " + dResponse.getVoluntaryExcess());

                                    obj.setArrayCover(arrayList);
                                    premiumList.add(obj);

                                }
                            }
                        }
                        isFlag++;
                    }

                    if (dResponse.getCompany().equalsIgnoreCase("cholamandalam")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("cholamandalam")) {
                                premiumList.remove(i);
                                break;
                            }
                        }

                        if (dResponse.getStatus().equalsIgnoreCase("1")) {
                            if (!dResponse.getNet().equalsIgnoreCase("0")) {
                                if (!TextUtils.isEmpty(dResponse.getNet())) {
                                    PremiumObj obj = new PremiumObj();

                                    obj.setLogo(dResponse.getLogo());
                                    obj.setNet(dResponse.getNet());
                                    obj.setIdv(dResponse.getIdv());

                                    obj.setCompany(dResponse.getCompany());
                                    obj.setTotalPremium(dResponse.getTotalPremium());
                                    obj.setEndDate(dResponse.getEndDate());
                                    obj.setStartDate(dResponse.getStartDate());
                                    obj.setNcb(dResponse.getNcb());
                                    obj.setCurrentNcb(dResponse.getCurrentNcb());
                                    obj.setPa(dResponse.getPa());
                                    obj.setTp(dResponse.getTp());
                                    obj.setInspection(dResponse.getInspection());
                                    obj.setBreakingAllowStatus(dResponse.getBreakingAllowStatus());
                                    obj.setOd(dResponse.getOd());
                                    obj.setGst(dResponse.getGst());
                                    obj.setTataFlag(dResponse.getTataFlag());
                                    obj.setZeroDept(dResponse.getZeroDept());
                                    obj.setIdvMin(dResponse.getIdvMin());
                                    obj.setIdvMax(dResponse.getIdvMax());
                                    obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                    int min = dResponse.getIdvMin();
                                    int max = dResponse.getIdvMax();

                                    if (min > 0)
                                        idvList.add(String.valueOf(min));
                                    if (max > 0)
                                        idvList.add(String.valueOf(max));

                                    obj.setPolicyType(dResponse.getPolicyType());
                                    obj.setTenure(dResponse.getTenure());
                                    ArrayList<String> arrayList = new ArrayList<>();

                                    if (!TextUtils.isEmpty(dResponse.getEmergencyCover()))
                                        if (!dResponse.getEmergencyCover().equalsIgnoreCase(ZERO) && !dResponse.getEmergencyCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Emergency Cover: " + dResponse.getEmergencyCover());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getZeroDept()))
                                        if (!dResponse.getZeroDept().equalsIgnoreCase(ZERO) && !dResponse.getZeroDept().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Zero Dep Cover: " + dResponse.getZeroDept());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityEmployee()))
                                        if (!dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Employee: " + dResponse.getLegalLiabilityEmployee());
                                            obj.setLegal_liability_employee(Float.valueOf(dResponse.getLegalLiabilityEmployee()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getImt23()))
                                        if (!dResponse.getImt23().equalsIgnoreCase(ZERO) && !dResponse.getImt23().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 23: " + dResponse.getImt23());

                                    if (!TextUtils.isEmpty(dResponse.getImt34()))
                                        if (!dResponse.getImt34().equalsIgnoreCase(ZERO) && !dResponse.getImt34().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 34: " + dResponse.getImt34());

                                    if (!TextUtils.isEmpty(dResponse.getTppdRestrictedTo()))
                                        if (!dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO) && !dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO1)) {
                                            obj.setTppd_restricted_to(Float.valueOf(dResponse.getTppdRestrictedTo()));
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityPaidDriver()))
                                        if (!dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Paid Driver: " + dResponse.getLegalLiabilityPaidDriver());
                                            obj.setLegal_liability_paid_driver(Float.valueOf(dResponse.getLegalLiabilityPaidDriver()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getConsumables()))
                                        if (!dResponse.getConsumables().equalsIgnoreCase(ZERO) && !dResponse.getConsumables().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Consumables: " + dResponse.getConsumables());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getTyreCover()))
                                        if (!dResponse.getTyreCover().equalsIgnoreCase(ZERO) && !dResponse.getTyreCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Tyre Cover: " + dResponse.getTyreCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getNcbProtection()))
                                        if (!dResponse.getNcbProtection().equalsIgnoreCase(ZERO) && !dResponse.getNcbProtection().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("NCB Protection: " + dResponse.getNcbProtection());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getEngineProtector()))
                                        if (!dResponse.getEngineProtector().equalsIgnoreCase(ZERO) && !dResponse.getEngineProtector().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Engine Cover: " + dResponse.getEngineProtector());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getReturnInvoice()))
                                        if (!dResponse.getReturnInvoice().equalsIgnoreCase(ZERO) && !dResponse.getReturnInvoice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Invoice Cover: " + dResponse.getReturnInvoice());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLossOfKey()))
                                        if (!dResponse.getLossOfKey().equalsIgnoreCase(ZERO) && !dResponse.getLossOfKey().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Loss of key: " + dResponse.getLossOfKey());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getRoadSideAssistance()))
                                        if (!dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO) && !dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Road side Assistance: " + dResponse.getRoadSideAssistance());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getPassengerAssistCover()))
                                        if (!dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO) && !dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Passenger Cover: " + dResponse.getPassengerAssistCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getHospitalCashCover()))
                                        if (!dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO) && !dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Hospital Cover: " + dResponse.getHospitalCashCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getAntiTheftDevice()))
                                        if (!dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO) && !dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Anti Theft Device: " + dResponse.getAntiTheftDevice());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getElectricalAccessory()))
                                        if (!dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Electrical Accessory: " + dResponse.getElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getNonElectricalAccessory()))
                                        if (!dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Non Electrical Accessory: " + dResponse.getNonElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKit()))
                                        if (!dResponse.getFuleKit().equalsIgnoreCase(ZERO) && !dResponse.getFuleKit().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fuel Kit: " + dResponse.getFuleKit());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKitTp()))
                                        if (!dResponse.getFuleKitTp().equalsIgnoreCase(ZERO) && !dResponse.getFuleKitTp().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Fuel Kit Tp: " + dResponse.getFuleKitTp());
                                            obj.setFuelKitTp(Float.valueOf(dResponse.getFuleKitTp()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getFiberGlassFuelTank()))
                                        if (!dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO) && !dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fiber Glass Fuel Tank: " + dResponse.getFiberGlassFuelTank());

                                    if (!TextUtils.isEmpty(dResponse.getPacoverforUnnamedPerson()))
                                        if (!dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO) && !dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("PA Cover for Unnamed Person: " + dResponse.getPacoverforUnnamedPerson());
                                            obj.setPacoverfor_unnamed_person(Float.valueOf(dResponse.getPacoverforUnnamedPerson()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getMemberOfAai()))
                                        if (!dResponse.getMemberOfAai().equalsIgnoreCase(ZERO) && !dResponse.getMemberOfAai().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Member Of Aai: " + dResponse.getMemberOfAai());

                                    if (!TextUtils.isEmpty(dResponse.getVoluntaryExcess()))
                                        if (!dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO) && !dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Voluntary Excess: " + dResponse.getVoluntaryExcess());

                                    obj.setArrayCover(arrayList);
                                    premiumList.add(obj);

                                }
                            }
                        }
                        isFlag++;
                    }

                    if (dResponse.getCompany().equalsIgnoreCase("royalSundaram")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("royalSundaram")) {
                                premiumList.remove(i);
                                break;
                            }
                        }

                        if (dResponse.getStatus().equalsIgnoreCase("1")) {
                            if (!dResponse.getNet().equalsIgnoreCase("0")) {
                                if (!TextUtils.isEmpty(dResponse.getNet())) {
                                    PremiumObj obj = new PremiumObj();

                                    obj.setLogo(dResponse.getLogo());
                                    obj.setNet(dResponse.getNet());
                                    obj.setIdv(dResponse.getIdv());

                                    obj.setCompany(dResponse.getCompany());
                                    obj.setTotalPremium(dResponse.getTotalPremium());
                                    obj.setEndDate(dResponse.getEndDate());
                                    obj.setStartDate(dResponse.getStartDate());
                                    obj.setNcb(dResponse.getNcb());
                                    obj.setCurrentNcb(dResponse.getCurrentNcb());
                                    obj.setPa(dResponse.getPa());
                                    obj.setTp(dResponse.getTp());
                                    obj.setInspection(dResponse.getInspection());
                                    obj.setBreakingAllowStatus(dResponse.getBreakingAllowStatus());
                                    obj.setOd(dResponse.getOd());
                                    obj.setGst(dResponse.getGst());
                                    obj.setTataFlag(dResponse.getTataFlag());
                                    obj.setZeroDept(dResponse.getZeroDept());
                                    obj.setIdvMin(dResponse.getIdvMin());
                                    obj.setIdvMax(dResponse.getIdvMax());
                                    obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                    int min = dResponse.getIdvMin();
                                    int max = dResponse.getIdvMax();

                                    if (min > 0)
                                        idvList.add(String.valueOf(min));
                                    if (max > 0)
                                        idvList.add(String.valueOf(max));

                                    obj.setPolicyType(dResponse.getPolicyType());
                                    obj.setTenure(dResponse.getTenure());
                                    ArrayList<String> arrayList = new ArrayList<>();

                                    if (!TextUtils.isEmpty(dResponse.getEmergencyCover()))
                                        if (!dResponse.getEmergencyCover().equalsIgnoreCase(ZERO) && !dResponse.getEmergencyCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Emergency Cover: " + dResponse.getEmergencyCover());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getZeroDept()))
                                        if (!dResponse.getZeroDept().equalsIgnoreCase(ZERO) && !dResponse.getZeroDept().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Zero Dep Cover: " + dResponse.getZeroDept());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityEmployee()))
                                        if (!dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityEmployee().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Employee: " + dResponse.getLegalLiabilityEmployee());
                                            obj.setLegal_liability_employee(Float.valueOf(dResponse.getLegalLiabilityEmployee()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getImt23()))
                                        if (!dResponse.getImt23().equalsIgnoreCase(ZERO) && !dResponse.getImt23().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 23: " + dResponse.getImt23());

                                    if (!TextUtils.isEmpty(dResponse.getImt34()))
                                        if (!dResponse.getImt34().equalsIgnoreCase(ZERO) && !dResponse.getImt34().equalsIgnoreCase(ZERO1))
                                            arrayList.add("IMT 34: " + dResponse.getImt34());

                                    if (!TextUtils.isEmpty(dResponse.getTppdRestrictedTo()))
                                        if (!dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO) && !dResponse.getTppdRestrictedTo().equalsIgnoreCase(ZERO1)) {
                                            obj.setTppd_restricted_to(Float.valueOf(dResponse.getTppdRestrictedTo()));
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getLegalLiabilityPaidDriver()))
                                        if (!dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO) && !dResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Legal Liability Paid Driver: " + dResponse.getLegalLiabilityPaidDriver());
                                            obj.setLegal_liability_paid_driver(Float.valueOf(dResponse.getLegalLiabilityPaidDriver()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getConsumables()))
                                        if (!dResponse.getConsumables().equalsIgnoreCase(ZERO) && !dResponse.getConsumables().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Consumables: " + dResponse.getConsumables());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getTyreCover()))
                                        if (!dResponse.getTyreCover().equalsIgnoreCase(ZERO) && !dResponse.getTyreCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Tyre Cover: " + dResponse.getTyreCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getNcbProtection()))
                                        if (!dResponse.getNcbProtection().equalsIgnoreCase(ZERO) && !dResponse.getNcbProtection().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("NCB Protection: " + dResponse.getNcbProtection());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getEngineProtector()))
                                        if (!dResponse.getEngineProtector().equalsIgnoreCase(ZERO) && !dResponse.getEngineProtector().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Engine Cover: " + dResponse.getEngineProtector());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getReturnInvoice()))
                                        if (!dResponse.getReturnInvoice().equalsIgnoreCase(ZERO) && !dResponse.getReturnInvoice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Invoice Cover: " + dResponse.getReturnInvoice());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getLossOfKey()))
                                        if (!dResponse.getLossOfKey().equalsIgnoreCase(ZERO) && !dResponse.getLossOfKey().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Loss of key: " + dResponse.getLossOfKey());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getRoadSideAssistance()))
                                        if (!dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO) && !dResponse.getRoadSideAssistance().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Road side Assistance: " + dResponse.getRoadSideAssistance());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getPassengerAssistCover()))
                                        if (!dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO) && !dResponse.getPassengerAssistCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Passenger Cover: " + dResponse.getPassengerAssistCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getHospitalCashCover()))
                                        if (!dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO) && !dResponse.getHospitalCashCover().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Hospital Cover: " + dResponse.getHospitalCashCover());
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getAntiTheftDevice()))
                                        if (!dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO) && !dResponse.getAntiTheftDevice().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Anti Theft Device: " + dResponse.getAntiTheftDevice());
                                        }
                                    if (!TextUtils.isEmpty(dResponse.getElectricalAccessory()))
                                        if (!dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Electrical Accessory: " + dResponse.getElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getNonElectricalAccessory()))
                                        if (!dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO) && !dResponse.getNonElectricalAccessory().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Non Electrical Accessory: " + dResponse.getNonElectricalAccessory());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKit()))
                                        if (!dResponse.getFuleKit().equalsIgnoreCase(ZERO) && !dResponse.getFuleKit().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fuel Kit: " + dResponse.getFuleKit());

                                    if (!TextUtils.isEmpty(dResponse.getFuleKitTp()))
                                        if (!dResponse.getFuleKitTp().equalsIgnoreCase(ZERO) && !dResponse.getFuleKitTp().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("Fuel Kit Tp: " + dResponse.getFuleKitTp());
                                            obj.setFuelKitTp(Float.valueOf(dResponse.getFuleKitTp()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getFiberGlassFuelTank()))
                                        if (!dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO) && !dResponse.getFiberGlassFuelTank().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Fiber Glass Fuel Tank: " + dResponse.getFiberGlassFuelTank());

                                    if (!TextUtils.isEmpty(dResponse.getPacoverforUnnamedPerson()))
                                        if (!dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO) && !dResponse.getPacoverforUnnamedPerson().equalsIgnoreCase(ZERO1)) {
                                            arrayList.add("PA Cover for Unnamed Person: " + dResponse.getPacoverforUnnamedPerson());
                                            obj.setPacoverfor_unnamed_person(Float.valueOf(dResponse.getPacoverforUnnamedPerson()));
                                        }

                                    if (!TextUtils.isEmpty(dResponse.getMemberOfAai()))
                                        if (!dResponse.getMemberOfAai().equalsIgnoreCase(ZERO) && !dResponse.getMemberOfAai().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Member Of Aai: " + dResponse.getMemberOfAai());

                                    if (!TextUtils.isEmpty(dResponse.getVoluntaryExcess()))
                                        if (!dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO) && !dResponse.getVoluntaryExcess().equalsIgnoreCase(ZERO1))
                                            arrayList.add("Voluntary Excess: " + dResponse.getVoluntaryExcess());

                                    obj.setArrayCover(arrayList);
                                    premiumList.add(obj);

                                }
                            }
                        }
                        isFlag++;
                    }
                }
                if (isFlag > 17) {
                    progressBar.setVisibility(View.GONE);
                    AppUtils.stopShimmer(shimmerFrameLayout);
                }

                if (premiumList.size() > 0) {
                    Collections.sort(premiumList, new Comparator<PremiumObj>() {
                        @Override
                        public int compare(PremiumObj lhs, PremiumObj rhs) {
                            return lhs.getTotalPremium().compareTo(rhs.getTotalPremium());
                        }
                    });
                    policyAdaptor.notifyDataSetChanged();
                    recyclerQuote.setAdapter(policyAdaptor);
                    findViewById(R.id.rlNoQuote).setVisibility(View.GONE);
                    findViewById(R.id.clQuote).setVisibility(View.VISIBLE);
                    AppUtils.stopShimmer(shimmerFrameLayout);
                    lblQuoteNo.setText("" + premiumList.size());
                } else {
                    if (isFlag > 18) {
                        findViewById(R.id.clQuote).setVisibility(View.GONE);
                        policyAdaptor.notifyItemRangeRemoved(0, premiumList.size());
                        policyAdaptor.notifyDataSetChanged();
                        if (premiumList.size() == 0)
                            findViewById(R.id.rlNoQuote).setVisibility(View.VISIBLE);
                    }
                    lblQuoteNo.setText("0");
                }

                if (vehicleType.equals("5") || vehicleType.equals("4") || vehicleType.equals("3")) {
                    AppUtils.stopShimmer(shimmerFrameLayout);
                    progressBar.setVisibility(View.GONE);
                    if (premiumList.size() == 0)
                        findViewById(R.id.rlNoQuote).setVisibility(View.VISIBLE);
                }
            }

            if (response instanceof BasicResponse) {
                BasicResponse fResponse = (BasicResponse) response;
                if (fResponse.getMsg().equalsIgnoreCase("pending"))
                    AppUtils.inspectionDialog(mContext, fResponse.getMsg());

                if (fResponse.getStatus() == 1) {
                    Toast.makeText(mContext, "" + fResponse.getMsg(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            if (response instanceof ProposalCompany) {

                ProposalCompany company = (ProposalCompany) response;
                if (company.getSuccess().equalsIgnoreCase(ONE)) {
                    String com = companyName;
                    if (companyName.equals("digitNew"))
                        com = "digit";

                    Intent intent = new Intent(mContext, OwnerDetailActivity.class);
                    mBundle.putString(AppUtils.IDV, idv);
                    mBundle.putString(AppUtils.GST, String.valueOf(gst));
                    mBundle.putString(AppUtils.NET_PREMIUM, String.valueOf(netPremium));
                    mBundle.putString(AppUtils.NCB, String.valueOf(currentNcb));
                    mBundle.putString(AppUtils.POLICY_TYPE, policyType);
                    mBundle.putString(AppUtils.ADDON_COVER, zeroDept);
                    mBundle.putString(AppUtils.IMG_PATH, imgPath);
                    mBundle.putString(AppUtils.COMPANY_NAME, com);
                    mBundle.putString(AppUtils.TOTAL_PREMIUM, String.valueOf(finalPremium));
                    mBundle.putString(AppUtils.POLICY_START_DATE, policyStartDate);
                    mBundle.putString(AppUtils.POLICY_END_DATE, policyEndDate);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }
                progressDialog.dismiss();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void inspectionDialog() {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle((R.string.app_name));
        alertDialogBuilder.setIcon(R.drawable.logo_app);
        alertDialogBuilder.setMessage("Your vehicle will be referred for pre inspection as the previous policy has already expired.");
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton(R.string.ok,
                        null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void OnPremiumButtonClick(int position) {

        setProposal(position);
    }

    private void setProposal(int position) {
        String userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        if (TextUtils.isEmpty(flag))
            flag = "0";
        tenure = premiumList.get(position).getTenure();

        if (tenure.equalsIgnoreCase("1 year"))
            tenure = "1";
        else if (tenure.equalsIgnoreCase("2 year"))
            tenure = "2";
        else if (tenure.equalsIgnoreCase("3 year"))
            tenure = "3";

        companyName = premiumList.get(position).getCompany();
        policyStartDate = premiumList.get(position).getStartDate();
        policyEndDate = premiumList.get(position).getEndDate();

        String breakingAllowed = premiumList.get(position).getBreakingAllowStatus();
        String inspectionAllowed = String.valueOf(premiumList.get(position).getInspection());
        String pType = premiumList.get(position).getPolicyType();

        if (breakingAllowed.equals(inspectionAllowed)
                || pType.equalsIgnoreCase("Third Party")
                || isNewVehicle) {

            imgPath = premiumList.get(position).getLogo();
            finalPremium = Float.valueOf(premiumList.get(position).getTotalPremium());
            idv = premiumList.get(position).getIdv();
            netPremium = Float.valueOf(premiumList.get(position).getNet());
            flag = premiumList.get(position).getTataFlag();

            gst = Float.valueOf(premiumList.get(position).getGst());
            od = Float.valueOf(premiumList.get(position).getOd());
            tp = Float.valueOf(premiumList.get(position).getTp());
            pa = Float.valueOf(premiumList.get(position).getPa());

            Float fule_kit_tp, legal_liability_employee, legal_liability_paid_driver,
                    pacoverfor_unnamed_person, tppd_restricted_to;

            fule_kit_tp = premiumList.get(position).getFuelKitTp();
            legal_liability_employee = premiumList.get(position).getLegal_liability_employee();
            legal_liability_paid_driver = premiumList.get(position).getLegal_liability_paid_driver();
            pacoverfor_unnamed_person = premiumList.get(position).getPacoverfor_unnamed_person();
            tppd_restricted_to = premiumList.get(position).getTppd_restricted_to();

            if (fule_kit_tp == null)
                fule_kit_tp = 0f;
            if (legal_liability_employee == null)
                legal_liability_employee = 0f;
            if (legal_liability_paid_driver == null)
                legal_liability_paid_driver = 0f;
            if (pacoverfor_unnamed_person == null)
                pacoverfor_unnamed_person = 0f;
            if (tppd_restricted_to == null)
                tppd_restricted_to = 0f;

            tp = tp + fule_kit_tp + legal_liability_employee + legal_liability_paid_driver
                    + pacoverfor_unnamed_person - tppd_restricted_to;

            od = netPremium - (pa + tp);
            paCoverValue = premiumList.get(position).getPa();

            if (tpOnly.equalsIgnoreCase("2"))
                tp = (float) 0;

            if (!TextUtils.isEmpty(newVehicle)) {
                if (newVehicle.equalsIgnoreCase("new_gadi")) {
                    currentNcb = "NA";
                }
            }

            if (companyName.equalsIgnoreCase("tata")) {
                if (!TextUtils.isEmpty(premiumList.get(position).getRoad_side_assistance())) {
                    od = od + 112;
                    netPremium = netPremium + 112;
                    gst = gst + 25;
                }
            }
            String insurer = premiumList.get(position).getPreviousInsurer();
            if (!TextUtils.isEmpty(insurer)) {
                if (companyName.equals(insurer)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Renewal Confirmation");
                    builder.setIcon(R.drawable.ic_alert);
                    builder.setMessage("Is it Square Insurance Broker's Renewal?");
                    builder.setPositiveButton(R.string.yes, (dialog, which) -> {
                        proposalCompany();
                    });
                    builder.setNegativeButton(R.string.no, null);
                    builder.show();
                } else
                    proposalCompany();
            } else
                proposalCompany();

        } else if (TextUtils.isEmpty(userId)) {
            preferences.edit().putBoolean(AppUtils.IS_INSPECTION, true).apply();
            startActivity(new Intent(mContext, MainActivity.class));
        } else inspectionDialog();
    }

    @Override
    public void OnBreakUpClicked(int position) {

        dialog = new Dialog(mContext);
        // Include dialog.xml file
        dialog.setContentView(R.layout.premium_break);
        // Set dialog title
        dialog.setTitle("Premium Breakup");
        final TextView txtRegNumber = dialog.findViewById(R.id.txtRegNumber);
        final TextView txtNCb = dialog.findViewById(R.id.txtNcb);
        final TextView txtVehicle = dialog.findViewById(R.id.txtVehicle);
        final TextView txtBasicOD = dialog.findViewById(R.id.txtBasicOD);
        final TextView txtTP = dialog.findViewById(R.id.txtTP);
        final TextView txtPA = dialog.findViewById(R.id.txtPA);
        final TextView txtNetPremium = dialog.findViewById(R.id.txtNetPremium);
        final TextView txtGST = dialog.findViewById(R.id.txtGST);
        final TextView txtFinalPremium = dialog.findViewById(R.id.txtFinalPremium);
        final TextView txtNcbTotal = dialog.findViewById(R.id.txtNcbTotal);
        final TextView txtIdvValue = dialog.findViewById(R.id.txtIdvValue);
        final TextView txtIdvRange = dialog.findViewById(R.id.txtIdvRange);
        final TextView txtQuote = dialog.findViewById(R.id.txtQuote);

        final ImageView btnCancel = dialog.findViewById(R.id.btnCancel);
        final Button btnShare = dialog.findViewById(R.id.btnShare);
        final Button btnBuy = dialog.findViewById(R.id.btnBuy);

        final RelativeLayout rlAddOnCover = dialog.findViewById(R.id.rlAddOnCover);
        final RelativeLayout rlNcb = dialog.findViewById(R.id.rlNcb);
        final ImageView imgInsureLogo = dialog.findViewById(R.id.imgInsureLogo);
        final RecyclerView listAddOn = dialog.findViewById(R.id.listAddOn);
        final RelativeLayout rlBasicOd = dialog.findViewById(R.id.rlBasicOd);
        final RelativeLayout rlTp = dialog.findViewById(R.id.rlTp);
        final RelativeLayout rlPA = dialog.findViewById(R.id.rlPA);
        final RelativeLayout rlIDV = dialog.findViewById(R.id.rlIDV);
        final TextView lblFutureDis = dialog.findViewById(R.id.lblFutureDis);
        final TextView txtFutureDis = dialog.findViewById(R.id.txtFutureDis);

        txtRegNumber.setText(registrationNumber);
        txtQuote.setText(quotationId);
        txtVehicle.setText(vehicleName);
        if (!TextUtils.isEmpty(currentNcb))
            txtNCb.setText(currentNcb);
        else rlNcb.setVisibility(View.GONE);

        if (tpOnly.equalsIgnoreCase("1"))
            rlBasicOd.setVisibility(View.GONE);
        else if (tpOnly.equalsIgnoreCase("2")) {
            rlTp.setVisibility(View.GONE);
            rlIDV.setVisibility(View.VISIBLE);
        } else rlIDV.setVisibility(View.VISIBLE);

        if (premiumList.size() > 0 && premiumList.size() != position) {
            String cName = premiumList.get(position).getCompany();
            if (cName.equals("future")
                    && !TextUtils.isEmpty(premiumList.get(position).getTataFlag())) {

                lblFutureDis.setVisibility(View.VISIBLE);
                txtFutureDis.setVisibility(View.VISIBLE);

                txtFutureDis.setText(premiumList.get(position).getTataFlag());
            }
            Glide.with(mContext)
                    .load(premiumList.get(position).getLogo())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .animate(android.R.anim.fade_in)
                    .into(imgInsureLogo);

            txtBasicOD.setText(premiumList.get(position).getOd());
            txtTP.setText(premiumList.get(position).getTp());
            txtNetPremium.setText(premiumList.get(position).getNet());
            txtGST.setText(premiumList.get(position).getGst());
            txtFinalPremium.setText(premiumList.get(position).getTotalPremium());
            String pa = premiumList.get(position).getPa();

            if (TextUtils.isEmpty(pa) || pa.equalsIgnoreCase("0")) {
                rlPA.setVisibility(View.GONE);
            } else
                txtPA.setText(pa);

            String ncb = premiumList.get(position).getNcb();
            if (!TextUtils.isEmpty(ncb) && !ncb.equals("0"))
                txtNcbTotal.setText(ncb);
            else rlNcb.setVisibility(View.GONE);

            txtIdvValue.setText(premiumList.get(position).getIdv());
            txtIdvRange.setText("(" + premiumList.get(position).getIdvMin()
                    + " - " + premiumList.get(position).getIdvMax() + ") ");

            if (premiumList.get(position).getArrayCover().size() > 0) {
                rlAddOnCover.setVisibility(View.VISIBLE);

                CustomAdapter arrayAdapter = new CustomAdapter(mContext,
                        premiumList.get(position).getArrayCover());
                listAddOn.setLayoutManager(new LinearLayoutManager(mContext));
                listAddOn.setAdapter(arrayAdapter);
            }
        }
        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnBuy.setOnClickListener(v -> {
            setProposal(position);
            dialog.dismiss();
        });
        btnShare.setOnClickListener(v -> {
            Window window = dialog.getWindow();
            View decorView = window.getDecorView();
            takeScreenShot(decorView);
        });
        dialog.show();
    }

    @Override
    public void onAddonView(int position) {
        //Toast.makeText(mContext, position, Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (itemId == R.id.action_filter) {
            onFilter();
            return true;
        } else if (itemId == R.id.action_edit) {
            onEditVehicle();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onEditVehicle() {
        Intent intent = new Intent(mContext, FilterPcActivity.class);
        mBundle.putString(AppUtils.IDV, idv);
        mBundle.putString(AppUtils.POLICY_TYPE, policyType);
        mBundle.putString(AppUtils.MAX_IDV, maxIdv);
        mBundle.putString(AppUtils.MIN_IDV, minIdv);

        intent.putExtras(mBundle);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, 555);
        if (isUpdated)
            progressDialog.hide();
        isUpdated = false;
    }

    public void onFilter() {
        if (isUpdated && premiumList.size() == 0)
            delayTime();
        else
            onAddOn();
    }

    private void delayTime() {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        final Handler handler = new Handler();
        handler.postDelayed(this::onAddOn, 4000);
    }

    public void onAddOn() {
        int[] doubleList = new int[idvList.size()];
        for (int i = 0; i < idvList.size(); ++i) {
            doubleList[i] = Integer.parseInt(idvList.get(i));
        }
        Arrays.sort(doubleList);
        if (idvList.size() > 0) {

            if (doubleList[0] > doubleList[(doubleList.length - 1)]) {
                maxIdv = "" + doubleList[0];
                minIdv = "" + doubleList[(doubleList.length - 1)];
            } else {
                minIdv = "" + doubleList[0];
                maxIdv = "" + doubleList[(doubleList.length - 1)];
            }

            if (!TextUtils.isEmpty(minIdv))
                if (TextUtils.isEmpty(idv))
                    idv = minIdv;
        }
        Intent intent = new Intent(mContext, AddOnCoverActivity.class);
        mBundle.putString(AppUtils.IDV, idv);
        mBundle.putString(AppUtils.POLICY_TYPE, policyType);
        mBundle.putString(AppUtils.MAX_IDV, maxIdv);
        mBundle.putString(AppUtils.MIN_IDV, minIdv);

        intent.putExtras(mBundle);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, 555);
        if (isUpdated)
            progressDialog.hide();
        isUpdated = false;

    }

    public void offlineRequest(String action) {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {

                UserManager.getInstance().offlineRequest(mContext, quotationId, action);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    public void onOfflineRequest(View view) {
        if (!TextUtils.isEmpty(quotationId))
            offlineRequest("Yes");
    }

    public void onOfflineDeny(View view) {
        if (!TextUtils.isEmpty(quotationId))
            offlineRequest("No");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null) {
                policyType = data.getStringExtra(AppUtils.POLICY_TYPE);
                isFilter = data.getStringExtra(AppUtils.IS_FILTER);
                vehicleName = data.getStringExtra(AppUtils.VEHICLE_FULL);
                tpOnly = data.getStringExtra(AppUtils.TP_ONLY);
                pcvCompany = data.getStringExtra(AppUtils.PCV_COMPANY);
                String uIdv = data.getStringExtra(AppUtils.UPDATED_IDV);

                String make = data.getStringExtra(AppUtils.MAKE);
                String modelName = data.getStringExtra(AppUtils.MODEL);
                String variantName = data.getStringExtra(AppUtils.VARIANT);
                String fuelType = data.getStringExtra(AppUtils.FUEL_TYPE);

                if (!TextUtils.isEmpty(make)) {
                    mBundle.putString(AppUtils.MAKE, make);
                    mBundle.putString(AppUtils.MODEL, modelName);
                    mBundle.putString(AppUtils.FUEL_TYPE, fuelType);
                    mBundle.putString(AppUtils.VARIANT, variantName);
                    mBundle.putString(AppUtils.PCV_COMPANY, pcvCompany);

                    getSupportActionBar().setTitle(make);
                    getSupportActionBar().setSubtitle(modelName + " " + variantName);
                }

                if (premiumList.size() > 0)
                    premiumList.clear();
                policyAdaptor.notifyItemRangeRemoved(0, premiumList.size());
                policyAdaptor.notifyDataSetChanged();

                previousInsurer = data.getStringExtra(AppUtils.SAOD_INSURER);
                previousPolicyNo = data.getStringExtra(AppUtils.SAOD_POLICY_NO);
                tpPolicyExpDate = data.getStringExtra(AppUtils.SAOD_TP_EXP_DATE);

                mBundle.putString(AppUtils.SAOD_INSURER, previousInsurer);
                mBundle.putString(AppUtils.SAOD_POLICY_NO, previousPolicyNo);
                mBundle.putString(AppUtils.SAOD_TP_EXP_DATE, tpPolicyExpDate);

                if (!TextUtils.isEmpty(uIdv)) {
                    idv = uIdv;
                }
            }
            recallInsurer();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void takeScreenShot(View view) {

        Date date = new Date();
        CharSequence format = DateFormat.format("MM-dd-yyyy_hh:mm:ss", date);

        try {
            File mainDir = new File(
                    this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "FilShare");
            if (!mainDir.exists()) {
                boolean mkdir = mainDir.mkdir();
            }

            //Providing file name along with Bitmap to capture screenview
            String path = mainDir + "/" + "Square" + "-" + format + ".jpeg";
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            //This logic is used to save file at given location with the given filename and compress the Image Quality.
            File imageFile = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            //Create New Method to take ScreenShot with the imageFile.
            shareScreenShot(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shareScreenShot(File imageFile) {
        Uri uri = FileProvider.getUriForFile(this, "com.dmw.noble.provider",
                imageFile);

        //Explicit intent
        String shareBody = String.format("%s%s", AppUtils.PRE_LINK, shareQuoteId);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_TEXT, shareBody);
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        try {
            this.startActivity(Intent.createChooser(intent, "Share With"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mContext, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }

    public void onRaiseOfflineQuote(View view) {
        Intent intent = new Intent(mContext, RaiseOfflineActivity.class);
        mBundle.putString(AppUtils.QUOTATION_ID, quotationId);
        intent.putExtras(mBundle);
        startActivity(intent);
    }
}