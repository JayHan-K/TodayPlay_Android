package co.kr.todayplay.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import co.kr.todayplay.R;
import co.kr.todayplay.adapter.pf_myPickPagerAdapter;

import co.kr.todayplay.object.RecommandItem;

public class MypickFragment extends Fragment {
    ViewPager2 viewPager2;
    pf_myPickPagerAdapter adapter;
    int user_id=-1;
    ArrayList<RecommandItem> playing_play_list = new ArrayList();
    ArrayList<RecommandItem> before_play_list = new ArrayList();
    ArrayList<RecommandItem> not_play_list = new ArrayList();
    ViewGroup rootView;
    TabLayout tabLayout;
    List<String> tabElement;


    public MypickFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup)inflater.inflate(R.layout.activity_mypick,container,false);
        Button pf_to_profile = rootView.findViewById(R.id.back_profile4);

        Bundle bundle = getArguments();
        if(bundle != null){
            user_id = bundle.getInt("user_id");
            Log.d("Bundle result", "MyPickFragment user_id: " + user_id);
        }

        tabLayout = (TabLayout) rootView.findViewById(R.id.pf_tab_ly);
        tabLayout.addTab((tabLayout.newTab().setText("공연중")));
        tabLayout.addTab((tabLayout.newTab().setText("공연예정")));
        tabLayout.addTab((tabLayout.newTab().setText("미정")));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabElement = Arrays.asList("공연중","공연예정","미정");

        try{
            new LoadingMyPick().execute();
        }catch (Exception e){

        }



        pf_to_profile.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ProfileFragment parentFrag = (ProfileFragment) MypickFragment.this.getParentFragment() ;
                parentFrag.BackToHome();
            }
        }));


        return rootView;
    }


    public String get_user_info(MyPickVolleyCallback callback){

        try{
            String[] resposeData = {""};
            RequestQueue queue = Volley.newRequestQueue(getContext());
            String url = "http://211.174.237.197/request_user_info_by_id/";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {
                    String data = response;
                    callback.onSuccess(data);


                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Login_Home", error.toString());
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("user_id", String.valueOf(user_id));
                    return params;
                }
            };
            queue.add(stringRequest);
            return "1";

        } catch (Exception e) {
            Log.d("Login_Home", e.toString());

        }
        return "0";
    }

    public String get_play_info(int play_id, MyPickVolleyCallback callback){

        try{
            String[] resposeData = {""};
            RequestQueue queue = Volley.newRequestQueue(getContext());
            String url = "http://211.174.237.197/request_play_info_by_id/";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {
                    String data = response;
                    callback.onSuccess(data);


                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Login_Home", error.toString());
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("play_id", String.valueOf(play_id));
                    return params;
                }
            };
            queue.add(stringRequest);
            return "1";

        } catch (Exception e) {
            Log.d("Login_Home", e.toString());

        }
        return "0";
    }

    private class LoadingMyPick extends AsyncTask {
        boolean isFinished = false;
        int count = 0;
        int maxCount = 0;

        @Override
        protected Object doInBackground(Object[] objects) {
            get_user_info(new MyPickVolleyCallback() {
                @Override
                public void onSuccess(String data) {
                    Log.d("profile_data","profile_data="+data);
                    String[] my_play_Str = data.split("my_play\": \"")[1].split("\",")[0].split(",");
                    maxCount = my_play_Str.length;
                    for (int i = 0; i < my_play_Str.length; i++) {
                        try {
                            int play_id = Integer.parseInt(my_play_Str[i].replace(" ", ""));
                            get_play_info(play_id, new MyPickVolleyCallback() {
                                @Override
                                public void onSuccess(String data) {
                                    String[] play_date = data.split("play_date");
                                    String playing_title = "";
                                    String before_title = "";
                                    String not_title = "";
                                    String last_end_date_str = "";

                                    boolean is_playing = false;
                                    boolean is_before = false;
                                    try {
                                        for (int j = 1; j < play_date.length; j++) {
                                            String my_date = play_date[j].split(":")[1].split("\"")[1].split("\\\\")[0];
                                            String start_date_str = my_date.split("~")[0];
                                            String end_date_str = my_date.split("~")[1];
                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                                            Calendar calendar = Calendar.getInstance();
                                            Date date = calendar.getTime();
                                            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
                                            String now_date_str = sdf.format(date);

                                            Date start_date = sdf.parse(start_date_str);
                                            Date end_date = sdf.parse(end_date_str);
                                            Date now_date = sdf.parse(now_date_str);

                                            if (last_end_date_str.equals("")) {
                                                last_end_date_str = end_date_str;
                                            } else {
                                                Date last_end_date = sdf.parse(last_end_date_str);
                                                if (end_date.compareTo(last_end_date) > 0) {
                                                    last_end_date_str = end_date_str;
                                                }
                                            }

                                            if (now_date.compareTo(start_date) < 0) {
                                                is_before = true;
                                                before_title = start_date_str + "시작";
                                            } else {
                                                if (now_date.compareTo(end_date) < 0) {
                                                    is_playing = true;
                                                    playing_title = end_date_str + "종료";
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        Log.e("Error", e.toString());
                                    }
                                    if (is_before) {
                                        before_play_list.add(new RecommandItem(play_id, R.drawable.poster_sample1, before_title));
                                        Log.d("MyPickFragment", "before list " + before_play_list);
                                    }
                                    if (is_playing) {
                                        playing_play_list.add(new RecommandItem(play_id, R.drawable.poster_sample1, playing_title));
                                        Log.d("MyPickFragment", "playing list " + playing_play_list);
                                    }
                                    if (!is_before && !is_playing) {
                                        not_play_list.add(new RecommandItem(play_id, R.drawable.poster_sample1, last_end_date_str + "종료"));
                                        Log.d("MyPickFragment", "not list " + not_play_list);
                                    }

                                    adapter = new pf_myPickPagerAdapter(getParentFragment(), tabLayout.getTabCount(), user_id, playing_play_list, before_play_list, not_play_list);
                                    viewPager2 = (ViewPager2) rootView.findViewById(R.id.viewPager2);
                                    viewPager2.setAdapter(adapter);
                                    new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
                                        @Override
                                        public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                                            tab.setText(tabElement.get(position));
                                        }
                                    }).attach();

                                }
                            });

                        } catch (Exception e) {

                        }
                    }

                }
            });
            return null;
        }
    }

}

interface MyPickVolleyCallback{
    void onSuccess(String data);
}