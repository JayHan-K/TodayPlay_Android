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
    public HomeAdPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }
    //homebanner정보 가져오기
    String HomeBanner_all_jsonString;
    String all_HomeBanner_result_url = "http://183.111.253.75/request_home_banner_info/";
    JSONArray HomeBanner_all_jsonArray;
    ArrayList<Banner> banners = new ArrayList();
    Banner banneritem;

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch (position){
            case 0:
                fragment = (Fragment)(new HomeAdPagerFragment(position));
                break;
            case 1:
                fragment = (Fragment)(new HomeAdPagerFragment(position));
                break;
            case 2:
                fragment = (Fragment)(new HomeAdPagerFragment(position));
                break;
            case 3:
                fragment = (Fragment)(new HomeAdPagerFragment(position));
                break;
            case 4:
                fragment = (Fragment)(new HomeAdPagerFragment(position));
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

    public static String getJsonFromServer(String url) throws IOException {

        BufferedReader inputStream = null;

        URL jsonUrl = new URL(url);
        URLConnection dc = jsonUrl.openConnection();

        dc.setConnectTimeout(10000);
        dc.setReadTimeout(10000);

        inputStream = new BufferedReader(new InputStreamReader(
                dc.getInputStream()));

        // read the JSON results into a string
        String jsonResult = inputStream.readLine();
        return jsonResult;
    }
    public ArrayList getbanner(){
        ArrayList<Banner> banners = new ArrayList<>();
        return banners;
    }

    public class UpdateBannerInfo extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                HomeBanner_all_jsonString = getJsonFromServer(all_HomeBanner_result_url);
                Log.d("Banner_all_jsonString", HomeBanner_all_jsonString);
                JSONObject jsonObject = new JSONObject(HomeBanner_all_jsonString);
                Log.d("HomeBanner jsonObject", jsonObject.toString());
                HomeBanner_all_jsonArray = jsonObject.getJSONArray("journal");
                for(int i=0; i<HomeBanner_all_jsonArray.length(); i++){
                    JSONObject HomeBanner_object = (JSONObject) HomeBanner_all_jsonArray.get(i);
                    int banner_id = (int) HomeBanner_object.get("play_id");
                    Log.d("HomeBannerObject", "Object " + i + ": " + HomeBanner_all_jsonArray.get(i).toString());
                    Log.d("banner_id", "banner_id = " + banner_id);
                    getbanner().add(banners.add(new Banner((int)HomeBanner_object.get("order"), banner_id, (String)HomeBanner_object.get("banner"))));
//                        banneritem =  banners.get((int)HomeBanner_object.get("order"));

                }
                Log.d("journal_done?","journal_done");


            } catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
}
