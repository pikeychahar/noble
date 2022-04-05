package com.dmw.noble.activity.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.dmw.noble.R;
import com.dmw.noble.activity.BlogActivity;
import com.dmw.noble.activity.CorporateActivity;
import com.dmw.noble.activity.CovidActivity;
import com.dmw.noble.activity.MainActivity;
import com.dmw.noble.activity.PosAgreementActivity;
import com.dmw.noble.activity.health.CitySumActivity;
import com.dmw.noble.activity.health.HealthViewActivity;
import com.dmw.noble.activity.health.royal.ProposerRoyalActivity;
import com.dmw.noble.activity.travel.GeographicalActivity;
import com.dmw.noble.activity_docs.DocsAllActivity;
import com.dmw.noble.activity_motor.BikeRegistrationActivity;
import com.dmw.noble.activity_pos.LeadsActivity;
import com.dmw.noble.activity_pos.MyPoliciesActivity;
import com.dmw.noble.activity_pos.QuotationListActivity;
import com.dmw.noble.adaptor.BlogAdaptor;
import com.dmw.noble.adaptor.PosterAdaptor;
import com.dmw.noble.adaptor.SliderAdapter;
import com.dmw.noble.interfaces.OnImageClickListener;
import com.dmw.noble.manager.ApiManager;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.model.BasicResponse;
import com.dmw.noble.model.PosterData;
import com.dmw.noble.model.SendOtp;
import com.dmw.noble.model.SharePoster;
import com.dmw.noble.model.blog.Blog;
import com.dmw.noble.model.blog.BlogList;
import com.dmw.noble.model_pos.AgentDetail;
import com.dmw.noble.network.ApiClient;
import com.dmw.noble.network.ApiInterface;
import com.dmw.noble.training.TrainingActivity;
import com.dmw.noble.utils.AppUtils;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements BlogAdaptor.OnBlogClickListener,
        OnImageClickListener {

    private Context mContext;
    private Bundle mBundle;
    private SharedPreferences preferences;
    private RecyclerView recyclerBlog;
    RecyclerView.LayoutManager mLayoutManager;
    BlogAdaptor blogAdaptor;
    ImageView imgBadge;
    PosterAdaptor posterAdaptor;
    List<Blog> blogList = new ArrayList<>();
    List<PosterData> posterList = new ArrayList<>();

    RelativeLayout rlBlog;
    AgentDetail agentDetailObj;
    String agentId, agentCode, agentName, spId, badgeColor, userBadge;
    TextView txtAgentId, txtUserName, txtLogin;
    de.hdodenhof.circleimageview.CircleImageView posImg;
    String userType, posStatus, posAgreement;
    Button btnTraining;
    int passChangeDays = 0;
    private String validOtp, newPassword, confirmPassword, oldPassword, userId, mobile, otp,
            mobilePermission;

    RelativeLayout rlBike, rlCar, rlPcv, rlGcv, rlHealth, rlTravel, rlQuote, rlPolicy,
            rlDoc, rlLead, rlCorona, rlMissDi;

    TabLayout indicator;
    List<String> imageList = new ArrayList<>();
    SliderAdapter sliderAdapter;
    ViewPager viewPager;
    Timer timer;
    Activity mActivity;
    boolean isAccess = true;
    Dialog dialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        mContext = getActivity();
        mBundle = new Bundle();
        timer = new Timer();

        preferences = mContext.getSharedPreferences(String.valueOf(R.string.app_name),
                MODE_PRIVATE);
        agentId = preferences.getString(AppUtils.PRIMARY_ID, "");
        agentCode = preferences.getString(AppUtils.AGENT_ID, "");
        agentName = preferences.getString(AppUtils.NAME, "");

        spId = preferences.getString(AppUtils.SP_ID, "");
        posAgreement = preferences.getString(AppUtils.IS_AGREEMENT_POS, "");
        mobile = preferences.getString(AppUtils.MOBILE, "");
        posStatus = preferences.getString(AppUtils.POS_STATUS, "");
        oldPassword = preferences.getString(AppUtils.USER_PASS, "");

        badgeColor = preferences.getString(AppUtils.BADGE_COLOR, "#0000ffff");
        userBadge = preferences.getString(AppUtils.USER_BADGE, "");

        userType = preferences.getString(AppUtils.USER_TYPE, "");
        userId = preferences.getString(AppUtils.PRIMARY_ID, "");

        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!TextUtils.isEmpty(userId))
            ApiManager.getInstance().userAuthentication(mContext, userId, userType);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerBlog = root.findViewById(R.id.recyclerBlog);
        rlBlog = root.findViewById(R.id.rlBlog);
        txtAgentId = root.findViewById(R.id.txtAgentId);
        txtUserName = root.findViewById(R.id.txtUserName);
        posImg = root.findViewById(R.id.userImg);
        txtLogin = root.findViewById(R.id.txtLogin);
        imgBadge = root.findViewById(R.id.imgBadge);

        rlBike = root.findViewById(R.id.rlBike);
        rlCar = root.findViewById(R.id.rlCar);
        rlPcv = root.findViewById(R.id.rlPcv);
        rlCorona = root.findViewById(R.id.rlCorona);
        rlMissDi = root.findViewById(R.id.rlMissDi);
        rlGcv = root.findViewById(R.id.rlGcv);
        rlHealth = root.findViewById(R.id.rlHealth);
        rlTravel = root.findViewById(R.id.rlTravel);
        rlQuote = root.findViewById(R.id.rlQuote);
        rlPolicy = root.findViewById(R.id.rlPolicy);
        rlDoc = root.findViewById(R.id.rlDoc);
        rlLead = root.findViewById(R.id.rlLead);
        btnTraining = root.findViewById(R.id.btnTraining);

        viewPager = root.findViewById(R.id.viewPager);
        indicator = root.findViewById(R.id.indicator);

        if (imageList.size() > 0)
            imageList.clear();

        imageList.add("https://www.squareinsurance.in/assets/non_motor/app-banner1.png");
        imageList.add("https://www.squareinsurance.in/assets/non_motor/app-banner2.png");
        imageList.add("https://www.squareinsurance.in/assets/non_motor/app-banner3.png");
        imageList.add("https://www.squareinsurance.in/assets/non_motor/app-banner4.png");
        imageList.add("https://www.squareinsurance.in/assets/non_motor/app-banner5.png");
        imageList.add("https://www.squareinsurance.in/assets/non_motor/app-banner6.png");
        imageList.add("https://www.squareinsurance.in/assets/non_motor/app-banner7.png");
        imageList.add("https://www.squareinsurance.in/assets/non_motor/app-banner8.png");
        imageList.add("https://www.squareinsurance.in/assets/non_motor/app-banner9.png");
        imageList.add("https://www.squareinsurance.in/assets/non_motor/app-banner10.png");
        imageList.add("https://www.squareinsurance.in/assets/non_motor/app-banner11.png");

        if (imageList != null) {
            sliderAdapter = new SliderAdapter(getActivity(), imageList, this);
            viewPager.setAdapter(sliderAdapter);
            indicator.setupWithViewPager(viewPager, true);
            timer.schedule(new SliderTimer(), 2000, 5000);
        }

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerBlog.setLayoutManager(mLayoutManager);
        recyclerBlog.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.HORIZONTAL, false));

        agentDetailObj = UserManager.getAgentDetail();

        if (agentDetailObj != null) {
            String status = agentDetailObj.getPosStatus();
            if (!TextUtils.isEmpty(status) && !TextUtils.isEmpty(posStatus)) {
                if (!posStatus.equals(status)) {
                    preferences.edit().clear().apply();
                    AgentDetail agentDetail = new AgentDetail();
                    UserManager.getInstance().setAgentDetail(agentDetail);
                    preferences.edit().putBoolean(AppUtils.IS_FIRST_TIME,
                            false).apply();
                    startActivity(new Intent(mContext, MainActivity.class));
                    requireActivity().finish();
                }
            }
        } else getAgentDetail();

        if (!TextUtils.isEmpty(userBadge)) {
            View imgUserBadge = root.findViewById(R.id.imgUserBadge);
            imgUserBadge.setVisibility(View.VISIBLE);
            Drawable backgroundDrawable = DrawableCompat.wrap(imgUserBadge.getBackground().mutate());
            DrawableCompat.setTint(backgroundDrawable, Color.parseColor(badgeColor));
        }

        if (!TextUtils.isEmpty(userType))
            if (userType.equalsIgnoreCase("agent")) {
                if (posStatus.equals("4")) {
                    isAccess = false;
                    root.findViewById(R.id.rlTraining).setVisibility(View.VISIBLE);
                }
            }
        getPosters();

        if (!TextUtils.isEmpty(agentName))
            txtUserName.setText(agentName);
        else {
            txtLogin.setVisibility(View.VISIBLE);
            imgBadge.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(userType))
            if (userType.equalsIgnoreCase("agent")) {
                if (!TextUtils.isEmpty(agentCode))
                    txtAgentId.setText(agentCode.toUpperCase());
                else txtAgentId.setVisibility(View.GONE);
                String path = agentDetailObj.getImage();

                try {
                    if (!TextUtils.isEmpty(path)) {
                        Picasso.with(getContext()).load(path)
                                .fit()
                                .placeholder(R.drawable.ic_user)
                                .error(R.drawable.ic_user)
                                .into(posImg);
                    } else getAgentDetail();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        imgBadge.setOnClickListener(v -> {

        });

        rlBike.setOnClickListener(v -> {
            if (isAccess) {
                preferences.edit().putString(AppUtils.VEHICLE_TYPE, "1").apply();
                startActivity(new Intent(mContext, BikeRegistrationActivity.class));
            } else AppUtils.authDialog(mContext);
        });

        rlCar.setOnClickListener(v -> {
            if (isAccess) {
                preferences.edit().putString(AppUtils.VEHICLE_TYPE, "2").apply();
                startActivity(new Intent(mContext, BikeRegistrationActivity.class));
            } else AppUtils.authDialog(mContext);
        });

        rlPcv.setOnClickListener(v -> {
            if (isAccess) {
                preferences.edit().putString(AppUtils.VEHICLE_TYPE, "3").apply();
                startActivity(new Intent(mContext, BikeRegistrationActivity.class));
            } else AppUtils.authDialog(mContext);
        });

        rlCorona.setOnClickListener(v -> {
            if (isAccess) {
//                if (TextUtils.isEmpty(userId))
//                    startActivity(new Intent(mContext, BasicLifeActivity.class));
                    startActivity(new Intent(mContext, ProposerRoyalActivity.class));
//                else startActivity(new Intent(mContext, PreQuoteLifeActivity.class));
//                Intent intent = new Intent(mContext, ContactActivity.class);
//                intent.putExtra(AppUtils.QUERY_TYPE, "life");
//                startActivity(intent);
            } else AppUtils.authDialog(mContext);
        });

        rlMissDi.setOnClickListener(v -> {
            if (isAccess) {
                preferences.edit().putString(AppUtils.VEHICLE_TYPE, "5").apply();
                startActivity(new Intent(mContext, BikeRegistrationActivity.class));
            } else AppUtils.authDialog(mContext);
        });

        rlGcv.setOnClickListener(v -> {
            if (isAccess) {
                preferences.edit().putString(AppUtils.VEHICLE_TYPE, "4").apply();
                startActivity(new Intent(mContext, BikeRegistrationActivity.class));
            } else AppUtils.authDialog(mContext);
        });

        rlHealth.setOnClickListener(v -> {
            if (isAccess) {
                if (TextUtils.isEmpty(userId)) {
                    preferences.edit().putString(AppUtils.TRAVEL_HEALTH, "health").apply();
                    startActivity(new Intent(mContext, HealthViewActivity.class));
                }else startActivity(new Intent(mContext, CitySumActivity.class));
            } else AppUtils.authDialog(mContext);
        });

        rlTravel.setOnClickListener(v -> {
            if (isAccess) {
                if (TextUtils.isEmpty(userId)) {
                    preferences.edit().putString(AppUtils.TRAVEL_HEALTH, "travel").apply();
                    startActivity(new Intent(mContext, HealthViewActivity.class));
                } else startActivity(new Intent(mContext, GeographicalActivity.class));

            } else AppUtils.authDialog(mContext);
        });

        rlPolicy.setOnClickListener(v -> {
            if (isAccess) {
                startActivity(new Intent(mContext, MyPoliciesActivity.class));
            } else AppUtils.authDialog(mContext);
        });

        rlQuote.setOnClickListener(v -> {
            if (isAccess) {
                startActivity(new Intent(mContext, QuotationListActivity.class));
            } else AppUtils.authDialog(mContext);
        });

        rlLead.setOnClickListener(v -> {

            if (isAccess) {
                startActivity(new Intent(mContext, LeadsActivity.class));
            } else AppUtils.authDialog(mContext);
        });

        rlDoc.setOnClickListener(v -> {
            if (isAccess) {
                startActivity(new Intent(mContext, DocsAllActivity.class));
            } else AppUtils.authDialog(mContext);
        });

        txtLogin.setOnClickListener(v -> {
            startActivity(new Intent(mContext, MainActivity.class));
            requireActivity().finishAffinity();
        });

        btnTraining.setOnClickListener(v -> startActivity(new Intent(mContext,
                TrainingActivity.class)));

        if (!TextUtils.isEmpty(agentId))
            if (!TextUtils.isEmpty(posAgreement) && posAgreement.equalsIgnoreCase("0")) {
                startActivity(new Intent(mContext, PosAgreementActivity.class));
            }

        return root;
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    public void getAgentDetail() {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();
        try {
            Call<AgentDetail> call = apiService.getAgentDetail(agentId, userType);
            call.enqueue(new Callback<AgentDetail>() {
                @Override
                public void onResponse(@NonNull Call<AgentDetail> call,
                                       @NonNull Response<AgentDetail> response) {
                    if (response.isSuccessful()) {
                        AgentDetail commonResponse = response.body();
                        if (commonResponse != null) {
                            String path = commonResponse.getImage();
                            String name = commonResponse.getName();
                            mobilePermission = commonResponse.getCreateMobilePermission();
                            passChangeDays = commonResponse.getChangedPassDays();
                            txtUserName.setText(name);

                            if (!TextUtils.isEmpty(mobilePermission))
                                preferences.edit().putString(AppUtils.IS_FIRST_TIME,
                                        mobilePermission).apply();

                            String status = commonResponse.getPosStatus();
                            if (!TextUtils.isEmpty(status) && !TextUtils.isEmpty(posStatus)) {
                                if (!posStatus.equals(status)) {
                                    preferences.edit().clear().apply();
                                    AgentDetail agentDetail = new AgentDetail();
                                    UserManager.getInstance().setAgentDetail(agentDetail);
                                    preferences.edit().putBoolean(AppUtils.IS_FIRST_TIME,
                                            false).apply();
                                    startActivity(new Intent(mContext, MainActivity.class));
                                    requireActivity().finish();
                                }

                            }

                            UserManager.getInstance().setAgentDetail(commonResponse);

                            try {
                                if (!TextUtils.isEmpty(path)) {
                                    Picasso.with(getContext()).load(path)
                                            .fit()
                                            .placeholder(R.drawable.ic_user)
                                            .error(R.drawable.ic_user)
                                            .into(posImg);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if (passChangeDays > 90) {
                                showChangePassDialog();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<AgentDetail> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getOtp() {
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<SendOtp> call = apiService.changePassOtp(mobile);
            call.enqueue(new Callback<SendOtp>() {
                @Override
                public void onResponse(@NotNull Call<SendOtp> call,
                                       @NotNull Response<SendOtp> response) {
                    if (response.isSuccessful()) {
                        SendOtp vResponse = response.body();
                        assert vResponse != null;
                        if (vResponse.getStatus() == 1) {
                            otp = String.valueOf(vResponse.getOtp());
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<SendOtp> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showChangePassDialog() {

        if (!TextUtils.isEmpty(mobile)) {
            getOtp();
        }

        final Dialog dialog = new Dialog(mContext,
                android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Change Password");
        dialog.setCancelable(false);

        // set the custom dialog components - text, image and button
        final EditText oldPass = dialog.findViewById(R.id.edtOld);
        final EditText newPass = dialog.findViewById(R.id.edtNewPass);
        final EditText confirmPass = dialog.findViewById(R.id.edtConPass);

        Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
        Button close = dialog.findViewById(R.id.dialogButtonClose);
        close.setVisibility(View.GONE);
        oldPass.setVisibility(View.GONE);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile = preferences.getString(AppUtils.MOBILE, "");
                validOtp = oldPass.getText().toString();
                newPassword = newPass.getText().toString();
                confirmPassword = confirmPass.getText().toString();
                int pikay = 0;
                if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(mContext, "Some thing went wrong!!!",
                            Toast.LENGTH_SHORT).show();
                    pikay++;
                }
//                else if (TextUtils.isEmpty(validOtp)) {
//                    oldPass.setError("Empty");
//                    pikay++;
//                }
                else if (TextUtils.isEmpty(newPassword)) {
                    newPass.setError("Empty");
                    pikay++;
                } else if (!(confirmPassword.equals(newPassword))) {
                    oldPass.setError("Password must be same");
                    Toast.makeText(mContext, "Password must be same",
                            Toast.LENGTH_SHORT).show();
                    pikay++;
                } else if (newPassword.length() < 8) {
                    newPass.setError("Minimum 8 Characters");
                    newPass.requestFocus();
                    pikay++;
                }
                if (!TextUtils.isEmpty(otp))
                    validOtp = otp;

                if (pikay == 0) {
                    changePassword();
//                    if (flag)
                    dialog.dismiss();

                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void changePassword() {

        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<BasicResponse> call = apiService.changePassword(mobile, validOtp, newPassword,
                    oldPassword, userId, userType);
            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(@NotNull Call<BasicResponse> call,
                                       @NotNull Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        BasicResponse commonResponse = response.body();

                        assert commonResponse != null;
                        if (commonResponse.getStatus() == 1)
                            Toast.makeText(mContext, "Password has been updated",
                                    Toast.LENGTH_SHORT).show();
                        else {
                            showChangePassDialog();
                            Toast.makeText(mContext, "" + commonResponse.getMsg(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<BasicResponse> call, @NotNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBlogClicked(int position, String url) {
        if (!TextUtils.isEmpty(url)) {
            Intent intent = new Intent(mContext, BlogActivity.class);
            mBundle.putString(AppUtils.BLOG_URL, url);
            intent.putExtras(mBundle);
            startActivity(intent);
        }
    }

    private void takeScreenShot(View view) {
        Date date = new Date();
        CharSequence format = DateFormat.format("MM-dd-yyyy_hh:mm:ss", date);

        File mainDir = new File(
                requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "FilShare");
        if (!mainDir.exists()) {
            boolean mkdir = mainDir.mkdir();
        }
        String path = mainDir + "/" + "Square" + "-" + format + ".jpeg";
        view.setDrawingCacheEnabled(true);
        try {
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File imageFile = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            shareScreenShot(imageFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void shareScreenShot(File imageFile) {
        Uri uri = FileProvider.getUriForFile(requireActivity(), "com.dmw.noble.provider",
                imageFile);

        //Explicit intent
        String shareBody = String.format("%s", AppUtils.PRE_LINK);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_TEXT, shareBody);
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        try {
            this.startActivity(Intent.createChooser(intent, "Share With"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mContext, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }


    public void getBlogList() {

        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<BlogList> call = apiService.getBlogList();
            call.enqueue(new Callback<BlogList>() {
                @Override
                public void onResponse(@NonNull Call<BlogList> call,
                                       @NonNull Response<BlogList> response) {
                    if (response.isSuccessful()) {
                        BlogList commonResponse = response.body();

                        if (commonResponse != null && commonResponse.getStatus() == 1) {
                            if (blogList.size() > 0)
                                blogList.clear();
                            if (commonResponse.getBlogs() != null) {
                                rlBlog.setVisibility(View.VISIBLE);
                                blogList.addAll(commonResponse.getBlogs());

                                blogAdaptor = new BlogAdaptor(mContext, blogList,
                                        (position, url) -> {

                                            if (!TextUtils.isEmpty(url)) {
                                                Intent intent = new Intent(mContext,
                                                        BlogActivity.class);
                                                mBundle.putString(AppUtils.BLOG_URL, url);
                                                intent.putExtras(mBundle);
                                                startActivity(intent);
                                            }
                                        });
                                recyclerBlog.setAdapter(blogAdaptor);
                                blogAdaptor.notifyDataSetChanged();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<BlogList> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPosters() {

        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<SharePoster> call = apiService.getPosters("App");
            call.enqueue(new Callback<SharePoster>() {
                @Override
                public void onResponse(@NonNull Call<SharePoster> call,
                                       @NonNull Response<SharePoster> response) {
                    if (response.isSuccessful()) {
                        SharePoster commonResponse = response.body();

                        if (commonResponse != null && commonResponse.getStatus()) {
                            if (posterList.size() > 0)
                                posterList.clear();
                            if (commonResponse.getData() != null) {
                                rlBlog.setVisibility(View.VISIBLE);
                                posterList.addAll(commonResponse.getData());
                                posterAdaptor = new PosterAdaptor(mContext, posterList,
                                        (position, url, title) -> {
                                            try {
                                                if (!TextUtils.isEmpty(url)) {
                                                    Intent intent = new Intent(mContext,
                                                            CovidActivity.class);
                                                    mBundle.putString(AppUtils.BLOG_URL, url);
                                                    mBundle.putString(AppUtils.TITLE, title);
                                                    intent.putExtras(mBundle);
                                                    startActivity(intent);
                                                }
                                            } catch (Exception e) {
                                                AppUtils.showToast(mContext, "Something went wrong, Retry in some time...");
                                                e.printStackTrace();
                                            }

                                        });
                                recyclerBlog.setAdapter(posterAdaptor);
                                posterAdaptor.notifyDataSetChanged();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SharePoster> call, @NonNull Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onImageClick(int position) {
        startActivity(new Intent(mContext, CorporateActivity.class));
    }

    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            mActivity.runOnUiThread(() -> {
                if (imageList != null) {
                    if (viewPager.getCurrentItem() < imageList.size() - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}