package com.square.pos.activity.health;

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
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.Health.HealthManager;
import com.square.pos.model_health.v2.HealthQuote;
import com.square.pos.utils.AppUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class MembersAgeActivity extends AbstractActivity implements
        onRequestCompleteCallBackListener {

    private Context mContext;
    private Bundle mBundle;
    private boolean isSelf, isSpouse, isFather, isMother;
    private int totalSon, totalDaughter, selfAge, spouseAge, fatherAge, motherAge, son1Age, son2Age,
            son3Age, son4Age, daughter1Age, daughter2Age, daughter3Age, daughter4Age;
    private TextView selectedTextView, txtSelf, txtSpouse, txtSon1, txtSon2, txtSon3, txtSon4,
            txtDaughter1, txtDaughter2, txtDaughter3, txtDaughter4, txtFather, txtMother;

    private SimpleDateFormat dateFormatter;
     SharedPreferences preferences;
    private boolean isFloater;
     String userId, userType, name, email, phone, gender, pincode, planType, sumAssured,
             healthQuoteId;
    private ProgressDialog progressDialog;
    private String[] memberArray;
     static double Y18 = 5.992e+11;
     static double Y99 = 3.122e+12;
     static double Y25 = 8.199e+11;
    private String calSelfAge, calSpouseAge, calMotherAge, calFatherAge, calSon1Age, calSon2Age,
            calDaughter1Age, calDaughter2Age, medicalHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_age);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        medicalHistory = "No";
        mBundle = getIntent().getBundleExtra("mBundle");
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        isFloater = preferences.getBoolean("isFloater", false);

        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        txtSelf = findViewById(R.id.chkSelf);
        txtSpouse = findViewById(R.id.chkSpouse);
        txtSon1 = findViewById(R.id.chkSon1);
        txtSon2 = findViewById(R.id.chkSon2);
        txtSon3 = findViewById(R.id.chkSon3);
        txtSon4 = findViewById(R.id.chkSon4);

        txtDaughter1 = findViewById(R.id.chkDaughter1);
        txtDaughter2 = findViewById(R.id.chkDaughter2);
        txtDaughter3 = findViewById(R.id.chkDaughter3);
        txtDaughter4 = findViewById(R.id.chkDaughter4);


        txtFather = findViewById(R.id.chkFather);
        txtMother = findViewById(R.id.chkMother);

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Getting Quotations...");

        if (mBundle != null) {
            isSelf = mBundle.getBoolean(AppUtils.IS_SELF, false);
            isSpouse = mBundle.getBoolean(AppUtils.IS_SPOUSE, false);
            isFather = mBundle.getBoolean(AppUtils.IS_FATHER, false);
            isMother = mBundle.getBoolean(AppUtils.IS_MOTHER, false);

            totalSon = mBundle.getInt("totalSon", 0);
            totalDaughter = mBundle.getInt("totalDaughter", 0);

            if (TextUtils.isEmpty(userId)) {
                name = mBundle.getString(AppUtils.HL_Name);
                email = mBundle.getString(AppUtils.HL_EMAIL);
                phone = mBundle.getString(AppUtils.HL_PHONE);
            } else {
                name = preferences.getString(AppUtils.NAME,"");
                email = preferences.getString(AppUtils.EMAIL,"");
                phone = preferences.getString(AppUtils.MOBILE,"");
            }

            gender = mBundle.getString(AppUtils.HL_GENDER);
            pincode = mBundle.getString(AppUtils.PINCODE);
            planType = mBundle.getString(AppUtils.PLAN_TYPE);
            sumAssured = mBundle.getString(AppUtils.SUM_ASSURED);
            memberArray = mBundle.getStringArray(AppUtils.MEMBER_ARRAY);
        }

        if (isSelf)
            findViewById(R.id.rlSelf).setVisibility(View.VISIBLE);
        if (isSpouse)
            findViewById(R.id.rlSpouse).setVisibility(View.VISIBLE);
        if (isFather)
            findViewById(R.id.rf).setVisibility(View.VISIBLE);
        if (isMother)
            findViewById(R.id.rm).setVisibility(View.VISIBLE);

        if (totalSon == 1)
            findViewById(R.id.rs1).setVisibility(View.VISIBLE);
        else if (totalSon == 2) {
            findViewById(R.id.rs1).setVisibility(View.VISIBLE);
            findViewById(R.id.rs2).setVisibility(View.VISIBLE);
        }
