package smt3.assignme_11.class_detail;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ClassDetailPagerAdapter extends FragmentStateAdapter {

    public ClassDetailPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Cd_Task();
            case 1:
                return new Cd_Material();
            case 2:
                return new Cd_People();
            default:
                return new Cd_Task();

        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
