package smt3.assignme_11.timeline

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
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
import java.util.Locale

class tl_2_completed : Fragment() {


    private lateinit var completedRecView: RecyclerView
    private lateinit var onTimeAdapter: CompletedRecViewAdapter
    private lateinit var lateAdapter: ComplatedLateRecViewAdapter
    private lateinit var taskOntimeArrayList:ArrayList<Tugas>
    private lateinit var tasklateArrayList:ArrayList<Tugas>
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tl_2_completed, container, false)
        sharedPreferences =
            requireContext().getSharedPreferences("MyAppName", AppCompatActivity.MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("Email", "")
        Log.d("Email_Debug", "User Email: $userEmail")

        completedRecView = view.findViewById<RecyclerView?>(R.id.completedRecView)
        completedRecView.layoutManager = LinearLayoutManager(requireContext())

        onTimeAdapter = CompletedRecViewAdapter(requireContext())
        lateAdapter = ComplatedLateRecViewAdapter(requireContext())

        val concatAdapter = ConcatAdapter(onTimeAdapter, lateAdapter)
        completedRecView.adapter = concatAdapter


        getTasksLateFromServer()
        getTasksOnTimeFromServer()
        return view

    }
    private fun getTasksOnTimeFromServer() {
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val url = Db_User.urlshowCompletedAndOnTime

        val stringRequest: StringRequest = object : StringRequest(
            com.android.volley.Request.Method.POST, url,
            object : com.android.volley.Response.Listener<String?> {
                override fun onResponse(response: String?) {
                    Log.d("Response_Debug", "Raw Response: $response")
                    response?.let {
                        if (it.isNotEmpty()) {
                            val taskOnTime = parseTaskOnTime(it)
                            showTaskClasses(taskOnTime)
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
    private fun parseTaskOnTime(response: String): ArrayList<Tugas> {
        val taskOnTime = ArrayList<Tugas>()

        try {
            val jsonResponse = JSONObject(response)
            val userClassesArray = jsonResponse.getJSONArray("userClasses")

            for (i in 0 until userClassesArray.length()) {
                val taskObj = userClassesArray.getJSONObject(i)
                val taskId = taskObj.getInt("TaskId")
                val taskName = taskObj.getString("TaskName")
                val taskDesc = taskObj.getString("ClassName")
                val dueDateStr  = taskObj.getString("DueDate")

                val formattedDate  = formatDate(dueDateStr)

                // Create Kelas object and add to the list
                val tugas = Tugas(
                    taskId,
                    taskName,
                    taskDesc,
                    "",
                    0,
                    formattedDate
                )
                taskOnTime.add(tugas)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            // Handle JSON parsing error here
        }

        return taskOnTime
    }

    // Fungsi untuk menampilkan daftar kelas yang telah di-join menggunakan RecyclerView
    private fun showTaskClasses(taskOnTime: ArrayList<Tugas>) {
        taskOntimeArrayList = ArrayList(taskOnTime)
        onTimeAdapter.setTugas(taskOntimeArrayList)
        onTimeAdapter.notifyDataSetChanged()
        completedRecView.visibility = View.VISIBLE
        if (taskOntimeArrayList.isEmpty()) {
            // Handle UI jika tidak ada kelas yang di-join
        }
    }
    private fun getTasksLateFromServer() {
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val url = Db_User.urlshowCompletedAndLate

        val stringRequest: StringRequest = object : StringRequest(
            com.android.volley.Request.Method.POST, url,
            object : com.android.volley.Response.Listener<String?> {
                override fun onResponse(response: String?) {
                    response?.let {
                        Log.d("Response_Debug", "Raw Response: $response")
                        if (it.isNotEmpty()) {
                            val taskLate = parseTaskLate(it)
                            showTaskLateClasses(taskLate)
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
    private fun parseTaskLate(response: String): ArrayList<Tugas> {
        val taskLate = ArrayList<Tugas>()

        try {
            val jsonResponse = JSONObject(response)
            val userClassesArray = jsonResponse.getJSONArray("userClasses")

            for (i in 0 until userClassesArray.length()) {
                val taskObj = userClassesArray.getJSONObject(i)
                val taskId = taskObj.getInt("TaskId")
                val taskName = taskObj.getString("TaskName")
                val taskDesc = taskObj.getString("ClassName")
                val dueDateStr  = taskObj.getString("DueDate")

                val formattedDate  = formatDate(dueDateStr)

                // Create Kelas object and add to the list
                val tugas = Tugas(
                    taskId,
                    taskName,
                    taskDesc,
                    "",
                    0,
                    formattedDate
                )
                taskLate.add(tugas)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            // Handle JSON parsing error here
        }

        return taskLate
    }

    // Fungsi untuk menampilkan daftar kelas yang telah di-join menggunakan RecyclerView
    private fun showTaskLateClasses(taskLate: ArrayList<Tugas>) {
        tasklateArrayList = ArrayList(taskLate)
        lateAdapter.setTugas(tasklateArrayList)
        lateAdapter.notifyDataSetChanged()
        completedRecView.visibility = View.VISIBLE
        if (tasklateArrayList.isEmpty()) {
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
