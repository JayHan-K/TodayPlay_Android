package co.kr.todayplay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import java.util.HashMap;
import java.util.Map;

import co.kr.todayplay.fragment.CategoryFragment;
import co.kr.todayplay.fragment.CommunityFragment;
import co.kr.todayplay.fragment.HomeFragment;
import co.kr.todayplay.fragment.JournalFragment;
import co.kr.todayplay.fragment.Profile.profilePopupFragment;
import co.kr.todayplay.fragment.ProfileFragment;
import co.kr.todayplay.fragment.SearchFragment;
import co.kr.todayplay.object.Banner;
import co.kr.todayplay.object.Line;
import co.kr.todayplay.object.Ranking;
import co.kr.todayplay.object.Recommend;
import co.kr.todayplay.object.Sim_uri;

public class MainActivity extends AppCompatActivity {
    private Long mBackwait = 0L;
    // 선언해서 밑에서 작업할시 이동이 안되는 현상이 나타나서 밑에 따로 선언해주었습니다.
    private final CategoryFragment categoryFragment = new CategoryFragment();
    private final SearchFragment searchFragment = new SearchFragment();
    private final ProfileFragment profileFragment = new ProfileFragment();
    private final CommunityFragment communityFragment = new CommunityFragment();
    private final JournalFragment journalFragment = new JournalFragment();
    private final HomeFragment homeFragment = new HomeFragment();

    //상단 배너부분 정보
    static String HomeBanner_all_jsonString;
    static String all_HomeBanner_result_url = "http://211.174.237.197/request_home_banner_info/";
    static JSONArray HomeBanner_all_jsonArray;
    static ArrayList<Banner> banners = new ArrayList<Banner>();
    static Banner data;

    //오늘의 추천 정보
    static String Recommend_all_jsonString;
    static String all_Recommend_result_url ="http://211.174.237.197/request_todays_recommend/";
    static JSONArray Recommend_all_jsonArray;
    static ArrayList<Recommend> recommands =new ArrayList<Recommend>();
    static Recommend data1;

    //오늘의 저널 정보
    static String Journal_all_jsonString;
    static String all_Journal_result_url ="http://211.174.237.197/request_todays_journal/";
    static JSONArray Journal_all_jsonArray;
    static ArrayList<Recommend> recommandj =new ArrayList<Recommend>();
    static Recommend data2;

    //핫랭크 인기 순위
    static String Ranking_all_jsonString;
    static String all_Ranking_result_url="http://211.174.237.197/request_hot_rank/";
    static JSONArray Ranking_all_jsonArray;
    static ArrayList<Ranking> rankings = new ArrayList<Ranking>();
    static Ranking data3;

    //오늘의 한줄 정보
    static String Line_all_jsonString;
    static String all_Line_result_url = "http://211.174.237.197/request_todays_line/";
    static JSONArray Line_all_jsonArray;
    static ArrayList<Line> line = new ArrayList<Line>();
    static Line data4;

    String userId=null ;
    String nickname = null;
    static int cnt=0;
    int current =0;
    public static Activity MainActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cnt =0;
        MainActivity = MainActivity.this;


        //인기작정보
        UpdateRankingInfo updateRankingInfo = new UpdateRankingInfo();
        updateRankingInfo.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        //배너정보
        UpdateBannerInfo updateBannerInfo = new UpdateBannerInfo();
        updateBannerInfo.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        //오늘의 추천
        UpdateRecommendInfo updateRecommendInfo = new UpdateRecommendInfo();
        updateRecommendInfo.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        //저널정보
        UpdateJournalInfo updateJournalInfo = new UpdateJournalInfo();
        updateJournalInfo.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        //한줄정보
        UpdateLineInfo updateLineInfo = new UpdateLineInfo();
        updateLineInfo.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        nickname = intent.getStringExtra("nickname");


        Log.d("userId","userId="+userId);
        Log.d("nickname","nickname="+nickname);


