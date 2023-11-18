package smt3.assignme_11

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Button
import android.widget.LinearLayout
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import androidx.core.content.ContextCompat
import android.view.WindowManager
import android.view.Gravity

class FP_4_status : AppCompatActivity() {
    private lateinit var btnBackToLogin : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fp4_status)

        //Reset button func
        btnBackToLogin = findViewById(R.id.btnBackLogin);
        btnBackToLogin.setOnClickListener { // Arahkan pengguna ke halaman login (SignInActivity)
            val intent = Intent(this@FP_4_status, SignInActivity::class.java)
            startActivity(intent)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            finish()
        }
    }

    override fun onBackPressed() {
        // Biarkan default behavior onBackpressed terjadi
        // Hilangkan pemanggilan super.onBackPressed() atau navigasi kembali di sini
        // Jika ingin membatasi perilaku kembali, Anda bisa menambahkan logika di sini
    }
}