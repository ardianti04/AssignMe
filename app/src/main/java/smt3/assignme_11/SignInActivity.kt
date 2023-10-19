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
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import androidx.core.content.ContextCompat
import android.view.WindowManager
import android.view.Gravity

class SignInActivity : AppCompatActivity() {
    private lateinit var txtSignUp : TextView
    private lateinit var txtLogin : TextView

    lateinit var popupBtn: TextView
    lateinit var mDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        popupBtn = findViewById(R.id.txtforgotPassword)
        mDialog = Dialog(this)
        val pressedColor = ContextCompat.getColor(this, R.color.black_900_7f)

        popupBtn.setOnClickListener{
            popupBtn.setTextColor(ContextCompat.getColorStateList(this, R.color.login_register))
            val dialogView = layoutInflater.inflate(R.layout.activity_fp1_enter_email, null)
            val window = mDialog.window
            window?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.black_semi_transparent)))
            mDialog.setContentView(dialogView)

            val imageViewCloseButton = dialogView.findViewById<ImageView>(R.id.closeButtonFpEmail)
            imageViewCloseButton.setOnClickListener {
                imageViewCloseButton.setColorFilter(pressedColor)
                mDialog.dismiss()
            }

            txtLogin = dialogView.findViewById(R.id.txtLoginInFP)
            txtLogin.setTextColor(ContextCompat.getColorStateList(this, R.color.login_fp_email))
            txtLogin.setOnClickListener {
                mDialog.dismiss()
            }

            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(window?.attributes)
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams.gravity = Gravity.CENTER
            window?.attributes = layoutParams
            mDialog.setCanceledOnTouchOutside(true)
            mDialog.show()
        }

        val imageView = findViewById<ImageView>(R.id.backButtonImageLogin)

        imageView.setOnClickListener {
            imageView.setColorFilter(pressedColor)
            val intent = Intent( this@SignInActivity, LandingActivity::class.java)
            startActivity(intent)
        }

        txtSignUp = findViewById(R.id.txtSignUpInSignIn)
        txtSignUp.setTextColor(ContextCompat.getColorStateList(this, R.color.login_register))
        txtSignUp.setOnClickListener {
            val intent = Intent( this@SignInActivity, SignUpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}