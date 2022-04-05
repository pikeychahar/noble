package com.square.pos.manager;

import android.content.Context;

import com.square.pos.activity_pos.CorporateList;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.model.BasicResponse;
import com.square.pos.model_pos.Assign;
import com.square.pos.model_pos.EarningList;
import com.square.pos.model_pos.NewLead;
import com.square.pos.network.ApiClient;
import com.square.pos.network.ApiInterface;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prahalad Chahar on 2020-01-08.
 */
public class AgentManager {
    private static AgentManager instance;

    private AgentManager() {

    }

    public static AgentManager getInstance() {
        if (instance == null)
            instance = new AgentManager();

        return instance;
    }

    public void earning(final Context mContext, String agentId) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<EarningList> call = apiService.earning(agentId);
            call.enqueue(new Callback<EarningList>() {
                @Override
                public void onResponse(Call<EarningList> call, Response<EarningList> response) {
                    if (response.isSuccessful()) {
                        EarningList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<EarningList> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeDoc(final Context mContext, String docId) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<BasicResponse> call = apiService.removeDoc(docId);
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

    public void getCorporateList(final Context mContext) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<CorporateList> call = apiService.getCorporateList();
            call.enqueue(new Callback<CorporateList>() {
                @Override
                public void onResponse(Call<CorporateList> call, Response<CorporateList> response) {
                    if (response.isSuccessful()) {
                        CorporateList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<CorporateList> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addLead(final Context cxt, String agentId, String leadType, String name,
                        String email, String mobile, String vehicleType, String vehicleNumber,
                        String regYear, String makeName, String modelName, String variantName,
                        String expDate, String fuelType, String policyType, String policyExpStatus,
                        String previousInsure, String cityHealth, String gender, String sumAssured,
                        String planType, String selfAge, String spouseAge, String fatherAge,
                        String motherAge, String sonAge, String daughterAge, String corporateCity,
                        String insuranceType, String orgName, String leadFrom, String posName,
                        String referenceName, String assign, String businessType, String isPrePolicy,
                        String healthPolicyType, String rCFront, String rCBack, String invoice,
                        String documentFile, String documentFile2, String docKYC, String docOther) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        File fileDocument, fileDocument2, fileRCFront, fileRCBack, fileInvoice,fileKYC, fileOtherDoc;;
        MultipartBody.Part documentRCFront, documentRCBack, documentInvoice, documentKYC,
                documentOtherDoc;

        documentRCFront = documentRCBack = documentInvoice = documentOtherDoc = documentKYC = null;

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

            documentRCFront = MultipartBody.Part.createFormData("rc_front_image", "", attachmentEmpty);
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

            documentRCBack = MultipartBody.Part.createFormData("rc_back_image", "", attachmentEmpty);
        }
        if (invoice != null) {
            try {
                fileInvoice = new File(invoice);
                if (fileInvoice.exists()) {
                    documentInvoice = MultipartBody.Part.createFormData("invoice_image",
                            fileInvoice.getName(),
                            RequestBody.create(MediaType.parse("multipart/form-data"), fileInvoice));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");

            documentInvoice = MultipartBody.Part.createFormData("invoice_image", "", attachmentEmpty);
        }
        if (docKYC != null) {
            try {
                fileKYC = new File(docKYC);
                if (fileKYC.exists()) {
                    documentKYC = MultipartBody.Part.createFormData("kyc_documents",
                            fileKYC.getName(),
                            RequestBody.create(fileKYC, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");

            documentKYC = MultipartBody.Part.createFormData("kyc_documents", "", attachmentEmpty);
        }

        if (docOther != null) {
            try {
                fileOtherDoc = new File(docOther);
                if (fileOtherDoc.exists()) {
                    documentOtherDoc = MultipartBody.Part.createFormData("other_documents",
                            fileOtherDoc.getName(),
                            RequestBody.create(fileOtherDoc, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");

            documentOtherDoc = MultipartBody.Part.createFormData("other_documents", "", attachmentEmpty);
        }



        MultipartBody.Part[] documents;
        documents = new MultipartBody.Part[2];

        if (documentFile != null) {
            try {
                fileDocument = new File(documentFile);
                if (fileDocument.exists()) {
                    documents[0] = MultipartBody.Part.createFormData("previous_policy_documents[]",
                            fileDocument.getName(),
                            RequestBody.create(fileDocument, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty =
                    RequestBody.create("", MediaType.parse("text/plain"));

            documents[0] =
                    MultipartBody.Part.createFormData("previous_policy_documents[]", "", attachmentEmpty);
        }
        if (documentFile2 != null) {
            try {
                fileDocument2 = new File(documentFile2);
                if (fileDocument2.exists()) {
                    documents[1] = MultipartBody.Part.createFormData("previous_policy_documents[]",
                            fileDocument2.getName(),
                            RequestBody.create(fileDocument2, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty =
                    RequestBody.create("", MediaType.parse("text/plain"));

            documents[1] =
                    MultipartBody.Part.createFormData("previous_policy_documents[]", "", attachmentEmpty);
        }

        RequestBody requestAgentId =
                RequestBody.create(agentId, MediaType.parse("multipart/form-data"));

        String addedBy = "Agent";
        RequestBody requestAddedBy =
                RequestBody.create(addedBy, MediaType.parse("multipart/form-data"));

        RequestBody requestType =
                RequestBody.create(leadType, MediaType.parse("multipart/form-data"));

        RequestBody requestName =
                RequestBody.create(name, MediaType.parse("multipart/form-data"));

        RequestBody requestEmail =
                RequestBody.create(email, MediaType.parse("multipart/form-data"));

        RequestBody requestMobile =
                RequestBody.create(mobile, MediaType.parse("multipart/form-data"));

        RequestBody requestVehicleType =
                RequestBody.create(vehicleType, MediaType.parse("multipart/form-data"));

        RequestBody requestVehicleNumber =
                RequestBody.create(vehicleNumber, MediaType.parse("multipart/form-data"));

        RequestBody requestRegYear =
                RequestBody.create(regYear, MediaType.parse("multipart/form-data"));

        RequestBody requestMake =
                RequestBody.create(makeName, MediaType.parse("multipart/form-data"));

        RequestBody requestModel =
                RequestBody.create(modelName, MediaType.parse("multipart/form-data"));

        RequestBody requestVariant =
                RequestBody.create(variantName, MediaType.parse("multipart/form-data"));

        RequestBody requestExpDate =
                RequestBody.create(expDate, MediaType.parse("multipart/form-data"));

        RequestBody requestFuelType =
                RequestBody.create(fuelType, MediaType.parse("multipart/form-data"));

        RequestBody requestPolicyType =
                RequestBody.create(policyType, MediaType.parse("multipart/form-data"));

        RequestBody requestPolicyExpStatus =
                RequestBody.create(policyExpStatus, MediaType.parse("multipart/form-data"));

        RequestBody requestPreviousInsure =
                RequestBody.create(previousInsure, MediaType.parse("multipart/form-data"));

        RequestBody requestCityHealth =
                RequestBody.create(cityHealth, MediaType.parse("multipart/form-data"));

        RequestBody requestGender =
                RequestBody.create(gender, MediaType.parse("multipart/form-data"));

        RequestBody requestSumAssured =
                RequestBody.create(sumAssured, MediaType.parse("multipart/form-data"));

        RequestBody requestPlanType =
                RequestBody.create(planType, MediaType.parse("multipart/form-data"));

        RequestBody requestSelfAge =
                RequestBody.create(selfAge, MediaType.parse("multipart/form-data"));

        RequestBody requestSpouseAge =
                RequestBody.create(spouseAge, MediaType.parse("multipart/form-data"));

        RequestBody requestFatherAge =
                RequestBody.create(fatherAge, MediaType.parse("multipart/form-data"));

        RequestBody requestMotherAge =
                RequestBody.create(motherAge, MediaType.parse("multipart/form-data"));

        RequestBody requestSonAge =
                RequestBody.create(sonAge, MediaType.parse("multipart/form-data"));

        RequestBody requestDaughterAge =
                RequestBody.create(daughterAge, MediaType.parse("multipart/form-data"));

        RequestBody requestCorporateCity =
                RequestBody.create(corporateCity, MediaType.parse("multipart/form-data"));

        RequestBody requestInsType =
                RequestBody.create(insuranceType, MediaType.parse("multipart/form-data"));

        RequestBody requestOrgName =
                RequestBody.create(orgName, MediaType.parse("multipart/form-data"));

        RequestBody requestLeadFrom =
                RequestBody.create(leadFrom, MediaType.parse("multipart/form-data"));

        RequestBody requestPosName =
                RequestBody.create(posName, MediaType.parse("multipart/form-data"));

        RequestBody requestRefName =
                RequestBody.create(referenceName, MediaType.parse("multipart/form-data"));

        RequestBody requestAssign =
                RequestBody.create(assign, MediaType.parse("multipart/form-data"));

        RequestBody requestBusinessType =
                RequestBody.create(businessType, MediaType.parse("multipart/form-data"));

        RequestBody requestHealthPolicyType =
                RequestBody.create(healthPolicyType, MediaType.parse("multipart/form-data"));

        RequestBody requestPrePolicyStatus =
                RequestBody.create(isPrePolicy, MediaType.parse("multipart/form-data"));

        Call<NewLead> call = apiService.addLead(requestAddedBy, requestAgentId, requestAgentId, requestType,
                requestName, requestEmail, requestMobile, requestVehicleType, requestVehicleNumber,
                requestRegYear, requestMake, requestModel, requestVariant, requestExpDate,
                requestFuelType, requestPolicyType, requestPolicyExpStatus, requestPreviousInsure,
                requestCityHealth, requestGender, requestSumAssured, requestPlanType, requestSelfAge,
                requestSpouseAge, requestFatherAge, requestMotherAge, requestSonAge,
                requestDaughterAge, requestCorporateCity, requestInsType, requestOrgName,
                requestLeadFrom, requestPosName, requestRefName, requestAssign, requestBusinessType,
                requestPrePolicyStatus, requestHealthPolicyType, documentRCFront, documentRCBack,
                documentInvoice, documents, documentKYC, documentOtherDoc);


        call.enqueue(new Callback<NewLead>() {
            @Override
            public void onResponse(Call<NewLead> call, Response<NewLead> response) {

                if (response.isSuccessful()) {
                    NewLead assessmentListResponse = response.body();
                    ((onRequestCompleteCallBackListener)cxt ).
                            onResponse(assessmentListResponse);
                }
            }

            @Override
            public void onFailure(Call<NewLead> call, Throwable t) {
                //onResponse(t);
                t.printStackTrace();
            }
        });
    }
    public void getAssignList(final Context mContext, String leadType) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<Assign> call = apiService.getAssignList(leadType);
            call.enqueue(new Callback<Assign>() {
                @Override
                public void onResponse(Call<Assign> call, Response<Assign> response) {
                    if (response.isSuccessful()) {
                        Assign commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(Call<Assign> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
