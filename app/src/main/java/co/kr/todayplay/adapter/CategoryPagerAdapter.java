package co.kr.todayplay.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import co.kr.todayplay.fragment.category.CategoryCurrent;
import co.kr.todayplay.fragment.category.CategoryMusical;
import co.kr.todayplay.fragment.category.CategoryPlay;
import co.kr.todayplay.fragment.category.CategoryTotal;


public class CategoryPagerAdapter extends FragmentStateAdapter {
    int num;

    public CategoryPagerAdapter(Fragment fm, int num){
        super(fm);
        this.num = num;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new CategoryTotal();

            case 1:
                return new CategoryMusical();

            case 2:
                return new CategoryPlay();

            case 3:
                return new CategoryCurrent();
            default:
                return null;

        }
    }

    @Override
    public int getItemCount() {
        return num;
    }

}