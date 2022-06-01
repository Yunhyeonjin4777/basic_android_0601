package com.example.v3;

import static com.example.v3.LoginJobOffererActivity.myID;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class JobWriteActivity extends AppCompatActivity{

    EditText edit_title;
    EditText edit_datestart;
    EditText edit_dateend;
    EditText edit_pay;
    EditText edit_cropinfo;
    EditText edit_person;
    EditText edit_content;
    //num
    EditText edit_num;

    Button back_btn;
    Button save_btn;
    //map
    Button but_place;
    EditText edit_place;
    EditText edit_place2;

    //photo
    ImageView img_cloth;
    Button btn_upload;

    //절대 경로를 저장할 변수 선언
    private String path;
    //map 변수선언
    private String address;
    private String latitude;
    private String longitude;

    //preferences 객체 구현
    SharedPreferences setting;
    SharedPreferences.Editor editor;

    //Launcher 셋팅
    ActivityResultLauncher<Intent> activityResultLauncher;

    //난수생성
    int max_num_value = 500;
    int min_num_value = 1;

    //어댑터로 값을 넘겨주기 위한 array
    ArrayList<Data_mylist> myList = new ArrayList<>();
    ArrayList<Data_mylist> allList = new ArrayList<>();

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobwrite);
        Log.e("00 | " + "WRITE", "onCreate");

        //findview
        edit_title = (EditText) findViewById(R.id.edit_title);
        edit_datestart = (EditText) findViewById(R.id.edit_datestart);
        edit_dateend = (EditText) findViewById(R.id.edit_dateend);
        edit_pay = (EditText) findViewById(R.id.edit_pay);
        edit_cropinfo = (EditText) findViewById(R.id.edit_cropinfo);
        edit_person = (EditText) findViewById(R.id.edit_person);
        edit_content = (EditText) findViewById(R.id.edit_content);
        //photo
        img_cloth = findViewById(R.id.img_cloth);
        btn_upload = findViewById(R.id.btn_upload);
        //num
        edit_num = findViewById(R.id.edit_num);
        //map
        but_place = findViewById(R.id.but_place);
        edit_place = findViewById(R.id.edit_place);
        edit_place2 = findViewById(R.id.edit_place2);

        //뒤로가기
//        back_btn = (Button) findViewById(R.id.back_btn);
        save_btn = (Button) findViewById(R.id.save_btn);

        //preferences 셋팅
        setting = getSharedPreferences("setting", MODE_PRIVATE);
        editor= setting.edit();

        //back 클릭이벤트
