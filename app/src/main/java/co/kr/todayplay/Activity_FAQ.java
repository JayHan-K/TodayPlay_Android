package co.kr.todayplay;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

import co.kr.todayplay.adapter.CategoryPagerAdapter;
import co.kr.todayplay.adapter.FAQPagerAdapter;

public class Activity_FAQ extends AppCompatActivity {
    ViewPager2 viewPager2;
    FAQPagerAdapter adapter;
    private TabLayout tabLayout;
    Button button9;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        tabLayout = findViewById(R.id.tabLayout2);
        View viewFrist =getLayoutInflater().inflate(R.layout.custom_tab,null);
        TextView tabOne = viewFrist.findViewById(R.id.textView60);
        tabOne.setText("운영정책");
        TabLayout.Tab tab1 = tabLayout.newTab();
        tab1.setCustomView(viewFrist);

        View viewsecond =getLayoutInflater().inflate(R.layout.custom_tab,null);
        TextView tabtwo = viewsecond.findViewById(R.id.textView60);
        tabtwo.setText("이벤트");
        TabLayout.Tab tab2 = tabLayout.newTab();
        tab2.setCustomView(viewsecond);

        View viewthird =getLayoutInflater().inflate(R.layout.custom_tab,null);
        TextView tabthree = viewthird.findViewById(R.id.textView60);
        tabthree.setText("서비스 문의/오류");
        TabLayout.Tab tab3 = tabLayout.newTab();
        tab3.setCustomView(viewthird);

        tabLayout.addTab(tab1);
        tabLayout.addTab(tab2);
        tabLayout.addTab(tab3);



//        tabLayout.addTab((tabLayout.newTab().setText("운영정책")));
//        tabLayout.addTab((tabLayout.newTab().setText("이벤트")));
//        tabLayout.addTab((tabLayout.newTab().setText("서비스 문의/오류")));

        final List<String> tabElement = Arrays.asList("운영정책","이벤트","서비스 문의/오류");

        adapter = new FAQPagerAdapter(this,tabLayout.getTabCount());

        viewPager2 = findViewById(R.id.faq_vp);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                tab.setText(tabElement.get(position));
//                tab.setCustomView()

            }
        }).attach();
        setupTabIcons();
        button9 = (Button)findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
    private void setupTabIcons(){
        View viewFrist =getLayoutInflater().inflate(R.layout.custom_tab,null);
        TextView tabOne = viewFrist.findViewById(R.id.textView60);
        tabOne.setText("운영정책");
        tabOne.setGravity(Gravity.CENTER);
        tabLayout.getTabAt(0).setCustomView(viewFrist);
        View viewsecond =getLayoutInflater().inflate(R.layout.custom_tab,null);
        TextView tabtwo = viewsecond.findViewById(R.id.textView60);
        tabtwo.setText("이벤트");
        tabtwo.setGravity(Gravity.CENTER);
        tabLayout.getTabAt(1).setCustomView(viewsecond);
        View viewthird =getLayoutInflater().inflate(R.layout.custom_tab,null);
        TextView tabthree = viewthird.findViewById(R.id.textView60);
        tabthree.setText("서비스 문의/오류");
        tabLayout.getTabAt(2).setCustomView(viewthird);
        tabthree.setGravity(Gravity.CENTER);
    }





//

}
