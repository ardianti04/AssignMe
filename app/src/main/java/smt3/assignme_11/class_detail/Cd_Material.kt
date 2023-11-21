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
 * Use the [Cd_Material.newInstance] factory method to
 * create an instance of this fragment.
 */
class Cd_Material : Fragment() {
    private lateinit var materialRecView: RecyclerView
    private lateinit var adapter: MaterialRecViewAdapter
    private lateinit var sharedPreferences: SharedPreferences


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_cd__material, container, false)
        sharedPreferences = requireContext().getSharedPreferences("MyAppName", AppCompatActivity.MODE_PRIVATE)


        materialRecView = view.findViewById<RecyclerView?>(R.id.materialRecView)
        materialRecView.layoutManager = LinearLayoutManager(requireContext())

        adapter = MaterialRecViewAdapter(requireContext())
        materialRecView.adapter = adapter

        val materi = getMateriData()
        adapter.setMateris(materi)

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

    fun getMateriData(): ArrayList<Materi>? {
        val materi = ArrayList<Materi>()
        materi.add(
            Materi(
                1,
                "Matematika Diskrit",
                "6 Juni 2023"
            )
        )
        materi.add(
            Materi(
                2,
                "Limit",
                "7 Juni 2023"
            )
        )

        return materi
    }
}