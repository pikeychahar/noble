package com.square.pos.manager;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.square.pos.BuildConfig;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.model.BasicResponse;
import com.square.pos.model.CommonResponse;
import com.square.pos.model.Filter;
import com.square.pos.model.FilterCar;
import com.square.pos.model.NewPremiumQuote;
import com.square.pos.model.Nominee;
import com.square.pos.model.OtherInformation;
import com.square.pos.model.Owner;
import com.square.pos.model.PCVGCVList;
import com.square.pos.model.PremiumQuote;
import com.square.pos.model.TempResponse;
import com.square.pos.model.VehicleOther;
import com.square.pos.model.VehicleQuote;
import com.square.pos.model.fetch_detail.GetNominee;
import com.square.pos.model.fetch_detail.GetOwner;
import com.square.pos.model.fetch_detail.GetVehicle;
import com.square.pos.model.master.CityMaster;
import com.square.pos.model.master.FinanceStatus;
import com.square.pos.model.master.GenderStatus;
import com.square.pos.model.master.Marital;
import com.square.pos.model.master.NomineeStatus;
import com.square.pos.model.master.OccupationStatus;
import com.square.pos.model.master.PincodeMaster;
import com.square.pos.model.master.SalutationStatus;
import com.square.pos.model.master.StateMaster;
import com.square.pos.model_life.AnualIncomeList;
import com.square.pos.model_life.AppointeeList;
import com.square.pos.model_life.BusinessList;
import com.square.pos.model_life.GenderList;
import com.square.pos.model_life.GetLifeQuoteId;
import com.square.pos.model_life.LifePinList;
import com.square.pos.model_life.LifePremium;
import com.square.pos.model_life.LifeProposal;
import com.square.pos.model_life.MaritalList;
import com.square.pos.model_life.NomineeList;
import com.square.pos.model_life.OccupationList;
import com.square.pos.model_life.QualificationList;
import com.square.pos.model_life.SalutationList;
import com.square.pos.model_life.SectorList;
import com.square.pos.model_life.SumList;
import com.square.pos.network.ApiClient;
import com.square.pos.network.ApiInterface;
import com.square.pos.utils.AppUtils;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prahalad Chahar on 14/08/20.
 */
public class ApiManager {

    private static ApiManager instance;

    private ApiManager() {

    }

    public static ApiManager getInstance() {

        if (instance == null)
            instance = new ApiManager();
        return instance;
    }

    public void applyFilterCar(final Context cxt, String quotationId, String ownedBy,
                               String ownerChange, String claimExp, String ncbOld, String pACover,
                               String zeroDept, String imtCover, String imtCover34, String cover,
                               String tpOnly, String idv, String policyExpDate, String antiTheft,
                               String vehicleModified, String tpp, String voluntary,
                               String voluntaryValue, String memberAAI, String numberAAI,
                               String memberName, String membershipExpiryDate,
                               String electricalAccessory, String electricalAccessoryValue,
                               String nonElectricalAccessory, String nonElectricalAccValue,
                               String fuelKit, String fuelKitValue, String PAUnnamed,
                               String PAUnnamedValue, String legalEmployee,
                               String legalEmployeeValue, String legalDriver,
                               String legalDriverValue, String fiber, String emergencyCover,
                               String consumables, String tyreCover, String ncbProtection,
                               String engineProtector, String returnInvoice, String lossKey,
                               String roadAssistance, String passengerCover, String hydrostaticCover,
                               String hospitalCashCover, String lossPersonal, String tpPreInsurer,
                               String tpPolicyNumber, String tpPolicyExpDate, String make,
                               String model, String fuel, String variant, String company,
                               String sbiCode, String carrierType) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<FilterCar> call = apiService.filterCar(quotationId, AppUtils.TOKEN, ownedBy,
                    ownerChange, claimExp, ncbOld, pACover, zeroDept, imtCover, imtCover34, cover,
                    tpOnly, idv, policyExpDate, antiTheft, vehicleModified, tpp, voluntary,
                    voluntaryValue, memberAAI, numberAAI, memberName, membershipExpiryDate,
                    electricalAccessory, electricalAccessoryValue, nonElectricalAccessory,
                    nonElectricalAccValue, fuelKit, fuelKitValue, PAUnnamed, PAUnnamedValue,
                    legalEmployee, legalEmployeeValue, legalDriver, legalDriverValue, fiber,
                    emergencyCover, consumables, tyreCover, ncbProtection, engineProtector,
                    returnInvoice, lossKey, roadAssistance, passengerCover, hydrostaticCover,
                    hospitalCashCover, lossPersonal, tpPreInsurer, tpPolicyNumber, tpPolicyExpDate,
                    make, model, fuel, variant, company, sbiCode, carrierType);

