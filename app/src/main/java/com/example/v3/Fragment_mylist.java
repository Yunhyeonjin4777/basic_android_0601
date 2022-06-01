package com.example.v3;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import static com.example.v3.LoginJobOffererActivity.myID;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class Fragment_mylist extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private String mParam1;
    private String mParam2;
    private String mParam3;


    //리사이클러뷰 변수 선언
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    static Adapter_mylist adapter_mylist;

    //어댑터로 값을 넘겨주기 위한 array
    ArrayList<Data_mylist> mylistDataset = new ArrayList<>();


    //작성하기 button
    Button write_btn;

    //preferences setting 객체 구현


    //Gson 객체
    Gson gson = new Gson();

    //Launcher 셋팅
    ActivityResultLauncher<Intent> activityResultLauncher;

    //난수생성
    int max_num_value = 500;
    int min_num_value = 1;

    View view;

    SharedPreferences setting;
    SharedPreferences.Editor editor;

    public Fragment_mylist() {
        // Required empty public constructor
    }

    //메인에서 Fragment_home.newInstance라고 Fragment_home을 호출할 때
    //Fragment_home 메모리에 올라와 있는 것을 가져오게 되는 것
    public static Fragment_mylist newInstance(String param1, String param2, String param3) {
        Fragment_mylist fragment = new Fragment_mylist();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    //Fragment_home가 메모리에 올라갔을 때
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
        }

    }

    @Override
    //뷰가 생성되었을 때
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("00 | " + "MYLIST", "onCreateView");

        View view = (View) inflater.inflate(R.layout.fragment_mylist, container, false);

        //일감 추가 버튼 이벤트
        write_btn = (Button) view.findViewById(R.id.write_btn);

        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("00 | " + "MYLIST", "[일감추가] 버튼 이벤트");

                Intent intent = new Intent(view.getContext(), JobWriteActivity.class);
                startActivity(intent);
//                activityResultLauncher.launch(intent);
            }
        });


        //preferences 셋팅
        setting = getContext().getSharedPreferences("setting", MODE_PRIVATE);
        editor= setting.edit();


        //리사이클러뷰 셋팅
        recyclerView = view.findViewById(R.id.rv_mylist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter_mylist = new Adapter_mylist(getActivity(), mylistDataset);

        //리사이클러뷰 연결
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_mylist);


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.e("00 | " + "MYLIST", "onStart");



        String loadlist = setting.getString(LoginJobOffererActivity.myID, "");
        Type type = new TypeToken<ArrayList<Data_mylist>>() {}.getType();
        mylistDataset = gson.fromJson(loadlist,type);
        Log.d("00 | " + "MYLIST", "mylistDataset.size : " + mylistDataset.size());

        for (int i = 0; i < mylistDataset.size(); i++){
            Log.d("00 | " + "MYLIST", i + " 의 게시글 제목 : " + mylistDataset.get(i).getTitle());
        }


        adapter_mylist.setAdapter(mylistDataset);


//        //jobwrite에서 intent 수신결과
//        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
//                , new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        if(result.getResultCode() == RESULT_OK)
//                        {
//                            if(result.getData() != null)
//                            {
//                                Log.d("00 | " + "MYLIST", "activityResultLauncher 수신 결과");
//
//                                Bundle bundle = result.getData().getExtras();
//                                String get_title = (String) bundle.get("title");
//                                String get_datestart = (String) bundle.get("datestart");
//                                String get_dateend = (String) bundle.get("dateend");
//                                String get_pay = (String) bundle.get("pay");
//                                String get_cropinfo = (String) bundle.get("cropinfo");
//                                String get_person = (String) bundle.get("person");
//                                String get_content = (String) bundle.get("content");
//                                String get_num = (String) bundle.get("num");
//                                String get_photo = (String) bundle.get("photo");
//                                String get_address = (String) bundle.get("address");
//                                String get_latitude = (String) bundle.get("latitude");
//                                String get_longitude = (String) bundle.get("longitude");
//
//                                //난수생성해서 일련번호
//                                Random random = new Random();
//                                int randomNum = random.nextInt(max_num_value - min_num_value + 1) + min_num_value;
//
//                                Log.d("00 | " + "MYLIST", get_title + " | " + get_datestart + " | " + get_dateend + " | " + get_pay + " | " + get_cropinfo + " | " + get_person + " | " + get_content + " | " + get_num + " | " + get_photo + " | " + get_address + " | " + get_latitude + " | " + get_longitude);
//                                Log.d("00 | " + "MYLIST", "randomNum : " + randomNum);
//
//
//                                mylistDataset.add(new Data_mylist(LoginJobOffererActivity.myID, get_title, get_cropinfo, get_datestart, get_dateend, get_pay, get_person, get_content, get_photo, get_num, get_address, get_latitude, get_longitude, randomNum));
//                                String jobDataSet = gson.toJson(mylistDataset);
//                                Log.d("00 | " + "MYLIST", "jobDataSet : " + jobDataSet);
//
//                                //쉐어드에 저장
//                                editor.putString(LoginJobOffererActivity.myID, jobDataSet);
//                                editor.commit();
//
//                                alljobDataset.add( new Data_mylist(myID, get_title, get_cropinfo, get_datestart, get_dateend, get_pay, get_person, get_content, get_photo, get_num, get_address, get_latitude, get_longitude, randomNum));
//                                String alljobDataSet = gson.toJson(alljobDataset);
//                                Log.d("00 | " + "MYLIST", "alljobDataSet : " + alljobDataSet);
//
//                                //쉐어드에 저장
//                                editor.putString("alljobDataSet", alljobDataSet);
//                                editor.commit();
//
//
//                                Toast.makeText(getContext(), "작성 되었습니다", Toast.LENGTH_SHORT).show();
//                            }
//                            else {
//                                Toast.makeText(getContext(), "저장되지 않았습니다", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                });





    }

    @Override
    public void onResume() {
        super.onResume();
//
//
//        String loadlist = setting.getString(LoginJobOffererActivity.myID, "");
//        Type type = new TypeToken<ArrayList<Data_mylist>>() {}.getType();
//        ArrayList<Data_mylist> mylistDataset = gson.fromJson(loadlist,type);
//        Log.d("TAG", "ArrayList<Data_mylist> mylistDataset : " + mylistDataset);
//        adapter_mylist.setAdapter(mylistDataset);
//
//        //리사이클러뷰 연결
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter_mylist);

    }

}


