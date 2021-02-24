package co.kr.todayplay.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import co.kr.todayplay.R;


public class PerformVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public ArrayList<Item> data;
    public ArrayList<VideoListViewHolder> itemController = new ArrayList<>();

    public static class Item{
        private String url;
        private String video_title;
        private String channel;
        private String hits;

        public Item(){}

        public Item(String url, String video_title, String channel, String hits){
            this.url = url;
            this.video_title = video_title;
            this.channel = channel;
            this.hits = hits;
        }

        public String getUrl() {
            return url;
        }

        public String getChannel() {
            return channel;
        }

        public String getHits() {
            return hits;
        }

        public String getVideo_title() {
            return video_title;
        }
    }

    public static class VideoListViewHolder extends RecyclerView.ViewHolder{
        public YouTubePlayerView youTubePlayerView;
        public ImageView imageView;
        public TextView video_title_tv;
        public TextView date_tv;
        public TextView channel_tv;
        public TextView hits_tv;

        public VideoListViewHolder(@NonNull View itemView) {
            super(itemView);
            youTubePlayerView = (YouTubePlayerView)itemView.findViewById(R.id.perform_video_pv);
            video_title_tv = (TextView)itemView.findViewById(R.id.perform_video_title_tv);
            channel_tv = (TextView)itemView.findViewById(R.id.perform_video_channel_tv);
            hits_tv = (TextView)itemView.findViewById(R.id.perform_video_hits_tv);
        }
    }

    public PerformVideoAdapter(ArrayList<Item> data){
        super();
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.perform_video_item, parent, false);
        VideoListViewHolder videoListViewHolder = new VideoListViewHolder(view);
        itemController.add(videoListViewHolder);
        return videoListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Item item = data.get(position);
        final VideoListViewHolder itemController = (VideoListViewHolder) holder;
        String videoUrl = item.getUrl();
        String viedoId = videoUrl.split("v=")[1];
        Log.d("Video Adapter", viedoId);
        itemController.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(viedoId,0);
                youTubePlayer.pause();
            }
        });
        itemController.video_title_tv.setText(item.getVideo_title());
        itemController.channel_tv.setText(item.getChannel());
        itemController.hits_tv.setText(item.getHits());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



}
