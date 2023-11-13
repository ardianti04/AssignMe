package smt3.assignme_11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Button
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.android.material.textfield.TextInputEditText
import androidx.core.content.ContextCompat
import android.widget.Toast
import com.vishnusivadas.advanced_httpurlconnection.PutData
import smt3.assignme_11.helper.PreferenceHelper
import smt3.assignme_11.helper.Constant

class SignInActivity : AppCompatActivity() {
    private lateinit var sharedPref : PreferenceHelper

    private lateinit var txtSignUp : TextView
    private lateinit var txtLogin : TextView
    private lateinit var btnSendCode : Button
    private lateinit var btnSignIn :Button
    private lateinit var txtForgotPass: TextView

    private lateinit var textInputEmail : TextInputEditText
    private lateinit var textInputPassword : TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        //text forgot pass
        txtForgotPass = findViewById(R.id.txtforgotPassword)
        txtForgotPass.setTextColor(ContextCompat.getColorStateList(this, R.color.login_register))
        txtForgotPass.setOnClickListener{
            val intent = Intent( this@SignInActivity, FP_1_enter_email::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        //button back
        val pressedColor = ContextCompat.getColor(this, R.color.black_900_7f)
        val backBtn = findViewById<ImageView>(R.id.backButtonImageLogin)

        backBtn.setOnClickListener {
            backBtn.setColorFilter(pressedColor)
            val intent = Intent( this@SignInActivity, LandingActivity::class.java)
            startActivity(intent)
        }

        //text sign up
        txtSignUp = findViewById(R.id.txtSignUpInSignIn)
        txtSignUp.setTextColor(ContextCompat.getColorStateList(this, R.color.login_register))
        txtSignUp.setOnClickListener {
            val intent = Intent( this@SignInActivity, SignUpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }


        sharedPref = PreferenceHelper(this)
        //button sign in
        textInputEmail = findViewById(R.id.emailInputLogin);
        textInputPassword = findViewById(R.id.passwordInputLogin);
        btnSignIn = findViewById(R.id.btnSignIn)

        btnSignIn.setOnClickListener{
            val emailInputLogin: String
            val passwordInputLogin: String

            emailInputLogin = textInputEmail.text.toString()
            passwordInputLogin = textInputPassword.text.toString()

            if (emailInputLogin.isNotEmpty() && passwordInputLogin.isNotEmpty()) {

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailInputLogin).matches()) {
                    Toast.makeText(applicationContext, "Format email tidak valid", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (passwordInputLogin.length < 8) {
                    Toast.makeText(applicationContext, "Password harus minimal 8 karakter", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val emailKey = Constant.PREF_EMAIL
                val passwordKey = Constant.PREF_PASSWORD

                // Store the login information in SharedPreferences
                sharedPref.put(emailKey, emailInputLogin)
                sharedPref.put(passwordKey, passwordInputLogin)
                sharedPref.put(Constant.PREF_IS_LOGIN, true)

                //Start ProgressBar first (Set visibility VISIBLE)

                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    val field = arrayOfNulls<String>(2)
                    field[0] = "Email"
                    field[1] = "Password"

                    //Creating array for data
                    val data = arrayOfNulls<String>(2)
                    data[0] = emailInputLogin
                    data[1] = passwordInputLogin

                    val putData = PutData(
                        "http://192.168.0.2/retrofit/login.php",
                        "POST",
                        field,
                        data
                    )
                    if (putData.startPut()) {
                        if (putData.onComplete()) {

                            val result = putData.result
                            if (result.equals("Login Success")) {
                                Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
                                moveIntent()
                            } else {
                                Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
                            }

                            Log.i("PutData", result)
                        }
                    }
                    //End Write and Read data with URL

                }
            }
            else {
                Toast.makeText(applicationContext, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
    }



    private fun moveIntent(){
        startActivity(Intent(this, Main_Activity::class.java))
        finish()
    }
}