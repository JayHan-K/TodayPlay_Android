package co.kr.todayplay.fragment.perform;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
//import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.VolleyMultipartRequest;
import co.kr.todayplay.VolleySingleton;
import co.kr.todayplay.fragment.HomeFragment;


public class PerformRecommendDialogFragment extends DialogFragment {
    private Fragment parentFragment;
    private ConstraintLayout recommend_btn, not_recommend_btn;
    private ImageView recommend_hover_iv, not_recommend_hover_iv;
    boolean recommend_flag = false;
    int play_id, user_id, num_of_star, recommend;
    ArrayList<Uri> images;
    String review_good;
    String review_bad;
    String review_tip;
    String certification_imgpath, review_certified_pic = "";
    String certification_type = "";
    String[] review_pic=new String[3];
    String[] review_pic_name = new String[3];
    String review_pic1, review_pic1_name = "";
    String review_pic2, review_pic2_name = "";
    String review_pic3, review_pic3_name = "";
    long now_time = System.currentTimeMillis();
    Date mDate = new Date(now_time);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
    SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA);
    String time = simpleDateFormat.format(mDate);

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
            images = bundle.getParcelableArrayList("images");
            play_id = bundle.getInt("play_id");
            user_id = bundle.getInt("user_id");
            review_good = bundle.getString("review_good");
            review_bad = bundle.getString("review_bad");
            review_tip = bundle.getString("review_tip");
            //후기 인증 사진
//            certification_imgpath = bundle.getString("certification_imgpath");
            certification_imgpath = getRealPathFromURI(images.get(0));
            certification_type = bundle.getString("certification_type");
//            review_pic1 = bundle.getString("review_pic1");
//            review_pic2 = bundle.getString("review_pic2");
//            review_pic3 = bundle.getString("review_pic3");
            int cnt = images.size()-1;
            while(cnt>0){
                if(getRealPathFromURI(images.get(cnt)) == null) images.remove(cnt);
                cnt --;
            }
            Log.d("after remove", "images size = " + images.size());
            for(int i=1;i<images.size();i++){
                review_pic[i-1]=getRealPathFromURI(images.get(i));
                Log.d("Bundle result", "review_pic" + i + " = " + review_pic[i-1] + " images" + i + " = " + images.get(i));
            }
            num_of_star = bundle.getInt("num_of_star");
            Log.d("images_size",Integer.toString(images.size()));
            Log.d("Bundle result", "play_id: " + play_id + " | user_id = " + user_id + " | review_good = " + review_good + " | review_bad = " + review_bad + " | review_tip = " + review_tip + " | certification_imgpath = " + certification_imgpath +  " | review_pic1 = " + review_pic1 + " | review_pic2 = " + review_pic2 + " | review_pic3 = " + review_pic3 + " | num_of_star = " + num_of_star);
        }

        String[] certified = certification_imgpath.split("/");
        review_certified_pic = certified[certified.length-1];
        review_certified_pic = time + review_certified_pic;

        for(int i =1 ; i<images.size();i++){
            String[] pic = review_pic[i-1].split("/");
            review_pic_name[i-1] =pic[pic.length-1];
            review_pic_name[i-1] = time+review_pic_name[i-1];
        }
//        if(!review_pic1.equals("")){
//            String[] pic1 = review_pic1.split("/");
//            review_pic1_name = pic1[pic1.length-1];
//        }
//        if(!review_pic2.equals("")){
//            String[] pic2 = review_pic2.split("/");
//            review_pic2_name = pic2[pic2.length-1];
//        }
//        if(!review_pic3.equals("")){
//            String[] pic3 = review_pic3.split("/");
//            review_pic3_name = pic3[pic3.length-1];
//        }
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
                            for(int b=0;b<images.size();b++){
                                String result = uploadImage(b,new VolleyReviewCallback() {
                                    @Override
                                    public void onSuccess(String data) {
                                        Log.d("success_data",data);

                                    }
                                });
                            }
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

                            Func(images.size()-1);

//                            for(int b=0;b<images.size();++b){
//                                String result = uploadImage(b,new VolleyReviewCallback() {
//                                    @Override
//                                    public void onSuccess(String data) {
//                                        Log.d("success_data",data);
//
//                                    }
//                                });
//                            }



                        }
                    }

                });

            }

        });

        return builder.create();
    }

    private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column_index);
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
    public void Func(int num){
        if(num ==-1){
            HomeFragment homeFragment = new HomeFragment();
            ((MainActivity)getContext()).replaceFragmentHome(homeFragment);
            dismiss();
        }else{
            String result = uploadImage(num,new VolleyReviewCallback() {
                @Override
                public void onSuccess(String data) {
                    Log.d("success_data",data);
                    Func(num-1);

                }
            });
        }

    }
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
                    SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA);
                    Date date = Calendar.getInstance().getTime();
                    String written_date = format.format(date);
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
                    params.put("review_certified_pic", certification_type + "_" + review_certified_pic);
                    for(int i =0 ; i<images.size()-1;++i){
                        if(!review_pic_name[i].equals("")) params.put("review_pic"+Integer.toString(i+1), review_pic_name[i]);
                        else params.put("review_pic"+Integer.toString(i+1), "");
                        Log.d("review_pic","review_pic"+Integer.toString(i+1));
                    }
//                    if(!review_pic1_name.equals("")) params.put("review_pic1", time + review_pic1_name);
//                    else params.put("review_pic1", "");
//                    if(!review_pic2_name.equals("")) params.put("review_pic2", time + review_pic2_name);
//                    else params.put("review_pic2", "");
//                    if(!review_pic3_name.equals("")) params.put("review_pic3", time + review_pic3_name);
//                    else params.put("review_pic3", "");
                    params.put("recommend", recommend);
                    params.put("num_of_star", num_of_star);
                    params.put("written_date", written_date);

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

    private String uploadImage(int s ,VolleyReviewCallback callback){
        // loading or check internet connection or something...
        // ... then
//        String url = "http://211.174.237.197/request_change_user_pic_by_user_id/";
        String url = "http://211.174.237.197/db_api/upload/review/";
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String[] resposeData = {""};
                String data = new String(response.data);
                Log.d("DB_response", data);
                resposeData[0] = data;

                callback.onSuccess(data);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        Log.e("Error Status", status);
                        Log.e("Error Message", message);

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message+" Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message+ " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message+" Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/json");

                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                try {
                    Log.d("num_s",Integer.toString(s));
                    InputStream in = getActivity().getContentResolver().openInputStream(images.get(s));
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    Log.d("bitmap live?",bitmap.toString());

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);

                    if(s==0){
                        params.put("file", new DataPart(review_certified_pic,byteArrayOutputStream.toByteArray(), "image/jpeg"));

                    }else{
                        params.put("file", new DataPart(review_pic_name[s],byteArrayOutputStream.toByteArray(), "image/jpeg"));
                        Log.d("bitmap live?",review_pic_name[s]);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

//                params.put("cover", new DataPart("file_cover.jpg", AppHelper.getFileDataFromDrawable(getActivity().getBaseContext(), mCoverImage.getDrawable()), "image/jpeg"));

                return params;
            }
        };

        VolleySingleton.getInstance(requireActivity().getBaseContext()).addToRequestQueue(multipartRequest);
//        AppController.getInstance().addToRequestQueue(multipartRequest);
        return "0";
    }

}


interface VolleyReviewCallback{
    void onSuccess(String data);
}
