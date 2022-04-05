package com.square.pos.activity_crm;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.CrmManager;
import com.square.pos.model.BasicResponse;
import com.square.pos.model_crm.EndorseViewData;
import com.square.pos.model_crm.Vehicle;
import com.square.pos.model_crm.VehicleData;
import com.square.pos.utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import in.gauriinfotech.commons.Commons;

public class RaiseEndorsementActivity extends AbstractActivity implements
        onRequestCompleteCallBackListener {

    EditText edtSalutation, edtCustomerName, edtPhone, edtMail, edtGender, edtDob, edtAddress1,
            edtLocation, edtPincode, edtCity, edtState, edtPan, edtGst, edtAadhar, edtRegYear,
            edtEngine, edtChassis, edtCubic, edtCNGIdv, edtPa, edtUnnamed, edtLegal, edtLegalEmp,
            edtNcb, edtFinancierName, edtNomineeName, edtNomineeDob, edtNomineeRelation, edtPolicyNo,
            edtPrePolicyExp, edtPrePolicyNcb, edtPreInsurer;

    String salutation, name, phone, email, gender, dob, address, location, pincode, city, state,
            pan, gst, aadhar, regYear, make, model, variant, fuel, cubic, engine, chassis, cngIdv,
            pa, unnamed, legal, legalEmp, ncb, financier, nomineeName, nomineeDob, nomineeRelation,
            policyNo, prePolicyExp, prePolicyNcb, preInsurer, mNcb, mFinancier, mNomineeName,
            mNomineeDob, mNomineeRelation, mPolicyNo, mPrePolicyExp, mPrePolicyNcb, mPreInsurer,
            mSalutation, mName, mPhone, mEmail, mGender, mDob, mAddress, mLocation, mPincode, mCity,
            mState, mPan, mGst, mAadhar, mRegYear, mMake, mModel, mVariant, mFuel, mCubic, mEngine,
            mChassis, mCngIdv, mPa, mUnnamed, mLegal, mLegalEmp, userId, userType, srId, srNo,
            otherDoc, oldFormData, nameUpdateReason, ncbUpdateReason, rcFront, rcBack, letterDoc,
            sourceType, vehicleType;
    JSONObject formJson;

    EndorseViewData endorseViewData;

    AutoCompleteTextView edtMake, edtModel, edtVariant, edtFuel;

    TextView txtOwner, txtVehicle, txtOther, txtNameChangeReason, txtNCBChangeReason;
    Context mContext;
    ProgressDialog progressDialog;

    final CharSequence[] items = {"Take Photo", "Photos", "Documents"};
    File photoFile = null;
    EditText edtRcFront, edtRcBack, edtLetter, commonTextView, edtOther;
    boolean isOwnerChange, isNcbChange;

    ArrayList<Vehicle> makeList = new ArrayList<>();
    ArrayList<Vehicle> modelList = new ArrayList<>();
    ArrayList<Vehicle> variantList = new ArrayList<>();
    ArrayList<Vehicle> fuelList = new ArrayList<>();

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    boolean isCameraPermitted = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_endorsement);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait..");

        SharedPreferences preferences = getSharedPreferences(String.valueOf(R.string.app_name),
                MODE_PRIVATE);

        nameUpdateReason = ncbUpdateReason = salutation = name = phone = email = gender = dob =
                address = location = pincode = city = state = pan = gst = aadhar = regYear = make =
                        model = variant = fuel = cubic = engine = chassis = cngIdv = pa = unnamed =
                                legal = legalEmp = ncb = financier = nomineeName = nomineeDob =
                                        nomineeRelation = policyNo = prePolicyExp = prePolicyNcb =
                                                preInsurer = "";

        edtRcFront = findViewById(R.id.edtRcFront);
        edtRcBack = findViewById(R.id.edtRcBack);
        edtLetter = findViewById(R.id.edtLetter);
        edtOther = findViewById(R.id.edtOther);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");

        edtSalutation = findViewById(R.id.edtSalutation);
        edtCustomerName = findViewById(R.id.edtCustomerName);
        edtPhone = findViewById(R.id.edtPhone);
        edtMail = findViewById(R.id.edtCsMail);
        edtGender = findViewById(R.id.edtGender);
        edtDob = findViewById(R.id.edtDob);
        edtAddress1 = findViewById(R.id.edtAddress1);
        edtLocation = findViewById(R.id.edtLocation);
        edtPincode = findViewById(R.id.edtPincode);
        edtCity = findViewById(R.id.edtCity);
        edtState = findViewById(R.id.edtState);
        edtPan = findViewById(R.id.edtPan);
        edtGst = findViewById(R.id.edtGst);
        edtAadhar = findViewById(R.id.edtAadhar);
        edtNcb = findViewById(R.id.edtNcb);
        edtFinancierName = findViewById(R.id.edtFinancierName);
        edtNomineeName = findViewById(R.id.edtNomineeName);
        edtNomineeDob = findViewById(R.id.edtNomineeDob);
        edtNomineeRelation = findViewById(R.id.edtNomineeRelation);
        edtPolicyNo = findViewById(R.id.edtPolicyNo);
        edtPrePolicyExp = findViewById(R.id.edtPrePolicyExp);
        edtPrePolicyNcb = findViewById(R.id.edtPrePolicyNcb);
        edtPreInsurer = findViewById(R.id.edtPreInsurer);
        edtRegYear = findViewById(R.id.edtRegYear);
        edtMake = findViewById(R.id.edtMake);
        edtModel = findViewById(R.id.edtModel);
        edtVariant = findViewById(R.id.edtVariant);
        edtFuel = findViewById(R.id.edtFuel);
        edtEngine = findViewById(R.id.edtEngine);
        edtChassis = findViewById(R.id.edtChassis);
        edtCubic = findViewById(R.id.edtCubic);
        edtCNGIdv = findViewById(R.id.edtCNGIdv);
        edtPa = findViewById(R.id.edtPa);
        edtUnnamed = findViewById(R.id.edtUnnamed);
        edtLegal = findViewById(R.id.edtLegal);
        edtLegalEmp = findViewById(R.id.edtLegalEmp);

        txtOwner = findViewById(R.id.txtOwner);
        txtVehicle = findViewById(R.id.txtVehicle);
        txtOther = findViewById(R.id.txtOther);
        txtNameChangeReason = findViewById(R.id.txtNameChangeReason);
        txtNCBChangeReason = findViewById(R.id.txtNCBChangeReason);

        endorseViewData = CrmManager.getInstance().getEndorseViewData();

        if (endorseViewData != null) {

            srId = endorseViewData.getSrId();
            srNo = endorseViewData.getId();
            sourceType = endorseViewData.getSource();
            oldFormData = endorseViewData.getOldString();
            vehicleType = endorseViewData.getGadiType();

            mSalutation = endorseViewData.getSalutation();
            edtSalutation.setText(endorseViewData.getSalutation());

            mName = endorseViewData.getFirstName();
            edtCustomerName.setText(endorseViewData.getFirstName());

            mPhone = endorseViewData.getMobileNumber();
            edtPhone.setText(endorseViewData.getMobileNumber());

            mEmail = endorseViewData.getEmailId();
            edtMail.setText(endorseViewData.getEmailId());

            mGender = endorseViewData.getGender();
            edtGender.setText(endorseViewData.getGender());

            mDob = endorseViewData.getDob();
            edtDob.setText(endorseViewData.getDob());

            mAddress = endorseViewData.getHouseNumber();
            edtAddress1.setText(endorseViewData.getHouseNumber());

            mLocation = endorseViewData.getLocation();
            edtLocation.setText(endorseViewData.getLocation());

            mPincode = endorseViewData.getPincode();
            edtPincode.setText(endorseViewData.getPincode());

            mCity = endorseViewData.getCity();
            edtCity.setText(endorseViewData.getCity());

            mState = endorseViewData.getState();
            edtState.setText(endorseViewData.getState());

            mPan = endorseViewData.getPancardNo();
            edtPan.setText(endorseViewData.getPancardNo());

            mGst = endorseViewData.getGstIn();
            edtGst.setText(endorseViewData.getGstIn());

            mAadhar = endorseViewData.getAadharcardNo();
            edtAadhar.setText(endorseViewData.getAadharcardNo());

            mRegYear = endorseViewData.getRegistrationYear();
            edtRegYear.setText(endorseViewData.getRegistrationYear());

            mMake = endorseViewData.getMake();
            edtMake.setText(endorseViewData.getMake());

            mModel = endorseViewData.getModel();
            edtModel.setText(endorseViewData.getModel());

            mVariant = endorseViewData.getVariant();
            edtVariant.setText(endorseViewData.getVariant());

            mFuel = endorseViewData.getFuelType();
            edtFuel.setText(endorseViewData.getFuelType());

            mEngine = endorseViewData.getEngineNo();
            edtEngine.setText(endorseViewData.getEngineNo());

            mChassis = endorseViewData.getChassisNo();
            edtChassis.setText(endorseViewData.getChassisNo());

            mCubic = endorseViewData.getCubicCapacity();
            edtCubic.setText(endorseViewData.getCubicCapacity());

            mCngIdv = endorseViewData.getCngIdv();
            edtCNGIdv.setText(endorseViewData.getCngIdv());

            mPa = endorseViewData.getPaOwnerDriver();
            edtPa.setText(endorseViewData.getPaOwnerDriver());

            mUnnamed = endorseViewData.getUnnamedPa();
            edtUnnamed.setText(endorseViewData.getUnnamedPa());

            mLegal = endorseViewData.getLegalLiability();
            edtLegal.setText(endorseViewData.getLegalLiability());

            mLegalEmp = endorseViewData.getLegalLiabilityForEmp();
            edtLegalEmp.setText(endorseViewData.getLegalLiabilityForEmp());

            mNcb = endorseViewData.getNcb();
            edtNcb.setText(endorseViewData.getNcb());

            mFinancier = endorseViewData.getFinancierName();
            edtFinancierName.setText(endorseViewData.getFinancierName());

            mNomineeDob = endorseViewData.getNomineeDob();
            edtNomineeDob.setText(endorseViewData.getNomineeDob());

            mNomineeName = endorseViewData.getNomineeFullName();
            edtNomineeName.setText(endorseViewData.getNomineeFullName());

            mNomineeRelation = endorseViewData.getNomineeRelation();
            edtNomineeRelation.setText(endorseViewData.getNomineeRelation());

            mPolicyNo = endorseViewData.getPreviousPolicyNo();
            edtPolicyNo.setText(endorseViewData.getPreviousPolicyNo());

            mPrePolicyExp = endorseViewData.getPreviousPolicyExpiry();
            edtPrePolicyExp.setText(endorseViewData.getPreviousPolicyExpiry());

            mPrePolicyNcb = endorseViewData.getPreviouPolicyNcb();
            edtPrePolicyNcb.setText(endorseViewData.getPreviouPolicyNcb());

            mPreInsurer = endorseViewData.getPreviousPolicyInsurer();
            edtPreInsurer.setText(endorseViewData.getPreviousPolicyInsurer());
            endorsementMake();
        }

        edtCustomerName.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                String str = edtCustomerName.getText().toString();
                if (!isOwnerChange && !str.equals(mName))
                    showOwnerChangeReasonDialog();
            }
        });

        edtNcb.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                String str = edtNcb.getText().toString();
                if (!isNcbChange && !str.equals(mNcb))
                    showNcbChangeReasonDialog();
            }
        });
        checkCameraPermission();
    }


    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    MY_CAMERA_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isCameraPermitted = true;

            } else {
                isCameraPermitted = false;
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setMakeAdaptor() {
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < makeList.size(); i++) {
            list.add(makeList.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list);
        edtMake.setAdapter(adapter);

        edtMake.setOnItemClickListener((adapterView, view, index, arg3) -> {
            make = (String) adapterView.getAdapter().getItem(index);
            endorsementModel();
        });
    }

    private void setModelAdaptor() {
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < modelList.size(); i++) {
            list.add(modelList.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list);
        edtModel.setAdapter(adapter);

        edtModel.setOnItemClickListener((adapterView, view, index, arg3) -> {
            model = (String) adapterView.getAdapter().getItem(index);
            endorsementFuel();
            endorsementVariant();
        });
    }

    private void setVariantAdaptor() {
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < variantList.size(); i++) {
            list.add(variantList.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list);
        edtVariant.setAdapter(adapter);

        edtVariant.setOnItemClickListener((adapterView, view, index, arg3) -> {
            variant = (String) adapterView.getAdapter().getItem(index);
        });
    }

    private void setFuelAdaptor() {
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < fuelList.size(); i++) {
            list.add(fuelList.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, list);
        edtFuel.setAdapter(adapter);

        edtFuel.setOnItemClickListener((adapterView, view, index, arg3) -> {
            fuel = (String) adapterView.getAdapter().getItem(index);
            endorsementVariant();
        });
    }

    private void showOwnerChangeReasonDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(mContext);
        builderSingle.setTitle("Is it an Ownership Change or Name Correction?");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext,
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Name Correction (spelling error, Typo mistake)");
        arrayAdapter.add("Transfer of Ownership from Individual to Individual");
        arrayAdapter.add("Transfer of Ownership from Individual to Company");

        builderSingle.setNegativeButton("cancel",
                (dialog, which) -> {
                    String str = edtCustomerName.getText().toString();
                    if (!str.equals(mName))
                        edtCustomerName.setText(mName);
                    dialog.dismiss();
                });

        builderSingle.setAdapter(arrayAdapter,
                (dialog, which) -> {
                    txtNameChangeReason.setVisibility(View.VISIBLE);
                    txtNameChangeReason.setText(arrayAdapter.getItem(which));
                    nameUpdateReason = arrayAdapter.getItem(which);
                    isOwnerChange = true;
                    dialog.dismiss();

                });
        builderSingle.show();
    }

    private void showNcbChangeReasonDialog() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(mContext);
        builderSingle.setTitle("What kind of NCB change it is?");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext,
                android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Transfer of NCB benefit (from one policy to another)");
        arrayAdapter.add("Incorrect NCB declared at the time of Policy Issuance");

        builderSingle.setNegativeButton("cancel",
                (dialog, which) -> {
                    String str = edtNcb.getText().toString();
                    if (!str.equals(mNcb))
                        edtNcb.setText(mNcb);
                    dialog.dismiss();
                });

        builderSingle.setAdapter(arrayAdapter,
                (dialog, which) -> {
                    txtNCBChangeReason.setVisibility(View.VISIBLE);
                    txtNCBChangeReason.setText(arrayAdapter.getItem(which));
                    ncbUpdateReason = arrayAdapter.getItem(which);
                    isNcbChange = true;
                    dialog.dismiss();

                });
        builderSingle.show();
    }

    public void onOtherDetailClick(View view) {
        findViewById(R.id.layoutOwnerDetail).setVisibility(View.GONE);
        findViewById(R.id.layoutVehicleDetail).setVisibility(View.GONE);
        findViewById(R.id.layoutOtherDetail).setVisibility(View.VISIBLE);
        txtVehicle.setTextColor(getColor(R.color.colorGray));
        txtOwner.setTextColor(getColor(R.color.colorGray));
        txtOther.setTextColor(getColor(R.color.colorPrimaryDark));
    }

    public void onVehicleDetailClick(View view) {
        findViewById(R.id.layoutOwnerDetail).setVisibility(View.GONE);
        findViewById(R.id.layoutVehicleDetail).setVisibility(View.VISIBLE);
        findViewById(R.id.layoutOtherDetail).setVisibility(View.GONE);
        txtOwner.setTextColor(getColor(R.color.colorGray));
        txtOther.setTextColor(getColor(R.color.colorGray));
        txtVehicle.setTextColor(getColor(R.color.colorPrimaryDark));
    }

    public void onOwnerDetailClick(View view) {
        findViewById(R.id.layoutOwnerDetail).setVisibility(View.VISIBLE);
        findViewById(R.id.layoutVehicleDetail).setVisibility(View.GONE);
        findViewById(R.id.layoutOtherDetail).setVisibility(View.GONE);
        txtVehicle.setTextColor(getColor(R.color.colorGray));
        txtOther.setTextColor(getColor(R.color.colorGray));
        txtOwner.setTextColor(getColor(R.color.colorPrimaryDark));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void raiseEndorsement() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                CrmManager.getInstance().raiseEndorsement(mContext, userId, userType, srId, srNo,
                        formJson, oldFormData, nameUpdateReason, ncbUpdateReason, rcFront, rcBack,
                        letterDoc, otherDoc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void endorsementMake() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                CrmManager.getInstance().endorsementMake(mContext, sourceType, vehicleType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void endorsementModel() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                CrmManager.getInstance().endorsementModel(mContext, sourceType, vehicleType, make);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void endorsementVariant() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                CrmManager.getInstance().endorsementVariant(mContext, sourceType, vehicleType,
                        model);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void endorsementFuel() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                CrmManager.getInstance().endorsementFuel(mContext, sourceType, vehicleType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkChanges() {

        salutation = edtSalutation.getText().toString();
        name = edtCustomerName.getText().toString();
        phone = edtPhone.getText().toString();
        email = edtMail.getText().toString();
        gender = edtGender.getText().toString();
        dob = edtDob.getText().toString();
        address = edtAddress1.getText().toString();
        location = edtLocation.getText().toString();
        pincode = edtPincode.getText().toString();
        city = edtCity.getText().toString();
        state = edtState.getText().toString();
        pan = edtPan.getText().toString();
        gst = edtGst.getText().toString();
        aadhar = edtAadhar.getText().toString();
        regYear = edtRegYear.getText().toString();
        make = edtMake.getText().toString();
        model = edtModel.getText().toString();
        variant = edtVariant.getText().toString();
        fuel = edtFuel.getText().toString();
        cubic = edtCubic.getText().toString();
        engine = edtEngine.getText().toString();
        chassis = edtChassis.getText().toString();
        cngIdv = edtCNGIdv.getText().toString();
        pa = edtPa.getText().toString();
        unnamed = edtUnnamed.getText().toString();
        legal = edtLegal.getText().toString();
        legalEmp = edtLegalEmp.getText().toString();
        ncb = edtNcb.getText().toString();
        financier = edtFinancierName.getText().toString();
        nomineeName = edtNomineeName.getText().toString();
        nomineeDob = edtNomineeDob.getText().toString();
        nomineeRelation = edtNomineeRelation.getText().toString();
        policyNo = edtPolicyNo.getText().toString();
        prePolicyExp = edtPrePolicyExp.getText().toString();
        prePolicyNcb = edtPrePolicyNcb.getText().toString();
        preInsurer = edtPreInsurer.getText().toString();

        formJson = new JSONObject();

        if (!TextUtils.isEmpty(salutation))
            if (!mSalutation.equals(salutation)) {
                try {
                    formJson.put("salutation", salutation);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        if (!TextUtils.isEmpty(name))
            if (!mName.equals(name)) {
                try {
                    formJson.put("firstName", name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        if (!TextUtils.isEmpty(phone))
            if (!mPhone.equals(phone)) {
                try {
                    formJson.put("mobileNo", phone);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        if (!TextUtils.isEmpty(email))
            if (!mEmail.equals(email)) {
                try {
                    formJson.put("emailId", email);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        if (!TextUtils.isEmpty(gender))
            if (!mGender.equals(gender)) {
                try {
                    formJson.put("gender", gender);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        if (!TextUtils.isEmpty(dob))
            if (!mDob.equals(dob)) {
                try {
                    formJson.put("dob", dob);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        if (!TextUtils.isEmpty(address))
            if (!mAddress.equals(address)) {
                try {
                    formJson.put("streetNo", address);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        if (!TextUtils.isEmpty(location))
            if (!mLocation.equals(location)) {
                try {
                    formJson.put("location", location);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        if (!mPincode.equals(pincode)) {
            try {
                formJson.put("pincode", pincode);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mCity.equals(city)) {
            try {
                formJson.put("city", city);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mState.equals(state)) {
            try {
                formJson.put("state", state);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mPan.equals(pan)) {
            try {
                formJson.put("pancardNo", pan);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mGst.equals(gst)) {
            try {
                formJson.put("GstIN", gst);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mAadhar.equals(aadhar)) {
            try {
                formJson.put("aadharcardNo", aadhar);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mRegYear.equals(regYear)) {
            try {
                formJson.put("registrationYear", regYear);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (!mMake.equals(make)) {
            try {
                formJson.put("make", make);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mModel.equals(model)) {
            try {
                formJson.put("model", model);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mVariant.equals(variant)) {
            try {
                formJson.put("variant", variant);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mFuel.equals(fuel)) {
            try {
                formJson.put("fuelType", fuel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mCubic.equals(cubic)) {
            try {
                formJson.put("cubicCapacity", cubic);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mEngine.equals(engine)) {
            try {
                formJson.put("engineNo", engine);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mChassis.equals(chassis)) {
            try {
                formJson.put("chassisNo", chassis);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mCngIdv.equals(cngIdv)) {
            try {
                formJson.put("Cng_idv", cngIdv);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mPa.equals(pa)) {
            try {
                formJson.put("paOwnerDriver", pa);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mUnnamed.equals(unnamed)) {
            try {
                formJson.put("unnamedPa", unnamed);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mLegal.equals(legal)) {
            try {
                formJson.put("legalLiability", legal);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mLegalEmp.equals(legalEmp)) {
            try {
                formJson.put("legalLiabilityForEmp", legalEmp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mNcb.equals(ncb)) {
            try {
                formJson.put("ncb", ncb);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mFinancier.equals(financier)) {
            try {
                formJson.put("financierName", financier);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mNomineeName.equals(nomineeName)) {
            try {
                formJson.put("nomineeName", nomineeName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mNomineeDob.equals(nomineeDob)) {
            try {
                formJson.put("nomineeDob", nomineeDob);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mNomineeRelation.equals(nomineeRelation)) {
            try {
                formJson.put("nomineeRelation", nomineeRelation);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mPolicyNo.equals(policyNo)) {
            try {
                formJson.put("previousPolicyNo", policyNo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mPrePolicyExp.equals(prePolicyExp)) {
            try {
                formJson.put("previousPolicyExpiry", prePolicyExp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mPrePolicyNcb.equals(prePolicyNcb)) {
            try {
                formJson.put("previousPolicyNCB", prePolicyNcb);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!mPreInsurer.equals(preInsurer)) {
            try {
                formJson.put("previousInsurer", preInsurer);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (TextUtils.isEmpty(letterDoc)) {
            AppUtils.showToast(mContext, "Request Letter is Mandate");
            return false;
        }
        return true;
    }

    public void onSubmitClick(View view) {
        if (checkChanges())
            raiseEndorsement();

    }

    public void onUploadFile(View view) {
        commonTextView = (EditText) view;
        commonTextView.setError(null);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Upload File!");

        builder.setItems(items, this::onDialogClick);

        builder.show();
    }

    private void onDialogClick(DialogInterface dialog, int item) {
        switch (item) {
            case 0:
                dispatchTakePictureIntent();
                break;
            case 1:
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, AppUtils.REQUEST_CODE_GALLERY);
                break;
            case 2:
                String path = String.valueOf(Environment.getExternalStorageDirectory());
                File file = new File(path);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setDataAndType(Uri.fromFile(file), "*/*");

                try {
                    startActivityForResult(Intent.createChooser(intent, "Select File"), AppUtils.REQUEST_CODE_FILES);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                dialog.dismiss();
                break;
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.square.pos.provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(takePictureIntent, AppUtils.REQUEST_CODE_CAMERA);
            }
        }
    }

    private File createImageFile() throws IOException {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image;

        if (commonTextView == edtRcFront) {
            image = File.createTempFile("Img_RC_FRONT", ".jpg", storageDir);
        } else if (commonTextView == edtRcBack) {
            image = File.createTempFile("Img_RC_Back", ".jpg", storageDir);
        } else if (commonTextView == edtLetter) {
            image = File.createTempFile("Img_Letter", ".jpg", storageDir);
        } else if (commonTextView == edtOther) {
            image = File.createTempFile("Img_Other", ".jpg", storageDir);
        } else {
            image = File.createTempFile("IMG_", ".jpg", storageDir);
        }
        image.renameTo(storageDir);
        return image;
    }

    private String getRealPathFromURIPdf(Context context, Uri uri) {
        String path = null;
        if (isDownloadsDocument(uri) || isExternalStorageDocument(uri)) {
            String[] proj = {MediaStore.MediaColumns.DATA};
            Cursor cursor = getContentResolver().query(uri, proj, null, null,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                path = cursor.getString(column_index);
                if (TextUtils.isEmpty(path))
                    path = uri.getPath();
            }
            assert cursor != null;
            cursor.close();
        } else path = Commons.getPath(uri, context);
        if (path != null) {
            path = path.replace("/document/raw:", "");
        }
        return path;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case AppUtils.REQUEST_CODE_CAMERA:
                    if (true) {//intent != null) {
                        commonTextView.setText(photoFile.getName());
                        if (commonTextView == edtRcFront)
                            rcFront = photoFile.getAbsolutePath();
                        else if (commonTextView == edtRcBack)
                            rcBack = photoFile.getAbsolutePath();
                        else if (commonTextView == edtLetter)
                            letterDoc = photoFile.getAbsolutePath();
                        else if (commonTextView == edtOther)
                            otherDoc = photoFile.getAbsolutePath();
                        else
                            AppUtils.showToast(mContext, "Something went wrong");

                    }
                    break;
                case AppUtils.REQUEST_CODE_GALLERY:
                    if (data != null) {
                        Uri selectedImage = data.getData();
                        if (selectedImage != null) {
                            Uri selectedImageUri = data.getData();
                            commonTextView.setText(AppUtils.getFileName(selectedImageUri, mContext));

                            if (commonTextView == edtRcFront) {
                                rcFront = getRealPathFromURI(selectedImage);
                            } else if (commonTextView == edtRcBack) {
                                rcBack = getRealPathFromURI(selectedImage);
                            } else if (commonTextView == edtLetter) {
                                letterDoc = getRealPathFromURI(selectedImage);
                            } else if (commonTextView == edtOther) {
                                otherDoc = getRealPathFromURI(selectedImage);
                            } else
                                Toast.makeText(mContext, "Some thing went wrong...", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case AppUtils.REQUEST_CODE_FILES:
                    Uri data1 = data.getData();
                    if (commonTextView == edtRcFront)
                        rcFront = getRealPathFromURIPdf(mContext, data1);
                    else if (commonTextView == edtRcBack)
                        rcBack = getRealPathFromURIPdf(mContext, data1);
                    else if (commonTextView == edtLetter)
                        letterDoc = getRealPathFromURIPdf(mContext, data1);
                    else if (commonTextView == edtOther)
                        otherDoc = getRealPathFromURIPdf(mContext, data1);
                    else
                        AppUtils.showToast(mContext, "Something went wrong!!");

                    commonTextView.setText(AppUtils.getFileName(data1, mContext));
                    break;
            }
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null,
                null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToLast();
        return cursor.getString(column_index);
    }


    @Override
    public void onResponse(Object response) {

        if (response instanceof VehicleData) {
            VehicleData pResponse = (VehicleData) response;
            if (pResponse.getStatus()) {
                switch (pResponse.getType()) {
                    case "Make":
                        makeList.clear();
                        makeList.addAll(pResponse.getData());
                        setMakeAdaptor();
                        break;
                    case "Model":
                        modelList.clear();
                        modelList.addAll(pResponse.getData());
                        setModelAdaptor();
                        break;
                    case "Variant":
                        variantList.clear();
                        variantList.addAll(pResponse.getData());
                        setVariantAdaptor();
                        break;
                    case "Fuel":
                        fuelList.clear();
                        fuelList.addAll(pResponse.getData());
                        setFuelAdaptor();
                        break;
                }
            }
        }
        if (response instanceof BasicResponse) {
            BasicResponse bResponse = (BasicResponse) response;
            if (bResponse.getStatus() != null && bResponse.getStatus() == 1) {
                Toast.makeText(mContext, "Request Created Successfully",
                        Toast.LENGTH_SHORT).show();
                finish();
            } else
                AppUtils.showToast(mContext, "" + bResponse.getMsg());
        }
        progressDialog.dismiss();
    }
}