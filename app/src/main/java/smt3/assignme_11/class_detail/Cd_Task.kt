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


class Cd_Task : Fragment() {

    private lateinit var taskRecView: RecyclerView
    private lateinit var adapter: TaskRecViewAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var txt: TextView
    private var classId = -1


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

        classId = arguments?.getInt("ClassId", -1) ?: -1
        Log.d("Cd_Task", "Received classId in Cd task: $classId")
        //val tugas = getTaskDat()
        //adapter.setTugas(tugas)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = requireActivity().findViewById<TabLayout>(R.id.tabLayout) // Sesuaikan dengan ID tabLayout Anda
        val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager) // Sesuaikan dengan ID viewPager Anda

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0 && isAdded && isVisible && viewPager.currentItem == 0) {
                    getTaskData()
                }
            }
        })
    }

    private fun getTaskData() {
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val url = Db_User.urlShowTask

        val stringRequest: StringRequest = object : StringRequest(
            com.android.volley.Request.Method.POST, url,
            object : com.android.volley.Response.Listener<String?> {
                override fun onResponse(response: String?) {
                    response?.let {
                        Log.d("Response_Debug", "Raw Response: $response")
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
                    error.printStackTrace()
                    // Handle error responses here
                }
            }) {
            override fun getParams(): Map<String, String?> {
                val paramV: MutableMap<String, String?> = HashMap()
                paramV["ClassId"] = classId.toString()
                return paramV
            }
        }
        queue.add(stringRequest)
    }

    private fun parseTask(response: String): ArrayList<Tugas> {
        val tasks = ArrayList<Tugas>()

        try {
            // Pengecekan apakah respons adalah JSON yang valid
            if(response.startsWith("{") && response.endsWith("}")) {
                val jsonResponse = JSONObject(response)
                val tasksArray = jsonResponse.getJSONArray("tasks")

                for (i in 0 until tasksArray.length()) {
                    val classObj = tasksArray.getJSONObject(i)
                    val taskId = classObj.getInt("taskId")
                    val taskName = classObj.getString("taskName")
                    val taskDesc = classObj.getString("taskDesc")
                    val dueDate = classObj.getString("dueDate")
                    // Create Kelas object and add to the list
                    val tugas = Tugas(
                        taskId,
                        taskName,
                        taskDesc,
                        dueDate
                    )
                    tasks.add(tugas)
                }
            } else {
                // Respons bukan JSON yang valid, lakukan penanganan kesalahan
                Log.e("Cd_Task", "Response is not a valid JSON format")
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            // Handle JSON parsing error here
            Log.e("Cd_Task", "Error parsing JSON")
        }
        return tasks
    }

    private fun showTask(tasks: ArrayList<Tugas>) {
        val tasksArrayList = ArrayList(tasks)
        adapter.setTugas(tasksArrayList)
        adapter.notifyDataSetChanged()
        taskRecView.visibility = View.VISIBLE
        if (tasksArrayList.isEmpty()) {
        }
    }

    fun getTaskDat(): ArrayList<Tugas>? {
        val tugas = ArrayList<Tugas>()
        tugas.add(
            Tugas(
                1,
                "Matematika",
                "Tugas Mencatat",
                "6 Juni 2023",
            )
        )
        tugas.add(
            Tugas(
                2,
                "Bahasa indonesia",
                "Tugas Mencatat",
                "7 Juni 2023"
            )
        )

        return tugas
    }
}