package com.dmw.noble.activity.health;

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
import android.widget.TextView;
import android.widget.Toast;

import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.Health.HealthManager;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.model.City;
import com.dmw.noble.model.CityList;
import com.dmw.noble.model.PinList;
import com.dmw.noble.model.Pincode;
import com.dmw.noble.model_health.v2.HealthSumInsured;
import com.dmw.noble.model_health.v2.SumInsured;
import com.dmw.noble.utils.AppUtils;
import com.dmw.noble.utils.String2WithTag;
import com.dmw.noble.utils.String3WithTag;
import com.dmw.noble.utils.String4WithTagRaheja;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CitySumActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {

    private com.toptoche.searchablespinnerlibrary.SearchableSpinner spnPinCode, spnCity,
            spnSumAssured;

    private List<City> cityList = new ArrayList<>();
    private List<String2WithTag> cities = new ArrayList<>();
    private List<SumInsured> sumInsuredList = new ArrayList<>();
    private List<String3WithTag> sumList = new ArrayList<>();
    private List<Pincode> pincodeList = new ArrayList<>();
    private List<String4WithTagRaheja> pin = new ArrayList<>();

    private String pincode, planType, sumAssured, city, pinId, gender;
    private Context mContext;
    private Bundle mBundle;
    private SharedPreferences preferences;
    private ProgressDialog progressdialog;
    TextView txtMale, txtFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_sum);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        mContext = this;
        mBundle = getIntent().getExtras();

        if (mBundle == null)
            mBundle = new Bundle();

        spnPinCode = findViewById(R.id.spnPincode);
        spnCity = findViewById(R.id.spnCity);
        spnSumAssured = findViewById(R.id.spnSumAssured);
        txtFemale = findViewById(R.id.lblFemale);
        txtMale = findViewById(R.id.lblMale);
        gender = "male";
        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        spnPinCode.setTitle("Select Pincode");
        spnPinCode.setPositiveButton("OK");

        planType = "Floater";

        getCities();
        getSumInsured();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initPincode() {
        for (int i = 0; i < pincodeList.size(); i++) {
            String stateName = pincodeList.get(i).getAreaName().trim();
            String stateId = pincodeList.get(i).getPincode();
            String pinArea = stateId + " " + stateName;
            String pId = pincodeList.get(i).getId();
            pin.add(new String4WithTagRaheja(pinArea, stateId, pId));
        }

        ArrayAdapter<String4WithTagRaheja> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, pin);
        spnPinCode.setAdapter(adapter);


        spnPinCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String4WithTagRaheja stringWithTag = (String4WithTagRaheja) parent.getItemAtPosition(position);
                pinId = (String) stringWithTag.tag;
                pincode = stringWithTag.nameValue;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onResponse(Object response) {

        if (response instanceof CityList) {
            CityList cResponse = (CityList) response;
            if (cResponse.getMessage().equalsIgnoreCase("ok")) {
                cityList.clear();
                cities.clear();
                cityList = cResponse.getCity();
                if (cityList.size() > 0) {
                    initCities();
                }
            }
        }
        if (response instanceof HealthSumInsured) {
            HealthSumInsured cResponse = (HealthSumInsured) response;
            if (cResponse.getStatus().equalsIgnoreCase("1")) {
                sumInsuredList.clear();
                sumList.clear();
                sumInsuredList = cResponse.getSumInsured();
                if (sumInsuredList.size() > 0) {
                    initSumInsured();
                }
            }
        }
        if (response instanceof PinList) {
            PinList cResponse = (PinList) response;
            if (cResponse.getMessage().equalsIgnoreCase("ok")) {
                pincodeList.clear();
                pin.clear();
                pincodeList = cResponse.getPincode();
                if (pincodeList.size() > 0) {
                    initPincode();
                }
            }
            progressdialog.dismiss();
        }

    }

    private void initCities() {
        for (int i = 0; i < cityList.size(); i++) {
            String stateName = cityList.get(i).getCityName().trim();
            String stateId = cityList.get(i).getCityName();
            cities.add(new String2WithTag(stateName, stateId));
        }

        ArrayAdapter<String2WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, cities);
        spnCity.setAdapter(adapter);

        for (int i = 0; i < cityList.size(); i++) {
            String stateName = cityList.get(i).getCityName().trim();
            if (stateName.equalsIgnoreCase("jaipur")) {
                spnCity.setSelection(i);
                break;
            }
        }


        spnCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                city = (String) stringWithTag.tag;
                if (!TextUtils.isEmpty(city))
                    getPincode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void onMaleClick(View view) {

        txtFemale.setBackground(getDrawable(R.drawable.light_theme));
        txtFemale.setTextColor(getResources().getColor(R.color.colorPrimary));

        txtMale.setBackground(getDrawable(R.drawable.dark_theme));
        txtMale.setTextColor(Color.WHITE);
        gender = "male";
    }

    public void onFemaleClick(View view) {
        txtMale.setBackground(getDrawable(R.drawable.light_theme));
        txtMale.setTextColor(getResources().getColor(R.color.colorPrimary));

        txtFemale.setBackground(getDrawable(R.drawable.dark_theme));
        txtFemale.setTextColor(Color.WHITE);
        gender = "female";
    }

    private void initSumInsured() {
        for (int i = 0; i < sumInsuredList.size(); i++) {
            String stateName = sumInsuredList.get(i).getSuminsured().trim();
            String stateId = sumInsuredList.get(i).getId();
            sumList.add(new String3WithTag(stateName, stateId));
        }

        ArrayAdapter<String3WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, sumList);
        spnSumAssured.setAdapter(adapter);


        spnSumAssured.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String3WithTag stringWithTag = (String3WithTag) parent.getItemAtPosition(position);
                sumAssured = stringWithTag.string;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void getPincode() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().getPin(mContext, city);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void onSelectMembers(View view) {

        if (spnSumAssured.getSelectedItem() != null)
            sumAssured = spnSumAssured.getSelectedItem().toString().trim();

        Intent intent = new Intent(mContext, HealthMembersActivity.class);
        mBundle.putString(AppUtils.PLAN_TYPE, planType);
        mBundle.putString(AppUtils.SUM_ASSURED, sumAssured);
        mBundle.putString(AppUtils.PINCODE, pinId);
        mBundle.putString(AppUtils.PINCODE_PIN, pincode);
        mBundle.putString(AppUtils.HL_GENDER, gender);
        mBundle.putString(AppUtils.HL_CITY, city);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    public void getCities() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().getCities(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }

    public void getSumInsured() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                HealthManager.getInstance().getHealthSum(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }

    public void onFloaterClick(View view) {
        planType = "Floater";
    }

    public void onIndividualClick(View view) {
        planType = "Individual";
    }
}