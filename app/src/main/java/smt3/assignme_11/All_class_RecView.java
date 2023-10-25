package smt3.assignme_11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class All_class_RecView extends AppCompatActivity {
    private RecyclerView classRecView;
    private ClassRViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_class_rec_view);

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