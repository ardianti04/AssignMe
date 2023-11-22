package smt3.assignme_11

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
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



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment_4_setting.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment_4_setting : Fragment() {

    private lateinit var txtNama: TextView
    private lateinit var txtEmail: TextView
    private lateinit var sharedPreferences: SharedPreferences

//    private lateinit var viewModel: MyViewModel
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var btnLogout: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
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

        val btnMasukChangePassword = view.findViewById<ImageButton>(R.id.btnMasukChangePassword)

        btnMasukChangePassword.setOnClickListener {
            val intent = Intent(context, FP_3_resetpass::class.java)
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
        tampilkanDataSetting()
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
                //paramV["Username"] = sharedPreferences.getString("Username", "")
                return paramV
            }
        }
        queue.add(stringRequest)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment_4_setting.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment_4_setting().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}