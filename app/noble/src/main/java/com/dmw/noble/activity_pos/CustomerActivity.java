package com.dmw.noble.activity_pos;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.adaptor.CustomerAdapter;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.model_pos.Customer;
import com.dmw.noble.model_pos.CustomerList;
import com.dmw.noble.utils.AppUtils;

import java.util.ArrayList;
import java.util.Objects;

public class CustomerActivity extends AbstractActivity implements
        onRequestCompleteCallBackListener, CustomerAdapter.OnCallClickListener {

    ArrayList<Customer> customerList = new ArrayList<>();
    ArrayList<Customer> incompleteList = new ArrayList<>();
    ArrayList<Customer> trainingList = new ArrayList<>();
    ArrayList<Customer> pendingList = new ArrayList<>();
    ArrayList<Customer> verifiedList = new ArrayList<>();
    ArrayList<Customer> certifiedList = new ArrayList<>();
    ArrayList<Customer> rejectedList = new ArrayList<>();
    private CustomerAdapter customerAdapter;
    private Context mContext;
    private RecyclerView recyclerView;
    private String agentId, userType;
    RecyclerView.LayoutManager mLayoutManager;
    SharedPreferences preferences;
    private ProgressDialog progressdialog;
    int filterType = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        agentId = preferences.getString(AppUtils.PRIMARY_ID, "");

        userType = preferences.getString(AppUtils.USER_TYPE, "");
        posStatus = preferences.getString(AppUtils.POS_STATUS, "");

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");
//        progressdialog.setCancelable(false);

        recyclerView = findViewById(R.id.customerList);

        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        //if (!TextUtils.isEmpty(agentId)) {
        getPolicyList();
        customerAdapter = new CustomerAdapter(mContext, customerList);
        recyclerView.setAdapter(customerAdapter);
        customerAdapter.notifyDataSetChanged();
        //}
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
        if (response instanceof CustomerList) {
            CustomerList policyResponse = (CustomerList) response;
            if (policyResponse.getStatus().equals("1")) {
                if (customerList.size() > 0)
                    customerList.clear();
                if (policyResponse.getCustomer() != null) {
                    customerList.addAll(policyResponse.getCustomer());

                    for (int index = 0; index < customerList.size(); index++) {
                        if (customerList.get(index).getPosStatus().equals("0"))
                            pendingList.add(customerList.get(index));
                        else if (customerList.get(index).getPosStatus().equals("1"))
                            verifiedList.add(customerList.get(index));
                        else if (customerList.get(index).getPosStatus().equals("2"))
                            certifiedList.add(customerList.get(index));
                        else if (customerList.get(index).getPosStatus().equals("3"))
                            incompleteList.add(customerList.get(index));
                        else if (customerList.get(index).getPosStatus().equals("4"))
                            trainingList.add(customerList.get(index));
                        else if (customerList.get(index).getPosStatus().equals("6"))
                            rejectedList.add(customerList.get(index));
                    }
                    if (customerList.size() == 0) {
                        findViewById(R.id.rlNoPolicies).setVisibility(View.VISIBLE);
                    } else findViewById(R.id.rlNoPolicies).setVisibility(View.GONE);
                    customerAdapter.notifyDataSetChanged();
                }
            }else findViewById(R.id.rlNoPolicies).setVisibility(View.VISIBLE);
        }
        progressdialog.dismiss();
    }

    public void getPolicyList() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().getCustomerList(mContext, userType, agentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void OnCallClicked(int position, String phone) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        String phoneNumber = "tel:" + phone;
        callIntent.setData(Uri.parse(phoneNumber));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        startActivity(callIntent);
    }

    public void onAllPosClick(View view) {
        customerAdapter = new CustomerAdapter(mContext, customerList);
        recyclerView.setAdapter(customerAdapter);
        customerAdapter.notifyDataSetChanged();
        if (customerList.size() == 0) {
            findViewById(R.id.rlNoPolicies).setVisibility(View.VISIBLE);
        } else findViewById(R.id.rlNoPolicies).setVisibility(View.GONE);
    }

    public void onPendingPos(View view) {
        customerAdapter = new CustomerAdapter(mContext, pendingList);
        recyclerView.setAdapter(customerAdapter);
        customerAdapter.notifyDataSetChanged();
        if (pendingList.size() == 0) {
            findViewById(R.id.rlNoPolicies).setVisibility(View.VISIBLE);
        } else findViewById(R.id.rlNoPolicies).setVisibility(View.GONE);
    }

    public void onVerifiedPos(View view) {
        customerAdapter = new CustomerAdapter(mContext, verifiedList);
        recyclerView.setAdapter(customerAdapter);
        customerAdapter.notifyDataSetChanged();
        if (verifiedList.size() == 0) {
            findViewById(R.id.rlNoPolicies).setVisibility(View.VISIBLE);
        } else findViewById(R.id.rlNoPolicies).setVisibility(View.GONE);
    }

    public void onCertifiedClick(View view) {
        customerAdapter = new CustomerAdapter(mContext, certifiedList);
        recyclerView.setAdapter(customerAdapter);
        customerAdapter.notifyDataSetChanged();
        if (certifiedList.size() == 0) {
            findViewById(R.id.rlNoPolicies).setVisibility(View.VISIBLE);
        } else findViewById(R.id.rlNoPolicies).setVisibility(View.GONE);
    }

    public void OnRejectedPos(View view) {
        customerAdapter = new CustomerAdapter(mContext, rejectedList);
        recyclerView.setAdapter(customerAdapter);
        customerAdapter.notifyDataSetChanged();
        if (rejectedList.size() == 0) {
            findViewById(R.id.rlNoPolicies).setVisibility(View.VISIBLE);
        } else findViewById(R.id.rlNoPolicies).setVisibility(View.GONE);
    }

    public void onTrainingClick(View view) {
        customerAdapter = new CustomerAdapter(mContext, trainingList);
        recyclerView.setAdapter(customerAdapter);
        customerAdapter.notifyDataSetChanged();
        if (trainingList.size() == 0) {
            findViewById(R.id.rlNoPolicies).setVisibility(View.VISIBLE);
        } else findViewById(R.id.rlNoPolicies).setVisibility(View.GONE);
    }

    public void onIncompletePos(View view) {
        customerAdapter = new CustomerAdapter(mContext, incompleteList);
        recyclerView.setAdapter(customerAdapter);
        customerAdapter.notifyDataSetChanged();
        if (incompleteList.size() == 0) {
            findViewById(R.id.rlNoPolicies).setVisibility(View.VISIBLE);
        } else findViewById(R.id.rlNoPolicies).setVisibility(View.GONE);
    }
}