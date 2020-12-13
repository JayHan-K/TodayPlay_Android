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
                CategoryTotal tab1 = new CategoryTotal();
                return tab1;

            case 1:
                CategoryMusical tab2 = new CategoryMusical();
                return tab2;

            case 2:
                CategoryPlay tab3 = new CategoryPlay();
                return tab3;

            case 3:
                CategoryCurrent tab4 = new CategoryCurrent();
                return tab4;
            default:
                return null;

        }
    }

    @Override
    public int getItemCount() {
        return num;
    }

}