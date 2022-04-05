package com.dmw.noble.activity_motor;

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
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.CrmManager;
import com.dmw.noble.model.BasicResponse;
import com.dmw.noble.utils.AppUtils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import in.gauriinfotech.commons.Commons;

public class RaiseOfflineActivity extends AbstractActivity implements
        onRequestCompleteCallBackListener {

    EditText edtRcFront, edtRcBack, edtPolicy, edtInvoice, edtOther, edtRemark, commonTextView;

    Context mContext;
    ProgressDialog progressDialog;
    Bundle mBundle;

    final CharSequence[] items = {"Take Photo", "Photos", "Documents"};
    File photoFile = null;

    String rcFront, rcBack, newVehicle, invoice, policyPdf, otherDoc, remark, prePolicy,
            quotationId;
    boolean isNewVehicle, isPreviousPolicy;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    boolean isCameraPermitted = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_offline);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait..");

        SharedPreferences preferences = getSharedPreferences(String.valueOf(R.string.app_name),
                MODE_PRIVATE);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        mBundle = getIntent().getExtras();

        edtInvoice = findViewById(R.id.edtInvoice);
        edtRcFront = findViewById(R.id.edtRcFront);
        edtRcBack = findViewById(R.id.edtRcBack);
        edtPolicy = findViewById(R.id.edtPolicy);
        edtOther = findViewById(R.id.edtOther);
        edtRemark = findViewById(R.id.edtRemark);

        if (mBundle != null) {
            newVehicle = mBundle.getString(AppUtils.NEW_VEHICLE);
            prePolicy = mBundle.getString(AppUtils.PRE_POLICY_TYPE);
            quotationId = mBundle.getString(AppUtils.QUOTATION_ID);

            if (!TextUtils.isEmpty(newVehicle) && newVehicle.equals("new_gadi")) {
                isNewVehicle = true;
                isPreviousPolicy = false;
                findViewById(R.id.txtRcFront).setVisibility(View.GONE);
                findViewById(R.id.txtRcBack).setVisibility(View.GONE);
                findViewById(R.id.txtPolicy).setVisibility(View.GONE);
                findViewById(R.id.txtInvoice).setVisibility(View.VISIBLE);
            } else {
                isNewVehicle = false;
                isPreviousPolicy = false;
                findViewById(R.id.txtRcFront).setVisibility(View.VISIBLE);
                findViewById(R.id.txtRcBack).setVisibility(View.VISIBLE);
                findViewById(R.id.txtInvoice).setVisibility(View.GONE);

                if (!TextUtils.isEmpty(prePolicy)) {
                    isPreviousPolicy = true;
                    findViewById(R.id.txtPolicy).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.txtPolicy).setVisibility(View.GONE);
                }
            }
        }
        checkCameraPermission();
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            isCameraPermitted = false;
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
    }

    public void onSubmitClick(View view) {
        if (isValid())
            requestOffLineQuote();
    }

    private boolean isValid() {
        remark = edtRemark.getText().toString();
        if (TextUtils.isEmpty(remark)) {
            edtRemark.setError(getString(R.string.field_cannot));
            edtRemark.requestFocus();
            return false;
        } else if (isNewVehicle) {
            if (TextUtils.isEmpty(invoice)) {
                AppUtils.showToast(mContext, "Upload Invoice");
                return false;
            }
        } else {
            if (TextUtils.isEmpty(rcFront)) {
                AppUtils.showToast(mContext, "Upload RC Front");
                return false;
            }
            if (isPreviousPolicy && TextUtils.isEmpty(policyPdf)) {
                AppUtils.showToast(mContext, "Upload Previous Policy");
                return false;
            }
        }
        return true;
    }

    public void requestOffLineQuote() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                CrmManager.getInstance().requestOffLineQuote(mContext, quotationId, "",
                        invoice, rcFront, rcBack, remark, policyPdf, otherDoc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onResponse(Object response) {
        try {
            if (response instanceof BasicResponse) {
                BasicResponse bResponse = (BasicResponse) response;
                if (bResponse.getStatus() != null && bResponse.getStatus() == 1) {
                    showDialog();
                } else
                    AppUtils.showToast(mContext, "" + bResponse.getMsg());
            }
            progressDialog.dismiss();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void onUploadFile(View view) {
        commonTextView = (EditText) view;
        commonTextView.setError(null);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Upload File!");

        builder.setItems(items, this::onDialogClick);

        builder.show();
    }

    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle("Offline Quote");
        alertDialogBuilder.setIcon(R.drawable.logo);
        alertDialogBuilder.setMessage("Request Submitted successfully, Executive will coordinate to you soon.");
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    finish();
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void onDialogClick(DialogInterface dialog, int item) {
        switch (item) {
            case 0:
                if (isCameraPermitted)
                    dispatchTakePictureIntent();
                else AppUtils.showToast(mContext, "Camera Permission Denied!!");
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
                    startActivityForResult(Intent.createChooser(intent, "Select File"),
                            AppUtils.REQUEST_CODE_FILES);
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
        try {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                photoFile = createImageFile();

                Uri photoURI = FileProvider.getUriForFile(this, AppUtils.PROVIDER,
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(takePictureIntent, AppUtils.REQUEST_CODE_CAMERA);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private File createImageFile() throws IOException {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image;

        if (commonTextView == edtRcFront) {
            image = File.createTempFile("Img_RC_FRONT", ".jpg", storageDir);
        } else if (commonTextView == edtRcBack) {
            image = File.createTempFile("Img_RC_Back", ".jpg", storageDir);
        } else if (commonTextView == edtPolicy) {
            image = File.createTempFile("Img_Policy", ".jpg", storageDir);
        } else if (commonTextView == edtOther) {
            image = File.createTempFile("Img_Other", ".jpg", storageDir);
        } else if (commonTextView == edtInvoice) {
            image = File.createTempFile("Img_Invoice", ".jpg", storageDir);
        } else {
            image = File.createTempFile("IMG_", ".jpg", storageDir);
        }
        image.renameTo(storageDir);
        return image;
    }

    private String getRealPathFromURIPdf(Context context, Uri uri) {
        String path = null;
        try {
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
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
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
        try {
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case AppUtils.REQUEST_CODE_CAMERA:
                        if (true) {//intent != null) {
                            commonTextView.setText(photoFile.getName());
                            if (commonTextView == edtRcFront)
                                rcFront = photoFile.getAbsolutePath();
                            else if (commonTextView == edtRcBack)
                                rcBack = photoFile.getAbsolutePath();
                            else if (commonTextView == edtInvoice)
                                invoice = photoFile.getAbsolutePath();
                            else if (commonTextView == edtPolicy)
                                policyPdf = photoFile.getAbsolutePath();
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
                                } else if (commonTextView == edtInvoice) {
                                    invoice = getRealPathFromURI(selectedImage);
                                } else if (commonTextView == edtPolicy) {
                                    policyPdf = getRealPathFromURI(selectedImage);
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
                        else if (commonTextView == edtInvoice)
                            invoice = getRealPathFromURIPdf(mContext, data1);
                        else if (commonTextView == edtPolicy)
                            policyPdf = getRealPathFromURIPdf(mContext, data1);
                        else if (commonTextView == edtOther)
                            otherDoc = getRealPathFromURIPdf(mContext, data1);
                        else
                            AppUtils.showToast(mContext, "Something went wrong!!");

                        commonTextView.setText(AppUtils.getFileName(data1, mContext));
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0) {
            if (requestCode == MY_CAMERA_REQUEST_CODE) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isCameraPermitted = true;

                } else {
                    isCameraPermitted = false;
                    Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}