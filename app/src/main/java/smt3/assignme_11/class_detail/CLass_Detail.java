package smt3.assignme_11.class_detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import smt3.assignme_11.R;

public class CLass_Detail extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ClassDetailPagerAdapter classDetailPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);

        tabLayout=findViewById(R.id.tabLayout);
        viewPager2=findViewById(R.id.viewPager);
        classDetailPagerAdapter=new ClassDetailPagerAdapter(this);

        viewPager2.setAdapter(classDetailPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
    }
}