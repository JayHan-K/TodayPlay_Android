package co.kr.todayplay.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.fragment.CategoryClickedFragment;
import co.kr.todayplay.object.RecommandItem;
import co.kr.todayplay.object.totalItem;

public class TotalCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<totalItem> data = new ArrayList<>();
    private ArrayList<RecommandHolder> itemController = new ArrayList<>();
    String category;

    public class RecommandHolder extends RecyclerView.ViewHolder{
        private TextView title_tv;

        public RecommandHolder(@NonNull View itemView){
            super(itemView);
            this.title_tv = itemView.findViewById(R.id.total_tv);
        }
    }

    public TotalCategoryAdapter(ArrayList<totalItem> data,String category){
        super();
        this.category =category;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.total_category, parent, false);
        RecommandHolder recommandHolder = new RecommandHolder(view);
        itemController.add(recommandHolder);
        return recommandHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position){
       totalItem recommandItem = data.get(position);
       RecommandHolder itemController = (RecommandHolder) holder;
       itemController.title_tv.setText(recommandItem.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle2 = new Bundle();
                bundle2.putString("category_name",recommandItem.getTitle());
                Log.d("keywordinclicked2","keywordinclicked"+recommandItem.getTitle());
                bundle2.putString("category",category);
                CategoryClickedFragment categoryClickedFragment = new CategoryClickedFragment();
                categoryClickedFragment.setArguments(bundle2);
                ((MainActivity)view.getContext()).replaceFragment2(categoryClickedFragment);

            }
        });

    }

    @Override
    public int getItemCount(){return data.size();}



}
