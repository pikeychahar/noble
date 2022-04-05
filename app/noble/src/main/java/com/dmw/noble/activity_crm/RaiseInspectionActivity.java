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
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.dmw.noble.R;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.CrmManager;
import com.dmw.noble.model.BasicResponse;
import com.dmw.noble.model_crm.ClaimPincode;
import com.dmw.noble.model_crm.Vehicle;
import com.dmw.noble.model_crm.VehicleData;
import com.dmw.noble.utils.AppUtils;
import com.dmw.noble.utils.String2WithTag;
import com.dmw.noble.utils.String3WithTag;
import com.dmw.noble.utils.String4WithTag;
import com.dmw.noble.utils.String5WithTag;
import com.dmw.noble.utils.String6WithTag;
import com.dmw.noble.utils.String7WithTag;
import com.dmw.noble.utils.StringWithTag;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;

import in.gauriinfotech.commons.Commons;

public class RaiseInspectionActivity extends AppCompatActivity implements
        onRequestCompleteCallBackListener {
    EditText edtCustomerName, edtCustomerContact, edtCSAltNo, edtCustomerEmail, edtCsAltMail,
            edtPincode, edtEmpContact, edtPosContact, edtEngine, edtChassis, edtRcFront, edtRcBack,
            edtQuote, commonTextView, edtRegNo, edtRegYear, edtAddress, edtVehLoc;
    TextView txtState, txtDistrict, txtCity;
    com.dmw.noble.utils.SearchableSpinner spnEmpName, spnPos, spnProduct, spnMake, spnModel,
            spnFuel, spnVariant, spnInsurer;
    Spinner spnInspectionMode;
    Context mContext;
    ProgressDialog progressDialog;

    final CharSequence[] items = {"Take Photo", "Photos", "Documents"};
    File photoFile = null;

    String userId, userType, rcFront, rcBack, quoteDoc, customerName, customerPhone, regNo,
            customerAltPhone, customerEmail, customerAltEmail, pincode, city, district, regYear,
            state, empName, empContact, posName, posContact, engine, chassis, product, make, model,
            fuel, variant, insurer, productId, address, vehicle, insMode, posId, empId, rto, remark,
            quoteId, endorsementId;

    ArrayList<Vehicle> makeList = new ArrayList<>();
    ArrayList<Vehicle> modelList = new ArrayList<>();
    ArrayList<Vehicle> variantList = new ArrayList<>();
    ArrayList<Vehicle> productList = new ArrayList<>();
    ArrayList<Vehicle> empList = new ArrayList<>();
    ArrayList<Vehicle> partnerList = new ArrayList<>();
    ArrayList<Vehicle> insurerList = new ArrayList<>();

    ArrayList<StringWithTag> insurers = new ArrayList<>();
    ArrayList<String2WithTag> products = new ArrayList<>();
    ArrayList<String3WithTag> makes = new ArrayList<>();
    ArrayList<String4WithTag> models = new ArrayList<>();
    ArrayList<String5WithTag> variants = new ArrayList<>();
    ArrayList<String6WithTag> employees = new ArrayList<>();
    ArrayList<String7WithTag> partners = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_inspection);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait..");

        quoteId = endorsementId = customerName = customerEmail = customerAltEmail = customerPhone
                = customerAltPhone = address = vehicle = pincode = city = state = empId = empName
                = empContact = posId = posName = posContact = product = insMode = regNo = rto = make
                = model = variant = insurer = engine = chassis = remark = rcFront = rcBack
                = quoteDoc = "";

        SharedPreferences preferences = getSharedPreferences(String.valueOf(R.string.app_name),
                MODE_PRIVATE);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");

        edtCustomerName = findViewById(R.id.edtCustomerName);
        edtCustomerContact = findViewById(R.id.edtCustomerContact);
        edtCSAltNo = findViewById(R.id.edtCSAltNo);
        edtCustomerEmail = findViewById(R.id.edtCustomerEmail);
        edtCsAltMail = findViewById(R.id.edtCsAltMail);
        edtPincode = findViewById(R.id.edtPincode);
        edtAddress = findViewById(R.id.edtAddress);
        edtVehLoc = findViewById(R.id.edtVehLoc);
        edtEmpContact = findViewById(R.id.edtEmpContact);
        edtPosContact = findViewById(R.id.edtPosContact);
        edtRegNo = findViewById(R.id.edtRegNo);
        edtRegYear = findViewById(R.id.edtRegYear);
        edtEngine = findViewById(R.id.edtEngine);
        edtChassis = findViewById(R.id.edtChassis);

        edtRcFront = findViewById(R.id.edtRcFront);
        edtRcBack = findViewById(R.id.edtRcBack);
        edtQuote = findViewById(R.id.edtQuote);

        txtState = findViewById(R.id.txtState);
        txtDistrict = findViewById(R.id.txtDistrict);
        txtCity = findViewById(R.id.txtCity);

        spnInspectionMode = findViewById(R.id.spnInspectionMode);
        spnEmpName = findViewById(R.id.spnEmpName);
        spnPos = findViewById(R.id.spnPos);
        spnProduct = findViewById(R.id.spnProduct);
        spnMake = findViewById(R.id.spnMake);
        spnModel = findViewById(R.id.spnModel);
        spnFuel = findViewById(R.id.spnFuel);
        spnVariant = findViewById(R.id.spnVariant);
        spnInsurer = findViewById(R.id.spnInsurer);
        getSurveyEmployee();
        getProducts();
        getInsurer();

        edtPincode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        edtPincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 6) {
                    pincode = edtPincode.getText().toString();
                    getPincode();
                } else {
                    txtCity.setText("");
                    txtDistrict.setText("");
                    txtState.setText("");
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSubmitClick(View view) {
        if (isValid())
            raiseSurvey();
    }

    public void getPincode() {
        if (AppUtils.isOnline(mContext)) {
            try {
                CrmManager.getInstance().getCrmPincode(mContext, pincode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Object response) {

        if (response instanceof ClaimPincode) {
            ClaimPincode cResponse = (ClaimPincode) response;
            if (cResponse.getStatus()) {
                pincode = cResponse.getPinCode();
                state = cResponse.getStateName();
                txtState.setText(state);
                district = cResponse.getDistrictName();
                txtDistrict.setText(district);
                city = cResponse.getCityOrVillageName();
                txtCity.setText(city);
            }
        }

        if (response instanceof VehicleData) {
            VehicleData pResponse = (VehicleData) response;
            if (pResponse.getStatus()) {
                switch (pResponse.getType()) {
                    case "Make":
                        makeList.clear();
                        makes.clear();
                        makeList.addAll(pResponse.getData());
                        initMake();
                        break;
                    case "Model":
                        modelList.clear();
                        models.clear();
                        modelList.addAll(pResponse.getData());
                        initModel();
                        break;
                    case "Variant":
                        variantList.clear();
                        variants.clear();
                        variantList.addAll(pResponse.getData());
                        initVariant();
                        break;
                    case "Product":
                        productList.clear();
                        products.clear();
                        productList.addAll(pResponse.getData());
                        initProduct();
                        break;
                    case "Insurer":
                        insurerList.clear();
                        insurers.clear();
                        insurerList.addAll(pResponse.getData());
                        initInsurer();
                        break;
                    case "Employee":
                        empList.clear();
                        employees.clear();
                        empList.addAll(pResponse.getData());
                        initEmp();
                        break;
                    case "Agents":
                        partnerList.clear();
                        partners.clear();
                        partnerList.addAll(pResponse.getData());
                        initPartner();
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

    public void surveyMake() {
        if (AppUtils.isOnline(mContext)) {
            try {
                CrmManager.getInstance().surveyMake(mContext, productId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void surveyModel() {
        if (AppUtils.isOnline(mContext)) {
            try {
                CrmManager.getInstance().surveyModel(mContext, make);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void surveyVariant() {
        if (AppUtils.isOnline(mContext)) {
            try {
                CrmManager.getInstance().surveyVariant(mContext, model);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getProducts() {
        if (AppUtils.isOnline(mContext)) {
            try {
                CrmManager.getInstance().getProducts(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getInsurer() {
        if (AppUtils.isOnline(mContext)) {
            try {
                CrmManager.getInstance().getInsurer(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getSurveyEmployee() {
        if (AppUtils.isOnline(mContext)) {
            try {
                CrmManager.getInstance().getSurveyEmployee(mContext, userId, userType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getSurveyPartner() {
        if (AppUtils.isOnline(mContext)) {
            try {
                CrmManager.getInstance().getSurveyPartner(mContext, userId, userType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void raiseSurvey() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                CrmManager.getInstance().raiseSurvey(mContext, userId, userType, quoteId,
                        endorsementId, customerName, customerEmail, customerAltEmail, customerPhone,
                        customerAltPhone, address, vehicle, pincode, city, state, empId, empName,
                        empContact, posId, posName, posContact, product, insMode, regNo, rto, make,
                        model, variant, insurer, engine, chassis, remark, rcFront, rcBack,
                        quoteDoc);
            } catch (Exception e) {
                progressDialog.dismiss();
                e.printStackTrace();
            }
        } else {
            progressDialog.dismiss();
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void initProduct() {
        for (int i = 0; i < productList.size(); i++) {
            String stateName = productList.get(i).getName().trim();
            String stateId = productList.get(i).getId();
            products.add(new String2WithTag(stateName, stateId));
        }

        ArrayAdapter<String2WithTag> adapter = new ArrayAdapter<>(this,
                R.layout.layout_list, products);
        spnProduct.setAdapter(adapter);


        spnProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                productId = (String) stringWithTag.tag;
                surveyMake();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initInsurer() {
        for (int i = 0; i < insurerList.size(); i++) {
            String stateName = insurerList.get(i).getName().trim();
            String stateId = insurerList.get(i).getId();
            insurers.add(new StringWithTag(stateName, stateId));
        }

        ArrayAdapter<StringWithTag> adapter = new ArrayAdapter<>(this,
                R.layout.layout_list, insurers);
        spnInsurer.setAdapter(adapter);


        spnInsurer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag stringWithTag = (StringWithTag) parent.getItemAtPosition(position);
                insurer = (String) stringWithTag.string;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initEmp() {
        for (int i = 0; i < empList.size(); i++) {
            String stateName = empList.get(i).getName().trim();
            String stateId = empList.get(i).getId();
            employees.add(new String6WithTag(stateName, stateId));
        }

        ArrayAdapter<String6WithTag> adapter = new ArrayAdapter<>(this,
                R.layout.layout_list, employees);
        spnEmpName.setAdapter(adapter);


        spnEmpName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String6WithTag stringWithTag = (String6WithTag) parent.getItemAtPosition(position);
                empName = (String) stringWithTag.string;
                getSurveyPartner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initPartner() {
        for (int i = 0; i < partnerList.size(); i++) {
            String stateName = partnerList.get(i).getName().trim();
            String stateId = partnerList.get(i).getId();
            partners.add(new String7WithTag(stateName, stateId));
        }

        ArrayAdapter<String7WithTag> adapter = new ArrayAdapter<>(this,
                R.layout.layout_list, partners);
        spnPos.setAdapter(adapter);


        spnPos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String7WithTag stringWithTag = (String7WithTag) parent.getItemAtPosition(position);
                posName = (String) stringWithTag.string;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initMake() {
        for (int i = 0; i < makeList.size(); i++) {
            String stateName = makeList.get(i).getName().trim();
            String stateId = makeList.get(i).getId();
            makes.add(new String3WithTag(stateName, stateId));
        }

        ArrayAdapter<String3WithTag> adapter = new ArrayAdapter<>(this,
                R.layout.layout_list, makes);
        spnMake.setAdapter(adapter);


        spnMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String3WithTag stringWithTag = (String3WithTag) parent.getItemAtPosition(position);
                make = stringWithTag.string;
                surveyModel();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initModel() {
        for (int i = 0; i < modelList.size(); i++) {
            String stateName = modelList.get(i).getName().trim();
            String stateId = modelList.get(i).getId();
            models.add(new String4WithTag(stateName, stateId));
        }

        ArrayAdapter<String4WithTag> adapter = new ArrayAdapter<>(this,
                R.layout.layout_list, models);
        spnModel.setAdapter(adapter);


        spnModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String4WithTag stringWithTag = (String4WithTag) parent.getItemAtPosition(position);
                model = stringWithTag.string;
                surveyVariant();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initVariant() {
        for (int i = 0; i < variantList.size(); i++) {
            String stateName = variantList.get(i).getName().trim();
            String stateId = variantList.get(i).getId();
            variants.add(new String5WithTag(stateName, stateId));
        }

        ArrayAdapter<String5WithTag> adapter = new ArrayAdapter<>(this,
                R.layout.layout_list, variants);
        spnVariant.setAdapter(adapter);


        spnVariant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String5WithTag stringWithTag = (String5WithTag) parent.getItemAtPosition(position);
                variant = stringWithTag.string;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean isValid() {

        Pattern regex = Pattern.compile("[$&+, :;=\\\\?@#|/'<>.^*()%!-]");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        int year = calendar.get(Calendar.YEAR);
        int sYear = year - 30;

        customerName = edtCustomerName.getText().toString();
        customerPhone = edtCustomerContact.getText().toString();
        customerAltPhone = edtCSAltNo.getText().toString();
        customerEmail = edtCustomerEmail.getText().toString();
        customerAltEmail = edtCsAltMail.getText().toString();
        engine = edtEngine.getText().toString();
        chassis = edtChassis.getText().toString();
        regNo = edtRegNo.getText().toString();
        regYear = edtRegYear.getText().toString();
        pincode = edtPincode.getText().toString();
        address = edtAddress.getText().toString();
        vehicle = edtVehLoc.getText().toString();
        city = txtCity.getText().toString();

        if (TextUtils.isEmpty(customerName)) {
            edtCustomerName.setError("Could not be empty");
            edtCustomerName.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(customerPhone)) {
            edtCustomerContact.setError("Could not be empty");
            edtCustomerContact.requestFocus();
            return false;
        } else if (!AppUtils.isValidMobile(customerPhone)) {
            edtCustomerContact.setError("Invalid Number");
            edtCustomerContact.requestFocus();
            return false;
        } else if (!TextUtils.isEmpty(customerAltPhone) && !AppUtils.isValidMobile(customerPhone)) {
            edtCSAltNo.setError("Invalid Number");
            edtCSAltNo.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(customerEmail)) {
            edtCustomerEmail.setError("Could not be empty");
            edtCustomerEmail.requestFocus();
            return false;
        } else if (!AppUtils.isValidMail(customerEmail)) {
            edtCustomerEmail.setError("Invalid Email");
            edtCustomerEmail.requestFocus();
            return false;
        } else if (!TextUtils.isEmpty(customerAltEmail) && !AppUtils.isValidMail(customerAltEmail)) {
            edtCsAltMail.setError("Invalid Email");
            edtCsAltMail.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(address)) {
            edtAddress.requestFocus();
            edtAddress.setError("Field can not be empty");
            return false;
        } else if (TextUtils.isEmpty(vehicle)) {
            edtVehLoc.requestFocus();
            edtVehLoc.setError("Field can not be empty");
            return false;
        } else if (TextUtils.isEmpty(engine)) {
            edtEngine.requestFocus();
            edtEngine.setError("Field can not be empty");
            return false;
        } else if (engine.length() <= 5) {
            edtEngine.requestFocus();
            edtEngine.setError("Minimum 6 character");
            return false;
        } else if (regex.matcher(engine).find()) {
            edtEngine.requestFocus();
            edtEngine.setError("Invalid Format");
            return false;
        } else if (TextUtils.isEmpty(chassis)) {
            edtChassis.requestFocus();
            edtChassis.setError("Field can not be empty");
            return false;
        } else if (chassis.length() <= 5) {
            edtChassis.requestFocus();
            edtChassis.setError("Minimum 6 character");
            return false;
        } else if (regex.matcher(chassis).find()) {
            edtChassis.requestFocus();
            edtChassis.setError("Invalid Format");
            return false;
        } else if (TextUtils.isEmpty(regNo)) {
            edtRegNo.setError("Could not be empty");
            edtRegNo.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(regYear)) {
            edtRegYear.setError("Could not be empty");
            edtRegYear.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(pincode)) {
            edtPincode.setError("Could not be empty");
            edtPincode.requestFocus();
            return false;
        } else if (pincode.length() != 6) {
            edtPincode.setError("Invalid Pincode");
            edtPincode.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(city)) {
            edtPincode.setError("Select Valid Pincode");
            edtPincode.requestFocus();
            return false;
        } else {
            int mYear = Integer.parseInt(regYear);
            if ((mYear >= year) || (mYear <= sYear)) {
                edtRegYear.setError("Invalid Year");
                edtRegYear.requestFocus();
                Toast.makeText(mContext, "Minimum Year is " + sYear, Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        engine = engine.replaceAll("\n", "");
        engine = engine.replaceAll(" ", "");
        chassis = chassis.replaceAll("\n", "");
        chassis = chassis.replaceAll(" ", "");

        return true;
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

        if (commonTextView == edtRcFront) {
            image = File.createTempFile("Img_RC_FRONT", ".jpg", storageDir);
        } else if (commonTextView == edtRcBack) {
            image = File.createTempFile("Img_RC_Back", ".jpg", storageDir);
        } else if (commonTextView == edtQuote) {
            image = File.createTempFile("Img_Letter", ".jpg", storageDir);
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
                        else if (commonTextView == edtQuote)
                            quoteDoc = photoFile.getAbsolutePath();
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
                            } else if (commonTextView == edtQuote) {
                                quoteDoc = getRealPathFromURI(selectedImage);
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
                    else if (commonTextView == edtQuote)
                        quoteDoc = getRealPathFromURIPdf(mContext, data1);
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
}