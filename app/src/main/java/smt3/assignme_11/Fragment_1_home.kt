package smt3.assignme_11

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley.newRequestQueue
import org.json.JSONException
import org.json.JSONObject
import java.util.Locale


class Fragment_1_home : Fragment() {

    private lateinit var classRecView: RecyclerView
    private lateinit var adapter: ClassRViewAdapter
    private lateinit var btnTambah: ImageButton
    private lateinit var txtName: TextView
    private lateinit var search:SearchView
    private lateinit var joinedClassesArrayList:ArrayList<Kelas>
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
        val username = sharedPreferences.getString("Username", "")
        val email = sharedPreferences.getString("Email", "")
        val apiKey = sharedPreferences.getString("apiKey", "")

        txtName = view.findViewById(R.id.txtName)

        btnTambah = view.findViewById<ImageButton>(R.id.btnTambah)
        val pressedColor = ContextCompat.getColor(requireContext(), R.color.black_900_7f)
        btnTambah.setOnClickListener {
            btnTambah.setColorFilter(pressedColor)
            val requestCode = 123
            val intent = Intent(requireContext(), JoinClassActivity::class.java)
            activity?.startActivityForResult(intent, requestCode)
        }

        classRecView = view.findViewById<RecyclerView>(R.id.classRecView) ?: RecyclerView(requireContext())
        classRecView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ClassRViewAdapter(requireContext())
        classRecView.adapter = adapter

        nama()
        getJoinedClasses()

        // Inisialisasi SearchView
        search = view.findViewById<SearchView>(R.id.search)
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    performSearch(it)
                }
                return true
            }
        })
        return view

    }

    private fun getJoinedClasses() {
        val queue: RequestQueue = newRequestQueue(requireContext())
        val url = Db_User.urlShowJoinedClass

        val stringRequest: StringRequest = object : StringRequest(
            com.android.volley.Request.Method.POST, url,
            object : com.android.volley.Response.Listener<String?> {
                override fun onResponse(response: String?) {
                    response?.let {
                        if (it.isNotEmpty()) {
                            val joinedClasses = parseJoinedClasses(it)
                            showJoinedClasses(joinedClasses)
                        } else {
                            // Handle empty response here
                        }
                    } ?: run {
                        // Handle null response here
                    }
                }
            }, object : com.android.volley.Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError) {
                    error.printStackTrace()
                    // Handle error responses here
                }
            }) {
            override fun getParams(): Map<String, String?> {
                val paramV: MutableMap<String, String?> = HashMap()
                paramV["Email"] = sharedPreferences.getString("Email", "")
                return paramV
            }
        }
        queue.add(stringRequest)
    }

    // Fungsi untuk mengurai response JSON dan mendapatkan daftar kelas yang telah di-join
    private fun parseJoinedClasses(response: String): ArrayList<Kelas> {
        val joinedClasses = ArrayList<Kelas>()

        try {
            val jsonResponse = JSONObject(response)
            val userClassesArray = jsonResponse.getJSONArray("userClasses")

            for (i in 0 until userClassesArray.length()) {
                val classObj = userClassesArray.getJSONObject(i)
                val classId = classObj.getInt("ClassId")
                val className = classObj.getString("ClassName")
                val subjectName = classObj.getString("SubjectName")

                // Create Kelas object and add to the list
                val kelas = Kelas(
                    classId,
                    className,
                    "",
                    "", // Fill in other fields as needed
                    subjectName,
                    ""
                )
                joinedClasses.add(kelas)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            // Handle JSON parsing error here
        }

        return joinedClasses
    }

    // Fungsi untuk menampilkan daftar kelas yang telah di-join menggunakan RecyclerView
    private fun showJoinedClasses(joinedClasses: ArrayList<Kelas>) {
        joinedClassesArrayList = ArrayList(joinedClasses)
        adapter.setKelas(joinedClassesArrayList)
        adapter.notifyDataSetChanged()
        classRecView.visibility = View.VISIBLE
        if (joinedClassesArrayList.isEmpty()) {
            // Handle UI jika tidak ada kelas yang di-join
        }
    }

    private fun performSearch(query: String) {
        val filteredList = ArrayList<Kelas>()
        val lowerCaseQuery = query.lowercase(Locale.ROOT)
        for (kelas in joinedClassesArrayList) {
            if (kelas.nama_kelas.lowercase(Locale.ROOT).contains(lowerCaseQuery) ||
                kelas.nama_mapel.lowercase(Locale.ROOT).contains(lowerCaseQuery)
            ) {
                filteredList.add(kelas)
            }
        }
        adapter.setKelas(filteredList)
        adapter.notifyDataSetChanged()
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123 && resultCode == RESULT_OK) { // Sesuaikan dengan kode permintaan Anda
            if (data != null) {
                val className = data.getStringExtra("ClassName")
                val subjectName = data.getStringExtra("SubjectName")
                // Lakukan sesuatu dengan className dan subjectName yang Anda terima
            }
        }
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
                return paramV
            }
        }
        queue.add(stringRequest)
        }


    /*fun getClassData(): ArrayList<Kelas>? {
        val kelas = ArrayList<Kelas>()
        kelas.add(
            Kelas(
                1,
                "Kelas XII E",
                "E234",
                "Citra Kirana",
                "Matematika",
                "Kelas untuk siswa ini saja"
            )
        )
        kelas.add(
            Kelas(
                2,
                "Kelas XII A",
                "A234",
                "Lusiana",
                "IPA",
                "Kelas untuk siswa ini saja"
            )
        )
        return kelas
    }*/
}