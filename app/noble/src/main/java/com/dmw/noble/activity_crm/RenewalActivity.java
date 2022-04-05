package com.dmw.noble.activity_crm;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.activity_motor.DetailedVehicleActivity;
import com.dmw.noble.activity_motor.OverViewActivity;
import com.dmw.noble.adaptor.crm.RenewalAdaptor;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.CrmManager;
import com.dmw.noble.model_crm.BasicBoolResponse;
import com.dmw.noble.model_crm.CreateRenewal;
import com.dmw.noble.model_crm.RenewData;
import com.dmw.noble.model_crm.RenewalList;
import com.dmw.noble.utils.AppUtils;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

public class RenewalActivity extends AbstractActivity implements
        onRequestCompleteCallBackListener, RenewalAdaptor.OnProceedListener,
        RenewalAdaptor.OnQuickRenewal, RenewalAdaptor.OnDownloadClickListener,
        RenewalAdaptor.OnRenewalAction {

    Context mContext;
    RecyclerView rcAllRenewal;
    String userId, userType, quoteId, regNo, fileType, strDate, strTime, remark, srTableId,
            actionStatus;
    ProgressDialog progressDialog;
    SharedPreferences preferences;
    ArrayList<RenewData> renewalList = new ArrayList<>();
    ArrayList<RenewData> searchList = new ArrayList<>();
    RenewalAdaptor renewalAdaptor;
    Bundle mBundle;
    boolean isQuick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renewal);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);

        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait..");

        mBundle = new Bundle();

        rcAllRenewal = findViewById(R.id.rcAllRenewal);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rcAllRenewal.setLayoutManager(mLayoutManager);
        getRenewalList();

        renewalAdaptor = new RenewalAdaptor(mContext, renewalList);
        rcAllRenewal.setAdapter(renewalAdaptor);
        renewalAdaptor.notifyDataSetChanged();
    }

    public void getRenewalList() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                CrmManager.getInstance().getRenewalList(mContext, userId, userType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void createRenewal() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                CrmManager.getInstance().createRenewal(mContext, userId, userType, quoteId, regNo,
                        fileType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void quickRenewal() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                CrmManager.getInstance().quickRenewal(mContext, userId, userType, quoteId, regNo,
                        fileType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void renewalAction() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                CrmManager.getInstance().renewalAction(mContext, userId, userType, srTableId,
                        actionStatus, strDate, strTime, remark);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof RenewalList) {
            RenewalList pResponse = (RenewalList) response;
            if (pResponse.getStatus()) {
                if (renewalList.size() > 0)
                    renewalList.clear();
                searchList.clear();
                if (pResponse.getData() != null && pResponse.getData().size() > 0) {
                    findViewById(R.id.rlNoLeads).setVisibility(View.GONE);
                    findViewById(R.id.llFil).setVisibility(View.VISIBLE);
                    renewalList.addAll(pResponse.getData());

                    Collections.sort(renewalList, new Comparator<RenewData>() {
                        @Override
                        public int compare(RenewData lhs, RenewData rhs) {
                            return lhs.getExpiryDays().compareTo(rhs.getExpiryDays());
                        }
                    });
                    searchList.addAll(renewalList);
                    renewalAdaptor.notifyDataSetChanged();
                    this.setTitle("Renewal - " + renewalList.size());
                } else {
                    findViewById(R.id.rlNoLeads).setVisibility(View.VISIBLE);
                    findViewById(R.id.llFil).setVisibility(View.GONE);
                }
            } else {
                findViewById(R.id.rlNoLeads).setVisibility(View.VISIBLE);
                findViewById(R.id.llFil).setVisibility(View.GONE);
            }
        }

        if (response instanceof CreateRenewal) {
            CreateRenewal renewal = (CreateRenewal) response;
            if (renewal.getStatus()) {
                String quotationId = renewal.getQuotationId();

                String insertId = renewal.getLastInsertId();
                String vehicle = renewal.getVehicle();
                String make = renewal.getMake();
                String modelName = renewal.getModel();
                String variantName = renewal.getVariant();
                String registrationYear = renewal.getRegistrationYear();
                String prePolicy = renewal.getIsPrevious();
                String previousInsurer = renewal.getQuotationId();
                String fuelType = renewal.getFuelType();
                String vehicleType = renewal.getVehicleType();
                String controlId = renewal.getControllId();
                String pcvCompany = renewal.getCompany();
                String pcvType = renewal.getCompany();
                String sbiCode = renewal.getCompany();
                String newVehicle = renewal.getNewGadi();
                String reg = renewal.getRegistrationNo();
                String prePolicyName = renewal.getPreviousInsurer();
                String purchaseDate = renewal.getPurchaseDate();
                String registrationDate = renewal.getRegistrationDate();
                String manufactureDate = renewal.getManufactureDate();
                String policyExpDate = renewal.getPolicyExpieryDate();
                String policyExp = renewal.getPolicyExpiery();
                String preNcb = renewal.getNcbOld();
                String preClaim = renewal.getClaimExpiringPolicy();
                String ownerChange = renewal.getOwnerChange();
                String tpOnly = renewal.getTpOnly();
                String saodInsurer = renewal.getTpExpirePolicyCpmpany();
                String saodDate = renewal.getTpPolicyExpireDate();
                String saodPolicyNo = renewal.getTpExpirePolicyNumber();

                Intent intent;
                if (isQuick)
                    intent = new Intent(mContext, OverViewActivity.class);
                else
                    intent = new Intent(mContext, DetailedVehicleActivity.class);

                mBundle.putString(AppUtils.QUOTATION_ID, quotationId);
                mBundle.putString(AppUtils.INSERT_ID, insertId);
                mBundle.putString(AppUtils.REGISTRATION_YEAR, registrationYear);
                if (registrationYear != null)
                    mBundle.putInt(AppUtils.REG_YEAR, Integer.parseInt(registrationYear));
                mBundle.putString(AppUtils.NEW_VEHICLE, newVehicle);
                mBundle.putString(AppUtils.VEHICLE_TYPE, vehicleType);
                mBundle.putString(AppUtils.VEHICLE_FULL, vehicle);
                mBundle.putString(AppUtils.REGISTRATION_NUMBER, reg);
                mBundle.putString(AppUtils.IS_PREVIOUS, prePolicy);
                mBundle.putString(AppUtils.PRE_POLICY_TYPE, prePolicyName);
                mBundle.putString(AppUtils.PURCHASE_DATE, purchaseDate);
                mBundle.putString(AppUtils.REGISTRATION_DATE, registrationDate);
                mBundle.putString(AppUtils.MANUFACTURE_DATE, manufactureDate);
                mBundle.putString(AppUtils.POLICY_EXPIRY_DATE, policyExpDate);
                mBundle.putString(AppUtils.POLICY_EXPIRY, policyExp);
                mBundle.putString(AppUtils.PRE_NCB, preNcb);
                mBundle.putString(AppUtils.CLAIM_EXP, preClaim);
                mBundle.putString(AppUtils.OWNER_CHANGE, ownerChange);
                mBundle.putString(AppUtils.TP_ONLY, tpOnly);
                mBundle.putBoolean(AppUtils.OLD_QUOTE, true);
                mBundle.putString(AppUtils.CONTROL_ID, controlId);
                mBundle.putString(AppUtils.SAOD_INSURER, saodInsurer);
                mBundle.putString(AppUtils.SAOD_POLICY_NO, saodPolicyNo);
                mBundle.putString(AppUtils.SAOD_TP_EXP_DATE, saodDate);
                mBundle.putString(AppUtils.INSURER, previousInsurer);
                mBundle.putString(AppUtils.MAKE, make);
                mBundle.putString(AppUtils.MODEL, modelName);
                mBundle.putString(AppUtils.FUEL_TYPE, fuelType);
                mBundle.putString(AppUtils.VARIANT, variantName);
                mBundle.putString(AppUtils.PCV_COMPANY, pcvCompany);
                mBundle.putString(AppUtils.PCV_TYPE, pcvType);
                mBundle.putString(AppUtils.SBI_GCV_CODE, sbiCode);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        }

        if (response instanceof BasicBoolResponse) {
            BasicBoolResponse pResponse = (BasicBoolResponse) response;
            if (pResponse.getStatus()) {
                AppUtils.showToast(mContext, "Action Updated Successfully");
                getRenewalList();
            }
        }
        progressDialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProceedClick(int position) {
        isQuick = false;
        quoteId = renewalList.get(position).getQuotationId();
        regNo = renewalList.get(position).getVehicleNo();
        fileType = renewalList.get(position).getFileType();
        createRenewal();
    }

    @Override
    public void onQuickRenewalClick(int position) {
        isQuick = true;
        quoteId = renewalList.get(position).getQuotationId();
        regNo = renewalList.get(position).getVehicleNo();
        fileType = renewalList.get(position).getFileType();
        if (fileType.equalsIgnoreCase("new")) {
            onViewDialog();
        } else {
            quickRenewal();
        }
    }

    @Override
    public void OnDownloadClicked(int position, String url) {
        try {
            Intent i = new Intent("android.intent.action.MAIN");
            i.setComponent(ComponentName
                    .unflattenFromString("com.android.chrome/com.android.chrome.Main"));
            i.addCategory("android.intent.category.LAUNCHER");
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(i);
        }
    }

    public void onViewDialog() {
        final Dialog dialog = new Dialog(mContext,
                android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        dialog.setContentView(R.layout.layout_otp);
        dialog.setTitle("Registration number");
        TextView txtMobile = dialog.findViewById(R.id.txtMobile);
        TextInputLayout txtTitle = dialog.findViewById(R.id.txtOtp);
        Button btnVerify = dialog.findViewById(R.id.btnVerify);
        final EditText edtOtp = dialog.findViewById(R.id.edtOtp);

        txtMobile.setText("Enter A Valid Vehicle No.");
        txtTitle.setHint("Registration Number");
        edtOtp.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        btnVerify.setText(getString(R.string.proceed));

        btnVerify.setOnClickListener(v -> {
            String reg = edtOtp.getText().toString();

            if (TextUtils.isEmpty(reg)) {
                edtOtp.setError(getString(R.string.field_cannot));
            } else {
                regNo = reg;
                quickRenewal();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onRenewalActionClick(int position, int action) {

        if (action == 1)
            actionStatus = "Follow Up";
        else if (action == 2)
            actionStatus = "Lost";

        srTableId = AppUtils.encode(renewalList.get(position).getSrId());

        final Dialog dialog = new Dialog(mContext);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.renewal_action_dialog);

        TextInputLayout txtDate = dialog.findViewById(R.id.txtDate);
        TextInputLayout txtTime = dialog.findViewById(R.id.txtTime);
        final Button btnUpdate = dialog.findViewById(R.id.btnUpdate);
        final EditText edtDate = dialog.findViewById(R.id.edtDat);
        final EditText edtTime = dialog.findViewById(R.id.edtTime);
        final EditText edtRemark = dialog.findViewById(R.id.edtRemark);

        if (action == 2) {
            txtDate.setVisibility(View.GONE);
            txtTime.setVisibility(View.GONE);
        }

        edtDate.setOnClickListener(v -> {
            onEdtDatePicker(edtDate);
        });

        edtTime.setOnClickListener(v -> {
            onEdtTimePicker(edtTime);
        });

        btnUpdate.setOnClickListener(v -> {
            remark = edtRemark.getText().toString();
            if (action == 1 && TextUtils.isEmpty(strDate)) {
                edtDate.setError(getString(R.string.field_cannot));
            } else if (action == 1 && TextUtils.isEmpty(strTime)) {
                edtTime.setError(getString(R.string.field_cannot));
            } else if (TextUtils.isEmpty(remark))
                edtRemark.setError(getString(R.string.field_cannot));
            else {
                renewalAction();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void onEdtDatePicker(EditText txtSelected) {
        txtSelected.setError(null);

        Calendar mCurrentTime = Calendar.getInstance();
        int year = mCurrentTime.get(Calendar.YEAR);
        int month = mCurrentTime.get(Calendar.MONTH);
        int day = mCurrentTime.get(Calendar.DAY_OF_WEEK);

        DatePickerDialog datePicker = new DatePickerDialog(mContext, (view1, year1, month1,
                                                                      dayOfMonth) -> {
            String d, m;
            month1 = month1 + 1;
            if (month1 < 10)
                m = "0" + month1;
            else m = String.valueOf(month1);
            if (dayOfMonth < 10)
                d = "0" + dayOfMonth;
            else d = String.valueOf(dayOfMonth);
            strDate = "" + d + "-" + m + "-" + year1;
            txtSelected.setText(strDate);
        }, year, month, day);
        datePicker.setTitle("Select Date");
        datePicker.show();
        try {
            datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
            datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorPrimary));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onEdtTimePicker(EditText txtSelected) {
        txtSelected.setError(null);

        Calendar mCurrentTime = Calendar.getInstance();
        int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mCurrentTime.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(mContext, (view11, hourOfDay,
                                                                      minute1) -> {
            strTime = getTime(hourOfDay, minute1);
            txtSelected.setText(strTime);
        }, hour, minute, false);
        timePicker.setTitle("Select Time");
        timePicker.show();
    }

    private String getTime(int hr, int min) {
        Time tme = new Time(hr, min, 0);
        Format formatter;
        formatter = new SimpleDateFormat("hh:mm a", Locale.US);
        return formatter.format(tme);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                searchForTextInList(newText);
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                return true;
            }
        };

        searchView.setOnQueryTextListener(queryTextListener);

        searchView.setOnCloseListener(() -> {
            renewalList.clear();
            renewalList.addAll(searchList);
            renewalAdaptor.notifyDataSetChanged();
            this.setTitle("Renewal - " + renewalList.size());
            return false;
        });
        return true;
    }

    private synchronized void searchForTextInList(String searchString) {
        int textLength = searchString.length();
        renewalList.clear();
        if (searchString.trim().length() == 0) {
            renewalList.addAll(searchList);
        } else {
            searchString = searchString.replaceAll("^\\s+", "");

            for (int i = 0; i < searchList.size(); i++) {
                RenewData assessment = searchList.get(i);
                String userName = "";
                if (!TextUtils.isEmpty(assessment.getVehicleNo()))
                    userName = assessment.getVehicleNo().toLowerCase();
                String contactNo = "";
                if (!TextUtils.isEmpty(assessment.getCustomerName()))
                    contactNo = assessment.getCustomerName();
                if (textLength > 0) {
                    if (userName.contains(searchString) || contactNo.contains(searchString)) {
                        renewalList.add(assessment);
                    }
                }
            }
            renewalAdaptor.notifyDataSetChanged();
            this.setTitle("Renewal - " + renewalList.size());
        }
    }


    public void onTodayClick(View view) {
        renewalList.clear();
        for (int i = 0; i < searchList.size(); i++) {
            RenewData assessment = searchList.get(i);
            if (!TextUtils.isEmpty(assessment.getExpiryDays())
                    && assessment.getExpiryDays().equalsIgnoreCase("today"))
                renewalList.add(assessment);
        }
        renewalAdaptor.notifyDataSetChanged();
        this.setTitle("Renewal - " + renewalList.size());
    }

    public void onTenClick(View view) {
        try {
            renewalList.clear();
            for (int i = 0; i < searchList.size(); i++) {
                RenewData assessment = searchList.get(i);
                if (!TextUtils.isEmpty(assessment.getExpiryDays())) {
                    if (!assessment.getExpiryDays().equalsIgnoreCase("today")) {
                        int total = Integer.parseInt(assessment.getExpiryDays());
                        if (total > 0 && total <= 10) {
                            renewalList.add(assessment);
                        }
                    }
                }
            }
            renewalAdaptor.notifyDataSetChanged();
            this.setTitle("Renewal - " + renewalList.size());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void onTwentyClick(View view) {
        try {
            renewalList.clear();
            for (int i = 0; i < searchList.size(); i++) {
                RenewData assessment = searchList.get(i);
                if (!TextUtils.isEmpty(assessment.getExpiryDays())) {
                    if (!assessment.getExpiryDays().equalsIgnoreCase("today")) {
                        int total = Integer.parseInt(assessment.getExpiryDays());
                        if (total > 0 && total <= 20) {
                            renewalList.add(assessment);
                        }
                    }
                }
            }
            renewalAdaptor.notifyDataSetChanged();
            this.setTitle("Renewal - " + renewalList.size());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
