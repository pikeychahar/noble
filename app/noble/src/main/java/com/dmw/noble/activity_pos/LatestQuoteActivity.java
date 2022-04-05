package com.dmw.noble.activity_pos;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.activity.health.HealthQuotationActivity;
import com.dmw.noble.adaptor.health.HealthQuoteAdapter;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.model_health.HealthQuote;
import com.dmw.noble.model_health.HealthQuoteList;
import com.dmw.noble.utils.AppUtils;

import java.util.ArrayList;
import java.util.Objects;

public class LatestQuoteActivity extends AbstractActivity implements
        onRequestCompleteCallBackListener, HealthQuoteAdapter.OnProceedClick {

    ArrayList<HealthQuote> quotationList = new ArrayList<>();
    ArrayList<HealthQuote> searchList = new ArrayList<>();
    HealthQuoteAdapter quotationAdapter;
    Context mContext;
    RecyclerView recyclerView;
    String agentId, userType;
    RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences preferences;
    ProgressDialog progressdialog;
    Bundle mBundle;
    TextView txtHealth, txtLife, txtTravel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_quote);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        mContext = this;
        mBundle = new Bundle();

        agentId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");
        recyclerView = findViewById(R.id.rcQuote);

        txtHealth = findViewById(R.id.txtHealth);
        txtLife = findViewById(R.id.txtLife);
        txtTravel = findViewById(R.id.txtTravel);

        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);

        getQuotation();
        quotationAdapter = new HealthQuoteAdapter(mContext, quotationList);
        recyclerView.setAdapter(quotationAdapter);
        quotationAdapter.notifyDataSetChanged();
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
        if (response instanceof HealthQuoteList) {
            HealthQuoteList policyResponse = (HealthQuoteList) response;
            if (policyResponse.getStatus().equals("1")) {
                if (quotationList.size() > 0)
                    quotationList.clear();
                searchList.clear();
                if (policyResponse.getHealthQuote() != null) {
                    quotationList.addAll(policyResponse.getHealthQuote());
                    this.setTitle("My Quotes (" + quotationList.size() + ")");
                    searchList.addAll(quotationList);
                    if (quotationList.size() == 0) {
                        findViewById(R.id.rlNoPolicies).setVisibility(View.VISIBLE);
                    } else findViewById(R.id.rlNoPolicies).setVisibility(View.GONE);
                    quotationAdapter.notifyDataSetChanged();
                }
            } else {
                findViewById(R.id.rlNoPolicies).setVisibility(View.VISIBLE);
            }
        }
        progressdialog.dismiss();
    }

    public void getQuotation() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().healthQuotationList(mContext, userType, agentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void OnProceedListener(int pos, int type) {

        String qId = quotationList.get(pos).getQuoteId();
        String insId = quotationList.get(pos).getPincode();
        String sum = quotationList.get(pos).getSumInsured();
        String planType = quotationList.get(pos).getPlanType();

        if (type == 0) {
            Intent intent = new Intent(mContext, HealthQuotationActivity.class);
            mBundle.putString(AppUtils.QUOTATION_ID_1, qId);
            mBundle.putString(AppUtils.PINCODE, insId);
            mBundle.putString(AppUtils.SUM_ASSURED, sum);
            mBundle.putString(AppUtils.PLAN_TYPE, planType);
            intent.putExtras(mBundle);
            startActivity(intent);
        } else if (type == 1) {
            String url = quotationList.get(pos).getPdfUrl();
            try {
                Intent i = new Intent("android.intent.action.MAIN");
                i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                i.addCategory("android.intent.category.LAUNCHER");
                i.setData(Uri.parse(url));
                startActivity(i);
            } catch (ActivityNotFoundException e) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
            }
        }
    }

    private synchronized void searchForTextInList(String searchString) {
        int textLength = searchString.length();
        quotationList.clear();
        if (searchString.trim().length() == 0) {
            quotationList.addAll(searchList);
        } else {
            searchString = searchString.replaceAll("^\\s+", "");

            for (int i = 0; i < searchList.size(); i++) {
                HealthQuote assessment = searchList.get(i);
                String userName = "";
                if (!TextUtils.isEmpty(assessment.getName()))
                    userName = assessment.getPlanType().toLowerCase();
                String contactNo = "";
                if (!TextUtils.isEmpty(assessment.getCompany()))
                    contactNo = assessment.getCompany();
                if (textLength > 0) {
                    if (userName.contains(searchString) || contactNo.contains(searchString)) {
                        quotationList.add(assessment);
                    }
                }
            }
            quotationAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                searchForTextInList(newText);

                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        searchView.setOnCloseListener(() -> {
            quotationList.clear();
            quotationList.addAll(searchList);
            quotationAdapter.notifyDataSetChanged();
            return false;
        });
        return true;
    }

    public void onHealthClick(View view) {

        txtHealth.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.selected_border, null));
        txtLife.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txtTravel.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
    }

    public void onTravelClick(View view) {

        txtTravel.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.selected_border, null));
        txtLife.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txtHealth.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
    }

    public void onLifeClick(View view) {

        txtLife.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.selected_border, null));
        txtHealth.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txtTravel.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
    }
}