package co.kr.todayplay.fragment.perform;

import android.app.AlertDialog;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
//import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.fragment.HomeFragment;

public class PerformRecommendDialogFragment extends DialogFragment {
    private Fragment parentFragment;
    private ConstraintLayout recommend_btn, not_recommend_btn;
    private ImageView recommend_hover_iv, not_recommend_hover_iv;
    boolean recommend_flag = false;
    int play_id, user_id, num_of_star, recommend;
    String review_good;
    String review_bad;
    String review_tip;
    String certification_imgpath, review_certified_pic = "";
    String certification_type = "";
    String review_pic1, review_pic1_name = "";
    String review_pic2, review_pic2_name = "";
    String review_pic3, review_pic3_name = "";

    String upload_url = "http://211.174.237.197/db_api/upload_file/review";

    public PerformRecommendDialogFragment(){}

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.pop_up_recommend_perform,null);
        builder.setView(view);

        Bundle bundle = getArguments();
        if(bundle != null){
            play_id = bundle.getInt("play_id");
            user_id = bundle.getInt("user_id");
            review_good = bundle.getString("review_good");
            review_bad = bundle.getString("review_bad");
            review_tip = bundle.getString("review_tip");
            //후기 인증 사진
            certification_imgpath = bundle.getString("certification_imgpath");
            certification_type = bundle.getString("certification_type");
            review_pic1 = bundle.getString("review_pic1");
            review_pic2 = bundle.getString("review_pic2");
            review_pic3 = bundle.getString("review_pic3");
            num_of_star = bundle.getInt("num_of_star");

            Log.d("Bundle result", "play_id: " + play_id + " | user_id = " + user_id + " | review_good = " + review_good + " | review_bad = " + review_bad + " | review_tip = " + review_tip + " | certification_imgpath = " + certification_imgpath + " | num_of_star = " + num_of_star);
        }
        String[] certified = certification_imgpath.split("/");
        review_certified_pic = certified[certified.length-1];
        if(!review_pic1.equals("")){
            String[] pic1 = review_pic1.split("/");
            review_pic1_name = pic1[pic1.length-1];
        }
        if(!review_pic2.equals("")){
            String[] pic2 = review_pic2.split("/");
            review_pic2_name = pic2[pic2.length-1];
        }
        if(!review_pic3.equals("")){
            String[] pic3 = review_pic3.split("/");
            review_pic3_name = pic3[pic3.length-1];
        }
        Log.d("RecommendDialogFragment", "review_certified_pic = " + review_certified_pic + " review_pic1_name = " + review_pic1_name + " review_pic2_name = " + review_pic2_name + " review_pic3_name = " + review_pic3_name);

        recommend_hover_iv = (ImageView)view.findViewById(R.id.recomend_hover_iv);
        recommend_hover_iv.setImageResource(R.drawable.write_review_recomend_icon_gray);

        not_recommend_hover_iv = (ImageView)view.findViewById(R.id.not_recomend_hover_iv);
        not_recommend_hover_iv.setImageResource(R.drawable.write_review_not_recomend_icon_gray);

        recommend_btn = (ConstraintLayout)view.findViewById(R.id.recommend_btn);
        not_recommend_btn = (ConstraintLayout)view.findViewById(R.id.not_recommend_btn);
        not_recommend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //파일 전송
                /*
                postSendFileData("review_certified_pic", certification_imgpath);
                if(!review_pic1.equals("")){
                    postSendFileData("review_pic1", review_pic1);
                }
                if(!review_pic2.equals("")){
                    postSendFileData("review_pic2", review_pic2);
                }
                if(!review_pic3.equals("")){
                    postSendFileData("review_pic3", review_pic3);
                }
                */
                recommend_flag = false;
                not_recommend_hover_iv.setImageResource(R.drawable.write_review_not_recomend_icon_yellow);
                recommend_hover_iv.setImageResource(R.drawable.write_review_recomend_icon_gray);
                recommend = 0;
                String send_review = postSendReviewData(Integer.toString(play_id), Integer.toString(user_id), review_good, review_bad, review_tip, review_pic1, review_pic2, review_pic3, certification_type, review_certified_pic, Integer.toString(recommend), Integer.toString(num_of_star), new VolleyReviewCallback() {
                    @Override
                    public void onSuccess(String data) {
                        //Toast.makeText(getActivity().getApplicationContext(), "Result: " + data, Toast.LENGTH_SHORT).show();
                        if (!data.equals("1")) {
                            Log.d("postSendReviewData", "POST ResultFailed.");

                        } else {
                            Log.d("postSendReviewData","Success to send review");
                            Toast.makeText(getActivity().getApplicationContext(), "후기가 등록되었습니다.", Toast.LENGTH_LONG).show();
                            Log.d("pressed","pressed=");
                            HomeFragment homeFragment = new HomeFragment();
                            ((MainActivity)view.getContext()).replaceFragmentHome(homeFragment);
                            dismiss();
                        }
                    }

                });

            }
        });

        recommend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recommend_flag = true;
                recommend_hover_iv.setImageResource(R.drawable.write_review_recomend_icon_yellow);
                not_recommend_hover_iv.setImageResource(R.drawable.write_review_not_recomend_icon_gray);
                recommend = 1;

                String send_review = postSendReviewData(Integer.toString(play_id), Integer.toString(user_id), review_good, review_bad, review_tip, review_pic1_name, review_pic2_name, review_pic3_name, certification_type, review_certified_pic, Integer.toString(recommend), Integer.toString(num_of_star), new VolleyReviewCallback() {
                    @Override
                    public void onSuccess(String data) {
                        //Toast.makeText(getActivity().getApplicationContext(), "Result: " + data, Toast.LENGTH_SHORT).show();
                        if (!data.equals("1")) {
                            Log.d("postSendReviewData", "POST ResultFailed.");

                        } else {
                            Log.d("postSendReviewData","Success to send review");
                            Toast.makeText(getActivity().getApplicationContext(), "후기가 등록되었습니다.", Toast.LENGTH_LONG).show();
                            Log.d("pressed","pressed=");
                            HomeFragment homeFragment = new HomeFragment();
                            ((MainActivity)view.getContext()).replaceFragmentHome(homeFragment);
                            dismiss();
                        }
                    }

                });

            }

        });

        return builder.create();
    }
