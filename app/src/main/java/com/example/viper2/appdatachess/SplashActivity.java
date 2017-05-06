package com.example.viper2.appdatachess;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private static  final long SPLASH_DELAY = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        TimerTask task= new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent().setClass(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                SplashActivity.this.finish();

                Toast.makeText(getApplicationContext(), "Aqui va el intent", Toast.LENGTH_SHORT).show();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_DELAY);
    }

}
