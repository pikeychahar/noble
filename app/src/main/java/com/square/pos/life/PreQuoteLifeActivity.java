package com.square.pos.life;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.square.pos.R;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.ApiManager;
import com.square.pos.model_life.AnualIncome;
import com.square.pos.model_life.AnualIncomeList;
import com.square.pos.model_life.GetLifeQuoteId;
import com.square.pos.model_life.Occupation;
import com.square.pos.model_life.OccupationList;
import com.square.pos.model_life.Qualification;
import com.square.pos.model_life.QualificationList;
import com.square.pos.model_life.SumList;
import com.square.pos.model_life.Suminsured;
import com.square.pos.utils.AppUtils;
import com.square.pos.utils.String2WithTag;
import com.square.pos.utils.String3WithTag;
import com.square.pos.utils.String4WithTag;
import com.square.pos.utils.StringWithTag;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class PreQuoteLifeActivity extends AppCompatActivity
        implements onRequestCompleteCallBackListener {
    EditText edtDob;
    RadioButton btnMale;
    Spinner spnSumIns, spnAnnual, spnTobacco, spnOccupation, spnEducation;

    Context mContext;
    Bundle mBundle;
    String name, email, phone, userId, userType, dob, sumInsured, eduId, education,
            occupationId, occupation, annualId, annual, gender, tobacco, quoteId;
    SharedPreferences preferences;

    ArrayList<Suminsured> sumInsuredList = new ArrayList<>();
    ArrayList<StringWithTag> sumInsureds = new ArrayList<>();
    ArrayList<Qualification> educationList = new ArrayList<>();
    ArrayList<String2WithTag> educations = new ArrayList<>();
    ArrayList<Occupation> occupationList = new ArrayList<>();
    ArrayList<String3WithTag> occupations = new ArrayList<>();
    ArrayList<AnualIncome> annualList = new ArrayList<>();
    ArrayList<String4WithTag> annuals = new ArrayList<>();
int ageYears;
    ProgressDialog progressdialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_quote_life);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        this.setTitle("Life Insurance");
        mBundle = getIntent().getExtras();
        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait...");

        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);

        name = preferences.getString(AppUtils.NAME, "");
        email = preferences.getString(AppUtils.MOBILE, "");
        phone = preferences.getString(AppUtils.EMAIL, "");
        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");

        edtDob = findViewById(R.id.edtDob);
        btnMale = findViewById(R.id.btnMale);
        spnSumIns = findViewById(R.id.spnSumIns);
        spnAnnual = findViewById(R.id.spnAnnual);
        spnTobacco = findViewById(R.id.spnTobacco);
        spnOccupation = findViewById(R.id.spnOccupation);
        spnEducation = findViewById(R.id.spnEducation);
        gender = "male";

        getOccupation();
        getSumInsured();
        getQualification();
        getSumAnnual();

        if (mBundle != null) {
            name = mBundle.getString(AppUtils.NAME);
            phone = mBundle.getString(AppUtils.MOBILE);
            email = mBundle.getString(AppUtils.EMAIL);
        } else {
            mBundle = new Bundle();
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

    public void onDateClick(View view) {

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

    public void onLifeNomineeClick(View view) {
        if (isValid())
            getQuoteId();
    }

    public boolean isValid() {
        if (btnMale.isChecked())
            gender = "male";
        else
            gender = "female";

        if (spnTobacco.getSelectedItemPosition() == 0)
            tobacco = "0";
        else tobacco = "1";

        dob = edtDob.getText().toString();

        if (TextUtils.isEmpty(dob)) {
            edtDob.setError(getString(R.string.field_cannot));
            return false;
        }else {
            ageYears = AppUtils.getAge(dob);
        }

        return true;
    }

    public void getQuoteId() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                ApiManager.getInstance().getQuoteId(mContext, userId, userType, name, email, phone,
                        dob, gender, tobacco, annualId, eduId, occupationId, sumInsured);
            } catch (Exception e) {
                progressdialog.dismiss();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getSumInsured() {
        if (AppUtils.isOnline(mContext)) {
            try {
                ApiManager.getInstance().getLifeSumInsured(mContext);
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
                ApiManager.getInstance().getLifeOccupation(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getSumAnnual() {
        if (AppUtils.isOnline(mContext)) {
            try {
                ApiManager.getInstance().getLifeAnnual(mContext);
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
                ApiManager.getInstance().getLifeQualification(mContext,"0");
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
            occupations.add(new String3WithTag(stateName, stateId));
        }

        ArrayAdapter<String3WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, occupations);
        spnOccupation.setAdapter(adapter);

        spnOccupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String3WithTag stringWithTag = (String3WithTag) parent.getItemAtPosition(position);
                occupationId = (String) stringWithTag.tag;
                occupation = stringWithTag.string;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSumInsured() {
        for (int i = 0; i < sumInsuredList.size(); i++) {
            String stateName = sumInsuredList.get(i).getSuminsured().trim();
            String stateId = sumInsuredList.get(i).getSuminsured();
            sumInsureds.add(new StringWithTag(stateName, stateId));
        }

        ArrayAdapter<StringWithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, sumInsureds);
        spnSumIns.setAdapter(adapter);

        spnSumIns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag stringWithTag = (StringWithTag) parent.getItemAtPosition(position);
                sumInsured = (String) stringWithTag.tag;
                sumInsured = stringWithTag.string;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initAnnual() {
        for (int i = 0; i < annualList.size(); i++) {
            String stateName = annualList.get(i).getName().trim();
            String stateId = annualList.get(i).getCode();
            annuals.add(new String4WithTag(stateName, stateId));
        }

        ArrayAdapter<String4WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, annuals);
        spnAnnual.setAdapter(adapter);

        spnAnnual.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String4WithTag stringWithTag = (String4WithTag) parent.getItemAtPosition(position);
                annualId = (String) stringWithTag.tag;
                annual = stringWithTag.string;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    @Override
    public void onResponse(Object response) {
        if (response instanceof AnualIncomeList) {
            AnualIncomeList cResponse = (AnualIncomeList) response;
            if (cResponse.getStatus().equals("1") && cResponse.getAnualIncome() != null) {
                annualList.clear();
                annuals.clear();
                annualList = (ArrayList<AnualIncome>) cResponse.getAnualIncome();
                if (annualList.size() > 0) {
                    initAnnual();
                }
            }
        }
        if (response instanceof SumList) {
            SumList cResponse = (SumList) response;
            if (cResponse.getStatus().equals("1") && cResponse.getSuminsured() != null) {
                sumInsuredList.clear();
                sumInsureds.clear();
                sumInsuredList = (ArrayList<Suminsured>) cResponse.getSuminsured();
                if (sumInsuredList.size() > 0) {
                    initSumInsured();
                }
            }
        }

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
        if (response instanceof GetLifeQuoteId) {
            GetLifeQuoteId cResponse = (GetLifeQuoteId) response;
            if (cResponse.getSuccess().equals("1")) {
                quoteId = cResponse.getqId();
                if (!TextUtils.isEmpty(quoteId)) {
                    Intent intent = new Intent(mContext, LifePremiumActivity.class);
                    mBundle.putString(AppUtils.QUOTATION_ID, quoteId);
                    mBundle.putString(AppUtils.SUMINSURED, sumInsured);
                    mBundle.putString(AppUtils.TOBACCO_USER, tobacco);
                    mBundle.putInt(AppUtils.AGE_YEAR, ageYears);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }
            } else AppUtils.showToast(mContext, cResponse.getMessage());
            progressdialog.dismiss();
        }
    }
}