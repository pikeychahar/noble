package com.dmw.noble.training;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.ankushgrover.hourglass.Hourglass;
import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.activity.DashboardActivity;
import com.dmw.noble.adaptor.ExamAdaptor;
import com.dmw.noble.elements.ExpandableHeightGridView;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.model.BasicResponse;
import com.dmw.noble.training.model.QuestionDatum;
import com.dmw.noble.training.model.QuestionList;
import com.dmw.noble.utils.AppUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ExamActivity extends AbstractActivity implements onRequestCompleteCallBackListener,
        ExamAdaptor.OnItemTapListener, ExamAdaptor.OnTotalNumber {

    private RadioGroup rgQuestion;
    private RadioButton rbAns1, rbAns2, rbAns3, rbAns4;
    ExamAdaptor examAdaptor;
    private Context mContext;
    private ExpandableHeightGridView recyclerView;
    private String agentId;
    SharedPreferences preferences;
    private ProgressDialog progressdialog;
    private ArrayList<QuestionDatum> questionList = new ArrayList<>();
    private TextView titleName, txtQuestion, txtTime;
    private int questionNumber = 1;
    private int cPosition = 1;
    private int preNumber, nextNumber, index;
    private Hourglass examTimer;
    int timePeriod;
    private long savedMills;
    private String answer;
    private boolean isFirst = true;
    private String result, marks;
    private boolean isSubmitExam = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        preferences = getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        agentId = preferences.getString(AppUtils.PRIMARY_ID, "");
        mContext = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");

        recyclerView = findViewById(R.id.recyclerQuestions);
        titleName = findViewById(R.id.titleName);
        txtQuestion = findViewById(R.id.txtQuestion);
        txtTime = findViewById(R.id.txtTime);

        rbAns1 = findViewById(R.id.rbQue1);
        rbAns2 = findViewById(R.id.rbQue2);
        rbAns3 = findViewById(R.id.rbQue3);
        rbAns4 = findViewById(R.id.rbQue4);

        rgQuestion = findViewById(R.id.rgQuestion);

        recyclerView.setExpanded(true);
        preNumber = index = 0;
        nextNumber = 1;

        getQuestionList();
        timePeriod = 45 * 60000;


        rgQuestion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                if (checkedRadioButton != null) {
                    boolean isChecked = checkedRadioButton.isChecked();
                    // If the radiobutton that has changed in check state is now checked...
                    if (isChecked) {
                        // Changes the textview's text to "Checked: example radiobutton text"
                        answer = checkedRadioButton.getText().toString();
                        submitQuestion(questionList.get(index).getId(), answer);
                    }
                }
            }
        });
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof QuestionList) {
            QuestionList policyResponse = (QuestionList) response;
            if (policyResponse.getStatus() == 1) {
                if (questionList.size() > 0)
                    questionList.clear();

                questionList = (ArrayList<QuestionDatum>) policyResponse.getQuestionData();
                examAdaptor = new ExamAdaptor(mContext, questionList);
                recyclerView.setAdapter(examAdaptor);
                examAdaptor.notifyDataSetChanged();

                if (isFirst) {
                    titleName.setText("Question " + questionList.get(0).getId() + " Of " + questionList.size());
                    txtQuestion.setText("Question " + questionList.get(0).getId() + " : " + questionList.get(0).getQuestion());

                    rbAns1.setText(questionList.get(0).getOption1());
                    rbAns2.setText(questionList.get(0).getOption2());
                    rbAns3.setText(questionList.get(0).getOption3());
                    rbAns4.setText(questionList.get(0).getOption4());

                    int selectedOption = questionList.get(0).getOptionNo();

                    if (selectedOption == 0) {
                        rgQuestion.clearCheck();
                    } else if (selectedOption == 1) {
                        rgQuestion.check(rgQuestion.getChildAt(0).getId());
                    } else if (selectedOption == 2) {
                        rgQuestion.check(rgQuestion.getChildAt(1).getId());
                    } else if (selectedOption == 3) {
                        rgQuestion.check(rgQuestion.getChildAt(2).getId());
                    } else if (selectedOption == 4) {
                        rgQuestion.check(rgQuestion.getChildAt(3).getId());
                    }
                    isFirst = false;

                    trainingCounter();
                }
                findViewById(R.id.layoutParent).setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(mContext, "" + policyResponse.getMsg(), Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        if (response instanceof BasicResponse) {
            BasicResponse basicResponse = (BasicResponse) response;
            if (basicResponse.getStatus() == 1) {
                if (isSubmitExam) {
                    onResultDialog();
                } else {
                    UserManager.getInstance().getQuestionList(mContext, agentId);
                }
            }
        }
        progressdialog.dismiss();
    }

    public void getQuestionList() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().getQuestionList(this, agentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemClicked(int position) {

        titleName.setText(MessageFormat.format("Question {0} Of {1}",
                questionList.get(position).getId(), questionList.size()));
        txtQuestion.setText(MessageFormat.format("Question {0} : {1}",
                questionList.get(position).getId(), questionList.get(position).getQuestion()));

        rbAns1.setText(questionList.get(position).getOption1());
        rbAns2.setText(questionList.get(position).getOption2());
        rbAns3.setText(questionList.get(position).getOption3());
        rbAns4.setText(questionList.get(position).getOption4());

        index = position;

        nextNumber = position + 1;
        preNumber = position - 1;

        if (nextNumber == questionList.size())
            findViewById(R.id.btnSaveNext).setVisibility(View.GONE);
        else findViewById(R.id.btnSaveNext).setVisibility(View.VISIBLE);

        if (preNumber <= 0)
            findViewById(R.id.btnPrevious).setVisibility(View.GONE);
        else findViewById(R.id.btnPrevious).setVisibility(View.VISIBLE);

        int selectedOption = questionList.get(position).getOptionNo();

        if (selectedOption == 1) {
            rgQuestion.check(rgQuestion.getChildAt(0).getId());
        } else if (selectedOption == 2) {
            rgQuestion.check(rgQuestion.getChildAt(1).getId());
        } else if (selectedOption == 3) {
            rgQuestion.check(rgQuestion.getChildAt(2).getId());
        } else if (selectedOption == 4) {
            rgQuestion.check(rgQuestion.getChildAt(3).getId());
        } else {
            rgQuestion.clearCheck();
        }
    }

    public void onNextQueClick(View view) {

        titleName.setText(MessageFormat.format("Question {0} Of {1}",
                questionList.get(nextNumber).getId(), questionList.size()));

        txtQuestion.setText(MessageFormat.format("Question {0} : {1}",
                questionList.get(nextNumber).getId(), questionList.get(nextNumber).getQuestion()));

        rbAns1.setText(questionList.get(nextNumber).getOption1());
        rbAns2.setText(questionList.get(nextNumber).getOption2());
        rbAns3.setText(questionList.get(nextNumber).getOption3());
        rbAns4.setText(questionList.get(nextNumber).getOption4());

        int selectedOption = questionList.get(nextNumber).getOptionNo();

        if (selectedOption == 1) {
            rgQuestion.check(rgQuestion.getChildAt(0).getId());
        } else if (selectedOption == 2) {
            rgQuestion.check(rgQuestion.getChildAt(1).getId());
        } else if (selectedOption == 3) {
            rgQuestion.check(rgQuestion.getChildAt(2).getId());
        } else if (selectedOption == 4) {
            rgQuestion.check(rgQuestion.getChildAt(3).getId());
        } else {
            rgQuestion.clearCheck();
        }
//        int id = (rgQuestion.getCheckedRadioButtonId());
//        RadioButton rb = findViewById(rgQuestion.getCheckedRadioButtonId());
//        String gh = rb.getText().toString();
//        Toast.makeText(mContext, gh, Toast.LENGTH_SHORT).show();

        index = nextNumber;
        preNumber = nextNumber - 1;
        ++nextNumber;

        if (nextNumber == questionList.size())
            findViewById(R.id.btnSaveNext).setVisibility(View.GONE);
        else findViewById(R.id.btnSaveNext).setVisibility(View.VISIBLE);

        findViewById(R.id.btnPrevious).setVisibility(View.VISIBLE);

    }

    public void onPreQueClick(View view) {

        titleName.setText(MessageFormat.format("Question {0} Of {1}",
                questionList.get(preNumber).getId(), questionList.size()));
        txtQuestion.setText(MessageFormat.format("Question {0} : {1}",
                questionList.get(preNumber).getId(), questionList.get(preNumber).getQuestion()));

// titleName.setText("Question " + questionList.get(questionNumber).getId() + " Of "
//                    + questionList.size());
//            txtQuestion.setText("Question " + questionList.get(cPosition).getId() + " : "
//                    + questionList.get(cPosition).getQuestion());

        rbAns1.setText(questionList.get(preNumber).getOption1());
        rbAns2.setText(questionList.get(preNumber).getOption2());
        rbAns3.setText(questionList.get(preNumber).getOption3());
        rbAns4.setText(questionList.get(preNumber).getOption4());

        int selectedOption = questionList.get(preNumber).getOptionNo();

        if (selectedOption == 1) {
            rgQuestion.check(rgQuestion.getChildAt(0).getId());
        } else if (selectedOption == 2) {
            rgQuestion.check(rgQuestion.getChildAt(1).getId());
        } else if (selectedOption == 3) {
            rgQuestion.check(rgQuestion.getChildAt(2).getId());
        } else if (selectedOption == 4) {
            rgQuestion.check(rgQuestion.getChildAt(3).getId());
        } else {
            rgQuestion.clearCheck();
        }
        index = preNumber;
        nextNumber = preNumber + 1;
        --preNumber;

        if (preNumber < 0)
            findViewById(R.id.btnPrevious).setVisibility(View.GONE);
        else findViewById(R.id.btnPrevious).setVisibility(View.VISIBLE);
        findViewById(R.id.btnSaveNext).setVisibility(View.VISIBLE);

    }

    public void submitQuestion(String queId, String answer) {
        if (AppUtils.isOnline(mContext)) {
            try {
                UserManager.getInstance().submitQuestion(mContext, agentId, queId, answer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    public void submitExam() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().submitExam(mContext, agentId, result, marks, "" + savedMills);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }

    private void trainingCounter() {

        examTimer = new Hourglass(2700000, 1000) {
            @Override
            public void onTimerTick(long millisUntilFinished) {
                @SuppressLint("DefaultLocale") String hms = String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                                        .toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                                        .toMinutes(millisUntilFinished)));
                txtTime.setText(hms + " / " + formatMilliSecondsToTime(2700000));

                savedMills = ((long) 2700000 - millisUntilFinished) / 1000;
            }

            @Override
            public void onTimerFinish() {
                isSubmitExam = true;
                submitExam();
            }
        };

        examTimer.startTimer();
    }

    @Override
    public void onBackPressed() {

        onBackDialog();

    }

    private void onBackDialog() {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle((R.string.app_name));
        alertDialogBuilder.setIcon(R.drawable.logo_app);
        alertDialogBuilder.setMessage("Do you want to Finish your Exam!");
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton(R.string.str_yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                examTimer.pauseTimer();
                                examTimer.onTimerFinish();
                                isSubmitExam = true;
                                submitExam();


                            }
                        })
                .setNegativeButton(R.string.no, null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void onResultDialog() {

        String yesString, noString, extra;
        if (result.equalsIgnoreCase("pass")) {
            yesString = "Home";
            noString = "cancel";
            extra = "\nYou will get confirmation mail for Certificate!!";
        } else {
            yesString = "Re-Attend Exam";
            noString = "no";
            extra = "";
        }

        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle("PoSP Examination Result");
        alertDialogBuilder.setIcon(R.drawable.logo_app);
        alertDialogBuilder.setMessage("Result is: " + result.toUpperCase() + extra);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(yesString,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (result.equalsIgnoreCase("pass")) {
                                    startActivity(new Intent(mContext, DashboardActivity.class));
                                    finishAffinity();
                                } else {
                                    finish();
                                }

                            }
                        })
                .setNegativeButton(noString, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(mContext, DashboardActivity.class));
                        finishAffinity();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private String formatMilliSecondsToTime(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : "
                + twoDigitString(seconds);
    }

    private String twoDigitString(long number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (examTimer != null) {
            examTimer.resumeTimer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (examTimer != null) {
            examTimer.pauseTimer();
            examTimer.onTimerFinish();
        }
    }

    @Override
    public void getTotalNumber(int total) {
        marks = "" + total;

        if (total >= 36) {
            result = "pass";
        } else result = "fail";
    }

    public void onFinishExam(View view) {
        isSubmitExam = true;
        submitExam();
    }
}