//        back_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

        //map버튼
        but_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JobWriteActivity.this, Map2Activity.class);
                activityResultLauncher.launch(intent);
            }
        });

        //이미지업로드 버튼
        btn_upload.setOnClickListener(new View.OnClickListener() {
            //TODO DKFJFJFJFJ
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);
            }
        });

        //이미지 수신결과
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                , new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK)
                        {


                            Uri uri = null;
                            if(result.getData() != null)
                            {
                                Intent intent = result.getData();
                                uri = intent.getData();
                            }



                            if(uri != null)
                            {
                                img_cloth.setImageURI(uri);

                                //절대 경로 얻을 수 있는 메소드 사용
                                path = getRealPathFromURI(getApplicationContext(), uri);

                                File file = new File(path);
                                file.getName();


                                Toast.makeText(JobWriteActivity.this, "사진을 업로드하였습니다", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(JobWriteActivity.this, "사진을 업로드하지 못했습니다", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if(result.getResultCode() == 1004)
                        {
                            Intent intent = result.getData();
                            address = (String) intent.getStringExtra("address");
                            latitude = (String) intent.getStringExtra("latitude");
                            longitude = (String) intent.getStringExtra("longitude");
                            edit_place.setText(address);
                        }
                    }
                });


        //save 클릭이벤트
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("00 | " + "WRITE", "[저장] 버튼 이벤트");

                //작성한 editText을 스트링 값으로 저장
                String title = edit_title.getText().toString();
                String datestart = edit_datestart.getText().toString();
                String dateend = edit_dateend.getText().toString();
                String pay = edit_pay.getText().toString();
                String cropinfo = edit_cropinfo.getText().toString();
                String person = edit_person.getText().toString();
                String content = edit_content.getText().toString();
                String num = edit_num.getText().toString();
                String detailAddress = edit_place2.getText().toString();
                Double.toString(1.24657);



                //난수생성해서 일련번호
                Random random = new Random();
                int randomNum = random.nextInt(max_num_value - min_num_value + 1) + min_num_value;

                Log.d("00 | " + "MYLIST", title + " | " + datestart + " | " + dateend + " | " + pay + " | " + cropinfo + " | " + person + " | " + content + " | " + num + " | " + detailAddress + " | " + address + detailAddress + " | " + latitude + " | " + longitude);
                Log.d("00 | " + "MYLIST", "randomNum : " + randomNum);


                myList.add(new Data_mylist(LoginJobOffererActivity.myID, title, cropinfo, datestart, dateend, pay, person, content, path, num, address + detailAddress, latitude, longitude, randomNum));
                String jobDataSet = gson.toJson(myList);
                Log.d("00 | " + "MYLIST", "jobDataSet : " + jobDataSet);

                //쉐어드에 저장
                editor.putString(LoginJobOffererActivity.myID, jobDataSet);
                editor.commit();

                allList.add( new Data_mylist(myID, title, cropinfo, datestart, dateend, pay, person, content, path, num, address + detailAddress, latitude, longitude, randomNum));
                String alljobDataSet = gson.toJson(allList);
                Log.d("00 | " + "MYLIST", "alljobDataSet : " + alljobDataSet);

                //쉐어드에 저장
                editor.putString("alljobDataSet", alljobDataSet);
                editor.commit();


                Toast.makeText(JobWriteActivity.this, "작성 되었습니다", Toast.LENGTH_SHORT).show();







//                //fragment_mylist로 인텐트 전달
//                Intent intent = getIntent();
//                intent.putExtra("title", title);
//                intent.putExtra("datestart", datestart);
//                intent.putExtra("dateend", dateend);
//                intent.putExtra("pay", pay);
//                intent.putExtra("cropinfo", cropinfo);
//                intent.putExtra("person", person);
//                intent.putExtra("content", content);
//                intent.putExtra("num", num);
//                intent.putExtra("photo", path);
//                intent.putExtra("address", address + detailAddress);
//                Log.d("TAG","address : " + address + " " + detailAddress);
//                intent.putExtra("latitude", latitude);
//                intent.putExtra("longitude", longitude);
//
//                setResult(RESULT_OK, intent);
//                finish();


                //jsonArray 이용해서 저장하기
//                JSONObject jsonObj1 = new JSONObject();
//                JSONArray jsonArr1 = new JSONArray();
//                try {
//                    //각각의 데이터 값을 obj로 넣기
//                    jsonObj1.put("title", title);
//                    jsonObj1.put("datestart", datestart);
//                    jsonObj1.put("dateend", dateend);
//                    jsonObj1.put("pay", pay);
//                    jsonObj1.put("cropinfo", cropinfo);
//                    jsonObj1.put("person", person);
//                    jsonObj1.put("content", content);
//
//                    //obj를 여러개 갖고 싶어서 jsonarray에 다시 넣는다
//                    jsonArr1.put(jsonObj1);
//
//                    //jxonarray에 담김 것을 jsonobject형식을 유지한 arraylist로 만들고 싶다
//                    ArrayList<JSONObject> arrayJson = new ArrayList<JSONObject>();
//
//                    for (int k = 0; k < jsonArr1.length(); k++)
//                    {
//                        JSONObject tempJson = jsonArr1.getJSONObject(k);
//                        arrayJson.add(tempJson);
//                    }
//
//                    String arrayJson = arrayJson.toString();
//
//                    //쉐어드에 저장
//                    editor.putString("arrayJson", arrayJson);
//                    editor.commit();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }


//                //jsonobject만 이용해서 저장하기
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

            }
        });

    }
    // URI -> 절대경로
    public static String getRealPathFromURI(Context context, Uri contentUri) {

        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        cursor.moveToNext();
        String path = cursor.getString((cursor.getColumnIndex(MediaStore.MediaColumns.DATA)));

        Uri uri = Uri.fromFile(new File(path));

        cursor.close();
        return path;
    }
}
