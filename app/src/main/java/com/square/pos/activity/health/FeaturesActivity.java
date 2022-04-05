package com.square.pos.activity.health;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.adaptor.health.BrochureAdaptor;
import com.square.pos.adaptor.health.HospitalAdaptor;
import com.square.pos.adaptor.health.PlanCoverAdaptor;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.Health.HealthManager;
import com.square.pos.model_health.v2.Brochure;
import com.square.pos.model_health.v2.BrochureList;
import com.square.pos.model_health.v2.Cover;
import com.square.pos.model_health.v2.Hospital;
import com.square.pos.model_health.v2.HospitalCity;
import com.square.pos.model_health.v2.HospitalCityList;
import com.square.pos.model_health.v2.HospitalList;
import com.square.pos.model_health.v2.HospitalPincode;
import com.square.pos.model_health.v2.HospitalPincodeList;
import com.square.pos.model_health.v2.HospitalStateList;
import com.square.pos.model_health.v2.HospitalsState;
import com.square.pos.model_health.v2.PlanCover;
import com.square.pos.network.ApiClient;
import com.square.pos.network.ApiInterface;
import com.square.pos.utils.AppUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeaturesActivity extends AbstractActivity implements
        onRequestCompleteCallBackListener, BrochureAdaptor.OnProposalListener,
        BrochureAdaptor.OnClaimListener, BrochureAdaptor.OnPolicyWordingListener,
        BrochureAdaptor.OnBrochureClickListener {

    RecyclerView rvHospitalList, rvCoverList, rvBrochureList;
    ArrayList<Hospital> hospitalList = new ArrayList<>();
    ArrayList<Cover> coverList = new ArrayList<>();
    ArrayList<Cover> notCoverList = new ArrayList<>();
    ArrayList<Brochure> brochureList = new ArrayList<>();
    String companyName, pincode, sumInsured, planName1, planName, subPlan, state, city, ftn, appKey;
    ProgressDialog progressDialog;
    BrochureAdaptor brochureAdaptor;
    HospitalAdaptor hospitalAdaptor;
    PlanCoverAdaptor planCoverAdaptor;
    RelativeLayout rlHospitalList;
    Context mContext;
    Bundle mBundle;

    List<HospitalsState> stateList = new ArrayList<>();
    List<String> states = new ArrayList<>();
    List<HospitalPincode> pincodeList = new ArrayList<>();
    List<String> pincodes = new ArrayList<>();
    List<HospitalCity> citiesList = new ArrayList<>();
    List<String> cities = new ArrayList<>();
    com.toptoche.searchablespinnerlibrary.SearchableSpinner spnCity, spnState, spnPincode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features);
        Objects.requireNonNull(getSupportActionBar()).hide();

        mContext = this;
        mBundle = getIntent().getExtras();

        if (mBundle != null) {
            pincode = mBundle.getString(AppUtils.PINCODE_PIN);
            sumInsured = mBundle.getString(AppUtils.SUM_ASSURED);
            companyName = mBundle.getString(AppUtils.HL_COMPANY);
            planName = mBundle.getString(AppUtils.HL_FT_PLAN);
            subPlan = mBundle.getString(AppUtils.HL_FT_SUB_PLAN);
            planName1 = mBundle.getString(AppUtils.HEALTH_PLAN_NAME);
            ftn = mBundle.getString(AppUtils.HL_FT_N);
            appKey = mBundle.getString(AppUtils.HL_APP_KEY, "");
        }

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Almost there...");

        rvHospitalList = findViewById(R.id.rvHospitalList);
        rvCoverList = findViewById(R.id.rvCoverList);
        rvBrochureList = findViewById(R.id.rvBrochureList);

        spnState = findViewById(R.id.spnState);
        spnCity = findViewById(R.id.spnCity);
        spnCity = findViewById(R.id.spnCity);
        spnPincode = findViewById(R.id.spnPincode);
        rlHospitalList = findViewById(R.id.rlHospitalList);

        getState(mContext);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(mContext);
        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(mContext);

        rvHospitalList.setLayoutManager(mLayoutManager);
        rvHospitalList.setNestedScrollingEnabled(false);

        rvBrochureList.setLayoutManager(mLayoutManager1);
        rvBrochureList.setNestedScrollingEnabled(false);

        rvCoverList.setLayoutManager(mLayoutManager2);
        rvCoverList.setNestedScrollingEnabled(false);

        brochureAdaptor = new BrochureAdaptor(mContext, brochureList);
        brochureAdaptor.notifyDataSetChanged();
        rvCoverList.setAdapter(brochureAdaptor);
        getBrochure();

        hospitalAdaptor = new HospitalAdaptor(mContext, hospitalList);
        hospitalAdaptor.notifyDataSetChanged();
        rvHospitalList.setAdapter(hospitalAdaptor);
        getHospitalList();

        planCoverAdaptor = new PlanCoverAdaptor(mContext, coverList);
        planCoverAdaptor.notifyDataSetChanged();
        rvCoverList.setAdapter(planCoverAdaptor);
        getPlanBenefits();

        spnState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getCity(mContext);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getPincode(mContext);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnPincode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pincode = spnPincode.getSelectedItem().toString();
                getHospitalList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (!TextUtils.isEmpty(ftn)) {
            switch (ftn) {
                case "1":
                    rlHospitalList.setVisibility(View.VISIBLE);
                    findViewById(R.id.rlPlanList).setVisibility(View.GONE);
                    rvBrochureList.setVisibility(View.GONE);
                    break;
                case "2":
                    rlHospitalList.setVisibility(View.GONE);
                    findViewById(R.id.rlPlanList).setVisibility(View.GONE);
                    rvBrochureList.setVisibility(View.VISIBLE);
                    break;
                case "3":
                    rlHospitalList.setVisibility(View.GONE);
                    findViewById(R.id.rlPlanList).setVisibility(View.VISIBLE);
                    rvBrochureList.setVisibility(View.GONE);
                    break;
            }
        }
    }

    public void getPlanBenefits() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                HealthManager.getInstance().getPlanBenefits(mContext, companyName, planName,
                        subPlan, sumInsured, appKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            progressDialog.dismiss();
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getHospitalList() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                HealthManager.getInstance().hospitalList(mContext, companyName, pincode, appKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            progressDialog.dismiss();
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getBrochure() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                HealthManager.getInstance().brochureList(mContext, companyName, planName1,
                        planName, subPlan, appKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            progressDialog.dismiss();
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Object response) {

        if (response instanceof HospitalList) {
            HospitalList cResponse = (HospitalList) response;
            if (cResponse.getStatus().equals(1)) {
                if (hospitalList.size() > 0)
                    hospitalList.clear();

                if (cResponse.getHospitals() != null) {
                    hospitalList.addAll(cResponse.getHospitals());
                    hospitalAdaptor = new HospitalAdaptor(mContext, hospitalList);
                    hospitalAdaptor.notifyDataSetChanged();
                    rvHospitalList.setAdapter(hospitalAdaptor);
                }
            }
        }
        if (response instanceof BrochureList) {
            BrochureList cResponse = (BrochureList) response;
            if (cResponse.getStatus().equals(1)) {
                if (brochureList.size() > 0)
                    brochureList.clear();

                if (cResponse.getBrochure() != null) {
                    brochureList.addAll(cResponse.getBrochure());
                    brochureAdaptor = new BrochureAdaptor(mContext, brochureList);
                    brochureAdaptor.notifyDataSetChanged();
                    rvBrochureList.setAdapter(brochureAdaptor);
                }
            }

        }
        if (response instanceof PlanCover) {
            PlanCover cResponse = (PlanCover) response;
            if (cResponse.getStatus().equals(1)) {
                if (coverList.size() > 0)
                    coverList.clear();
                notCoverList.clear();

                if (cResponse.getCovers() != null) {
                    for (int index = 0; index < cResponse.getCovers().size(); index++) {
                        if (cResponse.getCovers().get(index).getFeatures().contains("Not"))
                            notCoverList.add(cResponse.getCovers().get(index));
                        else coverList.add(cResponse.getCovers().get(index));
                    }
                    planCoverAdaptor = new PlanCoverAdaptor(mContext, coverList);
                    planCoverAdaptor.notifyDataSetChanged();
                    rvCoverList.setAdapter(planCoverAdaptor);
                }
            }

            if (coverList.size() == 0)
                findViewById(R.id.rlPlanList).setVisibility(View.GONE);
            else findViewById(R.id.rlPlanList).setVisibility(View.VISIBLE);
        }
        progressDialog.dismiss();
    }

    public void onCoveredClick(View view) {
        planCoverAdaptor = new PlanCoverAdaptor(mContext, coverList);
        planCoverAdaptor.notifyDataSetChanged();
        rvCoverList.setAdapter(planCoverAdaptor);
    }

    public void onNotCoveredClick(View view) {
        planCoverAdaptor = new PlanCoverAdaptor(mContext, notCoverList);
        planCoverAdaptor.notifyDataSetChanged();
        rvCoverList.setAdapter(planCoverAdaptor);
    }

    public void onViewPlans(View view) {
        RelativeLayout rlPlanList = findViewById(R.id.rlPlanList);
        if (rlPlanList.getVisibility() == View.VISIBLE)
            rlPlanList.setVisibility(View.GONE);
        else rlPlanList.setVisibility(View.VISIBLE);
        if (coverList.size() == 0)
            Toast.makeText(mContext, "No Cover Data Found", Toast.LENGTH_SHORT).show();
    }

    public void onHospitalView(View view) {
        if (rlHospitalList.getVisibility() == View.VISIBLE)
            rlHospitalList.setVisibility(View.GONE);
        else rlHospitalList.setVisibility(View.VISIBLE);
        if (hospitalList.size() == 0)
            Toast.makeText(mContext, "No Hospital Data Found", Toast.LENGTH_SHORT).show();
    }

    public void onBrochureClick(View view) {
        if (rvBrochureList.getVisibility() == View.VISIBLE)
            rvBrochureList.setVisibility(View.GONE);
        else rvBrochureList.setVisibility(View.VISIBLE);

        if (brochureList.size() == 0)
            Toast.makeText(mContext, "No Brochure Data Found", Toast.LENGTH_SHORT).show();
    }

    public void onCloseClick(View view) {
        finish();
    }

    @Override
    public void onBBrochureClicked(int position, String url) {
        viewDoc(url);
    }

    @Override
    public void onPWordingClicked(int position, String url) {
        viewDoc(url);
    }

    @Override
    public void onProposerClicked(int position, String url) {
        viewDoc(url);
    }

    @Override
    public void onClaimClicked(int position, String url) {
        viewDoc(url);
    }

    private void viewDoc(String url) {
        try {
            Intent i = new Intent("android.intent.action.MAIN");
            i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
            i.addCategory("android.intent.category.LAUNCHER");
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            // Chrome is not installed
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(i);
        }

    }

    public void getState(final Context cxt) {
        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<HospitalStateList> call = apiService.getHospitalStateList(companyName);
            call.enqueue(new Callback<HospitalStateList>() {
                @Override
                public void onResponse(@NotNull Call<HospitalStateList> call,
                                       @NotNull Response<HospitalStateList> response) {
                    if (response.isSuccessful()) {
                        HospitalStateList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                        if (commonResponse != null) {
                            stateList = commonResponse.getHospitalsState();
                            if (stateList.size() > 0) {
                                for (int i = 0; i < stateList.size(); i++) {
                                    states.add(stateList.get(i).getState());
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                                        android.R.layout.simple_dropdown_item_1line, states);
                                spnState.setAdapter(adapter);
                                state = stateList.get(0).getState();
                                getCity(mContext);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<HospitalStateList> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCity(final Context cxt) {

        if (spnState.getSelectedItem() != null)
            state = spnState.getSelectedItem().toString();

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<HospitalCityList> call = apiService.getHospitalCityList(companyName, state);
            call.enqueue(new Callback<HospitalCityList>() {
                @Override
                public void onResponse(@NotNull Call<HospitalCityList> call,
                                       @NotNull Response<HospitalCityList> response) {
                    if (response.isSuccessful()) {
                        HospitalCityList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                        if (commonResponse != null) {

                            citiesList.clear();
                            cities.clear();
                            citiesList = commonResponse.getHospitalCity();
                            if (citiesList.size() > 0) {
                                for (int i = 0; i < citiesList.size(); i++) {
                                    cities.add(citiesList.get(i).getCity());
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                                        android.R.layout.simple_dropdown_item_1line, cities);
                                spnCity.setAdapter(adapter);
                                city = citiesList.get(0).getCity();
                                getPincode(mContext);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<HospitalCityList> call,
                                      @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPincode(final Context cxt) {

        if (spnState.getSelectedItem() != null)
            state = spnState.getSelectedItem().toString();
        if (spnCity.getSelectedItem() != null)
            city = spnCity.getSelectedItem().toString();

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<HospitalPincodeList> call = apiService.getHospitalPincodeList(companyName, state,
                    city);
            call.enqueue(new Callback<HospitalPincodeList>() {
                @Override
                public void onResponse(@NotNull Call<HospitalPincodeList> call,
                                       @NotNull Response<HospitalPincodeList> response) {
                    if (response.isSuccessful()) {
                        HospitalPincodeList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);

                        if (commonResponse != null) {
                            pincodeList.clear();
                            pincodes.clear();
                            pincodeList = commonResponse.getHospitalPincode();
                            if (pincodeList.size() > 0) {
                                for (int i = 0; i < pincodeList.size(); i++) {
                                    pincodes.add(pincodeList.get(i).getPincode());
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                                        android.R.layout.simple_dropdown_item_1line, pincodes);
                                spnPincode.setAdapter(adapter);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<HospitalPincodeList> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}