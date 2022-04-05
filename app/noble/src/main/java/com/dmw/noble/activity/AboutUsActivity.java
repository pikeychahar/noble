package com.dmw.noble.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.dmw.noble.R;

import java.util.Objects;

/**
 * Created by Prahalad Kumar Chahar
 */
public class AboutUsActivity extends AbstractActivity {
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mContext = this;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCertificateClick(View view) {
        Intent intent = new Intent(mContext, ImageActivity.class);
        intent.putExtra("img", "https://www.squareinsurance.in/assets/images/Licence.jpg");
        startActivity(intent);
    }

    public void onContact(View view) {
        startActivity(new Intent(mContext, ContactActivity.class));
    }
}
