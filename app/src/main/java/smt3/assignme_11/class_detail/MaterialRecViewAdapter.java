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

import smt3.assignme_11.Detail_Tugas;
import smt3.assignme_11.Detailed_Material;
import smt3.assignme_11.R;

public class MaterialRecViewAdapter extends RecyclerView.Adapter<MaterialRecViewAdapter.ViewHolder>{
    private static final String TAG = "MaterialRecViewAdapter";
    private ArrayList<Materi> materis=new ArrayList<>();
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
        holder.txtNamaMateri.setText(materis.get(holder.getBindingAdapterPosition()).getNama_Materi());
        holder.txtTglUpload.setText(materis.get(holder.getBindingAdapterPosition()).getTanggal_upload());



        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, materis.get(holder.getBindingAdapterPosition()).getNama_Materi()+" Selected", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(mContext, Detailed_Material.class);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return materis.size();
    }

    public void setMaterial(ArrayList<Materi> materis) {
        this.materis = materis;
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
