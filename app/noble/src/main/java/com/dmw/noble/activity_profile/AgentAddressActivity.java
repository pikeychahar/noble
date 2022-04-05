package com.dmw.noble.activity_profile;

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
import android.widget.Toast;

import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.activity_pos.UploadDocumentActivity;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.model.Account;
import com.dmw.noble.model.AccountType;
import com.dmw.noble.model.Bank;
import com.dmw.noble.model.BankList;
import com.dmw.noble.model.City;
import com.dmw.noble.model.CityList;
import com.dmw.noble.model.CommonResponse;
import com.dmw.noble.model.State;
import com.dmw.noble.model.StateList;
import com.dmw.noble.model_pos.NewAgent;
import com.dmw.noble.utils.AppUtils;
import com.dmw.noble.utils.String3WithTag;
import com.dmw.noble.utils.String4WithTag;
import com.dmw.noble.utils.StringWithTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AgentAddressActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {
    private Context mContext;
    private com.toptoche.searchablespinnerlibrary.SearchableSpinner spnState, spnAccountType,
            spnCities, spnBank;
    private EditText edtAddress1, edtAddress2, edtAddress3, edtPinCode,
            edtBranchName, edtAccountNumber, edtIFSC;
    private String address1, address2, address3, city, stateName, pincode, bankName, branchName,
            accountNumber, ifscCode, accountType;
    private List<State> stateList = new ArrayList<>();
    private List<BankList> bankList = new ArrayList<>();
    private List<City> citiesList = new ArrayList<>();
    private List<AccountType> accountTypeList = new ArrayList<>();
    private String firstName, lastName, dateOfBirth, emailAddress, mobile, alternateMobile, panCard,
            aadharNumber, password, confirmPassword, reference, gender, qualification;
    private Bundle mBundle;
    private String stateId, cityId, bankId, accTypeId;
    private ProgressDialog progressdialog;
    private List<StringWithTag> state = new ArrayList<>();
    private List<StringWithTag> bank = new ArrayList<>();
    private List<String4WithTag> cities = new ArrayList<>();
    private List<String3WithTag> aType = new ArrayList<>();
    private SharedPreferences preferences, posPreferences;
    String addedBy, addedById, referenceId, permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_address);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        posPreferences = getSharedPreferences("pos", MODE_PRIVATE);
        mBundle = getIntent().getBundleExtra("personal");

        addedBy = preferences.getString(AppUtils.USER_TYPE, "");
        addedById = preferences.getString(AppUtils.PRIMARY_ID, "");

        referenceId = preferences.getString(AppUtils.REFERENCE_ID, "");
        permission = preferences.getString(AppUtils.POS_PERMISSION, "");

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");

        if (mBundle != null) {
            firstName = mBundle.getString("firstName");
            lastName = mBundle.getString("lastName");
            dateOfBirth = mBundle.getString("dateOfBirth");
            emailAddress = mBundle.getString("emailAddress");
            mobile = mBundle.getString("mobile");
            alternateMobile = mBundle.getString("alternateMobile");
            panCard = mBundle.getString("panCard");
            aadharNumber = mBundle.getString("aadharNumber");
            password = mBundle.getString("password");
            reference = mBundle.getString("reference");
            gender = mBundle.getString("gender");
            qualification = mBundle.getString("qualification");
        }

        if (!TextUtils.isEmpty(permission) && (permission.equals("1") || permission.equals("2")))
            reference = referenceId;

        edtAddress1 = findViewById(R.id.edtAddress1);
        edtAddress2 = findViewById(R.id.edtAddress2);
        edtAddress3 = findViewById(R.id.edtAddress3);
        spnState = findViewById(R.id.edtState);
        edtPinCode = findViewById(R.id.edtPinCode);
        spnCities = findViewById(R.id.edtCity);

        spnBank = findViewById(R.id.edtBankName);
        edtBranchName = findViewById(R.id.edtBranchName);
        edtAccountNumber = findViewById(R.id.edtBankAccount);
        edtIFSC = findViewById(R.id.edtIfsc);

        spnAccountType = findViewById(R.id.accountType);
        stateList = UserManager.getInstance().getStateList();
        bankList = UserManager.getInstance().getBankLists();
        spnState.setTitle("Select State");
        spnState.setPositiveButton("OK");

        spnCities.setTitle("Select City");
        spnCities.setPositiveButton("OK");

        spnBank.setTitle("Select Bank");
        spnBank.setPositiveButton("OK");
        spnAccountType.setTitle("Select Account Type");
        spnAccountType.setPositiveButton("OK");

        getBankList();

        if (stateList.size() > 0) {
            initState();
        } else getStates();
        if (bankList.size() > 0) {
            initBank();
        } else getBankList();
        getAccType();
    }

    private void initBank() {
        for (int i = 0; i < bankList.size(); i++) {
            String bankName = bankList.get(i).getBankName();
            String bankId = bankList.get(i).getBankId();
            bank.add(new StringWithTag(bankName, bankId));
        }

        ArrayAdapter<StringWithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, bank);
        spnBank.setAdapter(adapter);

        spnBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag stringWithTag = (StringWithTag) parent.getItemAtPosition(position);
                bankId = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initState() {
        for (int i = 0; i < stateList.size(); i++) {
            String stateName = stateList.get(i).getStateName();
            String stateId = stateList.get(i).getStateName();
            state.add(new StringWithTag(stateName, stateId));
        }

        ArrayAdapter<StringWithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, state);
        spnState.setAdapter(adapter);

        spnState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag stringWithTag = (StringWithTag) parent.getItemAtPosition(position);
                stateId = (String) stringWithTag.tag;
                if (!TextUtils.isEmpty(stateId))
                    getCities(stateId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initAccountType() {
        for (int i = 0; i < accountTypeList.size(); i++) {
            String accType = accountTypeList.get(i).getAccountTypesName();
            String accId = accountTypeList.get(i).getAccountTypesId();
            aType.add(new String3WithTag(accType, accId));
        }

        ArrayAdapter<String3WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, aType);
        spnAccountType.setAdapter(adapter);

        spnAccountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String3WithTag stringWithTag = (String3WithTag) parent.getItemAtPosition(position);
                accTypeId = (String) stringWithTag.tag;
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

    public void onPrevious(View view) {
        onBackPressed();
    }

    public void onNextClick(View view) {
        if (checkValidation())
            createAgent();
    }

    public boolean checkValidation() {
        getFormValues();
        if (TextUtils.isEmpty(address1)) {
            edtAddress1.setError("Field can not be empty");
            edtAddress1.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(address2)) {
            edtAddress2.setError("Field can not be empty");
            edtAddress2.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(pincode)) {
            edtPinCode.setError("Field can not be empty");
            edtPinCode.requestFocus();
            return false;
        } else if (pincode.length() != 6) {
            edtPinCode.setError("Invalid PIN code");
            edtPinCode.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(branchName)) {
            edtBranchName.setError("Field can not be empty");
            edtBranchName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(accountNumber)) {
            edtAccountNumber.setError("Field can not be empty");
            edtAccountNumber.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(ifscCode)) {
            edtIFSC.setError("Field can not be empty");
            edtIFSC.requestFocus();
            return false;
        }

        return true;
    }

    public void getFormValues() {
        address1 = edtAddress1.getText().toString();
        address2 = edtAddress2.getText().toString();
        address3 = edtAddress3.getText().toString();
        city = spnCities.getSelectedItem().toString();
        stateName = spnState.getSelectedItem().toString();
        pincode = edtPinCode.getText().toString();
        bankName = spnBank.getSelectedItem().toString();
        branchName = edtBranchName.getText().toString();
        accountNumber = edtAccountNumber.getText().toString();
        ifscCode = edtIFSC.getText().toString();
//        accountType = spnAccountType.getSelectedItem().toString();
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof NewAgent) {
            NewAgent newAgent = (NewAgent) response;
            if (newAgent.getSuccess().equals("1")) {
                String agent_id = newAgent.getId();
                String agent_name = newAgent.getName();
                String agent_mobile = newAgent.getMobile();
                String agent_email = newAgent.getEmail();

                preferences.edit().putString(AppUtils.AGENT_ID, agent_id).apply();
                preferences.edit().putString(AppUtils.AGENT_NAME, agent_name).apply();
                preferences.edit().putString(AppUtils.AGENT_MOBILE, agent_mobile).apply();
                preferences.edit().putString(AppUtils.AGENT_EMAIL, agent_email).apply();
                mBundle.putString(AppUtils.AGENT_ID, agent_id);
                Intent intent = new Intent(mContext, UploadDocumentActivity.class);
                intent.putExtra("mBundle", mBundle);
                posPreferences.edit().clear().apply();
                startActivity(intent);
            }
        }
        if (response instanceof CityList) {
            CityList cityList = (CityList) response;
            if (cityList.getMessage().equalsIgnoreCase("ok")) {
                citiesList.clear();
                cities.clear();
                citiesList = cityList.getCity();
                if (citiesList.size() > 0) {
                    initCity();
                }
            }
        }
        if (response instanceof Bank) {
            Bank stateResponse = (Bank) response;
            if (stateResponse.getMessage().equalsIgnoreCase("ok")) {
                bankList = stateResponse.getBankList();
                if (bankList.size() > 0) {
                    initBank();
                }
            }
        }
        if (response instanceof StateList) {
            StateList stateResponse = (StateList) response;
            if (stateResponse.getMessage().equalsIgnoreCase("ok")) {
                stateList = stateResponse.getState();
                if (stateList.size() > 0) {
                    initState();
                }
            }
        }
        if (response instanceof Account) {
            Account stateResponse = (Account) response;
            if (stateResponse.getMessage().equalsIgnoreCase("ok")) {
                accountTypeList = stateResponse.getAccountTypes();
                if (accountTypeList.size() > 0) {
                    initAccountType();
                }
            }
        }
        if (response instanceof CommonResponse) {
            CommonResponse commonResponse = (CommonResponse) response;
            if (commonResponse.getSuccess().equals("0"))
                Toast.makeText(mContext, "" + commonResponse.getMsg(), Toast.LENGTH_LONG).show();
        }
        progressdialog.dismiss();
    }

    public void createAgent() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().createAgent(mContext, firstName, lastName, emailAddress,
                        mobile, password, alternateMobile, gender, dateOfBirth, qualification,
                        panCard, aadharNumber, address1, address2, address3, stateId, cityId,
                        pincode, bankId, branchName, accTypeId, accountNumber, ifscCode, addedBy,
                        addedById, reference);
            } catch (Exception e) {
                e.printStackTrace();

            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }

    public void getStates() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().getState(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }

    public void getCities(String state_id) {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getCity(mContext, state_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getBankList() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getBanks(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getAccType() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getAccountType(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void initCity() {
        if (citiesList.size() > 0) {
            for (int i = 0; i < citiesList.size(); i++) {
                String cityName = citiesList.get(i).getCityName();
                cityId = citiesList.get(i).getCityName();
                cities.add(new String4WithTag(cityName, cityId));
            }

            ArrayAdapter<String4WithTag> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_dropdown_item_1line, cities);
            spnCities.setAdapter(adapter);

            spnCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String4WithTag string4WithTag = (String4WithTag) parent.getItemAtPosition(position);
                    cityId = (String) string4WithTag.tag;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }
}