            call.enqueue(new Callback<FilterCar>() {
                @Override
                public void onResponse(@NotNull Call<FilterCar> call,
                                       @NotNull Response<FilterCar> response) {
                    if (response.isSuccessful()) {
                        FilterCar commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<FilterCar> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initQuotationId(final Context cxt, String ipAddress, String email, String mobile, String policyExpiry,
                                String policyType, String previousInsurer, String regYear,
                                String variant, String model, String make, String vType,
                                String regNumber, String name, String newVehicle,
                                String userType, String fuelType, String isPrePolicy, String company,
                                String pcvType, String agentId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        String version = "android pos - " + BuildConfig.VERSION_NAME;

        try {
            Call<VehicleQuote> call = apiService.initQuoteId(ipAddress, email, AppUtils.TOKEN, version, mobile, policyExpiry,
                    policyType, previousInsurer, regYear, variant, model, make, vType, regNumber,
                    name, newVehicle, userType, fuelType, isPrePolicy, company, pcvType, agentId);
            call.enqueue(new Callback<VehicleQuote>() {
                @Override
                public void onResponse(@NonNull Call<VehicleQuote> call, @NonNull
                        Response<VehicleQuote> response) {

                    if (response.isSuccessful()) {
                        VehicleQuote commonResponse = response.body();

                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<VehicleQuote> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeGcmId(final Context cxt, String userId, String userType) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<BasicResponse> call = apiService.removeGcmId(userId, userType);

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

    public void userAuthentication(final Context cxt, String userId, String userType) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<TempResponse> call = apiService.userAuthentication(userId, userType);

            call.enqueue(new Callback<TempResponse>() {
                @Override
                public void onResponse(@NotNull Call<TempResponse> call,
                                       @NotNull Response<TempResponse> response) {
                    if (response.isSuccessful()) {
                        TempResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).onResponse(commonResponse);
                        if (commonResponse != null) {
                            String status = commonResponse.getStatus();
                            if (status.equals("0"))
                                AppUtils.userAuthDialog(cxt);
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<TempResponse> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateOtherInformation(final Context cxt, String manufactureDate, String purchaseDate,
                                       String registrationDate, String policyExpiryDate, String vOwned,
                                       String ownerChange, String claimExpire, String ncb, String zDep,
                                       String insertId, String sbiCode) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<OtherInformation> call = apiService.updateOtherInformation(manufactureDate,
                    purchaseDate, registrationDate, policyExpiryDate, vOwned, ownerChange,
                    claimExpire, ncb, zDep, insertId, sbiCode, AppUtils.TOKEN);

            call.enqueue(new Callback<OtherInformation>() {
                @Override
                public void onResponse(@NotNull Call<OtherInformation> call,
                                       @NotNull Response<OtherInformation> response) {
                    if (response.isSuccessful()) {
                        OtherInformation commonResponse = response.body();

                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<OtherInformation> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateFilter(final Context cxt, String pACover, String zeroDept, String rsa,
                             String unnamed, String unnamedValue, String paidDriver,
                             String coverFor, String tppd, String ownedBy, String idv,
                             String ownerChange, String claimExp, String ncbOld, String cover,
                             String tpOnly, String policyExpDate, String quotationId,
                             String tpPreInsurer, String tpPolicyNumber, String tpPolicyExpDate,
                             String make, String model, String fuel, String variant) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();
        int tppdInt = 0;
        if (!TextUtils.isEmpty(tppd))
            tppdInt = Integer.parseInt(tppd);

        try {
            Call<Filter> call = apiService.twFilter(pACover, zeroDept, rsa, unnamed, unnamedValue,
                    paidDriver, coverFor, tppdInt, ownedBy, idv, ownerChange, claimExp, ncbOld,
                    cover, tpOnly, policyExpDate, quotationId, tpPreInsurer, tpPolicyNumber,
                    tpPolicyExpDate, AppUtils.TOKEN, make, model, fuel, variant);
            call.enqueue(new Callback<Filter>() {
                @Override
                public void onResponse(@NotNull Call<Filter> call,
                                       @NotNull Response<Filter> response) {

                    if (response.isSuccessful()) {
                        Filter commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<Filter> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPremium(final Context cxt, String quotationId, String vehicleType, String company,
                           String idv) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<PremiumQuote> call = apiService.getPremium(AppUtils.TOKEN, quotationId,
                    vehicleType, company, idv, AppUtils.TOKEN_QUOTE);
            call.enqueue(new Callback<PremiumQuote>() {
                @Override
                public void onResponse(@NotNull Call<PremiumQuote> call,
                                       @NotNull Response<PremiumQuote> response) {

                    if (response.isSuccessful()) {
                        PremiumQuote commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<PremiumQuote> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPremiumCar(final Context cxt, String quotationId, String vehicleType,
                              String company, String idv) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<NewPremiumQuote> call = apiService.getPremiumCar(AppUtils.TOKEN, quotationId,
                    vehicleType, company, idv, AppUtils.TOKEN_QUOTE);
            call.enqueue(new Callback<NewPremiumQuote>() {
                @Override
                public void onResponse(@NotNull Call<NewPremiumQuote> call,
                                       @NotNull Response<NewPremiumQuote> response) {

                    if (response.isSuccessful()) {
                        NewPremiumQuote commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<NewPremiumQuote> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchOwner(final Context mContext, String quotationId) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<GetOwner> call = apiService.fetchOwner(AppUtils.TOKEN, quotationId);
            call.enqueue(new Callback<GetOwner>() {
                @Override
                public void onResponse(@NotNull Call<GetOwner> call, @NotNull Response<GetOwner> response) {
                    if (response.isSuccessful()) {
                        GetOwner commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<GetOwner> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchVehicle(final Context mContext, String quotationId) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<GetVehicle> call = apiService.fetchVehicle(AppUtils.TOKEN, quotationId);
            call.enqueue(new Callback<GetVehicle>() {
                @Override
                public void onResponse(@NotNull Call<GetVehicle> call, @NotNull Response<GetVehicle> response) {
                    if (response.isSuccessful()) {
                        GetVehicle commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<GetVehicle> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchNominee(final Context mContext, String quotationId) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<GetNominee> call = apiService.fetchNominee(AppUtils.TOKEN, quotationId);
            call.enqueue(new Callback<GetNominee>() {
                @Override
                public void onResponse(@NotNull Call<GetNominee> call, @NotNull Response<GetNominee> response) {
                    if (response.isSuccessful()) {
                        GetNominee commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<GetNominee> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchLifeProposal(final Context cxt, String quoteId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<LifeProposal> call = apiService.fetchLifeProposal(AppUtils.TOKEN, quoteId);
            call.enqueue(new Callback<LifeProposal>() {
                @Override
                public void onResponse(@NotNull Call<LifeProposal> call,
                                       @NotNull Response<LifeProposal> response) {
                    if (response.isSuccessful()) {
                        LifeProposal commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LifeProposal> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getNomineeRelation(final Context cxt, String type, String company) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<NomineeStatus> call = apiService.getNomineeRelation(type, company);
            call.enqueue(new Callback<NomineeStatus>() {
                @Override
                public void onResponse(@NotNull Call<NomineeStatus> call,
                                       @NotNull Response<NomineeStatus> response) {
                    if (response.isSuccessful()) {
                        NomineeStatus commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<NomineeStatus> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getGender(final Context cxt, String type, String company) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<GenderStatus> call = apiService.getGender(type, company);
            call.enqueue(new Callback<GenderStatus>() {
                @Override
                public void onResponse(Call<GenderStatus> call, Response<GenderStatus> response) {
                    if (response.isSuccessful()) {
                        GenderStatus commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<GenderStatus> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMarital(final Context cxt, String type, String company) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<Marital> call = apiService.getMarital(type, company);
            call.enqueue(new Callback<Marital>() {
                @Override
                public void onResponse(Call<Marital> call, Response<Marital> response) {
                    if (response.isSuccessful()) {
                        Marital commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<Marital> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMasterState(final Context cxt, String company) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<StateMaster> call = apiService.getMasterState("state", company);
            call.enqueue(new Callback<StateMaster>() {
                @Override
                public void onResponse(Call<StateMaster> call, Response<StateMaster> response) {
                    if (response.isSuccessful()) {
                        StateMaster commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<StateMaster> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMasterCity(final Context cxt, String company, String stateId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<CityMaster> call = apiService.getMasterCity("city", company, stateId);
            call.enqueue(new Callback<CityMaster>() {
                @Override
                public void onResponse(@NonNull Call<CityMaster> call, @NonNull Response<CityMaster> response) {
                    if (response.isSuccessful()) {
                        CityMaster commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CityMaster> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getMasterPincode(final Context cxt, String company, String cityId, String stateId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<PincodeMaster> call = apiService.getMasterPincode("pincode", company, cityId, stateId);
            call.enqueue(new Callback<PincodeMaster>() {
                @Override
                public void onResponse(@NonNull Call<PincodeMaster> call, @NonNull Response<PincodeMaster> response) {
                    if (response.isSuccessful()) {
                        PincodeMaster commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PincodeMaster> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getQuoteId(final Context cxt, String userId, String userType, String userName,
                           String userPhone, String userEmail, String dob, String gender,
                           String tobaccoUser, String annualIncome, String qualification,
                           String occupation, String sumInsured) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<GetLifeQuoteId> call = apiService.getQuoteLifeId(userId, userName, userType, userPhone,
                    userEmail, dob, gender, tobaccoUser, annualIncome, qualification, occupation,
                    sumInsured);
            call.enqueue(new Callback<GetLifeQuoteId>() {
                @Override
                public void onResponse(@NonNull Call<GetLifeQuoteId> call,
                                       @NonNull Response<GetLifeQuoteId> response) {
                    if (response.isSuccessful()) {
                        GetLifeQuoteId commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<GetLifeQuoteId> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveProposal(final Context cxt, String quoteId, String company, String gross,
                             String net, String tax, String extra, String planName,
                             String sumInsured) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<CommonResponse> call = apiService.saveProposal(AppUtils.TOKEN, quoteId, company,
                    gross, net, tax, extra, planName, sumInsured);
            call.enqueue(new Callback<CommonResponse>() {
                @Override
                public void onResponse(@NonNull Call<CommonResponse> call,
                                       @NonNull Response<CommonResponse> response) {
                    if (response.isSuccessful()) {
                        CommonResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateLifeFilter(final Context cxt, String quoteId, String tobaccoUser, String coverAge,
                                 String incomeBenefit, String criticalIllness, String personalAccident,
                                 String cancerCare, String accidentDeath, String sumInsured) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<CommonResponse> call = apiService.updateLifeFilter(AppUtils.TOKEN, quoteId, tobaccoUser,
                    coverAge, incomeBenefit, criticalIllness, cancerCare, personalAccident,
                    accidentDeath, sumInsured);
            call.enqueue(new Callback<CommonResponse>() {
                @Override
                public void onResponse(@NonNull Call<CommonResponse> call,
                                       @NonNull Response<CommonResponse> response) {
                    if (response.isSuccessful()) {
                        CommonResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getSalutation(final Context cxt, String type, String company) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<SalutationStatus> call = apiService.getSalutation(type, company);
            call.enqueue(new Callback<SalutationStatus>() {
                @Override
                public void onResponse(Call<SalutationStatus> call, Response<SalutationStatus> response) {
                    if (response.isSuccessful()) {
                        SalutationStatus commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<SalutationStatus> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getOccupation(final Context cxt, String type, String company) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<OccupationStatus> call = apiService.getOccupation(type, company);
            call.enqueue(new Callback<OccupationStatus>() {
                @Override
                public void onResponse(Call<OccupationStatus> call, Response<OccupationStatus> response) {
                    if (response.isSuccessful()) {
                        OccupationStatus commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<OccupationStatus> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getFinancier(final Context cxt, String type, String company) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<FinanceStatus> call = apiService.getFinancier(type, company);
            call.enqueue(new Callback<FinanceStatus>() {
                @Override
                public void onResponse(@NonNull Call<FinanceStatus> call,
                                       @NonNull Response<FinanceStatus> response) {
                    if (response.isSuccessful()) {
                        FinanceStatus commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<FinanceStatus> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLifeAnnual(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<AnualIncomeList> call = apiService.getLifeAnnual("annualIncome");
            call.enqueue(new Callback<AnualIncomeList>() {
                @Override
                public void onResponse(@NonNull Call<AnualIncomeList> call,
                                       @NonNull Response<AnualIncomeList> response) {
                    if (response.isSuccessful()) {
                        AnualIncomeList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<AnualIncomeList> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLifePincode(final Context cxt, String pincode) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<LifePinList> call = apiService.getLifePincode("pincode", pincode);
            call.enqueue(new Callback<LifePinList>() {
                @Override
                public void onResponse(@NonNull Call<LifePinList> call,
                                       @NonNull Response<LifePinList> response) {
                    if (response.isSuccessful()) {
                        LifePinList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LifePinList> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLifeQualification(final Context cxt, String back) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<QualificationList> call = apiService.getLifeQualification("qualification", back);
            call.enqueue(new Callback<QualificationList>() {
                @Override
                public void onResponse(@NonNull Call<QualificationList> call,
                                       @NonNull Response<QualificationList> response) {
                    if (response.isSuccessful()) {
                        QualificationList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<QualificationList> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLifeMarital(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<MaritalList> call = apiService.getLifeMarital("marital");
            call.enqueue(new Callback<MaritalList>() {
                @Override
                public void onResponse(@NonNull Call<MaritalList> call,
                                       @NonNull Response<MaritalList> response) {
                    if (response.isSuccessful()) {
                        MaritalList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MaritalList> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLifeSumInsured(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<SumList> call = apiService.getLifeSumInsured("suminsured");
            call.enqueue(new Callback<SumList>() {
                @Override
                public void onResponse(@NonNull Call<SumList> call,
                                       @NonNull Response<SumList> response) {
                    if (response.isSuccessful()) {
                        SumList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SumList> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLifeNomineeRelation(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<NomineeList> call = apiService.getLifeNomineeRelation("nominee_relation");
            call.enqueue(new Callback<NomineeList>() {
                @Override
                public void onResponse(@NonNull Call<NomineeList> call,
                                       @NonNull Response<NomineeList> response) {
                    if (response.isSuccessful()) {
                        NomineeList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<NomineeList> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLifeOccupation(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<OccupationList> call = apiService.getLifeOccupation("occupation");
            call.enqueue(new Callback<OccupationList>() {
                @Override
                public void onResponse(@NonNull Call<OccupationList> call,
                                       @NonNull Response<OccupationList> response) {
                    if (response.isSuccessful()) {
                        OccupationList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<OccupationList> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLifeSector(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<SectorList> call = apiService.getLifeSector("sector");
            call.enqueue(new Callback<SectorList>() {
                @Override
                public void onResponse(@NonNull Call<SectorList> call,
                                       @NonNull Response<SectorList> response) {
                    if (response.isSuccessful()) {
                        SectorList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SectorList> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLifeGender(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<GenderList> call = apiService.getLifeGender("gender");
            call.enqueue(new Callback<GenderList>() {
                @Override
                public void onResponse(@NonNull Call<GenderList> call,
                                       @NonNull Response<GenderList> response) {
                    if (response.isSuccessful()) {
                        GenderList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<GenderList> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLifeBusiness(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<BusinessList> call = apiService.getLifeBusiness("businessBelongs");
            call.enqueue(new Callback<BusinessList>() {
                @Override
                public void onResponse(@NonNull Call<BusinessList> call,
                                       @NonNull Response<BusinessList> response) {
                    if (response.isSuccessful()) {
                        BusinessList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<BusinessList> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLifeSalutation(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<SalutationList> call = apiService.getLifeSalutation("salutation");
            call.enqueue(new Callback<SalutationList>() {
                @Override
                public void onResponse(@NonNull Call<SalutationList> call,
                                       @NonNull Response<SalutationList> response) {
                    if (response.isSuccessful()) {
                        SalutationList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SalutationList> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLifeAppointee(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<AppointeeList> call = apiService.getLifeAppointee("appointee_relation");
            call.enqueue(new Callback<AppointeeList>() {
                @Override
                public void onResponse(@NonNull Call<AppointeeList> call,
                                       @NonNull Response<AppointeeList> response) {
                    if (response.isSuccessful()) {
                        AppointeeList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<AppointeeList> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void vehicleOtherDetail(final Context cxt, String financierCity, String financierName,
                                   String hypothecation, String chassisNo, String engineNo,
                                   String regNumber, String prePolicyNo, String quotationId,
                                   String rtoId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<VehicleOther> call = apiService.vehicleOtherDetail(AppUtils.TOKEN, financierCity, financierName,
                    hypothecation, chassisNo, engineNo, regNumber, prePolicyNo, quotationId, rtoId);
            call.enqueue(new Callback<VehicleOther>() {
                @Override
                public void onResponse(@NotNull Call<VehicleOther> call, @NotNull Response<VehicleOther> response) {
                    if (response.isSuccessful()) {
                        VehicleOther commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<VehicleOther> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void nomineeDetail(final Context cxt, String nomineeName, String nomineeRelation,
                              String nomineeGender, String nomineeDob, String status,
                              String quotationId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<Nominee> call = apiService.nomineeDetail(AppUtils.TOKEN, nomineeName,
                    nomineeRelation, nomineeGender, nomineeDob, status, quotationId);
            call.enqueue(new Callback<Nominee>() {
                @Override
                public void onResponse(Call<Nominee> call, Response<Nominee> response) {
                    if (response.isSuccessful()) {
                        Nominee commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<Nominee> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void storeGcmId(final Context cxt, String userId, String userType, String fcmId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<BasicResponse> call = apiService.storeGcmId(AppUtils.TOKEN, userId, userType,
                    fcmId);

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

    public void ownerDetailSubmit(final Context cxt, String agentId, String userId,
                                  String quotationId, String salutation, String firstName,
                                  String lastName, String dob, String orgName, String regPinCode, String regCity,
                                  String regAddress3, String regAddress2, String regAddress1,
                                  String regAddress, String gstNo, String panCard, String pinCode,
                                  String state, String city, String address3, String address2,
                                  String address1, String nationality, String alternateMobile,
                                  String email, String mobile, String occupation,
                                  String maritalStatus, String gender) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<Owner> call = apiService.ownerDetail(AppUtils.TOKEN, agentId, "",
                    quotationId, salutation, firstName, "", lastName, dob, orgName, regPinCode,
                    regCity, regAddress3, regAddress2, regAddress1, regAddress, gstNo, panCard,
                    pinCode, state, city, address3, address2, address1, nationality, alternateMobile,
                    email, mobile, occupation, maritalStatus, gender);
            call.enqueue(new Callback<Owner>() {
                @Override
                public void onResponse(Call<Owner> call, Response<Owner> response) {
                    if (response.isSuccessful()) {
                        Owner commonResponse = response.body();

                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<Owner> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateLifeProposal(final Context cxt, String quotationId, String salutation,
                                   String firstName, String lastName, String dob, String pinCode,
                                   String state, String city, String address1, String address2,
                                   String address3, String annualIncome, String email,
                                   String mobile, String occupation, String education,
                                   String maritalStatus, String gender, String nmTitle,
                                   String nmFirstName, String nmLastName, String nmGender,
                                   String nmMarital, String nmRelation, String nmPhone,
                                   String nmDob, String apTitle, String apFirstName,
                                   String apLastName, String apGender, String apMarital,
                                   String apRelation, String apPhone, String apDob, String business,
                                   String existingCover, String empName, String empAddress,
                                   String empSector, String spouseName) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<CommonResponse> call = apiService.updateLifeProposal(AppUtils.TOKEN, quotationId,
                    salutation, firstName, lastName, dob, pinCode, state, city, address1, address2,
                    address3, annualIncome, email, mobile, occupation, education, maritalStatus,
                    gender, nmTitle, nmFirstName, nmLastName, nmGender, nmMarital, nmRelation,
                    nmPhone, nmDob, apTitle, apFirstName, apLastName, apGender, apMarital,
                    apRelation, apPhone, apDob, business, existingCover, empName, empAddress,
                    empSector, spouseName);
            call.enqueue(new Callback<CommonResponse>() {
                @Override
                public void onResponse(@NonNull Call<CommonResponse> call,
                                       @NonNull Response<CommonResponse> response) {
                    if (response.isSuccessful()) {
                        CommonResponse commonResponse = response.body();

                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLifeQuote(final Context cxt, String company, String qId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<LifePremium> call = apiService.getLifeQuotation(AppUtils.TOKEN, company, qId);

            call.enqueue(new Callback<LifePremium>() {
                @Override
                public void onResponse(@NotNull Call<LifePremium> call, @NotNull Response<LifePremium> response) {
                    if (response.isSuccessful()) {
                        LifePremium commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<LifePremium> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
