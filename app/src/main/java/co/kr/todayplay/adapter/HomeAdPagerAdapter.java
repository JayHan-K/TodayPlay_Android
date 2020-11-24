package co.kr.todayplay.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import co.kr.todayplay.R;
import co.kr.todayplay.fragment.home.HomeAdPagerFragment;

public class HomeAdPagerAdapter extends FragmentStatePagerAdapter {
    public HomeAdPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch (position){
            case 0:
                fragment = (Fragment)(new HomeAdPagerFragment(R.drawable.ad_sample1,"플로리앙 첼레르의 3부작,\n그 마지막 이야기<Le Fils:아들>"));
                break;
            case 1:
                fragment = (Fragment)(new HomeAdPagerFragment(R.drawable.ad_sample2,"플로리앙 첼레르의 3부작,\n그 마지막 이야기<Le Fils:아들>"));
                break;
            case 2:
                fragment = (Fragment)(new HomeAdPagerFragment(R.drawable.ad_sample3,"플로리앙 첼레르의 3부작,\n그 마지막 이야기<Le Fils:아들>"));
                break;
            case 3:
                fragment = (Fragment)(new HomeAdPagerFragment(R.drawable.ad_sample4,"플로리앙 첼레르의 3부작,\n그 마지막 이야기<Le Fils:아들>"));
                break;
            case 4:
                fragment = (Fragment)(new HomeAdPagerFragment(R.drawable.ad_sample5,"플로리앙 첼레르의 3부작,\n그 마지막 이야기<Le Fils:아들>"));
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
