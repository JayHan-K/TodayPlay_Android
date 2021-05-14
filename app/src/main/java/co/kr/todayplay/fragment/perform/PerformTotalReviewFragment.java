package co.kr.todayplay.fragment.perform;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.AppHelper;
import co.kr.todayplay.DBHelper.PlayDB.PlayDBHelper;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.PerformReviewAdapter;
import co.kr.todayplay.adapter.PerformReviewCommentAdapter;
import co.kr.todayplay.adapter.PerformTotalReviewAdapter;

public class PerformTotalReviewFragment extends Fragment {
    ImageButton back_btn;
    Button write_btn;
    TextView perform_title_tv, perform_date_tv, perform_director_tv, perform_actor_tv;
    RecyclerView review_rv;
    ImageView perform_poster_iv;
    PerformTotalReviewAdapter performTotalReviewAdapter;
    ArrayList<PerformTotalReviewAdapter.ReviewItem> total_review_data = new ArrayList<>();
    PerformWriteReviewFragment1 performWriteReviewFragment1 = new PerformWriteReviewFragment1();

    String play_info_request_url = "http://211.174.237.197/request_play_info_by_id/";
    String reviews_request_url = "http://211.174.237.197/request_review_info_by_play_id/";
    String review_user_request_url = "http://211.174.237.197/request_user_info_by_id/";

    int play_id = -1;
    PlayDBHelper playDBHelper;

