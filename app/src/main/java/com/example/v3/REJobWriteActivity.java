package com.example.v3;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class REJobWriteActivity extends AppCompatActivity{

    EditText edit_title;
    EditText edit_datestart;
    EditText edit_dateend;
    EditText edit_pay;
    EditText edit_cropinfo;
    EditText edit_person;
    EditText edit_content;
    //photo
    ImageView img_cloth;

    Button save_btn;

    //preferences 객체 구현
    SharedPreferences setting;
    SharedPreferences.Editor editor;

    //Gson 객체
    Gson gson = new Gson();

    //URI를 저장할 변수 선언
    private Uri uri;

    ArrayList<Data_mylist> mylistDataset = new ArrayList<>();
//    ArrayList<Data_mylist> alljobDataset = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobwrite);
        Log.e("00 | " + "REWRITE", "onCreate");

        //findview
        edit_title = (EditText) findViewById(R.id.edit_title);
        edit_datestart = (EditText) findViewById(R.id.edit_datestart);
        edit_dateend = (EditText) findViewById(R.id.edit_dateend);
        edit_pay = (EditText) findViewById(R.id.edit_pay);
        edit_cropinfo = (EditText) findViewById(R.id.edit_cropinfo);
        edit_person = (EditText) findViewById(R.id.edit_person);
        edit_content = (EditText) findViewById(R.id.edit_content);
        img_cloth = findViewById(R.id.img_cloth);
        save_btn = (Button) findViewById(R.id.save_btn);

        //intent값 가져오기
        Intent intent = getIntent();
        String get_title = intent.getStringExtra("title");
        String get_cropinfo = intent.getStringExtra("cropinfo");
        String get_datestart = intent.getStringExtra("datestart");
        String get_dateend = intent.getStringExtra("dateend");
        String get_pay = intent.getStringExtra("pay");
        String get_person = intent.getStringExtra("person");
        String get_content = intent.getStringExtra("content");
        uri = intent.getParcelableExtra("uri");
        int get_position = intent.getIntExtra("position", 0);

        Log.d("00 | " + "REWRITE", get_title + " | " + get_cropinfo + " | " + get_datestart + " | " + get_dateend + " | " + get_pay + " | " + get_content + " | " + uri + " | " + get_position);


        //데이터 레이아웃이랑 연결
        edit_title.setText(get_title);
        edit_cropinfo.setText(get_cropinfo);
        edit_datestart.setText(get_datestart);
        edit_dateend.setText(get_dateend);
        edit_pay.setText(get_pay);
        edit_person.setText(get_person);
        edit_content.setText(get_content);
        img_cloth.setImageURI(uri);

        //preferences 셋팅
        setting = getSharedPreferences("setting", MODE_PRIVATE);
        editor= setting.edit();

//        //back 클릭이벤트
//        back_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

        //save 클릭이벤트
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //작성한 editText을 스트링 값으로 저장
                String title = edit_title.getText().toString();
                String datestart = edit_datestart.getText().toString();
                String dateend = edit_dateend.getText().toString();
                String pay = edit_pay.getText().toString();
                String cropinfo = edit_cropinfo.getText().toString();
                String person = edit_person.getText().toString();
                String content = edit_content.getText().toString();

                //fragment_mylist로 인텐트 전달
                Intent intent = getIntent();
                intent.putExtra("retitle", title);
                intent.putExtra("recropinfo", cropinfo);
                intent.putExtra("redatestart", datestart);
                intent.putExtra("redateend", dateend);
                intent.putExtra("pay", pay);
                intent.putExtra("person", person);
                intent.putExtra("content", content);

                //쉐어드에서 gson데이터 받아와서 mylistDataset 에 전달
                String loadlist = setting.getString(LoginJobOffererActivity.myID, "");
                Type type = new TypeToken<ArrayList<Data_mylist>>() {}.getType();
                mylistDataset = gson.fromJson(loadlist,type);
                Log.d("00 | " + "REWRITE", "mylistDataset : " + mylistDataset);

//                Fragment_mylist.adapter_mylist.setAdapter(mylistDataset);
//                Log.d("!!!setAdapter","Fragment_mylist.mylistDataset  : " + mylistDataset );
//                Fragment_mylist.adapter_mylist.notifyDataSetChanged();

                //수정한 mylistDataset
                mylistDataset.get(get_position).setTitle(title);
                mylistDataset.get(get_position).setDatestart(datestart);
                mylistDataset.get(get_position).setDateend(dateend);
                mylistDataset.get(get_position).setPay(pay);
                mylistDataset.get(get_position).setCropinfo(cropinfo);
                mylistDataset.get(get_position).setPerson(person);
                mylistDataset.get(get_position).setContent(content);

                String jobDataSet = gson.toJson(mylistDataset);

                //쉐어드에 저장
                editor.putString(LoginJobOffererActivity.myID, jobDataSet);
                editor.commit();


//                for(int i = 0; i < alljobDataset.size(); i++)
//                {
//                    if(alljobDataset.get(i).getTitle().equals(get_title))
//                    {
//                        Log.d("!!!alljobDataSet 인덱스값", "i : " + i);
//                        Log.d("!!!alljobDataSet 수정", "alljobDataset.get(i).getTitle() : " + alljobDataset.get(i).getTitle());
//                        alljobDataset.get(i).setTitle(title);
//                        alljobDataset.get(i).setDatestart(datestart);
//                        alljobDataset.get(i).setDateend(dateend);
//                        alljobDataset.get(i).setPay(pay);
//                        alljobDataset.get(i).setCropinfo(cropinfo);
//                        alljobDataset.get(i).setPerson(person);
//                        alljobDataset.get(i).setContent(content);
//
//                        String alljobDataSet = gson.toJson(alljobDataset);
//
//                        //쉐어드에 저장
//                        editor.putString("alljobDataSet", alljobDataSet);
//                        editor.commit();
//
//                    }
//                }

                Toast.makeText(getApplication(), "수정하였습니다", Toast.LENGTH_SHORT).show();

                setResult(RESULT_OK, intent);
                finish();

            }
        });




        //save 클릭이벤트 & 저장까지
//        save_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //작성한 editText을 스트링 값으로 저장
//                String title = edit_title.getText().toString();
//                String datestart = edit_datestart.getText().toString();
//                String dateend = edit_dateend.getText().toString();
//                String pay = edit_pay.getText().toString();
//                String cropinfo = edit_cropinfo.getText().toString();
//                String person = edit_person.getText().toString();
//                String content = edit_content.getText().toString();
//
//                //preferences에 저장하기
//                JSONObject obj = new JSONObject();
//                try {
//                    obj.put("title", title);
//                    obj.put("datestart", datestart);
//                    obj.put("dateend", dateend);
//                    obj.put("pay", pay);
//                    obj.put("cropinfo", cropinfo);
//                    obj.put("person", person);
//                    obj.put("content", content);
//
//                    String jobwriteinfo = obj.toString();
//                    Log.d("!!!jobwriteinfo", "jobwriteinfo : " + jobwriteinfo);
//                    editor.putString("jobwriteinfo", jobwriteinfo);
//                    editor.commit();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                //fragment_mylist로 인텐트 전달
//                Intent intent = getIntent();
//                intent.putExtra("retitle", title);
//                intent.putExtra("recropinfo", cropinfo);
//                intent.putExtra("redatestart", datestart);
//                intent.putExtra("redateend", dateend);
//                setResult(RESULT_OK, intent);
//                finish();
//
//            }
//        });


    }

}
