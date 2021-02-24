package co.kr.todayplay.fragment.perform;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.apache.commons.io.IOUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.kr.todayplay.R;
import co.kr.todayplay.adapter.PerformVideoAdapter;
import kotlinx.coroutines.GlobalScope;

public class PerformVideoFragment extends Fragment {
    public RecyclerView video_rv;
    private Context mContext;
    public String result="";
    public HashMap<String, String> youtubeDataResult = new HashMap<>();
    public ArrayList<PerformVideoAdapter.Item> data = new ArrayList<>();
    public PerformVideoFragment(){}
    public ArrayList<String> urls = new ArrayList<>();
    PerformVideoAdapter performVideoAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_perform_video, container, false);
        video_rv = (RecyclerView)viewGroup.findViewById(R.id.video_rv);
        //video_rv.setLayoutManager(new LinearLayoutManager(getParentFragment().getContext(), LinearLayoutManager.VERTICAL, false));
        video_rv.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        performVideoAdapter = new PerformVideoAdapter(data);
        video_rv.setAdapter(performVideoAdapter);


        urls.add("https://www.googleapis.com/youtube/v3/videos?part=snippet,statistics&id=WAdWghqAeGY&key=AIzaSyCDhqKYR8Bh2goJieMGwiTUYxt4uxPxSNM");
        urls.add("https://www.googleapis.com/youtube/v3/videos?part=snippet,statistics&id=AWAPJ0uDA_Y&key=AIzaSyCDhqKYR8Bh2goJieMGwiTUYxt4uxPxSNM");



        /*getDataFromYoutube("https://www.googleapis.com/youtube/v3/videos?part=snippet,statistics&id=WAdWghqAeGY&key=AIzaSyCDhqKYR8Bh2goJieMGwiTUYxt4uxPxSNM");
        Log.d("Video", result);
        data.add(new PerformVideoAdapter.Item("https://www.youtube.com/watch?v=WAdWghqAeGY",youtubeDataResult.get("title"), youtubeDataResult.get("channelTitle"), youtubeDataResult.get("viewCount")));
        getDataFromYoutube("https://www.googleapis.com/youtube/v3/videos?part=snippet,statistics&id=AWAPJ0uDA_Y&key=AIzaSyCDhqKYR8Bh2goJieMGwiTUYxt4uxPxSNM");
        data.add(new PerformVideoAdapter.Item("https://www.youtube.com/watch?v=AWAPJ0uDA_Y",youtubeDataResult.get("title"), youtubeDataResult.get("channelTitle"), youtubeDataResult.get("viewCount")));

        */

        return viewGroup;
    }




    public String getDataFromYoutube(String url){

        try{
            final String[] response_var = {""};
            HashMap<String, String> youtubeData = new HashMap<String, String>();
            final String[] titleStr = {""};
            final String[] channelTitleStr = {""};
            final String[] viewCountStr = {""};
            RequestQueue queue = Volley.newRequestQueue(getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {


                    String snippet = response.split("snippet")[1];
                    String title = snippet.split("title")[1].split("\",")[0];
                    String channleTitle = snippet.split("channelTitle")[1].split("\",")[0];
                    String statistics = response.split("statistics")[1];
                    String viewCount = statistics.split("viewCount")[1].split("\",")[0];
                    data.add(new PerformVideoAdapter.Item("https://www.youtube.com/watch?v=WAdWghqAeGY",title, channleTitle, viewCount));
                    video_rv.setAdapter(performVideoAdapter);
                }
            }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Video", error.toString());
                }
            })
            {

            };
            queue.add(stringRequest);
            return response_var[0];


        }catch(Exception e){
            Log.d("Video", e.toString());

        }
        return "";
    }

/*
    class YoutubeDataTask extends AsyncTask<ArrayList<String>, Integer, Long>{
        String result = "";

        @Override
        protected Long doInBackground(ArrayList<String>... urls) {
            ArrayList<String> urlData = urls[0];

            for(int i=0;i< urlData.size();i++){

                String url = urlData.get(i);
                Log.d("Video", url);
                RequestQueue queue = Volley.newRequestQueue(getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {


                        String snippet = response.split("snippet")[1];
                        String title = snippet.split("title")[1].split("\",")[0];
                        String channleTitle = snippet.split("channelTitle")[1].split("\",")[0];
                        String statistics = response.split("statistics")[1];
                        String viewCount = statistics.split("viewCount")[1].split("\",")[0];
                        data.add(new PerformVideoAdapter.Item("https://www.youtube.com/watch?v=WAdWghqAeGY",title, channleTitle, viewCount));
                    }
                }, new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Video", error.toString());
                    }
                })
                {

                };
                queue.add(stringRequest);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            Log.d("Video", "result : " + result);

        }
    }
*/

}


