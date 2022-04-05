package com.square.pos.activity_pos;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.activity.PrivacyActivity;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.UserManager;
import com.square.pos.model_pos.AgentDocuments;
import com.square.pos.utils.AppUtils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class UploadDocumentActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {

    private TextView commonTextView, txtPAN, txtAadharFront, txtAadharBack, txtQualification,
            txtCheck, txtProfile, txtSignature;
    private Context mContext;
    private final CharSequence[] items = {"Take Photo", "Photos"};
    private File photoFile = null;
    private String mCurrentPhotoPath;
    private String panCard, aadharCardFront, aadharBack, qualification, cheque, profile, signature;
    private String agent_id;
    SharedPreferences preferences, posPreferences;
    private ProgressDialog progressdialog;
    private CheckBox checked;
    private Button btnUpload;
    private Bundle mBundle;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    boolean isCameraPermitted = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_document);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mContext = this;
        preferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        posPreferences = getSharedPreferences("pos", MODE_PRIVATE);

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");
        progressdialog.setCancelable(false);

        checked = findViewById(R.id.checkbox);
        btnUpload = findViewById(R.id.btnUpload);
        txtPAN = findViewById(R.id.ulPAN);
        txtAadharFront = findViewById(R.id.ulAF);
        txtAadharBack = findViewById(R.id.ulAB);
        txtQualification = findViewById(R.id.ulQualification);
        txtCheck = findViewById(R.id.ulCheck);
        txtProfile = findViewById(R.id.ulProfilePhoto);
        txtSignature = findViewById(R.id.ulSign);
