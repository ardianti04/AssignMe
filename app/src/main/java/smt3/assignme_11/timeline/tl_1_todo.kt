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
import smt3.assignme_11.R
import smt3.assignme_11.class_detail.Tugas


class tl_1_todo : Fragment() {
    private lateinit var todoRecView: RecyclerView
    private lateinit var adapter: TodoRecViewAdapter
    private lateinit var sharedPreferences: SharedPreferences


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

        val tugas = getTaskData()
        adapter.setTugas(tugas)

        return view

    }
    fun getTaskData(): ArrayList<Tugas>? {
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