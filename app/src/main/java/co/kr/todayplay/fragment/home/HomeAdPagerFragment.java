package co.kr.todayplay.fragment.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.fragment.PerformInfoFragment;
import co.kr.todayplay.fragment.perform.PerformDetailFragment;
import co.kr.todayplay.fragment.perform.PerformReviewFragment;

public class HomeAdPagerFragment extends Fragment {
    PerformInfoFragment performInfoFragment = new PerformInfoFragment();
    //PerformDetailFragment performDetailFragment = new PerformDetailFragment();
    PerformReviewFragment performReviewFragment = new PerformReviewFragment();
    private int imageResource;
    private String ad_name;

    public HomeAdPagerFragment(int imageResource, String ad_name) {
        this.imageResource = imageResource;
        this.ad_name = ad_name;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_home_ad_fragment, container, false);

        ImageView homeMainAdIV = (ImageView) viewGroup.findViewById(R.id.home_main_ad_iv);
        homeMainAdIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("clicked", "onClick: image");
                ((MainActivity)getActivity()).replaceFragment(performInfoFragment);
                //((MainActivity)getActivity()).replaceFragment(performDetailFragment);
                //((MainActivity)getActivity()).replaceFragment(performReviewFragment);

            }
        });
        TextView homeMaindAdTX = (TextView) viewGroup.findViewById(R.id.home_main_ad_tx);
        homeMainAdIV.setBackgroundResource(imageResource);
        homeMaindAdTX.setText(ad_name);
        return viewGroup;
    }
}
