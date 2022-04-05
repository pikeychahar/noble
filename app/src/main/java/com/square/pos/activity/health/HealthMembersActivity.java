package com.square.pos.activity.health;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.square.pos.R;
import com.square.pos.activity.AbstractActivity;
import com.square.pos.utils.AppUtils;

import java.util.Objects;

public class HealthMembersActivity extends AbstractActivity {

    private CheckBox chkSelf, chkSon, chkDaughter, chkSpouse, chkFather, chkMother;
    private Context mContext;
    private int totalSon, totalDaughter;
    private boolean isSelf, isSpouse, isFather, isMother, isIndividual;
    private Bundle mBundle;
    private String planType, individualName, flSelf, flSpouse, flFather, flMother;
    private String[] memberArray;
    TextView txtTotalSon, txtTotalDaughter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_members);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        mBundle = getIntent().getExtras();

        chkSelf = findViewById(R.id.chkSelf);
        chkSon = findViewById(R.id.chkSon);
        chkDaughter = findViewById(R.id.chkDaughter);
        chkFather = findViewById(R.id.chkFather);
        chkMother = findViewById(R.id.chkMother);
        chkSpouse = findViewById(R.id.chkSpouse);

        txtTotalSon = findViewById(R.id.txtTotalSon);
        txtTotalDaughter = findViewById(R.id.txtTotalDaughter);

        chkSelf.setEnabled(true);
        chkSelf.setChecked(true);
        if (mBundle != null) {
            planType = mBundle.getString(AppUtils.PLAN_TYPE);
        }
        if (!TextUtils.isEmpty(planType))
            if (planType.equalsIgnoreCase("Individual")) {
                isIndividual = true;
                findViewById(R.id.rlSD).setVisibility(View.GONE);
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

    public void onMemberAgeClick(View view) {
        getValues();
        int check = 0;
        if (!isIndividual) {
            if (isSelf)
                check++;
            if (isSpouse)
                check++;
            if (isFather)
                check++;
            if (isMother)
                check++;
        }
        if (check > 2)
            Toast.makeText(mContext, "Only 2 Adults allowed", Toast.LENGTH_SHORT).show();
        else if (!isIndividual && check <= 1)
            AppUtils.showToast(mContext, "Select Minimum 2 Members");
        else {
            Intent intent = new Intent(mContext, MembersAgeActivity.class);
            mBundle.putBoolean(AppUtils.IS_SELF, isSelf);
            mBundle.putBoolean(AppUtils.IS_SPOUSE, isSpouse);
            mBundle.putBoolean(AppUtils.IS_FATHER, isFather);
            mBundle.putBoolean(AppUtils.IS_MOTHER, isMother);
            mBundle.putInt(AppUtils.TOTAL_SON, totalSon);
            mBundle.putInt(AppUtils.TOTAL_DAUGHTER, totalDaughter);
//            mBundle.putString(AppUtils.INDIVIDUAL_NAME, individualName);
            mBundle.putString(AppUtils.FL_SELF_NAME, flSelf);
            mBundle.putString(AppUtils.FL_SPOUSE_NAME, flSpouse);
            mBundle.putString(AppUtils.FL_FATHER_NAME, flFather);
            mBundle.putString(AppUtils.FL_MOTHER_NAME, flMother);
            mBundle.putStringArray(AppUtils.MEMBER_ARRAY, memberArray);
            intent.putExtra("mBundle", mBundle);

            if (isSelected())
                startActivity(intent);
            else Toast.makeText(mContext, "Please Select at least one",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void getValues() {

        isSelf = chkSelf.isChecked();
        isSpouse = chkSpouse.isChecked();
        isFather = chkFather.isChecked();
        isMother = chkMother.isChecked();

        if (chkDaughter.isChecked())
            totalDaughter = Integer.parseInt(txtTotalDaughter.getText().toString());
        else totalDaughter = 0;
        if (chkSon.isChecked())
            totalSon = Integer.parseInt(txtTotalSon.getText().toString());
        else totalSon = 0;


        if (isIndividual) {
            memberArray = new String[1];

            if (chkSelf.isChecked())
                memberArray[0] = "self";
            else if (chkSpouse.isChecked())
                memberArray[0] = "Spouse";
            else if (chkFather.isChecked())
                memberArray[0] = "Father";
            else if (chkMother.isChecked())
                memberArray[0] = "Mother";

        } else {
            memberArray = new String[8];
            int index = 0;
            if (chkSelf.isChecked()) {
                memberArray[index] = "self";
                index++;
            }
            if (chkSpouse.isChecked()) {
                memberArray[index] = "Spouse";
                index++;
            }
            if (chkFather.isChecked()) {
                memberArray[index] = "Father";
                index++;
            }
            if (chkMother.isChecked()) {
                memberArray[index] = "Mother";
                index++;
            }
            if (chkSon.isChecked()) {
                totalSon = Integer.parseInt(txtTotalSon.getText().toString());
                if (totalSon == 1) {
                    memberArray[index] = "Son1";
                    index++;
                }
                if (totalSon == 2) {
                    memberArray[index] = "Son1";
                    index++;
                    memberArray[index] = "Son2";
                }
            }

            if (chkDaughter.isChecked()) {
                totalDaughter = Integer.parseInt(txtTotalDaughter.getText().toString());

                if (chkSon.isChecked())
                    if (totalSon > 0)
                        index++;
                if (totalDaughter == 1) {
                    memberArray[index] = "Daughter1";
                } else if (totalDaughter == 2) {
                    memberArray[index] = "Daughter1";
                    index++;
                    memberArray[index] = "Daughter2";
                }
            }
        }
    }

    private boolean isSelected() {
        if (chkSelf.isChecked())
            return true;
        else if (chkSpouse.isChecked())
            return true;
        else if (chkSon.isChecked())
            return true;
        else if (chkDaughter.isChecked())
            return true;
        else if (chkFather.isChecked())
            return true;
        else return chkMother.isChecked();
    }

    public void onCheckBoxClick(View view) {
        if (view == chkSelf) {
            if (isIndividual) {
                if (chkSelf.isChecked()) {
                    chkSpouse.setChecked(false);
                    chkSon.setChecked(false);
                    chkDaughter.setChecked(false);
                    chkFather.setChecked(false);
                    chkMother.setChecked(false);
                }
            } else {
                chkSelf.setChecked(true);
            }
        } else if (view == chkSpouse) {
            if (isIndividual) {
                if (chkSpouse.isChecked()) {
                    chkSelf.setChecked(false);
                    chkSon.setChecked(false);
                    chkDaughter.setChecked(false);
                    chkFather.setChecked(false);
                    chkMother.setChecked(false);
                }
            }

        } else if (view == chkSon) {
            if (isIndividual) {
                if (chkSon.isChecked()) {
                    chkSelf.setChecked(false);
                    chkSpouse.setChecked(false);
                    chkDaughter.setChecked(false);
                    chkFather.setChecked(false);
                    chkMother.setChecked(false);
                }
            } else {
                if (chkSon.isChecked())
                    findViewById(R.id.rlAddSon).setVisibility(View.VISIBLE);
                else findViewById(R.id.rlAddSon).setVisibility(View.GONE);
            }

        } else if (view == chkDaughter) {
            if (isIndividual) {
                if (chkDaughter.isChecked()) {
                    chkSelf.setChecked(false);
                    chkSon.setChecked(false);
                    chkSpouse.setChecked(false);
                    chkFather.setChecked(false);
                    chkMother.setChecked(false);
                }
            } else {
                if (chkDaughter.isChecked())
                    findViewById(R.id.rlAddDaughter).setVisibility(View.VISIBLE);
                else findViewById(R.id.rlAddDaughter).setVisibility(View.GONE);
            }

        } else if (view == chkFather) {
            if (isIndividual) {
                if (chkFather.isChecked()) {
                    chkSelf.setChecked(false);
                    chkSon.setChecked(false);
                    chkDaughter.setChecked(false);
                    chkSpouse.setChecked(false);
                    chkMother.setChecked(false);
                }
            }

        } else if (view == chkMother) {
            if (isIndividual) {
                if (chkMother.isChecked()) {
                    chkSelf.setChecked(false);
                    chkSon.setChecked(false);
                    chkDaughter.setChecked(false);
                    chkFather.setChecked(false);
                    chkSpouse.setChecked(false);
                }
            }
        }
    }

    public void onAddSon(View view) {
        totalSon = Integer.parseInt(txtTotalSon.getText().toString());
        if (totalSon == 2)
            Toast.makeText(mContext, "Maximum 2", Toast.LENGTH_SHORT).show();
        else {
            txtTotalSon.setText("2");
        }

    }

    public void onSubSon(View view) {
        totalSon = Integer.parseInt(txtTotalSon.getText().toString());
        if (totalSon == 1)
            Toast.makeText(mContext, "Minimum 1", Toast.LENGTH_SHORT).show();
        else {
            txtTotalSon.setText("1");
        }
    }

    public void onSubDaughter(View view) {
        totalDaughter = Integer.parseInt(txtTotalDaughter.getText().toString());

        if (totalDaughter == 1)
            Toast.makeText(mContext, "Minimum 1", Toast.LENGTH_SHORT).show();
        else {
            txtTotalDaughter.setText("1");
        }
    }

    public void onAddDaughter(View view) {
        totalDaughter = Integer.parseInt(txtTotalDaughter.getText().toString());
        if (totalDaughter == 2)
            Toast.makeText(mContext, "Maximum 2", Toast.LENGTH_SHORT).show();
        else {
            txtTotalDaughter.setText("2");
        }
    }
}
