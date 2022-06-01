package com.example.v3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class ApplyViewActivity2 extends AppCompatActivity {

    TextView tv_title;
    TextView tv_profile_content;

    //전화하기
    Button but_call;

    TextView tv_pay;
    TextView tv_place;
    TextView tv_cropinfo;
    TextView tv_person;
    TextView tv_datestart;
    TextView tv_dateend;
    TextView tv_content;
    TextView tv_pay2;

    //수정하기
    Button but_apply;
    //photo
    ImageView img_cloth;

    //preferences 선언
    SharedPreferences jobseeDataset;
    SharedPreferences.Editor editor;

    //Launcher 셋팅
    ActivityResultLauncher<Intent> activityResultLauncher;

    //Gson 객체
    Gson gson = new Gson();

    //URI를 저장할 변수 선언
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applyview);

        //preferences 객체
        jobseeDataset = getSharedPreferences("jobseeDataset", MODE_PRIVATE);
        editor= jobseeDataset.edit();

        tv_title = findViewById(R.id.tv_title);
        tv_profile_content = findViewById(R.id.tv_profile_content);

        tv_pay = findViewById(R.id.tv_pay);
        tv_place = findViewById(R.id.tv_place);
        tv_cropinfo = findViewById(R.id.tv_cropinfo);
        tv_person = findViewById(R.id.tv_person);
        tv_datestart = findViewById(R.id.tv_datestart);
        tv_dateend = findViewById(R.id.tv_dateend);
        tv_content = findViewById(R.id.tv_content);
        tv_pay2 = findViewById(R.id.tv_pay2);
        //수정하기버튼
        but_apply = findViewById(R.id.but_apply);
        //photo
        img_cloth = findViewById(R.id.img_cloth);

        Intent intent = getIntent();
        String get_ID = intent.getStringExtra("ID");
        String get_title = intent.getStringExtra("title");
        String get_cropinfo = intent.getStringExtra("cropinfo");
        String get_datestart = intent.getStringExtra("datestart");
        String get_dateend = intent.getStringExtra("dateend");
        String get_pay = intent.getStringExtra("pay");
        String get_person = intent.getStringExtra("person");
        String get_content = intent.getStringExtra("content");
        //photo
        uri = intent.getParcelableExtra("uri");
        //num
        String get_num = intent.getStringExtra("num");
        //난수
        String get_randomNum = intent.getStringExtra("randomNum");
        Log.d("TAG","get_randomNum : " + get_randomNum);

        int get_position = intent.getIntExtra("position", 0);

        //레이아웃에 불러온 데이터 넣기
        tv_title.setText(get_title);
        tv_datestart.setText(get_datestart);
        tv_dateend.setText(get_dateend);
        tv_pay.setText(get_pay);
        tv_cropinfo.setText(get_cropinfo);
        tv_person.setText(get_person);
        tv_content.setText(get_content);
        //photo
        img_cloth.setImageURI(uri);

        tv_pay2.setText(get_pay);

        //call구현
        but_call = findViewById(R.id.but_call);

        but_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + get_num));
                startActivity(i);
            }
        });


        but_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //쉐어드에서 gson데이터 받아와서 mylistDataset 에 전달
                String joinload = jobseeDataset.getString("joinDataSet", "");

//                Type type = new TypeToken<ArrayList<Data_join>>() {}.getType();
//                ArrayList<Data_join> joinloadstr = gson.fromJson(joinload,type);
                editor.putString(get_ID, joinload);
                editor.commit();

                Toast.makeText(getApplication(), "지원을 완료하였습니다.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ApplyViewActivity2.this, JobSeekerActivity.class);
                startActivity(intent);
            }
        });



    }
}
