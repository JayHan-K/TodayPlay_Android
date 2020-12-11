package co.kr.todayplay.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.PerformPagerAdapter;
import co.kr.todayplay.adapter.PerformTagAdapter;

public class PerformInfoFragment extends Fragment {
    RecyclerView tag_rv;
    TabLayout tabLayout;
    ViewPager viewPager;
    PerformPagerAdapter performPagerAdapter;
    public PerformInfoFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_perform_prac, container, false);
        tabLayout = (TabLayout)viewGroup.findViewById(R.id.tabLayout);
        tag_rv = (RecyclerView)viewGroup.findViewById(R.id.tag_rv);
        viewPager = (ViewPager)viewGroup.findViewById(R.id.viewPager);

        tag_rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        ArrayList<String> data = new ArrayList<>();
        data.add("세계4대뮤지컬");
        data.add("로맨스");
        data.add("드라마");
        data.add("가면");
        data.add("대극장");
        data.add("세계4대뮤지컬");
        PerformTagAdapter performTagAdapter = new PerformTagAdapter(data);
        tag_rv.setAdapter(performTagAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("후기 분석"));
        tabLayout.addTab(tabLayout.newTab().setText("상세 정보"));
        tabLayout.addTab(tabLayout.newTab().setText("영상 자료"));
        tabLayout.addTab(tabLayout.newTab().setText("History"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        performPagerAdapter = new PerformPagerAdapter(getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(performPagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                performPagerAdapter.notifyDataSetChanged();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }

        });

        return viewGroup;
    }
}
