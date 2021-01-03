package co.kr.todayplay.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import co.kr.todayplay.fragment.Profile.ProfileIng;
import co.kr.todayplay.fragment.category.CategoryCurrent;
import co.kr.todayplay.fragment.category.CategoryMusical;
import co.kr.todayplay.fragment.category.CategoryPlay;
import co.kr.todayplay.fragment.category.CategoryTotal;


public class pf_myPickPagerAdapter extends FragmentStateAdapter {
    int num;

    public pf_myPickPagerAdapter(Fragment fm, int num){
        super(fm);
        this.num = num;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                ProfileIng tab1 = new ProfileIng();
                return tab1;

            case 1:
                ProfileIng tab2 = new ProfileIng();
                return tab2;

            case 2:
                ProfileIng tab3 = new ProfileIng();
                return tab3;

            default:
                return null;

        }
    }

    @Override
    public int getItemCount() {
        return num;
    }

}