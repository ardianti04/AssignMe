package smt3.assignme_11.task_detail;

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
import smt3.assignme_11.class_detail.Tugas;


public class TaskAttachmentRecViewAdapter extends RecyclerView.Adapter<TaskAttachmentRecViewAdapter.ViewHolder>{
    private static final String TAG = "TaskAttachmentRecViewAdapter";
    private ArrayList<Tugas> tugas=new ArrayList<>();
    private Context mContext;

    public TaskAttachmentRecViewAdapter(Context mContext) {
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
        Tugas currentTugas = tugas.get(position);

        holder.txtNamaAttachment.setText(currentTugas.getAttachment());

        if (mContext instanceof Detail_Tugas) {
            // Tambahkan logika tampilan attachment di sini sesuai dengan desain yang diinginkan
            String attachment = currentTugas.getAttachment();
            // Tampilkan attachment sesuai kebutuhan
        }
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, tugas.get(holder.getBindingAdapterPosition()).getAttachment()+
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
        return tugas.size();
    }

    public void setTugas(ArrayList<Tugas> tugas) {
        this.tugas = tugas;
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