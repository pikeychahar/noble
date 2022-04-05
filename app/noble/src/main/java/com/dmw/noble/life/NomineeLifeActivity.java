package com.dmw.noble.life;

import android.app.DatePickerDialog;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.ApiManager;
import com.dmw.noble.model.CommonResponse;
import com.dmw.noble.model_life.AppointeeList;
import com.dmw.noble.model_life.AppointeeRelation;
import com.dmw.noble.model_life.Gender;
import com.dmw.noble.model_life.GenderList;
import com.dmw.noble.model_life.LifeProposal;
import com.dmw.noble.model_life.Marital;
import com.dmw.noble.model_life.MaritalList;
import com.dmw.noble.model_life.NomineeList;
import com.dmw.noble.model_life.NomineeRelation;
import com.dmw.noble.model_life.Salutation;
import com.dmw.noble.model_life.SalutationList;
import com.dmw.noble.utils.AppUtils;
import com.dmw.noble.utils.String2WithTag;
import com.dmw.noble.utils.String3WithTag;
import com.dmw.noble.utils.String4WithTag;
import com.dmw.noble.utils.String6WithTag;
import com.dmw.noble.utils.StringWithTag;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class NomineeLifeActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {
    Context mContext;
    Bundle mBundle;
    Spinner spnNmTitle, spnNmRelation, spnNmGender, spnNmMaterial, spnAppTitle, spnAppRelation,
            spnAppGender, spnAppMaterial;
    EditText edtNmFirstName, edtNmLastName, edtNmMobile, edtNmDob, edtAppFirstName, edtAppLastName,
            edtAppMobile, edtAppDob;
    String quotationId, salutation, firstName, lastName, dob, pinCode, state, city, address1,
            address2, address3, annualIncome, email, mobile, occupation, education, maritalStatus,
            gender, nmTitle, nmFirstName, nmLastName, nmGender, nmMarital, nmRelation, nmPhone,
            nmDob, apTitle, apFirstName, apLastName, apGender, apMarital, apRelation, apPhone,
            apDob, business, existingCover, empName, empAddress, empSector, spouseName;
    boolean isAppointee;

    ProgressDialog progressdialog;

    ArrayList<Salutation> salutationList = new ArrayList<>();
    ArrayList<StringWithTag> salutations = new ArrayList<>();

    ArrayList<NomineeRelation> relationList = new ArrayList<>();
    ArrayList<String2WithTag> relations = new ArrayList<>();

    ArrayList<Marital> maritalList = new ArrayList<>();
    ArrayList<String3WithTag> maritalData = new ArrayList<>();

    ArrayList<AppointeeRelation> appRelationList = new ArrayList<>();
    ArrayList<String4WithTag> appRelations = new ArrayList<>();

    ArrayList<Gender> genderList = new ArrayList<>();
    ArrayList<String6WithTag> genders = new ArrayList<>();
    LifeProposal lifeProposal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nominee_life);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;

        this.setTitle("Nominee Detail");
        spnNmTitle = findViewById(R.id.spnNmTitle);
        spnNmRelation = findViewById(R.id.spnNmRelation);
        spnNmGender = findViewById(R.id.spnNmGender);
        spnNmMaterial = findViewById(R.id.spnNmMaterial);

        edtNmFirstName = findViewById(R.id.edtNmFirstName);
        edtNmLastName = findViewById(R.id.edtNmLastName);
        edtNmMobile = findViewById(R.id.edtNmMobile);
        edtNmDob = findViewById(R.id.edtNmDob);

        //App is refer as Appointee
        spnAppTitle = findViewById(R.id.spnAppTitle);
        spnAppRelation = findViewById(R.id.spnAppRelation);
        spnAppGender = findViewById(R.id.spnAppGender);
        spnAppMaterial = findViewById(R.id.spnAppMaterial);

        edtAppMobile = findViewById(R.id.edtAppMobile);
        edtAppFirstName = findViewById(R.id.edtAppFirstName);
        edtAppLastName = findViewById(R.id.edtAppLastName);
        edtAppDob = findViewById(R.id.edtAppDob);

        getSalutation();
        getNomineeRelation();
        getGender();
        getMarital();
        getAppointeeRelation();

        final Intent intent = getIntent();
        lifeProposal = (LifeProposal) intent.getSerializableExtra("lifeProposal");

        mBundle = getIntent().getExtras();
        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait...");

        if (mBundle == null) {
            mBundle = new Bundle();
        } else {
            quotationId = mBundle.getString(AppUtils.QUOTATION_ID);
            salutation = mBundle.getString(AppUtils.LF_SAL_ID);
            firstName = mBundle.getString(AppUtils.A_FIRST_NAME);
            lastName = mBundle.getString(AppUtils.A_LAST_NAME);
            dob = mBundle.getString(AppUtils.OWNER_DOB);
            pinCode = mBundle.getString(AppUtils.PINCODE);
            state = mBundle.getString(AppUtils.LF_STATE);
            city = mBundle.getString(AppUtils.LF_CITY);
            address1 = mBundle.getString(AppUtils.LF_ADDRESS1);
            address2 = mBundle.getString(AppUtils.LF_ADDRESS2);
            address3 = mBundle.getString(AppUtils.LF_ADDRESS3);
            annualIncome = mBundle.getString(AppUtils.ANNUAL_INCOME);
            email = mBundle.getString(AppUtils.EMAIL);
            mobile = mBundle.getString(AppUtils.MOBILE);
            occupation = mBundle.getString(AppUtils.LF_OCC_ID);
            education = mBundle.getString(AppUtils.LF_EDU_ID);
            maritalStatus = mBundle.getString(AppUtils.LF_MARITAL);
            gender = mBundle.getString(AppUtils.LF_GENDER);
            business = mBundle.getString(AppUtils.LF_BUSINESS);
            existingCover = mBundle.getString(AppUtils.LF_EXISTING);
            empName = mBundle.getString(AppUtils.LF_EMPLOYER_NAME);
            empAddress = mBundle.getString(AppUtils.LF_EMPLOYER_ADDRESS);
            empSector = mBundle.getString(AppUtils.LF_SECTOR);
            spouseName = mBundle.getString(AppUtils.LF_SPOUSE);
        }

        if (lifeProposal != null){
            edtNmFirstName.setText(lifeProposal.getNomineeFirstName());
            edtNmLastName.setText(lifeProposal.getNomineeLastName());
            edtNmMobile.setText(lifeProposal.getNomineeMobileNo());
            edtNmDob.setText(lifeProposal.getNomineeDob());

            edtAppDob.setText(lifeProposal.getAppointeeDob());
            edtAppFirstName.setText(lifeProposal.getAppointeeFirstName());
            edtAppLastName.setText(lifeProposal.getAppointeeLastName());
            edtAppMobile.setText(lifeProposal.getAppointeeMobileNo());
        }
    }

    private void initValues() {
        nmFirstName = AppUtils.trimString(edtNmFirstName);
        nmLastName = AppUtils.trimString(edtNmLastName);
        nmPhone = AppUtils.trimString(edtNmMobile);
        nmDob = AppUtils.trimString(edtNmDob);

        apFirstName = AppUtils.trimString(edtAppFirstName);
        apLastName = AppUtils.trimString(edtAppLastName);
        apPhone = AppUtils.trimString(edtAppMobile);
        apDob = AppUtils.trimString(edtAppDob);
    }

    private boolean isValidData() {
        initValues();
        if (TextUtils.isEmpty(nmFirstName)) {
            edtNmFirstName.setError("Field can not be empty");
            edtNmFirstName.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(nmLastName)) {
            edtNmLastName.setError("Field can not be empty");
            edtNmLastName.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(nmPhone)) {
            edtNmMobile.setError("Field can not be empty");
            edtNmMobile.requestFocus();
            return false;
        } else if (!(AppUtils.isValidMobile(nmPhone))) {
            edtNmMobile.setError("Invalid Phone Number");
            edtNmMobile.requestFocus();
            return false;
        }
        if (!TextUtils.isEmpty(nmDob)) {
            if (AppUtils.validDate(nmDob)) {
                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                Date date = null;
                try {
                    date = formatter.parse(nmDob);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                if (date != null) {
                    nmDob = newFormat.format(date);
                }
            } else {
                edtNmDob.setError("Invalid Date");
                edtNmDob.requestFocus();
                return false;
            }
        } else {
            edtNmDob.setError("Field can not be empty");
            edtNmDob.requestFocus();
            return false;
        }
        if (isAppointee) {
            if (TextUtils.isEmpty(apFirstName)) {
                edtAppFirstName.setError("Field can not be empty");
                edtAppFirstName.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(apLastName)) {
                edtAppLastName.setError("Field can not be empty");
                edtAppLastName.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(apPhone)) {
                edtAppMobile.setError("Field can not be empty");
                edtAppMobile.requestFocus();
                return false;
            } else if (!(AppUtils.isValidMobile(apPhone))) {
                edtAppMobile.setError("Invalid Phone Number");
                edtAppMobile.requestFocus();
                return false;
            }
            if (!TextUtils.isEmpty(apDob)) {
                if (AppUtils.validDate(apDob)) {
                    DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                    Date date = null;
                    try {
                        date = formatter.parse(apDob);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    if (date != null) {
                        apDob = newFormat.format(date);
                    }
                } else {
                    edtAppDob.setError("Invalid Date");
                    edtAppDob.requestFocus();
                    return false;
                }
            } else {
                edtAppDob.setError("Field can not be empty");
                edtAppDob.requestFocus();
                return false;
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

    public void onLifeReviewClick(View view) {
        if (isValidData())
            updateProposal();
    }

    public void onLifeDateClick(View view) {

        EditText edtDob = (EditText) view;

        AppUtils.checkSoftKeyboard(this);
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                R.style.datepicker, (view1, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);

            edtDob.setText(AppUtils.SIMPLE_DATE_FORMAT.format(newDate.getTime()));
            edtDob.setError(null);

            if (edtDob == edtNmDob) {
                String dt = edtDob.getText().toString().trim();
                int yr = AppUtils.getAge(dt);
                if (yr < 18) {
                    findViewById(R.id.llAppointee).setVisibility(View.VISIBLE);
                    isAppointee = true;
                } else {
                    isAppointee = false;
                    findViewById(R.id.llAppointee).setVisibility(View.GONE);
                }
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(newCalendar.getTimeInMillis());
        datePickerDialog.show();
    }

    @Override
    public void onResponse(Object response) {
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
        if (response instanceof NomineeList) {
            NomineeList cResponse = (NomineeList) response;
            if (cResponse.getStatus().equals("1") && cResponse.getNomineeRelation() != null) {
                relationList.clear();
                relations.clear();
                relationList = (ArrayList<NomineeRelation>) cResponse.getNomineeRelation();
                if (relationList.size() > 0) {
                    initNomineeRelation();
                }
            }
        }
        if (response instanceof AppointeeList) {
            AppointeeList cResponse = (AppointeeList) response;
            if (cResponse.getStatus().equals("1") && cResponse.getAppointeeRelation() != null) {
                appRelationList.clear();
                appRelations.clear();
                appRelationList = (ArrayList<AppointeeRelation>) cResponse.getAppointeeRelation();
                if (appRelationList.size() > 0) {
                    initAppointeeRelation();
                }
            }
        }
        if (response instanceof CommonResponse) {
            CommonResponse cResponse = (CommonResponse) response;
            if (cResponse.getSuccess().equals("1")) {

                Intent intent = new Intent(mContext, OverviewLifeActivity.class);
                intent.putExtras(mBundle);
                intent.putExtra("lifeProposal", lifeProposal);
                startActivity(intent);
            }
        }
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

        spnNmMaterial.setAdapter(adapter);
        spnNmMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String3WithTag stringWithTag = (String3WithTag) parent.getItemAtPosition(position);
                nmMarital = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnAppMaterial.setAdapter(adapter);
        spnAppMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String3WithTag stringWithTag = (String3WithTag) parent.getItemAtPosition(position);
                apMarital = (String) stringWithTag.tag;
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

        spnNmTitle.setAdapter(adapter);
        spnNmTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag stringWithTag = (StringWithTag) parent.getItemAtPosition(position);
                nmTitle = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnAppTitle.setAdapter(adapter);
        spnAppTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag stringWithTag = (StringWithTag) parent.getItemAtPosition(position);
                apTitle = (String) stringWithTag.tag;
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

        spnNmGender.setAdapter(adapter);
        spnNmGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String6WithTag stringWithTag = (String6WithTag) parent.getItemAtPosition(position);
                nmGender = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnAppGender.setAdapter(adapter);
        spnAppGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String6WithTag stringWithTag = (String6WithTag) parent.getItemAtPosition(position);
                apGender = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getNomineeRelation() {
        if (AppUtils.isOnline(mContext)) {
            try {
                ApiManager.getInstance().getLifeNomineeRelation(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void initNomineeRelation() {
        for (int i = 0; i < relationList.size(); i++) {
            String stateName = relationList.get(i).getName().trim();
            String stateId = relationList.get(i).getCode();
            relations.add(new String2WithTag(stateName, stateId));
        }

        ArrayAdapter<String2WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, relations);

        spnNmRelation.setAdapter(adapter);
        spnNmRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                nmRelation = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getAppointeeRelation() {
        if (AppUtils.isOnline(mContext)) {
            try {
                ApiManager.getInstance().getLifeAppointee(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void initAppointeeRelation() {
        for (int i = 0; i < appRelationList.size(); i++) {
            String stateName = appRelationList.get(i).getName().trim();
            String stateId = appRelationList.get(i).getCode();
            appRelations.add(new String4WithTag(stateName, stateId));
        }

        ArrayAdapter<String4WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, appRelations);

        spnAppRelation.setAdapter(adapter);
        spnAppRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String4WithTag stringWithTag = (String4WithTag) parent.getItemAtPosition(position);
                apRelation = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void updateProposal() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                ApiManager.getInstance().updateLifeProposal(mContext, quotationId, salutation,
                        firstName, lastName, dob, pinCode, state, city, address1, address2,
                        address3, annualIncome, email, mobile, occupation, education, maritalStatus,
                        gender, nmTitle, nmFirstName, nmLastName, nmGender, nmMarital, nmRelation,
                        nmPhone, nmDob, apTitle, apFirstName, apLastName, apGender, apMarital,
                        apRelation, apPhone, apDob, business, existingCover, empName, empAddress,
                        empSector, spouseName);
            } catch (Exception e) {
                progressdialog.dismiss();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

}