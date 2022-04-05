package com.dmw.noble.training;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.ankushgrover.hourglass.Hourglass;
import com.dmw.noble.R;
import com.dmw.noble.activity.AbstractActivity;
import com.dmw.noble.interfaces.onRequestCompleteCallBackListener;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.model.BasicResponse;
import com.dmw.noble.training.model.Module;
import com.dmw.noble.training.model.Module1;
import com.dmw.noble.training.model.Module3;
import com.dmw.noble.training.model.Module4;
import com.dmw.noble.utils.AppUtils;

import org.xml.sax.XMLReader;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TrainingViewActivity extends AbstractActivity implements onRequestCompleteCallBackListener {

    private Module1 module1;
    private Module.Module2 module2;
    private Module3 module3;
    private Module4 module4;

    private TextView titleName, txtDes, txtTime;
    private Context mContext;
    private Bundle mBundle;
    private int moduleNumber, spendTime, status;
    private int timePeriod;
    private long savedMills;
    private Hourglass hourglass;

    private String agentId;
    private ProgressDialog progressdialog;
    private boolean isBacked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traning_view);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mContext = this;
        SharedPreferences preferences = getSharedPreferences(String.valueOf(R.string.app_name),
                MODE_PRIVATE);

        progressdialog = new ProgressDialog(mContext);
        progressdialog.setMessage("Please Wait....");
//        progressdialog.setCancelable(false);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        agentId = preferences.getString(AppUtils.PRIMARY_ID, "");
        mBundle = getIntent().getExtras();

        titleName = findViewById(R.id.titleName);
        txtDes = findViewById(R.id.txtDes);
        txtTime = findViewById(R.id.txtTime);

        if (mBundle != null) {

            moduleNumber = mBundle.getInt("moduleNumber");
            spendTime = mBundle.getInt("spendTime");

            if (moduleNumber == 1) {
                module1 = (Module1) mBundle.getSerializable("module");
                titleName.setText(module1.getTitle());


//                txtDes.setText(module1.getContent());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    txtDes.setText(Html.fromHtml(module1.getContent(), null, new MyTagHandler()));
                } else {
                    txtDes.setText(Html.fromHtml(module1.getContent(),null, new MyTagHandler()));
                }
                timePeriod = Integer.parseInt(module1.getTime());
                timePeriod = Integer.parseInt(module1.getTime());
                status = Integer.parseInt(module1.getStatus());
//                txtTime.setText((timePeriod/3600) + " : "   + ((timePeriod/60)%60) + " : " + (timePeriod%60));
                trainingCounter(timePeriod * 1000 - spendTime * 1000);

            } else if (moduleNumber == 2) {
                module2 = (Module.Module2) mBundle.getSerializable("module");
                titleName.setText(module2.getTitle());
//                txtDes.setText(module2.getContent());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    txtDes.setText(Html.fromHtml(module2.getContent(), null, new MyTagHandler()));
                } else {
                    txtDes.setText(Html.fromHtml(module2.getContent(),null, new MyTagHandler()));
                }
                timePeriod = Integer.parseInt(module2.getTime());
                status = Integer.parseInt(module2.getStatus());
//                txtTime.setText((timePeriod/3600) + " : "   + ((timePeriod/60)%60) + " : " + (timePeriod%60));
                trainingCounter(timePeriod * 1000 - spendTime * 1000);

            } else if (moduleNumber == 3) {
                module3 = (Module3) mBundle.getSerializable("module");
                titleName.setText(module3.getTitle());
//                txtDes.setText(module3.getContent());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    txtDes.setText(Html.fromHtml(module3.getContent(), null, new MyTagHandler()));
                } else {
                    txtDes.setText(Html.fromHtml(module3.getContent(),null, new MyTagHandler()));
                }
                timePeriod = Integer.parseInt(module3.getTime());
                status = Integer.parseInt(module3.getStatus());
//                txtTime.setText((timePeriod/3600) + " : "   + ((timePeriod/60)%60) + " : " + (timePeriod%60));
                trainingCounter(timePeriod * 1000 - spendTime * 1000);

            } else {
                module4 = (Module4) mBundle.getSerializable("module");
                titleName.setText(module4.getTitle());
//                txtDes.setText(module4.getContent());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    txtDes.setText(Html.fromHtml(module4.getContent(), null, new MyTagHandler()));
                } else {
                    txtDes.setText(Html.fromHtml(module4.getContent(),null, new MyTagHandler()));
                }
                timePeriod = Integer.parseInt(module4.getTime());
                status = Integer.parseInt(module4.getStatus());
//                txtTime.setText((timePeriod/3600) + " : "   + ((timePeriod/60)%60) + " : " + (timePeriod%60));
                trainingCounter(timePeriod * 1000 - spendTime * 1000);
            }

        }
    }

    private void trainingCounter(final long mills) {

        hourglass = new Hourglass(mills, 1000) {
            @Override
            public void onTimerTick(long millisUntilFinished) {
                @SuppressLint("DefaultLocale") String hms = String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                txtTime.setText(hms + " / " + formatMilliSecondsToTime(timePeriod * 1000));

                savedMills = (mills - millisUntilFinished) / 1000;
            }

            @Override
            public void onTimerFinish() {
                // Timer finished
                //todo
                //on back click
                if (status == 0)
                    submitModule();
//                Toast.makeText(mContext, "Done done", Toast.LENGTH_SHORT).show();
            }
        };

        hourglass.startTimer();

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
        if (hourglass != null) {
            hourglass.resumeTimer();
//            trainingCounter(timePeriod * 1000 - (spendTime * 1000 + savedMills * 1000));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (hourglass != null) {
            hourglass.pauseTimer();
            hourglass.onTimerFinish();
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
    public void onBackPressed() {
        if (status == 1)
            finish();
        else
            onBackDialog();

    }

    private void onBackDialog() {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle((R.string.app_name));
        alertDialogBuilder.setIcon(R.drawable.logo_app);
        alertDialogBuilder.setMessage("Do you want to pause your training session!");
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton(R.string.str_yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                hourglass.pauseTimer();
                                hourglass.onTimerFinish();
                                isBacked = true;

                            }
                        })
                .setNegativeButton(R.string.no, null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onResponse(Object response) {
        if (response instanceof BasicResponse) {
            BasicResponse basicResponse = (BasicResponse) response;
            if (basicResponse.getStatus() == 1) {
                //handle todo
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                if (isBacked)
                    finish();

                progressdialog.hide();
            }

        }
        progressdialog.hide();
    }

    public void submitModule() {
        if (AppUtils.isOnline(mContext)) {
            progressdialog.show();
            try {
                UserManager.getInstance().submitTraining(mContext, agentId,
                        "" + savedMills, "module" + moduleNumber);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mContext, "No Network", Toast.LENGTH_SHORT).show();
        }
    }


}

class MyTagHandler implements Html.TagHandler {

   boolean first = true;
   String parent = null;
   int index = 1;
   @Override
   public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {

       if (tag.equals("ul")) {
           parent = "ul";
       } else if (tag.equals("ol")) {
           parent = "ol";
       }

       if (tag.equals("li")) {
           if (parent.equals("ul")) {
               if (first) {
                   output.append("\n\t•");
                   first = false;
               } else {
                   first = true;
               }
           } else{
               if (first) {
                   output.append("\n\t"+index+". ");
                   first = false;
                   index++;
               } else {
                   first = true;
               }
           }
       }
   }
}