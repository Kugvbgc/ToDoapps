package com.khair.todoapps;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class CompleteMyAdapter extends RecyclerView.Adapter<CompleteMyAdapter.CompleteViewHolder> {
    public  static ArrayList<HashMap<String,String>>CompleteList=new ArrayList<>();
    Context context;
    public CompleteMyAdapter(Context context){
        this.context=context;

    }

    public class CompleteViewHolder extends RecyclerView.ViewHolder{
        TextView item_title,item_description,item_time;
        ImageView popup;

        public CompleteViewHolder(@NonNull View itemView) {
            super(itemView);
            item_title=itemView.findViewById(R.id.item_title);
            item_description=itemView.findViewById(R.id.item_description);
            item_time=itemView.findViewById(R.id.item_time);
            popup=itemView.findViewById(R.id.popup);
        }
    }

    @NonNull
    @Override
    public CompleteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_layout,parent,false);

        return new CompleteViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CompleteViewHolder holder, @SuppressLint("RecyclerView") int position) {
       HashMap<String,String> hashMap=CompleteList.get(position);
        String id=hashMap.get("id");
        String type=hashMap.get("type");
        String task=hashMap.get("task");
        String description=hashMap.get("description");
        String time=hashMap.get("time");
        long timeAsLong = Long.parseLong(time);
        Date date = new Date(timeAsLong);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:a", Locale.getDefault());
        //dd-MM-yyyy

        holder.item_title.setText(task);
        holder.item_description.setText(description);
        holder.item_time.setText(sdf.format(date));
        holder.popup.setOnClickListener(v -> {
            // Create a PopupMenu
            PopupMenu popupMenu = new PopupMenu(context, holder.itemView);
            // Inflate the menu from XML resource
            popupMenu.getMenuInflater().inflate(R.menu.cmpopup, popupMenu.getMenu());

            try {
                Field popup = PopupMenu.class.getDeclaredField("mPopup");
                popup.setAccessible(true);
                Object menuPopupHelper = popup.get(popupMenu);
                Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                Field fieldPopupStyle = classPopupHelper.getDeclaredField("mPopup");
                fieldPopupStyle.setAccessible(true);
                Object menuPopupWindow = fieldPopupStyle.get(menuPopupHelper);
                Class<?> popupWindowClass = menuPopupWindow.getClass();
                popupWindowClass.getMethod("setEnterTransition", Object.class).invoke(menuPopupWindow, null);
                popupWindowClass.getMethod("setExitTransition", Object.class).invoke(menuPopupWindow, null);
            } catch (Exception e) {
                e.printStackTrace();
            }


            // Set a click listener for menu item clicks
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    // Handle the menu item clicks
                    SqLiteDatabase dbHelper=new SqLiteDatabase(context);

                    if (item.getItemId() == R.id.UnComplete) {
                        dbHelper.Task_unCompleteData(task,description);
                        dbHelper.Delete_taskComplete(id);
                        CompleteList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, CompleteList.size());

                    } else if (item.getItemId() == R.id.Delete) {
                        dbHelper.Delete_taskComplete(id);
                        CompleteList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, CompleteList.size());

                    }


                    return false;
                }
            });

            // Show the popup menu
            popupMenu.show();
        });


    }

    @Override
    public int getItemCount() {
        return CompleteList.size();
    }
}
