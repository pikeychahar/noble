package com.dmw.noble.activity_crm;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.CrmManager;
import com.dmw.noble.model.BasicResponse;
import com.dmw.noble.model_crm.Cancellation;
import com.dmw.noble.utils.AppUtils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import in.gauriinfotech.commons.Commons;

public class NewCancellationActivity extends AbstractActivity implements onRequestCompleteCallBackListener {
    Context mContext;
    final CharSequence[] items = {"Take Photo", "Photos", "Documents"};
    File photoFile = null;
    String userId, userType, policyPdf, cancelCheque, letter, remark, srId, srNo;
    EditText edtLetter, commonTextView, edtAltPdf, edtRemark, edtCancelCheque;
    Cancellation cancellationObj;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cancellation);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        SharedPreferences preferences = getSharedPreferences(String.valueOf(R.string.app_name),
                MODE_PRIVATE);

        remark = policyPdf = letter = cancelCheque = "";
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait..");

        edtAltPdf = findViewById(R.id.edtAltPdf);
        edtLetter = findViewById(R.id.edtLetter);
        edtRemark = findViewById(R.id.edtRemark);
        edtCancelCheque = findViewById(R.id.edtCancelCheque);

        final Intent intent = getIntent();
        cancellationObj = (Cancellation) intent.getSerializableExtra("cancellation");

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");

        if (cancellationObj != null) {
            srId = cancellationObj.getSrId();
            srNo = cancellationObj.getSRNo();
        }
    }

    public void onSubmit(View view) {
        if (isValid())
            raiseCancellation();
    }

    private boolean isValid() {

        remark = edtRemark.getText().toString();

        if (TextUtils.isEmpty(policyPdf)) {
            AppUtils.showToast(mContext, "Select Alternate Policy PDF");
            return false;
        } else if (TextUtils.isEmpty(letter)) {
            AppUtils.showToast(mContext, "Select Customer Letter");
            return false;
        }
        return true;
    }

    public void raiseCancellation() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                CrmManager.getInstance().raiseCancellation(mContext, userId, userType, srId, srNo,
                        remark, policyPdf, letter, cancelCheque);
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

        if (commonTextView == edtAltPdf) {
            image = File.createTempFile("Img_R", ".jpg", storageDir);
        } else if (commonTextView == edtLetter) {
            image = File.createTempFile("Img", ".jpg", storageDir);
        } else if (commonTextView == edtCancelCheque) {
            image = File.createTempFile("Img_Cheque", ".jpg", storageDir);
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
                        if (commonTextView == edtAltPdf)
                            policyPdf = photoFile.getAbsolutePath();

                        else if (commonTextView == edtLetter) {
                            letter = photoFile.getAbsolutePath();
                        } else if (commonTextView == edtCancelCheque) {
                            cancelCheque = photoFile.getAbsolutePath();
                        } else
                            AppUtils.showToast(mContext, "Something went wrong");

                    }
                    break;
                case AppUtils.REQUEST_CODE_GALLERY:
                    if (data != null) {
                        Uri selectedImage = data.getData();
                        if (selectedImage != null) {
                            Uri selectedImageUri = data.getData();
                            commonTextView.setText(AppUtils.getFileName(selectedImageUri, mContext));

                            if (commonTextView == edtAltPdf) {
                                policyPdf = getRealPathFromURI(selectedImage);
                            } else if (commonTextView == edtLetter) {
                                letter = getRealPathFromURI(selectedImage);
                            } else if (commonTextView == edtCancelCheque) {
                                cancelCheque = getRealPathFromURI(selectedImage);
                            } else
                                Toast.makeText(mContext, "Some thing went wrong...", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;

                case AppUtils.REQUEST_CODE_FILES:
                    Uri data1 = data.getData();
                    if (commonTextView == edtAltPdf)
                        policyPdf = getRealPathFromURIPdf(mContext, data1);
                    else if (commonTextView == edtLetter) {
                        letter = getRealPathFromURIPdf(mContext, data1);
                    } else if (commonTextView == edtCancelCheque) {
                        cancelCheque = getRealPathFromURIPdf(mContext, data1);
                    } else
                        AppUtils.showToast(mContext, "Something went wrong");
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
        if (response instanceof BasicResponse) {
            BasicResponse claimResponse = (BasicResponse) response;
            if (claimResponse.getStatus() == 1) {
                Toast.makeText(mContext, "Request Created Successfully",
                        Toast.LENGTH_SHORT).show();

            } else {
                AppUtils.showToast(mContext, "" + claimResponse.getMsg());
            }
            progressDialog.dismiss();
            finish();
        }
    }
}