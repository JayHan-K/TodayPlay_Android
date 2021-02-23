package co.kr.todayplay.adapter;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

public class JournalHotListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<Item> data = new ArrayList<>();
    public ArrayList<JournalTotalListViewHolder> itemController = new ArrayList<>();

    public static class Item{
        private int journal_id;
        private String img_path;
        private String subtitle;
        private String title;

        public Item(int journal_id, String img_path, String subtitle, String title){
            this.journal_id = journal_id;
            this.subtitle = subtitle;
            this.img_path = img_path;
            this.title = title;
        }

        public int getJournal_id() {
            return journal_id;
        }

        public String getTitle() {
            return title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public String getImg_path() {
            return img_path;
        }
    }

    public class JournalTotalListViewHolder extends RecyclerView.ViewHolder{
        public TextView journal_title_tv;
        public TextView journal_subtitle_tv;
        public ImageView imageView;

        public JournalTotalListViewHolder(View itemView){
            super(itemView);
            journal_subtitle_tv = itemView.findViewById(R.id.journal_subtitle_tv);
            journal_title_tv = itemView.findViewById(R.id.journal_title_tv);
            imageView = itemView.findViewById(R.id.journal_iv);
        }

    }

    public JournalHotListAdapter(ArrayList<Item> data){
        super();
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.journal_hot_item, parent, false);
        JournalTotalListViewHolder journalTotalListViewHolder = new JournalTotalListViewHolder(view);
        itemController.add(journalTotalListViewHolder);
        return journalTotalListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item item = data.get(position);
        JournalTotalListViewHolder itemController = (JournalTotalListViewHolder) holder;
        itemController.journal_subtitle_tv.setText(item.getSubtitle());
        Bitmap bm = BitmapFactory.decodeFile(item.getImg_path());
        itemController.imageView.setImageBitmap(bm);
        itemController.journal_title_tv.setText(item.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("journal_id", item.getJournal_id());
                JournalDetailFragment journalDetailFragment = new JournalDetailFragment();
                journalDetailFragment.setArguments(bundle);
                ((MainActivity)view.getContext()).replaceFragment(journalDetailFragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
