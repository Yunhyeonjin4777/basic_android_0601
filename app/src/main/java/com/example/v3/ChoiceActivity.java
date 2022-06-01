package com.example.v3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ChoiceActivity extends AppCompatActivity {

    Button but_jobSeeker;
    Button but_jobOfferer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        but_jobSeeker = (Button) findViewById(R.id.but_jobSeeker);
        but_jobOfferer = (Button) findViewById(R.id.but_jobOfferer);

        //구직자
        but_jobSeeker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChoiceActivity.this, LoginJobSeekerActivity.class);
                startActivity(intent);
            }
        });

        //구인자
        but_jobOfferer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChoiceActivity.this, LoginJobOffererActivity.class);
                startActivity(intent);
            }
        });


    }
}
