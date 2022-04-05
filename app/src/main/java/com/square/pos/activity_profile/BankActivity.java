package com.square.pos.activity_profile;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.loader.content.CursorLoader;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.adaptor.AccountAdapter;
import com.square.pos.adaptor.BankAdapter;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.UserManager;
import com.square.pos.model.Account;
import com.square.pos.model.AccountType;
import com.square.pos.model.Bank;
import com.square.pos.model.BankList;
import com.square.pos.model.CommonResponse;
import com.square.pos.model_pos.AgentDetail;
import com.square.pos.model_pos.ProfileRequest;
import com.square.pos.utils.AppUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BankActivity extends AbstractActivity implements onRequestCompleteCallBackListener {
    private Context mContext;
    private AutoCompleteTextView spnBank, spnAccountType, spnBank2, spnAccountType2;
    private List<BankList> bankList = new ArrayList<>();
    private String bankId, accTypeId, agentId, mCurrentPhotoPath, pictureName, stringPictureFile,
            values, bankName, accountNumber, branchName, ifsc, accountType;

    private final CharSequence[] items = {"Take Photo", "Photos"};
    private List<AccountType> accountTypeList = new ArrayList<>();
    private AgentDetail agentDetailObj;
    private EditText edtBankAccount, edtIfsc, edtBranchName, edtBankAccount2, edtIfsc2,
            edtBranchName2;
    private BankAdapter bankAdapter;
    private AccountAdapter accountAdapter;
    private File photoFile = null;
    private SharedPreferences preferences;
    private ProgressDialog progressdialog;
    private ImageView chequeImage, chequeImage2;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mContext = this;

        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        agentId = preferences.getString(AppUtils.PRIMARY_ID, "");
        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Sending Request");

        spnBank = findViewById(R.id.edtBankName);
        spnAccountType = findViewById(R.id.accountType);
        edtBankAccount = findViewById(R.id.edtBankAccount);
        edtIfsc = findViewById(R.id.edtIfsc);
        edtBranchName = findViewById(R.id.edtBranchName);
        chequeImage = findViewById(R.id.docImage);
        chequeImage2 = findViewById(R.id.docImage1);

        spnBank2 = findViewById(R.id.edtBankName2);
        spnAccountType2 = findViewById(R.id.accountType2);

        edtBankAccount2 = findViewById(R.id.edtBankAccount2);
        edtIfsc2 = findViewById(R.id.edtIfsc2);
        edtBranchName2 = findViewById(R.id.edtBranchName2);

        bankList = UserManager.getInstance().getBankLists();

        agentDetailObj = UserManager.getAgentDetail();

        if (bankList.size() > 0) {
            setBankAdapter();
        } else getBankList();

        if (agentDetailObj != null) {

            String url = agentDetailObj.getChequeImage();
            if (!TextUtils.isEmpty(url)) {
                Glide.with(this)
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(getDrawable(R.drawable.placeholder))
                        .into(chequeImage);
                Glide.with(this)
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(getDrawable(R.drawable.placeholder))
                        .into(chequeImage2);
            } else chequeImage.setVisibility(View.GONE);

            edtBankAccount.setText(agentDetailObj.getAccountNo());
            edtIfsc.setText(agentDetailObj.getIfsc());
            edtBranchName.setText(agentDetailObj.getBranchName());
            spnAccountType.setText(agentDetailObj.getAccountType());
            spnBank.setText(agentDetailObj.getBankName());

            edtBankAccount2.setText(agentDetailObj.getAccountNo());
            edtIfsc2.setText(agentDetailObj.getIfsc());
            edtBranchName2.setText(agentDetailObj.getBranchName());
            spnAccountType2.setText(agentDetailObj.getAccountType());
            spnBank2.setText(agentDetailObj.getBankName());

            bankId = agentDetailObj.getBankId();
            accTypeId = agentDetailObj.getAccountTypeId();
        }
        getAccType();

        spnBank.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (bankAdapter != null)
                    bankAdapter.notifyDataSetChanged();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        spnAccountType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (accountAdapter != null)
                    accountAdapter.notifyDataSetChanged();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        requestEligibility();
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof Account) {
            Account stateResponse = (Account) response;
            if (stateResponse.getMessage().equalsIgnoreCase("ok")) {
                accountTypeList = stateResponse.getAccountTypes();
                if (accountTypeList.size() > 0) {
                    setAccountAdapter();
                }
            }
        }
        if (response instanceof Bank) {
            Bank stateResponse = (Bank) response;
            if (stateResponse.getMessage().equalsIgnoreCase("ok")) {
                bankList = stateResponse.getBankList();
                if (bankList.size() > 0) {
                    setBankAdapter();
                }
            }
        }
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
                finish();
            }
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

    public void getBankList() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getBanks(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void putBankDetail() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().updateBankDetail(mContext, agentId, "bank_details",
                        values, stringPictureFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getAccType() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getAccountType(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void setBankAdapter() {
        bankAdapter = new BankAdapter(mContext,
                android.R.layout.select_dialog_item, bankList);
        spnBank.setThreshold(0);
        spnBank.setAdapter(bankAdapter);
        spnBank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bankId = bankList.get(position).getBankId();
                spnBank.setText(bankList.get(position).getBankName());
                spnBank.setSelection(spnBank.getText().length());

            }
        });
    }

    public void setAccountAdapter() {
        accountAdapter = new AccountAdapter(mContext,
                android.R.layout.select_dialog_item, accountTypeList);
        spnAccountType.setThreshold(0);
        spnAccountType.setAdapter(accountAdapter);
        spnAccountType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                accTypeId = accountTypeList.get(position).getAccountTypesId();
                spnAccountType.setText(accountTypeList.get(position).getAccountTypesName());
                spnAccountType.setSelection(spnAccountType.getText().length());

            }
        });
    }

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

    public void onEditClick() {
        String status = menu.findItem(R.id.action_edit).getTitle().toString();
        if (status.equalsIgnoreCase("edit")) {
            menu.findItem(R.id.action_edit).setTitle("Update");
            findViewById(R.id.rlViewBank).setVisibility(View.GONE);
            findViewById(R.id.rlEditBank).setVisibility(View.VISIBLE);
        } else if (status.equalsIgnoreCase("requested")) {
            Toast.makeText(mContext, "Already Requested for Edit", Toast.LENGTH_SHORT).show();

        } else {
            if (checkValid())
                putBankDetail();
        }
    }

    public void onUpdateCheque(View view) {
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
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, AppUtils.REQUEST_CODE_CAMERA);
//                dispatchTakePictureIntent();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case AppUtils.REQUEST_CODE_CAMERA:


                    Bitmap bmp = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                    chequeImage.setImageBitmap(bmp);

                    pictureName = MediaStore.Images.Media.insertImage(mContext.getContentResolver(), bmp,
                            "title", null);

                    stringPictureFile = getRealPathFromURI(Uri.parse(pictureName));


                    break;
                case AppUtils.REQUEST_CODE_GALLERY:
                    if (data != null) {
                        Uri selectedImage = data.getData();
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(),
                                    selectedImage);
                            chequeImage.setImageBitmap(bitmap);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        stringPictureFile = getRealPathFromURI(selectedImage);
                        //Uri.parse(pictureName));
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(mContext, contentUri, proj, null,
                null, null);

        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image;


        image = File.createTempFile(
                "Img_",  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );


        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        image.renameTo(storageDir);
        return image;
    }

    private boolean checkValid() {
        bankName = spnBank.getText().toString();
        branchName = edtBranchName.getText().toString();
        ifsc = edtIfsc.getText().toString();
        accountType = spnAccountType.getText().toString();
        accountNumber = edtBankAccount.getText().toString();

        if (TextUtils.isEmpty(bankName)) {
            spnBank.setError("Empty");
            spnBank.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(branchName)) {
            edtBranchName.setError("Empty");
            edtBranchName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(ifsc)) {
            edtIfsc.setError("Empty");
            edtIfsc.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(accountType)) {
            spnAccountType.setError("Empty");
            spnAccountType.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(accountNumber)) {
            edtBankAccount.setError("Empty");
            edtBankAccount.requestFocus();
            return false;
        }
        String cheque_image = "0";
        if (!TextUtils.isEmpty(stringPictureFile))
            cheque_image = "cheque_image";
        values = bankId + "__," + branchName + "__," + accTypeId + "__," + accountNumber + "__,"
                + ifsc + "__," + cheque_image;

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit, menu);
        this.menu = menu;
        return true;
    }
}
