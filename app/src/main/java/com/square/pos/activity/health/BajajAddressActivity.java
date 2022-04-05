package com.square.pos.activity.health;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.printservice.PrinterDiscoverySession;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.Health.HealthManager;
import com.square.pos.model.TempResponse;
import com.square.pos.model_health.bajaj.BajajPincode;
import com.square.pos.model_health.bajaj.City;
import com.square.pos.utils.AppUtils;
import com.square.pos.utils.String7WithTag;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class BajajAddressActivity extends AbstractActivity implements
        onRequestCompleteCallBackListener {

    EditText edtAddress1, edtAddress2, edtAddress3, edtMonthlyIncome;
    SearchableSpinner spnAddressCity;
    Context mContext;
    String quotationId, companyName, addressCityId, cityId;
    List<City> cityList = new ArrayList<>();
    List<String7WithTag> cities = new ArrayList<>();
    Bundle mBundle;

    ArrayList<String> title, firstName, lastName, gender, dob, occupation, height,
            weight, mobile, email, exists, tobacco, tables, tables_value, nomineeFirstName,
            nomineeLastName, nomineeRelation, building, street, address1Array, pincode,
            monthlyIncomeArray, sameTraveller;

    String address1, address2, address3, monthlyIncome;
    ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bajaj_address);
            mContext = this;

        mBundle = getIntent().getExtras();

        edtAddress1 = findViewById(R.id.edtAddress1);
        edtAddress2 = findViewById(R.id.edtAddress2);
        edtAddress3 = findViewById(R.id.edtAddress3);
        edtMonthlyIncome = findViewById(R.id.edtMonthlyIncome);
        spnAddressCity = findViewById(R.id.spnAddressCity);

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");

        if (mBundle != null) {

            quotationId = mBundle.getString(AppUtils.QUOTATION_ID_1);
            cityId = mBundle.getString(AppUtils.HL_CITY_ID);
            companyName = mBundle.getString(AppUtils.HL_COMPANY);
            title = mBundle.getStringArrayList(AppUtils.HL_AP_SAL);
            firstName = mBundle.getStringArrayList(AppUtils.HL_INSURED_FNAME);
            lastName = mBundle.getStringArrayList(AppUtils.HL_INSURED_LNAME);
            gender = mBundle.getStringArrayList(AppUtils.HL_INSURED_GENDER);
            dob = mBundle.getStringArrayList(AppUtils.HL_INSURED_DOB);
            occupation = mBundle.getStringArrayList(AppUtils.HL_INSURED_OCCUPATION);
            height = mBundle.getStringArrayList(AppUtils.HL_INSURED_HEIGHT);
            weight = mBundle.getStringArrayList(AppUtils.HL_INSURED_WEIGHT);
            tobacco = mBundle.getStringArrayList(AppUtils.HL_BAJAJ_TB);
            mobile = mBundle.getStringArrayList(AppUtils.HL_BAJAJ_PHONE);
            email = mBundle.getStringArrayList(AppUtils.HL_BAJAJ_EMAIL);
            exists = mBundle.getStringArrayList(AppUtils.HL_BAJAJ_EXTRA);
            tables = mBundle.getStringArrayList(AppUtils.HL_BAJAJ_TABLE);
            tables_value = mBundle.getStringArrayList(AppUtils.HL_BAJAJ_TV);

            nomineeFirstName = mBundle.getStringArrayList(AppUtils.HL_BAJAJ_NF);
            nomineeLastName = mBundle.getStringArrayList(AppUtils.HL_BAJAJ_NL);
            nomineeRelation = mBundle.getStringArrayList(AppUtils.HL_BAJAJ_NR);
        }
        getHealthCity();
    }

    public void getHealthCity() {
        if (AppUtils.isOnline(mContext)) {
            try {
                HealthManager.getInstance().getHealthBajajCity(mContext, quotationId, companyName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void initCity() {

        for (int i = 0; i < cityList.size(); i++) {
            String cityName = cityList.get(i).getName();
            String stateName = cityList.get(i).getPincode();
            String stateId = cityList.get(i).getCode();
            cities.add(new String7WithTag(cityName + " - " + stateName, stateId));
        }

        ArrayAdapter<String7WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, cities);
        spnAddressCity.setAdapter(adapter);

        spnAddressCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String7WithTag stringWithTag = (String7WithTag) parent.getItemAtPosition(position);
                addressCityId = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof BajajPincode) {
            BajajPincode cResponse = (BajajPincode) response;
            if (cResponse.getStatus().equalsIgnoreCase("1")
                    && cResponse.getCity() != null) {
                cityList.clear();
                cities.clear();
                cityList = cResponse.getCity();
                if (cityList.size() > 0) {
                    initCity();
                }
            }
        }
        if (response instanceof TempResponse) {
            TempResponse cResponse = (TempResponse) response;
            if (cResponse.getStatus().equalsIgnoreCase("1")) {
                Intent intent = new Intent(mContext, HealthReviewActivity.class);
                mBundle.putStringArrayList(AppUtils.HL_BAJAJ_A1,building);
                mBundle.putStringArrayList(AppUtils.HL_BAJAJ_A2,street);
                mBundle.putStringArrayList(AppUtils.HL_BAJAJ_A3,address1Array);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        }
    }

    public void updateHealthProposal() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {

                building = new ArrayList<>();
                street = new ArrayList<>();
                address1Array = new ArrayList<>();
                pincode = new ArrayList<>();
                monthlyIncomeArray = new ArrayList<>();
                sameTraveller = new ArrayList<>();


                building.add(address1);
                street.add(address2);
                address1Array.add(address3);
                pincode.add(addressCityId);
                monthlyIncomeArray.add(monthlyIncome);

                sameTraveller.add("1");

                HealthManager.getInstance().updateHealthProposalBajaj(mContext, companyName,
                        quotationId, title, firstName, lastName, gender, dob, occupation, height,
                        weight, mobile, email, exists, tobacco, tables, tables_value, nomineeFirstName,
                        nomineeLastName, nomineeRelation, building, street, address1Array, pincode,
                        monthlyIncomeArray, sameTraveller);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }

    public boolean checkValidation() {

        address1 = edtAddress1.getText().toString();
        address2 = edtAddress2.getText().toString();
        address3 = edtAddress3.getText().toString();
        monthlyIncome = edtMonthlyIncome.getText().toString();

        if (TextUtils.isEmpty(address1)) {
            edtAddress1.setError(getString(R.string.field_cannot));
            edtAddress1.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(address2)) {
            edtAddress2.setError(getString(R.string.field_cannot));
            edtAddress2.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(address3)) {
            edtAddress3.setError(getString(R.string.field_cannot));
            edtAddress3.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(monthlyIncome)) {
            edtMonthlyIncome.setError(getString(R.string.field_cannot));
            edtMonthlyIncome.requestFocus();
            return false;
        }

        return true;
    }


    public void onBajajReview(View view) {
        checkValidation();
        updateHealthProposal();
    }
}