package com.dmw.noble.activity.health.royal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.activity.health.MemberDetailActivity;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.Health.HealthManager;
import com.dmw.noble.model_health.v2.City;
import com.dmw.noble.model_health.v2.Document;
import com.dmw.noble.model_health.v2.Education;
import com.dmw.noble.model_health.v2.Gender;
import com.dmw.noble.model_health.v2.HealthCity;
import com.dmw.noble.model_health.v2.HealthDocument;
import com.dmw.noble.model_health.v2.HealthGender;
import com.dmw.noble.model_health.v2.HealthMarital;
import com.dmw.noble.model_health.v2.HealthOccupation;
import com.dmw.noble.model_health.v2.HealthQualification;
import com.dmw.noble.model_health.v2.HealthSalutation;
import com.dmw.noble.model_health.v2.MaritalStatus;
import com.dmw.noble.model_health.v2.Occupation;
import com.dmw.noble.model_health.v2.Salutation;
import com.dmw.noble.utils.AppUtils;
import com.dmw.noble.utils.String2WithTag;
import com.dmw.noble.utils.String3WithTag;
import com.dmw.noble.utils.String4WithTag;
import com.dmw.noble.utils.String5WithTag;
import com.dmw.noble.utils.String6WithTag;
import com.dmw.noble.utils.String7WithTag;
import com.dmw.noble.utils.StringWithTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProposerRoyalActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {

    ImageView imgInsurer;
    TextView txtQuoteId, txtPolicyType, txtTotalPremium, txtPlanName, txtPolicyTenure;
    Spinner spnTpa, spnGender, spnMaterial, spnDocType, spnOccupation;
    EditText edtFirstName, edtLastName, edtDob, edtEmail, edtPhone, edtDocNo, edtDes, edtAddress1,
            edtAddress2, edtAddress3, edtPinCode;
    com.toptoche.searchablespinnerlibrary.SearchableSpinner spnCity, spnQualification;
    Context mContext;
    ProgressDialog progressdialog;

    private List<Occupation> occupationsList = new ArrayList<>();

    private List<Gender> genderList = new ArrayList<>();
    private List<String6WithTag> genders = new ArrayList<>();
    String gName, genderId;
    private List<Document> documentList = new ArrayList<>();
    private List<Education> qualificationList = new ArrayList<>();
    private List<City> cityList = new ArrayList<>();
    private List<Salutation> salutationList = new ArrayList<>();
    private List<String5WithTag> salutations = new ArrayList<>();
    private List<StringWithTag> qLists = new ArrayList<>();
    private List<String2WithTag> docsList = new ArrayList<>();
    private List<MaritalStatus> maritalList = new ArrayList<>();
    private List<String3WithTag> maritalLists = new ArrayList<>();
    private List<String4WithTag> occupations = new ArrayList<>();

    private List<String7WithTag> cities = new ArrayList<>();


    String occupationId, maritalId, oName, mName, companyName, quotationId,
            imgPath, totalPremium, planName, planType, planTenure, policyStartDate, policyEndDate,
            tpa, fName, lName, dob, email, phone, qualification, document, documentNo, designation,
            address1, address2, address3, city, pincode, cityId, title, documentId;

    Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_proposer_royal);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        this.setTitle("Life Insurance");
        mBundle = getIntent().getExtras();

        imgInsurer = findViewById(R.id.imgInsurer);
        txtQuoteId = findViewById(R.id.txtQuoteId);
        txtPolicyType = findViewById(R.id.txtPolicyType);
        txtTotalPremium = findViewById(R.id.txtTotalPremium);
        txtPlanName = findViewById(R.id.txtPlanName);
        txtPolicyTenure = findViewById(R.id.txtPolicyTenure);

        spnTpa = findViewById(R.id.spnTpa);
        spnGender = findViewById(R.id.spnGender);
        spnMaterial = findViewById(R.id.spnMaterial);
        spnDocType = findViewById(R.id.spnDocType);
        spnOccupation = findViewById(R.id.spnOccupation);
        spnQualification = findViewById(R.id.spnQualification);

        spnCity = findViewById(R.id.spnCity);
        edtPinCode = findViewById(R.id.edtPinCode);

        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtDob = findViewById(R.id.edtDob);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtDocNo = findViewById(R.id.edtDocNo);
        edtDes = findViewById(R.id.edtDes);
        edtAddress1 = findViewById(R.id.edtAddress1);
        edtAddress2 = findViewById(R.id.edtAddress2);
        edtAddress3 = findViewById(R.id.edtAddress3);

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");

