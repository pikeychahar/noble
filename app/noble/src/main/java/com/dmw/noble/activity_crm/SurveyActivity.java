package com.dmw.noble.activity_crm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dmw.noble.R;
import com.dmw.noble.adaptor.crm.SurveyAdapter;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.CrmManager;
import com.dmw.noble.model_crm.SurveyData;
import com.dmw.noble.model_crm.SurveyList;
import com.dmw.noble.utils.AppUtils;

import java.util.ArrayList;
import java.util.Objects;

public class SurveyActivity extends AppCompatActivity implements onRequestCompleteCallBackListener,
        SurveyAdapter.OnInfoClickListener, SurveyAdapter.OnStatusStatusListener {

    Context mContext;
    RecyclerView rcAllSurvey;
    String userId, userType;
    ProgressDialog progressDialog;
    SharedPreferences preferences;
    ArrayList<SurveyData> surveyList = new ArrayList<>();
    SurveyAdapter surveyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);

        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait..");

        rcAllSurvey = findViewById(R.id.rcAllSurvey);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rcAllSurvey.setLayoutManager(mLayoutManager);
        getCrmInspection();

        surveyAdapter = new SurveyAdapter(mContext, surveyList);
        rcAllSurvey.setAdapter(surveyAdapter);
        surveyAdapter.notifyDataSetChanged();
    }

    public void onCreateSurvey(View view) {
        Intent intent = new Intent(mContext, RaiseInspectionActivity.class);
        startActivityForResult(intent, 555);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK)
            getCrmInspection();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getCrmInspection() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                CrmManager.getInstance().getCrmInspection(mContext, userId, userType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof SurveyList) {
            SurveyList policyResponse = (SurveyList) response;
            if (policyResponse.getStatus()) {
                if (surveyList.size() > 0)
                    surveyList.clear();
                if (policyResponse.getData() != null) {
                    findViewById(R.id.rlNoData).setVisibility(View.GONE);
                    surveyList.addAll(policyResponse.getData());
                    surveyAdapter.notifyDataSetChanged();
                } else findViewById(R.id.rlNoData).setVisibility(View.VISIBLE);
            } else findViewById(R.id.rlNoData).setVisibility(View.VISIBLE);
        }
        progressDialog.dismiss();
    }

    @Override
    public void onStatusClick(int position) {
        Bundle mBundle = new Bundle();
        SurveyData clamObj = surveyList.get(position);
        Intent intent = new Intent(mContext, ChatActivity.class);
        mBundle.putString(AppUtils.CLAIM_ID, clamObj.getId());
        mBundle.putString(AppUtils.CLAIM_NO, clamObj.getSurveyId());
        mBundle.putString(AppUtils.CLAIM_MANAGER, clamObj.getAssignedTo());
        mBundle.putString(AppUtils.CHAT_TYPE, "Inspection");
        intent.putExtras(mBundle);
        startActivity(intent);
    }

    @Override
    public void onInfoClick(int position) {
        Bundle mBundle = new Bundle();
        SurveyData clamObj = surveyList.get(position);
        Intent intent = new Intent(mContext, InspectionViewActivity.class);
        mBundle.putString(AppUtils.CLAIM_ID, clamObj.getSurveyId());
        intent.putExtras(mBundle);
        startActivity(intent);
    }
}