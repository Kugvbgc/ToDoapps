package com.khair.todoapps;

import static android.app.DownloadManager.COLUMN_DESCRIPTION;
import static android.app.DownloadManager.COLUMN_ID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.AccessControlContext;
import java.util.ArrayList;
import java.util.HashMap;


public class SqLiteDatabase extends SQLiteOpenHelper {


    public SqLiteDatabase( Context context) {
        super(context, "name", null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table task_unComplete(id INTEGER primary key autoincrement,item_type TEXT,task TEXT,reason TEXT,time DOUBLE)");
        db.execSQL("create table task_Complete(id INTEGER primary key autoincrement,item_type TEXT,task TEXT,reason TEXT,time DOUBLE)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists task_unComplete");
        db.execSQL("drop table if exists task_Complete");

    }
  ///============================================================
  ///
  /// ===============================================================
  ///
  public Cursor getTask_unComplete(){
      SQLiteDatabase db =this.getReadableDatabase();
      Cursor cursor= db.rawQuery("select * from task_unComplete",null);


      return cursor;
  }
    ///=======================================================================
    public Cursor getTask_Complete(){
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor cursor= db.rawQuery("select * from task_Complete",null);

        return cursor;
    }
   ///
   /// =================================================================================
   ///




///=========================================================================================

  public void Task_unCompleteData(String task,String reason){

      SQLiteDatabase db=this.getWritableDatabase();
      ContentValues values=new ContentValues();

      values.put("task",task);
      values.put("reason",reason);
      values.put("item_type","unComplete");
      values.put("time",System.currentTimeMillis());

      db.insert("task_unComplete",null,values);

  }
    public void Task_CompleteData(String task,String reason){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("task",task);
        values.put("reason",reason);
        values.put("item_type","Complete");
        values.put("time",System.currentTimeMillis());

        db.insert("task_Complete",null,values);

    }

    public void Delete_task_unComplete(String id){
        int myId=Integer.parseInt(id);
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from task_unComplete where id like'"+myId+"'");

    }
    public void Delete_taskComplete(String id){
        int myId=Integer.parseInt(id);
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from task_Complete where id like'"+myId+"'");


    }



    ///==============================================================
    public void cm_updateTaskQuery(int id, String task, String reason) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Construct the raw SQL query
        String query = "UPDATE task_Complete SET task =?, reason = ? WHERE ID = ?";

        // Execute the query using execSQL
        db.execSQL(query, new Object[]{task, reason, id});
    }

    ///=================================================================
    public void un_updateTaskQuery(int id, String task, String reason) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Construct the raw SQL query
        String query = "UPDATE task_unComplete SET task =?, reason = ? WHERE ID = ?";

        // Execute the query using execSQL
        db.execSQL(query, new Object[]{task, reason, id});
    }
    //========================================================
    public Cursor searchCompleteAndUnComplete(String searchQuery) {
        SQLiteDatabase db = this.getReadableDatabase();

        // SQL query with UNION to combine expense and income tables' results
        String query = "SELECT * FROM task_Complete WHERE task LIKE '%"+searchQuery+"%'" +
                " UNION SELECT * FROM task_unComplete WHERE task LIKE '%"+searchQuery+"%'";


        Cursor cursor = db.rawQuery(query, null);

        // Execute the query
        return cursor;
    }

 ///================================================================

}
