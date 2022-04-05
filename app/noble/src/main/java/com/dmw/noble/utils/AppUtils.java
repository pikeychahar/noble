package com.dmw.noble.utils;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.dmw.noble.R;
import com.dmw.noble.activity.DashboardActivity;
import com.dmw.noble.activity.MainActivity;
import com.dmw.noble.training.TrainingActivity;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Prahalad Kumar Chahar
 */
public class AppUtils {

    public static final String TOKEN = "square123123";
    public static final String LOGIN_TOKEN = "login token";
    public static final String HL_TOKEN = "202cb962ac59075b964b07152d234b70";

    public static final String IS_FIRST_TIME = "is_first_time";
    public static final int REQUEST_PERMISSION_STORAGE = 1;
    public static final String MOBILE = "mobile";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final int REQUEST_CODE_GALLERY = 1001;
    public static final int REQUEST_CODE_CAMERA = 1002;
    public static final String AGENT_ID = "agent";
    public static final String VEHICLE_TYPE = "vehicle";
    public static final String REGISTRATION_YEAR = "regYear";
    public static final String PRIMARY_ID = "user_id";
    public static final String REGISTRATION_NUMBER = "registration_number";
    public static final String IS_AGENT = "agentScreen";
    public static final String AGENT_MOBILE = "agent_mobile";
    public static final String AGENT_NAME = "agent_name";
    public static final String AGENT_EMAIL = "agent_email";
    public static final String AGENT_DETAIL = "agent_detail";
    public static final String SAOD_INSURER = "previousInsurer";
    public static final String SAOD_TP_EXP_DATE = "tpPolicyExpDate";
    public static final String SAOD_POLICY_NO = "previousPolicyNo";
    public static final String MAKE = "make";
    public static final String MODEL = "model";
    public static final String VARIANT = "variant";
    public static final String INSURER = "insurer";
    public static final String POLICY_TYPE = "policy type";
    public static final String POLICY_EXPIRY = "policy expire";
    public static final String QUOTATION_ID = "quotation id";
    public static final String NEW_VEHICLE = "new_gadi";
    public static final String INSERT_ID = "insert id";
    public static final String RTO_CODE = "rto code";
    public static final String ACKO_PAYMENT = "acko payment";
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    public static final SimpleDateFormat YMD_HMS_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    public static final DateFormat DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ", Locale.US);
    public static final String PH_MOBILE = "policy holder mobile";
    public static final String OTP = "otp";
    public static final String OWNED_BY = "owned by";
    public static final String RUPEE = "₹ ";
    public static final String ZERO_DEPT = "zero dept";
    public static final String OWNER_CHANGE = "owner change";
    public static final String CLAIM_EXP = "claim exp";
    public static final String PRE_NCB = "previous NCB";
    public static final String POLICY_EXPIRY_DATE = "policy expire date";
    public static final String COMPANY_NAME = "company name";
    public static final String OWNER_NAME = "owner name";
    public static final String OWNER_ADDRESS = "owner address";
    public static final String OWNER_EMAIL = "owner email";
    public static final String OWNER_PHONE = "owner phone";
    public static final String NOMINEE_NAME = "nominee name";
    public static final String NOMINEE_RELATION = "relation";
    public static final int REQUEST_CODE_FILES = 1211;
    public static final String IS_PREVIOUS = "do not know";
    public static final String REG_YEAR = "reg year";
    public static final String PA_COVER = "pa cover ";
    public static final String COVER = "cover";
    public static final String MAX_IDV = "max idv";
    public static final String MIN_IDV = "min idv";
    public static final String IS_FILTER = "is filter";
    public static final String POLICY_EXPIRED = "expired";
    public static final String FUEL_TYPE = "fuel type";
    public static final String IS_NCB = "ncb available";
    public static final String PAYMENT_NAME = "payment name";
    public static final String PAYMENT_EMAIL = "payment email";
    public static final String PAYMENT_TIME = "time";
    public static final String PAYMENT_STATUS = "status";
    public static final String POLICY_URL = "url";
    public static final String RESPONSE_URL = "response url";
    public static final String UPDATED_IDV = "updated idv";
    public static final String HL_Name = "name";
    public static final String HL_PHONE = "phone";
    public static final String HL_EMAIL = "email";
    public static final String HL_GENDER = "GENDER";
    public static final String PINCODE = "health code";
    public static final String PLAN_TYPE = "health plan type";
    public static final String SUM_ASSURED = "health Sum Assured";
    public static final String QUOTATION_ID_1 = "Health Quotation Id1";
    public static final String HEALTH_PLAN = "plan type";
    public static final String INDIVIDUAL_NAME = "individualName";
    public static final String HEALTH_LINK = "Health Payment Link";
    public static final String FL_SELF_NAME = "floater self";
    public static final String FL_SPOUSE_NAME = "floater spouse";
    public static final String FL_FATHER_NAME = "floater father";
    public static final String FL_MOTHER_NAME = "floater mother";
    public static final String STAR = "*";
    public static final String PURCHASE_DATE = "purchaseDate";
    public static final String OLD_QUOTE = "old quote";
    public static final String TP_ONLY = "tp only";
    public static final String BLOG_URL = "blog url";
    public static final String IS_INSPECTION = "from Inspection";
    public static final String CONTROL_ID = "control id";
    public static final String SP_ID = "service provider";
    public static final String IS_AGREE = "isAgreed";
    public static final String IS_AGREEMENT_POS = "isAgreed pos";
    public static final String POS_STATUS = "pos status";
    public static final String USER_TYPE = "user type";
    public static final String USER_PASS = "password";
    public static final String IMG_PATH = "imgPath";
    public static final String TOTAL_PREMIUM = "final premium";
    public static final String MEMBER_ARRAY = "member";
    public static final String HL_CITY = "health City";
    public static final String HEALTH_PLAN_NAME = "health plan name";

