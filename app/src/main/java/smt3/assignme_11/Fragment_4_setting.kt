package smt3.assignme_11

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.json.JSONException
import org.json.JSONObject


//class SessionManager(private val context: Context) {
//
//    private val sharedPreferences: SharedPreferences =
//        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
//    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
//
//    fun saveUserEmail(userEmail: String) {
//        editor.putString("user_email", userEmail)
//        editor.apply()
//    }
//
//    fun getUserEmail(): String? {
//        return sharedPreferences.getString("user_email", null)
//    }
//}




class Fragment_4_setting : Fragment() {

    private lateinit var txtNama: TextView
    private lateinit var txtEmail: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editProfile: RelativeLayout

//    private lateinit var viewModel: MyViewModel
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var btnLogout: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_4_setting, container, false)
        sharedPreferences = requireContext().getSharedPreferences("MyAppName", AppCompatActivity.MODE_PRIVATE)
        txtNama = view.findViewById(R.id.txtNama)
        txtEmail=view.findViewById(R.id.txtEmail)



        editProfile = view.findViewById(R.id.relativeEditProfile)
        editProfile.setOnClickListener {
            val intent = Intent(context, EditProfile::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        // Temukan tombol logout
        val btnLogout = view.findViewById<AppCompatButton>(R.id.btnLogOut)

        // Tambahkan OnClickListener ke tombol logout
        btnLogout.setOnClickListener {
            // Munculkan bottom sheet logout
            val bottomSheetFragment = BS_logout()
            bottomSheetFragment.setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomTransparentBottomSheet)
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    private fun tampilkanDataSetting() {
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val url = Db_User.urlSetting

        val stringRequest: StringRequest = object : StringRequest(
            com.android.volley.Request.Method.POST, url,
            object : com.android.volley.Response.Listener<String?> {
                override fun onResponse(response: String?) {
                    // Mengurai respons JSON
                    try {
                        val jsonResponse = JSONObject(response)
                        val status = jsonResponse.getString("status")

                        // Periksa status untuk memastikan itu sukses sebelum menampilkan data
                        if (status.equals("success", ignoreCase = true)) {
                            val username = jsonResponse.getString("Username").replace("\"", "")
                            val email = jsonResponse.getString("Email").replace("\"", "")

                            // Tampilkan data di UI
                            txtNama.text = username
                            txtEmail.text = email
                        } else {
                            val message = jsonResponse.optString("message", "Unknown error")
                            Toast.makeText(requireContext(), "Error: $message", Toast.LENGTH_SHORT).show()
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
                paramV["Username"] = sharedPreferences.getString("Username", "")
                return paramV
            }
        }
        queue.add(stringRequest)
    }
    override fun onResume() {
        super.onResume()
        tampilkanDataSetting()
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment_4_setting().apply {
                arguments = Bundle().apply {

                }
            }
    }
}