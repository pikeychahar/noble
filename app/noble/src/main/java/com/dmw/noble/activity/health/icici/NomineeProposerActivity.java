package com.dmw.noble.activity.health.icici;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.Health.HealthManager;
import com.dmw.noble.model.TempResponse;
import com.dmw.noble.model.master.PincodeMaster;
import com.dmw.noble.model_health.v2.Gender;
import com.dmw.noble.model_health.v2.HealthGender;
import com.dmw.noble.model_health.v2.HealthMarital;
import com.dmw.noble.model_health.v2.HealthRelation;
import com.dmw.noble.model_health.v2.HealthSalutation;
import com.dmw.noble.model_health.v2.MaritalStatus;
import com.dmw.noble.model_health.v2.Relation;
import com.dmw.noble.model_health.v2.Salutation;
import com.dmw.noble.utils.AppUtils;
import com.dmw.noble.utils.String3WithTag;
import com.dmw.noble.utils.String5WithTag;
import com.dmw.noble.utils.String6WithTag;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class NomineeProposerActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {

    Context mContext;
    Bundle mBundle;
    ProgressDialog progressdialog;

    EditText edtFirstName, edtLastName, edtDob, edtPincode, edtMobile, edtEmail, edtAddress1,
            edtAddress2, edtPan, edtProposerFName, edtProposerLName, edtProposerDob;

    Spinner spnGender, spnRelation, spnProposerMarital, spnProposerGender, spnTitle;

    String proposerFName, proposerLName, proposerDob, genderId, proposerMarital, relationId,
            quotationId, companyName, nomineeFName, nomineeLName, nmDob, pin, nmPincode, nmMobile,
            nmEmail, nmAddress1, nmAddress2, nmRelationId, panNo, nmGenderId, titleId;

    String titleName, firstName, lastName, gender, dob, height, weight, exists, occupationId,
            iffcoQ1, iffcoQ2, iffcoQ3;

    private List<Gender> genderList = new ArrayList<>();
    List<String6WithTag> genders = new ArrayList<>();

    List<String3WithTag> relations = new ArrayList<>();
    private List<Relation> relationList = new ArrayList<>();

    private List<Salutation> salutationList = new ArrayList<>();
    List<String5WithTag> salutations = new ArrayList<>();

    private List<MaritalStatus> maritalList = new ArrayList<>();
    List<String3WithTag> maritalLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nominee_proposer);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        mBundle = getIntent().getExtras();
        progressdialog = new ProgressDialog(mContext);
        edtFirstName = findViewById(R.id.edtFNomineeName);
        edtLastName = findViewById(R.id.edtLNomineeName);
        edtDob = findViewById(R.id.edtNomineeDob);
        edtMobile = findViewById(R.id.edtMobile);
        edtEmail = findViewById(R.id.edtEmail);
        edtPan = findViewById(R.id.edtPan);
        edtAddress1 = findViewById(R.id.edtNomineeAddress1);
        edtAddress2 = findViewById(R.id.edtNomineeAddress2);
        edtPincode = findViewById(R.id.edtPinCode);
        edtProposerFName = findViewById(R.id.edtProposerFName);
        edtProposerLName = findViewById(R.id.edtProposerLName);
        edtProposerDob = findViewById(R.id.edtProposerDob);

        spnGender = findViewById(R.id.spnGender);
        spnRelation = findViewById(R.id.spnNominee);
        spnProposerMarital = findViewById(R.id.spnProposerMarital);
        spnProposerGender = findViewById(R.id.spnProposerGender);
        spnTitle = findViewById(R.id.spnTitle);

        if (mBundle != null) {
            quotationId = mBundle.getString(AppUtils.QUOTATION_ID_1);
            companyName = mBundle.getString(AppUtils.HL_COMPANY);

            firstName = mBundle.getString(AppUtils.HL_INSURED_FNAME);
            lastName = mBundle.getString(AppUtils.HL_INSURED_LNAME);
            gender = mBundle.getString(AppUtils.HL_INSURED_GENDER);
            dob = mBundle.getString(AppUtils.HL_INSURED_DOB);
            titleName = mBundle.getString(AppUtils.HL_INSURED_TITLE);
            height = mBundle.getString(AppUtils.HL_INSURED_HEIGHT);
            weight = mBundle.getString(AppUtils.HL_INSURED_WEIGHT);
            relationId = mBundle.getString(AppUtils.HL_INSURED_RELATION);
            occupationId = mBundle.getString(AppUtils.HL_INSURED_OCCUPATION);
            exists = mBundle.getString(AppUtils.ICICI_EXIST);

            iffcoQ1 = mBundle.getString(AppUtils.IFFCO_Q1);
            iffcoQ2 = mBundle.getString(AppUtils.IFFCO_Q2);
            iffcoQ3 = mBundle.getString(AppUtils.IFFCO_Q3);

            String email = mBundle.getString(AppUtils.HL_EMAIL);
            String phone = mBundle.getString(AppUtils.HL_PHONE);

            edtEmail.setText(email);
            edtMobile.setText(phone);

            if (companyName.equalsIgnoreCase("iffco")) {
                findViewById(R.id.rlIffcoProposer).setVisibility(View.VISIBLE);
                salutationList = HealthManager.getInstance().getTitleList();

                if (salutationList != null && salutationList.size() > 0)
                    initSalutation();
                else
                    getSalutation();
                getMaritalStatus();
            }
        }

        genderList = HealthManager.getInstance().getGenderList();

        if (genderList != null && genderList.size() > 0)
            initGender();
        else
            getGender();
        getRelation();

        edtPincode.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(6)});
        edtPincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 6) {
                    pin = edtPincode.getText().toString();
                    getPincode();
                }
            }
        });

    }

    public void getPincode() {
        if (AppUtils.isOnline(mContext)) {
            try {
                HealthManager.getInstance().getPinCodeHealth(mContext, quotationId, companyName,
                        pin);
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

    private boolean isValid() {

        nomineeFName = edtFirstName.getText().toString();
        nomineeLName = edtLastName.getText().toString();
        nmDob = edtDob.getText().toString();
        nmMobile = edtMobile.getText().toString();
        nmEmail = edtEmail.getText().toString();
        nmAddress1 = edtAddress1.getText().toString();
        nmAddress2 = edtAddress2.getText().toString();
        panNo = edtPan.getText().toString();

        if (TextUtils.isEmpty(nomineeFName) || !AppUtils.validateName(nomineeFName)) {
            edtFirstName.setError("Invalid Name");
            edtFirstName.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(nomineeLName) || !AppUtils.validateName(nomineeLName)) {
            edtLastName.setError("Invalid Name");
            edtLastName.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(nmDob)) {
            edtDob.setError(getString(R.string.field_cannot));
            return false;
        } else if (TextUtils.isEmpty(nmPincode)) {
            edtPincode.setError(getString(R.string.field_cannot));
            edtPincode.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(nmAddress1)) {
            edtAddress1.setError(getString(R.string.field_cannot));
            edtAddress1.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(nmAddress2)) {
            edtAddress2.setError(getString(R.string.field_cannot));
            edtAddress2.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(nmRelationId)) {
            Toast.makeText(mContext, "Select Nominee relation", Toast.LENGTH_SHORT).show();
            return false;
        }
        float grossPremium = Float.parseFloat(mBundle.getString(AppUtils.HEALTH_TOTAL_PREMIUM, "0"));
        if (grossPremium > 50000) {
            if (TextUtils.isEmpty(panNo)) {
                edtAddress1.setError(getString(R.string.field_cannot));
                edtAddress1.requestFocus();
                return false;
            }
        }
        if (companyName.equalsIgnoreCase("iffco")) {

            proposerFName = edtProposerFName.getText().toString();
            proposerLName = edtProposerLName.getText().toString();
            proposerDob = edtProposerDob.getText().toString();

            if (TextUtils.isEmpty(proposerFName)) {
                edtProposerFName.setError(getString(R.string.field_cannot));
                edtProposerFName.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(proposerLName)) {
                edtProposerLName.setError(getString(R.string.field_cannot));
                edtProposerLName.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(proposerDob)) {
                edtProposerDob.setError(getString(R.string.field_cannot));
                edtProposerDob.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(genderId)) {
                Toast.makeText(mContext, "Select Gender", Toast.LENGTH_SHORT).show();
                return false;
            } else if (TextUtils.isEmpty(proposerMarital)) {
                Toast.makeText(mContext, "Select Marital", Toast.LENGTH_SHORT).show();
                return false;
            }

        }

        return true;
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

    private void initGender() {
        for (int i = 0; i < genderList.size(); i++) {
            String stateName = genderList.get(i).getName().trim();
            String stateId = genderList.get(i).getCode();
            genders.add(new String6WithTag(stateName, stateId));
        }

        ArrayAdapter<String6WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, genders);
        spnGender.setAdapter(adapter);
        if (companyName.equalsIgnoreCase("iffco"))
            spnProposerGender.setAdapter(adapter);

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
                nmGenderId = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnProposerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String6WithTag stringWithTag = (String6WithTag) parent.getItemAtPosition(position);
                genderId = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initRelation() {
        for (int i = 0; i < relationList.size(); i++) {
            String stateName = relationList.get(i).getName().trim();
            String stateId = String.valueOf(relationList.get(i).getCode());
            relations.add(new String3WithTag(stateName, stateId));
        }

        ArrayAdapter<String3WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, relations);
        spnRelation.setAdapter(adapter);


        spnRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String3WithTag stringWithTag = (String3WithTag) parent.getItemAtPosition(position);
                nmRelationId = (String) stringWithTag.tag;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getRelation() {
        if (AppUtils.isOnline(mContext)) {
//            progressdialog.show();
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

    public void submitIcici() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {

                HealthManager.getInstance().updateHealthProposalIcici(mContext, companyName,
                        quotationId, titleName, firstName, lastName, gender, dob, height, weight,
                        relationId, nmRelationId, nomineeFName, nomineeLName, exists, nmDob,
                        nmMobile, nmEmail, panNo, nmPincode, nmAddress1, nmAddress2);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }

    public void submitIffco() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {

                HealthManager.getInstance().updateHealthProposalIffco(mContext, companyName,
                        quotationId, titleName, firstName, lastName, gender, dob, height, weight,
                        occupationId, relationId, nmRelationId, nomineeFName, nomineeLName, iffcoQ1,
                        iffcoQ2, iffcoQ3, nmDob, titleId, proposerFName, proposerLName, proposerDob,
                        genderId, proposerMarital, nmMobile, nmMobile, nmEmail, nmEmail, panNo,
                        nmPincode, nmAddress1, nmAddress2);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }

    public void onDateClick(View view) {
        EditText edtCommon = (EditText) view;
        Calendar newCalendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                R.style.datepicker, (view1, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            edtCommon.setText(AppUtils.SIMPLE_DATE_FORMAT.format(newDate.getTime()));
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public void onReviewClick(View view) {
        if (isValid()) {
            if (companyName.equalsIgnoreCase("icici"))
                submitIcici();
            else submitIffco();
        }
    }

    private void initSalutation() {
        for (int i = 0; i < salutationList.size(); i++) {
            String stateName = salutationList.get(i).getName().trim();
            String stateId = salutationList.get(i).getCode();
            salutations.add(new String5WithTag(stateName, stateId));
        }

        ArrayAdapter<String5WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, salutations);
        spnTitle.setAdapter(adapter);

        spnTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String5WithTag stringWithTag = (String5WithTag) parent.getItemAtPosition(position);
                titleId = (String) stringWithTag.tag;
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
        spnProposerMarital.setAdapter(adapter);

        spnProposerMarital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String3WithTag stringWithTag = (String3WithTag) parent.getItemAtPosition(position);
                proposerMarital = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onResponse(Object response) {

        if (response instanceof PincodeMaster) {
            PincodeMaster cResponse = (PincodeMaster) response;
            if (cResponse.getStatus().equalsIgnoreCase("1") && cResponse.getPincode() != null) {
                nmPincode = cResponse.getPincode().get(0).getCode();
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


        if (response instanceof HealthRelation) {
            HealthRelation cResponse = (HealthRelation) response;
            if (cResponse.getStatus().equalsIgnoreCase("1")) {
                relationList.clear();
                relations.clear();
                relationList = cResponse.getRelation();
                if (relationList.size() > 0) {
                    initRelation();
                }
            }
        }
        if (response instanceof TempResponse) {
            TempResponse cResponse = (TempResponse) response;
            if (cResponse.getStatus().equalsIgnoreCase("1")) {
                String nmRelation = spnRelation.getSelectedItem().toString();
                mBundle.putString(AppUtils.NOMINEE_NAME, nomineeFName + " " + nomineeLName);
                mBundle.putString(AppUtils.NOMINEE_RELATION, nmRelation);
                mBundle.putString(AppUtils.HL_PROPOSER_ADDRESS1, nmAddress1);
                mBundle.putString(AppUtils.HL_PROPOSER_ADDRESS2, nmAddress2);
                Intent intent = new Intent(mContext, HealthReviewNewActivity.class);
                intent.putExtras(mBundle);
                startActivity(intent);
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
        progressdialog.dismiss();
    }
}