    public static final String HEALTH_INSURER_IMG_PATH = "health insurer img path";
    public static final String HEALTH_TOTAL_PREMIUM = "health total premium";
    public static final String HL_TENURE = "plan tenure";
    public static final String HL_COMPANY = "health company";
    public static final String IS_SELF = "isSelf";
    public static final String IS_SPOUSE = "isSpouse";
    public static final String IS_FATHER = "isFather";
    public static final String IS_MOTHER = "isMother";
    public static final String TOTAL_SON = "totalSon";
    public static final String TOTAL_DAUGHTER = "totalDaughter";
    public static final String HL_PROPOSER_FIRST_NAME = "proposer first Name";
    public static final String HL_TPA = "proposer tpa";
    public static final String HL_PROPOSER_LAST_NAME = "proposer last name";
    public static final String HL_PROPOSER_DOB = "proposer dob";
    public static final String HL_PROPOSER_GENDER = "proposerGender";
    public static final String HL_PROPOSER_MARITAL = "proposer marital status";
    public static final String HL_PROPOSER_EMAIL = "proposer email";
    public static final String HL_PROPOSER_PHONE = "proposer phone";
    public static final String HL_PROPOSER_QUALIFICATION = "proposer Qualification";
    public static final String HL_PROPOSER_DOC_TYPE = "proposer doc type";
    public static final String HL_PROPOSER_DOC_NO = "proposer doc no";
    public static final String HL_PROPOSER_DOC_OCCUPATION = "proposer occupation";
    public static final String HL_PROPOSER_DESIGNATION = "proposer designation";
    public static final String HL_PROPOSER_CITY = "proposer city";
    public static final String HL_PROPOSER_PIN = "proposer pincode";
    public static final String HL_PROPOSER_ADDRESS1 = "proposer address1";
    public static final String HL_PROPOSER_ADDRESS2 = "proposer address2";
    public static final String HL_PROPOSER_ADDRESS3 = "proposer address3";
    public static final String HL_INSURED_FNAME = "insured first name";
    public static final String HL_INSURED_LNAME = "insured last name";
    public static final String HL_INSURED_DOB = "insured dob";
    public static final String HL_INSURED_GENDER = "insured gender";
    public static final String HL_INSURED_HEIGHT = "insured height";
    public static final String HL_INSURED_WEIGHT = "insured weight";
    public static final String HL_INSURED_OCCUPATION = "insured occupation";
    public static final String HL_INSURED_DESIGNATION = "insured Designation";
    public static final String HL_INSURED_QUE1 = "insured question 1";
    public static final String HL_INSURED_QUE2 = "insured question 2";
    public static final String HL_INSURED_QUE3 = "insured question 3";
    public static final String HL_INSURED_QUE4 = "insured question 4";
    public static final String HL_INSURED_QUE5 = "insured question 5";
    public static final String HL_INSURED_QUE6 = "insured question 6";
    public static final String HL_INSURED_ALCOHOL = "insured Alcohol";
    public static final String HL_INSURED_SMOKE = "insured Smoke";
    public static final String HL_INSURED_TOBACCO = "insured tobacco";
    public static final String HL_INSURED_NARCOTICS = "insured narcotics";
    public static final String HL_INSURED_SUBSTANCE = "insured substance";
    public static final String NOMINEE_MOBILE = "nominee mobile";
    public static final String HEALTH_NET_PREMIUM = "net premium";
    public static final String HEALTH_GST_PREMIUM = "service tax";
    public static final String HL_MED_MONTH = "medical month";
    public static final String HL_MED_YEAR = "med year";
    public static final String HL_ILLNESS = "illness";
    public static final String HL_TREAT = "treatment";
    public static final String HL_OUTCOME = "outcome";
    public static final String HL_AL_Q = "al qun";
    public static final String HL_AL_Y = "al year";
    public static final String HL_SM_Q = "sm qun";
    public static final String HL_SM_Y = "sm year";
    public static final String HL_TB_Q = "tb qun";
    public static final String HL_TB_Y = "tb year";
    public static final String HL_NAR_Q = "nar qun";
    public static final String HL_NAR_Y = "nar year";
    public static final String HL_OS_Q = "os qun";
    public static final String HL_OS_Y = "os year";

