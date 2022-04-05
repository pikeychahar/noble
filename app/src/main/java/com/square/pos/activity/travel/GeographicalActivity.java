package com.square.pos.activity.travel;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.activity.PrivacyActivity;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.TravelManager;
import com.square.pos.model_travel.Country;
import com.square.pos.model_travel.CountryList;
import com.square.pos.model_travel.TravelQuote;
import com.square.pos.utils.AppUtils;
import com.square.pos.utils.SearchableSpinner;
import com.square.pos.utils.String2WithTag;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by Prahalad Kumar Chahar
 */
public class GeographicalActivity extends AbstractActivity implements
        onRequestCompleteCallBackListener {

    TextView txtIndividual, txtFamily, txtGroup, selectedTextView, fromDate, toDate;
    Context mContext;
    SimpleDateFormat dateFormatter;
    String planId, self, member2, member3, member4, member5, member6, member7, countyId, qId,
            sumInsured, startDate, endDate, name, mobile, email, userId, userType;
    int planType;
    boolean isIndividual, isFamily, isGroup;
    EditText edtMember1, edtMember2, edtMember3, edtMember4, edtMember5, edtMember6,
            edtMember7;
    TextView txtAge1, txtAge2, txtAge3, txtAge4, txtAge5, txtAge6, txtAge7;
    List<Country> countriesList = new ArrayList<>();
    List<String2WithTag> countries = new ArrayList<>();
    SearchableSpinner spnCountry;
    Bundle mBundle;
    ProgressDialog progressDialog;
    Spinner spnSumInsured;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geographical);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        name = preferences.getString(AppUtils.NAME, "");
        email = preferences.getString(AppUtils.EMAIL, "");
        mobile = preferences.getString(AppUtils.MOBILE, "");
        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");

        mContext = this;
        mBundle = getIntent().getExtras();

        if (mBundle != null) {
            name = mBundle.getString(AppUtils.HL_Name);
            email = mBundle.getString(AppUtils.HL_EMAIL);
            mobile = mBundle.getString(AppUtils.HL_PHONE);
        } else {
            mBundle = new Bundle();
        }

        txtIndividual = findViewById(R.id.lblIndividual);
        txtFamily = findViewById(R.id.lblFamily);
        txtGroup = findViewById(R.id.lblGroup);

        fromDate = findViewById(R.id.fromDate);
        toDate = findViewById(R.id.toDate);
        spnCountry = findViewById(R.id.spnCountry);

        spnSumInsured = findViewById(R.id.spnSumInsured);

        edtMember1 = findViewById(R.id.edtMember1);
        edtMember2 = findViewById(R.id.edtMember2);
        edtMember3 = findViewById(R.id.edtMember3);
        edtMember4 = findViewById(R.id.edtMember4);
        edtMember5 = findViewById(R.id.edtMember5);
        edtMember6 = findViewById(R.id.edtMember6);
        edtMember7 = findViewById(R.id.edtMember7);

        txtAge1 = findViewById(R.id.txtAge1);
        txtAge2 = findViewById(R.id.txtAge2);
        txtAge3 = findViewById(R.id.txtAge3);
        txtAge4 = findViewById(R.id.txtAge4);
        txtAge5 = findViewById(R.id.txtAge5);
        txtAge6 = findViewById(R.id.txtAge6);
        txtAge7 = findViewById(R.id.txtAge7);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        spnCountry.setTitle("Select County");

        txtAge1.setText("Self Age");
        txtAge2.setText("Spouse Age");
        txtAge3.setText("Child1 Age");
        txtAge4.setText("Child2 Age");
        txtAge5.setText("Child3 Age");
        txtAge6.setText("Father Age");
        txtAge7.setText("Mother Age");

        planType = 1;
        sumInsured = "";

        isFamily = true;
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");

        getCountry();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void onIndividualClick(View view) {
        planType = 3;
        txtIndividual.setBackground(getDrawable(R.drawable.dark_theme));
        txtIndividual.setTextColor(Color.WHITE);

        txtFamily.setBackground(getDrawable(R.drawable.light_theme));
        txtFamily.setTextColor(getColor(R.color.colorPrimaryDark));
        txtGroup.setBackground(getDrawable(R.drawable.light_theme));
        txtGroup.setTextColor(getColor(R.color.colorPrimaryDark));
        findViewById(R.id.lLay1).setVisibility(View.GONE);
        findViewById(R.id.lVal1).setVisibility(View.GONE);
        findViewById(R.id.lLay2).setVisibility(View.GONE);
        findViewById(R.id.lVal2).setVisibility(View.GONE);
        txtAge1.setText("Self Age");

        isIndividual = true;
        isFamily = false;
        isGroup = false;

        removeValues();
    }

    private void removeValues() {
        edtMember1.setText("");
        edtMember2.setText("");
        edtMember3.setText("");
        edtMember4.setText("");
        edtMember5.setText("");
        edtMember6.setText("");
        edtMember7.setText("");
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void onFamilyClick(View view) {
        planType = 1;
        txtFamily.setBackground(getDrawable(R.drawable.dark_theme));
        txtFamily.setTextColor(Color.WHITE);

        txtIndividual.setBackground(getDrawable(R.drawable.light_theme));
        txtIndividual.setTextColor(getColor(R.color.colorPrimaryDark));
        txtGroup.setBackground(getDrawable(R.drawable.light_theme));
        txtGroup.setTextColor(getColor(R.color.colorPrimaryDark));

//        findViewById(R.id.rlFamily).setVisibility(View.VISIBLE);
        txtAge1.setText("Self Age");
        txtAge2.setText("Spouse Age");
        txtAge3.setText("Child1 Age");
        txtAge3.setText("Child2 Age");
        txtAge4.setText("Child3 Age");
        txtAge5.setText("Father Age");
        txtAge6.setText("Mother Age");

        edtMember6.setVisibility(View.VISIBLE);
        edtMember7.setVisibility(View.VISIBLE);
        txtAge6.setVisibility(View.VISIBLE);
        txtAge7.setVisibility(View.VISIBLE);

        findViewById(R.id.lLay1).setVisibility(View.VISIBLE);
        findViewById(R.id.lVal1).setVisibility(View.VISIBLE);
        findViewById(R.id.lLay2).setVisibility(View.VISIBLE);
        findViewById(R.id.lVal2).setVisibility(View.VISIBLE);

        isIndividual = false;
        isFamily = true;
        isGroup = false;
        removeValues();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void onGroupClick(View view) {
        planType = 2;
        txtGroup.setBackground(getDrawable(R.drawable.dark_theme));
        txtGroup.setTextColor(Color.WHITE);

        txtFamily.setBackground(getDrawable(R.drawable.light_theme));
        txtFamily.setTextColor(getColor(R.color.colorPrimaryDark));
        txtIndividual.setBackground(getDrawable(R.drawable.light_theme));
        txtIndividual.setTextColor(getColor(R.color.colorPrimaryDark));

        txtAge1.setText("Member1 Age");
        txtAge2.setText("Member2 Age");
        txtAge3.setText("Member3 Age");
        txtAge4.setText("Member4 Age");
        txtAge5.setText("Member5 Age");

        findViewById(R.id.lLay1).setVisibility(View.VISIBLE);
        findViewById(R.id.lVal1).setVisibility(View.VISIBLE);
        findViewById(R.id.lLay2).setVisibility(View.VISIBLE);
        findViewById(R.id.lVal2).setVisibility(View.VISIBLE);

        edtMember6.setVisibility(View.GONE);
        edtMember7.setVisibility(View.GONE);
        txtAge6.setVisibility(View.GONE);
        txtAge7.setVisibility(View.GONE);

        isIndividual = false;
        isFamily = false;
        isGroup = true;
        removeValues();
    }

    public void onDateClick(View view) {
        selectedTextView = (TextView) view;
        Calendar newCalendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                R.style.datepicker, (view1, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);

            selectedTextView.setText(dateFormatter.format(newDate.getTime()));
            selectedTextView.setError(null);
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));

        double timeInMillis = Calendar.getInstance().getTimeInMillis();
        datePickerDialog.getDatePicker().setMinDate((long) timeInMillis);
        datePickerDialog.show();
        try {
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorPrimaryDark));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onTermsClick(View view) {
        startActivity(new Intent(mContext, PrivacyActivity.class));
    }

    private boolean isValid() {
        try {
            startDate = fromDate.getText().toString();
            endDate = toDate.getText().toString();
            sumInsured = spnSumInsured.getSelectedItem().toString();

            self = edtMember1.getText().toString();
            member2 = edtMember2.getText().toString();
            member3 = edtMember3.getText().toString();
            member4 = edtMember4.getText().toString();
            member5 = edtMember5.getText().toString();
            member6 = edtMember6.getText().toString();
            member7 = edtMember7.getText().toString();

            if (TextUtils.isEmpty(startDate)) {
                fromDate.setError("Select Date");
                fromDate.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(endDate)) {
                toDate.setError("Select Date");
                toDate.requestFocus();
                return false;
            } else if (!startDate.equals(endDate)) {
                Date start = dateFormatter.parse(startDate);
                Date end = dateFormatter.parse(endDate);
                if (start != null) {
                    if (!(start.before(end))) {
                        toDate.setError("Invalid Date");
                        toDate.requestFocus();
                        AppUtils.showToast(mContext, "Date should be greater than start date");
                        return false;
                    }
                }
            }

            startDate = AppUtils.YYYYMMDD(startDate);
            endDate = AppUtils.YYYYMMDD(endDate);

            if (isFamily || isGroup) {
                if (TextUtils.isEmpty(self)) {
                    edtMember1.setError("Field can not be empty");
                    edtMember1.requestFocus();
                    return false;
                } else if (TextUtils.isEmpty(member2)) {
                    edtMember2.setError("Field can not be empty");
                    edtMember2.requestFocus();
                    return false;
                }
            } else if (isIndividual) {
                if (TextUtils.isEmpty(self)) {
                    edtMember1.setError("Field can not be empty");
                    edtMember1.requestFocus();
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            AppUtils.showToast(mContext, "Something went wrong..");
            return false;
        }
        return true;
    }

    public void onGetQuoteClick(View view) {
        if (isValid()) {
            if (planType == 1) {
                getTravelQuoteIdFamily();
                planId = "family";
            } else if (planType == 2) {
                getTravelQuoteIdGroup();
                planId = "group";
            } else if (planType == 3) {
                getTravelQuoteIdIndividual();
                planId = "individual";
            }
        }
    }

    public void getCountry() {
        if (AppUtils.isOnline(mContext)) {
            try {
                TravelManager.getInstance().getCountry(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getTravelQuoteIdFamily() {
        if (AppUtils.isOnline(mContext)) {
            try {
                progressDialog.show();
                TravelManager.getInstance().getTravelQuoteIdFamily(mContext, userId, userType, name,
                        mobile, email, countyId, startDate, endDate, qId, sumInsured, self, member2,
                        member3, member4, member5, member6, member7);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getTravelQuoteIdGroup() {
        if (AppUtils.isOnline(mContext)) {
            try {
                progressDialog.show();
                TravelManager.getInstance().getTravelQuoteIdGroup(mContext, userId, userType, name,
                        mobile, email, countyId, startDate, endDate, qId, sumInsured, self, member2,
                        member3, member4, member5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getTravelQuoteIdIndividual() {
        if (AppUtils.isOnline(mContext)) {
            try {
                progressDialog.show();
                TravelManager.getInstance().getTravelQuoteIdIndividual(mContext, userId, userType,
                        name, mobile, email, countyId, startDate, endDate, qId, sumInsured, self);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Object response) {
        try {
            if (response instanceof CountryList) {
                CountryList cResponse = (CountryList) response;
                if (cResponse.getSuccess().equals("1")) {
                    countriesList.clear();
                    countries.clear();
                    countriesList.addAll(cResponse.getData());
                    initCounty();
                }
            }
            if (response instanceof TravelQuote) {
                TravelQuote cResponse = (TravelQuote) response;
                if (cResponse.getSuccess().equals("success")) {
                    qId = cResponse.getData().getQid();
                    Intent intent = new Intent(mContext, TravelQuoteActivity.class);
                    mBundle.putString(AppUtils.QUOTATION_ID, qId);
                    mBundle.putString(AppUtils.PLAN_TYPE, planId);
                    intent.putExtras(mBundle);
                    startActivity(intent);

                }
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCounty() {
        for (int i = 0; i < countriesList.size(); i++) {
            String stateName = countriesList.get(i).getCountryName().trim();
            String stateId = countriesList.get(i).getId();
            countries.add(new String2WithTag(stateName, stateId));
        }

        ArrayAdapter<String2WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, countries);
        spnCountry.setAdapter(adapter);

        spnCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                countyId = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
