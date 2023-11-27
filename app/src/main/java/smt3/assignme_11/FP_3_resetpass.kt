package smt3.assignme_11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText

class FP_3_resetpass : AppCompatActivity() {
    private lateinit var btnReset : Button
    private lateinit var txtError : TextView
    private lateinit var editTextNewPassword : TextInputEditText
    private lateinit var editTextConfirmNewPassword : TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fp3_resetpass)



        val email = intent.getStringExtra("Email")
        //Reset button func
        editTextNewPassword = findViewById(R.id.newPassInputFP)
        editTextConfirmNewPassword = findViewById(R.id.confirmNewPassInputFP)
        txtError = findViewById(R.id.txtError)
        btnReset = findViewById(R.id.btnResetPassword);

        btnReset.setOnClickListener {

            val password: String
            val confirmPassword: String

            password = editTextNewPassword.text.toString()
            confirmPassword = editTextConfirmNewPassword.text.toString()

            if (password.isNotEmpty() && confirmPassword.isNotEmpty()) {

                if (password.length < 8) {
                    Toast.makeText(applicationContext, "Password must be 8 characters minimum", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (password != confirmPassword) {
                    Toast.makeText(applicationContext, "Password doesn't match", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val queue: RequestQueue = Volley.newRequestQueue(applicationContext)
                val url = Db_User.urlNewPassword
                val stringRequest: StringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    object : Response.Listener<String?> {
                        override fun onResponse(response: String?) {
                            if (response.equals("success")) {
                                val intent = Intent(this@FP_3_resetpass, FP_4_status::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
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
                        paramV["newPassword"] = password
                        return paramV
                    }
                }
                queue.add(stringRequest)
            }
        }
    }

    override fun onBackPressed() {
        // Biarkan default behavior onBackpressed terjadi
        // Hilangkan pemanggilan super.onBackPressed() atau navigasi kembali di sini
        // Jika ingin membatasi perilaku kembali, Anda bisa menambahkan logika di sini
    }
}