package smt3.assignme_11

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
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

class FP_1_enter_email : AppCompatActivity() {
    private lateinit var btnSendCode : Button
    private lateinit var txtLogin : TextView
    private lateinit var editTextEmail : TextInputEditText
    private lateinit var resetPassword : Button

    private lateinit var txtError : TextView

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

        txtError = findViewById(R.id.txtError)
        editTextEmail = findViewById(R.id.email)
        btnSendCode = findViewById(R.id.btnSendCode);
        btnSendCode.setOnClickListener {
            val email: String

            email = editTextEmail.text.toString()

            if (email.isNotEmpty()){

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    txtError.text = "Format email tidak valid"
                    txtError.visibility = View.VISIBLE
                    return@setOnClickListener
                }

                val queue: RequestQueue = Volley.newRequestQueue(applicationContext)
                val url = Db_User.urlResetPassword
                val stringRequest: StringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    object : Response.Listener<String?> {
                        override fun onResponse(response: String?) {
                            if (response.equals("success")) {

                                val intent = Intent(this@FP_1_enter_email, FP_2_otp::class.java)
                                intent.putExtra("Email", editTextEmail.text.toString())
                                startActivity(intent)
                            } else txtError.text = response
                        }
                    }, object : Response.ErrorListener {
                        override fun onErrorResponse(error: VolleyError) {
                            error.printStackTrace()
                        }
                    }) {
                    override fun getParams(): Map<String, String?> {
                        val paramV: MutableMap<String, String?> = HashMap()
                        paramV["Email"] = email
                        return paramV
                    }
                }
                stringRequest.retryPolicy
                queue.add(stringRequest)
            }else {
                Toast.makeText(applicationContext, "Kolom harus diisi", Toast.LENGTH_SHORT).show()
            }
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