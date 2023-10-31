package smt3.assignme_11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class All_Task_RecView extends AppCompatActivity {
    private RecyclerView taskRecView;
    private TaskRecViewAdapter adapter;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_task_rec_view);

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