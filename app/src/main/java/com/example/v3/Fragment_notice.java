package com.example.v3;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Fragment_notice extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private String mParam1;
    private String mParam2;
    private String mParam3;

    //리사이클러뷰 변수 선언
    private RecyclerView recyclerView;
    private Adapter_notice adapter_notice;

    //어댑터로 값을 넘겨주기 위한 array
    private ArrayList<Data_JobSeekerjoin> joinlist;

    //preferences jobseeDataset 객체 구현
    SharedPreferences jobseeDataset;
    SharedPreferences.Editor editor;

    //Gson 객체
    Gson gson = new Gson();

    public Fragment_notice() {
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
        View view = (View) inflater.inflate(R.layout.fragment_notice, container, false);

        //preferences jobseeDataset 셋팅
        jobseeDataset = getContext().getSharedPreferences("jobseeDataset", MODE_PRIVATE);
        editor= jobseeDataset.edit();

        joinlist = new ArrayList<>();

        //리사이클러뷰 셋팅
        recyclerView = view.findViewById(R.id.rv_notice);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        adapter_notice = new Adapter_notice(joinlist);
        //리사이클러뷰 연결
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter_notice);

        //쉐어드에서 gson데이터 받아와서 mylistDataset 에 전달
        String joinload = jobseeDataset.getString(LoginJobOffererActivity.myID, "");
        Type type = new TypeToken<ArrayList<Data_JobSeekerjoin>>() {}.getType();
        ArrayList<Data_JobSeekerjoin> joinlist = gson.fromJson(joinload,type);

        adapter_notice.setAdapter(joinlist);





//        //mylistDataset.get(0)일감목록에 해당하는 지원자가 있으면 출력해라
//        if(Fragment_mylist.mylistDataset.get(0) != null)
//        {
//            for(int i = 0; i < joinlist.size(); i++)
//            {
//                if(Fragment_mylist.mylistDataset.get(0).getRandomNum() == joinlist.get(i).getRandamNum())
//                {
//                    Log.d("TAG", "getRandomNum() : " + Fragment_mylist.mylistDataset.get(0).getRandomNum() + " / " + joinlist.get(i).getRandamNum());
//
//                    adapter_notice.setAdapter(joinlist);
//                    adapter_notice.notifyDataSetChanged();
//                }
//            }
//        }




//        String RandomNum_1_profile = jobseeDataset.getString(String.valueOf(RandomNum_1), "");
//        Type type = new TypeToken<ArrayList<Data_JobSeekerjoin>>() {}.getType();
//        ArrayList<Data_JobSeekerjoin> RandomNum_1_profile_list = gson.fromJson(RandomNum_1_profile,type);
//        allprofile.add(RandomNum_1_profile_list);
//        //[com.example.v3.Data_mylist@e775e79]


//        //mylistDataset 추가 문구
//        Data_mylist data_mylist = new Data_mylist(LoginJobOffererActivity.myID, get_title, get_cropinfo, get_datestart, get_dateend, get_pay, get_person, get_content, get_photo, get_num, get_address, get_latitude, get_longitude, randomNum);


//        allprofile.add(data_mylist);
//        //[com.example.v3.Data_mylist@e775e79]
//
//        String jobDataSet = gson.toJson(allprofile);
//        //[{"ID":"1","address":"null","content":"","cropinfo":"","dateend":"","datestart":"","num":"","pay":"","person":"","randomNum":399,"title":"11"}]
//
//        //쉐어드에 저장
//        editor.putString(allprofile_gson, jobDataSet);
//        editor.commit();


//        String loadlist = jobseeDataset.getString(allprofile_gson, "");
//        type = new TypeToken<ArrayList<Data_JobSeekerjoin>>() {}.getType();
//        ArrayList<Data_JobSeekerjoin> allprofile = gson.fromJson(loadlist,type);
//        adapter_notice.setAdapter(allprofile);
//        //[com.example.v3.Data_mylist@20590ed, com.example.v3.Data_mylist@2d51b22]



//        //arraylist에서 같은 id가 있으면 삭제
//        for(Data_mylist value : Fragment_mylist.mylistDataset)
//        {
//            if(value.getID().equals(LoginJobOffererActivity.myID))
//            {
//                Log.d("!!!있으면 출력해라 통과", "Fragment_mylist.mylistDataset : " + Fragment_mylist.mylistDataset);
//                //리사이클러뷰 셋팅
//                recyclerView = view.findViewById(R.id.rv_notice);
//                recyclerView.setHasFixedSize(true);
//                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
//                adapter_notice = new Adapter_notice(joinlist);
//                //리사이클러뷰 연결
//                recyclerView.setLayoutManager(gridLayoutManager);
//                recyclerView.setAdapter(adapter_notice);
//
//                //쉐어드에서 gson데이터 받아와서 mylistDataset 에 전달
//                String joinload = jobseeDataset.getString(LoginJobOffererActivity.myID, "");
//                Type type = new TypeToken<ArrayList<Data_JobSeekerjoin>>() {}.getType();
//                ArrayList<Data_JobSeekerjoin> joinlist = gson.fromJson(joinload,type);
//
//                adapter_notice.setAdapter(joinlist);
//            }
//            else if(LoginJobOffererActivity.myID == null)
//            {
//                Toast.makeText(getContext(), "지원자의 프로필이 없습니다", Toast.LENGTH_SHORT).show();
//            }
//        }

        return view;
    }
}
