package com.square.pos.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.square.pos.R;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.UserManager;
import com.square.pos.model.BasicResponse;
import com.square.pos.model.Datum;
import com.square.pos.model.NonMotorList;
import com.square.pos.utils.AppUtils;
import com.square.pos.utils.String2WithTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CorporateActivity extends AbstractActivity implements onRequestCompleteCallBackListener {
    EditText edtOrgName, edtUserName, edtUserMobile, edtUserEmail, edtCity, edtRemark;
    com.toptoche.searchablespinnerlibrary.SearchableSpinner spnCorporateIns;

    Context mContext;
    String insuranceType, companyName, name, mobile, email, city, remarks, insId;
    List<Datum> insList = new ArrayList<>();
    List<String2WithTag> insurers = new ArrayList<>();
    ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corporate);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mContext = this;

        edtOrgName = findViewById(R.id.edtOrgName);
        edtUserName = findViewById(R.id.edtUserName);
        edtUserMobile = findViewById(R.id.edtUserMobile);
        edtUserEmail = findViewById(R.id.edtUserEmail);
        edtCity = findViewById(R.id.edtCity);
        edtRemark = findViewById(R.id.edtRemark);

        spnCorporateIns = findViewById(R.id.spnCorporateIns);
        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");
        getNonMotorLead();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSubmitClick(View view) {
        if (isValid())
            submitNonMotorLead();

    }

    private boolean isValid() {

        companyName = edtOrgName.getText().toString().trim();
        name = edtUserName.getText().toString().trim();
        mobile = edtUserMobile.getText().toString().trim();
        email = edtUserEmail.getText().toString().trim();
        city = edtCity.getText().toString().trim();
        remarks = edtRemark.getText().toString().trim();

        if (TextUtils.isEmpty(insId)) {
            Toast.makeText(mContext, "Select Insurance Type", Toast.LENGTH_SHORT).show();
            return false;
        }else if (TextUtils.isEmpty(companyName)) {
            edtOrgName.setError("Field cannot be Empty");
            return false;
        } else if (TextUtils.isEmpty(name)) {
            edtUserName.setError("Field cannot be Empty");
            return false;
        } else if (TextUtils.isEmpty(mobile)) {
            edtUserMobile.setError("Field cannot be Empty");
            return false;
        } else if (!(AppUtils.isValidMobile(mobile))) {
            edtUserMobile.setError("Invalid Phone Number");
            edtUserMobile.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(email)) {
            edtUserEmail.setError("Field cannot be Empty");
            return false;
        } else if (!(AppUtils.isValidMail(email))) {
            edtUserEmail.setError("Invalid Email");
            edtUserEmail.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(city)) {
            edtCity.setError("Field cannot be Empty");
            return false;
        } else if (TextUtils.isEmpty(remarks)) {
            edtRemark.setError("Field cannot be Empty");
            return false;
        }

        return true;
    }

    public void submitNonMotorLead() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().nonMotorLead(mContext, insId, companyName, name,
                        mobile, email, city, remarks);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getNonMotorLead() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getNonMotorLead(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void submitDialog() {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle((R.string.app_name));
        alertDialogBuilder.setIcon(R.drawable.app_logo);
        alertDialogBuilder.setMessage("Your Request saved successfully!");
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.ok,
                        (dialog, id) -> {
                            finish();
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void initCities() {
        for (int i = 0; i < insList.size(); i++) {
            String stateName = insList.get(i).getInsType().trim();
            String stateId = insList.get(i).getId();
            insurers.add(new String2WithTag(stateName, stateId));
        }

        ArrayAdapter<String2WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, insurers);
        spnCorporateIns.setAdapter(adapter);


        spnCorporateIns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                insId = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof BasicResponse) {
            BasicResponse response1 = (BasicResponse) response;
            if (response1.getStatus() == 1) {
                submitDialog();
            } else {
                if (!TextUtils.isEmpty(response1.getMsg()))
                    Toast.makeText(mContext, "" + (response1).getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
        if (response instanceof NonMotorList) {
            NonMotorList response1 = (NonMotorList) response;
            if (response1.getStatus() == 1) {
                if (response1.getData() != null) {
                    if (response1.getData().size() > 0)
                        insList.addAll(response1.getData());
                    initCities();
                }
            }
        }
        progressdialog.dismiss();
    }
}