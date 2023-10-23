package smt3.assignme_11

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LandingActivity : AppCompatActivity() {
    private lateinit var btnSignIn : Button
    private lateinit var btnCreateAccount : Button

    //test commit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing);

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
}