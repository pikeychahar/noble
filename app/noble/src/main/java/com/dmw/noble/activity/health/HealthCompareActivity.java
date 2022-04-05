package com.dmw.noble.activity.health;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.adaptor.health.CompareAdapter;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.Health.HealthManager;
import com.dmw.noble.model_health.compare.Compare;
import com.dmw.noble.model_health.compare.Feature;
import com.dmw.noble.utils.AppUtils;
import com.dmw.noble.utils.UtilClass;

import java.util.ArrayList;
import java.util.Objects;

public class HealthCompareActivity extends AbstractActivity implements onRequestCompleteCallBackListener {
    Context mContext;
    Bundle mBundle;
    RelativeLayout rlCompare1, rlCompare2, rlCompare3;
    ImageView img1, img2, img3;
    TextView txtPlan1, txtPlan2, txtPlan3, txtPremium1, txtPremium2, txtPremium3, txtSumInsured1,
            txtSumInsured2, txtSumInsured3;
    RecyclerView recyclerView;
    ProgressDialog progressdialog;
    RecyclerView.LayoutManager mLayoutManager;
    CompareAdapter compareAdapter;
    ArrayList<Feature> compareList = new ArrayList<>();
    String companies, sumInsured, planName, subPlanName, tenure, premium, appKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_compare);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        mBundle = getIntent().getExtras();
        if (mBundle != null) {
            companies = mBundle.getString("compare company");
            planName = mBundle.getString("compare plan");
            subPlanName = mBundle.getString("compare subPlan");
            tenure = mBundle.getString("compare tenure");
            premium = mBundle.getString("compare premium");
            sumInsured = mBundle.getString("compare sum insured");
            appKey = mBundle.getString(AppUtils.HL_APP_KEY);
        }

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");

        recyclerView = findViewById(R.id.compareList);
        recyclerView.setNestedScrollingEnabled(false);

        txtSumInsured1 = findViewById(R.id.txtSumInsured1);
        txtSumInsured2 = findViewById(R.id.txtSumInsured2);
        txtSumInsured3 = findViewById(R.id.txtSumInsured3);

        txtPremium1 = findViewById(R.id.txtPremium1);
        txtPremium2 = findViewById(R.id.txtPremium2);
        txtPremium3 = findViewById(R.id.txtPremium3);

        txtPlan1 = findViewById(R.id.txtPlan1);
        txtPlan2 = findViewById(R.id.txtPlan2);
        txtPlan3 = findViewById(R.id.txtPlan3);

        rlCompare1 = findViewById(R.id.rlCompare1);
        rlCompare2 = findViewById(R.id.rlCompare2);
        rlCompare3 = findViewById(R.id.rlCompare3);

        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        compareData();
        compareAdapter = new CompareAdapter(mContext, compareList);
        recyclerView.setAdapter(compareAdapter);
        compareAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof Compare) {
            Compare compareResponse = (Compare) response;
            if (compareResponse.getSuccess().equals("1")) {
                compareList.addAll(compareResponse.getFeatures());
            }
            if (compareResponse.getCompanies() != null) {
                int count = compareResponse.getCompanies().size();

                if (count != 3) {
                    int checked = UtilClass.getInstance().getChecked();
                    if (checked == 2) {
                        rlCompare3.setVisibility(View.GONE);
                        LinearLayout.LayoutParams params0 = (LinearLayout.LayoutParams) rlCompare3.getLayoutParams();
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rlCompare1.getLayoutParams();
                        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) rlCompare2.getLayoutParams();
                        params.weight = 1.5f;
                        params1.weight = 1.5f;
                        params0.weight = 0;

                        rlCompare1.setLayoutParams(params);
                        rlCompare2.setLayoutParams(params1);
                        rlCompare3.setLayoutParams(params0);
                    }
                }

                for (int i = 0; i < count; i++) {
                    String premium = compareResponse.getCompanies().get(i).getPremium();
                    String tenure = compareResponse.getCompanies().get(i).getTenure();
                    String sum = compareResponse.getCompanies().get(i).getSuminsured();
                    String logo = compareResponse.getCompanies().get(i).getLogo();
                    if (i == 0) {
                        txtPlan1.setText(compareResponse.getCompanies().get(i).getSubplan());
                        txtPremium1.setText(premium + "/" + tenure + " year");
                        txtSumInsured1.setText(sum);
                        if (!TextUtils.isEmpty((logo))) {
                            Glide.with(mContext)
                                    .load(logo)
                                    .placeholder(R.drawable.placeholder)
                                    .error(R.drawable.placeholder)
                                    .animate(android.R.anim.fade_in)
                                    .into(img1);
                        }
                    } else if (i == 1) {
                        txtPlan2.setText(compareResponse.getCompanies().get(i).getSubplan());
                        txtPremium2.setText(premium + "/" + tenure + " year");
                        txtSumInsured2.setText(sum);
                        if (!TextUtils.isEmpty((logo))) {
                            Glide.with(mContext)
                                    .load(logo)
                                    .placeholder(R.drawable.placeholder)
                                    .error(R.drawable.placeholder)
                                    .animate(android.R.anim.fade_in)
                                    .into(img2);
                        }
                    } else if (i == 2) {
                        txtPlan3.setText(compareResponse.getCompanies().get(i).getSubplan());
                        txtPremium3.setText(premium + "/" + tenure + " year");
                        txtSumInsured3.setText(sum);
                        if (!TextUtils.isEmpty((logo))) {
                            Glide.with(mContext)
                                    .load(logo)
                                    .placeholder(R.drawable.placeholder)
                                    .error(R.drawable.placeholder)
                                    .animate(android.R.anim.fade_in)
                                    .into(img3);
                        }
                    }
                }
            }
        }
        compareAdapter.notifyDataSetChanged();
        progressdialog.dismiss();
    }

    public void compareData() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                HealthManager.getInstance().compareData(mContext, companies, sumInsured, planName,
                        subPlanName, tenure, premium,appKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}