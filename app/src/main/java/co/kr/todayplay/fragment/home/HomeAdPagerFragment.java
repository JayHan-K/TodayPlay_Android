package co.kr.todayplay.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import co.kr.todayplay.R;

public class HomeAdPagerFragment extends Fragment {
    private int imageResource;
    private String ad_name;

    public HomeAdPagerFragment(int imageResource, String ad_name) {
        this.imageResource = imageResource;
        this.ad_name = ad_name;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_home_ad_fragment, container, false);

        ImageView homeMainAdIV = (ImageView) viewGroup.findViewById(R.id.home_main_ad_iv);
        TextView homeMaindAdTX = (TextView) viewGroup.findViewById(R.id.home_main_ad_tx);
        homeMainAdIV.setBackgroundResource(imageResource);
        homeMaindAdTX.setText(ad_name);
        return viewGroup;
    }
}
