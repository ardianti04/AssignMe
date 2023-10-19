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

class SignInActivity : AppCompatActivity() {
    private lateinit var txtSignUp : TextView
    private lateinit var txtLogin : TextView
    private lateinit var btnSendCode : Button

    lateinit var txtForgotPass: TextView
    lateinit var mDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        txtForgotPass = findViewById(R.id.txtforgotPassword)
        txtForgotPass.setTextColor(ContextCompat.getColorStateList(this, R.color.login_register))
        txtForgotPass.setOnClickListener{
            val intent = Intent( this@SignInActivity, FP_1_enter_email::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        val pressedColor = ContextCompat.getColor(this, R.color.black_900_7f)
        val backBtn = findViewById<ImageView>(R.id.backButtonImageLogin)

        backBtn.setOnClickListener {
            backBtn.setColorFilter(pressedColor)
            val intent = Intent( this@SignInActivity, LandingActivity::class.java)
            startActivity(intent)
        }

        txtSignUp = findViewById(R.id.txtSignUpInSignIn)
        txtSignUp.setTextColor(ContextCompat.getColorStateList(this, R.color.login_register))
        txtSignUp.setOnClickListener {
            val intent = Intent( this@SignInActivity, SignUpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}