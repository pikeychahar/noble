package com.square.pos.activity_motor;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.ApiManager;
import com.square.pos.manager.UserManager;
import com.square.pos.model.MotorKyc;
import com.square.pos.model.Nominee;
import com.square.pos.model.fetch_detail.GetNominee;
import com.square.pos.model.master.NomineeRelation;
import com.square.pos.model.master.NomineeStatus;
import com.square.pos.utils.AppUtils;
import com.square.pos.utils.String2WithTag;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class NomineeDetailActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {

    private Context mContext;
    private com.toptoche.searchablespinnerlibrary.SearchableSpinner spnRelation;
    List<NomineeRelation> relationList = new ArrayList<>();
    List<String2WithTag> relations = new ArrayList<>();
    private EditText edtDOB, edtNomineeName;
    String dob, formattedDob, age, nomineeName, gender, relationId, relation, relationName,
            quotationId, company;
    private ProgressDialog progressdialog;
    private Bundle mBundle;
    RadioButton btnMale, btnFemale;
    SimpleDateFormat dateFormatter;
    final CharSequence[] items = {"Take Photo", "Photos"};

    File photoFile = null;
    TextView commonTextView, txtPan, txtCheque, txtAadharFront, txtAadharBack, txtElectricBill,
            txtCertificate, txtPhoto;
    String docPan, docCheque, docAadharFront, docAadharBack, docElectric, docCertificate, docPhoto,
            mCurrentPhotoPath;
    boolean isKycChecked;
    CheckBox checkKyc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nominee_detail);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        mBundle = getIntent().getExtras();
        if (mBundle != null) {
            quotationId = mBundle.getString(AppUtils.QUOTATION_ID);
            company = mBundle.getString(AppUtils.COMPANY_NAME);
        }

        spnRelation = findViewById(R.id.spnRelationShip);
        edtDOB = findViewById(R.id.edtDob);
        btnMale = findViewById(R.id.btnMale);
        btnFemale = findViewById(R.id.btnFemale);

        txtPan = findViewById(R.id.txtPan);
        txtCheque = findViewById(R.id.txtCheque);
        txtAadharFront = findViewById(R.id.txtAadharFront);
        txtAadharBack = findViewById(R.id.txtAadharBack);
        txtElectricBill = findViewById(R.id.txtElectricBill);
        txtCertificate = findViewById(R.id.txtCertificate);
        txtPhoto = findViewById(R.id.txtPhoto);

        checkKyc = findViewById(R.id.checkKyc);

        gender = "male";
        fetchNominee();
        getRelationshipList();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    }

    private void initRelation() {
        for (int i = 0; i < relationList.size(); i++) {
            String stateName = relationList.get(i).getName().trim();
            String stateId = relationList.get(i).getCode();
            relations.add(new String2WithTag(stateName, stateId));
        }

        ArrayAdapter<String2WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, relations);
        spnRelation.setAdapter(adapter);

        if (!TextUtils.isEmpty(relation))
            for (int i = 0; i < relationList.size(); i++) {
                String stateName = relationList.get(i).getName().trim();
                if (stateName.equalsIgnoreCase(relation)) {
                    spnRelation.setSelection(i);
                    break;
                }
            }

        spnRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                relationId = (String) stringWithTag.tag;
                relation = (String) stringWithTag.string;
                relationName = (String) stringWithTag.string;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initValues() {

        nomineeName = AppUtils.trimString(edtNomineeName);
        dob = age = edtDOB.getText().toString();

        if (spnRelation.getSelectedItem() != null)
            relation = spnRelation.getSelectedItem().toString();
        if (btnFemale.isChecked())
            gender = "female";
        else gender = "male";
    }

    private boolean checkValidation() {
        initValues();
        if (TextUtils.isEmpty(nomineeName)) {
            edtNomineeName.requestFocus();
            edtNomineeName.setError("Field can not be empty");
            return false;
        }
        if (!AppUtils.validateName(nomineeName)) {
            edtNomineeName.setError("Invalid Name");
            edtNomineeName.requestFocus();
            return false;
        }
        if (!TextUtils.isEmpty(dob)) {
            if (AppUtils.validDate(dob)) {
                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Date date = null;
                try {
                    date = formatter.parse(dob);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
                formattedDob = newFormat.format(date);
            } else {
                edtDOB.setError("Invalid Date");
                edtDOB.requestFocus();
                return false;
            }
        } else {
            edtDOB.setError("Invalid Date");
            edtDOB.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(relation)) {
            Toast.makeText(mContext, "Select Nominee Relation", Toast.LENGTH_SHORT).show();
            ((TextView) spnRelation.getSelectedView()).setError("Select Value");

            return false;
        }

        if (!TextUtils.isEmpty(relation)) {
            if (relation.equalsIgnoreCase("Father")
                    || relation.equalsIgnoreCase("Brother")
                    || relation.equalsIgnoreCase("uncle")
                    || relation.equalsIgnoreCase("son")) {
                if (gender.equalsIgnoreCase("female")) {
                    btnMale.setChecked(true);
                    gender = "male";
                }
            } else if (relation.equalsIgnoreCase("Mother")
                    || relation.equalsIgnoreCase("sister")
                    || relation.equalsIgnoreCase("Aunty")
                    || relation.equalsIgnoreCase("daughter")) {
                if (gender.equalsIgnoreCase("male")) {
                    btnFemale.setChecked(true);
                    gender = "female";
                }
            }
        }
        nomineeName = nomineeName.replaceAll("\\s{2,}", " ").trim();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onNomineeFinish(View view) {
        if (checkValidation())
            submitNominee();
    }

    public void submitNominee() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.setMessage("Almost Done....");
            progressdialog.show();
            try {
                if (company.equalsIgnoreCase("iffco")
                        || company.equalsIgnoreCase("sbi")
                        || company.equalsIgnoreCase("raheja")
                        || company.equalsIgnoreCase("future"))

                    relation = relationId;

                ApiManager.getInstance().nomineeDetail(mContext, nomineeName, relation, gender,
                        formattedDob, "1", quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }

    @Override
    public void onResponse(Object response) {
        try {
            if (response instanceof Nominee) {
                Nominee nResponse = (Nominee) response;
                if (nResponse.getSuccess().equalsIgnoreCase("1")) {
                    mBundle.putString(AppUtils.NOMINEE_NAME, nomineeName);
                    mBundle.putString(AppUtils.NOMINEE_RELATION, relationName);
                    mBundle.putString(AppUtils.NOMINEE_AGE, age);
                    Intent intent = new Intent(mContext, OverViewActivity.class);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }
            }
            if (response instanceof NomineeStatus) {
                NomineeStatus nResponse = (NomineeStatus) response;
                if (nResponse.getStatus().equalsIgnoreCase("1")) {
                    if (relationList.size() > 0)
                        relationList.clear();
                    relationList.addAll(nResponse.getNomineeRelation());
                    initRelation();
                }
            }
            if (response instanceof GetNominee) {
                GetNominee nResponse = (GetNominee) response;
                if (nResponse.getSuccess().equalsIgnoreCase("1")) {

                    nomineeName = nResponse.getNomineeName();
                    dob = nResponse.getNomineeDob();
                    gender = nResponse.getNomineeGender();
                    relation = nResponse.getNomineeRelation();

                    if (!TextUtils.isEmpty(dob)) {
                        dob = AppUtils.ddMMYYYY(dob);
                        edtDOB.setText(dob);
                    }

                    if (!TextUtils.isEmpty(relation) && relationList.size() > 0)
                        initRelation();

                    edtNomineeName = findViewById(R.id.edtNomineeName);
                    if (!TextUtils.isEmpty(nomineeName))
                        edtNomineeName.setText(nomineeName);

                    if (!TextUtils.isEmpty(gender)) {
                        if (gender.toLowerCase().equals("female")) {
                            btnFemale.setChecked(true);
                            gender = "female";
                        }
                    }
                }
            }
            if (response instanceof MotorKyc) {
                MotorKyc docResponse = (MotorKyc) response;
                if (docResponse.getSuccess().equals("1")) {
                    findViewById(R.id.rlKyc).setVisibility(View.GONE);
                    checkKyc.setChecked(false);
                    showKycDialog();
                }
            }
            progressdialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getRelationshipList() {
        if (AppUtils.isOnline(mContext)) {
            try {
                ApiManager.getInstance().getNomineeRelation(mContext, "nominee_relation",
                        company);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void fetchNominee() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.setMessage("Fetching Data....");
            progressdialog.show();
            try {
                ApiManager.getInstance().fetchNominee(mContext, quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void onDateClick(View view) {
        AppUtils.checkSoftKeyboard(this);
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.add(Calendar.YEAR, -18);
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                R.style.datepicker, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                edtDOB.setText(dateFormatter.format(newDate.getTime()));
                edtDOB.setError(null);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(newCalendar.getTimeInMillis());
        datePickerDialog.show();
        try {
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorPrimary));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case AppUtils.REQUEST_CODE_CAMERA:
                    commonTextView.setText(photoFile.getName());

                    if (commonTextView == txtPan)
                        docPan = photoFile.getAbsolutePath();
                    else if (commonTextView == txtCheque)
                        docCheque = photoFile.getAbsolutePath();
                    else if (commonTextView == txtAadharFront)
                        docAadharFront = photoFile.getAbsolutePath();
                    else if (commonTextView == txtAadharBack)
                        docAadharBack = photoFile.getAbsolutePath();
                    else if (commonTextView == txtCertificate)
                        docCertificate = photoFile.getAbsolutePath();
                    else if (commonTextView == txtElectricBill)
                        docElectric = photoFile.getAbsolutePath();
                    else if (commonTextView == txtPhoto)
                        docPhoto = photoFile.getAbsolutePath();
                    else
                        AppUtils.showToast(mContext, "Something went wrong");
                    break;
                case AppUtils.REQUEST_CODE_GALLERY:
                    if (data != null) {
                        Uri selectedImage = data.getData();
                        if (selectedImage != null) {
                            Uri selectedImageUri = data.getData();
                            commonTextView.setText(AppUtils.getFileName(selectedImageUri, mContext));

                            if (commonTextView == txtPan) {
                                docPan = getRealPathFromURI(selectedImage);
                            } else if (commonTextView == txtCheque)
                                docCheque = getRealPathFromURI(selectedImage);
                            else if (commonTextView == txtAadharFront)
                                docAadharFront = getRealPathFromURI(selectedImage);
                            else if (commonTextView == txtAadharBack)
                                docAadharBack = getRealPathFromURI(selectedImage);
                            else if (commonTextView == txtCertificate)
                                docCertificate = getRealPathFromURI(selectedImage);
                            else if (commonTextView == txtElectricBill)
                                docElectric = getRealPathFromURI(selectedImage);
                            else if (commonTextView == txtPhoto)
                                docPhoto = getRealPathFromURI(selectedImage);
                            else
                                AppUtils.showToast(mContext, "Some thing went wrong...");
                        }
                    }
                    break;
            }
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToLast();
        return cursor.getString(column_index);
    }

    public void onUploadKycDoc(View view) {
        commonTextView = (TextView) view;
        commonTextView.setError(null);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Upload Photo!");
        builder.setItems(items, this::onDialogClick);
        builder.show();
    }

    private void onDialogClick(DialogInterface dialog, int item) {
        switch (item) {
            case 0:
                dispatchTakePictureIntent();
                break;
            case 1:
                Intent i = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, AppUtils.REQUEST_CODE_GALLERY);
                break;
            case 2:
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
                        "com.insure.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(takePictureIntent, AppUtils.REQUEST_CODE_CAMERA);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image;

        if (commonTextView == txtPan) {
            image = File.createTempFile("PAN_", ".jpg", storageDir
            );
        } else if (commonTextView == txtCheque) {
            image = File.createTempFile("CHEQUE_", ".jpg", storageDir);
        } else if (commonTextView == txtAadharFront) {
            image = File.createTempFile("FRONT_", ".jpg", storageDir);
        } else if (commonTextView == txtAadharBack) {
            image = File.createTempFile("BACK_", ".jpg", storageDir);
        } else if (commonTextView == txtCertificate) {
            image = File.createTempFile("CER_", ".jpg", storageDir);
        } else if (commonTextView == txtElectricBill) {
            image = File.createTempFile("ELE_", ".jpg", storageDir);
        } else if (commonTextView == txtPhoto) {
            image = File.createTempFile("PHOTO_", ".jpg", storageDir);
        } else {
            image = File.createTempFile("Img_", ".jpg", storageDir);
        }

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        assert storageDir != null;
        image.renameTo(storageDir);
        return image;
    }

    public void onDocumentUploadClick(View view) {
        if (checkValidKyc())
            uploadKycDocument();

    }

    public void onKycCheck(View view) {
        CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked())
            findViewById(R.id.rlKyc).setVisibility(View.VISIBLE);
        else findViewById(R.id.rlKyc).setVisibility(View.GONE);
        isKycChecked = checkBox.isChecked();
    }

    private boolean checkValidKyc() {
        //validation of PAN or Cheque
        if (TextUtils.isEmpty(docPan) && TextUtils.isEmpty(docCheque)) {
            txtCheque.setError(getString(R.string.select_document));
            txtPan.setError(getString(R.string.select_document));
            AppUtils.showToast(mContext, "Select Minimum One Document!!");
            return false;
        }

        //Optional Document Validation if User select Aadhar
        if (!TextUtils.isEmpty(docAadharFront) && TextUtils.isEmpty(docAadharBack)) {
            txtAadharBack.setError(getString(R.string.select_document));
            AppUtils.showToast(mContext, "Aadhar Card Back Required!!");
            return false;
        }
        if (TextUtils.isEmpty(docAadharFront) && !TextUtils.isEmpty(docAadharBack)) {
            txtAadharFront.setError(getString(R.string.select_document));
            AppUtils.showToast(mContext, "Aadhar Card Front Required!!");
            return false;
        }
        return true;
    }

    public void uploadKycDocument() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.setMessage("Uploading Documents....");
            progressdialog.show();
            try {
                UserManager.getInstance().uploadKycDocument(mContext, docPan, docCheque,
                        docAadharFront, docAadharBack, docCertificate, docElectric, docPhoto,
                        quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void showKycDialog() {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle("KYC Documents");
        alertDialogBuilder.setIcon(R.drawable.logo);
        alertDialogBuilder.setMessage("Your KYC document Uploaded Successfully!!");
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.ok, (dialog, id) -> {
                    if (checkValidation())
                        submitNominee();
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}