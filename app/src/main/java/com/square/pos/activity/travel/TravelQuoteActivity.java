package com.square.pos.activity.travel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.activity.health.HealthPaymentActivity;
import com.square.pos.adaptor.TravelAdapter;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.TravelManager;
import com.square.pos.model_crm.BasicBoolResponse;
import com.square.pos.model_travel.CoverageDetail;
import com.square.pos.model_travel.TravelPlanData;
import com.square.pos.model_travel.TravelPremium;
import com.square.pos.model_travel.TravelPremiumData;
import com.square.pos.model_travel.TravelPremiumQuote;
import com.square.pos.utils.AppUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class TravelQuoteActivity extends AbstractActivity implements
        onRequestCompleteCallBackListener, TravelAdapter.OnPremiumClick {
    Context mContext;
    ProgressBar progressBar;
    SwipeRefreshLayout swipeContainer;
    TravelAdapter travelAdapter;
    ProgressDialog progressdialog;
    ShimmerFrameLayout shimmerFrameLayout;
    Bundle mBundle;
    RecyclerView recyclerLife;
    String userId, userType, quotationId, planId, company, geography, premium, pId;
    TextView txtQuoteId;
    ArrayList<TravelPremiumQuote> premiumList = new ArrayList<>();
    int pCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_quote);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(getColor(R.color.colorPrimaryDark)));

        mContext = this;
        mBundle = getIntent().getExtras();
        SharedPreferences preferences = getSharedPreferences(String.valueOf(R.string.app_name),
                MODE_PRIVATE);

        userType = preferences.getString(AppUtils.USER_TYPE, "");
        userId = preferences.getString(AppUtils.PRIMARY_ID, "");

        progressBar = findViewById(R.id.progressBar);
        swipeContainer = findViewById(R.id.swipeContainer);
        recyclerLife = findViewById(R.id.premiumQuoteList);
        shimmerFrameLayout = findViewById(R.id.shimmerLayout);
        txtQuoteId = findViewById(R.id.txtQuoteId);

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait...");

        if (mBundle != null) {
            quotationId = mBundle.getString(AppUtils.QUOTATION_ID);
            planId = mBundle.getString(AppUtils.PLAN_TYPE);
            txtQuoteId.setText(quotationId);
            getDigitTravel();
        }

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerLife.setLayoutManager(mLayoutManager);
        recyclerLife.setNestedScrollingEnabled(false);

        travelAdapter = new TravelAdapter(mContext, premiumList);
        swipeContainer.setColorSchemeResources(android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeContainer.setOnRefreshListener(() -> {
            if (!TextUtils.isEmpty(quotationId)) {
                if (premiumList.size() > 0)
                    premiumList.clear();
                travelAdapter.notifyItemRangeRemoved(0, premiumList.size());
                travelAdapter.notifyDataSetChanged();
                AppUtils.startShimmer(shimmerFrameLayout);
                getDigitTravel();
            }
            swipeContainer.setRefreshing(false);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void onResponse(Object response) {
        try {
            if (response instanceof TravelPremium) {
                TravelPremium tResponse = (TravelPremium) response;
                if (tResponse.getData() != null) {
                    TravelPremiumData travelPremiumData = tResponse.getData();
                    if (travelPremiumData.getSuccess().equalsIgnoreCase("success")) {
                        pCount++;
                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("digit")) {
                                premiumList.remove(i);
                                break;
                            }
                        }
                        if (travelPremiumData.getData().size() > 0) {
                            TravelPlanData lResponse = travelPremiumData.getData().get(0);
                            TravelPremiumQuote travelPremiumQuote = new TravelPremiumQuote();
                            travelPremiumQuote.setCompany(lResponse.getCompany());

                            travelPremiumQuote.setImgPath(lResponse.getLogo());
                            travelPremiumQuote.setPlanName(lResponse.getPlan());
                            travelPremiumQuote.setServiceTax(lResponse.getPremium());
                            travelPremiumQuote.setPremium(lResponse.getPremium());
                            travelPremiumQuote.setGross(lResponse.getPremium());
                            travelPremiumQuote.setSumAssured(travelPremiumData.getSuminsured());
                            travelPremiumQuote.setExtra(travelPremiumData.getData().get(0).getGeography());

                            ArrayList<String> arrayList = new ArrayList<>();
                            for (int koo = 0; koo < lResponse.getCoverageDetail().size(); koo++) {

                                CoverageDetail coverageDetail = lResponse.getCoverageDetail().get(koo);

                                if (!TextUtils.isEmpty(coverageDetail.getCoverage()))
                                    arrayList.add(coverageDetail.getCoverage() + " - " + coverageDetail.getSumInsured());
                            }
                            travelPremiumQuote.setArrayCover(arrayList);
                            premiumList.add(travelPremiumQuote);
                        }
                    }
                }
            }

            if (premiumList.size() > 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Collections.sort(premiumList,
                            Comparator.comparing(TravelPremiumQuote::getPremium));
                } else {
                    Collections.sort(premiumList,
                            (o1, o2) -> Float.compare(o1.getPremium(), o2.getPremium()));
                }

                travelAdapter.notifyDataSetChanged();
                recyclerLife.setAdapter(travelAdapter);
                findViewById(R.id.rlNoQuote).setVisibility(View.GONE);
                AppUtils.stopShimmer(shimmerFrameLayout);
            } else {
                travelAdapter.notifyItemRangeRemoved(0, premiumList.size());
                travelAdapter.notifyItemChanged(0);
            }

            AppUtils.stopShimmer(shimmerFrameLayout);
            progressBar.setVisibility(View.GONE);
            if (premiumList.size() == 0)
                findViewById(R.id.rlNoQuote).setVisibility(View.VISIBLE);

            if (response instanceof BasicBoolResponse) {
                BasicBoolResponse boolResponse = (BasicBoolResponse) response;
                if (boolResponse.getStatus()) {
                    Intent intent = new Intent(mContext, HealthPaymentActivity.class);
                    if (!TextUtils.isEmpty(userId))
                        mBundle.putString(AppUtils.HEALTH_LINK,
                                mContext.getResources().getString(R.string.base_url) +
                                        "proposal_travel/proposal/" + quotationId + "/" + pId + "?role_id="
                                        + userId + "&role_type="
                                        + userType);
                    else mBundle.putString(AppUtils.HEALTH_LINK,
                            mContext.getResources().getString(R.string.base_url) +
                                    "proposal_travel/proposal/" + quotationId + "/" + pId);

                    intent.putExtras(mBundle);
                    startActivity(intent);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnPremiumButtonClick(int position, int tag) {
        company = premiumList.get(position).getCompany();
        geography = premiumList.get(position).getExtra();
        pId = premiumList.get(position).getPlanName();
        premium = "" + premiumList.get(position).getPremium();

        updateTravelProposal();
    }

    @Override
    protected void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmer();
    }

    public void getDigitTravel() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);
            try {
                TravelManager.getInstance().getTravelPremium(mContext, quotationId, planId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }


    public void updateTravelProposal() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);
            try {
                TravelManager.getInstance().updateTravelProposal(mContext, company, geography, premium,
                        planId, quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
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

    public void onShareClick(View view) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Square Insurance");
            String url = AppUtils.TRAVEL_QUOTE_LINK + quotationId;
            shareIntent.putExtra(Intent.EXTRA_TEXT, url);
            startActivity(Intent.createChooser(shareIntent, "Share With"));
        } catch (Exception e) {
            //e.toString();
            e.printStackTrace();
        }
    }
}
