package co.kr.todayplay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import co.kr.todayplay.fragment.CategoryFragment;
import co.kr.todayplay.fragment.CommunityFragment;
import co.kr.todayplay.fragment.HomeFragment;
import co.kr.todayplay.fragment.JournalFragment;
import co.kr.todayplay.fragment.ProfileFragment;
import co.kr.todayplay.fragment.SearchFragment;
import co.kr.todayplay.object.Banner;
import co.kr.todayplay.object.Ranking;
import co.kr.todayplay.object.Recommend;

public class MainActivity extends AppCompatActivity {

    private Long mBackwait = 0L;
    // 선언해서 밑에서 작업할시 이동이 안돼는 현상이 나타나서 밑에 따로 선언해주었습니다.
    private final CategoryFragment categoryFragment = new CategoryFragment();
    private final SearchFragment searchFragment = new SearchFragment();
    private final ProfileFragment profileFragment = new ProfileFragment();
    private final CommunityFragment communityFragment = new CommunityFragment();
    private final JournalFragment journalFragment = new JournalFragment();


    //상단 배너부분 정보
    String HomeBanner_all_jsonString;
    String all_HomeBanner_result_url = "http://183.111.253.75/request_home_banner_info/";
    JSONArray HomeBanner_all_jsonArray;
    ArrayList<Banner> banners = new ArrayList();
    Banner data;

    //오늘의 추천 정보
    String Recommend_all_jsonString;
    String all_Recommend_result_url ="http://183.111.253.75/request_todays_recommend/";
    JSONArray Recommend_all_jsonArray;
    ArrayList<Recommend> recommands =new ArrayList();
    Recommend data1;

    //오늘의 저널 정보
    String Journal_all_jsonString;
    String all_Journal_result_url ="http://183.111.253.75/request_todays_journal/";
    JSONArray Journal_all_jsonArray;
    ArrayList<Recommend> recommandj =new ArrayList();
    Recommend data2;

    //핫랭크 인기 순위
    String Ranking_all_jsonString;
    String all_Ranking_result_url="http://183.111.253.75/request_hot_rank/";
    JSONArray Ranking_all_jsonArray;
    ArrayList<Ranking> rankings = new ArrayList<Ranking>();
    Ranking data3;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //배너정보
        UpdateBannerInfo updateBannerInfo = new UpdateBannerInfo();
        updateBannerInfo.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        //추천정보
        UpdateRecommendInfo updateRecommendInfo =new UpdateRecommendInfo();
        updateRecommendInfo.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        //저널정보
        UpdateJournalInfo updateJournalInfo = new UpdateJournalInfo();
        updateJournalInfo.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        //인기작정보
        UpdateRankingInfo updateRankingInfo = new UpdateRankingInfo();
        updateRankingInfo.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        BottomNavigationView main_bottomNavigationView = findViewById(R.id.main_bottomNavigationView);
        main_bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        final HomeFragment homeFragment = new HomeFragment(banners,recommands,recommandj,rankings);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, homeFragment).commitAllowingStateLoss();

        main_bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction transaction = MainActivity.this.getSupportFragmentManager().beginTransaction();
                switch (menuItem.getItemId()){
                    case R.id.bottom_home:
                        transaction.replace(R.id.main_frameLayout, new HomeFragment(banners,recommands,recommandj,rankings)).commitAllowingStateLoss();
                        break;
                    case R.id.bottom_category:
                        transaction.replace(R.id.main_frameLayout, new CategoryFragment()).commitAllowingStateLoss();
                        break;

                    case R.id.bottom_search:
                        transaction.replace(R.id.main_frameLayout, new SearchFragment()).commitAllowingStateLoss();
                        break;
                    case R.id.bottom_community:
                        transaction.replace(R.id.main_frameLayout, new JournalFragment()).commitAllowingStateLoss();
                        break;
                    case R.id.bottom_profile:
                        transaction.replace(R.id.main_frameLayout, new ProfileFragment()).commitAllowingStateLoss();
                        break;
                }
                return true;
            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frameLayout, fragment).commit();
    }

    @Override
    public void onBackPressed() {
//        finish();
//        if(System.currentTimeMillis() - mBackwait >= 2000){
//
//            mBackwait = System.currentTimeMillis();
//            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
//        }
        if (System.currentTimeMillis() > mBackwait + 2500) {
            mBackwait = System.currentTimeMillis();
            Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show();
            return;
        }
        if (System.currentTimeMillis() <= mBackwait+ 2500) {
            finish();
        }

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
//상단배너
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
                HomeBanner_all_jsonArray = jsonObject.getJSONArray("home_banner");
                for(int i=0; i<HomeBanner_all_jsonArray.length(); i++){
                    JSONObject HomeBanner_object = (JSONObject) HomeBanner_all_jsonArray.get(i);
                    int banner_id = (int) HomeBanner_object.get("play_id");
                    Log.d("HomeBannerObject", "Object " + i + ": " + HomeBanner_all_jsonArray.get(i).toString());
                    Log.d("banner_id", "banner_id = " + banner_id);
                    data = new Banner((int)HomeBanner_object.get("order"), banner_id, (String)HomeBanner_object.get("banner"));
                    banners.add(data);

                    System.out.println("in banner"+banners);
                }
                Log.d("banner_done?","banner_done");
//                final HomeFragment homeFragment = new HomeFragment(banners,recommands);
//                getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, homeFragment).commitAllowingStateLoss();


            } catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
