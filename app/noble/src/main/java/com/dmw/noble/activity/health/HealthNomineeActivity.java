package com.dmw.noble.activity.health;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.activity.PrivacyActivity;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.Health.HealthManager;
import com.dmw.noble.model.TempResponse;
import com.dmw.noble.model_health.v2.Apointee;
import com.dmw.noble.model_health.v2.City;
import com.dmw.noble.model_health.v2.Gender;
import com.dmw.noble.model_health.v2.HealthApointee;
import com.dmw.noble.model_health.v2.HealthCity;
import com.dmw.noble.model_health.v2.HealthGender;
import com.dmw.noble.model_health.v2.HealthRelation;
import com.dmw.noble.model_health.v2.HealthSalutation;
import com.dmw.noble.model_health.v2.Relation;
import com.dmw.noble.model_health.v2.Salutation;
import com.dmw.noble.utils.AppUtils;
import com.dmw.noble.utils.String2WithTag;
import com.dmw.noble.utils.String3WithTag;
import com.dmw.noble.utils.String5WithTag;
import com.dmw.noble.utils.String6WithTag;
import com.dmw.noble.utils.String7WithTag;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HealthNomineeActivity extends AbstractActivity implements
        onRequestCompleteCallBackListener {

    private Context mContext;
    private String current = "";
    private String ddmmyyyy = "DDMMYYYY";
    private Calendar cal = Calendar.getInstance();
    private int mYear, lYear;
    private com.toptoche.searchablespinnerlibrary.SearchableSpinner spnNominee, spnAppointee;
    private List<Relation> nomineeHealthList = new ArrayList<>();
    private List<Apointee> appointeeHealthList = new ArrayList<>();
    private List<String2WithTag> nominees = new ArrayList<>();
    private List<String3WithTag> appointees = new ArrayList<>();

    String nomineeFirstName, nomineeLastName, nomineeRelation, nomineeAge, appointeeFirstName,
            appointeeLastName, appointeeAge, appointeeRelation, quotationId, nomineeSalutation,
            income, nGender, tenure, city, companyName, nomineePincode, nomineeCity, nMobile,
            nomineeAddress1, nomineeAddress2, addressCityId, addressPhone, addressMail,
            addressApollo1, addressApollo2, addressPincode, cityId, nomineeRelationName;

    private ProgressDialog progressdialog;
    private Bundle mBundle;
    EditText edtNomineeFName, edtLNomineeName, edtNomineeAge, edtFAppointeeName, edtAppointeeAge,
            edtLAppointeeName, edtNomineeMobile, edtNomineeAddress1, edtNomineeAddress2, edtPinCode;
    private List<City> cityList = new ArrayList<>();
    private List<String7WithTag> cities = new ArrayList<>();
    private List<Salutation> salutationList = new ArrayList<>();
    private List<String5WithTag> salutations = new ArrayList<>();
    private SearchableSpinner spnCity;
    private SimpleDateFormat dateFormatter;
    boolean isAppointee = false;

    Spinner spnGender, spnSalutation;
    private List<Gender> genderList = new ArrayList<>();
    private List<String6WithTag> genders = new ArrayList<>();
    String gName, genderId;

    EditText edtAddressMobile, edtAddressEmail, edtAddress1, edtAddress2, edtAddressPincode,
            edtAnnualIncome;
    SearchableSpinner addressCity;

    ArrayList<String> tpa, proposerFirstName, proposerLastName, proposerDob, proposerGender,
            proposerOccupation, proposerDesignation, proposerQualification, proposerMaritalStatus,
            proposerMobile, proposerEmail, proposerDocument, proposerDocumentNo, proposerAddress1,
            proposerAddress2, proposerAddress3, proposerPincode, firstName, lastName, gender, dob,
            occupation, designation, height, weight, que1, que2, que3, que4, que5, que6, alcohol,
            smoke, tobacco, narcotics, substance, nomineeRelationArray, nomineeFirstNameArray,
            nomineeLastNamArray, nomineeDob, nomineeGender, nomineeMobile, nomineeAddress1Array,
            nomineeAddress2Array, nomineePincodeArray, nomineeCityArray, apFName, aLName, aAAge,
            aRelation, proposerCity, alQuantity, alYear, smQuantity, smYear, tbQuantity, tbYear,
            narQuantity, narYear, osQuantity, osYear, medMonth, medYear, nameIllness, treatment,
            outCome, salutation, relation, cigarette, pouch, liquor, beer, wine, anyDiseases,
            addressMobile, addressEmail, address1, address2, area, pincode, annualIncome,
            title_nominee, proposerTitle, maritalStatus, exists, tobaccoBajaj, building, street,
            monthlyIncome, sameTraveller, proposerPlat, proposerStreet, sbiMobile, sbiEmail, sbiPlat,
            sbiTobacco, sbiAlcohol, sbiSmoke, sbiOther, sbiDocumentNo, sbiDocument, sbiBuilding,
            sbiStreet, sbiAddress1, sbiPincode, appointeeNameArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_nominee);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        mBundle = getIntent().getExtras();

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");

        edtNomineeFName = findViewById(R.id.edtFNomineeName);
        edtLNomineeName = findViewById(R.id.edtLNomineeName);
        edtNomineeAge = findViewById(R.id.edtNomineeAge);
        spnNominee = findViewById(R.id.spnNominee);
        edtFAppointeeName = findViewById(R.id.edtFAppointeeName);
        edtLAppointeeName = findViewById(R.id.edtLAppointeeName);
        edtAppointeeAge = findViewById(R.id.edtAppointeeAge);
        spnAppointee = findViewById(R.id.spnAppointee);
        edtNomineeMobile = findViewById(R.id.edtNomineeMobile);
        edtNomineeAddress1 = findViewById(R.id.edtNomineeAddress1);
        edtNomineeAddress2 = findViewById(R.id.edtNomineeAddress2);
        edtPinCode = findViewById(R.id.edtPinCode);
        edtAnnualIncome = findViewById(R.id.edtAnnualIncome);

        spnCity = findViewById(R.id.spnCity);

        spnGender = findViewById(R.id.spnGender);
        spnSalutation = findViewById(R.id.spnSalutation);

        edtAddressMobile = findViewById(R.id.edtAddMobile);
        edtAddressEmail = findViewById(R.id.edtAddEmail);
        edtAddress1 = findViewById(R.id.edtAddress1);
        edtAddress2 = findViewById(R.id.edtAddress2);
        edtAddressPincode = findViewById(R.id.edtAddressPinCode);
        addressCity = findViewById(R.id.spnAddressCity);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        if (mBundle != null) {

            quotationId = mBundle.getString(AppUtils.QUOTATION_ID_1);
            city = mBundle.getString(AppUtils.HL_CITY);
            cityId = mBundle.getString(AppUtils.HL_CITY_ID);
            companyName = mBundle.getString(AppUtils.HL_COMPANY);
            tpa = mBundle.getStringArrayList(AppUtils.HL_TPA);
            proposerFirstName = mBundle.getStringArrayList(AppUtils.HL_PROPOSER_FIRST_NAME);
            proposerLastName = mBundle.getStringArrayList(AppUtils.HL_PROPOSER_LAST_NAME);
            proposerDob = mBundle.getStringArrayList(AppUtils.HL_PROPOSER_DOB);
            proposerGender = mBundle.getStringArrayList(AppUtils.HL_PROPOSER_GENDER);
            proposerOccupation = mBundle.getStringArrayList(AppUtils.HL_PROPOSER_DOC_OCCUPATION);
            proposerDesignation = mBundle.getStringArrayList(AppUtils.HL_PROPOSER_DESIGNATION);
            proposerQualification = mBundle.getStringArrayList(AppUtils.HL_PROPOSER_QUALIFICATION);
            proposerMaritalStatus = mBundle.getStringArrayList(AppUtils.HL_PROPOSER_MARITAL);
            proposerMobile = mBundle.getStringArrayList(AppUtils.HL_PROPOSER_PHONE);
            proposerEmail = mBundle.getStringArrayList(AppUtils.HL_PROPOSER_EMAIL);
            proposerDocument = mBundle.getStringArrayList(AppUtils.HL_PROPOSER_DOC_TYPE);
            proposerDocumentNo = mBundle.getStringArrayList(AppUtils.HL_PROPOSER_DOC_NO);
            proposerAddress1 = mBundle.getStringArrayList(AppUtils.HL_PROPOSER_ADDRESS1);
            proposerAddress2 = mBundle.getStringArrayList(AppUtils.HL_PROPOSER_ADDRESS2);
            proposerAddress3 = mBundle.getStringArrayList(AppUtils.HL_PROPOSER_ADDRESS3);
            proposerPincode = mBundle.getStringArrayList(AppUtils.HL_PROPOSER_PIN);
            proposerCity = mBundle.getStringArrayList(AppUtils.HL_PROPOSER_CITY);
            proposerTitle = mBundle.getStringArrayList(AppUtils.HL_PROPOSER_TITLE);
            firstName = mBundle.getStringArrayList(AppUtils.HL_INSURED_FNAME);
            lastName = mBundle.getStringArrayList(AppUtils.HL_INSURED_LNAME);
            gender = mBundle.getStringArrayList(AppUtils.HL_INSURED_GENDER);
            dob = mBundle.getStringArrayList(AppUtils.HL_INSURED_DOB);
            occupation = mBundle.getStringArrayList(AppUtils.HL_INSURED_OCCUPATION);
            designation = mBundle.getStringArrayList(AppUtils.HL_INSURED_DESIGNATION);
            height = mBundle.getStringArrayList(AppUtils.HL_INSURED_HEIGHT);
            weight = mBundle.getStringArrayList(AppUtils.HL_INSURED_WEIGHT);
            que1 = mBundle.getStringArrayList(AppUtils.HL_INSURED_QUE1);
            que2 = mBundle.getStringArrayList(AppUtils.HL_INSURED_QUE2);
            que3 = mBundle.getStringArrayList(AppUtils.HL_INSURED_QUE3);
            que4 = mBundle.getStringArrayList(AppUtils.HL_INSURED_QUE4);
            que5 = mBundle.getStringArrayList(AppUtils.HL_INSURED_QUE5);
            que6 = mBundle.getStringArrayList(AppUtils.HL_INSURED_QUE6);
            alcohol = mBundle.getStringArrayList(AppUtils.HL_INSURED_ALCOHOL);
            smoke = mBundle.getStringArrayList(AppUtils.HL_INSURED_SMOKE);
            tobacco = mBundle.getStringArrayList(AppUtils.HL_INSURED_TOBACCO);
            narcotics = mBundle.getStringArrayList(AppUtils.HL_INSURED_NARCOTICS);
            substance = mBundle.getStringArrayList(AppUtils.HL_INSURED_SUBSTANCE);
            maritalStatus = mBundle.getStringArrayList(AppUtils.HL_INSURED_MARITAL);

            alQuantity = mBundle.getStringArrayList(AppUtils.HL_AL_Q);
            alYear = mBundle.getStringArrayList(AppUtils.HL_AL_Y);
            smQuantity = mBundle.getStringArrayList(AppUtils.HL_SM_Q);
            smYear = mBundle.getStringArrayList(AppUtils.HL_SM_Y);
            tbQuantity = mBundle.getStringArrayList(AppUtils.HL_TB_Q);
            tbYear = mBundle.getStringArrayList(AppUtils.HL_TB_Y);
            narQuantity = mBundle.getStringArrayList(AppUtils.HL_NAR_Q);
            narYear = mBundle.getStringArrayList(AppUtils.HL_NAR_Y);
            osQuantity = mBundle.getStringArrayList(AppUtils.HL_OS_Q);
            osYear = mBundle.getStringArrayList(AppUtils.HL_OS_Y);
            medMonth = mBundle.getStringArrayList(AppUtils.HL_MED_MONTH);
            medYear = mBundle.getStringArrayList(AppUtils.HL_MED_YEAR);
            nameIllness = mBundle.getStringArrayList(AppUtils.HL_ILLNESS);
            treatment = mBundle.getStringArrayList(AppUtils.HL_TREAT);
            outCome = mBundle.getStringArrayList(AppUtils.HL_OUTCOME);

            salutation = mBundle.getStringArrayList(AppUtils.HL_AP_SAL);
            relation = mBundle.getStringArrayList(AppUtils.HL_AP_REL);
            cigarette = mBundle.getStringArrayList(AppUtils.HL_AP_CIG);
            pouch = mBundle.getStringArrayList(AppUtils.HL_AP_POUCH);
            liquor = mBundle.getStringArrayList(AppUtils.HL_AP_LIQ);
            beer = mBundle.getStringArrayList(AppUtils.HL_AP_BEER);
            wine = mBundle.getStringArrayList(AppUtils.HL_AP_WINE);
            anyDiseases = mBundle.getStringArrayList(AppUtils.HL_AP_ANY);

            sbiMobile = mBundle.getStringArrayList(AppUtils.HL_SBI_PHONE);
            sbiEmail = mBundle.getStringArrayList(AppUtils.HL_SBI_EMAIL);
            sbiPlat = mBundle.getStringArrayList(AppUtils.HL_SBI_PLOT);

            sbiTobacco = mBundle.getStringArrayList(AppUtils.HL_SBI_Q1);
            sbiAlcohol = mBundle.getStringArrayList(AppUtils.HL_SBI_Q2);
            sbiSmoke = mBundle.getStringArrayList(AppUtils.HL_SBI_Q3);
            sbiOther = mBundle.getStringArrayList(AppUtils.HL_SBI_Q4);

            sbiDocument = mBundle.getStringArrayList(AppUtils.HL_SBI_DOC);
            sbiDocumentNo = mBundle.getStringArrayList(AppUtils.HL_SBI_DOC_NO);
            sbiBuilding = mBundle.getStringArrayList(AppUtils.HL_SBI_BUILDING);
            sbiStreet = mBundle.getStringArrayList(AppUtils.HL_SBI_STREET);
            sbiAddress1 = mBundle.getStringArrayList(AppUtils.HL_SBI_ADDRESS1);
            sbiPincode = mBundle.getStringArrayList(AppUtils.HL_SBI_PINCODE);

        }

        getSalutation();
        getHealthNominee();
        getHealthAppointee();

        if (companyName.equalsIgnoreCase("bajaj"))
            getHealthBajajCity();
        else {
            if (!companyName.equalsIgnoreCase("sbi"))
                getHealthCity();
        }

        genderList = HealthManager.getInstance().getGenderList();
        if (genderList.size() > 0)
            initGender();
        else getGender();

        if (companyName.equalsIgnoreCase("apollo")) {
            findViewById(R.id.txtAnnualIncome).setVisibility(View.VISIBLE);
            findViewById(R.id.ccApolloAddress).setVisibility(View.VISIBLE);
        }
        if (companyName.equalsIgnoreCase("sbi")) {
            findViewById(R.id.txtNomineeMobile).setVisibility(View.GONE);
            findViewById(R.id.txtNomineeAddress1).setVisibility(View.GONE);
            findViewById(R.id.txtNomineeAddress2).setVisibility(View.GONE);
            findViewById(R.id.text_city).setVisibility(View.GONE);
            findViewById(R.id.txtPincode).setVisibility(View.GONE);
        }
    }

    private void initGender() {
        for (int i = 0; i < genderList.size(); i++) {
            String stateName = genderList.get(i).getName().trim();
            String stateId = genderList.get(i).getCode();
            genders.add(new String6WithTag(stateName, stateId));
        }

        ArrayAdapter<String6WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, genders);
        spnGender.setAdapter(adapter);

        for (int i = 0; i < genderList.size(); i++) {
            String stateName = genderList.get(i).getName().trim();
            if (stateName.equalsIgnoreCase("male")) {
                spnGender.setSelection(i);
                break;
            }
        }
        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String6WithTag stringWithTag = (String6WithTag) parent.getItemAtPosition(position);
                genderId = (String) stringWithTag.tag;
                gName = stringWithTag.string;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getGender() {
        if (AppUtils.isOnline(mContext)) {
            try {
                HealthManager.getInstance().getGender(mContext, quotationId, companyName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
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

    private void initNominee() {
        for (int i = 0; i < nomineeHealthList.size(); i++) {
            String stateName = nomineeHealthList.get(i).getName().trim();
            String stateId = String.valueOf(nomineeHealthList.get(i).getCode());
            nominees.add(new String2WithTag(stateName, stateId));
        }

        ArrayAdapter<String2WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, nominees);
        spnNominee.setAdapter(adapter);

        spnNominee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                nomineeRelation = (String) stringWithTag.tag;
                nomineeRelationName = (String) stringWithTag.string;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initAppointee() {
        for (int i = 0; i < appointeeHealthList.size(); i++) {
            String stateName = appointeeHealthList.get(i).getName();
            String stateId = String.valueOf(appointeeHealthList.get(i).getCode());
            appointees.add(new String3WithTag(stateName, stateId));
        }

        ArrayAdapter<String3WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, appointees);
        spnAppointee.setAdapter(adapter);


        spnAppointee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String3WithTag stringWithTag = (String3WithTag) parent.getItemAtPosition(position);
                appointeeRelation = (String) stringWithTag.string;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void onTermsClick(View view) {
        startActivity(new Intent(mContext, PrivacyActivity.class));
    }

    @Override
    public void onResponse(Object response) {

        if (response instanceof HealthRelation) {
            HealthRelation cResponse = (HealthRelation) response;
            if (cResponse.getStatus().equalsIgnoreCase("1")) {
                nomineeHealthList.clear();
                nominees.clear();
                nomineeHealthList = cResponse.getRelation();
                if (nomineeHealthList.size() > 0) {
                    initNominee();
                }
            }
        }
        if (response instanceof HealthSalutation) {
            HealthSalutation cResponse = (HealthSalutation) response;
            if (cResponse.getStatus().equalsIgnoreCase("1")
                    && cResponse.getSalutation() != null) {

                salutationList.clear();
                salutations.clear();
                salutationList = cResponse.getSalutation();
                if (salutationList.size() > 0) {
                    initSalutation();
                }
            }
        }
        if (response instanceof HealthCity) {
            HealthCity cResponse = (HealthCity) response;
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
        if (response instanceof HealthGender) {
            HealthGender cResponse = (HealthGender) response;
            if (cResponse.getStatus().equalsIgnoreCase("1") && cResponse.getGender() != null) {
                genderList.clear();
                genders.clear();
                genderList = cResponse.getGender();
                if (genderList.size() > 0) {
                    initGender();
                }
            }
        }
        if (response instanceof HealthApointee) {
            HealthApointee cResponse = (HealthApointee) response;
            if (cResponse.getStatus().equalsIgnoreCase("1")) {
                appointeeHealthList.clear();
                appointees.clear();
                appointeeHealthList = cResponse.getApointee();
                if (appointeeHealthList != null)
                    if (appointeeHealthList.size() > 0) {
                        initAppointee();
                    }
            }
        }

        if (response instanceof TempResponse) {
            TempResponse cResponse = (TempResponse) response;
            if (cResponse.getStatus().equalsIgnoreCase("1")) {
                review();
            }
        }

        progressdialog.dismiss();
    }

    public void getHealthNominee() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                HealthManager.getInstance().getRelation(mContext, quotationId, companyName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }

    public void getHealthAppointee() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                HealthManager.getInstance().getAppointee(mContext, quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }

    private void getValues() {

        nomineeFirstName = edtNomineeFName.getText().toString();
        nomineeLastName = edtLNomineeName.getText().toString();
        nomineeAge = edtNomineeAge.getText().toString();

        appointeeFirstName = edtFAppointeeName.getText().toString();
        appointeeLastName = edtLAppointeeName.getText().toString();
        appointeeAge = edtAppointeeAge.getText().toString();
        nMobile = edtNomineeMobile.getText().toString();
        nomineeAddress1 = edtNomineeAddress1.getText().toString();
        nomineeAddress2 = edtNomineeAddress2.getText().toString();
        nomineePincode = edtPinCode.getText().toString();

        addressPhone = edtAddressMobile.getText().toString();
        addressMail = edtAddressEmail.getText().toString();
        addressApollo1 = edtAddress1.getText().toString();
        addressApollo2 = edtAddress2.getText().toString();
        addressPincode = edtAddressPincode.getText().toString();
        income = edtAnnualIncome.getText().toString();
    }

    public void updateHealthProposal() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                nomineeRelationArray = new ArrayList<>();
                nomineeFirstNameArray = new ArrayList<>();
                nomineeLastNamArray = new ArrayList<>();
                nomineeAddress1Array = new ArrayList<>();
                nomineeAddress2Array = new ArrayList<>();
                nomineePincodeArray = new ArrayList<>();
                nomineeCityArray = new ArrayList<>();
                appointeeNameArray = new ArrayList<>();
                apFName = new ArrayList<>();
                aLName = new ArrayList<>();
                aAAge = new ArrayList<>();
                aRelation = new ArrayList<>();
                nomineeDob = new ArrayList<>();
                nomineeGender = new ArrayList<>();
                nomineeMobile = new ArrayList<>();

                title_nominee = new ArrayList<>();
                annualIncome = new ArrayList<>();
                addressMobile = new ArrayList<>();
                addressEmail = new ArrayList<>();
                pincode = new ArrayList<>();
                area = new ArrayList<>();
                address1 = new ArrayList<>();
                address2 = new ArrayList<>();

                nomineeRelationArray.add(nomineeRelation);
                nomineeFirstNameArray.add(nomineeFirstName);
                nomineeLastNamArray.add(nomineeLastName);
                nomineeDob.add(nomineeAge);
                appointeeNameArray.add((appointeeFirstName + " " + appointeeLastName));
                apFName.add(appointeeFirstName);
                aLName.add(appointeeLastName);
                aRelation.add(appointeeRelation);
                nomineeGender.add(genderId);
                nomineeMobile.add(nMobile);
                nomineeCityArray.add(nomineeCity);
                nomineePincodeArray.add(nomineePincode);
                nomineeAddress1Array.add(nomineeAddress1);
                nomineeAddress2Array.add(nomineeAddress2);

                if (companyName.equalsIgnoreCase("apollo")) {

                    annualIncome.add(income);
                    title_nominee.add(nomineeSalutation);
                    addressMobile.add(addressPhone);
                    addressEmail.add(addressMail);
                    pincode.add(addressPincode);
                    area.add(addressCityId);
                    address1.add(addressApollo1);
                    address2.add(addressApollo2);
                }

                HealthManager.getInstance().updateHealthProposal(mContext, companyName,
                        quotationId, nomineePincodeArray, nomineeCityArray, tpa, proposerFirstName,
                        proposerLastName, proposerDob, proposerGender, proposerOccupation,
                        proposerDesignation, proposerQualification, proposerMaritalStatus,
                        proposerMobile, proposerEmail, proposerDocument, proposerDocumentNo,
                        proposerAddress1, proposerAddress2, proposerAddress3, proposerPincode,
                        proposerCity, firstName, lastName, gender, dob, occupation, designation,
                        height, weight, que1, que2, que3, que4, que5, que6, alcohol, smoke, tobacco,
                        narcotics, substance, nomineeRelationArray, nomineeFirstNameArray,
                        nomineeLastNamArray, nomineeDob, nomineeGender, nomineeMobile,
                        nomineeAddress1Array, nomineeAddress2Array, apFName, aLName, aAAge,
                        aRelation, alQuantity, alYear, smQuantity, smYear, tbQuantity, tbYear,
                        narQuantity, narYear, osQuantity, osYear, medMonth, medYear, nameIllness,
                        treatment, outCome, proposerTitle, maritalStatus, salutation, relation, cigarette, pouch,
                        liquor, beer, wine, anyDiseases, addressMobile, addressEmail, address1,
                        address2, area, pincode, annualIncome, title_nominee, exists,
                        tobaccoBajaj, building, street, monthlyIncome, sameTraveller);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }

    public void updateHealthProposalSbi() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                nomineeRelationArray = new ArrayList<>();
                nomineeFirstNameArray = new ArrayList<>();
                nomineeLastNamArray = new ArrayList<>();
                nomineeAddress1Array = new ArrayList<>();
                nomineeAddress2Array = new ArrayList<>();
                nomineePincodeArray = new ArrayList<>();
                nomineeCityArray = new ArrayList<>();
                appointeeNameArray = new ArrayList<>();
                apFName = new ArrayList<>();
                aLName = new ArrayList<>();
                aAAge = new ArrayList<>();
                aRelation = new ArrayList<>();
                nomineeDob = new ArrayList<>();
                nomineeGender = new ArrayList<>();
                nomineeMobile = new ArrayList<>();

                title_nominee = new ArrayList<>();
                annualIncome = new ArrayList<>();
                addressMobile = new ArrayList<>();
                addressEmail = new ArrayList<>();
                pincode = new ArrayList<>();
                area = new ArrayList<>();
                address1 = new ArrayList<>();
                address2 = new ArrayList<>();

                proposerPlat = new ArrayList<>();
                proposerStreet = new ArrayList<>();
                proposerAddress1 = new ArrayList<>();
                proposerPincode = new ArrayList<>();

                nomineeRelationArray.add(nomineeRelationName);
                nomineeFirstNameArray.add(nomineeFirstName);
                nomineeLastNamArray.add(nomineeLastName);
                nomineeDob.add(nomineeAge);
                appointeeNameArray.add((appointeeFirstName + " " + appointeeLastName));
                apFName.add(appointeeFirstName);
                aLName.add(appointeeLastName);
                aRelation.add(appointeeRelation);
                nomineeGender.add(genderId);
                nomineeMobile.add(nMobile);
                nomineeCityArray.add(nomineeCity);
                nomineePincodeArray.add(nomineePincode);
                nomineeAddress1Array.add(nomineeAddress1);
                nomineeAddress2Array.add(nomineeAddress2);

                sameTraveller = new ArrayList<>();
                sameTraveller.add("1");

                HealthManager.getInstance().updateHealthProposalSbi(mContext, companyName,
                        quotationId, proposerTitle, proposerFirstName, proposerLastName, proposerDob,
                        proposerGender, proposerDesignation, proposerMaritalStatus, proposerMobile,
                        proposerEmail, proposerPlat, proposerStreet, proposerAddress1,
                        proposerPincode, salutation, firstName, lastName, gender, dob, sbiMobile,
                        sbiEmail, maritalStatus, sbiDocument, sbiDocumentNo, occupation, height,
                        weight, sbiPlat, sbiBuilding, sbiStreet, sbiAddress1, sbiPincode,
                        nomineeRelationArray, nomineeFirstNameArray, nomineeLastNamArray,
                        nomineeGender, nomineeDob, sameTraveller, appointeeNameArray, aRelation,
                        sbiAlcohol, sbiSmoke, sbiTobacco, sbiOther);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }

    private boolean isValid() {

        getValues();

        if (TextUtils.isEmpty(nomineeFirstName)) {
            edtNomineeFName.setError(getString(R.string.field_cannot));
            edtNomineeFName.requestFocus();
            return false;
        } else if (!AppUtils.validateName(nomineeFirstName)) {
            edtNomineeFName.setError(getString(R.string.invalid_name));
            edtNomineeFName.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(nomineeLastName)) {
            edtLNomineeName.setError(getString(R.string.field_cannot));
            edtLNomineeName.requestFocus();
            return false;
        } else if (!AppUtils.validateName(nomineeLastName)) {
            edtLNomineeName.setError(getString(R.string.invalid_name));
            edtLNomineeName.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(nomineeAge)) {
            edtNomineeAge.setError(getString(R.string.field_cannot));
            edtNomineeAge.requestFocus();
            return false;
        } else {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = null;
            try {
                date = formatter.parse(nomineeAge);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (date != null && (!TextUtils.isEmpty(nomineeAge))) {
                nomineeAge = newFormat.format(date);
            }
        }
        if (!companyName.equalsIgnoreCase("sbi")) {
            if (TextUtils.isEmpty(nomineeAddress1)) {
                edtNomineeAddress1.setError(getString(R.string.field_cannot));
                edtNomineeAddress1.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(nomineeAddress2)) {
                edtNomineeAddress2.setError(getString(R.string.field_cannot));
                edtNomineeAddress2.requestFocus();
                return false;
            } else if (nomineeAddress1.length() < 5) {
                edtNomineeAddress1.setError("Minimum 5 Characters");
                edtNomineeAddress1.requestFocus();
                return false;
            } else if (nomineeAddress2.length() < 10) {
                edtNomineeAddress2.setError("Minimum 10 Characters");
                edtNomineeAddress2.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(nMobile)) {
                edtNomineeMobile.setError(getString(R.string.field_cannot));
                edtNomineeMobile.requestFocus();
                return false;
            } else if (!(AppUtils.isValidMobile(nMobile))) {
                edtNomineeMobile.setError("Invalid Phone Number");
                edtNomineeMobile.requestFocus();
                return false;
            }
            if (TextUtils.isEmpty(nomineePincode)) {
                edtPinCode.setError(getString(R.string.field_cannot));
                edtPinCode.requestFocus();
                return false;
            } else if (nomineePincode.length() < 6) {
                edtPinCode.setError(getString(R.string.minimum_6));
                edtPinCode.requestFocus();
                return false;
            }
        }

//        if (spnGender.getSelectedItemPosition() > 0)
//            nGender = spnGender.getSelectedItem().toString().toLowerCase();
//        else {
//            Toast.makeText(mContext, "Select Gender", Toast.LENGTH_SHORT).show();
//            return false;
//        }

        if (isAppointee) {
            if (TextUtils.isEmpty(appointeeFirstName)) {
                edtFAppointeeName.setError(getString(R.string.field_cannot));
                edtFAppointeeName.requestFocus();
                return false;
            } else if (!AppUtils.validateName(appointeeFirstName)) {
                edtFAppointeeName.setError(getString(R.string.invalid_name));
                edtFAppointeeName.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(appointeeLastName)) {
                edtLAppointeeName.setError(getString(R.string.field_cannot));
                edtLAppointeeName.requestFocus();
                return false;
            } else if (!AppUtils.validateName(appointeeLastName)) {
                edtLAppointeeName.setError(getString(R.string.invalid_name));
                edtLAppointeeName.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(appointeeAge)) {
                edtAppointeeAge.setError(getString(R.string.field_cannot));
                edtAppointeeAge.requestFocus();
                return false;
            }
        }
        if (companyName.equalsIgnoreCase("apollo")) {

            if (TextUtils.isEmpty(addressPhone)) {
                edtAddressMobile.setError(getString(R.string.field_cannot));
                edtAddressMobile.requestFocus();
                return false;
            } else if (!(AppUtils.isValidMobile(addressPhone))) {
                edtAddressMobile.setError("Invalid Phone Number");
                edtAddressMobile.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(addressMail)) {
                edtAddressEmail.setError(getString(R.string.field_cannot));
                edtAddressEmail.requestFocus();
                return false;
            } else if (!(AppUtils.isValidMail(addressMail))) {
                edtAddressEmail.setError("Invalid Email");
                edtAddressEmail.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(addressPincode)) {
                edtAddressPincode.setError(getString(R.string.field_cannot));
                edtAddressPincode.requestFocus();
                return false;
            } else if (addressPincode.length() < 6) {
                edtAddressPincode.setError(getString(R.string.minimum_6));
                edtAddressPincode.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(addressApollo1)) {
                edtAddress1.setError(getString(R.string.field_cannot));
                edtAddress1.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(addressApollo2)) {
                edtAddress2.setError(getString(R.string.field_cannot));
                edtAddress2.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(income)) {
                edtAnnualIncome.setError(getString(R.string.field_cannot));
                edtAnnualIncome.requestFocus();
                return false;
            }
        }

        return true;
    }

    public void onMedicalHistory(View view) {
        if (isValid()) {
            if (companyName.equalsIgnoreCase("sbi"))
                updateHealthProposalSbi();
            else
                updateHealthProposal();
        }
    }

    public void getHealthCity() {
        if (AppUtils.isOnline(mContext)) {
            try {
                HealthManager.getInstance().getHealthCity(mContext, quotationId, companyName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getHealthBajajCity() {
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
            String stateName = cityList.get(i).getState();
            String stateId = cityList.get(i).getId();
            cities.add(new String7WithTag(cityName + " - " + stateName, stateId));
        }

        ArrayAdapter<String7WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, cities);
        spnCity.setAdapter(adapter);
        addressCity.setAdapter(adapter);

        for (int i = 0; i < cityList.size(); i++) {
            String stateName = cityList.get(i).getId().trim();
            if (stateName.equalsIgnoreCase(cityId)) {
                spnCity.setSelection(i);
                addressCity.setSelection(i);
                nomineeCity = addressCityId = cityList.get(i).getId();
                break;
            }
        }

        spnCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String7WithTag stringWithTag = (String7WithTag) parent.getItemAtPosition(position);
                nomineeCity = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addressCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void initSalutation() {
        for (int i = 0; i < salutationList.size(); i++) {
            String stateName = salutationList.get(i).getName().trim();
            String stateId = salutationList.get(i).getCode();
            salutations.add(new String5WithTag(stateName, stateId));
        }

        ArrayAdapter<String5WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, salutations);
        spnSalutation.setAdapter(adapter);

        spnSalutation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String5WithTag stringWithTag = (String5WithTag) parent.getItemAtPosition(position);
                nomineeSalutation = (String) stringWithTag.tag;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getSalutation() {
        if (AppUtils.isOnline(mContext)) {
            try {
                HealthManager.getInstance().getSalutation(mContext, quotationId, companyName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void review() {

        mBundle.putString(AppUtils.NOMINEE_NAME, (nomineeFirstName + " " + nomineeLastName));
        mBundle.putString(AppUtils.NOMINEE_RELATION, nomineeRelation);
        mBundle.putString(AppUtils.NOMINEE_MOBILE, nMobile);

        Intent intent = new Intent(mContext, HealthReviewActivity.class);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    public void onDateClick(View view) {

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                R.style.datepicker, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                int years = AppUtils.getAge(dateFormatter.format(newDate.getTime()));
                edtNomineeAge.setText(dateFormatter.format(newDate.getTime()));

                if (years < 18 && (companyName.equalsIgnoreCase("royal")
                        || companyName.equalsIgnoreCase("sbi"))) {
                    isAppointee = true;
                    findViewById(R.id.cc2).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.cc2).setVisibility(View.GONE);
                    isAppointee = false;
                }
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
        try {
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorPrimary));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}