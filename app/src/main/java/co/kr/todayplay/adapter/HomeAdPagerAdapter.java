package co.kr.todayplay.adapter;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

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

import co.kr.todayplay.R;
import co.kr.todayplay.fragment.home.HomeAdPagerFragment;
import co.kr.todayplay.object.Banner;

public class HomeAdPagerAdapter extends FragmentStatePagerAdapter {

    //homebanner정보 가져오기
    ArrayList<Banner> banners = new ArrayList();
    Banner banneritem;


    public HomeAdPagerAdapter(@NonNull FragmentManager fm,ArrayList<Banner> banners) {
        super(fm);
        this.banners =banners;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch (position){
            case 0:
                fragment = (Fragment)(new HomeAdPagerFragment(banneritem = banners.get(position)));
                break;
            case 1:
                fragment = (Fragment)(new HomeAdPagerFragment(banneritem = banners.get(position)));
                break;
            case 2:
                fragment = (Fragment)(new HomeAdPagerFragment(banneritem = banners.get(position)));
                break;
            case 3:
                fragment = (Fragment)(new HomeAdPagerFragment(banneritem = banners.get(position)));
                break;
            case 4:
                fragment = (Fragment)(new HomeAdPagerFragment(banneritem = banners.get(position)));
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }


}
