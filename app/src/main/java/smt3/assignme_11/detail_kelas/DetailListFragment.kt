package smt3.assignme_11.detail_kelas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import smt3.assignme_11.R
import smt3.assignme_11.TaskRecViewAdapter
import smt3.assignme_11.Tugas
import smt3.assignme_11.timeline.TaskListFragment

class DetailListFragment :Fragment() {

    private lateinit var taskRecView: RecyclerView
    private lateinit var adapter: TaskRecViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_task_list, container, false)

        // Inisialisasi RecyclerView dan adapter
        taskRecView = view.findViewById(R.id.taskRecView)
        taskRecView.layoutManager = LinearLayoutManager(requireContext())
        adapter = TaskRecViewAdapter(requireContext())
        taskRecView.adapter = adapter

        // Mendapatkan data tugas dan mengatur adapter
        return view
    }
    private fun getTaskData(): ArrayList<Tugas> {
        // Mengembalikan data tugas (sesuaikan dengan kebutuhan Anda)
        // Contoh sederhana, Anda dapat mengganti dengan logika pengambilan data yang sesungguhnya.
        val tugas = ArrayList<Tugas>()
        tugas.add(Tugas(1, "Tugas 1", "Deskripsi Tugas 1", "Due Date 1",R.drawable.assignment_icon))
        tugas.add(Tugas(2, "Tugas 2", "Deskripsi Tugas 2", "Due Date 2",R.drawable.assignment_icon))
        // Tambahkan lebih banyak tugas jika diperlukan
        return tugas
    }
    companion object {
        @JvmStatic
        fun newInstance(taskType: String): TaskListFragment {
            val fragment = TaskListFragment()
            val args = Bundle()
            args.putString(ARG_TASK_TYPE, taskType.toString())
            fragment.arguments = args
            return fragment
        }

        private const val ARG_TASK_TYPE = "taskType"
    }

}