//        agent_id = preferences.getString(AppUtils.AGENT_ID, "");
        btnUpload.setAlpha((float) 0.7);
        btnUpload.setClickable(false);
        mBundle = getIntent().getBundleExtra("mBundle");
        if (mBundle != null) {
            agent_id = mBundle.getString(AppUtils.AGENT_ID);
        }
        checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checked.isChecked()) {
                    btnUpload.setAlpha((float) 1);
                    btnUpload.setClickable(true);
                } else {
                    btnUpload.setAlpha((float) 0.7);
                    btnUpload.setClickable(false);
                }
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

    public void onNextClick(View view) {
    }

    public void onPrevious(View view) {
        onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                        "com.square.pos.provider",
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

        if (commonTextView == txtPAN) {
            image = File.createTempFile(
                    "PAN_",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } else if (commonTextView == txtAadharFront) {
            image = File.createTempFile(
                    "Aadhar_Front_",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } else if (commonTextView == txtAadharBack) {
            image = File.createTempFile(
                    "Aadhar_Back_",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } else if (commonTextView == txtCheck) {
            image = File.createTempFile(
                    "Check_",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } else if (commonTextView == txtQualification) {
            image = File.createTempFile(
                    "MarkSheet_",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } else if (commonTextView == txtProfile) {
            image = File.createTempFile(
                    "Profile_Photo",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } else if (commonTextView == txtSignature) {
            image = File.createTempFile(
                    "Signature_",  /* prefix */
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
//        if (resultCode == RESULT_OK) {
//            Uri content_describer = data.getData();
//            //get the path
//            Log.d("Path???", content_describer.getPath());
//            BufferedReader reader = null;
//            try {
//                File file = new File(content_describer.getPath());
//
//                // open the user-picked file for reading:
//                InputStream in = this.getContentResolver().openInputStream(content_describer);
//                // now read the content:
//                reader = new BufferedReader(new InputStreamReader(in));
//                String line;
//                StringBuilder builder = new StringBuilder();
//
//                while ((line = reader.readLine()) != null) {
//                    builder.append(line);
//                }
//                // Do something with the content in
//                commonTextView.setText(file.getName());
//
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        switch (requestCode) {
            case AppUtils.REQUEST_CODE_CAMERA:
                if (true) {//intent != null) {
                    commonTextView.setText(photoFile.getName());
                    if (commonTextView == txtPAN)
                        panCard = photoFile.getAbsolutePath();

                    else if (commonTextView == txtAadharFront)
                        aadharCardFront = photoFile.getAbsolutePath();

                    else if (commonTextView == txtAadharBack)
                        aadharBack = photoFile.getAbsolutePath();

                    else if (commonTextView == txtQualification)
                        qualification = photoFile.getAbsolutePath();

                    else if (commonTextView == txtCheck)
                        cheque = photoFile.getAbsolutePath();

                    else if (commonTextView == txtProfile)
                        profile = photoFile.getAbsolutePath();

                    else if (commonTextView == txtSignature)
                        signature = photoFile.getAbsolutePath();
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

                        if (commonTextView == txtPAN) {
                            panCard = getRealPathFromURI(selectedImage);
                        } else if (commonTextView == txtAadharFront)
                            aadharCardFront = getRealPathFromURI(selectedImage);

                        else if (commonTextView == txtAadharBack)
                            aadharBack = getRealPathFromURI(selectedImage);

                        else if (commonTextView == txtQualification)
                            qualification = getRealPathFromURI(selectedImage);

                        else if (commonTextView == txtCheck)
                            cheque = getRealPathFromURI(selectedImage);

                        else if (commonTextView == txtProfile)
                            profile = getRealPathFromURI(selectedImage);

                        else if (commonTextView == txtSignature)
                            signature = getRealPathFromURI(selectedImage);
                        else
                            Toast.makeText(mContext, "Some thing went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onTermsClick(View view) {
        startActivity(new Intent(mContext, PrivacyActivity.class));
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

    @Override
    public void onResponse(Object response) {
        if (response instanceof AgentDocuments) {
            AgentDocuments docResponse = (AgentDocuments) response;
            if (docResponse.getSuccess().equalsIgnoreCase("1")) {
                AppUtils.commonDialog(mContext, "NEW REQUEST FOR POS",
                        "POS created Successful!!!");
                posPreferences.edit().clear().apply();
            } else
                AppUtils.commonDialog(mContext, "NEW REQUEST FOR POS",
                        "Error! Your Document not uploaded Contact to Branch");
        }
        progressdialog.dismiss();
    }

    public void uploadDocument() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().uploadDocuments(mContext, new File(panCard),
                        new File(aadharCardFront), new File(qualification), new File(cheque),
                        new File(profile), new File(signature), new File(aadharBack), agent_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void onUploadClick(View view) {
        if (checkValidation())
            uploadDocument();
    }

    public boolean checkValidation() {
        if (TextUtils.isEmpty(panCard)) {
            txtPAN.setError("Invalid File");
            Toast.makeText(mContext, "Upload Pan Card", Toast.LENGTH_SHORT).show();
            return false;
        } else txtPAN.setError(null);
        if (TextUtils.isEmpty(aadharCardFront)) {
            txtAadharFront.setError("Invalid File");
            Toast.makeText(mContext, "Upload Aadhar Card Front", Toast.LENGTH_SHORT).show();
            return false;
        } else txtAadharFront.setError(null);
        if (TextUtils.isEmpty(aadharBack)) {
            txtAadharBack.setError("Invalid File");
            Toast.makeText(mContext, "Upload Aadhar Card Back", Toast.LENGTH_SHORT).show();
            return false;
        } else txtAadharBack.setError(null);
        if (TextUtils.isEmpty(qualification)) {
            txtQualification.setError("Invalid File");
            Toast.makeText(mContext, "Upload Marksheet", Toast.LENGTH_SHORT).show();
            return false;
        } else txtQualification.setError(null);
        if (TextUtils.isEmpty(cheque)) {
            txtCheck.setError("Invalid File");
            Toast.makeText(mContext, "Upload canceled Check ", Toast.LENGTH_SHORT).show();
            return false;
        } else txtCheck.setError(null);
        if (TextUtils.isEmpty(signature)) {
            txtSignature.setError("Invalid File");
            Toast.makeText(mContext, "Upload Signature", Toast.LENGTH_SHORT).show();
            return false;
        } else txtSignature.setError(null);
        if (TextUtils.isEmpty(profile)) {
            txtProfile.setError("Invalid File");
            Toast.makeText(mContext, "Upload Profile Photo", Toast.LENGTH_SHORT).show();
            return false;
        } else txtProfile.setError(null);
        return true;
    }

    @Override
    public void onBackPressed() {

    }
}
