package com.dmw.noble.network;

import com.dmw.noble.activity_pos.CorporateList;
import com.dmw.noble.model.Account;
import com.dmw.noble.model.AddonCover;
import com.dmw.noble.model.Bank;
import com.dmw.noble.model.BasicResponse;
import com.dmw.noble.model.ChangeNo;
import com.dmw.noble.model.CityList;
import com.dmw.noble.model.CommonResponse;
import com.dmw.noble.model.DocWallet;
import com.dmw.noble.model.DocsList;
import com.dmw.noble.model.FetchQuote;
import com.dmw.noble.model.Filter;
import com.dmw.noble.model.FilterCar;
import com.dmw.noble.model.Fuel;
import com.dmw.noble.model.InsurerList;
import com.dmw.noble.model.LeadUrl;
import com.dmw.noble.model.Login;
import com.dmw.noble.model.ManufactureList;
import com.dmw.noble.model.ModelList;
import com.dmw.noble.model.MotorKyc;
import com.dmw.noble.model.NewPremiumQuote;
import com.dmw.noble.model.Nominee;
import com.dmw.noble.model.NonMotorList;
import com.dmw.noble.model.OtherInformation;
import com.dmw.noble.model.Otp;
import com.dmw.noble.model.Owner;
import com.dmw.noble.model.PCVGCVList;
import com.dmw.noble.model.PaymentTrack;
import com.dmw.noble.model.PinList;
import com.dmw.noble.model.PremiumQuote;
import com.dmw.noble.model.ProposalCompany;
import com.dmw.noble.model.ProposalPincode;
import com.dmw.noble.model.RTOList;
import com.dmw.noble.model.Review;
import com.dmw.noble.model.RtoLocation;
import com.dmw.noble.model.SbiMisd;
import com.dmw.noble.model.SendOtp;
import com.dmw.noble.model.Session;
import com.dmw.noble.model.SharePoster;
import com.dmw.noble.model.SignUp;
import com.dmw.noble.model.StateList;
import com.dmw.noble.model.TempResponse;
import com.dmw.noble.model.VahanData;
import com.dmw.noble.model.VariantList;
import com.dmw.noble.model.VehicleOther;
import com.dmw.noble.model.VehicleQuote;
import com.dmw.noble.model.blog.BlogList;
import com.dmw.noble.model.fetch_detail.GetNominee;
import com.dmw.noble.model.fetch_detail.GetOwner;
import com.dmw.noble.model.fetch_detail.GetVehicle;
import com.dmw.noble.model.master.CityMaster;
import com.dmw.noble.model.master.FinanceStatus;
import com.dmw.noble.model.master.GenderStatus;
import com.dmw.noble.model.master.Marital;
import com.dmw.noble.model.master.NomineeStatus;
import com.dmw.noble.model.master.OccupationStatus;
import com.dmw.noble.model.master.PincodeMaster;
import com.dmw.noble.model.master.SalutationStatus;
import com.dmw.noble.model.master.StateMaster;
import com.dmw.noble.model_crm.AddonList;
import com.dmw.noble.model_crm.BasicBool;
import com.dmw.noble.model_crm.BasicBoolResponse;
import com.dmw.noble.model_crm.CancellationData;
import com.dmw.noble.model_crm.CancellationList;
import com.dmw.noble.model_crm.ChatList;
import com.dmw.noble.model_crm.ChildPosOtp;
import com.dmw.noble.model_crm.ClaimList;
import com.dmw.noble.model_crm.ClaimPincode;
import com.dmw.noble.model_crm.ClaimView;
import com.dmw.noble.model_crm.CreateClaim;
import com.dmw.noble.model_crm.CreateRenewal;
import com.dmw.noble.model_crm.CrmQuotes;
import com.dmw.noble.model_crm.DetailedPolicyList;
import com.dmw.noble.model_crm.EndorseCreatedList;
import com.dmw.noble.model_crm.EndorseViewData;
import com.dmw.noble.model_crm.EndorsementList;
import com.dmw.noble.model_crm.InsurerNewList;
import com.dmw.noble.model_crm.MobilePosList;
import com.dmw.noble.model_crm.MyCancelled;
import com.dmw.noble.model_crm.MyClaim;
import com.dmw.noble.model_crm.MyTicket;
import com.dmw.noble.model_crm.NewMobilePos;
import com.dmw.noble.model_crm.OfflineList;
import com.dmw.noble.model_crm.OfflineView;
import com.dmw.noble.model_crm.RaiseTicket;
import com.dmw.noble.model_crm.RenewalList;
import com.dmw.noble.model_crm.ResponseBool;
import com.dmw.noble.model_crm.SingleSurvey;
import com.dmw.noble.model_crm.StatementList;
import com.dmw.noble.model_crm.SurveyList;
import com.dmw.noble.model_crm.TicketList;
import com.dmw.noble.model_crm.VehicleData;
import com.dmw.noble.model_crm.policy.CrmMaster;
import com.dmw.noble.model_health.HealthPolicy;
import com.dmw.noble.model_health.HealthQuoteList;
import com.dmw.noble.model_health.bajaj.BajajPincode;
import com.dmw.noble.model_health.compare.Compare;
import com.dmw.noble.model_health.v2.BrochureList;
import com.dmw.noble.model_health.v2.HealthApointee;
import com.dmw.noble.model_health.v2.HealthCity;
import com.dmw.noble.model_health.v2.HealthDocument;
import com.dmw.noble.model_health.v2.HealthGender;
import com.dmw.noble.model_health.v2.HealthMarital;
import com.dmw.noble.model_health.v2.HealthOccupation;
import com.dmw.noble.model_health.v2.HealthPaymentTrack;
import com.dmw.noble.model_health.v2.HealthPremium;
import com.dmw.noble.model_health.v2.HealthQualification;
import com.dmw.noble.model_health.v2.HealthQuote;
import com.dmw.noble.model_health.v2.HealthRelation;
import com.dmw.noble.model_health.v2.HealthSalutation;
import com.dmw.noble.model_health.v2.HealthState;
import com.dmw.noble.model_health.v2.HealthSumInsured;
import com.dmw.noble.model_health.v2.HospitalCityList;
import com.dmw.noble.model_health.v2.HospitalList;
import com.dmw.noble.model_health.v2.HospitalPincodeList;
import com.dmw.noble.model_health.v2.HospitalStateList;
import com.dmw.noble.model_health.v2.PlanCover;
import com.dmw.noble.model_life.AnualIncomeList;
import com.dmw.noble.model_life.AppointeeList;
import com.dmw.noble.model_life.BusinessList;
import com.dmw.noble.model_life.GenderList;
import com.dmw.noble.model_life.GetLifeQuoteId;
import com.dmw.noble.model_life.LifePinList;
import com.dmw.noble.model_life.LifePremium;
import com.dmw.noble.model_life.LifeProposal;
import com.dmw.noble.model_life.MaritalList;
import com.dmw.noble.model_life.NomineeList;
import com.dmw.noble.model_life.OccupationList;
import com.dmw.noble.model_life.QualificationList;
import com.dmw.noble.model_life.SalutationList;
import com.dmw.noble.model_life.SectorList;
import com.dmw.noble.model_life.SumList;
import com.dmw.noble.model_pos.AgentDetail;
import com.dmw.noble.model_pos.AgentDocuments;
import com.dmw.noble.model_pos.AgentPolicyList;
import com.dmw.noble.model_pos.Assign;
import com.dmw.noble.model_pos.CityListId;
import com.dmw.noble.model_pos.CustomerList;
import com.dmw.noble.model_pos.EarningList;
import com.dmw.noble.model_pos.Employee;
import com.dmw.noble.model_pos.Leads;
import com.dmw.noble.model_pos.NewAgent;
import com.dmw.noble.model_pos.NewLead;
import com.dmw.noble.model_pos.ProfileRequest;
import com.dmw.noble.model_pos.QuotationList;
import com.dmw.noble.model_pos.StateListId;
import com.dmw.noble.model_travel.CountryList;
import com.dmw.noble.model_travel.TravelPremium;
import com.dmw.noble.model_travel.TravelQuote;
import com.dmw.noble.training.model.ModuleMaster;
import com.dmw.noble.training.model.QuestionList;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {


    @FormUrlEncoded
    @POST("quotation_details/ApiData/login.php")
    Call<Login> postLogin(@Field("mobile") String mobile,
                          @Field("type") String type,
                          @Field("password") String password);

    @GET("noble/partners/login")
    Call<Login> nobleLogin(@Query("Encode") String encoded,
                           @Query("Action") String action,
                           @Query("Source") String source);

    @GET("noble/partners/login")
    Call<Login> nobleSubPosLogin(@Query("Encode") String encoded,
                                 @Query("Mobile") String mobile,
                                 @Query("Action") String action,
                                 @Query("Source") String source);

    @FormUrlEncoded
    @POST("quotation_details/ApiData/register_user.php")
    Call<SignUp> postSignUp(@Field("name") String name,
                            @Field("mobile") String mobile,
                            @Field("email") String email,
                            @Field("password") String password,
                            @Field("address") String address,
                            @Field("gcm_id") String deviceId
    );

    @FormUrlEncoded
    @POST("quotation_details/ApiData/pos_agreement_status")
    Call<BasicResponse> posAgreement(@Field("pos_id") String quotationId);

    @FormUrlEncoded
    @POST("api_quotes/storeFcmId")
    Call<BasicResponse> storeGcmId(@Field("token") String token,
                                   @Field("id") String userId,
                                   @Field("type") String userType,
                                   @Field("fcmId") String fcmId
    );

    @FormUrlEncoded
    @POST("api/fcm/FcmUpdateData")
    Call<BasicResponse> removeGcmId(@Field("user_id") String userId,
                                    @Field("user_type") String userType
    );

    @FormUrlEncoded
    @POST("api/login/LoginBlock")
    Call<TempResponse> userAuthentication(@Field("user_id") String userId,
                                          @Field("user_type") String userType
    );

    @FormUrlEncoded
    @POST("quotation_details/ApiData/pos_register.php")
    Call<NewAgent> createAgent(@Field("name1") String name,
                               @Field("name2") String middleName,
                               @Field("name3") String lastName,
                               @Field("email") String email,
                               @Field("mobile") String mobile,
                               @Field("password") String password,
                               @Field("mobile1") String alternateMobile,
                               @Field("gender") String gender,
                               @Field("dob") String dob,
                               @Field("qualification") String qualification,
                               @Field("pancard") String panCard,
                               @Field("aadharcard") String aadharCard,
                               @Field("address1") String address1,
                               @Field("address2") String address2,
                               @Field("address3") String address3,
                               @Field("state") String state,
                               @Field("city") String city,
                               @Field("pincode") String pinCode,
                               @Field("bank_name") String bankName,
                               @Field("branch_name") String branchName,
                               @Field("account_type") String accountType,
                               @Field("account_no") String accountNumber,
                               @Field("ifsc") String ifsc,
                               @Field("added_by") String addedBy,//Employee Agent SP
                               @Field("added_byid") String addedById,//primary key
                               @Field("reference") String reference //RM ID  reference_id
    );

    //Motor Quote Process
    @FormUrlEncoded
    @POST("api_quotes/insert_quote")
    Call<VehicleQuote> initQuoteId(@Field("ip_address") String ipAddress,
                                   @Field("email") String email,
                                   @Field("token") String token,
                                   @Field("version") String version,
                                   @Field("mobile") String mobile,
                                   @Field("policy_expiery") String policyExpiry,
                                   @Field("policy_type") String policyType,
                                   @Field("previous_insurer") String previousInsurer,
                                   @Field("reg_year") String regYear,
                                   @Field("variant") String variant,
                                   @Field("model") String model,
                                   @Field("make") String make,
                                   @Field("gadi_type") String vType,
                                   @Field("registration_no") String regNumber,
                                   @Field("name") String name,
                                   @Field("new_gadi") String newVehicle,
                                   @Field("type") String type,
                                   @Field("fuel_type") String fuelType,
                                   @Field("dont_know") String prePolicy,
                                   @Field("company") String pcvCompany,
                                   @Field("pcvType") String pcvType,
                                   @Field("type_id") String type_id

    );

    @FormUrlEncoded
    @POST("api_quotes/insertOtherInformation")
    Call<OtherInformation> updateOtherInformation(@Field("manufacture_date") String manufactureDate,
                                                  @Field("purchase_date") String purchaseDate,
                                                  @Field("registration_date") String registrationDate,
                                                  @Field("policy_expiery_date") String policyExpiryDate,
                                                  @Field("vehicle_owned_by") String vehicleOwnedBy,
                                                  @Field("owner_change") String ownerChange,
                                                  @Field("claim_expiring_policy") String claimExpiringPolicy,
                                                  @Field("ncb_old") String ncb,
                                                  @Field("zero_dept_expiring_policy") String zDep,
                                                  @Field("lastInsertId") String insertId,
                                                  @Field("sbiCode") String sbiCode,
                                                  @Field("token") String token
    );

    @FormUrlEncoded
    @POST("api_quotes/get_quote")
    Call<PremiumQuote> getPremium(@Field("token") String token,
                                  @Field("q_id") String quotationId,
                                  @Field("v_type") String vehicleType,
                                  @Field("company") String company,
                                  @Field("idv") String idv,
                                  @Field("query") String query
    );

    @FormUrlEncoded
    @POST("api_quotes/get_quote")
    Call<NewPremiumQuote> getPremiumCar(@Field("token") String token,
                                        @Field("q_id") String quotationId,
                                        @Field("v_type") String vehicleType,
                                        @Field("company") String company,
                                        @Field("idv") String idv,
                                        @Field("query") String query
    );

    @GET("api_master/categoryGcv")
    Call<PCVGCVList> getPCVGCV();

    @FormUrlEncoded
    @POST("api_quotes/filterupdateBike")
    Call<Filter> twFilter(@Field("pa_cover") String pACover,
                          @Field("zero_dept") String zeroDept,
                          @Field("road_side_assistance") String rsa,
                          @Field("pacoverfor_unnamed_person") String unnamed,
                          @Field("pacoverfor_unnamed_person_value") String unnamedValue,
                          @Field("legal_liability_paid_driver") String paidDriver,
                          @Field("cover_for") String coverFor,
                          @Field("tppd_restricted_to") int tppd,
                          @Field("vehicle_owned_by") String ownedBy,
                          @Field("idv") String idv,
                          @Field("owner_change") String ownerChange,
                          @Field("claim_expiring_policy") String claimExp,
                          @Field("ncb_old") String ncbOld,
                          @Field("cover") String cover,
                          @Field("tp_only") String tpOnly,
                          @Field("policy_expiery_date") String policyExpDate,
                          @Field("quotation_id") String quotationId,
                          @Field("tp_expire_policy_company") String tpPreInsurer,
                          @Field("tp_expire_policy_number") String tpPolicyNumber,
                          @Field("tp_policy_expire_date") String tpPolicyExpDate,
                          @Field("token") String token,
                          @Field("manufacture") String manufacture,
                          @Field("model") String model,
                          @Field("fuel_type") String fuel_type,
                          @Field("variant") String variant
    );

    @FormUrlEncoded
    @POST("api_quotes/filterupdateCar")
    Call<FilterCar> filterCar(@Field("quotation_id") String quotationId,
                              @Field("token") String token,
                              @Field("vehicle_owned_by") String ownedBy,
                              @Field("owner_change") String ownerChange,
                              @Field("claim_expiring_policy") String claimExp,
                              @Field("ncb_old") String ncbOld,
                              @Field("pa_cover") String pACover,
                              @Field("zero_dept") String zeroDept,
                              @Field("imt23") String imtCover,
                              @Field("imt34") String imtCover34,
                              @Field("cover") String cover,
                              @Field("tp_only") String tpOnly,
                              @Field("idv") String idv,
                              @Field("policy_expiery_date") String policyExpDate,
                              @Field("anti_theft_device") String antiTheft,
                              @Field("vehicle_modified_for_handicap") String vehicleModified,
                              @Field("tppd_restricted_to") String tpp,
                              @Field("voluntary_excess") String voluntary,
                              @Field("voluntary_excess_value") String voluntaryValue,
                              @Field("member_of_aai") String memberAAI,
                              @Field("association_name") String numberAAI,
                              @Field("member_name") String memberName,
                              @Field("membership_expiry_date") String membershipExpiryDate,
                              @Field("electrical_accessory") String electricalAccessory,
                              @Field("electrical_accessory_value") String electricalAccessoryValue,
                              @Field("non_electrical_accessory") String nonElectricalAccessory,
                              @Field("non_electrical_accessory_value") String nonElectricalAccValue,
                              @Field("fule_kit") String fuelKit,
                              @Field("fule_kit_value") String fuelKitValue,
                              @Field("pacoverfor_unnamed_person") String PAUnnamed,
                              @Field("pacoverfor_unnamed_person_value") String PAUnnamedValue,
                              @Field("legal_liability_employee") String legalEmployee,
                              @Field("legal_liability_employee_value") String legalEmployeeValue,
                              @Field("legal_liability_paid_driver") String legalDriver,
                              @Field("legal_liability_paid_driver_value") String legalDriverValue,
                              @Field("fiber_glass_fuel_tank") String fiber,
                              @Field("emergency_cover") String emergencyCover,
                              @Field("consumables") String consumables,
                              @Field("tyre_cover") String tyreCover,
                              @Field("ncb_protection") String ncbProtection,
                              @Field("engine_protector") String engineProtector,
                              @Field("return_invoice") String returnInvoice,
                              @Field("loss_of_key") String lossKey,
                              @Field("road_side_assistance") String roadAssistance,
                              @Field("passenger_assist_cover") String passengerCover,
                              @Field("hydrostatic_lock_cover") String hydrostaticCover,
                              @Field("hospital_cash_cover") String hospitalCashCover,
                              @Field("loss_personal_belonging") String lossPersonal,
                              @Field("tp_expire_policy_company") String tpPreInsurer,
                              @Field("tp_expire_policy_number") String tpPolicyNumber,
                              @Field("tp_policy_expire_date") String tpPolicyExpDate,
                              @Field("manufacture") String manufacture,
                              @Field("model") String model,
                              @Field("fuel_type") String fuel_type,
                              @Field("variant") String variant,
                              @Field("company_id") String company,
                              @Field("sbiCode") String sbiCode,
                              @Field("carrier_type") String carrierType
    );

    @FormUrlEncoded
    @POST("api_review/proposal1_fetch")
    Call<GetOwner> fetchOwner(@Field("token") String token,
                              @Field("quotation_id") String quotationId);

    @FormUrlEncoded
    @POST("api_review/proposal2_fetch")
    Call<GetVehicle> fetchVehicle(@Field("token") String token,
                                  @Field("quotation_id") String quotationId);

    @FormUrlEncoded
    @POST("api_review/proposal3_fetch")
    Call<GetNominee> fetchNominee(@Field("token") String token,
                                  @Field("quotation_id") String quotationId);

    @FormUrlEncoded
    @POST("api_review/proposal1")
    Call<Owner> ownerDetail(@Field("token") String token,
                            @Field("agent_id") String agentId,
                            @Field("user_id") String userId,
                            @Field("quotation_id") String quotationId,
                            @Field("salutation") String salutation,
                            @Field("first_name") String name,
                            @Field("middle_name") String middleName,
                            @Field("last_name") String lastName,
                            @Field("dob") String dob,
                            @Field("organization_name") String orgName,
                            @Field("registration_pincode") String regPinCode,
                            @Field("registration_city") String regCity,
                            @Field("registration_address3") String regAddress3,
                            @Field("registration_address2") String regAddress2,
                            @Field("registration_address1") String regAddress1,
                            @Field("registration_address") String regAddress,
                            @Field("gst_no") String gstNo,
                            @Field("pan_no") String panCard,
                            @Field("pincode") String pinCode,
                            @Field("state") String state,
                            @Field("city") String city,
                            @Field("address3") String address3,
                            @Field("address2") String address2,
                            @Field("address1") String address1,
                            @Field("nationality") String nationality,
                            @Field("additional_contact") String alternateMobile,
                            @Field("email_proposal") String email,
                            @Field("mobile_proposal") String mobile,
                            @Field("occupation") String occupation,
                            @Field("marital_status") String maritalStatus,
                            @Field("gender") String gender
    );

    @FormUrlEncoded
    @POST("api_review/proposal3")
    Call<Nominee> nomineeDetail(@Field("token") String token,
                                @Field("nominee_name") String nomineeName,
                                @Field("nominee_relation") String nomineeRelation,
                                @Field("nominee_gender") String nomineeGender,
                                @Field("nominee_dob") String nomineeDob,
                                @Field("status") String status,
                                @Field("quotation_id") String quotationId);

    @FormUrlEncoded
    @POST("api_review/proposal2")
    Call<VehicleOther> vehicleOtherDetail(@Field("token") String token,
                                          @Field("financier_city") String financierCity,
                                          @Field("financier_name") String financierName,
                                          @Field("hypothecation") String hypothecation,
                                          @Field("chassies_no") String chassisNo,
                                          @Field("engine_no") String engineNo,
                                          @Field("reg_number") String reNumber,
                                          @Field("pre_policy_no") String prePolicyNo,
                                          @Field("quotation_id") String quotationId,
                                          @Field("rto_id") String rtoId);

    @Multipart
    @POST("api/Api_review_pay/UploadDocuments")
    Call<MotorKyc> uploadKyc(@Part("qid") RequestBody quoteId,
                             @Part MultipartBody.Part filePan,
                             @Part MultipartBody.Part fileCheque,
                             @Part MultipartBody.Part fileAadharFront,
                             @Part MultipartBody.Part fileAadharBack,
                             @Part MultipartBody.Part fileElectricBill,
                             @Part MultipartBody.Part fileCertificate,
                             @Part MultipartBody.Part fileProfile);

    @FormUrlEncoded
    @POST("api/Api_review_pay/review_pay_motor")
    Call<Review> getReviewData(@Field("quotation_id") String quotationId,
                               @Field("token") String token);

    @GET("review_pay/index/")
    Call<Session> getMotorPaymentLink(@Query("qid") String quoteId,
                                      @Query("type") String source);

    @FormUrlEncoded
    @POST("quotation_details/ApiData/proposalPincode")
    Call<ProposalPincode> getProposerPincode(@Field("company") String company,
                                             @Field("pincode") String pincode);

    //HEALTH
    @FormUrlEncoded
    @POST("api_quotes_health/save_quote_health")
    Call<HealthQuote> getHealthQuoteId(@Field("token") String token,
                                       @Field("version") String version,
                                       @Field("id") String userId,
                                       @Field("user_type") String userType,
                                       @Field("pname") String name,
                                       @Field("pemail") String email,
                                       @Field("pmobile") String phone,
                                       @Field("gender") String gender,
                                       @Field("pincode") String pincode,
                                       @Field("sumInsured") String sumInsured,
                                       @Field("plan_type") String planType,
                                       @Field("self") String selfAge,
                                       @Field("Spouse") String spouseAge,
                                       @Field("Father") String fatherAge,
                                       @Field("Mother") String motherAge,
                                       @Field("Son1") String son1Age,
                                       @Field("Son2") String son2Age,
                                       @Field("Daughter1") String daughter1Age,
                                       @Field("Daughter2") String daughter2Age,
                                       @Field("member_checkbox[]") String[] members,
                                       @Field("selfDob") String selfDob,
                                       @Field("SpouseDob") String spouseDob,
                                       @Field("FatherDob") String fatherDob,
                                       @Field("MotherDob") String motherDob,
                                       @Field("Son1Dob") String son1Dob,
                                       @Field("Son2Dob") String son2Dob,
                                       @Field("Daughter1Dob") String daughter1Dob,
                                       @Field("Daughter2Dob") String daughter2Dob,
                                       @Field("medical_history") String medicalHistory
    );

    @Multipart
    @POST("api_proposal_health/ProposalCompanyHealth")
    Call<TempResponse> updateHealthProposalIcici(@Part("token") RequestBody token,
                                                 @Part("company") RequestBody company,
                                                 @Part("qid") RequestBody quotationId,
                                                 @Part("title[]") RequestBody title,
                                                 @Part("first_name[]") RequestBody firstName,
                                                 @Part("last_name[]") RequestBody lastName,
                                                 @Part("gender[]") RequestBody gender,
                                                 @Part("dob[]") RequestBody dob,
                                                 @Part("height[]") RequestBody height,
                                                 @Part("weight[]") RequestBody weight,
                                                 @Part("relation_with_applicant[]") RequestBody relation,
                                                 @Part("nominee_realtion[]") RequestBody nomineeRelation,
                                                 @Part("nominee_first_name[]") RequestBody nomineeFirstName,
                                                 @Part("nominee_last_name[]") RequestBody nomineeLastName,
                                                 @Part("exists[]") RequestBody exists,
                                                 @Part("nomineeDob[]") RequestBody nomineeDob,
                                                 @Part("proposer_mobile[]") RequestBody proposerMobile,
                                                 @Part("proposer_email[]") RequestBody proposerEmail,
                                                 @Part("proposer_pan_card[]") RequestBody proposerPan,
                                                 @Part("proposer_pincode[]") RequestBody pincode,
                                                 @Part("proposer_address1[]") RequestBody address1,
                                                 @Part("proposer_address2[]") RequestBody address2,
                                                 @Part("pType[]") RequestBody pType,
                                                 @Part("sametraveller") RequestBody sameTraveller
    );

    @Multipart
    @POST("api_proposal_health/ProposalCompanyHealth")
    Call<TempResponse> updateHealthProposalIffco(@Part("token") RequestBody token,
                                                 @Part("company") RequestBody company,
                                                 @Part("qid") RequestBody quotationId,
                                                 @Part("title[]") RequestBody title,
                                                 @Part("first_name[]") RequestBody firstName,
                                                 @Part("last_name[]") RequestBody lastName,
                                                 @Part("gender[]") RequestBody gender,
                                                 @Part("dob[]") RequestBody dob,
                                                 @Part("height[]") RequestBody height,
                                                 @Part("weight[]") RequestBody weight,
                                                 @Part("occuption[]") RequestBody occupation,
                                                 @Part("relation_with_applicant[]") RequestBody relation,
                                                 @Part("nominee_realtion[]") RequestBody nomineeRelation,
                                                 @Part("nominee_first_name[]") RequestBody nomineeFirstName,
                                                 @Part("nominee_last_name[]") RequestBody nomineeLastName,
                                                 @Part("cigarette[]") RequestBody cigarette,
                                                 @Part("Alcohol[]") RequestBody alcohol,
                                                 @Part("Tobacco[]") RequestBody tobacco,
                                                 @Part("nomineeDob[]") RequestBody nomineeDob,
                                                 @Part("proposer_title[]") RequestBody proposerTitle,
                                                 @Part("proposer_first_name[]") RequestBody proposerFirstName,
                                                 @Part("proposer_last_name[]") RequestBody proposerLastName,
                                                 @Part("proposer_dob[]") RequestBody proposerDob,
                                                 @Part("proposer_gender[]") RequestBody proposerGender,
                                                 @Part("proposer_marital_status[]") RequestBody proposerMaritalStatus,
                                                 @Part("proposer_mobile[]") RequestBody proposerMobile,
                                                 @Part("proposer_mobile_emergency[]") RequestBody proposerMobileEmergency,
                                                 @Part("proposer_email[]") RequestBody proposerEmail,
                                                 @Part("proposer_name_emergency[]") RequestBody proposerNameEmergency,
                                                 @Part("pan_card[]") RequestBody proposerPan,
                                                 @Part("proposer_pincode[]") RequestBody pincode,
                                                 @Part("nominee_pincode[]") RequestBody nmPincode,
                                                 @Part("proposer_address1[]") RequestBody address1,
                                                 @Part("proposer_address2[]") RequestBody address2,
                                                 @Part("pType[]") RequestBody pType,
                                                 @Part("sametraveller") RequestBody sameTraveller
    );

    @FormUrlEncoded
    @POST("api_proposal_health/ProposalCompanyHealth")
    Call<TempResponse> updateHealthProposal(@Field("token") String token,
                                            @Field("company") String company,
                                            @Field("qid") String quotationId,
                                            @Field("nominee_pincode[]") ArrayList<String> nomineePincode,
                                            @Field("nominee_area[]") ArrayList<String> nomineeCity,
                                            @Field("tpa[]") ArrayList<String> tpa,
                                            @Field("proposer_first_name[]") ArrayList<String> proposerFirstName,
                                            @Field("proposer_last_name[]") ArrayList<String> proposerLastName,
                                            @Field("proposer_dob[]") ArrayList<String> proposerDob,
                                            @Field("gender_proposor[]") ArrayList<String> proposerGender,
                                            @Field("proposer_occuption[]") ArrayList<String> proposerOccupation,
                                            @Field("proposer_designation[]") ArrayList<String> proposerDesignation,
                                            @Field("proposor_educationQualification[]") ArrayList<String> proposerQualification,
                                            @Field("proposer_marital_status[]") ArrayList<String> proposerMaritalStatus,
                                            @Field("proposer_mobile[]") ArrayList<String> proposerMobile,
                                            @Field("proposer_email[]") ArrayList<String> proposerEmail,
                                            @Field("proposer_document[]") ArrayList<String> proposerDocument,
                                            @Field("proposer_document_no[]") ArrayList<String> proposerDocumentNo,
                                            @Field("proposer_address1[]") ArrayList<String> proposerAddress1,
                                            @Field("proposer_address2[]") ArrayList<String> proposerAddress2,
                                            @Field("proposer_address3[]") ArrayList<String> proposerAddress3,
                                            @Field("proposer_pincode[]") ArrayList<String> proposerPincode,
                                            @Field("proposer_area[]") ArrayList<String> proposerCity,
                                            @Field("first_name[]") ArrayList<String> firstName,
                                            @Field("last_name[]") ArrayList<String> lastName,
                                            @Field("gender[]") ArrayList<String> gender,
                                            @Field("dob[]") ArrayList<String> dob,
                                            @Field("occuption[]") ArrayList<String> occupation,
                                            @Field("designation[]") ArrayList<String> designation,
                                            @Field("height[]") ArrayList<String> height,
                                            @Field("weight[]") ArrayList<String> weight,
                                            @Field("q1[]") ArrayList<String> que1,
                                            @Field("q2[]") ArrayList<String> que2,
                                            @Field("q3[]") ArrayList<String> que3,
                                            @Field("q4[]") ArrayList<String> que4,
                                            @Field("q5[]") ArrayList<String> que5,
                                            @Field("q6[]") ArrayList<String> que6,
                                            @Field("Alcohol[]") ArrayList<String> alcohol,
                                            @Field("Smoke[]") ArrayList<String> smoke,
                                            @Field("Tobacco[]") ArrayList<String> tobacco,
                                            @Field("Narcotics[]") ArrayList<String> narcotics,
                                            @Field("Substance[]") ArrayList<String> substance,
                                            @Field("nominee_realtion[]") ArrayList<String> nomineeRelation,
                                            @Field("nominee_first_name[]") ArrayList<String> nomineeFirstName,
                                            @Field("nominee_last_name[]") ArrayList<String> nomineeLastName,
                                            @Field("nominee_dob[]") ArrayList<String> nomineeDob,
                                            @Field("nominee_gender[]") ArrayList<String> nomineeGender,
                                            @Field("nominee_mobile[]") ArrayList<String> nomineeMobile,
                                            @Field("nominee_address1[]") ArrayList<String> nomineeAddress1,
                                            @Field("nominee_address2[]") ArrayList<String> nomineeAddress2,
                                            @Field("guardian_first_name[]") ArrayList<String> appointeeFName,
                                            @Field("guardian_last_name[]") ArrayList<String> appointeeLName,
                                            @Field("guardian_age[]") ArrayList<String> appointeeAge,
                                            @Field("guardian_relation[]") ArrayList<String> appointeeRelation,
                                            @Field("Alcohol_Quantity[]") ArrayList<String> alQuantity,
                                            @Field("Alcohol_Years[]") ArrayList<String> alYear,
                                            @Field("Smoke_Quantity[]") ArrayList<String> smQuantity,
                                            @Field("Smoke_Years[]") ArrayList<String> smYear,
                                            @Field("Tobacco_Quantity[]") ArrayList<String> tbQuantity,
                                            @Field("Tobacco_Years[]") ArrayList<String> tbYear,
                                            @Field("Narcotics_Quantity[]") ArrayList<String> narQuantity,
                                            @Field("Narcotics_Years[]") ArrayList<String> narYear,
                                            @Field("Substance_Quantity[]") ArrayList<String> osQuantity,
                                            @Field("Substance_Years[]") ArrayList<String> osYear,
                                            @Field("otherMedical_month[]") ArrayList<String> medMonth,
                                            @Field("otherMedical_year[]") ArrayList<String> medYear,
                                            @Field("otherMedical_name[]") ArrayList<String> nameIllness,
                                            @Field("otherMedical_going[]") ArrayList<String> treatment,
                                            @Field("otherMedical_outcome[]") ArrayList<String> outCome,
                                            //apollo
                                            @Field("proposer_title[]") ArrayList<String> proposerTitle,
                                            @Field("marital_status[]") ArrayList<String> maritalStatus,
                                            @Field("title[]") ArrayList<String> salutation,
                                            @Field("relation[]") ArrayList<String> relation,
                                            @Field("cigarette[]") ArrayList<String> cigarette,
                                            @Field("pouch[]") ArrayList<String> pouch,
                                            @Field("liquor[]") ArrayList<String> liquor,
                                            @Field("beer[]") ArrayList<String> beer,
                                            @Field("wine[]") ArrayList<String> wine,
                                            @Field("tables[]") ArrayList<String> anyDiseases,
                                            @Field("mobile[]") ArrayList<String> addressMobile,
                                            @Field("email[]") ArrayList<String> addressEmail,
                                            @Field("address1[]") ArrayList<String> address1,
                                            @Field("address2[]") ArrayList<String> address2,
                                            @Field("area[]") ArrayList<String> area,
                                            @Field("pincode[]") ArrayList<String> pincode,
                                            @Field("annualIncome[]") ArrayList<String> annualIncome,
                                            @Field("title_nominee[]") ArrayList<String> title_nominee,
                                            //bajaj
                                            @Field("exists[]") ArrayList<String> exists,
                                            @Field("tobacco[]") ArrayList<String> tobaccoBajaj,
                                            @Field("building[]") ArrayList<String> building,
                                            @Field("street[]") ArrayList<String> street,
                                            @Field("montlyIncome[]") ArrayList<String> montlyIncome,
                                            @Field("sametraveller[]") ArrayList<String> sametraveller
    );

    @FormUrlEncoded
    @POST("api_proposal_health/ProposalCompanyHealth")
    Call<TempResponse> updateHealthProposalSbi(@Field("token") String token,
                                               @Field("company") String company,
                                               @Field("qid") String quotationId,
                                               @Field("proposer_title[]") ArrayList<String> proposerTitle,
                                               @Field("proposer_first_name[]") ArrayList<String> proposerFirstName,
                                               @Field("proposer_last_name[]") ArrayList<String> proposerLastName,
                                               @Field("proposer_dob[]") ArrayList<String> proposerDob,
                                               @Field("gender_proposor[]") ArrayList<String> proposerGender,
                                               @Field("proposer_designation[]") ArrayList<String> proposerDesignation,
                                               @Field("proposer_marital_status[]") ArrayList<String> proposerMaritalStatus,
                                               @Field("proposer_mobile[]") ArrayList<String> proposerMobile,
                                               @Field("proposer_email[]") ArrayList<String> proposerEmail,
                                               @Field("plat_proposer[]") ArrayList<String> proposerPlat,
                                               @Field("street_proposer[]") ArrayList<String> proposerStreet,
                                               @Field("proposer_address1[]") ArrayList<String> proposerAddress1,
                                               @Field("proposer_pincode[]") ArrayList<String> proposerPincode,
                                               @Field("title[]") ArrayList<String> title,
                                               @Field("first_name[]") ArrayList<String> firstName,
                                               @Field("last_name[]") ArrayList<String> lastName,
                                               @Field("gender[]") ArrayList<String> gender,
                                               @Field("dob[]") ArrayList<String> dob,
                                               @Field("mobile[]") ArrayList<String> mobile,
                                               @Field("email[]") ArrayList<String> email,
                                               @Field("marital_status[]") ArrayList<String> maritalStatus,
                                               @Field("document[]") ArrayList<String> document,
                                               @Field("document_no[]") ArrayList<String> documentNo,
                                               @Field("occuption[]") ArrayList<String> occupation,
                                               @Field("height[]") ArrayList<String> height,
                                               @Field("weight[]") ArrayList<String> weight,
                                               @Field("plat[]") ArrayList<String> plat,
                                               @Field("building[]") ArrayList<String> building,
                                               @Field("street[]") ArrayList<String> street,
                                               @Field("address1[]") ArrayList<String> address1,
                                               @Field("pincode[]") ArrayList<String> pincode,
                                               @Field("nominee_realtion[]") ArrayList<String> nominee_realtion,
                                               @Field("nominee_first_name[]") ArrayList<String> nomineeFirstName,
                                               @Field("nominee_last_name[]") ArrayList<String> nomineeLastName,
                                               @Field("nominee_gender[]") ArrayList<String> nomineeGender,
                                               @Field("nominee_dob[]") ArrayList<String> nomineeDob,
                                               @Field("sametraveller[]") ArrayList<String> sametraveller,
                                               @Field("aponiteeName[]") ArrayList<String> appointeeName,
                                               @Field("apointee_relation[]") ArrayList<String> appointeeRelation,
                                               @Field("alcohol[]") ArrayList<String> alcohol,
                                               @Field("smoke[]") ArrayList<String> smoke,
                                               @Field("tobacco[]") ArrayList<String> tobacco,
                                               @Field("others[]") ArrayList<String> narcotics);

    @FormUrlEncoded
    @POST("api_proposal_health/ProposalCompanyHealth")
    Call<TempResponse> updateHealthProposalBajaj(@Field("token") String token,
                                                 @Field("company") String company,
                                                 @Field("qid") String quotationId,
                                                 @Field("title[]") ArrayList<String> title,
                                                 @Field("first_name[]") ArrayList<String> firstName,
                                                 @Field("last_name[]") ArrayList<String> lastName,
                                                 @Field("gender[]") ArrayList<String> gender,
                                                 @Field("dob[]") ArrayList<String> dob,
                                                 @Field("occuption[]") ArrayList<String> occupation,
                                                 @Field("height[]") ArrayList<String> height,
                                                 @Field("weight[]") ArrayList<String> weight,
                                                 @Field("mobile[]") ArrayList<String> mobile,
                                                 @Field("email[]") ArrayList<String> email,
                                                 @Field("exists[]") ArrayList<String> exists,
                                                 @Field("tobacco[]") ArrayList<String> tobacco,
                                                 @Field("tables[]") ArrayList<String> tables,
                                                 @Field("tables_value[]") ArrayList<String> tables_value,
                                                 @Field("nominee_first_name[]") ArrayList<String> nomineeFirstName,
                                                 @Field("nominee_last_name[]") ArrayList<String> nomineeLastName,
                                                 @Field("nominee_realtion[]") ArrayList<String> nomineeRelation,
                                                 @Field("building[]") ArrayList<String> building,
                                                 @Field("street[]") ArrayList<String> street,
                                                 @Field("address1[]") ArrayList<String> address1,
                                                 @Field("pincode[]") ArrayList<String> pincode,
                                                 @Field("montlyIncome[]") ArrayList<String> monthlyIncome,
                                                 @Field("sametraveller[]") ArrayList<String> sameTraveller
    );

    @FormUrlEncoded
    @POST("api_quotes_health/show_quotes")
    Call<HealthPremium> getHealthQuotation(@Field("token") String token,
                                           @Field("company") String company,
                                           @Field("qid") String qid
    );

    @FormUrlEncoded
    @POST("api_quotes_life/show_quotes")
    Call<LifePremium> getLifeQuotation(@Field("token") String token,
                                       @Field("company") String company,
                                       @Field("qid") String qid
    );

    @FormUrlEncoded
    @POST("api_quotes_health/filterupdate")
    Call<TempResponse> filterHealth(@Field("token") String token,
                                    @Field("suminsured") String sumInsured,
                                    @Field("policy_period") String tenure,
                                    @Field("pincode") String pincode,
                                    @Field("qid") String qid
    );

    @FormUrlEncoded
    @POST("api_master_health/masterDataHealth")
    Call<PincodeMaster> getSbiPincode(@Field("token") String token,
                                      @Field("qid") String quotationId,
                                      @Field("type") String type,
                                      @Field("company") String company);

    @FormUrlEncoded
    @POST("api_master_health/masterDataHealth")
    Call<PincodeMaster> getHealthPincode(@Field("token") String token,
                                         @Field("qid") String quotationId,
                                         @Field("type") String type,
                                         @Field("company") String company,
                                         @Field("pin") String pin);


    @FormUrlEncoded
    @POST("Health_api/Hospital_list")
    Call<HospitalList> hospitalList(@Field("token") String token,
                                    @Field("company") String company,
                                    @Field("pincode") String pincode,
                                    @Field("app_key") String appKey
    );

    @FormUrlEncoded
    @POST("Health_api/Hospital_state")
    Call<HospitalStateList> getHospitalStateList(@Field("company") String company
    );

    @FormUrlEncoded
    @POST("Health_api/Hospital_city")
    Call<HospitalCityList> getHospitalCityList(@Field("company") String company,
                                               @Field("state") String state
    );

    @FormUrlEncoded
    @POST("Health_api/Hospital_pincode")
    Call<HospitalPincodeList> getHospitalPincodeList(@Field("company") String company,
                                                     @Field("state") String state,
                                                     @Field("city") String city
    );

    @FormUrlEncoded
    @POST("Health_api/Brochure")
    Call<BrochureList> brochureList(@Field("token") String token,
                                    @Field("company") String company,
                                    @Field("plan_name") String planName,
                                    @Field("feat_plan") String ftPlan,
                                    @Field("sub_plan") String subPlanName,
                                    @Field("app_key") String appKey
    );

    @FormUrlEncoded
    @POST("Health_api/health_plan_benifit")
    Call<PlanCover> getPlanBenefits(@Field("token") String token,
                                    @Field("company") String company,
                                    @Field("plan_name") String planName,
                                    @Field("subplan") String subPlan,
                                    @Field("suminsured") String sumInsured,
                                    @Field("app_key") String appKey
    );

    @GET("quotation_details/ApiData/blog.php")
    Call<BlogList> getBlogList();

    @GET("health_review/index/app")
    Call<Session> getHealthPayment(@Query("quote") String quoteId);

    @FormUrlEncoded
    @POST("api_master_health/masterDataHealth")
    Call<HealthGender> getHealthGender(@Field("token") String token,
                                       @Field("qid") String quotationId,
                                       @Field("type") String type,
                                       @Field("company") String company);

    @FormUrlEncoded
    @POST("api_master_health/masterDataHealth")
    Call<HealthSalutation> getSalutation(@Field("token") String token,
                                         @Field("qid") String quotationId,
                                         @Field("type") String type,
                                         @Field("company") String company);

    @FormUrlEncoded
    @POST("api_master_health/masterDataHealth")
    Call<HealthMarital> getMarital(@Field("token") String token,
                                   @Field("qid") String quotationId,
                                   @Field("type") String type,
                                   @Field("company") String company);

    @FormUrlEncoded
    @POST("api_master_health/masterDataHealth")
    Call<HealthCity> getHealthCity(@Field("token") String token,
                                   @Field("qid") String quotationId,
                                   @Field("type") String type,
                                   @Field("company") String company);

    @FormUrlEncoded
    @POST("api_master_health/masterDataHealth")
    Call<BajajPincode> getHealthBajajCity(@Field("token") String token,
                                          @Field("qid") String quotationId,
                                          @Field("type") String type,
                                          @Field("company") String company);

    @FormUrlEncoded
    @POST("api_master_health/downloadPolicy")
    Call<HealthPaymentTrack> getPaymentHealthTrack(@Field("qid") String quotationId,
                                                   @Field("token") String token);

    @FormUrlEncoded
    @POST("api_master_health/masterDataHealth")
    Call<HealthRelation> getRelation(@Field("token") String token,
                                     @Field("qid") String quotationId,
                                     @Field("type") String type,
                                     @Field("company") String company);

    @FormUrlEncoded
    @POST("api_master_health/masterDataHealth")
    Call<HealthState> getState(@Field("token") String token,
                               @Field("qid") String quotationId,
                               @Field("type") String type,
                               @Field("company") String company);


    @FormUrlEncoded
    @POST("api_master_health/masterDataHealth")
    Call<HealthOccupation> getOccupation(@Field("token") String token,
                                         @Field("qid") String quotationId,
                                         @Field("type") String type,
                                         @Field("company") String company);

    @FormUrlEncoded
    @POST("api_master_health/masterDataHealth")
    Call<HealthDocument> getHealthDocument(@Field("token") String token,
                                           @Field("qid") String quotationId,
                                           @Field("type") String type,
                                           @Field("company") String company);

    @FormUrlEncoded
    @POST("api_master_health/masterDataHealth")
    Call<HealthQualification> getHealthQualification(@Field("token") String token,
                                                     @Field("qid") String quotationId,
                                                     @Field("type") String type,
                                                     @Field("company") String company);


    @FormUrlEncoded
    @POST("api_master_health/masterDataHealth")
    Call<HealthApointee> getAppointee(@Field("token") String token,
                                      @Field("qid") String quotationId,
                                      @Field("type") String type,
                                      @Field("company") String company);


    @FormUrlEncoded
    @POST("api_quotes_health/saveBeforeProposal")
    Call<CommonResponse> companyProposal(@Field("token") String token,
                                         @Field("qid") String quotationId,
                                         @Field("gross") Float gross,
                                         @Field("net") Float net,
                                         @Field("tax") Float tax,
                                         @Field("extra") String extra,
                                         @Field("plan_name") String planName,
                                         @Field("plan_code") String planCode,
                                         @Field("suminsured") String sumInsured,
                                         @Field("company") String company);

    @GET("quotation_details/ApiData/healthPincode.php")
    Call<PinList> getPincode();

    @GET("quotation_details/ApiData/employeeList.php")
    Call<Employee> getReference();

    @FormUrlEncoded
    @POST("quotation_details/ApiData/offline_lead_from_no_quote.php")
    Call<TempResponse> offlineRequest(@Field("quote_id") String quotationId,
                                      @Field("allow") String action,
                                      @Field("type") String requestType);

    @FormUrlEncoded
    @POST("api_quotes_life/proposalDetails")
    Call<CommonResponse> updateLifeProposal(@Field("token") String token,
                                            @Field("qid") String quotationId,
                                            @Field("title") String salutation,
                                            @Field("first_name") String name,
                                            @Field("last_name") String lastName,
                                            @Field("dob") String dob,
                                            @Field("pincode") String pinCode,
                                            @Field("state") String state,
                                            @Field("city") String city,
                                            @Field("address1") String address1,
                                            @Field("address2") String address2,
                                            @Field("address3") String address3,
                                            @Field("annual_income") String annualIncome,
                                            @Field("proposer_email") String email,
                                            @Field("proposer_mobile") String mobile,
                                            @Field("occuption") String occupation,
                                            @Field("education") String education,
                                            @Field("marrital_status") String maritalStatus,
                                            @Field("gender") String gender,

                                            @Field("nominee_title") String nomineeTitle,
                                            @Field("nominee_first_name") String nmFirstName,
                                            @Field("nominee_last_name") String nmLasName,
                                            @Field("nominee_gender") String nmGender,
                                            @Field("nominee_marrital_status") String nmMarital,
                                            @Field("nominee_relation") String nmRelation,
                                            @Field("nominee_mobile_no") String nmPhone,
                                            @Field("nominee_dob") String nmDob,

                                            @Field("appointee_title") String appointeeTitle,
                                            @Field("appointee_first_name") String apFirstName,
                                            @Field("appointee_last_name") String apLasName,
                                            @Field("appointee_gender") String apGender,
                                            @Field("appointee_marrital_status") String apMarital,
                                            @Field("appointee_relation") String apRelation,
                                            @Field("appointee_mobile_no") String apPhone,
                                            @Field("appointee_dob") String apDob,

                                            @Field("businessBelongs") String business,
                                            @Field("existingCover") String existingCover,
                                            @Field("EmpName") String empName,
                                            @Field("EmpAddress") String empAddress,
                                            @Field("EmpSector") String empSector,

                                            @Field("spouseName") String spouseName
    );

    @FormUrlEncoded
    @POST("api_quotes_master/moduleMaster")
    Call<ModuleMaster> getModuleMaster(@Field("agent_id") String agentId);

    @FormUrlEncoded
    @POST("api_quotes_master/exam/1")
    Call<QuestionList> getQuestionList(@Field("agent_id") String agentId);

    @FormUrlEncoded
    @POST("Api_master/masterData")
    Call<NomineeStatus> getNomineeRelation(@Field("type") String type,
                                           @Field("company") String company);

    @FormUrlEncoded
    @POST("api_quotes_life/proposalDetailsFetch")
    Call<LifeProposal> fetchLifeProposal(@Field("token") String token,
                                         @Field("qid") String quotationId);

    @FormUrlEncoded
    @POST("Api_master/masterData")
    Call<GenderStatus> getGender(@Field("type") String type,
                                 @Field("company") String company);

    @FormUrlEncoded
    @POST("Api_master/masterData")
    Call<Marital> getMarital(@Field("type") String type,
                             @Field("company") String company);

    @FormUrlEncoded
    @POST("Api_master/masterData")
    Call<SalutationStatus> getSalutation(@Field("type") String type,
                                         @Field("company") String company);

    @FormUrlEncoded
    @POST("Api_master/masterData")
    Call<StateMaster> getMasterState(@Field("type") String type,
                                     @Field("company") String company);

    @FormUrlEncoded
    @POST("Api_master/masterData")
    Call<CityMaster> getMasterCity(@Field("type") String type,
                                   @Field("company") String company,
                                   @Field("id") String stateId);

    @FormUrlEncoded
    @POST("api_quotes_master/checkReadingStudyData")
    Call<BasicResponse> submitTraining(@Field("agent_id") String agentId,
                                       @Field("time_second") String timeInterval,
                                       @Field("module") String moduleNumber);

    @FormUrlEncoded
    @POST("Api_master/masterData")
    Call<PincodeMaster> getMasterPincode(@Field("type") String type,
                                         @Field("company") String company,
                                         @Field("id") String cityId,
                                         @Field("stateId") String stateId);

    @FormUrlEncoded
    @POST("Prequotes_life/save_quote_life_app/")
    Call<GetLifeQuoteId> getQuoteLifeId(@Field("userId") String userId,
                                        @Field("userType") String userType,
                                        @Field("pname") String userName,
                                        @Field("pmobile") String userMobile,
                                        @Field("pemail") String userEmail,
                                        @Field("dob") String dob,
                                        @Field("gender") String gender,
                                        @Field("tobaccoUser") String tobaccoUser,
                                        @Field("income") String income,
                                        @Field("qualification") String qualification,
                                        @Field("occupation") String occupation,
                                        @Field("sumInsured") String sumInsured);

    @FormUrlEncoded
    @POST("api_quotes_life/proposalCompany")
    Call<CommonResponse> saveProposal(@Field("token") String token,
                                      @Field("qid") String qid,
                                      @Field("company") String company,
                                      @Field("gross") String gross,
                                      @Field("net") String net,
                                      @Field("tax") String tax,
                                      @Field("extra") String extra,
                                      @Field("plan_name") String planName,
                                      @Field("suminsured") String sumInsured);

    @FormUrlEncoded
    @POST("api_quotes_life/filterupdate")
    Call<CommonResponse> updateLifeFilter(@Field("token") String token,
                                          @Field("qid") String qid,
                                          @Field("tobaccoUser") String tobaccoUser,
                                          @Field("coverAge") String coverAge,
                                          @Field("income_benefit") String incomeBenefit,
                                          @Field("critical_Illness") String criticalIllness,
                                          @Field("cancer_care") String cancerCare,
                                          @Field("personal_Accident_Cover_rider") String personalAccident,
                                          @Field("accident_Death_Cover") String accidentDeath,
                                          @Field("suminsured") String sumInsured);

    @FormUrlEncoded
    @POST("Api_master/masterData")
    Call<OccupationStatus> getOccupation(@Field("type") String type,
                                         @Field("company") String company);

    @FormUrlEncoded
    @POST("Api_master/masterData")
    Call<FinanceStatus> getFinancier(@Field("type") String type,
                                     @Field("company") String company);

    @FormUrlEncoded
    @POST("Prequotes_life/masterData")
    Call<LifePinList> getLifePincode(@Field("type") String type,
                                     @Field("pincode") String pincode);

    @FormUrlEncoded
    @POST("Prequotes_life/masterData")
    Call<AnualIncomeList> getLifeAnnual(@Field("type") String type);

    @FormUrlEncoded
    @POST("Prequotes_life/masterData")
    Call<QualificationList> getLifeQualification(@Field("type") String type,
                                                 @Field("back") String back);

    @FormUrlEncoded
    @POST("Prequotes_life/masterData")
    Call<MaritalList> getLifeMarital(@Field("type") String type);

    @FormUrlEncoded
    @POST("Prequotes_life/masterData")
    Call<SumList> getLifeSumInsured(@Field("type") String type);

    @FormUrlEncoded
    @POST("Prequotes_life/masterData")
    Call<NomineeList> getLifeNomineeRelation(@Field("type") String type);

    @FormUrlEncoded
    @POST("Prequotes_life/masterData")
    Call<OccupationList> getLifeOccupation(@Field("type") String type);

    @FormUrlEncoded
    @POST("Prequotes_life/masterData")
    Call<SectorList> getLifeSector(@Field("type") String type);

    @FormUrlEncoded
    @POST("Prequotes_life/masterData")
    Call<GenderList> getLifeGender(@Field("type") String type);

    @FormUrlEncoded
    @POST("Prequotes_life/masterData")
    Call<BusinessList> getLifeBusiness(@Field("type") String type);

    @FormUrlEncoded
    @POST("Prequotes_life/masterData")
    Call<SalutationList> getLifeSalutation(@Field("type") String type);

    @FormUrlEncoded
    @POST("Prequotes_life/masterData")
    Call<AppointeeList> getLifeAppointee(@Field("type") String type);

    @GET("quotation_details/ApiData/NewState.php")
    Call<StateList> getStateList();

    @GET("quotation_details/ApiData/state.php")
    Call<StateListId> getStateListId();

    @GET("quotation_details/ApiData/bank_name.php")
    Call<Bank> getBankList();

    @FormUrlEncoded
    @POST("quotation_details/ApiData/city.php")
    Call<CityListId> getCitiesListId(@Field("state_id") String stateId);

    @FormUrlEncoded
    @POST("quotation_details/ApiData/NewCity.php")
    Call<CityList> getCitiesList(@Field("state_name") String stateId);

    @FormUrlEncoded
    @POST("quotation_details/ApiData/NewPincode.php")
    Call<PinList> getPinList(@Field("city_name") String cityName);


    @FormUrlEncoded
    @POST("api_master/manufacture")
    Call<ManufactureList> getManufacture(@Field("type") String type,
                                         @Field("pcvType") String pcvType,
                                         @Field("company") String pcvCompany);

    @FormUrlEncoded
    @POST("api_master/modal")
    Call<ModelList> getModel(@Field("make_id") String make_id,
                             @Field("type") String vehicleType,
                             @Field("pcvType") String pcvType,
                             @Field("company") String pcvCompany
    );

    @FormUrlEncoded
    @POST("api_master/variant")
    Call<VariantList> getVariant(@Field("make_id") String make_id,
                                 @Field("model_id") String model_id,
                                 @Field("type") String vehicleType,
                                 @Field("fuel_type") String fuel_type,
                                 @Field("company") String pcvCompany);

    @FormUrlEncoded
    @POST("api_master/fuelType")
    Call<Fuel> getFuel(@Field("make_id") String make_id,
                       @Field("model_id") String model_id,
                       @Field("type") String vehicleType,
                       @Field("company") String pcvCompany);


    @FormUrlEncoded
    @POST("quotation_details/ApiData/forgotPassword.php")
    Call<SendOtp> sendOtp(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("quotation_details/get_sbiData")
    Call<SbiMisd> sbiData(@Field("lastInsertId") String id);

    @GET("quotation_details/get_DataMiscd")
    Call<SbiMisd> getMiscDData();

    @FormUrlEncoded
    @POST("quotation_details/ApiData/otpMatch.php")
    Call<Otp> verifyOtp(@Field("mobile") String mobile,
                        @Field("otp") String otp);

    @FormUrlEncoded
    @POST("quotation_details/ApiData/updatePassword.php")
    Call<BasicResponse> updatePassword(@Field("mobile") String mobile,
                                       @Field("newPassword") String newPassword);

    @FormUrlEncoded
    @POST("quotation_details/ApiData/policy.php")
    Call<AgentPolicyList> agentPolicyList(@Field("type") String type,
                                          @Field("id") String id);

    @FormUrlEncoded
    @POST("quotation_details/ApiData/policy_health.php")
    Call<HealthPolicy> getHealthPolicy(@Field("id") String id,
                                       @Field("type") String type);

    @FormUrlEncoded
    @POST("quotation_details/ApiData/customer_list.php")
    Call<CustomerList> getCustomerList(@Field("added_by") String type,
                                       @Field("added_by_id") String id);

    @FormUrlEncoded
    @POST("quotation_details/ApiData/getLeads.php")
    Call<Leads> leadList(@Field("type") String userType,
                         @Field("id") String agentId);

    @FormUrlEncoded
    @POST("quotation_details/quotation")
    Call<QuotationList> quotationList(@Field("type") String type,
                                      @Field("id") String id);

    @FormUrlEncoded
    @POST("Quotation_details/healthquotation")
    Call<HealthQuoteList> healthQuotationList(@Field("type") String type,
                                              @Field("id") String id);

    @FormUrlEncoded
    @POST("quotation_details/inspection")
    Call<QuotationList> inspectionList(@Field("type") String type,
                                       @Field("id") String id);

    @FormUrlEncoded
    @POST("quotation_details/ApiData/getDocsWalletListNew.php")
    Call<DocsList> getDocsWalletList(@Field("AgentUserId") String userId,
                                     @Field("UserType") String userType);

    @FormUrlEncoded
    @POST("quotation_details/ApiData/pos_details.php")
    Call<AgentDetail> getAgentDetail(@Field("agent_id") String agentId,
                                     @Field("type") String userTYpe);

    @FormUrlEncoded
    @POST("quotation_details/ApiData/resetPassword.php")
    Call<BasicResponse> changePassword(@Field("mobile") String mobile,
                                       @Field("oldPassword") String otp,
                                       @Field("newPassword") String newPassword,
                                       @Field("previousPass") String oldPass,
                                       @Field("userId") String userId,
                                       @Field("userType") String userType);

    @FormUrlEncoded
    @POST("quotation_details/ApiData/RequestEligibility.php")
    Call<CommonResponse> requestEligibility(@Field("AgentId") String mobile);

    @GET("quotation_details/ApiData/previous_insurer.php")
    Call<InsurerList> getInsurerList();

    @GET("crm-api/backuplive/api/Globel/GetIns_Companies")
    Call<InsurerNewList> getNewInsurerList();

    @GET("quotation_details/ApiData/rto.php")
    Call<RTOList> getRtoList();

    @GET("quotation_details/ApiData/account_type.php")
    Call<Account> getAccType();

    @GET("quotation_details/ApiData/cities.php")
    Call<CityList> getCities();

    @FormUrlEncoded
    @POST("quotation_details/ApiData/rtoLocation.php")
    Call<RtoLocation> getRtoLocation(@Field("quotation_id") String quotationId);

    @FormUrlEncoded
    @POST("quotation_details/ApiData/rto_location")
    Call<CommonResponse> validRto(@Field("rto") String rto);

    @FormUrlEncoded
    @POST("api_quotes_health/suminsureds")
    Call<HealthSumInsured> getHealthSum(@Field("token") String token);

    @Multipart
    @POST("quotation_details/ApiData/pos_document_update.php")
    Call<AgentDocuments> uploadAgentDocument(@Part("agent_id") RequestBody agentId,
                                             @Part MultipartBody.Part filePan,
                                             @Part MultipartBody.Part fileAadharFront,
                                             @Part MultipartBody.Part fileDegree,
                                             @Part MultipartBody.Part fileCheque,
                                             @Part MultipartBody.Part fileProfile,
                                             @Part MultipartBody.Part fileSignature,
                                             @Part MultipartBody.Part fileAadharBack);


    @Multipart
    @POST("quotation_details/ApiData/add_docs_walletNew.php")
    Call<DocWallet> uploadDoc(@Part("id") RequestBody agentId,
                              @Part("added_by") RequestBody addedBy,
                              @Part("doc_type") RequestBody docType,
                              @Part("client_party_name") RequestBody clientPartyName,
                              @Part("client_party_contact") RequestBody clientPartContact,
                              @Part("veh_registration_no") RequestBody regNumber,
                              @Part("policy_number") RequestBody policyNumber,
                              @Part("engine_chassis_no") RequestBody engineChassis,
                              @Part MultipartBody.Part fileRCFront,
                              @Part MultipartBody.Part fileRCBack,
                              @Part MultipartBody.Part fileInsurance,
                              @Part MultipartBody.Part fileGst,
                              @Part MultipartBody.Part fileOther,
                              @Part MultipartBody.Part fileMandate,
                              @Part MultipartBody.Part fileOtherDocNm,
                              @Part MultipartBody.Part fileProposal,
                              @Part MultipartBody.Part fileKyc);

    @FormUrlEncoded
    @POST("quotation_details/ApiData/profile_update_request.php")
    Call<ProfileRequest> updateProfile(@Field("id") String agentId,
                                       @Field("type") String type,
                                       @Field("data_array") String values);

    @Multipart
    @POST("quotation_details/ApiData/profile_update_request.php")
    Call<ProfileRequest> updateBankDetail(@Part("id") RequestBody agentId,
                                          @Part("type") RequestBody type,
                                          @Part("data_array") RequestBody values,
                                          @Part MultipartBody.Part fileCheque);

    @Multipart
    @POST("quotation_details/ApiData/update_profile.php")
    Call<BasicResponse> updateProfile(@Part("agent_id") RequestBody agentId,
                                      @Part MultipartBody.Part fileCheque);

    @Multipart
    @POST("quotation_details/ApiData/profile_update_request.php")
    Call<ProfileRequest> updateBankDetail1(@Part("id") RequestBody agentId,
                                           @Part("type") RequestBody type,
                                           @Part("data_array") RequestBody values);

    @Multipart
    @POST("quotation_details/ApiData/profile_update_request.php")
    Call<ProfileRequest> updateDocument(@Part("id") RequestBody agentId,
                                        @Part("type") RequestBody type,
                                        @Part("data_array") RequestBody data,
                                        @Part MultipartBody.Part filePan,
                                        @Part MultipartBody.Part fileAadhar,
                                        @Part MultipartBody.Part fileAadharBack,
                                        @Part MultipartBody.Part fileQualification,
                                        @Part MultipartBody.Part fileImage,
                                        @Part MultipartBody.Part fileSignature);

    @Multipart
    @POST("api_master/proposalCompanies")
    Call<ProposalCompany> proposalCompany(@Part("company") RequestBody companyName,
                                          @Part("premium") RequestBody premium,
                                          @Part("policy_start_date") RequestBody policyStartDate,
                                          @Part("policy_end_date") RequestBody policyEndDate,
                                          @Part("idv") RequestBody idv,
                                          @Part("tenure") RequestBody tenure,
                                          @Part("status") RequestBody status,
                                          @Part("net") RequestBody net,
                                          @Part("gst") RequestBody gst,
                                          @Part("od") RequestBody od,
                                          @Part("tp") RequestBody tp,
                                          @Part("flag") RequestBody flag,
                                          @Part("pa") RequestBody pa,
                                          @Part("quotation_id") RequestBody quotationId);

    @FormUrlEncoded
    @POST("quotation_details/ApiData/filter.php")
    Call<Filter> filter(@Field("pa_cover") String pACover,
                        @Field("zero_dept") String zeroDept,
                        @Field("cover_for") String coverFor,
                        @Field("vehicle_owned_by") String ownedBy,
                        @Field("idv") String idv,
                        @Field("owner_change") String ownerChange,
                        @Field("claim_expiring_policy") String claimExp,
                        @Field("ncb_old") String ncbOld,
                        @Field("cover") String cover,
                        @Field("tp_only") String tpOnly,
                        @Field("policy_expiery_date") String policyExpDate,
                        @Field("quotation_id") String quotationId,
                        @Field("tp_expire_policy_company") String tpPreInsurer,
                        @Field("tp_expire_policy_number") String tpPolicyNumber,
                        @Field("tp_policy_expire_date") String tpPolicyExpDate);

    @FormUrlEncoded
    @POST("quotation_details/ApiData/otp_sent.php")
    Call<SendOtp> verifyMobile(@Field("quoteId") String quoteId);

    @FormUrlEncoded
    @POST("quotation_details/ApiData/SentOTPPasswordChange.php")
    Call<SendOtp> changePassOtp(@Field("mobile") String quoteId);

    @FormUrlEncoded
    @POST("quotation_details/ApiData/posVerify.php")
    Call<SendOtp> verifyPosMobile(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("quotation_details/ApiData/otp_verify.php")
    Call<CommonResponse> verifyMobileOtp(@Field("quoteId") String quoteId,
                                         @Field("otp") String otp);

    @FormUrlEncoded
    @POST("quotation_details/ApiData/changeNumber.php")
    Call<ChangeNo> changeNumber(@Field("mobile_no") String mobile,
                                @Field("quotation_id") String quotationId);


    @FormUrlEncoded
    @POST("quotation_details/ApiData/ShareLink.php")
    Call<BasicResponse> shareLink(@Field("quoteId") String quoteId,
                                  @Field("email") String email,
                                  @Field("mobile") String phone,
                                  @Field("for") String vehicle,
                                  @Field("type") String vehicleType);

    @Multipart
    @POST("upload_doc_api/agent_policy_document")
    Call<CommonResponse> uploadPolicyDocument(@Part("agent_id") RequestBody agentId,
                                              @Part("quote_id") RequestBody quoteId,
                                              @Part("business_type") RequestBody data,
                                              @Part MultipartBody.Part fileInvoice,
                                              @Part MultipartBody.Part filePrePolicy,
                                              @Part MultipartBody.Part fileRCFront,
                                              @Part MultipartBody.Part fileRCBack);

    @Multipart
    @POST("quotation_details/ApiData/addLeads.php")
    Call<NewLead> addLead(@Part("added_by") RequestBody addedBy,
                          @Part("agent_id") RequestBody agentId,
                          @Part("employee_id") RequestBody employee_id,
                          @Part("type") RequestBody leadType,
                          @Part("name") RequestBody name,
                          @Part("email") RequestBody email,
                          @Part("mobile") RequestBody mobile,
                          @Part("gadi_type") RequestBody vehicleType,
                          @Part("gadi_no") RequestBody vehicleNo,
                          @Part("registration_year") RequestBody registrationYear,
                          @Part("make") RequestBody makeName,
                          @Part("model") RequestBody modelName,
                          @Part("variant") RequestBody variantName,
                          @Part("expiry_date") RequestBody expiryDate,
                          @Part("fuel_type") RequestBody fuelType,
                          @Part("policy_type") RequestBody policyType,
                          @Part("policy_expiry_duration") RequestBody policyExpiryStatus,
                          @Part("previous_ins") RequestBody previousInsurance,
                          @Part("pincode") RequestBody cityHealth,
                          @Part("gender") RequestBody gender,
                          @Part("sum_assured") RequestBody sumAssured,
                          @Part("plan_type") RequestBody planType,
                          @Part("self_age") RequestBody selfAge,
                          @Part("spouse_age") RequestBody spouseAge,
                          @Part("father_age") RequestBody fatherAge,
                          @Part("mother_age") RequestBody motherAge,
                          @Part("son_age") RequestBody sonAge,
                          @Part("daughter_age") RequestBody daughterAge,
                          @Part("city_name") RequestBody corporateCity,
                          @Part("corporate_ins_type") RequestBody corporateInsuranceType,
                          @Part("organisation_name") RequestBody organisationName,
                          @Part("lead_from") RequestBody leadFrom,
                          @Part("lead_from_pos") RequestBody leadPos,
                          @Part("lead_from_employee") RequestBody leadEmployee,
                          @Part("assign") RequestBody assign,
                          @Part("file_type") RequestBody businessType,
                          @Part("previous_policy_docs_availablity") RequestBody isPreviousPolicy,
                          @Part("health_policy_type") RequestBody health_policy_type,

                          @Part MultipartBody.Part fileRCFront,
                          @Part MultipartBody.Part fileRCBack,
                          @Part MultipartBody.Part fileInvoice,
                          @Part MultipartBody.Part[] documents,
                          @Part MultipartBody.Part fileKyc,
                          @Part MultipartBody.Part fileOtherDoc);

    @FormUrlEncoded
    @POST("quotation_details/ApiData/Earning.php")
    Call<EarningList> earning(@Field("agent_id") String agentId);

    @FormUrlEncoded
    @POST("quotation_details/ApiData/DeleteDocWallet.php")
    Call<BasicResponse> removeDoc(@Field("docWalletId") String docId);

    @GET("quotation_details/ApiData/corporateInsuranceType.php")
    Call<CorporateList> getCorporateList();

    @FormUrlEncoded
    @POST("quotation_details/ApiData/leadAssignEmployeeList.php")
    Call<Assign> getAssignList(@Field("type") String leadType);


    @FormUrlEncoded
    @POST("api_quotes_master/submit_answer")
    Call<BasicResponse> submitQuestion(@Field("agent_id") String agentId,
                                       @Field("qid") String questionId,
                                       @Field("answer") String answer);

    @FormUrlEncoded
    @POST("api_quotes_master/submit_exam")
    Call<BasicResponse> submitExam(@Field("agent_id") String agentId,
                                   @Field("type") String type,
                                   @Field("marks") String marks,
                                   @Field("time") String time);

    @FormUrlEncoded
    @POST("quotation_details/ApiData/quotationList.php")
    Call<AddonCover> getCovers(@Field("quotation_id") String quotationId);


    @FormUrlEncoded
    @POST("quotation_details/ApiData/paymentStatus.php")
    Call<PaymentTrack> getPaymentStatus(@Field("quotation_id") String quotationId,
                                        @Field("url") String url);

    @FormUrlEncoded
    @POST("api_v2/iffco/SetSession")
    Call<Session> setSession(@Field("quotation_id") String quotationId,
                             @Field("post_url") String linkUrl);

    @GET("api/nonMotor/NonMotorLead")
    Call<NonMotorList> getNonMotorLead();

    @FormUrlEncoded
    @POST("api/nonMotor/NonMotorLeadAddForm")
    Call<BasicResponse> nonMotorLead(@Field("ins_type") String insuranceType,
                                     @Field("company_name") String companyName,
                                     @Field("name") String name,
                                     @Field("mobile") String mobile,
                                     @Field("email") String email,
                                     @Field("pincode") String city,
                                     @Field("remarks") String remarks);

    @FormUrlEncoded
    @POST("api/HealthQuote/Compare_Quote")
    Call<Compare> compareData(@Field("company") String comapnies,
                              @Field("sumInsured") String sumInsured,
                              @Field("plan") String plan,
                              @Field("subplan") String subplan,
                              @Field("tenure") String tenure,
                              @Field("premium") String premium,
                              @Field("app_key") String appKey);

    @FormUrlEncoded
    @POST("contactus/add_contact_us")
    Call<CommonResponse> getQuery(@Field("type") String type,
                                  @Field("name") String name,
                                  @Field("mobile") String mobile,
                                  @Field("email") String email,
                                  @Field("message") String remarks);

    @FormUrlEncoded
    @POST("api/Life_travel_lead/Add_life_travel_lead")
    Call<BasicResponse> raiseLifeTravelQuery(@Field("type") String type,
                                             @Field("name") String name,
                                             @Field("mobile") String mobile,
                                             @Field("email") String email,
                                             @Field("user_id") String userId,
                                             @Field("user_type") String userType,
                                             @Field("remarks") String remarks);

    @FormUrlEncoded
    @POST("quotation_details/quotation_detailsfetch")
    Call<FetchQuote> fetchQuote(@Field("registration_no") String regNo);

    @FormUrlEncoded
    @POST("quotation_details/vahan_detailsfetch")
    Call<VahanData> fetchVahanData(@Field("registration_no") String regNo,
                                   @Field("type") String vType);

    @Multipart
    @POST("crm-api/backuplive/api/Ticket/Add")
    Call<RaiseTicket> createTicket(@Part("User_Id") RequestBody userId,
                                   @Part("User_Type") RequestBody userType,
                                   @Part("QuoteId") RequestBody quoteId,
                                   @Part("PolicyType") RequestBody policyType,
                                   @Part("RcYesNo") RequestBody isRC,
                                   @Part("VehicleType") RequestBody vehicleType,
                                   @Part("Make") RequestBody make,
                                   @Part("Model") RequestBody model,
                                   @Part("Variant") RequestBody Variant,
                                   @Part("FuelType") RequestBody fuelType,
                                   @Part("CC") RequestBody cubicCapacity,
                                   @Part("Sc") RequestBody seatingCapacity,
                                   @Part("Gvw") RequestBody gvw,
                                   @Part("InsurerName") RequestBody insurerName,
                                   @Part("CityName") RequestBody cityName,
                                   @Part("PageUrl") RequestBody pageUrl,
                                   @Part("PageType") RequestBody pageType,
                                   @Part("Policy_No") RequestBody policyNo,
                                   @Part("Request_Id") RequestBody requestId,
                                   @Part("Remark") RequestBody remark,
                                   @Part("Source") RequestBody source,

                                   @Part MultipartBody.Part filePaymentScreen,//payment Done, policy not received
                                   @Part MultipartBody.Part fileRcFront,
                                   @Part MultipartBody.Part fileRcBack,
                                   @Part MultipartBody.Part fileErrorPage,//Error during payment
                                   @Part MultipartBody.Part fileAttachment);//Other, survey

    @FormUrlEncoded
    @POST("crm-api/backuplive/api/Ticket/Add")
    Call<RaiseTicket> reviewPageTicket(@Field("User_Id") String userId,
                                       @Field("User_Type") String usertype,
                                       @Field("PageType") String pageType,
                                       @Field("PolicyType") String policyType,
                                       @Field("PageUrl") String pageUrl,
                                       @Field("QuoteId") String quoteId,
                                       @Field("Remark") String remark,
                                       @Field("Source") String source);

    @Multipart
    @POST("crm-api/backuplive/api/Offlinequote/SubmitApi")
    Call<BasicResponse> raiseOffline(@Part("login_id") RequestBody userId,
                                     @Part("login_type") RequestBody userType,
                                     @Part("customername") RequestBody customerName,
                                     @Part("customeremail") RequestBody customerEmail,
                                     @Part("customermobileno") RequestBody customerMobile,
                                     @Part("VehicleTypeId") RequestBody vehicleType,
                                     @Part("PolicyTypeId") RequestBody policyTypeId,//1,2,3
                                     @Part("IsNewVehicle") RequestBody isNewVehicle,//Rollover/New
                                     @Part("RegistrationDate") RequestBody registrationDate,
                                     @Part("Registration_State_Code") RequestBody RegStateCode,
                                     @Part("Registration_District_Code") RequestBody RegDistrictCode,
                                     @Part("Registration_City_Code") RequestBody RegCityCode,
                                     @Part("Registration_Code") RequestBody regCode,
                                     @Part("IsPyp") RequestBody isPyp,
                                     @Part("Pypexpirydate") RequestBody pExpiryDate,
                                     @Part("IsClaimRequest") RequestBody isClaimRequest,
                                     @Part("OwnerChange") RequestBody ownerChange,
                                     @Part("NcbProtection") RequestBody ncbProtection,
                                     @Part("previousinsurer") RequestBody previousInsurer,
                                     @Part("lastyearncb") RequestBody lastYearNcb,
                                     @Part("IsBestQuote") RequestBody isBestQuote,
                                     @Part("requiredinsurer") RequestBody requiredInsurer,
                                     @Part("requiredaddons") RequestBody requiredAddons,
                                     @Part("idvlowestvalue") RequestBody minIdv,
                                     @Part("idvhighestvalue") RequestBody maxIdv,
                                     @Part("remark") RequestBody remark,
                                     @Part("Tenure_Comprehensive") RequestBody tenure,
                                     @Part("TPexpirydate") RequestBody tpExtDate,
                                     @Part("TPpolicynumber") RequestBody tpPypNo,
                                     @Part("TPpolicyinsurer") RequestBody tpPyp,
                                     @Part("Source") RequestBody source,

                                     @Part MultipartBody.Part fileRcFront,//RcFront
                                     @Part MultipartBody.Part fileRcBack,//RcBack
                                     @Part MultipartBody.Part fileInvoice,//InvoiceImage
                                     @Part MultipartBody.Part filePyp,//PreviousPolicyPdf
                                     @Part MultipartBody.Part fileAttachment);//OtherDocument

    @GET("crm-api/backuplive/api/Geographically/RoleGetStateCityDistrictData_By_PinCode")
    Call<ClaimPincode> getCrmPincode(@Query("Pincode") String pincode);

    @GET("crm-api/backuplive/api/Claim/SingleData")
    Call<ClaimView> getClaimViewData(@Query("Claim_Id") String claimId,
                                     @Query("Device_Type") String source);

    @GET("crm-api/backuplive/api/b-crm/Endosment/getSinglePolicyDetails")
    Call<EndorseViewData> getEndorseView(@Query("Id") String id,
                                         @Query("Device_Type") String source);

    @GET("crm-api/backuplive/api/Claim/GridData")
    Call<MyClaim> getClaimRequest(@Query("User_Id") String userId,
                                  @Query("User_Type") String userType,
                                  @Query("url") String url,
                                  @Query("PageType") String PageType,
                                  @Query("Device_Type") String source);

    @GET("crm-api/backuplive/api/b-crm/Cancellation/viewCancellationRequestsData")
    Call<MyCancelled> getCancelledList(@Query("User_Id") String userId,
                                       @Query("User_Type") String userType,
                                       @Query("url") String url,
                                       @Query("Device_Type") String source);

    @GET("crm-api/backuplive/api/Claim/CreateClaimPolicyDataFetch")
    Call<ClaimList> getAllClaim(@Query("User_Id") String userId,
                                @Query("User_Type") String userType,
                                @Query("searchTerm") String searchTerm,
                                @Query("Type") String type,
                                @Query("Device_Type") String source);

    @FormUrlEncoded
    @POST("crm-api/backuplive/api/b-crm/Cancellation/createCancellationRequestsData")
    Call<CancellationList> getCancellationList(@Field("User_Id") String userId,
                                               @Field("User_Type") String userType,
                                               @Field("SearchValue") String searchTerm,
                                               @Field("Device_Type") String source);

    @GET("crm-api/backuplive/api/Ticket/TicketDatas")
    Call<MyTicket> getAllTicket(@Query("User_Id") String userId,
                                @Query("url") String url,
                                @Query("ApiSource") String source,
                                @Query("User_Type") String userType);

    @FormUrlEncoded
    @POST("crm-api/backuplive/api/b-crm/PolicyCommon/getPolicyDataCommon")
    Call<EndorsementList> getAllEndorsement(@Field("User_Id") String userId,
                                            @Field("User_Type") String userType,
                                            @Field("SearchValue") String searchValue,//policy no, mobile, reg no
                                            @Field("url") String url,
                                            @Field("Device_Type") String source);

    @GET("crm-api/backuplive/api/offlinequote/SingleData")
    Call<OfflineView> getOfflineQuoteDetail(@Query("User_Id") String userId,
                                            @Query("User_Type") String userType,
                                            @Query("Quotation") String quotationId,
                                            @Query("types") String url);

    @GET("crm-api/backuplive/api/Chat/Messages")
    Call<ChatList> getClaimChat(@Query("User_Id") String userId,
                                @Query("Type") String chatType,
                                @Query("Reference_Id") String referenceId);

    @Multipart
    @POST("crm-api/backuplive/api/Chat/Send")
    Call<BasicBool> sendChat(@Part("Login_User_Id") RequestBody userId,
                             @Part("Login_User_Type") RequestBody userType,
                             @Part("CreateUser_Id") RequestBody createUserId,//same as userId
                             @Part("CreateUser_Type") RequestBody createUserType,//same as userType
                             @Part("CurrentUser_Id") RequestBody currentId,
                             @Part("CurrentUser_Type") RequestBody currentType,
                             @Part("Is_Attachement") RequestBody isAttachment,
                             @Part("Message") RequestBody message,
                             @Part("Reference_No") RequestBody refNo,
                             @Part("Reference_Id") RequestBody ferId,
                             @Part("Type") RequestBody msgType,
                             @Part MultipartBody.Part fileAttachment);

    @GET("crm-api/backuplive/api/Globel/GetQuoteDate")
    Call<CrmQuotes> getCrmQuotes(@Query("Ids") String userId,
                                 @Query("Roles") String userType);

    @GET("crm-api/backuplive/api/Offlinequote/GetAddon")
    Call<AddonList> getAddonList(@Query("vehicleType") String vehicleType);

    @GET("crm-api/backuplive/api/offlinequote/QuotationDataFetch")
    Call<OfflineList> getOfflineQuote(@Query("User_Id") String userId,
                                      @Query("User_Type") String userType,
                                      @Query("url") String url,
                                      @Query("PageType") String pageType);

    @GET("crm-api/backuplive/api/b-crm/ManageCancellation/getSinglePolicyDetails")
    Call<CancellationData> getCancellationSingle(@Query("Id") String userId,
                                                 @Query("Device_Type") String source);

    @FormUrlEncoded
    @POST("crm-api/backuplive/api/b-crm/Endosment/viewEndosmentRequestsData")
    Call<EndorseCreatedList> getCreatedEndorsement(@Field("User_Id") String userId,
                                                   @Field("User_Type") String userType,
                                                   @Field("url") String url,
                                                   @Field("Device_Type") String source);

    @FormUrlEncoded
    @POST("crm-api/backuplive/api/Subpos/ViewDatas")
    Call<MobilePosList> getMobilePosData(@Field("User_Id") String userId,
                                         @Field("User_Type") String userType,
                                         @Field("Action") String action,
                                         @Field("Device_Type") String source);

    @FormUrlEncoded
    @POST("crm-api/backuplive/api/Subpos/Add")
    Call<NewMobilePos> raiseMobilePos(@Field("User_Id") String userId,
                                      @Field("User_Type") String userType,
                                      @Field("Data") String action,
                                      @Field("Device_Type") String source);

    @FormUrlEncoded
    @POST("crm-api/backuplive/api/Subpos/ApprovedSubpos")
    Call<ResponseBool> newChildPos(@Field("User_Id") String userId,
                                   @Field("User_Type") String userType,
                                   @Field("Mobile") String mobile,
                                   @Field("Otp") String Otp,
                                   @Field("Device_Type") String source);

    @FormUrlEncoded
    @POST("crm-api/backuplive/api/Subpos/SendOtp")
    Call<ChildPosOtp> getChildPosOtp(@Field("User_Id") String userId,
                                     @Field("User_Type") String userType,
                                     @Field("Mobile") String Mobile,
                                     @Field("Device_Type") String source);

    @FormUrlEncoded
    @POST("crm-api/backuplive/api/b-crm/Endosment/searchMakes")
    Call<VehicleData> endorsementMake(@Field("source") String source,
                                      @Field("gadiType") String gadiType);

    @FormUrlEncoded
    @POST("crm-api/backuplive/api/b-crm/Endosment/searchModels")
    Call<VehicleData> endorsementModel(@Field("source") String source,
                                       @Field("gadiType") String gadiType,
                                       @Field("make") String make);

    @FormUrlEncoded
    @POST("crm-api/backuplive/api/b-crm/Endosment/searchVariants")
    Call<VehicleData> endorsementVariant(@Field("source") String source,
                                         @Field("gadiType") String gadiType,
                                         @Field("model") String model);

    @FormUrlEncoded
    @POST("crm-api/backuplive/api/b-crm/Universal/searchMakes")
    Call<VehicleData> surveyMake(@Field("product") String product);

    @FormUrlEncoded
    @POST("crm-api/backuplive/api/b-crm/Universal/searchModels")
    Call<VehicleData> surveyModel(@Field("make") String make);

    @FormUrlEncoded
    @POST("crm-api/backuplive/api/b-crm/Universal/searchVariants")
    Call<VehicleData> surveyVariant(@Field("model") String model);

    @FormUrlEncoded
    @POST("crm-api/backuplive/api/b-crm/Survey/getSingleSurveyDetails")
    Call<SingleSurvey> singleSurveyData(@Field("id") String surveyId,
                                        @Field("Device_Type") String source);

    @Multipart
    @POST("crm-api/backuplive/api/b-crm/Endosment/submitEndosmentForm")
    Call<BasicResponse> raiseEndorsement(@Part("loginId") RequestBody userId,
                                         @Part("loginType") RequestBody userType,
                                         @Part("SrId") RequestBody srId,
                                         @Part("srNo") RequestBody srNo,
                                         @Part("formValues") RequestBody formJson,
                                         @Part("oldDataString") RequestBody formOldJson,
                                         @Part("nameUpdateReason") RequestBody nameUpdateReason,
                                         @Part("ncbUpdateReason") RequestBody ncbUpdateReason,
                                         @Part("deviceType") RequestBody source,
                                         @Part MultipartBody.Part fileRcFront,
                                         @Part MultipartBody.Part fileRcBack,
                                         @Part MultipartBody.Part fileLetter,
                                         @Part MultipartBody.Part fileOther);

    @Multipart
    @POST("crm-api/backuplive/api/b-crm/Cancellation/submitCancellationRequest")
    Call<BasicResponse> raiseCancellation(@Part("login_id") RequestBody userId,
                                          @Part("login_type") RequestBody userType,
                                          @Part("SrId") RequestBody srId,
                                          @Part("srNo") RequestBody srNo,
                                          @Part("remarks") RequestBody remarks,
                                          @Part("Device_Type") RequestBody source,
                                          @Part MultipartBody.Part fileAltPolicy,
                                          @Part MultipartBody.Part fileCustomLetter,
                                          @Part MultipartBody.Part fileCancelCheque);

    @Multipart
    @POST("OfflineQuote/AddOfflineQuote")
    Call<BasicResponse> requestOffLineQuote(@Part("QuotationId") RequestBody quoteId,
                                            @Part("OfflineQuoteRaiseTicket") RequestBody offline,
                                            @Part("OfflineQuoteRemark") RequestBody remark,
                                            @Part("Source") RequestBody source,
                                            @Part MultipartBody.Part fileInvoiceImage,
                                            @Part MultipartBody.Part fileRcFront,
                                            @Part MultipartBody.Part fileRcBack,
                                            @Part MultipartBody.Part filePreviousPolicyPdf,
                                            @Part MultipartBody.Part fileOtherDocument);

    @FormUrlEncoded
    @POST("crm-api/backuplive/api/Claim/AddClaim")
    Call<CreateClaim> raiseClaimRequest(@Field("Source") String source,
                                        @Field("User_Id") String userId,
                                        @Field("User_Type") String userType,
                                        @Field("Quotation_Id") String qId,
                                        @Field("Company_Name") String company,
                                        @Field("Intimated_To_Insurer") String intimateInsurer,
                                        @Field("Claim_Intimated_By") String intimatedBy,
                                        @Field("Intimation_Time") String intimatedTime,
                                        @Field("Intimation_Date") String intimatedDate,
                                        @Field("Claim_Intimation_No") String intimationNo,
                                        @Field("Intimator_Name") String intimatorName,
                                        @Field("Intimator_Contact_No") String intimatorContact,
                                        @Field("Alternate_No") String intimatorAltContact,
                                        @Field("WhatsApp_No") String intimatorWhatsApp,
                                        @Field("Mail_Id") String intimatorMail,
                                        @Field("Reason_Delay_Intimation") String intDelayReason,
                                        @Field("LossType") String lossType,
                                        @Field("CauseOfLossType") String causeOfLossType,
                                        @Field("Date_Lose") String dateLose,
                                        @Field("Time_Lose") String timeLose,
                                        @Field("Estimated_Amount") String estimatedAmount,
                                        @Field("Accident_Garage_Name") String accidentGarageName,
                                        @Field("Accident_Garage_Near_LandMark") String accidentLandMark,
                                        @Field("Garage_Pincode") String garagePincode,
                                        @Field("Fir_Status") String firStatus,
                                        @Field("Fir_Remarks") String firRemarks,
                                        @Field("Tp_loss_Status") String tpLossStatus,
                                        @Field("Tp_loss_Remarks") String tpLossRemarks,
                                        @Field("Driver_Name") String driverName,
                                        @Field("Driver_Contact_No") String driverContactNo,
                                        @Field("Driver_DL_No") String driverDLNo,
                                        @Field("Spot_Survey_Status") String spotSurveyStatus,
                                        @Field("Spot_Survey_Date") String spotSurveyDate,
                                        @Field("Spot_Survey_Time") String spotSurveyTime,
                                        @Field("Spot_Surveyor_Name") String spotSurveyorName,
                                        @Field("Spot_Surveyor_Mobile") String spotSurveyorMobile,
                                        @Field("Spot_Surveyor_location") String spotSurveyorLocation,
                                        @Field("Spot_Pincode") String spotPincode,
                                        @Field("Survey_Status") String surveyStatus,
                                        @Field("Survey_Date") String surveyDate,
                                        @Field("Survey_Time") String surveyTime,
                                        @Field("Surveyor_Name") String surveyorName,
                                        @Field("Surveyor_Mobile") String surveyorMobile,
                                        @Field("Survey_Mail_Id") String surveyMail,
                                        @Field("Survey_Type") String surveyType,
                                        @Field("Registration_Code") String regNo,
                                        @Field("customermobileno") String customerPhone,
                                        @Field("customeremail") String customerEmail);

    @GET("crm-api/backuplive/api/Ticket/TicketTypes")
    Call<TicketList> getTicketTypes();

    @GET("crm-api/backuplive/api/b-crm/Universal/searchProducts")
    Call<VehicleData> getProducts();

    @GET("crm-api/backuplive/api/b-crm/Universal/searchInsurer")
    Call<VehicleData> getInsurer();

    @GET("crm-api/backuplive/api/b-crm/Survey/fetchSurveryRequestsData")
    Call<SurveyList> getCrmInspection(@Query("User_Id") String userId,
                                      @Query("User_Type") String userType,
                                      @Query("url") String url,
                                      @Query("Device_Type") String source);

    @GET("crm-api/backuplive/api/myaccount/PolicyDataFetch")
    Call<DetailedPolicyList> getDetailedPolicy(@Query("User_Id") String userId,
                                               @Query("User_Type") String userType,
                                               @Query("url") String url,
                                               @Query("Type") String Type,
                                               @Query("Device_Type") String source);

    @GET("crm-api/backuplive/api/b-crm/Filter/commonfilterFieldsData")
    Call<CrmMaster> getCommonFilters(@Query("User_Id") String userId,
                                     @Query("User_Type") String userType,
                                     @Query("portal") String url,
                                     @Query("Device_Type") String source);

    @FormUrlEncoded
    @POST("crm-api/backuplive/api/reports/Statement/AgentEarningStatementGridDataApp")
    Call<StatementList> getStatementList(@Field("User_Id") String userId,
                                         @Field("User_Type") String userType,
                                         @Field("User_Code") String userCode,
                                         @Field("Action") String action,
                                         @Field("Year") String year,
                                         @Field("From_Month") String fromMonth,
                                         @Field("To_Month") String toMonth,
                                         @Field("Device_Type") String source);

    @GET("crm-api/backuplive/api/b-crm/Universal/searchEmployee")
    Call<VehicleData> getSurveyEmployee(@Query("loginId") String userId,
                                        @Query("loginType") String userType,
                                        @Query("mainOption") String mainOption,
                                        @Query("subOption") String subOption,
                                        @Query("Device_Type") String source);

    @GET("crm-api/backuplive/api/b-crm/Universal/searchAgents")
    Call<VehicleData> getSurveyPartner(@Query("employee") String employeeId,
                                       @Query("employeeName") String employeeName,
                                       @Query("deviceType") String source);

    @Multipart
    @POST("crm-api/backuplive/api/b-crm/Survey/createSurvey")
    Call<BasicResponse> raiseInspection(@Part("loginId") RequestBody userId,
                                        @Part("loginType") RequestBody userType,
                                        @Part("quoteId") RequestBody quoteId,
                                        @Part("endorsementId") RequestBody endorsementId,
                                        @Part("customerName") RequestBody customerName,
                                        @Part("customerEmail") RequestBody customerEmail,
                                        @Part("customerEmailAlt") RequestBody customerEmailAlt,
                                        @Part("customerMobile") RequestBody customerMobile,
                                        @Part("customerMobileAlt") RequestBody customerMobileAlt,
                                        @Part("customerAddress") RequestBody customerAddress,
                                        @Part("vehicleLocation") RequestBody vehicleLocation,
                                        @Part("pincode") RequestBody pincode,
                                        @Part("state") RequestBody state,
                                        @Part("city") RequestBody city,
                                        @Part("employeeId") RequestBody employeeId,
                                        @Part("employeeName") RequestBody employeeName,
                                        @Part("employeeMobile") RequestBody employeeMobile,
                                        @Part("agentId") RequestBody agentId,
                                        @Part("agentName") RequestBody agentName,
                                        @Part("agentMobile") RequestBody agentMobile,
                                        @Part("product") RequestBody product,
                                        @Part("inspectionMode") RequestBody inspectionMode,
                                        @Part("registrationNo") RequestBody registrationNo,
                                        @Part("rto") RequestBody rto,
                                        @Part("make") RequestBody make,
                                        @Part("model") RequestBody model,
                                        @Part("variant") RequestBody variant,
                                        @Part("insurerName") RequestBody insurerName,
                                        @Part("engineNo") RequestBody engineNo,
                                        @Part("chassisNo") RequestBody chassisNo,
                                        @Part("remarks") RequestBody remarks,
                                        @Part("Device_Type") RequestBody source,
                                        @Part MultipartBody.Part fileRcFront,
                                        @Part MultipartBody.Part fileRcBack,
                                        @Part MultipartBody.Part fileQuotationDoc);

    @GET("crm-api/backuplive/api/myaccount/GirdData")
    Call<RenewalList> getRenewalList(@Query("User_Id") String userId,
                                     @Query("User_Type") String userType,
                                     @Query("PageType") String pageType,
                                     @Query("Device_Type") String source);

    @FormUrlEncoded
    @POST("crm-api/backuplive/api/Myaccount/RenewalQuoteMotor3/quick/user/quote")
    Call<CreateRenewal> createRenewal(@Field("login_id") String userId,
                                      @Field("login_type") String UserType,
                                      @Field("Quotation") String quotationId,
                                      @Field("RegNo") String regNo,
                                      @Field("File_Type") String fileType,
                                      @Field("Device_Type") String source);

    @FormUrlEncoded
    @POST("crm-api/backuplive/api/Myaccount/RenewalQuoteMotor3/quick/user/review")
    Call<CreateRenewal> quickRenewal(@Field("login_id") String userId,
                                     @Field("login_type") String UserType,
                                     @Field("Quotation") String quotationId,
                                     @Field("RegNo") String regNo,
                                     @Field("File_Type") String fileType,
                                     @Field("Device_Type") String source);

    @FormUrlEncoded
    @POST("crm-api/backuplive/api/Myaccount/ChangeStatus")
    Call<BasicBoolResponse> renewalAction(@Field("User_Id") String userId,
                                          @Field("User_Type") String UserType,
                                          @Field("SrTableId") String srTableId,
                                          @Field("Status") String actionStatus,
                                          @Field("dates") String dates,
                                          @Field("times") String time,
                                          @Field("remark") String remark,
                                          @Field("Device_Type") String source);

    @GET("crm-api/backuplive/api/WebsiteSection/ViewDataPoster")
    Call<SharePoster> getPosters(@Query("Device_Type") String source);

    @FormUrlEncoded
    @POST("crm-api/backuplive/api/Auth/ShareUrl")
    Call<LeadUrl> getShareUrl(@Field("User_Id") String userId,
                              @Field("UserCode") String userCode,
                              @Field("UserType") String userType);

    @FormUrlEncoded
    @POST("crm-api/backuplive/api/auth/LoginSendOtp")
    Call<SendOtp> getLoginOtp(@Field("username") String userId,
                              @Field("ButtonValue") String usertype,
                              @Field("type") String type,
                              @Field("Device_Type") String source);


    @FormUrlEncoded
    @POST("api_master/GetCountry")
    Call<CountryList> getCountry(@Field("token") String token);

    @FormUrlEncoded
    @POST("prequotes_travel/showProposal")
    Call<TravelQuote> getTravelQuoteIdFamily(@Field("role_id") String userId,
                                             @Field("role_type") String userType,
                                             @Field("name") String name,
                                             @Field("mobile") String mobile,
                                             @Field("email") String email,
                                             @Field("plan_type") String planType,
                                             @Field("country") String countryId,
                                             @Field("start_date") String startDate,
                                             @Field("end_date") String endDate,
                                             @Field("medicalCondition") String medicalCondition,
                                             @Field("qid") String qid,
                                             @Field("sum_insured") String sumInsured,
                                             @Field("self_age") String selfAge,
                                             @Field("spouse_age") String spouseAge,
                                             @Field("chil1_age") String child1Age,
                                             @Field("chil2_age") String child2Age,
                                             @Field("chil3_age") String child3Age,
                                             @Field("father_age") String fatherAge,
                                             @Field("mother_age") String motherAge,
                                             @Field("form_name") String formName,
                                             @Field("source") String source);

    @FormUrlEncoded
    @POST("prequotes_travel/showProposal")
    Call<TravelQuote> getTravelQuoteIdGroup(@Field("role_id") String userId,
                                            @Field("role_type") String userType,
                                            @Field("name") String name,
                                            @Field("mobile") String mobile,
                                            @Field("email") String email,
                                            @Field("plan_type") String planType,
                                            @Field("country") String countryId,
                                            @Field("start_date") String startDate,
                                            @Field("end_date") String endDate,
                                            @Field("medicalCondition") String medicalCondition,
                                            @Field("qid") String qid,
                                            @Field("sum_insured") String sumInsured,
                                            @Field("member1_age") String member1Age,
                                            @Field("member2_age") String member2Age,
                                            @Field("member3_age") String member3Age,
                                            @Field("member4_age") String member4Age,
                                            @Field("member5_age") String member5Age,
                                            @Field("form_name") String formName,
                                            @Field("source") String source);

    @FormUrlEncoded
    @POST("prequotes_travel/showProposal")
    Call<TravelQuote> getTravelQuoteIdIndividual(@Field("role_id") String userId,
                                                 @Field("role_type") String userType,
                                                 @Field("name") String name,
                                                 @Field("mobile") String mobile,
                                                 @Field("email") String email,
                                                 @Field("plan_type") String planType,
                                                 @Field("country") String countryId,
                                                 @Field("start_date") String startDate,
                                                 @Field("end_date") String endDate,
                                                 @Field("medicalCondition") String medicalCondition,
                                                 @Field("qid") String qid,
                                                 @Field("sum_insured") String sumInsured,
                                                 @Field("self_age3") String selfAge,
                                                 @Field("form_name") String formName,
                                                 @Field("source") String source);

    @FormUrlEncoded
    @POST("prequotes_travel/show_proposal_travel")
    Call<TravelPremium> getTravelPremium(@Field("q_id") String quoteId,
                                         @Field("p_id") String planId,
                                         @Field("source") String source);

    @FormUrlEncoded
    @POST("prequotes_travel/updateClickBuyCompanyDetails")
    Call<BasicBoolResponse> updateTravelProposal(@Field("company") String company,
                                                 @Field("geography") String geography,
                                                 @Field("premium") String premium,
                                                 @Field("pid") String pid,
                                                 @Field("q_id") String qId,
                                                 @Field("source") String source);

}
