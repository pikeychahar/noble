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
import android.view.MotionEvent;
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
import com.dmw.noble.model.CommonResponse;
import com.dmw.noble.model.FetchQuote;
import com.dmw.noble.model.Fuel;
import com.dmw.noble.model.FuelType;
import com.dmw.noble.model.Insurer;
import com.dmw.noble.model.InsurerList;
import com.dmw.noble.model.Manufacture;
import com.dmw.noble.model.ManufactureList;
import com.dmw.noble.model.Model;
import com.dmw.noble.model.ModelList;
import com.dmw.noble.model.RTOList;
import com.dmw.noble.model.Rto;
import com.dmw.noble.model.Variant;
import com.dmw.noble.model.VariantList;
import com.dmw.noble.model.VehicleQuote;
import com.dmw.noble.utils.AppUtils;
import com.dmw.noble.utils.BankWithId;
import com.dmw.noble.utils.String2WithTag;
import com.dmw.noble.utils.String3WithTag;
import com.dmw.noble.utils.String4WithTag;
import com.dmw.noble.utils.StringWithTag;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class RegistrationDetailedActivity extends AbstractActivity implements
        onRequestCompleteCallBackListener {

    private Context mContext;
    private com.dmw.noble.utils.SearchableSpinner manufacture, model, variant,
            insurer, regRTO, spnFuelType;
    private List<Manufacture> manufactureList = new ArrayList<>();
    private List<Model> modelList = new ArrayList<>();
    private List<FuelType> fuelList = new ArrayList<>();
    private List<Variant> variantList = new ArrayList<>();
    private List<Insurer> insurerList = new ArrayList<>();
    List<Rto> rtoList = new ArrayList<>();
    String insurerId, variantId;
    List<StringWithTag> manuList = new ArrayList<>();
    List<String2WithTag> piList = new ArrayList<>();
    List<String3WithTag> rList = new ArrayList<>();
    List<String4WithTag> moList = new ArrayList<>();
    List<BankWithId> vList = new ArrayList<>();
    SharedPreferences preferences;
    String vehicleType, make, modelName, variantName, regYear, previousInsurer, policyType,
            policyExpiry, registrationNumber, newVehicle, rtoCode, fuelType, prePolicy, name,
            mobile, email, mYear, agentId, userType, pcvCompany, pcvType, rtoName, lastInsertId,
            insurerName;
    private EditText edtRegYear;
    private int registrationYear;
    private CheckBox cbPolicy;
    private Spinner spnPolicyType, spnPolicyExpire;
    private Bundle mBundle;
    private View view;
    private ProgressDialog progressDialog;
    private boolean policyExpired;
    private com.google.android.material.textfield.TextInputLayout txtManufacture, txtRegistration,
            txtModel, txtVariant, txtPreInsurer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_detailed);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        view = findViewById(R.id.root_view);

        mContext = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        mBundle = getIntent().getExtras();

        userType = preferences.getString(AppUtils.USER_TYPE, "");
        vehicleType = preferences.getString(AppUtils.VEHICLE_TYPE, "");

        name = preferences.getString(AppUtils.NAME, "");
        mobile = preferences.getString(AppUtils.MOBILE, "");
        email = preferences.getString(AppUtils.EMAIL, "");
        agentId = preferences.getString(AppUtils.PRIMARY_ID, "");

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(false);

        manufacture = findViewById(R.id.edtManu);
        cbPolicy = findViewById(R.id.chkDP);
        model = findViewById(R.id.edtModel);
        variant = findViewById(R.id.edtVariant);
        insurer = findViewById(R.id.edtPreInsurer);
        regRTO = findViewById(R.id.edtRegNum);
        txtManufacture = findViewById(R.id.txtManufacture);
        txtRegistration = findViewById(R.id.txtRegistration);
        txtModel = findViewById(R.id.txtModel);
        txtVariant = findViewById(R.id.txtVariant);
        txtPreInsurer = findViewById(R.id.txtPreInsurer);

        spnPolicyType = findViewById(R.id.spnPolicyType);
        spnPolicyExpire = findViewById(R.id.spnPolicyExpire);
        spnFuelType = findViewById(R.id.spnFuelType);
        fuelType = spnFuelType.getSelectedItem().toString();

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
        assert mBundle != null;
        registrationNumber = mBundle.getString(AppUtils.REGISTRATION_NUMBER);
        newVehicle = mBundle.getString(AppUtils.NEW_VEHICLE);
        pcvCompany = mBundle.getString(AppUtils.PCV_COMPANY);
        pcvType = mBundle.getString(AppUtils.PCV_TYPE);
        rtoName = mBundle.getString(AppUtils.RTO_NAME);
        edtRegYear = findViewById(R.id.edtRegYear);

        if (TextUtils.isEmpty(newVehicle))
            validRto();
        if (newVehicle != null)
            if (newVehicle.equalsIgnoreCase("new_gadi")) {

                rtoList = UserManager.getInstance().getRtoList();
                if (rtoList.size() > 0)
                    initRtoList();
                else
                    getRto();
                findViewById(R.id.rlNewVehicle).setVisibility(View.GONE);
                findViewById(R.id.txtPreInsurer).setVisibility(View.GONE);
                edtRegYear.setText("2022");
            }

        if (!TextUtils.isEmpty(registrationNumber) && registrationNumber.length() > 5) {
            findViewById(R.id.txtRegistration).setVisibility(View.GONE);
            findViewById(R.id.in1).setVisibility(View.GONE);
            fetchQuote();
        }

        spnFuelType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fuelType = spnFuelType.getSelectedItem().toString();
                getVariant();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(vehicleType))
            getManufacture();

        insurerList = UserManager.getInstance().getInsurerList();
        if (insurerList.size() > 0) {
            initPreInsurer();
            txtPreInsurer.setHelperText("Previous Insurer");
        } else
            getPreInsurer();

        model.setTitle("Select Model");
        model.setPositiveButton(" ");

        manufacture.setTitle("Select Manufacture");
        manufacture.setPositiveButton(" ");

        spnFuelType.setTitle("Select Fuel Type");
        spnFuelType.setPositiveButton(" ");

        variant.setTitle("Select Variant");
        variant.setPositiveButton(" ");

        regRTO.setTitle("Select RTO");
        regRTO.setPositiveButton(" ");

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
            if (response instanceof CommonResponse) {
                CommonResponse mResponse = (CommonResponse) response;
                boolean isValidRto = mResponse.getSuccess().equals("1");
                if (!isValidRto) {
                    Toast.makeText(mContext, "Invalid RTO", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
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
                        txtManufacture.setHelperText("Manufacture");
                    }
                }

                if (manufactureList.size() == 0)
                    AppUtils.showToast(mContext,"No Data found!!");
            }
            if (response instanceof ModelList) {
                ModelList mResponse = (ModelList) response;
                if (mResponse.getStatus().equals(1)) {
                    if (modelList.size() > 0) {
                        modelList.clear();
                        moList.clear();
                    }
                    modelList = mResponse.getModel();
                    if (modelList.size() > 0) {
                        initModel();
                        txtModel.setHelperText("Model");
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

                        List<String> strings = new ArrayList<>(fuelList.size());
                        for (int i = 0; i < fuelList.size(); i++) {
                            strings.add(Objects.toString(fuelList.get(i).getFuelType(), ""));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                this, android.R.layout.simple_spinner_item, strings);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnFuelType.setAdapter(adapter);

                        if (!TextUtils.isEmpty(fuelType) && fuelList.size() != 0) {
                            for (int i = 0; i < fuelList.size() - 1; i++) {
                                if (fuelType.equalsIgnoreCase(fuelList.get(i).getFuelType())
                                        && i < fuelList.size()) {
                                    spnFuelType.setSelectionM(i);
                                    getVariant();
                                    break;
                                }
                            }
                        }

                    }
                }
            }
            if (response instanceof FetchQuote) {
                FetchQuote fetchQuote = (FetchQuote) response;
                if (fetchQuote.getStatus().equals("1")) {
                    if (!TextUtils.isEmpty(fetchQuote.getManufacture())) {
                        make = fetchQuote.getManufacture();
                        modelName = fetchQuote.getModel();
                        variantName = fetchQuote.getVariant();
                        fuelType = fetchQuote.getFuelType();
                        insurerName = fetchQuote.getPreviousInsurer();
                        regYear = fetchQuote.getRegistrationYear();
                        initManufacture();
                        initPreInsurer();
                        if (!TextUtils.isEmpty(fetchQuote.getRegistrationYear()))
                            registrationYear = Integer.parseInt(fetchQuote.getRegistrationYear());
                        edtRegYear.setText(regYear);
                        lastInsertId = fetchQuote.getLastInsertId();
                    }
                }
            }
            if (response instanceof VariantList) {
                VariantList mResponse = (VariantList) response;
                if (mResponse.getVariant() != null) {

                    variantList.clear();
                    vList.clear();
                    variantList = mResponse.getVariant();

                    if (variantList.size() > 0)
                        txtVariant.setHelperText("Variants");
                } else {
                    variantList.clear();
                    vList.clear();
                }
                initVariant();
            }
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
                        txtPreInsurer.setHelperText("Previous Insurer");
                    }
                }
            }
            if (response instanceof RTOList) {
                RTOList mResponse = (RTOList) response;
                if (mResponse.getStatus().equals(1)) {
                    if (rtoList.size() > 0) {
                        rtoList.clear();
                        rList.clear();
                    }
                    rtoList = mResponse.getRto();
                    if (rtoList.size() > 0) {
                        initRtoList();
                        txtRegistration.setHelperText("Registration Area Code");
                    }
                }
            }
            if (response instanceof VehicleQuote) {
                VehicleQuote vehicleQuote = (VehicleQuote) response;
                if (vehicleQuote.getSuccess().equalsIgnoreCase("1")) {

                    String quotationId = vehicleQuote.getQuotationId();
                    String insertId = vehicleQuote.getInsertId();
                    String controlId = vehicleQuote.getControllId();
                    String vehicle = make + " " + modelName + " " + variantName;
                    Intent intent = new Intent(mContext, DetailedVehicleActivity.class);
                    mBundle.putString(AppUtils.QUOTATION_ID, quotationId);
                    mBundle.putString(AppUtils.INSERT_ID, insertId);
                    mBundle.putString(AppUtils.NEW_VEHICLE, newVehicle);
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
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    private void initManufacture() {
        for (int i = 0; i < manufactureList.size(); i++) {
            String bankName = manufactureList.get(i).getMake();
            String bankId = manufactureList.get(i).getMake();
            manuList.add(new StringWithTag(bankName, bankId));
        }

        ArrayAdapter<StringWithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, manuList);
        manufacture.setAdapter(adapter);
//        manufacture.onTouch(view, MotionEvent.obtain(1, 1, MotionEvent.ACTION_UP, 1, 1, 1));

//        manufacture.setSelection(0, false);
        manufacture.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                make = manufactureList.get(position).getMake();
                if (!TextUtils.isEmpty(make))
                    getModel();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(make) && manufactureList.size() != 0) {
            for (int i = 0; i < manufactureList.size() - 1; i++) {
                if (make.equalsIgnoreCase(manufactureList.get(i).getMake())
                        && i < manufactureList.size()) {
                    manufacture.setSelectionM(i);
                    break;
                }
            }
        }
    }

    private void initModel() {
        for (int i = 0; i < modelList.size(); i++) {
            String modelName = modelList.get(i).getModelName();
            String modelId = modelList.get(i).getModelName();
            moList.add(new String4WithTag(modelName, modelId));
        }

        ArrayAdapter<String4WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, moList);
        model.setAdapter(adapter);
