package co.kr.todayplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.R;

public class PerformTagAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<String> data = new ArrayList<>();
    public ArrayList<PerformTagViewHolder> tagItemController = new ArrayList<>();

    public static class PerformTagViewHolder extends RecyclerView.ViewHolder{
        public TextView tag;
        public PerformTagViewHolder(View itemView){
            super(itemView);
            tag = (TextView)itemView.findViewById(R.id.tag_tv);
        }
    }

    public PerformTagAdapter(ArrayList<String> data){
        super();
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.perform_tag_item, parent, false);
        PerformTagViewHolder holder = new PerformTagViewHolder(view);
        tagItemController.add(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final String tag_name = data.get(position);
        final PerformTagViewHolder itemController = (PerformTagViewHolder) holder;
        itemController.tag.setText(tag_name);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
