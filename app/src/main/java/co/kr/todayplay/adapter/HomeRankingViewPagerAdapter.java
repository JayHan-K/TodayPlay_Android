package co.kr.todayplay.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import co.kr.todayplay.fragment.home.HomeRankingFragmentFirst;

public class HomeRankingViewPagerAdapter extends FragmentStateAdapter {
    public int mCount;

    public HomeRankingViewPagerAdapter(Fragment fa, int count) {
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        if(index==0) return new HomeRankingFragmentFirst();
        else if(index==1) return new HomeRankingFragmentFirst();
        else if(index==2) return new HomeRankingFragmentFirst();
        else return new HomeRankingFragmentFirst();
    }

    @Override
    public int getItemCount() {
        return 2000;
    }

    public int getRealPosition(int position) { return position % mCount; }
}
