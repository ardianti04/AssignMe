package smt3.assignme_11.class_detail

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import smt3.assignme_11.Db_User
import smt3.assignme_11.R
import java.text.SimpleDateFormat
import java.util.Locale

class Cd_Material : Fragment() {
    private lateinit var materialRecView: RecyclerView
    private lateinit var adapter: MaterialRecViewAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var materialsArrayList: java.util.ArrayList<Materi>

    companion object {
        fun newInstance(classId: Int): Cd_Material {
            val fragment = Cd_Material()
            val args = Bundle()
            args.putInt("ClassId", classId)
            fragment.arguments = args
            Log.d("Cd_Material", "ClassId in newInstance: $classId")
            return fragment
        }
    }

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
        getMateriData()

        return view

    }

    fun getMateriData() {
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val url = Db_User.urlShowMaterial

        val stringRequest: StringRequest = object : StringRequest(
            com.android.volley.Request.Method.POST, url,
            object : com.android.volley.Response.Listener<String?> {
                override fun onResponse(response: String?) {
                    response?.let {
                        Log.d("Response_Debug response", "Raw Response: $response")
                        if (it.isNotEmpty()) {
                            val tasks = parseMaterial(it)
                            showMaterial(tasks)
                        } else {
                            // Handle empty response here
                        }
                    } ?: run {
                        // Handle null response here
                    }
                }
            }, object : com.android.volley.Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError) {
                    Log.d("Response_Debug error", "Raw Response: $error")
                    error.printStackTrace()
                    // Handle error responses here
                }
            }) {
            override fun getParams(): Map<String, String?> {
                val paramV: MutableMap<String, String?> = HashMap()
                arguments?.getInt("ClassId")?.let { classId ->
                    paramV["ClassId"] = classId.toString()
                    Log.d("Cd_Material Param", "ParamV: $paramV")
                }
                return paramV
            }
        }
        queue.add(stringRequest)
    }
    private fun parseMaterial(response: String): ArrayList<Materi> {
        val material = ArrayList<Materi>()

        try {
            val jsonResponse = JSONObject(response)
            val materialArray = jsonResponse.getJSONArray("materials")

            for (i in 0 until materialArray.length()) {
                val classObj = materialArray.getJSONObject(i)
                val materialId = classObj.getInt("materialId")
                val materialName = classObj.getString("materialName")
                val uploadDate = classObj.getString("uploadDate")

                val formattedDate  = formatDate(uploadDate)

                val tugas = Materi(
                    materialId,
                    materialName,
                    formattedDate
                )
                material.add(tugas)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.e("Cd_Material parseTask", "Error parsing JSON: ${e.message}")
            // Handle JSON parsing error here
        }

        return material
    }
    private fun showMaterial(material: ArrayList<Materi>) {
        materialsArrayList = ArrayList(material)
        adapter.setMaterial(materialsArrayList)
        adapter.notifyDataSetChanged()
        materialRecView.visibility = View.VISIBLE
        if (materialsArrayList.isEmpty()) {
        }
    }
    private fun formatDate(dateStr: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateStr)
        return outputFormat.format(date)
    }
}