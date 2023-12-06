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
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import smt3.assignme_11.Db_User
import smt3.assignme_11.R


class Cd_People : Fragment() {
    private lateinit var teacherRecView: RecyclerView
    private lateinit var studentRecView: RecyclerView
    private lateinit var teacherAdapter: PeopleRecViewAdapter
    private lateinit var studentAdapter: PeopleRecViewAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var teachersArrayList: java.util.ArrayList<User>
    private lateinit var studentsArrayList: java.util.ArrayList<User>

    companion object {
        fun newInstance(classId: Int): Cd_People {
            val fragment = Cd_People()
            val args = Bundle()
            args.putInt("ClassId", classId)
            fragment.arguments = args
            Log.d("Cd_People", "ClassId in newInstance: $classId")
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_cd__people, container, false)
        sharedPreferences = requireContext().getSharedPreferences("MyAppName", AppCompatActivity.MODE_PRIVATE)



        teacherRecView = view.findViewById<RecyclerView?>(R.id.teacherRecView)
        teacherRecView.layoutManager = LinearLayoutManager(requireContext())
        studentRecView = view.findViewById<RecyclerView?>(R.id.studentRecView)
        studentRecView.layoutManager = LinearLayoutManager(requireContext())

        teacherAdapter = PeopleRecViewAdapter(requireContext())
        studentAdapter= PeopleRecViewAdapter(requireContext())

        teacherRecView.adapter = teacherAdapter
        studentRecView.adapter = studentAdapter

       getTeacherData()
       getStudentData()


        return view

    }
    fun getTeacherData() {
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val url = Db_User.urlShowTeacher

        val stringRequest: StringRequest = object : StringRequest(
            com.android.volley.Request.Method.POST, url,
            object : com.android.volley.Response.Listener<String?> {
                override fun onResponse(response: String?) {
                    response?.let {
                        Log.d("Response_Debug response", "Raw Response: $response")
                        if (it.isNotEmpty()) {
                            val teachers = parseTeachers(it)
                            showTeachers(teachers)
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
                    Log.d("Cd_People Param", "ParamV: $paramV")
                }
                return paramV
            }
        }
        queue.add(stringRequest)
    }
    private fun parseTeachers(response: String): ArrayList<User> {
        val teachers = ArrayList<User>()

        try {
            val jsonResponse = JSONObject(response)
            val teachersArray = jsonResponse.getJSONArray("teachers")

            for (i in 0 until teachersArray.length()) {
                val classObj = teachersArray.getJSONObject(i)
                val userId = classObj.getInt("userId")
                val userName = classObj.getString("userName")

                val users = User(
                    userId,
                    userName,
                    "",
                    ""
                )
                teachers.add(users)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.e("Cd_People parseTask2", "Error parsing JSON: ${e.message}")
            // Handle JSON parsing error here
        }

        return teachers
    }

    private fun showTeachers(teachers: ArrayList<User>) {
        teachersArrayList = ArrayList(teachers)
        teacherAdapter.setUsers(teachersArrayList)
        teacherAdapter.notifyDataSetChanged()
        teacherRecView.visibility = View.VISIBLE
        if (teachersArrayList.isEmpty()) {
        }
    }


    fun getStudentData() {
        val queue: RequestQueue = Volley.newRequestQueue(requireContext())
        val url = Db_User.urlShowStudent

        val stringRequest: StringRequest = object : StringRequest(
            com.android.volley.Request.Method.POST, url,
            object : com.android.volley.Response.Listener<String?> {
                override fun onResponse(response: String?) {
                    response?.let {
                        Log.d("Response_Debug response", "Raw Response: $response")
                        if (it.isNotEmpty()) {
                            val students = parseStudent(it)
                            showStudent(students)
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
                    Log.d("Cd_People Param", "ParamV: $paramV")
                }
                return paramV
            }
        }
        queue.add(stringRequest)
    }
    private fun parseStudent(response: String): ArrayList<User> {
        val students = ArrayList<User>()

        try {
            val jsonResponse = JSONObject(response)
            val studentsArray = jsonResponse.getJSONArray("students")

            for (i in 0 until studentsArray.length()) {
                val classObj = studentsArray.getJSONObject(i)
                val userId = classObj.getInt("userId")
                val userName = classObj.getString("userName")

                val users = User(
                    userId,
                    userName,
                    "",
                    ""
                )
                students.add(users)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.e("Cd_People parseTask2", "Error parsing JSON: ${e.message}")
            // Handle JSON parsing error here
        }

        return students
    }

    private fun showStudent(students: ArrayList<User>) {
        studentsArrayList = ArrayList(students)
        studentAdapter.setUsers(studentsArrayList)
        studentAdapter.notifyDataSetChanged()
        studentRecView.visibility = View.VISIBLE
        if (studentsArrayList.isEmpty()) {
        }
    }
}