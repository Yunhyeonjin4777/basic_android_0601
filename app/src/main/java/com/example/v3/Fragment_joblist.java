package com.example.v3;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Fragment_joblist extends Fragment {

    //뷰페이저
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 3000;
    ViewPager viewPager;
    Integer[] imageId = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4};
    String[] imagesName = {"image1","image2","image3","image4"};
    //뷰페이저

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private String mParam1;
    private String mParam2;
    private String mParam3;

    //리사이클러뷰 변수 선언
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Adapter_joblist adapter_joblist;




    //어댑터로 값을 넘겨주기 위한 array
    static ArrayList<Data_joblist> joblistDataset;
    static ArrayList<Data_JobSeekerjoin> data_JobSeekerjoin1 = new ArrayList<>();


    //preferences 객체 구현
    SharedPreferences setting;
    SharedPreferences.Editor editor;

    //Gson 객체
    Gson gson = new Gson();


    public Fragment_joblist() {
        // Required empty public constructor
    }

    //메인에서 Fragment_home.newInstance라고 Fragment_home을 호출할 때
    //Fragment_home 메모리에 올라와 있는 것을 가져오게 되는 것
    public static Fragment_joblist newInstance(String param1, String param2, String param3) {
        Fragment_joblist fragment = new Fragment_joblist();
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
        View view = (View) inflater.inflate(R.layout.fragment_joblist, container, false);

        //preferences 셋팅
        setting = getContext().getSharedPreferences("setting", MODE_PRIVATE);
        editor= setting.edit();

        //1. 화면이 로딩 -> 등록된 일감정보를 받아온다
        //2. 정보 -> 어댑터에게 넘겨준다
        //3. 어댑터 -> 셋팅

        joblistDataset = new ArrayList<>();

        //리사이클러뷰 셋팅
        recyclerView = view.findViewById(R.id.rv_joblist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter_joblist = new Adapter_joblist(joblistDataset);



        //리사이클러뷰 연결
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_joblist);



        //쉐어드에서 gson데이터 받아와서 mylistDataset 에 전달
        String alljobload = setting.getString("alljobDataSet", "");
        Type type = new TypeToken<ArrayList<Data_joblist>>() {}.getType();
        ArrayList<Data_joblist> joblistDataset = gson.fromJson(alljobload,type);
        adapter_joblist.setAdapter(joblistDataset);
        Log.d("!!!adapter_joblist", "adapter_joblist : " + adapter_joblist);



        //뷰페이저
        viewPager = (ViewPager) view.findViewById(R.id.pager_gallery);
        PagerAdapter adapter = new CustomAdapter(getActivity(),imageId,imagesName);
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if(currentPage == 3)
                {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        },DELAY_MS, PERIOD_MS);
        //뷰페이저

        return view;
    }

}
