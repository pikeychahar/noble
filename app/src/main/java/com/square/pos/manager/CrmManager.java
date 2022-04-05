package com.square.pos.manager;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.model.BasicResponse;
import com.square.pos.model.LeadUrl;
import com.square.pos.model.SendOtp;
import com.square.pos.model.SharePoster;
import com.square.pos.model_crm.AddonList;
import com.square.pos.model_crm.BasicBool;
import com.square.pos.model_crm.BasicBoolResponse;
import com.square.pos.model_crm.CancellationData;
import com.square.pos.model_crm.CancellationList;
import com.square.pos.model_crm.ChatList;
import com.square.pos.model_crm.ChildPosOtp;
import com.square.pos.model_crm.ClaimList;
import com.square.pos.model_crm.ClaimPincode;
import com.square.pos.model_crm.ClaimView;
import com.square.pos.model_crm.CreateClaim;
import com.square.pos.model_crm.CreateRenewal;
import com.square.pos.model_crm.CrmQuotes;
import com.square.pos.model_crm.DetailedPolicyList;
import com.square.pos.model_crm.EndorseCreatedList;
import com.square.pos.model_crm.EndorseViewData;
import com.square.pos.model_crm.EndorsementList;
import com.square.pos.model_crm.InsurerNewList;
import com.square.pos.model_crm.MobilePosList;
import com.square.pos.model_crm.MyCancelled;
import com.square.pos.model_crm.MyClaim;
import com.square.pos.model_crm.MyTicket;
import com.square.pos.model_crm.OfflineList;
import com.square.pos.model_crm.OfflineView;
import com.square.pos.model_crm.RaiseTicket;
import com.square.pos.model_crm.RenewalList;
import com.square.pos.model_crm.ResponseBool;
import com.square.pos.model_crm.SingleSurvey;
import com.square.pos.model_crm.StatementList;
import com.square.pos.model_crm.SurveyList;
import com.square.pos.model_crm.TicketList;
import com.square.pos.model_crm.VehicleData;
import com.square.pos.model_crm.policy.CrmMaster;
import com.square.pos.network.ApiClient;
import com.square.pos.network.ApiInterface;
import com.square.pos.utils.AppUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prahalad Chahar on 20/08/21.
 */
public class CrmManager {

    private static CrmManager instance;
    static String source = "App";

    private CrmManager() {
    }

    public static CrmManager getInstance() {

        if (instance == null)
            instance = new CrmManager();
        return instance;
    }

    public EndorseViewData getEndorseViewData() {
        return endorseViewData;
    }

    public void setEndorseViewData(EndorseViewData endorseViewData) {
        this.endorseViewData = endorseViewData;
    }

    private EndorseViewData endorseViewData = new EndorseViewData();

