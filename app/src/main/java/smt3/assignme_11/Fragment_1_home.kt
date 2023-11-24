package smt3.assignme_11

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley.newRequestQueue
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment_1_home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment_1_home : Fragment() {

    private lateinit var classRecView: RecyclerView
    private lateinit var adapter: ClassRViewAdapter
    private lateinit var btnTambah: ImageButton
    private lateinit var txtName: TextView
    private lateinit var sharedPreferences: SharedPreferences


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val view =  inflater.inflate(R.layout.fragment_1_home, container, false)
        sharedPreferences = requireContext().getSharedPreferences("MyAppName", AppCompatActivity.MODE_PRIVATE)
        txtName = view.findViewById(R.id.txtName)

        btnTambah = view.findViewById<ImageButton>(R.id.btnTambah)
        val pressedColor = ContextCompat.getColor(requireContext(), R.color.black_900_7f)
        btnTambah.setOnClickListener {
            btnTambah.setColorFilter(pressedColor)
            val intent = Intent(requireContext(), JoinClassActivity::class.java)
            startActivity(intent)
        }

        classRecView = view.findViewById<RecyclerView?>(R.id.classRecView)
        adapter = ClassRViewAdapter(requireContext())

        // Atur adapter ke RecyclerView
        classRecView.adapter = adapter
        classRecView.layoutManager = LinearLayoutManager(requireContext())

        nama()
        getClassDataFromApi()

        return view

    }

    private fun nama() {
        val queue: RequestQueue = newRequestQueue(requireContext())
        val url = Db_User.urlProfile

        val stringRequest: StringRequest = object : StringRequest(
            com.android.volley.Request.Method.POST, url,
            object : com.android.volley.Response.Listener<String?> {
                override fun onResponse(response: String?) {
                    val username = response?.replace("\"", "") // Menghapus tanda petik ganda
                    txtName.text = username
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


    fun getClassDataFromApi() {
        val queue: RequestQueue = newRequestQueue(requireContext())
        val url = Db_User.urlRecViewHome // Gantilah dengan URL API Anda

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener<JSONArray> { response ->
                Log.d("API_RESPONSE", response.toString())
                val kelasList = parseJsonResponse(response)
                adapter.setKelas(kelasList)
            },
            Response.ErrorListener { error ->
                // Handle error
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    try {
                        // Coba mengonversi respons ke dalam objek JSON tunggal
                        val jsonResponse = JSONObject(String(error.networkResponse.data))
                        // Periksa apakah respons adalah objek JSON tunggal
                        if (jsonResponse.has("status") && jsonResponse.has("message")) {
                            // Tangani respons objek JSON tunggal
                            Log.e("API_ERROR", "Error fetching class data: ${jsonResponse.getString("message")}")
                            Toast.makeText(requireContext(), jsonResponse.getString("message"), Toast.LENGTH_SHORT).show()
                        } else {
                            // Tangani respons yang tidak diharapkan
                            Log.e("API_ERROR", "Unexpected error fetching class data", error)
                            Toast.makeText(requireContext(), "Unexpected error fetching class data", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: JSONException) {
                        // Tangani kesalahan pengonversian
                        Log.e("API_ERROR", "Error converting error response to JSON", e)
                        Toast.makeText(requireContext(), "Error converting error response to JSON", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Tangani ketika networkResponse atau datanya null
                    Log.e("API_ERROR", "Error network response is null")
                    Toast.makeText(requireContext(), "Error network response is null", Toast.LENGTH_SHORT).show()

                    activity?.runOnUiThread {
                        // Update UI di sini
                        Toast.makeText(requireContext(), "Error network response is null", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )


        queue.add(jsonArrayRequest)
    }

    private fun parseJsonResponse(response: JSONArray): ArrayList<Kelas> {
        val kelasList = ArrayList<Kelas>()

        for (i in 0 until response.length()) {
            try {
                val jsonObject: JSONObject = response.getJSONObject(i)

                val namaKelas = jsonObject.getString("ClassName")
                val namaMapel = jsonObject.getString("SubjectName")

                val kelas = Kelas(namaKelas,namaMapel)
                kelasList.add(kelas)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        return kelasList
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment_1_home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment_1_home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}