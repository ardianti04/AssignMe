package smt3.assignme_11.task_detail;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
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


public class UploadAttachmentRecViewAdapter extends RecyclerView.Adapter<UploadAttachmentRecViewAdapter.ViewHolder>{
    private static final String TAG = "TaskAttachmentRecViewAdapter";
    private ArrayList<Tugas> tugas=new ArrayList<>();
    private Context mContext;

    public UploadAttachmentRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_attachment_student,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        Tugas currentTugas = tugas.get(position);

        holder.txtNamaAttachment.setText(getFileName(currentTugas.getAttachment()));

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
        holder.iconDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the position of the item that needs to be removed
                int adapterPosition = holder.getAdapterPosition();

                // Check if the position is valid and within the list size
                if (adapterPosition != RecyclerView.NO_POSITION && adapterPosition < tugas.size()) {
                    // Show a confirmation dialog before deletion
                    showDeleteConfirmationDialog(adapterPosition);
                }
            }
        });
    }

    private String getFileName(String attachmentUri) {
        String fileName = "";
        if (attachmentUri != null) {
            Uri uri = Uri.parse(attachmentUri);
            // Mendapatkan nama file dari URI menggunakan split
            String[] pathSegments = uri.getPath().split("/");
            fileName = pathSegments[pathSegments.length - 1];
        }
        return fileName;
    }
    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Delete Attachment");
        builder.setMessage("Are you sure you want to delete this attachment?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Remove the item from the list
                tugas.remove(position);

                // Notify the adapter that an item has been removed
                notifyItemRemoved(position);
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return tugas.size();
    }

    public void setAttachment(ArrayList<Tugas> tugas) {
        this.tugas = tugas;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CardView parent;
        private ImageView iconAttachment;
        private TextView txtNamaAttachment;
        private ImageView iconDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent=itemView.findViewById(R.id.parent);
            txtNamaAttachment=itemView.findViewById(R.id.txtAttachment);
            iconAttachment=itemView.findViewById(R.id.iconAttachment);
            iconDelete=itemView.findViewById(R.id.imgDelete);
        }
    }
}