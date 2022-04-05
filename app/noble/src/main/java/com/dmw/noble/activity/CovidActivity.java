package com.dmw.noble.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.dmw.noble.R;
import com.dmw.noble.utils.AppUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class CovidActivity extends AbstractActivity {

    Context mContext;
    Bundle mBundle;
    CardView cardView;
    String name, phone, email, url, title, posId;
    ImageView img;
    TextView txtEmail, txtPhone, txtName,txtDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid);
        Objects.requireNonNull(getSupportActionBar()).hide();

        mContext = this;
        mBundle = getIntent().getExtras();

        preferences = mContext.getSharedPreferences(String.valueOf(R.string.app_name),
                MODE_PRIVATE);
        name = preferences.getString(AppUtils.NAME, "");
        phone = preferences.getString(AppUtils.MOBILE, "");
        email = preferences.getString(AppUtils.EMAIL, "");
        posId = preferences.getString(AppUtils.AGENT_ID, "");

        cardView = findViewById(R.id.cvShare);
        img = findViewById(R.id.img);
        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtDes = findViewById(R.id.txtDes);

        if (!TextUtils.isEmpty(name)) {
            findViewById(R.id.rlPersonal).setVisibility(View.VISIBLE);
            txtName.setText(name.toUpperCase());
            txtPhone.setText(phone);
            txtDes.setText(posId);
            txtEmail.setText(email);

            if (TextUtils.isEmpty(posId))
                txtDes.setVisibility(View.GONE);

        }else {
            AppUtils.LEAD_URL = mContext.getResources().getString(R.string.base_url);
        }
        Shimmer shimmer = new Shimmer.AlphaHighlightBuilder()
                .setDuration(1300)
                .setBaseAlpha(0.8f)
                .setHighlightAlpha(0.9f)
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build();

        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
        shimmerDrawable.setShimmer(shimmer);

        if (mBundle != null) {
            url = mBundle.getString(AppUtils.BLOG_URL);
            title = mBundle.getString(AppUtils.TITLE);
            if (!TextUtils.isEmpty(url)) {
                Picasso.with(mContext).load(url)
                        .placeholder(shimmerDrawable)
                        .fit()
                        .error(R.drawable.placeholder)
                        .into(img);
            }
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

    public void onClickData(View view) {
        View window = cardView;
        if (window != null) {
            takeScreenShot(window);
        }
    }

    private void takeScreenShot(View view) {
        Date date = new Date();
        CharSequence format = DateFormat.format("MM-dd-yyyy_hh:mm:ss", date);

        try {
            File mainDir = new File(
                    this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "FilShare");
            if (!mainDir.exists()) {
                boolean mkdir = mainDir.mkdir();
            }
            String path = mainDir + "/" + "Square" + "-" + format + ".jpeg";
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File imageFile = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            shareScreenShot(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shareScreenShot(File imageFile) {
        Uri uri = FileProvider.getUriForFile(this, AppUtils.AUTHORITY, imageFile);

        String shareBody = String.format("%s", title + "\nFor more details visit "
                + AppUtils.LEAD_URL);
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

}