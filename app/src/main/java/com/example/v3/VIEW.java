package com.example.v3;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.Timer;
import java.util.TimerTask;

public class VIEW extends AppCompatActivity {

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 3000;
    ViewPager viewPager;
    Integer[] imageId = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4};
    String[] imagesName = {"image1","image2","image3","image4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_gallery);

        viewPager = (ViewPager) findViewById(R.id.pager_gallery);
        PagerAdapter adapter = new CustomAdapter(VIEW.this,imageId,imagesName);
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if(currentPage == 3)
                {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        },DELAY_MS, PERIOD_MS);
    }
}