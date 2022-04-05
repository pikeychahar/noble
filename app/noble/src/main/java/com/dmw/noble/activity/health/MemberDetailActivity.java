package com.dmw.noble.activity.health;

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

import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.Health.HealthManager;
import com.dmw.noble.model.master.Pincode;
import com.dmw.noble.model.master.PincodeMaster;
import com.dmw.noble.model_health.MemberDetail;
import com.dmw.noble.model_health.v2.Document;
import com.dmw.noble.model_health.v2.Gender;
import com.dmw.noble.model_health.v2.HealthDocument;
import com.dmw.noble.model_health.v2.HealthGender;
import com.dmw.noble.model_health.v2.HealthMarital;
import com.dmw.noble.model_health.v2.HealthOccupation;
import com.dmw.noble.model_health.v2.HealthRelation;
import com.dmw.noble.model_health.v2.HealthSalutation;
import com.dmw.noble.model_health.v2.MaritalStatus;
import com.dmw.noble.model_health.v2.Occupation;
import com.dmw.noble.model_health.v2.Relation;
import com.dmw.noble.model_health.v2.Salutation;
import com.dmw.noble.utils.AppUtils;
import com.dmw.noble.utils.String2WithTag;
import com.dmw.noble.utils.String3WithTag;
import com.dmw.noble.utils.String4WithTag;
import com.dmw.noble.utils.String5WithTag;
import com.dmw.noble.utils.String6WithTag;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MemberDetailActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {


    private SearchableSpinner spnOccupation, spnRelation;
    private Context mContext;
    private List<String2WithTag> occupations = new ArrayList<>();
    private List<Occupation> occupationsList = new ArrayList<>();

    private List<String3WithTag> relations = new ArrayList<>();
    private List<Relation> relationList = new ArrayList<>();

    private List<Salutation> salutationList = new ArrayList<>();
    private List<String5WithTag> salutations = new ArrayList<>();
    private EditText edtDOB;
    private Bundle mBundle;
    private Spinner spnGender;
    private String quotationId, planFor;

    private List<Gender> genderList = new ArrayList<>();
    private List<String6WithTag> genders = new ArrayList<>();
    String gName, genderId;

    EditText edtFName, edtLName, edtInches, edtWeight, edtDes, edtYear,
            edtNameIllness, edtAlQuantity, edtAlYear, edtSmokeQuantity, edtSmokeYear, edtTobQuantity,
            edtTobYear, edtNarQuantity, edtNarYear, edtOsQuantity, edtOsYear;
    public ProgressDialog progressdialog;

    Spinner spnMonths, spnTreatment, spnOutcome, spnSalutation, spnQue1, spnQue2, spnQue3, spnQue4,
            spnQue5, spnMaterial;
    private List<MaritalStatus> maritalList = new ArrayList<>();
    private List<String3WithTag> maritalLists = new ArrayList<>();
    private List<Document> documentList = new ArrayList<>();
    private List<String2WithTag> docsList = new ArrayList<>();

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

    int totalSon, totalDaughter;

    ArrayList<String> proposerFirstName, proposerLastName, proposerDob, proposerGender,
            proposerOccupation, proposerDesignation;

    String apolloQue1, apolloQue2, apolloQue3, apolloQue4, apolloQue5, apolloQue6, apolloSal,
            apolloRelation;

    //Bajaj
    EditText edtBajajMobile, edtBajajEmail, edtFNomineeName, edtLNomineeName;
    SearchableSpinner spnBajajNominee;
    RadioButton rbB1Yes, rbB1No, rbB2Yes, rbB3Yes, rbB2No, rbB3No;

    String existsBajaj, tobaccoBajaj, tablesBajaj, tableValue, bajajMobile, bajajEmail,
            bajajNomineeFName, bajajNomineeLName, bajajNomineeRelation;

    //SBI
    EditText edtSbiMobile, edtSbiEmail, edtSbiPlot, edtSbiBuilding, edtSbiStreet, edtSbiAddress1,
            edtDocNo;
    String sbiMobile, sbiEmail, sbiPlot, sbiBuilding, sbiStreet, sbiAddress1, sbiPincode,
            documentId, documentNo;
    SearchableSpinner spnSbiPincode;
    RadioButton rbS1Yes, rbS1No, rbS2Yes, rbS3Yes, rbS2No, rbS3No, rbS4Yes, rbS4No;
    Spinner spnDocType;

    private List<String4WithTag> sbiPins = new ArrayList<>();
    private List<Pincode> sbiPincodeList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        mBundle = getIntent().getExtras();

        //Bajaj
        edtBajajMobile = findViewById(R.id.edtBajajMobile);
        edtBajajEmail = findViewById(R.id.edtBajajEmail);
        edtFNomineeName = findViewById(R.id.edtFNomineeName);
        edtLNomineeName = findViewById(R.id.edtLNomineeName);
        rbB1Yes = findViewById(R.id.rbB1Yes);
        rbB1No = findViewById(R.id.rbB1No);
        rbB2Yes = findViewById(R.id.rbB2Yes);
        rbB3Yes = findViewById(R.id.rbB3Yes);
        rbB2No = findViewById(R.id.rbB2No);
        rbB3No = findViewById(R.id.rbB3No);
        spnBajajNominee = findViewById(R.id.spnBajajNominee);

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

        spnMaterial = findViewById(R.id.spnMaterial);
        spnSalutation = findViewById(R.id.spnSalutation);
        spnOccupation = findViewById(R.id.spnOccupation);
        spnRelation = findViewById(R.id.spnRelation);
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

        spnQue1 = findViewById(R.id.spnAns1);
        spnQue2 = findViewById(R.id.spnAns2);
        spnQue3 = findViewById(R.id.spnAns3);
        spnQue4 = findViewById(R.id.spnAns4);
        spnQue5 = findViewById(R.id.spnAns5);

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

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");

        if (mBundle != null) {
            quotationId = mBundle.getString(AppUtils.QUOTATION_ID_1);
            planFor = mBundle.getString(AppUtils.INDIVIDUAL_NAME);
            companyName = mBundle.getString(AppUtils.HL_COMPANY);
            healthPlanType = mBundle.getString(AppUtils.HEALTH_PLAN);

            isSpouse = mBundle.getBoolean(AppUtils.IS_SPOUSE);
            isMother = mBundle.getBoolean(AppUtils.IS_MOTHER);
            isFather = mBundle.getBoolean(AppUtils.IS_FATHER);
            totalSon = mBundle.getInt(AppUtils.TOTAL_SON);
            totalDaughter = mBundle.getInt(AppUtils.TOTAL_DAUGHTER);

            String mail = mBundle.getString(AppUtils.HL_EMAIL);
            String phone = mBundle.getString(AppUtils.HL_PHONE);

            edtSbiEmail.setText(mail);
            edtBajajEmail.setText(mail);
            edtSbiMobile.setText(phone);
            edtBajajMobile.setText(phone);

            if (!companyName.equalsIgnoreCase("bajaj")
                    && !companyName.equalsIgnoreCase("sbi")) {

                proposerFirstName = mBundle.getStringArrayList(AppUtils.HL_PROPOSER_FIRST_NAME);
                edtFName.setText(proposerFirstName.get(0));

                proposerLastName = mBundle.getStringArrayList(AppUtils.HL_PROPOSER_LAST_NAME);
                edtLName.setText(proposerLastName.get(0));

                proposerDob = mBundle.getStringArrayList(AppUtils.HL_PROPOSER_DOB);
                edtDOB.setText(proposerDob.get(0));

                proposerDesignation = mBundle.getStringArrayList(AppUtils.HL_PROPOSER_DESIGNATION);
                edtDes.setText(proposerDesignation.get(0));
            }
        }

        if (!TextUtils.isEmpty(planFor))
            txtIndividualName.setText("Insured member detail - " + planFor);
        getOccupations();

        que1 = que2 = que3 = que4 = que5 = que6 = smoke = tobacco = substance = alcohol = narcotics
                = existsBajaj = tobaccoBajaj = tablesBajaj = tableValue = "No";

        getSalutation();
        getRelation();
        getMaritalStatus();

        genderList = HealthManager.getInstance().getGenderList();
        if (genderList.size() > 0)
            initGender();
        else getGender();

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
            getDocuments();

            sbiPincodeList = HealthManager.getInstance().getSbiPincodeList();
            if (sbiPincodeList != null && sbiPincodeList.size() > 0)
                initSbiPincode();
            else getSbiPincode();

            findViewById(R.id.layoutSbi).setVisibility(View.VISIBLE);
            findViewById(R.id.textDes).setVisibility(View.VISIBLE);
            findViewById(R.id.spnSalutation).setVisibility(View.VISIBLE);
        }
        String calSelf = mBundle.getString(AppUtils.CAL_SELF);

        if (!TextUtils.isEmpty(calSelf))
            edtDOB.setText(calSelf);

        final String[] current = {""};
        String ddmmyyyy = "DDMMYYYY";
        Calendar cal = Calendar.getInstance();
        int mYear, lYear;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        mYear = calendar.get(Calendar.YEAR);
        lYear = mYear - 19;

        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current[0])) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current[0].replaceAll("[^\\d.]|\\.", "");

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

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current[0] = clean;
                    edtDOB.setText(current[0]);
                    edtDOB.setSelection(sel < current[0].length() ? sel : current[0].length());
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

    public void getGender() {
        if (AppUtils.isOnline(mContext)) {
            try {
                HealthManager.getInstance().getGender(mContext, quotationId, companyName);
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

    @Override
    public void onResponse(Object response) {

        if (response instanceof HealthGender) {
            HealthGender cResponse = (HealthGender) response;
            if (cResponse.getStatus().equalsIgnoreCase("1") && cResponse.getGender() != null) {
                genderList.clear();
                genders.clear();
                genderList = cResponse.getGender();
                if (genderList.size() > 0) {
                    initGender();
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

        if (response instanceof HealthDocument) {
            HealthDocument cResponse = (HealthDocument) response;
            if (cResponse.getStatus().equalsIgnoreCase("1")
                    && cResponse.getDocument() != null) {

                documentList.clear();
                docsList.clear();
                documentList = cResponse.getDocument();
                if (documentList.size() > 0) {
                    initDocument();
                }
            }
        }

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
        if (response instanceof HealthRelation) {
            HealthRelation cResponse = (HealthRelation) response;
            if (cResponse.getStatus().equalsIgnoreCase("1")) {
                relationList.clear();
                relations.clear();
                relationList = cResponse.getRelation();
                if (relationList.size() > 0) {
                    initRelation();
                    if (companyName.equalsIgnoreCase("bajaj"))
                        initNominee();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    public void onNextClick(View view) {
        if (checkValidation()) {

            ArrayList<String> insuredFirstName = new ArrayList<>();
            ArrayList<String> insuredLastName = new ArrayList<>();
            ArrayList<String> insuredDob = new ArrayList<>();
            ArrayList<String> insuredGender = new ArrayList<>();
            ArrayList<String> insuredHeight = new ArrayList<>();
            ArrayList<String> insuredWeight = new ArrayList<>();
            ArrayList<String> insuredOccupation = new ArrayList<>();
            ArrayList<String> insuredDesignation = new ArrayList<>();
            ArrayList<String> insuredQue1 = new ArrayList<>();
            ArrayList<String> insuredQue2 = new ArrayList<>();
            ArrayList<String> insuredQue3 = new ArrayList<>();
            ArrayList<String> insuredQue4 = new ArrayList<>();
            ArrayList<String> insuredQue5 = new ArrayList<>();
            ArrayList<String> insuredQue6 = new ArrayList<>();
            ArrayList<String> insuredAlcohol = new ArrayList<>();
            ArrayList<String> insuredSmoke = new ArrayList<>();
            ArrayList<String> insuredTobacco = new ArrayList<>();
            ArrayList<String> insuredNarcotics = new ArrayList<>();
            ArrayList<String> insuredSubstance = new ArrayList<>();
            ArrayList<String> insuredMarital = new ArrayList<>();

            ArrayList<String> alQuantityArray = new ArrayList<>();
            ArrayList<String> alYearArray = new ArrayList<>();
            ArrayList<String> smQuantityArray = new ArrayList<>();
            ArrayList<String> smYearArray = new ArrayList<>();
            ArrayList<String> tbQuantityArray = new ArrayList<>();
            ArrayList<String> tbYearArray = new ArrayList<>();
            ArrayList<String> narQuantityArray = new ArrayList<>();
            ArrayList<String> narYearArray = new ArrayList<>();
            ArrayList<String> osQuantityArray = new ArrayList<>();
            ArrayList<String> osYearArray = new ArrayList<>();
            ArrayList<String> medMonth = new ArrayList<>();
            ArrayList<String> medYear = new ArrayList<>();
            ArrayList<String> nameIllness = new ArrayList<>();
            ArrayList<String> treatment = new ArrayList<>();
            ArrayList<String> outCome = new ArrayList<>();

            ArrayList<String> salutation = new ArrayList<>();
            ArrayList<String> relation = new ArrayList<>();

            ArrayList<String> cigarette = new ArrayList<>();
            ArrayList<String> pouch = new ArrayList<>();
            ArrayList<String> liquor = new ArrayList<>();
            ArrayList<String> beer = new ArrayList<>();
            ArrayList<String> wine = new ArrayList<>();
            ArrayList<String> anyDiseases = new ArrayList<>();

            //bajaj
            ArrayList<String> existsList = new ArrayList<>();
            ArrayList<String> tobaccoBajajList = new ArrayList<>();
            ArrayList<String> tableList = new ArrayList<>();
            ArrayList<String> tableValueList = new ArrayList<>();
            ArrayList<String> bajajPhone = new ArrayList<>();
            ArrayList<String> bajajEmail = new ArrayList<>();
            ArrayList<String> bajajNF = new ArrayList<>();
            ArrayList<String> bajajNL = new ArrayList<>();
            ArrayList<String> bajajNR = new ArrayList<>();

            //SBI
            ArrayList<String> sbiEmail = new ArrayList<>();
            ArrayList<String> sbiMobile = new ArrayList<>();
            ArrayList<String> sbiPlot = new ArrayList<>();
            ArrayList<String> sbiBuilding = new ArrayList<>();
            ArrayList<String> sbiStreet = new ArrayList<>();
            ArrayList<String> sbiAddress1 = new ArrayList<>();
            ArrayList<String> sbiPincode = new ArrayList<>();

            ArrayList<String> sbiTobacco = new ArrayList<>();
            ArrayList<String> sbiAlcohol = new ArrayList<>();
            ArrayList<String> sbiSmoke = new ArrayList<>();
            ArrayList<String> sbiOther = new ArrayList<>();
            ArrayList<String> sbiDoc = new ArrayList<>();
            ArrayList<String> sbiDocNo = new ArrayList<>();

            insuredFirstName.add(firstName);
            insuredLastName.add(lastName);
            insuredDob.add(dob);
            insuredGender.add(genderId);
            insuredHeight.add(height);
            insuredWeight.add(weight);
            insuredOccupation.add(occupationId);

            if (companyName.equalsIgnoreCase("royal")) {
                insuredDesignation.add(designation);
                insuredQue1.add(que1);
                insuredQue2.add(que2);
                insuredQue3.add(que3);
                insuredQue4.add(que4);
                insuredQue5.add(que5);
                insuredQue6.add(que6);
                insuredAlcohol.add(alcohol);
                insuredSmoke.add(smoke);
                insuredTobacco.add(tobacco);
                insuredNarcotics.add(narcotics);
                insuredSubstance.add(substance);

                if (isQue6 || isQue5 || isQue4 || isQue3 || isQue2 || isQue1) {
                    medMonth.add(medicalMonth);
                    medYear.add(medicalYear);
                    nameIllness.add(medicalIllness);
                    treatment.add(treatmentStatus);
                    outCome.add(treatOutcome);
                }

                if (isAlcohol) {
                    alQuantityArray.add(alQuantity);
                    alYearArray.add(alYear);

                }
                if (isSmoke) {
                    smQuantityArray.add(smokeQuantity);
                    smYearArray.add(smokeYear);
                }
                if (isTobacco) {
                    tbQuantityArray.add(tbQuantity);
                    tbYearArray.add(tbYear);
                }
                if (isNarcotics) {
                    narQuantityArray.add(narQuantity);
                    narYearArray.add(narYear);
                }

                if (isOtherSub) {
                    osQuantityArray.add(osQuantity);
                    osYearArray.add(osYear);
                }
            } else if (companyName.equalsIgnoreCase("apollo")) {
                relation.add(apolloRelation);
                salutation.add(apolloSal);
                cigarette.add(apolloQue1);
                pouch.add(apolloQue2);
                liquor.add(apolloQue3);
                beer.add(apolloQue4);
                wine.add(apolloQue5);
                anyDiseases.add(apolloQue6);
                insuredMarital.add(maritalId);
            } else if (companyName.equalsIgnoreCase("bajaj")) {

                existsList.add(existsBajaj);
                tobaccoBajajList.add(tobaccoBajaj);
                tableList.add(tablesBajaj);
                tableValueList.add(tableValue);
                bajajPhone.add(bajajMobile);
                bajajEmail.add(this.bajajEmail);
                bajajNF.add(this.bajajNomineeFName);
                bajajNL.add(this.bajajNomineeLName);
                bajajNR.add(this.bajajNomineeRelation);
                salutation.add(apolloSal);
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

                sbiTobacco.add(sbiQ1);
                sbiAlcohol.add(sbiQ2);
                sbiSmoke.add(sbiQ3);
                sbiOther.add(sbiQ4);

                salutation.add(apolloSal);
                insuredMarital.add(maritalId);
                sbiDoc.add(documentId);
                sbiDocNo.add(documentNo);

                sbiEmail.add(this.sbiEmail);
                sbiMobile.add(this.sbiMobile);
                sbiPlot.add(this.sbiPlot);
                sbiBuilding.add(this.sbiBuilding);
                sbiStreet.add(this.sbiStreet);
                sbiAddress1.add(this.sbiAddress1);

                sbiPincode.add(this.sbiPincode);
            }

            mBundle.putStringArrayList(AppUtils.HL_SBI_Q1, sbiTobacco);
            mBundle.putStringArrayList(AppUtils.HL_SBI_Q2, sbiAlcohol);
            mBundle.putStringArrayList(AppUtils.HL_SBI_Q3, sbiSmoke);
            mBundle.putStringArrayList(AppUtils.HL_SBI_Q4, sbiOther);

            mBundle.putStringArrayList(AppUtils.HL_SBI_EMAIL, sbiEmail);
            mBundle.putStringArrayList(AppUtils.HL_SBI_PHONE, sbiMobile);
            mBundle.putStringArrayList(AppUtils.HL_SBI_PLOT, sbiPlot);
            mBundle.putStringArrayList(AppUtils.HL_SBI_BUILDING, sbiBuilding);
            mBundle.putStringArrayList(AppUtils.HL_SBI_STREET, sbiStreet);
            mBundle.putStringArrayList(AppUtils.HL_SBI_ADDRESS1, sbiAddress1);
            mBundle.putStringArrayList(AppUtils.HL_SBI_PINCODE, sbiPincode);

            mBundle.putStringArrayList(AppUtils.HL_SBI_DOC, sbiDoc);
            mBundle.putStringArrayList(AppUtils.HL_SBI_DOC_NO, sbiDocNo);


            mBundle.putStringArrayList(AppUtils.HL_AP_REL, relation);
            mBundle.putStringArrayList(AppUtils.HL_AP_SAL, salutation);
            mBundle.putStringArrayList(AppUtils.HL_AP_CIG, cigarette);
            mBundle.putStringArrayList(AppUtils.HL_AP_POUCH, pouch);
            mBundle.putStringArrayList(AppUtils.HL_AP_LIQ, liquor);
            mBundle.putStringArrayList(AppUtils.HL_AP_BEER, beer);
            mBundle.putStringArrayList(AppUtils.HL_AP_WINE, wine);
            mBundle.putStringArrayList(AppUtils.HL_AP_ANY, anyDiseases);
            mBundle.putStringArrayList(AppUtils.HL_INSURED_MARITAL, insuredMarital);

            mBundle.putStringArrayList(AppUtils.HL_BAJAJ_EXTRA, existsList);
            mBundle.putStringArrayList(AppUtils.HL_BAJAJ_TB, tobaccoBajajList);
            mBundle.putStringArrayList(AppUtils.HL_BAJAJ_TABLE, tableList);
            mBundle.putStringArrayList(AppUtils.HL_BAJAJ_TV, tableValueList);
            mBundle.putStringArrayList(AppUtils.HL_BAJAJ_PHONE, bajajPhone);
            mBundle.putStringArrayList(AppUtils.HL_BAJAJ_EMAIL, bajajEmail);
            mBundle.putStringArrayList(AppUtils.HL_BAJAJ_NF, bajajNF);
            mBundle.putStringArrayList(AppUtils.HL_BAJAJ_NL, bajajNL);
            mBundle.putStringArrayList(AppUtils.HL_BAJAJ_NR, bajajNR);

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

            Intent intent;
            if (!TextUtils.isEmpty(healthPlanType)) {
                if (healthPlanType.equalsIgnoreCase(getString(R.string.individual))) {
                    if (companyName.equalsIgnoreCase("bajaj"))
                        intent = new Intent(mContext, BajajAddressActivity.class);
                    else
                        intent = new Intent(mContext, HealthNomineeActivity.class);
                } else {
                    if (isFather || isMother || isSpouse) {
                        intent = new Intent(mContext, Member2DetailActivity.class);
                    } else if (totalDaughter > 0 || totalSon > 0) {
                        intent = new Intent(mContext, Member3DetailActivity.class);
                    } else {
                        if (companyName.equalsIgnoreCase("bajaj"))
                            intent = new Intent(mContext, BajajAddressActivity.class);
                        else
                            intent = new Intent(mContext, HealthNomineeActivity.class);
                    }
                }
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        }
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

    public void getRelation() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                HealthManager.getInstance().getRelation(mContext, quotationId, companyName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
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

        if (companyName.equalsIgnoreCase("royal")) {
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
        } else if (companyName.equalsIgnoreCase("sbi")) {
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
        return true;
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

}
