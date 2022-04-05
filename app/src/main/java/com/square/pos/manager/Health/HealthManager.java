package com.square.pos.manager.Health;

import android.content.Context;

import androidx.annotation.NonNull;

import com.square.pos.BuildConfig;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.model.CommonResponse;
import com.square.pos.model.Session;
import com.square.pos.model.TempResponse;
import com.square.pos.model.master.Pincode;
import com.square.pos.model.master.PincodeMaster;
import com.square.pos.model_health.bajaj.BajajPincode;
import com.square.pos.model_health.compare.Compare;
import com.square.pos.model_health.v2.BrochureList;
import com.square.pos.model_health.v2.Document;
import com.square.pos.model_health.v2.Gender;
import com.square.pos.model_health.v2.HealthApointee;
import com.square.pos.model_health.v2.HealthCity;
import com.square.pos.model_health.v2.HealthDocument;
import com.square.pos.model_health.v2.HealthGender;
import com.square.pos.model_health.v2.HealthMarital;
import com.square.pos.model_health.v2.HealthOccupation;
import com.square.pos.model_health.v2.HealthPaymentTrack;
import com.square.pos.model_health.v2.HealthPremium;
import com.square.pos.model_health.v2.HealthQualification;
import com.square.pos.model_health.v2.HealthQuote;
import com.square.pos.model_health.v2.HealthRelation;
import com.square.pos.model_health.v2.HealthSalutation;
import com.square.pos.model_health.v2.HealthState;
import com.square.pos.model_health.v2.HealthSumInsured;
import com.square.pos.model_health.v2.HospitalList;
import com.square.pos.model_health.v2.Occupation;
import com.square.pos.model_health.v2.PlanCover;
import com.square.pos.model_health.v2.Relation;
import com.square.pos.model_health.v2.Salutation;
import com.square.pos.network.ApiClient;
import com.square.pos.network.ApiInterface;
import com.square.pos.utils.AppUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prahalad Chahar on 21/04/20.
 */
public class HealthManager {

    private static HealthManager instance;

    public static HealthManager getInstance() {
        if (instance == null)
            instance = new HealthManager();

        return instance;
    }

    private HealthManager() {

    }

    public ArrayList<Relation> getRelationList() {
        return relationList;
    }

    public void setRelationList(ArrayList<Relation> relationList) {
        this.relationList = relationList;
    }

    public ArrayList<Pincode> getSbiPincodeList() {
        return sbiPincodeList;
    }

    public void setSbiPincodeList(ArrayList<Pincode> sbiPincodeList) {
        this.sbiPincodeList = sbiPincodeList;
    }

    public ArrayList<Pincode> sbiPincodeList = new ArrayList<>();


    public ArrayList<Relation> relationList = new ArrayList<>();

    public ArrayList<Gender> getGenderList() {
        return genderList;
    }

    public void setGenderList(ArrayList<Gender> genderList) {
        this.genderList = genderList;
    }

    public ArrayList<Gender> genderList = new ArrayList<>();

