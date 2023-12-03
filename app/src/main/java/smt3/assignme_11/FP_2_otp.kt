package smt3.assignme_11

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Button
import android.widget.LinearLayout
import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.util.Log
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import androidx.core.content.ContextCompat
import android.view.WindowManager
import android.view.Gravity
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.goodiebag.pinview.Pinview

class FP_2_otp : AppCompatActivity() {
    private lateinit var btnVerify : Button
    private lateinit var txtLogin : TextView
    private lateinit var editTextOtp : Pinview
    private lateinit var txtError : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fp2_otp)



        //Back button func
        val backBtn = findViewById<ImageView>(R.id.backButtonFpOtp)
        backBtn.setOnClickListener {
            val intent = Intent( this@FP_2_otp, FP_1_enter_email::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        val email = intent.getStringExtra("Email")
        //Verify button func
        txtError = findViewById(R.id.txtError)
        editTextOtp = findViewById(R.id.otpCode)
        btnVerify = findViewById(R.id.btnVerify);
        btnVerify.setOnClickListener {

            val otp: String

            otp = editTextOtp.value

            if (otp.isNotEmpty()) {

                if (otp.length <6) {
                    txtError.text = resources.getString(R.string.lbl_otpInvalid)
                    return@setOnClickListener
                }

                val queue: RequestQueue = Volley.newRequestQueue(applicationContext)
                val url = Db_User.urlCheckOtp
                val stringRequest: StringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    object : Response.Listener<String?> {
                        override fun onResponse(response: String?) {
                            Log.d("Response_Debug", "Raw Response: $response")
                            if (response.equals("success")) {
                                val intent = Intent(this@FP_2_otp, FP_3_resetpass::class.java)
                                intent.putExtra("Email", email)
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
                        paramV["Otp"] = otp
                        return paramV
                    }
                }
                queue.add(stringRequest)
            }
        }



    }
}