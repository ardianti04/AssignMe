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
import smt3.assignme_11.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Cd_Task.newInstance] factory method to
 * create an instance of this fragment.
 */
class Cd_Task : Fragment() {

    private lateinit var taskRecView: RecyclerView
    private lateinit var adapter: TaskRecViewAdapter
    private lateinit var sharedPreferences: SharedPreferences


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_cd__task, container, false)
        sharedPreferences = requireContext().getSharedPreferences("MyAppName", AppCompatActivity.MODE_PRIVATE)


        taskRecView = view.findViewById<RecyclerView?>(R.id.taskRecView)
        taskRecView.layoutManager = LinearLayoutManager(requireContext())

        adapter = TaskRecViewAdapter(requireContext())
        taskRecView.adapter = adapter

        val tugas = getTaskData()
        adapter.setTugas(tugas)

        return view

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Cd_Task.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Cd_Task().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun getTaskData(): ArrayList<Tugas>? {
        val tugas = ArrayList<Tugas>()
        tugas.add(
            Tugas(
                1,
                "Matematika",
                "Tugas Mencatat",
                "6 Juni 2023",
                R.drawable.assignment_icon
            )
        )
        tugas.add(
            Tugas(
                2,
                "Bahasa indonesia",
                "Tugas Mencatat",
                "7 Juni 2023",
                R.drawable.assignment_icon
            )
        )

        return tugas
    }
}