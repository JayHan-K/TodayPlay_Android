package co.kr.todayplay.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.kr.todayplay.R;
import co.kr.todayplay.object.totalItem;

public class SearchSuggestAdapter extends RecyclerView.Adapter<SearchSuggestAdapter.ItemViewHolder> implements Filterable {

    private List<String> mDataList;
    private List<String> mDataListAll;

    public SearchSuggestAdapter(List<String> items){
        mDataList = items;
        mDataListAll = new ArrayList<>(items);
    }
    private onItemListener mListener;
    public void setOnClickListener(onItemListener listener){
        mListener = listener;
    }
    public void dataSetChanged(List<String> exampleList){
        mDataList = exampleList;
        notifyDataSetChanged();
    }




    @NonNull
    @Override
    public SearchSuggestAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_rv,parent,false);

        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchSuggestAdapter.ItemViewHolder holder, final int position) {
        String currentItem = mDataList.get(position);

        holder.textView69.setText(currentItem);
        if(mListener != null){
            final int pos = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClicked(position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private final Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<String> filteredList = new ArrayList<>();
            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(mDataListAll);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(String item : mDataListAll){
                    if(item.toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mDataList.clear();
            mDataList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };


    static class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView textView69;
        ItemViewHolder(View itemView){
            super(itemView);
            textView69 = itemView.findViewById(R.id.textView69);
        }
    }

    public interface onItemListener{
        void onItemClicked(int position);
    }

    public String ReturnFiltered(int position){

        String filtered = mDataList.get(position);
        System.out.println(filtered);
        return filtered;
    }

}
