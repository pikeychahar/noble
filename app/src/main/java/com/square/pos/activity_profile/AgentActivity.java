package com.square.pos.activity_profile;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.UserManager;
import com.square.pos.model_pos.Employee;
import com.square.pos.model_pos.EmployeeList;
import com.square.pos.utils.AppUtils;
import com.square.pos.utils.String5WithTag;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AgentActivity extends AbstractActivity implements onRequestCompleteCallBackListener {

    private Context mContext;
    private Spinner spnQualification;
    private EditText edtFirstName, edtLastName, edtDOB, edtEmailAddress, edtMobile,
            edtAlternateMobile, edtPAN, edtAadhar, edtPassword, edtConfirmPass;
    String firstName, lastName, dateOfBirth, emailAddress, mobile, alternateMobile, panCard,
            aadharNumber, password, confirmPassword, reference, gender, qualification, referenceId,
            permission;
    Bundle mBundle = new Bundle();
    int genderPos, qualificationPos;
    boolean isAgentOn, isPermitted;
    SharedPreferences preferences, posPreferences;
    private com.toptoche.searchablespinnerlibrary.SearchableSpinner spnReference;
    private List<EmployeeList> referenceList = new ArrayList<>();
    List<String5WithTag> refList = new ArrayList<>();
    SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        this.setTitle("Personal Detail");
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        posPreferences = getSharedPreferences("pos", MODE_PRIVATE);

        isAgentOn = preferences.getBoolean(AppUtils.IS_AGENT, false);
        permission = preferences.getString(AppUtils.POS_PERMISSION, "");
        referenceId = preferences.getString(AppUtils.EMP_ID, "");

        edtDOB = findViewById(R.id.dateOfBirth1);
        edtFirstName = findViewById(R.id.editFirstName);
        edtLastName = findViewById(R.id.editLastName);
        edtEmailAddress = findViewById(R.id.editEmail);
        edtMobile = findViewById(R.id.editPhone);
        edtAlternateMobile = findViewById(R.id.editAdditionalPhone);
        edtPAN = findViewById(R.id.editPanCard);
        edtAadhar = findViewById(R.id.editAadhar);
        edtPassword = findViewById(R.id.editPassword);
        edtConfirmPass = findViewById(R.id.editPassword2);
        spnReference = findViewById(R.id.spnReference);

        spnQualification = findViewById(R.id.qualification);
        gender = "Male";

        if (!TextUtils.isEmpty(permission) && (permission.equals("1") || permission.equals("2"))) {
            findViewById(R.id.txtReference).setVisibility(View.GONE);
            isPermitted = true;
        } else getReference();
        findViewById(R.id.txtReference).setVisibility(View.GONE);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setSaved();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onAgentAddress(View view) {

        if (checkValidation()) {
            Intent intent = new Intent(mContext, AgentAddressActivity.class);
            mBundle.putString("firstName", firstName);
            mBundle.putString("lastName", lastName);
            mBundle.putString("dateOfBirth", dateOfBirth);
            mBundle.putString("emailAddress", emailAddress);
            mBundle.putString("mobile", mobile);
            mBundle.putString("alternateMobile", alternateMobile);
            mBundle.putString("panCard", panCard);
            mBundle.putString("aadharNumber", aadharNumber);
            mBundle.putString("password", password);
            mBundle.putString("reference", referenceId);
            mBundle.putString("gender", gender);
            mBundle.putString("qualification", qualification);
            intent.putExtra("personal", mBundle);
            startActivity(intent);
            saveValues();
        }
    }

    private void getPersonalValues() {

        firstName = edtFirstName.getText().toString().trim();
        lastName = edtLastName.getText().toString().trim();
        dateOfBirth = edtDOB.getText().toString().trim();
        emailAddress = edtEmailAddress.getText().toString().trim();
        mobile = edtMobile.getText().toString();
        alternateMobile = edtAlternateMobile.getText().toString();
        panCard = edtPAN.getText().toString();
        aadharNumber = edtAadhar.getText().toString();
        password = edtPassword.getText().toString().trim();
        confirmPassword = edtConfirmPass.getText().toString().trim();

        if (spnReference.getSelectedItem() != null)
            reference = spnReference.getSelectedItem().toString();

        qualification = spnQualification.getSelectedItem().toString();
        qualificationPos = spnQualification.getSelectedItemPosition();
    }

    private void initReference() {
        for (int i = 0; i < referenceList.size(); i++) {
            String modelName = referenceList.get(i).getName();
            String modelId = referenceList.get(i).getEmployeeId();
            String full = modelName + "-" + modelId;
            refList.add(new String5WithTag(full, modelId));
        }

        ArrayAdapter<String5WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, refList);
        spnReference.setAdapter(adapter);

        spnReference.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String5WithTag stringWithTag = (String5WithTag) parent.getItemAtPosition(position);
                referenceId = stringWithTag.tag.toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean checkValidation() {

        getPersonalValues();
        if (TextUtils.isEmpty(firstName)) {
            edtFirstName.setError("Field can not be empty");
            edtFirstName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(lastName)) {
            edtLastName.setError("Field can not be empty");
            edtLastName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(dateOfBirth)) {
            edtDOB.setError("Field can not be empty");
            edtDOB.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(emailAddress)) {
            edtEmailAddress.setError("Field can not be empty");
            edtEmailAddress.requestFocus();
            return false;
        } else if (!(AppUtils.isValidMail(emailAddress))) {
            edtEmailAddress.setError("Invalid Email");
            edtEmailAddress.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(mobile)) {
            edtMobile.setError("Field can not be empty");
            edtMobile.requestFocus();
            return false;
        } else if (!(AppUtils.isValidMobile(mobile))) {
            edtMobile.setError("Invalid Phone Number");
            edtMobile.requestFocus();
            return false;
        }
        if (!TextUtils.isEmpty(alternateMobile)) {
            if (!(AppUtils.isValidMobile(alternateMobile))) {
                edtAlternateMobile.setError("Invalid Phone Number");
                edtAlternateMobile.requestFocus();
                return false;
            }
        }
        if (TextUtils.isEmpty(panCard)) {
            edtPAN.setError("Field can not be empty");
            edtPAN.requestFocus();
            return false;
        } else if (panCard.length() != 10) {
            edtPAN.setError("Invalid PAN card Number");
            edtPAN.requestFocus();
            return false;
        } else if (AppUtils.isValidPAN(panCard)) {
            edtPAN.setError("Invalid PAN card Number");
            edtPAN.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(aadharNumber)) {
            edtAadhar.setError("Field can not be empty");
            edtAadhar.requestFocus();
            return false;
        } else if (aadharNumber.length() != 12) {
            edtAadhar.setError("Invalid Aadhar card Number");
            edtAadhar.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Field can not be empty");
            edtPassword.requestFocus();
            return false;
        }
        if (password.length() < 8) {
            edtPassword.setError("Minimum size 8 Characters");
            edtPassword.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            edtConfirmPass.setError("Field can not be empty");
            edtConfirmPass.requestFocus();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            edtConfirmPass.setError("Password must be same");
            edtConfirmPass.requestFocus();
            return false;
        }

        if (!(qualificationPos > 0)) {
            spnQualification.requestFocus();
            Toast.makeText(mContext, "Select Qualification", Toast.LENGTH_SHORT).show();
            return false;
        }
//        if (!isPermitted)
//            if (spnReference.getSelectedItem() == null) {
//                Toast.makeText(mContext, "Select Reference", Toast.LENGTH_SHORT).show();
//                return false;
//            }
        return true;
    }

    private void saveValues() {
        getPersonalValues();

        posPreferences.edit().putString(AppUtils.A_FIRST_NAME, firstName).apply();
        posPreferences.edit().putString(AppUtils.A_LAST_NAME, lastName).apply();
        posPreferences.edit().putString(AppUtils.A_DATE_OF_BIRTH, dateOfBirth).apply();
        posPreferences.edit().putString(AppUtils.A_EMAIL, emailAddress).apply();
        posPreferences.edit().putString(AppUtils.A_MOBILE, mobile).apply();
        posPreferences.edit().putString(AppUtils.A_ALTER_MOBILE, alternateMobile).apply();
        posPreferences.edit().putString(AppUtils.A_PAN, panCard).apply();
        posPreferences.edit().putString(AppUtils.A_AADHAR, aadharNumber).apply();
        posPreferences.edit().putString(AppUtils.A_PASSWORD, password).apply();
        posPreferences.edit().putString(AppUtils.A_CONFIRM_PASSWORD, confirmPassword).apply();
        posPreferences.edit().putString(AppUtils.A_REFERENCE, reference).apply();
    }

    private void setSaved() {
        firstName = posPreferences.getString(AppUtils.A_FIRST_NAME, "");
        if (!TextUtils.isEmpty(firstName))
            edtFirstName.setText(firstName);

        lastName = posPreferences.getString(AppUtils.A_LAST_NAME, "");
        if (!TextUtils.isEmpty(lastName))
            edtLastName.setText(lastName);

        dateOfBirth = posPreferences.getString(AppUtils.A_DATE_OF_BIRTH, "");
        if (!TextUtils.isEmpty(dateOfBirth))
            edtDOB.setText(dateOfBirth);

        emailAddress = posPreferences.getString(AppUtils.A_EMAIL, "");
        if (!TextUtils.isEmpty(emailAddress))
            edtEmailAddress.setText(emailAddress);

        mobile = posPreferences.getString(AppUtils.A_MOBILE, "");
        if (!TextUtils.isEmpty(mobile))
            edtMobile.setText(mobile);

        alternateMobile = posPreferences.getString(AppUtils.A_ALTER_MOBILE, "");
        if (!TextUtils.isEmpty(alternateMobile))
            edtAlternateMobile.setText(alternateMobile);

        panCard = posPreferences.getString(AppUtils.A_PAN, "");
        if (!TextUtils.isEmpty(panCard))
            edtPAN.setText(panCard);

        aadharNumber = posPreferences.getString(AppUtils.A_AADHAR, "");
        if (!TextUtils.isEmpty(aadharNumber))
            edtAadhar.setText(aadharNumber);

        password = posPreferences.getString(AppUtils.A_PASSWORD, "");
        if (!TextUtils.isEmpty(password))
            edtPassword.setText(password);

        confirmPassword = posPreferences.getString(AppUtils.A_CONFIRM_PASSWORD, "");
        if (!TextUtils.isEmpty(confirmPassword))
            edtConfirmPass.setText(confirmPassword);
    }

    @Override
    protected void onPause() {
        saveValues();
        super.onPause();
    }

    @Override
    protected void onResume() {
        saveValues();
        super.onResume();
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof Employee) {
            Employee mResponse = (Employee) response;
            if (mResponse.getStatus().equals(1)) {
                if (referenceList.size() > 0) {
                    referenceList.clear();
                    refList.clear();
                }
                referenceList = mResponse.getEmployeeList();
                if (referenceList.size() > 0)
                    initReference();
            }
        }
    }

    public void getReference() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getReferenceList(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void onMaleClick(View view) {
        gender = "Male";
    }

    public void onFemaleClick(View view) {
        gender = "Female";
    }

    public void onDateClick(View view) {
        AppUtils.checkSoftKeyboard(this);
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.add(Calendar.YEAR, -18);
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                R.style.datepicker, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                edtDOB.setText(dateFormatter.format(newDate.getTime()));
                edtDOB.setError(null);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(newCalendar.getTimeInMillis());
        datePickerDialog.show();

        try {
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorPrimary));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
