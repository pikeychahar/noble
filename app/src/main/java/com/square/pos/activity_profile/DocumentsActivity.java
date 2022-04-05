package com.square.pos.activity_profile;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.activity.ImageActivity;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.UserManager;
import com.square.pos.model.CommonResponse;
import com.square.pos.model_pos.AgentDetail;
import com.square.pos.model_pos.ProfileRequest;
import com.square.pos.utils.AppUtils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import in.gauriinfotech.commons.Commons;

public class DocumentsActivity extends AbstractActivity implements onRequestCompleteCallBackListener {
    private TextView commonTextView, txtPAN, txtAadharFront, txtAadharBack, txtQualification,
            txtCheck, txtProfile, txtSignature, txtPAN1, txtAadharFront1, txtAadharBack1,
            txtQualification1, txtCheck1, txtProfile1, txtSignature1;

    private Context mContext;
    private AgentDetail agentDetailObj;
    private String panCard, aadharCardFront, aadharBack, qualification, cheque, profile, signature,
            panCard1, aadharCardFront1, aadharBack1, qualification1, cheque1, profile1, signature1,
            agentId, mCurrentPhotoPath;
    private SharedPreferences preferences;
    private ProgressDialog progressdialog;
    private Menu menu;

    private final CharSequence[] items = {"Take Photo", "Photos"};
    private File photoFile = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        agentId = preferences.getString(AppUtils.PRIMARY_ID, "");
        agentDetailObj = UserManager.getAgentDetail();
        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Sending Request");

        txtPAN = findViewById(R.id.ulPAN);
        txtAadharFront = findViewById(R.id.ulAF);
        txtAadharBack = findViewById(R.id.ulAB);
        txtQualification = findViewById(R.id.ulQualification);
        txtCheck = findViewById(R.id.ulCheck);
        txtProfile = findViewById(R.id.ulProfilePhoto);
        txtSignature = findViewById(R.id.ulSign);

        //upload
        txtPAN1 = findViewById(R.id.ulPAN1);
        txtAadharFront1 = findViewById(R.id.ulAF1);
        txtAadharBack1 = findViewById(R.id.ulAB1);
        txtQualification1 = findViewById(R.id.ulQualification1);
        txtCheck1 = findViewById(R.id.ulCheck1);
        txtProfile1 = findViewById(R.id.ulProfilePhoto1);
        txtSignature1 = findViewById(R.id.ulSign1);

