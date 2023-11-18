package detail_kelas

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import smt3.assignme_11.timeline.TaskListFragment

class DetailKelasFragmentAdapter (fragment:Fragment) :FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int=3

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> TaskListFragment.newInstance("Task")
            1 -> TaskListFragment.newInstance("Material")
            2 -> TaskListFragment.newInstance("People")
            else -> throw IllegalArgumentException("Posisi tidak valid: $position")
        }
    }

}