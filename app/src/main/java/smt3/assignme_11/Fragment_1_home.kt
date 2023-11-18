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
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class Fragment_1_home : Fragment() {

    private lateinit var classRecView: RecyclerView
    private lateinit var adapter: ClassRViewAdapter
    private lateinit var btnTambah: ImageButton
    private lateinit var txtName: TextView
    private lateinit var sharedPreferences: SharedPreferences


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
        val view =  inflater.inflate(R.layout.fragment_1_home, container, false)
        sharedPreferences = requireContext().getSharedPreferences("MyAppName", AppCompatActivity.MODE_PRIVATE)
        txtName = view.findViewById(R.id.txtName)

        val btnTambah = view.findViewById<ImageButton>(R.id.btnTambah)
        val pressedColor = ContextCompat.getColor(requireContext(), R.color.black_900_7f)
        btnTambah.setOnClickListener {
            btnTambah.setColorFilter(pressedColor)
            val intent = Intent(requireContext(), JoinClassActivity::class.java)
            startActivity(intent)
        }

        classRecView = view.findViewById<RecyclerView?>(R.id.classRecView)
        classRecView.layoutManager = LinearLayoutManager(requireContext())

        adapter = ClassRViewAdapter(requireContext())
        classRecView.adapter = adapter

        val kelas = getClassData()
        adapter.setKelas(kelas)
        nama()

        return view

    }
    private fun nama() {
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
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

    /*private fun getDataFromAPI() {
        val apiUrl = Db_User.urlProfile // Ganti dengan URL API Anda

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(apiUrl)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                handleResponseData(responseData) // Panggil fungsi untuk menangani respons
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                // Handle failure di sini jika permintaan gagal
            }
        })
    }

    private fun handleResponseData(responseData: String?) {
        responseData?.let {
            try {
                val json = JSONObject(responseData)
                val status = json.getString("status")

                if (status == "success") {
                    val username = json.getString("Username")
                    requireActivity().runOnUiThread {
                        txtName.text = username // Mengatur nilai txtName dengan username dari respons API
                    }
                } else {
                    // Handle pesan jika login gagal
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }*/


    private fun getClassData(): ArrayList<Kelas>? {
        val kelas = ArrayList<Kelas>()
        kelas.add(
            Kelas(
                1,
                "Kelas XII E",
                "E234",
                "Citra Kirana",
                "Matematika",
                "Kelas untuk siswa ini saja",
                "https://image.gambarpng.id/pngs/gambar-transparent-perlengkapan-belajar-matematika_56394.png"
            )
        )
        kelas.add(
            Kelas(
                2,
                "Kelas XII A",
                "A234",
                "Lusiana",
                "IPA",
                "Kelas untuk siswa ini saja",
                "https://primaindisoft.com/wp-content/uploads/2019/09/ipa.png"
            )
        )
        return kelas
    }
}