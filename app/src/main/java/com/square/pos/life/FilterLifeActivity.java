package com.square.pos.life;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.square.pos.R;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.ApiManager;
import com.square.pos.model.CommonResponse;
import com.square.pos.model_life.SumList;
import com.square.pos.model_life.Suminsured;
import com.square.pos.utils.AppUtils;
import com.square.pos.utils.StringWithTag;

import java.util.ArrayList;
import java.util.Objects;

public class FilterLifeActivity extends AppCompatActivity
        implements onRequestCompleteCallBackListener {
    ArrayList<Suminsured> sumInsuredList = new ArrayList<>();
    ArrayList<StringWithTag> sumInsureds = new ArrayList<>();
    Spinner spnSumIns, spnTobacco;

    Context mContext;
    Bundle mBundle;
    String sumInsured, tobacco, accident, personalAccident, cancerCover, critical, income,
            coverAge, quotationId;
    ProgressDialog progressdialog;
    CheckBox chkAccident, chkPersonal, chkCancer, chkCritical, chkIncome;
    EditText edtCoverTillAge;
    TextInputLayout txtCTA;
    int ageYears;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_life);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        this.setTitle("Filter And Addon Cover");
        mBundle = getIntent().getExtras();

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait...");

        spnSumIns = findViewById(R.id.spnSumIns);
        spnTobacco = findViewById(R.id.spnTobacco);

        chkIncome = findViewById(R.id.chkIncome);
        chkCritical = findViewById(R.id.chkCritical);
        chkAccident = findViewById(R.id.chkAccident);
        chkCancer = findViewById(R.id.chkCancer);
        chkPersonal = findViewById(R.id.chkPersonal);
        edtCoverTillAge = findViewById(R.id.edtCoverTillAge);
        txtCTA = findViewById(R.id.txtCTA);

        if (mBundle != null) {
            quotationId = mBundle.getString(AppUtils.QUOTATION_ID);
            sumInsured = mBundle.getString(AppUtils.SUMINSURED);
            tobacco = mBundle.getString(AppUtils.TOBACCO_USER);
            tobacco = mBundle.getString(AppUtils.TOBACCO_USER);
            ageYears = mBundle.getInt(AppUtils.AGE_YEAR);
            ageYears = ageYears + 10;
            txtCTA.setHint("Cover Till Age (From " + ageYears + " to 85 Max)");

            if (!TextUtils.isEmpty(tobacco)) {
                if (tobacco.equals("1"))
                    spnTobacco.setSelection(1);
            }
        }

        getSumInsured();
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

    private void initSumInsured() {
        for (int i = 0; i < sumInsuredList.size(); i++) {
            String stateName = sumInsuredList.get(i).getSuminsured().trim();
            String stateId = sumInsuredList.get(i).getSuminsured();
            sumInsureds.add(new StringWithTag(stateName, stateId));
        }

        ArrayAdapter<StringWithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, sumInsureds);
        spnSumIns.setAdapter(adapter);

        if (!TextUtils.isEmpty(sumInsured) && sumInsuredList.size() > 0)
            for (int i = 0; i < sumInsuredList.size(); i++) {
                String sName = sumInsuredList.get(i).getSuminsured();
                if (sName.equalsIgnoreCase(sumInsured)) {
                    spnSumIns.setSelection(i);
                    break;
                }
            }


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

    @Override
    public void onResponse(Object response) {
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

        if (response instanceof CommonResponse) {
            CommonResponse cResponse = (CommonResponse) response;
            if (cResponse.getSuccess().equals("1")) {
                Intent intent = new Intent();
                mBundle.putString(AppUtils.TOBACCO_USER, tobacco);
                mBundle.putString(AppUtils.SUMINSURED, sumInsured);
                intent.putExtras(mBundle);
                setResult(RESULT_OK, intent);
                finish();
            }else AppUtils.showToast(mContext,cResponse.getMsg());

            progressdialog.dismiss();
        }

    }

    public void onUpdateLifeFilter(View view) {
        if (chkAccident.isChecked())
            accident = "1";
        else accident = "0";
        if (chkCancer.isChecked())
            cancerCover = "1";
        else cancerCover = "0";
        if (chkCritical.isChecked())
            critical = "1";
        else critical = "0";
        if (chkPersonal.isChecked())
            personalAccident = "1";
        else personalAccident = "0";
        if (chkIncome.isChecked())
            income = "1";
        else income = "0";

        if (spnTobacco.getSelectedItemPosition() == 0)
            tobacco = "0";
        else tobacco = "1";

        coverAge = edtCoverTillAge.getText().toString();

        if (TextUtils.isEmpty(coverAge)) {
            edtCoverTillAge.setError(getString(R.string.field_cannot));
            edtCoverTillAge.requestFocus();
        } else {
            int year = Integer.parseInt(coverAge);
            if ((year >= ageYears) && (year <= 85)) {
                updateLifeFilter();
            } else {
                edtCoverTillAge.setError("Select In Range");
                edtCoverTillAge.requestFocus();
            }
        }

    }

    public void updateLifeFilter() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                ApiManager.getInstance().updateLifeFilter(mContext, quotationId, tobacco, coverAge,
                        income, critical, cancerCover, personalAccident, accident, sumInsured);
            } catch (Exception e) {
                progressdialog.dismiss();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }
}