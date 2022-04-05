package com.square.pos.activity_pos;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.AgentManager;
import com.square.pos.manager.UserManager;
import com.square.pos.model.City;
import com.square.pos.model.CityList;
import com.square.pos.model.Insurer;
import com.square.pos.model.InsurerList;
import com.square.pos.model.Manufacture;
import com.square.pos.model.ManufactureList;
import com.square.pos.model.Model;
import com.square.pos.model.ModelList;
import com.square.pos.model.PinList;
import com.square.pos.model.Pincode;
import com.square.pos.model.RTOList;
import com.square.pos.model.Rto;
import com.square.pos.model.Variant;
import com.square.pos.model.VariantList;
import com.square.pos.model_pos.Assign;
import com.square.pos.model_pos.AssignList;
import com.square.pos.model_pos.NewLead;
import com.square.pos.utils.AppUtils;
import com.square.pos.utils.BankWithId;
import com.square.pos.utils.String2WithTag;
import com.square.pos.utils.String3WithTag;
import com.square.pos.utils.String4WithTag;
import com.square.pos.utils.String7WithTag;
import com.square.pos.utils.StringWithTag;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import in.gauriinfotech.commons.Commons;

public class AddLeadActivity extends AbstractActivity implements onRequestCompleteCallBackListener {

    private Context mContext;
    private com.toptoche.searchablespinnerlibrary.SearchableSpinner manufacture, model, variant,
            insurer, regRTO, spnFuelType, spnPin, spnCorporateCity, spnCorpotrateType, spnEmp;

    private List<Manufacture> manufactureList = new ArrayList<>();
    private List<Model> modelList = new ArrayList<>();
    private List<Variant> varientList = new ArrayList<>();
    private List<Insurer> insurerList = new ArrayList<>();
    private List<Rto> rtoList = new ArrayList<>();
    private List<CorporateInsuranceType> coList = new ArrayList<>();

    private ProgressDialog progressdialog;

    private List<StringWithTag> manuList = new ArrayList<>();
    private List<StringWithTag> corporateList = new ArrayList<>();
    private List<String2WithTag> piList = new ArrayList<>();
    private List<String3WithTag> rList = new ArrayList<>();
    private List<String4WithTag> pin = new ArrayList<>();
    private List<String4WithTag> moList = new ArrayList<>();
    private List<BankWithId> vList = new ArrayList<>();
    private List<AssignList> assignList = new ArrayList<>();
    private List<String7WithTag> assigns = new ArrayList<>();

    private SharedPreferences preferences;
    private String vehicleType, make, modelName, variantName, regYear, previousInsurer, policyType,
            policyExpiry, registrationNumber, newVehicle, rtoCode, fuelType, manuId, insurerId,
            variantId, modelId, rtoId, cityId, coInsuranceType, documentFile, gender,
            sumAssured, planType, selfAge, spouseAge, fatherAge, motherAge, sonAge, daughterAge,
            corporateCity, orgName, agentId, leadType, name, email, mobile, manuName, expDate,
            policyExpiryStatus, documentFile2, pincode, leadFrom, refName, posName, docInvoice,
            docRCFront, docRCBack, assignName, businessType, isPrePolicy, healthPolicyType,
            docKYC, docOtherDoc;

    private EditText edtRegYear, edtName, edtEmail, edtMobile, edtRegNumber, edtPolicyExpDate,
            edtSelfAge, edtSpouseAge, edtFatherAge, edtMotherAge, edtSonAge, edtDaughterAge,
            edtOrgName;

    private String ddmmyyyy = "DDMMYYYY";
    private String current = "";
    private Calendar cal = Calendar.getInstance();
    private int lYear;

