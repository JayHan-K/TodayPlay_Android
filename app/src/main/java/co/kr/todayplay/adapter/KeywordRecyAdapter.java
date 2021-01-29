package co.kr.todayplay.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.kr.todayplay.R;
import co.kr.todayplay.object.totalItem;

public class KeywordRecyAdapter extends RecyclerView.Adapter<KeywordRecyAdapter.ItemViewHolder> {
    ArrayList<totalItem> items = new ArrayList<>();
    private Button button12;

    public interface OnItemClickListener{
        public void onItemClick(View view,int position);
    }
    private OnItemClickListener onItemClickListener;

    public KeywordRecyAdapter(ArrayList<totalItem> items,OnItemClickListener onItemClickListener){
        this.items = items;
        this.onItemClickListener =onItemClickListener;

    }

    @NonNull
    @Override
    public KeywordRecyAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.mykeyword_rv,parent,false);
        return new ItemViewHolder(view);
    }

    public void delItem(int position){
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,items.size());
    }

    @Override
    public void onBindViewHolder(@NonNull KeywordRecyAdapter.ItemViewHolder holder, final int position) {
        holder.onBind(items.get(position));

        holder.button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view,position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

//    public KeywordRecyAdapter(OnItemClickListener onItemClickListener){
//        this.onItemClickListener =onItemClickListener;
//    }


    static class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView keyword;
        Button button12;


        public ItemViewHolder(View itemView){
            super(itemView);

            keyword = itemView.findViewById(R.id.textView68);
            button12 = (Button)itemView.findViewById(R.id.button12);

        }


        public void onBind(totalItem data){

            keyword.setText(data.getTitle());

        }

    }


}
