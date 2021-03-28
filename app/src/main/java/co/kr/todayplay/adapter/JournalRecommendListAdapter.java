package co.kr.todayplay.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.fragment.Journal.JournalDetailFragment;

public class JournalRecommendListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<Item> data = new ArrayList<>();
    public ArrayList<JournalRecommendListViewHolder> itemController = new ArrayList<>();

    public static class Item{
        private int journal_id;
        private String img_path;
        private int drawable;
        private String subtitle;
        private String title;

        public Item(){

        }
        public Item(int journal_id, int drawable, String subtitle, String title){
            this.journal_id = journal_id;
            this.subtitle = subtitle;
            this.drawable = drawable;
            this.title = title;
        }

        public Item(int journal_id, String img_path, String subtitle, String title){
            this.journal_id = journal_id;
            this.subtitle = subtitle;
            this.img_path = img_path;
            this.title = title;
        }

        public String getImg_path() {
            return img_path;
        }

        public String getTitle() {
            return title;
        }

        public String getSubtitle() {
            return subtitle;
        }
        public int getDrawable(){
            return drawable;
        }

        public int getJournal_id() {
            return journal_id;
        }
    }

    public class JournalRecommendListViewHolder extends RecyclerView.ViewHolder{
        public TextView journal_title_tv;
        public TextView journal_subtitle_tv;
        public ImageView imageView;

        public JournalRecommendListViewHolder(View itemView){
            super(itemView);
            journal_subtitle_tv = itemView.findViewById(R.id.journal_subtitle_tv);
            journal_title_tv = itemView.findViewById(R.id.journal_title_tv);
            imageView = itemView.findViewById(R.id.journal_iv);
        }

    }

    public JournalRecommendListAdapter(ArrayList<Item> data){
        super();
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.journal_recommend_item, parent, false);
        JournalRecommendListViewHolder journalRecommendListViewHolder = new JournalRecommendListViewHolder(view);
        itemController.add(journalRecommendListViewHolder);
        return journalRecommendListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item item = data.get(position);
        JournalRecommendListViewHolder itemController = (JournalRecommendListViewHolder) holder;
        itemController.journal_subtitle_tv.setText(item.getSubtitle());
        Bitmap bm = BitmapFactory.decodeFile(item.getImg_path());
        itemController.imageView.setImageBitmap(bm);
        itemController.journal_title_tv.setText(item.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JournalDetailFragment journalDetailFragment = new JournalDetailFragment();
                ((MainActivity)view.getContext()).replaceFragment(journalDetailFragment, item.getJournal_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
