package smt3.assignme_11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

public class All_Material extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_material);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    Intent intent=new Intent(All_Material.this,All_Class_Work.class);
                    startActivity(intent);
                } else if (tab.getPosition()==1) {
                    Intent intent=new Intent(All_Material.this,All_Material.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                if ((item.getItemId()==R.id.botton_Home)){
//                    Intent homeIntent=new Intent(All_Material.this,All_class_RecView.class);
//                    startActivity(homeIntent);
//                }else if(item.getItemId()==R.id.botton_Timeline){
//                    Intent timelineIntent=new Intent(All_Material.this, All_Task_RecView.class);
//                    startActivity(timelineIntent);
//                } else if (item.getItemId()==R.id.botton_achives) {
//                    Intent timelineIntent=new Intent(All_Material.this, Archive.class);
//                    startActivity(timelineIntent);
//                } else if (item.getItemId()==R.id.botton_Pengaturan) {
//                    Intent timelineIntent=new Intent(All_Material.this, Settings.class);
//                    startActivity(timelineIntent);
//                }
//                return false;
//            }
//        });
    }
}