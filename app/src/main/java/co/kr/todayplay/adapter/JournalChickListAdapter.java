package co.kr.todayplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.kr.todayplay.R;

public class JournalChickListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ChickItem> data = new ArrayList<>();
    private ArrayList<JournalChickHolder> itemController = new ArrayList<>();
    public static class ChickItem{
        private int drawable;
        private String title;
        private String subTitle;

        public ChickItem(){}
        public ChickItem(int drawable, String title, String subTitle){
            this.drawable = drawable;
            this.title = title;
            this.subTitle = subTitle;
        }
        public int getDrawable(){
            return drawable;
        }
        public String getTitle(){
            return title;
        }
        public String getSubTitle(){
            return subTitle;
        }
    }

    public class JournalChickHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView title_tv;
        private TextView subtitle_tv;
        public JournalChickHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.chick_card_view_iv);
            this.title_tv = itemView.findViewById(R.id.chick_card_view_title_tv);
            this.subtitle_tv = itemView.findViewById(R.id.chick_card_view_subtitle_tv);
        }
    }

    public JournalChickListAdapter(ArrayList<ChickItem> data){
        super();
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.journal_chick_card_view, parent, false);
        JournalChickHolder journalChickHolder = new JournalChickHolder(view);
        itemController.add(journalChickHolder);
        return journalChickHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChickItem chickItem = data.get(position);
        JournalChickHolder itemController = (JournalChickHolder) holder;
        itemController.imageView.setImageResource(chickItem.getDrawable());
        itemController.subtitle_tv.setText(chickItem.getSubTitle());
        itemController.title_tv.setText(chickItem.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CommunityHotIssueJournalAdapter 참고 itemClickListener.onItemClicked(holder, journal, position)
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
