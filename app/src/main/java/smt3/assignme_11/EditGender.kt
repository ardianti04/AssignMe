package smt3.assignme_11

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class EditGender : AppCompatActivity() {

    private lateinit var btnSave : Button
    private lateinit var btnBack : ImageView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var male: RadioButton
    private lateinit var female: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_gender)

        btnBack = findViewById(R.id.btnBackUsername);
        btnBack.setOnClickListener(){
            val intent = Intent( this@EditGender, EditProfile::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        male = findViewById(R.id.radioButtonMale)
        female = findViewById(R.id.radioButtonFemale)


        btnSave = findViewById(R.id.btnSave);
        sharedPreferences = getSharedPreferences("MyAppName", AppCompatActivity.MODE_PRIVATE)
        btnSave.setOnClickListener { // Arahkan pengguna ke halaman login (SignInActivity)
            val selectedGender = if (male.isChecked) "Laki-Laki" else "Perempuan"

            val queue: RequestQueue = Volley.newRequestQueue(this)
            val url = Db_User.urlChangeGender

            val stringRequest: StringRequest = object : StringRequest(
                com.android.volley.Request.Method.POST, url,
                object : com.android.volley.Response.Listener<String?> {
                    override fun onResponse(response: String?) {
                        if (response.equals("success")){
                            Log.d("Response_Debug", "Raw Response: $response")
                            Toast.makeText(getApplicationContext(), "Gender Changed", Toast.LENGTH_SHORT).show()
                            val intent = Intent( this@EditGender, EditProfile::class.java)
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
                    paramV["Gender"] = selectedGender
                    //paramV["Username"] = sharedPreferences.getString("Username", "")
                    return paramV
                }
            }
            queue.add(stringRequest)
        }
    }
}