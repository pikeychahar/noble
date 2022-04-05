package com.square.pos.activity_motor;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.os.StrictMode;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.adaptor.PremiumBikeAdapter;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.ApiManager;
import com.square.pos.manager.UserManager;
import com.square.pos.model.AddonCover;
import com.square.pos.model.BasicResponse;
import com.square.pos.model.Filter;
import com.square.pos.model.Fuel;
import com.square.pos.model.FuelType;
import com.square.pos.model.Insurer;
import com.square.pos.model.InsurerList;
import com.square.pos.model.Manufacture;
import com.square.pos.model.ManufactureList;
import com.square.pos.model.Model;
import com.square.pos.model.ModelList;
import com.square.pos.model.PremiumObjTw;
import com.square.pos.model.PremiumQuote;
import com.square.pos.model.ProposalCompany;
import com.square.pos.model.Variant;
import com.square.pos.model.VariantList;
import com.square.pos.utils.AppUtils;
import com.square.pos.utils.BankWithId;
import com.square.pos.utils.SearchableSpinner;
import com.square.pos.utils.String2WithTag;
import com.square.pos.utils.String4WithTag;
import com.square.pos.utils.String5WithTag;
import com.square.pos.utils.StringWithTag;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class PremiumBikeActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener,
        PremiumBikeAdapter.OnBikePremiumBreakClickListener, PremiumBikeAdapter.OnPremiumBikeClick {


    TextView txtIDV, txtQuoteId, lblQuoteNo;
    Dialog vehicleDialog;
    SearchableSpinner spnManufacture, spnModel, spnFuel,
            spnVariant;

    private List<Manufacture> manufactureList = new ArrayList<>();
    private List<Model> modelList = new ArrayList<>();
    private List<Variant> variantList = new ArrayList<>();
    List<FuelType> fuelList = new ArrayList<>();
    List<StringWithTag> manuList = new ArrayList<>();
    List<String4WithTag> moList = new ArrayList<>();
    List<String5WithTag> fList = new ArrayList<>();
    List<BankWithId> vList = new ArrayList<>();

    String idv, minIdv, maxIdv, quotationId, currentNcb, flag, imgPath, pcvCompany, policyExpiry,
            companyName, policyStartDate, policyEndDate, tenure, newVehicle, paCover, zeroDept,
            coverFor, ownedBy, ownerChange, claimExp, ncbOld, policyExpDate, cover, tpOnly, rsa,
            shareEmail, shareMobile, shareType, shareVehicle, shareQuoteId, vehicleType, policyType,
            previousPolicyType, prePolicy, vehicleName, registrationNumber, tppd, paCoverValue,
            make, modelName, fuelType, variantName, fMake, fModel, fFuel, fVariant, pcvType,
            userType, userId, unnamed, unnamedValue, paidDriver;

    Float finalPremium, netPremium, gst, od, tp;
    Context mContext;
    Dialog dialog;
    SharedPreferences preferences;

    Bundle mBundle;
    SwitchCompat paSwitch;
    int totalExpDay;
    ProgressDialog progressDialog;
    boolean isFlag, policyExpired, isNcb, isPaCover;
    private SwipeRefreshLayout swipeContainer;
    ArrayList<Integer> idvList = new ArrayList<>();
    Spinner spnPolicyType;
    private RecyclerView recyclerQuote;
    private PremiumBikeAdapter policyAdaptor;
    ArrayList<PremiumObjTw> premiumList = new ArrayList<>();
    ShimmerFrameLayout shimmerFrameLayout;

    //SAOD
    private TextView txtTPPreInsurer, txtTPExpDate, txtPolicyExpDate;
    private com.toptoche.searchablespinnerlibrary.SearchableSpinner spnTPPreInsurer;
    private EditText edtPolicyNumber;
    private List<Insurer> insurerList = new ArrayList<>();
    List<String2WithTag> piList = new ArrayList<>();
    String insurerId, previousInsurer, previousPolicyNo, tpPolicyExpDate, isPreviousPolicy,
            tpSaodInsurer;

    private SimpleDateFormat dateFormatter;
    private int regYear, pCount = 0;
    private long minDate, maxDate;
    private ProgressBar progressBar;
    com.google.android.material.floatingactionbutton.FloatingActionButton floating_action_button;
    List<String> spinnerArray = new ArrayList<>();
    RelativeLayout rlIdv, rlPA;
    ImageView imgOffline;

    //for training POS only
    private boolean isValidUser = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium_bike);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(getColor(R.color.colorPrimaryDark)));

        mContext = this;
        AppUtils.verifyStoragePermission(PremiumBikeActivity.this);
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);

        userType = preferences.getString(AppUtils.USER_TYPE, "");
        userId = preferences.getString(AppUtils.PRIMARY_ID, "");

        if (!TextUtils.isEmpty(userId))
            ApiManager.getInstance().userAuthentication(mContext, userId, userType);

        vehicleType = preferences.getString(AppUtils.VEHICLE_TYPE, "Vehicle");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //For POS only
        String userType, posStatus;
        userType = preferences.getString(AppUtils.USER_TYPE, "");
        posStatus = preferences.getString(AppUtils.POS_STATUS, "");
        if (!TextUtils.isEmpty(userType))
            if (userType.equalsIgnoreCase("agent"))
                isValidUser = !posStatus.equals("4");

        //user_interface  started : Do not delete
        if (vehicleType.equals("1"))
            shareVehicle = "Bike";
        else shareVehicle = "Vehicle";

        vehicleType = "1";
        vehicleDialog = new Dialog(mContext, android.R.style.ThemeOverlay_Material_Dialog_Alert);
        vehicleDialog.setContentView(R.layout.layout_vehicle);

        spnManufacture = vehicleDialog.findViewById(R.id.edtManufacture);
        spnModel = vehicleDialog.findViewById(R.id.edtModelName);
        spnFuel = vehicleDialog.findViewById(R.id.edtFuel);
        spnVariant = vehicleDialog.findViewById(R.id.edtVariant);
        mBundle = getIntent().getExtras();
        recyclerQuote = findViewById(R.id.premiumQuoteList);
        progressBar = findViewById(R.id.progressBar);
        swipeContainer = findViewById(R.id.swipeContainer);
        txtTPPreInsurer = findViewById(R.id.txtPreIns);
        txtTPExpDate = findViewById(R.id.txtPEDate);
        txtPolicyExpDate = findViewById(R.id.txtTED);
        txtIDV = findViewById(R.id.txtIDV);
        spnPolicyType = findViewById(R.id.spnPolicyType);
        txtQuoteId = findViewById(R.id.txtQuoteId);
        lblQuoteNo = findViewById(R.id.lblQuoteNo);
        spnTPPreInsurer = findViewById(R.id.edtPreInsurer);
        edtPolicyNumber = findViewById(R.id.edtPN);
        paSwitch = findViewById(R.id.swPA);
        floating_action_button = findViewById(R.id.floating_action_button);
        shimmerFrameLayout = findViewById(R.id.shimmerLayout);
        imgOffline = findViewById(R.id.imgOffline);

        rlIdv = findViewById(R.id.rlIdv);
        rlPA = findViewById(R.id.rlPA);

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Getting Quotations...");

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerQuote.setLayoutManager(mLayoutManager);

        recyclerQuote.setNestedScrollingEnabled(false);
        policyAdaptor = new PremiumBikeAdapter(mContext, premiumList);

        coverFor = cover = "1";
        idv = txtIDV.getText().toString();

        spinnerArray = new ArrayList<>();
        spinnerArray.add("Comprehensive");
        spinnerArray.add("Third Party");
        spinnerArray.add("Standalone OD");

        String str = "https://www.squareinsurance.in/uploads/offline-quotes.png";
        Glide.with(mContext)
                .load(str)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(imgOffline);

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
            vehicleName = mBundle.getString(AppUtils.VEHICLE_FULL);
            registrationNumber = mBundle.getString(AppUtils.REGISTRATION_NUMBER);
            policyExpired = mBundle.getBoolean(AppUtils.POLICY_EXPIRED);
            isPreviousPolicy = mBundle.getString(AppUtils.IS_PREVIOUS);
            isNcb = mBundle.getBoolean(AppUtils.IS_NCB);
            regYear = mBundle.getInt(AppUtils.REG_YEAR);
            policyExpiry = mBundle.getString(AppUtils.POLICY_EXPIRY);

            txtQuoteId.setText(quotationId);
            make = fMake = mBundle.getString(AppUtils.MAKE);
            fuelType = fFuel = mBundle.getString(AppUtils.FUEL_TYPE);
            modelName = fModel = mBundle.getString(AppUtils.MODEL);
            variantName = fVariant = mBundle.getString(AppUtils.VARIANT);

            getManufacture();

            if (!TextUtils.isEmpty(make))
                getModel();
            if (!TextUtils.isEmpty(modelName))
                getFuel();
            if (!TextUtils.isEmpty(fuelType))
                getVariant();

            if (!TextUtils.isEmpty(make)) {
                getSupportActionBar().setTitle(make);
                getSupportActionBar().setSubtitle(modelName + " " + variantName);
                vehicleName = make + " " + modelName + " " + variantName;
//                lblVehicle.setText(vehicleName);
            }
            tpSaodInsurer = mBundle.getString(AppUtils.SAOD_INSURER);
            if (!TextUtils.isEmpty(tpSaodInsurer))
                previousInsurer = tpSaodInsurer;
            edtPolicyNumber.setText(mBundle.getString(AppUtils.SAOD_POLICY_NO));
            txtTPPreInsurer.setText(tpSaodInsurer);
            txtTPExpDate.setText(mBundle.getString(AppUtils.SAOD_TP_EXP_DATE));
            txtPolicyExpDate.setText(mBundle.getString(AppUtils.SAOD_TP_EXP_DATE));
        }

        insurerList = UserManager.getInstance().getInsurerList();
        if (insurerList.size() > 0)
            initPreInsurer();
        else
            getPreInsurer();

        if (!TextUtils.isEmpty(newVehicle)) {
            if (newVehicle.equalsIgnoreCase("new_gadi")) {
                coverFor = "0";
                cover = "1";
            }
        } else {
            coverFor = "1";
            cover = "0";
        }

        tpOnly = zeroDept = tppd = unnamed = rsa = paidDriver = "0";
        boolean isOdHide = false;
        if (!TextUtils.isEmpty(previousPolicyType)) {
            if (((!previousPolicyType.equalsIgnoreCase("Comprehensive"))
            ) && !TextUtils.isEmpty(policyExpiry)
                    && !policyExpiry.equalsIgnoreCase("Not Expired")) {
                isOdHide = true;
            }
        }
        //|| totalExpDay > 90

        if (!TextUtils.isEmpty(newVehicle) || !TextUtils.isEmpty(isPreviousPolicy) || isOdHide) {
            if (newVehicle.equalsIgnoreCase("new_gadi")
                    || isPreviousPolicy.equals("1") || isOdHide) {

                spinnerArray = new ArrayList<>();
                spinnerArray.add("Comprehensive");
                spinnerArray.add("Third Party");
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPolicyType.setAdapter(adapter);

        if (policyType.equalsIgnoreCase("Comprehensive")) {
            spnPolicyType.setSelection(0);
            tpOnly = "0";
            rlIdv.setAlpha(1);
            rlIdv.setClickable(true);
        } else if (policyType.equalsIgnoreCase("Third Party")) {
            spnPolicyType.setSelection(1);
            tpOnly = "1";
            rlIdv.setAlpha(.8f);
            rlIdv.setClickable(false);
            txtIDV.setText("0");
        } else {
            tpOnly = "2";
            rlIdv.setAlpha(1);
            rlIdv.setClickable(true);
            spnPolicyType.setSelection(2);
            findViewById(R.id.rlSOD).setVisibility(View.VISIBLE);
            findViewById(R.id.llSAOD).setVisibility(View.GONE);

            edtPolicyNumber.setText(mBundle.getString(AppUtils.SAOD_POLICY_NO));
            txtTPPreInsurer.setText(mBundle.getString(AppUtils.SAOD_INSURER));
            txtTPExpDate.setText(mBundle.getString(AppUtils.SAOD_TP_EXP_DATE));
            txtPolicyExpDate.setText(mBundle.getString(AppUtils.SAOD_TP_EXP_DATE));
            if (!TextUtils.isEmpty(previousPolicyNo))
                if (isValidSaOd())
                    applyFilter();
        }
        paSwitch.setChecked(true);
        paCover = "1";
        paSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                paCover = "1";
            else paCover = "0";
        });

        if (!TextUtils.isEmpty(quotationId))
            shareQuoteId = AppUtils.encode(quotationId);

        if (!TextUtils.isEmpty(ncbOld) && isNcb) {

            if (ncbOld.equals("0"))
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

        if (!TextUtils.isEmpty(quotationId)) {
            getCovers();
            recallAll();
        }
        final int[] check = {0};
        spnPolicyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (++check[0] > 1) {
                    if (position == 0) {
                        tpOnly = "0";
                        policyType = "Comprehensive";
                        applyFilter();
                        findViewById(R.id.rlSOD).setVisibility(View.GONE);
                        rlIdv.setAlpha(1);
                        rlIdv.setClickable(true);
                    } else if (position == 1) {
                        tpOnly = "1";
                        policyType = "Third party";
                        applyFilter();
                        rlIdv.setAlpha(.8f);
                        rlIdv.setClickable(false);
                        txtIDV.setText("0");
                        findViewById(R.id.rlSOD).setVisibility(View.GONE);
                    } else if (position == 2) {
                        tpOnly = "2";
                        rlIdv.setAlpha(1);
                        rlIdv.setClickable(true);
                        policyType = "Standalone Od";
                        findViewById(R.id.rlSOD).setVisibility(View.VISIBLE);
                        findViewById(R.id.llSAOD).setVisibility(View.VISIBLE);
                        if (!TextUtils.isEmpty(previousPolicyNo))
                            if (isValidSaOd())
                                applyFilter();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        swipeContainer.setOnRefreshListener(() -> {

            if (!TextUtils.isEmpty(quotationId)) {
                if (tpOnly.equalsIgnoreCase("2")) {
                    if (isValidSaOd()) {
                        recallAll();
                    }
                } else {
                    recallAll();
                }
            }

            swipeContainer.setRefreshing(false);
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        if (!TextUtils.isEmpty(ownedBy))
            if (!ownedBy.equalsIgnoreCase("Individual")) {
                paCover = "0";
                rlPA.setClickable(false);
                rlPA.setAlpha(.8f);
                paSwitch.setClickable(false);
                if (tpOnly.equalsIgnoreCase("2")) {
                    if (isValidSaOd())
                        applyFilter();
                } else applyFilter();
            }

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        regYear = regYear + 4;
        calendar.set(regYear, 1, 1);
        minDate = calendar.getTimeInMillis();

        regYear = regYear + 1;
        calendar.set(regYear, 11, 30);
        maxDate = calendar.getTimeInMillis();
        calendar.set(regYear, currentMonth, currentDay);

        txtPolicyExpDate.setText(dateFormatter.format(calendar.getTime()));
        txtTPExpDate.setText(dateFormatter.format(calendar.getTime()));

    }

    private void recallAll() {
        if (premiumList.size() > 0)
            premiumList.clear();

        pCount = 0;
        isFlag = false;

        policyAdaptor.notifyItemRangeRemoved(0, premiumList.size());
        policyAdaptor.notifyDataSetChanged();
        AppUtils.startShimmer(shimmerFrameLayout);

        getMagma();
        getChola();
        getUnited();
        getDigit();
        getRoyal();
        getNewIndia();
        getUniversal();
        getIffco();
        getHdfc();
        getReliance();
        getBharti();
        getBajaj();
        getFuture();
        getTata();
        getShriram();
        getAcko();
        getOriental();
        getLiberty();
        getIcici();
        getSbi();
    }


    @SuppressLint("NonConstantResourceId")
    private void onBikeFilter() {
        final Dialog dialog = new Dialog(mContext, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_filter);
        dialog.setCancelable(false);
        // Set dialog title
//        dialog.setTitle("Set Filter");
        Button closeButton = dialog.findViewById(R.id.closeDialog);
        RadioGroup rgCoverFor = dialog.findViewById(R.id.rgCoverFor);
        final TextView txtCurrent = dialog.findViewById(R.id.lblNCB);
        final RelativeLayout ccV = dialog.findViewById(R.id.ccV);
        final RelativeLayout rlCover = dialog.findViewById(R.id.rlCover);
        SwitchCompat swZD = dialog.findViewById(R.id.swZD);
        SwitchCompat swRsa = dialog.findViewById(R.id.swRsa);
        SwitchCompat swUnnamed = dialog.findViewById(R.id.swUnnamed);
        SwitchCompat swPaid = dialog.findViewById(R.id.swPaid);
        SwitchCompat swTppd = dialog.findViewById(R.id.swTppd);
        LinearLayout llPD = dialog.findViewById(R.id.llPD);
        Spinner spnUnnamedValue = dialog.findViewById(R.id.spnUnnamedValue);

        Button btnUpdateFilter = dialog.findViewById(R.id.btnUpdateFilter);
        if (tpOnly.equals("1")) {
            swZD.setVisibility(View.GONE);
            swRsa.setVisibility(View.GONE);
            ccV.setVisibility(View.GONE);
            swUnnamed.setVisibility(View.VISIBLE);
            swPaid.setVisibility(View.VISIBLE);
        } else if (tpOnly.equals("2")) {
            swZD.setVisibility(View.VISIBLE);
            swRsa.setVisibility(View.VISIBLE);
            swUnnamed.setVisibility(View.GONE);
            swPaid.setVisibility(View.GONE);
            llPD.setVisibility(View.GONE);
        } else {
            swZD.setVisibility(View.VISIBLE);
            swRsa.setVisibility(View.VISIBLE);
            swUnnamed.setVisibility(View.VISIBLE);
            swPaid.setVisibility(View.VISIBLE);
            llPD.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(currentNcb))
            txtCurrent.setText("Update NCB (" + currentNcb + ")");
        if (!TextUtils.isEmpty(newVehicle))
            if (newVehicle.equalsIgnoreCase("new_gadi")) {
                ccV.setVisibility(View.GONE);
                rlCover.setVisibility(View.GONE);
            }

        if (!TextUtils.isEmpty(previousPolicyType)) {
            if ((!previousPolicyType.equalsIgnoreCase("Comprehensive"))
                    || totalExpDay > 90
                    || claimExp.equals("1") || policyExpired || (!isNcb)) {

                ccV.setVisibility(View.GONE);
            }
        }

        if (!TextUtils.isEmpty(prePolicy))
            if (prePolicy.equals("1")) {
                ccV.setVisibility(View.GONE);
            }
        if (TextUtils.isEmpty(currentNcb))
            ccV.setVisibility(View.GONE);

        if (coverFor.equalsIgnoreCase("3"))
            rgCoverFor.check(R.id.c3);
        if (coverFor.equalsIgnoreCase("2"))
            rgCoverFor.check(R.id.c2);
        if (coverFor.equals("1"))
            rgCoverFor.check(R.id.c1);


        if (zeroDept.equals("1"))
            swZD.setChecked(true);
        if (rsa.equals("1"))
            swRsa.setChecked(true);

        if (unnamed.equals("1")) {
            swUnnamed.setChecked(true);
            spnUnnamedValue.setVisibility(View.VISIBLE);
        }
        if (paidDriver.equals("1"))
            swPaid.setChecked(true);

        if (tppd.equals("1"))
            swTppd.setChecked(true);

        RadioButton claimYes = dialog.findViewById(R.id.cYes);
        RadioButton claimNo = dialog.findViewById(R.id.cNo);
        if (claimExp.equals("1")) {
            claimYes.setChecked(true);
            claimNo.setChecked(false);
            dialog.findViewById(R.id.cc).setVisibility(View.GONE);
        } else {
            claimYes.setChecked(false);
            claimNo.setChecked(true);
        }
        final Spinner spnUpdatedNCB = dialog.findViewById(R.id.spnNCB);
        if (!TextUtils.isEmpty(ncbOld))
            if (ncbOld.equals("0"))
                spnUpdatedNCB.setSelection(0);
            else if (ncbOld.equalsIgnoreCase("20"))
                spnUpdatedNCB.setSelection(1);
            else if (ncbOld.equalsIgnoreCase("25"))
                spnUpdatedNCB.setSelection(2);
            else if (ncbOld.equalsIgnoreCase("35"))
                spnUpdatedNCB.setSelection(3);
            else if (ncbOld.equalsIgnoreCase("45"))
                spnUpdatedNCB.setSelection(4);
            else if (ncbOld.equalsIgnoreCase("50"))
                spnUpdatedNCB.setSelection(5);

        claimYes.setOnClickListener(view -> {
            claimExp = "1";
            dialog.findViewById(R.id.cc).setVisibility(View.GONE);
        });
        claimNo.setOnClickListener(view -> {
            dialog.findViewById(R.id.cc).setVisibility(View.VISIBLE);
            claimExp = "0";
        });

        swZD.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                zeroDept = "1";
            else zeroDept = "0";
        });
        swRsa.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                rsa = "1";
            else rsa = "0";
        });

        swPaid.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                paidDriver = "1";
            else paidDriver = "0";
        });

        swUnnamed.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                unnamed = "1";
                spnUnnamedValue.setVisibility(View.VISIBLE);
            } else {
                unnamed = "0";
                spnUnnamedValue.setVisibility(View.GONE);
            }
        });
        swTppd.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                tppd = "1";
            else tppd = "0";
        });

        rgCoverFor.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            switch (checkedId) {
                case R.id.c1:
                    coverFor = "1";
                    break;
                case R.id.c2:
                    coverFor = "2";
                    break;
                case R.id.c3:
                    coverFor = "3";
                    break;
            }
        });

        btnUpdateFilter.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(paidDriver) && paidDriver.equals("1")) {
                unnamedValue = spnUnnamedValue.getSelectedItem().toString();
            }
            String ncb = spnUpdatedNCB.getSelectedItem().toString();
            if (ncb.equals("20%")) {
                ncbOld = "20";
                currentNcb = "25";
                txtCurrent.setText(currentNcb);
            }
            if (ncb.equals("25%")) {
                ncbOld = "25";
                currentNcb = "35";
                txtCurrent.setText(currentNcb);
            }

            if (ncb.equals("35%")) {
                ncbOld = "35";
                currentNcb = "45";
                txtCurrent.setText(currentNcb);
            }

            if (ncb.equals("45%")) {
                ncbOld = "45";
                currentNcb = "50";
                txtCurrent.setText(currentNcb);
            }
            if (ncb.equals("50%")) {
                ncbOld = "50";
                currentNcb = "50";
                txtCurrent.setText(currentNcb);
            }
            if (ncb.equals("0%")) {
                ncbOld = "0";
                currentNcb = "20";
                txtCurrent.setText(currentNcb);
            }

            if (tpOnly.equalsIgnoreCase("2")) {
                if (isValidSaOd())
                    applyFilter();
            } else applyFilter();
            dialog.dismiss();
        });

        closeButton.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    public void onPACover(View view) {
        try {
            if (paSwitch.isChecked())
                paCover = "1";
            else {
                paCover = "0";
                BottomSheetDialog dialog = new BottomSheetDialog(mContext);
                dialog.setContentView(R.layout.layout_pa_cover);

                TextView txtShare = dialog.findViewById(R.id.txtShare);
                txtShare.setOnClickListener(view1 -> dialog.dismiss());
                dialog.show();
            }

            if (tpOnly.equalsIgnoreCase("2")) {
                if (isValidSaOd())
                    applyFilter();
            } else applyFilter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filter, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_filter:
                onBikeFilter();
                return true;
            case R.id.action_edit:
                onVehicleEditClick();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onIDV(View view) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(mContext);
        // Include dialog.xml file
        dialog.setContentView(R.layout.custom_dialog);
        // Set dialog title
        dialog.setTitle("Update IDV");
        ImageButton closeButton = dialog.findViewById(R.id.closeDialog);
        final Button updateIDV = dialog.findViewById(R.id.btnUpdateIdv);
        final EditText edtIDV = dialog.findViewById(R.id.edtIdv);
        final TextView txtMinIDV = dialog.findViewById(R.id.minIdv);
        final TextView txtMaxIDV = dialog.findViewById(R.id.maxIdv);

        if (idvList.size() > 0) {
            Collections.sort(idvList, Integer::compareTo);

            int idvMin = idvList.get(0);
            int idvMax = idvList.get(idvList.size() - 1);

            if (idvMin > idvMax) {
                minIdv = String.valueOf(idvMax);
                maxIdv = String.valueOf(idvMin);
            } else {
                minIdv = String.valueOf(idvMin);
                maxIdv = String.valueOf(idvMax);
            }
        }
        txtMaxIDV.setText(maxIdv);
        txtMinIDV.setText(minIdv);
        edtIDV.setText(idv);

        closeButton.setOnClickListener(view12 -> dialog.dismiss());
        updateIDV.setOnClickListener(view1 -> {
            if (maxIdv != null && minIdv != null) {
                idv = edtIDV.getText().toString();
                if (Integer.parseInt(maxIdv) >= Integer.parseInt(idv) &&
                        Integer.parseInt(minIdv) <= Integer.parseInt(idv)) {
                    txtIDV.setText(idv);
                    updateIDV.setText(idv);

                    if (tpOnly.equalsIgnoreCase("2")) {
                        if (isValidSaOd())
                            applyFilter();
                    } else applyFilter();
                    dialog.dismiss();
                    AppUtils.checkSoftKeyboard(this);

                } else {
                    edtIDV.setError("IDV must be in minimum and maximum");
                }
            }
        });
        dialog.show();
    }

    public void onEditSaOd(View view) {
        findViewById(R.id.llSAOD).setVisibility(View.VISIBLE);
    }

    public void getPreInsurer() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getInsurer(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void onHideSaOdClick(View view) {
        TextView tv, update;
        update = findViewById(R.id.btnUpdate);
        tv = (TextView) view;

        if (tv == update) {
            tpOnly = "2";
            policyType = "Standalone OD";
            if (isValidSaOd()) {
                applyFilter();
                findViewById(R.id.llSAOD).setVisibility(View.GONE);
            }
        } else findViewById(R.id.llSAOD).setVisibility(View.GONE);

    }

    private void initPreInsurer() {
        for (int i = 0; i < insurerList.size(); i++) {
            String modelName = insurerList.get(i).getInsurer();
            String modelId = insurerList.get(i).getId();
            piList.add(new String2WithTag(modelName, modelId));
        }

        ArrayAdapter<String2WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, piList);
        spnTPPreInsurer.setAdapter(adapter);

        spnTPPreInsurer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                insurerId = stringWithTag.string;
                txtTPPreInsurer.setText(insurerId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean isValidSaOd() {
        if (spnTPPreInsurer.getSelectedItem() != null)
            previousInsurer = spnTPPreInsurer.getSelectedItem().toString();
        previousPolicyNo = edtPolicyNumber.getText().toString();
        tpPolicyExpDate = txtPolicyExpDate.getText().toString();

        if (TextUtils.isEmpty(previousInsurer)) {
            Toast.makeText(mContext, "Select Previous Insurer", Toast.LENGTH_SHORT).show();
            findViewById(R.id.llSAOD).setVisibility(View.VISIBLE);
            return false;
        }
        if (TextUtils.isEmpty(previousPolicyNo)) {
            findViewById(R.id.llSAOD).setVisibility(View.VISIBLE);
            edtPolicyNumber.setError("Field can not be empty");
            return false;
        }

        return true;
    }

    public void onDatePicker(View view) {

        Calendar newCalendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                R.style.datepicker, (view1, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);

            txtPolicyExpDate.setText(dateFormatter.format(newDate.getTime()));
            txtTPExpDate.setText(dateFormatter.format(newDate.getTime()));
            txtPolicyExpDate.setError(null);
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(minDate);
        datePickerDialog.getDatePicker().setMaxDate(maxDate);
        datePickerDialog.show();
    }

    public void onShareClick(View view) {

        final Dialog dialog = new Dialog(mContext, android.R.style.Theme_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_share);
        dialog.setTitle("Share Link");

        // set the custom dialog components - text, image and button
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

        close.setOnClickListener(view1 -> {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
            dialog.dismiss();
        });


        dialog.show();
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

    public void applyFilter() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                ApiManager.getInstance().updateFilter(mContext, paCover, zeroDept, rsa, unnamed,
                        unnamedValue, paidDriver, coverFor, tppd, ownedBy, idv, ownerChange,
                        claimExp, ncbOld, cover, tpOnly, policyExpDate, quotationId,
                        previousInsurer, previousPolicyNo, tpPolicyExpDate, make, modelName,
                        fuelType, variantName);
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
                try {
                    UserManager.getInstance().proposalCompany(mContext, companyName, finalPremium,
                            policyStartDate, policyEndDate, idv, tenure, "0", netPremium, gst,
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

    public void getAcko() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);
            try {
                ApiManager.getInstance().getPremium(mContext, quotationId, "tw",
                        "acko", idv);
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
                ApiManager.getInstance().getPremium(mContext, quotationId, "tw",
                        "liberty", idv);
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
                ApiManager.getInstance().getPremium(mContext, quotationId, "tw",
                        "icici", idv);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getUnited() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);
            try {
                ApiManager.getInstance().getPremium(mContext, quotationId, "tw",
                        "united", idv);

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
                ApiManager.getInstance().getPremium(mContext, quotationId, "tw",
                        "newindia", idv);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getSbi() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);
            try {
                ApiManager.getInstance().getPremium(mContext, quotationId, "tw",
                        "sbi", idv);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getChola() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);
            try {
                ApiManager.getInstance().getPremium(mContext, quotationId, "tw",
                        "Cholamandalam", idv);

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
                ApiManager.getInstance().getPremium(mContext, quotationId, "tw",
                        "magma", idv);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getDigit() {
        if (AppUtils.isOnline(mContext)) {

            progressBar.setVisibility(View.VISIBLE);
            try {
                ApiManager.getInstance().getPremium(mContext, quotationId, "tw",
                        "digit", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getRoyal() {
        if (AppUtils.isOnline(mContext)) {

            progressBar.setVisibility(View.VISIBLE);
            try {
                ApiManager.getInstance().getPremium(mContext, quotationId, "tw",
                        "royalSundaram", idv);
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
                ApiManager.getInstance().getPremium(mContext, quotationId, "tw",
                        "universal", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getReliance() {
        if (AppUtils.isOnline(mContext)) {

            progressBar.setVisibility(View.VISIBLE);
            try {
                ApiManager.getInstance().getPremium(mContext, quotationId, "tw",
                        "reliance", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getIffco() {
        if (AppUtils.isOnline(mContext)) {

            progressBar.setVisibility(View.VISIBLE);
            try {
                ApiManager.getInstance().getPremium(mContext, quotationId, "tw",
                        "iffco", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getOriental() {
        if (AppUtils.isOnline(mContext)) {

            progressBar.setVisibility(View.VISIBLE);
            try {
                ApiManager.getInstance().getPremium(mContext, quotationId, "tw",
                        "oriental", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getHdfc() {
        if (AppUtils.isOnline(mContext)) {

            progressBar.setVisibility(View.VISIBLE);
            try {
                ApiManager.getInstance().getPremium(mContext, quotationId, "tw",
                        "hdfc", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getFuture() {
        if (AppUtils.isOnline(mContext)) {

            progressBar.setVisibility(View.VISIBLE);
            try {
                ApiManager.getInstance().getPremium(mContext, quotationId, "tw",
                        "future", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getShriram() {
        if (AppUtils.isOnline(mContext)) {

            progressBar.setVisibility(View.VISIBLE);
            try {
                ApiManager.getInstance().getPremium(mContext, quotationId, "tw",
                        "shriram", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getBajaj() {
        if (AppUtils.isOnline(mContext)) {

            try {
                ApiManager.getInstance().getPremium(mContext, quotationId, "tw",
                        "bajaj", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getTata() {
        if (AppUtils.isOnline(mContext)) {

            try {
                ApiManager.getInstance().getPremium(mContext, quotationId, "tw",
                        "tata", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getBharti() {
        if (AppUtils.isOnline(mContext)) {

            try {
                ApiManager.getInstance().getPremium(mContext, quotationId, "tw",
                        "bharti", idv);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void OnPremiumButtonClick(int position) {
        setProposal(position);
    }

    private void setProposal(int position) {
        float tppdAmount = 0, tpAmount = 0;
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

        String policyType = premiumList.get(position).getPolicyType();
        if (!TextUtils.isEmpty(policyType)) {
            if (policyType.contains("Third"))
                tpOnly = "1";

            else if (policyType.equalsIgnoreCase("Comprehensive"))
                tpOnly = "0";
            else if (policyType.contains("od")) tpOnly = "2";
        }

        float legalPD = 0, unnamedAmt = 0;
        imgPath = premiumList.get(position).getLogo();
        finalPremium = Float.valueOf(premiumList.get(position).getTotalPremium());
        idv = premiumList.get(position).getIdv();
        netPremium = Float.valueOf(premiumList.get(position).getNet());
        flag = premiumList.get(position).getTataFlag();
        policyStartDate = premiumList.get(position).getStartDate();
        policyEndDate = premiumList.get(position).getEndDate();
        if (!TextUtils.isEmpty(premiumList.get(position).getUnnamedPerson()))
            unnamedAmt = Float.parseFloat(premiumList.get(position).getUnnamedPerson());
        if (!TextUtils.isEmpty(premiumList.get(position).getPaidDriver()))
            legalPD = Float.parseFloat(premiumList.get(position).getPaidDriver());

        gst = Float.valueOf(premiumList.get(position).getGst());
        od = Float.valueOf(premiumList.get(position).getOd());

        if (!TextUtils.isEmpty(premiumList.get(position).getTp()))
            tpAmount = Float.parseFloat(premiumList.get(position).getTp());

        if (!TextUtils.isEmpty(premiumList.get(position).getTppd()))
            tppdAmount = Float.parseFloat(premiumList.get(position).getTppd());

        paCoverValue = premiumList.get(position).getPa();

        if (tpOnly.equals("0")) {
            float netPA = 0, netOd;

            if (!TextUtils.isEmpty(paCoverValue))
                netPA = Float.parseFloat(paCoverValue);

            if (tpOnly.equalsIgnoreCase("2"))
                tp = (float) 0;
            else {
                if (companyName.equalsIgnoreCase("bajaj"))
                    tpAmount = tpAmount + tppdAmount;
                tp = tpAmount - tppdAmount + unnamedAmt + legalPD;
            }
            netOd = netPremium - (tp + netPA);
            od = netOd;

        } else if (tpOnly.equals("1")) {
            od = 0f;
            if (companyName.equalsIgnoreCase("bajaj"))
                tpAmount = tpAmount + tppdAmount;
            tp = tpAmount - tppdAmount + unnamedAmt + legalPD;

        } else if (tpOnly.equalsIgnoreCase("2")) {
            tp = 0f;
            od = netPremium;
        }

        if (!TextUtils.isEmpty(newVehicle)) {
            if (newVehicle.equalsIgnoreCase("new_gadi")) {
                currentNcb = "NA";
            }
        }
        if (paCoverValue != null) {
            if (!TextUtils.isEmpty(paCoverValue)) {
                isPaCover = paCoverValue.equals("0");
            } else isPaCover = true;
        } else isPaCover = true;

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
    }

    @Override
    public void OnBreakUpClicked(int position) {

        if (premiumList.size() > 0) {
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
            final TextView txtTppd = dialog.findViewById(R.id.txtTppd);
            final TextView txtPD = dialog.findViewById(R.id.txtPD);
            final TextView txtUP = dialog.findViewById(R.id.txtUP);

            final TextView lblFutureDis = dialog.findViewById(R.id.lblFutureDis);
            final TextView txtFutureDis = dialog.findViewById(R.id.txtFutureDis);
            final TextView txtQuote = dialog.findViewById(R.id.txtQuote);

            final ImageView btnCancel = dialog.findViewById(R.id.btnCancel);
            final Button btnShare = dialog.findViewById(R.id.btnShare);
            final Button btnBuy = dialog.findViewById(R.id.btnBuy);
            final TextView txtRsa = dialog.findViewById(R.id.txtRsa);
            final RelativeLayout rlRsa = dialog.findViewById(R.id.rlRsa);

            final RelativeLayout rlNcb = dialog.findViewById(R.id.rlNcb);
            final ImageView imgInsureLogo = dialog.findViewById(R.id.imgInsureLogo);
            final RelativeLayout rlBasicOd = dialog.findViewById(R.id.rlBasicOd);
            final RelativeLayout rlTp = dialog.findViewById(R.id.rlTp);
            final RelativeLayout rlPA = dialog.findViewById(R.id.rlPA);
            final RelativeLayout rlIDV = dialog.findViewById(R.id.rlIDV);
            final RelativeLayout rlTppd = dialog.findViewById(R.id.rlTppd);
            final TextView txtZD = dialog.findViewById(R.id.txtZD);
            final RelativeLayout rlZD = dialog.findViewById(R.id.rlZD);
            final RelativeLayout rlPD = dialog.findViewById(R.id.rlPD);
            final RelativeLayout rlUP = dialog.findViewById(R.id.rlUP);

            if (!TextUtils.isEmpty(registrationNumber))
                txtRegNumber.setText(registrationNumber);
            else txtRegNumber.setVisibility(View.GONE);

            txtQuote.setText(quotationId);
            txtVehicle.setText(vehicleName);
            if (!TextUtils.isEmpty(currentNcb))
                txtNCb.setText(currentNcb);
            else rlNcb.setVisibility(View.GONE);

            String policyType = premiumList.get(position).getPolicyType();
            String cName = premiumList.get(position).getCompany();

            if (cName.equals("future")
                    && !TextUtils.isEmpty(premiumList.get(position).getTataFlag())) {

                lblFutureDis.setVisibility(View.VISIBLE);
                txtFutureDis.setVisibility(View.VISIBLE);

                txtFutureDis.setText(premiumList.get(position).getTataFlag());
            }
            if (policyType.contains("Third"))
                rlBasicOd.setVisibility(View.GONE);
            else if (policyType.contains("Saod")) {
                rlTp.setVisibility(View.GONE);
                rlIDV.setVisibility(View.VISIBLE);
            } else rlIDV.setVisibility(View.VISIBLE);

            paCoverValue = premiumList.get(position).getPa();
            if (paCoverValue != null) {
                if (!TextUtils.isEmpty(paCoverValue)) {
                    if (paCoverValue.equals("0"))
                        rlPA.setVisibility(View.GONE);
                    else rlPA.setVisibility(View.VISIBLE);
                } else rlPA.setVisibility(View.GONE);
            } else rlPA.setVisibility(View.GONE);

            String za = premiumList.get(position).getZeroDept();
            if (!TextUtils.isEmpty(za) && !za.equals("0")) {
                rlZD.setVisibility(View.VISIBLE);
                txtZD.setText(za);
            }

            String rsa = premiumList.get(position).getRsa();
            if (!TextUtils.isEmpty(rsa) && !rsa.equals("0")) {
                rlRsa.setVisibility(View.VISIBLE);
                txtRsa.setText(rsa);
            }

            Glide.with(mContext)
                    .load(premiumList.get(position).getLogo())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .animate(android.R.anim.fade_in)
                    .into(imgInsureLogo);

            txtBasicOD.setText(premiumList.get(position).getOd());
            txtTP.setText(premiumList.get(position).getTp());

            String paid = premiumList.get(position).getPaidDriver();
            if (!TextUtils.isEmpty(paid) && !paid.equals("0")) {
                rlPD.setVisibility(View.VISIBLE);
                txtPD.setText(paid);
            } else {
                rlPD.setVisibility(View.GONE);
            }

            String unnamed = premiumList.get(position).getUnnamedPerson();
            if (!TextUtils.isEmpty(unnamed) && !unnamed.equals("0")) {
                rlUP.setVisibility(View.VISIBLE);
                txtUP.setText(unnamed);
            } else {
                rlUP.setVisibility(View.GONE);
            }

            String tppd = premiumList.get(position).getTppd();
            if (!TextUtils.isEmpty(tppd) && !tppd.equals("0")) {
                rlTppd.setVisibility(View.VISIBLE);
                txtTppd.setText(tppd);

                if (premiumList.get(position).getCompany().equals("bajaj")) {
                    float bajajTppd = Float.parseFloat(premiumList.get(position).getTppd());
                    float bajajTp = Float.parseFloat(premiumList.get(position).getTp());
                    bajajTp = bajajTp + bajajTppd;
                    txtTP.setText(String.format("%s", bajajTp));
                }

            }
            txtNetPremium.setText(premiumList.get(position).getNet());
            txtGST.setText(premiumList.get(position).getGst());
            txtFinalPremium.setText(premiumList.get(position).getTotalPremium());

            String ncb = premiumList.get(position).getNcb();
            if (!TextUtils.isEmpty(ncb) && !ncb.equals("0"))
                txtNcbTotal.setText(ncb);
            else rlNcb.setVisibility(View.GONE);

            txtIdvValue.setText(premiumList.get(position).getIdv());

            if (TextUtils.isEmpty(premiumList.get(position).getPa())
                    || premiumList.get(position).getPa().equals("0"))
                rlPA.setVisibility(View.GONE);
            else
                txtPA.setText(premiumList.get(position).getPa());

            txtIdvRange.setText("(" + premiumList.get(position).getIdvMin()
                    + " - " + premiumList.get(position).getIdvMax() + ") ");

            btnCancel.setOnClickListener(v -> dialog.dismiss());
            btnBuy.setOnClickListener(v -> {
                setProposal(position);
                dialog.dismiss();
            });
            btnShare.setOnClickListener(v -> {
                Window window = dialog.getWindow();
                View decorView;
                if (window != null) {
                    decorView = window.getDecorView();
                    takeScreenShot(decorView);
                }

            });
            dialog.show();
        }
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
    public void onResponse(Object response) {
        try {
            if (response instanceof ManufactureList) {
                ManufactureList mResponse = (ManufactureList) response;
                if (mResponse.getStatus().equals(1)) {
                    if (manufactureList.size() > 0) {
                        manuList.clear();
                        manufactureList.clear();
                    }
                    manufactureList = mResponse.getManufacture();
                    if (manufactureList.size() > 0) {
                        initManufacture();
                    }
                }
            }
            if (response instanceof ModelList) {
                ModelList mResponse = (ModelList) response;
                if (mResponse.getStatus().equals(1)) {
                    if (modelList.size() > 0) {
                        moList.clear();
                        modelList.clear();
                    }
                    modelList = mResponse.getModel();
                    if (modelList.size() > 0) {
                        initModel();
                    }
                }
            }

            if (response instanceof Fuel) {
                Fuel mResponse = (Fuel) response;
                if (mResponse.getStatus().equals(1)) {
                    if (fuelList.size() > 0)
                        fuelList.clear();

                    fuelList = mResponse.getFuelType();
                    if (fuelList.size() > 0) {
                        initFuel();
                    }
                }
            }

            if (response instanceof VariantList) {
                VariantList mResponse = (VariantList) response;
                if (mResponse.getVariant() != null) {

                    variantList.clear();
                    vList.clear();
                    variantList = mResponse.getVariant();

                } else {
                    variantList.clear();
                    vList.clear();
                }
                initVariant();
            }

            if (response instanceof BasicResponse) {
                BasicResponse fResponse = (BasicResponse) response;
                if (fResponse.getStatus() == 1) {
                    Toast.makeText(mContext, "" + fResponse.getMsg(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            if (response instanceof InsurerList) {
                InsurerList mResponse = (InsurerList) response;
                if (mResponse.getStatus().equals(1)) {
                    if (insurerList.size() > 0) {
                        insurerList.clear();
                        piList.clear();
                    }
                    insurerList = mResponse.getInsurer();
                    if (insurerList.size() > 0)
                        initPreInsurer();
                }
            }

            if (response instanceof ProposalCompany) {
                ProposalCompany company = (ProposalCompany) response;
                if (company.getSuccess().equals("1")) {
                    Intent intent = new Intent(mContext, OwnerDetailActivity.class);

                    mBundle.putString(AppUtils.IDV, idv);
                    mBundle.putString(AppUtils.GST, String.valueOf(gst));
                    mBundle.putString(AppUtils.NET_PREMIUM, String.valueOf(netPremium));
                    mBundle.putString(AppUtils.TOTAL_PREMIUM, String.valueOf(finalPremium));
                    mBundle.putString(AppUtils.NCB, String.valueOf(currentNcb));
                    mBundle.putString(AppUtils.POLICY_TYPE, policyType);
                    mBundle.putString(AppUtils.VEHICLE_FULL, vehicleName);
                    mBundle.putString(AppUtils.ADDON_COVER, zeroDept);
                    mBundle.putString(AppUtils.IMG_PATH, imgPath);
                    mBundle.putString(AppUtils.COMPANY_NAME, companyName);
                    mBundle.putString(AppUtils.POLICY_START_DATE, policyStartDate);
                    mBundle.putString(AppUtils.POLICY_END_DATE, policyEndDate);
                    mBundle.putBoolean(AppUtils.IS_PA_COVER, isPaCover);

                    intent.putExtras(mBundle);
                    startActivity(intent);
                }
                progressDialog.dismiss();
            }

            if (response instanceof Filter) {
                Filter fResponse = (Filter) response;
                if (fResponse.getSuccess().equals("1")) {
                    String query = fResponse.getQuery();
                    if (!TextUtils.isEmpty(query))
                        AppUtils.setTokenQuote(query);
                    progressDialog.dismiss();
                    recallAll();
                }
            }
            if (response instanceof AddonCover) {
                AddonCover fResponse = (AddonCover) response;
                if (fResponse.getStatus() == 1) {

                    if (!TextUtils.isEmpty(fResponse.getPaCover()))
                        if (fResponse.getPaCover().equals("1")) {
                            paSwitch.setChecked(true);
                            paCover = "1";
                        } else {
                            paCover = "0";
                            paSwitch.setChecked(false);
                        }

                    if (!TextUtils.isEmpty(fResponse.getZeroDept()))
                        if (fResponse.getZeroDept().equals("1")) {
                            zeroDept = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getRoadSideAssistance()))
                        if (fResponse.getRoadSideAssistance().equals("1")) {
                            rsa = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getPacoverforUnnamedPerson()))
                        if (fResponse.getPacoverforUnnamedPerson().equals("1")) {
                            unnamed = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getLegalLiabilityPaidDriver()))
                        if (fResponse.getLegalLiabilityPaidDriver().equals("1")) {
                            paidDriver = "1";
                        }
                }
            }

            if (response instanceof PremiumQuote) {
                PremiumQuote dResponse = (PremiumQuote) response;

                if (dResponse.getCompany().equalsIgnoreCase("iffco")) {
                    pCount++;
                    if (pCount > 15)
                        isFlag = true;
                    for (int i = 0; i < premiumList.size(); i++) {
                        if (premiumList.get(i).getCompany().equalsIgnoreCase("iffco")) {
                            premiumList.remove(i);
                            break;
                        }
                    }
                    if (dResponse.getStatus().equals("1")) {
                        if (!dResponse.getNet().equals("0")) {
                            if (!TextUtils.isEmpty(dResponse.getNet())) {
                                PremiumObjTw obj = new PremiumObjTw();

                                obj.setLogo(dResponse.getLogo());
                                obj.setNet(dResponse.getNet());
                                obj.setIdv(dResponse.getIdv());
                                obj.setPolicyType(dResponse.getPolicyType());
                                obj.setTenure(dResponse.getTenure());
                                obj.setTppd(dResponse.getTppd());
                                obj.setCompany(dResponse.getCompany());
                                obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                obj.setTotalPremium(dResponse.getTotalPremium());
                                obj.setEndDate(dResponse.getEndDate());
                                obj.setStartDate(dResponse.getStartDate());
                                obj.setNcb(dResponse.getNcb());
                                obj.setCurrentNcb(dResponse.getCurrentNcb());
                                obj.setPa(dResponse.getPa());
                                obj.setPaidDriver(dResponse.getPaidDriver());
                                obj.setUnnamedPerson(dResponse.getUnNamedPA());

                                obj.setTp(dResponse.getTp());
                                obj.setOd(dResponse.getOd());
                                obj.setGst(dResponse.getGst());
                                obj.setTataFlag(dResponse.getTataFlag());
                                obj.setZeroDept(dResponse.getZeroDept());
                                obj.setRsa(dResponse.getRoadSideAssistance());
                                obj.setIdvMin(dResponse.getIdvMin());
                                obj.setIdvMax(dResponse.getIdvMax());

                                double min = dResponse.getIdvMin();
                                double max = dResponse.getIdvMax();

                                if (min > 0)
                                    idvList.add((int) Math.floor(min + 0.5d));
                                if (max > 0)
                                    idvList.add((int) Math.floor(max + 0.5d));
                                String mIdv = dResponse.getIdv();
                                if (TextUtils.isEmpty(idv) && (!TextUtils.isEmpty(mIdv))
                                        && (!mIdv.equals("1")) && (!mIdv.equals("0"))
                                        && !tpOnly.equals("1")) {
                                    txtIDV.setText(mIdv);
                                    idv = mIdv;
                                }
                                premiumList.add(obj);
                            }
                        }
                    }
                    if (isFlag)
                        progressBar.setVisibility(View.GONE);
                }

                if (dResponse.getCompany().equalsIgnoreCase("digit")) {
                    pCount++;
                    if (pCount > 15)
                        isFlag = true;
                    for (int i = 0; i < premiumList.size(); i++) {
                        if (premiumList.get(i).getCompany().equalsIgnoreCase("digit")) {
                            premiumList.remove(i);
                            break;
                        }
                    }
                    if (dResponse.getStatus().equals("1")) {
                        if (!dResponse.getNet().equals("0")) {
                            if (!TextUtils.isEmpty(dResponse.getNet())) {
                                PremiumObjTw obj = new PremiumObjTw();

                                obj.setLogo(dResponse.getLogo());
                                obj.setNet(dResponse.getNet());
                                obj.setIdv(dResponse.getIdv());
                                obj.setPolicyType(dResponse.getPolicyType());
                                obj.setTenure(dResponse.getTenure());
                                obj.setTppd(dResponse.getTppd());
                                obj.setCompany(dResponse.getCompany());
                                obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                obj.setTotalPremium(dResponse.getTotalPremium());
                                obj.setEndDate(dResponse.getEndDate());
                                obj.setStartDate(dResponse.getStartDate());
                                obj.setNcb(dResponse.getNcb());
                                obj.setCurrentNcb(dResponse.getCurrentNcb());
                                obj.setPa(dResponse.getPa());
                                obj.setPaidDriver(dResponse.getPaidDriver());
                                obj.setUnnamedPerson(dResponse.getUnNamedPA());

                                obj.setTp(dResponse.getTp());
                                obj.setOd(dResponse.getOd());
                                obj.setGst(dResponse.getGst());
                                obj.setTataFlag(dResponse.getTataFlag());
                                obj.setZeroDept(dResponse.getZeroDept());
                                obj.setRsa(dResponse.getRoadSideAssistance());
                                obj.setIdvMin(dResponse.getIdvMin());
                                obj.setIdvMax(dResponse.getIdvMax());

                                double min = dResponse.getIdvMin();
                                double max = dResponse.getIdvMax();

                                if (min > 0)
                                    idvList.add((int) Math.floor(min + 0.5d));
                                if (max > 0)
                                    idvList.add((int) Math.floor(max + 0.5d));

                                String mIdv = dResponse.getIdv();
                                if (TextUtils.isEmpty(idv) && (!TextUtils.isEmpty(mIdv))
                                        && (!mIdv.equals("1")) && (!mIdv.equals("0"))
                                        && !tpOnly.equals("1")) {
                                    txtIDV.setText(mIdv);
                                    idv = mIdv;
                                }
                                premiumList.add(obj);
                            }
                        }
                    }
                    if (isFlag)
                        progressBar.setVisibility(View.GONE);
                }

                //Royal
                if (dResponse.getCompany().equalsIgnoreCase("royalSundaram")) {
                    pCount++;
                    if (pCount > 15)
                        isFlag = true;
                    for (int i = 0; i < premiumList.size(); i++) {
                        if (premiumList.get(i).getCompany().equalsIgnoreCase("royalSundaram")) {
                            premiumList.remove(i);
                            break;
                        }
                    }
                    if (dResponse.getStatus().equals("1")) {
                        if (!dResponse.getNet().equals("0")) {
                            if (!TextUtils.isEmpty(dResponse.getNet())) {
                                PremiumObjTw obj = new PremiumObjTw();

                                obj.setLogo(dResponse.getLogo());
                                obj.setNet(dResponse.getNet());
                                obj.setIdv(dResponse.getIdv());
                                obj.setPolicyType(dResponse.getPolicyType());
                                obj.setTenure(dResponse.getTenure());
                                obj.setTppd(dResponse.getTppd());
                                obj.setCompany(dResponse.getCompany());
                                obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                obj.setTotalPremium(dResponse.getTotalPremium());
                                obj.setEndDate(dResponse.getEndDate());
                                obj.setStartDate(dResponse.getStartDate());
                                obj.setNcb(dResponse.getNcb());
                                obj.setCurrentNcb(dResponse.getCurrentNcb());
                                obj.setPa(dResponse.getPa());
                                obj.setPaidDriver(dResponse.getPaidDriver());
                                obj.setUnnamedPerson(dResponse.getUnNamedPA());

                                obj.setTp(dResponse.getTp());
                                obj.setOd(dResponse.getOd());
                                obj.setGst(dResponse.getGst());
                                obj.setTataFlag(dResponse.getTataFlag());
                                obj.setZeroDept(dResponse.getZeroDept());
                                obj.setRsa(dResponse.getRoadSideAssistance());
                                obj.setIdvMin(dResponse.getIdvMin());
                                obj.setIdvMax(dResponse.getIdvMax());

                                double min = dResponse.getIdvMin();
                                double max = dResponse.getIdvMax();

                                if (min > 0)
                                    idvList.add((int) Math.floor(min + 0.5d));
                                if (max > 0)
                                    idvList.add((int) Math.floor(max + 0.5d));

                                String mIdv = dResponse.getIdv();
                                if (TextUtils.isEmpty(idv) && (!TextUtils.isEmpty(mIdv))
                                        && (!mIdv.equals("1")) && (!mIdv.equals("0"))
                                        && !tpOnly.equals("1")) {
                                    txtIDV.setText(mIdv);
                                    idv = mIdv;
                                }
                                premiumList.add(obj);
                            }
                        }
                    }
                    if (isFlag)
                        progressBar.setVisibility(View.GONE);
                }

                //UNIVERSAL
                if (dResponse.getCompany().equalsIgnoreCase("universal")) {
                    pCount++;
                    if (pCount > 15)
                        isFlag = true;
                    for (int i = 0; i < premiumList.size(); i++) {
                        if (premiumList.get(i).getCompany().equalsIgnoreCase("universal")) {
                            premiumList.remove(i);
                            break;
                        }
                    }
                    if (dResponse.getStatus().equals("1")) {
                        if (!dResponse.getNet().equals("0")) {
                            if (!TextUtils.isEmpty(dResponse.getNet())) {
                                PremiumObjTw obj = new PremiumObjTw();

                                obj.setLogo(dResponse.getLogo());
                                obj.setNet(dResponse.getNet());
                                obj.setIdv(dResponse.getIdv());
                                obj.setPolicyType(dResponse.getPolicyType());
                                obj.setTenure(dResponse.getTenure());
                                obj.setTppd(dResponse.getTppd());
                                obj.setCompany(dResponse.getCompany());
                                obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                obj.setTotalPremium(dResponse.getTotalPremium());
                                obj.setEndDate(dResponse.getEndDate());
                                obj.setStartDate(dResponse.getStartDate());
                                obj.setNcb(dResponse.getNcb());
                                obj.setCurrentNcb(dResponse.getCurrentNcb());
                                obj.setPa(dResponse.getPa());
                                obj.setPaidDriver(dResponse.getPaidDriver());
                                obj.setUnnamedPerson(dResponse.getUnNamedPA());

                                obj.setTp(dResponse.getTp());
                                obj.setOd(dResponse.getOd());
                                obj.setGst(dResponse.getGst());
                                obj.setTataFlag(dResponse.getTataFlag());
                                obj.setZeroDept(dResponse.getZeroDept());
                                obj.setRsa(dResponse.getRoadSideAssistance());
                                obj.setIdvMin(dResponse.getIdvMin());
                                obj.setIdvMax(dResponse.getIdvMax());

                                double min = dResponse.getIdvMin();
                                double max = dResponse.getIdvMax();

                                if (min > 0)
                                    idvList.add((int) Math.floor(min + 0.5d));
                                if (max > 0)
                                    idvList.add((int) Math.floor(max + 0.5d));

                                String mIdv = dResponse.getIdv();
                                if (TextUtils.isEmpty(idv) && (!TextUtils.isEmpty(mIdv))
                                        && (!mIdv.equals("1")) && (!mIdv.equals("0"))
                                        && !tpOnly.equals("1")) {
                                    txtIDV.setText(mIdv);
                                    idv = mIdv;
                                }
                                premiumList.add(obj);
                            }
                        }
                    }
                    if (isFlag)
                        progressBar.setVisibility(View.GONE);
                }

                //UNITED
                if (dResponse.getCompany().equalsIgnoreCase("united")) {
                    pCount++;
                    if (pCount > 15)
                        isFlag = true;
                    for (int i = 0; i < premiumList.size(); i++) {
                        if (premiumList.get(i).getCompany().equalsIgnoreCase("united")) {
                            premiumList.remove(i);
                            break;
                        }
                    }
                    if (dResponse.getStatus().equals("1")) {
                        if (!dResponse.getNet().equals("0")) {
                            if (!TextUtils.isEmpty(dResponse.getNet())) {
                                PremiumObjTw obj = new PremiumObjTw();

                                obj.setLogo(dResponse.getLogo());
                                obj.setNet(dResponse.getNet());
                                obj.setIdv(dResponse.getIdv());
                                obj.setPolicyType(dResponse.getPolicyType());
                                obj.setTenure(dResponse.getTenure());
                                obj.setTppd(dResponse.getTppd());
                                obj.setCompany(dResponse.getCompany());
                                obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                obj.setTotalPremium(dResponse.getTotalPremium());
                                obj.setEndDate(dResponse.getEndDate());
                                obj.setStartDate(dResponse.getStartDate());
                                obj.setNcb(dResponse.getNcb());
                                obj.setCurrentNcb(dResponse.getCurrentNcb());
                                obj.setPa(dResponse.getPa());
                                obj.setPaidDriver(dResponse.getPaidDriver());
                                obj.setUnnamedPerson(dResponse.getUnNamedPA());

                                obj.setTp(dResponse.getTp());
                                obj.setOd(dResponse.getOd());
                                obj.setGst(dResponse.getGst());
                                obj.setTataFlag(dResponse.getTataFlag());
                                obj.setZeroDept(dResponse.getZeroDept());
                                obj.setRsa(dResponse.getRoadSideAssistance());
                                obj.setIdvMin(dResponse.getIdvMin());
                                obj.setIdvMax(dResponse.getIdvMax());

                                double min = dResponse.getIdvMin();
                                double max = dResponse.getIdvMax();

                                if (min > 0)
                                    idvList.add((int) Math.floor(min + 0.5d));
                                if (max > 0)
                                    idvList.add((int) Math.floor(max + 0.5d));

                                String mIdv = dResponse.getIdv();
                                if (TextUtils.isEmpty(idv) && (!TextUtils.isEmpty(mIdv))
                                        && (!mIdv.equals("1")) && (!mIdv.equals("0"))
                                        && !tpOnly.equals("1")) {
                                    txtIDV.setText(mIdv);
                                    idv = mIdv;
                                }
                                premiumList.add(obj);
                            }
                        }
                    }
                    if (isFlag)
                        progressBar.setVisibility(View.GONE);
                }

                //New India
                if (dResponse.getCompany().equalsIgnoreCase("newindia")) {
                    pCount++;
                    if (pCount > 15)
                        isFlag = true;
                    for (int i = 0; i < premiumList.size(); i++) {
                        if (premiumList.get(i).getCompany().equalsIgnoreCase("newindia")) {
                            premiumList.remove(i);
                            break;
                        }
                    }
                    if (dResponse.getStatus().equals("1")) {
                        if (!dResponse.getNet().equals("0")) {
                            if (!TextUtils.isEmpty(dResponse.getNet())) {
                                PremiumObjTw obj = new PremiumObjTw();

                                obj.setLogo(dResponse.getLogo());
                                obj.setNet(dResponse.getNet());
                                obj.setIdv(dResponse.getIdv());
                                obj.setPolicyType(dResponse.getPolicyType());
                                obj.setTenure(dResponse.getTenure());
                                obj.setTppd(dResponse.getTppd());
                                obj.setCompany(dResponse.getCompany());
                                obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                obj.setTotalPremium(dResponse.getTotalPremium());
                                obj.setEndDate(dResponse.getEndDate());
                                obj.setStartDate(dResponse.getStartDate());
                                obj.setNcb(dResponse.getNcb());
                                obj.setCurrentNcb(dResponse.getCurrentNcb());
                                obj.setPa(dResponse.getPa());
                                obj.setPaidDriver(dResponse.getPaidDriver());
                                obj.setUnnamedPerson(dResponse.getUnNamedPA());

                                obj.setTp(dResponse.getTp());
                                obj.setOd(dResponse.getOd());
                                obj.setGst(dResponse.getGst());
                                obj.setTataFlag(dResponse.getTataFlag());
                                obj.setZeroDept(dResponse.getZeroDept());
                                obj.setRsa(dResponse.getRoadSideAssistance());
                                obj.setIdvMin(dResponse.getIdvMin());
                                obj.setIdvMax(dResponse.getIdvMax());

                                double min = dResponse.getIdvMin();
                                double max = dResponse.getIdvMax();

                                if (min > 0)
                                    idvList.add((int) Math.floor(min + 0.5d));
                                if (max > 0)
                                    idvList.add((int) Math.floor(max + 0.5d));

                                String mIdv = dResponse.getIdv();
                                if (TextUtils.isEmpty(idv) && (!TextUtils.isEmpty(mIdv))
                                        && (!mIdv.equals("1")) && (!mIdv.equals("0"))
                                        && !tpOnly.equals("1")) {
                                    txtIDV.setText(mIdv);
                                    idv = mIdv;
                                }
                                premiumList.add(obj);
                            }
                        }
                    }
                    if (isFlag)
                        progressBar.setVisibility(View.GONE);
                }

                if (dResponse.getCompany().equalsIgnoreCase("acko")) {
                    pCount++;
                    if (pCount > 15)
                        isFlag = true;
                    for (int i = 0; i < premiumList.size(); i++) {
                        if (premiumList.get(i).getCompany().equalsIgnoreCase("acko")) {
                            premiumList.remove(i);
                            break;
                        }
                    }
                    if (dResponse.getStatus().equals("1")) {
                        if (!dResponse.getNet().equals("0")) {
                            if (!TextUtils.isEmpty(dResponse.getNet())) {
                                PremiumObjTw obj = new PremiumObjTw();

                                obj.setLogo(dResponse.getLogo());
                                obj.setNet(dResponse.getNet());
                                obj.setIdv(dResponse.getIdv());
                                obj.setPolicyType(dResponse.getPolicyType());
                                obj.setTenure(dResponse.getTenure());
                                obj.setTppd(dResponse.getTppd());
                                obj.setCompany(dResponse.getCompany());
                                obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                obj.setTotalPremium(dResponse.getTotalPremium());
                                obj.setEndDate(dResponse.getEndDate());
                                obj.setStartDate(dResponse.getStartDate());
                                obj.setNcb(dResponse.getNcb());
                                obj.setCurrentNcb(dResponse.getCurrentNcb());
                                obj.setPa(dResponse.getPa());
                                obj.setPaidDriver(dResponse.getPaidDriver());
                                obj.setUnnamedPerson(dResponse.getUnNamedPA());

                                obj.setTp(dResponse.getTp());
                                obj.setOd(dResponse.getOd());
                                obj.setGst(dResponse.getGst());
                                obj.setTataFlag(dResponse.getTataFlag());
                                obj.setZeroDept(dResponse.getZeroDept());
                                obj.setRsa(dResponse.getRoadSideAssistance());
                                obj.setIdvMin(dResponse.getIdvMin());
                                obj.setIdvMax(dResponse.getIdvMax());

                                double min = dResponse.getIdvMin();
                                double max = dResponse.getIdvMax();

                                if (min > 0)
                                    idvList.add((int) Math.floor(min + 0.5d));
                                if (max > 0)
                                    idvList.add((int) Math.floor(max + 0.5d));
                                String mIdv = dResponse.getIdv();
                                if (TextUtils.isEmpty(idv) && (!TextUtils.isEmpty(mIdv))
                                        && (!mIdv.equals("1")) && (!mIdv.equals("0"))
                                        && !tpOnly.equals("1")) {
                                    txtIDV.setText(mIdv);
                                    idv = mIdv;
                                }
                                premiumList.add(obj);
                            }
                        }
                    }
                    if (isFlag)
                        progressBar.setVisibility(View.GONE);
                }

                if (dResponse.getCompany().equalsIgnoreCase("hdfc")) {
                    pCount++;
                    if (pCount > 15)
                        isFlag = true;
                    for (int i = 0; i < premiumList.size(); i++) {
                        if (premiumList.get(i).getCompany().equalsIgnoreCase("hdfc")) {
                            premiumList.remove(i);
                            break;
                        }
                    }
                    if (dResponse.getStatus().equals("1")) {
                        if (!dResponse.getNet().equals("0")) {
                            if (!TextUtils.isEmpty(dResponse.getNet())) {
                                PremiumObjTw obj = new PremiumObjTw();

                                obj.setLogo(dResponse.getLogo());
                                obj.setNet(dResponse.getNet());
                                obj.setIdv(dResponse.getIdv());
                                obj.setPolicyType(dResponse.getPolicyType());
                                obj.setTenure(dResponse.getTenure());
                                obj.setTppd(dResponse.getTppd());
                                obj.setCompany(dResponse.getCompany());
                                obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                obj.setTotalPremium(dResponse.getTotalPremium());
                                obj.setEndDate(dResponse.getEndDate());
                                obj.setStartDate(dResponse.getStartDate());
                                obj.setNcb(dResponse.getNcb());
                                obj.setCurrentNcb(dResponse.getCurrentNcb());
                                obj.setPa(dResponse.getPa());
                                obj.setPaidDriver(dResponse.getPaidDriver());
                                obj.setUnnamedPerson(dResponse.getUnNamedPA());

                                obj.setTp(dResponse.getTp());
                                obj.setOd(dResponse.getOd());
                                obj.setGst(dResponse.getGst());
                                obj.setTataFlag(dResponse.getTataFlag());
                                obj.setZeroDept(dResponse.getZeroDept());
                                obj.setRsa(dResponse.getRoadSideAssistance());
                                obj.setIdvMin(dResponse.getIdvMin());
                                obj.setIdvMax(dResponse.getIdvMax());

                                double min = dResponse.getIdvMin();
                                double max = dResponse.getIdvMax();

                                if (min > 0)
                                    idvList.add((int) Math.floor(min + 0.5d));
                                if (max > 0)
                                    idvList.add((int) Math.floor(max + 0.5d));

                                String mIdv = dResponse.getIdv();
                                if (TextUtils.isEmpty(idv) && (!TextUtils.isEmpty(mIdv))
                                        && (!mIdv.equals("1")) && (!mIdv.equals("0"))
                                        && !tpOnly.equals("1")) {
                                    txtIDV.setText(mIdv);
                                    idv = mIdv;
                                }
                                premiumList.add(obj);
                            }
                        }
                    }
                    if (isFlag)
                        progressBar.setVisibility(View.GONE);
                }

                if (dResponse.getCompany().equalsIgnoreCase("future")) {
                    pCount++;
                    if (pCount > 15)
                        isFlag = true;
                    for (int i = 0; i < premiumList.size(); i++) {
                        if (premiumList.get(i).getCompany().equalsIgnoreCase("future")) {
                            premiumList.remove(i);
                            break;
                        }
                    }
                    if (dResponse.getStatus().equals("1")) {
                        if (!dResponse.getNet().equals("0")) {
                            if (!TextUtils.isEmpty(dResponse.getNet())) {
                                PremiumObjTw obj = new PremiumObjTw();

                                obj.setLogo(dResponse.getLogo());
                                obj.setNet(dResponse.getNet());
                                obj.setIdv(dResponse.getIdv());
                                obj.setPolicyType(dResponse.getPolicyType());
                                obj.setTenure(dResponse.getTenure());
                                obj.setTppd(dResponse.getTppd());
                                obj.setCompany(dResponse.getCompany());
                                obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                obj.setTotalPremium(dResponse.getTotalPremium());
                                obj.setEndDate(dResponse.getEndDate());
                                obj.setStartDate(dResponse.getStartDate());
                                obj.setNcb(dResponse.getNcb());
                                obj.setCurrentNcb(dResponse.getCurrentNcb());
                                obj.setPa(dResponse.getPa());
                                obj.setPaidDriver(dResponse.getPaidDriver());
                                obj.setUnnamedPerson(dResponse.getUnNamedPA());

                                obj.setTp(dResponse.getTp());
                                obj.setOd(dResponse.getOd());
                                obj.setGst(dResponse.getGst());
                                obj.setTataFlag(dResponse.getTataFlag());
                                obj.setZeroDept(dResponse.getZeroDept());
                                obj.setRsa(dResponse.getRoadSideAssistance());
                                obj.setIdvMin(dResponse.getIdvMin());
                                obj.setIdvMax(dResponse.getIdvMax());

                                double min = dResponse.getIdvMin();
                                double max = dResponse.getIdvMax();

                                if (min > 0)
                                    idvList.add((int) Math.floor(min + 0.5d));
                                if (max > 0)
                                    idvList.add((int) Math.floor(max + 0.5d));

                                String mIdv = dResponse.getIdv();
                                if (TextUtils.isEmpty(idv) && (!TextUtils.isEmpty(mIdv))
                                        && (!mIdv.equals("1")) && (!mIdv.equals("0"))
                                        && !tpOnly.equals("1")) {
                                    txtIDV.setText(mIdv);
                                    idv = mIdv;
                                }
                                premiumList.add(obj);
                            }
                        }
                    }
                    if (isFlag)
                        progressBar.setVisibility(View.GONE);
                }

                if (dResponse.getCompany().equalsIgnoreCase("shriram")) {
                    pCount++;
                    if (pCount > 15)
                        isFlag = true;
                    for (int i = 0; i < premiumList.size(); i++) {
                        if (premiumList.get(i).getCompany().equalsIgnoreCase("shriram")) {
                            premiumList.remove(i);
                            break;
                        }
                    }
                    if (dResponse.getStatus().equals("1")) {
                        if (!dResponse.getNet().equals("0")) {
                            if (!TextUtils.isEmpty(dResponse.getNet())) {
                                PremiumObjTw obj = new PremiumObjTw();

                                obj.setLogo(dResponse.getLogo());
                                obj.setNet(dResponse.getNet());
                                obj.setIdv(dResponse.getIdv());
                                obj.setPolicyType(dResponse.getPolicyType());
                                obj.setTenure(dResponse.getTenure());
                                obj.setTppd(dResponse.getTppd());
                                obj.setCompany(dResponse.getCompany());
                                obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                obj.setTotalPremium(dResponse.getTotalPremium());
                                obj.setEndDate(dResponse.getEndDate());
                                obj.setStartDate(dResponse.getStartDate());
                                obj.setNcb(dResponse.getNcb());
                                obj.setCurrentNcb(dResponse.getCurrentNcb());
                                obj.setPa(dResponse.getPa());
                                obj.setPaidDriver(dResponse.getPaidDriver());
                                obj.setUnnamedPerson(dResponse.getUnNamedPA());

                                obj.setTp(dResponse.getTp());
                                obj.setOd(dResponse.getOd());
                                obj.setGst(dResponse.getGst());
                                obj.setTataFlag(dResponse.getTataFlag());
                                obj.setZeroDept(dResponse.getZeroDept());
                                obj.setRsa(dResponse.getRoadSideAssistance());
                                obj.setIdvMin(dResponse.getIdvMin());
                                obj.setIdvMax(dResponse.getIdvMax());

                                double min = dResponse.getIdvMin();
                                double max = dResponse.getIdvMax();

                                if (min > 0)
                                    idvList.add((int) Math.floor(min + 0.5d));
                                if (max > 0)
                                    idvList.add((int) Math.floor(max + 0.5d));
                                String mIdv = dResponse.getIdv();
                                if (TextUtils.isEmpty(idv) && (!TextUtils.isEmpty(mIdv))
                                        && (!mIdv.equals("1")) && (!mIdv.equals("0"))
                                        && !tpOnly.equals("1")) {
                                    txtIDV.setText(mIdv);
                                    idv = mIdv;
                                }

                                premiumList.add(obj);
                            }
                        }
                    }
                    if (isFlag)
                        progressBar.setVisibility(View.GONE);
                }
                if (dResponse.getCompany().equalsIgnoreCase("reliance")) {
                    pCount++;
                    if (pCount > 15)
                        isFlag = true;
                    for (int i = 0; i < premiumList.size(); i++) {
                        if (premiumList.get(i).getCompany().equalsIgnoreCase("reliance")) {
                            premiumList.remove(i);
                            break;
                        }
                    }
                    if (dResponse.getStatus().equals("1")) {
                        if (!dResponse.getNet().equals("0")) {
                            if (!TextUtils.isEmpty(dResponse.getNet())) {
                                PremiumObjTw obj = new PremiumObjTw();

                                obj.setLogo(dResponse.getLogo());
                                obj.setNet(dResponse.getNet());
                                obj.setIdv(dResponse.getIdv());
                                obj.setPolicyType(dResponse.getPolicyType());
                                obj.setTenure(dResponse.getTenure());
                                obj.setTppd(dResponse.getTppd());
                                obj.setCompany(dResponse.getCompany());
                                obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                obj.setTotalPremium(dResponse.getTotalPremium());
                                obj.setEndDate(dResponse.getEndDate());
                                obj.setStartDate(dResponse.getStartDate());
                                obj.setNcb(dResponse.getNcb());
                                obj.setCurrentNcb(dResponse.getCurrentNcb());
                                obj.setPa(dResponse.getPa());
                                obj.setPaidDriver(dResponse.getPaidDriver());
                                obj.setUnnamedPerson(dResponse.getUnNamedPA());

                                obj.setTp(dResponse.getTp());
                                obj.setOd(dResponse.getOd());
                                obj.setGst(dResponse.getGst());
                                obj.setTataFlag(dResponse.getTataFlag());
                                obj.setZeroDept(dResponse.getZeroDept());
                                obj.setRsa(dResponse.getRoadSideAssistance());
                                obj.setIdvMin(dResponse.getIdvMin());
                                obj.setIdvMax(dResponse.getIdvMax());

                                double min = dResponse.getIdvMin();
                                double max = dResponse.getIdvMax();

                                if (min > 0)
                                    idvList.add((int) Math.floor(min + 0.5d));
                                if (max > 0)
                                    idvList.add((int) Math.floor(max + 0.5d));
                                String mIdv = dResponse.getIdv();
                                if (TextUtils.isEmpty(idv) && (!TextUtils.isEmpty(mIdv))
                                        && (!mIdv.equals("1")) && (!mIdv.equals("0"))
                                        && !tpOnly.equals("1")) {
                                    txtIDV.setText(mIdv);
                                    idv = mIdv;
                                }
                                premiumList.add(obj);
                            }
                        }
                    }
                    if (isFlag)
                        progressBar.setVisibility(View.GONE);
                }
                if (dResponse.getCompany().equalsIgnoreCase("bajaj")) {
                    pCount++;
                    if (pCount > 15)
                        isFlag = true;
                    for (int i = 0; i < premiumList.size(); i++) {
                        if (premiumList.get(i).getCompany().equalsIgnoreCase("bajaj")) {
                            premiumList.remove(i);
                            break;
                        }
                    }
                    if (dResponse.getStatus().equals("1")) {
                        if (!dResponse.getNet().equals("0")) {
                            if (!TextUtils.isEmpty(dResponse.getNet())) {
                                PremiumObjTw obj = new PremiumObjTw();

                                obj.setLogo(dResponse.getLogo());
                                obj.setNet(dResponse.getNet());
                                obj.setIdv(dResponse.getIdv());
                                obj.setPolicyType(dResponse.getPolicyType());
                                obj.setTenure(dResponse.getTenure());
                                obj.setTppd(dResponse.getTppd());
                                obj.setCompany(dResponse.getCompany());
                                obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                obj.setTotalPremium(dResponse.getTotalPremium());
                                obj.setEndDate(dResponse.getEndDate());
                                obj.setStartDate(dResponse.getStartDate());
                                obj.setNcb(dResponse.getNcb());
                                obj.setCurrentNcb(dResponse.getCurrentNcb());
                                obj.setPa(dResponse.getPa());
                                obj.setPaidDriver(dResponse.getPaidDriver());
                                obj.setUnnamedPerson(dResponse.getUnNamedPA());

                                obj.setTp(dResponse.getTp());
                                obj.setOd(dResponse.getOd());
                                obj.setGst(dResponse.getGst());
                                obj.setTataFlag(dResponse.getTataFlag());
                                obj.setZeroDept(dResponse.getZeroDept());
                                obj.setRsa(dResponse.getRoadSideAssistance());
                                obj.setIdvMin(dResponse.getIdvMin());
                                obj.setIdvMax(dResponse.getIdvMax());

                                double min = dResponse.getIdvMin();
                                double max = dResponse.getIdvMax();

                                if (min > 0)
                                    idvList.add((int) Math.floor(min + 0.5d));
                                if (max > 0)
                                    idvList.add((int) Math.floor(max + 0.5d));
                                String mIdv = dResponse.getIdv();
                                if (TextUtils.isEmpty(idv) && (!TextUtils.isEmpty(mIdv))
                                        && (!mIdv.equals("1")) && (!mIdv.equals("0"))
                                        && !tpOnly.equals("1")) {
                                    txtIDV.setText(mIdv);
                                    idv = mIdv;
                                }
                                premiumList.add(obj);
                            }
                        }
                    }
                    if (isFlag)
                        progressBar.setVisibility(View.GONE);
                }
                if (dResponse.getCompany().equalsIgnoreCase("icici")) {
                    pCount++;
                    if (pCount > 15)
                        isFlag = true;
                    for (int i = 0; i < premiumList.size(); i++) {
                        if (premiumList.get(i).getCompany().equalsIgnoreCase("icici")) {
                            premiumList.remove(i);
                            break;
                        }
                    }
                    if (dResponse.getStatus().equals("1")) {
                        if (!dResponse.getNet().equals("0")) {
                            if (!TextUtils.isEmpty(dResponse.getNet())) {
                                PremiumObjTw obj = new PremiumObjTw();

                                obj.setLogo(dResponse.getLogo());
                                obj.setNet(dResponse.getNet());
                                obj.setIdv(dResponse.getIdv());
                                obj.setPolicyType(dResponse.getPolicyType());
                                obj.setTenure(dResponse.getTenure());
                                obj.setTppd(dResponse.getTppd());
                                obj.setCompany(dResponse.getCompany());
                                obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                obj.setTotalPremium(dResponse.getTotalPremium());
                                obj.setEndDate(dResponse.getEndDate());
                                obj.setStartDate(dResponse.getStartDate());
                                obj.setNcb(dResponse.getNcb());
                                obj.setCurrentNcb(dResponse.getCurrentNcb());
                                obj.setPa(dResponse.getPa());
                                obj.setPaidDriver(dResponse.getPaidDriver());
                                obj.setUnnamedPerson(dResponse.getUnNamedPA());

                                obj.setTp(dResponse.getTp());
                                obj.setOd(dResponse.getOd());
                                obj.setGst(dResponse.getGst());
                                obj.setTataFlag(dResponse.getTataFlag());
                                obj.setZeroDept(dResponse.getZeroDept());
                                obj.setRsa(dResponse.getRoadSideAssistance());
                                obj.setIdvMin(dResponse.getIdvMin());
                                obj.setIdvMax(dResponse.getIdvMax());

                                double min = dResponse.getIdvMin();
                                double max = dResponse.getIdvMax();

                                if (min > 0)
                                    idvList.add((int) Math.floor(min + 0.5d));
                                if (max > 0)
                                    idvList.add((int) Math.floor(max + 0.5d));
                                String mIdv = dResponse.getIdv();
                                if (TextUtils.isEmpty(idv) && (!TextUtils.isEmpty(mIdv))
                                        && (!mIdv.equals("1")) && (!mIdv.equals("0"))
                                        && !tpOnly.equals("1")) {
                                    txtIDV.setText(mIdv);
                                    idv = mIdv;
                                }
                                premiumList.add(obj);
                            }
                        }
                    }
                    if (isFlag)
                        progressBar.setVisibility(View.GONE);
                }

                if (dResponse.getCompany().equalsIgnoreCase("sbi")) {
                    pCount++;
                    if (pCount > 15)
                        isFlag = true;
                    for (int i = 0; i < premiumList.size(); i++) {
                        if (premiumList.get(i).getCompany().equalsIgnoreCase("sbi")) {
                            premiumList.remove(i);
                            break;
                        }
                    }
                    if (dResponse.getStatus().equals("1")) {
                        if (!dResponse.getNet().equals("0")) {
                            if (!TextUtils.isEmpty(dResponse.getNet())) {
                                PremiumObjTw obj = new PremiumObjTw();

                                obj.setLogo(dResponse.getLogo());
                                obj.setNet(dResponse.getNet());
                                obj.setIdv(dResponse.getIdv());
                                obj.setPolicyType(dResponse.getPolicyType());
                                obj.setTenure(dResponse.getTenure());
                                obj.setTppd(dResponse.getTppd());
                                obj.setCompany(dResponse.getCompany());
                                obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                obj.setTotalPremium(dResponse.getTotalPremium());
                                obj.setEndDate(dResponse.getEndDate());
                                obj.setStartDate(dResponse.getStartDate());
                                obj.setNcb(dResponse.getNcb());
                                obj.setCurrentNcb(dResponse.getCurrentNcb());
                                obj.setPa(dResponse.getPa());
                                obj.setPaidDriver(dResponse.getPaidDriver());
                                obj.setUnnamedPerson(dResponse.getUnNamedPA());

                                obj.setTp(dResponse.getTp());
                                obj.setOd(dResponse.getOd());
                                obj.setGst(dResponse.getGst());
                                obj.setTataFlag(dResponse.getTataFlag());
                                obj.setZeroDept(dResponse.getZeroDept());
                                obj.setRsa(dResponse.getRoadSideAssistance());
                                obj.setIdvMin(dResponse.getIdvMin());
                                obj.setIdvMax(dResponse.getIdvMax());

                                double min = dResponse.getIdvMin();
                                double max = dResponse.getIdvMax();

                                if (min > 0)
                                    idvList.add((int) Math.floor(min + 0.5d));
                                if (max > 0)
                                    idvList.add((int) Math.floor(max + 0.5d));
                                String mIdv = dResponse.getIdv();
                                if (TextUtils.isEmpty(idv) && (!TextUtils.isEmpty(mIdv))
                                        && (!mIdv.equals("1")) && (!mIdv.equals("0"))
                                        && !tpOnly.equals("1")) {
                                    txtIDV.setText(mIdv);
                                    idv = mIdv;
                                }
                                premiumList.add(obj);
                            }
                        }
                    }
                    if (isFlag)
                        progressBar.setVisibility(View.GONE);
                }

                if (dResponse.getCompany().equalsIgnoreCase("cholamandalam")) {
                    pCount++;
                    if (pCount > 15)
                        isFlag = true;
                    for (int i = 0; i < premiumList.size(); i++) {
                        if (premiumList.get(i).getCompany().equalsIgnoreCase("cholamandalam")) {
                            premiumList.remove(i);
                            break;
                        }
                    }
                    if (dResponse.getStatus().equals("1")) {
                        if (!dResponse.getNet().equals("0")) {
                            if (!TextUtils.isEmpty(dResponse.getNet())) {
                                PremiumObjTw obj = new PremiumObjTw();

                                obj.setLogo(dResponse.getLogo());
                                obj.setNet(dResponse.getNet());
                                obj.setIdv(dResponse.getIdv());
                                obj.setPolicyType(dResponse.getPolicyType());
                                obj.setTenure(dResponse.getTenure());
                                obj.setTppd(dResponse.getTppd());
                                obj.setCompany(dResponse.getCompany());
                                obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                obj.setTotalPremium(dResponse.getTotalPremium());
                                obj.setEndDate(dResponse.getEndDate());
                                obj.setStartDate(dResponse.getStartDate());
                                obj.setNcb(dResponse.getNcb());
                                obj.setCurrentNcb(dResponse.getCurrentNcb());
                                obj.setPa(dResponse.getPa());
                                obj.setPaidDriver(dResponse.getPaidDriver());
                                obj.setUnnamedPerson(dResponse.getUnNamedPA());

                                obj.setTp(dResponse.getTp());
                                obj.setOd(dResponse.getOd());
                                obj.setGst(dResponse.getGst());
                                obj.setTataFlag(dResponse.getTataFlag());
                                obj.setZeroDept(dResponse.getZeroDept());
                                obj.setRsa(dResponse.getRoadSideAssistance());
                                obj.setIdvMin(dResponse.getIdvMin());
                                obj.setIdvMax(dResponse.getIdvMax());

                                double min = dResponse.getIdvMin();
                                double max = dResponse.getIdvMax();

                                if (min > 0)
                                    idvList.add((int) Math.floor(min + 0.5d));
                                if (max > 0)
                                    idvList.add((int) Math.floor(max + 0.5d));
                                String mIdv = dResponse.getIdv();
                                if (TextUtils.isEmpty(idv) && (!TextUtils.isEmpty(mIdv))
                                        && (!mIdv.equals("1")) && (!mIdv.equals("0"))
                                        && !tpOnly.equals("1")) {
                                    txtIDV.setText(mIdv);
                                    idv = mIdv;
                                }
                                premiumList.add(obj);
                            }
                        }
                    }
                    if (isFlag)
                        progressBar.setVisibility(View.GONE);
                }

                if (dResponse.getCompany().equalsIgnoreCase("magma")) {
                    pCount++;
                    if (pCount > 15)
                        isFlag = true;
                    for (int i = 0; i < premiumList.size(); i++) {
                        if (premiumList.get(i).getCompany().equalsIgnoreCase("magma")) {
                            premiumList.remove(i);
                            break;
                        }
                    }
                    if (dResponse.getStatus().equals("1")) {
                        if (!dResponse.getNet().equals("0")) {
                            if (!TextUtils.isEmpty(dResponse.getNet())) {
                                PremiumObjTw obj = new PremiumObjTw();

                                obj.setLogo(dResponse.getLogo());
                                obj.setNet(dResponse.getNet());
                                obj.setIdv(dResponse.getIdv());
                                obj.setPolicyType(dResponse.getPolicyType());
                                obj.setTenure(dResponse.getTenure());
                                obj.setTppd(dResponse.getTppd());
                                obj.setCompany(dResponse.getCompany());
                                obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                obj.setTotalPremium(dResponse.getTotalPremium());
                                obj.setEndDate(dResponse.getEndDate());
                                obj.setStartDate(dResponse.getStartDate());
                                obj.setNcb(dResponse.getNcb());
                                obj.setCurrentNcb(dResponse.getCurrentNcb());
                                obj.setPa(dResponse.getPa());
                                obj.setPaidDriver(dResponse.getPaidDriver());
                                obj.setUnnamedPerson(dResponse.getUnNamedPA());

                                obj.setTp(dResponse.getTp());
                                obj.setOd(dResponse.getOd());
                                obj.setGst(dResponse.getGst());
                                obj.setTataFlag(dResponse.getTataFlag());
                                obj.setZeroDept(dResponse.getZeroDept());
                                obj.setRsa(dResponse.getRoadSideAssistance());
                                obj.setIdvMin(dResponse.getIdvMin());
                                obj.setIdvMax(dResponse.getIdvMax());

                                double min = dResponse.getIdvMin();
                                double max = dResponse.getIdvMax();

                                if (min > 0)
                                    idvList.add((int) Math.floor(min + 0.5d));
                                if (max > 0)
                                    idvList.add((int) Math.floor(max + 0.5d));
                                String mIdv = dResponse.getIdv();
                                if (TextUtils.isEmpty(idv) && (!TextUtils.isEmpty(mIdv))
                                        && (!mIdv.equals("1")) && (!mIdv.equals("0"))
                                        && !tpOnly.equals("1")) {
                                    txtIDV.setText(mIdv);
                                    idv = mIdv;
                                }
                                premiumList.add(obj);
                            }
                        }
                    }
                    if (isFlag)
                        progressBar.setVisibility(View.GONE);
                }
                if (dResponse.getCompany().equalsIgnoreCase("tata")) {
                    pCount++;
                    if (pCount > 15)
                        isFlag = true;
                    for (int i = 0; i < premiumList.size(); i++) {
                        if (premiumList.get(i).getCompany().equalsIgnoreCase("tata")) {
                            premiumList.remove(i);
                            break;
                        }
                    }
                    if (dResponse.getStatus().equals("1")) {
                        if (!dResponse.getNet().equals("0")) {
                            if (!TextUtils.isEmpty(dResponse.getNet())) {
                                PremiumObjTw obj = new PremiumObjTw();

                                obj.setLogo(dResponse.getLogo());
                                obj.setNet(dResponse.getNet());
                                obj.setIdv(dResponse.getIdv());
                                obj.setPolicyType(dResponse.getPolicyType());
                                obj.setTenure(dResponse.getTenure());
                                obj.setTppd(dResponse.getTppd());
                                obj.setCompany(dResponse.getCompany());
                                obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                obj.setTotalPremium(dResponse.getTotalPremium());
                                obj.setEndDate(dResponse.getEndDate());
                                obj.setStartDate(dResponse.getStartDate());
                                obj.setNcb(dResponse.getNcb());
                                obj.setCurrentNcb(dResponse.getCurrentNcb());
                                obj.setPa(dResponse.getPa());
                                obj.setPaidDriver(dResponse.getPaidDriver());
                                obj.setUnnamedPerson(dResponse.getUnNamedPA());

                                obj.setTp(dResponse.getTp());
                                obj.setOd(dResponse.getOd());
                                obj.setGst(dResponse.getGst());
                                obj.setTataFlag(dResponse.getTataFlag());
                                obj.setZeroDept(dResponse.getZeroDept());
                                obj.setRsa(dResponse.getRoadSideAssistance());
                                obj.setIdvMin(dResponse.getIdvMin());
                                obj.setIdvMax(dResponse.getIdvMax());

                                double min = dResponse.getIdvMin();
                                double max = dResponse.getIdvMax();

                                if (min > 0)
                                    idvList.add((int) Math.floor(min + 0.5d));
                                if (max > 0)
                                    idvList.add((int) Math.floor(max + 0.5d));
                                String mIdv = dResponse.getIdv();
                                if (TextUtils.isEmpty(idv) && (!TextUtils.isEmpty(mIdv))
                                        && (!mIdv.equals("1")) && (!mIdv.equals("0"))
                                        && !tpOnly.equals("1")) {
                                    txtIDV.setText(mIdv);
                                    idv = mIdv;
                                }
                                premiumList.add(obj);
                            }
                        }
                    }
                    if (isFlag)
                        progressBar.setVisibility(View.GONE);
                }
                if (dResponse.getCompany().equalsIgnoreCase("bharti")) {
                    pCount++;
                    if (pCount > 15)
                        isFlag = true;
                    for (int i = 0; i < premiumList.size(); i++) {
                        if (premiumList.get(i).getCompany().equalsIgnoreCase("bharti")) {
                            premiumList.remove(i);
                            break;
                        }
                    }
                    if (dResponse.getStatus().equals("1")) {
                        if (!dResponse.getNet().equals("0")) {
                            if (!TextUtils.isEmpty(dResponse.getNet())) {
                                PremiumObjTw obj = new PremiumObjTw();

                                obj.setLogo(dResponse.getLogo());
                                obj.setNet(dResponse.getNet());
                                obj.setIdv(dResponse.getIdv());
                                obj.setPolicyType(dResponse.getPolicyType());
                                obj.setTenure(dResponse.getTenure());
                                obj.setTppd(dResponse.getTppd());
                                obj.setCompany(dResponse.getCompany());
                                obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                obj.setTotalPremium(dResponse.getTotalPremium());
                                obj.setEndDate(dResponse.getEndDate());
                                obj.setStartDate(dResponse.getStartDate());
                                obj.setNcb(dResponse.getNcb());
                                obj.setCurrentNcb(dResponse.getCurrentNcb());
                                obj.setPa(dResponse.getPa());
                                obj.setPaidDriver(dResponse.getPaidDriver());
                                obj.setUnnamedPerson(dResponse.getUnNamedPA());

                                obj.setTp(dResponse.getTp());
                                obj.setOd(dResponse.getOd());
                                obj.setGst(dResponse.getGst());
                                obj.setTataFlag(dResponse.getTataFlag());
                                obj.setZeroDept(dResponse.getZeroDept());
                                obj.setRsa(dResponse.getRoadSideAssistance());
                                obj.setIdvMin(dResponse.getIdvMin());
                                obj.setIdvMax(dResponse.getIdvMax());

                                double min = dResponse.getIdvMin();
                                double max = dResponse.getIdvMax();

                                if (min > 0)
                                    idvList.add((int) Math.floor(min + 0.5d));
                                if (max > 0)
                                    idvList.add((int) Math.floor(max + 0.5d));
                                String mIdv = dResponse.getIdv();
                                if (TextUtils.isEmpty(idv) && (!TextUtils.isEmpty(mIdv))
                                        && (!mIdv.equals("1")) && (!mIdv.equals("0"))
                                        && !tpOnly.equals("1")) {
                                    txtIDV.setText(mIdv);
                                    idv = mIdv;
                                }
                                premiumList.add(obj);
                            }
                        }
                    }
                    if (isFlag)
                        progressBar.setVisibility(View.GONE);
                }
                if (dResponse.getCompany().equalsIgnoreCase("oriental")) {
                    pCount++;
                    if (pCount > 15)
                        isFlag = true;
                    for (int i = 0; i < premiumList.size(); i++) {
                        if (premiumList.get(i).getCompany().equalsIgnoreCase("oriental")) {
                            premiumList.remove(i);
                            break;
                        }
                    }
                    if (dResponse.getStatus().equals("1")) {
                        if (!dResponse.getNet().equals("0")) {
                            if (!TextUtils.isEmpty(dResponse.getNet())) {
                                PremiumObjTw obj = new PremiumObjTw();

                                obj.setLogo(dResponse.getLogo());
                                obj.setNet(dResponse.getNet());
                                obj.setIdv(dResponse.getIdv());
                                obj.setPolicyType(dResponse.getPolicyType());
                                obj.setTenure(dResponse.getTenure());
                                obj.setTppd(dResponse.getTppd());
                                obj.setCompany(dResponse.getCompany());
                                obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                obj.setTotalPremium(dResponse.getTotalPremium());
                                obj.setEndDate(dResponse.getEndDate());
                                obj.setStartDate(dResponse.getStartDate());
                                obj.setNcb(dResponse.getNcb());
                                obj.setCurrentNcb(dResponse.getCurrentNcb());
                                obj.setPa(dResponse.getPa());
                                obj.setPaidDriver(dResponse.getPaidDriver());
                                obj.setUnnamedPerson(dResponse.getUnNamedPA());

                                obj.setTp(dResponse.getTp());
                                obj.setOd(dResponse.getOd());
                                obj.setGst(dResponse.getGst());
                                obj.setTataFlag(dResponse.getTataFlag());
                                obj.setZeroDept(dResponse.getZeroDept());
                                obj.setRsa(dResponse.getRoadSideAssistance());
                                obj.setIdvMin(dResponse.getIdvMin());
                                obj.setIdvMax(dResponse.getIdvMax());

                                double min = dResponse.getIdvMin();
                                double max = dResponse.getIdvMax();

                                if (min > 0)
                                    idvList.add((int) Math.floor(min + 0.5d));
                                if (max > 0)
                                    idvList.add((int) Math.floor(max + 0.5d));
                                String mIdv = dResponse.getIdv();
                                if (TextUtils.isEmpty(idv) && (!TextUtils.isEmpty(mIdv))
                                        && (!mIdv.equals("1")) && (!mIdv.equals("0"))
                                        && !tpOnly.equals("1")) {
                                    txtIDV.setText(mIdv);
                                    idv = mIdv;
                                }

                                premiumList.add(obj);
                            }
                        }
                    }
                    if (isFlag)
                        progressBar.setVisibility(View.GONE);
                }
                if (dResponse.getCompany().equalsIgnoreCase("liberty")) {
                    pCount++;
                    if (pCount > 15)
                        isFlag = true;

                    for (int i = 0; i < premiumList.size(); i++) {
                        if (premiumList.get(i).getCompany().equalsIgnoreCase("liberty")) {
                            premiumList.remove(i);
                            break;
                        }
                    }
                    if (dResponse.getStatus().equals("1")) {
                        if (!dResponse.getNet().equals("0")) {
                            if (!TextUtils.isEmpty(dResponse.getNet())) {
                                PremiumObjTw obj = new PremiumObjTw();

                                obj.setLogo(dResponse.getLogo());
                                obj.setNet(dResponse.getNet());
                                obj.setIdv(dResponse.getIdv());
                                obj.setPolicyType(dResponse.getPolicyType());
                                obj.setTenure(dResponse.getTenure());
                                obj.setTppd(dResponse.getTppd());
                                obj.setCompany(dResponse.getCompany());
                                obj.setPreviousInsurer(dResponse.getPreviousInsurer());

                                obj.setTotalPremium(dResponse.getTotalPremium());
                                obj.setEndDate(dResponse.getEndDate());
                                obj.setStartDate(dResponse.getStartDate());
                                obj.setNcb(dResponse.getNcb());
                                obj.setCurrentNcb(dResponse.getCurrentNcb());
                                obj.setPa(dResponse.getPa());
                                obj.setPaidDriver(dResponse.getPaidDriver());
                                obj.setUnnamedPerson(dResponse.getUnNamedPA());

                                obj.setTp(dResponse.getTp());
                                obj.setOd(dResponse.getOd());
                                obj.setGst(dResponse.getGst());
                                obj.setTataFlag(dResponse.getTataFlag());
                                obj.setZeroDept(dResponse.getZeroDept());
                                obj.setRsa(dResponse.getRoadSideAssistance());
                                obj.setIdvMin(dResponse.getIdvMin());
                                obj.setIdvMax(dResponse.getIdvMax());

                                double min = dResponse.getIdvMin();
                                double max = dResponse.getIdvMax();

                                if (min > 0)
                                    idvList.add((int) Math.floor(min + 0.5d));
                                if (max > 0)
                                    idvList.add((int) Math.floor(max + 0.5d));

                                String mIdv = dResponse.getIdv();
                                if (TextUtils.isEmpty(idv) && (!TextUtils.isEmpty(mIdv))
                                        && (!mIdv.equals("1")) && (!mIdv.equals("0"))
                                        && !tpOnly.equals("1")) {
                                    txtIDV.setText(mIdv);
                                    idv = mIdv;
                                }
                                premiumList.add(obj);
                            }
                        }
                    }
                    if (isFlag)
                        progressBar.setVisibility(View.GONE);
                }
//            if (tpOnly.equals("1"))
//                findViewById(R.id.rlIDV).setVisibility(View.GONE);
//            else
//                findViewById(R.id.rlIDV).setVisibility(View.VISIBLE);

                if (premiumList.size() > 0) {
                    Collections.sort(premiumList, (lhs, rhs) -> lhs.getTotalPremium()
                            .compareTo(rhs.getTotalPremium()));

                    policyAdaptor.notifyDataSetChanged();
                    recyclerQuote.setAdapter(policyAdaptor);
                    findViewById(R.id.noQuote).setVisibility(View.GONE);
                    findViewById(R.id.clQuote).setVisibility(View.VISIBLE);
                    lblQuoteNo.setText(String.valueOf(premiumList.size()));
                    AppUtils.stopShimmer(shimmerFrameLayout);
                } else {
                    findViewById(R.id.clQuote).setVisibility(View.GONE);
                    policyAdaptor.notifyItemRangeRemoved(0, premiumList.size());
                    policyAdaptor.notifyDataSetChanged();
                    lblQuoteNo.setText("0");

                    if (pCount > 16) {
                        AppUtils.stopShimmer(shimmerFrameLayout);
                        if (premiumList.size() == 0)
                            findViewById(R.id.noQuote).setVisibility(View.VISIBLE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        mBundle.putString(AppUtils.POLICY_TYPE, policyType);
        intent.putExtras(mBundle);
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }

    public void getManufacture() {
        if (AppUtils.isOnline(mContext)) {
            try {

//                UserManager.getInstance().getManufacture(mContext, vehicleType, pcvType, pcvCompany);
                UserManager.getInstance().getManufacture(mContext, vehicleType, "", "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getModel() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getModel(mContext, fMake, vehicleType, pcvType,
                        pcvCompany);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getFuel() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getFuel(mContext, fMake, fModel, vehicleType,
                        pcvCompany);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getVariant() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getVariant(mContext, fMake, fModel, vehicleType,
                        fFuel, pcvCompany);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void initManufacture() {
        for (int i = 0; i < manufactureList.size(); i++) {
            String bankName = manufactureList.get(i).getMake();
            String bankId = manufactureList.get(i).getMake();
            manuList.add(new StringWithTag(bankName, bankId));
        }

        ArrayAdapter<StringWithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, manuList);
        spnManufacture.setAdapter(adapter);

        if (!TextUtils.isEmpty(make) && manufactureList.size() > 0)
            for (int i = 0; i < manufactureList.size(); i++) {
                String sName = manufactureList.get(i).getMake();
                if (sName.equalsIgnoreCase(make)) {
                    spnManufacture.setSelectionM(i);
                    break;
                }
            }

        spnManufacture.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag stringWithTag = (StringWithTag) parent.getItemAtPosition(position);
                fMake = stringWithTag.string;
                if (!TextUtils.isEmpty(fMake))
                    getModel();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initModel() {

        for (int i = 0; i < modelList.size(); i++) {
            String modelName = modelList.get(i).getModelName();
            String modelId = modelList.get(i).getModelName();
            moList.add(new String4WithTag(modelName, modelId));
        }

        ArrayAdapter<String4WithTag> adapter = new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item, moList);
        spnModel.setAdapter(adapter);

        if (!TextUtils.isEmpty(modelName) && modelList.size() > 0) {
            int i = 0;
            while (i < modelList.size()) {
                modelList.size();
                String sName = modelList.get(i).getModelName();
                if (sName.equalsIgnoreCase(modelName)) {
                    spnModel.setSelection(i);
                    break;
                }
                i++;
            }
        }
        spnModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String4WithTag stringWithTag = (String4WithTag) parent.getItemAtPosition(position);
                fModel = stringWithTag.string;
                if (!TextUtils.isEmpty(fModel))
                    getFuel();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initFuel() {
        for (int i = 0; i < fuelList.size(); i++) {
            String modelName = fuelList.get(i).getFuelType();
            String modelId = fuelList.get(i).getFuelType();
            fList.add(new String5WithTag(modelName, modelId));
        }

        ArrayAdapter<String5WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, fList);
        spnFuel.setAdapter(adapter);


        spnFuel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String5WithTag stringWithTag = (String5WithTag) parent.getItemAtPosition(position);
                fuelType = stringWithTag.string;
                getVariant();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(fuelType) && fuelList.size() != 0) {
            for (int i = 0; i < fuelList.size() - 1; i++) {
                if ((fuelType).contains(fuelList.get(i).getFuelType())
                        && i < fuelList.size()) {
                    spnFuel.setSelectionM(i);
                    break;
                }
            }
        }
    }

    private void initVariant() {
        for (int i = 0; i < variantList.size(); i++) {
            String modelName = variantList.get(i).getVariantName();
            String modelId = variantList.get(i).getVariantName();
            String cc = variantList.get(i).getCubicCapacity();
            String full = modelName + " " + "(" + cc + ")";
            vList.add(new BankWithId(full, modelId));
        }

        ArrayAdapter<BankWithId> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, vList);
        spnVariant.setAdapter(adapter);


        spnVariant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BankWithId stringWithTag = (BankWithId) parent.getItemAtPosition(position);
                fVariant = stringWithTag.string;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(variantName) && variantList.size() != 0) {
            for (int i = 0; i < variantList.size() - 1; i++) {
                String modelName = variantList.get(i).getVariantName();
                String cc = variantList.get(i).getCubicCapacity();
                String full = modelName + " " + "(" + cc + ")";
                if (variantName.equals(full)) {
                    spnVariant.setSelection(i);
                    break;
                }
            }
        }
    }

    public void onVehicleEditClick() {
        vehicleDialog.setTitle("Edit Vehicle");
        vehicleDialog.setCancelable(false);

        Button close = vehicleDialog.findViewById(R.id.dialogButtonClose);
        Button btnUpdate = vehicleDialog.findViewById(R.id.dialogButtonOK);

        close.setOnClickListener(view1 -> vehicleDialog.dismiss());

        btnUpdate.setOnClickListener(v -> {

            if (!TextUtils.isEmpty(fMake))
                make = fMake;
            if (!TextUtils.isEmpty(fModel))
                modelName = fModel;
            if (!TextUtils.isEmpty(fVariant))
                variantName = fVariant;

            if (spnFuel.getSelectedItem() != null)
                fuelType = spnFuel.getSelectedItem().toString();

            if (!TextUtils.isEmpty(make)) {
                Objects.requireNonNull(getSupportActionBar()).setTitle(make);
                getSupportActionBar().setSubtitle(modelName + " " + variantName);
            }
            applyFilter();
            vehicleDialog.dismiss();
            Objects.requireNonNull(getSupportActionBar()).setTitle(make);
            getSupportActionBar().setSubtitle(modelName + " " + variantName);
            vehicleName = make + " " + modelName + " " + variantName;
//            lblVehicle.setText(vehicleName); TODO
        });

        vehicleDialog.show();

        if (!TextUtils.isEmpty(make) && manufactureList.size() != 0) {
            for (int i = 0; i < manufactureList.size() - 1; i++) {
                if (make.equalsIgnoreCase(manufactureList.get(i).getMake())
                        && i < manufactureList.size()) {
                    spnManufacture.setSelectionM(i);
                    break;
                }
            }
        }

        if (!TextUtils.isEmpty(modelName) && modelList.size() != 0) {
            for (int i = 0; i < modelList.size() - 1; i++) {
                if (modelName.equalsIgnoreCase(modelList.get(i).getModelName())
                        && i < modelList.size()) {
                    spnModel.setSelectionM(i);
                    break;
                }
            }
        }

        if (!TextUtils.isEmpty(variantName) && variantList.size() != 0) {
            for (int i = 0; i < variantList.size() - 1; i++) {
                if ((variantName.substring(0, 3)).contains(variantList.get(i).getVariantName())
                        && i < variantList.size()) {
                    spnVariant.setSelectionM(i);
                    break;
                }
            }
        }
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
            String path = mainDir + "/" + "Square" + "-" + format + ".jpeg";
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File imageFile = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            shareScreenShot(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shareScreenShot(File imageFile) {
        Uri uri = FileProvider.getUriForFile(this, "com.square.pos.provider",
                imageFile);

        //Explicit intent
        String shareBody = String.format("%s%s", AppUtils.PRE_LINK, shareQuoteId);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        try {
            this.startActivity(Intent.createChooser(intent, "Share With"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mContext, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }

    public void getCovers() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getCovers(mContext, quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmer();
    }

    public void onRaiseOfflineQuote(View view) {
        Intent intent = new Intent(mContext, RaiseOfflineActivity.class);
        mBundle.putString(AppUtils.QUOTATION_ID, quotationId);
        intent.putExtras(mBundle);
        startActivity(intent);
    }
}