package co.kr.todayplay.adapter;

import android.content.Context;
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
        private int drawable;
        private String perform_title;
        private String time;
        private String directors;
        private String actors;

        public Item(){}

        public Item(int drawable, String perform_title, String time, String directors, String actors){
            this.drawable = drawable;
            this.perform_title = perform_title;
            this.time = time;
            this.directors = directors;
            this.actors = actors;
        }

        public int getDrawable() {
            return drawable;
        }

        public String getActors() {
            return actors;
        }

        public String getDirectors() {
            return directors;
        }

        public String getPerform_title() {
            return perform_title;
        }

        public String getTime() {
            return time;
        }
    }

    public static class HistoryListViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView perform_title_tv;
        public TextView time_tv;
        public TextView actors_tv;
        public TextView directors_tv;

        public HistoryListViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.perform_history_iv);
            perform_title_tv = (TextView)itemView.findViewById(R.id.perform_history_title_tv);
            time_tv = (TextView)itemView.findViewById(R.id.perform_history_time_tv);
            directors_tv = (TextView)itemView.findViewById(R.id.perform_history_directors_tv);
            actors_tv = (TextView)itemView.findViewById(R.id.perform_history_actors_tv);
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
        itemController.imageView.setImageResource(item.getDrawable());
        itemController.perform_title_tv.setText(item.getPerform_title());
        itemController.time_tv.setText(item.getTime());
        itemController.directors_tv.setText(item.getDirectors());
        itemController.actors_tv.setText(item.getActors());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
