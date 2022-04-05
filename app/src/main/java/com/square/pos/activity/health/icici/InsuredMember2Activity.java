package com.square.pos.activity.health.icici;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.Health.HealthManager;
import com.square.pos.model_health.v2.Gender;
import com.square.pos.model_health.v2.HealthGender;
import com.square.pos.model_health.v2.HealthOccupation;
import com.square.pos.model_health.v2.HealthRelation;
import com.square.pos.model_health.v2.HealthSalutation;
import com.square.pos.model_health.v2.Occupation;
import com.square.pos.model_health.v2.Relation;
import com.square.pos.model_health.v2.Salutation;
import com.square.pos.utils.AppUtils;
import com.square.pos.utils.String2WithTag;
import com.square.pos.utils.String3WithTag;
import com.square.pos.utils.String5WithTag;
import com.square.pos.utils.String6WithTag;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class InsuredMember2Activity extends AbstractActivity
        implements onRequestCompleteCallBackListener {

    String quotationId, planFor, companyName, healthPlanType;
    boolean isSpouse, isFather, isMother;
    int totalSon, totalDaughter;

    TextView lblInsuredMember;
    EditText edtFName, edtLName, edtDob, edtHeight, edtWeight, /* Iffco*/
            edtIffco1, edtIffco2, edtIffco3;
    Spinner spnTitle, spnGender, spnOccupation, spnRelation;
    String titleId, genderId, occupationId, relationId, genderValue;

    private List<String2WithTag> occupations = new ArrayList<>();
    private List<Occupation> occupationsList = new ArrayList<>();

    private List<String3WithTag> relations = new ArrayList<>();
    private List<Relation> relationList = new ArrayList<>();

    private List<Salutation> salutationList = new ArrayList<>();
    private List<String5WithTag> salutations = new ArrayList<>();

    private List<Gender> genderList = new ArrayList<>();
    private List<String6WithTag> genders = new ArrayList<>();

    RadioButton rbIciciYes, irbIciciNo;
    RelativeLayout iffcoMedical;

    private Bundle mBundle;
    private Context mContext;
    public ProgressDialog progressdialog;

    String firstName, lastName, dob, height, weight, iffcoQ1, iffcoQ2, iffcoQ3;

    String tempFName, tempLName, tempGenderId, tempRelationId, tempDob, tempHeight, tempWeight,
            tempTitleId, tempOccupationId, tempExist, tempIf1, tempIf2, tempIf3;

    ArrayList<String> tempInsuredName = new ArrayList<>();
    ArrayList<String> tempInsuredGender = new ArrayList<>();
    ArrayList<String> tempInsuredDob = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insured_member2);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        mBundle = getIntent().getExtras();

        lblInsuredMember = findViewById(R.id.lblInsuredMember);
        edtFName = findViewById(R.id.editFirstName);
        edtLName = findViewById(R.id.editLastName);
        edtDob = findViewById(R.id.edtDob);
        edtHeight = findViewById(R.id.edtHeight);
        edtWeight = findViewById(R.id.edtWeight);

        //IFFCO
        edtIffco1 = findViewById(R.id.edtIffco1);
        edtIffco2 = findViewById(R.id.edtIffco2);
        edtIffco3 = findViewById(R.id.edtIffco3);

        spnTitle = findViewById(R.id.spnTitle);
        spnGender = findViewById(R.id.spnGender);
        spnOccupation = findViewById(R.id.spnOccHealth);//Iffco
        spnRelation = findViewById(R.id.spnRelation);

        rbIciciYes = findViewById(R.id.rbYes);
        irbIciciNo = findViewById(R.id.rbNo);
        iffcoMedical = findViewById(R.id.iffcoMedical);

        progressdialog = new ProgressDialog(mContext);
        if (mBundle != null) {
            quotationId = mBundle.getString(AppUtils.QUOTATION_ID_1);
            planFor = mBundle.getString(AppUtils.INDIVIDUAL_NAME);
            companyName = mBundle.getString(AppUtils.HL_COMPANY);
            healthPlanType = mBundle.getString(AppUtils.HEALTH_PLAN);

            isSpouse = mBundle.getBoolean(AppUtils.IS_SPOUSE);
            isMother = mBundle.getBoolean(AppUtils.IS_MOTHER);
            isFather = mBundle.getBoolean(AppUtils.IS_FATHER);
            totalSon = mBundle.getInt(AppUtils.TOTAL_SON);
            totalDaughter = mBundle.getInt(AppUtils.TOTAL_DAUGHTER);

            tempFName = mBundle.getString(AppUtils.HL_INSURED_FNAME);
            tempLName = mBundle.getString(AppUtils.HL_INSURED_LNAME);
            tempGenderId = mBundle.getString(AppUtils.HL_INSURED_GENDER);
            tempDob = mBundle.getString(AppUtils.HL_INSURED_DOB);
            tempTitleId = mBundle.getString(AppUtils.HL_INSURED_TITLE);
            tempHeight = mBundle.getString(AppUtils.HL_INSURED_HEIGHT);
            tempWeight = mBundle.getString(AppUtils.HL_INSURED_WEIGHT);
            tempRelationId = mBundle.getString(AppUtils.HL_INSURED_RELATION);
            tempOccupationId = mBundle.getString(AppUtils.HL_INSURED_OCCUPATION);

            tempInsuredName = mBundle.getStringArrayList(AppUtils.HL_INSURED_NAME);
            tempInsuredGender = mBundle.getStringArrayList(AppUtils.HL_INSURED_GENDER_VALUE);
            tempInsuredDob = mBundle.getStringArrayList(AppUtils.HL_INSURED_DOB_VALUE);
            tempExist = mBundle.getString(AppUtils.ICICI_EXIST);

            tempIf1 = mBundle.getString(AppUtils.IFFCO_Q1);
            tempIf2 = mBundle.getString(AppUtils.IFFCO_Q2);
            tempIf3 = mBundle.getString(AppUtils.IFFCO_Q3);

            if (!TextUtils.isEmpty(companyName))
                if (companyName.equalsIgnoreCase("iffco")) {
                    spnOccupation.setVisibility(View.VISIBLE);
                    iffcoMedical.setVisibility(View.VISIBLE);

                    occupationsList = HealthManager.getInstance().getOccList();
                    if (occupationsList != null && occupationsList.size() > 0)
                        initOccupations();
                    else
                        getOccupations();
                }
        }

        if (isSpouse) {
            lblInsuredMember.setText("Spouse");
            edtDob.setText(mBundle.getString(AppUtils.CAL_SPOUSE, ""));
        } else if (isMother) {
            lblInsuredMember.setText("Mother");
            edtDob.setText(mBundle.getString(AppUtils.CAL_MOTHER, ""));
        } else if (isFather) {
            lblInsuredMember.setText("Father");
            edtDob.setText(mBundle.getString(AppUtils.CAL_MOTHER, ""));
        } else if (totalSon > 0) {
            lblInsuredMember.setText("Child1");
            edtDob.setText(mBundle.getString(AppUtils.CAL_SON1, ""));
        } else if (totalDaughter > 0) {
            lblInsuredMember.setText("Child1");
            edtDob.setText(mBundle.getString(AppUtils.CAL_DAUGHTER1, ""));
        } else lblInsuredMember.setText("Insured Member");

        edtDob.setOnClickListener(v -> onDateClick());

        salutationList = HealthManager.getInstance().getTitleList();
        relationList = HealthManager.getInstance().getRelationList();
        genderList = HealthManager.getInstance().getGenderList();
        if (salutationList != null && salutationList.size() > 0)
            initSalutation();
        else
            getSalutation();

        if (relationList != null && relationList.size() > 0)
            initRelation();
        else
            getRelation();

        if (genderList != null && genderList.size() > 0)
            initGender();
        else
            getGender();
    }

    private boolean isValid() {

        firstName = edtFName.getText().toString();
        lastName = edtLName.getText().toString();
        dob = edtDob.getText().toString();
        height = edtHeight.getText().toString();
        weight = edtWeight.getText().toString();

        if (TextUtils.isEmpty(firstName)) {
            edtFName.setError(getString(R.string.field_cannot));
            edtFName.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(lastName)) {
            edtLName.setError(getString(R.string.field_cannot));
            edtLName.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(dob)) {
            edtDob.setError(getString(R.string.field_cannot));
            return false;
        } else if (TextUtils.isEmpty(height)) {
            edtHeight.setError(getString(R.string.field_cannot));
            edtHeight.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(weight)) {
            edtWeight.setError(getString(R.string.field_cannot));
            edtWeight.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(genderId)) {
            getGender();
            return false;
        } else if (TextUtils.isEmpty(relationId)) {
            getRelation();
            return false;
        } else if (TextUtils.isEmpty(titleId)) {
            getSalutation();
            return false;
        }
        if (rbIciciYes.isChecked()) {
            Toast.makeText(mContext, "Not Allowed In Medical Case", Toast.LENGTH_SHORT).show();
            return false;
        }

        iffcoQ1 = edtIffco1.getText().toString();
        iffcoQ2 = edtIffco2.getText().toString();
        iffcoQ3 = edtIffco3.getText().toString();

        if (iffcoQ1.equalsIgnoreCase("No") || iffcoQ1.equals("0"))
            iffcoQ1 = "";
        if (iffcoQ2.equalsIgnoreCase("No") || iffcoQ2.equals("0"))
            iffcoQ2 = "";
        if (iffcoQ3.equalsIgnoreCase("No") || iffcoQ3.equals("0"))
            iffcoQ3 = "";

        genderValue = spnGender.getSelectedItem().toString();
        return true;
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

    private void initOccupations() {
        for (int i = 0; i < occupationsList.size(); i++) {
            String stateName = occupationsList.get(i).getName().trim();
            String stateId = String.valueOf(occupationsList.get(i).getCode());
            occupations.add(new String2WithTag(stateName, stateId));
        }

        ArrayAdapter<String2WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, occupations);
        spnOccupation.setAdapter(adapter);

        spnOccupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                occupationId = (String) stringWithTag.tag;
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getOccupations() {
        if (AppUtils.isOnline(mContext)) {
//            progressdialog.show();
            try {
                HealthManager.getInstance().getOccupation(mContext, quotationId, companyName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
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
                relationId = (String) stringWithTag.tag;

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

    @Override
    public void onResponse(Object response) {

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

        if (response instanceof HealthOccupation) {
            HealthOccupation cResponse = (HealthOccupation) response;
            if (cResponse.getStatus().equalsIgnoreCase("1")) {
                occupationsList.clear();
                occupations.clear();
                occupationsList = cResponse.getOccupation();
                if (occupationsList.size() > 0) {
                    initOccupations();
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

    }

    public void onDateClick() {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                R.style.datepicker, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                edtDob.setText(AppUtils.SIMPLE_DATE_FORMAT.format(newDate.getTime()));

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public void onNextClick(View view) {
        if (isValid()) {
            Intent intent;

            tempInsuredName.add(firstName + " " + lastName);
            tempInsuredGender.add(genderValue);
            tempInsuredDob.add(dob);

            mBundle.putString(AppUtils.HL_INSURED_FNAME, tempFName + "*#" + firstName);
            mBundle.putString(AppUtils.HL_INSURED_LNAME, tempLName + "*#" + lastName);
            mBundle.putString(AppUtils.HL_INSURED_DOB, tempDob + "*#" + dob);
            mBundle.putString(AppUtils.HL_INSURED_HEIGHT, tempHeight + "*#" + height);
            mBundle.putString(AppUtils.HL_INSURED_WEIGHT, tempWeight + "*#" + weight);
            mBundle.putString(AppUtils.HL_INSURED_GENDER, tempGenderId + "*#" + genderId);
            mBundle.putString(AppUtils.HL_INSURED_TITLE, tempTitleId + "*#" + titleId);
            mBundle.putString(AppUtils.HL_INSURED_RELATION, tempRelationId + "*#" + relationId);
            mBundle.putString(AppUtils.HL_INSURED_OCCUPATION, tempOccupationId + "*#" + occupationId);
            mBundle.putString(AppUtils.ICICI_EXIST, tempExist + "*#" + "No");

            mBundle.putString(AppUtils.IFFCO_Q1, iffcoQ1 + "*#" + tempIf1);
            mBundle.putString(AppUtils.IFFCO_Q2, iffcoQ2 + "*#" + tempIf2);
            mBundle.putString(AppUtils.IFFCO_Q3, iffcoQ3 + "*#" + tempIf3);

            mBundle.putStringArrayList(AppUtils.HL_INSURED_NAME, tempInsuredName);
            mBundle.putStringArrayList(AppUtils.HL_INSURED_GENDER_VALUE, tempInsuredGender);
            mBundle.putStringArrayList(AppUtils.HL_INSURED_DOB_VALUE, tempInsuredDob);

            if (isFather || isMother || isSpouse)
                if (totalDaughter > 0 || totalSon > 0)
                    intent = new Intent(mContext, InsuredMember3Activity.class);
                else intent = new Intent(mContext, NomineeProposerActivity.class);
            else intent = new Intent(mContext, NomineeProposerActivity.class);

            intent.putExtras(mBundle);
            startActivity(intent);
        }
    }
}
