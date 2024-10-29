package com.khair.todoapps;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class CompleteLodData extends MyItem{
Context context;
    ArrayList<HashMap<String,String>> itemList=new ArrayList<>();
    HashMap<String,String>hashMap;
    SqLiteDatabase dbHelper;
    public CompleteLodData(Context context) {
        this.context=context;
    }


    @Override
    public void LodeData() {
        itemList=new ArrayList<>();
        dbHelper=new SqLiteDatabase(context);

        Cursor cursor = dbHelper.getTask_Complete();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Get the 'type' column (which is created by the SQL query)
                String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("item_type"));
                String task = cursor.getString(cursor.getColumnIndexOrThrow("task"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("reason"));
                long time = cursor.getLong(cursor.getColumnIndexOrThrow("time"));
                hashMap=new HashMap<>();
                hashMap.put("id",id);
                hashMap.put("type",type);
                hashMap.put("task",task);
                hashMap.put("description",description);
                hashMap.put("time", String.valueOf(time));
                itemList.add(hashMap);

                //Now you can use this data to update your UI, RecyclerView, etc.
                Log.d("Data", "Type: " + type + ", Amount: " + task + ", Reason: " + description + ", Time: " + time);

            } while (cursor.moveToNext());
        }
        CompleteMyAdapter.CompleteList=itemList;



        cursor.close();

    }
}