    private int registrationYear;
    private CheckBox cbPolicy;
    private Spinner spnPolicyType, spnPolicyExpire, spnLeadType, spnSumIns;
    private List<City> cityList = new ArrayList<>();
    private List<Pincode> pinList = new ArrayList<>();
    private List<String2WithTag> cities = new ArrayList<>();
    private TextView uploadDocument, uploadDocument2, uploadDocumentRCF, uploadDocumentRCB,
            uploadDocumentInvoice, uploadDocumentKYC, uploadDocumentOther, txtCom;
    private CheckBox chkSelf, chkSpouse, chkFather, chkMother, chkSon, chkDaughter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lead);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mContext = this;
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        agentId = preferences.getString(AppUtils.PRIMARY_ID, "");

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();


        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");
        spnLeadType = findViewById(R.id.spnLeadType);

        leadType = "0";
        gender = "male";
        planType = "Individual";
        businessType = "Rollover";
        isPrePolicy = "Yes";
        healthPolicyType = "Fresh";
        refName = posName = previousInsurer = pincode = leadFrom = "";

        getPinCode();
        getAssign();

        manufacture = findViewById(R.id.edtManu);
        model = findViewById(R.id.edtModel);
        variant = findViewById(R.id.edtVariant);
        insurer = findViewById(R.id.edtPreInsurer);
        regRTO = findViewById(R.id.edtRegNum);
        cbPolicy = findViewById(R.id.chkDP);

        uploadDocument = findViewById(R.id.uploadDocument);
        uploadDocument2 = findViewById(R.id.uploadDocument2);
        uploadDocumentRCF = findViewById(R.id.uploadDocumentRCF);
        uploadDocumentRCB = findViewById(R.id.uploadDocumentRCB);
        uploadDocumentInvoice = findViewById(R.id.uploadDocumentInvoice);
        uploadDocumentKYC = findViewById(R.id.uploadDocumentKYC);
        uploadDocumentOther = findViewById(R.id.uploadDocumentOther);

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtMobile = findViewById(R.id.edtPhone);

        chkSelf = findViewById(R.id.chkSelf);
        chkSpouse = findViewById(R.id.chkSpouse);
        chkFather = findViewById(R.id.chkFather);
        chkMother = findViewById(R.id.chkMother);
        chkSon = findViewById(R.id.chkSon);
        chkDaughter = findViewById(R.id.chkDaughter);

        edtRegYear = findViewById(R.id.edtRegYear);
        edtRegNumber = findViewById(R.id.edtRegNumber);
        edtPolicyExpDate = findViewById(R.id.edtPolicyExpDate);

        edtSelfAge = findViewById(R.id.edtSelfAge);
        edtSpouseAge = findViewById(R.id.edtSpouseAge);
        edtFatherAge = findViewById(R.id.edtFatherAge);
        edtMotherAge = findViewById(R.id.edtMotherAge);
        edtSonAge = findViewById(R.id.edtSonAge);
        edtDaughterAge = findViewById(R.id.edtDaughterAge);

        edtOrgName = findViewById(R.id.edtOrgName);

        spnPolicyType = findViewById(R.id.spnPolicyType);
        spnPolicyExpire = findViewById(R.id.spnPolicyExpire);
        spnFuelType = findViewById(R.id.spnFuelType);
        spnPin = findViewById(R.id.spnCityName);
        spnCorporateCity = findViewById(R.id.spnCorporateCity);
        spnCorpotrateType = findViewById(R.id.edtInsuranceType);
        spnEmp = findViewById(R.id.spnEmpName);

        spnSumIns = findViewById(R.id.spnSumIns);

        vehicleType = "1";


        spnLeadType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                int pos = spnLeadType.getSelectedItemPosition();
                if (pos == 2) {
                    leadType = "3";
                    getAssign();
                    findViewById(R.id.cd).setVisibility(View.GONE);
                    findViewById(R.id.cdHealth).setVisibility(View.VISIBLE);
                    findViewById(R.id.cdCorporate).setVisibility(View.GONE);
                } else if (pos == 3) {
                    leadType = "4";
                    getAssign();
                    findViewById(R.id.cd).setVisibility(View.GONE);
                    findViewById(R.id.cdHealth).setVisibility(View.GONE);
                    findViewById(R.id.cdCorporate).setVisibility(View.VISIBLE);
                } else if (pos == 1) {
                    leadType = "2";
                    vehicleType = "2";
                    getAssign();
                    getManufacture();
                    findViewById(R.id.cd).setVisibility(View.VISIBLE);
                    findViewById(R.id.cdHealth).setVisibility(View.GONE);
                    findViewById(R.id.cdCorporate).setVisibility(View.GONE);
                } else {
                    leadType = "1";
                    vehicleType = "1";
                    getAssign();
                    getManufacture();
                    findViewById(R.id.cd).setVisibility(View.VISIBLE);
                    findViewById(R.id.cdHealth).setVisibility(View.GONE);
                    findViewById(R.id.cdCorporate).setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
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

        rtoList = UserManager.getInstance().getRtoList();
        if (rtoList.size() > 0)
            initRtoList();
        else
            getRto();

        getPreInsurer();

        model.setTitle("Select Model");
        model.setPositiveButton(" ");

        manufacture.setTitle("Select Manufacture");
        manufacture.setPositiveButton(" ");

        variant.setTitle("Select Variant");
        variant.setPositiveButton(" ");

        regRTO.setTitle("Select RTO");
        regRTO.setPositiveButton(" ");

        spnPin.setTitle("Select City");

        spnCorporateCity.setTitle("Select City");

        spnCorporateCity.setTitle("Select City");
        spnEmp.setTitle("Assign To");
        spnCorpotrateType.setTitle("Select Insurance type");

        cityList = UserManager.getInstance().getCitiesList();
        if (cityList.size() > 0) {
            initCities();
        } else getCities();

        getCorporate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        lYear = calendar.get(Calendar.YEAR);
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 2019) ? 2020 : (year > lYear) ? lYear : year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))
                                ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    edtPolicyExpDate.setText(current);
                    edtPolicyExpDate.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        edtPolicyExpDate.addTextChangedListener(tw);


    }

    private void initCities() {
        for (int i = 0; i < cityList.size(); i++) {
            String stateName = cityList.get(i).getCityName().trim();
            String stateId = cityList.get(i).getCityName();
            cities.add(new String2WithTag(stateName, stateId));
        }

        ArrayAdapter<String2WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, cities);

        spnCorporateCity.setAdapter(adapter);

        spnCorporateCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void initPinCode() {
        for (int i = 0; i < pinList.size(); i++) {
            String stateName = pinList.get(i).getAreaName().trim();
            String stateId = pinList.get(i).getPincode();
            String pinArea = stateId + " " + stateName;
            pin.add(new String4WithTag(pinArea, stateId));
        }

        ArrayAdapter<String4WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, pin);
        spnPin.setAdapter(adapter);

        spnPin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String4WithTag stringWithTag = (String4WithTag) parent.getItemAtPosition(position);
                pincode = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof ManufactureList) {
            ManufactureList mResponse = (ManufactureList) response;
            if (mResponse.getStatus().equals(1)) {
                if (manufactureList.size() > 0) {
                    manuList.clear();
                    manufactureList.clear();
                }
                manufactureList = mResponse.getManufacture();
                if (manufactureList.size() > 0)
                    initManufacture();
            }
        }
        if (response instanceof ModelList) {
            ModelList mResponse = (ModelList) response;
            if (mResponse.getStatus().equals(1)) {
                if (modelList.size() > 0) {
                    modelList.clear();
                    moList.clear();
                }
                modelList = mResponse.getModel();
                if (modelList.size() > 0)
                    initModel();
            }
        }
        if (response instanceof VariantList) {
            VariantList mResponse = (VariantList) response;
            if (mResponse.getStatus().equals(1)) {
                if (varientList.size() > 0) {
                    varientList.clear();
                    vList.clear();
                }
                varientList = mResponse.getVariant();
                if (varientList.size() > 0)
                    initVariant();
            }
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

        if (response instanceof RTOList) {
            RTOList mResponse = (RTOList) response;
            if (mResponse.getStatus().equals(1)) {
                if (rtoList.size() > 0) {
                    rtoList.clear();
                    rList.clear();
                }
                rtoList = mResponse.getRto();
                if (rtoList.size() > 0)
                    initRtoList();
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
        if (response instanceof PinList) {
            PinList cResponse = (PinList) response;
            if (cResponse.getMessage().equalsIgnoreCase("ok")) {
                pinList.clear();
                pin.clear();
                pinList = cResponse.getPincode();
                if (pinList.size() > 0) {
                    initPinCode();
                }
            }
        }
        if (response instanceof CorporateList) {
            CorporateList mResponse = (CorporateList) response;
            if (mResponse.getStatus().equals(1)) {
                if (coList.size() > 0) {
                    coList.clear();
                    corporateList.clear();
                }
                coList = mResponse.getCorporateInsuranceType();
                if (coList.size() > 0)
                    initCorporateType();
            }
        }
        if (response instanceof Assign) {
            Assign mResponse = (Assign) response;
            if (mResponse.getStatus().equals(1)) {
                if (assignList.size() > 0) {
                    assignList.clear();
                    assigns.clear();
                }
                assignList = mResponse.getAssignList();
                if (assignList.size() > 0)
                    initAssign();
            }
        }
        if (response instanceof NewLead) {
            NewLead newLeadResponse = (NewLead) response;
            if (newLeadResponse.getSuccess().equalsIgnoreCase("1")) {
                Toast.makeText(mContext, "Lead added Successfully",
                        Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(mContext, "Error! Please try again later ",
                        Toast.LENGTH_LONG).show();
            }
            finish();
        }
        progressdialog.dismiss();

    }

    private void initManufacture() {
        for (int i = 0; i < manufactureList.size(); i++) {
            String bankName = manufactureList.get(i).getMake();
            String bankId = manufactureList.get(i).getMake();
            manuList.add(new StringWithTag(bankName, bankId));
        }

        ArrayAdapter<StringWithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, manuList);
        manufacture.setAdapter(adapter);

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
    }

    private void initModel() {
        for (int i = 0; i < modelList.size(); i++) {
            String modelName = modelList.get(i).getModelName();
            String modelId = modelList.get(i).getModelName();
            moList.add(new String4WithTag(modelName, modelId));
        }

        ArrayAdapter<String4WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, moList);
        model.setAdapter(adapter);


        model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String4WithTag stringWithTag = (String4WithTag) parent.getItemAtPosition(position);
                modelName = modelList.get(position).getModelName();
                if (!TextUtils.isEmpty(modelName))
                    getVariant();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initVariant() {
        for (int i = 0; i < varientList.size(); i++) {
            String modelName = varientList.get(i).getVariantName();
            String modelId = varientList.get(i).getVariantId();
            vList.add(new BankWithId(modelName, modelId));
        }

        ArrayAdapter<BankWithId> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, vList);
        variant.setAdapter(adapter);

        variant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BankWithId stringWithTag = (BankWithId) parent.getItemAtPosition(position);
                variantId = (String) stringWithTag.tag;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initPreInsurer() {
        for (int i = 0; i < insurerList.size(); i++) {
            String modelName = insurerList.get(i).getInsurer();
            String modelId = insurerList.get(i).getId();
            piList.add(new String2WithTag(modelName, modelId));
        }

        ArrayAdapter<String2WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, piList);
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
    }

    private void initCorporateType() {
        for (int i = 0; i < coList.size(); i++) {
            String modelName = coList.get(i).getInsuranceType();
            String modelId = coList.get(i).getId();
            corporateList.add(new StringWithTag(modelName, modelId));
        }

        ArrayAdapter<StringWithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, corporateList);
        spnCorpotrateType.setAdapter(adapter);

        spnCorpotrateType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag stringWithTag = (StringWithTag) parent.getItemAtPosition(position);
                coInsuranceType = stringWithTag.string;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void initAssign() {
        for (int i = 0; i < assignList.size(); i++) {
            String modelName = assignList.get(i).getName();
            String modelId = assignList.get(i).getEmployeeId();
            String full = modelName + "-" + modelId;
            assigns.add(new String7WithTag(full, modelId));
        }

        ArrayAdapter<String7WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, assigns);
        spnEmp.setAdapter(adapter);

        spnEmp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String7WithTag stringWithTag = (String7WithTag) parent.getItemAtPosition(position);
                assignName = stringWithTag.string;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                android.R.layout.simple_dropdown_item_1line, rList);
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
            progressdialog.show();
            try {

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
                UserManager.getInstance().getModel(mContext, make, vehicleType, "", "");
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
                UserManager.getInstance().getVariant(mContext, make, modelName, vehicleType,
                        fuelType, "");
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

    public void onCheckClick(View view) {
        if (cbPolicy.isChecked()) {
            findViewById(R.id.txtRegistration).setVisibility(View.GONE);
            findViewById(R.id.rlNewVehicle).setVisibility(View.GONE);
            findViewById(R.id.rlRC).setVisibility(View.VISIBLE);
            findViewById(R.id.rlNew).setVisibility(View.GONE);
            isPrePolicy = "no";
        } else {
            findViewById(R.id.txtRegistration).setVisibility(View.VISIBLE);
            findViewById(R.id.rlNewVehicle).setVisibility(View.VISIBLE);
            findViewById(R.id.rlRC).setVisibility(View.GONE);
            findViewById(R.id.rlNew).setVisibility(View.GONE);
            isPrePolicy = "Yes";
        }
    }

    public void onNewVehicle(View view) {

        newVehicle = "new_gadi";
        businessType = "New";
        findViewById(R.id.in01).setVisibility(View.VISIBLE);
        findViewById(R.id.txtRegistration).setVisibility(View.VISIBLE);
        findViewById(R.id.txtRegNumber).setVisibility(View.GONE);

        findViewById(R.id.rlNewVehicle).setVisibility(View.GONE);
        findViewById(R.id.chkDP).setVisibility(View.GONE);
        findViewById(R.id.rlRC).setVisibility(View.GONE);
        findViewById(R.id.rlRollover).setVisibility(View.GONE);
        findViewById(R.id.rlNew).setVisibility(View.VISIBLE);

    }

    public void onRenewalVehicle(View view) {

        newVehicle = "";
        businessType = "Rollover";
        findViewById(R.id.in01).setVisibility(View.GONE);
        findViewById(R.id.txtRegistration).setVisibility(View.GONE);
        findViewById(R.id.txtRegNumber).setVisibility(View.VISIBLE);

        findViewById(R.id.rlNewVehicle).setVisibility(View.VISIBLE);
        findViewById(R.id.chkDP).setVisibility(View.VISIBLE);
        findViewById(R.id.rlRollover).setVisibility(View.VISIBLE);
        cbPolicy.setChecked(false);
        findViewById(R.id.rlNew).setVisibility(View.GONE);


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

    public void getCorporate() {
        if (AppUtils.isOnline(mContext)) {
            try {
                AgentManager.getInstance().getCorporateList(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void onUploadFile(View view) {
        txtCom = (TextView) view;
        String path = String.valueOf(Environment.getExternalStorageDirectory());
        File file = new File(path);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(Uri.fromFile(file), "*/*");

        try {
            startActivityForResult(Intent.createChooser(intent, "Select File"),
                    AppUtils.REQUEST_CODE_FILES);
        } catch (ActivityNotFoundException e) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case AppUtils.REQUEST_CODE_FILES:

                    Uri data1 = data.getData();
                    if (txtCom == uploadDocument) {
                        documentFile = getRealPathFromURI(mContext, data1);
                        uploadDocument.setText(documentFile);
                    } else if (txtCom == uploadDocument2) {
                        documentFile2 = getRealPathFromURI(mContext, data1);
                        uploadDocument2.setText(documentFile2);
                    } else if (txtCom == uploadDocumentRCF) {
                        docRCFront = getRealPathFromURI(mContext, data1);
                        uploadDocumentRCF.setText(docRCFront);
                    } else if (txtCom == uploadDocumentRCB) {
                        docRCBack = getRealPathFromURI(mContext, data1);
                        uploadDocumentRCB.setText(docRCBack);
                    } else if (txtCom == uploadDocumentKYC) {
                        docKYC = getRealPathFromURI(mContext, data1);
                        uploadDocumentKYC.setText(docKYC);
                    } else if (txtCom == uploadDocumentOther) {
                        docOtherDoc = getRealPathFromURI(mContext, data1);
                        uploadDocumentOther.setText(docOtherDoc);
                    } else if (txtCom == uploadDocumentInvoice) {
                        docInvoice = getRealPathFromURI(mContext, data1);
                        uploadDocumentInvoice.setText(docInvoice);
                    }
                    break;
            }
        }
    }

    //    public String getRealPathFromURIFile(Uri uri) {
//        String path = null;
//        String[] proj = {MediaStore.MediaColumns.DATA};
//        Cursor cursor = getContentResolver().query(uri, proj, null, null,
//                null);
//        if (cursor != null && cursor.moveToFirst()) {
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//            path = cursor.getString(column_index);
//            if (TextUtils.isEmpty(path))
//                path = uri.getPath();
//        }
//        assert cursor != null;
//        cursor.close();
//        return path;
//    }
    private String getRealPathFromURI(Context context, Uri uri) {

        String path = null;
        if (isDownloadsDocument(uri) || isExternalStorageDocument(uri)) {
            String[] proj = {MediaStore.MediaColumns.DATA};
            Cursor cursor = getContentResolver().query(uri, proj, null, null,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                path = cursor.getString(column_index);
                if (TextUtils.isEmpty(path))
                    path = uri.getPath();
            }
            assert cursor != null;
            cursor.close();
        } else path = Commons.getPath(uri, context);
        if (path != null) {
            path = path.replace("/document/raw:", "");
        }
        return path;
    }


//    public static String getRealPathFromURI(Context context, Uri uri) throws URISyntaxException {
//        String selection = null;
//        String[] selectionArgs = null;
//        String fullPath = Commons.getPath(uri, context);
//
//        Uri is different in versions after KITKAT (Android 4.4), we need to
//        DocumentsContract.isDocumentUri(context.getApplicationContext(), uri))
//        if (Build.VERSION.SDK_INT >= 21 && DocumentsContract.isDocumentUri(context,uri))
//            if (isExternalStorageDocument(uri)) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                return Environment.getExternalStorageDirectory() + "/" + split[1];
//            } else if (isDownloadsDocument(uri)) {
//                final String id = DocumentsContract.getDocumentId(uri);
//                uri = ContentUris.withAppendedId(
//                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
//            } else if (isMediaDocument(uri)) {
//                final String docId = DocumentsContract.getDocumentId(uri);
//                final String[] split = docId.split(":");
//                final String type = split[0];
//                if ("image".equals(type)) {
//                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                } else if ("video".equals(type)) {
//                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                } else if ("audio".equals(type)) {
//                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//                }
//                selection = "_id=?";
//                selectionArgs = new String[]{
//                        split[1]
//                };
//            }
//
//        if ("content".equalsIgnoreCase(uri.getScheme())) {
//            String[] projection = {
//                    MediaStore.Images.Media.DATA
//            };
//            Cursor cursor = null;
//            try {
//                cursor = context.getContentResolver()
//                        .query(uri, projection, selection, selectionArgs, null);
//                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                if (cursor.moveToFirst()) {
//                    return cursor.getString(column_index);
//                }
//            } catch (Exception e) {
//            }
//        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            return uri.getPath();
//        }
//        return null;
//    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    public void onAddLead(View view) {
        getValues();

        if (isValid())
            addLead();

    }

    private boolean isValid() {

        if (TextUtils.isEmpty(name)) {
            edtName.setError("Could not be empty");
            edtName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(mobile)) {
            edtMobile.setError("Could not be empty");
            edtMobile.requestFocus();
            return false;
        }
        if (!AppUtils.isValidMobile(mobile)) {
            edtMobile.setError("Invalid Number");
            edtMobile.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Could not be empty");
            edtEmail.requestFocus();
            return false;
        }
        if (!AppUtils.isValidMail(email)) {
            edtEmail.setError("Invalid Email");
            edtEmail.requestFocus();
            return false;
        }


        if (spnEmp.getSelectedItem() == null) {
            ((TextView) spnEmp.getSelectedView()).setError("Select Value");
            return false;

        }


        if (leadType.equalsIgnoreCase("2")
                || leadType.equalsIgnoreCase("1")) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());

            int year = calendar.get(Calendar.YEAR);
            int sYear = year - 16;
            String mYear = edtRegYear.getText().toString().trim();

            if (!TextUtils.isEmpty(mYear)) {
                registrationYear = Integer.parseInt(mYear);

                if ((registrationYear <= year) && (registrationYear >= sYear)) {

//                    Intent intent = new Intent(mContext, BasicDetailActivity.class);
//                    startActivity(intent);
                } else {
                    edtRegYear.setError("Invalid Year");
                    edtRegYear.requestFocus();
                    Toast.makeText(mContext, "Minimum Year is " + sYear,
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                edtRegYear.setError("Empty");
                edtRegYear.requestFocus();
                return false;
            }

            if (TextUtils.isEmpty(newVehicle) ||
                    !newVehicle.equalsIgnoreCase("new_gadi")) {

                if (TextUtils.isEmpty(registrationNumber)) {
                    edtRegNumber.setError("Could not be empty");
                    edtRegNumber.requestFocus();
                    return false;
                }

            }
            if (!cbPolicy.isChecked()) {
                if (TextUtils.isEmpty(policyExpiry)) {
                    edtPolicyExpDate.setError("Could not be empty");
                    edtPolicyExpDate.requestFocus();
                    return false;
                }
            }
        }

        if (leadType.equalsIgnoreCase("4")) {
            if (TextUtils.isEmpty(orgName)) {
                edtOrgName.setError("Could not be empty");
                edtOrgName.requestFocus();
                return false;
            }

        }
        if (leadType.equalsIgnoreCase("3")) {
            if (chkSelf.isChecked()) {
                if (TextUtils.isEmpty(selfAge)) {
                    edtSelfAge.setError("Could not be empty");
                    edtSelfAge.requestFocus();
                    return false;
                }
            }
            if (chkSpouse.isChecked()) {
                if (TextUtils.isEmpty(spouseAge)) {
                    edtSpouseAge.setError("Could not be empty");
                    edtSpouseAge.requestFocus();
                    return false;
                }
            }
            if (chkFather.isChecked()) {
                if (TextUtils.isEmpty(fatherAge)) {
                    edtFatherAge.setError("Could not be empty");
                    edtFatherAge.requestFocus();
                    return false;
                }
            }
            if (chkMother.isChecked()) {
                if (TextUtils.isEmpty(motherAge)) {
                    edtMotherAge.setError("Could not be empty");
                    edtMotherAge.requestFocus();
                    return false;
                }
            }
            if (chkSon.isChecked()) {
                if (TextUtils.isEmpty(sonAge)) {
                    edtSonAge.setError("Could not be empty");
                    edtSonAge.requestFocus();
                    return false;
                }
            }
            if (chkDaughter.isChecked()) {
                if (TextUtils.isEmpty(daughterAge)) {
                    edtDaughterAge.setError("Could not be empty");
                    edtDaughterAge.requestFocus();
                    return false;
                }
            }

        }
        return true;
    }

    public void addLead() {

        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            if (TextUtils.isEmpty(documentFile))
                documentFile = "";
            if (TextUtils.isEmpty(documentFile2))
                documentFile2 = "";

            if (TextUtils.isEmpty(docRCFront))
                docRCFront = "";
            if (TextUtils.isEmpty(docRCBack))
                docRCBack = "";
            if (TextUtils.isEmpty(docInvoice))
                docInvoice = "";
            if (TextUtils.isEmpty(docKYC))
                docKYC = "";

            if (TextUtils.isEmpty(docOtherDoc))
                docOtherDoc = "";

            try {
                AgentManager.getInstance().addLead(mContext, agentId, leadType, name, email, mobile,
                        vehicleType, registrationNumber, regYear, manuName, modelName,
                        variantName, expDate, fuelType, policyType, policyExpiryStatus,
                        previousInsurer, pincode, gender, sumAssured, planType, selfAge,
                        spouseAge, fatherAge, motherAge, sonAge, daughterAge, corporateCity,
                        coInsuranceType, orgName, leadFrom, posName, refName, assignName,
                        businessType, isPrePolicy, healthPolicyType, docRCFront, docRCBack, docInvoice, documentFile,
                        documentFile2, docKYC, docOtherDoc);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void getValues() {

        name = edtName.getText().toString();
        email = edtEmail.getText().toString();
        mobile = edtMobile.getText().toString();

        sumAssured = spnSumIns.getSelectedItem().toString();
        selfAge = edtSelfAge.getText().toString();
        spouseAge = edtSpouseAge.getText().toString();
        fatherAge = edtFatherAge.getText().toString();
        motherAge = edtMotherAge.getText().toString();
        sonAge = edtSonAge.getText().toString();
        daughterAge = edtDaughterAge.getText().toString();

        registrationNumber = edtRegNumber.getText().toString();
        regYear = edtRegYear.getText().toString();
        regYear = edtRegYear.getText().toString();
        expDate = edtPolicyExpDate.getText().toString();
        fuelType = spnFuelType.getSelectedItem().toString();
        policyType = spnPolicyType.getSelectedItem().toString();
        policyExpiryStatus = spnPolicyExpire.getSelectedItem().toString();

        coInsuranceType = spnCorpotrateType.getSelectedItem().toString();
        orgName = edtOrgName.getText().toString();
        corporateCity = spnCorporateCity.getSelectedItem().toString();

        if (!leadType.equals("3")) {
            if (manufacture != null)
                manuName = manufacture.getSelectedItem().toString();
            if (model != null)
                modelName = model.getSelectedItem().toString();
            if (variant != null)
                variantName = variant.getSelectedItem().toString();
            regYear = edtRegYear.getText().toString();
        } else {
            manuName = modelName = variantName = "";
        }


        if (!cbPolicy.isChecked()) {
            previousInsurer = insurer.getSelectedItem().toString();
            policyType = spnPolicyType.getSelectedItem().toString();
            policyExpiry = spnPolicyExpire.getSelectedItem().toString();
        }
        if (!TextUtils.isEmpty(newVehicle)
                && newVehicle.equalsIgnoreCase("new_gadi")) {
            registrationNumber = rtoCode;
        }
    }

    public void onMaleClick(View view) {
        gender = "male";
    }

    public void onFemaleClick(View view) {
        gender = "female";
    }

    public void onIndividualClick(View view) {
        planType = "Individual";

        chkSelf.setChecked(false);
        chkSpouse.setChecked(false);
        chkFather.setChecked(false);
        chkMother.setChecked(false);
        chkSon.setChecked(false);
        chkDaughter.setChecked(false);
        findViewById(R.id.txtNote).setVisibility(View.GONE);
    }

    public void onFloaterClick(View view) {
        planType = "Floater";
        edtSpouseAge.setVisibility(View.VISIBLE);
        edtFatherAge.setVisibility(View.VISIBLE);
        edtMotherAge.setVisibility(View.VISIBLE);
        edtSonAge.setVisibility(View.VISIBLE);
        edtSelfAge.setVisibility(View.VISIBLE);
        edtDaughterAge.setVisibility(View.VISIBLE);
        findViewById(R.id.txtNote).setVisibility(View.VISIBLE);
    }

    public void onCheckClicked(View view) {
        CheckBox checkBox = (CheckBox) view;
        if (planType.equalsIgnoreCase("Individual")) {
            if (checkBox == chkSelf && chkSelf.isChecked()) {
                chkSpouse.setChecked(false);
                chkFather.setChecked(false);
                chkMother.setChecked(false);
                chkSon.setChecked(false);
                chkDaughter.setChecked(false);
                edtSpouseAge.setVisibility(View.GONE);
                edtFatherAge.setVisibility(View.GONE);
                edtMotherAge.setVisibility(View.GONE);
                edtSonAge.setVisibility(View.GONE);
                edtDaughterAge.setVisibility(View.GONE);
                edtSelfAge.setVisibility(View.VISIBLE);
            }
            if (checkBox == chkSpouse && chkSpouse.isChecked()) {
                chkSelf.setChecked(false);
                chkFather.setChecked(false);
                chkMother.setChecked(false);
                chkSon.setChecked(false);
                chkDaughter.setChecked(false);
                edtSelfAge.setVisibility(View.GONE);
                edtFatherAge.setVisibility(View.GONE);
                edtMotherAge.setVisibility(View.GONE);
                edtSonAge.setVisibility(View.GONE);
                edtDaughterAge.setVisibility(View.GONE);
                edtSpouseAge.setVisibility(View.VISIBLE);
            }
            if (checkBox == chkFather && chkFather.isChecked()) {
                chkSelf.setChecked(false);
                chkSpouse.setChecked(false);
                chkMother.setChecked(false);
                chkSon.setChecked(false);
                chkDaughter.setChecked(false);
                edtSpouseAge.setVisibility(View.GONE);
                edtSelfAge.setVisibility(View.GONE);
                edtMotherAge.setVisibility(View.GONE);
                edtSonAge.setVisibility(View.GONE);
                edtDaughterAge.setVisibility(View.GONE);
                edtFatherAge.setVisibility(View.VISIBLE);
            }
            if (checkBox == chkMother && chkMother.isChecked()) {
                chkSelf.setChecked(false);
                chkSpouse.setChecked(false);
                chkFather.setChecked(false);
                chkSon.setChecked(false);
                chkDaughter.setChecked(false);
                edtSpouseAge.setVisibility(View.GONE);
                edtFatherAge.setVisibility(View.GONE);
                edtSelfAge.setVisibility(View.GONE);
                edtSonAge.setVisibility(View.GONE);
                edtDaughterAge.setVisibility(View.GONE);
                edtMotherAge.setVisibility(View.VISIBLE);
            }
            if (checkBox == chkSon && chkSon.isChecked()) {
                chkSelf.setChecked(false);
                chkSpouse.setChecked(false);
                chkFather.setChecked(false);
                chkMother.setChecked(false);
                chkDaughter.setChecked(false);
                edtSpouseAge.setVisibility(View.GONE);
                edtFatherAge.setVisibility(View.GONE);
                edtMotherAge.setVisibility(View.GONE);
                edtSonAge.setVisibility(View.GONE);
                edtDaughterAge.setVisibility(View.GONE);
                edtSonAge.setVisibility(View.VISIBLE);
            }
            if (checkBox == chkDaughter && chkDaughter.isChecked()) {
                chkSelf.setChecked(false);
                chkSpouse.setChecked(false);
                chkFather.setChecked(false);
                chkMother.setChecked(false);
                chkSon.setChecked(false);
                edtSpouseAge.setVisibility(View.GONE);
                edtFatherAge.setVisibility(View.GONE);
                edtMotherAge.setVisibility(View.GONE);
                edtSonAge.setVisibility(View.GONE);
                edtSelfAge.setVisibility(View.GONE);
                edtDaughterAge.setVisibility(View.VISIBLE);
            }
        }

    }

    public void getPinCode() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getPin(mContext, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }


    public void getAssign() {
        if (AppUtils.isOnline(mContext)) {
            try {
                AgentManager.getInstance().getAssignList(mContext, leadType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void onFreshClick(View view) {
        healthPolicyType = "Fresh";
    }

    public void onPortabilityClick(View view) {
        healthPolicyType = "Portability";
    }

}
