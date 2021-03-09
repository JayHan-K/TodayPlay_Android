package co.kr.todayplay.fragment;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import co.kr.todayplay.R;
import co.kr.todayplay.adapter.ProfileMyScrapDetailAdapter;
import co.kr.todayplay.adapter.ProfileMypickAdapter;
import co.kr.todayplay.adapter.pf_myPickPagerAdapter;
import co.kr.todayplay.object.RecommandItem;

public class MyScrapFragment extends Fragment {
    pf_myPickPagerAdapter adapter;
    RecyclerView pf_scrap_rv;
    int user_id = -1;
    ArrayList<RecommandItem> data_recommand = new ArrayList();

    public MyScrapFragment(int user_id){
        this.user_id=user_id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.activity_myscrap,container,false);
        Button pf_to_profile = rootView.findViewById(R.id.scrap_to_profile_bt);
        pf_scrap_rv =(RecyclerView)rootView.findViewById(R.id.pf_scrap_rv);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(),2);
        pf_scrap_rv.setLayoutManager(gridLayoutManager);


        get_user_info(new MyScrapVolleyCallback() {
            @Override
            public void onSuccess(String data) {
                Log.d("MyScrap", "data : " +  data);
                String[] my_journal_str = data.split("my_journal\": \"")[1].split("\",")[0].split(",");

                Log.d("MyScrap", "my_journal_str : " +  my_journal_str);
                for(int i=0;i<my_journal_str.length; i++){
                    try{
                        int journal_id = Integer.parseInt(my_journal_str[i].replace(" ", ""));
                        get_journal_info(journal_id, new MyScrapVolleyCallback() {
                            @Override
                            public void onSuccess(String data) {
                                Log.d("MyScrap", "journal data : " +  data);
                                Log.d("MyScrap", "journal_id : " + journal_id);
                                String journal_title = data.split("journal_title\": \"")[1].split("\",")[0];
                                journal_title = unicodeConvert(journal_title);
                                data_recommand.add(new RecommandItem(journal_id, R.drawable.poster_sample1,journal_title));

                            /*
                            poster_sample1 이미지 대신에 journal_id 에 알맞은 이미지를
                            포스터 이미지 내부 저장소에서 받아와서 뿌려주는것만 좀 처리해주시면 감사하겠습니다.
                            */
                                ProfileMyScrapDetailAdapter profileMyScrapDetailAdapter = new ProfileMyScrapDetailAdapter(data_recommand, user_id);
                                pf_scrap_rv.setAdapter(profileMyScrapDetailAdapter);
                            }
                        });
                    }catch (Exception e){

                    }


                }
            }
        });

        pf_to_profile.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ProfileFragment parentFrag = (ProfileFragment) MyScrapFragment.this.getParentFragment() ;
                parentFrag.BackToHome();

            }
        }));

        return rootView;
    }

    private String unicodeConvert(String str) {
        StringBuilder sb = new StringBuilder();
        char ch;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            ch = str.charAt(i);
            if (ch == '\\' && str.charAt(i+1) == 'u') {
                sb.append((char) Integer.parseInt(str.substring(i+2, i+6), 16));
                i+=5;
                continue;
            }
            sb.append(ch);
        }
        return sb.toString();
    }

    public String get_user_info(MyScrapVolleyCallback callback){

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

    public String get_journal_info(int journal_id, MyScrapVolleyCallback callback){

        try{
            String[] resposeData = {""};
            RequestQueue queue = Volley.newRequestQueue(getContext());
            String url = "http://211.174.237.197/request_journal_info_by_id/";

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
                    params.put("journal_id", String.valueOf(journal_id));
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

}

interface MyScrapVolleyCallback{
    void onSuccess(String data);
}