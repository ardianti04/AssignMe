package smt3.assignme_11.timeline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import smt3.assignme_11.TaskRecViewAdapter
import smt3.assignme_11.R

class Fragment_2_timeline : Fragment() {

    private lateinit var containerFragment: FrameLayout
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private var fragmentList: ArrayList<Fragment> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_2_timeline, container, false)
        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager2 = view.findViewById(R.id.viewPager)
        containerFragment = view.findViewById(R.id.containerFragment)

        // Inisialisasi ViewPager dengan FragmentStateAdapter
        val timelineFragmentStateAdapter  = TimelineFragmentStateAdapter(this)
        viewPager2.adapter = timelineFragmentStateAdapter


        // Menyambungkan TabLayout ke ViewPager
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            // Anda dapat menyesuaikan judul tab berdasarkan posisi jika diperlukan
            tab.text = when (position) {
                0 -> "To-Do"
                1 -> "Completed"
                2 -> "Overdue"
                else -> ""
            }
        }.attach()

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> tl_1_todo()
                    1 -> tl_2_completed()
                    2 -> tl_3_overdue()
                    else -> throw IllegalArgumentException("Invalid position: $position")
                }
            }
        })

        return view
    }

    private fun showFragment(fragment: Fragment): Fragment {
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.containerFragment, fragment)
        transaction.commit()
        return fragment
    }

    // Adapter ViewPager
    private inner class TimelineFragmentStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 3 // Jumlah tab

        override fun createFragment(position: Int): Fragment {
            // Membuat dan mengembalikan fragment yang berbeda berdasarkan posisi
            return when (position) {
                0 -> tl_1_todo()
                1 -> tl_2_completed()
                2 -> tl_3_overdue()
                else -> throw IllegalArgumentException("Posisi tidak valid: $position")
            }
        }
    }
}

/*0 -> TaskListFragment.newInstance("To-Do")
                1 -> TaskListFragment.newInstance("Completed")
                2 -> TaskListFragment.newInstance("Overdue")*/