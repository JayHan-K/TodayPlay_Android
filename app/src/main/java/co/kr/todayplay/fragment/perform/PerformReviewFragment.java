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
import co.kr.todayplay.adapter.PerformReviewAdapter;

public class PerformReviewFragment extends Fragment {
    RecyclerView review_rv;
    Button write_review_btn, more_review_btn;
    BarChart satisfy_ratio_chart;
    PerformTotalReviewFragment performTotalReviewFragment = new PerformTotalReviewFragment();
    PerformWriteReviewFragment1 performWriteReviewFragment1 = new PerformWriteReviewFragment1();
    PieChart recommend_ratio_chart;
    ImageView keywords_iv;

    ArrayList<PerformReviewAdapter.ReviewItem> all_review_data = new ArrayList<>();
    PerformReviewAdapter performReviewAdapter;

    int play_id = -1;
    int user_id = -1;

    String review_info_request_url = "http://211.174.237.197/request_review_info_by_play_id/";
    String review_user_request_url = "http://211.174.237.197/request_user_info_by_id/";

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

        //--만족도 비율 차트 Start --
        int yellowColor = getResources().getColor(R.color.yellow);
        int whiteColor = getResources().getColor(R.color.white);
        int lightGrayColor = getResources().getColor(R.color.lightgray);
        ArrayList<BarEntry> dataset = new ArrayList<>();
        dataset.add(new BarEntry(0,1f));
        dataset.add(new BarEntry(0.9f, 1f));
        dataset.add(new BarEntry(1.9f, 2f));
        dataset.add(new BarEntry(2.9f, 3f));
        dataset.add(new BarEntry(3.9f, 4f));
        dataset.add(new BarEntry(4.9f, 5f));

        ArrayList<BarEntry> dataset2 = new ArrayList<>();
        dataset2.add(new BarEntry(0.22f, 2f));
        dataset2.add(new BarEntry(1.12f, 2f));
        dataset2.add(new BarEntry(2.12f, 3f));
        dataset2.add(new BarEntry(3.12f, 4f));
        dataset2.add(new BarEntry(4.12f, 5f));
        dataset2.add(new BarEntry(5.12f, 2f));

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
        int[] colorArray = new int[] {whiteColor, yellowColor};
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(27, ""));
        pieEntries.add(new PieEntry(73, ""));
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setValueTextColor(Color.TRANSPARENT);
        pieDataSet.setColors(colorArray);
        PieData pieData = new PieData(pieDataSet);
        recommend_ratio_chart.setDrawCenterText(true);
        recommend_ratio_chart.setDrawEntryLabels(false);
        recommend_ratio_chart.setCenterText("73%");
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
        Map<String, Integer> wcData = new HashMap<>();
        wcData.put("오늘의 공연",2);
        wcData.put("라이어",7);
        wcData.put("감동적인",6);
        wcData.put("로맨스",2);
        wcData.put("코믹",3);
        wcData.put("코미디",2);
        wcData.put("연극",2);
        wcData.put("가족",3);
        wcData.put("로맨틱",2);
        wcData.put("로코",2);
        wcData.put("오리지널",2);

        WordCloud wordCloud = new WordCloud(wcData, 250, 250, yellowColor, Color.TRANSPARENT);
        wordCloud.setWordColorOpacityAuto(true);
        wordCloud.setPaddingX(20);
        wordCloud.setPaddingY(15);
        Bitmap generatedWordCloudBmp = wordCloud.generate();
        keywords_iv.setImageBitmap(generatedWordCloudBmp);
        //--후기 키워드 End --

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
        data.add(new PerformReviewAdapter.ReviewItem(R.drawable.icon_mypage, "제인", true, "20대, Beginner", "2020.10.23 작성", 23, 23, "노래를 매우 잘합니다. 오리지널 캐스트라 그런지 한국 버전으로 봤을 때와 느낌이 다르네. 그리고 넘버들이 ", "무대 장치들이 예전에는 실물들이라 더 웅장하고 멋있었는데.. 대체 왜 영상으로 대체된거죠? 오페라의 유령은 ", image_data));
        all_review_data.add(new PerformReviewAdapter.ReviewItem(R.drawable.icon_mypage, "제인1", false, "20대, Beginner", "2020.10.23 작성", 23, 23, "노래를 매우 잘합니다. 오리지널 캐스트라 그런지 한국 버전으로 봤을 때와 느낌이 다르네. 그리고 넘버들이 ", "무대 장치들이 예전에는 실물들이라 더 웅장하고 멋있었는데.. 대체 왜 영상으로 대체된거죠? 오페라의 유령은 "));
*/
        performReviewAdapter = new PerformReviewAdapter(getActivity().getApplicationContext(), all_review_data);
        review_rv.setAdapter(performReviewAdapter);

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
                            String birth = user.getString("birthday");
                            Log.d("User_idRequest", "user_id = " + user_id + " nickname = " + nickname + " level = " + level + " profile_path = " + profile + " birth = " + birth);

                            int review_id = review.getInt("review_id");
                            int play_id = review.getInt("play_id");

                            String review_good = review.getString("review_good");
                            String review_bad = review.getString("review_bad");
                            String review_tip = review.getString("review_tip");
                            String review_certified_pic =  review.getString("review_certified_pic");
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
                            int recommend = review.getInt("recommend");
                            boolean thumb = false;
                            if(recommend == 1) thumb = true;
                            int review_num_of_heart = review.getInt("review_num_of_heart");
                            String written_date = review.getString("written_date");
                            Log.d("User_idRequest", "review_id = " + review_id + " play_id = " + play_id + " user_id = " + user_id + " good = " + review_good + " bad = " + review_bad + " tip = " + review_tip + " certified_pic = " + review_certified_pic + " pic1 = " + str_review_pics.get(0) + " pic2 = " + str_review_pics.get(1)  +  " pic3 = " + str_review_pics.get(2) + " comment = " + comment + " recommend = " + recommend + " review_num_of_heart = " + review_num_of_heart + " written_date = " + written_date);

                            all_review_data.add(new PerformReviewAdapter.ReviewItem(profile, nickname, thumb, level, written_date, review_num_of_heart,0, review_good, review_bad, review_pics));
                            performReviewAdapter.notifyDataSetChanged();
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
