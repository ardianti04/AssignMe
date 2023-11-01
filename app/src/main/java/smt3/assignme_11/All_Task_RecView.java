package smt3.assignme_11;

import static smt3.assignme_11.R.id.tabLayoutTimeline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class All_Task_RecView extends AppCompatActivity {
    private RecyclerView taskRecView;
    private TaskRecViewAdapter adapter;
    private BottomNavigationView bottomNavigationView;
    private TabLayout tabLayoutTimeline;
    private ViewPager2 viewPagerTimeline;
    private Button todo,completed,overdue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_task_rec_view);

        tabLayoutTimeline = findViewById(R.id.tabLayoutTimeline);
        viewPagerTimeline = findViewById(R.id.viewPagerTimeline);

        tabLayoutTimeline.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    Intent intent=new Intent(All_Task_RecView.this,All_Task_RecView.class);
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


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if ((item.getItemId()==R.id.botton_Home)){
                    Intent homeIntent=new Intent(All_Task_RecView.this,All_class_RecView.class);
                    startActivity(homeIntent);
                }else if(item.getItemId()==R.id.botton_Timeline){
                    Intent timelineIntent=new Intent(All_Task_RecView.this, All_Task_RecView.class);
                    startActivity(timelineIntent);
                } else if (item.getItemId()==R.id.botton_achives) {
                    Intent timelineIntent=new Intent(All_Task_RecView.this, Archive.class);
                    startActivity(timelineIntent);
                } else if (item.getItemId()==R.id.botton_Pengaturan) {

                }
                return false;
            }
        });

        adapter=new TaskRecViewAdapter(this);
        taskRecView=findViewById(R.id.taskRecView);

        taskRecView.setAdapter(adapter);
        taskRecView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Tugas>tugas  = getTaskData();
        adapter.setTugas(tugas);
        
    }

    private ArrayList<Tugas> getTaskData() {
        ArrayList<Tugas>tugas=new ArrayList<>();
        tugas.add(new Tugas(1,"Matematika","Tugas Bab 1"
        ,"Jun 25 2023","https://image.gambarpng.id/pngs/gambar-transparent-perlengkapan-belajar-matematika_56394.png"));
        tugas.add(new Tugas(2,"Bahasa Indonesia","Tugas Merangkum Bab 2"
                ,"29 Juni 2023","https://image.gambarpng.id/pngs/gambar-transparent-perlengkapan-belajar-matematika_56394.png"));

        return tugas;
    }
}