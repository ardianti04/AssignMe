package smt3.assignme_11

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


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
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        // Inisialisasi SessionManager
//        val sessionManager = SessionManager(requireContext())
//
//        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
//
//        val emailInputLogin = sessionManager.getUserEmail()
//
//        // Panggil fungsi getUserData dengan menggunakan coroutine
//        viewLifecycleOwner.lifecycleScope.launch {
//            try {
//                val result = withContext(Dispatchers.Main) {
//                    viewModel.getUserData(emailInputLogin.toString()).value
//                }
//
//                // Handle hasilnya di sini
//                if (result != null) {
//                    try {
//                        val jsonObject = JSONObject(result)
//                        val username = jsonObject.getString("Username")
//                        val email = jsonObject.getString("Email")
//
//                        // Update UI elements (TextViews) with username and email
//                        view.findViewById<TextView>(R.id.txtNama).text = username
//                        val emailTextView = view.findViewById<TextView>(R.id.txtEmail)
//                        emailTextView.text = email
//                    } catch (e: JSONException) {
//                        e.printStackTrace()
//                    }
//                } else {
//                    Log.e("Fragment_4_setting", "Result Json is null Server Response :$result")
//                }
//            } catch (e: JSONException) {
//                Log.e("Fragment_4_setting", "Error parsing JSON: ${e.message}")
//            } catch (e: Exception) {
//                // Tangkap pengecualian umum
//                Log.e("Fragment_4_setting", "An error occurred: ${e.message}")
//            }
//
//        }
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