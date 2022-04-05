package com.square.pos.manager;

import android.content.Context;

import androidx.annotation.NonNull;

import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.model_crm.BasicBoolResponse;
import com.square.pos.model_travel.CountryList;
import com.square.pos.model_travel.TravelPremium;
import com.square.pos.model_travel.TravelQuote;
import com.square.pos.network.ApiClient;
import com.square.pos.network.ApiInterface;
import com.square.pos.utils.AppUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prahalad Chahar on 23/02/22.
 */

public class TravelManager {
    private static TravelManager instance;

    private TravelManager() {
    }

    public static TravelManager getInstance() {
        if (instance == null)
            instance = new TravelManager();
        return instance;
    }

    public void getCountry(final Context mContext) {

        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();
        try {
            Call<CountryList> call = apiService.getCountry(AppUtils.TOKEN);
            call.enqueue(new Callback<CountryList>() {
                @Override
                public void onResponse(@NonNull Call<CountryList> call,
                                       @NonNull Response<CountryList> response) {
                    if (response.isSuccessful()) {
                        CountryList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CountryList> call,
                                      @NonNull Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTravelQuoteIdFamily(final Context mContext, String userId, String userType,
                                       String name, String mobile, String email, String countryId,
                                       String startDate, String endDate, String qId,
                                       String sumInsured, String selfAge, String spouseAge,
                                       String child1Age, String child2Age, String child3Age,
                                       String fatherAge, String motherAge) {

        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        String formName = "form1";
        String planType = "family";
        String medicalCondition = "0";
        try {
            Call<TravelQuote> call = apiService.getTravelQuoteIdFamily(userId, userType, name,
                    mobile, email, planType, countryId, startDate, endDate, medicalCondition, qId,
                    sumInsured, selfAge, spouseAge, child1Age, child2Age, child3Age, fatherAge,
                    motherAge, formName, "app");
            call.enqueue(new Callback<TravelQuote>() {
                @Override
                public void onResponse(@NonNull Call<TravelQuote> call,
                                       @NonNull Response<TravelQuote> response) {
                    if (response.isSuccessful()) {
                        TravelQuote commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TravelQuote> call,
                                      @NonNull Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTravelQuoteIdGroup(final Context mContext, String userId, String userType,
                                      String name, String mobile, String email, String countryId,
                                      String startDate, String endDate, String qId,
                                      String sumInsured, String m1, String m2, String m3, String m4,
                                      String m5) {

        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();
        String formName = "form2";
        String planType = "group";
        String medicalCondition = "0";
        try {
            Call<TravelQuote> call = apiService.getTravelQuoteIdGroup(userId, userType, name,
                    mobile, email, planType, countryId, startDate, endDate, medicalCondition, qId,
                    sumInsured, m1, m2, m3, m4, m5, formName, "app");
            call.enqueue(new Callback<TravelQuote>() {
                @Override
                public void onResponse(@NonNull Call<TravelQuote> call,
                                       @NonNull Response<TravelQuote> response) {
                    if (response.isSuccessful()) {
                        TravelQuote commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TravelQuote> call,
                                      @NonNull Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTravelQuoteIdIndividual(final Context mContext, String userId, String userType,
                                           String name, String mobile, String email,
                                           String countryId, String startDate, String endDate,
                                           String qId, String sumInsured, String m1) {

        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();
        String formName = "form3";
        String planType = "family";
        String medicalCondition = "0";
        try {
            Call<TravelQuote> call = apiService.getTravelQuoteIdIndividual(userId, userType, name,
                    mobile, email, planType, countryId,
                    startDate, endDate, medicalCondition, qId, sumInsured, m1, formName, "app");
            call.enqueue(new Callback<TravelQuote>() {
                @Override
                public void onResponse(@NonNull Call<TravelQuote> call,
                                       @NonNull Response<TravelQuote> response) {
                    if (response.isSuccessful()) {
                        TravelQuote commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TravelQuote> call,
                                      @NonNull Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTravelPremium(final Context mContext, String quoteId, String planId) {

        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();
        try {
            Call<TravelPremium> call = apiService.getTravelPremium(quoteId, planId, "app");
            call.enqueue(new Callback<TravelPremium>() {
                @Override
                public void onResponse(@NonNull Call<TravelPremium> call,
                                       @NonNull Response<TravelPremium> response) {
                    if (response.isSuccessful()) {
                        TravelPremium commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TravelPremium> call,
                                      @NonNull Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTravelProposal(final Context mContext, String company, String geography,
                                     String premium, String pid, String qId) {

        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();
        try {
            Call<BasicBoolResponse> call = apiService.updateTravelProposal(company, geography, premium,
                    pid, qId, "app");

            call.enqueue(new Callback<BasicBoolResponse>() {
                @Override
                public void onResponse(@NonNull Call<BasicBoolResponse> call,
                                       @NonNull Response<BasicBoolResponse> response) {
                    if (response.isSuccessful()) {
                        BasicBoolResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<BasicBoolResponse> call,
                                      @NonNull Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
