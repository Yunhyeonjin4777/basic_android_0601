package com.example.v3;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Fragment_jobofferermenu extends Fragment {

    Button but_choice;
    TextView tv_name;
    TextView tv_email;
    //preferences setting 객체 구현
    SharedPreferences setting;
    SharedPreferences.Editor editor;

    //Gson 객체
    Gson gson = new Gson();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private String mParam1;
    private String mParam2;
    private String mParam3;

    public Fragment_jobofferermenu() {
    }

    public static Fragment_jobofferermenu newInstance(String param1, String param2, String param3) {
        Fragment_jobofferermenu fragment = new Fragment_jobofferermenu();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //레이아웃과 연결
        View view = (View) inflater.inflate(R.layout.fragment_jobofferermenu, container, false);

        //preferences 셋팅
        setting = getContext().getSharedPreferences("setting", MODE_PRIVATE);
        editor= setting.edit();

        tv_email = view.findViewById(R.id.tv_email);
        tv_name = view.findViewById(R.id.tv_name);

        String loadlist = setting.getString("joinDataSet", "");
        Type type = new TypeToken<ArrayList<Data_join>>() {}.getType();
        ArrayList<Data_join> data_join = gson.fromJson(loadlist,type);

        for(int i = 0; i < data_join.size(); i++)
        {
            if(data_join.get(i).getEmail().equals(LoginJobOffererActivity.myID))
            {
                String get_email = data_join.get(i).getEmail();
                tv_email.setText(get_email);

                String get_name = data_join.get(i).getName();
                tv_name.setText(get_name);
            }
        }

        but_choice = (Button) view.findViewById(R.id.but_choice);

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
}