    public static final String HL_AP_REL = "apollo relation";
    public static final String HL_AP_SAL = "apollo salutation";
    public static final String HL_AP_CIG = "apollo cigarette";
    public static final String HL_AP_POUCH = "apollo pouch";
    public static final String HL_AP_LIQ = "apollo liq";
    public static final String HL_AP_BEER = "apollo beer";
    public static final String HL_AP_WINE = "apollo wine";
    public static final String HL_AP_ANY = "apollo any d";
    public static final String CAL_SELF = "self cal";
    public static final String CAL_SPOUSE = "spouse cal";
    public static final String CAL_FATHER = "father cal";
    public static final String CAL_MOTHER = "mother cal";
    public static final String CAL_SON1 = "son1 cal";
    public static final String CAL_SON2 = "son2 cal";
    public static final String CAL_DAUGHTER1 = "daughter1 cal";
    public static final String CAL_DAUGHTER2 = "daughter2 cal";
    public static final String HL_PROPOSER_TITLE = "proposer title";
    public static final String HL_INSURED_MARITAL = "marital status";
    public static final String HL_CITY_ID = "city id";
    public static final String HL_BAJAJ_EXTRA = "bajaj ex";
    public static final String HL_BAJAJ_TB = "bajaj tb";
    public static final String HL_BAJAJ_TABLE = "bajaj table";
    public static final String HL_BAJAJ_TV = "bajaj table value";
    public static final String HL_BAJAJ_PHONE = "bajaj phone";
    public static final String HL_BAJAJ_EMAIL = "bajaj email";
    public static final String HL_BAJAJ_NF = "bajaj nf";
    public static final String HL_BAJAJ_NL = "bajaj nl";
    public static final String HL_BAJAJ_NR = "bajaj nr";
    public static final String HL_BAJAJ_A1 = "bajaj address1";
    public static final String HL_BAJAJ_A2 = "bajaj address2";
    public static final String HL_BAJAJ_A3 = "bajaj address3";
    public static final String PCV_COMPANY = "PCv company";
    public static final String REFERENCE_ID = "pos reference id";
    public static final String POS_PERMISSION = "pos preference id";

