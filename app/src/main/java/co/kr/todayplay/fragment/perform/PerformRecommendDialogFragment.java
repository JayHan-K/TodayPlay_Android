package co.kr.todayplay.fragment.perform;

import android.app.AlertDialog;
import android.app.Dialog;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
    String review_good, review_bad, review_tip, review_pic;

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
            review_pic = bundle.getString("review_pic");
            num_of_star = bundle.getInt("num_of_star");

            Log.d("Bundle result", "play_id: " + play_id + " | user_id = " + user_id + " | review_good = " + review_good + " | review_bad = " + review_bad + " | review_tip = " + review_tip + " | review_pic = " + review_pic + " | num_of_star = " + num_of_star);
        }

        recommend_hover_iv = (ImageView)view.findViewById(R.id.recomend_hover_iv);
        recommend_hover_iv.setImageResource(R.drawable.write_review_recomend_icon_gray);

        not_recommend_hover_iv = (ImageView)view.findViewById(R.id.not_recomend_hover_iv);
        not_recommend_hover_iv.setImageResource(R.drawable.write_review_not_recomend_icon_gray);

        recommend_btn = (ConstraintLayout)view.findViewById(R.id.recommend_btn);
        not_recommend_btn = (ConstraintLayout)view.findViewById(R.id.not_recommend_btn);
        not_recommend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recommend_flag = false;
                not_recommend_hover_iv.setImageResource(R.drawable.write_review_not_recomend_icon_yellow);
                recommend_hover_iv.setImageResource(R.drawable.write_review_recomend_icon_gray);

                recommend = 0;
                String send_review = postSendReviewData(Integer.toString(play_id), Integer.toString(user_id), review_good, review_bad, review_tip, review_pic, Integer.toString(recommend), Integer.toString(num_of_star), new VolleyReviewCallback() {
                    @Override
                    public void onSuccess(String data) {
                        //Toast.makeText(getActivity().getApplicationContext(), "Result: " + data, Toast.LENGTH_SHORT).show();
                        if (data.equals("-1")) {
                            Log.d("postSendReviewData", "POST ResultFailed.");
                            HomeFragment homeFragment = new HomeFragment();
                            ((MainActivity)view.getContext()).replaceFragmentHome(homeFragment);

                        } else {
                            Log.d("postSendReviewData","Success to send review");
                            Toast.makeText(getActivity().getApplicationContext(), "후기가 등록되었습니다.", Toast.LENGTH_SHORT).show();
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

                String send_review = postSendReviewData(Integer.toString(play_id), Integer.toString(user_id), review_good, review_bad, review_tip, review_pic, Integer.toString(recommend), Integer.toString(num_of_star), new VolleyReviewCallback() {
                    @Override
                    public void onSuccess(String data) {
                        Toast.makeText(getActivity().getApplicationContext(), "Result: " + data, Toast.LENGTH_SHORT).show();
                        if (!data.equals("-1")) {
                            Log.d("postSendReviewData", "POST ResultFailed.");

                        } else {
                            Log.d("postSendReviewData","Success to send review");
                            Toast.makeText(getActivity().getApplicationContext(), "후기가 등록되었습니다.", Toast.LENGTH_SHORT).show();
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

    public String postSendReviewData(String play_id, String user_id, String review_good,  String review_bad, String review_tip, String review_pic, String recommend, String num_of_star, VolleyReviewCallback callback){

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
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("play_id", play_id);
                    params.put("user_id", user_id);
                    params.put("review_good", review_good);
                    params.put("review_bad", review_bad);
                    params.put("review_tip", review_tip);
                    params.put("review_pic", review_pic);
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
