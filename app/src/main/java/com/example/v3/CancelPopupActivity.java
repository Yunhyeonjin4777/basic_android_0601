package com.example.v3;

import static com.example.v3.Fragment_mylist.newInstance;
import static com.example.v3.LoginJobOffererActivity.myID;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CancelPopupActivity extends Activity {

    String key;
    int position;
    TextView textView;
    Button confirm_btn;
    Button cancel_btn;

    //preferences 변수 선언
    SharedPreferences setting;
    SharedPreferences.Editor setting_editor;

    //preferences jobseeDataset 변수 선언
    SharedPreferences jobseeDataset;
    SharedPreferences.Editor jobseeDataset_editor;

    //Gson 객체
    Gson gson = new Gson();

    ArrayList<Data_mylist> mylistDataset = new ArrayList<>();
    ArrayList<Data_mylist> alljobDataset = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cancelpopup);

        //preferences 셋팅
        setting = CancelPopupActivity.this.getSharedPreferences("setting", MODE_PRIVATE);
        setting_editor = setting.edit();

        //preferences jobseeDataset 셋팅
        jobseeDataset = CancelPopupActivity.this.getSharedPreferences("jobseeDataset", MODE_PRIVATE);
        jobseeDataset_editor = jobseeDataset.edit();

        //findview
        textView = (TextView) findViewById(R.id.textView);
        confirm_btn = findViewById(R.id.confirm_btn);
        cancel_btn = findViewById(R.id.cancel_btn);

        //인텐트로 데이터 받아오기
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        int position = intent.getIntExtra("position", 0);

        //내가 confirm_btn(확인) 버튼을 눌렀을 때
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("title", title);
                intent.putExtra("position", position);


                //alljobDataset에서 같은 randomNum이 있으면 삭제
                for (int j = 0; j < Fragment_joblist.data_JobSeekerjoin1.size(); j++)
                {
                    if(mylistDataset.get(position).getRandomNum() == Fragment_joblist.data_JobSeekerjoin1.get(j).getRandamNum())
                    {
                        Log.d("TAG", "삭제) getRandomNum() : " + mylistDataset.get(position).getRandomNum() + " / " + Fragment_joblist.data_JobSeekerjoin1.get(j).getRandamNum());
                        Log.d("TAG", "삭제) Fragment_joblist.data_JobSeekerjoin1 : " + Fragment_joblist.data_JobSeekerjoin1);

                        Fragment_joblist.data_JobSeekerjoin1.remove(j);

                        String joinDataSet = gson.toJson(Fragment_joblist.data_JobSeekerjoin1);
                        Log.d("TAG","joinDataSet : " + joinDataSet);

                        //쉐어드에 저장
                        jobseeDataset_editor.putString(LoginJobOffererActivity.myID, joinDataSet);
                        jobseeDataset_editor.commit();

                    }
                }

                //쉐어드에서 gson데이터 받아와서 mylistDataset 에 전달
                String loadlist = setting.getString(LoginJobOffererActivity.myID, "");

                Type type = new TypeToken<ArrayList<Data_mylist>>() {}.getType();
                mylistDataset = gson.fromJson(loadlist,type);
                Fragment_mylist.adapter_mylist.setAdapter(mylistDataset);
//                Fragment_mylist.adapter_mylist.notifyDataSetChanged();

                //arraylist에서 삭제
                mylistDataset.remove(position);
                String jobDataSet = gson.toJson(mylistDataset);
                Log.d("TAG", "삭제) mylistDataset : " + mylistDataset);

                //쉐어드에 저장
                setting_editor.putString(LoginJobOffererActivity.myID, jobDataSet);
                setting_editor.commit();


                //alljobDataset에서 같은 title이 있으면 삭제
                for(int i = 0; i < alljobDataset.size(); i++)
                {
                    if(alljobDataset.get(i).getTitle().equals(title))
                    {
                        alljobDataset.remove(i);
                        String alljobDataSet = gson.toJson(alljobDataset);

                        //쉐어드에 저장
                        setting_editor.putString("alljobDataSet", alljobDataSet);
                        setting_editor.commit();
                    }
                }


                Toast.makeText(getApplication(), "삭제되었습니다", Toast.LENGTH_SHORT).show();

                //액티비티(팝업) 닫기
                finish();






                //기존에 obj으로 재저장한거
//                String allDataSet = gson.toJson(Fragment_mylist.alljobDataset);
//                JSONObject obj = new JSONObject();
//                try {
//                     obj.put("myID", allDataSet);
//                     String objstr= obj.toString();
//                     editor.putString("alljobDataSet", objstr);
//                     editor.commit();
//                     } catch (JSONException e) {
//                     e.printStackTrace();
//                }

            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }


}