    public static final String HL_SBI_Q1 = "sbi q1";
    public static final String HL_SBI_Q2 = "sbi q2";
    public static final String HL_SBI_Q3 = "sbi q3";
    public static final String HL_SBI_Q4 = "sbi q3";
    public static final String HL_SBI_EMAIL = "sbi email";
    public static final String HL_SBI_PHONE = "sbi phone";
    public static final String HL_SBI_PLOT = "sbi polt";
    public static final String HL_SBI_BUILDING = "sbi building";
    public static final String HL_SBI_STREET = "sbi street";
    public static final String HL_SBI_ADDRESS1 = "sbi address1";
    public static final String HL_SBI_PINCODE = "sbi pincode";
    public static final String HL_SBI_DOC = "sbi doc";
    public static final String HL_SBI_DOC_NO = "sbi doc no";
    public static final String PCV_TYPE = "pcv type";
    public static final String IS_PA_COVER = "isPaCover";
    public static final String EMP_ID = "employee id";
    public static final String HL_INSURED_TITLE = "health title id";
    public static final String HL_INSURED_RELATION = "health Relation id";
    public static final String HL_INSURED_NAME = "insured full name";
    public static final String HL_INSURED_GENDER_VALUE = "insured full gender";
    public static final String HL_INSURED_DOB_VALUE = "insured full dob";
    public static final String ICICI_EXIST = "icici exist";
    public static final String IFFCO_Q1 = "iffco q1";
    public static final String IFFCO_Q2 = "iffco q2";
    public static final String IFFCO_Q3 = "iffco q3";
    public static final String RTO_NAME = "rto name";
    public static final String PINCODE_PIN = "health pin code pin";
    public static final String HL_FT_PLAN = "health feature plan";
    public static final String HL_FT_SUB_PLAN = "health sub feature plan";
    public static final String HL_FT_N = "feature number";
    public static final String BK_F1 = "Bike first time";
    public static final String SBI_GCV_CODE = "sbi gcv code";
    public static final String QUERY_TYPE = "query_lead_type";
    public static final String CLAIM_NO = "Claim Number";
    public static final String CLAIM_MANAGER = "Claim Manager";
    public static final String CHAT_TYPE = "chat type";
    public static final String LIFE_QUOTE_LINK = "https://www.squareinsurance.in/prequotes_life/life_quote?quote=";
    public static final String LF_SAL_ID = "life sal id";
    public static final String LF_STATE = "life state";
    public static final String LF_CITY = "life city";
    public static final String LF_ADDRESS1 = "life address 1";
    public static final String LF_ADDRESS2 = "life address 2";
    public static final String LF_ADDRESS3 = "life address 2";
    public static final String ANNUAL_INCOME = "life annual income";
    public static final String LF_OCC_ID = "occupation id";
    public static final String LF_EDU_ID = "life edu id";
    public static final String LF_MARITAL = "life marital id";
    public static final String LF_GENDER = "life gender id";
    public static final String LF_BUSINESS = "life business id";
    public static final String LF_EXISTING = "life existing";
    public static final String LF_EMPLOYER_NAME = "life emp name";
    public static final String LF_EMPLOYER_ADDRESS = "life emp address";
    public static final String LF_SECTOR = "life sector id";
    public static final String LF_SPOUSE = "life spouse";
    public static final String SUMINSURED = "life sum insured";
    public static final String TOBACCO_USER = "Tobacco User";
    public static final String AGE_YEAR = "age year";
    public static final String CLAIM_ID = "claim id";
    public static final String USER_BADGE = "user badge";
    public static final String BADGE_COLOR = "badge Color";
    public static final String MOBILE_PERMISSION = "POS Mobile Permission";
    public static final String TITLE = "Titles";
    public static final String AUTHORITY = "com.dmw.noble.provider";
    public static final String HL_APP_KEY = "health app key";
    public static final String TRAVEL_QUOTE_LINK = "https://www.squareinsurance.in/prequotes/travel-insurance/";
    public static final String TRAVEL_HEALTH = "health or travel";
    public static final String PROVIDER = "com.dmw.noble.provider";
    public static final String PCV_LIST = "pcv list";
    public static final String GCV_LIST = "gcv list";
    public static String LEAD_URL = "www.squareinsurance.in";

