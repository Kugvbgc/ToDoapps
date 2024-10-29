package com.khair.todoapps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class searchViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static ArrayList<HashMap<String,String>>arrayList=new ArrayList<>();
    HashMap<String,String>hashMap;

    int Complete=1;
    int UnComplete=2;


    public class UnCompleteViewHolder extends RecyclerView.ViewHolder{
        TextView item_title1,item_description1,item_time1;

        public UnCompleteViewHolder(@NonNull View itemView) {
            super(itemView);
            item_title1=itemView.findViewById(R.id.item_title);
            item_description1=itemView.findViewById(R.id.item_description);
            item_time1=itemView.findViewById(R.id.item_time);
        }
    }

    public class CompleteViewHolder extends RecyclerView.ViewHolder{
        TextView item_title,item_description,item_time;


        public CompleteViewHolder(@NonNull View itemView) {
            super(itemView);
            item_title=itemView.findViewById(R.id.item_title);
            item_description=itemView.findViewById(R.id.item_description);
            item_time=itemView.findViewById(R.id.item_time);
        }
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        if (viewType==UnComplete){
            View UnCompleteView=inflater.inflate(R.layout.item_layout,parent,false);
            return new UnCompleteViewHolder(UnCompleteView);
        }else {
            View CompleteView=inflater.inflate(R.layout.item_layout,parent,false);
            return new CompleteViewHolder(CompleteView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==UnComplete){
            UnCompleteViewHolder unCompleteViewHolder= (UnCompleteViewHolder) holder;
            hashMap=arrayList.get(position);
            String id=hashMap.get("id");
            String type=hashMap.get("type");
            String task=hashMap.get("task");
            String description=hashMap.get("description");
            String time=hashMap.get("time");
            long timeAsLong = Long.parseLong(time);
            Date date = new Date(timeAsLong);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:a", Locale.getDefault());
            unCompleteViewHolder.item_title1.setText(task);
            unCompleteViewHolder.item_description1.setText(description);
            unCompleteViewHolder.item_time1.setText(sdf.format(date));


        }else if (getItemViewType(position)==Complete){
            CompleteViewHolder completeViewHolder= (CompleteViewHolder) holder;
            hashMap=arrayList.get(position);
            String id=hashMap.get("id");
            String type=hashMap.get("type");
            String task=hashMap.get("task");
            String description=hashMap.get("description");
            String time=hashMap.get("time");
            long timeAsLong = Long.parseLong(time);
            Date date = new Date(timeAsLong);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:a", Locale.getDefault());
            completeViewHolder.item_title.setText(task);
            completeViewHolder.item_description.setText(description);
            completeViewHolder.item_time.setText(sdf.format(date));


        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        hashMap=arrayList.get(position);
        String item_type=hashMap.get("type");
        if (item_type.contains("unComplete"))return UnComplete;
        else return Complete;

    }
}