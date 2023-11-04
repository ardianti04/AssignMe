package smt3.assignme_11

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class JoinClassActivity : AppCompatActivity() {
    private lateinit var btnJoin : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_class)

        // Inisialisasi komponen
        val pressedColor = ContextCompat.getColor(this, R.color.black_900_7f)
        val btnBack = findViewById<ImageView>(R.id.backButtonJoinClass)
        btnBack.setOnClickListener(){
            btnBack.setColorFilter(pressedColor)
            val intent = Intent( this@JoinClassActivity, Main_Activity::class.java)
            startActivity(intent)
        }


        // Mengatur tindakan saat tombol "Join" diklik
        btnJoin = findViewById(R.id.btnJoin);
        btnJoin.setOnClickListener { // Arahkan pengguna ke halaman login (SignInActivity)
            val intent = Intent(this@JoinClassActivity, Main_Activity::class.java)
            startActivity(intent)
        }
            // Lakukan tindakan bergabung dengan kelas di sini
            //dialog.dismiss() // Tutup dialog setelah tindakan selesai
        }

        // Mengatur tindakan saat tombol "Cancel" diklik
        //btnBack.setOnClickListener {
            //dialog.dismiss() // Tutup dialog jika tombol "Cancel" diklik
        }

