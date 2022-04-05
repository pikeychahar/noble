package com.square.pos.activity_motor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.ApiManager;
import com.square.pos.manager.UserManager;
import com.square.pos.model.AddonCover;
import com.square.pos.model.FilterCar;
import com.square.pos.model.Fuel;
import com.square.pos.model.FuelType;
import com.square.pos.model.Manufacture;
import com.square.pos.model.ManufactureList;
import com.square.pos.model.Model;
import com.square.pos.model.ModelList;
import com.square.pos.model.SbiData;
import com.square.pos.model.SbiMisd;
import com.square.pos.model.Variant;
import com.square.pos.model.VariantList;
import com.square.pos.utils.AppUtils;
import com.square.pos.utils.BankWithId;
import com.square.pos.utils.SearchableSpinner;
import com.square.pos.utils.String2WithTag;
import com.square.pos.utils.String4WithTag;
import com.square.pos.utils.StringWithTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FilterPcActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {

    private Context mContext;
    private ProgressDialog progressDialog;
    String quotationId, ownedBy, ownerChange, claimExp, ncbOld,
            pACover, zeroDept, cover, tpOnly, idv, policyExpDate, antiTheft,
            vehicleModified, tpp, voluntary, voluntaryValue, memberAAI, numberAAI,
            memberName, membershipExpiryDate, electricalAccessory, electricalAccessoryValue,
            nonElectricalAccessory, nonElectricalAccValue, fuelKit, fuelKitValue, PAUnnamed,
            PAUnnamedValue, legalEmployee, legalEmployeeValue, legalDriver, legalDriverValue,
            fiber, emergencyCover, consumables, tyreCover, ncbProtection, engineProtector,
            returnInvoice, lossKey, roadAssistance, passengerCover, hydrostaticCover,
            hospitalCashCover, lossPersonal, policyType, maxIdv, minIdv, previousPolicyType,
            newVehicle, prePolicy, imtCover, vehicleType, make, modelName, fuelType, variantName,
            fMake, fModel, fFuel, fVariant, pcvType, pcvCompany, vehicleName, imtCover34,
            previousInsurer, previousPolicyNo, tpPolicyExpDate, sbiCode, carrierType;

    com.square.pos.utils.SearchableSpinner spnManufacture, spnModel, spnFuel, spnVariant,
            spnInsurer;

    boolean isChanged = true;
    List<Manufacture> manufactureList = new ArrayList<>();
    List<Model> modelList = new ArrayList<>();
    List<Variant> variantList = new ArrayList<>();
    List<FuelType> fuelList = new ArrayList<>();
    List<String2WithTag> fList = new ArrayList<>();
    List<StringWithTag> manuList = new ArrayList<>();
    List<String4WithTag> moList = new ArrayList<>();
    List<BankWithId> vList = new ArrayList<>();

    final List<String> companyList = new ArrayList<>();
    List<SbiData> sbiDataList = new ArrayList<>();
    List<String2WithTag> sbiList = new ArrayList<>();
    SearchableSpinner spnMiscType;

    Bundle mBundle;
    RelativeLayout rlCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_pc);
        Objects.requireNonNull(getSupportActionBar()).hide();

        mContext = this;
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait");

        spnManufacture = findViewById(R.id.edtManufacture);
        spnModel = findViewById(R.id.edtModelName);
        spnFuel = findViewById(R.id.edtFuel);
        spnVariant = findViewById(R.id.edtVariant);
        spnInsurer = findViewById(R.id.edtInsurer);
        spnMiscType = findViewById(R.id.spnMiscType);
        rlCompany = findViewById(R.id.rlCompany);

        spnManufacture.setTitle("Select Manufacture");
        spnModel.setTitle("Select Model");
        spnVariant.setTitle("Select Variant");
        spnInsurer.setTitle("Select Insurer");
        spnFuel.setTitle("Select Fuel");

        mBundle = getIntent().getExtras();
        if (mBundle != null) {
            quotationId = mBundle.getString(AppUtils.QUOTATION_ID);
            idv = mBundle.getString(AppUtils.IDV);
            policyExpDate = mBundle.getString(AppUtils.POLICY_EXPIRY_DATE);
            policyType = mBundle.getString(AppUtils.POLICY_TYPE);
            newVehicle = mBundle.getString(AppUtils.NEW_VEHICLE);
            vehicleType = mBundle.getString(AppUtils.VEHICLE_TYPE);
            pcvCompany = mBundle.getString(AppUtils.PCV_COMPANY);
            pcvType = mBundle.getString(AppUtils.PCV_TYPE);
            sbiCode = mBundle.getString(AppUtils.SBI_GCV_CODE);
            tpOnly = mBundle.getString(AppUtils.TP_ONLY);

            make = fMake = mBundle.getString(AppUtils.MAKE);
            fuelType = fFuel = mBundle.getString(AppUtils.FUEL_TYPE);
            modelName = fModel = mBundle.getString(AppUtils.MODEL);
            variantName = fVariant = mBundle.getString(AppUtils.VARIANT);

            previousInsurer = mBundle.getString(AppUtils.SAOD_INSURER, "");
            previousPolicyNo = mBundle.getString(AppUtils.SAOD_POLICY_NO, "");
            tpPolicyExpDate = mBundle.getString(AppUtils.SAOD_TP_EXP_DATE, "");

            getCovers();

            switch (vehicleType) {
                case "3":
                    rlCompany.setVisibility(View.VISIBLE);
                    companyList.add("Shriram");
                    companyList.add("Digit");
                    companyList.add("Reliance");
                    companyList.add("Bajaj");
                    companyList.add("ICICI");
                    companyList.add("HDFC");
                    break;
                case "4":
                    rlCompany.setVisibility(View.VISIBLE);
                    companyList.add("Shriram");
                    companyList.add("Digit");
                    companyList.add("Tata");
                    companyList.add("Bajaj");
                    companyList.add("SBI");
                    companyList.add("Reliance");
                    companyList.add("ICICI");
                    companyList.add("HDFC");
                    break;
                case "5":
                    rlCompany.setVisibility(View.VISIBLE);
                    companyList.add("SBI");
                    companyList.add("ICICI");
                    getMiscDData();
                    break;
                default:
                    rlCompany.setVisibility(View.GONE);
                    break;
            }

            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    mContext, android.R.layout.simple_spinner_dropdown_item, companyList);
            spnInsurer.setAdapter(spinnerArrayAdapter);

            if (!TextUtils.isEmpty(pcvCompany)) {
                for (int index = 0; index < companyList.size(); index++) {
                    if (companyList.get(index).equalsIgnoreCase(pcvCompany)) {
                        spnInsurer.setSelection(index);
                        break;
                    }
                }
            }

            spnInsurer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                    String selected = spnInsurer.getSelectedItem().toString();

                    if (!selected.toLowerCase().equalsIgnoreCase(pcvCompany)) {
                        manufactureList.clear();
                        manuList.clear();

                        moList.clear();
                        modelList.clear();

                        variantList.clear();
                        vList.clear();

                        fuelList.clear();
                        fList.clear();

                        initManufacture();
                        initModel();
                        initFuel();
                        initVariant();

                        fMake = fFuel = fModel = fVariant = "";
                        pcvCompany = selected.toLowerCase();

                        if (vehicleType.equals("5"))
                            if (index == 0) {
                                findViewById(R.id.lblSBI).setVisibility(View.VISIBLE);
                                findViewById(R.id.llMisc).setVisibility(View.VISIBLE);
                            } else {
                                findViewById(R.id.lblSBI).setVisibility(View.GONE);
                                findViewById(R.id.llMisc).setVisibility(View.GONE);
                            }
                        getManufacture();
                    } else {
                        pcvCompany = selected.toLowerCase();
                        getManufacture();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            getManufacture();
            if (!TextUtils.isEmpty(make))
                getModel();
            if (!TextUtils.isEmpty(modelName))
                getFuel();
            if (!TextUtils.isEmpty(fuelType))
                getVariant();

            vehicleName = make + " " + modelName + " " + variantName;

            if (!TextUtils.isEmpty(policyType)) {
                if (policyType.equalsIgnoreCase("Comprehensive"))
                    tpOnly = "0";
                else if (policyType.contains("Third"))
                    tpOnly = "1";
                else
                    tpOnly = "2";

            } else tpOnly = "0";
        }
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
                progressDialog.dismiss();
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
                    fList.clear();

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
            if (response instanceof SbiMisd) {
                SbiMisd nResponse = (SbiMisd) response;
                if (nResponse.getStatus().equals("1")) {
                    sbiDataList.clear();
                    sbiList.clear();
                    sbiDataList.addAll(nResponse.getSbiData());
                    initMisD();
                }
            }
            if (response instanceof FilterCar) {
                FilterCar fResponse = (FilterCar) response;
                if (fResponse.getSuccess().equals("1")) {
                    String query = fResponse.getQuery();
                    if (!TextUtils.isEmpty(query))
                        AppUtils.setTokenQuote(query);
                    progressDialog.dismiss();
                    onBack();
                }
                progressDialog.dismiss();
            }
            if (response instanceof AddonCover) {
                AddonCover fResponse = (AddonCover) response;
                if (fResponse.getStatus() == 1) {

                    if (!TextUtils.isEmpty(fResponse.getPaCover()))
                        if (fResponse.getPaCover().equals("1")) {
                            pACover = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getElectricalAccessory()))
                        if (fResponse.getElectricalAccessory().equals("1")) {
                            electricalAccessory = "1";
                            electricalAccessoryValue = fResponse.getElectricalAccessoryValue();
                        }

                    if (!TextUtils.isEmpty(fResponse.getNonElectricalAccessory()))
                        if (fResponse.getNonElectricalAccessory().equals("1")) {
                            nonElectricalAccessory = "1";
                            nonElectricalAccValue = fResponse.getNonElectricalAccessoryValue();
                        }

                    if (!TextUtils.isEmpty(fResponse.getFuleKit()))
                        if (fResponse.getFuleKit().equals("1")) {
                            fuelKit = "1";
                            fuelKitValue = fResponse.getFuleKitValue();
                        }

                    if (!TextUtils.isEmpty(fResponse.getPacoverforUnnamedPerson()))
                        if (fResponse.getPacoverforUnnamedPerson().equals("1")) {
                            PAUnnamed = "1";
                            PAUnnamedValue = fResponse.getPacoverforUnnamedPersonValue();
                        }

                    if (!TextUtils.isEmpty(fResponse.getLegalLiabilityPaidDriver()))
                        if (fResponse.getLegalLiabilityPaidDriver().equals("1")) {
                            legalDriver = "1";
                            legalDriverValue = fResponse.getLegalLiabilityPaidDriverValue();
                        }

                    if (!TextUtils.isEmpty(fResponse.getTppdRestrictedTo()))
                        if (fResponse.getTppdRestrictedTo().equals("1")) {
                            tpp = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getFiberGlassFuelTank()))
                        if (fResponse.getFiberGlassFuelTank().equals("1")) {
                            fiber = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getZeroDept()))
                        if (fResponse.getZeroDept().equals("1")) {
                            zeroDept = "1";
                        }
                    if (!TextUtils.isEmpty(fResponse.getImt23()))
                        if (fResponse.getImt23().equals("1")) {
                            imtCover = "1";
                        }
                    if (!TextUtils.isEmpty(fResponse.getImt34()))
                        if (fResponse.getImt34().equals("1")) {
                            imtCover34 = "1";
                        }
                    if (!TextUtils.isEmpty(fResponse.getEmergencyCover()))
                        if (fResponse.getEmergencyCover().equals("1")) {
                            emergencyCover = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getLegalLiabilityEmployee()))
                        if (fResponse.getLegalLiabilityEmployee().equalsIgnoreCase("1")) {
                            legalEmployee = "1";
                            legalEmployeeValue = fResponse.getLegalLiabilityEmployee();
                        }

                    if (!TextUtils.isEmpty(fResponse.getConsumables()))
                        if (fResponse.getConsumables().equals("1")) {
                            consumables = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getTyreCover()))
                        if (fResponse.getTyreCover().equals("1")) {
                            tyreCover = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getNcbProtection()))
                        if (fResponse.getNcbProtection().equals("1")) {
                            ncbProtection = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getEngineProtector()))
                        if (fResponse.getEngineProtector().equals("1")) {
                            engineProtector = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getReturnInvoice()))
                        if (fResponse.getReturnInvoice().equals("1")) {
                            returnInvoice = "1";
                        }


                    if (!TextUtils.isEmpty(fResponse.getLossOfKey()))
                        if (fResponse.getLossOfKey().equals("1")) {
                            lossKey = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getRoadSideAssistance()))
                        if (fResponse.getRoadSideAssistance().equals("1")) {
                            roadAssistance = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getPassengerAssistCover()))
                        if (fResponse.getPassengerAssistCover().equals("1")) {
                            passengerCover = "1";
                        }
                    if (!TextUtils.isEmpty(fResponse.getHospitalCashCover()))
                        if (fResponse.getHospitalCashCover().equals("1")) {
                            hospitalCashCover = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getAntiTheftDevice()))
                        if (fResponse.getAntiTheftDevice().equals("1")) {
                            antiTheft = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getVehicleModifiedForHandicap()))
                        if (fResponse.getVehicleModifiedForHandicap().equals("1")) {
                            vehicleModified = "1";
                        }

                    if (!TextUtils.isEmpty(fResponse.getVoluntaryExcess()))
                        if (fResponse.getVoluntaryExcess().equals("1")) {
                            voluntary = "1";
                            voluntaryValue = fResponse.getVoluntaryExcessValue();
                        }


                    if (!TextUtils.isEmpty(fResponse.getMemberOfAai()))
                        if (fResponse.getMemberOfAai().equals("1")) {
                            memberAAI = "1";
                            memberName = fResponse.getMemberName();
                            membershipExpiryDate = fResponse.getMembershipExpiryDate();
                        }

                    if (!TextUtils.isEmpty(fResponse.getCarrierType()))
                        carrierType = fResponse.getCarrierType();
                }
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onBack() {
        Intent intent = new Intent();
        intent.putExtra(AppUtils.IS_FILTER, "1");

        intent.putExtra(AppUtils.VEHICLE_FULL, vehicleName);
        intent.putExtra(AppUtils.MAKE, make);
        intent.putExtra(AppUtils.MODEL, modelName);
        intent.putExtra(AppUtils.FUEL_TYPE, fuelType);
        intent.putExtra(AppUtils.VARIANT, variantName);
        intent.putExtra(AppUtils.PCV_COMPANY, pcvCompany);

        mBundle.putString(AppUtils.VEHICLE_FULL, vehicleName);
        mBundle.putString(AppUtils.MAKE, make);
        mBundle.putString(AppUtils.MODEL, modelName);
        mBundle.putString(AppUtils.FUEL_TYPE, fuelType);
        mBundle.putString(AppUtils.VARIANT, variantName);
        mBundle.putString(AppUtils.PCV_COMPANY, pcvCompany);
        mBundle.putString(AppUtils.POLICY_TYPE, policyType);
        mBundle.putString(AppUtils.TP_ONLY, tpOnly);

        intent.putExtra(AppUtils.SAOD_INSURER, previousInsurer);
        intent.putExtra(AppUtils.SAOD_POLICY_NO, previousPolicyNo);
        intent.putExtra(AppUtils.SAOD_TP_EXP_DATE, tpPolicyExpDate);
        intent.putExtras(mBundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void getManufacture() {
        progressDialog.show();
        if (AppUtils.isOnline(mContext)) {
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
                        fuelType, pcvCompany);
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
                    spnManufacture.setSelection(i);
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

    public void initFuel() {
        for (int i = 0; i < fuelList.size(); i++) {
            String bankName = fuelList.get(i).getFuelType();
            String bankId = fuelList.get(i).getFuelType();
            fList.add(new String2WithTag(bankName, bankId));
        }

        ArrayAdapter<String2WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, fList);
        spnFuel.setAdapter(adapter);

        if (!TextUtils.isEmpty(fFuel) && fuelList.size() > 0)
            for (int i = 0; i < fuelList.size(); i++) {
                String sName = fuelList.get(i).getFuelType();
                if (sName.equalsIgnoreCase(fFuel)) {
                    spnFuel.setSelection(i);
                    break;
                }
            }

        spnFuel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                fuelType = stringWithTag.string;
                if (!TextUtils.isEmpty(fuelType))
                    getVariant();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void applyFilter() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
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
                        sbiCode,carrierType);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
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
                if (modelList.size() > i) {
                    String sName = modelList.get(i).getModelName();
                    if (sName.equalsIgnoreCase(modelName)) {
                        spnModel.setSelection(i);
                        break;
                    }
                    i++;
                }
            }
        }
        spnModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String4WithTag stringWithTag = (String4WithTag) parent.getItemAtPosition(position);
                fModel = stringWithTag.string;
                if (!TextUtils.isEmpty(fModel))
                    getFuel();
                getVariant();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

        if (!TextUtils.isEmpty(variantName) && variantList.size() > 0)
            for (int i = 0; i < variantList.size(); i++) {
                String modelName = variantList.get(i).getVariantName();
                String cc = variantList.get(i).getCubicCapacity();
                String full = modelName + " " + "(" + cc + ")";
                if (variantName.equals(full)) {
                    spnVariant.setSelection(i);
                    break;
                }
            }

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
    }

    private void initMisD() {
        for (int i = 0; i < sbiDataList.size(); i++) {
            String stateName = sbiDataList.get(i).getName().trim();
            String stateId = sbiDataList.get(i).getCode();
            sbiList.add(new String2WithTag(stateName, stateId));
        }

        ArrayAdapter<String2WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, sbiList);
        spnMiscType.setAdapter(adapter);

        if (!TextUtils.isEmpty(sbiCode) && sbiDataList.size() > 0)
            for (int i = 0; i < sbiDataList.size(); i++) {
                String sName = sbiDataList.get(i).getCode();
                if (sName.equalsIgnoreCase(sbiCode)) {
                    spnMiscType.setSelection(i);
                    break;
                }
            }

        spnMiscType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                sbiCode = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getMiscDData() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getMiscDData(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClose(View view) {
        finish();
    }

    public void onUpdateClick(View view) {

        if (spnManufacture.getSelectedItem() == null) {
            showError("Select Manufacture");
        } else if (spnModel.getSelectedItem() == null) {
            showError("Select Model");

        } else if (spnFuel.getSelectedItem() == null) {
            showError("Select Fuel");

        } else if (spnVariant.getSelectedItem() == null) {
            showError("Select Manufacture");

        } else {
            if (!TextUtils.isEmpty(fMake))
                make = fMake;
            if (!TextUtils.isEmpty(fModel))
                modelName = fModel;
            if (!TextUtils.isEmpty(fVariant))
                variantName = fVariant;

            if (spnFuel.getSelectedItem() != null)
                fuelType = spnFuel.getSelectedItem().toString();

            vehicleName = make + " " + modelName + " " + variantName;
            applyFilter();
        }
    }

    private void showError(String errorMsg) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout);
        TextView txtTitle = bottomSheetDialog.findViewById(R.id.txtTitle);
        TextView txtMsg = bottomSheetDialog.findViewById(R.id.txtMsg);
        Button btnClose = bottomSheetDialog.findViewById(R.id.btnClose);

        txtTitle.setText("Attention");
        txtMsg.setText(errorMsg);

        btnClose.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
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

}