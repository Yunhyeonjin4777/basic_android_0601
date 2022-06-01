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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class DetailViewActivity2 extends AppCompatActivity {

    TextView tv_title;
    TextView tv_profile_content;

    //전화하기
    Button but_call1;

    TextView tv_pay;
    TextView tv_cropinfo;
    TextView tv_person;
    TextView tv_datestart;
    TextView tv_dateend;
    TextView tv_content;
    TextView tv_pay2;

    //수정하기
    Button but_rewrite;

    //photo
    ImageView img_cloth;

    //preferences 객체 구현
    SharedPreferences setting;
    SharedPreferences.Editor editor;

    //URI를 저장할 변수 선언
    private Uri uri;

    //Launcher 셋팅
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailview);

        tv_title = findViewById(R.id.tv_title);
        tv_profile_content = findViewById(R.id.tv_profile_content);
        tv_pay = findViewById(R.id.tv_pay);
        tv_cropinfo = findViewById(R.id.tv_cropinfo);
        tv_person = findViewById(R.id.tv_person);
        tv_datestart = findViewById(R.id.tv_datestart);
        tv_dateend = findViewById(R.id.tv_dateend);
        tv_content = findViewById(R.id.tv_content);
        tv_pay2 = findViewById(R.id.tv_pay2);
        //수정하기버튼
        but_rewrite = findViewById(R.id.but_rewrite);
        //photo
        img_cloth = findViewById(R.id.img_cloth);

        Intent intent = getIntent();
        String get_time = intent.getStringExtra("time");
        String get_title = intent.getStringExtra("title");
        String get_cropinfo = intent.getStringExtra("cropinfo");
        String get_datestart = intent.getStringExtra("datestart");
        String get_dateend = intent.getStringExtra("dateend");
        String get_pay = intent.getStringExtra("pay");
        String get_person = intent.getStringExtra("person");
        String get_content = intent.getStringExtra("content");
        //photo
        uri = intent.getParcelableExtra("uri");
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
        but_call1 = findViewById(R.id.but_call1);

        but_call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-1234-5678"));
                startActivity(i);
            }
        });


        but_rewrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailViewActivity2.this, REJobWriteActivity.class);
                intent.putExtra("title", get_title);
                intent.putExtra("datestart", get_datestart);
                intent.putExtra("dateend", get_dateend);
                intent.putExtra("cropinfo", get_cropinfo);
                intent.putExtra("pay", get_pay);
                intent.putExtra("person", get_person);
                intent.putExtra("content", get_content);
                //photo
                intent.putExtra("uri", uri);
                intent.putExtra("position", get_position);

                Log.d("!!!intent", "title : " + get_title + get_datestart);
                activityResultLauncher.launch(intent);
                Toast.makeText(view.getContext(),"작성창으로 이동합니다", Toast.LENGTH_SHORT).show();
            }
        });


        //jobwrite에서 intent 수신결과
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                , new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK)
                        {
                            if(result.getData() != null)
                            {

                                Bundle bundle = result.getData().getExtras();
                                String re_title = (String) bundle.get("retitle");
                                String re_cropinfo = (String) bundle.get("recropinfo");
                                String re_datestart = (String) bundle.get("redatestart");
                                String re_dateend = (String) bundle.get("redateend");
                                String re_pay = (String) bundle.get("pay");
                                String re_person = (String) bundle.get("person");
                                String re_content = (String) bundle.get("content");

                                Log.d("!!!RE->Detail intent", "re_title : " + re_title + re_cropinfo);

                                tv_title.setText(re_title);
                                tv_cropinfo.setText(re_cropinfo);
                                tv_datestart.setText(re_datestart);
                                tv_dateend.setText(re_dateend);
                                tv_pay.setText(re_pay);
                                tv_person.setText(re_person);
                                tv_content.setText(re_content);

                                Toast.makeText(DetailViewActivity2.this, "작성 되었습니다", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(DetailViewActivity2.this, "저장되지 않았습니다", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });




//        //preferences 셋팅
//        setting = DetailViewActivity.this.getSharedPreferences("setting", MODE_PRIVATE);
//        editor= setting.edit();
//
//        String jobwriteinfo = setting.getString("jobwriteinfo", "");
//
//        try {
//            JSONObject jsonObject = new JSONObject(jobwriteinfo);
//            Log.d("!!!JSONObject", "jobwriteinfo : " + jobwriteinfo);
//
//            //데이터 불러와서 string으로 변환
//            String get_title = jsonObject.getString("title");
//            String get_datestart = jsonObject.getString("datestart");
//            String get_dateend = jsonObject.getString("dateend");
//            String get_pay = jsonObject.getString("pay");
//            String get_cropinfo = jsonObject.getString("cropinfo");
//            String get_person = jsonObject.getString("person");
//            String get_content = jsonObject.getString("content");
//
//            //레이아웃에 불러온 데이터 넣기
//            tv_title.setText(get_title);
//            tv_datestart.setText(get_datestart);
//            tv_dateend.setText(get_dateend);
//            tv_pay.setText(get_pay);
//            tv_cropinfo.setText(get_cropinfo);
//            tv_person.setText(get_person);
//            tv_content.setText(get_content);
//
//            tv_pay2.setText(get_pay);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }
}
