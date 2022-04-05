package com.dmw.noble.activity_crm;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.CrmManager;
import com.dmw.noble.model_crm.Claim;
import com.dmw.noble.model_crm.ClaimPincode;
import com.dmw.noble.model_crm.CreateClaim;
import com.dmw.noble.utils.AppUtils;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class NewClaimActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {

    Context mContext;
    SimpleDateFormat dateFormatter;
    String strDateTime, cityName, stateName, districtName;

    Spinner spnPolicyType, spnLossType, spnIntimated, spnSurvey, spnClaimIntimatedBy, spnFir,
            spnTpLossType, spnSpotSurvey;
    EditText edtLossDate, edtIntimatedDate, edtSurveyDate, edtPhone, edtSurveyorName, edtLateReason,
            edtSurveyorPhone, edtLandmark, edtPlaceOfAccident, edtAccidentalPinCode, edtMail,
            edtIntimationNo, edtIntimationContactNo, edtIntimatorAlt, edtIntWhatsNo, edtEstAmt,
            edtFirRemark, edtTpRemark, edtSEmail, edtDriverName, edtDriverContact, edtDriverDL,
            edtSpotSurveyDate, edtSpotSurveyorName, edtSpotSurveyorPhone, edtSpotLocation,
            edtSpotPincode, txtSelected, edtIntimatorName, edt1, edt2, edt3, edt4, edtCustomerMail,
            edtCustomerPhone;

    String userId, userType, quoteId, companyName, policyType, lossType, lossDate, surveyMail,
            placeOfAccident, nearByLandMark, accidentalPincode, pincode, stateId, districtId, cityId,
            intimatedToInsurer, intimatedDate, claimIntimationNo, survey, intContact, intAltContact,
            surveyDate, surveyorName, surveyPhone, surveyDoc, spotSurveyTime, intWhatsApp, mPin,
            estimateAmt, fir, firRemark, surveyorMail, tpLoss, tpLossRemark, driverName, spotPincode,
            driverContact, driverDL, spotSurveyDate, spotSurveyPhone, spotSurveyLocation, delayReason,
            spotSurvey, spotSurveyPincode, spotStateId, spotDistrict, spotCity, intimatedBy,
            intimatedTime, intimatorName, intimatorMail, lossTime, surveyTime, surveyType, regNo,
            customerPhone, customerEmail;

    ProgressDialog progressdialog;
    TextView txtDistrict, txtState, txtCity, txtSpotState, txtSpotDistrict, txtSpotCity;
    SharedPreferences preferences;

    Bundle mBundle;
    Claim claimObj;
    int pinTag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_claim);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait...");

        final Intent intent = getIntent();
        claimObj = (Claim) intent.getSerializableExtra("claim");

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        initValues();

        mBundle = getIntent().getExtras();

        spnPolicyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                if (index == 1) {
                    findViewById(R.id.layoutDriver).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.layoutDriver).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnSurvey.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                if (index == 1) {
                    findViewById(R.id.txtSurveyDate).setVisibility(View.GONE);
                    findViewById(R.id.txtName).setVisibility(View.GONE);
                    findViewById(R.id.txtSurveyorMobile).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.txtSurveyDate).setVisibility(View.VISIBLE);
                    findViewById(R.id.txtName).setVisibility(View.VISIBLE);
                    findViewById(R.id.txtSurveyorMobile).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edtAccidentalPinCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        edtAccidentalPinCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 6) {
                    mPin = edtAccidentalPinCode.getText().toString();
                    pinTag = 0;
                    getPincode();
                } else {
                    txtCity.setText("");
                    txtDistrict.setText("");
                    txtState.setText("");
                }
            }
        });

        edtSpotPincode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        edtSpotPincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 6) {
                    mPin = edtSpotPincode.getText().toString();
                    pinTag = 1;
                    getPincode();
                } else {
                    txtSpotCity.setText("");
                    txtSpotDistrict.setText("");
                    txtSpotState.setText("");
                }
            }
        });

        if (claimObj != null) {
            quoteId = claimObj.getSrID();
            companyName = claimObj.getCompanyId();
        }
    }

    private void initValues() {
        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");

        spnPolicyType = findViewById(R.id.spnPolicyType);
        spnLossType = findViewById(R.id.spnLossType);
        spnIntimated = findViewById(R.id.spnIntimated);
        spnClaimIntimatedBy = findViewById(R.id.spnClaimIntimatedBy);
        spnSurvey = findViewById(R.id.spnSurvey);
        spnFir = findViewById(R.id.spnFir);
        spnTpLossType = findViewById(R.id.spnTpLossType);
        spnSpotSurvey = findViewById(R.id.spnSpotSurvey);

        edtLossDate = findViewById(R.id.edtLossDat);
        edtIntimatedDate = findViewById(R.id.edtIntimated);
        edtSurveyDate = findViewById(R.id.edtSurvey);
        edtSurveyorName = findViewById(R.id.edtSurveyorName);
        edtSurveyorPhone = findViewById(R.id.edtSurveyorPhone);

        edtPhone = findViewById(R.id.edtPhone);
        edtLandmark = findViewById(R.id.edtLandmark);
        edtPlaceOfAccident = findViewById(R.id.edtPlaceOfAccident);
        edtAccidentalPinCode = findViewById(R.id.edtAccidentalPinCode);
        edtIntimationNo = findViewById(R.id.edtIntimationNo);
        edtIntimationContactNo = findViewById(R.id.edtIntimationContactNo);
        edtIntimatorAlt = findViewById(R.id.edtIntimatorAlt);
        edtIntWhatsNo = findViewById(R.id.edtIntWhatsNo);
        edtMail = findViewById(R.id.edtMail);
        edtEstAmt = findViewById(R.id.edtEstAmt);
        edtLateReason = findViewById(R.id.edtLateReason);
        edtFirRemark = findViewById(R.id.edtFirRemark);
        edtTpRemark = findViewById(R.id.edtTpRemark);
        edtSEmail = findViewById(R.id.edtSEmail);
        edtDriverName = findViewById(R.id.edtDriverName);
        edtDriverContact = findViewById(R.id.edtDriverContact);
        edtDriverDL = findViewById(R.id.edtDriverDL);
        edtSpotSurveyDate = findViewById(R.id.edtSpotSurveyD);
        edtSpotSurveyorName = findViewById(R.id.edtSpotSurveyorName);
        edtSpotSurveyorPhone = findViewById(R.id.edtSpotSurveyorPhone);
        edtSpotLocation = findViewById(R.id.edtSpotLocation);
        edtSpotPincode = findViewById(R.id.edtSpotPincode);
        edtIntimatorName = findViewById(R.id.edtIntimatorName);

        txtState = findViewById(R.id.txtState);
        txtDistrict = findViewById(R.id.txtDistrict);
        txtCity = findViewById(R.id.txtCity);

        txtSpotState = findViewById(R.id.txtSpotState);
        txtSpotDistrict = findViewById(R.id.txtSpotDistrict);
        txtSpotCity = findViewById(R.id.txtSpotCity);

        edt1 = findViewById(R.id.edt1);
        edt2 = findViewById(R.id.edt2);
        edt3 = findViewById(R.id.edt3);
        edt4 = findViewById(R.id.edt4);

        edtCustomerPhone = findViewById(R.id.edtCustomerPhone);
        edtCustomerMail = findViewById(R.id.edtCustomerMail);
    }

    public void onEdtDatePicker(View view) {
        txtSelected = (EditText) view;
        txtSelected.setError(null);

        Calendar mCurrentTime = Calendar.getInstance();
        int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);//202115715335
        int minute = mCurrentTime.get(Calendar.MINUTE);
        int year = mCurrentTime.get(Calendar.YEAR);
        int month = mCurrentTime.get(Calendar.MONTH);
        int day = mCurrentTime.get(Calendar.DAY_OF_WEEK);

        DatePickerDialog datePicker = new DatePickerDialog(mContext, (view1, year1, month1, dayOfMonth) -> {
            String d, m;
            month1 = month1 + 1;
            if (month1 < 10)
                m = "0" + month1;
            else m = String.valueOf(month1);
            if (dayOfMonth < 10)
                d = "0" + dayOfMonth;
            else d = String.valueOf(dayOfMonth);
            strDateTime = "" + d + "-" + m + "-" + year1;
            TimePickerDialog timePicker = new TimePickerDialog(mContext, (view11, hourOfDay, minute1) -> {
                String time = getTime(hourOfDay, minute1);

                if (txtSelected == edtIntimatedDate) {
                    intimatedTime = time;
                    intimatedDate = strDateTime;
                } else if (txtSelected == edtLossDate) {
                    lossTime = time;
                    lossDate = strDateTime;
                } else if (txtSelected == edtSpotSurveyDate) {
                    spotSurveyTime = time;
                    spotSurveyDate = strDateTime;
                } else if (txtSelected == edtSurveyDate) {
                    surveyTime = time;
                    surveyDate = strDateTime;
                }

                strDateTime = strDateTime + " " + time;
                txtSelected.setText(strDateTime);
            }, hour, minute, false);
            timePicker.setTitle("Select Time");
            timePicker.show();
        }, year, month, day);
        datePicker.setTitle("Select Date");
        datePicker.show();
        try {
            datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
            datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorPrimary));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTime(int hr, int min) {
        Time tme = new Time(hr, min, 0);
        Format formatter;
        formatter = new SimpleDateFormat("hh:mm a", Locale.US);
        return formatter.format(tme);
    }

    public boolean isValid() {

        String reg1, reg2, reg3, reg4;

        policyType = spnPolicyType.getSelectedItem().toString();
        lossType = spnLossType.getSelectedItem().toString();
        intimatedToInsurer = spnIntimated.getSelectedItem().toString();
        survey = spnSurvey.getSelectedItem().toString();
        fir = spnFir.getSelectedItem().toString();
        tpLoss = spnTpLossType.getSelectedItem().toString();
        firRemark = edtFirRemark.getText().toString();
        surveyorMail = edtSEmail.getText().toString();
        tpLossRemark = edtTpRemark.getText().toString();
        driverName = edtDriverName.getText().toString();
        driverContact = edtDriverContact.getText().toString();
        driverDL = edtDriverDL.getText().toString();
        spotSurveyPhone = edtSpotSurveyorPhone.getText().toString();
        spotSurveyLocation = edtSpotLocation.getText().toString();
        spotSurvey = spnSpotSurvey.getSelectedItem().toString();
        spotSurveyPincode = edtSpotPincode.getText().toString();

        placeOfAccident = edtPlaceOfAccident.getText().toString();
        nearByLandMark = edtLandmark.getText().toString();
        accidentalPincode = edtAccidentalPinCode.getText().toString();
        pincode = edtAccidentalPinCode.getText().toString();

        //Intimation
        claimIntimationNo = edtIntimationNo.getText().toString();
        intContact = edtIntimationContactNo.getText().toString();
        intAltContact = edtIntimatorAlt.getText().toString();
        intWhatsApp = edtIntWhatsNo.getText().toString();
        estimateAmt = edtEstAmt.getText().toString();
        intimatorMail = edtMail.getText().toString();
        intimatorName = edtIntimatorName.getText().toString();
        delayReason = edtLateReason.getText().toString();

        intimatedBy = spnClaimIntimatedBy.getSelectedItem().toString();

        surveyorName = edtSurveyorName.getText().toString();
        surveyPhone = edtSurveyorPhone.getText().toString();
        surveyMail = edtSEmail.getText().toString();

        customerPhone = edtCustomerPhone.getText().toString();
        customerEmail = edtCustomerMail.getText().toString();

        reg1 = edt1.getText().toString();
        reg2 = edt2.getText().toString();
        reg3 = edt3.getText().toString();
        reg4 = edt4.getText().toString();


        int p = 0;
        if (TextUtils.isEmpty(lossDate)) {
            ++p;
            edtLossDate.setError(getString(R.string.field_cannot));
        } else {
            lossDate = AppUtils.yMDH(lossDate);
        }

        if (TextUtils.isEmpty(intContact)) {
            ++p;
            edtIntimationContactNo.setError(getString(R.string.field_cannot));
        } else if (!AppUtils.isValidMobile(intContact)) {
            ++p;
            edtIntimationContactNo.setError(getString(R.string.invalid_phone));
        } else if (TextUtils.isEmpty(intWhatsApp)) {
            ++p;
            edtIntWhatsNo.setError(getString(R.string.field_cannot));
        } else if (!AppUtils.isValidMobile(intWhatsApp)) {
            ++p;
            edtIntWhatsNo.setError(getString(R.string.invalid_phone));
        } else if (TextUtils.isEmpty(intimatorMail)) {
            ++p;
            edtMail.setError(getString(R.string.field_cannot));
        } else if (!AppUtils.isValidMail(intimatorMail)) {
            ++p;
            edtMail.setError(getString(R.string.invalid_email));
        } else if (TextUtils.isEmpty(intimatorName)) {
            ++p;
            edtIntimatorName.setError(getString(R.string.field_cannot));
        } else if (TextUtils.isEmpty(reg1)) {
            ++p;
            edt1.setError(getString(R.string.field_cannot));
        } else if (TextUtils.isEmpty(reg2)) {
            ++p;
            edt2.setError(getString(R.string.field_cannot));
        } else if (TextUtils.isEmpty(reg3)) {
            ++p;
            edt3.setError(getString(R.string.field_cannot));
        } else if (TextUtils.isEmpty(reg4)) {
            ++p;
            edt4.setError(getString(R.string.field_cannot));
        } else if (TextUtils.isEmpty(customerPhone)) {
            ++p;
            edtCustomerPhone.setError(getString(R.string.field_cannot));
        } else if (!AppUtils.isValidMobile(customerPhone)) {
            ++p;
            edtCustomerPhone.setError(getString(R.string.invalid_phone));
        } else if (TextUtils.isEmpty(customerEmail)) {
            ++p;
            edtCustomerMail.setError(getString(R.string.field_cannot));
        } else if (!AppUtils.isValidMail(customerEmail)) {
            ++p;
            edtCustomerMail.setError(getString(R.string.invalid_email));
        }

        if (TextUtils.isEmpty(intimatedDate)) {
            ++p;
            edtIntimatedDate.setError(getString(R.string.field_cannot));
        } else {
            intimatedDate = AppUtils.yMDH(intimatedDate);
        }

        if (TextUtils.isEmpty(claimIntimationNo)) {
            ++p;
            edtIntimationNo.setError(getString(R.string.field_cannot));
        }

        regNo = reg1 + "" + reg2 + "" + reg3 + "" + reg4;
        return p == 0;
    }

    public void createClaim() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                CrmManager.getInstance().raiseClaimRequest(mContext, userId, userType, quoteId,
                        companyName, intimatedToInsurer, intimatedBy, intimatedTime, intimatedDate,
                        claimIntimationNo, intimatorName, intContact, intAltContact, intWhatsApp,
                        intimatorMail, delayReason, policyType, lossType, lossDate, lossTime,
                        estimateAmt, placeOfAccident, nearByLandMark, pincode, fir, firRemark,
                        tpLoss, tpLossRemark, driverName, driverContact, driverDL, spotSurvey,
                        spotSurveyDate, spotSurveyTime, spotSurvey, spotSurveyPhone,
                        spotSurveyLocation, spotPincode, survey, surveyDate, surveyTime,
                        surveyorName, surveyPhone, surveyMail, surveyType, regNo, customerPhone,
                        customerEmail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getPincode() {
        if (AppUtils.isOnline(mContext)) {
            try {
                CrmManager.getInstance().getCrmPincode(mContext, mPin);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof ClaimPincode) {
            ClaimPincode cResponse = (ClaimPincode) response;
            if (cResponse.getStatus()) {
                if (pinTag == 0) {
                    pincode = cResponse.getPinCode();
                    stateId = cResponse.getStateIDPK();
                    districtId = cResponse.getDistrictIDPK();
                    cityId = cResponse.getCityOrVillageIDPK();

                    stateName = cResponse.getStateName();
                    txtState.setText(stateName);

                    districtName = cResponse.getDistrictName();
                    txtDistrict.setText(districtName);

                    cityName = cResponse.getCityOrVillageName();
                    txtCity.setText(cityName);
                } else {
                    spotPincode = cResponse.getPinCode();
                    spotStateId = cResponse.getStateIDPK();
                    spotDistrict = cResponse.getDistrictIDPK();
                    spotCity = cResponse.getCityOrVillageIDPK();

                    txtSpotState.setText(cResponse.getStateName());
                    txtSpotDistrict.setText(cResponse.getDistrictName());
                    txtSpotCity.setText(cResponse.getCityOrVillageName());
                }
            }
        }
        if (response instanceof CreateClaim) {
            CreateClaim claimResponse = (CreateClaim) response;
            if (claimResponse.getStatus()) {
                Toast.makeText(mContext, "Claim Created Successfully",
                        Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(mContext, "Error! Please try again later ",
                        Toast.LENGTH_LONG).show();
            }
            progressdialog.dismiss();
            finish();
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

    public void onCreateClaim(View view) {
        if (isValid())
            createClaim();
    }
}