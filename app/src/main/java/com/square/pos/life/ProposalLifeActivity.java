package com.square.pos.life;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.ApiManager;
import com.square.pos.model_life.BusinessBelong;
import com.square.pos.model_life.BusinessList;
import com.square.pos.model_life.Gender;
import com.square.pos.model_life.GenderList;
import com.square.pos.model_life.LifePinList;
import com.square.pos.model_life.LifeProposal;
import com.square.pos.model_life.Marital;
import com.square.pos.model_life.MaritalList;
import com.square.pos.model_life.Occupation;
import com.square.pos.model_life.OccupationList;
import com.square.pos.model_life.Qualification;
import com.square.pos.model_life.QualificationList;
import com.square.pos.model_life.Salutation;
import com.square.pos.model_life.SalutationList;
import com.square.pos.model_life.Sector;
import com.square.pos.model_life.SectorList;
import com.square.pos.utils.AppUtils;
import com.square.pos.utils.String2WithTag;
import com.square.pos.utils.String3WithTag;
import com.square.pos.utils.String4WithTag;
import com.square.pos.utils.String5WithTag;
import com.square.pos.utils.String6WithTag;
import com.square.pos.utils.StringWithTag;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ProposalLifeActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {
    Context mContext;
    Spinner spnSalutation, spnGender, spnMaterial, spnOccupation, spnBusiness, spnSector,
            spnEducation;
    EditText edtFirstName, edtLastName, edtSpouse, edtDob, edtEmail, edtPhone, edtAnnualIncome,
            edtOrgName, edtOrgAddress, edtPinCode, edtAddress1, edtAddress2, edtAddress3,
            edtExist;

    ArrayList<Salutation> salutationList = new ArrayList<>();
    ArrayList<StringWithTag> salutations = new ArrayList<>();

    ArrayList<BusinessBelong> businessList = new ArrayList<>();
    ArrayList<String2WithTag> businesses = new ArrayList<>();

    ArrayList<Marital> maritalList = new ArrayList<>();
    ArrayList<String3WithTag> maritalData = new ArrayList<>();

    ArrayList<Sector> sectorList = new ArrayList<>();
    ArrayList<String4WithTag> sectors = new ArrayList<>();

    ArrayList<Occupation> occupationList = new ArrayList<>();
    ArrayList<String5WithTag> occupations = new ArrayList<>();

    ArrayList<Gender> genderList = new ArrayList<>();
    ArrayList<String6WithTag> genders = new ArrayList<>();

    ArrayList<Qualification> educationList = new ArrayList<>();
    ArrayList<String2WithTag> educations = new ArrayList<>();

    String occupationId, occupation, maritalId, marital, sector, sectorId, business, businessId,
            salutation, salId, gender, genderId, firstName, lastName, dob, formattedDob, mobile,
            email, pincode, mPincode, cityId, stateId, address1, address2, address3,
            companyName, spouseName, existingCover, eduId, annualIncome, education, employerName,
            employerAddress, quotationId;

    Bundle mBundle;
    LifeProposal lifeProposal = new LifeProposal();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposal_life);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        this.setTitle("Proposer Detail");
        spnSalutation = findViewById(R.id.spnSalutation);
        spnGender = findViewById(R.id.spnGender);
        spnMaterial = findViewById(R.id.spnMaterial);
        spnOccupation = findViewById(R.id.spnOccupation);
        spnEducation = findViewById(R.id.spnEducation);
        spnBusiness = findViewById(R.id.spnBusiness);
        spnSector = findViewById(R.id.spnSector);

        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtSpouse = findViewById(R.id.edtSpouse);
        edtDob = findViewById(R.id.edtDob);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtAnnualIncome = findViewById(R.id.edtAnnualIncome);
        edtOrgName = findViewById(R.id.edtOrgName);
        edtOrgAddress = findViewById(R.id.edtOrgAddress);
        edtPinCode = findViewById(R.id.edtPinCode);
        edtAddress1 = findViewById(R.id.edtAddress1);
        edtAddress2 = findViewById(R.id.edtAddress2);
        edtAddress3 = findViewById(R.id.edtAddress3);
        edtExist = findViewById(R.id.edtExist);

        mBundle = getIntent().getExtras();

        if (mBundle == null) {
            mBundle = new Bundle();
        } else {
            quotationId = mBundle.getString(AppUtils.QUOTATION_ID);
            fetchLifeProposal();
        }

        getSector();
        getGender();
        getBusiness();
        getMarital();
        getSalutation();
        getOccupation();
        getEducation();

        edtPinCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        edtPinCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 6) {
                    mPincode = edtPinCode.getText().toString();
                    getPincode();
                }
            }
        });
    }

    public void getPincode() {
        if (AppUtils.isOnline(mContext)) {
            try {
                ApiManager.getInstance().getLifePincode(mContext, mPincode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void initValues() {

        firstName = AppUtils.trimString(edtFirstName);
        lastName = AppUtils.trimString(edtLastName);
        email = AppUtils.trimString(edtEmail);
        mobile = AppUtils.trimString(edtPhone);
        spouseName = AppUtils.trimString(edtSpouse);

        dob = AppUtils.trimString(edtDob);

        address1 = AppUtils.trimString(edtAddress1);
        address2 = AppUtils.trimString(edtAddress2);
        address3 = AppUtils.trimString(edtAddress3);
        address3 = AppUtils.trimString(edtAddress3);

        annualIncome = AppUtils.trimString(edtAnnualIncome);
        existingCover = AppUtils.trimString(edtExist);
        employerName = AppUtils.trimString(edtOrgName);
        employerAddress = AppUtils.trimString(edtOrgAddress);

    }

    private boolean checkValidation() {

        initValues();
        if (TextUtils.isEmpty(firstName)) {
            edtFirstName.setError("Field can not be empty");
            edtFirstName.requestFocus();
            return false;
        }
        if (!AppUtils.validateName(firstName)) {
            edtFirstName.setError("Invalid Name");
            edtFirstName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(lastName)) {
            edtLastName.setError("Field can not be empty");
            edtLastName.requestFocus();
            return false;
        }
        if (!AppUtils.validateName(lastName)) {
            edtLastName.setError("Invalid Name");
            edtLastName.requestFocus();
            return false;
        }
        if (!TextUtils.isEmpty(dob)) {
            if (AppUtils.validDate(dob)) {
                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                Date date = null;
                try {
                    date = formatter.parse(dob);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                if (date != null) {
                    formattedDob = newFormat.format(date);
                }
            } else {
                edtDob.setError("Invalid Date");
                edtDob.requestFocus();
                return false;
            }
        } else {
            edtDob.setError("Field can not be empty");
            edtDob.requestFocus();
            return false;
        }


        if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Field can not be empty");
            edtEmail.requestFocus();
            return false;
        } else if (!(AppUtils.isValidMail(email))) {
            edtEmail.setError("Invalid Email");
            edtEmail.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(mobile)) {
            edtPhone.setError("Field can not be empty");
            edtPhone.requestFocus();
            return false;
        } else if (!(AppUtils.isValidMobile(mobile))) {
            edtPhone.setError("Invalid Phone Number");
            edtPhone.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(pincode) || mPincode.length() != 6) {
            edtPinCode.setError("Invalid PIN code");
            edtPinCode.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(cityId)) {
            edtPinCode.setError("City not connected with this pincode");
            return false;
        } else if (TextUtils.isEmpty(stateId)) {
            edtPinCode.setError("State not connected with this pincode");
            return false;
        }

        if (TextUtils.isEmpty(address1)) {
            edtAddress1.setError("Field can not be empty");
            edtAddress1.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(address2)) {
            edtAddress2.setError("Field can not be empty");
            edtAddress2.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(address3)) {
            edtAddress3.setError("Field can not be empty");
            edtAddress3.requestFocus();
            return false;
        }


        String sal = "", gen = "";

        if (spnSalutation.getSelectedItem() != null)
            sal = spnSalutation.getSelectedItem().toString();
        if (spnGender.getSelectedItem() != null)
            gen = spnGender.getSelectedItem().toString();

        if (sal.equalsIgnoreCase("mr") && gen.equalsIgnoreCase("female")) {
            Toast.makeText(mContext, "Select Valid Gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        if ((sal.equalsIgnoreCase("miss") || sal.equalsIgnoreCase("mrs"))
                && gen.equalsIgnoreCase("male")) {
            Toast.makeText(mContext, "Select Valid Gender", Toast.LENGTH_SHORT).show();
            return false;
        }


        firstName = firstName.replaceAll("\\s{2,}", " ").trim();
        lastName = lastName.replaceAll("\\s{2,}", " ").trim();

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

    public void onDateClick(View view) {
        EditText edtDob = (EditText) view;
        AppUtils.checkSoftKeyboard(this);
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.add(Calendar.YEAR, -18);
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                R.style.datepicker, (view1, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);

            edtDob.setText(AppUtils.SIMPLE_DATE_FORMAT.format(newDate.getTime()));
            edtDob.setError(null);
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(newCalendar.getTimeInMillis());
        datePickerDialog.show();
    }

    public void onProposerSubmit(View view) {
        if (checkValidation()) {
            Intent intent = new Intent(mContext, NomineeLifeActivity.class);
            mBundle.putString(AppUtils.LF_SAL_ID, salId);
            mBundle.putString(AppUtils.A_FIRST_NAME, firstName);
            mBundle.putString(AppUtils.A_LAST_NAME, lastName);
            mBundle.putString(AppUtils.OWNER_DOB, formattedDob);
            mBundle.putString(AppUtils.PINCODE, pincode);
            mBundle.putString(AppUtils.LF_CITY, cityId);
            mBundle.putString(AppUtils.LF_STATE, stateId);
            mBundle.putString(AppUtils.LF_ADDRESS1, address1);
            mBundle.putString(AppUtils.LF_ADDRESS2, address2);
            mBundle.putString(AppUtils.LF_ADDRESS3, address3);
            mBundle.putString(AppUtils.ANNUAL_INCOME, annualIncome);
            mBundle.putString(AppUtils.EMAIL, email);
            mBundle.putString(AppUtils.MOBILE, mobile);
            mBundle.putString(AppUtils.LF_OCC_ID, occupationId);
            mBundle.putString(AppUtils.LF_EDU_ID, eduId);
            mBundle.putString(AppUtils.LF_MARITAL, maritalId);
            mBundle.putString(AppUtils.LF_GENDER, genderId);
            mBundle.putString(AppUtils.LF_BUSINESS, businessId);
            mBundle.putString(AppUtils.LF_EXISTING, existingCover);
            mBundle.putString(AppUtils.LF_EMPLOYER_NAME, employerName);
            mBundle.putString(AppUtils.LF_EMPLOYER_ADDRESS, employerAddress);
            mBundle.putString(AppUtils.LF_SECTOR, sectorId);
            mBundle.putString(AppUtils.LF_SPOUSE, spouseName);
            intent.putExtras(mBundle);
            intent.putExtra("lifeProposal", lifeProposal);
            startActivity(intent);
        }
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof OccupationList) {
            OccupationList cResponse = (OccupationList) response;
            if (cResponse.getStatus().equals("1") && cResponse.getOccupation() != null) {
                occupationList.clear();
                occupations.clear();
                occupationList = (ArrayList<Occupation>) cResponse.getOccupation();
                if (occupationList.size() > 0) {
                    initOccupation();
                }
            }
        }
        if (response instanceof BusinessList) {
            BusinessList cResponse = (BusinessList) response;
            if (cResponse.getStatus().equals("1") && cResponse.getBusinessBelongs() != null) {
                businesses.clear();
                businessList.clear();
                businessList = (ArrayList<BusinessBelong>) cResponse.getBusinessBelongs();
                if (businessList.size() > 0) {
                    initBusiness();
                }
            }
        }
        if (response instanceof SectorList) {
            SectorList cResponse = (SectorList) response;
            if (cResponse.getStatus().equals("1") && cResponse.getSector() != null) {
                sectorList.clear();
                sectors.clear();
                sectorList = (ArrayList<Sector>) cResponse.getSector();
                if (sectorList.size() > 0) {
                    initSector();
                }
            }
        }
        if (response instanceof MaritalList) {
            MaritalList cResponse = (MaritalList) response;
            if (cResponse.getStatus().equals("1") && cResponse.getMarital() != null) {
                maritalList.clear();
                maritalData.clear();
                maritalList = (ArrayList<Marital>) cResponse.getMarital();
                if (maritalList.size() > 0) {
                    initMarital();
                }
            }
        }
        if (response instanceof GenderList) {
            GenderList cResponse = (GenderList) response;
            if (cResponse.getStatus().equals("1") && cResponse.getGender() != null) {
                genderList.clear();
                genders.clear();
                genderList = (ArrayList<Gender>) cResponse.getGender();
                if (genderList.size() > 0) {
                    initGender();
                }
            }
        }
        if (response instanceof SalutationList) {
            SalutationList cResponse = (SalutationList) response;
            if (cResponse.getStatus().equals("1") && cResponse.getSalutation() != null) {
                salutationList.clear();
                salutations.clear();
                salutationList = (ArrayList<Salutation>) cResponse.getSalutation();
                if (salutationList.size() > 0) {
                    initSalutation();
                }
            }
        }
        if (response instanceof QualificationList) {
            QualificationList cResponse = (QualificationList) response;
            if (cResponse.getStatus().equals("1") && cResponse.getQualification() != null) {
                educationList.clear();
                educations.clear();
                educationList = (ArrayList<Qualification>) cResponse.getQualification();
                if (educationList.size() > 0) {
                    initEducation();
                }
            }
        }
        if (response instanceof LifePinList) {
            LifePinList cResponse = (LifePinList) response;
            if (cResponse.getStatus().equals("1") && cResponse.getPincode() != null) {
                stateId = cResponse.getPincode().get(0).getState();
                cityId = cResponse.getPincode().get(0).getCity();
                pincode = cResponse.getPincode().get(0).getPincode();
            }
        }
        if (response instanceof LifeProposal) {
            LifeProposal cResponse = (LifeProposal) response;
            if (cResponse.getStatus().equals("1")) {
                lifeProposal = cResponse;
                edtFirstName.setText(lifeProposal.getFirstName());
                edtLastName.setText(lifeProposal.getLastName());
                edtPinCode.setText(lifeProposal.getPincode());
                edtDob.setText(lifeProposal.getDob());
                edtPhone.setText(lifeProposal.getProposerMobile());
                edtAddress1.setText(lifeProposal.getAddress1());
                edtAddress2.setText(lifeProposal.getAddress2());
                edtAddress3.setText(lifeProposal.getAddress3());
                edtAnnualIncome.setText(lifeProposal.getAnnualIncome());
                edtEmail.setText(lifeProposal.getProposerEmail());
                edtExist.setText(lifeProposal.getExistingCover());
                edtOrgAddress.setText(lifeProposal.getEmpAddress());
                edtOrgName.setText(lifeProposal.getEmpName());
                edtSpouse.setText(lifeProposal.getSpouseName());
            }
        }
    }

    private void initEducation() {
        for (int i = 0; i < educationList.size(); i++) {
            String stateName = educationList.get(i).getName().trim();
            String stateId = educationList.get(i).getCode();
            educations.add(new String2WithTag(stateName, stateId));
        }

        ArrayAdapter<String2WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, educations);
        spnEducation.setAdapter(adapter);

        spnEducation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                eduId = (String) stringWithTag.tag;
                education = stringWithTag.string;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void getOccupation() {
        if (AppUtils.isOnline(mContext)) {
            try {
                ApiManager.getInstance().getLifeOccupation(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getEducation() {
        if (AppUtils.isOnline(mContext)) {
            try {
                ApiManager.getInstance().getLifeQualification(mContext, "1");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void initOccupation() {
        for (int i = 0; i < occupationList.size(); i++) {
            String stateName = occupationList.get(i).getName().trim();
            String stateId = occupationList.get(i).getCode();
            occupations.add(new String5WithTag(stateName, stateId));
        }

        ArrayAdapter<String5WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, occupations);
        spnOccupation.setAdapter(adapter);

        spnOccupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String5WithTag stringWithTag = (String5WithTag) parent.getItemAtPosition(position);
                occupationId = (String) stringWithTag.tag;
                occupation = stringWithTag.string;

                if (occupation.equalsIgnoreCase("salaried"))
                    findViewById(R.id.rlSal).setVisibility(View.VISIBLE);
                else findViewById(R.id.rlSal).setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getMarital() {
        if (AppUtils.isOnline(mContext)) {
            try {
                ApiManager.getInstance().getLifeMarital(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void initMarital() {
        for (int i = 0; i < maritalList.size(); i++) {
            String stateName = maritalList.get(i).getName().trim();
            String stateId = maritalList.get(i).getCode();
            maritalData.add(new String3WithTag(stateName, stateId));
        }

        ArrayAdapter<String3WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, maritalData);
        spnMaterial.setAdapter(adapter);

        spnMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String3WithTag stringWithTag = (String3WithTag) parent.getItemAtPosition(position);
                maritalId = (String) stringWithTag.tag;
                marital = stringWithTag.string;

                if (!TextUtils.isEmpty(salutation)){
                    if (salutation.equalsIgnoreCase("mrs.")
                            &&marital.equalsIgnoreCase("married")){
                        findViewById(R.id.txtSpouse).setVisibility(View.VISIBLE);
                    }else findViewById(R.id.txtSpouse).setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getSalutation() {
        if (AppUtils.isOnline(mContext)) {
            try {
                ApiManager.getInstance().getLifeSalutation(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void initSalutation() {
        for (int i = 0; i < salutationList.size(); i++) {
            String stateName = salutationList.get(i).getName().trim();
            String stateId = salutationList.get(i).getCode();
            salutations.add(new StringWithTag(stateName, stateId));
        }

        ArrayAdapter<StringWithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, salutations);
        spnSalutation.setAdapter(adapter);

        spnSalutation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag stringWithTag = (StringWithTag) parent.getItemAtPosition(position);
                salId = (String) stringWithTag.tag;
                salutation = stringWithTag.string;

                if (!TextUtils.isEmpty(marital)) {
                    if (salutation.equalsIgnoreCase("mrs.")
                            && marital.equalsIgnoreCase("married")) {
                        findViewById(R.id.txtSpouse).setVisibility(View.VISIBLE);
                    } else findViewById(R.id.txtSpouse).setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getSector() {
        if (AppUtils.isOnline(mContext)) {
            try {
                ApiManager.getInstance().getLifeSector(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void initSector() {
        for (int i = 0; i < sectorList.size(); i++) {
            String stateName = sectorList.get(i).getName().trim();
            String stateId = sectorList.get(i).getCode();
            sectors.add(new String4WithTag(stateName, stateId));
        }

        ArrayAdapter<String4WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, sectors);
        spnSector.setAdapter(adapter);

        spnSector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String4WithTag stringWithTag = (String4WithTag) parent.getItemAtPosition(position);
                sectorId = (String) stringWithTag.tag;
                sector = stringWithTag.string;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getBusiness() {
        if (AppUtils.isOnline(mContext)) {
            try {
                ApiManager.getInstance().getLifeBusiness(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void initBusiness() {
        for (int i = 0; i < businessList.size(); i++) {
            String stateName = businessList.get(i).getName().trim();
            String stateId = businessList.get(i).getCode();
            businesses.add(new String2WithTag(stateName, stateId));
        }

        ArrayAdapter<String2WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, businesses);
        spnBusiness.setAdapter(adapter);

        spnBusiness.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                businessId = (String) stringWithTag.tag;
                business = stringWithTag.string;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getGender() {
        if (AppUtils.isOnline(mContext)) {
            try {
                ApiManager.getInstance().getLifeGender(mContext);
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

        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String6WithTag stringWithTag = (String6WithTag) parent.getItemAtPosition(position);
                genderId = (String) stringWithTag.tag;
                gender = stringWithTag.string;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void fetchLifeProposal() {
        if (AppUtils.isOnline(mContext)) {
            try {
                ApiManager.getInstance().fetchLifeProposal(mContext, quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }
}