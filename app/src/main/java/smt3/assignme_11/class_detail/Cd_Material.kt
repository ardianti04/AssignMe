package smt3.assignme_11.class_detail

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import smt3.assignme_11.Db_User
import smt3.assignme_11.R

class Cd_Material : Fragment() {
    private lateinit var materialRecView: RecyclerView
    private lateinit var adapter: MaterialRecViewAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_cd__material, container, false)
        sharedPreferences = requireContext().getSharedPreferences("MyAppName", AppCompatActivity.MODE_PRIVATE)


        materialRecView = view.findViewById<RecyclerView?>(R.id.materialRecView)
        materialRecView.layoutManager = LinearLayoutManager(requireContext())

        adapter = MaterialRecViewAdapter(requireContext())
        materialRecView.adapter = adapter
        val classId = 1
        getMateriData(classId)

        return view

    }


    fun getMateriData(classId: Int) {
        val requestQueue = Volley.newRequestQueue(requireContext())
        val url = Db_User.urlShowMaterial

        val stringRequest = JsonObjectRequest(
            Request.Method.POST, url, null,
            { response ->
                try {
                    val jsonArray = response.getJSONArray("materials") // Sesuaikan dengan respons dari API
                    val materiList = ArrayList<Materi>()

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val idMateri = jsonObject.getInt("id_Materi")
                        val namaMateri = jsonObject.getString("nama_Materi")
                        val tanggalUpload = jsonObject.getString("tanggal_upload")

                        val materi = Materi(idMateri, namaMateri, tanggalUpload)
                        materiList.add(materi)
                    }

                    // Set data to RecyclerView adapter
                    adapter.setMateris(materiList)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error -> error.printStackTrace() })

        requestQueue.add(stringRequest)
    }
}