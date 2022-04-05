package com.dmw.noble.manager;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.model.Account;
import com.dmw.noble.model.AddonCover;
import com.dmw.noble.model.Bank;
import com.dmw.noble.model.BankList;
import com.dmw.noble.model.BasicResponse;
import com.dmw.noble.model.ChangeNo;
import com.dmw.noble.model.City;
import com.dmw.noble.model.CityList;
import com.dmw.noble.model.CommonResponse;
import com.dmw.noble.model.DocWallet;
import com.dmw.noble.model.DocsList;
import com.dmw.noble.model.FetchQuote;
import com.dmw.noble.model.Fuel;
import com.dmw.noble.model.Insurer;
import com.dmw.noble.model.InsurerList;
import com.dmw.noble.model.Login;
import com.dmw.noble.model.ManufactureList;
import com.dmw.noble.model.ModelList;
import com.dmw.noble.model.MotorKyc;
import com.dmw.noble.model.NonMotorList;
import com.dmw.noble.model.Otp;
import com.dmw.noble.model.PCVGCVList;
import com.dmw.noble.model.PaymentTrack;
import com.dmw.noble.model.PinList;
import com.dmw.noble.model.ProposalCompany;
import com.dmw.noble.model.ProposalPincode;
import com.dmw.noble.model.RTOList;
import com.dmw.noble.model.Relation;
import com.dmw.noble.model.Review;
import com.dmw.noble.model.Rto;
import com.dmw.noble.model.RtoLocation;
import com.dmw.noble.model.SbiMisd;
import com.dmw.noble.model.SendOtp;
import com.dmw.noble.model.Session;
import com.dmw.noble.model.SignUp;
import com.dmw.noble.model.State;
import com.dmw.noble.model.StateList;
import com.dmw.noble.model.TempResponse;
import com.dmw.noble.model.VahanData;
import com.dmw.noble.model.VariantList;
import com.dmw.noble.model.blog.BlogList;
import com.dmw.noble.model_health.HealthPolicy;
import com.dmw.noble.model_health.HealthQuoteList;
import com.dmw.noble.model_pos.AgentDetail;
import com.dmw.noble.model_pos.AgentDocuments;
import com.dmw.noble.model_pos.AgentPolicyList;
import com.dmw.noble.model_pos.CityListId;
import com.dmw.noble.model_pos.CustomerList;
import com.dmw.noble.model_pos.Employee;
import com.dmw.noble.model_pos.Leads;
import com.dmw.noble.model_pos.NewAgent;
import com.dmw.noble.model_pos.ProfileRequest;
import com.dmw.noble.model_pos.QuotationList;
import com.dmw.noble.model_pos.StateListId;
import com.dmw.noble.network.ApiClient;
import com.dmw.noble.network.ApiInterface;
import com.dmw.noble.training.model.ModuleMaster;
import com.dmw.noble.training.model.QuestionList;
import com.dmw.noble.utils.AppUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prahalad Kumar Chahar
 */
public class UserManager {

    private static UserManager instance;

    private UserManager() {
    }

    public static UserManager getInstance() {
        if (instance == null)
            instance = new UserManager();

        return instance;
    }

    private List<State> stateList = new ArrayList<>();
    private List<BankList> bankLists = new ArrayList<>();
    private List<City> citiesList = new ArrayList<>();
    private List<Relation> relationList = new ArrayList<>();

    public List<Insurer> getInsurerList() {
        return insurerList;
    }

    public void setInsurerList(List<Insurer> insurerList) {
        this.insurerList = insurerList;
    }

    private List<Insurer> insurerList = new ArrayList<>();

    public static AgentDetail getAgentDetail() {
        return agentDetail;
    }

    public void setAgentDetail(AgentDetail agentDetail) {
        UserManager.agentDetail = agentDetail;
    }

    private static AgentDetail agentDetail = new AgentDetail();

    public List<Rto> getRtoList() {
        return rtoList;
    }

    public void setRtoList(List<Rto> rtoList) {
        this.rtoList = rtoList;
    }

    private List<Rto> rtoList = new ArrayList<>();

    public List<Relation> getRelationList() {
        return relationList;
    }

    public void setRelationList(List<Relation> relationList) {
        this.relationList = relationList;
    }

    public List<State> getStateList() {
        return stateList;
    }

    public void setStateList(List<State> stateList) {
        this.stateList = stateList;
    }

    public List<BankList> getBankLists() {
        return bankLists;
    }

    public void setBankLists(List<BankList> bankLists) {
        this.bankLists = bankLists;
    }


    public List<City> getCitiesList() {
        return citiesList;
    }

    public void setCitiesList(List<City> citiesList) {
        this.citiesList = citiesList;
    }


