package smt3.assignme_11

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONException
import org.json.JSONObject


class SignInActivity : AppCompatActivity() {
    //private lateinit var sharedPref : PreferenceHelper
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var txtSignUp : TextView
    private lateinit var txtLogin : TextView
    private lateinit var btnSendCode : Button
    private lateinit var btnSignIn :Button
    private lateinit var txtForgotPass: TextView

    private lateinit var textInputEmail : TextInputEditText
    private lateinit var textInputPassword : TextInputEditText
    private lateinit var progress : ProgressBar
    private lateinit var txtError: TextView

    private lateinit var username: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var apiKey: String

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
        val backBtn = findViewById<ImageView>(R.id.backButtonImageLogin)

        backBtn.setOnClickListener {
            val intent = Intent( this@SignInActivity, LandingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
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

        sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE)

        //button sign in
        textInputEmail = findViewById(R.id.emailInputLogin);
        textInputPassword = findViewById(R.id.passwordInputLogin);
        progress = findViewById(R.id.loading);
        txtError = findViewById(R.id.txtError);
        btnSignIn = findViewById(R.id.btnSignIn)
        btnSignIn.setOnClickListener{
            progress.visibility = View.GONE


            email = textInputEmail.text.toString()
            password = textInputPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(applicationContext, "Format email tidak valid", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                //if (password.length < 8) {
                //    Toast.makeText(applicationContext, "Password harus minimal 8 karakter", Toast.LENGTH_SHORT).show()
                //    return@setOnClickListener
                //}

                val queue: RequestQueue = Volley.newRequestQueue(this)
                val url = Db_User.urlLogin

                val stringRequest: StringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    object : Response.Listener<String?> {
                        override fun onResponse(response: String?) {
                            try {
                                val jsonObject = JSONObject(response)
                                val status = jsonObject.getString("status")
                                val message = jsonObject.getString("message")
                                if (status.equals("success")){
                                    email = jsonObject.optString("Email")
                                    password = jsonObject.optString("Password")
                                    apiKey = jsonObject.optString("apiKey")
                                    username = jsonObject.optString("Username")

                                    val editor = sharedPreferences.edit();
                                    editor.putString("logged", "true")
                                    editor.putString("Username", username)
                                    editor.putString("Email", email)
                                    editor.putString("apiKey", apiKey)
                                    editor.apply()

                                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show()
                                    moveIntent()
                                }else txtError.text = response
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }
                    }, object : Response.ErrorListener {
                        override fun onErrorResponse(error: VolleyError?) {
                            progress.visibility = View.GONE
                            if (error != null) {
                                txtError.text = error.localizedMessage
                            }
                            txtError.visibility = View.VISIBLE
                        }
                    }) {
                    override fun getParams(): Map<String, String> {
                        val paramV: MutableMap<String, String> = HashMap()
                        paramV["Email"] = email
                        paramV["Password"] = password
                        return paramV
                    }
                }
                queue.add(stringRequest)

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