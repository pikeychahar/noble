package com.dmw.noble.activity_docs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.adaptor.DocsAdapter;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.AgentManager;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.model.BasicResponse;
import com.dmw.noble.model.DocsList;
import com.dmw.noble.model.DocsWallet;
import com.dmw.noble.utils.AppUtils;

import java.util.ArrayList;
import java.util.Objects;

public class DocsAllActivity extends AbstractActivity implements
        onRequestCompleteCallBackListener, DocsAdapter.OnDocClickListener, DocsAdapter.OnRemoveClickListener {
    private ArrayList<DocsWallet> docsList = new ArrayList<>();
    private DocsAdapter docsAdapter;
    private Context mContext;
    private RecyclerView recyclerView;
    private String agentId, docId;
    private RecyclerView.LayoutManager mLayoutManager;
    private SharedPreferences preferences;
    private ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docs_all);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        agentId = preferences.getString(AppUtils.PRIMARY_ID, "");
        mContext = this;

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");

        recyclerView = findViewById(R.id.docList);
        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        //if (!TextUtils.isEmpty(agentId)) {
        getDocsList();

        docsAdapter = new DocsAdapter(mContext, docsList);
        recyclerView.setAdapter(docsAdapter);
        docsAdapter.notifyDataSetChanged();
        //}
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (itemId == R.id.action_new_doc) {
            Intent intent = new Intent(mContext, DocWalletActivity.class);
            startActivityForResult(intent, 555);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 555) {
            getDocsList();
        }
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof DocsList) {
            DocsList policyResponse = (DocsList) response;
            if (policyResponse.getStatus() == 1) {
                if (docsList.size() > 0)
                    docsList.clear();
                if (policyResponse.getDocsWallet() != null) {
                    docsList.addAll(policyResponse.getDocsWallet());
                    if (docsList.size() == 0) {
                        findViewById(R.id.rlNoDoc).setVisibility(View.VISIBLE);
                    } else findViewById(R.id.rlNoDoc).setVisibility(View.GONE);

                    docsAdapter.notifyDataSetChanged();
                }
            }
        }
        if (response instanceof BasicResponse) {
            BasicResponse basicResponse = (BasicResponse) response;
            if (basicResponse.getStatus().equals(1))
                getDocsList();

        }
        progressdialog.dismiss();
    }

    public void getDocsList() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().getDocsWalletList(this, agentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_doc, menu);
        return true;
    }


    @Override
    public void OnDocClicked(int position) {
        DocsWallet docsWalletObj = docsList.get(position);
        Intent intent = new Intent(mContext, DocViewActivity.class);
        intent.putExtra("doc", docsWalletObj);
        startActivity(intent);
    }

    @Override
    public void OnRemoveClicked(int position) {
        DocsWallet docsWalletObj = docsList.get(position);
        docId = docsWalletObj.getId();
        if (!TextUtils.isEmpty(docId))
            removeDialog();

    }

    public void removeDoc(String docId) {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                AgentManager.getInstance().removeDoc(mContext, docId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

        alertDialogBuilder.setTitle((R.string.app_name));
        alertDialogBuilder.setIcon(R.drawable.logo_app);
        alertDialogBuilder.setMessage("Do you want to Remove this Doc!");
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton(R.string.str_yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                removeDoc(docId);
                            }
                        })
                .setNegativeButton(R.string.no, null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}