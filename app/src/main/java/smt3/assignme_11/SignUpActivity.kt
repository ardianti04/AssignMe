package smt3.assignme_11

import AuthService
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUpActivity : AppCompatActivity() {
    private lateinit var btnBackLogin : ImageView
    private lateinit var txtSignIn : TextView
    private lateinit var btnSignup:AppCompatButton
    private lateinit var nameInputSignup: EditText
    private lateinit var emailInputSignup: EditText
    private lateinit var passwordInputSignup: EditText
    private lateinit var ConfirmPasswordInputSignup: EditText




    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btnSignup = findViewById(R.id.btnSignUp)
        nameInputSignup = findViewById(R.id.nameInputSignup)
        emailInputSignup = findViewById(R.id.emailInputSignup)
        passwordInputSignup = findViewById(R.id.passwordInputSignup)
        ConfirmPasswordInputSignup = findViewById(R.id.ConfirmPasswordInputSignup)

        btnSignup.setOnClickListener{
            register()
        }


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

     fun register() {
        if (nameInputSignup.text.isEmpty()) {
            nameInputSignup.error = "The name column cannot be empty"
            nameInputSignup.requestFocus()
            return
        } else if (emailInputSignup.text.isEmpty()) {
            emailInputSignup.error = "The email column cannot be empty"
            emailInputSignup.requestFocus()
            return
        } else if (passwordInputSignup.text.isEmpty()) {
            passwordInputSignup.error = "The password column cannot be empty"
            passwordInputSignup.requestFocus()
            return
        } else if (ConfirmPasswordInputSignup.text.isEmpty()) {
            ConfirmPasswordInputSignup.error = "The Confirm Password column cannot be empty"
            ConfirmPasswordInputSignup.requestFocus()
            return
        }
    }

}