    public List<Salutation> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<Salutation> titleList) {
        this.titleList = titleList;
    }

    public List<Salutation> titleList = new ArrayList();

    public List<Occupation> getOccList() {
        return occList;
    }

    public void setOccList(List<Occupation> occList) {
        this.occList = occList;
    }

    public List<Occupation> occList = new ArrayList();

    public void getHealthSum(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<HealthSumInsured> call = apiService.getHealthSum(AppUtils.TOKEN);
            call.enqueue(new Callback<HealthSumInsured>() {
                @Override
                public void onResponse(@NotNull Call<HealthSumInsured> call,
                                       @NotNull Response<HealthSumInsured> response) {
                    if (response.isSuccessful()) {
                        HealthSumInsured commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                        assert commonResponse != null;
                    }
                }

                @Override
                public void onFailure(@NotNull Call<HealthSumInsured> call,
                                      @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getHealthQuoteId(final Context cxt, String userId, String userType, String name,
                                 String email, String phone, String gender, String pincode,
                                 String sumAssured, String planType, String selfAge,
                                 String spouseAge, String fatherAge, String motherAge,
                                 String sonAge1, String sonAge2, String daughterAge1,
                                 String daughterAge2, String[] memberArray, String selfDob,
                                 String spouseDob, String fatherDob, String motherDob,
                                 String sonDob1, String sonDob2, String daughterDob1,
                                 String daughterDob2, String medical) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();
        String version = "android pos - " + BuildConfig.VERSION_NAME;

        try {
            Call<HealthQuote> call = apiService.getHealthQuoteId(AppUtils.TOKEN, version, userId,
                    userType, name, email, phone, gender, pincode, sumAssured, planType, selfAge,
                    spouseAge, fatherAge, motherAge, sonAge1, sonAge2, daughterAge1, daughterAge2,
                    memberArray, selfDob, spouseDob, fatherDob, motherDob, sonDob1, sonDob2,
                    daughterDob1, daughterDob2, medical);

            call.enqueue(new Callback<HealthQuote>() {
                @Override
                public void onResponse(@NotNull Call<HealthQuote> call, @NotNull Response<HealthQuote> response) {
                    if (response.isSuccessful()) {
                        HealthQuote commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);

                    }
                }

                @Override
                public void onFailure(@NotNull Call<HealthQuote> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateHealthProposalSbi(final Context cxt, String company,
                                        String quotationId, ArrayList<String> proposerTitle,
                                        ArrayList<String> proposerFirstName,
                                        ArrayList<String> proposerLastName,
                                        ArrayList<String> proposerDob,
                                        ArrayList<String> proposerGender,
                                        ArrayList<String> proposerDesignation,
                                        ArrayList<String> proposerMaritalStatus,
                                        ArrayList<String> proposerMobile,
                                        ArrayList<String> proposerEmail,
                                        ArrayList<String> proposerPlat,
                                        ArrayList<String> proposerStreet,
                                        ArrayList<String> proposerAddress1,
                                        ArrayList<String> proposerPincode, ArrayList<String> title,
                                        ArrayList<String> firstName, ArrayList<String> lastName,
                                        ArrayList<String> gender, ArrayList<String> dob,
                                        ArrayList<String> mobile, ArrayList<String> email,
                                        ArrayList<String> maritalStatus, ArrayList<String> document,
                                        ArrayList<String> documentNo, ArrayList<String> occupation,
                                        ArrayList<String> height, ArrayList<String> weight,
                                        ArrayList<String> plat, ArrayList<String> building,
                                        ArrayList<String> street, ArrayList<String> address1,
                                        ArrayList<String> pincode, ArrayList<String> NomineeRelation,
                                        ArrayList<String> nomineeFirstName,
                                        ArrayList<String> nomineeLastName,
                                        ArrayList<String> nomineeGender,
                                        ArrayList<String> nomineeDob,
                                        ArrayList<String> sameTraveller,
                                        ArrayList<String> appointeeName,
                                        ArrayList<String> appointeeRelation,
                                        ArrayList<String> alcohol, ArrayList<String> smoke,
                                        ArrayList<String> tobacco, ArrayList<String> other) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<TempResponse> call = apiService.updateHealthProposalSbi(AppUtils.TOKEN, company,
                    quotationId, proposerTitle, proposerFirstName, proposerLastName, proposerDob,
                    proposerGender, proposerDesignation, proposerMaritalStatus, proposerMobile,
                    proposerEmail, proposerPlat, proposerStreet, proposerAddress1, proposerPincode,
                    title, firstName, lastName, gender, dob, mobile, email, maritalStatus, document,
                    documentNo, occupation, height, weight, plat, building, street, address1,
                    pincode, NomineeRelation, nomineeFirstName, nomineeLastName, nomineeGender,
                    nomineeDob, sameTraveller, appointeeName, appointeeRelation, alcohol,
                    smoke, tobacco, other);

            call.enqueue(new Callback<TempResponse>() {
                @Override
                public void onResponse(@NotNull Call<TempResponse> call,
                                       @NotNull Response<TempResponse> response) {
                    if (response.isSuccessful()) {
                        TempResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);

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

    public void hospitalList(final Context cxt, String company, String pincode,String appKey) {
        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<HospitalList> call = apiService.hospitalList(AppUtils.TOKEN, company, pincode,
                    appKey);

            call.enqueue(new Callback<HospitalList>() {
                @Override
                public void onResponse(@NotNull Call<HospitalList> call, @NotNull Response<HospitalList> response) {
                    if (response.isSuccessful()) {
                        HospitalList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<HospitalList> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void brochureList(final Context cxt, String company, String planName1,
                             String planName, String subPlanName, String appKey) {
        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<BrochureList> call = apiService.brochureList(AppUtils.TOKEN, company, planName1,
                    planName, subPlanName, appKey);

            call.enqueue(new Callback<BrochureList>() {
                @Override
                public void onResponse(@NotNull Call<BrochureList> call, @NotNull Response<BrochureList> response) {
                    if (response.isSuccessful()) {
                        BrochureList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<BrochureList> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPlanBenefits(final Context cxt, String company, String planName, String subPlan,
                                String sumInsured, String appKey) {
        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<PlanCover> call = apiService.getPlanBenefits(AppUtils.TOKEN, company, planName,
                    subPlan, sumInsured, AppUtils.encode(appKey));

            call.enqueue(new Callback<PlanCover>() {
                @Override
                public void onResponse(@NotNull Call<PlanCover> call, @NotNull Response<PlanCover> response) {
                    if (response.isSuccessful()) {
                        PlanCover commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<PlanCover> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateHealthProposalIcici(final Context cxt, String company, String quotationId,
                                          String title, String firstName, String lastName,
                                          String gender, String dob, String height,
                                          String weight, String relation, String nomineeRelation,
                                          String nomineeFirstName, String nomineeLastName,
                                          String exists, String nomineeDob,
                                          String proposerMobile, String proposerEmail,
                                          String proposerPan, String pincode, String address1,
                                          String address2) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {

            RequestBody requestCompanyName = RequestBody.create(company,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestQuoteId = RequestBody.create(quotationId,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestTitleName = RequestBody.create(title,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestFirstName = RequestBody.create(firstName,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestLastName = RequestBody.create(lastName,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestGender = RequestBody.create(gender,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestDob = RequestBody.create(dob,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestHeight = RequestBody.create(height,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestWeight = RequestBody.create(weight,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestRelation = RequestBody.create(relation,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestNomineeRelation = RequestBody.create(nomineeRelation,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestNomineeFName = RequestBody.create(nomineeFirstName,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestNomineeLName = RequestBody.create(nomineeLastName,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestExists = RequestBody.create(exists,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestNmDob = RequestBody.create(nomineeDob,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestNmMobile = RequestBody.create(proposerMobile,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestNmEmail = RequestBody.create(proposerEmail,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestPan = RequestBody.create(proposerPan,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestPincode = RequestBody.create(pincode,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestAddress1 = RequestBody.create(address1,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestAddress2 = RequestBody.create(address2,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestPType = RequestBody.create("ios",
                    MediaType.parse("multipart/form-data"));
            RequestBody requestSameTraveller = RequestBody.create("1",
                    MediaType.parse("multipart/form-data"));
            RequestBody requestToken = RequestBody.create(AppUtils.TOKEN,
                    MediaType.parse("multipart/form-data"));

            Call<TempResponse> call = apiService.updateHealthProposalIcici(requestToken, requestCompanyName,
                    requestQuoteId, requestTitleName, requestFirstName, requestLastName, requestGender, requestDob, requestHeight,
                    requestWeight, requestRelation,
                    requestNomineeRelation, requestNomineeFName, requestNomineeLName, requestExists, requestNmDob,
                    requestNmMobile, requestNmEmail, requestPan, requestPincode, requestAddress1, requestAddress2,
                    requestPType, requestSameTraveller);

            call.enqueue(new Callback<TempResponse>() {
                @Override
                public void onResponse(@NotNull Call<TempResponse> call,
                                       @NotNull Response<TempResponse> response) {
                    if (response.isSuccessful()) {
                        TempResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);

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

    public void updateHealthProposalIffco(final Context cxt, String company, String quotationId,
                                          String title, String firstName, String lastName,
                                          String gender, String dob, String height,
                                          String weight, String occupation, String relation,
                                          String nomineeRelation, String nomineeFirstName,
                                          String nomineeLastName, String cigarette, String alcohol,
                                          String tobacco, String nomineeDob, String proposerTitle,
                                          String proposerFirstName, String proposerLastName,
                                          String proposerDob, String proposerGender,
                                          String proposerMaritalStatus, String proposerMobile,
                                          String proposerMobileEmergency, String proposerEmail,
                                          String proposerNameEmergency, String proposerPan,
                                          String pincode, String address1, String address2) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {

            RequestBody requestCompanyName = RequestBody.create(company,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestQuoteId = RequestBody.create(quotationId,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestTitleName = RequestBody.create(title,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestFirstName = RequestBody.create(firstName,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestLastName = RequestBody.create(lastName,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestGender = RequestBody.create(gender,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestDob = RequestBody.create(dob,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestHeight = RequestBody.create(height,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestWeight = RequestBody.create(weight,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestOccupation = RequestBody.create(occupation,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestRelation = RequestBody.create(relation,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestNomineeRelation = RequestBody.create(nomineeRelation,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestNomineeFName = RequestBody.create(nomineeFirstName,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestNomineeLName = RequestBody.create(nomineeLastName,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestCigarette = RequestBody.create(cigarette,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestAlcohol = RequestBody.create(alcohol,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestTobacco = RequestBody.create(tobacco,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestNmDob = RequestBody.create(nomineeDob,
                    MediaType.parse("multipart/form-data"));

            RequestBody requestPTitle = RequestBody.create(proposerTitle,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestPFName = RequestBody.create(proposerFirstName,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestPLName = RequestBody.create(proposerLastName,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestPDob = RequestBody.create(proposerDob,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestProposerGender = RequestBody.create(proposerGender,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestMarital = RequestBody.create(proposerMaritalStatus,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestNmMobile = RequestBody.create(proposerMobile,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestNmEmerMobile = RequestBody.create(proposerMobileEmergency,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestNmEmail = RequestBody.create(proposerEmail,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestNmEmerEmail = RequestBody.create(proposerNameEmergency,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestPan = RequestBody.create(proposerPan,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestPincode = RequestBody.create(pincode,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestAddress1 = RequestBody.create(address1,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestAddress2 = RequestBody.create(address2,
                    MediaType.parse("multipart/form-data"));
            RequestBody requestPType = RequestBody.create("ios",
                    MediaType.parse("multipart/form-data"));
            RequestBody requestSameTraveller = RequestBody.create("1",
                    MediaType.parse("multipart/form-data"));
            RequestBody requestToken = RequestBody.create(AppUtils.TOKEN,
                    MediaType.parse("multipart/form-data"));

            Call<TempResponse> call = apiService.updateHealthProposalIffco(requestToken,
                    requestCompanyName, requestQuoteId, requestTitleName, requestFirstName,
                    requestLastName, requestGender, requestDob, requestHeight, requestWeight,
                    requestOccupation, requestRelation, requestNomineeRelation, requestNomineeFName,
                    requestNomineeLName, requestCigarette, requestAlcohol, requestTobacco,
                    requestNmDob, requestPTitle, requestPFName, requestPLName, requestPDob,
                    requestProposerGender, requestMarital, requestNmMobile, requestNmEmerMobile,
                    requestNmEmail, requestNmEmerEmail, requestPan, requestPincode, requestPincode,
                    requestAddress1, requestAddress2, requestPType, requestSameTraveller);

            call.enqueue(new Callback<TempResponse>() {
                @Override
                public void onResponse(@NotNull Call<TempResponse> call,
                                       @NotNull Response<TempResponse> response) {
                    if (response.isSuccessful()) {
                        TempResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);

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

    public void updateHealthProposal(final Context cxt, String company, String quotationId,
                                     ArrayList<String> nomineePincode, ArrayList<String> nomineeCity,
                                     ArrayList<String> tpa, ArrayList<String> proposerFirstName,
                                     ArrayList<String> proposerLastName,
                                     ArrayList<String> proposerDob,
                                     ArrayList<String> proposerGender,
                                     ArrayList<String> proposerOccupation,
                                     ArrayList<String> proposerDesignation,
                                     ArrayList<String> proposerQualification,
                                     ArrayList<String> proposerMaritalStatus,
                                     ArrayList<String> proposerMobile,
                                     ArrayList<String> proposerEmail,
                                     ArrayList<String> proposerDocument,
                                     ArrayList<String> proposerDocumentNo,
                                     ArrayList<String> proposerAddress1,
                                     ArrayList<String> proposerAddress2,
                                     ArrayList<String> proposerAddress3,
                                     ArrayList<String> proposerPincode,
                                     ArrayList<String> proposerCity,
                                     ArrayList<String> firstName, ArrayList<String> lastName,
                                     ArrayList<String> gender, ArrayList<String> dob,
                                     ArrayList<String> occupation, ArrayList<String> designation,
                                     ArrayList<String> height, ArrayList<String> weight,
                                     ArrayList<String> que1, ArrayList<String> que2,
                                     ArrayList<String> que3, ArrayList<String> que4,
                                     ArrayList<String> que5, ArrayList<String> que6,
                                     ArrayList<String> alcohol, ArrayList<String> smoke,
                                     ArrayList<String> tobacco, ArrayList<String> narcotics,
                                     ArrayList<String> substance, ArrayList<String> nomineeRelation,
                                     ArrayList<String> nomineeFirstName,
                                     ArrayList<String> nomineeLastName,
                                     ArrayList<String> nomineeDob, ArrayList<String> nomineeGender,
                                     ArrayList<String> nomineeMobile, ArrayList<String> nomineeAddress1,
                                     ArrayList<String> nomineeAddress2, ArrayList<String> appointeeFName,
                                     ArrayList<String> appointeeLName,
                                     ArrayList<String> appointeeAge,
                                     ArrayList<String> appointeeRelation,
                                     ArrayList<String> alQuantity, ArrayList<String> alYear,
                                     ArrayList<String> smQuantity, ArrayList<String> smYear,
                                     ArrayList<String> tbQuantity, ArrayList<String> tbYear,
                                     ArrayList<String> narQuantity, ArrayList<String> narYear,
                                     ArrayList<String> osQuantity, ArrayList<String> osYear,
                                     ArrayList<String> medMonth, ArrayList<String> medYear,
                                     ArrayList<String> nameIllness, ArrayList<String> treatment,
                                     ArrayList<String> outCome, ArrayList<String> proposerTitle,
                                     ArrayList<String> maritalStatus,
                                     ArrayList<String> salutation, ArrayList<String> relation,
                                     ArrayList<String> cigarette, ArrayList<String> pouch,
                                     ArrayList<String> liquor, ArrayList<String> beer,
                                     ArrayList<String> wine, ArrayList<String> anyDiseases,
                                     ArrayList<String> addressMobile,
                                     ArrayList<String> addressEmail, ArrayList<String> address1,
                                     ArrayList<String> address2, ArrayList<String> area,
                                     ArrayList<String> pincode, ArrayList<String> annualIncome,
                                     ArrayList<String> title_nominee, ArrayList<String> exists,
                                     ArrayList<String> tobaccoBajaj, ArrayList<String> building,
                                     ArrayList<String> street, ArrayList<String> monthlyIncome,
                                     ArrayList<String> sameTraveller) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<TempResponse> call = apiService.updateHealthProposal(AppUtils.TOKEN, company,
                    quotationId, nomineePincode, nomineeCity, tpa, proposerFirstName,
                    proposerLastName, proposerDob, proposerGender, proposerOccupation,
                    proposerDesignation, proposerQualification, proposerMaritalStatus,
                    proposerMobile, proposerEmail, proposerDocument, proposerDocumentNo,
                    proposerAddress1, proposerAddress2, proposerAddress3, proposerPincode,
                    proposerCity, firstName, lastName, gender, dob, occupation, designation, height,
                    weight, que1, que2, que3, que4, que5, que6, alcohol, smoke, tobacco, narcotics,
                    substance, nomineeRelation, nomineeFirstName, nomineeLastName, nomineeDob,
                    nomineeGender, nomineeMobile, nomineeAddress1, nomineeAddress2, appointeeFName,
                    appointeeLName, appointeeAge, appointeeRelation, alQuantity, alYear, smQuantity,
                    smYear, tbQuantity, tbYear, narQuantity, narYear, osQuantity, osYear, medMonth,
                    medYear, nameIllness, treatment, outCome, proposerTitle, maritalStatus, salutation, relation,
                    cigarette, pouch, liquor, beer, wine, anyDiseases, addressMobile, addressEmail,
                    address1, address2, area, pincode, annualIncome, title_nominee, exists,
                    tobaccoBajaj, building, street, monthlyIncome, sameTraveller);

            call.enqueue(new Callback<TempResponse>() {
                @Override
                public void onResponse(@NotNull Call<TempResponse> call,
                                       @NotNull Response<TempResponse> response) {
                    if (response.isSuccessful()) {
                        TempResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);

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

    public void updateHealthProposalBajaj(final Context cxt, String company, String quotationId,

                                          ArrayList<String> title,
                                          ArrayList<String> firstName, ArrayList<String> lastName,
                                          ArrayList<String> gender, ArrayList<String> dob,
                                          ArrayList<String> occupation,
                                          ArrayList<String> height, ArrayList<String> weight,
                                          ArrayList<String> mobile, ArrayList<String> email,
                                          ArrayList<String> exists, ArrayList<String> tobacco,
                                          ArrayList<String> tables, ArrayList<String> tables_value,
                                          ArrayList<String> nomineeFirstName,
                                          ArrayList<String> nomineeLastName,
                                          ArrayList<String> nomineeRelation, ArrayList<String> building,
                                          ArrayList<String> street, ArrayList<String> address1,
                                          ArrayList<String> pincode, ArrayList<String> monthlyIncome,
                                          ArrayList<String> sameTraveller) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<TempResponse> call = apiService.updateHealthProposalBajaj(AppUtils.TOKEN, company,
                    quotationId, title, firstName, lastName, gender, dob, occupation, height,
                    weight, mobile, email, exists, tobacco, tables, tables_value, nomineeFirstName,
                    nomineeLastName, nomineeRelation, building, street, address1, pincode,
                    monthlyIncome, sameTraveller);

            call.enqueue(new Callback<TempResponse>() {
                @Override
                public void onResponse(@NotNull Call<TempResponse> call,
                                       @NotNull Response<TempResponse> response) {
                    if (response.isSuccessful()) {
                        TempResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);

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

    public void getSbiPincode(final Context mContext, String quotationId, String company) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<PincodeMaster> call = apiService.getSbiPincode(AppUtils.TOKEN, quotationId,
                    "pincode", company);
            call.enqueue(new Callback<PincodeMaster>() {
                @Override
                public void onResponse(@NotNull Call<PincodeMaster> call,
                                       @NotNull Response<PincodeMaster> response) {
                    if (response.isSuccessful()) {
                        PincodeMaster commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                        if (commonResponse != null && commonResponse.getPincode().size() > 0)
                            setSbiPincodeList((ArrayList<Pincode>) commonResponse.getPincode());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<PincodeMaster> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getHealthQuotation(final Context cxt, String company, String qId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<HealthPremium> call = apiService.getHealthQuotation(AppUtils.TOKEN, company, qId);

            call.enqueue(new Callback<HealthPremium>() {
                @Override
                public void onResponse(@NotNull Call<HealthPremium> call, @NotNull Response<HealthPremium> response) {
                    if (response.isSuccessful()) {
                        HealthPremium commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<HealthPremium> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void filterHealth(final Context cxt, String sumInsured, String pincode, String tenure,
                             String qId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<TempResponse> call = apiService.filterHealth(AppUtils.TOKEN, sumInsured, tenure,
                    pincode, qId);

            call.enqueue(new Callback<TempResponse>() {
                @Override
                public void onResponse(@NotNull Call<TempResponse> call,
                                       @NotNull Response<TempResponse> response) {
                    if (response.isSuccessful()) {
                        TempResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);

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

    public void getPaymentHealthTrack(final Context mContext, String quotationId) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<HealthPaymentTrack> call = apiService.getPaymentHealthTrack(quotationId, AppUtils.TOKEN);
            call.enqueue(new Callback<HealthPaymentTrack>() {
                @Override
                public void onResponse(@NonNull Call<HealthPaymentTrack> call,
                                       @NonNull Response<HealthPaymentTrack> response) {
                    if (response.isSuccessful()) {
                        HealthPaymentTrack commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<HealthPaymentTrack> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getGender(final Context mContext, String quotationId, String company) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<HealthGender> call = apiService.getHealthGender(AppUtils.TOKEN, quotationId,
                    "gender", company);
            call.enqueue(new Callback<HealthGender>() {
                @Override
                public void onResponse(@NotNull Call<HealthGender> call,
                                       @NotNull Response<HealthGender> response) {
                    if (response.isSuccessful()) {
                        HealthGender commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                        if (commonResponse != null && commonResponse.getGender().size() > 0)
                            setGenderList((ArrayList<Gender>) commonResponse.getGender());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<HealthGender> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getHealthPayment(final Context mContext, String quotationId) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<Session> call = apiService.getHealthPayment(quotationId);
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

    public void getPinCodeHealth(final Context mContext, String quotationId, String company,
                                 String pin) {

        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<PincodeMaster> call = apiService.getHealthPincode(AppUtils.TOKEN, quotationId,
                    "pincode", company, pin);
            call.enqueue(new Callback<PincodeMaster>() {
                @Override
                public void onResponse(@NotNull Call<PincodeMaster> call,
                                       @NotNull Response<PincodeMaster> response) {
                    if (response.isSuccessful()) {
                        PincodeMaster commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<PincodeMaster> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getSalutation(final Context mContext, String quotationId, String company) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<HealthSalutation> call = apiService.getSalutation(AppUtils.TOKEN, quotationId,
                    "salutation", company);
            call.enqueue(new Callback<HealthSalutation>() {
                @Override
                public void onResponse(@NotNull Call<HealthSalutation> call,
                                       @NotNull Response<HealthSalutation> response) {
                    if (response.isSuccessful()) {
                        HealthSalutation commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);

                        if (commonResponse != null && commonResponse.getSalutation().size() > 0) {
                            setTitleList(commonResponse.getSalutation());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<HealthSalutation> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMaritalStatus(final Context mContext, String quotationId, String company) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<HealthMarital> call = apiService.getMarital(AppUtils.TOKEN, quotationId,
                    "marital_status", company);
            call.enqueue(new Callback<HealthMarital>() {
                @Override
                public void onResponse(@NotNull Call<HealthMarital> call,
                                       @NotNull Response<HealthMarital> response) {
                    if (response.isSuccessful()) {
                        HealthMarital commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<HealthMarital> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getHealthCity(final Context mContext, String quotationId, String company) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<HealthCity> call = apiService.getHealthCity(AppUtils.TOKEN, quotationId,
                    "city", company);
            call.enqueue(new Callback<HealthCity>() {
                @Override
                public void onResponse(@NotNull Call<HealthCity> call,
                                       @NotNull Response<HealthCity> response) {
                    if (response.isSuccessful()) {
                        HealthCity commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<HealthCity> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getHealthBajajCity(final Context mContext, String quotationId, String company) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<BajajPincode> call = apiService.getHealthBajajCity(AppUtils.TOKEN, quotationId,
                    "city", company);
            call.enqueue(new Callback<BajajPincode>() {
                @Override
                public void onResponse(@NotNull Call<BajajPincode> call,
                                       @NotNull Response<BajajPincode> response) {
                    if (response.isSuccessful()) {
                        BajajPincode commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<BajajPincode> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getOccupation(final Context mContext, String quotationId, String company) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<HealthOccupation> call = apiService.getOccupation(AppUtils.TOKEN, quotationId,
                    "occupation", company);
            call.enqueue(new Callback<HealthOccupation>() {
                @Override
                public void onResponse(@NotNull Call<HealthOccupation> call,
                                       @NotNull Response<HealthOccupation> response) {
                    if (response.isSuccessful()) {
                        HealthOccupation commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                        if (commonResponse != null && commonResponse.getOccupation().size() > 0) {
                            setOccList(commonResponse.getOccupation());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<HealthOccupation> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getRelationIcici(final Context mContext, String quotationId, String company) {

        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {

            Call<HealthRelation> call = apiService.getRelation(AppUtils.TOKEN, quotationId,
                    "relation_with_applicant", company);
            call.enqueue(new Callback<HealthRelation>() {
                @Override
                public void onResponse(@NotNull Call<HealthRelation> call,
                                       @NotNull Response<HealthRelation> response) {
                    if (response.isSuccessful()) {
                        HealthRelation commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                        if (commonResponse != null && commonResponse.getRelation().size() > 0)
                            setRelationList((ArrayList<Relation>) commonResponse.getRelation());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<HealthRelation> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getRelation(final Context mContext, String quotationId, String company) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<HealthRelation> call = apiService.getRelation(AppUtils.TOKEN, quotationId,
                    "relation", company);
            call.enqueue(new Callback<HealthRelation>() {
                @Override
                public void onResponse(@NotNull Call<HealthRelation> call,
                                       @NotNull Response<HealthRelation> response) {
                    if (response.isSuccessful()) {
                        HealthRelation commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);

                        if (commonResponse != null && commonResponse.getRelation().size() > 0)
                            setRelationList((ArrayList<Relation>) commonResponse.getRelation());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<HealthRelation> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getHealthState(final Context mContext, String quotationId, String company) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<HealthState> call = apiService.getState(AppUtils.TOKEN, quotationId,
                    "state", company);
            call.enqueue(new Callback<HealthState>() {
                @Override
                public void onResponse(@NotNull Call<HealthState> call,
                                       @NotNull Response<HealthState> response) {
                    if (response.isSuccessful()) {
                        HealthState commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<HealthState> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Document> getDocList() {
        return docList;
    }

    public void setDocList(List<Document> docList) {
        this.docList = docList;
    }

    public List<Document> docList = new ArrayList();

    public void getHealthDocument(final Context mContext, String quotationId, String company) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<HealthDocument> call = apiService.getHealthDocument(AppUtils.TOKEN, quotationId,
                    "document", company);
            call.enqueue(new Callback<HealthDocument>() {
                @Override
                public void onResponse(@NotNull Call<HealthDocument> call,
                                       @NotNull Response<HealthDocument> response) {
                    if (response.isSuccessful()) {
                        HealthDocument commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);

                        if (commonResponse != null && commonResponse.getDocument().size() > 0) {
                            setDocList(commonResponse.getDocument());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<HealthDocument> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getQualification(final Context mContext, String quotationId) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<HealthQualification> call = apiService.getHealthQualification(AppUtils.TOKEN,
                    quotationId, "education", "royal");

            call.enqueue(new Callback<HealthQualification>() {
                @Override
                public void onResponse(@NotNull Call<HealthQualification> call,
                                       @NotNull Response<HealthQualification> response) {
                    if (response.isSuccessful()) {
                        HealthQualification commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<HealthQualification> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAppointee(final Context mContext, String quotationId) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<HealthApointee> call = apiService.getAppointee(AppUtils.TOKEN,
                    quotationId, "appointee", "royal");

            call.enqueue(new Callback<HealthApointee>() {
                @Override
                public void onResponse(@NotNull Call<HealthApointee> call,
                                       @NotNull Response<HealthApointee> response) {
                    if (response.isSuccessful()) {
                        HealthApointee commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<HealthApointee> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void companyProposal(final Context mContext, String quotationId, Float gross, Float net,
                                Float tax, String extra, String planName, String planCode,
                                String sumInsured, String company) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<CommonResponse> call = apiService.companyProposal(AppUtils.TOKEN, quotationId,
                    gross, net, tax, extra, planName, planCode, sumInsured, company);

            call.enqueue(new Callback<CommonResponse>() {
                @Override
                public void onResponse(@NotNull Call<CommonResponse> call,
                                       @NotNull Response<CommonResponse> response) {
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

    public void compareData(final Context mContext, String companies, String sumInsured,
                            String plan, String subPlan, String tenure, String premium,
                            String appKey) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<Compare> call = apiService.compareData(companies, sumInsured, plan, subPlan,
                    tenure, premium, AppUtils.encode(appKey));
            call.enqueue(new Callback<Compare>() {
                @Override
                public void onResponse(@NotNull Call<Compare> call,
                                       @NotNull Response<Compare> response) {
                    if (response.isSuccessful()) {
                        Compare commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);

                    }
                }

                @Override
                public void onFailure(@NotNull Call<Compare> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
