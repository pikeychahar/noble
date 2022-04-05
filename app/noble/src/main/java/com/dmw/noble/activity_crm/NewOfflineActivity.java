package com.dmw.noble.activity_crm;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.adaptor.crm.AddonAdapter;
import com.dmw.noble.adaptor.crm.InsurerAdapter;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.CrmManager;
import com.dmw.noble.model.BasicResponse;
import com.dmw.noble.model_crm.Addon;
import com.dmw.noble.model_crm.AddonList;
import com.dmw.noble.model_crm.InsCompany;
import com.dmw.noble.model_crm.InsurerNewList;
import com.dmw.noble.utils.AppUtils;
import com.dmw.noble.utils.RealPathUtil;
import com.dmw.noble.utils.SearchableSpinner;
import com.dmw.noble.utils.String2WithTag;
import com.dmw.noble.utils.UtilClass;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import in.gauriinfotech.commons.Commons;

public class NewOfflineActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener, InsurerAdapter.OnCheckClickInterface,
        AddonAdapter.OnAddonCheckClickInterface {

    EditText edtCustomerName, edtCustomerPhone, edtCustomerEmail, edtRegDate, edt1, edt2, edt3, edt4,
            edtMinIdv, edtMaxIdv, edtExpDate, edtSelected, edtRemark, edtPN, edtTpExpDat;
    RadioButton btnTw, btnPc, btnPcv, btnGcv, btnMiscD, btnComprehensive, btnTp, btnSAOD,
            btnRollover, btnNew, btnPreferredYes, btnPreferredNo, btnPYPYes, btnPYPNo, btnOCYes,
            btnOCNo, btnNcbYes, btnNcbNo, btnClaimYes, btnClaimNo;
    SearchableSpinner spnPreInsurer, spnTpPreInsurer;
    Spinner spnNcb, spnTenure;
    TextView txtDocRCF, txtDocRCB, txtDocPreviousPolicy, txtDocOther, txtInsurer, txtDocInvoice,
            txtAddons, commonTextView;
    ArrayList<InsCompany> insurerList = new ArrayList<>();
    ArrayList<String2WithTag> piList = new ArrayList<>();
    ArrayList<Addon> addonList = new ArrayList<>();
    Context mContext;
    InsurerAdapter insurerAdapter;
    AddonAdapter addonAdapter;
    SimpleDateFormat dateFormatter;
    Calendar newCalendar;
    final CharSequence[] items = {"Take Photo", "Photos", "Documents"};
    File photoFile = null;
    String mCurrentPhotoPath, rcFront, rcBack, preInsurance, otherDoc, vehicleType, policyTypeId,
            fileType, isBestQuote, customerName, customerMobile, customerEmail, registrationDate,
            regStateCode, regDistrictCode, regCityCode, regCode, minIdv, maxIdv, policyExpDate,
            ownerChange, ncbProtection, claim, covers, previousInsurer, lastYearNcb, invoice,
            tenure, isNewVehicle, isPyp, remark, userId, userType;
    Dialog dialog;
    Dialog addonDialog;
    RecyclerView mRecycler, addRecycler;
    Button btnOk, btnCancel, btnAddonOk, btnAddonCancel;
    List<Integer> checkedList = new ArrayList<>();
    List<Integer> selectedAddonList = new ArrayList<>();
    String requiredInsurer, requiredAddons, tpExtDate, tpPypNo, tpPyp;
    int checked = 0;
    SharedPreferences preferences;
    ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_offline);

        mContext = this;
        initValues();

        regCityCode = regCode = policyExpDate = previousInsurer = lastYearNcb = requiredInsurer
                = requiredAddons = minIdv = maxIdv = remark = tpExtDate = tpPypNo = tpPyp = rcFront
                = rcBack = invoice = preInsurance = otherDoc = "";

        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");

        ownerChange = ncbProtection = claim = "No";
        tenure = policyTypeId = vehicleType = "1";
        isNewVehicle = "Rollover";
        isBestQuote = fileType = "0";
        isPyp = "Yes";

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait...");

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        newCalendar = Calendar.getInstance();
        getPreInsurer();
        getAddons();

        RadioGroup rgVehType = findViewById(R.id.rgVehType);
        rgVehType.setOnCheckedChangeListener((group, checkedId) -> {

            View radioButton = rgVehType.findViewById(checkedId);
            int index = rgVehType.indexOfChild(radioButton);
            btnNew.setVisibility(View.VISIBLE);
            switch (index) {
                case 1:
                    vehicleType = "1";
                    btnSAOD.setVisibility(View.VISIBLE);
                    findViewById(R.id.txtTenure).setVisibility(View.VISIBLE);
                    break;
                case 2:
                    vehicleType = "2";
                    btnSAOD.setVisibility(View.VISIBLE);
                    findViewById(R.id.txtTenure).setVisibility(View.GONE);
                    break;
                case 3:
                    vehicleType = "3";
                    btnComprehensive.setChecked(true);
                    btnSAOD.setVisibility(View.GONE);
                    findViewById(R.id.txtTenure).setVisibility(View.GONE);
                    break;
                case 4:
                    vehicleType = "4";
                    btnComprehensive.setChecked(true);
                    btnSAOD.setVisibility(View.GONE);
                    findViewById(R.id.txtTenure).setVisibility(View.GONE);
                    break;
                case 5:
                    vehicleType = "5";
                    btnComprehensive.setChecked(true);
                    btnSAOD.setVisibility(View.GONE);
                    findViewById(R.id.txtTenure).setVisibility(View.GONE);
                    break;
            }
        });

        RadioGroup rgPolicyType = findViewById(R.id.rvT);
        rgPolicyType.setOnCheckedChangeListener((group, checkedId) -> {

            View radioButton = rgPolicyType.findViewById(checkedId);
            int index = rgPolicyType.indexOfChild(radioButton);
            switch (index) {
                case 1:
                    policyTypeId = "1";
                    btnNew.setVisibility(View.VISIBLE);
                    findViewById(R.id.llSAOD).setVisibility(View.GONE);
                    break;
                case 2:
                    policyTypeId = "2";
                    btnNew.setVisibility(View.VISIBLE);
                    findViewById(R.id.llSAOD).setVisibility(View.GONE);
                    break;
                case 3:
                    policyTypeId = "3";
                    btnRollover.setChecked(true);
                    btnNew.setVisibility(View.GONE);
                    findViewById(R.id.llSAOD).setVisibility(View.VISIBLE);
                    break;
            }
        });

        RadioGroup rgFileType = findViewById(R.id.rvT1);
        rgFileType.setOnCheckedChangeListener((group, checkedId) -> {

            View radioButton = rgFileType.findViewById(checkedId);
            int index = rgFileType.indexOfChild(radioButton);
            switch (index) {
                case 1:
                    fileType = "0";
                    isNewVehicle = "Rollover";
                    txtDocRCF.setVisibility(View.VISIBLE);
                    txtDocRCB.setVisibility(View.VISIBLE);
                    txtDocPreviousPolicy.setVisibility(View.VISIBLE);
                    txtDocInvoice.setVisibility(View.GONE);
                    findViewById(R.id.includePreviousPolicy).setVisibility(View.VISIBLE);
                    break;
                case 2:
                    fileType = "1";
                    isNewVehicle = "New";
                    txtDocRCF.setVisibility(View.GONE);
                    txtDocRCB.setVisibility(View.GONE);
                    txtDocPreviousPolicy.setVisibility(View.GONE);
                    txtDocInvoice.setVisibility(View.VISIBLE);
                    findViewById(R.id.includePreviousPolicy).setVisibility(View.GONE);
                    break;
            }
        });

        RadioGroup rgPreferred = findViewById(R.id.rvPQ);
        rgPreferred.setOnCheckedChangeListener((group, checkedId) -> {

            View radioButton = rgPreferred.findViewById(checkedId);
            int index = rgPreferred.indexOfChild(radioButton);
            switch (index) {
                case 1:
                    isBestQuote = "1";
                    findViewById(R.id.llPQ).setVisibility(View.VISIBLE);
                    findViewById(R.id.llIdv).setVisibility(View.VISIBLE);
                    break;
                case 2:
                    isBestQuote = "0";
                    findViewById(R.id.llPQ).setVisibility(View.GONE);
                    findViewById(R.id.llIdv).setVisibility(View.GONE);
                    break;
            }
        });

        RadioGroup rgPyp = findViewById(R.id.rvPY);
        rgPyp.setOnCheckedChangeListener((group, checkedId) -> {

            View radioButton = rgPyp.findViewById(checkedId);
            int index = rgPyp.indexOfChild(radioButton);
            switch (index) {
                case 1:
                    isPyp = "Yes";
                    findViewById(R.id.rlPre).setVisibility(View.VISIBLE);
                    break;
                case 2:
                    isPyp = "No";
                    findViewById(R.id.rlPre).setVisibility(View.GONE);
                    break;
            }
        });

        RadioGroup rvOC = findViewById(R.id.rvOC);
        rvOC.setOnCheckedChangeListener((group, checkedId) -> {

            View radioButton = rvOC.findViewById(checkedId);
            int index = rvOC.indexOfChild(radioButton);
            switch (index) {
                case 1:
                    ownerChange = "Yes";
                    findViewById(R.id.rlNcb).setVisibility(View.GONE);
                    break;
                case 2:
                    ownerChange = "No";
                    if (claim.equals("No") || (claim.equals("Yes") && ncbProtection.equals("Yes")))
                        findViewById(R.id.rlNcb).setVisibility(View.VISIBLE);
                    break;
            }
        });

        RadioGroup rvCS = findViewById(R.id.rvCS);
        rvCS.setOnCheckedChangeListener((group, checkedId) -> {

            View radioButton = rvCS.findViewById(checkedId);
            int index = rvCS.indexOfChild(radioButton);
            switch (index) {
                case 1:
                    claim = "Yes";
                    if (ncbProtection.equals("No"))
                        findViewById(R.id.rlNcb).setVisibility(View.GONE);
                    break;
                case 2:
                    claim = "No";
                    if (ownerChange.equals("No"))
                        findViewById(R.id.rlNcb).setVisibility(View.VISIBLE);
                    break;
            }
        });

        RadioGroup rvNP = findViewById(R.id.rvNP);
        rvNP.setOnCheckedChangeListener((group, checkedId) -> {

            View radioButton = rvNP.findViewById(checkedId);
            int index = rvNP.indexOfChild(radioButton);
            switch (index) {
                case 1:
                    ncbProtection = "Yes";
                    if (claim.equals("Yes"))
                        findViewById(R.id.rlNcb).setVisibility(View.VISIBLE);
                    break;
                case 2:
                    ncbProtection = "No";
                    if (claim.equals("Yes"))
                        findViewById(R.id.rlNcb).setVisibility(View.GONE);
                    break;
            }
        });

        dialog = new Dialog(mContext, R.style.FullHeightDialog);
        dialog.setContentView(R.layout.layout_selected);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
        }

        mRecycler = dialog.findViewById(R.id.recyclerInsurer);
        btnOk = dialog.findViewById(R.id.btnOk);
        btnCancel = dialog.findViewById(R.id.btnCancel);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setNestedScrollingEnabled(false);

        addonDialog = new Dialog(mContext, R.style.FullHeightDialog);
        addonDialog.setContentView(R.layout.layout_selected);

        if (addonDialog.getWindow() != null) {
            addonDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
        }

        addRecycler = addonDialog.findViewById(R.id.recyclerInsurer);
        btnAddonOk = addonDialog.findViewById(R.id.btnOk);
        btnAddonCancel = addonDialog.findViewById(R.id.btnCancel);

        LinearLayoutManager mLManager = new LinearLayoutManager(mContext);
        addRecycler.setLayoutManager(mLManager);
        addRecycler.setNestedScrollingEnabled(false);

        UtilClass.getInstance().setChecked(0);
    }

    public void onDateClick(View view) {
        edtSelected = (EditText) view;
        AppUtils.checkSoftKeyboard(this);
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                R.style.datepicker, (view1, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            edtSelected.setText(dateFormatter.format(newDate.getTime()));
            edtSelected.setError(null);
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        try {
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorPrimary));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPreInsurer() {
        if (AppUtils.isOnline(mContext)) {
            try {
                CrmManager.getInstance().getNewInsurer(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getAddons() {
        if (AppUtils.isOnline(mContext)) {
            try {
                CrmManager.getInstance().getAddonList(mContext, vehicleType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void initPreInsurer() {
        for (int i = 0; i < insurerList.size(); i++) {
            String modelName = insurerList.get(i).getName();
            String modelId = insurerList.get(i).getId();
            piList.add(new String2WithTag(modelName, modelId));
        }

        ArrayAdapter<String2WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, piList);
        spnPreInsurer.setAdapter(adapter);

        spnTpPreInsurer.setAdapter(adapter);

        spnPreInsurer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                previousInsurer = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnTpPreInsurer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                tpPyp = (String) stringWithTag.tag;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof InsurerNewList) {
            InsurerNewList mResponse = (InsurerNewList) response;
            if (mResponse.getStatus()) {
                if (insurerList.size() > 0) {
                    insurerList.clear();
                    piList.clear();
                }
                insurerList = (ArrayList<InsCompany>) mResponse.getInsCompaines();
                if (insurerList.size() > 0) {
                    initPreInsurer();
                    insurerAdapter = new InsurerAdapter(mContext, insurerList);
                    mRecycler.setAdapter(insurerAdapter);
                    insurerAdapter.notifyDataSetChanged();
                }
            }
        }
        if (response instanceof AddonList) {
            AddonList mResponse = (AddonList) response;
            if (mResponse.getStatus()) {
                if (addonList.size() > 0) {
                    addonList.clear();
                    piList.clear();
                }
                addonList = (ArrayList<Addon>) mResponse.getAddon();
                if (addonList.size() > 0) {
                    addonAdapter = new AddonAdapter(mContext, addonList);
                    addRecycler.setAdapter(addonAdapter);
                    addonAdapter.notifyDataSetChanged();
                }
            }
        }

        if (response instanceof BasicResponse) {
            BasicResponse mResponse = (BasicResponse) response;
            if (mResponse.getStatus().equals(1)) {
                progressdialog.dismiss();

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    public void onInsurersClick(View mView) {
        TextView textView = (TextView) mView;
        if (insurerList.size() > 0)
            insurerAdapter.notifyDataSetChanged();

        btnOk.setOnClickListener(view -> {
            StringBuilder company = new StringBuilder();
            for (int i = 0; i < checkedList.size(); i++) {
                int index = checkedList.get(i);
                company = new StringBuilder(insurerList.get(index).getName() + ", " + company);
            }
            textView.setText(company.toString());
            requiredInsurer = String.valueOf(company);
            dialog.dismiss();
        });
        btnCancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    public void onAddonClick(View mView) {
        TextView textView = (TextView) mView;
        if (addonList.size() > 0)
            addonAdapter.notifyDataSetChanged();

        btnAddonOk.setOnClickListener(view -> {
            StringBuilder company = new StringBuilder();
            for (int i = 0; i < selectedAddonList.size(); i++) {
                int index = selectedAddonList.get(i);
                company = new StringBuilder(addonList.get(index).getName() + ", " + company);
            }
            textView.setText(company.toString());
            requiredAddons = String.valueOf(company);
            addonDialog.dismiss();
        });
        btnAddonCancel.setOnClickListener(view -> {
            addonDialog.dismiss();
        });
        addonDialog.show();
    }

    public void onUploadOfflineDoc(View view) {
        commonTextView = (TextView) view;
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
                        "com.dmw.noble.provider",
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

        if (commonTextView == txtDocRCF) {
            image = File.createTempFile("IMG_RC_FRONT_", ".jpg", storageDir);
        } else if (commonTextView == txtDocRCB) {
            image = File.createTempFile("IMG_RC_BACK_", ".jpg", storageDir);
        } else if (commonTextView == txtDocPreviousPolicy) {
            image = File.createTempFile("IMG_PREVIOUS_POLICY_", ".jpg", storageDir);
        } else if (commonTextView == txtDocOther) {
            image = File.createTempFile("IMG_", ".jpg", storageDir);
        } else {
            image = File.createTempFile("IMG_", ".jpg", storageDir);
        }
        mCurrentPhotoPath = image.getAbsolutePath();
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

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case AppUtils.REQUEST_CODE_CAMERA:
                    if (true) {//intent != null) {
                        commonTextView.setText(photoFile.getName());
                        if (commonTextView == txtDocRCF)
                            rcFront = photoFile.getAbsolutePath();
                        else if (commonTextView == txtDocRCB)
                            rcBack = photoFile.getAbsolutePath();
                        else if (commonTextView == txtDocPreviousPolicy)
                            preInsurance = photoFile.getAbsolutePath();
                        else if (commonTextView == txtDocInvoice)
                            invoice = photoFile.getAbsolutePath();
                        else if (commonTextView == txtDocOther)
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

                            if (commonTextView == txtDocRCF)
                                rcFront = getRealPathFromURI(selectedImage);
                            else if (commonTextView == txtDocRCB)
                                rcBack = getRealPathFromURI(selectedImage);
                            else if (commonTextView == txtDocPreviousPolicy)
                                preInsurance = getRealPathFromURI(selectedImage);
                            else if (commonTextView == txtDocInvoice)
                                invoice = getRealPathFromURI(selectedImage);
                            else if (commonTextView == txtDocOther)
                                otherDoc = getRealPathFromURI(selectedImage);
                            else
                                Toast.makeText(mContext, "Some thing went wrong...", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;

                case AppUtils.REQUEST_CODE_FILES:
                    Uri data1 = data.getData();
                    if (commonTextView == txtDocRCF)
                        rcFront = RealPathUtil.getRealPath(mContext, data1);
                    else if (commonTextView == txtDocRCB)
                        rcBack = RealPathUtil.getRealPath(mContext, data1);
                    else if (commonTextView == txtDocPreviousPolicy)
                        preInsurance = RealPathUtil.getRealPath(mContext, data1);
                    else if (commonTextView == txtDocInvoice)
                        invoice = RealPathUtil.getRealPath(mContext, data1);
                    else if (commonTextView == txtDocOther)
                        otherDoc = RealPathUtil.getRealPath(mContext, data1);
                    else {
                        AppUtils.showToast(mContext, "Something went wrong");
                        commonTextView.setText(AppUtils.getFileName(data1, mContext));
                    }
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
    public void onCheckChange(int position, boolean status) {
        if (status) {
            if (checked < 5) {
                ++checked;
                checkedList.add(position);
            }
        } else {
            if (checked > 0) {
                for (int i = 0; i < checkedList.size(); i++) {
                    if (checkedList.get(i) == position) {
                        checkedList.remove(i);
                        break;
                    }
                }
                --checked;
            }
        }
        UtilClass.getInstance().setChecked(checked);
    }

    private void initValues() {
        edtCustomerName = findViewById(R.id.edtCustomerName);
        edtCustomerPhone = findViewById(R.id.edtCustomerPhone);
        edtCustomerEmail = findViewById(R.id.edtCustomerEmail);

        edt1 = findViewById(R.id.edt1);
        edt2 = findViewById(R.id.edt2);
        edt3 = findViewById(R.id.edt3);
        edt4 = findViewById(R.id.edt4);
        edtRegDate = findViewById(R.id.edtRegDate);
        edtMinIdv = findViewById(R.id.edtMinIdv);
        edtMaxIdv = findViewById(R.id.edtMaxIdv);
        edtRemark = findViewById(R.id.edtRemark);
        edtPN = findViewById(R.id.edtPN);
        edtTpExpDat = findViewById(R.id.edtTpExpDat);

        btnTw = findViewById(R.id.btnTw);
        btnPc = findViewById(R.id.btnPc);
        btnPcv = findViewById(R.id.btnPcv);
        btnGcv = findViewById(R.id.btnGcv);
        btnMiscD = findViewById(R.id.btnMiscD);

        btnComprehensive = findViewById(R.id.btnComprehensive);
        btnTp = findViewById(R.id.btnTp);
        btnSAOD = findViewById(R.id.btnSAOD);
        btnOCYes = findViewById(R.id.btnOCYes);
        btnOCNo = findViewById(R.id.btnOCNo);
        btnNcbYes = findViewById(R.id.btnNcbYes);
        btnNcbNo = findViewById(R.id.btnNcbNo);
        btnClaimYes = findViewById(R.id.btnClaimYes);
        btnClaimNo = findViewById(R.id.btnClaimNo);

        btnRollover = findViewById(R.id.btnRollover);
        btnNew = findViewById(R.id.btnNew);

        btnPreferredYes = findViewById(R.id.btnPreferredYes);
        btnPreferredNo = findViewById(R.id.btnPreferredNo);

        btnPYPYes = findViewById(R.id.btnPYPYes);
        btnPYPNo = findViewById(R.id.btnPYPNo);
        edtExpDate = findViewById(R.id.edtPED);

        txtInsurer = findViewById(R.id.spnInsurer);
        txtAddons = findViewById(R.id.spnCover);
        spnPreInsurer = findViewById(R.id.spnPreInsurer);
        spnTpPreInsurer = findViewById(R.id.edtTpPreInsurer);
        spnNcb = findViewById(R.id.spnNcb);
        spnTenure = findViewById(R.id.spnTenure);

        txtDocRCF = findViewById(R.id.txtDocRCF);
        txtDocRCB = findViewById(R.id.txtDocRCB);
        txtDocOther = findViewById(R.id.txtDocOther);
        txtDocPreviousPolicy = findViewById(R.id.txtDocPreviousPolicy);
        txtDocInvoice = findViewById(R.id.txtDocInvoice);
    }

    private boolean isValid() {
//rcFront, rcBack, preInsurance, otherDoc, vehicleType, policyTypeId,fileType, isBestQuote, previousPolicy
//  ownerChange, ncbProtection, claim, covers, previousInsurer,

        customerName = edtCustomerName.getText().toString();
        customerMobile = edtCustomerPhone.getText().toString();
        customerEmail = edtCustomerEmail.getText().toString();
        registrationDate = edtRegDate.getText().toString();
        regStateCode = edt1.getText().toString();
        regDistrictCode = edt2.getText().toString();
        regCityCode = edt3.getText().toString();
        regCode = edt4.getText().toString();
        minIdv = edtMinIdv.getText().toString();
        maxIdv = edtMaxIdv.getText().toString();
        policyExpDate = edtExpDate.getText().toString();
        remark = edtRemark.getText().toString();
        tpPypNo = edtPN.getText().toString();
        tpExtDate = edtTpExpDat.getText().toString();

        lastYearNcb = spnNcb.getSelectedItem().toString().replaceAll("[^0-9]", "");

        if (TextUtils.isEmpty(customerName)) {
            edtCustomerName.setError(getString(R.string.field_cannot));
            edtCustomerName.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(customerMobile)) {
            edtCustomerPhone.setError(getString(R.string.field_cannot));
            edtCustomerPhone.requestFocus();
        } else if (!AppUtils.isValidMobile(customerMobile)) {
            edtCustomerPhone.setError(getString(R.string.invalid_phone));
            edtCustomerPhone.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(customerEmail)) {
            edtCustomerEmail.setError(getString(R.string.field_cannot));
            edtCustomerEmail.requestFocus();
            return false;
        } else if (!AppUtils.isValidMail(customerEmail)) {
            edtCustomerEmail.setError(getString(R.string.invalid_phone));
            edtCustomerEmail.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(registrationDate)) {
            edtRegDate.setError(getString(R.string.field_cannot));
            edtRegDate.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(regStateCode)) {
            edt1.setError(getString(R.string.field_cannot));
            edt1.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(regDistrictCode)) {
            edt2.setError(getString(R.string.field_cannot));
            edt2.requestFocus();
            return false;
        }

        registrationDate = AppUtils.YYYYMMDD(registrationDate);

        if (isPyp.equals("Yes")) {
            if (TextUtils.isEmpty(policyExpDate)) {
                edtExpDate.setError(getString(R.string.field_cannot));
                edtExpDate.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(previousInsurer)) {
                AppUtils.showToast(mContext, "Select Previous Insurer");
                return false;
            } else if (TextUtils.isEmpty(preInsurance)) {
                txtDocPreviousPolicy.setError(getString(R.string.field_cannot));
                return false;
            }
            policyExpDate = AppUtils.YYYYMMDD(policyExpDate);
        }

        if (isBestQuote.equals("1")) {
            if (TextUtils.isEmpty(requiredInsurer)) {
                AppUtils.showToast(mContext, "Select Minimum Insurer");
                return false;
            } else if (TextUtils.isEmpty(requiredAddons)) {
                AppUtils.showToast(mContext, "Select Minimum Addons");
                return false;
            } else if (TextUtils.isEmpty(minIdv)) {
                edtMinIdv.setError(getString(R.string.field_cannot));
                edtMinIdv.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(maxIdv)) {
                edtMaxIdv.setError(getString(R.string.field_cannot));
                edtMaxIdv.requestFocus();
                return false;
            }
        }

        if (policyTypeId.equals("3")) {
            if (TextUtils.isEmpty(tpPyp)) {
                AppUtils.showToast(mContext, "Select TP Insurer");
                return false;
            } else if (TextUtils.isEmpty(tpExtDate)) {
                edtTpExpDat.setError(getString(R.string.field_cannot));
                edtTpExpDat.requestFocus();
                return false;
            } else if (TextUtils.isEmpty(tpPypNo)) {
                edtPN.setError(getString(R.string.field_cannot));
                edtPN.requestFocus();
                return false;
            }
            tpExtDate = AppUtils.YYYYMMDD(tpExtDate);
        }

        if (vehicleType.equals("1") && spnTenure.getSelectedItem() != null) {
            if (spnTenure.getSelectedItemPosition() == 0)
                tenure = "1";
            else if (spnTenure.getSelectedItemPosition() == 1)
                tenure = "2";
            else tenure = "3";
        } else {
            tenure = "1";
        }

        if (fileType.equals("0")) {
            if (TextUtils.isEmpty(rcFront)) {
                AppUtils.showToast(mContext, "Choose RC Front");
                return false;
            }
            if (isPyp.equals("1") && TextUtils.isEmpty(preInsurance)) {
                AppUtils.showToast(mContext, "Choose Previous Policy");
                return false;
            }
        } else if (fileType.equals("1")) {
            if (TextUtils.isEmpty(invoice)) {
                AppUtils.showToast(mContext, "Choose Invoice");
                return false;
            }
        }

        return true;
    }

    public void onSubmitOfflineQuote(View view) {
        if (isValid())
            raiseOffline();
    }

    @Override
    public void onAddonCheckChange(int position, boolean status) {
        if (status)
            selectedAddonList.add(position);
        else {
            for (int i = 0; i < selectedAddonList.size(); i++) {
                if (selectedAddonList.get(i) == position) {
                    selectedAddonList.remove(i);
                    break;
                }
            }
        }
    }

    public void raiseOffline() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                CrmManager.getInstance().raiseOffline(mContext, userId, userType, customerName,
                        customerEmail, customerMobile, vehicleType, policyTypeId, isNewVehicle,
                        registrationDate, regStateCode, regDistrictCode, regCityCode, regCode,
                        isPyp, policyExpDate, claim, ownerChange, ncbProtection, previousInsurer,
                        lastYearNcb, isBestQuote, requiredInsurer, requiredAddons, minIdv, maxIdv,
                        remark, tenure, tpExtDate, tpPypNo, tpPyp, rcFront, rcBack, invoice,
                        preInsurance, otherDoc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }
}

