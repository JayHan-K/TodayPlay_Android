package co.kr.todayplay.fragment.perform;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mordred.wordcloud.WordCloud;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.AppHelper;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.PerformReviewAdapter2;

public class PerformReviewFragment extends Fragment {
    RecyclerView review_rv;
    Button write_review_btn, more_review_btn;
    BarChart satisfy_ratio_chart;
    PerformTotalReviewFragment performTotalReviewFragment = new PerformTotalReviewFragment();
    PerformWriteReviewFragment1 performWriteReviewFragment1 = new PerformWriteReviewFragment1();
    PieChart recommend_ratio_chart;
    ImageView keywords_iv;

    ArrayList<PerformReviewAdapter2.ReviewItem> all_review_data = new ArrayList<>();
    PerformReviewAdapter2 PerformReviewAdapter2;

    int play_id = -1;
    int user_id = -1;

    String review_info_request_url = "http://211.174.237.197/request_review_info_by_play_id/";
    String review_user_request_url = "http://211.174.237.197/request_user_info_by_id/";
    String review_statistics_request_url = "http://211.174.237.197/request_review_statistic_by_play_id/";

    public PerformReviewFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_perform_review,container,false);
        Bundle bundle = getArguments();
        if(bundle != null){
            play_id = bundle.getInt("play_id");
            user_id = bundle.getInt("user_id");
            Log.d("Review Bundle result", "play_id: " + play_id + " | user_id = " + user_id);
        }

        review_rv = (RecyclerView)viewGroup.findViewById(R.id.review_rv);
        write_review_btn = (Button)viewGroup.findViewById(R.id.write_rv_btn);
        more_review_btn = (Button)viewGroup.findViewById(R.id.more_rv_btn);
        satisfy_ratio_chart = (BarChart)viewGroup.findViewById(R.id.satisfy_ratio_barChart);
        recommend_ratio_chart = (PieChart)viewGroup.findViewById(R.id.piechart);
        keywords_iv = (ImageView)viewGroup.findViewById(R.id.keyword_iv);

        //후기 분석
        sendPOSTPlay_idRequestForStatistics(review_statistics_request_url, Integer.toString(play_id));

        //후기
        sendPOSTPlay_idRequest(review_info_request_url, Integer.toString(play_id));

        /*
        ArrayList<Integer> image_data = new ArrayList<>();
        image_data.add(R.drawable.poster_sample1);
        image_data.add(R.drawable.poster_sample2);
        image_data.add(R.drawable.poster_sample3);
        image_data.add(R.drawable.poster_sample4);
        image_data.add(R.drawable.poster_sample5);
        image_data.add(R.drawable.poster_sample6);
        data.add(new PerformReviewAdapter2.ReviewItem(R.drawable.icon_mypage, "제인", true, "20대, Beginner", "2020.10.23 작성", 23, 23, "노래를 매우 잘합니다. 오리지널 캐스트라 그런지 한국 버전으로 봤을 때와 느낌이 다르네. 그리고 넘버들이 ", "무대 장치들이 예전에는 실물들이라 더 웅장하고 멋있었는데.. 대체 왜 영상으로 대체된거죠? 오페라의 유령은 ", image_data));
        all_review_data.add(new PerformReviewAdapter2.ReviewItem(R.drawable.icon_mypage, "제인1", false, "20대, Beginner", "2020.10.23 작성", 23, 23, "노래를 매우 잘합니다. 오리지널 캐스트라 그런지 한국 버전으로 봤을 때와 느낌이 다르네. 그리고 넘버들이 ", "무대 장치들이 예전에는 실물들이라 더 웅장하고 멋있었는데.. 대체 왜 영상으로 대체된거죠? 오페라의 유령은 "));
*/
        PerformReviewAdapter2 = new PerformReviewAdapter2(getActivity().getApplicationContext(), all_review_data);
        review_rv.setAdapter(PerformReviewAdapter2);

        more_review_btn.setVisibility(View.GONE);
        review_rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        more_review_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment2(performTotalReviewFragment, play_id);
            }
        });

        write_review_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment2(performWriteReviewFragment1, play_id);
            }
        });

        return viewGroup;
    }

    public void sendPOSTPlay_idRequestForStatistics(String url, String play_id){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RequestForStatistics", "response = " + response);
                        try {
                            //--만족도 비율 차트 Start --
                            JSONObject average_statis = new JSONObject(response).getJSONObject("average_satisfaction");
                            JSONObject this_satis = new JSONObject(response).getJSONObject("this_satisfaction");
                            float avg_10 = average_statis.getInt("10대");
                            float avg_20 = average_statis.getInt("20대");
                            float avg_30 = average_statis.getInt("30대");
                            float avg_40 = average_statis.getInt("40대");
                            float avg_50 = average_statis.getInt("50대");
                            float avg_60 = average_statis.getInt("60대");
                            float this_10 = this_satis.getInt("10대");
                            float this_20 = this_satis.getInt("20대");
                            float this_30 = this_satis.getInt("30대");
                            float this_40 = this_satis.getInt("40대");
                            float this_50 = this_satis.getInt("50대");
                            float this_60 = this_satis.getInt("60대");
                            int yellowColor = getResources().getColor(R.color.yellow);
                            int whiteColor = getResources().getColor(R.color.white);
                            int lightGrayColor = getResources().getColor(R.color.lightgray);
                            ArrayList<BarEntry> dataset = new ArrayList<>();
                            //평균 만족도
                            dataset.add(new BarEntry(0, avg_10));
                            dataset.add(new BarEntry(0.9f, avg_20));
                            dataset.add(new BarEntry(1.9f, avg_30));
                            dataset.add(new BarEntry(2.9f, avg_40));
                            dataset.add(new BarEntry(3.9f, avg_50));
                            dataset.add(new BarEntry(4.9f, avg_60));

                            //이 공연 만족도
                            ArrayList<BarEntry> dataset2 = new ArrayList<>();
                            dataset2.add(new BarEntry(0.22f, this_10));
                            dataset2.add(new BarEntry(1.12f, this_20));
                            dataset2.add(new BarEntry(2.12f, this_30));
                            dataset2.add(new BarEntry(3.12f, this_40));
                            dataset2.add(new BarEntry(4.12f, this_50));
                            dataset2.add(new BarEntry(5.12f, this_60));

                            ArrayList<String> age = new ArrayList<>();
                            age.add("10대");
                            age.add("20대");
                            age.add("30대");
                            age.add("40대");
                            age.add("50대");
                            age.add("60대+");
                            satisfy_ratio_chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(age));
                            satisfy_ratio_chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                            satisfy_ratio_chart.getXAxis().setTextColor(whiteColor);

                            float groupSpace = 0f;
                            float barSpace = 0.04f;
                            float barWidth = 0.15f;
                            BarDataSet barDataSet = new BarDataSet(dataset,"평균 만족도");
                            barDataSet.setColors(whiteColor);
                            barDataSet.setValueTextColor(Color.TRANSPARENT);
                            BarDataSet barDataSet1 = new BarDataSet(dataset2,"이 공연 만족도");
                            barDataSet1.setColors(yellowColor);
                            barDataSet1.setValueTextColor(Color.TRANSPARENT);
                            satisfy_ratio_chart.getLegend().setTextColor(whiteColor);
                            satisfy_ratio_chart.getLegend().setForm(Legend.LegendForm.CIRCLE);
                            satisfy_ratio_chart.getLegend().setXEntrySpace(20f);
                            satisfy_ratio_chart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                            satisfy_ratio_chart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                            satisfy_ratio_chart.getLegend().setDrawInside(false);
                            satisfy_ratio_chart.getLegend().setYOffset(15f);

                            BarData barData = new BarData(barDataSet, barDataSet1);
                            barData.setBarWidth(barWidth);
                            satisfy_ratio_chart.getXAxis().setDrawGridLines(false);
                            satisfy_ratio_chart.getAxisRight().setDrawGridLines(false);
                            satisfy_ratio_chart.getAxisLeft().setGridColor(lightGrayColor);
                            satisfy_ratio_chart.setFitBars(true);
                            satisfy_ratio_chart.setData(barData);
                            satisfy_ratio_chart.getDescription().setEnabled(false);
                            satisfy_ratio_chart.getXAxis().setDrawAxisLine(false);
                            satisfy_ratio_chart.getAxisLeft().setDrawAxisLine(false);
                            satisfy_ratio_chart.getAxisRight().setDrawAxisLine(false);
                            satisfy_ratio_chart.animateY(1000);
                            satisfy_ratio_chart.getAxisLeft().setAxisMinimum(0f);
                            satisfy_ratio_chart.getAxisLeft().setAxisMaximum(5f);
                            satisfy_ratio_chart.getAxisLeft().setLabelCount(5);
                            satisfy_ratio_chart.getAxisLeft().setDrawLabels(true);
                            satisfy_ratio_chart.getAxisRight().setDrawLabels(false);
                            satisfy_ratio_chart.getAxisLeft().setTextColor(whiteColor);
                            satisfy_ratio_chart.invalidate();
                            //--만족도 비율 차트 End --

                            //--후기 추천 비율 차트 Start --
                            String recommend  = new JSONObject(response).getString("recommend");
                            int rcm = Integer.parseInt(recommend.split("%")[0]);
                            int[] colorArray = new int[] {whiteColor, yellowColor};
                            ArrayList<PieEntry> pieEntries = new ArrayList<>();
                            pieEntries.add(new PieEntry(100-rcm, ""));
                            pieEntries.add(new PieEntry(rcm, ""));
                            PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
                            pieDataSet.setValueTextColor(Color.TRANSPARENT);
                            pieDataSet.setColors(colorArray);
                            PieData pieData = new PieData(pieDataSet);
                            recommend_ratio_chart.setDrawCenterText(true);
                            recommend_ratio_chart.setDrawEntryLabels(false);
                            recommend_ratio_chart.setCenterText(recommend);
                            recommend_ratio_chart.setCenterTextColor(yellowColor);
                            recommend_ratio_chart.getDescription().setEnabled(false);
                            recommend_ratio_chart.setCenterTextSize(18);
                            recommend_ratio_chart.setData(pieData);
                            recommend_ratio_chart.setHoleColor(Color.TRANSPARENT);
                            recommend_ratio_chart.setHoleRadius(80);
                            recommend_ratio_chart.getLegend().setEnabled(false);
                            recommend_ratio_chart.animateXY(1000,1000);
                            recommend_ratio_chart.invalidate();
                            //--후기 추천 비율 차트 End --

                            //--후기 키워드 Start --
                            JSONObject keywords = new JSONObject(response).getJSONObject("keyword");
                            Map<String, Integer> wcData = new HashMap<>();
                            Iterator iter = keywords.keys();
                            while(iter.hasNext()){
                                String key = (String)iter.next();
                                String value = keywords.getString(key);
                                wcData.put(key,Integer.parseInt(value));
                            }

                            WordCloud wordCloud = new WordCloud(wcData, 250, 250, yellowColor, Color.TRANSPARENT);
                            wordCloud.setWordColorOpacityAuto(true);
                            wordCloud.setPaddingX(20);
                            wordCloud.setPaddingY(15);
                            Bitmap generatedWordCloudBmp = wordCloud.generate();
                            keywords_iv.setImageBitmap(generatedWordCloudBmp);
                            //--후기 키워드 End --
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
                params.put("play_id", play_id);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        AppHelper.requestQueue.add(stringRequest);
    }

    public void sendPOSTPlay_idRequest(String url, String play_id){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Play_idRequest", "Response = " + response);
                        if(!response.equals("-1")){
                            try {
                                JSONArray reviews = new JSONObject(response).getJSONArray("review");
                                Log.d("Play_idRequest", "reviews length = "+ reviews.length());
                                if(reviews.length()>2) {
                                    more_review_btn.setVisibility(View.VISIBLE);
                                    more_review_btn.setText(reviews.length()-2 + "개의 후기 더 보기");
                                }
                                if(reviews.length()>=2){
                                    for(int i=0; i<2; i++){
                                        JSONObject review = reviews.getJSONObject(i);
                                        String user_id = review.getString("user_id");
                                        sendPOSTUser_idRequest(review_user_request_url, user_id, review);
                                    }
                                }
                                else if(reviews.length() == 1){
                                    JSONObject review = reviews.getJSONObject(0);
                                    String user_id = review.getString("user_id");
                                    sendPOSTUser_idRequest(review_user_request_url, user_id, review);
                                }
                                else{
                                    Log.d("Play_idRequest", "Reviews None");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Play_idRequest", error.toString());
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
                            Log.d("User_idRequest", "user_pic = " + profile);
                            if(!profile.equals("")) {
                                if(profile.equals("null")){
                                    Log.d("User_idRequest", "user_pic is null");
                                    profile = "";
                                }
                                else{
                                    Log.d("User_idRequest", "user_pic is not null");
                                    profile = getActivity().getApplicationContext().getFileStreamPath(profile).toString();
                                }
                            }
                            String birth = user.getString("birthday");
                            Log.d("User_idRequest", "user_id = " + user_id + " nickname = " + nickname + " level = " + level + " profile_path = " + profile + " birth = " + birth);

                            String review_good = review.getString("review_good");
                            String review_bad = review.getString("review_bad");
                            String review_tip = review.getString("review_tip");
                            String review_certified_pic =  review.getString("review_certified_pic");

                            ArrayList<String> str_review_pics = new ArrayList<>();
                            String review_pic1 = review.getString("review_pic1");
                            String review_pic2 = review.getString("review_pic2");
                            String review_pic3 = review.getString("review_pic3");
                            Log.d("User_id", "review_pic1 = " + review_pic1 + " review_pic2 = " + review_pic2 + " review_pic3 = " + review_pic3);
                            if(!review_pic1.equals("")) {
                                str_review_pics.add(getActivity().getApplicationContext().getFileStreamPath(review_pic1).toString());
                                Log.d("User_idRequest", "review_pic1 = " + str_review_pics.get(0));
                            }
                            if(!review_pic2.equals("")) {
                                str_review_pics.add(getActivity().getApplicationContext().getFileStreamPath(review_pic2).toString());
                                Log.d("User_idRequest", "review_pic2 = " + str_review_pics.get(1));

                            }
                            if(!review_pic3.equals("")) {
                                str_review_pics.add(getActivity().getApplicationContext().getFileStreamPath(review_pic2).toString());
                                Log.d("User_idRequest", "review_pic3 = " + str_review_pics.get(2));

                            }


                            String comment = review.getString("comment");
                            int num_comment = 0;
                            //수정 num_comment

                            int recommend = review.getInt("recommend");
                            boolean thumb = false;
                            if(recommend == 1) thumb = true;
                            int review_num_of_heart = review.getInt("review_num_of_heart");
                            String written_date = review.getString("written_date");

                            int review_id = review.getInt("review_id");
                            int play_id = review.getInt("play_id");
                            Log.d("User_idRequest", "review_id = " + review_id + " play_id = " + play_id + " user_id = " + user_id + " good = " + review_good + " bad = " + review_bad + " tip = " + review_tip + " certified_pic = " + review_certified_pic + " comment = " + comment + " recommend = " + recommend + " review_num_of_heart = " + review_num_of_heart + " written_date = " + written_date);

                            all_review_data.add(new PerformReviewAdapter2.ReviewItem(profile, nickname, thumb, level, written_date, review_num_of_heart,num_comment, review_good, review_bad, str_review_pics));
                            PerformReviewAdapter2.notifyDataSetChanged();
                            Log.d("User_idRequest", "review all_review_data size = " + all_review_data.size());
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
                params.put("user_id", user_id);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        AppHelper.requestQueue.add(stringRequest);
    }

    private Uri getUriFromPath(String filePath) {
        long photoId;
        Uri photoUri = MediaStore.Images.Media.getContentUri("external");
        String[] projection = {MediaStore.Images.ImageColumns._ID};
        Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(photoUri, projection, MediaStore.Images.ImageColumns.DATA + " LIKE ?", new String[] { filePath }, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(projection[0]);
        photoId = cursor.getLong(columnIndex);

        cursor.close();
        return Uri.parse(photoUri.toString() + "/" + photoId);
    }
}
