package smt3.assignme_11

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.ImageView
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import smt3.assignme_11.SignInActivity

class SignUpActivity : AppCompatActivity() {
    private lateinit var btnBackLogin : ImageView
    private lateinit var txtSignIn : TextView

    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val linearLayout = findViewById<LinearLayout>(R.id.backButtonSignup)
        val imageView = findViewById<ImageView>(R.id.backButtonImageSignup)
        val pressedColor = ContextCompat.getColor(this, R.color.gray)

        imageView.setOnClickListener {
            imageView.setColorFilter(pressedColor)
            val intent = Intent( this@SignUpActivity, LandingActivity::class.java)
            startActivity(intent)
        }

        linearLayout.setOnClickListener {
            imageView.setColorFilter(pressedColor)
            val intent = Intent( this@SignUpActivity, LandingActivity::class.java)
            startActivity(intent)
        }

        txtSignIn = findViewById(R.id.txtLoginInSignUp)
        txtSignIn.setTextColor(ContextCompat.getColorStateList(this, R.color.login_register))
        txtSignIn.setOnClickListener {
            val intent = Intent( this@SignUpActivity, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }




    }
}