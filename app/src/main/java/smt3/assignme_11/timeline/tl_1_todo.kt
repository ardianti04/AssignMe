package smt3.assignme_11.timeline

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
import smt3.assignme_11.class_detail.Tugas
import java.text.SimpleDateFormat
import java.util.*


class tl_1_todo : Fragment() {
    private lateinit var todoRecView: RecyclerView
    private lateinit var adapter: TodoRecViewAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var taskArrayList:ArrayList<Tugas>


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tl_1_todo, container, false)
        sharedPreferences =
            requireContext().getSharedPreferences("MyAppName", AppCompatActivity.MODE_PRIVATE)


        todoRecView = view.findViewById<RecyclerView?>(R.id.todoRecView)
        todoRecView.layoutManager = LinearLayoutManager(requireContext())

        adapter = TodoRecViewAdapter(requireContext())
        todoRecView.adapter = adapter

        getTasksFromServer()

        return view

    }
    private fun getTasksFromServer() {
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val url = Db_User.urlShowTodo

        val stringRequest: StringRequest = object : StringRequest(
            com.android.volley.Request.Method.POST, url,
            object : com.android.volley.Response.Listener<String?> {
                override fun onResponse(response: String?) {
                    response?.let {
                        if (it.isNotEmpty()) {
                            val taskClasses = parseTaskClasses(it)
                            showTaskClasses(taskClasses)
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
    private fun parseTaskClasses(response: String): ArrayList<Tugas> {
        val taskClasses = ArrayList<Tugas>()

        try {
            val jsonResponse = JSONObject(response)
            val userClassesArray = jsonResponse.getJSONArray("userClasses")

            for (i in 0 until userClassesArray.length()) {
                val taskObj = userClassesArray.getJSONObject(i)
                val taskId = taskObj.getInt("TaskId")
                val taskName = taskObj.getString("TaskName")
                val taskDesc = taskObj.getString("TaskDesc")
                val dueDateStr  = taskObj.getString("DueDate")

                val formattedDate  = formatDate(dueDateStr)

                // Create Kelas object and add to the list
                val tugas = Tugas(
                    taskId,
                    taskName,
                    taskDesc,
                    formattedDate
                )
                taskClasses.add(tugas)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            // Handle JSON parsing error here
        }

        return taskClasses
    }

    // Fungsi untuk menampilkan daftar kelas yang telah di-join menggunakan RecyclerView
    private fun showTaskClasses(taskClasses: ArrayList<Tugas>) {
        taskArrayList = ArrayList(taskClasses)
        adapter.setTugas(taskArrayList)
        adapter.notifyDataSetChanged()
        todoRecView.visibility = View.VISIBLE
        if (taskArrayList.isEmpty()) {
            // Handle UI jika tidak ada kelas yang di-join
        }
    }
    private fun formatDate(dateStr: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateStr)
        return outputFormat.format(date)
    }
}
