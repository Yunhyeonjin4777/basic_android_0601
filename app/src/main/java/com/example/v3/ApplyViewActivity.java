package com.example.v3;

import static com.example.v3.LoginJobOffererActivity.myID;

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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ApplyViewActivity extends AppCompatActivity implements OnMapReadyCallback {

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

    //MAP 변수 선언
    private GoogleMap mMap;
    private String get_Latitude;
    private String get_Longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applyview);

        //MAP findView
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
        int get_randomNum = intent.getIntExtra("randomNum",0);
        Log.d("TAG","get_randomNum : " + get_randomNum);
        //title
        String get_title = intent.getStringExtra("title");
        Log.d("TAG","get_title : " + get_title);
        //map
        String get_address = intent.getStringExtra("address");
        get_Latitude = intent.getStringExtra("Latitude");
        get_Longitude = intent.getStringExtra("Longitude");
        Log.d("TAG", "Latitude, Longitude : " + get_Latitude + get_Longitude);

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

                //생성자로 데이터를 하나 더만들어서 추가해보기
                String email = JoinJobSeekerActivity.data_JobSeekerjoin.get(0).getEmail();
                String passwd = JoinJobSeekerActivity.data_JobSeekerjoin.get(0).getPasswd();
                String name = JoinJobSeekerActivity.data_JobSeekerjoin.get(0).getName();
                String content = JoinJobSeekerActivity.data_JobSeekerjoin.get(0).getContent();
                String num = JoinJobSeekerActivity.data_JobSeekerjoin.get(0).getNum();
                String sex = JoinJobSeekerActivity.data_JobSeekerjoin.get(0).getSex();
                String age = JoinJobSeekerActivity.data_JobSeekerjoin.get(0).getAge();
                String path = JoinJobSeekerActivity.data_JobSeekerjoin.get(0).getPhoto();
                int randamNum = get_randomNum;
                //해당 일감 title
                String title = get_title;
                Log.d("TAG","해당 일감 title로 바꿈 : " +  title);

                //해당 일감 임금
                String pay = get_pay;
                Log.d("TAG","해당 일감 임금로 바꿈 : " +  pay);

                //해당 일감 지원상태
                String status = JoinJobSeekerActivity.data_JobSeekerjoin.get(0).getStatus();

                //해당 일감 address
                String address = get_address;


                Data_JobSeekerjoin jobSeekerjoin = new Data_JobSeekerjoin(email, passwd, name, content, num, sex, age, path, randamNum, title, pay, status, address);

                Fragment_joblist.data_JobSeekerjoin1.add(jobSeekerjoin);

                String joinDataSet = gson.toJson(Fragment_joblist.data_JobSeekerjoin1);
                Log.d("TAG","joinDataSet : " + joinDataSet);

                //쉐어드에 저장
                editor.putString(get_ID, joinDataSet);
                editor.commit();

                Toast.makeText(getApplication(), "지원을 완료하였습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ApplyViewActivity.this, JobSeekerActivity.class);
                startActivity(intent);




//                //get_randomNum을 키로해서 저장시키기
//                String joinload = jobseeDataset.getString("joinDataSet", "");
//                editor.putString(String.valueOf(get_randomNum), joinload);
//                editor.commit();


//                for(int i = 0; i < JoinJobSeekerActivity.data_JobSeekerjoin.size(); i++)
//                {
//                    if(JoinJobSeekerActivity.data_JobSeekerjoin.get(i).getEmail().equals(LoginJobSeekerActivity.myIDJobseeker))
//                    {
//
//                        //생성자로 데이터를 하나 더만들어서 추가해보기
//                        String email = JoinJobSeekerActivity.data_JobSeekerjoin.get(i).getEmail();
//                        String passwd = JoinJobSeekerActivity.data_JobSeekerjoin.get(i).getPasswd();
//                        String name = JoinJobSeekerActivity.data_JobSeekerjoin.get(i).getName();
//                        String content = JoinJobSeekerActivity.data_JobSeekerjoin.get(i).getContent();
//                        String num = JoinJobSeekerActivity.data_JobSeekerjoin.get(i).getNum();
//                        String sex = JoinJobSeekerActivity.data_JobSeekerjoin.get(i).getSex();
//                        String age = JoinJobSeekerActivity.data_JobSeekerjoin.get(i).getAge();
//                        String path = JoinJobSeekerActivity.data_JobSeekerjoin.get(i).getContent();
//                        int randamNum = get_randomNum;
//
//                        Data_JobSeekerjoin jobSeekerjoin = new Data_JobSeekerjoin(email, passwd, name, content, num, sex, age, path, randamNum);
//                        Log.d("TAG","jobSeekerjoin : " + jobSeekerjoin);
//
//                        JoinJobSeekerActivity.data_JobSeekerjoin.add(jobSeekerjoin);
//
//                        String joinDataSet = gson.toJson(JoinJobSeekerActivity.data_JobSeekerjoin);
//                        Log.d("TAG","joinDataSet : " + joinDataSet);
//
//                        //쉐어드에 저장
//                        editor.putString(get_ID, joinDataSet);
//                        editor.commit();

//                        //기존에 있는 데이터의 randomNum만 set로 바꿔서 저장시키기
//                        JoinJobSeekerActivity.data_JobSeekerjoin.get(i).setRandamNum(get_randomNum);
//                        String joinDataSet = gson.toJson(JoinJobSeekerActivity.data_JobSeekerjoin);
//                        editor.putString(get_ID, joinDataSet);
//                        editor.commit();


//                        //array를 하나더 만들어서 넣어보기
//                        String joinDataSet = gson.toJson(JoinJobSeekerActivity.data_JobSeekerjoin);
//                        joinDataSet_Add.add(joinDataSet);
//                        editor.putString(get_ID, String.valueOf(joinDataSet_Add));
//                        editor.commit();

//                        String joinDataSet = gson.toJson(data_JobSeekerjoin);
//                        editor.putString("joinDataSet", joinDataSet);
//                        editor.commit();


                          //type 필요없는듯
//                        String joinload = jobseeDataset.getString("joinDataSet", "");
////                Type type = new TypeToken<ArrayList<Data_join>>() {}.getType();
////                ArrayList<Data_join> joinloadstr = gson.fromJson(joinload,type);
//                        editor.putString(get_ID, joinload);
//                        editor.commit();
//
//                    }
//                }

            }
        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap)
    {
        mMap = googleMap;
        LatLng latLng = new LatLng(Double.parseDouble(get_Latitude), Double.parseDouble(get_Longitude));
        Log.d("TAG", "latLng : " + latLng);
        //마커 생성
        MarkerOptions mOptions2 = new MarkerOptions();
        mOptions2.title("search result");
        //latlng : 위도 경도 쌍을 나타냄
        mOptions2.position(latLng);
        //마커(핀) 추가
        googleMap.addMarker(mOptions2);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
    }

}
