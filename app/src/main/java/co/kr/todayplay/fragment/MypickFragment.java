package co.kr.todayplay.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

import co.kr.todayplay.R;
import co.kr.todayplay.adapter.pf_myPickPagerAdapter;

public class MypickFragment extends Fragment {
    ViewPager2 viewPager2;
    pf_myPickPagerAdapter adapter;

    public MypickFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.activity_mypick,container,false);
        Button pf_to_profile = rootView.findViewById(R.id.back_profile4);

        final TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.pf_tab_ly);
        tabLayout.addTab((tabLayout.newTab().setText("공연중")));
        tabLayout.addTab((tabLayout.newTab().setText("공연예정")));
        tabLayout.addTab((tabLayout.newTab().setText("미정")));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final List<String> tabElement = Arrays.asList("공연중","공연예정","미정");

        adapter = new pf_myPickPagerAdapter(this,tabLayout.getTabCount());
        viewPager2 = (ViewPager2)rootView.findViewById(R.id.viewPager2);
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabElement.get(position));
            }
        }).attach();

        pf_to_profile.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ProfileFragment parentFrag = (ProfileFragment) MypickFragment.this.getParentFragment() ;
                parentFrag.BackToHome();
            }
        }));


        return rootView;
    }

}
