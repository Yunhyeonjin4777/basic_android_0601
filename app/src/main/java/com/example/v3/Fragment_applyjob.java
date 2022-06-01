package com.example.v3;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Fragment_applyjob extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private String mParam1;
    private String mParam2;
    private String mParam3;

    //리사이클러뷰 변수 선언
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Adapter_applyjob adapter_applyjob;

    //preferences 객체 구현
    SharedPreferences jobseeDataset;
    SharedPreferences.Editor editor;

    //Gson 객체
    Gson gson = new Gson();

    //어댑터로 값을 넘겨주기 위한 array
    private ArrayList<Data_JobSeekerjoin> applyDataset  = new ArrayList<>();
    // 하지만 사용은 static data_JobSeekerjoin1를 사용함


    public Fragment_applyjob() {
        // Required empty public constructor
    }

    //메인에서 Fragment_home.newInstance라고 Fragment_home을 호출할 때
    //Fragment_home 메모리에 올라와 있는 것을 가져오게 되는 것
    public static Fragment_applyjob newInstance(String param1, String param2, String param3) {
        Fragment_applyjob fragment = new Fragment_applyjob();
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
        //레이아웃과 연결
        View view = (View) inflater.inflate(R.layout.fragment_applyjob, container, false);

        //preferences jobseeDataset 셋팅
        jobseeDataset = getContext().getSharedPreferences("jobseeDataset", MODE_PRIVATE);
        editor= jobseeDataset.edit();

        //리사이클러뷰 셋팅
        recyclerView = view.findViewById(R.id.rv_applyjob);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter_applyjob = new Adapter_applyjob(applyDataset);
        //리사이클러뷰 연결
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_applyjob);

        //지원한 프로필 정보를 joinlist에 전달
        String joinload = jobseeDataset.getString(LoginJobOffererActivity.myID, "");
        Type type = new TypeToken<ArrayList<Data_JobSeekerjoin>>() {}.getType();
        ArrayList<Data_JobSeekerjoin> joinlist = gson.fromJson(joinload,type);

        adapter_applyjob.setAdapter(joinlist);

        return view;
    }
}
