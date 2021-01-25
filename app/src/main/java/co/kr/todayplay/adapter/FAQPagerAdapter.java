package co.kr.todayplay.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import co.kr.todayplay.fragment.faq.faq_event;
import co.kr.todayplay.fragment.faq.faq_policy;


public class FAQPagerAdapter extends FragmentStateAdapter {
    int num;

    public FAQPagerAdapter(AppCompatActivity fm, int num){
        super(fm);
        this.num = num;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position){
        switch (position){
            case 0:
                faq_event tab1 = new faq_event();
                return tab1;
            case 1:
                faq_event tab2 = new faq_event();
                return tab2;
            case 2:
                faq_event tab3 = new faq_event();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() { return num; }
}
