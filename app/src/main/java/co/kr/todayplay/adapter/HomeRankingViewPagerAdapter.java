package co.kr.todayplay.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

import co.kr.todayplay.fragment.home.HomeRankingFragmentFirst;
import co.kr.todayplay.object.Ranking;

public class HomeRankingViewPagerAdapter extends FragmentStateAdapter {
    public int mCount;
    ArrayList<Ranking> rankings;

    public HomeRankingViewPagerAdapter(Fragment fa, int count,ArrayList<Ranking> ranking_chosen) {
        super(fa);
        mCount = count;
        this.rankings = ranking_chosen;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        if(index==0) return new HomeRankingFragmentFirst(rankings,index);
        else return new HomeRankingFragmentFirst(rankings,index);
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public int getRealPosition(int position) { return position % mCount; }
}
