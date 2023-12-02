package smt3.assignme_11.class_detail

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayout
import org.json.JSONException
import org.json.JSONObject
import smt3.assignme_11.Db_User
import smt3.assignme_11.Kelas
import smt3.assignme_11.R
import java.text.SimpleDateFormat
import java.util.Locale


class Cd_Task : Fragment() {

    private lateinit var taskRecView: RecyclerView
    private lateinit var adapter: TaskRecViewAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var tasksArrayList: java.util.ArrayList<Tugas>

    companion object {
        fun newInstance(classId: Int): Cd_Task {
            val fragment = Cd_Task()
            val args = Bundle()
            args.putInt("ClassId", classId)
            fragment.arguments = args
            Log.d("Cd_Task", "ClassId in newInstance: $classId")
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_cd__task, container, false)
        sharedPreferences = requireContext().getSharedPreferences("MyAppName", AppCompatActivity.MODE_PRIVATE)
        val username = sharedPreferences.getString("Username", "")
        val email = sharedPreferences.getString("Email", "")
        val apiKey = sharedPreferences.getString("apiKey", "")

        taskRecView = view.findViewById<RecyclerView?>(R.id.teacherRecView)
        taskRecView.layoutManager = LinearLayoutManager(requireContext())

        adapter = TaskRecViewAdapter(requireContext())
        taskRecView.adapter = adapter

        getTaskData()

        return view
    }

    fun getTaskData() {
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
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
                arguments?.getInt("ClassId")?.let { classId ->
                    paramV["ClassId"] = classId.toString()
                    Log.d("Cd_Task Param", "ParamV: $paramV")
                }
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

                val formattedDate  = formatDate(dueDate)

                val tugas = Tugas(
                    taskId,
                    taskName,
                    taskDesc,
                    formattedDate
                )
                tasks.add(tugas)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.e("Cd_Task parseTask2", "Error parsing JSON: ${e.message}")
            // Handle JSON parsing error here
        }

        return tasks
    }

    private fun showTask(tasks: ArrayList<Tugas>) {
        tasksArrayList = ArrayList(tasks)
        adapter.setTugas(tasksArrayList)
        adapter.notifyDataSetChanged()
        taskRecView.visibility = View.VISIBLE
        if (tasksArrayList.isEmpty()) {
        }
    }
    private fun formatDate(dateStr: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateStr)
        return outputFormat.format(date)
    }
}