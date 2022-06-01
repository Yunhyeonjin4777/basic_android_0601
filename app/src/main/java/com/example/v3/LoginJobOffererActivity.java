package com.example.v3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LoginJobOffererActivity extends AppCompatActivity implements View.OnClickListener{

    EditText et_email;
    EditText et_password;
    Button btn01;
    Button but_next;
    TextView but_join;

    //preferences 선언
    SharedPreferences setting;
    SharedPreferences.Editor editor;

    //Gson 객체
    Gson gson = new Gson();

    static String myID;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //preferences 객체
        setting = getSharedPreferences("setting", MODE_PRIVATE);
        editor= setting.edit();

//        editor.clear();
//        editor.commit();

        //findview
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        btn01 = (Button) findViewById(R.id.btn01);
        but_next = (Button) findViewById(R.id.but_next);
        but_join = (TextView) findViewById(R.id.but_join);

        //JOIN버튼
        but_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginJobOffererActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });

        //LOGIN버튼
        but_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = et_email.getText().toString().trim();
                String passwd = et_password.getText().toString().trim();

                myID = email;
                Log.d("!!!LOGIN버튼", "myID : " + myID);

                if(email.length() == 0) {
                    Toast.makeText(getApplication(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    et_email.requestFocus();
                    return;
                }
                if(passwd.length() == 0) {
                    Toast.makeText(getApplication(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    et_password.requestFocus();
                    return;
                }



                //기존에 있던거 obj x
                //쉐어드에서 gson데이터 받아와서 mylistDataset 에 전달
                String joinload = setting.getString("joinDataSet", "");
                Type type = new TypeToken<ArrayList<Data_join>>() {}.getType();
                ArrayList<Data_join> data_join = new ArrayList<Data_join>();
                data_join = gson.fromJson(joinload,type);

                Log.d("00 | " + "LOGIN_jobOfferer", "data_join : " + data_join);



                if(data_join != null) {
                    //data_joinlist에 있는 값 들고와서 비교해야함
                    for (int i = 0; i < data_join.size(); i++)
                    {
                        //로그인 데이터에 내가 입력한 이메일 정보와 같은게 있으면
                        if (data_join.get(i).getEmail().equals(et_email.getText().toString()))
                        {
                            if(data_join.get(i).getPasswd().equals(passwd))
                            {
                                Toast.makeText(getApplication(), "로그인 성공!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginJobOffererActivity.this, MainActivity.class);
                                Log.d("!!!intent", "intent : " + intent);
                                startActivity(intent);

                            }
                            else if (data_join.get(i).getPasswd() != passwd)
                            {
                                Toast.makeText(getApplication(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                } else {
                    Log.d("00 | " + "LOGIN_jobOfferer", "data_join == null");

                }

            }
        });

        //interface구현하여 이벤트 리스너 처리
        btn01.setOnClickListener(this);

//        String loginstr = setting.getString("login", "");
//        try {
//            JSONObject jsonObject = new JSONObject(loginstr);
//            String email_val = jsonObject.getString("email");
//            String pass_val = jsonObject.getString("password");
//            et_email.setText(email_val);
//            et_password.setText(pass_val);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }


    @Override
    public void onClick(View view)
    {
        String edit_email, edit_password;

        edit_email = et_email.getText().toString();
        edit_password = et_password.getText().toString();

        //btn01을 누르면
        if(view.getId() == R.id.btn01)
        {
            JSONObject obj = new JSONObject();
            try {
                obj.put("email", edit_email);
                obj.put("password", edit_password);
                String login = obj.toString();
                Log.d("!!!login", "login : " + login);
                editor.putString("login", login);
                editor.commit();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
