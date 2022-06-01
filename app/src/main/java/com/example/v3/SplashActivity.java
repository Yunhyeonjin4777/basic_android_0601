package com.example.v3;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        BackgroundThread thread = new BackgroundThread();
        thread.start();

    }

    class BackgroundThread extends Thread
    {
        public void run(){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, ChoiceActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

}
