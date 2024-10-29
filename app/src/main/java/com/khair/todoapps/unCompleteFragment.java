package com.khair.todoapps;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class unCompleteFragment extends Fragment {

 RecyclerView recyclerView;
    FloatingActionButton fb_button;
    SqLiteDatabase dbHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View myView=inflater.inflate(R.layout.fragment_un_complete, container, false);
        if (container!=null){
            container.removeAllViews();
        }

        recyclerView =myView.findViewById(R.id.recycler_view);
        fb_button=myView.findViewById(R.id.fb_button);
        dbHelper=new SqLiteDatabase(getContext());
        MyItem myItem=new UnCompleteData(getContext());
        myItem.LodeData();





        // Initialize RecyclerView
        UnCompleteMyAdapter adapter=new UnCompleteMyAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));





        fb_button.setOnClickListener(v -> {
            showCustomDialog();


        });








        return myView;
    }
  ///=====================================================================================
  /// *************************************************************************************
  /// =====================================================================================
  ///

//=================================



  ///
///===========================================================================================

  /// =============================================================================================
  private void showCustomDialog() {

      AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

      LayoutInflater inflater = this.getLayoutInflater();
      View dialogView = inflater.inflate(R.layout.add_data_dolige, null);
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
                  MyItem myItem=new UnCompleteData(getContext());

                      dbHelper.Task_unCompleteData(inputText,inputText1);
                      myItem.LodeData();

                  dialog.dismiss(); // Close the dialog
              } else {
                  dialogEditText.setError("Please enter a value");
              }
          }
      });
  }

///===========================================================================================

//=========================================================



///===================================

}