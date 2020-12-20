package co.kr.todayplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.R;
import co.kr.todayplay.object.Journal;

public class PerformDetailJournalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public ArrayList<PerformDetailJournalAdapter.JournalItem> data = new ArrayList<>();
    private ArrayList<PerformDetailJournalAdapter.JournalHolder> itemController = new ArrayList<>();

    public static class JournalItem{
        private int drawable;

        public JournalItem(){}
        public JournalItem(int drawable){
            this.drawable = drawable;
        }
        public int getDrawable(){
            return drawable;
        }
    }

    public class JournalHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        public JournalHolder(@NonNull View itemView){
            super(itemView);
            this.imageView = itemView.findViewById(R.id.perform_detail_journal_iv);
        }
    }

    public PerformDetailJournalAdapter(ArrayList<PerformDetailJournalAdapter.JournalItem> data){
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
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