//오늘의 추천
    public class UpdateRecommendInfo extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Recommend_all_jsonString = getJsonFromServer(all_Recommend_result_url);
                Log.d("Recommend_all_json", Recommend_all_jsonString);
                JSONObject jsonObject = new JSONObject(Recommend_all_jsonString);
                Log.d("Recommend jsonObject", jsonObject.toString());
                Recommend_all_jsonArray = jsonObject.getJSONArray("todays_recommend");
                for(int i=0; i<Recommend_all_jsonArray.length(); i++){
                    JSONObject Recommend_object = (JSONObject) Recommend_all_jsonArray.get(i);
                    int play_id = (int) Recommend_object.get("play_id");
                    Log.d("RecommendObject", "Object " + i + ": " + Recommend_all_jsonArray.get(i).toString());
                    Log.d("play_id", "play_id = " + play_id);
                    data1 = new Recommend(play_id);
                    recommands.add(data1);

                }
                Log.d("recommend_done?","recommend_done");



            } catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
// 오늘의 저널
    public class UpdateJournalInfo extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Journal_all_jsonString = getJsonFromServer(all_Journal_result_url);
                Log.d("Journal_all_jsonString", Journal_all_jsonString);
                JSONObject jsonObject = new JSONObject(Journal_all_jsonString);
                Log.d("Journal jsonObject", jsonObject.toString());
                Journal_all_jsonArray = jsonObject.getJSONArray("todays_journal");
                for(int i=0; i<Journal_all_jsonArray.length(); i++){
                    JSONObject Journal_object = (JSONObject) Journal_all_jsonArray.get(i);
                    int journal_id = (int) Journal_object.get("journal_id");
                    Log.d("JournalObject", "Object " + i + ": " + Journal_all_jsonArray.get(i).toString());
                    Log.d("journal_id", "journal_id = " + journal_id);
                    data2 = new Recommend(journal_id);
                    recommandj.add(data2);

                }
                Log.d("journal_done?","journal_done");



            } catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
//인기작
    public class UpdateRankingInfo extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Ranking_all_jsonString = getJsonFromServer(all_Ranking_result_url);
                Log.d("Ranking_all_jsonString", Ranking_all_jsonString);
                JSONObject jsonObject = new JSONObject(Ranking_all_jsonString);
                Log.d("Ranking jsonObject", jsonObject.toString());
                Ranking_all_jsonArray = jsonObject.getJSONArray("hot_rank");
                for(int i=0; i<Ranking_all_jsonArray.length(); i++){
                    JSONObject Ranking_object = (JSONObject) Ranking_all_jsonArray.get(i);
                    int play_id = (int) Ranking_object.get("play_id");
                    Log.d("Ranking_object", "Object " + i + ": " + Ranking_all_jsonArray.get(i).toString());
                    Log.d("play_id", "play_id = " + play_id);
                    data3 = new Ranking((String)Ranking_object.get("category"),(int)Ranking_object.get("order"),play_id);
                    rankings.add(data3);

                }
                Log.d("ranking_done?","ranking_done");



            } catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
}