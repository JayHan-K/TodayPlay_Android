package co.kr.todayplay.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.kr.todayplay.DBHelper.PlayDB.PlayDBHelper;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.fragment.PerformInfoFragment;
import co.kr.todayplay.object.Data;
import co.kr.todayplay.object.category_recommend;

public class CategoryDetailRecyAdapter extends RecyclerView.Adapter<CategoryDetailRecyAdapter.ItemViewHolder> {


    private ArrayList<String> listData = new ArrayList<>();
    private ArrayList<Integer> listdata2 = new ArrayList<>();
    @NonNull
    @Override
    public CategoryDetailRecyAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_detailed_recy, parent, false);
        return  new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryDetailRecyAdapter.ItemViewHolder holder, int position) {
        holder.onBind(listData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("play_id",listdata2.get(position));
                PerformInfoFragment performInfoFragment = new PerformInfoFragment();
                performInfoFragment.setArguments(bundle);

                ((MainActivity)holder.itemView.getContext()).replaceFragment2(performInfoFragment);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void addItem(ArrayList<String> data,ArrayList<Integer> data2) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData=data;
        listdata2=data2;
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        private android.widget.ImageView ImageView;

        ItemViewHolder(View itemview) {
            super(itemview);
            ImageView = itemView.findViewById(R.id.imageview);
        }

        void onBind(String data) {
            Bitmap bm = BitmapFactory.decodeFile(data);
            ImageView.setImageBitmap(bm);
        }
    }
}
