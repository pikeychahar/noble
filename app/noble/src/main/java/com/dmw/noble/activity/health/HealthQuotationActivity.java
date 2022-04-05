package com.dmw.noble.activity.health;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.adaptor.StarAdapter;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.Health.HealthManager;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.model.City;
import com.dmw.noble.model.CityList;
import com.dmw.noble.model.CommonResponse;
import com.dmw.noble.model.PinList;
import com.dmw.noble.model.Pincode;
import com.dmw.noble.model.TempResponse;
import com.dmw.noble.model_health.HealthPremiumPojo;
import com.dmw.noble.model_health.v2.HealthPremium;
import com.dmw.noble.model_health.v2.HealthSumInsured;
import com.dmw.noble.model_health.v2.SumInsured;
import com.dmw.noble.utils.AppUtils;
import com.dmw.noble.utils.String2WithTag;
import com.dmw.noble.utils.String3WithTag;
import com.dmw.noble.utils.UtilClass;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class HealthQuotationActivity extends AbstractActivity implements
        onRequestCompleteCallBackListener, StarAdapter.OnPremiumClick,
        StarAdapter.OnBreakClickListener, StarAdapter.OnHospitalView,
        StarAdapter.OnBrochureView, StarAdapter.OnPlanBenefitView,
        StarAdapter.OnCheckClickInterface {

    List<City> cityList = new ArrayList<>();
    List<String2WithTag> cities = new ArrayList<>();
    List<SumInsured> sumInsuredList = new ArrayList<>();
    List<String3WithTag> sumList = new ArrayList<>();
    List<Pincode> pincodeList = new ArrayList<>();
    List<String2WithTag> pin = new ArrayList<>();

    Context mContext;
    Bundle mBundle;
    String quotationId, company, healthPlanType, planTenure, sumInsured, extra, planName, planCode,
            imgPath, userId, userType;
    ArrayList<HealthPremiumPojo> premiumList = new ArrayList<>();
    RecyclerView recyclerStar;
    StarAdapter starAdapter;
    ProgressBar progressBar;
    TextView txtQuoteId, txtCompare;
    SwipeRefreshLayout swipeContainer;
    String sumAssured, city, pinId, policyTenure;
    Float totalPremium, netPremium, serviceTax;
    ProgressDialog progressdialog;
    int checked = 0;
    List<Integer> checkedList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_quotation);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        mBundle = getIntent().getExtras();

        recyclerStar = findViewById(R.id.starList);
        progressBar = findViewById(R.id.progressBar);
        swipeContainer = findViewById(R.id.swipeContainer);
        txtQuoteId = findViewById(R.id.txtQuoteId);
        txtCompare = findViewById(R.id.txtCompare);

        SharedPreferences preferences = getSharedPreferences(String.valueOf(R.string.app_name),
                MODE_PRIVATE);
        userType = preferences.getString(AppUtils.USER_TYPE, "");
        userId = preferences.getString(AppUtils.PRIMARY_ID, "");

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait...");

        if (mBundle != null) {
            quotationId = mBundle.getString(AppUtils.QUOTATION_ID_1);
            city = mBundle.getString(AppUtils.HL_CITY);
            pinId = mBundle.getString(AppUtils.PINCODE);
            sumInsured = mBundle.getString(AppUtils.SUM_ASSURED);

            txtQuoteId.setText(quotationId);
            healthPlanType = mBundle.getString(AppUtils.PLAN_TYPE);

            getSupportActionBar().setTitle("Health Plans");
            getSupportActionBar().setSubtitle(healthPlanType);
        }

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerStar.setLayoutManager(mLayoutManager);
        recyclerStar.setNestedScrollingEnabled(false);

        if (!TextUtils.isEmpty(quotationId)) {
            getUniversal();
            checkedList.clear();
            getCare();
            getTata();
            getIcici();
            getIffco();
            getStarQuote();
            getBajajQuote();
            getDigitQuote();
            getHdfcQuote();
            getSbiQuote();
            getRoyalQuote();
            getFuture();
        }

        starAdapter = new StarAdapter(mContext, premiumList);
        swipeContainer.setColorSchemeResources(android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeContainer.setOnRefreshListener(() -> {
            if (!TextUtils.isEmpty(quotationId)) {
                if (premiumList.size() > 0)
                    premiumList.clear();

                txtCompare.setText(getString(R.string.compare));
                checked = 0;
                checkedList.clear();
                UtilClass.getInstance().setChecked(0);
                getIcici();
                getCare();
                getTata();
                getIffco();
                getStarQuote();
                getBajajQuote();
                getDigitQuote();
                getUniversal();
                getHdfcQuote();
                getSbiQuote();
                getRoyalQuote();
                getFuture();
            }
            swipeContainer.setRefreshing(false);
        });

        swipeContainer.post(() -> swipeContainer.setRefreshing(true));
        getCities();
        getSumInsured();
    }


    @Override
    public void onResponse(Object responses) {
        try {
            if (responses instanceof CityList) {
                CityList cResponse = (CityList) responses;
                if (cResponse.getMessage().equalsIgnoreCase("ok")) {
                    cityList.clear();
                    cities.clear();
                    cityList = cResponse.getCity();
                    if (cityList.size() > 0) {
                        initCities();
                    }
                }
            }
            if (responses instanceof HealthSumInsured) {
                HealthSumInsured cResponse = (HealthSumInsured) responses;
                if (cResponse.getStatus().equalsIgnoreCase("1")) {
                    sumInsuredList.clear();
                    sumList.clear();
                    sumInsuredList = cResponse.getSumInsured();
                    if (sumInsuredList.size() > 0) {
                        initSumInsured();
                    }
                }
            }

            if (responses instanceof PinList) {
                PinList cResponse = (PinList) responses;
                if (cResponse.getMessage().equalsIgnoreCase("ok")) {
                    pincodeList.clear();
                    pin.clear();
                    pincodeList = cResponse.getPincode();
                    if (pincodeList.size() > 0) {
                        initPincode();
                    }
                }
            }

            if (responses instanceof HealthPremium) {
                HealthPremium response = (HealthPremium) responses;
                if (response.getStatus() == 1) {

                    //Digit
                    if (response.getResult().get(0).getCompany().equalsIgnoreCase("digit")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("digit")) {
                                premiumList.remove(i);
                                break;
                            }
                        }
                        if (response.getResult().size() > 0) {
                            for (int koo = 0; koo < response.getResult().size(); koo++) {
                                if (response.getResult().get(koo).getGross() > 0) {
                                    HealthPremiumPojo healthPremiumPojo = new HealthPremiumPojo();

                                    healthPremiumPojo.setCompany(response.getResult().get(koo).getCompany());
                                    healthPremiumPojo.setImgPath(response.getResult().get(koo).getLogo());
                                    healthPremiumPojo.setPlanName(response.getResult().get(koo).getPlanName());
                                    healthPremiumPojo.setServiceTax(response.getResult().get(koo).getGst());
                                    healthPremiumPojo.setPremium(response.getResult().get(koo).getNet());
                                    healthPremiumPojo.setGross(response.getResult().get(koo).getGross());
                                    healthPremiumPojo.setPlanTenure(response.getTenure());
                                    healthPremiumPojo.setPlanCode(response.getResult().get(koo).getPlanCode());
                                    healthPremiumPojo.setSumAssured(response.getResult().get(koo).getSuminsured());
                                    healthPremiumPojo.setFeaturedPlan(response.getResult().get(koo).getFeatPlanName());
                                    healthPremiumPojo.setFeaturedSubPlan(response.getResult().get(koo).getFeatSubPlanName());
                                    healthPremiumPojo.setAppKey(response.getResult().get(koo).getAppKey());
                                    premiumList.add(healthPremiumPojo);
                                }
                            }
                        }
                    }
                    //IFFCO
                    if (response.getResult().get(0).getCompany().equalsIgnoreCase("iffco")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("iffco")) {
                                premiumList.remove(i);
                                break;
                            }
                        }
                        if (response.getResult().size() > 0) {
                            for (int koo = 0; koo < response.getResult().size(); koo++) {
                                if (response.getResult().get(koo).getGross() > 0) {
                                    HealthPremiumPojo healthPremiumPojo = new HealthPremiumPojo();

                                    healthPremiumPojo.setCompany(response.getResult().get(koo).getCompany());
                                    healthPremiumPojo.setImgPath(response.getResult().get(koo).getLogo());
                                    healthPremiumPojo.setPlanName(response.getResult().get(koo).getPlanName());
                                    healthPremiumPojo.setServiceTax(response.getResult().get(koo).getGst());
                                    healthPremiumPojo.setPremium(response.getResult().get(koo).getNet());
                                    healthPremiumPojo.setGross(response.getResult().get(koo).getGross());
                                    healthPremiumPojo.setPlanTenure(response.getTenure());
                                    healthPremiumPojo.setExtra(response.getResult().get(koo).getExtra());
                                    healthPremiumPojo.setPlanCode(response.getResult().get(koo).getPlanCode());
                                    healthPremiumPojo.setSumAssured(response.getResult().get(koo).getSuminsured());
                                    healthPremiumPojo.setFeaturedPlan(response.getResult().get(koo).getFeatPlanName());
                                    healthPremiumPojo.setFeaturedSubPlan(response.getResult().get(koo).getFeatSubPlanName());
                                    healthPremiumPojo.setAppKey(response.getResult().get(koo).getAppKey());
                                    premiumList.add(healthPremiumPojo);
                                }
                            }
                        }
                    }
                    //IFFCO
                    if (response.getResult().get(0).getCompany().equalsIgnoreCase("future")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("future")) {
                                premiumList.remove(i);
                                break;
                            }
                        }
                        if (response.getResult().size() > 0) {
                            for (int koo = 0; koo < response.getResult().size(); koo++) {
                                if (response.getResult().get(koo).getGross() > 0) {
                                    HealthPremiumPojo healthPremiumPojo = new HealthPremiumPojo();

                                    healthPremiumPojo.setCompany(response.getResult().get(koo).getCompany());
                                    healthPremiumPojo.setImgPath(response.getResult().get(koo).getLogo());
                                    healthPremiumPojo.setPlanName(response.getResult().get(koo).getPlanName());
                                    healthPremiumPojo.setServiceTax(response.getResult().get(koo).getGst());
                                    healthPremiumPojo.setPremium(response.getResult().get(koo).getNet());
                                    healthPremiumPojo.setGross(response.getResult().get(koo).getGross());
                                    healthPremiumPojo.setPlanTenure(response.getTenure());
                                    healthPremiumPojo.setExtra(response.getResult().get(koo).getExtra());
                                    healthPremiumPojo.setPlanCode(response.getResult().get(koo).getPlanCode());
                                    healthPremiumPojo.setSumAssured(response.getResult().get(koo).getSuminsured());
                                    healthPremiumPojo.setFeaturedPlan(response.getResult().get(koo).getFeatPlanName());
                                    healthPremiumPojo.setFeaturedSubPlan(response.getResult().get(koo).getFeatSubPlanName());
                                    healthPremiumPojo.setAppKey(response.getResult().get(koo).getAppKey());
                                    premiumList.add(healthPremiumPojo);
                                }
                            }
                        }
                    }
                    //CARE
                    if (response.getResult().get(0).getCompany().equalsIgnoreCase("care")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("care")) {
                                premiumList.remove(i);
                                break;
                            }
                        }
                        if (response.getResult().size() > 0) {
                            for (int koo = 0; koo < response.getResult().size(); koo++) {
                                if (response.getResult().get(koo).getGross() > 0) {
                                    HealthPremiumPojo healthPremiumPojo = new HealthPremiumPojo();

                                    healthPremiumPojo.setCompany(response.getResult().get(koo).getCompany());
                                    healthPremiumPojo.setImgPath(response.getResult().get(koo).getLogo());
                                    healthPremiumPojo.setPlanName(response.getResult().get(koo).getPlanName());
                                    healthPremiumPojo.setServiceTax(response.getResult().get(koo).getGst());
                                    healthPremiumPojo.setPremium(response.getResult().get(koo).getNet());
                                    healthPremiumPojo.setGross(response.getResult().get(koo).getGross());
                                    healthPremiumPojo.setPlanTenure(response.getTenure());
                                    healthPremiumPojo.setExtra(response.getResult().get(koo).getExtra());
                                    healthPremiumPojo.setPlanCode(response.getResult().get(koo).getPlanCode());
                                    healthPremiumPojo.setSumAssured(response.getResult().get(koo).getSuminsured());
                                    healthPremiumPojo.setFeaturedPlan(response.getResult().get(koo).getFeatPlanName());
                                    healthPremiumPojo.setFeaturedSubPlan(response.getResult().get(koo).getFeatSubPlanName());
                                    healthPremiumPojo.setAppKey(response.getResult().get(koo).getAppKey());
                                    premiumList.add(healthPremiumPojo);
                                }
                            }
                        }
                    }
                    //TATA
                    if (response.getResult().get(0).getCompany().equalsIgnoreCase("tata")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("tata")) {
                                premiumList.remove(i);
                                break;
                            }
                        }
                        if (response.getResult().size() > 0) {
                            for (int koo = 0; koo < response.getResult().size(); koo++) {
                                if (response.getResult().get(koo).getGross() > 0) {
                                    HealthPremiumPojo healthPremiumPojo = new HealthPremiumPojo();

                                    healthPremiumPojo.setCompany(response.getResult().get(koo).getCompany());
                                    healthPremiumPojo.setImgPath(response.getResult().get(koo).getLogo());
                                    healthPremiumPojo.setPlanName(response.getResult().get(koo).getPlanName());
                                    healthPremiumPojo.setServiceTax(response.getResult().get(koo).getGst());
                                    healthPremiumPojo.setPremium(response.getResult().get(koo).getNet());
                                    healthPremiumPojo.setGross(response.getResult().get(koo).getGross());
                                    healthPremiumPojo.setPlanTenure(response.getTenure());
                                    healthPremiumPojo.setExtra(response.getResult().get(koo).getExtra());
                                    healthPremiumPojo.setPlanCode(response.getResult().get(koo).getPlanCode());
                                    healthPremiumPojo.setSumAssured(response.getResult().get(koo).getSuminsured());
                                    healthPremiumPojo.setFeaturedPlan(response.getResult().get(koo).getFeatPlanName());
                                    healthPremiumPojo.setFeaturedSubPlan(response.getResult().get(koo).getFeatSubPlanName());
                                    healthPremiumPojo.setAppKey(response.getResult().get(koo).getAppKey());
                                    premiumList.add(healthPremiumPojo);
                                }
                            }
                        }
                    }
                    //ICICI
                    if (response.getResult().get(0).getCompany().equalsIgnoreCase("icici")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("icici")) {
                                premiumList.remove(i);
                                break;
                            }
                        }
                        if (response.getResult().size() > 0) {
                            for (int koo = 0; koo < response.getResult().size(); koo++) {
                                if (response.getResult().get(koo).getGross() > 0) {
                                    HealthPremiumPojo healthPremiumPojo = new HealthPremiumPojo();

                                    healthPremiumPojo.setCompany(response.getResult().get(koo).getCompany());
                                    healthPremiumPojo.setImgPath(response.getResult().get(koo).getLogo());
                                    healthPremiumPojo.setPlanName(response.getResult().get(koo).getPlanName());
                                    healthPremiumPojo.setServiceTax(response.getResult().get(koo).getGst());
                                    healthPremiumPojo.setPremium(response.getResult().get(koo).getNet());
                                    healthPremiumPojo.setGross(response.getResult().get(koo).getGross());
                                    healthPremiumPojo.setPlanTenure(response.getTenure());
                                    healthPremiumPojo.setExtra(response.getResult().get(koo).getExtra());
                                    healthPremiumPojo.setPlanCode(response.getResult().get(koo).getPlanCode());
                                    healthPremiumPojo.setSumAssured(response.getResult().get(koo).getSuminsured());
                                    healthPremiumPojo.setFeaturedPlan(response.getResult().get(koo).getFeatPlanName());
                                    healthPremiumPojo.setFeaturedSubPlan(response.getResult().get(koo).getFeatSubPlanName());
                                    healthPremiumPojo.setAppKey(response.getResult().get(koo).getAppKey());
                                    premiumList.add(healthPremiumPojo);
                                }
                            }
                        }
                    }
                    //Bajaj
                    if (response.getResult().get(0).getCompany().equalsIgnoreCase("bajaj")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("bajaj")) {
                                premiumList.remove(i);
                                break;
                            }
                        }
                        if (response.getResult().size() > 0) {
                            for (int koo = 0; koo < response.getResult().size(); koo++) {
                                if (response.getResult().get(koo).getGross() > 0) {
                                    HealthPremiumPojo healthPremiumPojo = new HealthPremiumPojo();

                                    healthPremiumPojo.setCompany(response.getResult().get(koo).getCompany());
                                    healthPremiumPojo.setImgPath(response.getResult().get(koo).getLogo());
                                    healthPremiumPojo.setPlanName(response.getResult().get(koo).getPlanName());
                                    healthPremiumPojo.setServiceTax(response.getResult().get(koo).getGst());
                                    healthPremiumPojo.setPremium(response.getResult().get(koo).getNet());
                                    healthPremiumPojo.setGross(response.getResult().get(koo).getGross());
                                    healthPremiumPojo.setPlanTenure(response.getTenure());
                                    healthPremiumPojo.setPlanCode(response.getResult().get(koo).getPlanCode());
                                    healthPremiumPojo.setSumAssured(response.getResult().get(koo).getSuminsured());
                                    healthPremiumPojo.setFeaturedPlan(response.getResult().get(koo).getFeatPlanName());
                                    healthPremiumPojo.setFeaturedSubPlan(response.getResult().get(koo).getFeatSubPlanName());
                                    healthPremiumPojo.setAppKey(response.getResult().get(koo).getAppKey());
                                    premiumList.add(healthPremiumPojo);
                                }
                            }
                        }
                    }
                    //Royal
                    if (response.getResult().get(0).getCompany().equalsIgnoreCase("royal")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("royal")) {
                                premiumList.remove(i);
                                break;
                            }
                        }
                        if (response.getResult().size() > 0) {
                            for (int koo = 0; koo < response.getResult().size(); koo++) {
                                if (response.getResult().get(koo).getGross() > 0) {
                                    HealthPremiumPojo healthPremiumPojo = new HealthPremiumPojo();

                                    healthPremiumPojo.setCompany(response.getResult().get(koo).getCompany());
                                    healthPremiumPojo.setImgPath(response.getResult().get(koo).getLogo());
                                    healthPremiumPojo.setPlanName(response.getResult().get(koo).getPlanName());
                                    healthPremiumPojo.setServiceTax(response.getResult().get(koo).getGst());
                                    healthPremiumPojo.setPremium(response.getResult().get(koo).getNet());
                                    healthPremiumPojo.setGross(response.getResult().get(koo).getGross());
                                    healthPremiumPojo.setPlanTenure(response.getTenure());
                                    healthPremiumPojo.setPlanCode(response.getResult().get(koo).getPlanCode());
                                    healthPremiumPojo.setSumAssured(response.getResult().get(koo).getSuminsured());
                                    healthPremiumPojo.setFeaturedPlan(response.getResult().get(koo).getFeatPlanName());
                                    healthPremiumPojo.setFeaturedSubPlan(response.getResult().get(koo).getFeatSubPlanName());
                                    healthPremiumPojo.setAppKey(response.getResult().get(koo).getAppKey());
                                    premiumList.add(healthPremiumPojo);
                                }
                            }
                        }
                    }
                    //apollo
                    if (response.getResult().get(0).getCompany().equalsIgnoreCase("hdfc")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("hdfc")) {
                                premiumList.remove(i);
                                break;
                            }
                        }
                        if (response.getResult().size() > 0) {
                            for (int koo = 0; koo < response.getResult().size(); koo++) {
                                if (response.getResult().get(koo).getGross() > 0) {
                                    HealthPremiumPojo healthPremiumPojo = new HealthPremiumPojo();

                                    healthPremiumPojo.setCompany(response.getResult().get(koo).getCompany());
                                    healthPremiumPojo.setImgPath(response.getResult().get(koo).getLogo());
                                    healthPremiumPojo.setPlanName(response.getResult().get(koo).getPlanName());
                                    healthPremiumPojo.setServiceTax(response.getResult().get(koo).getGst());
                                    healthPremiumPojo.setPremium(response.getResult().get(koo).getNet());
                                    healthPremiumPojo.setGross(response.getResult().get(koo).getGross());
                                    healthPremiumPojo.setPlanTenure(response.getTenure());
                                    healthPremiumPojo.setPlanCode(response.getResult().get(koo).getPlanCode());
                                    healthPremiumPojo.setSumAssured(response.getResult().get(koo).getSuminsured());
                                    healthPremiumPojo.setFeaturedPlan(response.getResult().get(koo).getFeatPlanName());
                                    healthPremiumPojo.setFeaturedSubPlan(response.getResult().get(koo).getFeatSubPlanName());
                                    healthPremiumPojo.setAppKey(response.getResult().get(koo).getAppKey());
                                    premiumList.add(healthPremiumPojo);
                                }
                            }
                        }
                    }
                    //sbi
                    if (response.getResult().get(0).getCompany().equalsIgnoreCase("sbi")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("sbi")) {
                                premiumList.remove(i);
                                break;
                            }
                        }
                        if (response.getResult().size() > 0) {
                            for (int koo = 0; koo < response.getResult().size(); koo++) {
                                if (response.getResult().get(koo).getGross() > 0) {
                                    HealthPremiumPojo healthPremiumPojo = new HealthPremiumPojo();

                                    healthPremiumPojo.setCompany(response.getResult().get(koo).getCompany());
                                    healthPremiumPojo.setImgPath(response.getResult().get(koo).getLogo());
                                    healthPremiumPojo.setPlanName(response.getResult().get(koo).getPlanName());
                                    healthPremiumPojo.setServiceTax(response.getResult().get(koo).getGst());
                                    healthPremiumPojo.setPremium(response.getResult().get(koo).getNet());
                                    healthPremiumPojo.setGross(response.getResult().get(koo).getGross());
                                    healthPremiumPojo.setPlanTenure(response.getTenure());
                                    healthPremiumPojo.setPlanCode(response.getResult().get(koo).getPlanCode());
                                    healthPremiumPojo.setSumAssured(response.getResult().get(koo).getSuminsured());
                                    healthPremiumPojo.setFeaturedPlan(response.getResult().get(koo).getFeatPlanName());
                                    healthPremiumPojo.setFeaturedSubPlan(response.getResult().get(koo).getFeatSubPlanName());
                                    healthPremiumPojo.setAppKey(response.getResult().get(koo).getAppKey());
                                    premiumList.add(healthPremiumPojo);
                                }
                            }
                        }
                    }
                    //universal
                    if (response.getResult().get(0).getCompany().equalsIgnoreCase("shompoo")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("shompoo")) {
                                premiumList.remove(i);
                                break;
                            }
                        }
                        if (response.getResult().size() > 0) {
                            for (int koo = 0; koo < response.getResult().size(); koo++) {
                                if (response.getResult().get(koo).getGross() > 0) {
                                    HealthPremiumPojo healthPremiumPojo = new HealthPremiumPojo();

                                    healthPremiumPojo.setCompany(response.getResult().get(koo).getCompany());
                                    healthPremiumPojo.setImgPath(response.getResult().get(koo).getLogo());
                                    healthPremiumPojo.setPlanName(response.getResult().get(koo).getPlanName());
                                    healthPremiumPojo.setServiceTax(response.getResult().get(koo).getGst());
                                    healthPremiumPojo.setPremium(response.getResult().get(koo).getNet());
                                    healthPremiumPojo.setGross(response.getResult().get(koo).getGross());
                                    healthPremiumPojo.setPlanTenure(response.getTenure());
                                    healthPremiumPojo.setPlanCode(response.getResult().get(koo).getPlanCode());
                                    healthPremiumPojo.setSumAssured(response.getResult().get(koo).getSuminsured());
                                    healthPremiumPojo.setFeaturedPlan(response.getResult().get(koo).getFeatPlanName());
                                    healthPremiumPojo.setFeaturedSubPlan(response.getResult().get(koo).getFeatSubPlanName());
                                    healthPremiumPojo.setAppKey(response.getResult().get(koo).getAppKey());
                                    premiumList.add(healthPremiumPojo);
                                }
                            }
                        }
                    }
                    //
                    if (response.getResult().get(0).getCompany().equalsIgnoreCase("starhealth")) {

                        for (int i = 0; i < premiumList.size(); i++) {
                            if (premiumList.get(i).getCompany().equalsIgnoreCase("starhealth")) {
                                premiumList.remove(i);
                                break;
                            }
                        }
                        if (response.getResult().size() > 0) {
                            for (int koo = 0; koo < response.getResult().size(); koo++) {
                                if (response.getResult().get(koo).getGross() > 0) {
                                    HealthPremiumPojo healthPremiumPojo = new HealthPremiumPojo();

                                    healthPremiumPojo.setCompany(response.getResult().get(koo).getCompany());
                                    healthPremiumPojo.setImgPath(response.getResult().get(koo).getLogo());
                                    healthPremiumPojo.setPlanName(response.getResult().get(koo).getPlanName());
                                    healthPremiumPojo.setPlanCode(response.getResult().get(koo).getPlanCode());
                                    healthPremiumPojo.setServiceTax(response.getResult().get(koo).getGst());
                                    healthPremiumPojo.setPremium(response.getResult().get(koo).getNet());
                                    healthPremiumPojo.setGross(response.getResult().get(koo).getGross());
                                    healthPremiumPojo.setPlanTenure(response.getTenure());
                                    healthPremiumPojo.setSumAssured(response.getResult().get(koo).getSuminsured());
                                    healthPremiumPojo.setFeaturedPlan(response.getResult().get(koo).getFeatPlanName());
                                    healthPremiumPojo.setFeaturedSubPlan(response.getResult().get(koo).getFeatSubPlanName());
                                    healthPremiumPojo.setAppKey(response.getResult().get(koo).getAppKey());

                                    premiumList.add(healthPremiumPojo);
                                }
                            }
                        }
                    }

                    if (premiumList.size() > 0) {

                        Collections.sort(premiumList, (lhs, rhs) ->
                                lhs.getGross().compareTo(rhs.getGross()));
                        starAdapter.notifyDataSetChanged();
                        recyclerStar.setAdapter(starAdapter);
                        findViewById(R.id.empty).setVisibility(View.GONE);
                    } else findViewById(R.id.empty).setVisibility(View.VISIBLE);
                }
                swipeContainer.setRefreshing(false);
                progressBar.setVisibility(View.GONE);

            }

            if (responses instanceof TempResponse) {
                TempResponse response = (TempResponse) responses;
                if (response.getStatus().equalsIgnoreCase("1")) {
                    if (premiumList.size() > 0)
                        premiumList.clear();

                    txtCompare.setText(getString(R.string.compare));
                    checked = 0;
                    checkedList.clear();
                    UtilClass.getInstance().setChecked(0);
                    getIcici();
                    getCare();
                    getTata();
                    getIffco();
                    getStarQuote();
                    getBajajQuote();
                    getDigitQuote();
                    getHdfcQuote();
                    getUniversal();
                    getSbiQuote();
                    getRoyalQuote();
                    getFuture();
                } else Toast.makeText(mContext, "" + response.getMsg(), Toast.LENGTH_SHORT).show();
                progressdialog.dismiss();
            }
            if (responses instanceof CommonResponse) {
                CommonResponse basicResponse = (CommonResponse) responses;
                if (basicResponse.getSuccess().equalsIgnoreCase("1")) {
                    Intent intent = new Intent(mContext, HealthPaymentActivity.class);
                    if (!TextUtils.isEmpty(userId))
                        mBundle.putString(AppUtils.HEALTH_LINK,
                                "https://www.squareinsurance.in/health_proposal/proposal?quote="
                                        + quotationId + "&role_id=" + userId + "&role_type="
                                        + userType + "&token" + AppUtils.HL_TOKEN);
                    else mBundle.putString(AppUtils.HEALTH_LINK,
                            "https://www.squareinsurance.in/health_proposal/proposal?quote="
                                    + quotationId);

                    intent.putExtras(mBundle);
                    startActivity(intent);
                }

                progressBar.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getIcici() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);
            try {
                HealthManager.getInstance().getHealthQuotation(mContext, "icici", quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getIffco() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);
            try {
                HealthManager.getInstance().getHealthQuotation(mContext, "iffco", quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getCare() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);
            try {
                HealthManager.getInstance().getHealthQuotation(mContext, "care", quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getTata() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);
            try {
                HealthManager.getInstance().getHealthQuotation(mContext, "tata", quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getBajajQuote() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);
            try {
                HealthManager.getInstance().getHealthQuotation(mContext, "bajaj", quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getDigitQuote() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);
            try {
                HealthManager.getInstance().getHealthQuotation(mContext, "digit", quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getUniversal() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);
            try {
                HealthManager.getInstance().getHealthQuotation(mContext, "shompoo", quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getSbiQuote() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);
            try {
                HealthManager.getInstance().getHealthQuotation(mContext, "sbi", quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getRoyalQuote() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);
            try {
                HealthManager.getInstance().getHealthQuotation(mContext, "royal", quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getFuture() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);
            try {
                HealthManager.getInstance().getHealthQuotation(mContext, "future", quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getHdfcQuote() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);
            try {
                HealthManager.getInstance().getHealthQuotation(mContext, "hdfc", quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getStarQuote() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);
            try {
                HealthManager.getInstance().getHealthQuotation(mContext, "starhealth", quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void proposalCompany() {
        if (AppUtils.isOnline(mContext)) {
            progressBar.setVisibility(View.VISIBLE);
            try {
                HealthManager.getInstance().companyProposal(mContext, quotationId, totalPremium,
                        netPremium, serviceTax, extra, planName, planCode, sumInsured, company);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void OnPremiumButtonClick(int position) {

        company = premiumList.get(position).getCompany();
        planTenure = premiumList.get(position).getPlanTenure();
        planName = premiumList.get(position).getPlanName();
        planCode = premiumList.get(position).getPlanCode();
        sumInsured = premiumList.get(position).getSumAssured();
        netPremium = premiumList.get(position).getPremium();
        serviceTax = premiumList.get(position).getServiceTax();
        totalPremium = premiumList.get(position).getGross();
        extra = premiumList.get(position).getExtra();
        imgPath = premiumList.get(position).getImgPath();
        policyTenure = premiumList.get(position).getPlanTenure();
        proposalCompany();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter, menu);
        MenuItem myItem = menu.findItem(R.id.action_edit);
        myItem.setVisible(false);
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
            onFilter();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onFilter() {
        final Dialog dialog = new Dialog(mContext,
                android.R.style.Theme_DeviceDefault_Light_DialogWhenLarge);
        dialog.setContentView(R.layout.health_filter);
        // Set dialog title
        dialog.setTitle("Set Filter");
        //Sum Insured
        SearchableSpinner spnSumAssured = dialog.findViewById(R.id.spnSumAssured);
        SearchableSpinner spnCity = dialog.findViewById(R.id.spnCity);
        SearchableSpinner spnPinCode = dialog.findViewById(R.id.spnPincode);
        Button btnUpdateFilter = dialog.findViewById(R.id.btnUpdateFilter);
        RadioButton two = dialog.findViewById(R.id.c2);
        RadioButton three = dialog.findViewById(R.id.c3);

        ArrayAdapter<String3WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, sumList);
        spnSumAssured.setAdapter(adapter);

        for (int i = 0; i < sumInsuredList.size(); i++) {
            String stateName = sumInsuredList.get(i).getSuminsured().trim();
            if (stateName.equalsIgnoreCase(sumInsured)) {
                spnSumAssured.setSelection(i);
                break;
            }
        }

        spnSumAssured.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String3WithTag stringWithTag = (String3WithTag) parent.getItemAtPosition(position);
                sumAssured = stringWithTag.string;
                mBundle.putString(AppUtils.SUM_ASSURED, sumAssured);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //city
        ArrayAdapter<String2WithTag> adapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, cities);
        spnCity.setAdapter(adapter1);

        for (int i = 0; i < cityList.size(); i++) {
            String stateName = cityList.get(i).getCityName().trim();
            if (stateName.equalsIgnoreCase(city)) {
                spnCity.setSelection(i);
                break;
            }
        }


        spnCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                city = (String) stringWithTag.tag;
                if (!TextUtils.isEmpty(city))
                    getPincode();
                mBundle.putString(AppUtils.HL_CITY, city);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //pincode
        ArrayAdapter<String2WithTag> adapter3 = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, pin);
        spnPinCode.setAdapter(adapter3);

        for (int i = 0; i < pincodeList.size(); i++) {
            String stateName = pincodeList.get(i).getId().trim();
            if (stateName.equalsIgnoreCase(pinId)) {
                spnPinCode.setSelection(i);
                break;
            }
        }

        spnPinCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                pinId = (String) stringWithTag.tag;
                mBundle.putString(AppUtils.PINCODE, pinId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnUpdateFilter.setOnClickListener(v -> {

            if (three.isSelected())
                planTenure = "3";
            else if (two.isSelected())
                planTenure = "2";
            else planTenure = "1";
            filterHealth();
            dialog.dismiss();
        });
        dialog.show();
    }

    private void initPincode() {
        for (int i = 0; i < pincodeList.size(); i++) {
            String stateName = pincodeList.get(i).getAreaName().trim();
            String stateId = pincodeList.get(i).getPincode();
            String pinArea = stateId + " " + stateName;
            String pId = pincodeList.get(i).getId();
            pin.add(new String2WithTag(pinArea, pId));
        }
    }

    public void getPincode() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getPin(mContext, city);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getCities() {
        if (AppUtils.isOnline(mContext)) {

            try {
                UserManager.getInstance().getCities(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();

        }
    }


    private void initCities() {
        for (int i = 0; i < cityList.size(); i++) {
            String stateName = cityList.get(i).getCityName().trim();
            String stateId = cityList.get(i).getCityName();
            cities.add(new String2WithTag(stateName, stateId));
        }
    }

    private void initSumInsured() {
        for (int i = 0; i < sumInsuredList.size(); i++) {
            String stateName = sumInsuredList.get(i).getSuminsured().trim();
            String stateId = sumInsuredList.get(i).getId();
            sumList.add(new String3WithTag(stateName, stateId));
        }
    }

    public void getSumInsured() {
        if (AppUtils.isOnline(mContext)) {

            try {
                HealthManager.getInstance().getHealthSum(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();

        }
    }

    public void filterHealth() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();

            try {
                HealthManager.getInstance().filterHealth(mContext, sumInsured, pinId, planTenure,
                        quotationId);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onBreakUpClick(int position) {

        Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setContentView(R.layout.health_break);
        dialog.setTitle("Premium Breakup");
        final Button btnCancel = dialog.findViewById(R.id.btnCancel);

        if (premiumList.size() > 0) {

            new HealthPremiumPojo();
            HealthPremiumPojo healthPremiumPojo;
            healthPremiumPojo = premiumList.get(position);


            final TextView txtFinalPremium = dialog.findViewById(R.id.txtFinalPremium);
            final TextView txtGST = dialog.findViewById(R.id.txtGST);
            final TextView txtNetPremium = dialog.findViewById(R.id.txtNetPremium);
            final TextView txtPlanName = dialog.findViewById(R.id.txtPlanName);
            final TextView txtPlanType = dialog.findViewById(R.id.txtPlanType);
            final ImageView imgInsureLogo = dialog.findViewById(R.id.imgInsureLogo);

            String imgPath = healthPremiumPojo.getImgPath();
            String finalPremium = "" + healthPremiumPojo.getGross();
            String gst = "" + healthPremiumPojo.getServiceTax();
            String premium = "" + healthPremiumPojo.getPremium();
            String planName = healthPremiumPojo.getPlanName();

            txtFinalPremium.setText(finalPremium);
            txtGST.setText(gst);
            txtNetPremium.setText(premium);
            txtPlanName.setText(planName);
            txtPlanType.setText(healthPlanType);

            Glide.with(mContext)
                    .load(imgPath)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(imgInsureLogo);

        }

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    public void onShareClick(View view) {

//        final Dialog dialog = new Dialog(mContext, android.R.style.Theme_Light);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.layout_share);
//        dialog.setTitle("Share Quotation");
//
//        // set the custom dialog components - text, image and button
//        final EditText edtEmail = dialog.findViewById(R.id.edtSEmail);
//        final EditText edtPhone = dialog.findViewById(R.id.edtSPhone);
//        final EditText edtLink = dialog.findViewById(R.id.edtSLink);
        final String link = String.format("%s%s", AppUtils.HEALTH_PRE_LINK, quotationId);
//        edtLink.setText(link);
//
//        final Button btnLink = dialog.findViewById(R.id.btnCopyLink);
//        final Button btnEmail = dialog.findViewById(R.id.btnSendEmail);
//        final Button btnPhone = dialog.findViewById(R.id.btnSendPhone);
//        final String[] shareType = new String[1];

//        btnEmail.setOnClickListener(v -> {
//
//
//            String shareEmail = edtEmail.getText().toString().trim();
//            if (TextUtils.isEmpty(shareEmail)) {
//                edtEmail.setError("Field can not be empty");
//                edtEmail.requestFocus();
//                return;
//            }
//            if (!AppUtils.isValidMail(shareEmail)) {
//                edtEmail.setError("Invalid Email");
//                edtEmail.requestFocus();
//                return;
//            }
//            shareType[0] = "email";
//            shareLink();
//            dialog.dismiss();
//        });
//        btnPhone.setOnClickListener(v -> {
//
//            String shareMobile = edtPhone.getText().toString().trim();
//            if (TextUtils.isEmpty(shareMobile)) {
//                edtPhone.setError("Field can not be empty");
//                edtPhone.requestFocus();
//                return;
//            }
//            if (!AppUtils.isValidMobile(shareMobile)) {
//                edtPhone.setError("Invalid Phone");
//                edtPhone.requestFocus();
//                return;
//
//            }
//            shareType[0] = "mobile";
//            shareLink();
//            dialog.dismiss();
//        });
//
//        btnLink.setOnClickListener(v -> {

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context
                .CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Square", link);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            Toast.makeText(mContext, "Link Copied", Toast.LENGTH_SHORT).show();
        }
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Square Insurance");

            shareIntent.putExtra(Intent.EXTRA_TEXT, link);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }
//        });
//
//        Button close = dialog.findViewById(R.id.dialogButtonClose);
//        // if button is clicked, close the custom dialog
//
//        close.setOnClickListener(view1 -> dialog.dismiss());
//
//        dialog.show();
    }

    public void shareLink() {
        Toast.makeText(mContext, "Fail, kindly copy link and share", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHospitalClick(int position) {
        if (premiumList.size() > 0) {
            HealthPremiumPojo healthPremiumPojo = premiumList.get(position);
            Intent intent = new Intent(mContext, FeaturesActivity.class);
            mBundle.putString(AppUtils.HL_COMPANY, healthPremiumPojo.getCompany());
            mBundle.putString(AppUtils.SUM_ASSURED, healthPremiumPojo.getSumAssured());
            mBundle.putString(AppUtils.HL_FT_PLAN, healthPremiumPojo.getFeaturedPlan());
            mBundle.putString(AppUtils.HEALTH_PLAN_NAME, healthPremiumPojo.getPlanName());
            mBundle.putString(AppUtils.HL_FT_SUB_PLAN, healthPremiumPojo.getFeaturedSubPlan());
            mBundle.putString(AppUtils.HL_APP_KEY, healthPremiumPojo.getAppKey());
            intent.putExtras(mBundle);
            startActivity(intent);
        }
    }

    @Override
    public void onBrochureClick(int position) {
        if (premiumList.size() > 0) {
            HealthPremiumPojo healthPremiumPojo = premiumList.get(position);
            Intent intent = new Intent(mContext, FeaturesActivity.class);
            mBundle.putString(AppUtils.HL_COMPANY, healthPremiumPojo.getCompany());
            mBundle.putString(AppUtils.SUM_ASSURED, healthPremiumPojo.getSumAssured());
            mBundle.putString(AppUtils.HL_FT_PLAN, healthPremiumPojo.getFeaturedPlan());
            mBundle.putString(AppUtils.HEALTH_PLAN_NAME, healthPremiumPojo.getPlanName());
            mBundle.putString(AppUtils.HL_FT_SUB_PLAN, healthPremiumPojo.getFeaturedSubPlan());
            mBundle.putString(AppUtils.HL_APP_KEY, healthPremiumPojo.getAppKey());
            intent.putExtras(mBundle);
            startActivity(intent);
        }
    }

    @Override
    public void onPlanBenefitClick(int position) {
        if (premiumList.size() > 0) {
            HealthPremiumPojo healthPremiumPojo = premiumList.get(position);
            Intent intent = new Intent(mContext, FeaturesActivity.class);
            mBundle.putString(AppUtils.HL_COMPANY, healthPremiumPojo.getCompany());
            mBundle.putString(AppUtils.SUM_ASSURED, healthPremiumPojo.getSumAssured());
            mBundle.putString(AppUtils.HL_FT_PLAN, healthPremiumPojo.getFeaturedPlan());
            mBundle.putString(AppUtils.HEALTH_PLAN_NAME, healthPremiumPojo.getPlanName());
            mBundle.putString(AppUtils.HL_FT_SUB_PLAN, healthPremiumPojo.getFeaturedSubPlan());
            mBundle.putString(AppUtils.HL_APP_KEY, healthPremiumPojo.getAppKey());
            intent.putExtras(mBundle);
            startActivity(intent);
        }
    }

    public void onClearAll(View view) {
        checked = 0;
        checkedList.clear();
        UtilClass.getInstance().setChecked(checked);
        txtCompare.setText(getString(R.string.compare));
        starAdapter.notifyDataSetChanged();
    }

    public void onCompare(View view) {
        if (checked > 1) {
            String companies, sumInsuredData, planData, subPlanData, premiumData, tenureData, appKey;
            String companies1, sumInsuredData1, planData1, subPlanData1, premiumData1, tenureData1,
                    appKey1;
            companies = sumInsuredData = planData = subPlanData = premiumData = tenureData = appKey
                    = "";
            companies1 = sumInsuredData1 = planData1 = subPlanData1 = premiumData1 = tenureData1
                    =appKey1= "";
            Bundle bundle = new Bundle();
            for (int i = 0; i < checkedList.size(); i++) {
                int index = checkedList.get(i);

                if (i == 0 && index >= 0) {
                    companies1 = premiumList.get(index).getCompany();
                    sumInsuredData1 = premiumList.get(index).getSumAssured();
                    planData1 = premiumList.get(index).getFeaturedPlan();
                    subPlanData1 = premiumList.get(index).getFeaturedSubPlan();
                    premiumData1 = premiumList.get(index).getPremium().toString();
                    tenureData1 = premiumList.get(index).getPlanTenure();
                    appKey1 = premiumList.get(index).getAppKey();
                } else {
                    companies = companies1 + "," + premiumList.get(index).getCompany();
                    sumInsuredData = sumInsuredData1 + "," + premiumList.get(index).getSumAssured();
                    planData = planData1 + "," + premiumList.get(index).getFeaturedPlan();
                    subPlanData = subPlanData1 + "," + premiumList.get(index).getFeaturedSubPlan();
                    premiumData = premiumData1 + "," + premiumList.get(index).getPremium().toString();
                    tenureData = tenureData1 + "," + premiumList.get(index).getPlanTenure();
                    appKey = appKey1 + "," + premiumList.get(index).getAppKey();

                    companies1 = companies;
                    sumInsuredData1 = sumInsuredData;
                    planData1 = planData;
                    subPlanData1 = subPlanData;
                    premiumData1 = premiumData;
                    tenureData1 = tenureData;
                    appKey1 = appKey;
                }
            }

            bundle.putString("compare company", companies);
            bundle.putString("compare sum insured", sumInsuredData);
            bundle.putString("compare plan", planData);
            bundle.putString("compare subPlan", subPlanData);
            bundle.putString("compare premium", premiumData);
            bundle.putString("compare tenure", tenureData);
            bundle.putString(AppUtils.HL_APP_KEY, appKey);

            Intent intent = new Intent(mContext, HealthCompareActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

        } else Toast.makeText(mContext, "Compare Minimum 2", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCheckChange(int position, boolean status) {
        if (status) {
            if (checked < 3) {
                ++checked;
                checkedList.add(position);
            }
        } else {
            if (checked > 0) {
                for (int i = 0; i < checkedList.size(); i++) {
                    if (checkedList.get(i) == position) {
                        checkedList.remove(i);
                        break;
                    }

                }
                --checked;
            }
        }
        txtCompare.setText(MessageFormat.format("Compare ({0})", checked));
        UtilClass.getInstance().setChecked(checked);
    }
}