        requestEligibility();
    }

    public void onViewOrUpload(View view) {
        commonTextView = (TextView) view;

        if (agentDetailObj != null) {

            panCard = agentDetailObj.getPancardImage();
            aadharCardFront = agentDetailObj.getAadharcardImage();
            aadharBack = agentDetailObj.getAadharcardImageBack();
            qualification = agentDetailObj.getQualificationImage();
            profile = agentDetailObj.getImage();
            signature = agentDetailObj.getSignatureImage();
            cheque = agentDetailObj.getChequeImage();

            if (view == txtPAN)
                if (!TextUtils.isEmpty(panCard)) {
                    if (panCard.contains(".pdf")) {
                        try {
                            Intent i = new Intent("android.intent.action.MAIN");
                            i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                            i.addCategory("android.intent.category.LAUNCHER");
                            i.setData(Uri.parse(panCard));
                            startActivity(i);
                        } catch (ActivityNotFoundException e) {
                            // Chrome is not installed
                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(panCard));
                            startActivity(i);
                        }
                    } else {
                        Intent intent1 = new Intent(mContext, ImageActivity.class);
                        intent1.putExtra("img", panCard);
                        startActivity(intent1);
                    }
                }
            if (view == txtAadharFront)
                if (!TextUtils.isEmpty(aadharCardFront)) {
                    if (aadharCardFront.contains(".pdf")) {
                        try {
                            Intent i = new Intent("android.intent.action.MAIN");
                            i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                            i.addCategory("android.intent.category.LAUNCHER");
                            i.setData(Uri.parse(aadharCardFront));
                            startActivity(i);
                        } catch (ActivityNotFoundException e) {
                            // Chrome is not installed
                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(aadharCardFront));
                            startActivity(i);
                        }
                    } else {
                        Intent intent1 = new Intent(mContext, ImageActivity.class);
                        intent1.putExtra("img", aadharCardFront);
                        startActivity(intent1);
                    }
                }
            if (view == txtAadharBack)
                if (!TextUtils.isEmpty(aadharBack)) {
                    if (aadharBack.contains(".pdf")) {
                        try {
                            Intent i = new Intent("android.intent.action.MAIN");
                            i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                            i.addCategory("android.intent.category.LAUNCHER");
                            i.setData(Uri.parse(aadharBack));
                            startActivity(i);
                        } catch (ActivityNotFoundException e) {
                            // Chrome is not installed
                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(aadharBack));
                            startActivity(i);
                        }
                    } else {
                        Intent intent1 = new Intent(mContext, ImageActivity.class);
                        intent1.putExtra("img", aadharBack);
                        startActivity(intent1);
                    }
                }
            if (view == txtCheck)
                if (!TextUtils.isEmpty(cheque)) {
                    if (cheque.contains(".pdf")) {
                        try {
                            Intent i = new Intent("android.intent.action.MAIN");
                            i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                            i.addCategory("android.intent.category.LAUNCHER");
                            i.setData(Uri.parse(cheque));
                            startActivity(i);
                        } catch (ActivityNotFoundException e) {
                            // Chrome is not installed
                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(cheque));
                            startActivity(i);
                        }
                    } else {
                        Intent intent1 = new Intent(mContext, ImageActivity.class);
                        intent1.putExtra("img", cheque);
                        startActivity(intent1);
                    }

                }
            if (view == txtProfile)
                if (!TextUtils.isEmpty(profile)) {
                    if (profile.contains(".pdf")) {
                        try {
                            Intent i = new Intent("android.intent.action.MAIN");
                            i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                            i.addCategory("android.intent.category.LAUNCHER");
                            i.setData(Uri.parse(profile));
                            startActivity(i);
                        } catch (ActivityNotFoundException e) {
                            // Chrome is not installed
                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(profile));
                            startActivity(i);
                        }
                    } else {
                        Intent intent1 = new Intent(mContext, ImageActivity.class);
                        intent1.putExtra("img", profile);
                        startActivity(intent1);
                    }
                }
            if (view == txtQualification)
                if (!TextUtils.isEmpty(qualification)) {
                    if (qualification.contains(".pdf")) {
                        try {
                            Intent i = new Intent("android.intent.action.MAIN");
                            i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                            i.addCategory("android.intent.category.LAUNCHER");
                            i.setData(Uri.parse(qualification));
                            startActivity(i);
                        } catch (ActivityNotFoundException e) {
                            // Chrome is not installed
                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(qualification));
                            startActivity(i);
                        }
                    } else {
                        Intent intent1 = new Intent(mContext, ImageActivity.class);
                        intent1.putExtra("img", qualification);
                        startActivity(intent1);
                    }
                }
            if (view == txtSignature)
                if (!TextUtils.isEmpty(signature)) {
                    if (signature.contains(".pdf")) {
                        try {
                            Intent i = new Intent("android.intent.action.MAIN");
                            i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                            i.addCategory("android.intent.category.LAUNCHER");
                            i.setData(Uri.parse(signature));
                            startActivity(i);
                        } catch (ActivityNotFoundException e) {
                            // Chrome is not installed
                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(signature));
                            startActivity(i);
                        }
                    } else {
                        Intent intent1 = new Intent(mContext, ImageActivity.class);
                        intent1.putExtra("img", signature);
                        startActivity(intent1);
                    }
                }

        }
    }

    //menu back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_edit:
                onEditClick();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onEditClick() {
        findViewById(R.id.CLEdit).setVisibility(View.VISIBLE);
        findViewById(R.id.CLView).setVisibility(View.GONE);

        String status = menu.findItem(R.id.action_edit).getTitle().toString();
        if (status.equalsIgnoreCase("edit")) {
            menu.findItem(R.id.action_edit).setTitle("Update");

        } else if (status.equalsIgnoreCase("requested")) {

            Toast.makeText(mContext, "Already requested", Toast.LENGTH_SHORT).show();
        } else {
            uploadDocuments();
        }
    }


    public void requestEligibility() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().requestEligibility(mContext, agentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof CommonResponse) {
            CommonResponse profileRequest = (CommonResponse) response;
            if (profileRequest.getSuccess().equalsIgnoreCase("0")) {
                menu.findItem(R.id.action_edit).setTitle("Requested");
                progressdialog.dismiss();

            }
        }
        if (response instanceof ProfileRequest) {
            ProfileRequest profileRequest = (ProfileRequest) response;
            if (profileRequest.getSuccess().equalsIgnoreCase("1")) {
                finish();
                progressdialog.dismiss();

            } else {
                Toast.makeText(mContext, "" + profileRequest.getMsg(), Toast.LENGTH_SHORT).show();
                progressdialog.dismiss();
                finish();
            }
        }

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

        if (commonTextView == txtPAN1) {
            image = File.createTempFile(
                    "PAN_",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } else if (commonTextView == txtAadharFront1) {
            image = File.createTempFile(
                    "Aadhar_Front_",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } else if (commonTextView == txtAadharBack1) {
            image = File.createTempFile(
                    "Aadhar_Back_",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } else if (commonTextView == txtCheck1) {
            image = File.createTempFile(
                    "Check_",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } else if (commonTextView == txtQualification1) {
            image = File.createTempFile(
                    "MarkSheet_",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } else if (commonTextView == txtProfile1) {
            image = File.createTempFile(
                    "Profile_Photo",  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } else if (commonTextView == txtSignature1) {
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


    public void uploadDocuments() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.setMessage("Uploading Data");
            progressdialog.show();

            String values = "0";

            if (!TextUtils.isEmpty(panCard1)) {
                values = "pancard_image";
            }
            if (!TextUtils.isEmpty(aadharCardFront1)) {
                values = values + "__,aadharcard_image";
            }
            if (!TextUtils.isEmpty(aadharBack1)) {
                values = values + "__,aadharcard_image_back";
            }
            if (!TextUtils.isEmpty(qualification1)) {
                values = values + "__,qualification_image";
            }
            if (!TextUtils.isEmpty(profile1)) {
                values = values + "__,image";
            }
            if (!TextUtils.isEmpty(signature1)) {
                values = values + "__,signature_image";
            }
            try {
                UserManager.getInstance().updateDocuments(mContext, agentId, "idproof_details",
                        values, panCard1, aadharCardFront1, aadharBack1, qualification1,
                        profile1, signature1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void onUploadFile(View view) {
        String path = String.valueOf(Environment.getExternalStorageDirectory());
        File file = new File(path);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(Uri.fromFile(file), "*/*");

        try {
            startActivityForResult(Intent.createChooser(intent, "Select File"), AppUtils.REQUEST_CODE_FILES);
        } catch (ActivityNotFoundException e) {

        }
    }

    private String getRealPathFromURI(Context context, Uri uri) {

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

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case AppUtils.REQUEST_CODE_CAMERA:
                    if (true) {//intent != null) {
                        commonTextView.setText(photoFile.getName());
                        if (commonTextView == txtPAN1)
                            panCard1 = photoFile.getAbsolutePath();

                        else if (commonTextView == txtAadharFront1)
                            aadharCardFront1 = photoFile.getAbsolutePath();

                        else if (commonTextView == txtAadharBack1)
                            aadharBack1 = photoFile.getAbsolutePath();

                        else if (commonTextView == txtQualification1)
                            qualification1 = photoFile.getAbsolutePath();

                        else if (commonTextView == txtCheck1)
                            cheque1 = photoFile.getAbsolutePath();

                        else if (commonTextView == txtProfile1)
                            profile1 = photoFile.getAbsolutePath();

                        else if (commonTextView == txtSignature1)
                            signature1 = photoFile.getAbsolutePath();
                        else
                            Toast.makeText(mContext, "Some thing went wrong", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case AppUtils.REQUEST_CODE_GALLERY:
                    if (data != null) {
                        Uri selectedImage = data.getData();
                        if (selectedImage != null) {
                            Uri selectedImageUri = data.getData();
                            String s = getRealPathFromURI(mContext, selectedImage);
                            commonTextView.setText(s);

                            if (commonTextView == txtPAN1) {
                                panCard1 = getRealPathFromURI(mContext, selectedImage);
                            } else if (commonTextView == txtAadharFront1)
                                aadharCardFront1 = getRealPathFromURI(mContext, selectedImage);

                            else if (commonTextView == txtAadharBack1)
                                aadharBack1 = getRealPathFromURI(mContext, selectedImage);

                            else if (commonTextView == txtQualification1)
                                qualification1 = getRealPathFromURI(mContext, selectedImage);

                            else if (commonTextView == txtCheck1)
                                cheque1 = getRealPathFromURI(mContext, selectedImage);

                            else if (commonTextView == txtProfile1)
                                profile1 = getRealPathFromURI(mContext, selectedImage);

                            else if (commonTextView == txtSignature1)
                                signature1 = getRealPathFromURI(mContext, selectedImage);
                            else
                                Toast.makeText(mContext, "Some thing went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;

//                case AppUtils.REQUEST_CODE_FILES:
//
//                    Uri data1 = data.getData();
//                    policyPdf = getRealPathFromURI(mContext, data1);
//
//                    uploadPrePolicy.setText(policyPdf);
//                    break;
            }
        }
    }
}