        if(userId !=null){
            SharedPreference.setAttribute(getApplicationContext(),"userId",userId);
            SharedPreference.setAttribute(getApplicationContext(),"nickname",nickname);
        }
        String get = get_play_id_from_user_id(userId, new VolleyCallback() {
            @Override
            public void onSuccess(String data) {
                Log.d("volley data","volley data="+data);
                Recommend_all_jsonString = data;
                //추천정보
                UpdateRecommendInfo updateRecommendInfo =new UpdateRecommendInfo();
                updateRecommendInfo.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });



        BottomNavigationView main_bottomNavigationView = findViewById(R.id.main_bottomNavigationView);
        main_bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        Bundle bundle = new Bundle();
        bundle.putSerializable("banners",banners);
        bundle.putSerializable("recommands",recommands);
        bundle.putSerializable("recommandj",recommandj);
        bundle.putSerializable("rankings",rankings);
        bundle.putSerializable("line",line);
        homeFragment.setArguments(bundle);

//        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, homeFragment).commitAllowingStateLoss();

        main_bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction transaction = MainActivity.this.getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();

//                if(menuItem.getItemId() == R.id.bottom_home && current !=1){
//                    HomeFragment homeFragment1 = new HomeFragment();
//
//                    bundle.putSerializable("banners",banners);
//                    bundle.putSerializable("recommands",recommands);
//                    bundle.putSerializable("recommandj",recommandj);
//                    bundle.putSerializable("rankings",rankings);
//                    bundle.putSerializable("line",line);
//                    homeFragment1.setArguments(bundle);
//                    transaction.replace(R.id.main_frameLayout, homeFragment1).commitAllowingStateLoss();
//                    current=1;
//                }else if(menuItem.getItemId() == R.id.bottom_category && current !=2){
//                    transaction.replace(R.id.main_frameLayout, new CategoryFragment()).commitAllowingStateLoss();
//                    current=2;
//                }else if(menuItem.getItemId() == R.id.bottom_search && current !=3){
//                    transaction.replace(R.id.main_frameLayout, new SearchFragment()).commitAllowingStateLoss();
//                    current=3;
//                }else if(menuItem.getItemId() == R.id.bottom_community && current !=4){
//                    transaction.replace(R.id.main_frameLayout, new JournalFragment()).commitAllowingStateLoss();
//                    current=4;
//                }else if(menuItem.getItemId() == R.id.bottom_profile && current !=5){
//                    ProfileFragment profileFragment = new ProfileFragment();
//                    bundle.putSerializable("user_id", Integer.valueOf(userId));
//                    profileFragment.setArguments(bundle);
//                    transaction.replace(R.id.main_frameLayout, profileFragment).commitAllowingStateLoss();
//                    current=5;
//                }

                if(menuItem.getItemId() == R.id.bottom_home && current !=1){
                    Log.d("trace","back to home");
                    HomeFragment homeFragment1 = new HomeFragment();

                    bundle.putSerializable("banners",banners);
                    bundle.putSerializable("recommands",recommands);
                    bundle.putSerializable("recommandj",recommandj);
                    bundle.putSerializable("rankings",rankings);
                    bundle.putSerializable("line",line);
                    homeFragment1.setArguments(bundle);
                    transaction.replace(R.id.main_frameLayout, homeFragment1).commitAllowingStateLoss();
                    current=1;
                }else if(menuItem.getItemId() == R.id.bottom_category && current !=2){
                    Toast.makeText(getApplicationContext(),"서비스 준비중 입니다.",Toast.LENGTH_SHORT).show();
                    current=2;
                }else if(menuItem.getItemId() == R.id.bottom_search && current !=3){
                    Toast.makeText(getApplicationContext(),"서비스 준비중 입니다.",Toast.LENGTH_SHORT).show();
                    current=3;
                }else if(menuItem.getItemId() == R.id.bottom_community && current !=4){
                    transaction.replace(R.id.main_frameLayout, new JournalFragment()).commitAllowingStateLoss();
                    current=4;
                }else if(menuItem.getItemId() == R.id.bottom_profile && current !=5){
                    ProfileFragment profileFragment = new ProfileFragment();
                    if(userId != null){
                        bundle.putSerializable("user_id", Integer.valueOf(userId));
                        profileFragment.setArguments(bundle);
                        transaction.replace(R.id.main_frameLayout, profileFragment).commitAllowingStateLoss();
                    }else{
//                        Intent intent = new Intent(getApplicationContext(), profilePopupActivity.class);
//                        startActivityForResult(intent,1);
                        profilePopupFragment profilePopupFragment = new profilePopupFragment();
                        transaction.replace(R.id.main_frameLayout,profilePopupFragment).commitAllowingStateLoss();
                    }

                    current=5;
                }

                return true;
            }
        });
    }

    public void replaceFragment2(Fragment fragment, int play_id){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        if(null != userId){
            bundle.putInt("user_id", Integer.parseInt(userId));
        }
        bundle.putInt("play_id",play_id);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.main_frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void replaceFragment3(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    public void replaceFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", Integer.parseInt(userId));
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.main_frameLayout, fragment).commit();
    }

    public void replaceFragmentHome(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("banners",banners);
        bundle.putSerializable("recommands",recommands);
        bundle.putSerializable("recommandj",recommandj);
        bundle.putSerializable("rankings",rankings);
        bundle.putSerializable("line",line);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.main_frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void replaceFragment(Fragment fragment, int journal_id) {
        Log.d("MainActivity", "replaceFragment: journal_id = " + journal_id + " | user_id = " + userId);
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", Integer.parseInt(userId));
        bundle.putInt("journal_id", journal_id);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.main_frameLayout, fragment).commit();
    }

    public void replaceFragment(Fragment fragment, int play_id, String certification_type, String certification_imgpath) {
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", Integer.parseInt(userId));
        bundle.putInt("play_id", play_id);
        bundle.putString("certification_type", certification_type);
        bundle.putString("certification_imgpath", certification_imgpath);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.main_frameLayout, fragment).commit();
    }

    public void replaceFragment(Fragment fragment, int play_id, String certification_type, String certification_imgpath, ArrayList<Uri> images) {
        Bundle bundle = new Bundle();
        bundle.putInt("user_id", Integer.parseInt(userId));
        bundle.putInt("play_id", play_id);
        bundle.putString("certification_type", certification_type);
        bundle.putString("certification_imgpath", certification_imgpath);
        bundle.putParcelableArrayList("image",images);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.main_frameLayout, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        if (System.currentTimeMillis() > mBackwait + 2500) {
//            mBackwait = System.currentTimeMillis();
//            Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show();
//            return;
//        }
//        if (System.currentTimeMillis() <= mBackwait+ 2500) {
//            finish();
//        }

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
        return inputStream.readLine();
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
                cnt++;


            } catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("update1","cnt="+cnt);
        if(cnt==5){
            Log.d("update","done");
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, homeFragment).commitAllowingStateLoss();
        }

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
                cnt++;

            } catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("update2","cnt="+cnt);
        if(cnt==5){
            Log.d("update","done");
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, homeFragment).commitAllowingStateLoss();
        }
    }
    }
