package com.dmw.noble.life;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.ApiManager;
import com.dmw.noble.model_life.LifeProposal;
import com.dmw.noble.utils.AppUtils;

import java.util.Objects;

public class OverviewLifeActivity extends AbstractActivity
        implements onRequestCompleteCallBackListener {
    Context mContext;
    LifeProposal lifeProposal;
    ImageView imgInsureLogo;
    TextView txtPlanName, txtQuoteId, txtNet, txtGst, txtGross;
    EditText edtName, edtAddress, edtMobile, edtNName, edtNRelation, edtNDob, edtSumInsured,
            edtAnnualIncome, edtTobacco;
    Bundle mBundle;
    String quotationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_life);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        this.setTitle("Review details");
        final Intent intent = getIntent();
        lifeProposal = (LifeProposal) intent.getSerializableExtra("lifeProposal");

        imgInsureLogo = findViewById(R.id.imgInsureLogo);
        txtPlanName = findViewById(R.id.txtPlanName);
        txtQuoteId = findViewById(R.id.txtQuoteId);
        txtNet = findViewById(R.id.txtNet);
        txtGst = findViewById(R.id.txtGst);
        txtGross = findViewById(R.id.txtGross);

        edtName = findViewById(R.id.edtOName);
        edtAddress = findViewById(R.id.edtAddress);
        edtMobile = findViewById(R.id.edtMobile);

        edtNName = findViewById(R.id.edtNName);
        edtNRelation = findViewById(R.id.edtNRelation);
        edtNDob = findViewById(R.id.edtNAge);
        edtSumInsured = findViewById(R.id.edtSumInsured);
        edtAnnualIncome = findViewById(R.id.edtAnnualIncome);
        edtTobacco = findViewById(R.id.edtTobacco);

        mBundle = getIntent().getExtras();
        if (mBundle != null) {
            quotationId = mBundle.getString(AppUtils.QUOTATION_ID);
            txtQuoteId.setText(quotationId);
            fetchLifeProposal();
        }

        if (lifeProposal != null) {
            initValue();
        }
    }

    public void onTermsClick(View view) {

    }

    public void initValue() {
        String path = lifeProposal.getLogo();
        Glide.with(mContext)
                .load(path)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(imgInsureLogo);

        txtPlanName.setText(lifeProposal.getPlanName());

        txtNet.setText(String.valueOf(lifeProposal.getNet()));
        txtGst.setText(String.valueOf(lifeProposal.getTax()));
        txtGross.setText(String.valueOf(lifeProposal.getFinal()));

        edtAddress.setText(String.format("%s %s %s", lifeProposal.getAddress1(),
                lifeProposal.getAddress2(), lifeProposal.getAddress3()));

        edtMobile.setText(lifeProposal.getProposerMobile());

        edtNName.setText(String.format("%s %s", lifeProposal.getNomineeFirstName(),
                lifeProposal.getNomineeLastName()));
        edtName.setText(String.format("%s %s", lifeProposal.getFirstName(),
                lifeProposal.getLastName()));

        edtNRelation.setText(lifeProposal.getNomineeRelationValue());
        edtNDob.setText(lifeProposal.getNomineeDob());
        edtSumInsured.setText(lifeProposal.getSumInsured());
        edtAnnualIncome.setText(lifeProposal.getAnnualIncome());

        String tobacco = lifeProposal.getTobaccoUser();
        if (!TextUtils.isEmpty(tobacco) && tobacco.equals("1"))
            edtTobacco.setText("Yes");
        else edtTobacco.setText("No");
    }

    public void onShareLink(View view) {
        String main = "https://www.squareinsurance.in/life_review/index/?quote=";

        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Square Insurance");
            String msg = " Thank you for showing interest in Square Insurance Brokers Pvt Ltd for your Life insurance . You are just one step far from your policy . Just click on the link and make payment to generate your health policy\n";
            String url = msg + main + quotationId;

            shareIntent.putExtra(Intent.EXTRA_TEXT, url);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
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
        if (response instanceof LifeProposal) {
            LifeProposal cResponse = (LifeProposal) response;
            if (cResponse.getStatus().equals("1")) {
                lifeProposal = cResponse;
                initValue();
            }
        }
    }

    public void fetchLifeProposal() {
        if (AppUtils.isOnline(mContext)) {
            try {
                ApiManager.getInstance().fetchLifeProposal(mContext, quotationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }
}