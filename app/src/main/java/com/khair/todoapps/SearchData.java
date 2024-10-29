package com.khair.todoapps;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchData extends MyItem{
    ArrayList<HashMap<String,String>>arrayList=new ArrayList<>();
    HashMap<String,String>hashMap;
    SqLiteDatabase dbHelper;
    Context context;
    String newText;

    public SearchData(Context context,String newText){
        this.newText=newText;
        this.context=context;
    }


    @Override
    public void LodeData() {

            arrayList=new ArrayList<>();
            dbHelper=new SqLiteDatabase(context);

            Cursor cursor = dbHelper.searchCompleteAndUnComplete(newText);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Get the 'type' column (which is created by the SQL query)
                    String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                    String type = cursor.getString(cursor.getColumnIndexOrThrow("item_type"));
                    String task = cursor.getString(cursor.getColumnIndexOrThrow("task"));
                    String reason = cursor.getString(cursor.getColumnIndexOrThrow("reason"));
                    long time = cursor.getLong(cursor.getColumnIndexOrThrow("time"));

                    hashMap=new HashMap<>();
                    hashMap.put("type",type);
                    hashMap.put("id", id);
                    hashMap.put("task", task);
                    hashMap.put("description",reason);
                    hashMap.put("time", String.valueOf(time));
                    arrayList.add(hashMap);

                    // Now you can use this data to update your UI, RecyclerView, etc.
                    Log.d("Data1", "Type: " + type + ", Amount: " + task + ", Reason: " + reason + ", Time: " + time);

                } while (cursor.moveToNext());

            }
            searchViewAdapter.arrayList=arrayList;

            cursor.close(); // Always close the cursor after use
        }

    }

