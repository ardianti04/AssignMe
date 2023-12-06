package smt3.assignme_11.material_detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import smt3.assignme_11.Db_User
import smt3.assignme_11.R
import smt3.assignme_11.class_detail.CLass_Detail
import smt3.assignme_11.class_detail.Materi
import smt3.assignme_11.class_detail.Tugas
import smt3.assignme_11.task_detail.TaskAttachmentRecViewAdapter
import java.text.SimpleDateFormat
import java.util.Locale

class Detail_Material : AppCompatActivity() {
    private lateinit var backButton : ImageView
    private lateinit var txtMaterialName: TextView
    private lateinit var txtMaterialDesc: TextView
    private lateinit var relativeAttachment: RelativeLayout
    private lateinit var attachmentTeacherRecView: RecyclerView
    private lateinit var adapter: MaterialAttachmentRecViewAdapter
    private lateinit var attachmentArrayList: java.util.ArrayList<Materi>
    private var materialId: Int = -1
    private var classId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_material)

        materialId = intent.getIntExtra("MaterialId", -1)
        Log.d("Detail_Tugas", "MaterialId from Intent: $materialId")
        classId = intent.getIntExtra("ClassId", -1)
        Log.d("Detail_Tugas", "ClassId from Intent: $classId")

        txtMaterialName = findViewById(R.id.txtNamaMateri)
        txtMaterialDesc = findViewById(R.id.txtDeskripsi)
        relativeAttachment = findViewById(R.id.RelativeAttachment)

        attachmentTeacherRecView = findViewById<RecyclerView>(R.id.attachment_teacher)
        attachmentTeacherRecView.layoutManager = LinearLayoutManager(this)
        adapter = MaterialAttachmentRecViewAdapter(this)
        attachmentTeacherRecView.adapter = adapter

        backButton = findViewById(R.id.backButton)
        backButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@Detail_Material, CLass_Detail::class.java)
            intent.putExtra("ClassId", classId)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
        getMateriData()
    }

    fun getMateriData() {
        val queue: RequestQueue = Volley.newRequestQueue(this)
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
                    paramV["MaterialId"] = materialId.toString()
                    Log.d("Detail_Material Param", "ParamV: $paramV")
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
                val classId = classObj.getInt("classId")
                val materialName = classObj.getString("materialName")
                val materialDesc = classObj.getString("materialDesc")
                val uploadDate = classObj.getString("uploadDate")
                val attachment = classObj.getString("attachment")

                val formattedDate  = formatDate(uploadDate)

                val materi = Materi(
                    materialId,
                    classId,
                    materialName,
                    materialDesc,
                    formattedDate,
                    attachment
                )
                material.add(materi)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.e("Detail_Material parseTask", "Error parsing JSON: ${e.message}")
            // Handle JSON parsing error here
        }

        return material
    }
    private fun showMaterial(material: ArrayList<Materi>) {
        attachmentArrayList = ArrayList(material)
        adapter.setMaterial(attachmentArrayList)
        adapter.notifyDataSetChanged()
        attachmentTeacherRecView.visibility = View.VISIBLE
        if (attachmentArrayList.isNotEmpty()) {
            val firstTask = attachmentArrayList[0]
            txtMaterialName.text = firstTask.nama_Materi
            txtMaterialDesc.text = firstTask.deskripsi_materi
        } else {
            // Handle jika tidak ada tugas
        }
    }
    private fun formatDate(dateStr: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateStr)
        return outputFormat.format(date)
    }
}