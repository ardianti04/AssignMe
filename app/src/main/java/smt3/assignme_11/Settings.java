package smt3.assignme_11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Settings extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if ((item.getItemId()==R.id.botton_Home)){
                    Intent homeIntent=new Intent(Settings.this,All_class_RecView.class);
                    startActivity(homeIntent);
                }else if(item.getItemId()==R.id.botton_Timeline){
                    Intent timelineIntent=new Intent(Settings.this, All_Task_RecView.class);
                    startActivity(timelineIntent);
                } else if (item.getItemId()==R.id.botton_achives) {
                    Intent timelineIntent=new Intent(Settings.this, Archive.class);
                    startActivity(timelineIntent);
                } else if (item.getItemId()==R.id.botton_Pengaturan) {
                    Intent timelineIntent=new Intent(Settings.this, Settings.class);
                    startActivity(timelineIntent);

                }
                return false;
            }
        });
    }
}