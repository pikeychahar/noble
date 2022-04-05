package com.square.pos.activity.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;

/**
 * Created by Prahalad Kumar Chahar
 */
public class TravelInsuranceActivity extends AbstractActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_insurance);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    public void onWorldWide(View view) {
        Toast.makeText(mContext, "Coming Soon...", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(mContext, GeographicalActivity.class));
    }
}
