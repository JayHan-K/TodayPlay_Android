package co.kr.todayplay.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.R;

public class PerformHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public ArrayList<Item> data;
    public ArrayList<HistoryListViewHolder> itemController = new ArrayList<>();

    public static class Item{
        private String img_path;
        private int drawable;
        private String time;
        private String directors;
        private String crews;
        private String company;

        public Item(){}

        public Item(int drawable, String time, String crews, String directors, String company){
            this.drawable = drawable;
            this.crews = crews;
            this.time = time;
            this.directors = directors;
            this.company = company;
        }

        public Item(String img_path, String time, String crews, String directors, String company){
            this.img_path = img_path;
            this.crews = crews;
            this.time = time;
            this.directors = directors;
            this.company = company;
        }

        public int getDrawable() {
            return drawable;
        }


        public String getDirectors() {
            return directors;
        }

        public String getCompany() {
            return company;
        }

        public String getCrews() {
            return crews;
        }

        public String getTime() {
            return time;
        }

        public String getImg_path() {
            return img_path;
        }
    }

    public static class HistoryListViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView crews_tv;
        public TextView time_tv;
        public TextView makers_tv;
        public TextView directors_tv;

        public HistoryListViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.perform_history_iv);
            crews_tv = (TextView)itemView.findViewById(R.id.play_crews_tv);
            time_tv = (TextView)itemView.findViewById(R.id.perform_history_time_tv);
            directors_tv = (TextView)itemView.findViewById(R.id.perform_history_directors_tv);
            makers_tv = (TextView)itemView.findViewById(R.id.perform_history_actors_tv);
        }
    }

    public PerformHistoryAdapter(ArrayList<Item> data){
        super();
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.perform_history_item, parent, false);
        HistoryListViewHolder historyListViewHolder = new HistoryListViewHolder(view);
        itemController.add(historyListViewHolder);
        return historyListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Item item = data.get(position);
        final HistoryListViewHolder itemController = (HistoryListViewHolder) holder;
        Bitmap bm = BitmapFactory.decodeFile(item.getImg_path());
        itemController.imageView.setImageBitmap(bm);
        itemController.crews_tv.setText(item.getCrews());
        itemController.time_tv.setText(item.getTime());
        itemController.directors_tv.setText(item.getDirectors());
        itemController.makers_tv.setText(item.getCompany());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
