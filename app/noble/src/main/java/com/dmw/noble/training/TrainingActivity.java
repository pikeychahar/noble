package com.dmw.noble.training;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.adaptor.ModuleAdapter;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.training.model.Module;
import com.dmw.noble.training.model.Module1;
import com.dmw.noble.training.model.Module3;
import com.dmw.noble.training.model.Module4;
import com.dmw.noble.training.model.ModuleMaster;
import com.dmw.noble.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TrainingActivity extends AbstractActivity implements onRequestCompleteCallBackListener,
        ModuleAdapter.onSubModuleClickListener {

    private TextView moduleName;
    private ProgressDialog progressdialog;
    private Context mContext;
    private RecyclerView subModuleList;
    private ModuleAdapter moduleAdapter;
    private int moduleNumber;
    private Bundle mBundle;
    private TextView btnModule1, btnModule2, btnModule3, btnModule4;
    private ImageView imgStatus1, imgStatus2, imgStatus3, imgStatus4;

    private RecyclerView.LayoutManager mLayoutManager;

    private List<Module1> moduleList1 = new ArrayList<>();
    private List<Module.Module2> moduleList2 = new ArrayList<>();
    private List<Module3> moduleList3 = new ArrayList<>();
    private List<Module4> moduleList4 = new ArrayList<>();
    private SharedPreferences preferences;
    private String agentId, s1, s2, s3, s4, spendTime1, spendTime2, spendTime3, spendTime4,
            totalTime1, totalTime2, totalTime3, totalTime4;
    private boolean isAgreed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainging);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        agentId = preferences.getString(AppUtils.PRIMARY_ID, "");
        isAgreed = preferences.getBoolean(AppUtils.IS_AGREE, false);

        //set default value Zero
        s1 = s2 = s3 = s4 = spendTime1 = spendTime2 = spendTime3 = spendTime4 = totalTime1 =
                totalTime2 = totalTime3 = totalTime4 = "0";

        moduleName = findViewById(R.id.moduleName);
        subModuleList = findViewById(R.id.subModuleList);

        btnModule1 = findViewById(R.id.moduleName1);
        btnModule2 = findViewById(R.id.moduleName2);
        btnModule3 = findViewById(R.id.moduleName3);
        btnModule4 = findViewById(R.id.moduleName4);

        imgStatus1 = findViewById(R.id.status1);
        imgStatus2 = findViewById(R.id.status2);
        imgStatus3 = findViewById(R.id.status3);
        imgStatus4 = findViewById(R.id.status4);

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");
        progressdialog.setCancelable(false);

        moduleNumber = 1;
        mBundle = new Bundle();

        mLayoutManager = new LinearLayoutManager(mContext);
        subModuleList.setLayoutManager(mLayoutManager);

        if (!TextUtils.isEmpty(agentId))
            getModuleMaster();
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof ModuleMaster) {
            ModuleMaster policyResponse = (ModuleMaster) response;
            if (policyResponse.getStatus().equals(1)) {

                moduleList1 = policyResponse.getModule().getModule1();
                moduleList2 = policyResponse.getModule().getModule2();
                moduleList3 = policyResponse.getModule().getModule3();
                moduleList4 = policyResponse.getModule().getModule4();

                moduleAdapter = new ModuleAdapter(mContext, moduleList1, moduleList2, moduleList3,
                        moduleList4, 1);
                subModuleList.setAdapter(moduleAdapter);
                moduleAdapter.notifyDataSetChanged();
                btnModule1.setBackground(getResources().getDrawable(R.drawable.bg_green_dark));
                btnModule2.setBackground(getResources().getDrawable(R.drawable.bg_green));
                btnModule3.setBackground(getResources().getDrawable(R.drawable.bg_green));
                btnModule4.setBackground(getResources().getDrawable(R.drawable.bg_green));

                s1 = policyResponse.getModule().getModule1().get(0).getStatus();
                s2 = policyResponse.getModule().getModule2().get(0).getStatus();
                s3 = policyResponse.getModule().getModule3().get(0).getStatus();
                s4 = policyResponse.getModule().getModule4().get(0).getStatus();

                if (s1.equals(s2) && s2.equals(s3) && s3.equals(s4) && s4.equals("1"))  {
                    findViewById(R.id.ccExam).setVisibility(View.VISIBLE);
                } else findViewById(R.id.ccExam).setVisibility(View.GONE);

                spendTime1 = policyResponse.getModule().getModule1().get(0).getSpentTime();
                spendTime2 = policyResponse.getModule().getModule2().get(0).getSpentTime();
                spendTime3 = policyResponse.getModule().getModule3().get(0).getSpentTime();
                spendTime4 = policyResponse.getModule().getModule4().get(0).getSpentTime();

                totalTime1 = policyResponse.getModule().getModule1().get(0).getTime();
                totalTime2 = policyResponse.getModule().getModule2().get(0).getTime();
                totalTime3 = policyResponse.getModule().getModule3().get(0).getTime();
                totalTime4 = policyResponse.getModule().getModule4().get(0).getTime();

                if (!TextUtils.isEmpty(s1))
                    if (s1.equalsIgnoreCase("1"))
                        imgStatus1.setVisibility(View.VISIBLE);

                if (!TextUtils.isEmpty(s2))
                    if (s2.equalsIgnoreCase("1"))
                        imgStatus2.setVisibility(View.VISIBLE);

                if (!TextUtils.isEmpty(s3))
                    if (s3.equalsIgnoreCase("1"))
                        imgStatus3.setVisibility(View.VISIBLE);

                if (!TextUtils.isEmpty(s4))
                    if (s4.equalsIgnoreCase("1"))
                        imgStatus4.setVisibility(View.VISIBLE);
            }
            progressdialog.hide();
        }
    }

    public void getModuleMaster() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().getModuleMaster(mContext, agentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onSubModuleClick(int position) {
        try {
            Intent intent = new Intent(mContext, TrainingViewActivity.class);

            if (moduleNumber == 1) {
                Module1 module1 = moduleList1.get(position);
                mBundle.putInt("moduleNumber", moduleNumber);
                mBundle.putInt("spendTime", Integer.parseInt(spendTime1));
                mBundle.putInt("totalTime", Integer.parseInt(totalTime1));
                mBundle.putSerializable("module", module1);
            } else if (moduleNumber == 2) {
                Module.Module2 module1 = moduleList2.get(position);
                mBundle.putInt("moduleNumber", moduleNumber);
                mBundle.putInt("spendTime", Integer.parseInt(spendTime2));
                mBundle.putInt("totalTime", Integer.parseInt(totalTime2));
                mBundle.putSerializable("module", module1);
            } else if (moduleNumber == 3) {
                Module3 module1 = moduleList3.get(position);
                mBundle.putInt("moduleNumber", moduleNumber);
                mBundle.putInt("spendTime", Integer.parseInt(spendTime3));
                mBundle.putInt("totalTime", Integer.parseInt(totalTime3));
                mBundle.putSerializable("module", module1);
            } else {
                Module4 module1 = moduleList4.get(position);
                mBundle.putInt("moduleNumber", moduleNumber);
                mBundle.putInt("spendTime", Integer.parseInt(spendTime4));
                mBundle.putInt("totalTime", Integer.parseInt(totalTime4));
                mBundle.putSerializable("module", module1);
            }
            intent.putExtras(mBundle);
            startActivityForResult(intent, 12);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void onModule1Click(View view) {
        moduleNumber = 1;

        moduleAdapter = new ModuleAdapter(mContext, moduleList1, moduleList2, moduleList3,
                moduleList4, 1);
        subModuleList.setAdapter(moduleAdapter);
        moduleAdapter.notifyDataSetChanged();

        btnModule1.setBackground(getResources().getDrawable(R.drawable.bg_green_dark));
        btnModule2.setBackground(getResources().getDrawable(R.drawable.bg_green));
        btnModule3.setBackground(getResources().getDrawable(R.drawable.bg_green));
        btnModule4.setBackground(getResources().getDrawable(R.drawable.bg_green));
    }

    public void onModule2Click(View view) {

        if (s1.equals("1")) {
            moduleNumber = 2;
            moduleAdapter = new ModuleAdapter(mContext, moduleList1, moduleList2, moduleList3,
                    moduleList4, 2);
            subModuleList.setAdapter(moduleAdapter);
            moduleAdapter.notifyDataSetChanged();

            btnModule1.setBackground(getResources().getDrawable(R.drawable.bg_green));
            btnModule2.setBackground(getResources().getDrawable(R.drawable.bg_green_dark));
            btnModule3.setBackground(getResources().getDrawable(R.drawable.bg_green));
            btnModule4.setBackground(getResources().getDrawable(R.drawable.bg_green));
        } else {
            Toast.makeText(mContext, "Kindly Complete Module 1", Toast.LENGTH_SHORT).show();
        }

    }

    public void onModule3Click(View view) {
        if (s2.equals("1")) {
            moduleNumber = 3;
            moduleAdapter = new ModuleAdapter(mContext, moduleList1, moduleList2, moduleList3,
                    moduleList4, 3);
            subModuleList.setAdapter(moduleAdapter);
            moduleAdapter.notifyDataSetChanged();

            btnModule1.setBackground(getResources().getDrawable(R.drawable.bg_green));
            btnModule2.setBackground(getResources().getDrawable(R.drawable.bg_green));
            btnModule3.setBackground(getResources().getDrawable(R.drawable.bg_green_dark));
            btnModule4.setBackground(getResources().getDrawable(R.drawable.bg_green));
        } else {
            Toast.makeText(mContext, "Kindly Complete Module 2", Toast.LENGTH_SHORT).show();
        }
    }

    public void onModule4Click(View view) {

        if (s3.equals("1")) {
            moduleNumber = 4;
            moduleAdapter = new ModuleAdapter(mContext, moduleList1, moduleList2, moduleList3,
                    moduleList4, 4);
            subModuleList.setAdapter(moduleAdapter);
            moduleAdapter.notifyDataSetChanged();

            btnModule1.setBackground(getResources().getDrawable(R.drawable.bg_green));
            btnModule2.setBackground(getResources().getDrawable(R.drawable.bg_green));
            btnModule3.setBackground(getResources().getDrawable(R.drawable.bg_green));
            btnModule4.setBackground(getResources().getDrawable(R.drawable.bg_green_dark));

        } else {
            if (s2.equals("0"))
                Toast.makeText(mContext, "Kindly Complete Module 2", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mContext, "Kindly Complete Module 3", Toast.LENGTH_SHORT).show();
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            getModuleMaster();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onExamClick(View view) {
        isAgreed = preferences.getBoolean(AppUtils.IS_AGREE, false);
        if (!isAgreed)
            onTermC();
        else startActivity(new Intent(mContext, ExamActivity.class));
    }

    public void onTermC() {

        final Dialog dialog = new Dialog(mContext, android.R.style.Theme_Light);
        dialog.requestWindowFeature(Window.DECOR_CAPTION_SHADE_DARK);
        dialog.setContentView(R.layout.agreement_dialog);
        dialog.setTitle("Terms and Conditions ");

        // set the custom dialog components - text, image and button
        final CheckBox chkAgree = dialog.findViewById(R.id.chkAgree);

        Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkAgree.isChecked()) {
                    startActivity(new Intent(mContext, ExamActivity.class));
                    preferences.edit().putBoolean(AppUtils.IS_AGREE, true).apply();
                } else
                    Toast.makeText(mContext, "Kindly Agree Agreement", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}