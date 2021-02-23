package co.kr.todayplay.fragment.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.HomeAdPagerAdapter;
import co.kr.todayplay.adapter.PerformHistoryAdapter;
import co.kr.todayplay.fragment.PerformInfoFragment;
import co.kr.todayplay.fragment.perform.PerformDetailFragment;
import co.kr.todayplay.fragment.perform.PerformHistoryFragment;
import co.kr.todayplay.fragment.perform.PerformReviewFragment;
import co.kr.todayplay.fragment.perform.PerformTotalReviewFragment;
import co.kr.todayplay.fragment.perform.PerformVideoFragment;
import co.kr.todayplay.object.Banner;

public class HomeAdPagerFragment extends Fragment {
    PerformInfoFragment performInfoFragment = new PerformInfoFragment();


    String imgpath;
    int position;
    File path;

    ArrayList<Banner> banners = new ArrayList();
    Banner banneritem;
    String file = "file";


    public HomeAdPagerFragment(Banner banneritem) {
        this.banneritem =banneritem;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_home_ad_fragment, container, false);
        ImageView homeMainAdIV = (ImageView) viewGroup.findViewById(R.id.home_main_ad_iv);
//        banneritem = banners.get(position);
        homeMainAdIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("clicked", "onClick: image");
                ((MainActivity)getActivity()).replaceFragment(performInfoFragment);
            }
        });
//        String imgpath = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/" + file + "/" +banneritem.getBanner();
        String imgpath = getContext().getFilesDir().toString() + "/" + banneritem.getBanner();
//        String imgpath = getParentFragment().getContext().getFileStreamPath(banneritem.getBanner()).toString();
//        String imgpath2 =imgpath +"/"+ banneritem.getBanner();
        Bitmap bm = BitmapFactory.decodeFile(imgpath);
        homeMainAdIV.setImageBitmap(bm);
        return viewGroup;
    }




}
