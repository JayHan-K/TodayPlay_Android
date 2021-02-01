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
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.fragment.Journal.JournalDetailFragment;
import co.kr.todayplay.object.Journal;

public class PerformDetailJournalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public ArrayList<JournalItem> data = new ArrayList<>();
    private ArrayList<JournalHolder> itemController = new ArrayList<>();

    public static class JournalItem{
        private int drawable;
        private String sub_title;
        private String title;

        public JournalItem(){}
        public JournalItem(int drawable, String sub_title, String title){
            this.drawable = drawable;
            this.sub_title = sub_title;
            this.title = title;
        }

        public String getSub_title() {
            return sub_title;
        }

        public String getTitle() {
            return title;
        }
        public int getDrawable(){
            return drawable;
        }
    }

    public class JournalHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView sub_tv;
        private TextView title_tv;
        public JournalHolder(@NonNull View itemView){
            super(itemView);
            this.imageView = itemView.findViewById(R.id.perform_detail_journal_iv);
            this.sub_tv = itemView.findViewById(R.id.perform_detail_sub_tv);
            this.title_tv = itemView.findViewById(R.id.perform_detail_title_tv);
        }
    }

    public PerformDetailJournalAdapter(ArrayList<JournalItem> data){
        super();
        this.data = data;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.perform_detail_journal_item, parent, false);
        JournalHolder journalHolder = new JournalHolder(view);
        itemController.add(journalHolder);
        return journalHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        JournalItem item = data.get(position);
        JournalHolder itemController = (JournalHolder) holder;
        itemController.imageView.setImageResource(item.getDrawable());
        itemController.sub_tv.setText(item.getSub_title());
        itemController.title_tv.setText(item.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JournalDetailFragment journalDetailFragment = new JournalDetailFragment();
                ((MainActivity)view.getContext()).replaceFragment(journalDetailFragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