    public static String SHARE_MESSAGE = "Square Insurance Pos, a free app, helps you to " +
            "check best price for your Bike/Car/Travel/Health Insurance, compare many options, " +
            "instant policy issuance, attractive incentive plan and much more." +
            "\nDownload today from: \n";


    public static final int MY_CAMERA_REQUEST_CODE = 100;

    public static String SHARE_LINK = " \nPayment link \n" +
            "https://www.squareinsurance.in/review_pay/index/";
    public static final String A_FIRST_NAME = "agent first Name";
    public static final String A_LAST_NAME = "last name";
    public static final String A_DATE_OF_BIRTH = "agent dob";
    public static final String A_EMAIL = "agent Email";
    public static final String A_MOBILE = "agent mobile";
    public static final String A_ALTER_MOBILE = "alternate mobile";
    public static final String A_PAN = "agent pan";
    public static final String A_AADHAR = "agent aadhar";
    public static final String A_PASSWORD = "agent password";
    public static final String A_CONFIRM_PASSWORD = "confirm password";
    public static final String A_REFERENCE = "agent reference";
    public static final String PRE_LINK = "https://www.squareinsurance.in/quotes/index/";
    public static final String HEALTH_PRE_LINK = "https://www.squareinsurance.in/prequotes_health/health_quote?quote=";
    public static final String PROPOSAL_LINK = "https://www.squareinsurance.in/proposal/";
    public static final String HEALTH_PROPOSAL_LINK = "https://www.squareinsurance.in/health_review/index/?quote=";

    public static final String IDV = "idv";
    public static final String NCB = "ncb";
    public static final String NET_PREMIUM = "net premium";
    public static final String GST = "gst";
    public static final String ADDON_COVER = "add on";
    public static final String ENGINE = "engine number";
    public static final String CHASSIS = "chassis number";
    public static final String MANUFACTURE_DATE = "manufacture date";
    public static final String REGISTRATION_DATE = "registration date";
    public static final String VEHICLE_FULL = "vehicle model";
    public static final String NOMINEE_AGE = "nominee age";
    public static final String PRE_POLICY_TYPE = "previous policy type";
    public static final String POLICY_EXPIRY_DAYS = "policy exp days";
    public static final String OWNER_FIRST = "Owner first name";
    public static final String OWNER_Last = "Owner last name";
    public static final String OWNER_GST = "Owner gst";
    public static final String OWNER_DOB = "Owner dob";
    public static final String OWNER_PAN = "Owner pan";
    public static final String OWNER_PIN = "Owner pin";
    public static final String OWNER_STATE = "Owner state";
    public static final String OWNER_CITY = "Owner city";
    public static final String OWNER_ADDRESS1 = "Owner AD1";
    public static final String OWNER_ADDRESS2 = "Owner AD2";
    public static final String OWNER_ADDRESS3 = "Owner AD3";
    public static final String V_POLICY_NO = "vehicle policy";
    public static final String V_REG_NO = "vehicle reg";
    public static final String NOMINEE_DOB = "Nominee dob";
    public static final String CHASSIS_NO = "chassis no";

    public static final String POLICY_START_DATE = "policy start date";
    public static final String POLICY_END_DATE = "policy end date";

    public static final String TYPE = "type";
    public static final String TERM = "terms";
    public static final String GRIEVANCE = "GRIEVANCE";
    public static final String REFUND = "refund";
    public static final String PRIVACY = "Privacy";
    public static final String PRIVACY_LINK
            = "https://www.squareinsurance.in/Privacypolicy/privacypolicyApp";
    public static final String TERM_LINK
            = "https://www.squareinsurance.in/Privacypolicy/teamsApp";
    public static final String GRIEVANCE_LINK
            = "https://www.squareinsurance.in/Privacypolicy/grievance_redress_policyApp";
    public static final String REFUND_LINK
            = "https://www.squareinsurance.in/Privacypolicy/refundPolicyApp";

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSION_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    public static final String BLANK_REGEX = "( +)";
    public static final String BLANK = " ";

    public static String getTokenQuote() {
        return TOKEN_QUOTE;
    }

    public static void setTokenQuote(String tokenQuote) {
        TOKEN_QUOTE = tokenQuote;
    }

