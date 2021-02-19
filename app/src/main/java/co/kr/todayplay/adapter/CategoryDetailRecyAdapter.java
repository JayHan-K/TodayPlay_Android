package co.kr.todayplay.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.kr.todayplay.R;
import co.kr.todayplay.object.Data;

public class CategoryDetailRecyAdapter extends RecyclerView.Adapter<CategoryDetailRecyAdapter.ItemViewHolder> {

    private ArrayList<Data> listData = new ArrayList<>();
    @NonNull
    @Override
    public CategoryDetailRecyAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_detailed_recy, parent, false);
        return  new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryDetailRecyAdapter.ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void addItem(Data data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        private android.widget.ImageView ImageView;

        ItemViewHolder(View itemview) {
            super(itemview);
            ImageView = itemView.findViewById(R.id.imageview);
        }

        void onBind(Data data) {
            ImageView.setImageResource(data.getresId());
        }
    }
}
