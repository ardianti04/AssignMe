package smt3.assignme_11;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

public class JoinClassDialog {
    private Context context;

    public JoinClassDialog(Context context) {
        this.context = context;
    }
    public void showJoinClassDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.joinclass_dialog, null);
        builder.setView(dialogView);

        // Inisialisasi komponen dalam dialog
        Button btnJoin = dialogView.findViewById(R.id.btnJoin);
        Button btnCancel = dialogView.findViewById(R.id.btnCencel);

        AlertDialog dialog = builder.create();
        dialog.show();

        // Mengatur tindakan saat tombol "Join" diklik
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lakukan tindakan bergabung dengan kelas di sini
                dialog.dismiss(); // Tutup dialog setelah tindakan selesai
            }
        });

        // Mengatur tindakan saat tombol "Cancel" diklik
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Tutup dialog jika tombol "Cancel" diklik
            }
        });
    }

}

