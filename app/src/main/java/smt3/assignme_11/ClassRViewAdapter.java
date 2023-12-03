package smt3.assignme_11;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import smt3.assignme_11.class_detail.CLass_Detail;


public class ClassRViewAdapter extends RecyclerView.Adapter<ClassRViewAdapter.ViewHolder>{
    private static final String TAG = "ClassRViewAdapter";

    private ArrayList<Kelas>kelas=new ArrayList<>();
    private Context mContext;

    public ClassRViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_class,parent,false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        Kelas currentKelas = kelas.get(position);

//        holder.txtNamakelas.setText(currentKelas.getNama_kelas());
//        holder.txtNamaMapel.setText(currentKelas.getNama_mapel());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, currentKelas.getId_kelas() + " Selected", Toast.LENGTH_SHORT).show();

                FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Intent intent = new Intent(mContext, CLass_Detail.class);
                intent.putExtra("ClassId", currentKelas.getId_kelas());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kelas.size();
    }

    public void setKelas(ArrayList<Kelas> kelas) {
        this.kelas = kelas;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private TextView txtNamaMapel,txtNamakelas;

         public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent=itemView.findViewById(R.id.parent1);
            txtNamaMapel=itemView.findViewById(R.id.txtNamaMapel1);
            txtNamakelas=itemView.findViewById(R.id.txtNamaKelas1);





         }
    }
}
