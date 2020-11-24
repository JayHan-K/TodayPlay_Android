package co.kr.todayplay.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import co.kr.todayplay.R;
import co.kr.todayplay.fragment.home.HomeAdMidPagerFragment;

public class HomeAdMidPagerAdapter extends FragmentStatePagerAdapter {
    public HomeAdMidPagerAdapter(@NonNull FragmentManager fm) { super(fm); }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch (position){
            case 0:
                fragment = (Fragment)(new HomeAdMidPagerFragment(R.drawable.ad_mid));
                break;
            case 1:
                fragment = (Fragment)(new HomeAdMidPagerFragment(R.drawable.ad_mid));
                break;
            case 2:
                fragment = (Fragment)(new HomeAdMidPagerFragment(R.drawable.ad_mid));
                break;
            case 3:
                fragment = (Fragment)(new HomeAdMidPagerFragment(R.drawable.ad_mid));
                break;
            case 4:
                fragment = (Fragment)(new HomeAdMidPagerFragment(R.drawable.ad_mid));
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
