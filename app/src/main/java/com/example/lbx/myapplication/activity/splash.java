package com.example.lbx.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;

import com.example.lbx.myapplication.R;

/**
 * Created by lbx on 2017/6/29.
 */

public class splash extends Activity{
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        mCountDownTimer.start();

    }
    CountDownTimer mCountDownTimer=new CountDownTimer(2000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            Intent intent=new Intent(splash.this,MainActivity.class);
            startActivity(intent);

            splash.this.finish();

        }
    };
}
