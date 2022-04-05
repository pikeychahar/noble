package com.dmw.noble.activity_motor;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.activity.PrivacyActivity;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.ApiManager;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.model.OtherInformation;
import com.dmw.noble.model.SbiData;
import com.dmw.noble.model.SbiMisd;
import com.dmw.noble.utils.AppUtils;
import com.dmw.noble.utils.String2WithTag;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class DetailedVehicleActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {

    TextView txtManuDate, txtPurDate, txtRegDate, txtPEDate, txtSelected;
    Context mContext;
    SimpleDateFormat dateFormatter;
    RadioButton ocYes, ocNo, exYes, exNo;
    SharedPreferences preferences;
    private int regYear;
    String manufactureDate, purchaseDate, registrationDate, policyExpireDate, vehicleOwnedBy, ncb,
            ownerChange, claimMade, claimExpire, insertId, newVehicle, zeroDept, prePolicy,
            vehicleType, prePolicyType, policyType, policyExpiry, company, sbiCode;
    Spinner spnOwned;
    RadioGroup rgOwnerChange, rgExpiring, rgZero;
    ProgressDialog progressDialog;
    Bundle mBundle;
    int totalDays;
    boolean policyExpired, isNcb, isOldQuote;
    TextView txt0, txt20, txt25, txt35, txt45, txt50;
    Calendar calendar = Calendar.getInstance();
    List<SbiData> sbiDataList = new ArrayList<>();
    List<String2WithTag> sbiList = new ArrayList<>();
    SearchableSpinner spnSbi;

    @SuppressLint({"UseCompatLoadingForDrawables", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_vehicle);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mContext = this;

        try {
            preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
            dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            vehicleType = preferences.getString(AppUtils.VEHICLE_TYPE, "Vehicle");
            policyType = preferences.getString(AppUtils.POLICY_TYPE, "Comprehensive");

            txtManuDate = findViewById(R.id.txtManuDate);
            txtPurDate = findViewById(R.id.txtPurDate);
            txtRegDate = findViewById(R.id.txtRegDate);
            txtPEDate = findViewById(R.id.txtPExpDate);

            txt0 = findViewById(R.id.txt0);
            txt20 = findViewById(R.id.txt20);
            txt25 = findViewById(R.id.txt25);
            txt35 = findViewById(R.id.txt35);
            txt45 = findViewById(R.id.txt45);
            txt50 = findViewById(R.id.txt50);

            spnOwned = findViewById(R.id.owned);

            rgOwnerChange = findViewById(R.id.rgOwnerChange);
            rgExpiring = findViewById(R.id.rgExpiring);
            rgZero = findViewById(R.id.rgZero);

            ocYes = findViewById(R.id.ocYes);
            ocNo = findViewById(R.id.ocNo);
            exYes = findViewById(R.id.epYes);
            exNo = findViewById(R.id.epNo);
            spnSbi = findViewById(R.id.spnSbi);

            claimExpire = ownerChange = "0";
            zeroDept = "1";
            isNcb = true;
            isOldQuote = false;

            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("One Moment...");

            mBundle = getIntent().getExtras();

            if (mBundle != null) {
                insertId = mBundle.getString(AppUtils.INSERT_ID);
                newVehicle = mBundle.getString(AppUtils.NEW_VEHICLE);
                regYear = mBundle.getInt(AppUtils.REG_YEAR);
                prePolicy = mBundle.getString(AppUtils.IS_PREVIOUS);
                policyExpired = mBundle.getBoolean(AppUtils.POLICY_EXPIRED);
                vehicleType = mBundle.getString(AppUtils.VEHICLE_TYPE);
                isOldQuote = mBundle.getBoolean(AppUtils.OLD_QUOTE);
                prePolicyType = mBundle.getString(AppUtils.PRE_POLICY_TYPE);
                policyExpiry = mBundle.getString(AppUtils.POLICY_EXPIRY);
                company = mBundle.getString(AppUtils.PCV_COMPANY);
                sbiCode = mBundle.getString(AppUtils.SBI_GCV_CODE);

                registrationDate = mBundle.getString(AppUtils.REGISTRATION_DATE);
                policyExpireDate = mBundle.getString(AppUtils.POLICY_EXPIRY_DATE);

                if (isOldQuote) {
                    manufactureDate = mBundle.getString(AppUtils.MANUFACTURE_DATE);
                    purchaseDate = mBundle.getString(AppUtils.PURCHASE_DATE);
                    registrationDate = mBundle.getString(AppUtils.REGISTRATION_DATE);
                    policyExpireDate = mBundle.getString(AppUtils.POLICY_EXPIRY_DATE);
                    ncb = mBundle.getString(AppUtils.PRE_NCB);
                    claimExpire = mBundle.getString(AppUtils.CLAIM_EXP);
                    ownerChange = mBundle.getString(AppUtils.OWNER_CHANGE);
                    prePolicyType = mBundle.getString(AppUtils.PRE_POLICY_TYPE);
                    sbiCode = mBundle.getString(AppUtils.SBI_GCV_CODE);
                    String tp = mBundle.getString(AppUtils.TP_ONLY);

                    if (!TextUtils.isEmpty(tp))
                        if (tp.equals("2"))
                            policyType = "Standalone od";
                        else if (tp.equals("1"))
                            policyType = "Third Party";
                        else
                            policyType = "Comprehensive";
                }

                if (!TextUtils.isEmpty(company)) {
                    if ((company.equals("reliance") || company.equals("sbi"))
                            && vehicleType.equals("4") && (!isOldQuote)) {
                        findViewById(R.id.rlSbi).setVisibility(View.VISIBLE);
                        getSbiData();
                    }
                }
            }

            if (!TextUtils.isEmpty(prePolicy))
                if (prePolicy.equals("1")) {
                    findViewById(R.id.lblPolicyExpire).setVisibility(View.GONE);
                    findViewById(R.id.txtPExpDate).setVisibility(View.GONE);
                    findViewById(R.id.rlNew).setVisibility(View.GONE);
                    isNcb = false;
                    zeroDept = "0";
                }
            if ((!TextUtils.isEmpty(prePolicyType))
                    && (prePolicyType.contains("third"))) {
                findViewById(R.id.llNcb).setVisibility(View.GONE);
                findViewById(R.id.lblCB).setVisibility(View.GONE);
                isNcb = false;
            }

            if (policyExpired) {
                findViewById(R.id.llNcb).setVisibility(View.GONE);
                findViewById(R.id.lblCB).setVisibility(View.GONE);
                isNcb = false;
            }

            if (regYear == 0)
                regYear = 2021;
            int currentYear = calendar.get(Calendar.YEAR);
            int diffYears = currentYear - regYear;

            calendar.set(Calendar.YEAR, regYear);

            if (isOldQuote) {
                if (!TextUtils.isEmpty(purchaseDate))
                    txtPurDate.setText(dateFormatChanger(purchaseDate));
                else txtPurDate.setText(dateFormatter.format(calendar.getTime()));

                if (!TextUtils.isEmpty(registrationDate))
                    txtRegDate.setText(dateFormatChanger(registrationDate));
                else txtRegDate.setText(dateFormatter.format(calendar.getTime()));

                if (!TextUtils.isEmpty(policyExpireDate))
                    txtPEDate.setText(dateFormatChanger(policyExpireDate));

                if (!TextUtils.isEmpty(manufactureDate))
                    txtManuDate.setText(dateFormatChanger(manufactureDate));
                else {
                    calendar.add(Calendar.DAY_OF_MONTH, -15);
                    txtManuDate.setText(dateFormatter.format(calendar.getTime()));
                }


                if (!TextUtils.isEmpty(claimExpire))
                    if (claimExpire.equals("1")) {
                        findViewById(R.id.lblCB).setVisibility(View.GONE);
                        findViewById(R.id.llNcb).setVisibility(View.GONE);
                        isNcb = false;
                        exYes.setChecked(true);
                    }
                if (!TextUtils.isEmpty(ownerChange))
                    if (ownerChange.equals("1")) {
                        findViewById(R.id.lblCB).setVisibility(View.GONE);
                        findViewById(R.id.llNcb).setVisibility(View.GONE);
                        ocYes.setChecked(true);
                        isNcb = false;
                    }

                if (!TextUtils.isEmpty(ncb)) {
                    switch (ncb) {
                        case "20":
                            setNcb20();
                            break;
                        case "25":
                            setNcb25();
                            break;
                        case "35":
                            setNcb35();
                            break;
                        case "45":
                            setNcb45();
                            break;
                        case "50":
                            setNcb50();
                            break;
                        default:
                            setNcb0();
                            break;
                    }
                }
            }

            if (TextUtils.isEmpty(ncb)) {
                switch (diffYears) {
                    case 0:
                    case 1:
                        setNcb0();
                        break;
                    case 2:
                        setNcb20();
                        break;
                    case 3:
                        setNcb25();
                        break;
                    case 4:
                        setNcb35();
                        break;
                    case 5:
                        setNcb45();
                        break;
                    default:
                        setNcb50();
                        break;
                }
            }

            if (!TextUtils.isEmpty(newVehicle)) {
                if (newVehicle.equals("new_gadi")) {
                    findViewById(R.id.rlNew).setVisibility(View.GONE);
                    findViewById(R.id.lblPolicyExpire).setVisibility(View.GONE);
                    findViewById(R.id.txtPExpDate).setVisibility(View.GONE);
                    Calendar calendar1 = Calendar.getInstance();
                    if (!isOldQuote) {
                        txtPurDate.setText(dateFormatter.format(calendar1.getTime()));
                        txtRegDate.setText(dateFormatter.format(calendar1.getTime()));
                        calendar1.add(Calendar.DAY_OF_MONTH, -15);
                        txtManuDate.setText(dateFormatter.format(calendar1.getTime()));
                    }
                    isNcb = false;
                }
            } else {
                if (!isOldQuote) {
                    if (!TextUtils.isEmpty(policyExpiry)) {
                        Calendar calendar = Calendar.getInstance();
                        if (policyExpiry.equalsIgnoreCase("Not Expired")) {
                            txtPEDate.setText(dateFormatter.format(calendar.getTime()));
                        } else if (policyExpiry.contains("Expired within")) {
                            calendar.add(Calendar.DAY_OF_MONTH, -1);
                            txtPEDate.setText(dateFormatter.format(calendar.getTime()));
                        } else {
                            calendar.add(Calendar.DAY_OF_MONTH, -90);
                            txtPEDate.setText(dateFormatter.format(calendar.getTime()));
                        }
                    }
                    txtPurDate.setText(dateFormatter.format(calendar.getTime()));
                    txtRegDate.setText(dateFormatter.format(calendar.getTime()));
                    calendar.add(Calendar.DAY_OF_MONTH, -15);
                    txtManuDate.setText(dateFormatter.format(calendar.getTime()));

                    if (!TextUtils.isEmpty(policyExpireDate)) {
                        if (!TextUtils.isEmpty(registrationDate))
                            txtRegDate.setText(dateFormatChanger(registrationDate));
                        else txtRegDate.setText(dateFormatter.format(calendar.getTime()));

                        if (!TextUtils.isEmpty(policyExpireDate))
                            txtPEDate.setText(dateFormatChanger(policyExpireDate));
                    }
                }
            }

            rgZero.setOnCheckedChangeListener((radioGroup, checkedId) -> {
                if (checkedId == R.id.zYes) {
                    zeroDept = "1";
                } else if (checkedId == R.id.zNo) {
                    zeroDept = "0";
                }
            });

            rgOwnerChange.setOnCheckedChangeListener((radioGroup, checkedId) -> {
                if (checkedId == R.id.ocYes) {
                    ownerChange = "1";
                } else if (checkedId == R.id.ocNo) {
                    ownerChange = "0";
                }
            });

            rgExpiring.setOnCheckedChangeListener((radioGroup, checkedId) -> {
                if (checkedId == R.id.ocYes) {
                    RadioButton yes = exYes;
                    claimMade = yes.getText().toString();
                    claimExpire = "1";
                } else if (checkedId == R.id.ocNo) {
                    RadioButton no = exNo;
                    claimMade = no.getText().toString();
                    claimExpire = "0";
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onDatePicker(View view) {

        txtSelected = (TextView) view;
        Calendar newCalendar = Calendar.getInstance();
        if (txtSelected != txtPEDate)
            newCalendar.set(regYear, 0, 1);

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                R.style.datepicker, (view1, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);

            txtSelected.setText(dateFormatter.format(newDate.getTime()));
            txtSelected.setError(null);
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
        if (!TextUtils.isEmpty(policyExpiry)
                && policyExpiry.equalsIgnoreCase("Not Expired")
                && txtSelected == txtPEDate) {
            datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        }
        if (txtSelected != txtPEDate) {
            datePickerDialog.getDatePicker().setMinDate(0);
        } else {
            if (!TextUtils.isEmpty(policyExpiry)) {
                if (policyExpiry.equalsIgnoreCase("Not Expired")) {
                    datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
                } else if (policyExpiry.contains("Expired within")) {
                    double timeInMillis = Calendar.getInstance().getTimeInMillis();
                    double max = timeInMillis - 8.64e+7;
                    double min = max - 7.603e+9;
                    datePickerDialog.getDatePicker().setMaxDate((long) max);
                    datePickerDialog.getDatePicker().setMinDate((long) min);
                } else {
                    double timeInMillis = Calendar.getInstance().getTimeInMillis();
                    double max = timeInMillis - 7.776e+9;
                    datePickerDialog.getDatePicker().setMaxDate((long) max);
                }
            }
        }
        datePickerDialog.show();
        try {
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorPrimary));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onOcYesClick(View view) {
        findViewById(R.id.lblCB).setVisibility(View.GONE);
        findViewById(R.id.llNcb).setVisibility(View.GONE);
        isNcb = false;
    }

    public void onOcNoClick(View view) {
        if (exNo.isChecked()) {
            findViewById(R.id.lblCB).setVisibility(View.VISIBLE);
            findViewById(R.id.llNcb).setVisibility(View.VISIBLE);
            isNcb = true;
        }
    }

    public void onEPYesClick(View view) {
        claimExpire = "1";
        findViewById(R.id.lblCB).setVisibility(View.GONE);
        findViewById(R.id.llNcb).setVisibility(View.GONE);
        isNcb = false;
    }

    public void onEPNoClick(View view) {
        if (ocNo.isChecked()) {
            claimExpire = "0";
            findViewById(R.id.lblCB).setVisibility(View.VISIBLE);
            findViewById(R.id.llNcb).setVisibility(View.VISIBLE);
            isNcb = true;
        }
    }

    public void onGetQuoteClick(View view) {
        if (isValid())
            otherInformation();
    }

    private void getValues() {
        manufactureDate = txtManuDate.getText().toString();
        purchaseDate = txtPurDate.getText().toString();
        registrationDate = txtRegDate.getText().toString();
        if (txtPEDate.getVisibility() == View.VISIBLE)
            policyExpireDate = txtPEDate.getText().toString();
        else policyExpireDate = "01-01-1970";
        vehicleOwnedBy = spnOwned.getSelectedItem().toString();

        if (!TextUtils.isEmpty(policyExpireDate))
            getDateAgo(policyExpireDate);
    }

    private boolean isValid() {
        getValues();

        if (TextUtils.isEmpty(manufactureDate)) {
            txtManuDate.setError("Select Date");
            txtManuDate.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(purchaseDate)) {
            txtPurDate.setError("Select Date");
            txtPurDate.requestFocus();
            txtManuDate.setError(null);
            return false;
        }
        if (TextUtils.isEmpty(registrationDate)) {
            txtRegDate.setError("Select Date");
            txtRegDate.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(newVehicle)
                && txtPEDate.getVisibility() == View.VISIBLE) {
            if (TextUtils.isEmpty(policyExpireDate)) {
                txtPEDate.setError("Select Date");
                txtPEDate.requestFocus();
                return false;
            }
        } else if (!newVehicle.equalsIgnoreCase("new_gadi")
                && txtPEDate.getVisibility() == View.VISIBLE) {
            if (TextUtils.isEmpty(policyExpireDate)) {
                txtPEDate.setError("Select Date");
                txtPEDate.requestFocus();
                return false;
            }
        }
        if (newVehicle != null) {
            if (newVehicle.equalsIgnoreCase("new_gadi")) {
                zeroDept = claimExpire = ownerChange = "0";
                policyExpireDate = "";
            }
        }
        return true;
    }

    @Override
    public void onResponse(Object response) {
        try {
            if (response instanceof OtherInformation) {
                OtherInformation vehicleQuote = (OtherInformation) response;
                if (vehicleQuote.getSuccess().equals("1")) {

                    String query = vehicleQuote.getQuery();
                    if (!TextUtils.isEmpty(query))
                        AppUtils.setTokenQuote(query);

                    Intent intent = new Intent(mContext, PremiumBikeActivity.class);
                    Intent intentCar = new Intent(mContext, PremiumActivity.class);

                    mBundle.putString(AppUtils.OWNED_BY, vehicleOwnedBy);
                    mBundle.putString(AppUtils.ZERO_DEPT, zeroDept);
                    mBundle.putString(AppUtils.OWNER_CHANGE, ownerChange);
                    mBundle.putString(AppUtils.CLAIM_EXP, claimExpire);
                    mBundle.putString(AppUtils.PRE_NCB, ncb);
                    mBundle.putString(AppUtils.MANUFACTURE_DATE, manufactureDate);
                    mBundle.putString(AppUtils.REGISTRATION_DATE, registrationDate);
                    mBundle.putString(AppUtils.POLICY_EXPIRY_DATE, policyExpireDate);
                    mBundle.putInt(AppUtils.POLICY_EXPIRY_DAYS, totalDays);
                    mBundle.putBoolean(AppUtils.IS_NCB, isNcb);
                    mBundle.putString(AppUtils.POLICY_TYPE, policyType);

                    intent.putExtras(mBundle);
                    intentCar.putExtras(mBundle);
                    if (vehicleType.equals("1"))
                        startActivityForResult(intent, 555);
                    else startActivityForResult(intentCar, 555);

                    if (!TextUtils.isEmpty(prePolicy))
                        if (prePolicy.equals("1"))
                            this.finish();
                    progressDialog.show();

                } else Toast.makeText(mContext, "" + vehicleQuote.getMsg(),
                        Toast.LENGTH_SHORT).show();
            }
            if (response instanceof SbiMisd) {
                SbiMisd nResponse = (SbiMisd) response;
                if (nResponse.getStatus().equals("1")) {
                    sbiDataList.clear();
                    sbiList.clear();
                    sbiDataList.addAll(nResponse.getSbiData());
                    initRelation();
                }
            }
            progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (data != null) {
                policyType = data.getStringExtra(AppUtils.POLICY_TYPE);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void otherInformation() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                ApiManager.getInstance().updateOtherInformation(mContext, manufactureDate,
                        purchaseDate, registrationDate, policyExpireDate, vehicleOwnedBy,
                        ownerChange, claimExpire, ncb, zeroDept, insertId, sbiCode);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    public void getDateAgo(String policyExpireDate) {
        try {
            Date date = dateFormatter.parse(policyExpireDate);
            Date now = new Date(System.currentTimeMillis());
            long days = 0;
            if (date != null) {
                days = getDateDiff(date, now);
            }
            totalDays = (int) days;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private long getDateDiff(Date date1, Date date2) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    private String dateFormatChanger(String inputDateStr) {
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        return outputFormat.format(date);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void onNcbTextClick(View view) {
        if (view == txt20) setNcb20();
        else if (view == txt25) setNcb25();
        else if (view == txt35) setNcb35();
        else if (view == txt45) setNcb45();
        else if (view == txt50) setNcb50();
        else setNcb0();
    }

    private void setNcb0() {
        ncb = "0";
        txt0.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.selected_border, null));
        txt20.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt25.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt35.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt45.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt50.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        AppUtils.bounceAnim(txt0, 300);
    }

    private void setNcb20() {
        ncb = "20";
        txt0.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt20.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.selected_border, null));
        txt25.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt35.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt45.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt50.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        AppUtils.bounceAnim(txt20, 300);
    }

    private void setNcb25() {
        ncb = "25";
        txt0.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt20.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt25.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.selected_border, null));
        txt35.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt45.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt50.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        AppUtils.bounceAnim(txt25, 300);
    }

    private void setNcb35() {
        ncb = "35";
        txt0.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt20.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt25.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt35.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.selected_border, null));
        txt45.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt50.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        AppUtils.bounceAnim(txt35, 300);
    }

    private void setNcb45() {
        ncb = "45";
        txt0.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt20.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt25.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt35.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt45.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.selected_border, null));
        txt50.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        AppUtils.bounceAnim(txt45, 300);
    }

    private void setNcb50() {
        ncb = "50";
        txt0.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt20.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt25.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt35.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt45.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.img_border, null));
        txt50.setBackground(ResourcesCompat.getDrawable(mContext.getResources(),
                R.drawable.selected_border, null));
        AppUtils.bounceAnim(txt50, 300);
    }

    public void getSbiData() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getSbiData(mContext, insertId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void initRelation() {
        for (int i = 0; i < sbiDataList.size(); i++) {
            String stateName = sbiDataList.get(i).getName().trim();
            String stateId = sbiDataList.get(i).getCode();
            sbiList.add(new String2WithTag(stateName, stateId));
        }

        ArrayAdapter<String2WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, sbiList);
        spnSbi.setAdapter(adapter);

        spnSbi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                sbiCode = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onPrivacyClick(View view) {
        startActivity(new Intent(mContext, PrivacyActivity.class));
    }
}