package com.dmw.noble.activity_motor;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.ApiManager;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.model.AddonCover;
import com.dmw.noble.model.FilterCar;
import com.dmw.noble.model.Insurer;
import com.dmw.noble.model.InsurerList;
import com.dmw.noble.utils.AppUtils;
import com.dmw.noble.utils.String2WithTag;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AddOnCoverActivity extends AbstractActivity implements onRequestCompleteCallBackListener {

    private LinearLayout layoutAdditional, layoutDiscount;
    RelativeLayout layoutAddOn;
    TextView lblDiscount;
    private Context mContext;
    private ProgressDialog progressDialog;
    String quotationId, ownedBy, ownerChange, claimExp, ncbOld, policyExpiry,
            pACover, zeroDept, cover, tpOnly, idv, policyExpDate, antiTheft,
            vehicleModified, tpp, voluntary, voluntaryValue, memberAAI, numberAAI,
            memberName, membershipExpiryDate, electricalAccessory, electricalAccessoryValue,
            nonElectricalAccessory, nonElectricalAccValue, fuelKit, fuelKitValue, PAUnnamed,
            PAUnnamedValue, legalEmployee, legalEmployeeValue, legalDriver, legalDriverValue,
            fiber, emergencyCover, consumables, tyreCover, ncbProtection, engineProtector,
            returnInvoice, lossKey, roadAssistance, passengerCover, hydrostaticCover,
            hospitalCashCover, lossPersonal, policyType, maxIdv, minIdv, previousPolicyType,
            newVehicle, prePolicy, imtCover, vehicleType, make, modelName, fuelType, variantName,
            pcvType, pcvCompany, vehicleName, imtCover34, sbiCode, carrierType;

    Dialog vehicleDialog;

    Bundle mBundle;
    TextView txt0, txt20, txt25, txt35, txt45, txt50;

    private CheckBox chkPA, chkElAcc, chkNonElAcc, chkBiFuelAcc, chkPAUn, chkLegalEmp,
            chkLegalDriver, chkFiber, chkEmergency, chkZeroDep, chkConsumables, chkTyre, chkNCB,
            chkEngine, chkInvoice, chkLoss, chkRoad, chkPassenger, chkPersonal, chkHospital,
            chkTheft, chkModified, chkTPPD, chkVoluntary, chkAAI, chkImt, chkImt34;
    private EditText edtElAcc, edtNonElAcc, edtBiFuelAcc, edtLegalEmp, edtAAIName, edtAAINumber,
            edtAAIDate, edtIdv;
    private Spinner spnPAUn, spnLegalDriver, spnVoluntary;
    TextView txtMinIdv;
    private int totalExpDay;
    private boolean policyExpired, isNcb;
    RadioButton claimYes, claimNo;
    Spinner spnPolicyType;
    //SAOD
    private TextView txtTPPreInsurer, txtTPExpDate, txtPolicyExpDate;
    private com.toptoche.searchablespinnerlibrary.SearchableSpinner spnTPPreInsurer;
    private EditText edtPolicyNumber;
    private List<Insurer> insurerList = new ArrayList<>();
    List<String2WithTag> piList = new ArrayList<>();
    private String insurerId, previousInsurer, previousPolicyNo, tpPolicyExpDate;
    private SimpleDateFormat dateFormatter;
    private int regYear;
    private long minDate, maxDate;
    boolean isMoreThan3, isOdHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_on_cover);
        Objects.requireNonNull(getSupportActionBar()).hide();

        mContext = this;
        layoutAdditional = findViewById(R.id.llAdd);
        layoutAddOn = findViewById(R.id.lladdOn);
        layoutDiscount = findViewById(R.id.llDiscount);

        lblDiscount = findViewById(R.id.lbl_discount);

        txtTPPreInsurer = findViewById(R.id.txtPreIns);
        txtTPExpDate = findViewById(R.id.txtPEDate);
        txtPolicyExpDate = findViewById(R.id.txtTED);

        spnTPPreInsurer = findViewById(R.id.edtPreInsurer);
        edtPolicyNumber = findViewById(R.id.edtPN);

        spnPolicyType = findViewById(R.id.spnPolicyType);

        edtElAcc = findViewById(R.id.edtElAcc);
        edtNonElAcc = findViewById(R.id.edtNonElAcc);
        edtBiFuelAcc = findViewById(R.id.edtBiFuelAcc);
        edtLegalEmp = findViewById(R.id.edtLegalEmp);
        edtAAIName = findViewById(R.id.edtAAIName);
        edtAAINumber = findViewById(R.id.edtAAINumber);
        edtAAIDate = findViewById(R.id.edtAAIDate);
        edtIdv = findViewById(R.id.edtIdv);

        txt0 = findViewById(R.id.txt0);
        txt20 = findViewById(R.id.txt20);
        txt25 = findViewById(R.id.txt25);
        txt35 = findViewById(R.id.txt35);
        txt45 = findViewById(R.id.txt45);
        txt50 = findViewById(R.id.txt50);

        txtMinIdv = findViewById(R.id.minIdv);

        claimYes = findViewById(R.id.cYes);
        claimNo = findViewById(R.id.cNo);

        spnPAUn = findViewById(R.id.spnPAUn);
        spnLegalDriver = findViewById(R.id.spnLegalDriver);
        spnVoluntary = findViewById(R.id.spnVoluntary);

        chkPA = findViewById(R.id.chkPA);
        chkElAcc = findViewById(R.id.chkElAcc);
        chkNonElAcc = findViewById(R.id.chkNonElAcc);
        chkBiFuelAcc = findViewById(R.id.chkBiFuelAcc);
        chkPAUn = findViewById(R.id.chkPAUn);
        chkLegalEmp = findViewById(R.id.chkLegalEmp);
        chkLegalDriver = findViewById(R.id.chkLegalDriver);
        chkFiber = findViewById(R.id.chkFiber);
        chkEmergency = findViewById(R.id.chkEmergency);
        chkImt = findViewById(R.id.chkImt);
        chkImt34 = findViewById(R.id.chkImt34);
        chkZeroDep = findViewById(R.id.chkZeroDep);
        chkConsumables = findViewById(R.id.chkConsumables);
        chkTyre = findViewById(R.id.chkTyre);
        chkNCB = findViewById(R.id.chkNCB);
        chkEngine = findViewById(R.id.chkEngine);
        chkInvoice = findViewById(R.id.chkInvoice);
        chkLoss = findViewById(R.id.chkLoss);
        chkRoad = findViewById(R.id.chkRoad);
        chkPassenger = findViewById(R.id.chkPassenger);
        chkPersonal = findViewById(R.id.chkPersonal);
        chkHospital = findViewById(R.id.chkHospital);
        chkTheft = findViewById(R.id.chkTheft);
        chkModified = findViewById(R.id.chkModified);
        chkTPPD = findViewById(R.id.chkTPPD);
        chkVoluntary = findViewById(R.id.chkVoluntary);
        chkAAI = findViewById(R.id.chkAAI);

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Applying Filter...");

        vehicleDialog = new Dialog(mContext,
                android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        vehicleDialog.setContentView(R.layout.layout_vehicle);

        mBundle = getIntent().getExtras();
        if (mBundle != null) {
            quotationId = mBundle.getString(AppUtils.QUOTATION_ID);
            ownedBy = mBundle.getString(AppUtils.OWNED_BY);
            ownerChange = mBundle.getString(AppUtils.OWNER_CHANGE);
            claimExp = mBundle.getString(AppUtils.CLAIM_EXP);
            ncbOld = mBundle.getString(AppUtils.PRE_NCB);
            pACover = mBundle.getString(AppUtils.PA_COVER);
            zeroDept = mBundle.getString(AppUtils.ZERO_DEPT);
            cover = mBundle.getString(AppUtils.COVER);
            idv = mBundle.getString(AppUtils.IDV);
            policyExpDate = mBundle.getString(AppUtils.POLICY_EXPIRY_DATE);
            policyType = mBundle.getString(AppUtils.POLICY_TYPE);
            maxIdv = mBundle.getString(AppUtils.MAX_IDV);
            minIdv = mBundle.getString(AppUtils.MIN_IDV);
            policyExpired = mBundle.getBoolean(AppUtils.POLICY_EXPIRED);
            previousPolicyType = mBundle.getString(AppUtils.PRE_POLICY_TYPE);
            totalExpDay = mBundle.getInt(AppUtils.POLICY_EXPIRY_DAYS);
            newVehicle = mBundle.getString(AppUtils.NEW_VEHICLE);
            vehicleType = mBundle.getString(AppUtils.VEHICLE_TYPE);
            pcvCompany = mBundle.getString(AppUtils.PCV_COMPANY);
            pcvType = mBundle.getString(AppUtils.PCV_TYPE);
            sbiCode = mBundle.getString(AppUtils.SBI_GCV_CODE);
            policyExpiry = mBundle.getString(AppUtils.POLICY_EXPIRY);

            make = mBundle.getString(AppUtils.MAKE);
            fuelType = mBundle.getString(AppUtils.FUEL_TYPE);
            modelName = mBundle.getString(AppUtils.MODEL);
            variantName = mBundle.getString(AppUtils.VARIANT);

            previousInsurer = mBundle.getString(AppUtils.SAOD_INSURER, "");
            previousPolicyNo = mBundle.getString(AppUtils.SAOD_POLICY_NO, "");
            tpPolicyExpDate = mBundle.getString(AppUtils.SAOD_TP_EXP_DATE, "");

            if (!TextUtils.isEmpty(previousPolicyNo))
                edtPolicyNumber.setText(previousPolicyNo);

            if (!TextUtils.isEmpty(tpPolicyExpDate))
                txtTPExpDate.setText(tpPolicyExpDate);

            prePolicy = mBundle.getString(AppUtils.IS_PREVIOUS);
            isNcb = mBundle.getBoolean(AppUtils.IS_NCB);

            if (!TextUtils.isEmpty(maxIdv) && !TextUtils.isEmpty(minIdv)) {
                double min = Double.parseDouble(minIdv);
                double max = Double.parseDouble(maxIdv);
                if (min > max) {
                    String swap = maxIdv;
                    maxIdv = minIdv;
                    minIdv = swap;
                }
            }

            if (!TextUtils.isEmpty(minIdv))
                txtMinIdv.setText("IDV :" + minIdv + " - " + maxIdv);
            else txtMinIdv.setText("");
            edtIdv.setText(idv);

            regYear = mBundle.getInt(AppUtils.REG_YEAR);
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            isMoreThan3 = (year - regYear) > 3;

            if (!TextUtils.isEmpty(policyType)) {

                if (policyType.equalsIgnoreCase("Comprehensive")) {
                    tpOnly = "0";
                    spnPolicyType.setSelection(0);
                    findViewById(R.id.rlIdv).setVisibility(View.VISIBLE);
                } else if (policyType.contains("Third")) {
                    tpOnly = "1";
                    findViewById(R.id.rlIdv).setVisibility(View.GONE);
                    findViewById(R.id.lbl_add_on_cover).setVisibility(View.GONE);
                    findViewById(R.id.lladdOn).setVisibility(View.GONE);
                    lblDiscount.setVisibility(View.GONE);
                    layoutDiscount.setVisibility(View.GONE);
                    findViewById(R.id.ccV).setVisibility(View.GONE);
                    spnPolicyType.setSelection(1);
                } else {
                    tpOnly = "2";
                    spnPolicyType.setSelection(2);
                }
            } else tpOnly = "0";

            String tpSaodInsurer = mBundle.getString(AppUtils.SAOD_INSURER);
            edtPolicyNumber.setText(mBundle.getString(AppUtils.SAOD_POLICY_NO));
            txtTPPreInsurer.setText(tpSaodInsurer);
            txtTPExpDate.setText(mBundle.getString(AppUtils.SAOD_TP_EXP_DATE));
            txtPolicyExpDate.setText(mBundle.getString(AppUtils.SAOD_TP_EXP_DATE));
        }

        if (!TextUtils.isEmpty(ownedBy))
            if (!ownedBy.equalsIgnoreCase("Individual")) {
                chkPA.setVisibility(View.GONE);
                pACover = "0";
            }
        if (!TextUtils.isEmpty(newVehicle))
            if (newVehicle.equalsIgnoreCase("new_gadi"))
                findViewById(R.id.ccV).setVisibility(View.GONE);

        if (!TextUtils.isEmpty(prePolicy))
            if (prePolicy.equalsIgnoreCase("1")) {
                findViewById(R.id.ccV).setVisibility(View.GONE);
            }
        if (!TextUtils.isEmpty(previousPolicyType)) {
            if ((!previousPolicyType.equalsIgnoreCase("Comprehensive"))
                    || totalExpDay > 90
                    || claimExp.equalsIgnoreCase("1") || policyExpired || (!isNcb)) {

                findViewById(R.id.ccV).setVisibility(View.GONE);
            }
        }
        if (TextUtils.isEmpty(vehicleType))
            vehicleType = "";

        if (!TextUtils.isEmpty(previousPolicyType)) {
            if (isMoreThan3 || (!previousPolicyType.equalsIgnoreCase("Comprehensive"))
                    && !TextUtils.isEmpty(policyExpiry)
                    && !policyExpiry.equalsIgnoreCase("Not Expired")) {
                isOdHide = true;
            }
        }

        if ((!TextUtils.isEmpty(newVehicle) && newVehicle.equalsIgnoreCase("new_gadi"))
                || isOdHide
                || (!TextUtils.isEmpty(prePolicy) && prePolicy.equalsIgnoreCase("1"))
                || vehicleType.equals("3")
                || vehicleType.equals("4")
                || vehicleType.equals("5")) {
            List<String> spinnerArray = new ArrayList<String>();
            spinnerArray.add("Comprehensive");
            spinnerArray.add("Third Party");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, spinnerArray);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnPolicyType.setAdapter(adapter);

            if (vehicleType.equals("4") && pcvCompany.equalsIgnoreCase("tata")) {
                findViewById(R.id.rlCarrier).setVisibility(View.VISIBLE);
                carrierType = "0";
            }
        }

        if (!TextUtils.isEmpty(ncbOld)) {
            switch (ncbOld) {
                case "20":
                    setNcb20();
                    break;
                case "25":
                    setNcb25();
                    break;
                case "35":
                    setNcb35();
                    break;
                case "45":
                    setNcb45();
                    break;
                case "50":
                    setNcb50();
                    break;
                default:
                    setNcb0();
                    break;
            }
        }

        if (claimExp.equalsIgnoreCase("1")) {
            claimYes.setChecked(true);
            claimNo.setChecked(false);
            findViewById(R.id.cc).setVisibility(View.GONE);
        } else {
            claimNo.setChecked(true);
            claimYes.setChecked(false);
            findViewById(R.id.cc).setVisibility(View.VISIBLE);
        }
        claimYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.cc).setVisibility(View.GONE);
                claimExp = "1";
            }
        });
        claimNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.cc).setVisibility(View.VISIBLE);
                claimExp = "0";
            }
        });
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        insurerList = UserManager.getInstance().getInsurerList();
        if (insurerList.size() > 0)
            initPreInsurer();
        else
            getPreInsurer();
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        regYear = regYear + 2;
        calendar.set(regYear, 0, 1);
        minDate = calendar.getTimeInMillis();

        regYear = regYear + 1;
        calendar.set(regYear, 11, 31);
        maxDate = calendar.getTimeInMillis();

        calendar.set(regYear, currentMonth, currentDay);


        txtPolicyExpDate.setText(dateFormatter.format(calendar.getTime()));
        txtTPExpDate.setText(dateFormatter.format(calendar.getTime()));

        spnPolicyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    tpOnly = "0";
                    policyType = "Comprehensive";
                    findViewById(R.id.rlIdv).setVisibility(View.VISIBLE);
                    findViewById(R.id.lbl_add_on_cover).setVisibility(View.VISIBLE);
                    if (vehicleType.equals("2"))
                        lblDiscount.setVisibility(View.VISIBLE);
                    else {
                        lblDiscount.setVisibility(View.GONE);
                        layoutDiscount.setVisibility(View.GONE);
                    }
                    if (totalExpDay > 90 || claimExp.equalsIgnoreCase("1")
                            || policyExpired || (!isNcb)) {
                        findViewById(R.id.ccV).setVisibility(View.GONE);
                    } else findViewById(R.id.ccV).setVisibility(View.VISIBLE);

                    mBundle.putString(AppUtils.POLICY_TYPE, policyType);
                    mBundle.putString(AppUtils.TP_ONLY, tpOnly);

                    findViewById(R.id.rlSOD).setVisibility(View.GONE);
                } else if (position == 1) {
                    tpOnly = "1";
                    policyType = "Third Party";
                    findViewById(R.id.rlIdv).setVisibility(View.GONE);
                    findViewById(R.id.lbl_add_on_cover).setVisibility(View.GONE);
                    findViewById(R.id.lladdOn).setVisibility(View.GONE);
                    lblDiscount.setVisibility(View.GONE);
                    layoutDiscount.setVisibility(View.GONE);
                    findViewById(R.id.ccV).setVisibility(View.GONE);
                    mBundle.putString(AppUtils.TP_ONLY, tpOnly);
                    mBundle.putString(AppUtils.POLICY_TYPE, policyType);
                    findViewById(R.id.rlSOD).setVisibility(View.GONE);
                } else {
                    tpOnly = "2";
                    policyType = "StandAlone";
                    mBundle.putString(AppUtils.POLICY_TYPE, policyType);
                    mBundle.putString(AppUtils.TP_ONLY, tpOnly);
                    findViewById(R.id.rlSOD).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getCovers();

        if (vehicleType.equals("4") || vehicleType.equals("3") || vehicleType.equals("5")) {
            if (vehicleType.equals("4") || vehicleType.equals("5")) {
                chkImt.setVisibility(View.VISIBLE);
                chkImt34.setVisibility(View.VISIBLE);
            }else
                chkImt34.setVisibility(View.VISIBLE);
            findViewById(R.id.lladdOn1).setVisibility(View.GONE);
            findViewById(R.id.lbl_discount).setVisibility(View.GONE);
            layoutDiscount.setVisibility(View.GONE);
        }
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

    public void onHideSaOdClick(View view) {
        TextView tv, update;
        update = findViewById(R.id.btnUpdate);
        tv = (TextView) view;

        if (tv == update) {
            tpOnly = "2";
            policyType = "Standalone OD";
            if (isValidSaOd()) {
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

//        if (findViewById(R.id.rlSOD).getVisibility() == View.VISIBLE)
//        if (!TextUtils.isEmpty(previousInsurer) && insurerList.size() > 1) {
//            for (int i = 0; i < insurerList.size()-1; i++) {
//                String stateName1 = insurerList.get(i).getInsurer();
//                if (stateName1.equalsIgnoreCase(previousInsurer)) {
//                    spnTPPreInsurer.setSelection(i);
//                    break;
//                }
//            }
//        }
        spnTPPreInsurer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                insurerId = (String) stringWithTag.string;
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
            ((TextView) spnTPPreInsurer.getSelectedView()).setError("Select Value");
            return false;
        }
        if (TextUtils.isEmpty(previousPolicyNo)) {
            edtPolicyNumber.setError("Field can not be empty");
            return false;
        }
        mBundle.putString(AppUtils.SAOD_INSURER, previousInsurer);
        mBundle.putString(AppUtils.SAOD_POLICY_NO, previousPolicyNo);
        mBundle.putString(AppUtils.SAOD_TP_EXP_DATE, tpPolicyExpDate);
        return true;
    }

    public void onDatePicker(View view) {
        Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
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

        try {
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorPrimary));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onExpandAdditionalCover(View view) {
        TextView textView = (TextView) view;
        if (layoutAdditional.getVisibility() == View.VISIBLE) {
            layoutAdditional.setVisibility(View.GONE);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                    R.drawable.ic_expand_more, 0);
        } else {
            layoutAdditional.setVisibility(View.VISIBLE);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                    R.drawable.ic_expand_less, 0);
        }
    }

    public void onExpandAddOnCover(View view) {
        TextView textView = (TextView) view;
        if (layoutAddOn.getVisibility() == View.VISIBLE) {
            layoutAddOn.setVisibility(View.GONE);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                    R.drawable.ic_expand_more, 0);
        } else {
            layoutAddOn.setVisibility(View.VISIBLE);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                    R.drawable.ic_expand_less, 0);
        }
    }

    public void onExpandDiscount(View view) {
        TextView textView = (TextView) view;
        if (layoutDiscount.getVisibility() == View.VISIBLE) {
            layoutDiscount.setVisibility(View.GONE);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                    R.drawable.ic_expand_more, 0);
        } else {
            layoutDiscount.setVisibility(View.VISIBLE);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                    R.drawable.ic_expand_less, 0);
        }
    }

    public void onElectricalClick(View view) {
        CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked()) {
            findViewById(R.id.edtElAcc).setVisibility(View.VISIBLE);
        } else findViewById(R.id.edtElAcc).setVisibility(View.GONE);
    }

    public void onNonElectricalClick(View view) {
        CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked()) {
            findViewById(R.id.edtNonElAcc).setVisibility(View.VISIBLE);
        } else findViewById(R.id.edtNonElAcc).setVisibility(View.GONE);
    }

    public void onBiFuelAcc(View view) {
        CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked()) {
            findViewById(R.id.edtBiFuelAcc).setVisibility(View.VISIBLE);
        } else findViewById(R.id.edtBiFuelAcc).setVisibility(View.GONE);

    }

    public void onPAUn(View view) {
        CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked()) {
            findViewById(R.id.spnPAUn).setVisibility(View.VISIBLE);
        } else findViewById(R.id.spnPAUn).setVisibility(View.GONE);

    }

    public void onLegalEmp(View view) {
        CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked()) {
            findViewById(R.id.edtLegalEmp).setVisibility(View.VISIBLE);
        } else findViewById(R.id.edtLegalEmp).setVisibility(View.GONE);

    }

    public void onLegalDriver(View view) {
        CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked()) {
            findViewById(R.id.spnLegalDriver).setVisibility(View.VISIBLE);
        } else findViewById(R.id.spnLegalDriver).setVisibility(View.GONE);
    }

    public void onVoluntary(View view) {
        CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked()) {
            findViewById(R.id.spnVoluntary).setVisibility(View.VISIBLE);
        } else findViewById(R.id.spnVoluntary).setVisibility(View.GONE);

    }

    public void onAAI(View view) {
        CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked()) {
            findViewById(R.id.edtAAIName).setVisibility(View.VISIBLE);
            findViewById(R.id.edtAAINumber).setVisibility(View.VISIBLE);
            findViewById(R.id.edtAAIDate).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.edtAAIName).setVisibility(View.GONE);
            findViewById(R.id.edtAAINumber).setVisibility(View.GONE);
            findViewById(R.id.edtAAIDate).setVisibility(View.GONE);
        }
    }

    @Override
    public void onResponse(Object response) {
        try {
            if (response instanceof FilterCar) {
                FilterCar fResponse = (FilterCar) response;
                if (fResponse.getSuccess().equals("1")) {
                    String query = fResponse.getQuery();
                    if (!TextUtils.isEmpty(query))
                        AppUtils.setTokenQuote(query);
                    progressDialog.dismiss();
                    onBackPressed();
                }
                progressDialog.dismiss();
            }
            if (response instanceof AddonCover) {
                AddonCover fResponse = (AddonCover) response;
                if (fResponse.getStatus() == 1) {

                    if (!TextUtils.isEmpty(fResponse.getPaCover()))
                        if (fResponse.getPaCover().equalsIgnoreCase("1")) {
                            chkPA.setChecked(true);
                            pACover = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getElectricalAccessory()))
                        if (fResponse.getElectricalAccessory().equalsIgnoreCase("1")) {
                            chkElAcc.setChecked(true);
                            edtElAcc.setText(fResponse.getElectricalAccessoryValue());
                            electricalAccessory = "1";
                            electricalAccessoryValue = fResponse.getElectricalAccessoryValue();
                        }

                    if (!TextUtils.isEmpty(fResponse.getNonElectricalAccessory()))
                        if (fResponse.getNonElectricalAccessory().equalsIgnoreCase("1")) {
                            chkNonElAcc.setChecked(true);
                            edtNonElAcc.setText(fResponse.getNonElectricalAccessoryValue());
                            nonElectricalAccessory = "1";
                            nonElectricalAccValue = fResponse.getNonElectricalAccessoryValue();
                        }

                    if (!TextUtils.isEmpty(fResponse.getFuleKit()))
                        if (fResponse.getFuleKit().equalsIgnoreCase("1")) {
                            chkBiFuelAcc.setChecked(true);
                            edtBiFuelAcc.setText(fResponse.getFuleKitValue());
                            fuelKit = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getPacoverforUnnamedPerson()))
                        if (fResponse.getPacoverforUnnamedPerson().equalsIgnoreCase("1")) {
                            chkPAUn.setChecked(true);
                            PAUnnamed = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getLegalLiabilityEmployee()))
                        if (fResponse.getLegalLiabilityEmployee().equalsIgnoreCase("1")) {
                            legalEmployee = "1";
                            legalEmployeeValue = fResponse.getLegalLiabilityEmployee();
                        }

                    if (!TextUtils.isEmpty(fResponse.getLegalLiabilityPaidDriver()))
                        if (fResponse.getLegalLiabilityPaidDriver().equalsIgnoreCase("1")) {
                            chkLegalDriver.setChecked(true);
                            legalDriver = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getLegalLiabilityEmployee()))
                        if (fResponse.getLegalLiabilityEmployee().equalsIgnoreCase("1")) {
                            chkLegalDriver.setChecked(true);
                            legalDriver = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getFiberGlassFuelTank()))
                        if (fResponse.getFiberGlassFuelTank().equalsIgnoreCase("1")) {
                            chkFiber.setChecked(true);
                            fiber = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getZeroDept()))
                        if (fResponse.getZeroDept().equalsIgnoreCase("1")) {
                            chkZeroDep.setChecked(true);
                            zeroDept = "1";
                        }
                    if (!TextUtils.isEmpty(fResponse.getImt23()))
                        if (fResponse.getImt23().equals("1")) {
                            chkImt.setChecked(true);
                            imtCover = "1";
                        }
                    if (!TextUtils.isEmpty(fResponse.getImt34()))
                        if (fResponse.getImt34().equals("1")) {
                            chkImt34.setChecked(true);
                            imtCover34 = "1";
                        }
                    if (!TextUtils.isEmpty(fResponse.getEmergencyCover()))
                        if (fResponse.getEmergencyCover().equalsIgnoreCase("1")) {
                            chkEmergency.setChecked(true);
                            emergencyCover = "1";
                        }


                    if (!TextUtils.isEmpty(fResponse.getConsumables()))
                        if (fResponse.getConsumables().equalsIgnoreCase("1")) {
                            chkConsumables.setChecked(true);
                            consumables = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getTyreCover()))
                        if (fResponse.getTyreCover().equalsIgnoreCase("1")) {
                            chkTyre.setChecked(true);
                            tyreCover = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getNcbProtection()))
                        if (fResponse.getNcbProtection().equalsIgnoreCase("1")) {
                            chkNCB.setChecked(true);
                            ncbProtection = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getEngineProtector()))
                        if (fResponse.getEngineProtector().equalsIgnoreCase("1")) {
                            chkEngine.setChecked(true);
                            engineProtector = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getReturnInvoice()))
                        if (fResponse.getReturnInvoice().equalsIgnoreCase("1")) {
                            chkInvoice.setChecked(true);
                            returnInvoice = "1";
                        }


                    if (!TextUtils.isEmpty(fResponse.getLossOfKey()))
                        if (fResponse.getLossOfKey().equalsIgnoreCase("1")) {
                            chkLoss.setChecked(true);
                            lossKey = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getRoadSideAssistance()))
                        if (fResponse.getRoadSideAssistance().equalsIgnoreCase("1")) {
                            chkRoad.setChecked(true);
                            roadAssistance = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getPassengerAssistCover()))
                        if (fResponse.getPassengerAssistCover().equalsIgnoreCase("1")) {
                            chkPassenger.setChecked(true);
                            passengerCover = "1";
                        }
                    if (!TextUtils.isEmpty(fResponse.getHospitalCashCover()))
                        if (fResponse.getHospitalCashCover().equalsIgnoreCase("1")) {
                            chkHospital.setChecked(true);
                            hospitalCashCover = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getAntiTheftDevice()))
                        if (fResponse.getAntiTheftDevice().equalsIgnoreCase("1")) {
                            chkTheft.setChecked(true);
                            antiTheft = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getVehicleModifiedForHandicap()))
                        if (fResponse.getVehicleModifiedForHandicap().equalsIgnoreCase("1")) {
                            chkModified.setChecked(true);
                            vehicleModified = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getVoluntaryExcess()))
                        if (fResponse.getVoluntaryExcess().equalsIgnoreCase("1")) {
                            chkVoluntary.setChecked(true);
                            voluntary = "1";
                            voluntaryValue = fResponse.getVoluntaryExcessValue();
                        }

                    if (!TextUtils.isEmpty(fResponse.getMemberOfAai()))
                        if (fResponse.getMemberOfAai().equalsIgnoreCase("1")) {
                            chkAAI.setChecked(true);
                            edtAAIName.setText(fResponse.getMemberName());
                            edtAAIDate.setText(fResponse.getMembershipExpiryDate());
                            memberAAI = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getCarrierType())) {
                        carrierType = fResponse.getCarrierType();
                        if (carrierType.equals("1")) {
                            RadioButton eg = findViewById(R.id.epNo);
                            eg.setChecked(true);
                        }
                    }
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        onBack();
    }

    private void onBack() {
        Intent intent = new Intent();
        intent.putExtra(AppUtils.IS_FILTER, "1");
        intent.putExtra(AppUtils.PRE_NCB, ncbOld);
        intent.putExtra(AppUtils.CLAIM_EXP, claimExp);
        intent.putExtra(AppUtils.UPDATED_IDV, idv);

        intent.putExtra(AppUtils.VEHICLE_FULL, vehicleName);
        intent.putExtra(AppUtils.MAKE, make);
        intent.putExtra(AppUtils.MODEL, modelName);
        intent.putExtra(AppUtils.FUEL_TYPE, fuelType);
        intent.putExtra(AppUtils.VARIANT, variantName);

        mBundle.putString(AppUtils.VEHICLE_FULL, vehicleName);
        mBundle.putString(AppUtils.MAKE, make);
        mBundle.putString(AppUtils.MODEL, modelName);
        mBundle.putString(AppUtils.FUEL_TYPE, fuelType);
        mBundle.putString(AppUtils.VARIANT, variantName);

        intent.putExtra(AppUtils.SAOD_INSURER, previousInsurer);
        intent.putExtra(AppUtils.SAOD_POLICY_NO, previousPolicyNo);
        intent.putExtra(AppUtils.SAOD_TP_EXP_DATE, tpPolicyExpDate);
        intent.putExtras(mBundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void applyFilter() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                if (!TextUtils.isEmpty(ownedBy))
                    if (!ownedBy.equalsIgnoreCase("Individual")) {
                        chkPA.setVisibility(View.GONE);
                        pACover = "0";
                    }

                ApiManager.getInstance().applyFilterCar(mContext, quotationId, ownedBy, ownerChange,
                        claimExp, ncbOld, pACover, zeroDept, imtCover, imtCover34, cover, tpOnly,
                        idv, policyExpDate, antiTheft, vehicleModified, tpp, voluntary,
                        voluntaryValue, memberAAI, numberAAI, memberName, membershipExpiryDate,
                        electricalAccessory, electricalAccessoryValue, nonElectricalAccessory,
                        nonElectricalAccValue, fuelKit, fuelKitValue, PAUnnamed, PAUnnamedValue,
                        legalEmployee, legalEmployeeValue, legalDriver, legalDriverValue, fiber,
                        emergencyCover, consumables, tyreCover, ncbProtection, engineProtector,
                        returnInvoice, lossKey, roadAssistance, passengerCover, hydrostaticCover,
                        hospitalCashCover, lossPersonal, previousInsurer, previousPolicyNo,
                        tpPolicyExpDate, make, modelName, fuelType, variantName, pcvCompany,
                        sbiCode, carrierType);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    private boolean getValues() {
        if (chkPA.isChecked() && (chkPA.getVisibility() == View.VISIBLE))
            pACover = "1";
        else pACover = "0";

        if (chkElAcc.isChecked()) {
            nonElectricalAccessory = "1";
            electricalAccessoryValue = edtElAcc.getText().toString();
        } else {
            nonElectricalAccessory = "0";
            electricalAccessoryValue = "0";
        }

        if (chkNonElAcc.isChecked()) {
            electricalAccessory = "1";
            nonElectricalAccValue = edtNonElAcc.getText().toString();
        } else {
            electricalAccessory = "0";
            nonElectricalAccValue = "0";
        }

        if (chkBiFuelAcc.isChecked()) {
            fuelKit = "1";
            fuelKitValue = edtBiFuelAcc.getText().toString();
        } else {
            fuelKit = "0";
            fuelKitValue = "0";
        }

        if (chkPAUn.isChecked()) {
            PAUnnamed = "1";
            PAUnnamedValue = spnPAUn.getSelectedItem().toString();
        } else {
            PAUnnamed = "0";
            PAUnnamedValue = "0";
        }

        if (chkLegalEmp.isChecked()) {
            legalEmployee = "1";
            legalEmployeeValue = edtLegalEmp.getText().toString();
        } else {
            legalEmployee = "0";
            legalEmployeeValue = "0";
        }

        if (chkLegalDriver.isChecked()) {
            legalDriver = "1";
            legalDriverValue = spnLegalDriver.getSelectedItem().toString();
        } else {
            legalDriver = "0";
            legalDriverValue = "0";
        }

        if (chkFiber.isChecked())
            fiber = "1";
        else fiber = "0";

        if (chkEmergency.isChecked())
            emergencyCover = "1";
        else emergencyCover = "0";

        if (chkImt.isChecked())
            imtCover = "1";
        else imtCover = "0";

        if (chkImt34.isChecked())
            imtCover34 = "1";
        else imtCover34 = "0";

        if (chkZeroDep.isChecked())
            zeroDept = "1";
        else zeroDept = "0";

        if (chkConsumables.isChecked())
            consumables = "1";
        else consumables = "0";

        if (chkTyre.isChecked())
            tyreCover = "1";
        else tyreCover = "0";

        if (chkNCB.isChecked())
            ncbProtection = "1";
        else ncbProtection = "0";

        if (chkEngine.isChecked())
            engineProtector = "1";
        else engineProtector = "0";

        if (chkInvoice.isChecked())
            returnInvoice = "1";
        else returnInvoice = "0";

        if (chkLoss.isChecked())
            lossKey = "1";
        else lossKey = "0";

        if (chkRoad.isChecked())
            roadAssistance = "1";
        else roadAssistance = "0";

        if (chkPassenger.isChecked())
            passengerCover = "1";
        else passengerCover = "0";

        if (chkPersonal.isChecked())
            lossPersonal = "1";
        else lossPersonal = "0";

        if (chkHospital.isChecked())
            hospitalCashCover = "1";
        else hospitalCashCover = "0";

        if (chkTheft.isChecked())
            antiTheft = "1";
        else antiTheft = "0";

        if (chkModified.isChecked())
            vehicleModified = "1";
        else vehicleModified = "0";

        if (chkTPPD.isChecked())
            tpp = "1";
        else tpp = "0";

        if (chkVoluntary.isChecked()) {
            voluntary = "1";
            voluntaryValue = spnVoluntary.getSelectedItem().toString();
        } else {
            voluntary = "0";
            voluntaryValue = "0";
        }

        if (chkAAI.isChecked()) {
            memberAAI = "1";
            numberAAI = edtAAINumber.getText().toString();
            memberName = edtAAIName.getText().toString();
            membershipExpiryDate = edtAAIDate.getText().toString();
        } else {
            memberAAI = "0";
            memberName = "0";
            membershipExpiryDate = "0";
        }

        hydrostaticCover = "0";
        cover = "0";
        if (!TextUtils.isEmpty(newVehicle))
            if (newVehicle.equalsIgnoreCase("new_gadi"))
                cover = "1";
        if (tpOnly.equalsIgnoreCase("0") || tpOnly.equalsIgnoreCase("2")) {
            idv = edtIdv.getText().toString().trim();
            if (!TextUtils.isEmpty(idv))
                if (!TextUtils.isEmpty(maxIdv)) {
                    if (Double.parseDouble(maxIdv) >= Double.parseDouble(idv) &&
                            Double.parseDouble(minIdv) <= Double.parseDouble(idv)) {

                        edtIdv.setText(idv);

                    } else {
                        edtIdv.setError("IDV must be in minimum and maximum");
                        return false;
                    }
                }
//            if (!TextUtils.isEmpty(maxIdv))
//                idv = maxIdv;
        }

        return true;
    }

    public void onApplyFilter(View view) {
        if (getValues())
            if (tpOnly.equalsIgnoreCase("2")) {
                if (isValidSaOd())
                    applyFilter();
            } else applyFilter();
    }

    public void onEditSaOd(View view) {
        findViewById(R.id.llSAOD).setVisibility(View.VISIBLE);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void onNcbTextClick(View view) {
        if (view == txt20) setNcb20();
        else if (view == txt25) setNcb25();
        else if (view == txt35) setNcb35();
        else if (view == txt45) setNcb45();
        else if (view == txt50) setNcb50();
        else setNcb0();
    }

    private void setNcb0() {
        ncbOld = "0";
        txt0.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.selected_border, null));
        txt20.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt25.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt35.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt45.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt50.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        AppUtils.bounceAnim(txt0, 300);
    }

    private void setNcb20() {
        ncbOld = "20";
        txt0.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt20.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.selected_border, null));
        txt25.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt35.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt45.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt50.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        AppUtils.bounceAnim(txt20, 300);
    }

    private void setNcb25() {
        ncbOld = "25";
        txt0.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt20.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt25.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.selected_border, null));
        txt35.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt45.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt50.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        AppUtils.bounceAnim(txt25, 300);
    }

    private void setNcb35() {
        ncbOld = "35";
        txt0.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt20.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt25.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt35.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.selected_border, null));
        txt45.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt50.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        AppUtils.bounceAnim(txt35, 300);
    }

    private void setNcb45() {
        ncbOld = "45";
        txt0.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt20.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt25.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt35.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt45.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.selected_border, null));
        txt50.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        AppUtils.bounceAnim(txt45, 300);
    }

    private void setNcb50() {
        ncbOld = "50";
        txt0.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt20.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt25.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt35.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt45.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt50.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.selected_border, null));
        AppUtils.bounceAnim(txt50, 300);
    }

    public void onPrivateCarrier(View view) {
        carrierType = "1";
    }

    public void onPublicCarrier(View view) {
        carrierType = "0";
    }
}