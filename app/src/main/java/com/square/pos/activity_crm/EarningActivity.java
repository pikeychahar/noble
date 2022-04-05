package com.square.pos.activity_crm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.adaptor.EarningAdapter;
import com.square.pos.adaptor.crm.StatementAdapter;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.CrmManager;
import com.square.pos.model_crm.DetailedPolicyList;
import com.square.pos.model_crm.PolicyData;
import com.square.pos.model_crm.StateData;
import com.square.pos.model_crm.StatementList;
import com.square.pos.utils.AppUtils;

import java.util.ArrayList;
import java.util.Objects;

public class EarningActivity extends AbstractActivity implements onRequestCompleteCallBackListener {

    Context mContext;
    String userId, posId, userType, url, type, year, fromMonth, toMonth;
    ArrayList<PolicyData> earningList = new ArrayList<>();
    ArrayList<StateData> statementList = new ArrayList<>();
    EarningAdapter earningAdapter;
    StatementAdapter statementAdapter;
    RecyclerView recyclerView, rcEarning;
    RecyclerView.LayoutManager mLayoutManager, eLayoutManager;
    SharedPreferences preferences;
    ProgressDialog progressdialog;
    RelativeLayout rlEarning, rlStatement;
    TextView txtEarning, txtStatement, txtTotalPremium, txtTotalPayout, txtTotalFile;
    Spinner spnYear, spnFromMonth, spnToMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earning);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);

        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        posId = preferences.getString(AppUtils.AGENT_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");
        mContext = this;

        url = "/account/MyEarning";
        type = "Earning";

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");
        recyclerView = findViewById(R.id.listEarning);
        rcEarning = findViewById(R.id.rcEarning);
        rlEarning = findViewById(R.id.rlEarning);
        rlStatement = findViewById(R.id.rlStatement);
        txtEarning = findViewById(R.id.txtEarning);
        txtStatement = findViewById(R.id.txtStatement);
        txtTotalPremium = findViewById(R.id.txtTotalPremium);
        txtTotalPayout = findViewById(R.id.txtTotalPayout);
        txtTotalFile = findViewById(R.id.txtTotalFile);

        spnFromMonth = findViewById(R.id.spnFromMonth);
        spnToMonth = findViewById(R.id.spnToMonth);
        spnYear = findViewById(R.id.spnYear);

        mLayoutManager = new LinearLayoutManager(mContext);
        eLayoutManager = new LinearLayoutManager(mContext);

        recyclerView.setLayoutManager(mLayoutManager);
        rcEarning.setLayoutManager(eLayoutManager);

//        getStatementList();
        getEarning();

        earningAdapter = new EarningAdapter(mContext, earningList);
        rcEarning.setAdapter(earningAdapter);
        earningAdapter.notifyDataSetChanged();

        statementAdapter = new StatementAdapter(mContext, statementList);
        recyclerView.setAdapter(statementAdapter);
        statementAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getEarning() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                CrmManager.getInstance().getDetailedPolicy(mContext, userId, userType, type, url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getStatementList() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                CrmManager.getInstance().getStatementList(mContext, userId, userType, posId, year,
                        fromMonth, toMonth);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof StatementList) {
            StatementList eResponse = (StatementList) response;
            if (eResponse.getData().size() > 0) {
                if (statementList.size() > 0)
                    statementList.clear();
                statementList.addAll(eResponse.getData());
                statementAdapter.notifyDataSetChanged();
            } else {
                AppUtils.showToast(mContext, getString(R.string.oops_no_data_found));
            }
        }
        if (response instanceof DetailedPolicyList) {
            DetailedPolicyList eResponse = (DetailedPolicyList) response;
            if (eResponse.isStatus()) {
                if (earningList.size() > 0)
                    earningList.clear();
                earningList.addAll(eResponse.getData());
                earningAdapter.notifyDataSetChanged();

                txtTotalFile.setText(eResponse.getTotalFiles());
                txtTotalPayout.setText(eResponse.getTotalEarning());
                txtTotalPremium.setText(eResponse.getTotalPremium());

            } else AppUtils.showToast(mContext, "" + eResponse.getMsg());
        }
        progressdialog.dismiss();
    }

    public void onEarningClick(View view) {
        rlStatement.setVisibility(View.GONE);
        rlEarning.setVisibility(View.VISIBLE);
        txtStatement.setTextColor(getColor(R.color.colorGray));
        txtEarning.setTextColor(getColor(R.color.colorPrimaryDark));
    }

    public void onStatementClick(View view) {
        rlEarning.setVisibility(View.GONE);
        rlStatement.setVisibility(View.VISIBLE);
        txtEarning.setTextColor(getColor(R.color.colorGray));
        txtStatement.setTextColor(getColor(R.color.colorPrimaryDark));
    }

    public void onStatementSearchClick(View view) {
        year = spnYear.getSelectedItem().toString();
        fromMonth = spnFromMonth.getSelectedItem().toString();
        toMonth = spnToMonth.getSelectedItem().toString();

        int toIndex, fromIndex;
        toIndex = spnToMonth.getSelectedItemPosition();
        fromIndex = spnFromMonth.getSelectedItemPosition();

        if (toIndex >= fromIndex)
            getStatementList();
        else AppUtils.showToast(mContext, "Invalid Selection");
    }
}