//        model.onTouch(view, MotionEvent.obtain(1, 1, MotionEvent.ACTION_UP, 1, 1, 1));
//        model.setSelection(0, false);
        model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String4WithTag stringWithTag = (String4WithTag) parent.getItemAtPosition(position);
                modelName = modelList.get(position).getModelName();
                if (!TextUtils.isEmpty(modelName)) {
                    getFuel();
                    getVariant();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (!TextUtils.isEmpty(modelName) && modelList.size() != 0) {
            for (int i = 0; i < modelList.size() - 1; i++) {
                if (modelName.equalsIgnoreCase(modelList.get(i).getModelName())
                        && i < modelList.size()) {
                    model.setSelectionM(i);
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
        variant.setAdapter(adapter);
//        variant.onTouch(view, MotionEvent.obtain(1, 1, MotionEvent.ACTION_UP, 1, 1, 1));
        variant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BankWithId stringWithTag = (BankWithId) parent.getItemAtPosition(position);
                variantId = stringWithTag.string;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(variantName) && variantList.size() != 0) {
            for (int i = 0; i < variantList.size() - 1; i++) {
                if (variantName.contains(variantList.get(i).getVariantName())
                        && i < variantList.size()) {
                    variant.setSelectionM(i);
                    break;
                }
            }
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

    private void initRtoList() {
        for (int i = 0; i < rtoList.size(); i++) {
            String modelName = rtoList.get(i).getRto();
            String region = rtoList.get(i).getRegionName();
            String rtoName = modelName + " " + region;
            String modelId = rtoList.get(i).getRto();
            rList.add(new String3WithTag(rtoName, modelId));
        }

        ArrayAdapter<String3WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, rList);
        regRTO.setAdapter(adapter);

        regRTO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String3WithTag stringWithTag = (String3WithTag) parent.getItemAtPosition(position);
                rtoCode = (String) stringWithTag.tag;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getManufacture() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {

                UserManager.getInstance().getManufacture(mContext, vehicleType, pcvType, pcvCompany);
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
                txtModel.setHelperText("Fetching Model...");
                UserManager.getInstance().getModel(mContext, make, vehicleType, pcvType, pcvCompany);
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
                txtVariant.setHelperText("Fetching Variants...");
                UserManager.getInstance().getVariant(mContext, make, modelName, vehicleType,
                        fuelType, pcvCompany);
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
                UserManager.getInstance().getFuel(mContext, make, modelName, vehicleType, pcvCompany);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
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
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        int year = calendar.get(Calendar.YEAR);
        int sYear = year - 30;

        mYear = edtRegYear.getText().toString().trim();

        if (TextUtils.isEmpty(make)) {
            Toast.makeText(mContext, "Select Manufacture", Toast.LENGTH_SHORT).show();
            ((TextView) manufacture.getSelectedView()).setError("Select Value");
            return false;
        }
        if (TextUtils.isEmpty(modelName)) {
            Toast.makeText(mContext, "Select Model", Toast.LENGTH_SHORT).show();
            ((TextView) model.getSelectedView()).setError("Select Value");

            return false;
        }
        if (newVehicle == null || !newVehicle.equalsIgnoreCase("new_gadi"))
            if (!cbPolicy.isChecked())
                if (TextUtils.isEmpty(previousInsurer)) {
                    Toast.makeText(mContext, "Select Previous Insurer", Toast.LENGTH_SHORT).show();
                    ((TextView) insurer.getSelectedView()).setError("Select Value");
                    return false;
                }
        if (variantList.size() > 0) {
            if (TextUtils.isEmpty(variantName)) {
                Toast.makeText(mContext, "Select Variant", Toast.LENGTH_SHORT).show();
                ((TextView) variant.getSelectedView()).setError("Select Value");
                return false;
            }
        } else {
            Toast.makeText(mContext, "Select Variant", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(registrationNumber))
            if (TextUtils.isEmpty(rtoCode)) {
                Toast.makeText(mContext, "Select RTO", Toast.LENGTH_SHORT).show();
                ((TextView) regRTO.getSelectedView()).setError("Select Value");
                return false;
            }
        mYear = edtRegYear.getText().toString().trim();
        if (!TextUtils.isEmpty(mYear)) {
            registrationYear = Integer.parseInt(mYear);

            if ((registrationYear <= year) && (registrationYear >= sYear)) {
            } else {
                edtRegYear.setError("Invalid Year");
                edtRegYear.requestFocus();
                Toast.makeText(mContext, "Minimum Year is " + sYear, Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            edtRegYear.setError("Empty");
            edtRegYear.requestFocus();
            return false;
        }
        return true;
    }

    private void getValues() {
        if (manufacture.getSelectedItem() != null)
            make = manufacture.getSelectedItem().toString();
//        if (regRTO.getSelectedItem() != null)
//            rtoCode = regRTO.getSelectedItem().toString();

        if (model.getSelectedItem() != null)
            modelName = model.getSelectedItem().toString();
        if (variant.getSelectedItem() != null)
            variantName = variantId;
        regYear = edtRegYear.getText().toString();

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
            findViewById(R.id.rlDontKnow).setVisibility(View.GONE);
        } else {
            findViewById(R.id.rlDontKnow).setVisibility(View.VISIBLE);
        }
    }

    private void onNextActivity() {
        if ((!TextUtils.isEmpty(name)) && (!TextUtils.isEmpty(agentId))) {
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
            mBundle.putString(AppUtils.NEW_VEHICLE, newVehicle);
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
                ApiManager.getInstance().initQuotationId(mContext, ipAddress, email, mobile,
                        policyExpiry, policyType, previousInsurer, regYear, variantName, modelName,
                        make, vehicleType, registrationNumber.toUpperCase(), name, newVehicle,
                        userType, fuelType, prePolicy, pcvCompany, pcvType, agentId);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    private void onDismiss() {
        manufacture.onTouch(view, MotionEvent.obtain(1, 1, MotionEvent.ACTION_DOWN, 1, 1, 1));
        model.onTouch(view, MotionEvent.obtain(1, 1, MotionEvent.ACTION_DOWN, 1, 1, 1));
        variant.onTouch(view, MotionEvent.obtain(1, 1, MotionEvent.ACTION_DOWN, 1, 1, 1));
    }

    @Override
    protected void onPause() {
        super.onPause();
        onDismiss();
    }

    public void validRto() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().validRto(mContext, rtoName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void fetchQuote() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().fetchQuote(mContext, registrationNumber);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
