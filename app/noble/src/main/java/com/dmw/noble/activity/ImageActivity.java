package com.dmw.noble.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dmw.noble.R;

import java.util.Objects;

public class ImageActivity extends AbstractActivity {
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        mContext = this;

        ImageView imageFullView = findViewById(R.id.imageFullView);

        Intent intent = getIntent();
        String imgPath = intent.getStringExtra("img");

        if (!TextUtils.isEmpty((imgPath))) {
            Glide.with(mContext)
                    .load(imgPath)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(imageFullView);
        }
    }
}
