package com.example.v3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Adapter_applyjob extends RecyclerView.Adapter<Adapter_applyjob.ViewHolder>{


    //apply어댑터에서 쓰는 array
    //!!프로필 Data_JobSeekerjoin로 해야할지 / 작업정보 들어있는 Data_joblist로 해야할지 헷갈리네
    private ArrayList<Data_JobSeekerjoin> applyDataset;


    public Adapter_applyjob(ArrayList<Data_JobSeekerjoin> applyDataset) {
        this.applyDataset = applyDataset;
    }

    public void setAdapter(ArrayList<Data_JobSeekerjoin> applyDataset) {
        this.applyDataset = applyDataset;
        Log.d("TAG","setAdapter_this.applyDataset : " + this.applyDataset);
    }

    @NonNull
    @Override
    public Adapter_applyjob.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.item_rv_apply, parent, false) ;
        Adapter_applyjob.ViewHolder vh = new Adapter_applyjob.ViewHolder(view) ;
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter_applyjob.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();

        holder.tv_title.setText(applyDataset.get(position).getTitle());
        holder.tv_place.setText(applyDataset.get(position).getAddress());
        holder.tv_pay.setText(applyDataset.get(position).getPay());
        holder.but_status.setText(applyDataset.get(position).getStatus());


    }

    @Override
    public int getItemCount() {
        Log.d("TAG","getItemCount()_applyDataset : " + applyDataset);
        if(applyDataset != null)
        {
            return applyDataset.size() ;
        }
        else
        {
            return 0 ;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //지원한 일감 title
        public TextView tv_title;
        //지원한 일감 주소
        public TextView tv_place;
        //지원한 일감 임금
        public TextView tv_pay;
        //상태표시 버튼
        public Button but_status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_place = itemView.findViewById(R.id.tv_place);
            tv_pay = itemView.findViewById(R.id.tv_pay);
            but_status = itemView.findViewById(R.id.but_status);

        }
    }



}
