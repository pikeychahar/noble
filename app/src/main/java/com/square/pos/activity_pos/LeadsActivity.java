package com.square.pos.activity_pos;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.adaptor.LeadAdapter;
import com.square.pos.interfaces.onRequestCompleteCallBackListener;
import com.square.pos.manager.UserManager;
import com.square.pos.model_pos.Lead;
import com.square.pos.model_pos.Leads;
import com.square.pos.utils.AppUtils;

import java.util.ArrayList;

public class LeadsActivity extends AbstractActivity implements onRequestCompleteCallBackListener,
        LeadAdapter.OnViewLead {

    private ArrayList<Lead> quotationList = new ArrayList<>();
    private ArrayList<Lead> searchList = new ArrayList<>();
    private LeadAdapter leadAdapter;
    private Context mContext;
    private RecyclerView recyclerView;
    private String agentId;
    private SharedPreferences preferences;
    private ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leads);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        mContext = this;


        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");
        recyclerView = findViewById(R.id.leadList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);

        agentId = preferences.getString(AppUtils.PRIMARY_ID, "");
        getLeads();
        //}
        leadAdapter = new LeadAdapter(mContext, quotationList);
        recyclerView.setAdapter(leadAdapter);
        leadAdapter.notifyDataSetChanged();
    }


    @Override
    public void onResponse(Object response) {
        if (response instanceof Leads) {
            Leads leadResponse = (Leads) response;
            if (leadResponse.getSuccess().equalsIgnoreCase("1")) {
                if (quotationList.size() > 0)
                    quotationList.clear();
                    searchList.clear();
                if (leadResponse.getLeads() != null) {
                    quotationList.addAll(leadResponse.getLeads());
                    searchList.addAll(quotationList);
                    if (quotationList.size() == 0) {
                        findViewById(R.id.rlNoLeads).setVisibility(View.VISIBLE);
                    } else findViewById(R.id.rlNoLeads).setVisibility(View.GONE);
                    leadAdapter.notifyDataSetChanged();
                }
            }else findViewById(R.id.rlNoLeads).setVisibility(View.VISIBLE);
        }
        progressdialog.dismiss();
    }

    public void getLeads() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().getLeads(this, agentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchViewItem.getActionView();
//        searchView.setQueryHint("Search");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setIconifiedByDefault(false);// Do not iconify the widget; expand it by defaul

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // This is your adapter that will be filtered
               searchForTextInList(newText);

                return true;
            }


            public boolean onQueryTextSubmit(String query) {
                // **Here you can get the value "query" which is entered in the search box.**


                return true;
            }
        };

        searchView.setOnQueryTextListener(queryTextListener);

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                quotationList.addAll(searchList);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_search:
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 555) {
            getLeads();
        }
    }

    @Override
    public void OnViewLeadClicked(int position) {
        Lead leadObj = quotationList.get(position);
        Intent intent = new Intent(mContext, LeadViewActivity.class);
        intent.putExtra("lead", leadObj);
        startActivity(intent);
    }

    public void onAddNewLead(View view) {
        Intent intent = new Intent(mContext, AddLeadActivity.class);
        startActivityForResult(intent, 555);
    }

    private synchronized void searchForTextInList(String searchString) {
        int textLength = searchString.length();
        quotationList.clear();
        if (searchString.trim().length() == 0) {
            quotationList.addAll(searchList);
        } else {
            searchString = searchString.replaceAll("^\\s+", "");

            for (int i = 0; i < searchList.size(); i++) {
                Lead assessment = searchList.get(i);
                String userName = "";
                if (!TextUtils.isEmpty(assessment.getName()))
                    userName = assessment.getName().toLowerCase();
                String contactNo = "";
                if (!TextUtils.isEmpty(assessment.getMobile()))
                    contactNo = assessment.getMobile();
                if (textLength > 0) {
                    if (userName.contains(searchString) || contactNo.contains(searchString)) {
                        quotationList.add(assessment);
                    }
                }
            }
            leadAdapter.notifyDataSetChanged();
        }

    }
}
