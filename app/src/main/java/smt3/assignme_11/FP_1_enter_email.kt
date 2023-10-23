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

class FP_1_enter_email : AppCompatActivity() {
    private lateinit var btnSendCode : Button
    private lateinit var txtLogin : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fp1_enter_email)

        val pressedColor = ContextCompat.getColor(this, R.color.black_900_7f)
        val backBtn = findViewById<ImageView>(R.id.backButtonFpEmail)
        backBtn.setOnClickListener {
            backBtn.setColorFilter(pressedColor)
            val intent = Intent( this@FP_1_enter_email, SignInActivity::class.java)
            startActivity(intent)
        }

        btnSendCode = findViewById(R.id.btnSendCode);
        btnSendCode.setOnClickListener { // Arahkan pengguna ke halaman login (SignInActivity)
            val intent = Intent(this@FP_1_enter_email, FP_2_otp::class.java)
            startActivity(intent)
        }

        txtLogin = findViewById(R.id.txtLoginInFpEmail)
        txtLogin.setTextColor(ContextCompat.getColorStateList(this, R.color.login_fp_email))
        txtLogin.setOnClickListener {
            val intent = Intent( this@FP_1_enter_email, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}