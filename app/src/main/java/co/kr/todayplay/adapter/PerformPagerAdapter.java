package co.kr.todayplay.adapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import co.kr.todayplay.fragment.category.CategoryMusical;
import co.kr.todayplay.fragment.category.CategoryPlay;
import co.kr.todayplay.fragment.category.CategoryTotal;
import co.kr.todayplay.fragment.perform.PerformDetailFragment;
import co.kr.todayplay.fragment.perform.PerformReviewFragment;

public class PerformPagerAdapter extends FragmentStatePagerAdapter {
    int num;

    public PerformPagerAdapter(FragmentManager fm, int num) {
        super(fm);
        this.num = num;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                PerformDetailFragment tab1 = new PerformDetailFragment();
                return tab1;

            case 1:
                PerformReviewFragment tab2 = new PerformReviewFragment();
                return tab2;

            case 2:
                PerformReviewFragment tab3 = new PerformReviewFragment();
                return tab3;

            case 3:
                PerformReviewFragment tab4 = new PerformReviewFragment();
                return tab4;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return num;
    }
}
