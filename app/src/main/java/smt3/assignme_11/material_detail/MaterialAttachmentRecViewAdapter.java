package smt3.assignme_11.material_detail;

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
import smt3.assignme_11.class_detail.Materi;
import smt3.assignme_11.class_detail.Tugas;
import smt3.assignme_11.task_detail.Detail_Tugas;


public class MaterialAttachmentRecViewAdapter extends RecyclerView.Adapter<MaterialAttachmentRecViewAdapter.ViewHolder>{
    private static final String TAG = "MaterialAttachmentRecViewAdapter";
    private ArrayList<Materi> materi=new ArrayList<>();
    private Context mContext;

    public MaterialAttachmentRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_attachment,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        Materi currentMateri = materi.get(position);

        holder.txtNamaAttachment.setText(currentMateri.getAttachment());

        if (mContext instanceof Detail_Tugas) {
            // Tambahkan logika tampilan attachment di sini sesuai dengan desain yang diinginkan
            String attachment = currentMateri.getAttachment();
            // Tampilkan attachment sesuai kebutuhan
        }
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, materi.get(holder.getBindingAdapterPosition()).getAttachment()+
                        " Selected", Toast.LENGTH_SHORT).show();

                //Intent intent = new Intent(mContext, Detail_Tugas.class);
                /*intent.putExtra("ClassId", currentTugas.getClassId());
                intent.putExtra("Attachment", currentTugas.getAttachment());
                intent.putExtra("TaskId", currentTugas.getId_Tugas());*/
                //mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return materi.size();
    }

    public void setMaterial(ArrayList<Materi> materi) {
        this.materi = materi;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CardView parent;
        private ImageView iconAttachment;
        private TextView txtNamaAttachment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent=itemView.findViewById(R.id.parent);
            txtNamaAttachment=itemView.findViewById(R.id.txtAttachment);
            iconAttachment=itemView.findViewById(R.id.iconAttachment);
        }
    }
}