package com.colinares.tictactoe.Activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.colinares.tictactoe.R;
import com.colinares.tictactoe.Utils.ThemeUtils;

public class SplashScreen extends AppCompatActivity {

    private ProgressBar progressBar;
    private ImageView mImage;

    private int progressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ThemeUtils themeUtils = new ThemeUtils(this);

        if (themeUtils.loadNightMode()){
            setTheme(R.style.DarkTheme);
        }else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mImage = findViewById(R.id.splash_image);
        progressBar = findViewById(R.id.progressBar);

        Animation goRotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);

        mImage.startAnimation(goRotate);

        final Thread loading = new Thread() {
            @Override
            public void run() {
                while (progressStatus < 200) {
                    progressStatus += 20;
                    try {
                        Thread.sleep(500);
                        progressBar.setProgress(progressStatus);
                        if (progressStatus == 200) {
                            startActivity(new Intent(SplashScreen.this, MainActivity.class));
                            finish();

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        loading.start();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
