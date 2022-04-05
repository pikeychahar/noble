package com.dmw.noble.activity.health;

import android.os.Bundle;
import android.view.MenuItem;
import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;

public class SumInsuredActivity extends AbstractActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_insured);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
