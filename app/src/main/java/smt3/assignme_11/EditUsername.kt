package smt3.assignme_11

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import okhttp3.Response

class EditUsername : AppCompatActivity() {

    private lateinit var btnSave : Button
    private lateinit var btnBack : ImageView
    private lateinit var textInputUsername : TextInputEditText
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_username)

        btnBack = findViewById(R.id.btnBackUsername);
        btnBack.setOnClickListener(){
            val intent = Intent( this@EditUsername, EditProfile::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        btnSave = findViewById(R.id.btnSave);
        textInputUsername = findViewById(R.id.editUsername)
        sharedPreferences = getSharedPreferences("MyAppName", AppCompatActivity.MODE_PRIVATE)

        btnSave.setOnClickListener { // Arahkan pengguna ke halaman login (SignInActivity)

            val username: String
            username = textInputUsername.text.toString()

            if (username.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "Password must be 8 characters minimum",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val queue: RequestQueue = Volley.newRequestQueue(this)
            val url = Db_User.urlChangeUsername

            val stringRequest: StringRequest = object : StringRequest(
                com.android.volley.Request.Method.POST, url,
                object : com.android.volley.Response.Listener<String?> {
                    override fun onResponse(response: String?) {
                        if (response.equals("success")){
                            Toast.makeText(getApplicationContext(), "Username Changed", Toast.LENGTH_SHORT).show()
                            val intent = Intent( this@EditUsername, EditProfile::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                            finish()
                        } else Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show()
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
                    paramV["newUsername"] = username
                    //paramV["Username"] = sharedPreferences.getString("Username", "")
                    return paramV
                }
            }
            queue.add(stringRequest)
        }
    }
}
