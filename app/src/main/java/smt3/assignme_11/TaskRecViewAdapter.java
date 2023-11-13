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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;



public class TaskRecViewAdapter extends RecyclerView.Adapter<TaskRecViewAdapter.ViewHolder>{
    private static final String TAG = "TaskRecViewAdapter";
    private ArrayList<Tugas> tugas=new ArrayList<>();
    private Context mContext;

    public TaskRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_task,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.txtDeskripsiTugas.setText(tugas.get(holder.getBindingAdapterPosition()).getDeskripsi_tugas());
        holder.txtNamaMapel.setText(tugas.get(holder.getBindingAdapterPosition()).getNama_mapel());
        Glide.with(mContext)
                .asBitmap()
                .load(tugas.get(holder.getBindingAdapterPosition()).getImageUrl())
                .into(holder.imgKelas);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, tugas.get(holder.getBindingAdapterPosition()).getNama_mapel()+" Selected", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(mContext, Detail_Tugas.class);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tugas.size();
    }

    public void setTugas(ArrayList<Tugas> tugas) {
        this.tugas = tugas;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private ImageView imgKelas,imgTglBerakhir;
        private TextView txtNamaMapel,txtDeskripsiTugas,txtTglBerakhir;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent=itemView.findViewById(R.id.parent);
            imgKelas=itemView.findViewById(R.id.imgKelas);
            imgTglBerakhir=itemView.findViewById(R.id.imgEditProfile);
            txtNamaMapel=itemView.findViewById(R.id.txtNamaMapel);
            txtDeskripsiTugas=itemView.findViewById(R.id.txtDeskrisiTugas);
            txtTglBerakhir=itemView.findViewById(R.id.txtTglBerakhir);
        }
    }
}
