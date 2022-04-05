package com.square.pos.activity_motor;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.ApiManager;
import com.square.pos.manager.UserManager;
import com.square.pos.model.Owner;
import com.square.pos.model.ProposalPincode;
import com.square.pos.model.fetch_detail.GetOwner;
import com.square.pos.model.master.Gender;
import com.square.pos.model.master.GenderStatus;
import com.square.pos.model.master.Marital;
import com.square.pos.model.master.MaritalStatus;
import com.square.pos.model.master.Occupation;
import com.square.pos.model.master.OccupationStatus;
import com.square.pos.model.master.Salutation;
import com.square.pos.model.master.SalutationStatus;
import com.square.pos.utils.AppUtils;
import com.square.pos.utils.String3WithTag;
import com.square.pos.utils.String4WithTag;
import com.square.pos.utils.String5WithTag;
import com.square.pos.utils.String6WithTag;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public class OwnerDetailActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {

    private EditText edtDOB, edtFirstName, edtLastName, edtEmail, edtMobile, edtAdditionalMobile,
            edtPan, edtAddress1, edtAddress2, edtAddress3, edtRAddress1, edtRAddress2, edtRAddress3,
            edtRPinCode, edtGst, edtOrgName, edtPincode;
    private Spinner spnGender, spnMaterial, spnSalutation;
    private Context mContext;
    SharedPreferences preferences;
    private com.toptoche.searchablespinnerlibrary.SearchableSpinner spnOccupation;
    private ProgressDialog progressdialog;

    List<Occupation> occupationsList = new ArrayList<>();
    List<MaritalStatus> maritalList = new ArrayList<>();
    List<Gender> genderList = new ArrayList<>();
    List<Salutation> salutationList = new ArrayList<>();
    List<String4WithTag> occupations = new ArrayList<>();
    List<String3WithTag> maritalLists = new ArrayList<>();
    List<String6WithTag> genders = new ArrayList<>();
    List<String5WithTag> salutations = new ArrayList<>();

    String occupationId, genderId, maritalId, salutationId, firstName,
            lastName, email, mobile, additionalMobile, panCard, address1, address2, address3,
            regAddress1, regAddress2, regAddress3, pincode, regPinCode, gst, gender, material, city,
            regCity, occupation, nationality, dob, formattedDob, salutation, agentId, userId,
            quotationId, regAddress, stateName, stateId, ownedBy, orgName, companyName,
            gName, sName, oName, mName, fPinCode, cityName, mPincode;


    private CheckBox regCheck;
    private Bundle mBundle;
    boolean isIndividual, isOccupationHide;

    ImageView imgInsurer;
    TextView txtQuoteId, txtPolicyType, txtTotalPremium, txtVehicle, txtPolicyTenure;
    String imgPath, policyType, totalPremium, vehicleName, policyStartDate, policyEndDate;
    SimpleDateFormat dateFormatter;
    float gross = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_detail);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        edtDOB = findViewById(R.id.oDob1);
        this.setTitle("Owner Detail");

        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        mBundle = getIntent().getExtras();

        agentId = preferences.getString(AppUtils.PRIMARY_ID, "");
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");

        String userPhone = preferences.getString(AppUtils.MOBILE, "");
        String userEmail = preferences.getString(AppUtils.EMAIL, "");

        edtFirstName = findViewById(R.id.editOFirstName);
        edtLastName = findViewById(R.id.editOLastName);
        edtEmail = findViewById(R.id.oEmail);
        edtMobile = findViewById(R.id.oPhone);
        edtAdditionalMobile = findViewById(R.id.oAdditionalPhone);
        edtPan = findViewById(R.id.oPanCard);
        edtAddress1 = findViewById(R.id.oAddress1);
        edtAddress2 = findViewById(R.id.oAddress2);
        edtAddress3 = findViewById(R.id.oAddress22);
        edtRAddress1 = findViewById(R.id.oRAddress1);
        edtRAddress2 = findViewById(R.id.oRAddress2);
        edtRAddress3 = findViewById(R.id.oRAddress22);
        edtRPinCode = findViewById(R.id.oRPinCode);
        edtGst = findViewById(R.id.oGst);
        edtOrgName = findViewById(R.id.edtOrgName);
        edtPincode = findViewById(R.id.edtPinCode);

        txtQuoteId = findViewById(R.id.txtQuoteId);
        txtPolicyType = findViewById(R.id.txtPolicyType);
        txtTotalPremium = findViewById(R.id.txtTotalPremium);
        txtVehicle = findViewById(R.id.txtVehicle);
        txtPolicyTenure = findViewById(R.id.txtPolicyTenure);
        imgInsurer = findViewById(R.id.imgInsurer);

        spnOccupation = findViewById(R.id.occupation);
        spnMaterial = findViewById(R.id.material);
        spnGender = findViewById(R.id.gender);
        spnSalutation = findViewById(R.id.salutation);

        regCheck = findViewById(R.id.regCheck);

        spnOccupation.setTitle("Select Occupation");
        spnOccupation.setPositiveButton("Close");

        if (mBundle != null) {
            quotationId = mBundle.getString(AppUtils.QUOTATION_ID);
            fetchOwner();
            ownedBy = mBundle.getString(AppUtils.OWNED_BY);
            companyName = mBundle.getString(AppUtils.COMPANY_NAME);
            imgPath = mBundle.getString(AppUtils.IMG_PATH);
            policyType = mBundle.getString(AppUtils.POLICY_TYPE);
            policyStartDate = mBundle.getString(AppUtils.POLICY_START_DATE);
            policyEndDate = mBundle.getString(AppUtils.POLICY_END_DATE);
            vehicleName = mBundle.getString(AppUtils.VEHICLE_FULL);
            totalPremium = mBundle.getString(AppUtils.TOTAL_PREMIUM);

            txtQuoteId.setText(quotationId);
            txtPolicyType.setText(policyType);
            txtVehicle.setText(vehicleName);
            txtPolicyTenure.setText(String.format("Policy Tenure: %s - %s", policyStartDate, policyEndDate));
            if (!TextUtils.isEmpty(totalPremium))
                txtTotalPremium.setText(String.format("Gross Premium: %s", totalPremium));

            Glide.with(mContext)
                    .load(imgPath)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(imgInsurer);

            String company = companyName.toLowerCase();
            if (company.equals("sbi") || company.equals("future") || company.equals("digit")
                    || company.equals("iffco")) {
                findViewById(R.id.lblOccupation).setVisibility(View.VISIBLE);
                AppUtils.setEditTextMaxLength(edtAddress1, 28);
                AppUtils.setEditTextMaxLength(edtAddress2, 28);
                AppUtils.setEditTextMaxLength(edtAddress3, 28);

                if (company.equals("digit")) {
                    AppUtils.setEditTextMaxLength(edtAddress1, 10);
                }
            } else {
                isOccupationHide = true;
            }
        }

        if (!TextUtils.isEmpty(userEmail))
            edtEmail.setText(userEmail);

        if (!TextUtils.isEmpty(userPhone))
            edtMobile.setText(userPhone);

        getOccupation();
        getGender();
        getMarital();
        getSalutation();

        if (!TextUtils.isEmpty(ownedBy))
            if (ownedBy.equalsIgnoreCase("Individual")) {
                findViewById(R.id.cvName).setVisibility(View.VISIBLE);
                findViewById(R.id.oDob).setVisibility(View.VISIBLE);
                findViewById(R.id.txtOrgName).setVisibility(View.GONE);
                isIndividual = true;
            } else {
                findViewById(R.id.cvName).setVisibility(View.GONE);
                findViewById(R.id.oDob).setVisibility(View.GONE);
                findViewById(R.id.txtOrgName).setVisibility(View.VISIBLE);
                findViewById(R.id.text_pan_card).setVisibility(View.VISIBLE);
                findViewById(R.id.text_gst).setVisibility(View.VISIBLE);
                isIndividual = false;
            }

        edtPincode.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(6)});
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
                    mPincode = edtPincode.getText().toString();
                    getPincode();
                }
            }
        });

        gross = Float.parseFloat(totalPremium);
        if (gross > 49999)
            findViewById(R.id.text_pan_card).setVisibility(View.VISIBLE);
    }

    public void getPincode() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getProposerPincode(mContext, companyName, mPincode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
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

        if (!TextUtils.isEmpty(oName))
            for (int i = 0; i < occupationsList.size(); i++) {
                String sName = occupationsList.get(i).getName().trim();
                if (sName.equalsIgnoreCase(oName)) {
                    spnOccupation.setSelection(i);
                    break;
                }
            }

        spnOccupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String4WithTag stringWithTag = (String4WithTag) parent.getItemAtPosition(position);
                occupationId = (String) stringWithTag.tag;
                oName = stringWithTag.string;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initGender() {
        genders.clear();
        for (int i = 0; i < genderList.size(); i++) {
            String stateName = genderList.get(i).getName().trim();
            String stateId = genderList.get(i).getCode();
            genders.add(new String6WithTag(stateName, stateId));
        }

        ArrayAdapter<String6WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, genders);
        spnGender.setAdapter(adapter);

        if (TextUtils.isEmpty(gender))
            gender = "male";
        for (int i = 0; i < genderList.size(); i++) {
            String sName = genderList.get(i).getName().trim();
            if (sName.equalsIgnoreCase(gender)) {
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

        if (TextUtils.isEmpty(mName))
            mName = "single";
        for (int i = 0; i < maritalList.size(); i++) {
            String sName = maritalList.get(i).getName().trim();
            if (sName.equalsIgnoreCase(mName)) {
                spnMaterial.setSelection(i);
                break;
            }
        }

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

    private void initSalutation() {
        for (int i = 0; i < salutationList.size(); i++) {
            String stateName = salutationList.get(i).getName().trim();
            stateName = stateName.replaceAll("[-+.^:,]", "");
            String stateId = salutationList.get(i).getCode();
            salutations.add(new String5WithTag(stateName, stateId));
        }

        ArrayAdapter<String5WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, salutations);
        spnSalutation.setAdapter(adapter);

        if (TextUtils.isEmpty(salutation))
            salutation = "Mr";
        for (int i = 0; i < salutationList.size(); i++) {
            String ssName = salutationList.get(i).getName().trim();
            if (ssName.equalsIgnoreCase(salutation)) {
                spnSalutation.setSelection(i);
                break;
            }
        }

        spnSalutation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String5WithTag stringWithTag = (String5WithTag) parent.getItemAtPosition(position);
                salutationId = (String) stringWithTag.tag;
                sName = stringWithTag.string;
                genderList.clear();
                Gender gender = new Gender();
                if (sName.equalsIgnoreCase("miss")
                        ||sName.equalsIgnoreCase("mrs")){
                    gender.setName("Female");
                    gender.setCode("Female");
                    genderList.add(gender);
                }else {
                    gender.setName("Male");
                    gender.setCode("Male");
                    genderList.add(gender);
                }
                initGender();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initValues() {

        firstName = AppUtils.trimString(edtFirstName);
        lastName = AppUtils.trimString(edtLastName);
        email = AppUtils.trimString(edtEmail);
        mobile = AppUtils.trimString(edtMobile);

        dob = AppUtils.trimString(edtDOB);
        panCard = AppUtils.trimString(edtPan);
        gst = AppUtils.trimString(edtGst);
        address1 = AppUtils.trimString(edtAddress1);
        address2 = AppUtils.trimString(edtAddress2);
        address3 = AppUtils.trimString(edtAddress3);
        orgName = AppUtils.trimString(edtOrgName);
        additionalMobile = AppUtils.trimString(edtAdditionalMobile);
        nationality = "Indian";

        if (spnGender.getSelectedItem() != null)
            gender = spnGender.getSelectedItem().toString();
        if (spnMaterial.getSelectedItem() != null)
            material = spnMaterial.getSelectedItem().toString();
        if (spnOccupation.getSelectedItem() != null)
            occupation = spnOccupation.getSelectedItem().toString();
        if (spnSalutation.getSelectedItem() != null)
            salutation = spnSalutation.getSelectedItem().toString();

        regAddress1 = edtRAddress1.getText().toString();
        regAddress2 = edtRAddress2.getText().toString();
        regAddress3 = edtRAddress3.getText().toString();

        regPinCode = edtRPinCode.getText().toString();

        if (regCheck.isChecked())
            regAddress = "yes";
        else regAddress = "no";

    }

    private boolean checkValidation() {
        initValues();
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
        Pattern addRegex = Pattern.compile("[$&+,:;=\\\\?@#|'<>.^*()%!]");

        if (isIndividual) {
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
                    edtDOB.setError("Invalid Date");// 12/11
                    edtDOB.requestFocus();
                    Toast.makeText(mContext, "1", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                edtDOB.setError("Field can not be empty");
                edtDOB.requestFocus();
                Toast.makeText(mContext, "2", Toast.LENGTH_SHORT).show();
                return false;
            }

        } else {
            if (TextUtils.isEmpty(orgName)) {
                edtOrgName.setError("Field can not be empty");
                edtOrgName.requestFocus();
                return false;
            }

            gender = material = dob = "";
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
            edtMobile.setError("Field can not be empty");
            edtMobile.requestFocus();
            return false;
        } else if (!(AppUtils.isValidMobile(mobile))) {
            edtMobile.setError("Invalid Phone Number");
            edtMobile.requestFocus();
            return false;
        }
        if (!TextUtils.isEmpty(panCard) || (!isIndividual) || gross > 49999) {
            if (panCard.length() != 10) {
                edtPan.setError("Invalid PAN card Number");
                edtPan.requestFocus();
                Toast.makeText(mContext, "5 Alphabet 4 Digit 1 Character",
                        Toast.LENGTH_SHORT).show();
                return false;
            } else if (AppUtils.isValidPAN(panCard)) {
                edtPan.setError("Invalid PAN card Number");
                edtPan.requestFocus();
                Toast.makeText(mContext, "5 Alphabet 4 Digit 1 Character",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (TextUtils.isEmpty(pincode) || mPincode.length() != 6) {
            edtPincode.setError("Invalid PIN code");
            edtPincode.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(city)) {
            edtPincode.setError("City not connected with this pincode");
            return false;
        } else if (TextUtils.isEmpty(stateId)) {
            edtPincode.setError("State not connected with this pincode");
            return false;
        }

        if (TextUtils.isEmpty(address1)) {
            edtAddress1.setError("Field can not be empty");
            edtAddress1.requestFocus();
            return false;
        } else if (address1.length() < 3) {
            edtAddress1.setError("Minimum 3 Characters");
            edtAddress1.requestFocus();
            return false;
        }
        if (addRegex.matcher(address1).find()) {
            edtAddress1.setError("Special Characters are not allowed");
            edtAddress1.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(address2)) {
            edtAddress2.setError("Field can not be empty");
            edtAddress2.requestFocus();
            return false;
        } else if (address2.length() < 10) {
            edtAddress2.setError("Minimum 10 Characters");
            edtAddress2.requestFocus();
            return false;
        }

        if (addRegex.matcher(address2).find()) {
            edtAddress2.setError("Special Characters are not allowed");
            edtAddress2.requestFocus();
            return false;
        }

        if (!TextUtils.isEmpty(address3)) {
            if (addRegex.matcher(address3).find()) {
                edtAddress3.setError("Special Characters are not allowed");
                edtAddress3.requestFocus();
                return false;
            }
        }

        if (companyName.equalsIgnoreCase("hdfc")) {
            if (!AppUtils.validateAddress(address1)) {
                edtAddress1.setError("Special Character Not Allowed");
                edtAddress1.requestFocus();
                return false;
            } else if (!AppUtils.validateAddress(address2)) {
                edtAddress2.setError("Special Character Not Allowed");
                edtAddress2.requestFocus();
                return false;
            } else if (!AppUtils.validateAddress(address3)) {
                edtAddress3.setError("Special Character Not Allowed");
                edtAddress3.requestFocus();
                return false;
            }
        }

        if (!regCheck.isChecked()) {
            if (TextUtils.isEmpty(regAddress1)) {
                edtRAddress1.setError("Field can not be empty");
                edtRAddress1.requestFocus();
                return false;
            }
            if (TextUtils.isEmpty(regAddress2)) {
                edtRAddress2.setError("Field can not be empty");
                edtRAddress2.requestFocus();
                return false;
            }

            if (TextUtils.isEmpty(regPinCode)) {
                edtRPinCode.setError("Field can not be empty");
                edtRPinCode.requestFocus();
                return false;
            } else if (mPincode.length() != 6) {
                edtRPinCode.setError("Invalid PIN code");
                edtRPinCode.requestFocus();
                return false;
            }
        } else {
            regAddress1 = address1;
            regAddress2 = address2;
            regAddress3 = address3;
            regPinCode = pincode;
        }

        if (isIndividual) {
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
        }

        firstName = firstName.replaceAll("\\s{2,}", " ").trim();
        lastName = lastName.replaceAll("\\s{2,}", " ").trim();

        return true;
    }

    public void onRegAddressClick(View view) {
        if (regCheck.isChecked())
            findViewById(R.id.rRL).setVisibility(View.GONE);
        else findViewById(R.id.rRL).setVisibility(View.VISIBLE);
    }

    public void onOwnerDetailSubmit(View view) {
        if (checkValidation())
            submitOwner();
    }

    @Override
    public void onResponse(Object response) {
        try {
            if (response instanceof Owner) {
                Owner owner = (Owner) response;
                if (owner.getSuccess().equalsIgnoreCase("1")) {

                    if (companyName.equalsIgnoreCase("iffco") ||
                            companyName.equalsIgnoreCase("sbi") ||
                            companyName.equalsIgnoreCase("future")) {

                        gender = gName;
                        material = mName;
                        occupation = oName;
                        salutation = sName;
                    }
                    Intent intent = new Intent(mContext, VehicleDetailsActivity.class);
                    if (isIndividual)
                        mBundle.putString(AppUtils.OWNER_NAME, salutation + " " + firstName + " "
                                + lastName);
                    else mBundle.putString(AppUtils.OWNER_NAME, salutation + " " + orgName);
                    mBundle.putString(AppUtils.OWNER_ADDRESS, address1 + " " + address2 + " "
                            + address3);
                    mBundle.putString(AppUtils.OWNER_EMAIL, email);
                    mBundle.putString(AppUtils.OWNER_PHONE, mobile);
                    mBundle.putString(AppUtils.PH_MOBILE, mobile);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }
            }

            if (response instanceof ProposalPincode) {
                ProposalPincode cResponse = (ProposalPincode) response;
                pincode = cResponse.getPincode();
                stateId = cResponse.getStateId();
                city = cResponse.getCityId();
            }

            if (response instanceof OccupationStatus) {
                OccupationStatus cResponse = (OccupationStatus) response;
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
            if (response instanceof Marital) {
                Marital cResponse = (Marital) response;
                if (cResponse.getStatus().equalsIgnoreCase("1")
                        && cResponse.getMaritalStatus() != null) {
                    maritalList.clear();
                    maritalLists.clear();
                    maritalList = cResponse.getMaritalStatus();
                    if (maritalList.size() > 0) {
                        initMarital();
                    }
                }
            }
            if (response instanceof GenderStatus) {
                GenderStatus cResponse = (GenderStatus) response;
                if (cResponse.getStatus().equalsIgnoreCase("1")
                        && cResponse.getGender() != null) {
                    genderList.clear();
                    genders.clear();
                    genderList = cResponse.getGender();
                    if (genderList.size() > 0) {
                        initGender();
                    }
                }
            }
            if (response instanceof SalutationStatus) {
                SalutationStatus cResponse = (SalutationStatus) response;
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

            if (response instanceof GetOwner) {
                GetOwner oResponse = (GetOwner) response;
                if (oResponse.getSuccess().equalsIgnoreCase("1")) {

                    salutation = oResponse.getSalutation();
                    firstName = oResponse.getFirstName() + " " + oResponse.getMiddleName();
                    lastName = oResponse.getLastName();
                    gender = oResponse.getGender();
                    mName = oResponse.getMaritalStatus();
                    dob = oResponse.getDob();
                    email = oResponse.getEmailProposal();
                    mobile = oResponse.getMobileProposal();
                    additionalMobile = oResponse.getAdditionalContact();
                    panCard = oResponse.getPanNo();
                    stateName = oResponse.getState();
                    cityName = oResponse.getCity();
                    fPinCode = oResponse.getPincode();
                    address1 = oResponse.getAddress1();
                    address2 = oResponse.getAddress2();
                    address3 = oResponse.getAddress3();
                    gst = oResponse.getGstNo();
                    oName = oResponse.getOccupation();

                    if (!TextUtils.isEmpty(fPinCode) && fPinCode.length() == 6) {
                        edtPincode.setText(fPinCode);
                        mPincode = regPinCode = fPinCode;
                    }

                    if (!TextUtils.isEmpty(firstName))
                        edtFirstName.setText(firstName.trim());

                    if (!TextUtils.isEmpty(lastName))
                        edtLastName.setText(lastName);

                    if (!TextUtils.isEmpty(email))
                        edtEmail.setText(email);

                    if (!TextUtils.isEmpty(mobile))
                        edtMobile.setText(mobile);

                    if (!TextUtils.isEmpty(additionalMobile))
                        edtAdditionalMobile.setText(additionalMobile);

                    if (!TextUtils.isEmpty(dob)) {
                        dob = AppUtils.ddMMYYYY(dob);
                        edtDOB.setText(dob);
                    }
                    if (!TextUtils.isEmpty(address1))
                        edtAddress1.setText(address1);
                    if (!TextUtils.isEmpty(address2))
                        edtAddress2.setText(address2);
                    if (!TextUtils.isEmpty(address3))
                        edtAddress3.setText(address3);
                    if (!TextUtils.isEmpty(panCard))
                        edtPan.setText(panCard);
                    if (!TextUtils.isEmpty(gst))
                        edtGst.setText(gst);

                    if (!isIndividual) {
                        orgName = oResponse.getFirstName();
                        edtOrgName.setText(orgName);
                    }
                }
            }
            progressdialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void submitOwner() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {

                if (!isIndividual) {
                    salutation = "MS";
                    formattedDob = "";
                }
                if (isOccupationHide)
                    occupation = "Other";

                if (companyName.equalsIgnoreCase("iffco") ||
                        companyName.equalsIgnoreCase("sbi") ||
                        companyName.equalsIgnoreCase("future")) {

                    gender = genderId;
                    material = maritalId;
                    occupation = occupationId;
                    salutation = salutationId;
                }

                ApiManager.getInstance().ownerDetailSubmit(mContext, agentId, userId, quotationId,
                        salutation, firstName, lastName, formattedDob, orgName, regPinCode, regCity,
                        regAddress3, regAddress2, regAddress1, regAddress, gst, panCard, pincode,
                        stateId, city, address3, address2.trim(), address1.trim(), nationality,
                        additionalMobile, email, mobile, occupation, material, gender);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }


    public void getOccupation() {
        if (AppUtils.isOnline(mContext)) {
            try {
                ApiManager.getInstance().getOccupation(mContext, "Occupation", companyName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getGender() {
        if (AppUtils.isOnline(mContext)) {
            try {
                ApiManager.getInstance().getGender(mContext, "gender", companyName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getMarital() {
        if (AppUtils.isOnline(mContext)) {
            try {
                ApiManager.getInstance().getMarital(mContext, "marital_status", companyName);
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
                ApiManager.getInstance().getSalutation(mContext, "salutation", companyName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getPinCode() {
        if (AppUtils.isOnline(mContext)) {
            try {
                ApiManager.getInstance().getMasterPincode(mContext, companyName, city, stateId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void fetchOwner() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                ApiManager.getInstance().fetchOwner(mContext, quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ref_share, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_refresh:
                onRefresh();
                return true;
            case R.id.action_share:
                onShare();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onShare() {
        String cName = companyName;
        if (!TextUtils.isEmpty(companyName))
            cName = companyName.toLowerCase();
        final String link = String.format("%s%s%s%s", AppUtils.PROPOSAL_LINK, cName, "/",
                quotationId);

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context
                .CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Square", link);

        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            Toast.makeText(mContext, "Link Copied", Toast.LENGTH_SHORT).show();
        }
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Square Insurance");

            shareIntent.putExtra(Intent.EXTRA_TEXT, link);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
    }

    private void onRefresh() {
        getOccupation();
        getGender();
        getMarital();
        getSalutation();
    }

    public void onDateClick(View view) {
        AppUtils.checkSoftKeyboard(this);
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.add(Calendar.YEAR, -18);
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                R.style.datepicker, (view1, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);

            edtDOB.setText(dateFormatter.format(newDate.getTime()));
            edtDOB.setError(null);
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
