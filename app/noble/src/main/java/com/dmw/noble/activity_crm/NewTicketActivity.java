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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.CrmManager;
import com.dmw.noble.model_crm.CrmQuotes;
import com.dmw.noble.model_crm.QuoteData;
import com.dmw.noble.model_crm.RaiseTicket;
import com.dmw.noble.model_crm.Ticket;
import com.dmw.noble.model_crm.TicketList;
import com.dmw.noble.utils.AppUtils;
import com.dmw.noble.utils.SearchableSpinner;
import com.dmw.noble.utils.String2WithTag;
import com.dmw.noble.utils.String3WithTag;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import in.gauriinfotech.commons.Commons;

public class NewTicketActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {

    Spinner spnIssueType, spnVehicleType, spnPages;
    RelativeLayout rl1PayDone, rlMakeModel, rlManualMake, rlHpn, rlError;
    SearchableSpinner spnSearchQuoteId;
    RadioButton btnYes, btnNo;
    TextInputLayout txtDocRcF, txtCommon, txtPayout, txtDoc;
    EditText edtMake, edtModel, edtVariant, edtFuel, edtCubicCapacity, edtSeating, edtCompany,
            edtFinancierName, edtCity, edtCommon, edtRemark, edtUrl, edtPayout, edtDocFile,
            commonTextView, edtDocRcF;
    Context mContext;
    final CharSequence[] items = {"Take Photo", "Photos", "Documents"};
    File photoFile = null;
    LinearLayout llQI;
    TextView lblQuoteId;
    ProgressDialog progressdialog;
    String issueId, issue, userId, userType, quoteId, isRC, vehicleType, make, model, variant,
            fuelType, cubic, seating, gvw, insureName, cityName, pageUrl, pageType, policyNo,
            requestNo, remark, paymentDoneDoc, rcFrontDoc, rcBackDoc, errorDoc, attachmentDoc,
            mCurrentPhotoPath, ticketType = "";

