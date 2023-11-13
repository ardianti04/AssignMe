package smt3.assignme_11

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment_1_home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment_1_home : Fragment() {

    private lateinit var classRecView: RecyclerView
    private lateinit var adapter: ClassRViewAdapter
    private lateinit var btnTambah: ImageButton


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
        val view =  inflater.inflate(R.layout.fragment_1_home, container, false)

        val btnTambah = view.findViewById<ImageButton>(R.id.btnTambah)
        val pressedColor = ContextCompat.getColor(requireContext(), R.color.black_900_7f)
        btnTambah.setOnClickListener {
            btnTambah.setColorFilter(pressedColor)
            val intent = Intent(requireContext(), JoinClassActivity::class.java)
            startActivity(intent)
        }

        classRecView = view.findViewById<RecyclerView?>(R.id.classRecView)
        classRecView.layoutManager = LinearLayoutManager(requireContext())

        adapter = ClassRViewAdapter(requireContext())
        classRecView.adapter = adapter

        val kelas = getClassData()
        adapter.setKelas(kelas)

        return view

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment_1_home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Fragment_1_home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }





    fun getClassData(): ArrayList<Kelas>? {
        val kelas = ArrayList<Kelas>()
        kelas.add(
            Kelas(
                1,
                "Kelas XII E",
                "E234",
                "Citra Kirana",
                "Matematika",
                "Kelas untuk siswa ini saja",
                "https://image.gambarpng.id/pngs/gambar-transparent-perlengkapan-belajar-matematika_56394.png"
            )
        )
        kelas.add(
            Kelas(
                2,
                "Kelas XII A",
                "A234",
                "Lusiana",
                "IPA",
                "Kelas untuk siswa ini saja",
                "https://primaindisoft.com/wp-content/uploads/2019/09/ipa.png"
            )
        )
        return kelas
    }
}