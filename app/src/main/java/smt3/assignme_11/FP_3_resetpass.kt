package smt3.assignme_11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Button
import androidx.core.content.ContextCompat

class FP_3_resetpass : AppCompatActivity() {
    private lateinit var btnReset : Button
    private lateinit var txtLogin : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fp3_resetpass)

        //Back button func
        val pressedColor = ContextCompat.getColor(this, R.color.black_900_7f)
        val backBtn = findViewById<ImageView>(R.id.backButtonFpReset)
        backBtn.setOnClickListener {
            backBtn.setColorFilter(pressedColor)
            val intent = Intent( this@FP_3_resetpass, FP_2_otp::class.java)
            startActivity(intent)
        }

        //Reset button func
        btnReset = findViewById(R.id.btnLogOut);
        btnReset.setOnClickListener { // Arahkan pengguna ke halaman login (SignInActivity)
            val intent = Intent(this@FP_3_resetpass, FP_4_status::class.java)
            startActivity(intent)
        }

        //Login text func
        txtLogin = findViewById(R.id.txtLoginInFpReset)
        txtLogin.setTextColor(ContextCompat.getColorStateList(this, R.color.login_fp_email))
        txtLogin.setOnClickListener {
            val intent = Intent( this@FP_3_resetpass, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}