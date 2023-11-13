package smt3.assignme_11

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.vishnusivadas.advanced_httpurlconnection.PutData

class SignUpActivity : AppCompatActivity() {
    private lateinit var txtSignIn : TextView

    private lateinit var textInputName : TextInputEditText
    private lateinit var textInputEmail : TextInputEditText
    private lateinit var textInputPassword : TextInputEditText
    private lateinit var textInputConfirmPassword : TextInputEditText
    private lateinit var btnSignup : AppCompatButton



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


        textInputName = findViewById(R.id.nameInputSignup);
        textInputEmail = findViewById(R.id.emailInputSignup);
        textInputPassword = findViewById(R.id.passwordInputSignup);
        textInputConfirmPassword = findViewById(R.id.ConfirmPasswordInputSignup);
        btnSignup = findViewById(R.id.btnSignUp);

        btnSignup.setOnClickListener {

            val nameInputSignup: String
            val emailInputSignup: String
            val passwordInputSignup: String
            val confirmPasswordInputSignup: String

            nameInputSignup = textInputName.text.toString()
            emailInputSignup = textInputEmail.text.toString()
            passwordInputSignup = textInputPassword.text.toString()
            confirmPasswordInputSignup = textInputConfirmPassword.text.toString()

            if (nameInputSignup.isNotEmpty() && emailInputSignup.isNotEmpty()
                && passwordInputSignup.isNotEmpty() && confirmPasswordInputSignup.isNotEmpty()) {

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailInputSignup).matches()) {
                    Toast.makeText(applicationContext, "Format email tidak valid", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (passwordInputSignup.length < 8) {
                    Toast.makeText(applicationContext, "Password harus minimal 8 karakter", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (passwordInputSignup != confirmPasswordInputSignup) {
                    Toast.makeText(applicationContext, "Konfirmasi password tidak sama dengan password", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                //Start ProgressBar first (Set visibility VISIBLE)

                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    val field = arrayOfNulls<String>(3)
                    field[0] = "Username"
                    field[1] = "Email"
                    field[2] = "Password"
                    //field[3] = "confirmpassword"
                    //Creating array for data
                    val data = arrayOfNulls<String>(3)
                    data[0] = nameInputSignup
                    data[1] = emailInputSignup
                    data[2] = passwordInputSignup
                    //data[3] = confirmPasswordInputSignup
                    val putData = PutData(
                        Db_User.urlRegister,
                        "POST",
                        field,
                        data
                    )
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            val result = putData.result
                            when {
                                //when email is not in use
                                result.equals("Signup Success") -> {
                                    Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, Main_Activity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                //when email is already in use
                                result.equals("Email already in use") -> {
                                    Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
                                    // Handle the case where email is already in use
                                }
                                else -> {
                                    Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
                                }
                            }
                            Log.i("PutData", result)
                        }
                    }
                    //End Write and Read data with URL

                }
            }
            else {
                Toast.makeText(applicationContext, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
            }
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