// 오늘의 저널
    public  class UpdateJournalInfo extends AsyncTask<Void, Void, Void> {
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
                cnt++;

            } catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("update3","cnt="+cnt);
        if(cnt==5){
            Log.d("update","done");
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, homeFragment).commitAllowingStateLoss();
        }
    }
    }
//인기작
    public  class UpdateRankingInfo extends AsyncTask<Void, Void, Void> {
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
                cnt++;

            } catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("update4","cnt="+cnt);
        if(cnt==5){
            Log.d("update","done");
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, homeFragment).commitAllowingStateLoss();
        }
    }
    }

    //한줄추천
    public  class UpdateLineInfo extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Line_all_jsonString = getJsonFromServer(all_Line_result_url);
                Log.d("Line_all_jsonString", Line_all_jsonString);
                JSONObject jsonObject = new JSONObject(Line_all_jsonString);
                Log.d("Line jsonObject", jsonObject.toString());
                JSONObject line_id_object = (JSONObject) jsonObject.get("todays_line");
                int line_id = (int) line_id_object.get("play_id");
                Log.d("lineObject", "Object "  + ": " + line_id_object.toString());
                Log.d("line_id", "line_id = " + line_id);
                data4 = new Line( line_id, (String)line_id_object.get("image"));
                line.add(data4);
                Log.d("line_done?","line_done");
                cnt++;

            } catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("update5","cnt="+cnt);
            if(cnt==5){
                Log.d("update","done");
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, homeFragment).commitAllowingStateLoss();
            }
        }
    }

    public String get_play_id_from_user_id(String userId,VolleyCallback callback){
        try{
            String[] resposeData ={""};
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://211.174.237.197/request_todays_recommend_by_id/";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    String data = response;
                    Log.d("get play_id", data);
                    resposeData[0] = data;

                    callback.onSuccess(data);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error",error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError{
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type","application/json");
                    params.put("user_id",userId);
                    return params;
                }

            };
            queue.add(stringRequest);
            return resposeData[0];
        }catch (Exception e){
            Log.d("error_get_play_id",e.toString());
        }
        return "0";
    }

}
