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

import co.kr.todayplay.DBHelper.PlayDB.PlayDBHelper;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.PerformVideoAdapter;
import kotlinx.coroutines.GlobalScope;

public class PerformVideoFragment extends Fragment {
    PlayDBHelper playDBHelper;

    public RecyclerView video_rv;
    private Context mContext;
    public String[] result;
    int play_id = -1;
    int user_id = -1;
    public HashMap<String, String> youtubeDataResult = new HashMap<>();
    public ArrayList<PerformVideoAdapter.Item> data = new ArrayList<>();
    public PerformVideoFragment(){}
    PerformVideoAdapter performVideoAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_perform_video, container, false);
        playDBHelper = new PlayDBHelper(this.getContext(), "Play.db",null,1);

        Bundle bundle = getArguments();
        if(bundle != null){
            play_id = bundle.getInt("play_id");
            user_id = bundle.getInt("user_id");
            Log.d("Bundle result", "play_id: " + play_id + " | user_id = " + user_id);
        }

        video_rv = (RecyclerView)viewGroup.findViewById(R.id.video_rv);
        //video_rv.setLayoutManager(new LinearLayoutManager(getParentFragment().getContext(), LinearLayoutManager.VERTICAL, false));
        video_rv.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        performVideoAdapter = new PerformVideoAdapter(data);

        String youtube_link_string = playDBHelper.getPlayVideos(play_id);

        if(!("").equals(youtube_link_string)){
            result = youtube_link_string.split(",");
            Log.d("result","result="+result[0]+"result.length"+result.length+" "+youtube_link_string);
            for(int i=0; i<result.length; i++){
                Log.d("youtube", result[i]);
                if(result[i].contains("https")){
                    String videoID = "";
                    try{
                        videoID = result[i].split("v=")[1];
                    }catch(Exception e){
                        videoID = result[i].split("be/")[1];
                    }
                    String apiUrl = "https://www.googleapis.com/youtube/v3/videos?part=snippet,statistics&id=" + videoID + "&key=AIzaSyCDhqKYR8Bh2goJieMGwiTUYxt4uxPxSNM";
                    if(i==result.length-1){
                        getDataFromYoutube(apiUrl, new YoutubeVolleyCallback() {
                            @Override
                            public void onSuccess(String returnUrl, String title, String channelTitle, String viewCount) {
                                data.add(new PerformVideoAdapter.Item(returnUrl,title,channelTitle, viewCount));
                                video_rv.setAdapter(performVideoAdapter);
                            }
                        });
                    }
                    else {

                        getDataFromYoutube(apiUrl, new YoutubeVolleyCallback() {
                            @Override
                            public void onSuccess(String returnUrl, String title, String channelTitle, String viewCount) {
                                Log.d("youtube", "callback onSucess, " + returnUrl );
                                data.add(new PerformVideoAdapter.Item(returnUrl, title, channelTitle, viewCount));
                            }
                        });
                    }
                }

            }
        }


        return viewGroup;
    }




    public String getDataFromYoutube(String url, final YoutubeVolleyCallback callback){

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

                    Log.d("response","getresponse="+response.split("items")[0]);
                    Log.d("response","getresponse="+response.split("items")[1]);
                    if(response.contains("snippet")){
                        String snippet = response.split("snippet")[1];
                        String title = snippet.split("title")[1].split("\",")[0];
                        String channelTitle = snippet.split("channelTitle")[1].split("\",")[0];
                        String statistics = response.split("statistics")[1];
                        String viewCount = statistics.split("viewCount")[1].split("\",")[0];
                        title = title.replaceAll(":", "");
                        title = title.replaceAll("\"", "");
                        title = title.trim();
                        channelTitle = channelTitle.replaceAll(":", "");
                        channelTitle = channelTitle.replaceAll("\"", "");
                        channelTitle = channelTitle.trim();
                        viewCount = viewCount.replaceAll(":", "");
                        viewCount = viewCount.replaceAll("\"", "");
                        viewCount = viewCount.trim();

                        String returnUrl = "https://www.youtube.com/watch?v=" + url.split("id=")[1].split("&")[0];

                        Log.d("youtube","return URL : " + returnUrl);
                        callback.onSuccess(returnUrl, title, channelTitle, viewCount);
                    }




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

}

interface YoutubeVolleyCallback{
    void onSuccess(String url, String title, String channelTitle, String viewCount);
}


