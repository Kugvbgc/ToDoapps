package com.khair.todoapps;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class UnCompleteMyAdapter extends RecyclerView.Adapter<UnCompleteMyAdapter.UnCompleteViewHolder> {
    Context context;
   public static ArrayList<HashMap<String,String>> itemList=new ArrayList<>();
    HashMap<String,String>hashMap;
    public UnCompleteMyAdapter(Context context){
        this.context=context;
    }

    public class UnCompleteViewHolder extends RecyclerView.ViewHolder{
        TextView item_title,item_description,item_time;
        ImageView popup;

        public UnCompleteViewHolder(@NonNull View itemView) {
            super(itemView);
            item_title=itemView.findViewById(R.id.item_title);
            item_description=itemView.findViewById(R.id.item_description);
            item_time=itemView.findViewById(R.id.item_time);
            popup=itemView.findViewById(R.id.popup);
        }
    }

    @NonNull
    @Override
    public UnCompleteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_layout,parent,false);
        return new UnCompleteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnCompleteViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SqLiteDatabase dbHelper=new SqLiteDatabase(context);
        hashMap=itemList.get(position);
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
            popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());

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

                    if (item.getItemId() == R.id.Edit) {
                        UpdateDialog(id);


                    } else if (item.getItemId() == R.id.Complete) {
                        dbHelper.Task_CompleteData(task,description);
                        dbHelper.Delete_task_unComplete(id);
                        itemList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, itemList.size());

                    } else if (item.getItemId() == R.id.Delete) {
                        dbHelper.Delete_task_unComplete(id);
                        itemList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, itemList.size());

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
        return itemList.size();
    }

    public void UpdateDialog(String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater =LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.updatedata, null);
        builder.setView(dialogView);
        TextInputEditText dialogEditText = dialogView.findViewById(R.id.dialogEditText);
        TextInputEditText dialogEditText1 = dialogView.findViewById(R.id.dialogEditText1);
        Button dialogButton = dialogView.findViewById(R.id.dialogButton);
        final AlertDialog dialog = builder.create();
        dialog.show();

        // Set the button click listener
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click
                String inputText = dialogEditText.getText().toString();
                String inputText1 = dialogEditText1.getText().toString();
                if (!inputText.isEmpty()&&!inputText1.isEmpty()) {
                    SqLiteDatabase dbHelper=new SqLiteDatabase(context);
                    MyItem myItem=new UnCompleteData(context);

                    dbHelper.un_updateTaskQuery(Integer.parseInt(id),inputText,inputText1);
                    myItem.LodeData();

                    dialog.dismiss(); // Close the dialog
                } else {
                    dialogEditText.setError("Please enter a value");
                }
            }
        });
    }
    //==========================================================
}

