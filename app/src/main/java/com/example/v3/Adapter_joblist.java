package com.example.v3;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

import java.util.ArrayList;

public class Adapter_joblist extends RecyclerView.Adapter<Adapter_joblist.ViewHolder>{




    //URI를 저장할 변수 선언
    private Uri uri;

    private ArrayList<Data_joblist> joblistDataset;

    public Adapter_joblist(ArrayList<Data_joblist> joblistDataset) {
        this.joblistDataset = joblistDataset;
    }

    public void setAdapter(ArrayList<Data_joblist> joblistDataset) {
        this.joblistDataset = joblistDataset;
        Log.d("!!!setAdapter()","this.joblistDataset : " + this.joblistDataset);
    }

    @NonNull
    @Override
    public Adapter_joblist.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_rv_joblist, parent, false) ;
        Adapter_joblist.ViewHolder vh = new Adapter_joblist.ViewHolder(view) ;
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_joblist.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();

        holder.tv_title.setText(joblistDataset.get(position).getTitle());
        holder.tv_cropinfo.setText(joblistDataset.get(position).getCropinfo());
        holder.tv_address.setText(joblistDataset.get(position).getAddress());
        holder.tv_pay.setText(joblistDataset.get(position).getPay());
        Log.d("!!!onBindViewHolder","position : " + position);

        //이미지 뷰에 uri 로드
        if (joblistDataset.get(position).getPhoto() != null)
        {
            uri = getUriFromPath(context, joblistDataset.get(position).getPhoto());
            Log.d("!!!path값","joinlist.get(position).getPhoto() : " +  joblistDataset.get(position).getPhoto());
            Log.d("!!!이미지 뷰에 uri 로드","uri : " +  uri);
            holder.iv_photo.setImageURI(uri);
        }


        //상세보기(ViewActivity)로 이동
        holder.but_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ApplyViewActivity.class);

                intent.putExtra("ID", joblistDataset.get(position).getID());
                intent.putExtra("title", joblistDataset.get(position).getTitle());
                intent.putExtra("cropinfo", joblistDataset.get(position).getCropinfo());
                intent.putExtra("datestart", joblistDataset.get(position).getDatestart());
                intent.putExtra("dateend", joblistDataset.get(position).getDateend());
                intent.putExtra("pay", joblistDataset.get(position).getPay());
                intent.putExtra("person", joblistDataset.get(position).getPerson());
                intent.putExtra("content", joblistDataset.get(position).getContent());
                //photo
                intent.putExtra("uri", uri);
                //num
                intent.putExtra("num", joblistDataset.get(position).getNum());
                //난수
                intent.putExtra("randomNum", joblistDataset.get(position).getRandomNum());
                //map
                intent.putExtra("address", joblistDataset.get(position).getAddress());
                intent.putExtra("Latitude", joblistDataset.get(position).getLatitude());
                intent.putExtra("Longitude", joblistDataset.get(position).getLongitude());
                Log.d("TAG","map Intent : " +  joblistDataset.get(position).getAddress() + " / " + joblistDataset.get(position).getLatitude() + " / " + joblistDataset.get(position).getLongitude());

                intent.putExtra("position", position);

                v.getContext().startActivity(intent);
                Toast.makeText(v.getContext(),"상세보기로 이동합니다", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("!!!getItemCount()","joblistDataset : " + joblistDataset);
        if(joblistDataset != null)
        {
            return joblistDataset.size() ;
        }
        else
        {
            return 0 ;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_title;
        public TextView tv_cropinfo;
        public TextView tv_address;
        public TextView tv_pay;
        public ImageView iv_photo;

        public Button but_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_cropinfo = itemView.findViewById(R.id.tv_cropinfo);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_pay = itemView.findViewById(R.id.tv_pay);
            iv_photo = itemView.findViewById(R.id.iv_photo);

            but_view = itemView.findViewById(R.id.but_view);

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
