package smt3.assignme_11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class All_class_RecView extends AppCompatActivity {
    private RecyclerView classRecView;
    private ClassRViewAdapter adapter;
    private ImageButton btnTambah;
    private AlertDialog dialog;
    private Button btnCencel,btnJoin;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_class_rec_view);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if ((item.getItemId()==R.id.botton_Home)){
                    Intent homeIntent=new Intent(All_class_RecView.this,All_class_RecView.class);
                    startActivity(homeIntent);
                }else if(item.getItemId()==R.id.botton_Timeline){
                    Intent timelineIntent=new Intent(All_class_RecView.this, All_Task_RecView.class);
                    startActivity(timelineIntent);
                } else if (item.getItemId()==R.id.botton_achives) {
                    Intent timelineIntent=new Intent(All_class_RecView.this, Archive.class);
                    startActivity(timelineIntent);
                } else if (item.getItemId()==R.id.botton_Pengaturan) {

                }
                return false;
            }
        });


        btnTambah=findViewById(R.id.btnTambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });








        adapter=new ClassRViewAdapter(this);
        classRecView=findViewById(R.id.classRecView);

        classRecView.setAdapter(adapter);
        classRecView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Kelas> kelas = getClassData();
        adapter.setKelas(kelas);


    }

    ArrayList<Kelas> getClassData() {
        ArrayList<Kelas>kelas=new ArrayList<>();
        kelas.add(new Kelas(1,"Kelas XII E","E234","Citra Kirana","Matematika"
                ,"Kelas untuk siswa ini saja","https://image.gambarpng.id/pngs/gambar-transparent-perlengkapan-belajar-matematika_56394.png"));
        kelas.add(new Kelas(2,"Kelas XII A","A234","Lusiana","IPA"
                ,"Kelas untuk siswa ini saja","https://primaindisoft.com/wp-content/uploads/2019/09/ipa.png"));
        return kelas;
    }



}