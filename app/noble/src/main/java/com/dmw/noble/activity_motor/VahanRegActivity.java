package com.dmw.noble.activity_motor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.ApiManager;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.model.Insurer;
import com.dmw.noble.model.InsurerList;
import com.dmw.noble.model.VehicleQuote;
import com.dmw.noble.utils.AppUtils;
import com.dmw.noble.utils.SearchableSpinner;
import com.dmw.noble.utils.String2WithTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VahanRegActivity extends AbstractActivity implements onRequestCompleteCallBackListener {

    Context mContext;
    EditText edtManufacture, edtModel, edtVariant, edtFuel, edtRegYear, edtPreInsurer;
    SearchableSpinner insurer;
    List<Insurer> insurerList = new ArrayList<>();
    List<String2WithTag> piList = new ArrayList<>();

    SharedPreferences preferences;
    String vehicleType, make, modelName, variantName, regYear, previousInsurer, policyType,
            policyExpiry, registrationNumber, rtoCode, fuelType, prePolicy, name,
            mobile, email, mYear, posId, userType, pcvCompany, pcvType, rtoName, insurerName,
            insurerId;
    int registrationYear;
    CheckBox cbPolicy;
    Spinner spnPolicyType, spnPolicyExpire;
    Bundle mBundle;
    ProgressDialog progressDialog;
    boolean policyExpired;
    int days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vahan_reg);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        mBundle = getIntent().getExtras();

        userType = preferences.getString(AppUtils.USER_TYPE, "");
        vehicleType = preferences.getString(AppUtils.VEHICLE_TYPE, "");

        name = preferences.getString(AppUtils.NAME, "");
        mobile = preferences.getString(AppUtils.MOBILE, "");
        email = preferences.getString(AppUtils.EMAIL, "");
        posId = preferences.getString(AppUtils.PRIMARY_ID, "");

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(false);

        edtManufacture = findViewById(R.id.edtManu);
        cbPolicy = findViewById(R.id.chkDP);
        edtModel = findViewById(R.id.edtModel);
        edtVariant = findViewById(R.id.edtVariant);
        insurer = findViewById(R.id.spnInsurer);
        edtPreInsurer = findViewById(R.id.edtPreInsurer);
        edtFuel = findViewById(R.id.spnFuelType);
        edtRegYear = findViewById(R.id.edtRegYear);

        spnPolicyType = findViewById(R.id.spnPolicyType);
        spnPolicyExpire = findViewById(R.id.spnPolicyExpire);

        if (!TextUtils.isEmpty(vehicleType)) {
            switch (vehicleType) {
                case "1":
                    this.setTitle("Bike Insurance");
                    break;
                case "2":
                    this.setTitle("Car Insurance");
                    break;
                case "3":
                    this.setTitle("PCV Insurance");
                    break;
                case "4":
                    this.setTitle("GCV Insurance");
                    break;
                case "5":
                    this.setTitle("MISCD Insurance");
                    break;
            }
        }

        insurerList = UserManager.getInstance().getInsurerList();

        if (insurerList.size() > 0)
            initPreInsurer();
        else getPreInsurer();

        if (mBundle != null) {
            registrationNumber = mBundle.getString(AppUtils.REGISTRATION_NUMBER);
            pcvCompany = mBundle.getString(AppUtils.PCV_COMPANY);
            pcvType = mBundle.getString(AppUtils.PCV_TYPE);
            rtoName = mBundle.getString(AppUtils.RTO_NAME);

            make = mBundle.getString(AppUtils.MAKE);
            modelName = mBundle.getString(AppUtils.MODEL);
            variantName = mBundle.getString(AppUtils.VARIANT);
            previousInsurer = mBundle.getString(AppUtils.INSURER);
            regYear = mBundle.getString(AppUtils.REGISTRATION_YEAR);
            fuelType = mBundle.getString(AppUtils.FUEL_TYPE);

            days = mBundle.getInt(AppUtils.POLICY_EXPIRY_DAYS, 0);

            if (!TextUtils.isEmpty(regYear))
                registrationYear = Integer.parseInt(regYear);

            edtManufacture.setText(make);
            edtModel.setText(modelName);
            edtVariant.setText(variantName);
            edtFuel.setText(fuelType);
            edtRegYear.setText(regYear);
            edtPreInsurer.setText(previousInsurer);

            try {
                if (insurerList.size() > 0 && !TextUtils.isEmpty(previousInsurer)) {
                    for (int i = 0; i < insurerList.size(); i++) {
                        String str = insurerList.get(i).getInsurer();
                        if (previousInsurer.equalsIgnoreCase(str)) {
                            insurer.setSelectionM(i);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (TextUtils.isEmpty(previousInsurer)) {
            findViewById(R.id.txtSpnPreInsurer).setVisibility(View.VISIBLE);
            findViewById(R.id.txtPreInsurer).setVisibility(View.GONE);
            insurerList = UserManager.getInstance().getInsurerList();
            if (insurerList.size() > 0) {
                initPreInsurer();
            } else
                getPreInsurer();
        }

        if (days >= 0)
            spnPolicyExpire.setSelection(0);
        else if (days <= 90)
            spnPolicyExpire.setSelection(1);
        else if (days > 90)
            spnPolicyExpire.setSelection(2);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(Object response) {
        try {
            if (response instanceof InsurerList) {
                InsurerList mResponse = (InsurerList) response;
                if (mResponse.getStatus().equals(1)) {
                    if (insurerList.size() > 0) {
                        insurerList.clear();
                        piList.clear();
                    }
                    insurerList = mResponse.getInsurer();
                    if (insurerList.size() > 0) {
                        initPreInsurer();
                    }
                }
            }
            if (response instanceof VehicleQuote) {

                VehicleQuote vehicleQuote = (VehicleQuote) response;
                if (vehicleQuote.getSuccess().equals("1")) {
                    String quotationId = vehicleQuote.getQuotationId();
                    String insertId = vehicleQuote.getInsertId();
                    String controlId = vehicleQuote.getControllId();
                    String vehicle = make + " " + modelName + " " + variantName;
                    Intent intent = new Intent(mContext, DetailedVehicleActivity.class);
                    mBundle.putString(AppUtils.QUOTATION_ID, quotationId);
                    mBundle.putString(AppUtils.INSERT_ID, insertId);
                    mBundle.putString(AppUtils.NEW_VEHICLE, "");
                    mBundle.putString(AppUtils.VEHICLE_FULL, vehicle);
                    mBundle.putString(AppUtils.MAKE, make);
                    mBundle.putString(AppUtils.MODEL, modelName);
                    mBundle.putString(AppUtils.VARIANT, variantName);
                    mBundle.putString(AppUtils.PRE_POLICY_TYPE, policyType);
                    mBundle.putInt(AppUtils.REG_YEAR, registrationYear);
                    mBundle.putString(AppUtils.REGISTRATION_NUMBER, registrationNumber.toUpperCase());
                    mBundle.putString(AppUtils.IS_PREVIOUS, prePolicy);
                    mBundle.putString(AppUtils.POLICY_EXPIRY, policyExpiry);
                    mBundle.putBoolean(AppUtils.POLICY_EXPIRED, policyExpired);
                    mBundle.putString(AppUtils.INSURER, previousInsurer);
                    mBundle.putString(AppUtils.REGISTRATION_YEAR, mYear);
                    mBundle.putString(AppUtils.FUEL_TYPE, fuelType);
                    mBundle.putString(AppUtils.VEHICLE_TYPE, vehicleType);
                    mBundle.putString(AppUtils.CONTROL_ID, controlId);
                    intent.putExtras(mBundle);
                    progressDialog.dismiss();
                    startActivity(intent);
                } else Toast.makeText(mContext, "" + vehicleQuote.getMsg(),
                        Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initPreInsurer() {
        for (int i = 0; i < insurerList.size(); i++) {
            String modelName = insurerList.get(i).getInsurer();
            String modelId = insurerList.get(i).getId();
            piList.add(new String2WithTag(modelName, modelId));
        }

        ArrayAdapter<String2WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, piList);
        insurer.setAdapter(adapter);

        insurer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                insurerId = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(insurerName) && insurerList.size() != 0) {
            for (int i = 0; i < insurerList.size() - 1; i++) {
                if (insurerName.equalsIgnoreCase(insurerList.get(i).getInsurer())
                        && i < insurerList.size()) {
                    insurer.setSelectionM(i);
                    break;
                }
            }
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

    public void getRto() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getRto(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void onContinue(View view) {
        if (isValid())
            onNextActivity();
    }

    private boolean isValid() {
        getValues();
        if (!cbPolicy.isChecked())
            if (TextUtils.isEmpty(previousInsurer)) {
                Toast.makeText(mContext, "Select Previous Insurer", Toast.LENGTH_SHORT).show();
                ((TextView) insurer.getSelectedView()).setError("Select Value");
                return false;
            }
        return true;
    }

    private void getValues() {
        if (!cbPolicy.isChecked()) {
            if (insurer.getSelectedItem() != null)
                previousInsurer = insurer.getSelectedItem().toString();
            policyType = spnPolicyType.getSelectedItem().toString().toLowerCase();

            if (!TextUtils.isEmpty(policyType))
                if (policyType.equalsIgnoreCase("Third party"))
                    policyType = "third_party";

            policyExpiry = spnPolicyExpire.getSelectedItem().toString();
            if (spnPolicyExpire.getSelectedItemPosition() == 2)
                policyExpired = true;
        }

        if (cbPolicy.isChecked())
            prePolicy = "1";
        else prePolicy = "0";
    }

    public void onCheckClick(View view) {
        if (cbPolicy.isChecked()) {
            findViewById(R.id.rlDotKnow).setVisibility(View.GONE);
        } else {
            findViewById(R.id.rlDotKnow).setVisibility(View.VISIBLE);
        }
    }

    private void onNextActivity() {

        if ((!TextUtils.isEmpty(name)) && (!TextUtils.isEmpty(posId))) {
            if (TextUtils.isEmpty(registrationNumber))
                registrationNumber = rtoCode.toUpperCase();
            else if (registrationNumber.length() < 5)
                registrationNumber = rtoCode.toUpperCase();
            getQuotationId();

        } else {
            Intent intent = new Intent(mContext, BasicDetailActivity.class);
            mBundle = new Bundle();
            mBundle.putString(AppUtils.MAKE, make);
            mBundle.putString(AppUtils.MODEL, modelName);
            mBundle.putString(AppUtils.VARIANT, variantName);
            mBundle.putString(AppUtils.REGISTRATION_YEAR, mYear);
            mBundle.putInt(AppUtils.REG_YEAR, registrationYear);
            mBundle.putString(AppUtils.INSURER, previousInsurer);
            mBundle.putString(AppUtils.PRE_POLICY_TYPE, policyType);
            mBundle.putString(AppUtils.POLICY_EXPIRY, policyExpiry);
            mBundle.putString(AppUtils.VEHICLE_TYPE, vehicleType);
            mBundle.putString(AppUtils.REGISTRATION_NUMBER, registrationNumber.toUpperCase());
            mBundle.putString(AppUtils.NEW_VEHICLE, "");
            mBundle.putString(AppUtils.RTO_CODE, rtoCode);
            mBundle.putBoolean(AppUtils.POLICY_EXPIRED, policyExpired);
            mBundle.putString(AppUtils.IS_PREVIOUS, prePolicy);
            mBundle.putString(AppUtils.FUEL_TYPE, fuelType);
            mBundle.putString(AppUtils.PCV_COMPANY, pcvCompany);
            mBundle.putString(AppUtils.PCV_TYPE, pcvType);
            intent.putExtras(mBundle);
            startActivity(intent);
            preferences.edit().putInt(AppUtils.REGISTRATION_YEAR, registrationYear).apply();
        }
    }

    @Override
    public void onBackPressed() {
        mBundle.clear();
        super.onBackPressed();
    }

    public void getQuotationId() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            if (!TextUtils.isEmpty(policyType))
                policyType = policyType.toLowerCase();

            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            assert wifiManager != null;
            String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

            try {

                String preInsurer, prePolicyType, prePolicyExp;
                if (prePolicy.equals("1")) {
                    preInsurer = prePolicyType = prePolicyExp = "";
                } else {
                    preInsurer = previousInsurer;
                    prePolicyType = policyType;
                    prePolicyExp = policyExpiry;
                }
                ApiManager.getInstance().initQuotationId(mContext, ipAddress, email, mobile,
                        prePolicyExp, prePolicyType, preInsurer, regYear, variantName, modelName,
                        make, vehicleType, registrationNumber.toUpperCase(), name, "",
                        userType, fuelType, prePolicy, pcvCompany, pcvType, posId);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }
}