    public void getBanks(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<Bank> call = apiService.getBankList();
            call.enqueue(new Callback<Bank>() {
                @Override
                public void onResponse(Call<Bank> call, Response<Bank> response) {
                    if (response.isSuccessful()) {
                        Bank commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                        if (commonResponse != null) {
                            bankLists = commonResponse.getBankList();
                            setBankLists(bankLists);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Bank> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getState(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<StateList> call = apiService.getStateList();
            call.enqueue(new Callback<StateList>() {
                @Override
                public void onResponse(Call<StateList> call, Response<StateList> response) {
                    if (response.isSuccessful()) {
                        StateList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                        if (commonResponse != null) {
                            stateList = commonResponse.getState();
                            setStateList(stateList);
                        }
                    }
                }

                @Override
                public void onFailure(Call<StateList> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getInspection(final Context cxt, String userType, String userId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<QuotationList> call = apiService.inspectionList(userType, userId);
            call.enqueue(new Callback<QuotationList>() {
                @Override
                public void onResponse(Call<QuotationList> call, Response<QuotationList> response) {
                    if (response.isSuccessful()) {
                        QuotationList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<QuotationList> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getStateWithId(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<StateListId> call = apiService.getStateListId();
            call.enqueue(new Callback<StateListId>() {
                @Override
                public void onResponse(Call<StateListId> call, Response<StateListId> response) {
                    if (response.isSuccessful()) {
                        StateListId commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);

                    }
                }

                @Override
                public void onFailure(Call<StateListId> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getReviewData(final Context cxt, String quoteId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<Review> call = apiService.getReviewData(quoteId, AppUtils.TOKEN);

            call.enqueue(new Callback<Review>() {
                @Override
                public void onResponse(Call<Review> call, Response<Review> response) {
                    if (response.isSuccessful()) {
                        Review commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<Review> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void submitQuestion(final Context cxt, String agentId, String questionId, String answer) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<BasicResponse> call = apiService.submitQuestion(agentId, questionId, answer);
            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(@NotNull Call<BasicResponse> call,
                                       @NotNull Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        BasicResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<BasicResponse> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void submitExam(final Context cxt, String agentId, String type, String marks, String time) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<BasicResponse> call = apiService.submitExam(agentId, type, marks, time);
            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(@NotNull Call<BasicResponse> call,
                                       @NotNull Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        BasicResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<BasicResponse> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getModuleMaster(final Context cxt, String agentId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<ModuleMaster> call = apiService.getModuleMaster(agentId);
            call.enqueue(new Callback<ModuleMaster>() {
                @Override
                public void onResponse(@NotNull Call<ModuleMaster> call,
                                       @NotNull Response<ModuleMaster> response) {
                    if (response.isSuccessful()) {
                        ModuleMaster commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ModuleMaster> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getQuestionList(final Context cxt, String agentId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<QuestionList> call = apiService.getQuestionList(agentId);
            call.enqueue(new Callback<QuestionList>() {
                @Override
                public void onResponse(@NotNull Call<QuestionList> call,
                                       @NotNull Response<QuestionList> response) {
                    if (response.isSuccessful()) {
                        QuestionList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<QuestionList> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void submitTraining(final Context cxt, String agentId, String timeInterval,
                               String moduleNumber) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<BasicResponse> call = apiService.submitTraining(agentId, timeInterval,
                    moduleNumber);
            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(@NonNull Call<BasicResponse> call,
                                       @NonNull Response<BasicResponse> response) {

                    if (response.isSuccessful()) {
                        BasicResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<BasicResponse> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPCV_GCV(final Context cxt) {
        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<PCVGCVList> call = apiService.getPCVGCV();
            call.enqueue(new Callback<PCVGCVList>() {
                @Override
                public void onResponse(@NonNull Call<PCVGCVList> call,
                                       @NonNull Response<PCVGCVList> response) {
                    if (response.isSuccessful()) {
                        PCVGCVList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);

                    }
                }

                @Override
                public void onFailure(@NonNull Call<PCVGCVList> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getProposerPincode(final Context mContext, String company, String pincode) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<ProposalPincode> call = apiService.getProposerPincode(company, pincode);
            call.enqueue(new Callback<ProposalPincode>() {
                @Override
                public void onResponse(@NotNull Call<ProposalPincode> call,
                                       @NotNull Response<ProposalPincode> response) {
                    if (response.isSuccessful()) {
                        ProposalPincode commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ProposalPincode> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPin(final Context cxt, String cityName) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<PinList> call = apiService.getPinList(cityName);
            call.enqueue(new Callback<PinList>() {
                @Override
                public void onResponse(Call<PinList> call, Response<PinList> response) {
                    if (response.isSuccessful()) {
                        PinList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<PinList> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCity(final Context cxt, String stateId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<CityList> call = apiService.getCitiesList(stateId);
            call.enqueue(new Callback<CityList>() {
                @Override
                public void onResponse(Call<CityList> call, Response<CityList> response) {
                    if (response.isSuccessful()) {
                        CityList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<CityList> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCitiesListId(final Context cxt, String stateId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<CityListId> call = apiService.getCitiesListId(stateId);
            call.enqueue(new Callback<CityListId>() {
                @Override
                public void onResponse(Call<CityListId> call, Response<CityListId> response) {
                    if (response.isSuccessful()) {
                        CityListId commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<CityListId> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCities(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<CityList> call = apiService.getCities();
            call.enqueue(new Callback<CityList>() {
                @Override
                public void onResponse(Call<CityList> call, Response<CityList> response) {
                    if (response.isSuccessful()) {
                        CityList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                        assert commonResponse != null;
                        citiesList = commonResponse.getCity();
                        if (citiesList != null)
                            if (citiesList.size() > 0)
                                setCitiesList(citiesList);
                    }
                }

                @Override
                public void onFailure(Call<CityList> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getReferenceList(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<Employee> call = apiService.getReference();
            call.enqueue(new Callback<Employee>() {
                @Override
                public void onResponse(Call<Employee> call, Response<Employee> response) {
                    if (response.isSuccessful()) {
                        Employee commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                        assert commonResponse != null;

                    }
                }

                @Override
                public void onFailure(Call<Employee> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getManufacture(final Context cxt, String type, String pcvType, String pcvCompany) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<ManufactureList> call = apiService.getManufacture(type, pcvType, pcvCompany);
            call.enqueue(new Callback<ManufactureList>() {
                @Override
                public void onResponse(Call<ManufactureList> call,
                                       Response<ManufactureList> response) {
                    if (response.isSuccessful()) {
                        ManufactureList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);

                    }
                }

                @Override
                public void onFailure(Call<ManufactureList> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getModel(final Context cxt, String make, String vehicleType, String pcvType,
                         String pcvCompany) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<ModelList> call = apiService.getModel(make, vehicleType, pcvType, pcvCompany);
            call.enqueue(new Callback<ModelList>() {
                @Override
                public void onResponse(Call<ModelList> call, Response<ModelList> response) {
                    if (response.isSuccessful()) {
                        ModelList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);

                    }
                }

                @Override
                public void onFailure(Call<ModelList> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void healthQuotationList(final Context cxt, String userType, String userId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<HealthQuoteList> call = apiService.healthQuotationList(userType, userId);
            call.enqueue(new Callback<HealthQuoteList>() {
                @Override
                public void onResponse(@NonNull Call<HealthQuoteList> call,
                                       @NonNull Response<HealthQuoteList> response) {
                    if (response.isSuccessful()) {
                        HealthQuoteList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<HealthQuoteList> call, @NonNull Throwable t) {
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getVariant(final Context cxt, String make, String model,
                           String vehicleType, String fuelType, String pcvCompany) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<VariantList> call = apiService.getVariant(make, model, vehicleType, fuelType,
                    pcvCompany);
            call.enqueue(new Callback<VariantList>() {
                @Override
                public void onResponse(@NotNull Call<VariantList> call,
                                       @NotNull Response<VariantList> response) {
                    if (response.isSuccessful()) {
                        VariantList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);

                    }
                }

                @Override
                public void onFailure(@NotNull Call<VariantList> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getFuel(final Context cxt, String make, String model, String vehicleType,
                        String pcvCompany) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<Fuel> call = apiService.getFuel(make, model, vehicleType, pcvCompany);
            call.enqueue(new Callback<Fuel>() {
                @Override
                public void onResponse(@NotNull Call<Fuel> call, @NotNull Response<Fuel> response) {
                    if (response.isSuccessful()) {
                        Fuel commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);

                    }
                }

                @Override
                public void onFailure(@NotNull Call<Fuel> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendOtp(final Context cxt, String mobile) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<SendOtp> call = apiService.sendOtp(mobile);
            call.enqueue(new Callback<SendOtp>() {
                @Override
                public void onResponse(Call<SendOtp> call, Response<SendOtp> response) {
                    if (response.isSuccessful()) {
                        SendOtp commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);

                    }
                }

                @Override
                public void onFailure(Call<SendOtp> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void verifyOtp(final Context cxt, String mobile, String otp) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<Otp> call = apiService.verifyOtp(mobile, otp);
            call.enqueue(new Callback<Otp>() {
                @Override
                public void onResponse(Call<Otp> call, Response<Otp> response) {
                    if (response.isSuccessful()) {
                        Otp commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<Otp> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getSbiData(final Context cxt, String lastInsertId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<SbiMisd> call = apiService.sbiData(lastInsertId);
            call.enqueue(new Callback<SbiMisd>() {
                @Override
                public void onResponse(@NonNull Call<SbiMisd> call,
                                       @NonNull Response<SbiMisd> response) {
                    if (response.isSuccessful()) {
                        SbiMisd commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SbiMisd> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getBlogList(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<BlogList> call = apiService.getBlogList();
            call.enqueue(new Callback<BlogList>() {
                @Override
                public void onResponse(@NonNull Call<BlogList> call, @NonNull Response<BlogList> response) {
                    if (response.isSuccessful()) {
                        BlogList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                        assert commonResponse != null;
//                        BlogList = commonResponse.getPosList();
//                        if (posList.size() > 0)
//                            setPosList(posList);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<BlogList> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updatePassword(final Context cxt, String mobile, String newPassword) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<BasicResponse> call = apiService.updatePassword(mobile, newPassword);
            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        BasicResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changePassword(final Context cxt, String mobile, String otp, String newPassword,
                               String oldPassword, String userId, String userType) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<BasicResponse> call = apiService.changePassword(mobile, otp, newPassword,
                    oldPassword, userId, userType);

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        BasicResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestEligibility(final Context cxt, String agentId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<CommonResponse> call = apiService.requestEligibility(agentId);
            call.enqueue(new Callback<CommonResponse>() {
                @Override
                public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                    if (response.isSuccessful()) {
                        CommonResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<CommonResponse> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProfile(final Context cxt, String agentId, String type, String values) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<ProfileRequest> call = apiService.updateProfile(agentId, type, values);
            call.enqueue(new Callback<ProfileRequest>() {
                @Override
                public void onResponse(Call<ProfileRequest> call, Response<ProfileRequest> response) {
                    if (response.isSuccessful()) {
                        ProfileRequest commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<ProfileRequest> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getInsurer(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<InsurerList> call = apiService.getInsurerList();
            call.enqueue(new Callback<InsurerList>() {
                @Override
                public void onResponse(Call<InsurerList> call, Response<InsurerList> response) {
                    if (response.isSuccessful()) {
                        InsurerList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                        if (commonResponse != null && commonResponse.getInsurer() != null)
                            setInsurerList(commonResponse.getInsurer());
                    }
                }

                @Override
                public void onFailure(Call<InsurerList> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getRto(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<RTOList> call = apiService.getRtoList();
            call.enqueue(new Callback<RTOList>() {
                @Override
                public void onResponse(@NonNull Call<RTOList> call,
                                       @NonNull Response<RTOList> response) {
                    if (response.isSuccessful()) {
                        RTOList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                        if (commonResponse != null) {
                            if (rtoList.size() > 0)
                                rtoList.clear();
                            setRtoList(rtoList);
                            rtoList = commonResponse.getRto();
                            setRtoList(rtoList);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<RTOList> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAccountType(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<Account> call = apiService.getAccType();
            call.enqueue(new Callback<Account>() {
                @Override
                public void onResponse(Call<Account> call, Response<Account> response) {
                    if (response.isSuccessful()) {
                        Account commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);

                    }
                }

                @Override
                public void onFailure(Call<Account> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void postLogin(final Context cxt, String mobile, String password) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<Login> call = apiService.postLogin(mobile, "2", password);
            call.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(@NonNull Call<Login> call, @NonNull Response<Login> response) {
                    if (response.isSuccessful()) {
                        Login commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Login> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void nobleLogin(final Context cxt, String mobile) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setUatCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<Login> call = apiService.nobleLogin(mobile, "PosLogin", "App");
            call.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(@NonNull Call<Login> call, @NonNull Response<Login> response) {
                    if (response.isSuccessful()) {
                        Login commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Login> call, @NonNull Throwable t) {
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void nobleSubPosLogin(final Context cxt, String encoded, String mobile) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setUatCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<Login> call = apiService.nobleSubPosLogin(encoded, mobile, "SubPosLogin",
                    "App");
            call.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(@NonNull Call<Login> call, @NonNull Response<Login> response) {
                    if (response.isSuccessful()) {
                        Login commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Login> call, @NonNull Throwable t) {
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void postSignUp(final Context cxt, String name, String mobile, String email,
                           String password, String address, String deviceId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<SignUp> call = apiService.postSignUp(name, mobile, email, password, address, deviceId);
            call.enqueue(new Callback<SignUp>() {
                @Override
                public void onResponse(Call<SignUp> call, Response<SignUp> response) {
                    if (response.isSuccessful()) {
                        SignUp commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<SignUp> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void offlineRequest(final Context mContext, String quotationId, String actionName) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<TempResponse> call = apiService.offlineRequest(quotationId, actionName,
                    "quote");
            call.enqueue(new Callback<TempResponse>() {
                @Override
                public void onResponse(@NonNull Call<TempResponse> call,
                                       @NonNull Response<TempResponse> response) {
                    if (response.isSuccessful()) {
                        TempResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TempResponse> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getDocsWalletList(final Context cxt, String userId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<DocsList> call = apiService.getDocsWalletList(userId, "agent");
            call.enqueue(new Callback<DocsList>() {
                @Override
                public void onResponse(Call<DocsList> call, Response<DocsList> response) {
                    if (response.isSuccessful()) {
                        DocsList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<DocsList> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAgentDetail(final Context cxt, String agentId, String userType) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<AgentDetail> call = apiService.getAgentDetail(agentId, userType);
            call.enqueue(new Callback<AgentDetail>() {
                @Override
                public void onResponse(@NonNull Call<AgentDetail> call,
                                       @NonNull Response<AgentDetail> response) {
                    if (response.isSuccessful()) {
                        AgentDetail commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                        setAgentDetail(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<AgentDetail> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getHealthPolicy(final Context cxt, String userId, String userType) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<HealthPolicy> call = apiService.getHealthPolicy(userId, userType);
            call.enqueue(new Callback<HealthPolicy>() {
                @Override
                public void onResponse(Call<HealthPolicy> call, Response<HealthPolicy> response) {
                    if (response.isSuccessful()) {
                        HealthPolicy commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<HealthPolicy> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAgentPolicy(final Context cxt, String userType, String userId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<AgentPolicyList> call = apiService.agentPolicyList(userType, userId);
            call.enqueue(new Callback<AgentPolicyList>() {
                @Override
                public void onResponse(Call<AgentPolicyList> call, Response<AgentPolicyList> response) {
                    if (response.isSuccessful()) {
                        AgentPolicyList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<AgentPolicyList> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCustomerList(final Context cxt, String userType, String userId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<CustomerList> call = apiService.getCustomerList(userType, userId);
            call.enqueue(new Callback<CustomerList>() {
                @Override
                public void onResponse(Call<CustomerList> call, Response<CustomerList> response) {
                    if (response.isSuccessful()) {
                        CustomerList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<CustomerList> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uploadKycDocument(final Context cxt, String panCard, String cheque,
                                  String aadharCardFront, String aadharBack,
                                  String electricBill, String certificate,
                                  String profile, String quoteId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        File filePan, fileAadharFront, fileDegree, fileCheque, fileProfile,
                fileSignature, fileAadharBack;

        MultipartBody.Part documentPan, documentCheque, documentAadharCardFront,
                documentAadharCardBack, documentElectricBill, documentCertificate, documentProfile;

        documentPan = documentAadharCardFront = documentElectricBill = documentCheque =
                documentProfile = documentAadharCardBack = documentCertificate = null;

        if (panCard != null) {
            try {
                filePan = new File(panCard);
                if (filePan.exists()) {
                    documentPan = MultipartBody.Part.createFormData("file[0]",
                            filePan.getName(),
                            RequestBody.create(filePan, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));

            documentPan = MultipartBody.Part.createFormData("attachment", "",
                    attachmentEmpty);
        }

        if (aadharCardFront != null) {
            try {
                fileAadharFront = new File(aadharCardFront);
                if (fileAadharFront.exists()) {
                    documentAadharCardFront = MultipartBody.Part.createFormData("file[2]",
                            fileAadharFront.getName(),
                            RequestBody.create(fileAadharFront,
                                    MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));

            documentAadharCardFront = MultipartBody.Part.createFormData("attachment",
                    "", attachmentEmpty);
        }
        if (electricBill != null) {
            try {
                fileDegree = new File(electricBill);
                if (fileDegree.exists()) {
                    documentElectricBill = MultipartBody.Part.createFormData("file[4]",
                            fileDegree.getName(),
                            RequestBody.create(fileDegree, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));

            documentElectricBill = MultipartBody.Part.createFormData("attachment", "",
                    attachmentEmpty);
        }

        if (cheque != null) {
            try {
                fileCheque = new File(cheque);
                if (fileCheque.exists()) {
                    documentCheque = MultipartBody.Part.createFormData("file[1]",
                            fileCheque.getName(),
                            RequestBody.create(fileCheque, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));

            documentCheque = MultipartBody.Part.createFormData("attachment", "",
                    attachmentEmpty);
        }
        if (profile != null) {
            try {
                fileProfile = new File(profile);
                if (fileProfile.exists()) {
                    documentProfile = MultipartBody.Part.createFormData("file[5]",
                            fileProfile.getName(),
                            RequestBody.create(fileProfile, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));

            documentProfile = MultipartBody.Part.createFormData("attachment", "",
                    attachmentEmpty);
        }
        if (aadharBack != null) {
            try {
                fileAadharBack = new File(aadharBack);
                if (fileAadharBack.exists()) {
                    documentAadharCardBack = MultipartBody.Part.createFormData("file[3]",
                            fileAadharBack.getName(),
                            RequestBody.create(fileAadharBack,
                                    MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));

            documentAadharCardBack = MultipartBody.Part.createFormData("attachment",
                    "", attachmentEmpty);
        }
        if (certificate != null) {
            try {
                fileSignature = new File(certificate);
                if (fileSignature.exists()) {
                    documentCertificate = MultipartBody.Part.createFormData("file[6]",
                            fileSignature.getName(),
                            RequestBody.create(fileSignature,
                                    MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));

            documentCertificate = MultipartBody.Part.createFormData("attachment", "",
                    attachmentEmpty);
        }

        RequestBody requestBodyId = RequestBody.create(quoteId,
                MediaType.parse("multipart/form-data"));

        Call<MotorKyc> call = apiService.uploadKyc(requestBodyId, documentPan, documentCheque,
                documentAadharCardFront, documentAadharCardBack, documentElectricBill,
                documentCertificate, documentProfile);
        call.enqueue(new Callback<MotorKyc>() {
            @Override
            public void onResponse(@NonNull Call<MotorKyc> call,
                                   @NonNull Response<MotorKyc> response) {

                if (response.isSuccessful()) {
                    MotorKyc assessmentListResponse = response.body();
                    ((onRequestCompleteCallBackListener) cxt).
                            onResponse(assessmentListResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MotorKyc> call, @NonNull Throwable t) {
                //onResponse(t);
                t.printStackTrace();
            }
        });
    }

    public void getQuotation(final Context cxt, String userType, String userId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<QuotationList> call = apiService.quotationList(userType, userId);
            call.enqueue(new Callback<QuotationList>() {
                @Override
                public void onResponse(Call<QuotationList> call, Response<QuotationList> response) {
                    if (response.isSuccessful()) {
                        QuotationList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<QuotationList> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLeads(final Context cxt, String userId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<Leads> call = apiService.leadList("agent", userId);
            call.enqueue(new Callback<Leads>() {
                @Override
                public void onResponse(Call<Leads> call, Response<Leads> response) {
                    if (response.isSuccessful()) {
                        Leads commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<Leads> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createAgent(final Context cxt, String firstName, String LastName, String email,
                            String mobile, String password, String alternateMobile, String gender,
                            String dob, String qualification, String panCard, String aadharCard,
                            String address1, String address2, String address3, String state,
                            String city, String pinCode, String bankName, String branchName,
                            String accountType, String accountNumber, String ifsc, String addedBy,
                            String addedById, String reference) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<NewAgent> call = apiService.createAgent(firstName, "", LastName, email,
                    mobile, password, alternateMobile, gender, dob, qualification, panCard,
                    aadharCard, address1, address2, address3, state, city, pinCode, bankName,
                    branchName, accountType, accountNumber, ifsc, addedBy, addedById, reference);
            call.enqueue(new Callback<NewAgent>() {
                @Override
                public void onResponse(Call<NewAgent> call, Response<NewAgent> response) {
                    if (response.isSuccessful()) {
                        NewAgent commonResponse = response.body();
                        if (commonResponse != null) {
                            Toast.makeText(cxt, "" + commonResponse.getMsg(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<NewAgent> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uploadDocuments(final Context cxt, File filePan, File fileAadharFront,
                                File fileDegree, File fileCheque, File fileProfile,
                                File fileSignature, File fileAadharBack, String agentId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        MultipartBody.Part pancard = MultipartBody.Part.createFormData("file_pan",
                filePan.getName(),
                RequestBody.create(filePan, MediaType.parse("multipart/form-data")));

        MultipartBody.Part aadharCardFront = MultipartBody.Part.createFormData("file_adhar",
                fileAadharFront.getName(),
                RequestBody.create(fileAadharFront, MediaType.parse("multipart/form-data")));

        MultipartBody.Part degree = MultipartBody.Part.createFormData("file_degree",
                fileDegree.getName(),
                RequestBody.create(fileDegree, MediaType.parse("multipart/form-data")));

        MultipartBody.Part cheque = MultipartBody.Part.createFormData("file_cheque",
                fileCheque.getName(),
                RequestBody.create(fileCheque, MediaType.parse("multipart/form-data")));

        MultipartBody.Part profile = MultipartBody.Part.createFormData("file_profile",
                fileProfile.getName(),
                RequestBody.create(fileProfile, MediaType.parse("multipart/form-data")));

        MultipartBody.Part signature = MultipartBody.Part.createFormData("file_signature",
                fileSignature.getName(),
                RequestBody.create(fileSignature, MediaType.parse("multipart/form-data")));

        MultipartBody.Part aadharCardBack =
                MultipartBody.Part.createFormData("file_adharback", fileAadharBack.getName(),
                        RequestBody.create(fileAadharBack, MediaType.parse("multipart/form-data")));

        RequestBody requestBodyId = RequestBody.create(agentId,
                MediaType.parse("multipart/form-data"));
        final RequestBody agent_id = RequestBody.create(agentId, okhttp3.MultipartBody.FORM);


        Call<AgentDocuments> call = apiService.uploadAgentDocument(requestBodyId, pancard,
                aadharCardFront, degree, cheque, profile, signature, aadharCardBack);
        call.enqueue(new Callback<AgentDocuments>() {
            @Override
            public void onResponse(Call<AgentDocuments> call, Response<AgentDocuments> response) {

                if (response.isSuccessful()) {
                    AgentDocuments assessmentListResponse = response.body();
                    ((onRequestCompleteCallBackListener) cxt).
                            onResponse(assessmentListResponse);
                    Log.d("SQUARE_LOG", "agent_id = " + agent_id.toString());
                }
            }

            @Override
            public void onFailure(Call<AgentDocuments> call, Throwable t) {
                //onResponse(t);
                t.printStackTrace();
            }
        });
    }

    public void uploadDocWallet(final Context cxt, String agentId, String addedBy, String docType,
                                String clientPartyName, String clientPartyContact,
                                String regNumber, String policyNumber, String engineChassisNumber,
                                String rCFront, String rCBack, String insurance, String gst,
                                String otherDoc, String mandate, String otherDocNm, String proposal,
                                String kyc) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        File fileRCFront, fileRCBack, fileInsurance, fileGst, fileOther, fileMandate, fileOtherDocNm, fileProposal,
                fileKyc;
        MultipartBody.Part documentRCFront, documentRCBack, documentInsurance, documentGst,
                documentOther, documentMandate, documentProposal, documentKyc, documentOtherDocNm;

        documentGst = documentInsurance = documentRCFront = documentRCBack = documentOther =
                documentMandate = documentProposal = documentKyc = documentOtherDocNm = null;

        if (rCFront != null) {
            try {
                fileRCFront = new File(rCFront);
                if (fileRCFront.exists()) {
                    documentRCFront = MultipartBody.Part.createFormData("rc_front_image",
                            fileRCFront.getName(),
                            RequestBody.create(MediaType.parse("multipart/form-data"), fileRCFront));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");

            documentRCFront = MultipartBody.Part.createFormData("attachment", "", attachmentEmpty);
        }

        if (rCBack != null) {
            try {
                fileRCBack = new File(rCBack);
                if (fileRCBack.exists()) {
                    documentRCBack = MultipartBody.Part.createFormData("rc_back_image",
                            fileRCBack.getName(),
                            RequestBody.create(MediaType.parse("multipart/form-data"), fileRCBack));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");

            documentRCBack = MultipartBody.Part.createFormData("attachment", "", attachmentEmpty);
        }
        if (otherDocNm != null) {
            try {
                fileOtherDocNm = new File(otherDocNm);
                if (fileOtherDocNm.exists()) {
                    documentOtherDocNm = MultipartBody.Part.createFormData("other_doc_nm",
                            fileOtherDocNm.getName(),
                            RequestBody.create(MediaType.parse("multipart/form-data"), fileOtherDocNm));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");

            documentOtherDocNm = MultipartBody.Part.createFormData("attachment", "", attachmentEmpty);
        }
        if (insurance != null) {
            try {
                fileInsurance = new File(insurance);
                if (fileInsurance.exists()) {
                    documentInsurance = MultipartBody.Part.createFormData("insurance",
                            fileInsurance.getName(),
                            RequestBody.create(MediaType.parse("multipart/form-data"), fileInsurance));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");

            documentInsurance = MultipartBody.Part.createFormData("attachment", "", attachmentEmpty);
        }


        if (gst != null) {
            try {
                fileGst = new File(gst);
                if (fileGst.exists()) {
                    documentGst = MultipartBody.Part.createFormData("gst",
                            fileGst.getName(),
                            RequestBody.create(fileGst, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");

            documentGst = MultipartBody.Part.createFormData("attachment", "", attachmentEmpty);
        }


        if (otherDoc != null) {
            try {
                fileOther = new File(otherDoc);
                if (fileOther.exists()) {
                    documentOther = MultipartBody.Part.createFormData("other_doc",
                            fileOther.getName(),
                            RequestBody.create(MediaType.parse("multipart/form-data"), fileOther));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");

            documentOther = MultipartBody.Part.createFormData("other_doc", "", attachmentEmpty);
        }


        if (mandate != null) {
            try {
                fileMandate = new File(mandate);
                if (fileMandate.exists()) {
                    documentMandate = MultipartBody.Part.createFormData("mandate",
                            fileMandate.getName(),
                            RequestBody.create(MediaType.parse("multipart/form-data"), fileMandate));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");

            documentMandate = MultipartBody.Part.createFormData("mandate", "", attachmentEmpty);
        }
        if (proposal != null) {
            try {
                fileProposal = new File(proposal);
                if (fileProposal.exists()) {
                    documentProposal = MultipartBody.Part.createFormData("proposal",
                            fileProposal.getName(),
                            RequestBody.create(MediaType.parse("multipart/form-data"), fileProposal));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");

            documentProposal = MultipartBody.Part.createFormData("proposal", "", attachmentEmpty);
        }
        if (kyc != null) {
            try {
                fileKyc = new File(kyc);
                if (fileKyc.exists()) {
                    documentKyc = MultipartBody.Part.createFormData("kyc",
                            fileKyc.getName(),
                            RequestBody.create(MediaType.parse("multipart/form-data"), fileKyc));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");

            documentKyc = MultipartBody.Part.createFormData("kyc", "", attachmentEmpty);
        }


        RequestBody requestAgentId = RequestBody.create(MediaType.parse("multipart/form-data"),
                agentId);

        RequestBody requestAddedBy = RequestBody.create(MediaType.parse("multipart/form-data"),
                addedBy);

        RequestBody requestDocType = RequestBody.create(MediaType.parse("multipart/form-data"),
                docType);

        RequestBody requestClientPartyName =
                RequestBody.create(clientPartyName, MediaType.parse("multipart/form-data"));

        RequestBody requestClientPartyContact =
                RequestBody.create(clientPartyContact, MediaType.parse("multipart/form-data"));

        RequestBody requestRegNumber =
                RequestBody.create(regNumber, MediaType.parse("multipart/form-data"));
        RequestBody requestEngineChassisNo =
                RequestBody.create(engineChassisNumber, MediaType.parse("multipart/form-data"));

        RequestBody requestPolicyNo = RequestBody.create(policyNumber, MediaType.parse("multipart/form-data"));


        Call<DocWallet> call = apiService.uploadDoc(requestAgentId, requestAddedBy,
                requestDocType, requestClientPartyName, requestClientPartyContact, requestRegNumber,
                requestPolicyNo, requestEngineChassisNo, documentRCFront, documentRCBack, documentInsurance, documentGst,
                documentOther, documentMandate, documentOtherDocNm, documentProposal, documentKyc);

        call.enqueue(new Callback<DocWallet>() {
            @Override
            public void onResponse(@NonNull Call<DocWallet> call, Response<DocWallet> response) {

                if (response.isSuccessful()) {
                    DocWallet assessmentListResponse = response.body();
                    ((onRequestCompleteCallBackListener) cxt).
                            onResponse(assessmentListResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DocWallet> call, @NonNull Throwable t) {
                //onResponse(t);
                t.printStackTrace();
            }
        });
    }

    public void updateBankDetail(final Context cxt, String agentId, String type, String values,
                                 String cheque) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        if (!TextUtils.isEmpty(cheque)) {
            File fileCheque = new File(cheque);

            MultipartBody.Part documentVehicle = MultipartBody.Part.createFormData("image_file",
                    fileCheque.getName(),
                    RequestBody.create(MediaType.parse("multipart/form-data"), fileCheque));

            RequestBody requestAgentId = RequestBody.create(MediaType.parse("multipart/form-data"),
                    agentId);

            RequestBody requestType = RequestBody.create(MediaType.parse("multipart/form-data"),
                    type);

            RequestBody requestValues = RequestBody.create(MediaType.parse("multipart/form-data"),
                    values);


            Call<ProfileRequest> call = apiService.updateBankDetail(requestAgentId, requestType,
                    requestValues, documentVehicle);
            call.enqueue(new Callback<ProfileRequest>() {
                @Override
                public void onResponse(Call<ProfileRequest> call, Response<ProfileRequest> response) {

                    if (response.isSuccessful()) {
                        ProfileRequest assessmentListResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(assessmentListResponse);
                    }
                }

                @Override
                public void onFailure(Call<ProfileRequest> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } else {
            JsonObject jsonObject = new JsonObject();
            RequestBody requestAgentId = RequestBody.create(agentId, MediaType.parse("multipart/form-data"));

            RequestBody requestType = RequestBody.create(type,
                    MediaType.parse("multipart/form-data"));

            RequestBody requestValues = RequestBody.create(MediaType.parse("multipart/form-data"),
                    values);


            Call<ProfileRequest> call = apiService.updateBankDetail1(requestAgentId, requestType,
                    requestValues);

            call.enqueue(new Callback<ProfileRequest>() {
                @Override
                public void onResponse(Call<ProfileRequest> call, Response<ProfileRequest> response) {

                    if (response.isSuccessful()) {
                        ProfileRequest assessmentListResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(assessmentListResponse);
                    }
                }

                @Override
                public void onFailure(Call<ProfileRequest> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        }


    }

    public void updateDocuments(final Context cxt, String agentId, String type, String values,
                                String pan, String aadhar, String aadharBack, String qualification,
                                String image, String signature) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        File filePan, fileAadhar, fileAadharBack, fileQualification, fileImage, fileSignature;
        MultipartBody.Part documentPan, documentAadhar, documentAadharBack, documentQualification,
                documentSignature, documentImage;

        documentQualification = documentAadharBack = documentPan = documentAadhar = documentImage =
                documentSignature = null;


        if (pan != null) {
            try {
                filePan = new File(pan);
                if (filePan.exists()) {
                    documentPan = MultipartBody.Part.createFormData("image_file",
                            filePan.getName(),
                            RequestBody.create(filePan, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("", MediaType.parse("text/plain"));

            documentPan = MultipartBody.Part.createFormData("attachment", "", attachmentEmpty);
        }


        if (aadhar != null) {
            try {
                fileAadhar = new File(aadhar);
                if (fileAadhar.exists()) {
                    documentAadhar = MultipartBody.Part.createFormData("image_file1",
                            fileAadhar.getName(),
                            RequestBody.create(fileAadhar, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("", MediaType.parse("text/plain"));

            documentAadhar = MultipartBody.Part.createFormData("attachment", "", attachmentEmpty);
        }


        if (aadharBack != null) {
            try {
                fileAadharBack = new File(aadharBack);
                if (fileAadharBack.exists()) {
                    documentAadharBack = MultipartBody.Part.createFormData("image_file2",
                            fileAadharBack.getName(),
                            RequestBody.create(fileAadharBack, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("", MediaType.parse("text/plain"));

            documentAadharBack = MultipartBody.Part.createFormData("attachment", "", attachmentEmpty);
        }


        if (qualification != null) {
            try {
                fileQualification = new File(qualification);
                if (fileQualification.exists()) {
                    documentQualification = MultipartBody.Part.createFormData("image_file3",
                            fileQualification.getName(),
                            RequestBody.create(fileQualification, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("", MediaType.parse("text/plain"));

            documentQualification = MultipartBody.Part.createFormData("attachment", "", attachmentEmpty);
        }


        if (image != null) {
            try {
                fileImage = new File(image);
                if (fileImage.exists()) {
                    documentImage = MultipartBody.Part.createFormData("image_file4",
                            fileImage.getName(),
                            RequestBody.create(fileImage, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("", MediaType.parse("text/plain"));

            documentImage = MultipartBody.Part.createFormData("attachment", "", attachmentEmpty);
        }


        if (signature != null) {
            try {
                fileSignature = new File(signature);
                if (fileSignature.exists()) {
                    documentSignature = MultipartBody.Part.createFormData("image_file5",
                            fileSignature.getName(),
                            RequestBody.create(fileSignature, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("", MediaType.parse("text/plain"));

            documentSignature = MultipartBody.Part.createFormData("attachment", "", attachmentEmpty);
        }


        RequestBody requestAgentId = RequestBody.create(agentId,
                MediaType.parse("multipart/form-data"));

        RequestBody requestType = RequestBody.create(type, MediaType.parse("multipart/form-data"));

        RequestBody requestValues = RequestBody.create(values, MediaType.parse("multipart/form-data")
        );


        Call<ProfileRequest> call = apiService.updateDocument(requestAgentId, requestType,
                requestValues, documentPan, documentAadhar, documentAadharBack,
                documentQualification, documentImage, documentSignature);

        call.enqueue(new Callback<ProfileRequest>() {
            @Override
            public void onResponse(@NotNull Call<ProfileRequest> call, @NotNull Response<ProfileRequest> response) {

                if (response.isSuccessful()) {
                    ProfileRequest assessmentListResponse = response.body();
                    ((onRequestCompleteCallBackListener) cxt).
                            onResponse(assessmentListResponse);
                }
            }

            @Override
            public void onFailure(Call<ProfileRequest> call, Throwable t) {
                //onResponse(t);
                t.printStackTrace();
            }
        });
    }

    public void updateProfilePhoto(final Context cxt, String agentId, String photo) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        File filePan;
        MultipartBody.Part documentPan = null;

        if (photo != null) {
            try {
                filePan = new File(photo);
                if (filePan.exists()) {
                    documentPan = MultipartBody.Part.createFormData("photo",
                            filePan.getName(),
                            RequestBody.create(filePan, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("", MediaType.parse("text/plain"));
            documentPan = MultipartBody.Part.createFormData("attachment", "", attachmentEmpty);
        }

        RequestBody requestAgentId = RequestBody.create(agentId, MediaType.parse("multipart/form-data"));

        Call<BasicResponse> call = apiService.updateProfile(requestAgentId, documentPan);

        call.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(@NotNull Call<BasicResponse> call, @NotNull Response<BasicResponse> response) {

                if (response.isSuccessful()) {
                    BasicResponse assessmentListResponse = response.body();
                    ((onRequestCompleteCallBackListener) cxt).
                            onResponse(assessmentListResponse);
                }
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable t) {
                //onResponse(t);
                t.printStackTrace();
            }
        });
    }

    public void proposalCompany(final Context cxt, String companyName, Float premium,
                                String policyStartDate, String policyEndDate, String idv,
                                String tenure, String status, Float net, Float gst, Float od,
                                Float tp, String flag, String pa, String quotationId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            RequestBody requestCompanyName = RequestBody.create(companyName, MediaType.parse("multipart/form-data"));
            RequestBody requestPremium = RequestBody.create(String.valueOf(premium), MediaType.parse("multipart/form-data"));
            RequestBody requestPolicyStartDate = RequestBody.create(policyStartDate, MediaType.parse("multipart/form-data"));
            RequestBody requestPolicyEndDate = RequestBody.create(policyEndDate, MediaType.parse("multipart/form-data"));
            RequestBody requestIdv = RequestBody.create(idv, MediaType.parse("multipart/form-data"));
            RequestBody requestTenure = RequestBody.create(tenure, MediaType.parse("multipart/form-data"));
            RequestBody requestStatus = RequestBody.create(status, MediaType.parse("multipart/form-data"));
            RequestBody requestNet = RequestBody.create(String.valueOf(net), MediaType.parse("multipart/form-data"));
            RequestBody requestGst = RequestBody.create(String.valueOf(gst), MediaType.parse("multipart/form-data"));
            RequestBody requestOd = RequestBody.create(String.valueOf(od), MediaType.parse("multipart/form-data"));
            RequestBody requestTp = RequestBody.create(String.valueOf(tp), MediaType.parse("multipart/form-data"));
            RequestBody requestFlag = RequestBody.create(flag, MediaType.parse("multipart/form-data"));
            RequestBody requestPa = RequestBody.create(pa, MediaType.parse("multipart/form-data"));
            RequestBody requestQuote = RequestBody.create(quotationId, MediaType.parse("multipart/form-data"));


            Call<ProposalCompany> call = apiService.proposalCompany(requestCompanyName, requestPremium, requestPolicyStartDate,
                    requestPolicyEndDate, requestIdv, requestTenure, requestStatus, requestNet, requestGst, requestOd,
                    requestTp, requestFlag, requestPa, requestQuote);
            call.enqueue(new Callback<ProposalCompany>() {
                @Override
                public void onResponse(Call<ProposalCompany> call, Response<ProposalCompany> response) {
                    if (response.isSuccessful()) {
                        ProposalCompany commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<ProposalCompany> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getRtoLocation(final Context mContext, String quotationId) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<RtoLocation> call = apiService.getRtoLocation(quotationId);
            call.enqueue(new Callback<RtoLocation>() {
                @Override
                public void onResponse(@NotNull Call<RtoLocation> call, @NotNull Response<RtoLocation> response) {
                    if (response.isSuccessful()) {
                        RtoLocation commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<RtoLocation> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validRto(final Context mContext, String rtoName) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<CommonResponse> call = apiService.validRto(rtoName);
            call.enqueue(new Callback<CommonResponse>() {
                @Override
                public void onResponse(@NotNull Call<CommonResponse> call, @NotNull Response<CommonResponse> response) {
                    if (response.isSuccessful()) {
                        CommonResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CommonResponse> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void verifyMobile(final Context mContext, String quotationId) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<SendOtp> call = apiService.verifyMobile(quotationId);
            call.enqueue(new Callback<SendOtp>() {
                @Override
                public void onResponse(Call<SendOtp> call, Response<SendOtp> response) {
                    if (response.isSuccessful()) {
                        SendOtp commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<SendOtp> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changePassOtp(final Context mContext, String mobile) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<SendOtp> call = apiService.changePassOtp(mobile);
            call.enqueue(new Callback<SendOtp>() {
                @Override
                public void onResponse(Call<SendOtp> call, Response<SendOtp> response) {
                    if (response.isSuccessful()) {
                        SendOtp commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<SendOtp> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void verifyPosMobile(final Context mContext, String mobile) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<SendOtp> call = apiService.verifyPosMobile(mobile);
            call.enqueue(new Callback<SendOtp>() {
                @Override
                public void onResponse(Call<SendOtp> call, Response<SendOtp> response) {
                    if (response.isSuccessful()) {
                        SendOtp commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<SendOtp> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void verifyMobileOtp(final Context mContext, String quotationId, String otp) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<CommonResponse> call = apiService.verifyMobileOtp(quotationId, otp);
            call.enqueue(new Callback<CommonResponse>() {
                @Override
                public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                    if (response.isSuccessful()) {
                        CommonResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<CommonResponse> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeNo(final Context mContext, String mobile, String quotationId) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<ChangeNo> call = apiService.changeNumber(mobile, quotationId);
            call.enqueue(new Callback<ChangeNo>() {
                @Override
                public void onResponse(Call<ChangeNo> call, Response<ChangeNo> response) {
                    if (response.isSuccessful()) {
                        ChangeNo commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<ChangeNo> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uploadPolicyDocuments(final Context cxt, String agentId, String quoteId, String businessType,
                                      String invoice, String prePolicy, String rCFront,
                                      String rCBack) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        File fileInvoice, filePrePolicy, fileRCFront, fileRCBack;
        MultipartBody.Part documentInvoice, documentPrePolicy, documentRCFront, documentRCBack;
        documentInvoice = documentPrePolicy = documentRCFront = documentRCBack = null;

        if (invoice != null) {
            try {
                fileInvoice = new File(invoice);
                if (fileInvoice.exists()) {
                    fileInvoice = Compressor.getDefault(cxt).compressToFile(fileInvoice);
                    documentInvoice = MultipartBody.Part.createFormData("invoice_image",
                            fileInvoice.getName(),
                            RequestBody.create(MediaType.parse("multipart/form-data"), fileInvoice));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");

            documentInvoice = MultipartBody.Part.createFormData("attachment", "", attachmentEmpty);
        }

        if (prePolicy != null) {
            try {
                filePrePolicy = new File(prePolicy);
                if (filePrePolicy.exists()) {
                    documentPrePolicy = MultipartBody.Part.createFormData("previous_policy_pdf",
                            filePrePolicy.getName(),
                            RequestBody.create(MediaType.parse("multipart/form-data"), filePrePolicy));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");

            documentPrePolicy = MultipartBody.Part.createFormData("attachment", "", attachmentEmpty);
        }

        if (rCFront != null) {
            try {
                fileRCFront = new File(rCFront);
                if (fileRCFront.exists()) {

                    fileRCFront = Compressor.getDefault(cxt).compressToFile(fileRCFront);

                    documentRCFront = MultipartBody.Part.createFormData("rc_front_image", fileRCFront.getName(),
                            RequestBody.create(MediaType.parse("multipart/form-data"), fileRCFront));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");

            documentRCFront = MultipartBody.Part.createFormData("attachment", "", attachmentEmpty);
        }


        if (rCBack != null) {
            try {
                fileRCBack = new File(rCBack);
                if (fileRCBack.exists()) {
                    fileRCBack = Compressor.getDefault(cxt).compressToFile(fileRCBack);
                    documentRCBack =
                            MultipartBody.Part.createFormData("rc_back_image", fileRCBack.getName(),
                                    RequestBody.create(MediaType.parse("multipart/form-data"), fileRCBack));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");

            documentRCBack = MultipartBody.Part.createFormData("attachment", "", attachmentEmpty);
        }


        RequestBody requestAgentId = RequestBody.create(MediaType.parse("multipart/form-data"),
                agentId);

        RequestBody requestQuoteId = RequestBody.create(MediaType.parse("multipart/form-data"),
                quoteId);

        RequestBody requestBusinessType = RequestBody.create(MediaType.parse("multipart/form-data"),
                businessType);


        Call<CommonResponse> call = apiService.uploadPolicyDocument(requestAgentId, requestQuoteId,
                requestBusinessType, documentInvoice, documentPrePolicy, documentRCFront, documentRCBack);

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                if (response.isSuccessful()) {
                    CommonResponse assessmentListResponse = response.body();
                    ((onRequestCompleteCallBackListener) cxt).
                            onResponse(assessmentListResponse);
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                //onResponse(t);
                t.printStackTrace();
            }
        });
    }

    //////////////////////////////////////////////////////////////////////////////////////////////


    public void getPaymentTrack(final Context mContext, String quotationId, String url) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<PaymentTrack> call = apiService.getPaymentStatus(quotationId, url);
            call.enqueue(new Callback<PaymentTrack>() {
                @Override
                public void onResponse(Call<PaymentTrack> call, Response<PaymentTrack> response) {
                    if (response.isSuccessful()) {
                        PaymentTrack commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<PaymentTrack> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSession(final Context mContext, String quotationId, String linkUrl) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<Session> call = apiService.setSession(quotationId, linkUrl);
            call.enqueue(new Callback<Session>() {
                @Override
                public void onResponse(Call<Session> call, Response<Session> response) {
                    if (response.isSuccessful()) {
                        Session commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<Session> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMotorPaymentLink(final Context mContext, String quotationId) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<Session> call = apiService.getMotorPaymentLink(quotationId, "app");
            call.enqueue(new Callback<Session>() {
                @Override
                public void onResponse(@NotNull Call<Session> call,
                                       @NotNull Response<Session> response) {
                    if (response.isSuccessful()) {
                        Session commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<Session> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shareLink(final Context mContext, String quotationId, String email, String phone,
                          String vehicle, String type) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<BasicResponse> call = apiService.shareLink(quotationId, email, phone, vehicle, type);
            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        BasicResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCovers(final Context mContext, String quotationId) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<AddonCover> call = apiService.getCovers(quotationId);

            call.enqueue(new Callback<AddonCover>() {
                @Override
                public void onResponse(Call<AddonCover> call, Response<AddonCover> response) {
                    if (response.isSuccessful()) {
                        AddonCover commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    } else Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<AddonCover> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getNonMotorLead(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<NonMotorList> call = apiService.getNonMotorLead();
            call.enqueue(new Callback<NonMotorList>() {
                @Override
                public void onResponse(Call<NonMotorList> call,
                                       Response<NonMotorList> response) {
                    if (response.isSuccessful()) {
                        NonMotorList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<NonMotorList> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void nonMotorLead(final Context mContext, String insuranceType, String companyName,
                             String name, String mobile, String email, String city,
                             String remarks) {

        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<BasicResponse> call = apiService.nonMotorLead(insuranceType, companyName, name,
                    mobile, email, city, remarks);
            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(@NonNull Call<BasicResponse> call,
                                       @NonNull Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        BasicResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<BasicResponse> call,
                                      @NonNull Throwable throwable) {
                    //onResponse(t);
                    throwable.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getQuery(final Context mContext, String name, String mobile, String email, String remarks) {

        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();
        String type = "app";
        try {
            Call<CommonResponse> call = apiService.getQuery(type, name, mobile, email, remarks);
            call.enqueue(new Callback<CommonResponse>() {
                @Override
                public void onResponse(@NonNull Call<CommonResponse> call,
                                       @NonNull Response<CommonResponse> response) {
                    if (response.isSuccessful()) {
                        CommonResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CommonResponse> call,
                                      @NonNull Throwable throwable) {
                    //onResponse(t);
                    throwable.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void raiseLifeTravelQuery(final Context mContext, String name, String mobile, String email,
                                     String userId, String userType, String remarks, String type) {

        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();
        try {
            Call<BasicResponse> call = apiService.raiseLifeTravelQuery(type, name, mobile, email, userId, userType, remarks);
            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(@NonNull Call<BasicResponse> call,
                                       @NonNull Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        BasicResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<BasicResponse> call,
                                      @NonNull Throwable throwable) {
                    //onResponse(t);
                    throwable.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchQuote(final Context mContext, String regNo) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<FetchQuote> call = apiService.fetchQuote(regNo);
            call.enqueue(new Callback<FetchQuote>() {
                @Override
                public void onResponse(@NonNull Call<FetchQuote> call,
                                       @NonNull Response<FetchQuote> response) {
                    if (response.isSuccessful()) {
                        FetchQuote commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<FetchQuote> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchVahanData(final Context mContext, String regNo, String vType) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<VahanData> call = apiService.fetchVahanData(regNo, vType);
            call.enqueue(new Callback<VahanData>() {
                @Override
                public void onResponse(@NonNull Call<VahanData> call,
                                       @NonNull Response<VahanData> response) {
                    if (response.isSuccessful()) {
                        VahanData commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<VahanData> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMiscDData(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<SbiMisd> call = apiService.getMiscDData();
            call.enqueue(new Callback<SbiMisd>() {
                @Override
                public void onResponse(@NonNull Call<SbiMisd> call,
                                       @NonNull Response<SbiMisd> response) {
                    if (response.isSuccessful()) {
                        SbiMisd commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SbiMisd> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
