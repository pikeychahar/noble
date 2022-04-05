package com.dmw.noble.life;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.adaptor.LifeAdapter;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.ApiManager;
import com.dmw.noble.model.CommonResponse;
import com.dmw.noble.model_life.LifePremium;
import com.dmw.noble.model_life.LifePremiumQuote;
import com.dmw.noble.utils.AppUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class LifePremiumActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener, LifeAdapter.OnPremiumClick {
    Context mContext;
    Bundle mBundle;
    String quotationId, userType, userId, company, gross, net, tax, extra, planName, sumInsured;
    ArrayList<LifePremiumQuote> premiumList = new ArrayList<>();
    RecyclerView recyclerLife;
    LifeAdapter lifeAdapter;
    ProgressBar progressBar;
    TextView txtQuoteId;
    SwipeRefreshLayout swipeContainer;
    ProgressDialog progressdialog;
    ShimmerFrameLayout shimmerFrameLayout;
    int pCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_premium);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(getColor(R.color.colorPrimaryDark)));

        mContext = this;
        this.setTitle("Life Insurance");
        mBundle = getIntent().getExtras();
        SharedPreferences preferences = getSharedPreferences(String.valueOf(R.string.app_name),
                MODE_PRIVATE);

        userType = preferences.getString(AppUtils.USER_TYPE, "");
        userId = preferences.getString(AppUtils.PRIMARY_ID, "");

        recyclerLife = findViewById(R.id.lifeQuoteList);
        progressBar = findViewById(R.id.progressBar);
        swipeContainer = findViewById(R.id.swipeContainer);
        txtQuoteId = findViewById(R.id.txtQuoteId);
        shimmerFrameLayout = findViewById(R.id.shimmerLayout);

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait...");

        if (mBundle != null) {
            quotationId = mBundle.getString(AppUtils.QUOTATION_ID);
            txtQuoteId.setText(quotationId);
            getHdfcLife();

        }

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerLife.setLayoutManager(mLayoutManager);
        recyclerLife.setNestedScrollingEnabled(false);

        lifeAdapter = new LifeAdapter(mContext, premiumList);
        swipeContainer.setColorSchemeResources(android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeContainer.setOnRefreshListener(() -> {
            if (!TextUtils.isEmpty(quotationId)) {
                if (premiumList.size() > 0)
                    premiumList.clear();
                pCount = 0;
                lifeAdapter.notifyItemRangeRemoved(0, premiumList.size());
                lifeAdapter.notifyDataSetChanged();
                AppUtils.startShimmer(shimmerFrameLayout);
                getHdfcLife();
            }
            swipeContainer.setRefreshing(false);
        });
    }

    public void onShareClick(View view) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");

            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Square Insurance");
            String url = AppUtils.LIFE_QUOTE_LINK + quotationId;
            shareIntent.putExtra(Intent.EXTRA_TEXT, url);
            startActivity(Intent.createChooser(shareIntent, "Share With"));
        } catch (Exception e) {
            //e.toString();
            e.printStackTrace();
        }
    }

    public void getHdfcLife() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);
            try {
                ApiManager.getInstance().getLifeQuote(mContext, "hdfc", quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();

    }

    @Override
    protected void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmer();
    }

    @Override
    public void onResponse(Object response) {

        if (response instanceof CommonResponse) {
            CommonResponse commonResponse = (CommonResponse) response;
            if (commonResponse.getSuccess().equals("1")) {
                Intent intent = new Intent(mContext, ProposalLifeActivity.class);
                intent.putExtras(mBundle);
                startActivity(intent);
                progressdialog.dismiss();
            }
        }

        if (response instanceof LifePremium) {
            LifePremium lResponse = (LifePremium) response;
            if (lResponse.getStatus().equals("1")) {
                pCount++;
                if (lResponse.getCompany().equalsIgnoreCase("hdfc")) {

                    for (int i = 0; i < premiumList.size(); i++) {
                        if (premiumList.get(i).getCompany().equalsIgnoreCase("hdfc")) {
                            premiumList.remove(i);
                            break;
                        }
                    }
                    if (lResponse.getResult().size() > 0) {
                        for (int koo = 0; koo < lResponse.getResult().size(); koo++) {
                            if (lResponse.getResult().get(koo).getGross() > 0) {
                                LifePremiumQuote lifePremiumQuote = new LifePremiumQuote();
                                lifePremiumQuote.setCompany(lResponse.getCompany());
                                lifePremiumQuote.setImgPath(lResponse.getResult().get(koo).getLogo());
                                lifePremiumQuote.setPlanName(lResponse.getResult().get(koo).getPlanName());
                                lifePremiumQuote.setAgeYears(lResponse.getResult().get(koo).getPolicyPeriod());
                                lifePremiumQuote.setMaxAge(lResponse.getResult().get(koo).getMaxLimit());
                                lifePremiumQuote.setServiceTax(lResponse.getResult().get(koo).getGst());
                                lifePremiumQuote.setPremium(lResponse.getResult().get(koo).getNet());
                                lifePremiumQuote.setGross(lResponse.getResult().get(koo).getGross());
                                lifePremiumQuote.setSumAssured(lResponse.getResult().get(koo).getSuminsured());

                                ArrayList<String> arrayList = new ArrayList<>();
                                if (!TextUtils.isEmpty(lResponse.getResult().get(koo).getCancerCare()))
                                    arrayList.add("Cancer Care rider: " + lResponse.getResult().get(koo).getCancerCare());

                                if (!TextUtils.isEmpty(lResponse.getResult().get(koo).getAccidentDeathCover()))
                                    arrayList.add("Accident Death Cover: " + lResponse.getResult().get(koo).getAccidentDeathCover());

                                if (!TextUtils.isEmpty(lResponse.getResult().get(koo).getCriticalIllness()))
                                    arrayList.add("Critical Illness riser: " + lResponse.getResult().get(koo).getCriticalIllness());


                                if (!TextUtils.isEmpty(lResponse.getResult().get(koo).getPersonalAccident()))
                                    arrayList.add("Personal Accident Cover: " + lResponse.getResult().get(koo).getPersonalAccident());

                                if (!TextUtils.isEmpty(lResponse.getResult().get(koo).getIncomeBenefit()))
                                    arrayList.add("Income Benefit on Accidental Disability: "
                                            + lResponse.getResult().get(koo).getIncomeBenefit());

                                lifePremiumQuote.setArrayCover(arrayList);
                                premiumList.add(lifePremiumQuote);
                            }
                        }
                    }
                }
            }
        }

        if (premiumList.size() > 0) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Collections.sort(premiumList,
                        Comparator.comparing(LifePremiumQuote::getPremium));
            } else {
                Collections.sort(premiumList,
                        (o1, o2) -> Float.compare(o1.getPremium(), o2.getPremium()));
            }

            lifeAdapter.notifyDataSetChanged();
            recyclerLife.setAdapter(lifeAdapter);
            findViewById(R.id.rlNoQuote).setVisibility(View.GONE);
            AppUtils.stopShimmer(shimmerFrameLayout);
        } else {
            lifeAdapter.notifyItemRangeRemoved(0, premiumList.size());
            lifeAdapter.notifyItemChanged(0);
        }

        if (pCount > 0) {
            AppUtils.stopShimmer(shimmerFrameLayout);
            progressBar.setVisibility(View.GONE);
            if (premiumList.size() == 0)
                findViewById(R.id.rlNoQuote).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void OnPremiumButtonClick(int position) {
        LifePremiumQuote lifePremiumQuote = premiumList.get(position);
        company = lifePremiumQuote.getCompany();
        gross = String.valueOf(lifePremiumQuote.getGross());
        net = String.valueOf(lifePremiumQuote.getPremium());
        tax = String.valueOf(lifePremiumQuote.getServiceTax());
        extra = lifePremiumQuote.getExtra();
        planName = lifePremiumQuote.getPlanName();
        sumInsured = lifePremiumQuote.getSumAssured();

        saveProposal();
    }

    public void saveProposal() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                ApiManager.getInstance().saveProposal(mContext, quotationId, company, gross, net,
                        tax, extra, planName, sumInsured);
            } catch (Exception e) {
                progressdialog.dismiss();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (itemId == R.id.action_filter) {
            onUpdateFilter();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menu1 = menu.findItem(R.id.action_edit);
        menu1.setVisible(false);
        return true;
    }

    public void onUpdateFilter() {
        Intent intent = new Intent(mContext, FilterLifeActivity.class);
        intent.putExtras(mBundle);
        startActivityForResult(intent, 555);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null) {
                getHdfcLife();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}