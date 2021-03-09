package co.kr.todayplay.fragment.Profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import co.kr.todayplay.R;
import co.kr.todayplay.adapter.ProfileMypickAdapter;
import co.kr.todayplay.object.RecommandItem;


public class ProfileIng extends Fragment {
    RecyclerView recommand_rv;
    int user_id = -1;
    int type = -1;
    ArrayList<RecommandItem> data_recommand = new ArrayList();


   public ProfileIng(int user_id, int type){
       this.user_id = user_id;
       this.type = type;
   }

   @Override
    public void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState);}

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
       ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.mypick_pf,container,false);
       recommand_rv = (RecyclerView)viewGroup.findViewById(R.id.pf_pick_rv);

//       LinearLayoutManager recommandLayoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
       GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(),3);
       recommand_rv.setLayoutManager(gridLayoutManager);





        get_user_info(new ProfileIngVolleyCallback() {
            @Override
            public void onSuccess(String data) {
                String[] my_play_Str = data.split("my_play\": \"")[1].split("\",")[0].split(",");
                for(int i=0;i<my_play_Str.length; i++){
                    try{
                        int play_id = Integer.parseInt(my_play_Str[i].replace(" ", ""));
                        get_play_info(play_id, new ProfileIngVolleyCallback() {
                            @Override
                            public void onSuccess(String data) {
                                String[] play_date = data.split("play_date");
                                String playing_title="";
                                String before_title="";
                                String not_title="";
                                String last_end_date_str = "";

                                boolean is_playing = false;
                                boolean is_before = false;
                                try{
                                    for(int j=1;j<play_date.length; j++){
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

                                        if(last_end_date_str.equals("")){
                                            last_end_date_str = end_date_str;
                                        }else{
                                            Date last_end_date = sdf.parse(last_end_date_str);
                                            if(end_date.compareTo(last_end_date) > 0){
                                                last_end_date_str = end_date_str;
                                            }
                                        }

                                        if(now_date.compareTo(start_date) < 0){
                                            is_before = true;
                                            before_title = start_date_str+"시작";
                                        }else{
                                            if(now_date.compareTo(end_date) < 0){
                                                is_playing = true;
                                                playing_title = end_date_str + "종료";
                                            }
                                        }
                                    }
                                }catch (Exception e) {
                                    Log.e("Error", e.toString());
                                }
                                if(type==1 && is_before){
                                    data_recommand.add(new RecommandItem(play_id, R.drawable.poster_sample1,before_title));
                                    Log.d("ProfileIng", "type : " + type + " play_id : " + play_id + " play_title: " + before_title);
                                }
                                if(type==0 && is_playing){
                                    data_recommand.add(new RecommandItem(play_id, R.drawable.poster_sample1, playing_title));
                                    Log.d("ProfileIng", "type : " + type + " play_id : " + play_id + " play_title: " + playing_title);
                                }
                                if(type==2 && !is_before && !is_playing){
                                    data_recommand.add(new RecommandItem(play_id, R.drawable.poster_sample1,last_end_date_str + "종료"));
                                }

                            /*
                            poster_sample1 이미지 대신에 play_id 에 알맞은 이미지를
                            포스터 이미지 내부 저장소에서 받아와서 뿌려주는것만 좀 처리해주시면 감사하겠습니다.
                            */
                                ProfileMypickAdapter profileMypickAdapter = new ProfileMypickAdapter(data_recommand);
                                recommand_rv.setAdapter(profileMypickAdapter);
                            }
                        });

                    }catch (Exception e){

                    }
                }
            }
        });



       return viewGroup;
    }
    
    public String get_user_info(ProfileIngVolleyCallback callback){

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

    public String get_play_info(int play_id, ProfileIngVolleyCallback callback){

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
}



interface ProfileIngVolleyCallback{
    void onSuccess(String data);
}