//        if (mBundle != null) {
//            quotationId = mBundle.getString(AppUtils.QUOTATION_ID_1);
//            imgPath = mBundle.getString(AppUtils.HEALTH_INSURER_IMG_PATH);
//            totalPremium = mBundle.getString(AppUtils.HEALTH_TOTAL_PREMIUM);
//            planName = mBundle.getString(AppUtils.HEALTH_PLAN_NAME);
//            planType = mBundle.getString(AppUtils.HEALTH_PLAN);
//            city = mBundle.getString(AppUtils.HL_CITY);
//            companyName = mBundle.getString(AppUtils.HL_COMPANY);
//            planTenure = mBundle.getString(AppUtils.HL_TENURE, "1");
//
//            txtQuoteId.setText(quotationId);
//            txtPlanName.setText(planName);
//            txtPolicyType.setText(planType);
//            txtTotalPremium.setText("Total Premium: " + totalPremium);
//
//            Calendar cal = Calendar.getInstance();
//            policyStartDate = AppUtils.SIMPLE_DATE_FORMAT.format(cal.getTime());
//            policyEndDate = AppUtils.addYears(policyStartDate, Integer.parseInt(planTenure));
//
//            txtPolicyTenure.setText("Policy tenure: " + policyStartDate + " - " + policyEndDate);
//
//            Glide.with(mContext)
//                    .load(imgPath)
//                    .placeholder(R.drawable.placeholder)
//                    .error(R.drawable.placeholder)
//                    .animate(android.R.anim.fade_in)
//                    .into(imgInsurer);
//
//        }
//
//        getGender();
//        getDocuments();
//        getHealthCity();
//        getMaritalStatus();
//        if (companyName.equalsIgnoreCase("apollo")) {
//            getSalutation();
//            spnQualification.setVisibility(View.GONE);
//            findViewById(R.id.text_occ).setVisibility(View.GONE);
//            findViewById(R.id.textDes).setVisibility(View.GONE);
//            findViewById(R.id.lblQualification).setVisibility(View.GONE);
//        } else {
//            getOccupation();
//            getQualification();
//        }
//
//
//        final String[] current = {""};
//        String ddmmyyyy = "DDMMYYYY";
//        Calendar cal = Calendar.getInstance();
//        int mYear, lYear;
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        mYear = calendar.get(Calendar.YEAR);
//        lYear = mYear - 19;
//
//        String calSelf = mBundle.getString(AppUtils.CAL_SELF);
//        if (!TextUtils.isEmpty(calSelf))
//            edtDob.setText(calSelf);
//
//        TextWatcher tw = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (!s.toString().equals(current[0])) {
//                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
//                    String cleanC = current[0].replaceAll("[^\\d.]|\\.", "");
//
//                    int cl = clean.length();
//                    int sel = cl;
//                    for (int i = 2; i <= cl && i < 6; i += 2) {
//                        sel++;
//                    }
//                    //Fix for pressing delete next to a forward slash
//                    if (clean.equals(cleanC)) sel--;
//
//                    if (clean.length() < 8) {
//                        clean = clean + ddmmyyyy.substring(clean.length());
//                    } else {
//                        //This part makes sure that when we finish entering numbers
//                        //the date is correct, fixing it otherwise
//                        int day = Integer.parseInt(clean.substring(0, 2));
//                        int mon = Integer.parseInt(clean.substring(2, 4));
//                        int year = Integer.parseInt(clean.substring(4, 8));
//
//                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
//                        cal.set(Calendar.MONTH, mon - 1);
//                        year = (year < 1950) ? 1950 : (year > lYear) ? lYear : year;
//                        cal.set(Calendar.YEAR, year);
//                        // ^ first set year for the line below to work correctly
//                        //with leap years - otherwise, date e.g. 29/02/2012
//                        //would be automatically corrected to 28/02/2012
//
//                        day = (day > cal.getActualMaximum(Calendar.DATE))
//                                ? cal.getActualMaximum(Calendar.DATE) : day;
//                        clean = String.format("%02d%02d%02d", day, mon, year);
//                    }
//
//                    clean = String.format("%s-%s-%s", clean.substring(0, 2),
//                            clean.substring(2, 4),
//                            clean.substring(4, 8));
//
//                    sel = sel < 0 ? 0 : sel;
//                    current[0] = clean;
//                    edtDob.setText(current[0]);
//                    edtDob.setSelection(sel < current[0].length() ? sel : current[0].length());
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        };
//        edtDob.addTextChangedListener(tw);

    }

    public void onInsuredDetail(View view) {
        if (isValidation()) {

            Intent intent = new Intent(mContext, MemberDetailActivity.class);

            ArrayList<String> tpaType = new ArrayList<>();
            ArrayList<String> proposerTitle = new ArrayList<>();
            ArrayList<String> proposerFirstName = new ArrayList<>();
            ArrayList<String> proposerLastName = new ArrayList<>();
            ArrayList<String> dateOfBirth = new ArrayList<>();
            ArrayList<String> proposerGender = new ArrayList<>();
            ArrayList<String> maritalStatus = new ArrayList<>();
            ArrayList<String> emailArray = new ArrayList<>();
            ArrayList<String> phoneArray = new ArrayList<>();

            ArrayList<String> proposerQualification = new ArrayList<>();
            ArrayList<String> proposerDocumentType = new ArrayList<>();
            ArrayList<String> proposerDocumentNo = new ArrayList<>();
            ArrayList<String> proposerOccupation = new ArrayList<>();
            ArrayList<String> proposerDesignation = new ArrayList<>();
            ArrayList<String> proposerCityId = new ArrayList<>();
            ArrayList<String> proposerPincode = new ArrayList<>();
            ArrayList<String> proposerAddress1 = new ArrayList<>();
            ArrayList<String> proposerAddress2 = new ArrayList<>();
            ArrayList<String> proposerAddress3 = new ArrayList<>();

            if (companyName.equalsIgnoreCase("royal"))
                tpaType.add(tpa);
            else
                proposerTitle.add(title);
            proposerFirstName.add(fName);
            proposerLastName.add(lName);
            dateOfBirth.add(dob);
            proposerGender.add(genderId);
            maritalStatus.add(maritalId);
            emailArray.add(email);
            phoneArray.add(phone);
            proposerQualification.add(qualification);
            proposerDocumentType.add(documentId);
            proposerDocumentNo.add(documentNo);
            proposerOccupation.add(oName);
            proposerDesignation.add(designation);
            proposerCityId.add(cityId);
            proposerPincode.add(pincode);
            proposerAddress1.add(address1);
            proposerAddress2.add(address2);
            proposerAddress3.add(address3);

            mBundle.putStringArrayList(AppUtils.HL_TPA, tpaType);
            mBundle.putStringArrayList(AppUtils.HL_PROPOSER_TITLE, proposerTitle);
            mBundle.putStringArrayList(AppUtils.HL_PROPOSER_FIRST_NAME, proposerFirstName);
            mBundle.putStringArrayList(AppUtils.HL_PROPOSER_LAST_NAME, proposerLastName);
            mBundle.putStringArrayList(AppUtils.HL_PROPOSER_DOB, dateOfBirth);
            mBundle.putStringArrayList(AppUtils.HL_PROPOSER_GENDER, proposerGender);
            mBundle.putStringArrayList(AppUtils.HL_PROPOSER_MARITAL, maritalStatus);
            mBundle.putStringArrayList(AppUtils.HL_PROPOSER_EMAIL, emailArray);
            mBundle.putStringArrayList(AppUtils.HL_PROPOSER_PHONE, phoneArray);
            mBundle.putStringArrayList(AppUtils.HL_PROPOSER_QUALIFICATION, proposerQualification);
            mBundle.putStringArrayList(AppUtils.HL_PROPOSER_DOC_TYPE, proposerDocumentType);
            mBundle.putStringArrayList(AppUtils.HL_PROPOSER_DOC_NO, proposerDocumentNo);
            mBundle.putStringArrayList(AppUtils.HL_PROPOSER_DOC_OCCUPATION, proposerOccupation);
            mBundle.putStringArrayList(AppUtils.HL_PROPOSER_DESIGNATION, proposerDesignation);
            mBundle.putStringArrayList(AppUtils.HL_PROPOSER_CITY, proposerCityId);
            mBundle.putStringArrayList(AppUtils.HL_PROPOSER_PIN, proposerPincode);
            mBundle.putStringArrayList(AppUtils.HL_PROPOSER_ADDRESS1, proposerAddress1);
            mBundle.putStringArrayList(AppUtils.HL_PROPOSER_ADDRESS2, proposerAddress2);
            mBundle.putStringArrayList(AppUtils.HL_PROPOSER_ADDRESS3, proposerAddress3);
            mBundle.putString(AppUtils.HL_CITY_ID, cityId);

            intent.putExtras(mBundle);
            startActivity(intent);
        }
    }

    @Override
    public void onResponse(Object response) {

        if (response instanceof HealthDocument) {
            HealthDocument cResponse = (HealthDocument) response;
            if (cResponse.getStatus().equalsIgnoreCase("1")
                    && cResponse.getDocument() != null) {

                documentList.clear();
                docsList.clear();
                documentList = cResponse.getDocument();
                if (documentList.size() > 0) {
                    initDocument();
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
        if (response instanceof HealthQualification) {
            HealthQualification cResponse = (HealthQualification) response;
            if (cResponse.getStatus().equalsIgnoreCase("1")
                    && cResponse.getEducation() != null) {

                qualificationList.clear();
                qLists.clear();
                qualificationList = cResponse.getEducation();
                if (qualificationList.size() > 0) {
                    initQualification();
                }
            }
        }
        if (response instanceof HealthOccupation) {
            HealthOccupation cResponse = (HealthOccupation) response;
            if (cResponse.getStatus().equalsIgnoreCase("1")
                    && cResponse.getOccupation() != null) {
                occupationsList.clear();
                occupations.clear();
                occupationsList = cResponse.getOccupation();
                if (occupationsList.size() > 0) {
                    initOccupation();
                }
            }
        }
        if (response instanceof HealthMarital) {
            HealthMarital cResponse = (HealthMarital) response;
            if (cResponse.getStatus().equalsIgnoreCase("1") && cResponse.getMaritalStatus() != null) {
                maritalList.clear();
                maritalLists.clear();
                maritalList = cResponse.getMaritalStatus();
                if (maritalList.size() > 0) {
                    initMarital();
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

    public void getMaritalStatus() {
        if (AppUtils.isOnline(mContext)) {
            try {
                HealthManager.getInstance().getMaritalStatus(mContext, quotationId, companyName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
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

    public void getQualification() {
        if (AppUtils.isOnline(mContext)) {
            try {
                HealthManager.getInstance().getQualification(mContext, quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getDocuments() {
        if (AppUtils.isOnline(mContext)) {
            try {
                HealthManager.getInstance().getHealthDocument(mContext, quotationId, companyName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getOccupation() {
        if (AppUtils.isOnline(mContext)) {
            try {
                HealthManager.getInstance().getOccupation(mContext, quotationId, companyName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
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

    private void initMarital() {
        for (int i = 0; i < maritalList.size(); i++) {
            String stateName = maritalList.get(i).getName().trim();
            String stateId = maritalList.get(i).getCode();
            maritalLists.add(new String3WithTag(stateName, stateId));
        }

        ArrayAdapter<String3WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, maritalLists);
        spnMaterial.setAdapter(adapter);

        spnMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String3WithTag stringWithTag = (String3WithTag) parent.getItemAtPosition(position);
                maritalId = (String) stringWithTag.tag;
                mName = stringWithTag.string;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initOccupation() {
        for (int i = 0; i < occupationsList.size(); i++) {
            String stateName = occupationsList.get(i).getName().trim();
            String stateId = occupationsList.get(i).getCode();
            occupations.add(new String4WithTag(stateName, stateId));
        }

        ArrayAdapter<String4WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, occupations);
        spnOccupation.setAdapter(adapter);

        spnOccupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String4WithTag stringWithTag = (String4WithTag) parent.getItemAtPosition(position);
                occupationId = (String) stringWithTag.tag;
                oName = stringWithTag.string;
                if (oName.equalsIgnoreCase("student")
                        || oName.equalsIgnoreCase("housewife"))
                    edtDes.setVisibility(View.GONE);
                else edtDes.setVisibility(View.VISIBLE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initDocument() {
        for (int i = 0; i < documentList.size(); i++) {
            String stateName = documentList.get(i).getName().trim();
            String stateId = documentList.get(i).getCode();
            docsList.add(new String2WithTag(stateName, stateId));
        }

        ArrayAdapter<String2WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, docsList);
        spnDocType.setAdapter(adapter);

        spnDocType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                documentId = (String) stringWithTag.tag;
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
        spnTpa.setAdapter(adapter);

        spnTpa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String5WithTag stringWithTag = (String5WithTag) parent.getItemAtPosition(position);
                title = (String) stringWithTag.tag;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

        for (int i = 0; i < cityList.size(); i++) {
            String stateName = cityList.get(i).getName().trim();
            if (stateName.equalsIgnoreCase(city)) {
                spnCity.setSelection(i);
                break;
            }
        }

        spnCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String7WithTag stringWithTag = (String7WithTag) parent.getItemAtPosition(position);
                city = stringWithTag.string;
                cityId = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initQualification() {
        for (int i = 0; i < qualificationList.size(); i++) {
            String stateName = qualificationList.get(i).getName().trim();
            String stateId = qualificationList.get(i).getCode();
            qLists.add(new StringWithTag(stateName, stateId));
        }

        ArrayAdapter<StringWithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, qLists);
        spnQualification.setAdapter(adapter);

        spnQualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag stringWithTag = (StringWithTag) parent.getItemAtPosition(position);
                qualification = stringWithTag.string;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean isValidation() {

        fName = edtFirstName.getText().toString().trim();
        lName = edtLastName.getText().toString().trim();
        dob = edtDob.getText().toString().trim();
        email = edtEmail.getText().toString().trim();
        phone = edtPhone.getText().toString().trim();
        documentNo = edtDocNo.getText().toString().trim();
        designation = edtDes.getText().toString().trim();
        address1 = edtAddress1.getText().toString().trim();
        address2 = edtAddress2.getText().toString().trim();
        address3 = edtAddress3.getText().toString().trim();
        pincode = edtPinCode.getText().toString().trim();

        if (spnQualification.getSelectedItem() != null)
            qualification = spnQualification.getSelectedItem().toString();
        if (spnDocType.getSelectedItem() != null)
            document = spnDocType.getSelectedItem().toString();

        if (companyName.equalsIgnoreCase("royal")) {
            if (spnTpa.getSelectedItemPosition() == 0) {
                Toast.makeText(mContext, "Select TPA", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                tpa = spnTpa.getSelectedItem().toString();
            }
            if (edtDes.getVisibility() == View.VISIBLE && (TextUtils.isEmpty(designation))) {
                edtDes.setError(getString(R.string.field_cannot));
                edtDes.requestFocus();
                return false;
            }
            if (oName.equalsIgnoreCase("politician")) {
                Toast.makeText(mContext, "Politician cannot create policy " +
                        "\nkindly contact nearest Branch", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if (TextUtils.isEmpty(fName)) {
            edtFirstName.setError(getString(R.string.field_cannot));
            edtFirstName.requestFocus();
            return false;
        } else if (!AppUtils.validateName(fName)) {
            edtFirstName.setError(getString(R.string.invalid_name));
            edtFirstName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(lName)) {
            edtLastName.setError(getString(R.string.field_cannot));
            edtLastName.requestFocus();
            return false;
        } else if (!AppUtils.validateName(lName)) {
            edtLastName.setError(getString(R.string.invalid_name));
            edtLastName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(dob)) {
            edtDob.setError(getString(R.string.field_cannot));
            edtDob.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            edtEmail.setError(getString(R.string.field_cannot));
            edtEmail.requestFocus();
            return false;
        } else if (!(AppUtils.isValidMail(email))) {
            edtEmail.setError(getString(R.string.invalid_email));
            edtEmail.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(phone)) {
            edtPhone.setError(getString(R.string.field_cannot));
            edtPhone.requestFocus();
            return false;
        } else if (!(AppUtils.isValidMobile(phone))) {
            edtPhone.setError(getString(R.string.invalid_phone));
            edtPhone.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(documentNo)) {
            edtDocNo.setError(getString(R.string.field_cannot));
            edtDocNo.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(pincode)) {
            edtPinCode.setError(getString(R.string.field_cannot));
            edtPinCode.requestFocus();
            return false;
        } else if (pincode.length() < 6) {
            edtPinCode.setError(getString(R.string.minimum_6));
            edtPinCode.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(address1)) {
            edtAddress1.setError(getString(R.string.field_cannot));
            edtAddress1.requestFocus();
            return false;
        } else if (address2.length() < 5) {
            edtAddress2.setError("Minimum 5 Characters");
            edtAddress2.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(address2)) {
            edtAddress2.setError(getString(R.string.field_cannot));
            edtAddress2.requestFocus();
            return false;
        } else if (address2.length() < 10) {
            edtAddress2.setError("Minimum 10 Characters");
            edtAddress2.requestFocus();
            return false;
        }

        if (document.equalsIgnoreCase("pan")) {
            if (!TextUtils.isEmpty(documentNo)) {
                if (documentNo.length() != 10) {
                    edtDocNo.setError("Invalid PAN card Number");
                    edtDocNo.requestFocus();
                    Toast.makeText(mContext, "5 Alphabet 4 Digit 1 Character",
                            Toast.LENGTH_SHORT).show();
                    return false;
                } else if (AppUtils.isValidPAN(documentNo)) {
                    edtDocNo.setError("Invalid PAN card Number");
                    edtDocNo.requestFocus();
                    Toast.makeText(mContext, "5 Alphabet 4 Digit 1 Character",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}