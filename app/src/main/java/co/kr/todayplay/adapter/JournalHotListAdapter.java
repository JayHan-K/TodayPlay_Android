package co.kr.todayplay.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.R;

public class JournalHotListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<HotItem> data = new ArrayList<>();
    public ArrayList<JournalHotListViewHolder> hot_itemController = new ArrayList<>();

    public static class HotItem{
        private int drawable;
        private String content;

        public HotItem(){

        }
        public HotItem(int drawable, String content){
            this.content = content;
            this.drawable = drawable;
        }
        public String getContent(){
            return content;
        }
        public int getDrawable(){
            return drawable;
        }
    }

    public class JournalHotListViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView index_tv;
        public TextView content_tv;
        public RoundedImageView roundedImageView;

        public JournalHotListViewHolder(View itemView){
            super(itemView);
            cardView = itemView.findViewById(R.id.community_hot_issue_cv);
            index_tv = itemView.findViewById(R.id.community_hot_issue_cv_index_tv);
            content_tv = itemView.findViewById(R.id.community_hot_issue_cv_title_tv);
            roundedImageView = itemView.findViewById(R.id.community_hot_issue_cv_iv);
        }

    }

    public JournalHotListAdapter(ArrayList<HotItem> data){
        super();
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.journal_hot_issue_card_view, parent, false);
        JournalHotListViewHolder journalHotListViewHolder = new JournalHotListViewHolder(view);
        hot_itemController.add(journalHotListViewHolder);
        return journalHotListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HotItem item = data.get(position);
        JournalHotListViewHolder itemController = (JournalHotListViewHolder) holder;
        int index = position + 1;
        String str = "%02d".format(Integer.toString(index));
        itemController.index_tv.setText(str);
        itemController.roundedImageView.setImageResource(item.getDrawable());
        itemController.content_tv.setText(item.getContent());
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