    public PerformTotalReviewFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_perform_total_review,container,false);
        Bundle bundle = getArguments();
        if(bundle != null){
            play_id = bundle.getInt("play_id");
            Log.d("Bundle result", "play_id: " + play_id);
        }
        playDBHelper = new PlayDBHelper(this.getContext(), "Play.db",null,1);

        back_btn = (ImageButton)viewGroup.findViewById(R.id.back_btn);
        write_btn = (Button)viewGroup.findViewById(R.id.write_review_btn);
        perform_actor_tv = (TextView)viewGroup.findViewById(R.id.actor_tv);
        perform_date_tv = (TextView)viewGroup.findViewById(R.id.perform_date_tv);
        perform_director_tv = (TextView)viewGroup.findViewById(R.id.direct_tv);
        perform_title_tv = (TextView)viewGroup.findViewById(R.id.perform_title_tv);
        review_rv = (RecyclerView)viewGroup.findViewById(R.id.perform_review_rv);
        perform_poster_iv = (ImageView)viewGroup.findViewById(R.id.perform_iv);

        perform_title_tv.setText(playDBHelper.getPlayCategory(play_id) + " " +playDBHelper.getPlayTitle(play_id));
        //수정
        //perform_date_tv.setText(playDBHelper.getPlayHistory(play_id));
        //perform_director_tv.setText(playDBHelper.get);
        //perform_actor_tv
        String main_poster_path = getActivity().getApplicationContext().getFileStreamPath(playDBHelper.getPlayPoster(play_id)).toString();
        Bitmap bm = BitmapFactory.decodeFile(main_poster_path);
        perform_poster_iv.setImageBitmap(bm);

        performTotalReviewAdapter = new PerformTotalReviewAdapter(getActivity().getApplicationContext(), total_review_data);
        review_rv.setAdapter(performTotalReviewAdapter);
        review_rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        sendPOSTPlay_idRequestForReview(reviews_request_url, Integer.toString(play_id));

        /*
        final ArrayList<PerformTotalReviewAdapter.ReviewItem> data = new ArrayList<>();
        ArrayList<Integer> image_data = new ArrayList<>();
        image_data.add(R.drawable.poster_sample1);
        image_data.add(R.drawable.poster_sample2);
        image_data.add(R.drawable.poster_sample3);
        image_data.add(R.drawable.poster_sample4);
        image_data.add(R.drawable.poster_sample5);
        image_data.add(R.drawable.poster_sample6);
        data.add(new PerformTotalReviewAdapter.ReviewItem(R.drawable.icon_mypage, "제인", true, "20대, Beginner", "2020.10.23 작성", 23, 23, "노래를 매우 잘합니다. 오리지널 캐스트라 그런지 한국 버전으로 봤을 때와 느낌이 다르네. 그리고 넘버들이 ", "무대 장치들이 예전에는 실물들이라 더 웅장하고 멋있었는데.. 대체 왜 영상으로 대체된거죠? 오페라의 유령은 ","무대장치들이 예전에는 실물들이라 더 웅장하고 멋있었는데.. 대체 왜 영상으로 대체된거죠? 오페라의 유령은 ", image_data, 23));
        data.add(new PerformTotalReviewAdapter.ReviewItem(R.drawable.icon_mypage, "제인1", false, "20대, Beginner", "2020.10.23 작성", 23, 23, "노래를 매우 잘합니다. 오리지널 캐스트라 그런지 한국 버전으로 봤을 때와 느낌이 다르네. 그리고 넘버들이 ", "무대 장치들이 예전에는 실물들이라 더 웅장하고 멋있었는데.. 대체 왜 영상으로 대체된거죠? 오페라의 유령은 ", "무대장치들이 예전에는 실물들이라 더 웅장하고 멋있었는데.. 대체 왜 영상으로 대체된거죠? 오페라의 유령은 ", 23));
*/


        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment2(performWriteReviewFragment1, play_id);
            }
        });

        return viewGroup;
    }

    public void sendPOSTPlay_idRequestForReview(String url, String play_id){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Play_idRequestForReview", "response = " + response);
                        try {
                            JSONArray reviews = new JSONObject(response).getJSONArray("review");
                            Log.d("Play_idRequestForReview", "reviews length = "+ reviews.length());
                            for(int i=0; i<reviews.length(); i++){
                                JSONObject review = reviews.getJSONObject(i);
                                String user_id = review.getString("user_id");
                                sendPOSTUser_idRequest(review_user_request_url, user_id, review);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("play_id", play_id);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        AppHelper.requestQueue.add(stringRequest);
    }

    public void sendPOSTUser_idRequest(String url, String user_id, JSONObject review){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("User_idRequest", "Response = " + response);
                        try {
                            JSONObject user = new JSONObject(response).getJSONObject("user");
                            String nickname = user.getString("nickname");
                            String level = user.getString("rank");
                            String profile = user.getString("user_pic");
                            String birth = user.getString("birthday");
                            Log.d("User_idRequest", "user_id = " + user_id + " nickname = " + nickname + " level = " + level + " profile_path = " + profile + " birth = " + birth);

                            String review_good = review.getString("review_good");
                            String review_bad = review.getString("review_bad");
                            String review_tip = review.getString("review_tip");
                            ArrayList<Uri> review_pics = new ArrayList<>();
                            ArrayList<String> str_review_pics = new ArrayList<>();
                            str_review_pics.add(review.getString("review_pic1"));
                            str_review_pics.add(review.getString("review_pic2"));
                            str_review_pics.add(review.getString("review_pic3"));
                            for(int j=0; j<2; j++){
                                if(str_review_pics.get(j).equals("")) continue;
                                //수정 - 리뷰 이미지 처리
                                //review_pics.add(getUriFromPath(str_review_pics.get(i)));
                            }
                            String comment = review.getString("comment");
                            int num_comment = 0;
                            if(!comment.equals("")) {
                                String[] comments = comment.split(", ");
                                num_comment = comments.length;
                            }

                            int recommend = review.getInt("recommend");
                            boolean thumb = false;
                            if(recommend == 1) thumb = true;
                            int review_num_of_heart = review.getInt("review_num_of_heart");
                            String written_date = review.getString("written_date");

                            String review_certified_pic =  review.getString("review_certified_pic");
                            int review_id = review.getInt("review_id");
                            int play_id = review.getInt("play_id");
                            Log.d("User_idRequest", "review_id = " + review_id + " play_id = " + play_id + " user_id = " + user_id + " good = " + review_good + " bad = " + review_bad + " tip = " + review_tip + " certified_pic = " + review_certified_pic + " pic1 = " + str_review_pics.get(0) + " pic2 = " + str_review_pics.get(1)  +  " pic3 = " + str_review_pics.get(2) + " comment = " + comment + " recommend = " + recommend + " review_num_of_heart = " + review_num_of_heart + " written_date = " + written_date);

                            total_review_data.add(new PerformTotalReviewAdapter.ReviewItem(profile, nickname, thumb, level, written_date, review_num_of_heart, num_comment, review_good, review_bad, review_tip, review_pics, num_comment));
                            total_review_data = reviewSort(total_review_data);
                            performTotalReviewAdapter.notifyDataSetChanged();
                            Log.d("User_idRequest", "review total_review_data size = " + total_review_data.size());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("user_id", user_id);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        AppHelper.requestQueue.add(stringRequest);
    }

    public void sendPOSTPlay_idRequestForPlayInfo(String url, String play_id){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.equals("-1")){
                            Log.d("Play_idReaust", "response = " + response);
                            try {
                                JSONObject jsonObject = new JSONObject(response).getJSONObject("play");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("play_id", play_id);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        AppHelper.requestQueue.add(stringRequest);
    }

    //최신 순으로 리뷰를 정렬하는 함수
    public ArrayList<PerformTotalReviewAdapter.ReviewItem> reviewSort(ArrayList<PerformTotalReviewAdapter.ReviewItem> reviews){
        ArrayList<PerformTotalReviewAdapter.ReviewItem> review_list = reviews;

        Collections.sort(review_list, new Comparator<PerformTotalReviewAdapter.ReviewItem>() {
            @Override
            public int compare(PerformTotalReviewAdapter.ReviewItem o1, PerformTotalReviewAdapter.ReviewItem o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        return review_list;
    }

}
