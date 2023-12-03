package smt3.assignme_11

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject


class EditProfile : AppCompatActivity() {

    private lateinit var username: RelativeLayout
    private lateinit var gender: RelativeLayout
    private lateinit var txtUsername: TextView
    private lateinit var txtGender: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        sharedPreferences = getSharedPreferences("MyAppName", AppCompatActivity.MODE_PRIVATE)
        txtUsername = findViewById(R.id.txtUsername)
        txtGender = findViewById(R.id.txtGender)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener(){
            val intent = Intent( this@EditProfile, Main_Activity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        username = findViewById(R.id.relativeUsername)
        username.setOnClickListener {
            val intent = Intent(this@EditProfile, EditUsername::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
        gender = findViewById(R.id.relativeGender)
        gender.setOnClickListener {
            val intent = Intent(this@EditProfile, EditGender::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

    private fun nama() {
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = Db_User.urlProfile

        val stringRequest: StringRequest = object : StringRequest(
            com.android.volley.Request.Method.POST, url,
            object : com.android.volley.Response.Listener<String?> {
                override fun onResponse(response: String?) {
                    try {
                        val jsonResult = JSONObject(response)
                        val status = jsonResult.getString("status")

                        if (status == "Success") {
                            val userData = jsonResult.getJSONObject("data")
                            val username = userData.getString("Username")
                            val gender = userData.getString("Gender")

                            txtUsername.text = username
                            txtGender.text = gender
                        } else {
                            // Handle failed response
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
                paramV["Email"] = sharedPreferences.getString("Email", "")
                paramV["apiKey"] = sharedPreferences.getString("apiKey", "")
                //paramV["Username"] = sharedPreferences.getString("Username", "")
                return paramV
            }
        }
        queue.add(stringRequest)
    }

    override fun onResume() {
        super.onResume()
        nama()
    }
}