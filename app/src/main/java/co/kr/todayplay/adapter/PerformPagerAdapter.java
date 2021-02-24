package co.kr.todayplay.adapter;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import co.kr.todayplay.fragment.Journal.JournalDetailFragment;
import co.kr.todayplay.fragment.category.CategoryMusical;
import co.kr.todayplay.fragment.category.CategoryPlay;
import co.kr.todayplay.fragment.category.CategoryTotal;
import co.kr.todayplay.fragment.perform.PerformDetailFragment;
import co.kr.todayplay.fragment.perform.PerformHistoryFragment;
import co.kr.todayplay.fragment.perform.PerformReviewFragment;
import co.kr.todayplay.fragment.perform.PerformVideoFragment;

public class PerformPagerAdapter extends FragmentStatePagerAdapter {
    int num;
    int play_id;

    public PerformPagerAdapter(FragmentManager fm, int num, int play_id) {
        super(fm);
        this.num = num;
        this.play_id = play_id;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Bundle bundle = new Bundle();
                bundle.putInt("play_id", this.play_id);
                PerformDetailFragment tab1 = new PerformDetailFragment();
                tab1.setArguments(bundle);
                return tab1;

            case 1:
                Bundle bundle2 = new Bundle();
                bundle2.putInt("play_id", this.play_id);
                PerformReviewFragment tab2 = new PerformReviewFragment();
                tab2.setArguments(bundle2);

                return tab2;

            case 2:
                Bundle bundle3 = new Bundle();
                bundle3.putInt("play_id", this.play_id);
                PerformVideoFragment tab3 = new PerformVideoFragment();
                tab3.setArguments(bundle3);

                return tab3;

            case 3:
                Bundle bundle4 = new Bundle();
                bundle4.putInt("play_id", this.play_id);
                PerformHistoryFragment tab4 = new PerformHistoryFragment();
                tab4.setArguments(bundle4);

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
