package smt3.assignme_11

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BS_logout : BottomSheetDialogFragment(){
    private lateinit var btnLogout : AppCompatButton
    private lateinit var txtCancel : TextView
    private lateinit var sharedPreferences: SharedPreferences
    //private lateinit var sharedPref : PreferenceHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottomsheet_logout, container, false)

        sharedPreferences = requireContext().getSharedPreferences("MyAppName", Context.MODE_PRIVATE)
        if (sharedPreferences.getString("logged", "false").equals("false")){
            startActivity(Intent(requireContext(), LandingActivity::class.java))
        }
        btnLogout = view.findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener {
            val queue: RequestQueue = Volley.newRequestQueue(requireContext())
            val url = Db_User.urlLogout

            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, url,
                object : Response.Listener<String?> {
                    override fun onResponse(response: String?) {
                        if (response.equals("success")) {
                            val editor = sharedPreferences.edit();
                            editor.putString("logged", "")
                            editor.putString("Username", "")
                            editor.putString("Email", "")
                            editor.putString("apiKey", "")
                            editor.apply()
                            startActivity(Intent(requireContext(), LandingActivity::class.java))

                        } else Toast.makeText(requireContext(), response, Toast.LENGTH_SHORT).show()
                    }
                }, object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                        error.printStackTrace()
                    }
                }) {
                override fun getParams(): Map<String, String?> {
                    val paramV: MutableMap<String, String?> = HashMap()
                    paramV["Email"] = sharedPreferences.getString("Email", "")
                    paramV["apiKey"] = sharedPreferences.getString("apiKey", "")
                    return paramV
                }
            }
            queue.add(stringRequest)

        }

        txtCancel = view.findViewById(R.id.txtCancel)
        txtCancel.setOnClickListener{
            dismiss()
        }
        return view
    }
}