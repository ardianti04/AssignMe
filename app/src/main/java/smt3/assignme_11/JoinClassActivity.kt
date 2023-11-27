package smt3.assignme_11

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONException
import org.json.JSONObject


class JoinClassActivity : AppCompatActivity() {
    private lateinit var btnJoin : Button
    private lateinit var textInputClassCode : TextInputEditText
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var className: String
    private lateinit var subjectName: String

    private lateinit var txtErrorJoin : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_class)
        sharedPreferences = getSharedPreferences("MyAppName", AppCompatActivity.MODE_PRIVATE)
        val username = sharedPreferences.getString("Username", "")
        val email = sharedPreferences.getString("Email", "")
        val apiKey = sharedPreferences.getString("apiKey", "")


        // Inisialisasi komponen
        val btnBack = findViewById<ImageView>(R.id.backButtonJoinClass)
        btnBack.setOnClickListener(){
            val intent = Intent( this@JoinClassActivity, Main_Activity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        txtErrorJoin = findViewById(R.id.txtErrorJoin)
        textInputClassCode = findViewById(R.id.inputClassCode)
        btnJoin = findViewById(R.id.btnJoin);
        btnJoin.setOnClickListener {

            val classCode: String = textInputClassCode.text.toString()

            if (classCode.isEmpty()) {
                txtErrorJoin.text = getString(R.string.code_cannot_be_empty)
                return@setOnClickListener
            }
            if (classCode.length < 6) {
                txtErrorJoin.text = getString(R.string.code_must_be_6_characters)
                return@setOnClickListener
            }

            className = "ClassName"
            subjectName = "SubjectName"

            val queue: RequestQueue = Volley.newRequestQueue(this)
            val url = Db_User.urlJoinClass

            val stringRequest: StringRequest = object : StringRequest(
                com.android.volley.Request.Method.POST, url,
                object : com.android.volley.Response.Listener<String?> {
                    override fun onResponse(response: String?) {
                        Log.d("Response", "Response: $response")
                        try {
                            val jsonObject = JSONObject(response)
                            val status = jsonObject.getString("status")
                            if (status.equals("success")){
                                className = jsonObject.optString("ClassName")
                                subjectName = jsonObject.optString("SubjectName")

                                Toast.makeText(getApplicationContext(), "Class Joined Successfully", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@JoinClassActivity, Main_Activity::class.java)
                                intent.putExtra("ClassName", className)
                                intent.putExtra("SubjectName", subjectName)
                                setResult(RESULT_OK, intent)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                startActivity(intent)
                                finish()
                            }else {
                                val errorMessage = jsonObject.optString("message")
                                txtErrorJoin.text = errorMessage
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                }, object : com.android.volley.Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                        error.printStackTrace()
                    }
                }) {
                override fun getParams(): Map<String, String?> {
                    val paramV: MutableMap<String, String?> = HashMap()
                    paramV["ClassName"] = className
                    paramV["SubjectName"] = subjectName
                    paramV["ClassCode"] = classCode
                    paramV["Email"] = email
                    return paramV
                }
            }
            stringRequest.setRetryPolicy(object : RetryPolicy {
                override fun getCurrentTimeout(): Int {
                    return 50000
                }

                override fun getCurrentRetryCount(): Int {
                    return 50000
                }

                @Throws(VolleyError::class)
                override fun retry(error: VolleyError) {
                }
            })
            queue.add(stringRequest)
        }


        // Lakukan tindakan bergabung dengan kelas di sini
            //dialog.dismiss() // Tutup dialog setelah tindakan selesai
        }

        // Mengatur tindakan saat tombol "Cancel" diklik
        //btnBack.setOnClickListener {
            //dialog.dismiss() // Tutup dialog jika tombol "Cancel" diklik
        }

