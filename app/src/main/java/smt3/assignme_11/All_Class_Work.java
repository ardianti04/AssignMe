package smt3.assignme_11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;

public class All_Class_Work extends AppCompatActivity {

    private RecyclerView taskRecView;
    private TaskRecViewAdapter adapter;

    private BottomNavigationView bottomNavigationView;

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_class_work);

        int pressedColor = ContextCompat.getColor(this, R.color.black_900_7f);
        ImageView btnBack = findViewById(R.id.backButtonImageLogin);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBack.setColorFilter(pressedColor);
                Intent intent = new Intent(All_Class_Work.this, Main_Activity.class);
                startActivity(intent);
            }
        });



        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                /*if(tab.getPosition()==0){
                    Intent intent=new Intent(All_Class_Work.this,All_Class_Work.class);
                    startActivity(intent);
                }*/
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /*bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if ((item.getItemId()==R.id.buttonHome)){
                    Intent homeIntent=new Intent(All_Class_Work.this,All_class_RecView.class);
                    startActivity(homeIntent);
                }else if(item.getItemId()==R.id.buttonTimeline){
                    Intent timelineIntent=new Intent(All_Class_Work.this, All_Task_RecView.class);
                    startActivity(timelineIntent);
                } else if (item.getItemId()==R.id.buttonArchives) {
                    Intent timelineIntent=new Intent(All_Class_Work.this, Archive.class);
                    startActivity(timelineIntent);
                } else if (item.getItemId()==R.id.buttonPengaturan) {
                    Intent timelineIntent=new Intent(All_Class_Work.this, Settings.class);
                    startActivity(timelineIntent);
                }
                return false;
            }
        });*/
        adapter=new TaskRecViewAdapter(this);
        taskRecView=findViewById(R.id.taskRecView);

        taskRecView.setAdapter(adapter);
        taskRecView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Tugas> tugas  = getTaskData();
        adapter.setTugas(tugas);

    }

    private ArrayList<Tugas> getTaskData() {
        ArrayList<Tugas>tugas=new ArrayList<>();
        tugas.add(new Tugas(1,"Aljabar","Tugas Merangkum Bab 1"
                ,"Jun 25 2023","https://image.gambarpng.id/pngs/gambar-transparent-perlengkapan-belajar-matematika_56394.png"));
        tugas.add(new Tugas(2,"Algebra","Tugas Merangkum Bab 2"
                ,"29 Juni 2023","https://image.gambarpng.id/pngs/gambar-transparent-perlengkapan-belajar-matematika_56394.png"));

        return tugas;
    }
}