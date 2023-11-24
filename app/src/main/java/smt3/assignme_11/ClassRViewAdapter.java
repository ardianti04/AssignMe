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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: Called");
        holder.txtNamaKelas.setText(kelas.get(holder.getBindingAdapterPosition()).getNama_kelas());
        holder.txtNamaMapel.setText(kelas.get(holder.getBindingAdapterPosition()).getNama_mapel());



        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, kelas.get(holder.getBindingAdapterPosition()).getNama_kelas()+" Selected", Toast.LENGTH_SHORT).show();

                FragmentManager fragmentManager = ((AppCompatActivity) mContext).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                Intent intent = new Intent(mContext, CLass_Detail.class);
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
        private ImageView imgKelas;
        private TextView txtNamaMapel,txtNamaKelas;

         public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent=itemView.findViewById(R.id.parent);
            imgKelas=itemView.findViewById(R.id.imgKelas);
            txtNamaMapel=itemView.findViewById(R.id.txtNamaMapel);
            txtNamaKelas=itemView.findViewById(R.id.txtNamaKelas);






         }
    }
}