    public static String TOKEN_QUOTE = "";

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        showNetworkAlert(context);
        return false;
    }

    public static void showNetworkAlert(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Network Alert");
        builder.setMessage("Please check your network connection and try again");
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ((Activity) context).finish();
            }
        });
        builder.show();
    }

    public static String capitalize(String str) {
        String[] words = str.trim().split(" ");
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (words[i].trim().length() > 0) {
                ret.append(Character.toUpperCase(words[i].trim().charAt(0)));
                ret.append(words[i].trim().substring(1));
                if (i < words.length - 1) {
                    ret.append(' ');
                }
            }
        }
        return ret.toString();
    }

    public static boolean isValidMobile(String phone) {
        Pattern p = Pattern.compile("(0/91)?[5-9][0-9]{9}");//
        Matcher m = p.matcher(phone);
        return (m.find() && m.group().equals(phone));
    }

    public static boolean validDate(String input) {
        if (input.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})"))
            return true;
        else return input.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})");
    }

    public static boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String addOneYear(String dateString) {

        Date date = null;
        try {
            date = SIMPLE_DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
            calendar.add(Calendar.DATE, -1);
        }
        calendar.add(Calendar.YEAR, 1);

        return SIMPLE_DATE_FORMAT.format(calendar.getTime());

    }

    public static String addYears(String dateString, int tenure) {

        Date date = null;
        try {
            date = SIMPLE_DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
            calendar.add(Calendar.DATE, -1);
        }
        calendar.add(Calendar.YEAR, tenure);

        return SIMPLE_DATE_FORMAT.format(calendar.getTime());

    }

    public static String addOneDay(String dateString, int days) {

        Date date = null;
        try {
            date = SIMPLE_DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        if (date != null) {

            Calendar calendar1 = Calendar.getInstance();
            Date date1 = calendar1.getTime();
            try {
                date1 = SIMPLE_DATE_FORMAT.parse(SIMPLE_DATE_FORMAT.format(date1));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (date.after(date1)) {
                calendar.setTime(date);

                System.out.println("policy expire date is greater");
            }
        }
        calendar.add(Calendar.DATE, days);

        return SIMPLE_DATE_FORMAT.format(calendar.getTime());
    }

    public static String dateFormatted(String dateString) {

        Date date = null;
        try {
            date = DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }

        return SIMPLE_DATE_FORMAT.format(calendar.getTime());
    }

    public static boolean isValidPAN(String pan) {
        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]");

        Matcher matcher = pattern.matcher(pan);
        return !matcher.matches();
    }

    public static int getAge(String dobString) {

        Date date = null;

        try {
            date = SIMPLE_DATE_FORMAT.parse(dobString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date == null) return 0;

        Calendar dob = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        dob.setTime(date);


        if (dob.after(now)) {
            throw new IllegalArgumentException("Can't be born in the future");
        }
        int year1 = now.get(Calendar.YEAR);
        int year2 = dob.get(Calendar.YEAR);
        int age = year1 - year2;
        int month1 = now.get(Calendar.MONTH);
        int month2 = dob.get(Calendar.MONTH);

        if (month2 > month1) {
            age--;
        } else if (month1 == month2) {
            int day1 = now.get(Calendar.DAY_OF_MONTH);
            int day2 = dob.get(Calendar.DAY_OF_MONTH);
            if (day2 > day1) {
                age--;
            }
        }
        return age;
    }

    public static Float convertToFloat(Double doubleValue) {
        return doubleValue == null ? null : doubleValue.floatValue();
    }

    public static String encode(String encodeString) {

        byte[] encrpt = new byte[0];
        try {
            encrpt = encodeString.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(encrpt, Base64.DEFAULT);
    }

    public static String calculateBmi(double height, double weight) {

        double heightInMeters = height * 0.01;
        double bmi = weight / Math.pow(heightInMeters, 2.0);
        if (bmi < 18) { //18.5
            return "Underweight";
        } else if (bmi >= 18 && bmi < 38)
            return "Normal";
//        else if (bmi >= 25 && bmi < 38) {//30
//            return "Overweight";
//        }
        else if (bmi >= 38) //30
            return "Obese";
        return "Invalid BMI";
    }

    public static String round(String val) {

        double value = Double.parseDouble(val);

        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return "" + (double) tmp / factor;
    }

    public static String ddMMYYYY(String date) {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            date = SIMPLE_DATE_FORMAT.format(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String YYYYMMDD(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            date = sdf2.format(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static void authDialog(Context mContext) {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(android.R.string.dialog_alert_title);
        alertDialogBuilder.setIcon(R.drawable.ic_alert);
        alertDialogBuilder.setMessage("Dear Partner You are not authorised to purchase policy.\nComplete Your Training and Online exam,\nStart your training today!!");
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.no, null)
                .setNegativeButton(R.string.start_training,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mContext.startActivity(new Intent(mContext, TrainingActivity.class));
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void userAuthDialog(Context mContext) {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(android.R.string.dialog_alert_title);
        alertDialogBuilder.setIcon(R.drawable.ic_alert);
        alertDialogBuilder.setMessage("User Authentication failed !!");
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.sign_in,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences preferences =
                                        mContext.getSharedPreferences(String.valueOf(R.string.app_name), Context.MODE_PRIVATE);
                                preferences.edit().clear().apply();
                                preferences.edit().putBoolean(AppUtils.IS_FIRST_TIME,
                                        false).apply();

                                mContext.startActivity(new Intent(mContext, MainActivity.class));
                                ((Activity) mContext).finishAffinity();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static boolean validateName(String txt) {
        String regex = "^[a-zA-Z ]+$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txt);
        return matcher.find();

    }

    public static boolean validateAddress(String txt) {
        String regex = "^[a-zA-Z0-9 ]+$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txt);
        return matcher.find();

    }

    public static void inspectionDialog(Context mContext, String msg) {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle("Inspection Request");
        alertDialogBuilder.setIcon(R.drawable.ic_alert);
        alertDialogBuilder.setMessage("Your Inspection request \n" + msg);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.ok, null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void setEditTextMaxLength(EditText editText, int length) {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(length);
        editText.setFilters(FilterArray);
    }

    public static void showToast(Context mContext, String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static String getFileName(Uri uri, Context context) {
        String result = null;
        if (Objects.equals(uri.getScheme(), "content")) {
            try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            assert result != null;
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public static void bounceAnim(TextView txtView, int time) {
        ObjectAnimator animY = ObjectAnimator.ofFloat(txtView, "translationX", -10f, 0f);
        animY.setDuration(time);
        animY.setInterpolator(new BounceInterpolator());
        animY.setRepeatCount(1);
        animY.start();
    }

    public static void commonDialog(Context mContext, String title, String msg) {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setIcon(R.drawable.logo);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    mContext.startActivity(new Intent(mContext, DashboardActivity.class));
                    ((Activity) mContext).finishAffinity();
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void verifyStoragePermission(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSION_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    public static String trimString(EditText editText) {
        String str = editText.getText().toString();

        if (!TextUtils.isEmpty(str))
            return str.replaceAll(BLANK_REGEX, BLANK).trim();
        else return "";
    }

    public static void checkSoftKeyboard(Activity mContext) {
        View view = mContext.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void stopShimmer(ShimmerFrameLayout shimmerFrameLayout) {
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
    }

    public static void startShimmer(ShimmerFrameLayout shimmerFrameLayout) {
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
    }

    public static String yMDH(String dateSample) {
        String dateString = "04-03-2021 20:17:46";

        String oldFormat = "dd-MM-yyyy h:mm a";
        String newFormat = "yyyy-MM-dd HH:mm:ss";

        SimpleDateFormat sdf1 = new SimpleDateFormat(oldFormat);
        SimpleDateFormat sdf2 = new SimpleDateFormat(newFormat);

        try {
            dateString = sdf2.format(Objects.requireNonNull(sdf1.parse(dateSample)));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public static String yMDH10() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 10);
        return YMD_HMS_DATE_FORMAT.format(calendar.getTime());
    }


    public static void basicDialog(Context mContext, String title, String msg, String url) {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.close, (dialog, which) -> {
                });
        Glide.with(mContext)
                .load(url)
                .into(new SimpleTarget<GlideDrawable>(100, 80) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        alertDialogBuilder.setIcon(resource);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
