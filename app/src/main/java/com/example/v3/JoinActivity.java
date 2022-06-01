package com.example.v3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;

public class JoinActivity extends AppCompatActivity {

    EditText join_email;
    EditText join_passwd;
    EditText join_passwdcheck;
    EditText join_name;
    EditText join_content;
    EditText join_num;

    Button but_join;

    static ArrayList<Data_join> data_join = new ArrayList<>();

    //preferences 객체 구현
    SharedPreferences setting;
    SharedPreferences.Editor editor;

    //Gson 객체
    Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        join_email = findViewById(R.id.join_email);
        join_passwd = findViewById(R.id.join_passwd);
        join_passwdcheck = findViewById(R.id.join_passwdcheck);
        join_name = findViewById(R.id.join_name);
        join_content = findViewById(R.id.join_content);
        join_num = findViewById(R.id.join_num);

        but_join = findViewById(R.id.but_join);

        //preferences 셋팅
        setting = getSharedPreferences("setting", MODE_PRIVATE);
        editor = setting.edit();


        //뒤로가기 버튼 -> login 창으로
//        but_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

        //가입하기 버튼
        but_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //email에 입력받은 없을때
                if (join_email.getText().toString().equals("")) {
                    Toast.makeText(getApplication(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

                //passwd에 입력받은 값이 없을때
                if (join_passwd.getText().toString().equals("")) {
                    Toast.makeText(getApplication(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

                //입력한 비번과 비번확인이 맞지 않을 때
//                String a = join_passwd.getText().toString();
//                String b = join_passwdcheck.getText().toString();
//                if (a != b) {
//                    Toast.makeText(getApplication(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
//                }

                //name에 입력받은 값이 없을때
                if (join_name.getText().toString().equals("")) {
                    Toast.makeText(getApplication(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

                //join_content 입력받은 값이 없을때
                if (join_content.getText().toString().equals("")) {
                    Toast.makeText(getApplication(), "소개글을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

                //num에 입력받은 값이 없을때
                if (join_num.getText().toString().equals("")) {
                    Toast.makeText(getApplication(), "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

                String email = join_email.getText().toString();
                String passwd = join_passwd.getText().toString();
                String passwdcheck = join_passwdcheck.getText().toString();
                String name = join_name.getText().toString();
                String content = join_content.getText().toString();
                String num = join_num.getText().toString();

                Data_join joininfo = new Data_join(email, passwd, name, content, num);
                data_join.add(joininfo);
                Log.d("!!!joininfo", "joininfo : " + joininfo);
                Log.d("!!!data_join", "data_join : " + data_join);

                String joinDataSet = gson.toJson(data_join);
                Log.d("!!!joinDataSet", "joinDataSet : " + joinDataSet);

                //setting 쉐어드에 저장
                editor.putString("joinDataSet", joinDataSet);
                editor.commit();

//                Fragment_mylist.mylistDataset.clear();

                Intent intent = new Intent(JoinActivity.this, LoginJobOffererActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }//onCreate

}//JoinActivity
