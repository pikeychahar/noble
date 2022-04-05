package com.dmw.noble.activity_motor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.activity.MobileVerifyActivity;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.ApiManager;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.model.City;
import com.dmw.noble.model.CityList;
import com.dmw.noble.model.Rto;
import com.dmw.noble.model.RtoLocation;
import com.dmw.noble.model.SendOtp;
import com.dmw.noble.model.VehicleOther;
import com.dmw.noble.model.fetch_detail.GetVehicle;
import com.dmw.noble.model.master.FinanceStatus;
import com.dmw.noble.model.master.Insurer;
import com.dmw.noble.utils.AppUtils;
import com.dmw.noble.utils.String2WithTag;
import com.dmw.noble.utils.String3WithTag;
import com.dmw.noble.utils.String4WithTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class VehicleDetailsActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {

    Context mContext;
    com.toptoche.searchablespinnerlibrary.SearchableSpinner spnCity, spnFinanceName, spnRTO;
    List<City> cityList = new ArrayList<>();
    List<Insurer> financeList = new ArrayList<>();
    List<Rto> rtoList = new ArrayList<>();
    List<String2WithTag> cities = new ArrayList<>();
    List<String3WithTag> financiers = new ArrayList<>();
    List<String4WithTag> locations = new ArrayList<>();
    CheckBox checkHypothecation;
    EditText edtRegNo, edtEngineNo, edtChassisNo, edtRto, edtPrePolicyNo, edtPrePolicyName,
            edtFinName;
    SharedPreferences preferences;
    String regNumber, engineNumber, chassisNumber, financierName, cityId, hypothecation,
            quotationId, ownedBy, rtoCode, newVehicle, companyName, prePolicy, prePolicyNo,
            financierId, rtoId, prePolicyName;
    ProgressDialog progressdialog;
    Bundle mBundle;
    boolean isRegistered, isPaCover;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        mBundle = getIntent().getExtras();

        spnCity = findViewById(R.id.fCity);
        spnRTO = findViewById(R.id.spnRTO);
        cityList = UserManager.getInstance().getCitiesList();
        checkHypothecation = findViewById(R.id.hypothecation);
        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");

        edtEngineNo = findViewById(R.id.edtEngineNo);
        edtChassisNo = findViewById(R.id.edtChassisNo);
        spnFinanceName = findViewById(R.id.edtFinanceName);
        edtFinName = findViewById(R.id.edtFinName);
        edtRegNo = findViewById(R.id.edtRegNo);
        edtRto = findViewById(R.id.edtRto);

        edtPrePolicyNo = findViewById(R.id.edtPrePolicy);
        edtPrePolicyName = findViewById(R.id.edtPrePolicyName);

        if (mBundle != null) {
            quotationId = mBundle.getString(AppUtils.QUOTATION_ID);
            ownedBy = mBundle.getString(AppUtils.OWNED_BY);
            regNumber = mBundle.getString(AppUtils.REGISTRATION_NUMBER, "");
            rtoCode = mBundle.getString(AppUtils.RTO_CODE, "");
            newVehicle = mBundle.getString(AppUtils.NEW_VEHICLE, "");
            companyName = mBundle.getString(AppUtils.COMPANY_NAME);
            prePolicy = mBundle.getString(AppUtils.IS_PREVIOUS);
            prePolicyName = mBundle.getString(AppUtils.INSURER);
            isPaCover = mBundle.getBoolean(AppUtils.IS_PA_COVER);
        }

        spnFinanceName.setTitle("Select Financier name");
        spnCity.setTitle("Select Financier City");

        if (!TextUtils.isEmpty(newVehicle)) {
            if (newVehicle.equalsIgnoreCase("new_gadi")) {
                findViewById(R.id.rlRegNo).setVisibility(View.GONE);
                isRegistered = false;
            } else if (regNumber.length() < 5) {
                isRegistered = true;
                findViewById(R.id.rlRegNo).setVisibility(View.VISIBLE);
                edtRto.setText(rtoCode);
            } else findViewById(R.id.rlRegNo).setVisibility(View.GONE);
        } else if (regNumber.length() < 5) {
            isRegistered = true;
            findViewById(R.id.rlRegNo).setVisibility(View.VISIBLE);
            edtRto.setText(rtoCode);
        } else findViewById(R.id.rlRegNo).setVisibility(View.GONE);
        if (!TextUtils.isEmpty(prePolicy))
            if (prePolicy.equalsIgnoreCase("1") || newVehicle.equalsIgnoreCase("new_gadi"))
                findViewById(R.id.prePolicy).setVisibility(View.GONE);
            else {
                edtPrePolicyName.setText(prePolicyName);
                findViewById(R.id.prePolicy).setVisibility(View.VISIBLE);
            }

        if (cityList.size() > 0) {
            initCities();
        } else getCities();

        getFinancier();
        fetchVehicle();
        if (!TextUtils.isEmpty(quotationId))
            getRtoLocation();

    }

    public void getCities() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getCities(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
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

        if (!TextUtils.isEmpty(cityId))
            for (int i = 0; i < cityList.size(); i++) {
                String stateName = cityList.get(i).getCityName().trim();
                if (stateName.equalsIgnoreCase(cityId)) {
                    spnCity.setSelection(i);
                    break;
                }
            }


        spnCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                cityId = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initRtoLocation() {
        for (int i = 0; i < rtoList.size(); i++) {
            String stateName = rtoList.get(i).getRegionName().trim();
            String stateId = rtoList.get(i).getRtoId();
            String rtoCode = rtoList.get(i).getRto();
            locations.add(new String4WithTag(stateName + " - " + rtoCode, stateId));
        }

        ArrayAdapter<String4WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, locations);
        spnRTO.setAdapter(adapter);


        spnRTO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String4WithTag stringWithTag = (String4WithTag) parent.getItemAtPosition(position);
                rtoId = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initFinance() {
        for (int i = 0; i < financeList.size(); i++) {
            String stateName = financeList.get(i).getName().trim();
            String stateId = financeList.get(i).getCode();
            financiers.add(new String3WithTag(stateName, stateId));
        }

        ArrayAdapter<String3WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, financiers);
        spnFinanceName.setAdapter(adapter);

//        if (!TextUtils.isEmpty(financierName))
//            for (int i = 0; i < financeList.size(); i++) {
//                String stateName = financeList.get(i).getName().trim();
//                if (stateName.equalsIgnoreCase(financierName)) {
//                    edtFinanceName.setSelection(i);
//                    break;
//                }
//            }

        spnFinanceName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String3WithTag stringWithTag = (String3WithTag) parent.getItemAtPosition(position);
                financierId = (String) stringWithTag.tag;
                financierName = (String) stringWithTag.string;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public boolean checkValidation() {
        initValues();
        Pattern regex = Pattern.compile("[$&+, :;=\\\\?@#|/'<>.^*()%!-]");

        if (TextUtils.isEmpty(engineNumber)) {
            edtEngineNo.requestFocus();
            edtEngineNo.setError("Field can not be empty");
            return false;
        } else if (engineNumber.length() <= 5) {
            edtEngineNo.requestFocus();
            edtEngineNo.setError("Minimum 6 character");
            return false;
        } else if (regex.matcher(engineNumber).find()) {
            edtEngineNo.requestFocus();
            edtEngineNo.setError("Invalid Format");
            return false;
        }
        if (TextUtils.isEmpty(chassisNumber)) {
            edtChassisNo.requestFocus();
            edtChassisNo.setError("Field can not be empty");
            return false;
        } else if (companyName.equalsIgnoreCase("future")) {
            if (chassisNumber.length() <= 16) {
                edtChassisNo.requestFocus();
                edtChassisNo.setError("Minimum 17 character");
                return false;
            }
        } else if (chassisNumber.length() <= 5) {
            edtChassisNo.requestFocus();
            edtChassisNo.setError("Minimum 6 character");
            return false;
        }
        if (regex.matcher(chassisNumber).find()) {
            edtChassisNo.requestFocus();
            edtChassisNo.setError("Invalid Format");
            return false;
        }

        engineNumber = engineNumber.replaceAll("\n", "");
        engineNumber = engineNumber.replaceAll(" ", "");
        chassisNumber = chassisNumber.replaceAll("\n", "");
        chassisNumber = chassisNumber.replaceAll(" ", "");

        edtChassisNo.setText(chassisNumber);
        edtEngineNo.setText(engineNumber);

        if (checkHypothecation.isChecked()) {
            if (TextUtils.isEmpty(financierName)) {
                Toast.makeText(mContext, "Select Financier Name", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (isRegistered) {
            String num = edtRegNo.getText().toString();
            if (TextUtils.isEmpty(num)) {
                edtRegNo.setError("Field can not be empty");
                edtRegNo.requestFocus();
                return false;
            } else if (regex.matcher(num).find()) {
                edtRegNo.requestFocus();
                edtRegNo.setError("Invalid Format");
                return false;
            }
        }
        if (!TextUtils.isEmpty(prePolicy))
            if (prePolicy.equalsIgnoreCase("0") && !newVehicle.equalsIgnoreCase("new_gadi"))
                if (TextUtils.isEmpty(prePolicyNo)) {
                    edtPrePolicyNo.requestFocus();
                    edtPrePolicyNo.setError("Field can not be empty");
                    return false;
                }
        if (!TextUtils.isEmpty(prePolicyNo))
            prePolicyNo = prePolicyNo.replaceAll("\n", "");
        return true;
    }

    public void initValues() {
        engineNumber = AppUtils.trimString(edtEngineNo);
        chassisNumber = AppUtils.trimString(edtChassisNo);

        if (checkHypothecation.isChecked())
            hypothecation = "1";
        else hypothecation = "0";

        if (isRegistered) {
            String num = edtRegNo.getText().toString();
            regNumber = rtoCode + num;
        }

        if (!TextUtils.isEmpty(prePolicy))
            if (prePolicy.equalsIgnoreCase("0"))
                prePolicyNo = edtPrePolicyNo.getText().toString();

    }

    public void onClickHypothecation(View view) {
        if (checkHypothecation.isChecked())
            findViewById(R.id.rr2).setVisibility(View.VISIBLE);
        else findViewById(R.id.rr2).setVisibility(View.GONE);

    }

    public void onNomineeClick(View view) {
        if (checkValidation())
            submitVehicle();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void submitVehicle() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                if (companyName.equalsIgnoreCase("iffco"))
                    financierName = financierId;

                if (TextUtils.isEmpty(cityId))
                    cityId = financierName = "";

                if (TextUtils.isEmpty(prePolicyNo))
                    prePolicyNo = "";
                ApiManager.getInstance().vehicleOtherDetail(mContext, cityId, financierName,
                        hypothecation, chassisNumber, engineNumber, regNumber, prePolicyNo,
                        quotationId, rtoId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }

    @Override
    public void onResponse(Object response) {
        try {
            if (response instanceof VehicleOther) {
                VehicleOther vResponse = (VehicleOther) response;

                if (vResponse.getSuccess().equalsIgnoreCase("1")) {
                    mBundle.putString(AppUtils.ENGINE, engineNumber);
                    mBundle.putString(AppUtils.CHASSIS, chassisNumber);
                    new Intent();
                    Intent intent;

//                if (ownedBy.equalsIgnoreCase("Individual") && !isPaCover) {
                    if (ownedBy.equalsIgnoreCase("Individual")) {
                        intent = new Intent(mContext, NomineeDetailActivity.class);
                    } else {
                        intent = new Intent(mContext, OverViewActivity.class);
                    }
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }
            }

            if (response instanceof SendOtp) {
                SendOtp vResponse = (SendOtp) response;
                if (vResponse.getStatus() == 1) {
                    Intent intent = new Intent(mContext, MobileVerifyActivity.class);
                    mBundle.putString(AppUtils.OTP, vResponse.getOtp().toString());
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }
            }

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

            if (response instanceof RtoLocation) {
                RtoLocation cResponse = (RtoLocation) response;
                if (cResponse.getStatus().equals(1)) {
                    rtoList.clear();
                    locations.clear();
                    rtoList = cResponse.getRto();
                    if (rtoList.size() > 0) {
                        initRtoLocation();
                    }
                }
            }

            if (response instanceof FinanceStatus) {
                FinanceStatus cResponse = (FinanceStatus) response;
                if (cResponse.getStatus().equalsIgnoreCase("1") && cResponse.getInsurer() != null) {
                    financeList.clear();
                    financiers.clear();
                    financeList = cResponse.getInsurer();
                    if (financeList.size() > 0) {
                        initFinance();
                    }
                }
            }

            if (response instanceof GetVehicle) {
                GetVehicle cResponse = (GetVehicle) response;
                if (cResponse.getSuccess().equalsIgnoreCase("1")) {
                    engineNumber = cResponse.getEngineNo();
                    chassisNumber = cResponse.getChassiesNo();
                    prePolicyNo = cResponse.getPrePolicyNo();
                    financierName = cResponse.getFinancierName();
                    cityId = cResponse.getFinancierCity();

                    edtEngineNo.setText(engineNumber);
                    edtChassisNo.setText(chassisNumber);
                    edtPrePolicyNo.setText(prePolicyNo);
                }
            }

            progressdialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getFinancier() {
        if (AppUtils.isOnline(mContext)) {
            try {
                ApiManager.getInstance().getFinancier(mContext, "insurer", companyName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getRtoLocation() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getRtoLocation(mContext, quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void fetchVehicle() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                ApiManager.getInstance().fetchVehicle(mContext, quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }
}

