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
import smt3.assignme_11.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Cd_People.newInstance] factory method to
 * create an instance of this fragment.
 */
class Cd_People : Fragment() {
    private lateinit var teacherRecView: RecyclerView
    private lateinit var studentRecView: RecyclerView
    private lateinit var teacherAdapter: PeopleRecViewAdapter
    private lateinit var studentAdapter: PeopleRecViewAdapter
    private lateinit var sharedPreferences: SharedPreferences

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

        val teacherData = getTeacherData()
        val studentData=getStudentData()

        teacherAdapter.setUsers(teacherData)
        studentAdapter.setUsers(studentData)

        return view

    }

    fun getTeacherData(): ArrayList<User>? {
        val teacherData = ArrayList<User>()
        teacherData.add(
            User(
                1,
                "Lestari",
                "lestari@gmail.com",
                "12345678"
            )
        )
        teacherData.add(
            User(
                2,
                "Indira",
                "Indira@gmail.com",
                "3333333"
            )
        )

        return teacherData
    }
    fun getStudentData(): ArrayList<User>? {
        val studentData = ArrayList<User>()
        studentData.add(
            User(
                1,
                "Ardianti",
                "ardianti@gmail.com",
                "62783839"
            )
        )
        studentData.add(
            User(
                2,
                "Tasya",
                "tasya@gmail.com",
                "8292993"
            )
        )
        studentData.add(
            User(
                3,
                "Lala",
                "lala@gmail.com",
                "28394949"
            )
        )
        studentData.add(
            User(
                4,
                "N",
                "lala@gmail.com",
                "28394949"
            )
        )

        return studentData
    }
}