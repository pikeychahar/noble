package com.square.pos.activity_crm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.adaptor.crm.TicketAdapter;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.CrmManager;
import com.square.pos.model_crm.MyTicket;
import com.square.pos.model_crm.TicketData;
import com.square.pos.utils.AppUtils;

import java.util.ArrayList;
import java.util.Objects;

public class TicketActivity extends AbstractActivity implements onRequestCompleteCallBackListener,
        TicketAdapter.OnTicketStatusListener {
    Context mContext;
    RecyclerView rcAllTicket;
    String userId, userType;
    ProgressDialog progressDialog;
    SharedPreferences preferences;
    ArrayList<TicketData> ticketList = new ArrayList<>();
    TicketAdapter ticketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);

        userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        userType = preferences.getString(AppUtils.USER_TYPE, "");

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait..");

        rcAllTicket = findViewById(R.id.rcAllTicket);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rcAllTicket.setLayoutManager(mLayoutManager);
        getAllTicket();

        ticketAdapter = new TicketAdapter(mContext, ticketList);
        rcAllTicket.setAdapter(ticketAdapter);
        ticketAdapter.notifyDataSetChanged();
    }

    public void onCreateTicket(View view) {
//        someActivityResultLauncher.launch(new Intent(mContext, NewTicketActivity.class));
        Intent intent = new Intent(mContext, NewTicketActivity.class);
        startActivityForResult(intent, 555);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK)
            getAllTicket();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getAllTicket() {
        if (AppUtils.isOnline(mContext)) {
            progressDialog.show();
            try {
                CrmManager.getInstance().getAllTicket(mContext, userId, userType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof MyTicket) {
            MyTicket policyResponse = (MyTicket) response;
            if (policyResponse.isStatus()) {
                if (ticketList.size() > 0)
                    ticketList.clear();
                if (policyResponse.getData() != null) {
                    findViewById(R.id.rlNoLeads).setVisibility(View.GONE);
                    ticketList.addAll(policyResponse.getData());
                    ticketAdapter.notifyDataSetChanged();
                } else findViewById(R.id.rlNoLeads).setVisibility(View.VISIBLE);
            } else findViewById(R.id.rlNoLeads).setVisibility(View.VISIBLE);
        }
        progressDialog.dismiss();
    }

    @Override
    public void onTicketClick(int position) {
        Bundle mBundle = new Bundle();
        TicketData clamObj = ticketList.get(position);
        Intent intent = new Intent(mContext, ChatActivity.class);
        mBundle.putString(AppUtils.CLAIM_ID, clamObj.getId());
        mBundle.putString(AppUtils.CLAIM_NO, clamObj.getTicketId());
        mBundle.putString(AppUtils.CLAIM_MANAGER, clamObj.getTicketManager());
        mBundle.putString(AppUtils.CHAT_TYPE, "Ticket");
        intent.putExtras(mBundle);

        if (!TextUtils.isEmpty(clamObj.getTicketManager()))
            startActivity(intent);
        else
            AppUtils.showToast(mContext, "Assign Manager not found");
    }
}