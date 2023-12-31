package smt3.assignme_11.timeline;

import android.content.Context;
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
import smt3.assignme_11.class_detail.TaskRecViewAdapter;
import smt3.assignme_11.class_detail.Tugas;

public class TodoRecViewAdapter extends RecyclerView.Adapter<TodoRecViewAdapter.ViewHolder>{
    private static final String TAG = "TodoRecViewAdapter";
    private ArrayList<Tugas> tugas=new ArrayList<>();
    private Context mContext;

    public TodoRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TodoRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_todo,parent,false);
        return new TodoRecViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoRecViewAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.txtDeskripsiTugas.setText(tugas.get(holder.getBindingAdapterPosition()).getDeskripsi_tugas());
        holder.txtNamaMapel.setText(tugas.get(holder.getBindingAdapterPosition()).getNama_mapel());
        holder.txtTglBerakhir.setText(tugas.get(holder.getBindingAdapterPosition()).getTgl_berakhir());



        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, tugas.get(holder.getBindingAdapterPosition()).getId_Tugas()+" Selected", Toast.LENGTH_SHORT).show();


//                Intent intent = new Intent(mContext, Detail_Tugas.class);
//                mContext.startActivity(intent);
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
        private ImageView iconAssignment,imgEditProfile;
        private TextView txtNamaMapel,txtDeskripsiTugas,txtTglBerakhir;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent=itemView.findViewById(R.id.parent);
            iconAssignment=itemView.findViewById(R.id.iconAssignment);
            txtNamaMapel=itemView.findViewById(R.id.txtNamaMapel);
            txtDeskripsiTugas=itemView.findViewById(R.id.txtDeskripsiTugas);
            txtTglBerakhir=itemView.findViewById(R.id.txtTglBerakhir);
            imgEditProfile=itemView.findViewById(R.id.imgEditProfile);
        }
    }
}