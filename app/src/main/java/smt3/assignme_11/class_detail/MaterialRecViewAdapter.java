package smt3.assignme_11.class_detail;

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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import smt3.assignme_11.R;
import smt3.assignme_11.material_detail.Detail_Material;

public class MaterialRecViewAdapter extends RecyclerView.Adapter<MaterialRecViewAdapter.ViewHolder>{
    private static final String TAG = "MaterialRecViewAdapter";
    private ArrayList<Materi> materi=new ArrayList<>();
    private Context mContext;

    public MaterialRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MaterialRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_material,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialRecViewAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        Materi currentMateri = materi.get(position);

        holder.txtNamaMateri.setText(materi.get(holder.getBindingAdapterPosition()).getNama_Materi());
        holder.txtTglUpload.setText(materi.get(holder.getBindingAdapterPosition()).getTanggal_upload());



        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, materi.get(holder.getBindingAdapterPosition()).getId_Materi()+
                        " Selected", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(mContext, Detail_Material.class);
                intent.putExtra("ClassId", currentMateri.getClassId());
                intent.putExtra("MaterialId", currentMateri.getId_Materi());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return materi.size();
    }

    public void setMaterial(ArrayList<Materi> materis) {
        this.materi = materis;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CardView parent;
        private ImageView imgMateri;
        private TextView txtNamaMateri,txtTglUpload;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent=itemView.findViewById(R.id.parent);
            imgMateri=itemView.findViewById(R.id.imgMateri);
            txtNamaMateri=itemView.findViewById(R.id.txtNamaMateri);
            txtTglUpload=itemView.findViewById(R.id.txtTglUpload);
        }
    }
}