//        else if (totalSon == 3) {
//            findViewById(R.id.rs1).setVisibility(View.VISIBLE);
//            findViewById(R.id.rs2).setVisibility(View.VISIBLE);
//            findViewById(R.id.rs3).setVisibility(View.VISIBLE);
//        } else if (totalSon == 4) {
//            findViewById(R.id.rs1).setVisibility(View.VISIBLE);
//            findViewById(R.id.rs2).setVisibility(View.VISIBLE);
//            findViewById(R.id.rs3).setVisibility(View.VISIBLE);
//            findViewById(R.id.rs4).setVisibility(View.VISIBLE);
//        }

        if (totalDaughter == 1)
            findViewById(R.id.rd1).setVisibility(View.VISIBLE);
        else if (totalDaughter == 2) {
            findViewById(R.id.rd1).setVisibility(View.VISIBLE);
            findViewById(R.id.rd2).setVisibility(View.VISIBLE);
        }
//        else if (totalDaughter == 3) {
//            findViewById(R.id.rd1).setVisibility(View.VISIBLE);
//            findViewById(R.id.rd2).setVisibility(View.VISIBLE);
//            findViewById(R.id.rd3).setVisibility(View.VISIBLE);
//        } else if (totalDaughter == 4) {
//            findViewById(R.id.rd1).setVisibility(View.VISIBLE);
//            findViewById(R.id.rd2).setVisibility(View.VISIBLE);
//            findViewById(R.id.rd3).setVisibility(View.VISIBLE);
//            findViewById(R.id.rd4).setVisibility(View.VISIBLE);
//        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onDateClick(View view) {
        selectedTextView = (TextView) view;
        Calendar newCalendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                R.style.datepicker, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                int years = AppUtils.getAge(dateFormatter.format(newDate.getTime()));

                if (selectedTextView == txtSelf) {
                    if (years > 18 && years < 99) {
                        selfAge = years;
                        selectedTextView.setText("" + years + " Years");

                        calSelfAge = AppUtils.SIMPLE_DATE_FORMAT.format(newDate.getTime());

                    } else {
                        Toast.makeText(mContext, "Age limit 18 - 99 Years", Toast.LENGTH_SHORT).show();
                    }
                } else if (selectedTextView == txtSpouse) {
                    if (years > 18 && years < 99) {
                        spouseAge = years;
                        selectedTextView.setText("" + years + " Years");
                        calSpouseAge = AppUtils.SIMPLE_DATE_FORMAT.format(newDate.getTime());
                    } else {
                        Toast.makeText(mContext, "Age limit 18 - 99 Years", Toast.LENGTH_SHORT).show();
                    }
                } else if (selectedTextView == txtFather) {
                    if (years > 18 && years < 99) {
                        fatherAge = years;
                        selectedTextView.setText("" + years + " Years");
                        calFatherAge = AppUtils.SIMPLE_DATE_FORMAT.format(newDate.getTime());
                    } else {
                        Toast.makeText(mContext, "Age limit 18 - 99 Years", Toast.LENGTH_SHORT).show();
                    }
                } else if (selectedTextView == txtMother) {
                    if (years > 18 && years < 99) {
                        motherAge = years;
                        selectedTextView.setText("" + years + " Years");
                        calMotherAge = AppUtils.SIMPLE_DATE_FORMAT.format(newDate.getTime());
                    } else {
                        Toast.makeText(mContext, "Age limit 18 - 99 Years", Toast.LENGTH_SHORT).show();
                    }
                } else if (selectedTextView == txtSon1) {
                    if (years > 0 && years < 26) {
                        son1Age = years;
                        selectedTextView.setText("" + years + " Years");
                        calSon1Age = AppUtils.SIMPLE_DATE_FORMAT.format(newDate.getTime());
                    } else {
                        Toast.makeText(mContext, "Age limit 1 - 25 Years", Toast.LENGTH_SHORT).show();
                    }
                } else if (selectedTextView == txtSon2) {
                    if (years > 0 && years < 26) {
                        son2Age = years;
                        selectedTextView.setText("" + years + " Years");
                        calSon2Age = AppUtils.SIMPLE_DATE_FORMAT.format(newDate.getTime());
                    } else {
                        Toast.makeText(mContext, "Age limit 1 - 25 Years", Toast.LENGTH_SHORT).show();
                    }
                } else if (selectedTextView == txtSon3) {
                    if (years > 0 && years < 26) {
                        son3Age = years;
                        selectedTextView.setText("" + years + " Years");
                    } else {
                        Toast.makeText(mContext, "Age limit 1 - 25 Years", Toast.LENGTH_SHORT).show();
                    }
                } else if (selectedTextView == txtSon4) {
                    if (years > 0 && years < 26) {
                        son4Age = years;
                        selectedTextView.setText("" + years + " Years");
                    } else {
                        Toast.makeText(mContext, "Age limit 1 - 25 Years", Toast.LENGTH_SHORT).show();
                    }
                } else if (selectedTextView == txtDaughter1) {
                    if (years > 0 && years < 26) {
                        daughter1Age = years;
                        selectedTextView.setText("" + years + " Years");
                        calDaughter1Age = AppUtils.SIMPLE_DATE_FORMAT.format(newDate.getTime());
                    } else {
                        Toast.makeText(mContext, "Age limit 1 - 25 Years", Toast.LENGTH_SHORT).show();
                    }
                } else if (selectedTextView == txtDaughter2) {
                    if (years > 0 && years < 26) {
                        daughter2Age = years;
                        selectedTextView.setText("" + years + " Years");
                        calDaughter2Age = AppUtils.SIMPLE_DATE_FORMAT.format(newDate.getTime());
                    } else {
                        Toast.makeText(mContext, "Age limit 1 - 25 Years", Toast.LENGTH_SHORT).show();
                    }
                } else if (selectedTextView == txtDaughter3) {
                    if (years > 0 && years < 26) {
                        daughter3Age = years;
                        selectedTextView.setText("" + years + " Years");
                    } else {
                        Toast.makeText(mContext, "Age limit 1 - 25 Years", Toast.LENGTH_SHORT).show();
                    }
                } else if (selectedTextView == txtDaughter4) {
                    if (years > 0 && years < 26) {
                        daughter4Age = years;
                        selectedTextView.setText("" + years + " Years");
                    } else {
                        Toast.makeText(mContext, "Age limit 1 - 25 Years", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
        if (selectedTextView == txtDaughter1 || selectedTextView == txtDaughter2
                || selectedTextView == txtSon1 || selectedTextView == txtSon2) {
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - ((long) Y25));
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        } else {
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - ((long) Y99));
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - ((long) Y18));
        }

        datePickerDialog.show();
        try {
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorPrimary));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCitySum(View view) {
        if (isValidate())
            medicalDialog();
    }

    private boolean isValidate() {
        if (isSelf) {
            String value = txtSelf.getText().toString();
            if (value.isEmpty()) {
                Toast.makeText(mContext, "Select Age of Self", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (!isFloater) {
            if (isSpouse) {
                String value = txtSpouse.getText().toString();
                if (value.isEmpty()) {
                    Toast.makeText(mContext, "Select Age of Spouse", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
//            if (totalSon == 4) {
//                String value1 = txtSon1.getText().toString();
//                String value2 = txtSon2.getText().toString();
//                String value3 = txtSon3.getText().toString();
//                String value4 = txtSon4.getText().toString();
//                if (value1.isEmpty()) {
//                    Toast.makeText(mContext, "Select Age of Son1", Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//                if (value2.isEmpty()) {
//                    Toast.makeText(mContext, "Select Age of Son2", Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//                if (value3.isEmpty()) {
//                    Toast.makeText(mContext, "Select Age of Son3", Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//                if (value4.isEmpty()) {
//                    Toast.makeText(mContext, "Select Age of Son4", Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//            } else if (totalSon == 3) {
//                String value1 = txtSon1.getText().toString();
//                String value2 = txtSon2.getText().toString();
//                String value3 = txtSon3.getText().toString();
//                if (value1.isEmpty()) {
//                    Toast.makeText(mContext, "Select Age of Son1", Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//                if (value2.isEmpty()) {
//                    Toast.makeText(mContext, "Select Age of Son2", Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//                if (value3.isEmpty()) {
//                    Toast.makeText(mContext, "Select Age of Son3", Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//            } else
            if (totalSon == 2) {
                String value1 = txtSon1.getText().toString();
                String value2 = txtSon2.getText().toString();
                if (value1.isEmpty()) {
                    Toast.makeText(mContext, "Select Age of Son1", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (value2.isEmpty()) {
                    Toast.makeText(mContext, "Select Age of Son2", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else if (totalSon == 1) {
                String value1 = txtSon1.getText().toString();
                if (value1.isEmpty()) {
                    Toast.makeText(mContext, "Select Age of Son1", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }

//            if (totalDaughter == 4) {
//
//                String value1 = txtDaughter1.getText().toString();
//                String value2 = txtDaughter2.getText().toString();
//                String value3 = txtDaughter3.getText().toString();
//                String value4 = txtDaughter4.getText().toString();
//                if (value1.isEmpty()) {
//                    Toast.makeText(mContext, "Select Age of Daughter1", Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//                if (value2.isEmpty()) {
//                    Toast.makeText(mContext, "Select Age of Daughter2", Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//                if (value3.isEmpty()) {
//                    Toast.makeText(mContext, "Select Age of Daughter3", Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//                if (value4.isEmpty()) {
//                    Toast.makeText(mContext, "Select Age of Daughter4", Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//            } else if (totalDaughter == 3) {
//
//                String value1 = txtDaughter1.getText().toString();
//                String value2 = txtDaughter2.getText().toString();
//                String value3 = txtDaughter3.getText().toString();
//                if (value1.isEmpty()) {
//                    Toast.makeText(mContext, "Select Age of Daughter1", Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//                if (value2.isEmpty()) {
//                    Toast.makeText(mContext, "Select Age of Daughter2", Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//                if (value3.isEmpty()) {
//                    Toast.makeText(mContext, "Select Age of Daughter3", Toast.LENGTH_SHORT).show();
//                    return false;
//                }
//
//            } else
            if (totalDaughter == 2) {

                String value1 = txtDaughter1.getText().toString();
                String value2 = txtDaughter2.getText().toString();
                if (value1.isEmpty()) {
                    Toast.makeText(mContext, "Select Age of Daughter1", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (value2.isEmpty()) {
                    Toast.makeText(mContext, "Select Age of Daughter2", Toast.LENGTH_SHORT).show();
                    return false;
                }

            } else if (totalDaughter == 1) {

                String value1 = txtDaughter1.getText().toString();
                if (value1.isEmpty()) {
                    Toast.makeText(mContext, "Select Age of Daughter1", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            if (isFather) {
                String value = txtFather.getText().toString();
                if (value.isEmpty()) {
                    Toast.makeText(mContext, "Select Age of Father", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            if (isMother) {
                String value = txtMother.getText().toString();
                if (value.isEmpty()) {
                    Toast.makeText(mContext, "Select Age of Mother", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        return true;
    }

    public void getHealthQuoteId() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                planType = planType.toLowerCase();
                userType = userType.toLowerCase();

                HealthManager.getInstance().getHealthQuoteId(mContext, userId, userType, name, email,
                        phone, gender, pincode, sumAssured, planType, String.valueOf(selfAge),
                        String.valueOf(spouseAge), String.valueOf(fatherAge),
                        String.valueOf(motherAge), String.valueOf(son1Age),
                        String.valueOf(son2Age), String.valueOf(daughter1Age),
                        String.valueOf(daughter2Age), memberArray, calSelfAge, calSpouseAge,
                        calFatherAge, calMotherAge, calSon1Age, calSon2Age, calDaughter1Age,
                        calDaughter2Age, medicalHistory);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Object response) {

        if (response instanceof HealthQuote) {
            HealthQuote healthQuote = (HealthQuote) response;
            if (healthQuote.getStatus().equalsIgnoreCase("1")) {
                healthQuoteId = healthQuote.getQid();
                if (!TextUtils.isEmpty(healthQuoteId)) {
                    Intent intent = new Intent(mContext, HealthQuotationActivity.class);
                    mBundle.putString(AppUtils.QUOTATION_ID_1, healthQuoteId);

                    mBundle.putString(AppUtils.CAL_SELF, calSelfAge);
                    mBundle.putString(AppUtils.CAL_SPOUSE, calSpouseAge);
                    mBundle.putString(AppUtils.CAL_FATHER, calFatherAge);
                    mBundle.putString(AppUtils.CAL_MOTHER, calMotherAge);
                    mBundle.putString(AppUtils.CAL_SON1, calSon1Age);
                    mBundle.putString(AppUtils.CAL_SON2, calSon2Age);
                    mBundle.putString(AppUtils.CAL_DAUGHTER1, calDaughter1Age);
                    mBundle.putString(AppUtils.CAL_DAUGHTER2, calDaughter2Age);

                    intent.putExtras(mBundle);
                    startActivity(intent);
                }
            } else Toast.makeText(mContext, "" + healthQuote.getMsg(), Toast.LENGTH_SHORT).show();
            progressDialog.hide();
        }
    }

    private void medicalDialog() {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle((R.string.medical_condition));
        alertDialogBuilder.setIcon(R.drawable.ic_alert);
        alertDialogBuilder.setMessage("Any Member Have Medical Conditions?");
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.str_yes,
                        (dialog, id) -> {
                        medicalHistory = "Yes";
                            getHealthQuoteId();
                        })
                .setNegativeButton(R.string.no, (dialog, id) -> {
                    medicalHistory = "No";
                    getHealthQuoteId();
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
