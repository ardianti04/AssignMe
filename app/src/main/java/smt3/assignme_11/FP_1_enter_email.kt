package smt3.assignme_11

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

class FP_1_enter_email : AppCompatActivity() {
    private lateinit var btnSendCode : Button
    private lateinit var txtLogin : TextView
    private lateinit var editTextEmail : TextInputEditText
    private lateinit var resetPassword : Button
    private lateinit var progress : ProgressBar
    private lateinit var txtError : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fp1_enter_email)

        val backBtn = findViewById<ImageView>(R.id.backButtonFpEmail)
        backBtn.setOnClickListener {
            val intent = Intent( this@FP_1_enter_email, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        progress = findViewById(R.id.loading);
        txtError = findViewById(R.id.txtError)
        editTextEmail = findViewById(R.id.email)
        btnSendCode = findViewById(R.id.btnSendCode);
        btnSendCode.setOnClickListener {
            progress.visibility = View.VISIBLE
            val email: String

            email = editTextEmail.text.toString()

            if (email.isNotEmpty()){

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    txtError.text = resources.getString(R.string.lbl_emailFormatInvalid)
                    txtError.visibility = View.VISIBLE
                    progress.visibility = View.GONE
                    return@setOnClickListener
                }

                val queue: RequestQueue = Volley.newRequestQueue(applicationContext)
                val url = Db_User.urlResetPassword
                val stringRequest: StringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    object : Response.Listener<String?> {
                        override fun onResponse(response: String?) {
                            Log.d("Response_Debug", "Raw Response: $response")
                            if (response.equals("success")) {

                                val intent = Intent(this@FP_1_enter_email, FP_2_otp::class.java)
                                intent.putExtra("Email", editTextEmail.text.toString())
                                startActivity(intent)
                            } else {
                                txtError.text = response
                            }
                            progress.visibility = View.GONE
                        }
                    }, object : Response.ErrorListener {
                        override fun onErrorResponse(error: VolleyError) {
                            error.printStackTrace()
                            progress.visibility = View.GONE
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
                txtError.text = resources.getString(R.string.lbl_column_must_be_filled)
                progress.visibility = View.GONE
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