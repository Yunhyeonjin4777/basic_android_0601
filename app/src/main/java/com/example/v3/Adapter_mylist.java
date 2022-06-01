package com.example.v3;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.media.Image;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Adapter_mylist extends RecyclerView.Adapter<Adapter_mylist.ViewHolder>{

    //URI를 저장할 변수 선언
    private Uri uri;
    private Activity activity;


    private ArrayList<Data_mylist> mylistDataset;

    public Adapter_mylist(Activity activity, ArrayList<Data_mylist> mylistDataset) {
        this.activity = activity;
        this.mylistDataset = mylistDataset;

    }

    public void setAdapter(ArrayList<Data_mylist> mylistDataset) {
        this.mylistDataset = mylistDataset;
        Log.d("00 | " + "ADAPTER_MYLIST", "setAdapter_mylistDataset.size : " + mylistDataset.size());

    }

    @NonNull
    @Override
    public Adapter_mylist.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_rv_mylist, parent, false) ;
        Adapter_mylist.ViewHolder vh = new Adapter_mylist.ViewHolder(view) ;
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter_mylist.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d("00 | " + "ADAPTER_MYLIST", "position : " + position);

        holder.tv_title.setText(mylistDataset.get(position).getTitle());
        holder.tv_cropinfo.setText(mylistDataset.get(position).getCropinfo());
        holder.tv_datestart.setText(mylistDataset.get(position).getDatestart());
        holder.tv_dateend.setText(mylistDataset.get(position).getDateend());


        //이미지 뷰에 uri 로드
        if (mylistDataset.get(position).getPhoto() != null)
        {
            Log.d("00 | " + "ADAPTER_MYLIST","mylistDataset.get(position).getPhoto() : " +  mylistDataset.get(position).getPhoto());

//            uri = getUriFromPath(activity, mylistDataset.get(position).getPhoto());
////            Log.d("!!!path값","joinlist.get(position).getPhoto() : " +  mylistDataset.get(position).getPhoto());
////            Log.d("!!!이미지 뷰에 uri 로드","uri : " +  uri);

            Glide.with(activity)
                    .load(mylistDataset.get(position).getPhoto())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.iv_photo);

        }

        //상세보기(ViewActivity)로 이동
        holder.but_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailViewActivity.class);
                Log.d("00 | " + "ADAPTER_MYLIST","[상세보기] 버튼 이벤트");

                intent.putExtra("time", mylistDataset.get(position).getID());
                intent.putExtra("title", mylistDataset.get(position).getTitle());
                intent.putExtra("cropinfo", mylistDataset.get(position).getCropinfo());
                intent.putExtra("datestart", mylistDataset.get(position).getDatestart());
                intent.putExtra("dateend", mylistDataset.get(position).getDateend());
                intent.putExtra("pay", mylistDataset.get(position).getPay());
                intent.putExtra("person", mylistDataset.get(position).getPerson());
                intent.putExtra("content", mylistDataset.get(position).getContent());
                //photo
                intent.putExtra("uri", uri);
                //map
                intent.putExtra("address", mylistDataset.get(position).getAddress());
                intent.putExtra("Latitude", "37.557527");
                intent.putExtra("Longitude", "126.9244669");
                intent.putExtra("position", position);


                v.getContext().startActivity(intent);
                Toast.makeText(v.getContext(),"상세보기로 이동합니다", Toast.LENGTH_SHORT).show();
            }
        });

        //일정삭제(cancelpopup)으로 이동
        holder.but_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CancelPopupActivity.class);
                intent.putExtra("title", holder.tv_title.getText().toString());
                intent.putExtra("position", position);
                Log.d("!!!캔슬팝업으로 이동","intent : " +  intent);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mylistDataset != null)
        {
            return mylistDataset.size() ;
        }
        else
        {
            return 0 ;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_title;
        public TextView tv_cropinfo;
        public TextView tv_datestart;
        public TextView tv_dateend;
        //photo
        public ImageView iv_photo;

        public Button but_view;
        public Button but_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_cropinfo = itemView.findViewById(R.id.tv_cropinfo);
            tv_datestart = itemView.findViewById(R.id.tv_datestart);
            tv_dateend = itemView.findViewById(R.id.tv_dateend);
            //photo
            iv_photo = itemView.findViewById(R.id.iv_photo);

            but_view = itemView.findViewById(R.id.but_view);
            but_delete = itemView.findViewById(R.id.but_delete);

        }
    }


    public Uri getUriFromPath(String filePath) {
        Cursor cursor = activity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, "_data = '" + filePath + "'", null, null);

        //커서를 다음 행으로 이동
        cursor.moveToNext();
        //값을 가져오는 메서드 (_id필드의 index 번호를 얻어 온다)
        int id = cursor.getInt(cursor.getColumnIndex("_id"));
        Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

        return uri;
    }


//    // 절대경로 -> URI
//    public Uri getUriFromPath(Context context, String filePath) {
//        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                null, "_data = '" + filePath + "'", null, null);
//        Log.d("TAG","filePath : " +  filePath);
//        cursor.moveToNext();
//
//        int id = cursor.getInt(0);
////        int id = cursor.getInt(cursor.getColumnIndex("_id"));
//        Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
//        return uri;

//        try
//        {
//            if(cursor.getCount() > 0)
//            {
//                Log.d("cursor","cursor.getCount() : " +  cursor.getCount());
//                int id = cursor.getInt(0);
//                Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
//                return uri;
//            }
//        } catch (SQLException e)
//        {
//            e.printStackTrace();
//            Log.d("cursor2","cursor.getCount() : " +  cursor.getCount());
//        }
//        Log.d("cursor3","uri : " +  uri);
//        return uri;
}
