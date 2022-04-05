package com.dmw.noble.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.dmw.noble.R;
import com.dmw.noble.utils.SampleSlide;

/**
 * Created by Prahalad Kumar Chahar
 */
public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(SampleSlide.newInstance(R.layout.intro));
        addSlide(SampleSlide.newInstance(R.layout.intro2));
        addSlide(SampleSlide.newInstance(R.layout.intro3));
        setBarColor(getResources().getColor(R.color.colorPrimary));
        setSeparatorColor(getResources().getColor(R.color.colorPrimaryDark));
        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);
        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permisssion in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        loadMainActivity();
    }

    private void loadMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);//GoogleDriveActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        loadMainActivity();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }
}
