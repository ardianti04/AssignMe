package smt3.assignme_11

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment_2_timeline.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment_2_timeline : Fragment() {

    private lateinit var taskRecView: RecyclerView
    private lateinit var adapter: TaskRecViewAdapter

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_2_timeline, container, false)

        tabLayout = view.findViewById<TabLayout>(R.id.tabLayoutTimeline)
        viewPager = view.findViewById<ViewPager2>(R.id.viewPagerTimeline)

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                /*if (tab.position == 0) {
                    val intent = Intent(requireActivity(), All_Class_Work::class.java)
                    startActivity(intent)
                }*/
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


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
         * @return A new instance of fragment Fragment_2_timeline.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment_2_timeline().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getTaskData(): ArrayList<Tugas>? {
        val tugas = ArrayList<Tugas>()
        tugas.add(
            Tugas(
                1,
                "Matematika",
                "Tugas Bab 1",
                "Jun 25 2023",
                "https://image.gambarpng.id/pngs/gambar-transparent-perlengkapan-belajar-matematika_56394.png"
            )
        )
        tugas.add(
            Tugas(
                2,
                "Bahasa Indonesia",
                "Tugas Merangkum Bab 2",
                "29 Juni 2023",
                "https://image.gambarpng.id/pngs/gambar-transparent-perlengkapan-belajar-matematika_56394.png"
            )
        )
        return tugas
    }
}