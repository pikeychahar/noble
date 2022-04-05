package com.square.pos.activity_profile;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.activity.ImageActivity;
import com.square.pos.adaptor.CityAdapter;
import com.square.pos.adaptor.StateAdapter;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.UserManager;
import com.square.pos.model.BasicResponse;
import com.square.pos.model.CommonResponse;
import com.square.pos.model.SendOtp;
import com.square.pos.model_pos.AgentDetail;
import com.square.pos.model_pos.City;
import com.square.pos.model_pos.CityListId;
import com.square.pos.model_pos.ProfileRequest;
import com.square.pos.model_pos.State;
import com.square.pos.model_pos.StateListId;
import com.square.pos.utils.AppUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfileActivity extends AbstractActivity implements onRequestCompleteCallBackListener {

    private List<State> stateList = new ArrayList<>();
    private List<City> citiesList = new ArrayList<>();
    private Context mContext;
    private String stateId, cityId;
    private ProgressDialog progressdialog;
    private EditText edtAgentName, edtAgentPhone2, edtAgentPhone, edtAgentMail, edtAddress1,
            edtAddress2, edtAddress3, edtPinCode, edtAgentName2, edtAgentMail2, edtAddress12,
            edtAddress22, edtAddress32, edtPinCode2;
    private SharedPreferences preferences;
    private String agentName, agentPhone2, agentPhone, agentEmail, address1, address2, address3,
            pincode, url, agentCode, agentId, mobile, stateName, cityName, gender, finalStr, otp,
            photoUrl, mCurrentPhotoPath,userType;

    private AgentDetail agentDetailObj;
    private AutoCompleteTextView edtStateName, edtCityName, edtStateName2, edtCityName2;
    private StateAdapter stateAdapter;
    private CityAdapter cityAdapter;
    private de.hdodenhof.circleimageview.CircleImageView userLogo;
    private Menu menu;
    private TextView txtAgentId,posStatus;

    //for profile
    private final CharSequence[] items = {"Take Photo", "Photos", "View photo"};
    private File photoFile = null;
    private RadioButton rbFemale,rbMale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        agentId = preferences.getString(AppUtils.PRIMARY_ID, "");
        agentCode = preferences.getString(AppUtils.AGENT_ID, "");

        String status;
        userType = preferences.getString(AppUtils.USER_TYPE, "");
        status = preferences.getString(AppUtils.POS_STATUS, "");
//        posStatus = "2";

        edtAgentName = findViewById(R.id.agentName);
        edtAgentPhone = findViewById(R.id.agentPhone);
        edtAgentPhone2 = findViewById(R.id.agentPhone2);
        edtAgentMail = findViewById(R.id.agentMail);
        edtAddress1 = findViewById(R.id.edtAddress1);
        edtAddress2 = findViewById(R.id.edtAddress2);
        edtAddress3 = findViewById(R.id.edtAddress3);
        edtPinCode = findViewById(R.id.edtPinCode);

        userLogo = findViewById(R.id.userLogo);
        txtAgentId = findViewById(R.id.txtAgentId);

        rbFemale = findViewById(R.id.rbFemale);
        rbMale = findViewById(R.id.rbMale);

        edtAgentMail2 = findViewById(R.id.agentMail2);
        edtAddress12 = findViewById(R.id.edtAddress12);
        edtAddress22 = findViewById(R.id.edtAddress22);
        edtAddress32 = findViewById(R.id.edtAddress32);
        edtPinCode2 = findViewById(R.id.edtPinCode2);

        edtStateName = findViewById(R.id.edtStateName);
        edtCityName = findViewById(R.id.edtSCityName);
        edtStateName2 = findViewById(R.id.edtStateName2);
        edtCityName2 = findViewById(R.id.edtSCityName2);

        posStatus = findViewById(R.id.posStatus);

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Sending Request");

        if (!TextUtils.isEmpty(userType))
            if (userType.equalsIgnoreCase("agent"))
                if (status.equalsIgnoreCase("4")) {
                    posStatus.setText("TRAINING");
                }

        agentDetailObj = UserManager.getAgentDetail();

        if (!TextUtils.isEmpty(agentCode))
            txtAgentId.setText(agentCode);
        else txtAgentId.setVisibility(View.GONE);

        getStates();

        agentName = preferences.getString(AppUtils.NAME, "");
        if (!TextUtils.isEmpty(agentName))
            edtAgentName.setText(agentName);
        agentPhone = preferences.getString(AppUtils.MOBILE, "");
        if (!TextUtils.isEmpty(agentPhone)) {
            edtAgentPhone.setText(agentPhone);
            edtAgentPhone2.setText(agentPhone);
        }
        if (agentDetailObj != null) {
            edtAgentMail.setText(agentDetailObj.getEmail());
            edtAgentMail2.setText(agentDetailObj.getEmail());
            edtPinCode.setText(agentDetailObj.getPincode());
            url = agentDetailObj.getCertificate();
            gender = agentDetailObj.getGender();


            if (!TextUtils.isEmpty(gender)){

                if (gender.equalsIgnoreCase("male"))
                    rbMale.setChecked(true);
                if (gender.equalsIgnoreCase("female"))
                    rbFemale.setChecked(true);
            }

            edtAddress1.setText(agentDetailObj.getAddress1());
            edtAddress2.setText(agentDetailObj.getAddress2());
            edtAddress3.setText(agentDetailObj.getAddress3());

            edtCityName.setText(agentDetailObj.getCity());
            edtStateName.setText(agentDetailObj.getState());

            edtAddress12.setText(agentDetailObj.getAddress1());
            edtAddress22.setText(agentDetailObj.getAddress2());
            edtAddress32.setText(agentDetailObj.getAddress3());

            edtCityName2.setText(agentDetailObj.getCity());
            edtStateName2.setText(agentDetailObj.getState());


            String path = agentDetailObj.getImage();
            if (!TextUtils.isEmpty(path))
                Glide.with(this)
                        .load(path)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(getDrawable(R.drawable.ic_user))
                        .into(userLogo);
            Picasso.with(mContext).load(path).fit().into(userLogo);
        }
        requestEligibility();
        setStateAdapter();


        edtStateName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (stateAdapter != null)
                    stateAdapter.notifyDataSetChanged();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtCityName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (cityAdapter != null)
                    cityAdapter.notifyDataSetChanged();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

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
                onEditUpdate();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResponse(Object response) {
        if (response instanceof StateListId) {
            StateListId stateResponse = (StateListId) response;
            if (stateResponse.getMessage().equalsIgnoreCase("ok")) {

                if (stateList.size() > 0) {
                    stateList.clear();
                }
                stateList = stateResponse.getState();
                setStateAdapter();
            }
        }
        if (response instanceof CityListId) {
            CityListId cityList = (CityListId) response;
            if (cityList.getMessage().equalsIgnoreCase("ok")) {
                citiesList.clear();

                citiesList = cityList.getCity();
                if (citiesList.size() > 0) {
                    setCityAdapter();
                }
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
        if (response instanceof CommonResponse) {
            CommonResponse profileRequest = (CommonResponse) response;
            if (profileRequest.getSuccess().equalsIgnoreCase("0")) {
                menu.findItem(R.id.action_edit).setTitle("Requested");
                progressdialog.dismiss();

            }
        }
        if (response instanceof SendOtp) {
            SendOtp nResponse = (SendOtp) response;
            if (nResponse.getStatus().equals(1)) {
                otp = nResponse.getOtp().toString();
                onViewDialog();
            }
        } if (response instanceof BasicResponse) {
            BasicResponse nResponse = (BasicResponse) response;
            if (nResponse.getStatus().equals(1)) {
                UserManager.getInstance().getAgentDetail(mContext,agentId,userType);
                agentDetailObj = UserManager.getAgentDetail();
            }
        }
        progressdialog.dismiss();
    }

    public void onDownloadCertificate(View view) {
        requestOtp();
    }

    private void openDoc() {
        if (!TextUtils.isEmpty(url))
            try {
                Intent i = new Intent("android.intent.action.MAIN");
                i.setComponent(ComponentName
                        .unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                i.addCategory("android.intent.category.LAUNCHER");
                i.setData(Uri.parse(url));
                startActivity(i);
            } catch (ActivityNotFoundException e) {
                // Chrome is not installed
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
            }
    }

    public void onViewDialog() {
        // Create custom dialog object
        final Dialog dialog = new Dialog(mContext);
        // Include dialog.xml file
        dialog.setContentView(R.layout.layout_otp);
        // Set dialog title
        dialog.setTitle("OTP verify");
        TextView txtMobile = dialog.findViewById(R.id.txtMobile);
        Button btnVerify = dialog.findViewById(R.id.btnVerify);
        final EditText edtOtp = dialog.findViewById(R.id.edtOtp);


        txtMobile.setText("OTP sent your mobile: " + agentPhone);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strOtp = edtOtp.getText().toString();

                if (TextUtils.isEmpty(strOtp)) {
                    edtOtp.setError("Empty");
                } else {
                    if (strOtp.equalsIgnoreCase(otp)) {
                        dialog.dismiss();
                        openDoc();
                    }
                }
            }
        });
        dialog.show();
    }

    public void requestOtp() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {

                UserManager.getInstance().verifyPosMobile(mContext, agentPhone);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }

    public void onEditUpdate() {

        String status = menu.findItem(R.id.action_edit).getTitle().toString();
        if (status.equalsIgnoreCase("edit")) {

            menu.findItem(R.id.action_edit).setTitle("Update");

            findViewById(R.id.rlEdit).setVisibility(View.VISIBLE);
            findViewById(R.id.rlNoEdit).setVisibility(View.GONE);
            edtAgentMail2.setVisibility(View.GONE);
            edtAgentMail.setVisibility(View.VISIBLE);
            edtAgentPhone2.setVisibility(View.VISIBLE);
            edtAgentPhone.setVisibility(View.GONE);

        } else if (status.equalsIgnoreCase("requested")) {
            Toast.makeText(mContext, "Already Requested for Edit", Toast.LENGTH_SHORT).show();

        } else {
            menu.findItem(R.id.action_edit).setTitle("Update");
            address1 = edtAddress1.getText().toString();
            address2 = edtAddress2.getText().toString();
            address3 = edtAddress3.getText().toString();
            pincode = edtPinCode.getText().toString();
            agentEmail = edtAgentMail.getText().toString();
            agentPhone = edtAgentPhone2.getText().toString();
            if (TextUtils.isEmpty(stateId))
                stateId = "0";
            if (TextUtils.isEmpty(cityId))
                cityId = "0";

            finalStr = agentEmail + "__," + agentPhone + "__," + address1 + "__," + address2 + "__,"
                    + address3 + "__," + pincode + "__," + stateId + "__," + cityId + "__," + gender;

            if (checkValid())
                updateProfile();
        }

    }

    public void updateProfile() {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().updateProfile(mContext, agentId, "basic_details",
                        finalStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getStates() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().getStateWithId(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }
    }

    public void getCities(String state_id) {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().getCitiesListId(mContext, state_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
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

    public void setStateAdapter() {
        stateAdapter = new StateAdapter(mContext,
                android.R.layout.select_dialog_item, stateList);
        edtStateName.setThreshold(0);
        edtStateName.setAdapter(stateAdapter);
        edtStateName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stateId = stateList.get(position).getId();
                edtStateName.setText(stateList.get(position).getStateName());
                edtStateName.setSelection(edtStateName.getText().length());
                edtCityName.setText("");
                getCities(stateId);

            }
        });
    }

    public void setCityAdapter() {
        cityAdapter = new CityAdapter(mContext,
                android.R.layout.select_dialog_item, citiesList);
        edtCityName.setThreshold(0);
        edtCityName.setAdapter(cityAdapter);
        edtCityName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityId = citiesList.get(position).getCityId();
                edtCityName.setText(citiesList.get(position).getCityName());
                edtCityName.setSelection(edtCityName.getText().length());
            }
        });
    }

    public boolean checkValid() {
        edtStateName.setError(null);
        edtCityName.setError(null);
        cityName = edtCityName.getText().toString();

        stateName = edtStateName.getText().toString();
        if (TextUtils.isEmpty(stateName)) {
            edtStateName.setError("Empty");
            edtStateName.setFocusable(true);
            return false;
        }

        if (TextUtils.isEmpty(cityName)) {
            edtCityName.setError("Empty");
            edtCityName.setFocusable(true);
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit, menu);
        this.menu = menu;
        return true;
    }

    public void onUploadProfile(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Profile Photo!");

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
                if (!TextUtils.isEmpty(agentDetailObj.getImage())) {
                    Intent intent1 = new Intent(mContext, ImageActivity.class);
                    intent1.putExtra("img", agentDetailObj.getImage());
                    startActivity(intent1);
                }
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


        image = File.createTempFile(
                "PROFILE_",  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );


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

                    photoUrl = photoFile.getAbsolutePath();
                    Bitmap myBitmap = BitmapFactory.decodeFile(photoUrl);
                    userLogo.setImageBitmap(myBitmap);
                    uploadDocument();
                }
                break;
            case AppUtils.REQUEST_CODE_GALLERY:
                if (data != null) {
                    Uri selectedImage = data.getData();
                    if (selectedImage != null) {
                        Uri selectedImageUri = data.getData();
                        String s = getRealPathFromURI(selectedImageUri);
//                        commonTextView.setText(s);
                        photoUrl = getRealPathFromURI(selectedImage);

                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();
                        userLogo.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    }
                    uploadDocument();
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
                UserManager.getInstance().updateProfilePhoto(mContext, agentId, photoUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void onMaleClick(View view) {
        gender = "Male";
    }

    public void onFemaleClick(View view) {
        gender = "Female";
    }
}
