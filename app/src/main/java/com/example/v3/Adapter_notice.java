package com.example.v3;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Adapter_notice extends RecyclerView.Adapter<Adapter_notice.ViewHolder>{

    //URI를 저장할 변수 선언
    private Uri uri;

    //Gson 객체
    Gson gson = new Gson();


    //preferences jobseeDataset 객체 구현
    SharedPreferences jobseeDataset;
    SharedPreferences.Editor editor;


    private ArrayList<Data_JobSeekerjoin> joinlist;

    public Adapter_notice(ArrayList<Data_JobSeekerjoin> joinlist) {
        this.joinlist = joinlist;
    }

    public void setAdapter(ArrayList<Data_JobSeekerjoin> joinlist) {
        this.joinlist = joinlist;
        Log.d("!!!setAdapter()","this.joinlist : " + this.joinlist);
    }


    @NonNull
    @Override
    public Adapter_notice.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_grid_notice, parent, false) ;
        Adapter_notice.ViewHolder vh = new Adapter_notice.ViewHolder(view) ;


        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_notice.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();

        //preferences jobseeDataset 셋팅
        jobseeDataset = context.getSharedPreferences("jobseeDataset", MODE_PRIVATE);
        editor= jobseeDataset.edit();

        holder.tv_name.setText(joinlist.get(position).getName());
        holder.tv_sex.setText(joinlist.get(position).getSex());
        holder.tv_age.setText(joinlist.get(position).getAge());
        //title
        holder.tv_title.setText(joinlist.get(position).getTitle());

        //call
        holder.but_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + joinlist.get(position).getNum()));
                Log.d("TAG","joinlist.get(position).getNum() : " +  joinlist.get(position).getNum());
                view.getContext().startActivity(i);
//                context.startActivity(i);
            }
        });

        //이미지경로
        if (joinlist.get(position).getPhoto() != null)
        {
            uri = getUriFromPath(context, joinlist.get(position).getPhoto());
            Log.d("TAG","joinlist.get(position).getPhoto() : " +  joinlist.get(position).getPhoto());
            Log.d("TAG","uri : " +  uri);
            holder.grid_item_img.setImageURI(uri);
        }

        //확정하기 버튼이벤트
        holder.but_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                holder.but_confirm.setText("확정하였습니다");

                Log.d("TAG","getStatus() : " + Fragment_joblist.data_JobSeekerjoin1.get(position).getStatus());

                Fragment_joblist.data_JobSeekerjoin1.get(position).setStatus("확정");
                Log.d("TAG","set -> getStatus() : " +  Fragment_joblist.data_JobSeekerjoin1.get(position).getStatus());

                String joinDataSet = gson.toJson(Fragment_joblist.data_JobSeekerjoin1);

                editor.putString(LoginJobOffererActivity.myID, joinDataSet);
                editor.commit();
                


            }
        });


    }

    @Override
    public int getItemCount() {
        Log.d("!!!getItemCount()","joinlist : " + joinlist);
        if(joinlist != null)
        {
            return joinlist.size() ;
        }
        else
        {
            return 0 ;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_name;
        public TextView tv_sex;
        public TextView tv_age;
        public ImageView grid_item_img;
        //title
        public TextView tv_title;
        //확정하기
        public Button but_confirm;
        //call
        public Button but_call;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_sex = itemView.findViewById(R.id.tv_sex);
            tv_age = itemView.findViewById(R.id.tv_age);
            grid_item_img = itemView.findViewById(R.id.grid_item_img);
            //title
            tv_title = itemView.findViewById(R.id.tv_title);
            //확정하기
            but_confirm = itemView.findViewById(R.id.but_confirm);
            //call
            but_call = itemView.findViewById(R.id.but_call);

        }
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
