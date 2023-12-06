package smt3.assignme_11.task_detail

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
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
import smt3.assignme_11.class_detail.Tugas
import java.text.SimpleDateFormat
import java.util.Locale

class Detail_Tugas : AppCompatActivity() {
    private lateinit var backButton : ImageView
    private lateinit var btnUpload : Button
    private lateinit var txtDueDate: TextView
    private lateinit var txtTaskName: TextView
    private lateinit var txtTaskDesc: TextView
    private lateinit var relativeAttachment: RelativeLayout
    private lateinit var attachmentTeacherRecView: RecyclerView
    private lateinit var adapter: TaskAttachmentRecViewAdapter
    private lateinit var attachmentArrayList: java.util.ArrayList<Tugas>
    private var classId: Int = -1
    private var taskId: Int = -1
    private var attachment: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tugas)

        classId = intent.getIntExtra("ClassId", -1)
        Log.d("Detail_Tugas", "ClassId from Intent: $classId")
        taskId = intent.getIntExtra("TaskId", -1)
        Log.d("Detail_Tugas", "TaskId from Intent: $taskId")
        attachment = intent.getStringExtra("Attachment")
        Log.d("Detail_Tugas", "Attachment from Intent: $attachment")

        txtDueDate = findViewById(R.id.txtTglBerakhir)
        txtTaskName = findViewById(R.id.txtNamaMateri)
        txtTaskDesc = findViewById(R.id.txtDeskripsi)
        relativeAttachment = findViewById(R.id.RelativeAttachment)
        relativeAttachment.visibility = View.VISIBLE

        attachmentTeacherRecView = findViewById<RecyclerView>(R.id.attachment_teacher)
        attachmentTeacherRecView.layoutManager = LinearLayoutManager(this)
        adapter = TaskAttachmentRecViewAdapter(this)
        attachmentTeacherRecView.adapter = adapter

        btnUpload = findViewById(R.id.btnUpload)
        btnUpload.setOnClickListener {
            //val bottomSheetFragment = BS_submit_task.newInstance(classId, taskId)
            //bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
            val intent = Intent(this@Detail_Tugas, AnswerTask::class.java)
            intent.putExtra("TaskId", taskId)
            startActivity(intent)
        }

        backButton = findViewById(R.id.backButton)
        backButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@Detail_Tugas, CLass_Detail::class.java)
            intent.putExtra("ClassId", classId)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })

        getTaskData()
    }
    fun getTaskData() {
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = Db_User.urlShowTask

        val stringRequest: StringRequest = object : StringRequest(
            com.android.volley.Request.Method.POST, url,
            object : com.android.volley.Response.Listener<String?> {
                override fun onResponse(response: String?) {
                    response?.let {
                        Log.d("Response_Debug response", "Raw Response: $response")
                        if (it.isNotEmpty()) {
                            val tasks = parseTask(it)
                            showTask(tasks)
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
                    paramV["TaskId"] = taskId.toString()
                    Log.d("Detail_Tugas Param", "ParamV: $paramV")
                return paramV
            }
        }
        queue.add(stringRequest)
    }
    private fun parseTask(response: String): ArrayList<Tugas> {
        val tasks = ArrayList<Tugas>()

        try {
            val jsonResponse = JSONObject(response)
            val tasksArray = jsonResponse.getJSONArray("tasks")

            for (i in 0 until tasksArray.length()) {
                val classObj = tasksArray.getJSONObject(i)
                val taskId = classObj.getInt("taskId")
                val taskName = classObj.getString("taskName")
                val taskDesc = classObj.getString("taskDesc")
                val dueDate = classObj.getString("dueDate")
                val classId = classObj.getInt("ClassId")
                val attachment = classObj.getString("attachment")

                val formattedDate  = formatDate(dueDate)

                val tugas = Tugas(
                    taskId,
                    taskName,
                    taskDesc,
                    formattedDate,
                    classId,
                    attachment
                )
                tasks.add(tugas)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.e("Detail_Tugas parseTask2", "Error parsing JSON: ${e.message}")
            // Handle JSON parsing error here
        }

        return tasks
    }
    private fun showTask(tasks: ArrayList<Tugas>) {
        attachmentArrayList = ArrayList(tasks)
        adapter.setTugas(attachmentArrayList)
        adapter.notifyDataSetChanged()
        attachmentTeacherRecView.visibility = View.VISIBLE

        if (attachmentArrayList.isNotEmpty()) {
            val firstTask = attachmentArrayList[0]
            if (firstTask.attachment != null && firstTask.attachment.isNotEmpty()) {
                // Jika attachment tidak kosong, tampilkan
                relativeAttachment.visibility = View.VISIBLE
            } else {
                // Jika attachment kosong atau null, sembunyikan
                relativeAttachment.visibility = View.GONE
            }

            txtDueDate.text = firstTask.tgl_berakhir
            txtTaskName.text = firstTask.nama_mapel
            txtTaskDesc.text = firstTask.deskripsi_tugas
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