/*
    public void postSendFileData(String type, String filepath){
        SimpleMultiPartRequest simpleMultiPartRequest = new SimpleMultiPartRequest(Request.Method.POST, upload_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity().getApplicationContext(), type + " 이미지 업로드 완료.", Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), type + "이미지 업로드 실패.", Toast.LENGTH_LONG).show();

            }
        });
        simpleMultiPartRequest.addFile(type, filepath);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(simpleMultiPartRequest);
    }
*/
    public String postSendReviewData(String play_id, String user_id, String review_good,  String review_bad, String review_tip, String review_pic1, String review_pic2, String review_pic3, String certification_type, String review_certified_pic,  String recommend, String num_of_star, VolleyReviewCallback callback){

        try{
            String[] resposeData = {""};
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            String url = "http://211.174.237.197/request_save_review/";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {
                    String data = response;
                    Log.d("postSendReviewData", data);
                    resposeData[0] = data;

                    callback.onSuccess(data);


                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("postSendReviewData", error.toString());
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    long now_time = System.currentTimeMillis();
                    Date mDate = new Date(now_time);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
                    String time = simpleDateFormat.format(mDate);
                    Log.d("PerformRecommendDialog", "time = " + time);
                    Log.d("PerformRecommendDialog", "send to server | play_id = " + play_id + " user_id = " + user_id + " review_good = " + review_good + " review_bad = " + review_bad + " review_tip = " + review_tip + " review_certified_pic = " + certification_type+"_"+time+review_certified_pic + " review_pic1 = " + time + review_pic1_name + " review_pic2 = " + time + review_pic2_name + " review_pic3 = " + time + review_pic3_name + " recommend = " + recommend + " num_of_star = " + num_of_star);
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("play_id", play_id);
                    params.put("user_id", user_id);
                    params.put("review_good", review_good);
                    params.put("review_bad", review_bad);
                    params.put("review_tip", review_tip);
                    params.put("review_certified_pic", certification_type + "_" + time + review_certified_pic);
                    if(!review_pic1_name.equals("")) params.put("review_pic1", time + review_pic1_name);
                    else params.put("review_pic1", "");
                    if(!review_pic2_name.equals("")) params.put("review_pic2", time + review_pic2_name);
                    else params.put("review_pic2", "");
                    if(!review_pic3_name.equals("")) params.put("review_pic3", time + review_pic3_name);
                    else params.put("review_pic3", "");
                    params.put("recommend", recommend);
                    params.put("num_of_star", num_of_star);

                    return params;
                }
            };
            queue.add(stringRequest);
            return resposeData[0];


        } catch (Exception e) {
            Log.d("postSendReviewData", e.toString());

        }
        return "1";
    }
}


interface VolleyReviewCallback{
    void onSuccess(String data);
}