    public void createTicket(final Context cxt, String userId, String userType, String quoteId,
                             String policyType, String isRC, String vehicleType, String make,
                             String model, String variant, String fuelType, String cubic,
                             String seating, String gvw, String insureName, String cityName,
                             String pageUrl, String pageType, String policyNo, String requestNo,
                             String remark, String paymentDoneDoc, String rcFrontDoc,
                             String rcBackDoc, String errorDoc, String attachmentDoc) {


        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        File filePaymentDone, fileRCFront, fileRCBack, fileError, fileAttachment;
        MultipartBody.Part documentPayment, documentRCFront, documentRCBack, documentErrorDoc,
                documentAttachment;
        documentPayment = documentRCFront = documentRCBack = documentErrorDoc = documentAttachment
                = null;

        if (paymentDoneDoc != null) {
            try {
                filePaymentDone = new File(paymentDoneDoc);
                if (filePaymentDone.exists()) {
                    filePaymentDone = Compressor.getDefault(cxt).compressToFile(filePaymentDone);
                    documentPayment = MultipartBody.Part.createFormData("Payment_Screen",
                            filePaymentDone.getName(),
                            RequestBody.create(paymentDoneDoc, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));

            documentPayment = MultipartBody.Part.createFormData("attachment", "",
                    attachmentEmpty);
        }

        if (rcFrontDoc != null) {
            try {
                fileRCFront = new File(rcFrontDoc);
                if (fileRCFront.exists()) {
                    documentRCFront = MultipartBody.Part.createFormData("Rc_Front",
                            fileRCFront.getName(),
                            RequestBody.create(fileRCFront, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));
            documentRCFront = MultipartBody.Part.createFormData("Rc_Front", "",
                    attachmentEmpty);
        }

        if (rcBackDoc != null) {
            try {
                fileRCBack = new File(rcBackDoc);
                if (fileRCBack.exists()) {
                    documentRCBack = MultipartBody.Part.createFormData("Rc_Back",
                            fileRCBack.getName(),
                            RequestBody.create(fileRCBack, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));
            documentRCBack = MultipartBody.Part.createFormData("Rc_Back", "",
                    attachmentEmpty);
        }

        if (errorDoc != null) {
            try {
                fileError = new File(errorDoc);
                if (fileError.exists()) {
                    documentErrorDoc = MultipartBody.Part.createFormData("Page_Error_Screen",
                            fileError.getName(),
                            RequestBody.create(fileError, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));
            documentErrorDoc = MultipartBody.Part.createFormData("Page_Error_Screen", "",
                    attachmentEmpty);
        }

        if (attachmentDoc != null) {
            try {
                fileAttachment = new File(attachmentDoc);
                if (fileAttachment.exists()) {
                    documentAttachment = MultipartBody.Part.createFormData("Attachement",
                            fileAttachment.getName(),
                            RequestBody.create(fileAttachment, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));
            documentAttachment = MultipartBody.Part.createFormData("Attachement", "",
                    attachmentEmpty);
        }

        RequestBody requestUserId = RequestBody.create(userId,
                MediaType.parse("multipart/form-data"));
        RequestBody requestUserType = RequestBody.create(userType,
                MediaType.parse("multipart/form-data"));
        RequestBody requestQuoteId = RequestBody.create(quoteId,
                MediaType.parse("multipart/form-data"));
        RequestBody requestPolicyType = RequestBody.create(policyType,
                MediaType.parse("multipart/form-data"));
        RequestBody requestIsRC = RequestBody.create(isRC,
                MediaType.parse("multipart/form-data"));
        RequestBody requestVehicleType = RequestBody.create(vehicleType,
                MediaType.parse("multipart/form-data"));
        RequestBody requestMake = RequestBody.create(make,
                MediaType.parse("multipart/form-data"));
        RequestBody requestModel = RequestBody.create(model,
                MediaType.parse("multipart/form-data"));
        RequestBody requestVariant = RequestBody.create(variant,
                MediaType.parse("multipart/form-data"));
        RequestBody requestFuelType = RequestBody.create(fuelType,
                MediaType.parse("multipart/form-data"));
        RequestBody requestCubic = RequestBody.create(cubic,
                MediaType.parse("multipart/form-data"));
        RequestBody requestSeating = RequestBody.create(seating,
                MediaType.parse("multipart/form-data"));
        RequestBody requestGvw = RequestBody.create(gvw,
                MediaType.parse("multipart/form-data"));
        RequestBody requestInsuranceName = RequestBody.create(insureName,
                MediaType.parse("multipart/form-data"));
        RequestBody requestCityName = RequestBody.create(cityName,
                MediaType.parse("multipart/form-data"));
        RequestBody requestPageUrl = RequestBody.create(pageUrl,
                MediaType.parse("multipart/form-data"));
        RequestBody requestPageType = RequestBody.create(pageType,
                MediaType.parse("multipart/form-data"));
        RequestBody requestPolicyNo = RequestBody.create(policyNo,
                MediaType.parse("multipart/form-data"));
        RequestBody requestRequestNo = RequestBody.create(requestNo,
                MediaType.parse("multipart/form-data"));
        RequestBody requestRemark = RequestBody.create(remark,
                MediaType.parse("multipart/form-data"));
        RequestBody requestSource = RequestBody.create(source,
                MediaType.parse("multipart/form-data"));

        Call<RaiseTicket> call = apiService.createTicket(requestUserId, requestUserType,
                requestQuoteId, requestPolicyType, requestIsRC, requestVehicleType, requestMake,
                requestModel, requestVariant, requestFuelType, requestCubic, requestSeating,
                requestGvw, requestInsuranceName, requestCityName, requestPageUrl, requestPageType,
                requestPolicyNo, requestRequestNo, requestRemark, requestSource,
                documentPayment, documentRCFront, documentRCBack, documentErrorDoc,
                documentAttachment);

        call.enqueue(new Callback<RaiseTicket>() {
            @Override
            public void onResponse(@NonNull Call<RaiseTicket> call,
                                   @NonNull Response<RaiseTicket> response) {

                if (response.isSuccessful()) {
                    RaiseTicket assessmentListResponse = response.body();
                    ((onRequestCompleteCallBackListener) cxt).
                            onResponse(assessmentListResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RaiseTicket> call, @NonNull Throwable t) {
                //onResponse(t);
                t.printStackTrace();
            }
        });
    }


    public void raiseOffline(final Context cxt, String userId, String userType, String customerName,
                             String customerEmail, String customerMobile, String vehicleType,
                             String policyTypeId, String isNewVehicle, String registrationDate,
                             String RegStateCode, String RegDistrictCode, String RegCityCode,
                             String regCode, String IsPyp, String pExpiryDate, String isClaimRequest,
                             String ownerChange, String ncbProtection, String previousInsurer,
                             String lastYearNcb, String isBestQuote, String requiredInsurer,
                             String requiredAddons, String minIdv, String maxIdv, String remark,
                             String tenure, String tpExtDate, String tpPypNo, String tpPyp,
                             String rcFrontDoc, String rcBackDoc, String invoiceDoc, String pypDoc,
                             String attachmentDoc) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        File fileInvoice, fileRCFront, fileRCBack, filePyp, fileAttachment;
        MultipartBody.Part documentInvoice, documentRCFront, documentRCBack, documentPypDoc,
                documentAttachment;
        documentInvoice = documentRCFront = documentRCBack = documentPypDoc = documentAttachment
                = null;

        if (invoiceDoc != null) {
            try {
                fileInvoice = new File(invoiceDoc);
                if (fileInvoice.exists()) {
                    fileInvoice = Compressor.getDefault(cxt).compressToFile(fileInvoice);
                    documentInvoice = MultipartBody.Part.createFormData("InvoiceImage",
                            fileInvoice.getName(),
                            RequestBody.create(invoiceDoc, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));

            documentInvoice = MultipartBody.Part.createFormData("InvoiceImage", "",
                    attachmentEmpty);
        }

        if (rcFrontDoc != null) {
            try {
                fileRCFront = new File(rcFrontDoc);
                if (fileRCFront.exists()) {
                    documentRCFront = MultipartBody.Part.createFormData("RcFront",
                            fileRCFront.getName(),
                            RequestBody.create(fileRCFront, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));
            documentRCFront = MultipartBody.Part.createFormData("RcFront", "",
                    attachmentEmpty);
        }

        if (rcBackDoc != null) {
            try {
                fileRCBack = new File(rcBackDoc);
                if (fileRCBack.exists()) {
                    documentRCBack = MultipartBody.Part.createFormData("RcBack",
                            fileRCBack.getName(),
                            RequestBody.create(fileRCBack, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));
            documentRCBack = MultipartBody.Part.createFormData("RcBack", "",
                    attachmentEmpty);
        }

        if (pypDoc != null) {
            try {
                filePyp = new File(pypDoc);
                if (filePyp.exists()) {
                    documentPypDoc = MultipartBody.Part.createFormData("PreviousPolicyPdf",
                            filePyp.getName(),
                            RequestBody.create(pypDoc, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));
            documentPypDoc = MultipartBody.Part.createFormData("PreviousPolicyPdf", "",
                    attachmentEmpty);
        }

        if (attachmentDoc != null) {
            try {
                fileAttachment = new File(attachmentDoc);
                if (fileAttachment.exists()) {
                    documentAttachment = MultipartBody.Part.createFormData("OtherDocument",
                            fileAttachment.getName(),
                            RequestBody.create(fileAttachment, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));
            documentAttachment = MultipartBody.Part.createFormData("OtherDocument", "",
                    attachmentEmpty);
        }

        RequestBody requestUserId = RequestBody.create(userId,
                MediaType.parse("multipart/form-data"));
        RequestBody requestUserType = RequestBody.create(userType,
                MediaType.parse("multipart/form-data"));

        RequestBody requestCustomerName = RequestBody.create(customerName,
                MediaType.parse("multipart/form-data"));
        RequestBody requestCustomerEmail = RequestBody.create(customerEmail,
                MediaType.parse("multipart/form-data"));
        RequestBody requestCustomerMobile = RequestBody.create(customerMobile,
                MediaType.parse("multipart/form-data"));
        RequestBody requestVehicleType = RequestBody.create(vehicleType,
                MediaType.parse("multipart/form-data"));
        RequestBody requestPolicyTypeId = RequestBody.create(policyTypeId,
                MediaType.parse("multipart/form-data"));
        RequestBody requestIsNewVehicle = RequestBody.create(isNewVehicle,
                MediaType.parse("multipart/form-data"));
        RequestBody requestRegistrationDate = RequestBody.create(registrationDate,
                MediaType.parse("multipart/form-data"));
        RequestBody requestRegStateCode = RequestBody.create(RegStateCode,
                MediaType.parse("multipart/form-data"));
        RequestBody requestRegDistrictCode = RequestBody.create(RegDistrictCode,
                MediaType.parse("multipart/form-data"));
        RequestBody requestRegCityCode = RequestBody.create(RegCityCode,
                MediaType.parse("multipart/form-data"));
        RequestBody requestRegCode = RequestBody.create(regCode,
                MediaType.parse("multipart/form-data"));
        RequestBody requestIsPyp = RequestBody.create(IsPyp,
                MediaType.parse("multipart/form-data"));
        RequestBody requestExpiryDate = RequestBody.create(pExpiryDate,
                MediaType.parse("multipart/form-data"));
        RequestBody requestIsClaimRequest = RequestBody.create(isClaimRequest,
                MediaType.parse("multipart/form-data"));
        RequestBody requestOwnerChange = RequestBody.create(ownerChange,
                MediaType.parse("multipart/form-data"));
        RequestBody requestNcbProtection = RequestBody.create(ncbProtection,
                MediaType.parse("multipart/form-data"));
        RequestBody requestPreviousInsurer = RequestBody.create(previousInsurer,
                MediaType.parse("multipart/form-data"));

        RequestBody requestLastYearNcb = RequestBody.create(lastYearNcb,
                MediaType.parse("multipart/form-data"));
        RequestBody requestIsBestQuote = RequestBody.create(isBestQuote,
                MediaType.parse("multipart/form-data"));
        RequestBody requestRequiredInsurer = RequestBody.create(requiredInsurer,
                MediaType.parse("multipart/form-data"));
        RequestBody requestRequiredAddons = RequestBody.create(requiredAddons,
                MediaType.parse("multipart/form-data"));
        RequestBody requestMinIdv = RequestBody.create(minIdv,
                MediaType.parse("multipart/form-data"));
        RequestBody requestMaxIdv = RequestBody.create(maxIdv,
                MediaType.parse("multipart/form-data"));
        RequestBody requestTenure = RequestBody.create(tenure,
                MediaType.parse("multipart/form-data"));
        RequestBody requestTpExtDate = RequestBody.create(tpExtDate,
                MediaType.parse("multipart/form-data"));
        RequestBody requestTpPypNo = RequestBody.create(tpPypNo,
                MediaType.parse("multipart/form-data"));
        RequestBody requestTpPyp = RequestBody.create(tpPyp,
                MediaType.parse("multipart/form-data"));
        RequestBody requestRemark = RequestBody.create(remark,
                MediaType.parse("multipart/form-data"));
        RequestBody requestSource = RequestBody.create(source,
                MediaType.parse("multipart/form-data"));

        Call<BasicResponse> call = apiService.raiseOffline(requestUserId, requestUserType,
                requestCustomerName, requestCustomerEmail, requestCustomerMobile, requestVehicleType,
                requestPolicyTypeId, requestIsNewVehicle, requestRegistrationDate, requestRegStateCode,
                requestRegDistrictCode, requestRegCityCode, requestRegCode, requestIsPyp,
                requestExpiryDate, requestIsClaimRequest, requestOwnerChange, requestNcbProtection,
                requestPreviousInsurer, requestLastYearNcb, requestIsBestQuote,
                requestRequiredInsurer, requestRequiredAddons, requestMinIdv, requestMaxIdv,
                requestRemark, requestTenure, requestTpExtDate, requestTpPypNo, requestTpPyp,
                requestSource, documentRCFront, documentRCBack, documentInvoice, documentPypDoc,
                documentAttachment);

        call.enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(@NonNull Call<BasicResponse> call,
                                   @NonNull Response<BasicResponse> response) {

                if (response.isSuccessful()) {
                    BasicResponse assessmentListResponse = response.body();
                    ((onRequestCompleteCallBackListener) cxt).
                            onResponse(assessmentListResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BasicResponse> call, @NonNull Throwable t) {
                //onResponse(t);
                t.printStackTrace();
            }
        });
    }


    public void sendChat(final Context cxt, String userId, String userType, String currentId,
                         String currentType, String isAttachment, String message, String claimNo,
                         String claimId, String msgType, String otherDoc) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        File fileOther;
        MultipartBody.Part documentOther;
        documentOther = null;

        if (otherDoc != null) {
            try {
                fileOther = new File(otherDoc);
                if (fileOther.exists()) {
                    documentOther = MultipartBody.Part.createFormData("Attachement",
                            fileOther.getName(),
                            RequestBody.create(fileOther, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));
            documentOther = MultipartBody.Part.createFormData("attachment", "",
                    attachmentEmpty);
        }

        RequestBody requestUserId = RequestBody.create(userId,
                MediaType.parse("multipart/form-data"));
        RequestBody requestUserType = RequestBody.create(userType,
                MediaType.parse("multipart/form-data"));

        RequestBody requestCreateId = RequestBody.create(userId,
                MediaType.parse("multipart/form-data"));
        RequestBody requestCreateType = RequestBody.create(userType,
                MediaType.parse("multipart/form-data"));

        RequestBody requestCurrentId = RequestBody.create(currentId,
                MediaType.parse("multipart/form-data"));
        RequestBody requestCurrentType = RequestBody.create(currentType,
                MediaType.parse("multipart/form-data"));

        RequestBody requestAttachment = RequestBody.create(isAttachment,
                MediaType.parse("multipart/form-data"));
        RequestBody requestMessage = RequestBody.create(message,
                MediaType.parse("multipart/form-data"));
        RequestBody requestClaimNo = RequestBody.create(claimNo,
                MediaType.parse("multipart/form-data"));
        RequestBody requestClaimId = RequestBody.create(claimId,
                MediaType.parse("multipart/form-data"));
        RequestBody requestMsgType = RequestBody.create(msgType,
                MediaType.parse("multipart/form-data"));

        Call<BasicBool> call = apiService.sendChat(requestUserId, requestUserType,
                requestCreateId, requestCreateType, requestCurrentId, requestCurrentType,
                requestAttachment, requestMessage, requestClaimNo, requestClaimId, requestMsgType,
                documentOther);

        call.enqueue(new Callback<BasicBool>() {
            @Override
            public void onResponse(@NonNull Call<BasicBool> call,
                                   @NonNull Response<BasicBool> response) {

                if (response.isSuccessful()) {
                    BasicBool assessmentListResponse = response.body();
                    ((onRequestCompleteCallBackListener) cxt).
                            onResponse(assessmentListResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BasicBool> call, @NonNull Throwable t) {
                //onResponse(t);
                t.printStackTrace();
            }
        });
    }


    public void getCrmPincode(final Context mContext, String pincode) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<ClaimPincode> call = apiService.getCrmPincode(pincode);
            call.enqueue(new Callback<ClaimPincode>() {
                @Override
                public void onResponse(@NotNull Call<ClaimPincode> call,
                                       @NotNull Response<ClaimPincode> response) {
                    if (response.isSuccessful()) {
                        ClaimPincode commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ClaimPincode> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getClaimViewData(final Context mContext, String claimId) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<ClaimView> call = apiService.getClaimViewData(claimId, source);
            call.enqueue(new Callback<ClaimView>() {
                @Override
                public void onResponse(@NotNull Call<ClaimView> call,
                                       @NotNull Response<ClaimView> response) {
                    if (response.isSuccessful()) {
                        ClaimView commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ClaimView> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getEndorseView(final Context mContext, String eId) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<EndorseViewData> call = apiService.getEndorseView(eId, source);
            call.enqueue(new Callback<EndorseViewData>() {
                @Override
                public void onResponse(@NotNull Call<EndorseViewData> call,
                                       @NotNull Response<EndorseViewData> response) {
                    if (response.isSuccessful()) {
                        EndorseViewData commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                        endorseViewData = commonResponse;
                        if (commonResponse != null)
                            setEndorseViewData(endorseViewData);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<EndorseViewData> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCancelledList(final Context mContext, String userId, String userType) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        String url = "/cancellation/view-requests";

        try {
            Call<MyCancelled> call = apiService.getCancelledList(userId, userType, url, source);
            call.enqueue(new Callback<MyCancelled>() {
                @Override
                public void onResponse(@NotNull Call<MyCancelled> call,
                                       @NotNull Response<MyCancelled> response) {
                    if (response.isSuccessful()) {
                        MyCancelled commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<MyCancelled> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCancellationList(final Context mContext, String userId, String userType,
                                    String searchData) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<CancellationList> call = apiService.getCancellationList(userId, userType,
                    searchData, source);
            call.enqueue(new Callback<CancellationList>() {
                @Override
                public void onResponse(@NotNull Call<CancellationList> call,
                                       @NotNull Response<CancellationList> response) {
                    if (response.isSuccessful()) {
                        CancellationList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CancellationList> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getClaimRequest(final Context mContext, String userId, String userType) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        String url = "claim-assistance/all-requests";
        String page = "Reports";

        try {
            Call<MyClaim> call = apiService.getClaimRequest(userId, userType, url, page, source);
            call.enqueue(new Callback<MyClaim>() {
                @Override
                public void onResponse(@NotNull Call<MyClaim> call,
                                       @NotNull Response<MyClaim> response) {
                    if (response.isSuccessful()) {
                        MyClaim commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<MyClaim> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllClaim(final Context mContext, String userId, String userType,
                            String searchData) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<ClaimList> call = apiService.getAllClaim(userId, userType, searchData,
                    "Claim", source);
            call.enqueue(new Callback<ClaimList>() {
                @Override
                public void onResponse(@NotNull Call<ClaimList> call,
                                       @NotNull Response<ClaimList> response) {
                    if (response.isSuccessful()) {
                        ClaimList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ClaimList> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllTicket(final Context mContext, String userId, String userType) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<MyTicket> call = apiService.getAllTicket(userId, "/ticket/all-tickets-user",
                    "app", userType);
            call.enqueue(new Callback<MyTicket>() {
                @Override
                public void onResponse(@NotNull Call<MyTicket> call,
                                       @NotNull Response<MyTicket> response) {
                    if (response.isSuccessful()) {
                        MyTicket commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<MyTicket> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllEndorsement(final Context mContext, String userId, String userType,
                                  String searchData) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<EndorsementList> call = apiService.getAllEndorsement(userId, userType, searchData,
                    "/endosment/create-requests", source);
            call.enqueue(new Callback<EndorsementList>() {
                @Override
                public void onResponse(@NotNull Call<EndorsementList> call,
                                       @NotNull Response<EndorsementList> response) {
                    if (response.isSuccessful()) {
                        EndorsementList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<EndorsementList> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getOfflineQuoteDetail(final Context mContext, String userId, String userType,
                                      String qId) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<OfflineView> call = apiService.getOfflineQuoteDetail(userId, userType, qId,
                    "view-Details-quote");
            call.enqueue(new Callback<OfflineView>() {
                @Override
                public void onResponse(@NotNull Call<OfflineView> call,
                                       @NotNull Response<OfflineView> response) {
                    if (response.isSuccessful()) {
                        OfflineView commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<OfflineView> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCrmQuotes(final Context mContext, String userId, String userType) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<CrmQuotes> call = apiService.getCrmQuotes(userId, userType);
            call.enqueue(new Callback<CrmQuotes>() {
                @Override
                public void onResponse(@NotNull Call<CrmQuotes> call,
                                       @NotNull Response<CrmQuotes> response) {
                    if (response.isSuccessful()) {
                        CrmQuotes commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CrmQuotes> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAddonList(final Context mContext, String vehicleType) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<AddonList> call = apiService.getAddonList(vehicleType);
            call.enqueue(new Callback<AddonList>() {
                @Override
                public void onResponse(@NotNull Call<AddonList> call,
                                       @NotNull Response<AddonList> response) {
                    if (response.isSuccessful()) {
                        AddonList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AddonList> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getOfflineQuote(final Context mContext, String userId, String userType) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<OfflineList> call = apiService.getOfflineQuote(userId, userType,
                    "/offline-quote/view-requests", "");
            call.enqueue(new Callback<OfflineList>() {
                @Override
                public void onResponse(@NotNull Call<OfflineList> call,
                                       @NotNull Response<OfflineList> response) {
                    if (response.isSuccessful()) {
                        OfflineList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<OfflineList> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCancellationSingle(final Context mContext, String id) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<CancellationData> call = apiService.getCancellationSingle(id, source);
            call.enqueue(new Callback<CancellationData>() {
                @Override
                public void onResponse(@NotNull Call<CancellationData> call,
                                       @NotNull Response<CancellationData> response) {
                    if (response.isSuccessful()) {
                        CancellationData commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CancellationData> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCreatedEndorsement(final Context mContext, String userId, String userType) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<EndorseCreatedList> call = apiService.getCreatedEndorsement(userId, userType,
                    "/endosment/view-requests", source);
            call.enqueue(new Callback<EndorseCreatedList>() {
                @Override
                public void onResponse(@NotNull Call<EndorseCreatedList> call,
                                       @NotNull Response<EndorseCreatedList> response) {
                    if (response.isSuccessful()) {
                        EndorseCreatedList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<EndorseCreatedList> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getChildPosOtp(final Context mContext, String userId, String userType,
                               String mobileData) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<ChildPosOtp> call = apiService.getChildPosOtp(userId, userType,
                    mobileData, source);
            call.enqueue(new Callback<ChildPosOtp>() {
                @Override
                public void onResponse(@NotNull Call<ChildPosOtp> call,
                                       @NotNull Response<ChildPosOtp> response) {
                    if (response.isSuccessful()) {
                        ChildPosOtp commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ChildPosOtp> call, @NotNull Throwable t) {
//                    onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void newChildPos(final Context mContext, String userId, String userType, String mobile,
                            String otp) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<ResponseBool> call = apiService.newChildPos(userId, userType,
                    mobile, otp, source);
            call.enqueue(new Callback<ResponseBool>() {
                @Override
                public void onResponse(@NotNull Call<ResponseBool> call,
                                       @NotNull Response<ResponseBool> response) {
                    if (response.isSuccessful()) {
                        ResponseBool commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ResponseBool> call, @NotNull Throwable t) {
//                    onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMobilePosData(final Context mContext, String userId, String userType) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<MobilePosList> call = apiService.getMobilePosData(userId, userType,
                    "", source);
            call.enqueue(new Callback<MobilePosList>() {
                @Override
                public void onResponse(@NotNull Call<MobilePosList> call,
                                       @NotNull Response<MobilePosList> response) {
                    if (response.isSuccessful()) {
                        MobilePosList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<MobilePosList> call, @NotNull Throwable t) {
//                    onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void raiseEndorsement(final Context mContext, String userId, String userType, String srId,
                                 String srNo, JSONObject formJson, String oldFormData,
                                 String nameUpdateReason, String ncbUpdateReason, String rcFront,
                                 String rcBack, String letterDoc, String otherDoc) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        File fileRcFront, fileRcBack, fileLetter, fileOther;
        MultipartBody.Part documentRcFront, documentRcBack, documentLetter, documentOther;
        documentRcFront = documentRcBack = documentLetter = documentOther = null;

        if (rcFront != null) {
            try {
                fileRcFront = new File(rcFront);
                if (fileRcFront.exists()) {
                    documentRcFront = MultipartBody.Part.createFormData("RcFrontDoc",
                            fileRcFront.getName(),
                            RequestBody.create(fileRcFront, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));
            documentRcFront = MultipartBody.Part.createFormData("RcFrontDoc", "",
                    attachmentEmpty);
        }

        if (rcBack != null) {
            try {
                fileRcBack = new File(rcBack);
                if (fileRcBack.exists()) {
                    documentRcBack = MultipartBody.Part.createFormData("RcBackDoc",
                            fileRcBack.getName(),
                            RequestBody.create(fileRcBack, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));
            documentRcBack = MultipartBody.Part.createFormData("RcBackDoc", "",
                    attachmentEmpty);
        }

        if (letterDoc != null) {
            try {
                fileLetter = new File(letterDoc);
                if (fileLetter.exists()) {
                    documentLetter = MultipartBody.Part.createFormData("RequestLetterDoc",
                            fileLetter.getName(),
                            RequestBody.create(fileLetter, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));
            documentLetter = MultipartBody.Part.createFormData("RequestLetterDoc", "",
                    attachmentEmpty);
        }

        if (otherDoc != null) {
            try {
                fileOther = new File(otherDoc);
                if (fileOther.exists()) {
                    documentOther = MultipartBody.Part.createFormData("SupportingDoc",
                            fileOther.getName(),
                            RequestBody.create(fileOther, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));
            documentOther = MultipartBody.Part.createFormData("SupportingDoc", "",
                    attachmentEmpty);
        }

        RequestBody requestUserId = RequestBody.create(userId,
                MediaType.parse("multipart/form-data"));
        RequestBody requestUserType = RequestBody.create(userType,
                MediaType.parse("multipart/form-data"));
        RequestBody requestSrId = RequestBody.create(srId,
                MediaType.parse("multipart/form-data"));
        RequestBody requestSrNo = RequestBody.create(srNo,
                MediaType.parse("multipart/form-data"));
        RequestBody requestJson = RequestBody.create(String.valueOf(formJson),
                MediaType.parse("multipart/form-data"));
        RequestBody requestOld = RequestBody.create(oldFormData,
                MediaType.parse("multipart/form-data"));
        RequestBody requestNameUpdate = RequestBody.create(nameUpdateReason,
                MediaType.parse("multipart/form-data"));
        RequestBody requestUpdateReason = RequestBody.create(ncbUpdateReason,
                MediaType.parse("multipart/form-data"));
        RequestBody requestSource = RequestBody.create(source,
                MediaType.parse("multipart/form-data"));

        try {
            Call<BasicResponse> call = apiService.raiseEndorsement(requestUserId, requestUserType,
                    requestSrId, requestSrNo, requestJson, requestOld, requestNameUpdate,
                    requestUpdateReason, requestSource, documentRcFront, documentRcBack,
                    documentLetter, documentOther);
            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(@NotNull Call<BasicResponse> call,
                                       @NotNull Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        BasicResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
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

    public void raiseCancellation(final Context mContext, String userId, String userType, String srId,
                                  String srNo, String remark, String policyPdf,
                                  String letter, String cancelCheque) {

        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        File filePolicy, fileLetter, fileCancel;
        MultipartBody.Part documentPolicy, documentLetter, documentCancelCheque;
        documentPolicy = documentLetter = documentCancelCheque = null;

        if (policyPdf != null) {
            try {
                filePolicy = new File(policyPdf);
                if (filePolicy.exists()) {
                    documentPolicy = MultipartBody.Part.createFormData("alternatePolicy",
                            filePolicy.getName(),
                            RequestBody.create(filePolicy, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));
            documentPolicy = MultipartBody.Part.createFormData("alternatePolicy", "",
                    attachmentEmpty);
        }

        if (letter != null) {
            try {
                fileLetter = new File(letter);
                if (fileLetter.exists()) {
                    documentLetter = MultipartBody.Part.createFormData("customerLetter",
                            fileLetter.getName(),
                            RequestBody.create(fileLetter, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));
            documentLetter = MultipartBody.Part.createFormData("customerLetter", "",
                    attachmentEmpty);
        }

        if (cancelCheque != null) {
            try {
                fileCancel = new File(cancelCheque);
                if (fileCancel.exists()) {
                    documentCancelCheque = MultipartBody.Part.createFormData("cancelCheque",
                            fileCancel.getName(),
                            RequestBody.create(fileCancel, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));
            documentCancelCheque = MultipartBody.Part.createFormData("cancelCheque", "",
                    attachmentEmpty);
        }

        RequestBody requestUserId = RequestBody.create(userId,
                MediaType.parse("multipart/form-data"));
        RequestBody requestUserType = RequestBody.create(userType,
                MediaType.parse("multipart/form-data"));
        RequestBody requestSrId = RequestBody.create(srId,
                MediaType.parse("multipart/form-data"));
        RequestBody requestSrNo = RequestBody.create(srNo,
                MediaType.parse("multipart/form-data"));
        RequestBody requestRemark = RequestBody.create(remark,
                MediaType.parse("multipart/form-data"));
        RequestBody requestSource = RequestBody.create(source,
                MediaType.parse("multipart/form-data"));

        try {
            Call<BasicResponse> call = apiService.raiseCancellation(requestUserId, requestUserType,
                    requestSrId, requestSrNo, requestRemark, requestSource, documentPolicy,
                    documentLetter, documentCancelCheque);
            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(@NotNull Call<BasicResponse> call,
                                       @NotNull Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        BasicResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
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

    public void raiseClaimRequest(final Context mContext, String userId, String userType, String qId,
                                  String company, String intimateInsurer, String intimatedBy,
                                  String intimatedTime, String intimatedDate, String intimationNo,
                                  String intimatorName, String intimatorContact,
                                  String intimatorAltContact, String intimatorWhatsApp,
                                  String intimatorMail, String intDelayReason, String lossType,
                                  String causeOfLossType, String dateLose, String timeLose,
                                  String estimatedAmount, String accidentGarageName,
                                  String accidentLandMark, String garagePincode, String firStatus,
                                  String firRemarks, String tpLossStatus, String tpLossRemarks,
                                  String driverName, String driverContactNo, String driverDLNo,
                                  String spotSurveyStatus, String spotSurveyDate,
                                  String spotSurveyTime, String spotSurveyorName,
                                  String spotSurveyorMobile, String spotSurveyorLocation,
                                  String spotPincode, String surveyStatus, String surveyDate,
                                  String surveyTime, String surveyorName, String surveyorMobile,
                                  String surveyMail, String surveyType, String regNo,
                                  String customerPhone, String customerEmail) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<CreateClaim> call = apiService.raiseClaimRequest(source, userId, userType,
                    qId, company, intimateInsurer, intimatedBy, intimatedTime, intimatedDate,
                    intimationNo, intimatorName, intimatorContact, intimatorAltContact,
                    intimatorWhatsApp, intimatorMail, intDelayReason, lossType, causeOfLossType,
                    dateLose, timeLose, estimatedAmount, accidentGarageName, accidentLandMark,
                    garagePincode, firStatus, firRemarks, tpLossStatus, tpLossRemarks,
                    driverName, driverContactNo, driverDLNo, spotSurveyStatus, spotSurveyDate,
                    spotSurveyTime, spotSurveyorName, spotSurveyorMobile, spotSurveyorLocation,
                    spotPincode, surveyStatus, surveyDate, surveyTime, surveyorName,
                    surveyorMobile, surveyMail, surveyType, regNo, customerPhone, customerEmail);
            call.enqueue(new Callback<CreateClaim>() {
                @Override
                public void onResponse(@NotNull Call<CreateClaim> call,
                                       @NotNull Response<CreateClaim> response) {
                    if (response.isSuccessful()) {
                        CreateClaim commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<CreateClaim> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getClaimChat(final Context mContext, String userId, String chatType, String refId) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();
        try {
            Call<ChatList> call = apiService.getClaimChat(userId, chatType, refId);
            call.enqueue(new Callback<ChatList>() {
                @Override
                public void onResponse(@NotNull Call<ChatList> call,
                                       @NotNull Response<ChatList> response) {
                    if (response.isSuccessful()) {
                        ChatList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ChatList> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTicketTypes(final Context mContext) {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();
        try {
            Call<TicketList> call = apiService.getTicketTypes();
            call.enqueue(new Callback<TicketList>() {
                @Override
                public void onResponse(@NotNull Call<TicketList> call,
                                       @NotNull Response<TicketList> response) {
                    if (response.isSuccessful()) {
                        TicketList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<TicketList> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getNewInsurer(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<InsurerNewList> call = apiService.getNewInsurerList();
            call.enqueue(new Callback<InsurerNewList>() {
                @Override
                public void onResponse(@NonNull Call<InsurerNewList> call,
                                       @NonNull Response<InsurerNewList> response) {
                    if (response.isSuccessful()) {
                        InsurerNewList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<InsurerNewList> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void endorsementMake(final Context cxt, String source, String vehicleType) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<VehicleData> call = apiService.endorsementMake(source, vehicleType);
            call.enqueue(new Callback<VehicleData>() {
                @Override
                public void onResponse(@NonNull Call<VehicleData> call,
                                       @NonNull Response<VehicleData> response) {
                    if (response.isSuccessful()) {
                        VehicleData commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<VehicleData> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void endorsementModel(final Context cxt, String source, String vehicleType, String make) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<VehicleData> call = apiService.endorsementModel(source, vehicleType, make);
            call.enqueue(new Callback<VehicleData>() {
                @Override
                public void onResponse(@NonNull Call<VehicleData> call,
                                       @NonNull Response<VehicleData> response) {
                    if (response.isSuccessful()) {
                        VehicleData commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<VehicleData> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void endorsementVariant(final Context cxt, String source, String vehicleType, String model) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<VehicleData> call = apiService.endorsementVariant(source, vehicleType, model);
            call.enqueue(new Callback<VehicleData>() {
                @Override
                public void onResponse(@NonNull Call<VehicleData> call,
                                       @NonNull Response<VehicleData> response) {
                    if (response.isSuccessful()) {
                        VehicleData commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<VehicleData> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void surveyMake(final Context cxt, String product) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<VehicleData> call = apiService.surveyMake(product);
            call.enqueue(new Callback<VehicleData>() {
                @Override
                public void onResponse(@NonNull Call<VehicleData> call,
                                       @NonNull Response<VehicleData> response) {
                    if (response.isSuccessful()) {
                        VehicleData commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<VehicleData> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void surveyModel(final Context cxt, String make) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<VehicleData> call = apiService.surveyModel(make);
            call.enqueue(new Callback<VehicleData>() {
                @Override
                public void onResponse(@NonNull Call<VehicleData> call,
                                       @NonNull Response<VehicleData> response) {
                    if (response.isSuccessful()) {
                        VehicleData commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<VehicleData> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void surveyVariant(final Context cxt, String model) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<VehicleData> call = apiService.surveyVariant(model);
            call.enqueue(new Callback<VehicleData>() {
                @Override
                public void onResponse(@NonNull Call<VehicleData> call,
                                       @NonNull Response<VehicleData> response) {
                    if (response.isSuccessful()) {
                        VehicleData commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<VehicleData> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void endorsementFuel(final Context cxt, String source, String vehicleType) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<VehicleData> call = apiService.endorsementMake(source, vehicleType);
            call.enqueue(new Callback<VehicleData>() {
                @Override
                public void onResponse(@NonNull Call<VehicleData> call,
                                       @NonNull Response<VehicleData> response) {
                    if (response.isSuccessful()) {
                        VehicleData commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<VehicleData> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getProducts(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<VehicleData> call = apiService.getProducts();
            call.enqueue(new Callback<VehicleData>() {
                @Override
                public void onResponse(@NonNull Call<VehicleData> call,
                                       @NonNull Response<VehicleData> response) {
                    if (response.isSuccessful()) {
                        VehicleData commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<VehicleData> call, @NonNull Throwable t) {
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
            Call<VehicleData> call = apiService.getInsurer();
            call.enqueue(new Callback<VehicleData>() {
                @Override
                public void onResponse(@NonNull Call<VehicleData> call,
                                       @NonNull Response<VehicleData> response) {
                    if (response.isSuccessful()) {
                        VehicleData commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<VehicleData> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCrmInspection(final Context cxt, String userId, String userTye) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();
        String url = "/survey/view-requests";
        try {
            Call<SurveyList> call = apiService.getCrmInspection(userId, userTye, url, source);
            call.enqueue(new Callback<SurveyList>() {
                @Override
                public void onResponse(@NonNull Call<SurveyList> call,
                                       @NonNull Response<SurveyList> response) {
                    if (response.isSuccessful()) {
                        SurveyList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SurveyList> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getSurveyEmployee(final Context cxt, String userId, String userTye) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();
        try {
            Call<VehicleData> call = apiService.getSurveyEmployee(userId, userTye, "5",
                    "Is_View", source);
            call.enqueue(new Callback<VehicleData>() {
                @Override
                public void onResponse(@NonNull Call<VehicleData> call,
                                       @NonNull Response<VehicleData> response) {
                    if (response.isSuccessful()) {
                        VehicleData commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<VehicleData> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getSurveyPartner(final Context cxt, String empId, String empName) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();
        try {
            Call<VehicleData> call = apiService.getSurveyPartner(empId, empName, source);
            call.enqueue(new Callback<VehicleData>() {
                @Override
                public void onResponse(@NonNull Call<VehicleData> call,
                                       @NonNull Response<VehicleData> response) {
                    if (response.isSuccessful()) {
                        VehicleData commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<VehicleData> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void singleSurveyData(final Context cxt, String surveyId) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();
        try {
            Call<SingleSurvey> call = apiService.singleSurveyData(surveyId, source);
            call.enqueue(new Callback<SingleSurvey>() {
                @Override
                public void onResponse(@NonNull Call<SingleSurvey> call,
                                       @NonNull Response<SingleSurvey> response) {
                    if (response.isSuccessful()) {
                        SingleSurvey commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SingleSurvey> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void raiseSurvey(final Context mContext, String userId, String userType, String quoteId,
                            String endorsementId, String customerName, String customerEmail,
                            String customerEmailAlt, String customerMobile, String cMobileAlt,
                            String customerAddress, String vehicleLocation, String pincode,
                            String city, String state, String employeeId, String employeeName,
                            String employeeMobile, String posId, String posName, String posMobile,
                            String product, String inspectionMode, String registrationNo, String rto,
                            String make, String model, String variant, String insurerName,
                            String engineNo, String chassisNo, String remark, String rcFront,
                            String rcBack, String quoteDoc) {

        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        File fileRcFront, fileRcBack, fileQuote;
        MultipartBody.Part documentRcFront, documentRcBack, documentQuoteDoc;
        documentRcFront = documentRcBack = documentQuoteDoc = null;

        if (rcFront != null) {
            try {
                fileRcFront = new File(rcFront);
                if (fileRcFront.exists()) {
                    documentRcFront = MultipartBody.Part.createFormData("rcFront",
                            fileRcFront.getName(),
                            RequestBody.create(fileRcFront, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));
            documentRcFront = MultipartBody.Part.createFormData("rcFront", "",
                    attachmentEmpty);
        }

        if (rcBack != null) {
            try {
                fileRcBack = new File(rcBack);
                if (fileRcBack.exists()) {
                    documentRcBack = MultipartBody.Part.createFormData("rcBack",
                            fileRcBack.getName(),
                            RequestBody.create(fileRcBack, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));
            documentRcBack = MultipartBody.Part.createFormData("rcBack", "",
                    attachmentEmpty);
        }

        if (quoteDoc != null) {
            try {
                fileQuote = new File(quoteDoc);
                if (fileQuote.exists()) {
                    documentQuoteDoc = MultipartBody.Part.createFormData("quotationDoc",
                            fileQuote.getName(),
                            RequestBody.create(fileQuote, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));
            documentQuoteDoc = MultipartBody.Part.createFormData("quotationDoc", "",
                    attachmentEmpty);
        }
        RequestBody requestUserId = RequestBody.create(userId,
                MediaType.parse("multipart/form-data"));
        RequestBody requestUserType = RequestBody.create(userType,
                MediaType.parse("multipart/form-data"));
        RequestBody requestQuoteId = RequestBody.create(quoteId,
                MediaType.parse("multipart/form-data"));
        RequestBody requestEndorsementId = RequestBody.create(endorsementId,
                MediaType.parse("multipart/form-data"));
        RequestBody requestCsName = RequestBody.create(customerName,
                MediaType.parse("multipart/form-data"));
        RequestBody requestCsEmail = RequestBody.create(customerEmail,
                MediaType.parse("multipart/form-data"));
        RequestBody requestCsAltEmail = RequestBody.create(customerEmailAlt,
                MediaType.parse("multipart/form-data"));
        RequestBody requestCsPhone = RequestBody.create(customerMobile,
                MediaType.parse("multipart/form-data"));
        RequestBody requestCsAltPhone = RequestBody.create(cMobileAlt,
                MediaType.parse("multipart/form-data"));
        RequestBody requestAddress = RequestBody.create(customerAddress,
                MediaType.parse("multipart/form-data"));
        RequestBody requestLocation = RequestBody.create(vehicleLocation,
                MediaType.parse("multipart/form-data"));
        RequestBody requestPincode = RequestBody.create(pincode,
                MediaType.parse("multipart/form-data"));
        RequestBody requestState = RequestBody.create(state,
                MediaType.parse("multipart/form-data"));
        RequestBody requestCity = RequestBody.create(city,
                MediaType.parse("multipart/form-data"));
        RequestBody requestEmpId = RequestBody.create(employeeId,
                MediaType.parse("multipart/form-data"));
        RequestBody requestEmpName = RequestBody.create(employeeName,
                MediaType.parse("multipart/form-data"));
        RequestBody requestEmpMobile = RequestBody.create(employeeMobile,
                MediaType.parse("multipart/form-data"));
        RequestBody requestPosId = RequestBody.create(posId,
                MediaType.parse("multipart/form-data"));
        RequestBody requestPosName = RequestBody.create(posName,
                MediaType.parse("multipart/form-data"));
        RequestBody requestPosMobile = RequestBody.create(posMobile,
                MediaType.parse("multipart/form-data"));
        RequestBody requestProduct = RequestBody.create(product,
                MediaType.parse("multipart/form-data"));
        RequestBody requestInsMode = RequestBody.create(inspectionMode,
                MediaType.parse("multipart/form-data"));
        RequestBody requestRegNo = RequestBody.create(registrationNo,
                MediaType.parse("multipart/form-data"));
        RequestBody requestRto = RequestBody.create(rto,
                MediaType.parse("multipart/form-data"));
        RequestBody requestMake = RequestBody.create(make,
                MediaType.parse("multipart/form-data"));
        RequestBody requestModel = RequestBody.create(model,
                MediaType.parse("multipart/form-data"));
        RequestBody requestVariant = RequestBody.create(variant,
                MediaType.parse("multipart/form-data"));
        RequestBody requestInsurerName = RequestBody.create(insurerName,
                MediaType.parse("multipart/form-data"));
        RequestBody requestEngineNo = RequestBody.create(engineNo,
                MediaType.parse("multipart/form-data"));
        RequestBody requestChassisNo = RequestBody.create(chassisNo,
                MediaType.parse("multipart/form-data"));
        RequestBody requestRemark = RequestBody.create(remark,
                MediaType.parse("multipart/form-data"));
        RequestBody requestSource = RequestBody.create(source,
                MediaType.parse("multipart/form-data"));

        try {
            Call<BasicResponse> call = apiService.raiseInspection(requestUserId, requestUserType,
                    requestQuoteId, requestEndorsementId, requestCsName, requestCsEmail,
                    requestCsAltEmail, requestCsPhone, requestCsAltPhone, requestAddress,
                    requestLocation, requestPincode, requestState, requestCity, requestEmpId,
                    requestEmpName, requestEmpMobile, requestPosId, requestPosName, requestPosMobile,
                    requestProduct, requestInsMode, requestRegNo, requestRto, requestMake, requestModel,
                    requestVariant, requestInsurerName, requestEngineNo, requestChassisNo,
                    requestRemark, requestSource, documentRcFront, documentRcBack, documentQuoteDoc);
            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(@NotNull Call<BasicResponse> call,
                                       @NotNull Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        BasicResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
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

    public void requestOffLineQuote(final Context mContext, String quotationId, String offline,
                                    String invoiceImg, String rcFront, String rcBack, String remark,
                                    String policyPdf, String otherDocument) {

        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        File filePolicy, fileInvoice, fileRcFront, fileRcBack, fileOther;
        MultipartBody.Part documentPolicy, documentRcFront, documentRcBack, documentInvoice,
                documentOther;
        documentPolicy = documentRcFront = documentRcBack = documentInvoice = documentOther = null;

        if (policyPdf != null) {
            try {
                filePolicy = new File(policyPdf);
                if (filePolicy.exists()) {
                    documentPolicy = MultipartBody.Part.createFormData("PreviousPolicyPdf",
                            filePolicy.getName(),
                            RequestBody.create(filePolicy, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));
            documentPolicy = MultipartBody.Part.createFormData("PreviousPolicyPdf", "",
                    attachmentEmpty);
        }

        if (invoiceImg != null) {
            try {
                fileInvoice = new File(invoiceImg);
                if (fileInvoice.exists()) {
                    documentInvoice = MultipartBody.Part.createFormData("InvoiceImage",
                            fileInvoice.getName(),
                            RequestBody.create(fileInvoice, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));
            documentInvoice = MultipartBody.Part.createFormData("InvoiceImage", "",
                    attachmentEmpty);
        }

        if (rcFront != null) {
            try {
                fileRcFront = new File(rcFront);
                if (fileRcFront.exists()) {
                    documentRcFront = MultipartBody.Part.createFormData("RC_Front",
                            fileRcFront.getName(),
                            RequestBody.create(fileRcFront, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));
            documentRcFront = MultipartBody.Part.createFormData("RC_Front", "",
                    attachmentEmpty);
        }
        if (rcBack != null) {
            try {
                fileRcBack = new File(rcBack);
                if (fileRcBack.exists()) {
                    documentRcBack = MultipartBody.Part.createFormData("RcBack",
                            fileRcBack.getName(),
                            RequestBody.create(fileRcBack, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));
            documentRcBack = MultipartBody.Part.createFormData("RcBack", "",
                    attachmentEmpty);
        }

        if (otherDocument != null) {
            try {
                fileOther = new File(otherDocument);
                if (fileOther.exists()) {
                    documentOther = MultipartBody.Part.createFormData("OtherDocument",
                            fileOther.getName(),
                            RequestBody.create(fileOther, MediaType.parse("multipart/form-data")));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            RequestBody attachmentEmpty = RequestBody.create("",
                    MediaType.parse("text/plain"));
            documentOther = MultipartBody.Part.createFormData("OtherDocument", "",
                    attachmentEmpty);
        }

        RequestBody reqQuoteId = RequestBody.create(quotationId,
                MediaType.parse("multipart/form-data"));
        RequestBody requestOffline = RequestBody.create(offline,
                MediaType.parse("multipart/form-data"));
        RequestBody requestRemark = RequestBody.create(remark,
                MediaType.parse("multipart/form-data"));
        RequestBody requestSource = RequestBody.create(source,
                MediaType.parse("multipart/form-data"));

        try {
            Call<BasicResponse> call = apiService.requestOffLineQuote(reqQuoteId, requestOffline,
                    requestRemark, requestSource, documentInvoice, documentRcFront, documentRcBack,
                    documentPolicy, documentOther);
            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(@NotNull Call<BasicResponse> call,
                                       @NotNull Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        BasicResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
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

    public void getDetailedPolicy(final Context cxt, String userId, String userTye, String type,
                                  String url) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();
        try {
            Call<DetailedPolicyList> call = apiService.getDetailedPolicy(userId, userTye, url, type,
                    source);
            call.enqueue(new Callback<DetailedPolicyList>() {
                @Override
                public void onResponse(@NonNull Call<DetailedPolicyList> call,
                                       @NonNull Response<DetailedPolicyList> response) {
                    if (response.isSuccessful()) {
                        DetailedPolicyList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<DetailedPolicyList> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCommonFilters(final Context cxt, String userId, String userTye) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();
        try {
            Call<CrmMaster> call = apiService.getCommonFilters(userId, userTye, "BMS",
                    source);
            call.enqueue(new Callback<CrmMaster>() {
                @Override
                public void onResponse(@NonNull Call<CrmMaster> call,
                                       @NonNull Response<CrmMaster> response) {
                    if (response.isSuccessful()) {
                        CrmMaster commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CrmMaster> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getStatementList(final Context cxt, String userId, String userTye, String userCode,
                                 String year, String fromMonth, String toMonth) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();
        try {
            Call<StatementList> call = apiService.getStatementList(userId, userTye, userCode,
                    "Statement", year, fromMonth, toMonth, source);
            call.enqueue(new Callback<StatementList>() {
                @Override
                public void onResponse(@NonNull Call<StatementList> call,
                                       @NonNull Response<StatementList> response) {
                    if (response.isSuccessful()) {
                        StatementList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<StatementList> call,
                                      @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getRenewalList(final Context cxt, String userId, String userType) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        String pageType = "PageType";
        try {
            Call<RenewalList> call = apiService.getRenewalList(userId, userType, pageType, source);
            call.enqueue(new Callback<RenewalList>() {
                @Override
                public void onResponse(@NonNull Call<RenewalList> call,
                                       @NonNull Response<RenewalList> response) {
                    if (response.isSuccessful()) {
                        RenewalList commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<RenewalList> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createRenewal(final Context cxt, String userId, String userType, String quoteId,
                              String regNo, String fileType) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<CreateRenewal> call = apiService.createRenewal(userId, userType, quoteId, regNo,
                    fileType, source);
            call.enqueue(new Callback<CreateRenewal>() {
                @Override
                public void onResponse(@NonNull Call<CreateRenewal> call,
                                       @NonNull Response<CreateRenewal> response) {
                    if (response.isSuccessful()) {
                        CreateRenewal commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CreateRenewal> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void quickRenewal(final Context cxt, String userId, String userType, String quoteId,
                             String regNo, String fileType) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<CreateRenewal> call = apiService.quickRenewal(userId, userType, quoteId, regNo,
                    fileType, source);
            call.enqueue(new Callback<CreateRenewal>() {
                @Override
                public void onResponse(@NonNull Call<CreateRenewal> call,
                                       @NonNull Response<CreateRenewal> response) {
                    if (response.isSuccessful()) {
                        CreateRenewal commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CreateRenewal> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void renewalAction(final Context cxt, String userId, String userType, String srTableId,
                              String actionStatus, String dates, String time, String remark) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<BasicBoolResponse> call = apiService.renewalAction(userId, userType, srTableId,
                    actionStatus, dates, time, remark, source);
            call.enqueue(new Callback<BasicBoolResponse>() {
                @Override
                public void onResponse(@NonNull Call<BasicBoolResponse> call,
                                       @NonNull Response<BasicBoolResponse> response) {
                    if (response.isSuccessful()) {
                        BasicBoolResponse commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<BasicBoolResponse> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPosters(final Context cxt) {

        ApiClient restClient = new ApiClient(cxt);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<SharePoster> call = apiService.getPosters(source);
            call.enqueue(new Callback<SharePoster>() {
                @Override
                public void onResponse(@NonNull Call<SharePoster> call,
                                       @NonNull Response<SharePoster> response) {
                    if (response.isSuccessful()) {
                        SharePoster commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) cxt).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SharePoster> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reviewPageTicket(final Context mContext, String userId, String usertype,
                                 String pageType, String policyType, String pageUrl, String quoteId,
                                 String remarks) {

        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();
        try {
            Call<RaiseTicket> call = apiService.reviewPageTicket(userId, usertype, pageType,
                    policyType, pageUrl, quoteId, remarks, source);
            call.enqueue(new Callback<RaiseTicket>() {
                @Override
                public void onResponse(@NonNull Call<RaiseTicket> call,
                                       @NonNull Response<RaiseTicket> response) {
                    if (response.isSuccessful()) {
                        RaiseTicket commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<RaiseTicket> call,
                                      @NonNull Throwable throwable) {
                    //onResponse(t);
                    throwable.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getShareUrl(final Context mContext, String userId, String userCode,
                            String userType) {

        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();
        try {
            Call<LeadUrl> call = apiService.getShareUrl(userId, userCode, userType);
            call.enqueue(new Callback<LeadUrl>() {
                @Override
                public void onResponse(@NonNull Call<LeadUrl> call,
                                       @NonNull Response<LeadUrl> response) {
                    if (response.isSuccessful()) {
                        LeadUrl commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                        if (commonResponse != null) {
                            if (!TextUtils.isEmpty(commonResponse.getData())) {
                                AppUtils.LEAD_URL = commonResponse.getData();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LeadUrl> call,
                                      @NonNull Throwable throwable) {
                    //onResponse(t);
                    throwable.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLoginOtp(final Context mContext, String userId) {

        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();
        String buttonValue = "Send Otp";
        try {
            Call<SendOtp> call = apiService.getLoginOtp(userId, buttonValue, "agent", source);
            call.enqueue(new Callback<SendOtp>() {
                @Override
                public void onResponse(@NonNull Call<SendOtp> call,
                                       @NonNull Response<SendOtp> response) {
                    if (response.isSuccessful()) {
                        SendOtp commonResponse = response.body();
                        ((onRequestCompleteCallBackListener) mContext).
                                onResponse(commonResponse);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SendOtp> call,
                                      @NonNull Throwable throwable) {
                    //onResponse(t);
                    throwable.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
