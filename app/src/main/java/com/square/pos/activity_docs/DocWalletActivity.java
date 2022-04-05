package com.square.pos.activity_docs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.UserManager;
import com.square.pos.model.DocWallet;
import com.square.pos.utils.AppUtils;

import java.io.File;
import java.io.IOException;

public class DocWalletActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {

    private Spinner spnDocType;
    private EditText edtPartyName, edtPartyContact, edtVehicleNumber, edtPolicyNumber,
            edtEngineChassis;
    private final CharSequence[] items = {"Take Photo", "Photos"};
    private File photoFile = null;
    private TextView commonTextView, txtMandate, txtOtherDoc, txtRCFront, txtGst, txtRCBack, txtVehicleOther,
            txtInsurance, txtProposal, txtKyc;

    private String agentId, mCurrentPhotoPath, mandate, rcFront, rcBack, vehOtherDoc,
            docType, clientPartyName, clientPartyContact, regNumber, vehDocType, policyNumber, gst,
            proposal, kyc, insurance, engineChassisNumber, otherDocNM;

    private Context mContext;
    private SharedPreferences preferences;
    private ProgressDialog progressdialog;
    private CheckBox chkRc, chkInsurance, chkGST, chkOther, chkMandate, chkProposal, chkKyc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_wallet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mContext = this;
        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");

        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        agentId = preferences.getString(AppUtils.PRIMARY_ID, "");

        spnDocType = findViewById(R.id.spnDocType);

        edtPartyName = findViewById(R.id.edtPartyName);
        edtPartyContact = findViewById(R.id.edtPartyContact);
        edtVehicleNumber = findViewById(R.id.edtVehicleNumber);
        edtPolicyNumber = findViewById(R.id.edtPolicyNumber);
        edtEngineChassis = findViewById(R.id.edtEngineChassis);


        txtRCFront = findViewById(R.id.uploadRc);
        txtRCBack = findViewById(R.id.uploadBck);
        txtGst = findViewById(R.id.uploadGst);
        txtInsurance = findViewById(R.id.uploadInsurance);
        txtVehicleOther = findViewById(R.id.uploadOther);
        txtOtherDoc = findViewById(R.id.uploadOtherDoc);

        txtMandate = findViewById(R.id.uploadMandateForm);
        txtProposal = findViewById(R.id.uploadProposal);
        txtKyc = findViewById(R.id.uploadKyc);

        chkRc = findViewById(R.id.chkRC);
        chkGST = findViewById(R.id.chkGST);
        chkInsurance = findViewById(R.id.chkInsurance);
        chkOther = findViewById(R.id.chkOther);
        chkMandate = findViewById(R.id.chkMandate);
        chkProposal = findViewById(R.id.chkProposal);
        chkKyc = findViewById(R.id.chkKyc);

