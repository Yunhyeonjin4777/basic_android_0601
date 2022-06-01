package com.example.v3;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Fragment_jobseekermenu extends Fragment {

    Button but_choice;
    TextView tv_name;
    ImageView iv_photo;

    //preferences 선언
    SharedPreferences jobseeDataset;
    SharedPreferences.Editor editor;

    //Gson 객체
    Gson gson = new Gson();

    //URI를 저장할 변수 선언
    private Uri uri;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private String mParam1;
    private String mParam2;
    private String mParam3;

    public Fragment_jobseekermenu() {
        // Required empty public constructor
    }

    //메인에서 Fragment_home.newInstance라고 Fragment_home을 호출할 때
    //Fragment_home 메모리에 올라와 있는 것을 가져오게 되는 것
    public static Fragment_jobseekermenu newInstance(String param1, String param2, String param3) {
        Fragment_jobseekermenu fragment = new Fragment_jobseekermenu();
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
        View view = (View) inflater.inflate(R.layout.fragment_jobseekermenu, container, false);

        //preferences 셋팅
        jobseeDataset = getContext().getSharedPreferences("jobseeDataset", MODE_PRIVATE);
        editor= jobseeDataset.edit();

        tv_name = view.findViewById(R.id.tv_name);
        but_choice = (Button) view.findViewById(R.id.but_choice);
        iv_photo = view.findViewById(R.id.iv_photo);

        String loadlist = jobseeDataset.getString("joinDataSet", "");
        Type type = new TypeToken<ArrayList<Data_JobSeekerjoin>>() {}.getType();
        ArrayList<Data_JobSeekerjoin> data_join = gson.fromJson(loadlist,type);

        for(int i = 0; i < data_join.size(); i++)
        {
            if(data_join.get(i).getEmail().equals(LoginJobSeekerActivity.myIDJobseeker))
            {
                String get_name = data_join.get(i).getEmail();
                tv_name.setText(get_name);

                uri = getUriFromPath(getContext(), data_join.get(i).getPhoto());
                Log.d("TAG","data_join.get(i).getPhoto() : " +  data_join.get(i).getPhoto());
                Log.d("TAG","uri : " +  uri);
                iv_photo.setImageURI(uri);

            }
        }

        //전환
        but_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChoiceActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    // 절대경로 -> URI
    public Uri getUriFromPath(Context context, String filePath) {

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, "_data = '" + filePath + "'", null, null);

        Log.d("!!!getUriFromPath","filePath : " +  filePath);

        cursor.moveToNext();
        int id = cursor.getInt(0);
//        int id = cursor.getInt(cursor.getColumnIndex("_id"));

        Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

        return uri;
    }
}
