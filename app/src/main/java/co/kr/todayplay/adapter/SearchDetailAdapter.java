package co.kr.todayplay.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.kr.todayplay.R;
import co.kr.todayplay.object.Data;


public class SearchDetailAdapter extends RecyclerView.Adapter<SearchDetailAdapter.ItemViewHolder> {
    ArrayList<Data> items = new ArrayList<>();


    public interface OnItemClickListener{
        public void onItemClick(View view,int position);
    }
    private OnItemClickListener onItemClickListener;

    public SearchDetailAdapter(ArrayList<Data> items,OnItemClickListener onItemClickListener){
        this.items = items;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SearchDetailAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.suggestdetail_result,parent,false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchDetailAdapter.ItemViewHolder holder, int position) {
        holder.onBind(items.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        public ItemViewHolder(View itemView){
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.pf_post_iv2);
        }

        void onBind(Data data){imageView.setImageResource(data.getresId());}
    }
}
