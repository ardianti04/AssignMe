package smt3.assignme_11.class_detail;

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

public class PeopleRecViewAdapter extends RecyclerView.Adapter<PeopleRecViewAdapter.ViewHolder>{
    private static final String TAG = "PeopleRecViewAdapter";
    private ArrayList<User> users=new ArrayList<>();
    private Context mContext;

    public PeopleRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PeopleRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_people,parent,false);
        return new PeopleRecViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleRecViewAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.txtUsername.setText(users.get(holder.getBindingAdapterPosition()).getUsername());


        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, users.get(holder.getBindingAdapterPosition()).getUsername()+" Selected", Toast.LENGTH_SHORT).show();


//                Intent intent = new Intent(mContext, Detail_Tugas.class);
//                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CardView parent;
        private ImageView imageUser;
        private TextView txtUsername;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent=itemView.findViewById(R.id.parent);
            imageUser=itemView.findViewById(R.id.imageUser);
            txtUsername=itemView.findViewById(R.id.txtUsername);
        }
    }
}
