package com.khair.todoapps;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {

    MaterialToolbar Toolbar;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar=findViewById(R.id.Toolbar);
        tabLayout=findViewById(R.id.TabLayout);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.FrameLayout,new unCompleteFragment());
        transaction.commit();

        tabLayout.addTab(tabLayout.newTab().setText("unComplete"));
        tabLayout.addTab(tabLayout.newTab().setText("Complete"));




        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction transaction=fragmentManager.beginTransaction();
                    transaction.add(R.id.FrameLayout,new unCompleteFragment());
                    transaction.commit();


                }else if (tab.getPosition()==1){
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction transaction=fragmentManager.beginTransaction();
                    transaction.add(R.id.FrameLayout,new CompleteFragment());
                    transaction.commit();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {



            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId()==R.id.search){
                    startActivity(new Intent(HomeActivity.this, searchActivity.class));

                }



                return true;
            }
        });




    }
    ///===========================================================================

 ///==========================================================================
}