package co.kr.todayplay.fragment.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.fragment.Journal.JournalViewFragment;

public class HomeAdMidPagerFragment extends Fragment {
    private int imageResource;
    JournalViewFragment journalViewFragment = new JournalViewFragment();

    public HomeAdMidPagerFragment(int imageResource){
        this.imageResource = imageResource;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_home_ad_mid_fragment, container, false);

        ImageView homeMainAdIV = (ImageView) viewGroup.findViewById(R.id.home_ad_mid_vi);
        homeMainAdIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("clicked", "onClick: image");
//                ((MainActivity)getActivity()).replaceFragment(journalViewFragment);
            }
        });
        homeMainAdIV.setBackgroundResource(imageResource);
        return viewGroup;
    }

}
