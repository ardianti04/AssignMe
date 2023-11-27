package smt3.assignme_11

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
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

        imageView.setOnClickListener {
            val intent = Intent( this@SignUpActivity, LandingActivity::class.java)
            startActivity(intent)
        }

        linearLayout.setOnClickListener {
            val intent = Intent( this@SignUpActivity, LandingActivity::class.java)
            startActivity(intent)
        }


        textInputName = findViewById(R.id.nameInputSignup);
        textInputEmail = findViewById(R.id.emailInputSignup);
        textInputPassword = findViewById(R.id.passwordInputSignup);
        textInputConfirmPassword = findViewById(R.id.ConfirmPasswordInputSignup);
        btnSignup = findViewById(R.id.btnSignUp);

        btnSignup.setOnClickListener {

            val username: String
            val email: String
            val password: String
            val confirmPassword: String

            username = textInputName.text.toString()
            email = textInputEmail.text.toString()
            password = textInputPassword.text.toString()
            confirmPassword = textInputConfirmPassword.text.toString()

            if (username.isNotEmpty() && email.isNotEmpty()
                && password.isNotEmpty() && confirmPassword.isNotEmpty()) {

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(applicationContext, "Format email tidak valid", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (password.length < 8) {
                    Toast.makeText(applicationContext, "Password harus minimal 8 karakter", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (password != confirmPassword) {
                    Toast.makeText(applicationContext, "Konfirmasi password tidak sama dengan password", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val queue: RequestQueue = Volley.newRequestQueue(this)
                val url = Db_User.urlRegister

                val stringRequest: StringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    object : Response.Listener<String?> {
                        override fun onResponse(response: String?) {
                            if (response.equals("success")){
                                Toast.makeText(getApplicationContext(), "Register successful", Toast.LENGTH_SHORT).show()
                                val intent = Intent(applicationContext, SignInActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }, object : Response.ErrorListener {
                        override fun onErrorResponse(error: VolleyError?) {

                        }
                    }) {
                    override fun getParams(): Map<String, String> {
                        val paramV: MutableMap<String, String> = HashMap()
                        paramV["Username"] = username
                        paramV["Email"] = email
                        paramV["Password"] = password
                        return paramV
                    }
                }
                queue.add(stringRequest)
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