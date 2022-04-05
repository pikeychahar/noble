package com.square.pos.activity.health;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.Health.HealthManager;
import com.square.pos.model.master.Pincode;
import com.square.pos.model.master.PincodeMaster;
import com.square.pos.model_health.MemberDetail;
import com.square.pos.model_health.v2.Document;
import com.square.pos.model_health.v2.Gender;
import com.square.pos.model_health.v2.HealthMarital;
import com.square.pos.model_health.v2.HealthOccupation;
import com.square.pos.model_health.v2.HealthSalutation;
import com.square.pos.model_health.v2.MaritalStatus;
import com.square.pos.model_health.v2.Occupation;
import com.square.pos.model_health.v2.Relation;
import com.square.pos.model_health.v2.Salutation;
import com.square.pos.utils.AppUtils;
import com.square.pos.utils.String2WithTag;
import com.square.pos.utils.String3WithTag;
import com.square.pos.utils.String4WithTag;
import com.square.pos.utils.String5WithTag;
import com.square.pos.utils.String6WithTag;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Member4DetailActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {

    private String current = "";
    private String ddmmyyyy = "DDMMYYYY";
    private Calendar cal = Calendar.getInstance();
    private int mYear, lYear;
    private List<String3WithTag> relations = new ArrayList<>();
    private List<Relation> relationList = new ArrayList<>();
    private SearchableSpinner spnOccupation, spnRelation;
    private Context mContext;
    private List<String2WithTag> occupations = new ArrayList<>();
    private List<Occupation> occupationsList = new ArrayList<>();
    private EditText edtDOB;
    private Bundle mBundle;
    private Spinner spnGender;
    private String quotationId, planFor;
    EditText edtFName, edtLName, edtInches, edtWeight, edtDes, edtYear,
            edtNameIllness, edtAlQuantity, edtAlYear, edtSmokeQuantity, edtSmokeYear, edtTobQuantity,
            edtTobYear, edtNarQuantity, edtNarYear, edtOsQuantity, edtOsYear;
    public ProgressDialog progressdialog;

    Spinner spnMonths, spnTreatment, spnOutcome, spnSalutation, spnQue1, spnQue2, spnQue3, spnQue4,
            spnQue5, spnMaterial;
    private List<MaritalStatus> maritalList = new ArrayList<>();
    private List<String3WithTag> maritalLists = new ArrayList<>();

    String occupationId, gender, firstName, lastName, dob, height, weight, designation, oName,
            anyHealthProblem, companyName, healthPlanType, alQuantity, alYear, tbQuantity, tbYear,
            smokeQuantity, smokeYear, narQuantity, narYear, osQuantity, osYear, que1, que2, que3,
            que4, que5, que6, alcohol, smoke, tobacco, narcotics, substance, medicalMonth,
            medicalYear, medicalIllness, treatmentStatus, treatOutcome, maritalId, mName;

    TextView txtIndividualName;

    RadioButton rbNoQue1, rbYesQue1, rbNoQue2, rbYesQue2, rbNoQue3, rbYesQue3, rbNoQue4, rbYesQue4,
            rbNoQue5, rbYesQue5, rbNoQue6, rbYesQue6, alYes, alNo, smokeYes, smokeNo, tobYes, tobNo,
            narYes, narNo, osYes, osNo;

    boolean isAlcohol, isNarcotics, isOtherSub, isSmoke, isTobacco, isSpouse, isFather, isMother,
            isQue6, isQue5, isQue4, isQue3, isQue2, isQue1;

    private List<Salutation> salutationList = new ArrayList<>();
    private List<String5WithTag> salutations = new ArrayList<>();

    int totalSon, totalDaughter;
    ArrayList<String> insuredFirstName, insuredLastName, insuredDob, insuredGender, insuredHeight,
            insuredWeight, insuredOccupation, insuredDesignation, insuredQue1, insuredQue2,
            insuredQue3, insuredQue4, insuredQue5, insuredQue6, insuredAlcohol, insuredSmoke,
            insuredTobacco, insuredNarcotics, insuredSubstance, alQuantityArray, alYearArray,
            smQuantityArray, smYearArray, tbQuantityArray, tbYearArray, narQuantityArray,
            narYearArray, osQuantityArray, osYearArray, medMonth, medYear, nameIllness, treatment,
            outCome, salutation, relation, cigarette, pouch, liquor, beer, wine, anyDiseases,
            insuredMarital;

    String apolloQue1, apolloQue2, apolloQue3, apolloQue4, apolloQue5, apolloQue6, apolloSal,
            apolloRelation;

    boolean isBelow;
    private List<Gender> genderList = new ArrayList<>();
    private List<String6WithTag> genders = new ArrayList<>();
    String gName, genderId;

    private List<String4WithTag> sbiPins = new ArrayList<>();
    private List<Pincode> sbiPincodeList = new ArrayList<>();

    //Bajaj
    EditText edtBajajMobile, edtBajajEmail, edtFNomineeName, edtLNomineeName;
    SearchableSpinner spnBajajNominee;
    RadioButton rbB1Yes, rbB1No, rbB2Yes, rbB3Yes, rbB2No, rbB3No;

    String existsBajaj, tobaccoBajaj, tablesBajaj, tableValue, bajajMobile, bajajEmail,
            bajajNomineeFName, bajajNomineeLName, bajajNomineeRelation;
    ArrayList<String> existsList, tobaccoBajajList, tableList, tableValueList, bajajPhone,
            bajajEmailArray, bajajNF, bajajNL, bajajNR;

    //SBI
    EditText edtSbiMobile, edtSbiEmail, edtSbiPlot, edtSbiBuilding, edtSbiStreet, edtSbiAddress1,
            edtDocNo;
    String sbiMobile, sbiEmail, sbiPlot, sbiBuilding, sbiStreet, sbiAddress1, sbiPincode,
            documentId, documentNo;
    SearchableSpinner spnSbiPincode;
    RadioButton rbS1Yes, rbS1No, rbS2Yes, rbS3Yes, rbS2No, rbS3No, rbS4Yes, rbS4No;
    Spinner spnDocType;
    private ArrayList<String> sbiMobileArray = new ArrayList<>();
    private ArrayList<String> sbiEmailArray = new ArrayList<>();
    private ArrayList<String> sbiPlotArray = new ArrayList<>();
    private ArrayList<String> sbiBuildingArray = new ArrayList<>();
    private ArrayList<String> sbiStreetArray = new ArrayList<>();
    private ArrayList<String> sbiAddress1Array = new ArrayList<>();
    private ArrayList<String> sbiPincodeArray = new ArrayList<>();
    private ArrayList<String> sbiDocumentIdArray = new ArrayList<>();
    private ArrayList<String> sbiDocumentNoArray = new ArrayList<>();
    ArrayList<String> sbiTobacco = new ArrayList<>();
    ArrayList<String> sbiAlcohol = new ArrayList<>();
    ArrayList<String> sbiSmoke = new ArrayList<>();
    ArrayList<String> sbiOther = new ArrayList<>();
    private List<Document> documentList = new ArrayList<>();
    private List<String2WithTag> docsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member4_detail);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        mBundle = getIntent().getExtras();

        //Bajaj
        edtBajajMobile = findViewById(R.id.edtBajajMobile);
        edtBajajEmail = findViewById(R.id.edtBajajEmail);
        edtFNomineeName = findViewById(R.id.edtFNomineeName);
        edtLNomineeName = findViewById(R.id.edtLNomineeName);

        spnBajajNominee = findViewById(R.id.spnBajajNominee);

        rbB1Yes = findViewById(R.id.rbB1Yes);
        rbB1No = findViewById(R.id.rbB1No);
        rbB2Yes = findViewById(R.id.rbB2Yes);
        rbB3Yes = findViewById(R.id.rbB3Yes);
        rbB2No = findViewById(R.id.rbB2No);
        rbB3No = findViewById(R.id.rbB3No);
        spnMaterial = findViewById(R.id.spnMaterial);
        spnSalutation = findViewById(R.id.spnSalutation);
        spnRelation = findViewById(R.id.spnRelation);
        spnOccupation = findViewById(R.id.spnOccupation);
        spnGender = findViewById(R.id.spnGender);
        edtDOB = findViewById(R.id.edtDob);
        edtFName = findViewById(R.id.edtFirstName);
        spnMonths = findViewById(R.id.spnMonths);
        spnTreatment = findViewById(R.id.spnTreatment);
        spnOutcome = findViewById(R.id.spnOutcome);
        edtLName = findViewById(R.id.edtLastName);
        edtInches = findViewById(R.id.edtInches);
        edtWeight = findViewById(R.id.edtWeight);
        txtIndividualName = findViewById(R.id.txtIndividualName);
        edtDes = findViewById(R.id.edtDes);
        edtYear = findViewById(R.id.edtYear);
        edtNameIllness = findViewById(R.id.edtNameIllness);
        edtAlQuantity = findViewById(R.id.edtAlQuantity);
        edtAlYear = findViewById(R.id.edtAlYear);
        edtSmokeQuantity = findViewById(R.id.edtSmokeQuantity);
        edtSmokeYear = findViewById(R.id.edtSmokeYear);
        edtTobQuantity = findViewById(R.id.edtTobQuantity);
        edtTobYear = findViewById(R.id.edtTobYear);
        edtNarQuantity = findViewById(R.id.edtNarQuantity);
        edtNarYear = findViewById(R.id.edtNarYear);
        edtOsQuantity = findViewById(R.id.edtOsQuantity);
        edtOsYear = findViewById(R.id.edtOsYear);

        spnQue1 = findViewById(R.id.spnAns1);
        spnQue2 = findViewById(R.id.spnAns2);
        spnQue3 = findViewById(R.id.spnAns3);
        spnQue4 = findViewById(R.id.spnAns4);
        spnQue5 = findViewById(R.id.spnAns5);
        rbNoQue1 = findViewById(R.id.rbNoQue1);
        rbYesQue1 = findViewById(R.id.rbYesQue1);
        rbNoQue2 = findViewById(R.id.rbNoQue2);
        rbYesQue2 = findViewById(R.id.rbYesQue2);
        rbNoQue3 = findViewById(R.id.rbNoQue3);
        rbYesQue3 = findViewById(R.id.rbYesQue3);
        rbNoQue4 = findViewById(R.id.rbNoQue4);
        rbYesQue4 = findViewById(R.id.rbYesQue4);
        rbNoQue5 = findViewById(R.id.rbNoQue5);
        rbYesQue5 = findViewById(R.id.rbYesQue5);
        rbNoQue6 = findViewById(R.id.rbNoQue6);
        rbYesQue6 = findViewById(R.id.rbYesQue6);

        alYes = findViewById(R.id.alYes);
        alNo = findViewById(R.id.alNo);
        smokeYes = findViewById(R.id.smokeYes);
        smokeNo = findViewById(R.id.smokeNo);
        tobYes = findViewById(R.id.tobYes);
        tobNo = findViewById(R.id.tobNo);
        narYes = findViewById(R.id.narYes);
        narNo = findViewById(R.id.narNo);
        osYes = findViewById(R.id.osYes);
        osNo = findViewById(R.id.osNo);

        //SBI
        edtSbiMobile = findViewById(R.id.edtSbiMobile);
        edtSbiEmail = findViewById(R.id.edtSbiEmail);
        edtSbiPlot = findViewById(R.id.edtPlot);
        edtSbiBuilding = findViewById(R.id.edtBuilding);
        edtSbiStreet = findViewById(R.id.edtStreet);
        edtSbiAddress1 = findViewById(R.id.edtAddress1);
        spnSbiPincode = findViewById(R.id.spnSbiPincode);
        spnDocType = findViewById(R.id.spnDocType);
        edtDocNo = findViewById(R.id.edtDocNo);

        rbS1Yes = findViewById(R.id.rbS1Yes);
        rbS1No = findViewById(R.id.rbS1No);
        rbS2Yes = findViewById(R.id.rbS2Yes);
        rbS3Yes = findViewById(R.id.rbS3Yes);
        rbS2No = findViewById(R.id.rbS2No);
        rbS4Yes = findViewById(R.id.rbS4Yes);
        rbS4No = findViewById(R.id.rbS4No);
        rbS3No = findViewById(R.id.rbS3No);

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");

        if (mBundle != null) {
            healthPlanType = mBundle.getString(AppUtils.HEALTH_PLAN);
            quotationId = mBundle.getString(AppUtils.QUOTATION_ID_1);
            companyName = mBundle.getString(AppUtils.HL_COMPANY);
            totalSon = mBundle.getInt(AppUtils.TOTAL_SON);
            totalDaughter = mBundle.getInt(AppUtils.TOTAL_DAUGHTER);

            isSpouse = mBundle.getBoolean(AppUtils.IS_SPOUSE);
            isMother = mBundle.getBoolean(AppUtils.IS_MOTHER);
            isFather = mBundle.getBoolean(AppUtils.IS_FATHER);

            insuredFirstName = mBundle.getStringArrayList(AppUtils.HL_INSURED_FNAME);
            insuredLastName = mBundle.getStringArrayList(AppUtils.HL_INSURED_LNAME);
            insuredDob = mBundle.getStringArrayList(AppUtils.HL_INSURED_DOB);
            insuredGender = mBundle.getStringArrayList(AppUtils.HL_INSURED_GENDER);
            insuredHeight = mBundle.getStringArrayList(AppUtils.HL_INSURED_HEIGHT);
            insuredWeight = mBundle.getStringArrayList(AppUtils.HL_INSURED_WEIGHT);
            insuredOccupation = mBundle.getStringArrayList(AppUtils.HL_INSURED_OCCUPATION);
            insuredDesignation = mBundle.getStringArrayList(AppUtils.HL_INSURED_DESIGNATION);
            insuredQue1 = mBundle.getStringArrayList(AppUtils.HL_INSURED_QUE1);
            insuredQue2 = mBundle.getStringArrayList(AppUtils.HL_INSURED_QUE2);
            insuredQue3 = mBundle.getStringArrayList(AppUtils.HL_INSURED_QUE3);
            insuredQue4 = mBundle.getStringArrayList(AppUtils.HL_INSURED_QUE4);
            insuredQue5 = mBundle.getStringArrayList(AppUtils.HL_INSURED_QUE5);
            insuredQue6 = mBundle.getStringArrayList(AppUtils.HL_INSURED_QUE6);
            insuredAlcohol = mBundle.getStringArrayList(AppUtils.HL_INSURED_ALCOHOL);
            insuredSmoke = mBundle.getStringArrayList(AppUtils.HL_INSURED_SMOKE);
            insuredTobacco = mBundle.getStringArrayList(AppUtils.HL_INSURED_TOBACCO);
            insuredNarcotics = mBundle.getStringArrayList(AppUtils.HL_INSURED_NARCOTICS);
            insuredSubstance = mBundle.getStringArrayList(AppUtils.HL_INSURED_SUBSTANCE);
            insuredMarital = mBundle.getStringArrayList(AppUtils.HL_INSURED_MARITAL);

            alQuantityArray = mBundle.getStringArrayList(AppUtils.HL_AL_Q);
            alYearArray = mBundle.getStringArrayList(AppUtils.HL_AL_Y);
            smQuantityArray = mBundle.getStringArrayList(AppUtils.HL_SM_Q);
            smYearArray = mBundle.getStringArrayList(AppUtils.HL_SM_Y);
            tbQuantityArray = mBundle.getStringArrayList(AppUtils.HL_TB_Q);
            tbYearArray = mBundle.getStringArrayList(AppUtils.HL_TB_Y);
            narQuantityArray = mBundle.getStringArrayList(AppUtils.HL_NAR_Q);
            narYearArray = mBundle.getStringArrayList(AppUtils.HL_NAR_Y);
            osQuantityArray = mBundle.getStringArrayList(AppUtils.HL_OS_Q);
            osYearArray = mBundle.getStringArrayList(AppUtils.HL_OS_Y);
            medMonth = mBundle.getStringArrayList(AppUtils.HL_MED_MONTH);
            medYear = mBundle.getStringArrayList(AppUtils.HL_MED_YEAR);
            nameIllness = mBundle.getStringArrayList(AppUtils.HL_ILLNESS);
            treatment = mBundle.getStringArrayList(AppUtils.HL_TREAT);
            outCome = mBundle.getStringArrayList(AppUtils.HL_OUTCOME);

            salutation = mBundle.getStringArrayList(AppUtils.HL_AP_SAL);
            relation = mBundle.getStringArrayList(AppUtils.HL_AP_REL);
            cigarette = mBundle.getStringArrayList(AppUtils.HL_AP_CIG);
            pouch = mBundle.getStringArrayList(AppUtils.HL_AP_POUCH);
            liquor = mBundle.getStringArrayList(AppUtils.HL_AP_LIQ);
            beer = mBundle.getStringArrayList(AppUtils.HL_AP_BEER);
            wine = mBundle.getStringArrayList(AppUtils.HL_AP_WINE);
            anyDiseases = mBundle.getStringArrayList(AppUtils.HL_AP_ANY);

//          bajaj
            bajajPhone = mBundle.getStringArrayList(AppUtils.HL_BAJAJ_PHONE);
            bajajEmailArray = mBundle.getStringArrayList(AppUtils.HL_BAJAJ_EMAIL);
            bajajNF = mBundle.getStringArrayList(AppUtils.HL_BAJAJ_NF);
            bajajNL = mBundle.getStringArrayList(AppUtils.HL_BAJAJ_NL);
            bajajNR = mBundle.getStringArrayList(AppUtils.HL_BAJAJ_NR);
            existsList = mBundle.getStringArrayList(AppUtils.HL_BAJAJ_EXTRA);
            tobaccoBajajList = mBundle.getStringArrayList(AppUtils.HL_BAJAJ_TB);
            tableList = mBundle.getStringArrayList(AppUtils.HL_BAJAJ_TABLE);
            tableValueList = mBundle.getStringArrayList(AppUtils.HL_BAJAJ_TV);
            //SBI
            sbiMobileArray = mBundle.getStringArrayList(AppUtils.HL_SBI_PHONE);
            sbiEmailArray = mBundle.getStringArrayList(AppUtils.HL_SBI_EMAIL);
            sbiPlotArray = mBundle.getStringArrayList(AppUtils.HL_SBI_PLOT);
            sbiBuildingArray = mBundle.getStringArrayList(AppUtils.HL_SBI_BUILDING);
            sbiStreetArray = mBundle.getStringArrayList(AppUtils.HL_SBI_STREET);
            sbiAddress1Array = mBundle.getStringArrayList(AppUtils.HL_SBI_ADDRESS1);
            sbiPincodeArray = mBundle.getStringArrayList(AppUtils.HL_SBI_PINCODE);
            sbiDocumentIdArray = mBundle.getStringArrayList(AppUtils.HL_SBI_DOC);
            sbiDocumentNoArray = mBundle.getStringArrayList(AppUtils.HL_SBI_DOC_NO);

            sbiTobacco = mBundle.getStringArrayList(AppUtils.HL_SBI_Q1);
            sbiAlcohol = mBundle.getStringArrayList(AppUtils.HL_SBI_Q2);
            sbiSmoke = mBundle.getStringArrayList(AppUtils.HL_SBI_Q3);
            sbiOther = mBundle.getStringArrayList(AppUtils.HL_SBI_Q4);
        }
        txtIndividualName.setText("Insured member detail - Child 2");
        String calDate;
        if (totalSon > 1) {
            calDate = mBundle.getString(AppUtils.CAL_SON2);
            if (!TextUtils.isEmpty(calDate))
                edtDOB.setText(calDate);
        } else if (totalDaughter > 1) {
            calDate = mBundle.getString(AppUtils.CAL_DAUGHTER2);
            if (!TextUtils.isEmpty(calDate))
                edtDOB.setText(calDate);
        } else {
            calDate = mBundle.getString(AppUtils.CAL_DAUGHTER1);
            if (!TextUtils.isEmpty(calDate))
                edtDOB.setText(calDate);
        }

        if (!TextUtils.isEmpty(calDate)) {
            int age = AppUtils.getAge(calDate);
            if (age < 18) {
                findViewById(R.id.textDes).setVisibility(View.GONE);
                isBelow = false;
            } else isBelow = true;
        }

        sbiPincodeList = HealthManager.getInstance().getSbiPincodeList();
        if (sbiPincodeList != null && sbiPincodeList.size() > 0)
            initSbiPincode();
        else getSbiPincode();
        getOccupations();
        getMaritalStatus();
        getSalutation();

        que1 = que2 = que3 = que4 = que5 = que6 = smoke = tobacco = substance = alcohol = narcotics
                = existsBajaj = tobaccoBajaj = tablesBajaj = tableValue = "No";

        relationList = HealthManager.getInstance().getRelationList();
        if (relationList.size() > 0)
            initRelation();
        if (companyName.equalsIgnoreCase("bajaj"))
            initNominee();

        genderList = HealthManager.getInstance().getGenderList();
        if (genderList.size() > 0)
            initGender();
        if (companyName.equalsIgnoreCase("royal")) {
            findViewById(R.id.layoutRoyal).setVisibility(View.VISIBLE);

        } else if (companyName.equalsIgnoreCase("apollo")) {
            findViewById(R.id.layoutApollo).setVisibility(View.VISIBLE);
            findViewById(R.id.lblSalutation).setVisibility(View.VISIBLE);
            findViewById(R.id.spnSalutation).setVisibility(View.VISIBLE);
            findViewById(R.id.textRelation).setVisibility(View.VISIBLE);
            findViewById(R.id.textDes).setVisibility(View.GONE);
        } else if (companyName.equalsIgnoreCase("bajaj")) {
            findViewById(R.id.layoutBajaj).setVisibility(View.VISIBLE);
            findViewById(R.id.textDes).setVisibility(View.GONE);
            findViewById(R.id.spnSalutation).setVisibility(View.VISIBLE);
        } else if (companyName.equalsIgnoreCase("sbi")) {
            documentList = HealthManager.getInstance().getDocList();
            if (documentList != null && documentList.size() > 0)
                initDocument();
            else getDocuments();

            sbiPincodeList = HealthManager.getInstance().getSbiPincodeList();
            if (sbiPincodeList != null && sbiPincodeList.size() > 0)
                initSbiPincode();
            else getSbiPincode();

            findViewById(R.id.layoutSbi).setVisibility(View.VISIBLE);
            findViewById(R.id.textDes).setVisibility(View.VISIBLE);
            findViewById(R.id.spnSalutation).setVisibility(View.VISIBLE);
            findViewById(R.id.layoutSbi).setVisibility(View.VISIBLE);
            findViewById(R.id.textDes).setVisibility(View.VISIBLE);
            findViewById(R.id.spnSalutation).setVisibility(View.VISIBLE);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        mYear = calendar.get(Calendar.YEAR);
        lYear = mYear - 1;

        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1950) ? 1950 : (year > lYear) ? lYear : year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))
                                ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s-%s-%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    edtDOB.setText(current);
                    edtDOB.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        edtDOB.addTextChangedListener(tw);


        rbYesQue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.rlMedOpt).setVisibility(View.VISIBLE);
                que1 = "Yes";
                isQue1 = true;
            }
        });

        rbNoQue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                que1 = "No";
                isQue1 = false;
                if (que6.equals(que5) && que4.equals(que3) && que2.equals(que1) && que1.equals("No"))
                    findViewById(R.id.rlMedOpt).setVisibility(View.GONE);
            }
        });


        rbYesQue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.rlMedOpt).setVisibility(View.VISIBLE);
                que2 = "Yes";
                isQue2 = true;
            }
        });

        rbNoQue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isQue2 = false;
                que2 = "No";
                if (que6.equals(que5) && que4.equals(que3) && que2.equals(que1) && que1.equals("No"))
                    findViewById(R.id.rlMedOpt).setVisibility(View.GONE);
            }
        });


        rbYesQue3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.rlMedOpt).setVisibility(View.VISIBLE);
                que3 = "Yes";
                isQue3 = true;
            }
        });

        rbNoQue3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isQue3 = false;
                que3 = "No";
                if (que6.equals(que5) && que4.equals(que3) && que2.equals(que1) && que1.equals("No"))
                    findViewById(R.id.rlMedOpt).setVisibility(View.GONE);
            }
        });


        rbYesQue4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.rlMedOpt).setVisibility(View.VISIBLE);
                que4 = "Yes";
                isQue4 = true;
            }
        });

        rbNoQue4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                que4 = "No";
                isQue4 = false;
                if (que6.equals(que5) && que4.equals(que3) && que2.equals(que1) && que1.equals("No"))
                    findViewById(R.id.rlMedOpt).setVisibility(View.GONE);
            }
        });


        rbYesQue5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.rlMedOpt).setVisibility(View.VISIBLE);
                que5 = "Yes";
                isQue5 = true;
            }
        });

        rbNoQue5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isQue5 = false;
                que5 = "No";
                if (que6.equals(que5) && que4.equals(que3) && que2.equals(que1) && que1.equals("No"))
                    findViewById(R.id.rlMedOpt).setVisibility(View.GONE);
            }
        });

        rbYesQue6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.rlMedOpt).setVisibility(View.VISIBLE);
                isQue6 = true;
                que6 = "Yes";
            }
        });

        rbNoQue6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.rlMedOpt).setVisibility(View.GONE);
                isQue6 = false;
                que6 = "No";
                if (que6.equals(que5) && que4.equals(que3) && que2.equals(que1) && que1.equals("No"))
                    findViewById(R.id.rlMedOpt).setVisibility(View.GONE);
            }
        });

        //Bajaj
        rbB1Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                existsBajaj = "Yes";
            }
        });

        rbB1No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                existsBajaj = "No";

            }
        });
        rbB2Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tobaccoBajaj = "Yes";
            }
        });

        rbB2No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tobaccoBajaj = "No";

            }
        });
        rbB3Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.rlDiseases).setVisibility(View.VISIBLE);
                tablesBajaj = "Yes";
            }
        });

        rbB3No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.rlDiseases).setVisibility(View.GONE);
                tablesBajaj = "No";

            }
        });


        //Life Style case handle

        //alcohol
        alNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.edtAlQuantity).setVisibility(View.GONE);
                findViewById(R.id.edtAlYear).setVisibility(View.GONE);
                isAlcohol = false;
                alcohol = "No";
            }
        });
        alYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.edtAlQuantity).setVisibility(View.VISIBLE);
                findViewById(R.id.edtAlYear).setVisibility(View.VISIBLE);
                isAlcohol = true;
                alcohol = "Yes";
            }
        });
        //smoking
        smokeNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.edtSmokeQuantity).setVisibility(View.GONE);
                findViewById(R.id.edtSmokeYear).setVisibility(View.GONE);
                isSmoke = false;
                smoke = "No";
            }
        });
        smokeYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.edtSmokeQuantity).setVisibility(View.VISIBLE);
                findViewById(R.id.edtSmokeYear).setVisibility(View.VISIBLE);
                isSmoke = true;
                smoke = "Yes";
            }
        });

        //tobacco
        tobNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.edtTobQuantity).setVisibility(View.GONE);
                findViewById(R.id.edtTobYear).setVisibility(View.GONE);
                isTobacco = false;
                tobacco = "No";
            }
        });
        tobYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.edtTobQuantity).setVisibility(View.VISIBLE);
                findViewById(R.id.edtTobYear).setVisibility(View.VISIBLE);
                isTobacco = true;
                tobacco = "Yes";
            }
        });
        //Narcotics
        narNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.edtNarQuantity).setVisibility(View.GONE);
                findViewById(R.id.edtNarYear).setVisibility(View.GONE);
                isNarcotics = false;
                narcotics = "No";
            }
        });
        narYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.edtNarQuantity).setVisibility(View.VISIBLE);
                findViewById(R.id.edtNarYear).setVisibility(View.VISIBLE);
                isNarcotics = true;
                narcotics = "Yes";
            }
        });

        //other substance
        osNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.edtOsQuantity).setVisibility(View.GONE);
                findViewById(R.id.edtOsYear).setVisibility(View.GONE);
                isOtherSub = false;
                substance = "No";
            }
        });
        osYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.edtOsQuantity).setVisibility(View.VISIBLE);
                findViewById(R.id.edtOsYear).setVisibility(View.VISIBLE);
                isOtherSub = true;
                substance = "Yes";
            }
        });
    }

    private void initOccupations() {
        for (int i = 0; i < occupationsList.size(); i++) {
            String stateName = occupationsList.get(i).getName().trim();
            String stateId = String.valueOf(occupationsList.get(i).getCode());
            occupations.add(new String2WithTag(stateName, stateId));
        }

        ArrayAdapter<String2WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, occupations);
        spnOccupation.setAdapter(adapter);

        spnOccupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                occupationId = (String) stringWithTag.tag;
                oName = stringWithTag.string;
                if (oName.equalsIgnoreCase("student")
                        || oName.equalsIgnoreCase("housewife"))
                    findViewById(R.id.textDes).setVisibility(View.GONE);
                else {
                    if (companyName.equalsIgnoreCase("royal"))
                        findViewById(R.id.textDes).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initSbiPincode() {
        for (int i = 0; i < sbiPincodeList.size(); i++) {
            String stateName = sbiPincodeList.get(i).getName().trim();
            String stateId = sbiPincodeList.get(i).getCode();
            sbiPins.add(new String4WithTag(stateName, stateId));
        }

        ArrayAdapter<String4WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, sbiPins);
        spnSbiPincode.setAdapter(adapter);

        spnSbiPincode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String4WithTag stringWithTag = (String4WithTag) parent.getItemAtPosition(position);
                sbiPincode = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getSbiPincode() {
        if (AppUtils.isOnline(mContext)) {
            try {
                HealthManager.getInstance().getSbiPincode(mContext, quotationId, companyName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getOccupations() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                HealthManager.getInstance().getOccupation(mContext, quotationId, companyName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }

    public void getDocuments() {
        if (AppUtils.isOnline(mContext)) {
            try {
                HealthManager.getInstance().getHealthDocument(mContext, quotationId, companyName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void initDocument() {
        for (int i = 0; i < documentList.size(); i++) {
            String stateName = documentList.get(i).getName().trim();
            String stateId = documentList.get(i).getCode();
            docsList.add(new String2WithTag(stateName, stateId));
        }

        ArrayAdapter<String2WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, docsList);
        spnDocType.setAdapter(adapter);

        spnDocType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                documentId = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void initNominee() {

        for (int i = 0; i < relationList.size(); i++) {
            String stateName = relationList.get(i).getName().trim();
            String stateId = String.valueOf(relationList.get(i).getCode());
            relations.add(new String3WithTag(stateName, stateId));
        }

        ArrayAdapter<String3WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, relations);
        spnBajajNominee.setAdapter(adapter);


        spnBajajNominee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String3WithTag stringWithTag = (String3WithTag) parent.getItemAtPosition(position);
                bajajNomineeRelation = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void initMarital() {
        for (int i = 0; i < maritalList.size(); i++) {
            String stateName = maritalList.get(i).getName().trim();
            String stateId = maritalList.get(i).getCode();
            maritalLists.add(new String3WithTag(stateName, stateId));
        }

        ArrayAdapter<String3WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, maritalLists);
        spnMaterial.setAdapter(adapter);

        spnMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String3WithTag stringWithTag = (String3WithTag) parent.getItemAtPosition(position);
                maritalId = (String) stringWithTag.tag;
                mName = stringWithTag.string;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSalutation() {
        for (int i = 0; i < salutationList.size(); i++) {
            String stateName = salutationList.get(i).getName().trim();
            String stateId = salutationList.get(i).getCode();
            salutations.add(new String5WithTag(stateName, stateId));
        }

        ArrayAdapter<String5WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, salutations);
        spnSalutation.setAdapter(adapter);

        spnSalutation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String5WithTag stringWithTag = (String5WithTag) parent.getItemAtPosition(position);
                apolloSal = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onResponse(Object response) {

        if (response instanceof HealthOccupation) {
            HealthOccupation cResponse = (HealthOccupation) response;
            if (cResponse.getStatus().equalsIgnoreCase("1")) {
                occupationsList.clear();
                occupations.clear();
                occupationsList = cResponse.getOccupation();
                if (occupationsList.size() > 0) {
                    initOccupations();
                }
            }
        }

        if (response instanceof PincodeMaster) {
            PincodeMaster cResponse = (PincodeMaster) response;
            if (cResponse.getStatus().equalsIgnoreCase("1") && cResponse.getPincode() != null) {
                sbiPincodeList.clear();
                sbiPins.clear();
                sbiPincodeList = cResponse.getPincode();
                if (sbiPincodeList.size() > 0) {
                    initSbiPincode();
                }
            }
        }
        if (response instanceof HealthMarital) {
            HealthMarital cResponse = (HealthMarital) response;
            if (cResponse.getStatus().equalsIgnoreCase("1") && cResponse.getMaritalStatus() != null) {
                maritalList.clear();
                maritalLists.clear();
                maritalList = cResponse.getMaritalStatus();
                if (maritalList.size() > 0) {
                    initMarital();
                }
            }
        }
        if (response instanceof HealthSalutation) {
            HealthSalutation cResponse = (HealthSalutation) response;
            if (cResponse.getStatus().equalsIgnoreCase("1")
                    && cResponse.getSalutation() != null) {

                salutationList.clear();
                salutations.clear();
                salutationList = cResponse.getSalutation();
                if (salutationList.size() > 0) {
                    initSalutation();
                }
            }
        }
        if (response instanceof MemberDetail) {
            MemberDetail cResponse = (MemberDetail) response;
            if (cResponse.getSuccess().equalsIgnoreCase("1")) {
                Intent intent = new Intent(mContext, HealthNomineeActivity.class);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        }
        progressdialog.hide();
    }

    public void onNomineeClick(View view) {

        if (anyHealthProblem.equalsIgnoreCase("Yes"))
            Toast.makeText(mContext, "You cannot proceed!!!", Toast.LENGTH_LONG).show();

    }


    public void getSalutation() {
        if (AppUtils.isOnline(mContext)) {
            try {
                HealthManager.getInstance().getSalutation(mContext, quotationId, companyName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void initRelation() {
        for (int i = 0; i < relationList.size(); i++) {
            String stateName = relationList.get(i).getName().trim();
            String stateId = String.valueOf(relationList.get(i).getCode());
            relations.add(new String3WithTag(stateName, stateId));
        }

        ArrayAdapter<String3WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, relations);
        spnRelation.setAdapter(adapter);


        spnRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String3WithTag stringWithTag = (String3WithTag) parent.getItemAtPosition(position);
                apolloRelation = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getMaritalStatus() {
        if (AppUtils.isOnline(mContext)) {
            try {
                HealthManager.getInstance().getMaritalStatus(mContext, quotationId, companyName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void initGender() {
        for (int i = 0; i < genderList.size(); i++) {
            String stateName = genderList.get(i).getName().trim();
            String stateId = genderList.get(i).getCode();
            genders.add(new String6WithTag(stateName, stateId));
        }

        ArrayAdapter<String6WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, genders);
        spnGender.setAdapter(adapter);

        for (int i = 0; i < genderList.size(); i++) {
            String stateName = genderList.get(i).getName().trim();
            if (stateName.equalsIgnoreCase("male")) {
                spnGender.setSelection(i);
                break;
            }
        }
        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String6WithTag stringWithTag = (String6WithTag) parent.getItemAtPosition(position);
                genderId = (String) stringWithTag.tag;
                gName = stringWithTag.string;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onNextClick(View view) {
        if (checkValidation()) {


            if (insuredFirstName.size() >= 4) {
                insuredFirstName.set(3, firstName);
                insuredLastName.set(3, lastName);
                insuredDob.set(3, dob);
                insuredGender.set(3, genderId);
                insuredHeight.set(3, height);
                insuredWeight.set(3, weight);
                insuredOccupation.set(3, occupationId);

                if (companyName.equalsIgnoreCase("royal")) {

                    insuredDesignation.set(3, designation);
                    insuredQue1.set(3, que1);
                    insuredQue2.set(3, que2);
                    insuredQue3.set(3, que3);
                    insuredQue4.set(3, que4);
                    insuredQue5.set(3, que5);
                    insuredQue6.set(3, que6);
                    insuredAlcohol.set(3, alcohol);
                    insuredSmoke.set(3, smoke);
                    insuredTobacco.set(3, tobacco);
                    insuredNarcotics.set(3, narcotics);
                    insuredSubstance.set(3, substance);

                    if (isQue6 || isQue5 || isQue4 || isQue3 || isQue2 || isQue1) {
                        medMonth.set(3, medicalMonth);
                        medYear.set(3, medicalYear);
                        nameIllness.set(3, medicalIllness);
                        treatment.set(3, treatmentStatus);
                        outCome.set(3, treatOutcome);
                    }

                    if (isAlcohol) {
                        alQuantityArray.set(3, alQuantity);
                        alYearArray.set(3, alYear);

                    }
                    if (isSmoke) {
                        smQuantityArray.set(3, smokeQuantity);
                        smYearArray.set(3, smokeYear);
                    }
                    if (isTobacco) {
                        tbQuantityArray.set(3, tbQuantity);
                        tbYearArray.set(3, tbYear);
                    }
                    if (isNarcotics) {
                        narQuantityArray.set(3, narQuantity);
                        narYearArray.set(3, narYear);
                    }

                    if (isOtherSub) {
                        osQuantityArray.set(3, osQuantity);
                        osYearArray.set(3, osYear);
                    }
                } else if (companyName.equalsIgnoreCase("apollo")) {

                    insuredMarital.set(3, maritalId);
                    relation.set(3, apolloRelation);
                    salutation.set(3, apolloSal);
                    cigarette.set(3, apolloQue1);
                    pouch.set(3, apolloQue2);
                    liquor.set(3, apolloQue3);
                    beer.set(3, apolloQue4);
                    wine.set(3, apolloQue5);
                    anyDiseases.set(3, apolloQue6);
                } else if (companyName.equalsIgnoreCase("bajaj")) {
                    salutation.set(3, apolloSal);
                    existsList.set(3, existsBajaj);
                    tobaccoBajajList.set(3, tobaccoBajaj);
                    tableList.set(3, tablesBajaj);
                    tableValueList.set(3, tableValue);
                    bajajPhone.set(3, bajajMobile);
                    bajajEmailArray.set(3, this.bajajEmail);
                    bajajNF.set(3, this.bajajNomineeFName);
                    bajajNL.set(3, this.bajajNomineeLName);
                    bajajNR.set(3, this.bajajNomineeRelation);
                } else if (companyName.equalsIgnoreCase("sbi")) {

                    String sbiQ1, sbiQ2, sbiQ3, sbiQ4;

                    sbiQ1 = sbiQ2 = sbiQ3 = sbiQ4 = "No";

                    if (rbS1Yes.isChecked())
                        sbiQ1 = "Yes";
                    if (rbS2Yes.isChecked())
                        sbiQ2 = "Yes";
                    if (rbS3Yes.isChecked())
                        sbiQ3 = "Yes";
                    if (rbS4Yes.isChecked())
                        sbiQ4 = "Yes";

                    sbiTobacco.set(3, sbiQ1);
                    sbiAlcohol.set(3, sbiQ2);
                    sbiSmoke.set(3, sbiQ3);
                    sbiOther.set(3, sbiQ4);

                    salutation.set(3, apolloSal);
                    insuredMarital.set(3, maritalId);
                    sbiDocumentIdArray.set(3, documentId);
                    sbiDocumentNoArray.set(3, documentNo);

                    sbiEmailArray.set(3, this.sbiEmail);
                    sbiMobileArray.set(3, this.sbiMobile);
                    sbiPlotArray.set(3, this.sbiPlot);
                    sbiBuildingArray.set(3, this.sbiBuilding);
                    sbiStreetArray.set(3, this.sbiStreet);
                    sbiAddress1Array.set(3, this.sbiAddress1);

                    sbiPincodeArray.set(3, this.sbiPincode);
                }
            } else {
                insuredFirstName.add(3, firstName);
                insuredLastName.add(3, lastName);
                insuredDob.add(3, dob);
                insuredGender.add(3, genderId);
                insuredHeight.add(3, height);
                insuredWeight.add(3, weight);
                insuredOccupation.add(3, occupationId);

                if (companyName.equalsIgnoreCase("royal")) {

                    insuredDesignation.add(3, designation);
                    insuredQue1.add(3, que1);
                    insuredQue2.add(3, que2);
                    insuredQue3.add(3, que3);
                    insuredQue4.add(3, que4);
                    insuredQue5.add(3, que5);
                    insuredQue6.add(3, que6);
                    insuredAlcohol.add(3, alcohol);
                    insuredSmoke.add(3, smoke);
                    insuredTobacco.add(3, tobacco);
                    insuredNarcotics.add(3, narcotics);
                    insuredSubstance.add(3, substance);

                    if (isQue6 || isQue5 || isQue4 || isQue3 || isQue2 || isQue1) {
                        medMonth.add(3, medicalMonth);
                        medYear.add(3, medicalYear);
                        nameIllness.add(3, medicalIllness);
                        treatment.add(3, treatmentStatus);
                        outCome.add(3, treatOutcome);
                    }

                    if (isAlcohol) {
                        alQuantityArray.add(3, alQuantity);
                        alYearArray.add(3, alYear);

                    }
                    if (isSmoke) {
                        smQuantityArray.add(3, smokeQuantity);
                        smYearArray.add(3, smokeYear);
                    }
                    if (isTobacco) {
                        tbQuantityArray.add(3, tbQuantity);
                        tbYearArray.add(3, tbYear);
                    }
                    if (isNarcotics) {
                        narQuantityArray.add(3, narQuantity);
                        narYearArray.add(3, narYear);
                    }

                    if (isOtherSub) {
                        osQuantityArray.add(3, osQuantity);
                        osYearArray.add(3, osYear);
                    }
                } else if (companyName.equalsIgnoreCase("apollo")) {

                    insuredMarital.add(3, maritalId);
                    relation.add(3, apolloRelation);
                    salutation.add(3, apolloSal);
                    cigarette.add(3, apolloQue1);
                    pouch.add(3, apolloQue2);
                    liquor.add(3, apolloQue3);
                    beer.add(3, apolloQue4);
                    wine.add(3, apolloQue5);
                    anyDiseases.add(3, apolloQue6);
                } else if (companyName.equalsIgnoreCase("bajaj")) {
                    salutation.add(3, apolloSal);
                    existsList.add(3, existsBajaj);
                    tobaccoBajajList.add(3, tobaccoBajaj);
                    tableList.add(3, tablesBajaj);
                    tableValueList.add(3, tableValue);
                    bajajPhone.add(3, bajajMobile);
                    bajajEmailArray.add(3, this.bajajEmail);
                    bajajNF.add(3, this.bajajNomineeFName);
                    bajajNL.add(3, this.bajajNomineeLName);
                    bajajNR.add(3, this.bajajNomineeRelation);
                } else if (companyName.equalsIgnoreCase("sbi")) {

                    String sbiQ1, sbiQ2, sbiQ3, sbiQ4;

                    sbiQ1 = sbiQ2 = sbiQ3 = sbiQ4 = "No";

                    if (rbS1Yes.isChecked())
                        sbiQ1 = "Yes";
                    if (rbS2Yes.isChecked())
                        sbiQ2 = "Yes";
                    if (rbS3Yes.isChecked())
                        sbiQ3 = "Yes";
                    if (rbS4Yes.isChecked())
                        sbiQ4 = "Yes";

                    sbiTobacco.add(3, sbiQ1);
                    sbiAlcohol.add(3, sbiQ2);
                    sbiSmoke.add(3, sbiQ3);
                    sbiOther.add(3, sbiQ4);

                    salutation.add(3, apolloSal);
                    insuredMarital.add(3, maritalId);
                    sbiDocumentIdArray.add(3, documentId);
                    sbiDocumentNoArray.add(3, documentNo);

                    sbiEmailArray.add(3, this.sbiEmail);
                    sbiMobileArray.add(3, this.sbiMobile);
                    sbiPlotArray.add(3, this.sbiPlot);
                    sbiBuildingArray.add(3, this.sbiBuilding);
                    sbiStreetArray.add(3, this.sbiStreet);
                    sbiAddress1Array.add(3, this.sbiAddress1);

                    sbiPincodeArray.add(3, this.sbiPincode);
                }
            }

            mBundle.putStringArrayList(AppUtils.HL_INSURED_MARITAL, insuredMarital);
            mBundle.putStringArrayList(AppUtils.HL_AP_REL, relation);
            mBundle.putStringArrayList(AppUtils.HL_AP_SAL, salutation);
            mBundle.putStringArrayList(AppUtils.HL_AP_CIG, cigarette);
            mBundle.putStringArrayList(AppUtils.HL_AP_POUCH, pouch);
            mBundle.putStringArrayList(AppUtils.HL_AP_LIQ, liquor);
            mBundle.putStringArrayList(AppUtils.HL_AP_BEER, beer);
            mBundle.putStringArrayList(AppUtils.HL_AP_WINE, wine);
            mBundle.putStringArrayList(AppUtils.HL_AP_ANY, anyDiseases);

            mBundle.putStringArrayList(AppUtils.HL_BAJAJ_EXTRA, existsList);
            mBundle.putStringArrayList(AppUtils.HL_BAJAJ_TB, tobaccoBajajList);
            mBundle.putStringArrayList(AppUtils.HL_BAJAJ_TABLE, tableList);
            mBundle.putStringArrayList(AppUtils.HL_BAJAJ_TV, tableValueList);
            mBundle.putStringArrayList(AppUtils.HL_BAJAJ_PHONE, bajajPhone);
            mBundle.putStringArrayList(AppUtils.HL_BAJAJ_EMAIL, bajajEmailArray);
            mBundle.putStringArrayList(AppUtils.HL_BAJAJ_NF, bajajNF);
            mBundle.putStringArrayList(AppUtils.HL_BAJAJ_NL, bajajNL);
            mBundle.putStringArrayList(AppUtils.HL_BAJAJ_NR, bajajNR);

            mBundle.putStringArrayList(AppUtils.HL_INSURED_FNAME, insuredFirstName);
            mBundle.putStringArrayList(AppUtils.HL_INSURED_LNAME, insuredLastName);
            mBundle.putStringArrayList(AppUtils.HL_INSURED_DOB, insuredDob);
            mBundle.putStringArrayList(AppUtils.HL_INSURED_GENDER, insuredGender);
            mBundle.putStringArrayList(AppUtils.HL_INSURED_HEIGHT, insuredHeight);
            mBundle.putStringArrayList(AppUtils.HL_INSURED_WEIGHT, insuredWeight);
            mBundle.putStringArrayList(AppUtils.HL_INSURED_OCCUPATION, insuredOccupation);
            mBundle.putStringArrayList(AppUtils.HL_INSURED_DESIGNATION, insuredDesignation);
            mBundle.putStringArrayList(AppUtils.HL_INSURED_QUE1, insuredQue1);
            mBundle.putStringArrayList(AppUtils.HL_INSURED_QUE2, insuredQue2);
            mBundle.putStringArrayList(AppUtils.HL_INSURED_QUE3, insuredQue3);
            mBundle.putStringArrayList(AppUtils.HL_INSURED_QUE4, insuredQue4);
            mBundle.putStringArrayList(AppUtils.HL_INSURED_QUE5, insuredQue5);
            mBundle.putStringArrayList(AppUtils.HL_INSURED_QUE6, insuredQue6);
            mBundle.putStringArrayList(AppUtils.HL_INSURED_ALCOHOL, insuredAlcohol);
            mBundle.putStringArrayList(AppUtils.HL_INSURED_SMOKE, insuredSmoke);
            mBundle.putStringArrayList(AppUtils.HL_INSURED_TOBACCO, insuredTobacco);
            mBundle.putStringArrayList(AppUtils.HL_INSURED_NARCOTICS, insuredNarcotics);
            mBundle.putStringArrayList(AppUtils.HL_INSURED_SUBSTANCE, insuredSubstance);

            mBundle.putStringArrayList(AppUtils.HL_MED_MONTH, medMonth);
            mBundle.putStringArrayList(AppUtils.HL_MED_YEAR, medYear);
            mBundle.putStringArrayList(AppUtils.HL_ILLNESS, nameIllness);
            mBundle.putStringArrayList(AppUtils.HL_TREAT, treatment);
            mBundle.putStringArrayList(AppUtils.HL_OUTCOME, outCome);
            mBundle.putStringArrayList(AppUtils.HL_AL_Q, alQuantityArray);
            mBundle.putStringArrayList(AppUtils.HL_AL_Y, alYearArray);
            mBundle.putStringArrayList(AppUtils.HL_SM_Q, smQuantityArray);
            mBundle.putStringArrayList(AppUtils.HL_SM_Y, smYearArray);
            mBundle.putStringArrayList(AppUtils.HL_TB_Q, tbQuantityArray);
            mBundle.putStringArrayList(AppUtils.HL_TB_Y, tbYearArray);
            mBundle.putStringArrayList(AppUtils.HL_NAR_Q, narQuantityArray);
            mBundle.putStringArrayList(AppUtils.HL_NAR_Y, narYearArray);
            mBundle.putStringArrayList(AppUtils.HL_OS_Q, osQuantityArray);
            mBundle.putStringArrayList(AppUtils.HL_OS_Y, osYearArray);

            Intent intent;
            if ((totalDaughter + totalSon) > 2) {
                intent = new Intent(mContext, Member5DetailActivity.class);
            } else {
                if (companyName.equalsIgnoreCase("bajaj"))
                    intent = new Intent(mContext, BajajAddressActivity.class);
                else
                    intent = new Intent(mContext, HealthNomineeActivity.class);
            }

            intent.putExtras(mBundle);
            startActivity(intent);
        }
    }

    private boolean checkValidation() {

        firstName = edtFName.getText().toString().trim();
        lastName = edtLName.getText().toString().trim();
        dob = edtDOB.getText().toString().trim();
        height = edtInches.getText().toString().trim();
        weight = edtWeight.getText().toString().trim();
        gender = spnGender.getSelectedItem().toString().toLowerCase();
        designation = edtDes.getText().toString();
        alQuantity = edtAlQuantity.getText().toString();
        alYear = edtAlYear.getText().toString();
        smokeQuantity = edtAlQuantity.getText().toString();
        smokeYear = edtSmokeYear.getText().toString();
        tbQuantity = edtAlQuantity.getText().toString();
        tbYear = edtTobYear.getText().toString();
        narQuantity = edtAlQuantity.getText().toString();
        narYear = edtNarYear.getText().toString();
        osQuantity = edtAlQuantity.getText().toString();
        osYear = edtOsYear.getText().toString();
        medicalYear = edtYear.getText().toString();
        medicalIllness = edtNameIllness.getText().toString();
        //bajaj
        bajajEmail = edtBajajEmail.getText().toString();
        bajajMobile = edtBajajMobile.getText().toString();
        bajajNomineeFName = edtFNomineeName.getText().toString();
        bajajNomineeLName = edtLNomineeName.getText().toString();
        //SBI
        sbiEmail = edtSbiEmail.getText().toString();
        sbiMobile = edtSbiMobile.getText().toString();
        sbiPlot = edtSbiPlot.getText().toString();
        sbiBuilding = edtSbiBuilding.getText().toString();
        sbiStreet = edtSbiStreet.getText().toString();
        sbiAddress1 = edtSbiAddress1.getText().toString();
        documentNo = edtDocNo.getText().toString().trim();

        if (companyName.equalsIgnoreCase("royal") && isBelow) {
            if (findViewById(R.id.textDes).getVisibility() == View.VISIBLE
                    && TextUtils.isEmpty(designation)) {

                edtDes.setError(getString(R.string.field_cannot));
                edtDes.requestFocus();
                return false;
            }

        } else if (companyName.equalsIgnoreCase("apollo")) {

            apolloQue1 = apolloQue2 = apolloQue3 = apolloQue4 = apolloQue5 = "";
            if (spnQue1.getSelectedItemPosition() > 0)
                apolloQue1 = spnQue1.getSelectedItem().toString();
            if (spnQue2.getSelectedItemPosition() > 0)
                apolloQue2 = spnQue2.getSelectedItem().toString();
            if (spnQue3.getSelectedItemPosition() > 0)
                apolloQue3 = spnQue3.getSelectedItem().toString();
            if (spnQue4.getSelectedItemPosition() > 0)
                apolloQue4 = spnQue4.getSelectedItem().toString();
            if (spnQue5.getSelectedItemPosition() > 0)
                apolloQue5 = spnQue5.getSelectedItem().toString();

            RadioButton rbYes = findViewById(R.id.rbYes);
            if (rbYes.isChecked())
                apolloQue6 = "Yes";
            else apolloQue6 = "No";
        } else if (companyName.equalsIgnoreCase("bajaj")) {

            if (TextUtils.isEmpty(bajajNomineeFName)) {
                edtFNomineeName.setError(getString(R.string.field_cannot));
                edtFNomineeName.requestFocus();
                return false;
            } else if (!AppUtils.validateName(bajajNomineeFName)) {
                edtFNomineeName.setError(getString(R.string.invalid_name));
                edtFNomineeName.requestFocus();
                return false;
            }
            if (TextUtils.isEmpty(bajajNomineeLName)) {
                edtLNomineeName.setError(getString(R.string.field_cannot));
                edtLNomineeName.requestFocus();
                return false;
            } else if (!AppUtils.validateName(bajajNomineeLName)) {
                edtLNomineeName.setError(getString(R.string.invalid_name));
                edtLNomineeName.requestFocus();
                return false;
            }
            if (TextUtils.isEmpty(bajajMobile)) {
                edtBajajMobile.setError(getString(R.string.field_cannot));
                edtBajajMobile.requestFocus();
                return false;
            } else if (!(AppUtils.isValidMobile(bajajMobile))) {
                edtBajajMobile.setError("Invalid Phone Number");
                edtBajajMobile.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(bajajEmail)) {
                edtBajajEmail.setError(getString(R.string.field_cannot));
                edtBajajEmail.requestFocus();
                return false;
            } else if (!(AppUtils.isValidMail(bajajEmail))) {
                edtBajajEmail.setError("Invalid Email");
                edtBajajEmail.requestFocus();
                return false;
            }
        }else if (companyName.equalsIgnoreCase("sbi")) {
            if (TextUtils.isEmpty(sbiMobile)) {
                edtSbiMobile.setError(getString(R.string.field_cannot));
                edtSbiMobile.requestFocus();
                return false;
            } else if (!(AppUtils.isValidMobile(sbiMobile))) {
                edtSbiMobile.setError("Invalid Phone Number");
                edtSbiMobile.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(sbiEmail)) {
                edtSbiEmail.setError(getString(R.string.field_cannot));
                edtSbiEmail.requestFocus();
                return false;
            } else if (!(AppUtils.isValidMail(sbiEmail))) {
                edtSbiEmail.setError("Invalid Email");
                edtSbiEmail.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(sbiPlot)) {
                edtSbiPlot.setError(getString(R.string.field_cannot));
                edtSbiPlot.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(sbiBuilding)) {
                edtSbiBuilding.setError(getString(R.string.field_cannot));
                edtSbiBuilding.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(sbiStreet)) {
                edtSbiStreet.setError(getString(R.string.field_cannot));
                edtSbiStreet.requestFocus();
                return false;
            }
            if (TextUtils.isEmpty(documentNo)) {
                edtDocNo.setError(getString(R.string.field_cannot));
                edtDocNo.requestFocus();
                return false;
            }
        }



        if (TextUtils.isEmpty(firstName)) {
            edtFName.setError(getString(R.string.field_cannot));
            edtFName.requestFocus();
            return false;
        } else if (!AppUtils.validateName(firstName)) {
            edtFName.setError(getString(R.string.invalid_name));
            edtFName.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(lastName)) {
            edtLName.setError(getString(R.string.field_cannot));
            edtLName.requestFocus();
            return false;
        } else if (!AppUtils.validateName(lastName)) {
            edtLName.setError(getString(R.string.invalid_name));
            edtLName.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(dob)) {
            edtDOB.setError(getString(R.string.field_cannot));
            edtDOB.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(height)) {
            edtInches.setError(getString(R.string.field_cannot));
            edtInches.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(weight)) {
            edtWeight.setError(getString(R.string.field_cannot));
            edtWeight.requestFocus();
            return false;
        }

        if (findViewById(R.id.rlMedOpt).getVisibility() == View.VISIBLE) {
            if (spnMonths.getSelectedItemPosition() == 0) {
                Toast.makeText(mContext, "Select month", Toast.LENGTH_SHORT).show();
                return false;
            } else if (spnTreatment.getSelectedItemPosition() == 0) {
                Toast.makeText(mContext, "Select Treatment Treatment", Toast.LENGTH_SHORT).show();
                return false;
            } else if (spnOutcome.getSelectedItemPosition() == 0) {
                Toast.makeText(mContext, "Select Treatment Outcome", Toast.LENGTH_SHORT).show();
                return false;
            } else if (TextUtils.isEmpty(medicalYear) || medicalYear.length() != 4) {
                edtYear.setError(getString(R.string.invalid_year));
                edtYear.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(medicalIllness)) {
                edtNameIllness.setError(getString(R.string.invalid_year));
                edtNameIllness.requestFocus();
                return false;
            }
            medicalMonth = spnMonths.getSelectedItem().toString();
            treatmentStatus = spnTreatment.getSelectedItem().toString();
            treatOutcome = spnOutcome.getSelectedItem().toString();
        }
        if (isAlcohol) {
            if (TextUtils.isEmpty(alQuantity)) {
                edtAlQuantity.setError(getString(R.string.field_cannot));
                edtAlQuantity.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(alYear)) {
                edtAlYear.setError(getString(R.string.field_cannot));
                edtAlYear.requestFocus();
                return false;
            }
        } else if (isSmoke) {
            if (TextUtils.isEmpty(smokeQuantity)) {
                edtSmokeQuantity.setError(getString(R.string.field_cannot));
                edtSmokeQuantity.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(smokeYear)) {
                edtSmokeYear.setError(getString(R.string.field_cannot));
                edtSmokeYear.requestFocus();
                return false;
            }
        } else if (isTobacco) {
            if (TextUtils.isEmpty(tbQuantity)) {
                edtTobQuantity.setError(getString(R.string.field_cannot));
                edtTobQuantity.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(tbYear)) {
                edtTobYear.setError(getString(R.string.field_cannot));
                edtTobYear.requestFocus();
                return false;
            }
        } else if (isNarcotics) {
            if (TextUtils.isEmpty(narQuantity)) {
                edtNarQuantity.setError(getString(R.string.field_cannot));
                edtNarQuantity.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(narYear)) {
                edtNarYear.setError(getString(R.string.field_cannot));
                edtNarYear.requestFocus();
                return false;
            }
        } else if (isOtherSub) {
            if (TextUtils.isEmpty(osQuantity)) {
                edtOsQuantity.setError(getString(R.string.field_cannot));
                edtOsQuantity.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(osYear)) {
                edtOsYear.setError(getString(R.string.field_cannot));
                edtOsYear.requestFocus();
                return false;
            }
        }

        String bmi = AppUtils.calculateBmi(Double.parseDouble(height), Double.parseDouble(weight));
        if (bmi.equalsIgnoreCase("Invalid BMI")) {
            Toast.makeText(mContext, "" + bmi, Toast.LENGTH_SHORT).show();
            return false;
        } else if (bmi.equalsIgnoreCase("Normal"))
            Toast.makeText(mContext, "BMI " + bmi, Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(mContext, "" + bmi, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (insuredFirstName.size() >= 4) {
            insuredFirstName.remove(3);
        }
        return true;
    }
}