package com.example.v3;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

public class JoinJobSeekerActivity extends AppCompatActivity {

    //스틱코드
    private String uri_string;

    EditText join_email;
    EditText join_passwd;
    EditText join_passwdcheck;
    EditText join_name;
    EditText join_content;
    EditText join_num;
    //추가
    EditText join_sex;
    EditText join_age;
    //photo
    ImageView img_cloth;
    Button btn_upload;

    Button but_join;

    //절대 경로를 저장할 변수 선언
    private String path;

//    Context context = getApplicationContext();

    static ArrayList<Data_JobSeekerjoin> data_JobSeekerjoin = new ArrayList<>();

    //preferences 객체 구현
    SharedPreferences jobseeDataset;
    SharedPreferences.Editor editor;

    //Gson 객체
    Gson gson = new Gson();

    //Launcher 셋팅
    ActivityResultLauncher<Intent> activityResultLauncher;

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinjobseeker);

        join_email = findViewById(R.id.join_email);
        join_passwd = findViewById(R.id.join_passwd);
        join_passwdcheck = findViewById(R.id.join_passwdcheck);
        join_name = findViewById(R.id.join_name);
        join_content = findViewById(R.id.join_content);
        join_num = findViewById(R.id.join_num);
        //추가
        join_sex = findViewById(R.id.join_sex);
        join_age = findViewById(R.id.join_age);
        //photo
        img_cloth = findViewById(R.id.img_cloth);
        btn_upload = findViewById(R.id.btn_upload);

        but_join = findViewById(R.id.but_join);

        //preferences 셋팅
        jobseeDataset = getSharedPreferences("jobseeDataset", MODE_PRIVATE);
        editor = jobseeDataset.edit();


        //이미지업로드 클릭 시 권한 확인
        btn_upload.setOnClickListener(new View.OnClickListener() {

            //사진 접근권한 확인
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                //권한을 가지고 있는지 없는지를 반환하는 메소드
                //반환 값이 PackageManager.PERMISSION_GRANTED와 같은지 비교
                // Pakage.PERMISSION_GRANTED -> 권한 있음
                // Pakage.PERMISSION_DENIED -> 권한 없음
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getApplicationContext(), "사진 수신권한 없음", Toast.LENGTH_SHORT).show();
                    Log.e("permissionCheck : ", "permission error");

                    if (shouldShowRequestPermissionRationale(
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        //사용자에게 사진을 읽어야 하는 이유를 설명합니다
                        Toast.makeText(getApplicationContext(),"사진 권한이 필요합니다", Toast.LENGTH_SHORT).show();
                    }

                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                    return;
                }

                Toast.makeText(getApplicationContext(), "사진 수신권한 있음", Toast.LENGTH_SHORT).show();
                Log.e("permissionCheck : ", "permission success");

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
                                Log.d("!!!이미지uri", "uri : " + uri);
                                //스틱코드
//                                uri_string = uri.toString();

                                //절대 경로 얻을 수 있는 메소드 사용
                                path = getRealPathFromURI(getApplicationContext(), uri);

                                Toast.makeText(JoinJobSeekerActivity.this, "사진을 업로드하였습니다", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(JoinJobSeekerActivity.this, "사진을 업로드하지 못했습니다", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });








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
                //추가
                String sex = join_sex.getText().toString();
                String age = join_age.getText().toString();
                //난수
                int randamNum = 0;
                String title = "title";
                String pay = "0";
                String status = "지원중";
                String address = "주소";

                Data_JobSeekerjoin jobSeekerjoin = new Data_JobSeekerjoin(email, passwd, name, content, num, sex, age, path, randamNum, title, pay, status, address);
                Log.d("!!!절대경로", "path : " + path);
                data_JobSeekerjoin.add(jobSeekerjoin);

                String joinDataSet = gson.toJson(data_JobSeekerjoin);

                //jobSeeDataset 쉐어드에 저장
                editor.putString("joinDataSet", joinDataSet);
                editor.commit();

                Intent intent = new Intent(JoinJobSeekerActivity.this, LoginJobSeekerActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }//onCreate


    // URI -> 절대경로
    public static String getRealPathFromURI(Context context, Uri contentUri) {

        String[] proj = {MediaStore.Images.Media.DATA};
        Log.d("getRealPathFromURI", "contentUri : " + contentUri);
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        cursor.moveToNext();
        String path = cursor.getString((cursor.getColumnIndex(MediaStore.MediaColumns.DATA)));
        Log.d("getRealPathFromURI", "path : " + path);

        Uri uri = Uri.fromFile(new File(path));

        cursor.close();
        return path;
    }


    // URI -> 절대경로 (수정전)
//    public String getRealPathFromURI(Uri contentUri) {
//        String[] proj = {MediaStore.Images.Media.DATA};
//        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
//        cursor.moveToNext();
//        int index = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
//        String path = cursor.getString(index);
////        Uri uri = Uri.fromFile(new File(path));
//
//        cursor.close();
//        return path;
//    }


}