        spnDocType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                if (spnDocType.getSelectedItemPosition() == 0) {
                    findViewById(R.id.rlHealth).setVisibility(View.GONE);
                    findViewById(R.id.rlVehicle).setVisibility(View.VISIBLE);
                    docType = "motor";
                    chkMandate.setChecked(false);
                    chkProposal.setChecked(false);
                    chkKyc.setChecked(false);

                } else if (spnDocType.getSelectedItemPosition() == 1) {
                    findViewById(R.id.rlHealth).setVisibility(View.VISIBLE);
                    findViewById(R.id.rlMandateForm).setVisibility(View.VISIBLE);
                    findViewById(R.id.rlVehicle).setVisibility(View.GONE);

                    docType = "non_motor";
                    chkRc.setChecked(false);
                    chkInsurance.setChecked(false);
                    chkGST.setChecked(false);
                    chkOther.setChecked(false);
                    chkMandate.setChecked(false);
                    chkProposal.setChecked(false);
                    chkKyc.setChecked(false);
                } else {
                    findViewById(R.id.rlHealth).setVisibility(View.VISIBLE);
                    findViewById(R.id.rlMandateForm).setVisibility(View.GONE);
                    findViewById(R.id.rlVehicle).setVisibility(View.GONE);
                    docType = "health";

                    chkMandate.setChecked(false);
                    chkRc.setChecked(false);
                    chkInsurance.setChecked(false);
                    chkGST.setChecked(false);
                    chkOther.setChecked(false);
                    chkProposal.setChecked(false);
                    chkKyc.setChecked(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof DocWallet) {
            DocWallet docResponse = (DocWallet) response;
            if (docResponse.getSuccess().equalsIgnoreCase("1")) {
                Toast.makeText(mContext, "Uploaded Successfully", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(mContext, "Error! Your Document not uploaded ",
                        Toast.LENGTH_LONG).show();
            }
        }
        progressdialog.dismiss();
        finish();
    }

    public void onUploadDoc(View view) {
        commonTextView = (TextView) view;

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Upload Photo!");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                onDialogClick(dialog, item);
            }
        });

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
                dialog.dismiss();
                break;
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
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

        if (commonTextView == txtMandate) {
            image = File.createTempFile(
                    "MANDATE_",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } else if (commonTextView == txtRCFront) {
            image = File.createTempFile(
                    "RC_Front_",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } else if (commonTextView == txtOtherDoc) {
            image = File.createTempFile(
                    "OTHER_",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } else if (commonTextView == txtRCBack) {
            image = File.createTempFile(
                    "RC_Back_",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } else if (commonTextView == txtVehicleOther) {
            image = File.createTempFile(
                    "Document_",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } else {
            image = File.createTempFile(
                    "Img_",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        }

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        image.renameTo(storageDir);
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode) {
            case AppUtils.REQUEST_CODE_CAMERA:
                if (true) {//intent != null) {
                    commonTextView.setText(photoFile.getName());

                    if (commonTextView == txtMandate)
                        mandate = photoFile.getAbsolutePath();

                    else if (commonTextView == txtOtherDoc)
                        otherDocNM = photoFile.getAbsolutePath();


                    else if (commonTextView == txtProposal)
                        proposal = photoFile.getAbsolutePath();

                    else if (commonTextView == txtKyc)
                        kyc = photoFile.getAbsolutePath();

                    else if (commonTextView == txtRCFront)
                        rcFront = photoFile.getAbsolutePath();

                    else if (commonTextView == txtRCBack)
                        rcBack = photoFile.getAbsolutePath();

                    else if (commonTextView == txtGst)
                        gst = photoFile.getAbsolutePath();

                    else if (commonTextView == txtVehicleOther)
                        vehOtherDoc = photoFile.getAbsolutePath();

                    else if (commonTextView == txtInsurance)
                        insurance = photoFile.getAbsolutePath();


                    else
                        Toast.makeText(mContext, "Some thing went wrong", Toast.LENGTH_SHORT).show();

                }
                break;
            case AppUtils.REQUEST_CODE_GALLERY:
                if (data != null) {
                    Uri selectedImage = data.getData();
                    if (selectedImage != null) {
                        Uri selectedImageUri = data.getData();
                        String s = getRealPathFromURI(selectedImageUri);
                        commonTextView.setText(s);

                        if (commonTextView == txtMandate)
                            mandate = getRealPathFromURI(selectedImage);

                        else if (commonTextView == txtOtherDoc)
                            otherDocNM = getRealPathFromURI(selectedImage);

                        else if (commonTextView == txtProposal)
                            proposal = getRealPathFromURI(selectedImage);

                        else if (commonTextView == txtKyc)
                            kyc = getRealPathFromURI(selectedImage);

                        else if (commonTextView == txtRCFront)
                            rcFront = getRealPathFromURI(selectedImage);

                        else if (commonTextView == txtRCBack)
                            rcBack = getRealPathFromURI(selectedImage);

                        else if (commonTextView == txtGst)
                            gst = getRealPathFromURI(selectedImage);

                        else if (commonTextView == txtInsurance)
                            insurance = getRealPathFromURI(selectedImage);

                        else if (commonTextView == txtVehicleOther)
                            vehOtherDoc = getRealPathFromURI(selectedImage);

                        else
                            Toast.makeText(mContext, "Some thing went wrong...", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
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


    public void uploadDocument() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().uploadDocWallet(mContext, agentId, "agent",
                        docType, clientPartyName, clientPartyContact, regNumber, policyNumber,engineChassisNumber,
                        rcFront, rcBack, insurance, gst, vehOtherDoc, mandate, otherDocNM,proposal, kyc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void onUploadWallet(View view) {
        if (checkValidate())
            uploadDocument();
    }

    private boolean checkValidate() {
        clientPartyName = edtPartyName.getText().toString();
        clientPartyContact = edtPartyContact.getText().toString();
        regNumber = edtVehicleNumber.getText().toString();
        policyNumber = edtPolicyNumber.getText().toString();
        engineChassisNumber = edtEngineChassis.getText().toString();
        if (spnDocType.getSelectedItemPosition() == 0) {
            if (chkRc.isChecked() || chkGST.isChecked() || chkInsurance.isChecked()
                    || chkOther.isChecked()) {

                if (chkRc.isChecked()) {
                    if (TextUtils.isEmpty(rcFront)) {
                        Toast.makeText(mContext, "Upload RC Front", Toast.LENGTH_SHORT).show();
                        return false;
                    } else if (TextUtils.isEmpty(rcBack)) {
                        Toast.makeText(mContext, "Upload RC Back", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
                if (chkGST.isChecked()) {
                    if (TextUtils.isEmpty(gst)) {
                        Toast.makeText(mContext, "Upload GST", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
                if (chkInsurance.isChecked()) {
                    if (TextUtils.isEmpty(insurance)) {
                        Toast.makeText(mContext, "Upload Insurance", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
                if (chkOther.isChecked()) {
                    if (TextUtils.isEmpty(vehOtherDoc)) {
                        Toast.makeText(mContext, "Upload Other", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            } else {
                Toast.makeText(mContext, "Upload Minimum One Document",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (spnDocType.getSelectedItemPosition() == 1) {
            if (chkMandate.isChecked() || chkProposal.isChecked() || chkKyc.isChecked()) {
                if (chkMandate.isChecked()) {
                    if (TextUtils.isEmpty(mandate)) {
                        Toast.makeText(mContext, "Upload Mandate", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
                if (chkProposal.isChecked()) {
                    if (TextUtils.isEmpty(proposal)) {
                        Toast.makeText(mContext, "Upload Proposal", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
                if (chkKyc.isChecked()) {
                    if (TextUtils.isEmpty(kyc)) {
                        Toast.makeText(mContext, "Upload KYC", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            } else {
                Toast.makeText(mContext, "Upload Minimum One Document",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (spnDocType.getSelectedItemPosition() == 2) {
            if (chkProposal.isChecked() || chkKyc.isChecked()) {

                if (chkProposal.isChecked()) {
                    if (TextUtils.isEmpty(proposal)) {
                        Toast.makeText(mContext, "Upload Proposal", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
                if (chkKyc.isChecked()) {
                    if (TextUtils.isEmpty(kyc)) {
                        Toast.makeText(mContext, "Upload KYC", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            } else {
                Toast.makeText(mContext, "Upload Minimum One Document",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }


        return true;
    }
}