    ArrayList<Ticket> ticketList = new ArrayList<>();
    ArrayList<String2WithTag> tickets = new ArrayList<>();
    ArrayList<QuoteData> quoteList = new ArrayList<>();
    ArrayList<String3WithTag> quotes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ticket);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        SharedPreferences preferences = getSharedPreferences(String.valueOf(R.string.app_name),
                MODE_PRIVATE);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");

        vehicleType = make = model = variant = fuelType = cubic = seating = gvw = insureName
                = cityName = pageUrl = pageType = policyNo = requestNo = remark = paymentDoneDoc
                = rcFrontDoc = rcBackDoc = errorDoc = attachmentDoc = mCurrentPhotoPath
                = quoteId = issueId = isRC = "";

        getCrmQuotes();

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait...");

        spnIssueType = findViewById(R.id.spnIssueType);
        spnVehicleType = findViewById(R.id.spnVehicleType);
        spnPages = findViewById(R.id.spnPages);

        rl1PayDone = findViewById(R.id.rl1PayDone);
        rlMakeModel = findViewById(R.id.rlMakeModel);
        rlManualMake = findViewById(R.id.rlManualMake);
        rlHpn = findViewById(R.id.rlHpn);
        rlError = findViewById(R.id.rlError);
        edtUrl = findViewById(R.id.edtUrl);
        spnSearchQuoteId = findViewById(R.id.spnSearchQuoteId);

        btnYes = findViewById(R.id.btnYes);
        btnNo = findViewById(R.id.btnNo);

        txtDocRcF = findViewById(R.id.txtDocRcF);
        txtCommon = findViewById(R.id.txtCommon);
        txtPayout = findViewById(R.id.txtPayout);
        txtDoc = findViewById(R.id.txtDoc);

        llQI = findViewById(R.id.llQI);
        lblQuoteId = findViewById(R.id.lblQuoteId);

        edtMake = findViewById(R.id.edtMake);
        edtModel = findViewById(R.id.edtModel);
        edtVariant = findViewById(R.id.edtVariant);
        edtFuel = findViewById(R.id.edtFuel);
        edtCubicCapacity = findViewById(R.id.edtCubicCapacity);
        edtSeating = findViewById(R.id.edtSeating);
        edtCompany = findViewById(R.id.edtCompany);
        edtFinancierName = findViewById(R.id.edtFinancierName);
        edtCity = findViewById(R.id.edtCity);
        edtRemark = findViewById(R.id.edtRemark);
        edtDocFile = findViewById(R.id.edtDocFile);
        edtPayout = findViewById(R.id.edtPayout);
        edtCommon = findViewById(R.id.edtCommon);
        edtDocRcF = findViewById(R.id.edtDocRcF);

        getTicketIssues();

        btnYes.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                isRC = "Yes";
                rlManualMake.setVisibility(View.GONE);
                txtDocRcF.setVisibility(View.VISIBLE);
                txtCommon.setVisibility(View.VISIBLE);
            }
        });

        btnNo.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                isRC = "No";
                rlManualMake.setVisibility(View.VISIBLE);
                txtDocRcF.setVisibility(View.GONE);
                txtCommon.setVisibility(View.GONE);
            }
        });
    }

    public void onSubmit(View view) {
        if (isValid()) createTicket();
    }

    private void inItClaimView() {
        attachmentDoc = errorDoc = paymentDoneDoc = rcBackDoc = "";
        llQI.setVisibility(View.GONE);
        lblQuoteId.setVisibility(View.GONE);
        rlMakeModel.setVisibility(View.GONE);
        rlHpn.setVisibility(View.GONE);
        rlError.setVisibility(View.GONE);
        edtDocFile.setVisibility(View.GONE);
        txtPayout.setVisibility(View.GONE);

        txtCommon.setVisibility(View.VISIBLE);
        txtCommon.setHint(getResources().getString(R.string.policy_number));
    }

    private void inItOfflineView() {
        attachmentDoc = errorDoc = paymentDoneDoc = rcBackDoc = "";
        llQI.setVisibility(View.GONE);
        lblQuoteId.setVisibility(View.GONE);
        rlMakeModel.setVisibility(View.GONE);
        rlHpn.setVisibility(View.GONE);
        rlError.setVisibility(View.GONE);
        edtDocFile.setVisibility(View.GONE);
        txtPayout.setVisibility(View.GONE);

        txtCommon.setVisibility(View.VISIBLE);
        txtCommon.setHint(getResources().getString(R.string.offline));
    }

    private void inItSurveyView() {
        attachmentDoc = errorDoc = paymentDoneDoc = rcBackDoc = "";
        llQI.setVisibility(View.GONE);
        lblQuoteId.setVisibility(View.GONE);
        rlMakeModel.setVisibility(View.GONE);
        rlHpn.setVisibility(View.GONE);
        rlError.setVisibility(View.GONE);
        edtDocFile.setText("");
        edtDocFile.setVisibility(View.VISIBLE);
        txtDoc.setHint(getResources().getString(R.string.upload_file));

        txtCommon.setVisibility(View.GONE);
        txtPayout.setVisibility(View.GONE);
    }

    private void onPaymentDone() {
        attachmentDoc = errorDoc = paymentDoneDoc = rcBackDoc = "";
        llQI.setVisibility(View.VISIBLE);
        lblQuoteId.setVisibility(View.VISIBLE);
        rlMakeModel.setVisibility(View.GONE);
        rlHpn.setVisibility(View.GONE);
        rlError.setVisibility(View.GONE);
        edtDocFile.setText("");
        edtDocFile.setVisibility(View.VISIBLE);
        txtDoc.setHint(getResources().getString(R.string.upload_file));
        txtCommon.setVisibility(View.GONE);
        txtPayout.setVisibility(View.GONE);
    }

    private void onMakeModel() {
        attachmentDoc = errorDoc = paymentDoneDoc = rcBackDoc = "";
        txtDoc.setHint(getResources().getString(R.string.upload_rc_back));
        rlMakeModel.setVisibility(View.VISIBLE);

        rlHpn.setVisibility(View.GONE);
        rlError.setVisibility(View.GONE);
        llQI.setVisibility(View.GONE);
        lblQuoteId.setVisibility(View.GONE);
        edtDocFile.setText("");
        edtDocFile.setVisibility(View.VISIBLE);
        txtCommon.setVisibility(View.GONE);
        txtPayout.setVisibility(View.GONE);
    }

    private void onHPN() {
        attachmentDoc = errorDoc = paymentDoneDoc = rcBackDoc = "";
        rlMakeModel.setVisibility(View.GONE);
        edtDocFile.setVisibility(View.GONE);

        rlHpn.setVisibility(View.VISIBLE);
        rlError.setVisibility(View.GONE);
        llQI.setVisibility(View.GONE);
        lblQuoteId.setVisibility(View.GONE);
        txtCommon.setVisibility(View.GONE);
        txtPayout.setVisibility(View.GONE);
    }

    private void onQuoteReflect() {
        attachmentDoc = errorDoc = paymentDoneDoc = rcBackDoc = "";
        edtDocFile.setVisibility(View.GONE);
        rlMakeModel.setVisibility(View.GONE);

        rlHpn.setVisibility(View.GONE);
        rlError.setVisibility(View.GONE);
        llQI.setVisibility(View.VISIBLE);
        lblQuoteId.setVisibility(View.VISIBLE);
        txtCommon.setVisibility(View.GONE);
        txtPayout.setVisibility(View.GONE);
    }

    private void onPolicy() {
        attachmentDoc = errorDoc = paymentDoneDoc = rcBackDoc = "";
        edtDocFile.setText("");
        edtDocFile.setVisibility(View.VISIBLE);
        txtDoc.setHint(getResources().getString(R.string.upload_file));
        rlMakeModel.setVisibility(View.GONE);

        rlHpn.setVisibility(View.GONE);
        rlError.setVisibility(View.VISIBLE);
        llQI.setVisibility(View.VISIBLE);
        lblQuoteId.setVisibility(View.VISIBLE);
        txtCommon.setVisibility(View.GONE);
        txtPayout.setVisibility(View.GONE);
    }

    private void onOther() {
        attachmentDoc = errorDoc = paymentDoneDoc = rcBackDoc = "";
        edtDocFile.setText("");
        edtDocFile.setVisibility(View.VISIBLE);
        txtDoc.setHint(getResources().getString(R.string.upload_file));
        rlMakeModel.setVisibility(View.GONE);

        rlHpn.setVisibility(View.GONE);
        rlError.setVisibility(View.GONE);
        llQI.setVisibility(View.GONE);
        lblQuoteId.setVisibility(View.GONE);

        txtCommon.setVisibility(View.GONE);
        txtPayout.setVisibility(View.GONE);
    }

    private void initOnlyRemark() {
        attachmentDoc = errorDoc = paymentDoneDoc = rcBackDoc = "";
        edtDocFile.setVisibility(View.GONE);
        rlMakeModel.setVisibility(View.GONE);

        rlHpn.setVisibility(View.GONE);
        rlError.setVisibility(View.GONE);
        llQI.setVisibility(View.GONE);
        lblQuoteId.setVisibility(View.GONE);

        txtCommon.setVisibility(View.GONE);
        txtPayout.setVisibility(View.GONE);
    }

    private void initPayout() {
        attachmentDoc = errorDoc = paymentDoneDoc = rcBackDoc = "";
        edtDocFile.setVisibility(View.GONE);
        rlMakeModel.setVisibility(View.GONE);

        rlHpn.setVisibility(View.GONE);
        rlError.setVisibility(View.GONE);
        llQI.setVisibility(View.GONE);
        lblQuoteId.setVisibility(View.GONE);

        txtCommon.setVisibility(View.VISIBLE);
        txtPayout.setVisibility(View.VISIBLE);
        txtCommon.setHint(getResources().getString(R.string.policy_number));
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
        if (response instanceof TicketList) {
            TicketList tResponse = (TicketList) response;
            if (tResponse.getStatus()) {
                ticketList.clear();
                tickets.clear();
                if (tResponse.getTicketList().size() > 0) {
                    ticketList.addAll(tResponse.getTicketList());
                    initTicketType();
                }
            }
        }

        if (response instanceof CrmQuotes) {
            CrmQuotes tResponse = (CrmQuotes) response;
            if (tResponse.getStatus()) {
                quoteList.clear();
                quotes.clear();
                if (tResponse.getData().size() > 0) {
                    quoteList.addAll(tResponse.getData());
                    initQuote();
                }
            }
        }

        if (response instanceof RaiseTicket) {
            RaiseTicket claimResponse = (RaiseTicket) response;
            if (claimResponse.getStatus()) {
                Toast.makeText(mContext, "Ticket Added Successfully",
                        Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(mContext, "Error! Please try again later ",
                        Toast.LENGTH_LONG).show();
            }
            progressdialog.dismiss();
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public void getTicketIssues() {
        if (AppUtils.isOnline(mContext)) {
            try {
                CrmManager.getInstance().getTicketTypes(mContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void getCrmQuotes() {
        if (AppUtils.isOnline(mContext)) {
            try {
                CrmManager.getInstance().getCrmQuotes(mContext, userId, userType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void initTicketType() {
        for (int i = 0; i < ticketList.size(); i++) {
            String stateName = ticketList.get(i).getName().trim();
            String stateId = ticketList.get(i).getId();
            tickets.add(new String2WithTag(stateName, stateId));
        }

        ArrayAdapter<String2WithTag> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, tickets);
        spnIssueType.setAdapter(adapter);

        spnIssueType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String2WithTag stringWithTag = (String2WithTag) parent.getItemAtPosition(position);
                issue = (String) stringWithTag.tag;
                issueId = stringWithTag.string;

                if (position == 0 || position == 3 || position == 4) {
                    ticketType = "claim";
                    inItClaimView();
                } else if (position == 1) {
                    inItSurveyView();
                    ticketType = "survey";
                } else if (position == 2) {
                    inItOfflineView();
                    ticketType = "offline";
                } else if (position == 5) {
                    ticketType = "pos";
                    initOnlyRemark();
                } else if (position == 6) {
                    ticketType = "payout";
                    initPayout();
                } else if (position == 7) {
                    ticketType = "payment_done";
                    onPaymentDone();
                } else if (position == 8) {
                    ticketType = "make";
                    onMakeModel();
                } else if (position == 9) {
                    ticketType = "hpn";
                    onHPN();
                } else if (position == 10) {
                    ticketType = "quote";
                    onQuoteReflect();
                } else if (position == 11) {
                    ticketType = "proposal";
                    onPolicy();
                } else if (position == 12) {
                    ticketType = "other";
                    onOther();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initQuote() {
        for (int i = 0; i < quoteList.size(); i++) {
            String stateName = quoteList.get(i).getName().trim();
            String stateId = quoteList.get(i).getId();
            quotes.add(new String3WithTag(stateName, stateId));
        }

        ArrayAdapter<String3WithTag> adapter = new ArrayAdapter<>(mContext,
                android.R.layout.simple_dropdown_item_1line, quotes);
        spnSearchQuoteId.setAdapter(adapter);

        spnSearchQuoteId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String3WithTag stringWithTag = (String3WithTag) parent.getItemAtPosition(position);
                quoteId = stringWithTag.tag.toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void createTicket() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();

            try {
                CrmManager.getInstance().createTicket(mContext, userId, userType, quoteId,
                        issue, isRC, vehicleType, make, model, variant, fuelType, cubic,
                        seating, gvw, insureName, cityName, pageUrl, pageType, policyNo, requestNo,
                        remark, paymentDoneDoc, rcFrontDoc, rcBackDoc, errorDoc, attachmentDoc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValid() {

        vehicleType = spnVehicleType.getSelectedItem().toString();
        pageType = spnPages.getSelectedItem().toString();
        make = edtMake.getText().toString();
        model = edtModel.getText().toString();
        variant = edtVariant.getText().toString();
        fuelType = edtFuel.getText().toString();
        seating = edtSeating.getText().toString();
        cubic = edtCubicCapacity.getText().toString();
        gvw = edtCubicCapacity.getText().toString();
        insureName = edtFinancierName.getText().toString();
        cityName = edtCity.getText().toString();
        pageUrl = edtUrl.getText().toString();
        policyNo = edtCommon.getText().toString();
        requestNo = edtPayout.getText().toString();
        remark = edtRemark.getText().toString();

        switch (ticketType) {
            case "claim":
                if (TextUtils.isEmpty(policyNo)) {
                    edtCommon.setError(getString(R.string.field_cannot));
                    return false;
                }
                break;
            case "survey":
                if (TextUtils.isEmpty(attachmentDoc)) {
                    AppUtils.showToast(mContext, "Choose File!");
                    return false;
                }
                break;
            case "payout":
                if (TextUtils.isEmpty(policyNo)) {
                    edtCommon.setError(getString(R.string.field_cannot));
                    return false;
                } else if (TextUtils.isEmpty(requestNo)) {
                    edtPayout.setError(getString(R.string.field_cannot));
                    return false;
                }
                break;
            case "make":
                if (isRC.equals("Yes")) {
                    if (TextUtils.isEmpty(rcFrontDoc)) {
                        AppUtils.showToast(mContext, "Choose RC Front File!");
                        return false;
                    } else if (TextUtils.isEmpty(rcBackDoc)) {
                        AppUtils.showToast(mContext, "Choose RC Back File!");
                        return false;
                    }
                } else {
                    if (TextUtils.isEmpty(make)) {
                        edtMake.setError(getString(R.string.field_cannot));
                        return false;
                    } else if (TextUtils.isEmpty(model)) {
                        edtModel.setError(getString(R.string.field_cannot));
                        return false;
                    } else if (TextUtils.isEmpty(variant)) {
                        edtVariant.setError(getString(R.string.field_cannot));
                        return false;
                    } else if (TextUtils.isEmpty(fuelType)) {
                        edtFuel.setError(getString(R.string.field_cannot));
                        return false;
                    } else if (TextUtils.isEmpty(cubic)) {
                        edtCubicCapacity.setError(getString(R.string.field_cannot));
                        return false;
                    } else if (TextUtils.isEmpty(gvw)) {
                        edtCubicCapacity.setError(getString(R.string.field_cannot));
                        return false;
                    }
                }
                break;
            case "hpn":
                if (TextUtils.isEmpty(insureName)) {
                    edtFinancierName.setError(getString(R.string.field_cannot));
                    return false;
                } else if (TextUtils.isEmpty(cityName)) {
                    edtCity.setError(getString(R.string.field_cannot));
                    return false;
                }
                break;
            case "proposal":
                if (TextUtils.isEmpty(pageUrl)) {
                    edtUrl.setError(getString(R.string.field_cannot));
                    return false;
                } else if (TextUtils.isEmpty(errorDoc) && TextUtils.isEmpty(paymentDoneDoc)
                        && TextUtils.isEmpty(attachmentDoc)) {
                    AppUtils.showToast(mContext, "Choose File!");
                    return false;
                }
                break;
        }

        if (TextUtils.isEmpty(remark)) {
            edtRemark.setError(getString(R.string.field_cannot));
            return false;
        }

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

        if (commonTextView == edtDocRcF) {
            image = File.createTempFile("Img_R", ".jpg", storageDir);
        } else if (commonTextView == edtDocFile) {
            image = File.createTempFile("Img", ".jpg", storageDir);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case AppUtils.REQUEST_CODE_CAMERA:
                    if (true) {//intent != null) {
                        commonTextView.setText(photoFile.getName());
                        if (commonTextView == edtDocRcF)
                            rcFrontDoc = photoFile.getAbsolutePath();

                        else if (commonTextView == edtDocFile) {
                            attachmentDoc = photoFile.getAbsolutePath();

                            switch (ticketType) {
                                case "payment_done":
                                    paymentDoneDoc = photoFile.getAbsolutePath();
                                    break;
                                case "error":
                                    errorDoc = photoFile.getAbsolutePath();
                                    break;
                                case "other":
                                    attachmentDoc = photoFile.getAbsolutePath();
                                    break;
                            }
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

                            if (commonTextView == edtDocRcF) {
                                rcFrontDoc = getRealPathFromURI(selectedImage);
                            } else if (commonTextView == edtDocFile) {
                                attachmentDoc = getRealPathFromURI(selectedImage);
                                switch (ticketType) {
                                    case "payment_done":
                                        paymentDoneDoc = getRealPathFromURI(selectedImage);
                                        break;
                                    case "error":
                                        errorDoc = getRealPathFromURI(selectedImage);
                                        break;
                                    case "other":
                                        attachmentDoc = getRealPathFromURI(selectedImage);
                                        break;
                                }
                            } else
                                Toast.makeText(mContext, "Some thing went wrong...", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;

                case AppUtils.REQUEST_CODE_FILES:
                    Uri data1 = data.getData();
                    if (commonTextView == edtDocRcF)
                        rcFrontDoc = getRealPathFromURIPdf(mContext, data1);

                    else if (commonTextView == edtDocFile) {
                        switch (ticketType) {
                            case "payment_done":
                                paymentDoneDoc = getRealPathFromURIPdf(mContext, data1);
                                break;
                            case "error":
                                errorDoc = getRealPathFromURIPdf(mContext, data1);
                                break;
                            case "other":
                                attachmentDoc = getRealPathFromURIPdf(mContext, data1);
                                break;
                        }
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
}