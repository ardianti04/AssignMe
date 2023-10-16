package smt3.assignme_11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Button
import android.widget.LinearLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import androidx.core.content.ContextCompat

class SignInActivity : AppCompatActivity() {
    private lateinit var btnBackLogin : ImageView
    private lateinit var txtSignUp : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val linearLayout = findViewById<LinearLayout>(R.id.backButtonLogin)
        val imageView = findViewById<ImageView>(R.id.backButtonImageLogin)
        val pressedColor = ContextCompat.getColor(this, R.color.gray)

        imageView.setOnClickListener {
            imageView.setColorFilter(pressedColor)
            val intent = Intent( this@SignInActivity, LandingActivity::class.java)
            startActivity(intent)
        }

        linearLayout.setOnClickListener {
            imageView.setColorFilter(pressedColor)
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