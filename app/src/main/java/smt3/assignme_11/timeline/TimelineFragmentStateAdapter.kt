package smt3.assignme_11.timeline

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import smt3.assignme_11.timeline.TaskListFragment


class TimelineFragmentStateAdapter (fragment: Fragment) : FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        // Membuat dan mengembalikan fragment yang berbeda berdasarkan posisi
        return when (position) {
            0 -> TaskListFragment.newInstance("To-Do")
            1 -> TaskListFragment.newInstance("Completed")
            2 -> TaskListFragment.newInstance("Overdue")
            else -> throw IllegalArgumentException("Posisi tidak valid: $position")
        }
    }
}