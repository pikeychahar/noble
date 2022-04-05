package com.dmw.noble.activity_motor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.adaptor.MyRecyclerViewAdapter;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.model.Category;
import com.dmw.noble.model.PCVGCVList;
import com.dmw.noble.model.SbiData;
import com.dmw.noble.model.SbiMisd;
import com.dmw.noble.model.VahanData;
import com.dmw.noble.utils.AppUtils;
import com.dmw.noble.utils.SearchableSpinner;
import com.dmw.noble.utils.String2WithTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Prahalad Kumar Chahar
 */

public class BikeRegistrationActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener, MyRecyclerViewAdapter.ItemClickListener {

    EditText edt1, edt2, edt3, edt4;
    Context mContext;
    SharedPreferences preferences;
    String registrationNumber, newVehicle, rtoName, vehicleType, pcvCompany, pcvType, sbiCode;
    Bundle mBundle;
    TextView btnDone;
    Spinner spnPcvCompany;
    SearchableSpinner spnMiscType;
    TextView txtInsCom1;
    List<SbiData> sbiDataList = new ArrayList<>();
    List<String2WithTag> sbiList = new ArrayList<>();
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;
    ArrayList<Category> gcvList = new ArrayList<>();
    ArrayList<Category> pcvList = new ArrayList<>();
    int numberOfColumns = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_registration);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        mBundle = new Bundle();
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        vehicleType = preferences.getString(AppUtils.VEHICLE_TYPE, "");

        edt1 = findViewById(R.id.edt1);
        edt2 = findViewById(R.id.edt2);
        edt3 = findViewById(R.id.edt3);
        edt4 = findViewById(R.id.edt4);
        btnDone = findViewById(R.id.btnDon);
        spnPcvCompany = findViewById(R.id.spnPcvCompany);
        spnMiscType = findViewById(R.id.spnMiscType);

        txtInsCom1 = findViewById(R.id.txtInsCom1);
        recyclerView = findViewById(R.id.rcGcv);
        pcvType = "car";

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.setCancelable(false);

        spnMiscType.setTitle("Misc-D type");

        pcvList = new Gson().fromJson(preferences.getString(AppUtils.PCV_LIST, ""),
                new TypeToken<List<Category>>() {
                }.getType());
        gcvList = new Gson().fromJson(preferences.getString(AppUtils.GCV_LIST, ""),
                new TypeToken<List<Category>>() {
                }.getType());

        if (pcvList == null || pcvList.size() == 0 || gcvList == null || gcvList.size() == 0) {
            pcvList = new ArrayList<>();
            gcvList = new ArrayList<>();
            getPCV_GCV();
        }

        final List<String> companyList = new ArrayList<>();
        AppUtils.showToast(mContext, "...." + vehicleType);
        switch (vehicleType) {
            case "1":
                this.setTitle("Bike Insurance");
                btnDone.setText("Got A New Bike");
                break;
            case "2":
                this.setTitle("Car Insurance");
                btnDone.setText("Got A New Car");
                break;
            case "3":
                findViewById(R.id.rlPcv).setVisibility(View.VISIBLE);
                findViewById(R.id.rlPCVF).setVisibility(View.VISIBLE);
                this.setTitle("Commercial Insurance");
                btnDone.setText("Got A New PCV Vehicle");
                companyList.add("Shriram");
                companyList.add("Digit");
                companyList.add("Reliance");
                companyList.add("Bajaj");
                companyList.add("ICICI");
                recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
                adapter = new MyRecyclerViewAdapter(this, pcvList);
                adapter.setClickListener(this);
                recyclerView.setAdapter(adapter);
                if (pcvList.size() > 0)
                    pcvType = pcvList.get(0).getCode();

                break;
            case "4":
                this.setTitle("Commercial Insurance");
                findViewById(R.id.rlPcv).setVisibility(View.VISIBLE);
                findViewById(R.id.rlPCVF).setVisibility(View.VISIBLE);
                btnDone.setText("Got A New Commercial Vehicle");
                companyList.add("Shriram");
                companyList.add("Digit");
                companyList.add("Tata");
                companyList.add("Bajaj");
                companyList.add("SBI");
                companyList.add("Reliance");
                companyList.add("ICICI");
                companyList.add("HDFC");
                txtInsCom1.setText("Select GCV type");
                recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
                adapter = new MyRecyclerViewAdapter(this, gcvList);
                adapter.setClickListener(this);
                recyclerView.setAdapter(adapter);

                if (gcvList.size() > 0)
                    pcvType = gcvList.get(0).getCode();
                break;
            case "5":
                getMiscDData();
                findViewById(R.id.rlPcv).setVisibility(View.VISIBLE);
                findViewById(R.id.llMiscView).setVisibility(View.VISIBLE);
                this.setTitle("Misc-D Insurance");
                btnDone.setText("Got A New Vehicle");
                companyList.add("SBI");
                companyList.add("ICICI");
                txtInsCom1.setText("Select Misc-D type");
                break;
            default:
                break;
        }

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                mContext, android.R.layout.simple_spinner_dropdown_item, companyList);

        spnPcvCompany.setAdapter(spinnerArrayAdapter);
        edt1.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(2)});
        edt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 1) {
                    edt2.requestFocus();
                    openKeyboard();
                }
            }
        });

        edt2.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(2)
        });

        edt2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 1) {
                    edt3.requestFocus();
                    openKeyboard();
                }

            }
        });

        edt3.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(3)
        });

        edt3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().length() > 2) {
                    edt4.requestFocus();
                    openKeyboard();
                }
            }
        });

        edt4.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(4)});
        edt4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                if (editable.toString().length() > 3) {
                    removeKeyboard();
                }
            }
        });

        edt2.setOnFocusChangeListener((v, hasFocus) -> {
            String s1 = edt1.getText().toString().trim();
            if (TextUtils.isEmpty(s1))
                edt1.requestFocus();
        });
        edt3.setOnFocusChangeListener((v, hasFocus) -> {
            String s1 = edt1.getText().toString().trim();
            String s2 = edt2.getText().toString().trim();
            if (TextUtils.isEmpty(s1)) {
                edt1.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(s2)) {
                edt2.requestFocus();
            }
        });

        edt4.setOnFocusChangeListener((v, hasFocus) -> {
            String s1 = edt1.getText().toString().trim();
            String s2 = edt2.getText().toString().trim();
            String s3 = edt3.getText().toString().trim();
            if (TextUtils.isEmpty(s1)) {
                edt1.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(s2)) {
                edt2.requestFocus();
            }
        });

        // PCV case
        spnPcvCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (vehicleType.equals("5")) {
                    if (position == 0)
                        findViewById(R.id.rlPCVF).setVisibility(View.VISIBLE);
                    else findViewById(R.id.rlPCVF).setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void openKeyboard() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    private void removeKeyboard() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
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

    public void onGetDetail(View view) {
        AppUtils.checkSoftKeyboard(this);
        rtoName = edt1.getText().toString().trim() + edt2.getText().toString().trim();

        String rto = edt1.getText().toString();
        String rtoCode = edt2.getText().toString();

        if (rto.length() != 2 || TextUtils.isEmpty(rto)) {
            Toast.makeText(mContext, "Invalid RTO", Toast.LENGTH_SHORT).show();
            edt1.requestFocus();
        } else if (rtoCode.length() != 2 || TextUtils.isEmpty(rtoCode)) {
            Toast.makeText(mContext, "Invalid RTO", Toast.LENGTH_SHORT).show();
            edt2.requestFocus();
        } else {
            registrationNumber = edt1.getText().toString() + edt2.getText().toString()
                    + edt3.getText().toString() + edt4.getText().toString();

            if (!TextUtils.isEmpty(registrationNumber))
                registrationNumber = registrationNumber.replace(" ", "");

            switch (vehicleType) {
                case "3":
                case "5":
                case "4":
                    pcvCompany = spnPcvCompany.getSelectedItem().toString().toLowerCase();
                    break;
                default:
                    pcvCompany = "";
                    break;
            }

            fetchQuote();
        }
    }


    public void onGetDetailNew(View view) {

        switch (vehicleType) {
            case "3":
            case "5":
            case "4":
                pcvCompany = spnPcvCompany.getSelectedItem().toString().toLowerCase();
                break;
            default:
                pcvCompany = "";
                break;
        }
        Intent intent = new Intent(mContext, RegistrationDetailedActivity.class);
        newVehicle = "new_gadi";
        mBundle.putString(AppUtils.NEW_VEHICLE, newVehicle);
        registrationNumber = "";
        mBundle.putString(AppUtils.PCV_COMPANY, pcvCompany);
        mBundle.putString(AppUtils.PCV_TYPE, pcvType);
        mBundle.putString(AppUtils.REGISTRATION_NUMBER, registrationNumber);
        mBundle.putString(AppUtils.SBI_GCV_CODE, sbiCode);
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    private void initRelation() {
        for (int i = 0; i < sbiDataList.size(); i++) {
            String stateName = sbiDataList.get(i).getName().trim();
            String stateId = sbiDataList.get(i).getCode();
            sbiList.add(new String2WithTag(stateName, stateId));
        }

        ArrayAdapter<String2WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, sbiList);
        spnMiscType.setAdapter(adapter);

        spnMiscType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                sbiCode = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onResponse(Object response) {
        try {
            if (response instanceof SbiMisd) {
                SbiMisd nResponse = (SbiMisd) response;
                if (nResponse.getStatus().equalsIgnoreCase("1")) {
                    sbiDataList.clear();
                    sbiList.clear();
                    sbiDataList.addAll(nResponse.getSbiData());
                    initRelation();
                }
            }

            if (response instanceof PCVGCVList) {
                PCVGCVList nResponse = (PCVGCVList) response;
                if (nResponse.getStatus() == 1) {
                    pcvList.clear();
                    gcvList.clear();
                    if (nResponse.getCategory().size() > 0) {
                        for (int i = 0; i < nResponse.getCategory().size(); i++) {
                            Category cg = nResponse.getCategory().get(i);
                            if (cg.getType().equals("pcv"))
                                pcvList.add(cg);
                            else gcvList.add(cg);
                        }

                        if (gcvList.size() > 0)
                            pcvType = gcvList.get(0).getCode();
                        else pcvType = pcvList.get(0).getCode();

                        preferences.edit().putString(AppUtils.PCV_LIST,
                                new Gson().toJson(pcvList)).apply();

                        preferences.edit().putString(AppUtils.GCV_LIST,
                                new Gson().toJson(gcvList)).apply();
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            if (response instanceof VahanData) {
                VahanData nResponse = (VahanData) response;

                mBundle.putString(AppUtils.REGISTRATION_NUMBER, registrationNumber);
                mBundle.putString(AppUtils.NEW_VEHICLE, "");
                mBundle.putString(AppUtils.PCV_TYPE, pcvType);
                mBundle.putString(AppUtils.PCV_COMPANY, pcvCompany);
                mBundle.putString(AppUtils.RTO_NAME, rtoName);
                mBundle.putString(AppUtils.SBI_GCV_CODE, sbiCode);

                switch (nResponse.getStatus()) {
                    case "1": {
                        String make = nResponse.getMake();
                        String modelName = nResponse.getModel();
                        String variantName = nResponse.getVariant();
                        String fuel = nResponse.getFuelType();
                        String mYear = nResponse.getRegYear();
                        String previousInsurer = nResponse.getInsurer();

                        mBundle.putString(AppUtils.MAKE, make);
                        mBundle.putString(AppUtils.MODEL, modelName);
                        mBundle.putString(AppUtils.VARIANT, variantName);
                        mBundle.putString(AppUtils.REGISTRATION_YEAR, mYear);
                        mBundle.putString(AppUtils.INSURER, previousInsurer);
                        mBundle.putString(AppUtils.FUEL_TYPE, fuel);

                        Intent intent = new Intent(mContext, VahanRegActivity.class);
                        intent.putExtras(mBundle);
                        startActivity(intent);
                        break;
                    }
                    case "0":
                        AppUtils.showToast(mContext, "" + nResponse.getMessage());
                        break;
                    case "2": {
                        Intent intent = new Intent(mContext, RegistrationDetailedActivity.class);
                        intent.putExtras(mBundle);
                        startActivity(intent);
                        break;
                    }
                }
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMiscDData() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getMiscDData(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void fetchQuote() {
        if (AppUtils.isOnline(mContext)) {
            try {
                progressDialog.show();
                UserManager.getInstance().fetchVahanData(mContext, registrationNumber, vehicleType);
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }
        }
    }

    public void getPCV_GCV() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getPCV_GCV(mContext);
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }
        }
    }


    @Override
    public void onItemClick(int position) {
        try {
            if (vehicleType.equals("3"))
                pcvType = "" + pcvList.get(position).getCode();

            if (vehicleType.equals("4"))
                pcvType = "" + pcvList.get(position).getCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
