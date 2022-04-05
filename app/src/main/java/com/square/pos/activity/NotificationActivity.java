package com.square.pos.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.square.pos.R;

/**
 * Created by Prahalad Kumar Chahar
 */
public class NotificationActivity extends AbstractActivity {
    private Context mContext;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContext = this;
        recyclerView = findViewById(R.id.recycler);

        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
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

    public void onClose(View view) {
        finish();
    }
}
