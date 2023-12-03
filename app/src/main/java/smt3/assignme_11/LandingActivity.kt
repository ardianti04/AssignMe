package smt3.assignme_11

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import smt3.assignme_11.helper.PreferenceHelper
import smt3.assignme_11.helper.Constant

class LandingActivity : AppCompatActivity() {
    private lateinit var btnSignIn : Button
    private lateinit var btnCreateAccount : Button
    private lateinit var sharedPref : PreferenceHelper
    private lateinit var sharedPreferences: SharedPreferences

    //test commit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing);

        sharedPref = PreferenceHelper(this)

        btnSignIn = findViewById(R.id.btnSignIn);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);

        btnSignIn.setOnClickListener { // Arahkan pengguna ke halaman login (SignInActivity)
            val intent = Intent(this@LandingActivity, SignInActivity::class.java)
            startActivity(intent)
        }
        btnCreateAccount.setOnClickListener { // Arahkan pengguna ke halaman login (SignInActivity)
            val intent = Intent(this@LandingActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

    }
    override fun onStart() {
        super.onStart()
        sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE)
        if (sharedPreferences.getString("logged", "false").equals("true")){
            moveIntent()
        }
    }

    private fun moveIntent(){
        startActivity(Intent(this, Main_Activity::class.java))
        finish()
    }
}