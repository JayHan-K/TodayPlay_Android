package co.kr.todayplay.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

import co.kr.todayplay.R;
import co.kr.todayplay.adapter.CategoryPagerAdapter;

public class CategoryFragment extends Fragment {
    ViewPager2 viewPager2;
    CategoryPagerAdapter adapter;
    ConstraintLayout linearlayout5;
    FrameLayout ProfileFragmentChildFragment3;

    public CategoryFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.activity_category,container,false);

        final TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        linearlayout5 = rootView.findViewById(R.id.linearLayout5);
        ProfileFragmentChildFragment3 = rootView.findViewById(R.id.pf_fragment_child_fragment3);
        tabLayout.addTab((tabLayout.newTab().setText("전체")));
        tabLayout.addTab((tabLayout.newTab().setText("뮤지컬")));
        tabLayout.addTab((tabLayout.newTab().setText("연극")));
        tabLayout.addTab((tabLayout.newTab().setText("공연중")));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final List<String> tabElement = Arrays.asList("전체","뮤지컬","연극","공연중");

        adapter = new CategoryPagerAdapter(this,tabLayout.getTabCount());
        viewPager2 = (ViewPager2)rootView.findViewById(R.id.viewcategory);
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabElement.get(position));


            }
        }).attach();


        return rootView;
    }
    public void categoryChangeToclicked(){
        getChildFragmentManager().beginTransaction().replace(
                R.id.pf_fragment_child_fragment3,
                new CategoryClickedFragment()
        ).commitAllowingStateLoss();
        linearlayout5.setVisibility(View.GONE);
        ProfileFragmentChildFragment3.setVisibility(View.VISIBLE);

    }
    public void BackToHome(){
        ProfileFragmentChildFragment3.setVisibility(View.GONE);
        linearlayout5.setVisibility(View.VISIBLE);
    }

}
