package com.khair.todoapps;

import android.annotation.SuppressLint;
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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class CompleteFragment extends Fragment {
     RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View myView=inflater.inflate(R.layout.fragment_complete, container, false);
        if (container!=null){
            container.removeAllViews();
        }
        recyclerView =myView.findViewById(R.id.recycler_view);
        MyItem myItem=new CompleteLodData(getContext());
        myItem.LodeData();


        // Initialize RecyclerView
       CompleteMyAdapter adapter=new CompleteMyAdapter(getContext());
       recyclerView.setAdapter(adapter);
       recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));




        return myView;
    }
    //============================================================================

 //===================================================================
}