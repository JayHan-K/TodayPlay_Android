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
import co.kr.todayplay.RecyclerDecoration;
import co.kr.todayplay.fragment.Journal.JournalDetailFragment;
import co.kr.todayplay.fragment.ProfileFragment;
import co.kr.todayplay.object.RecommandItem;

public class ProfileMyScrapDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<RecommandItem> data = new ArrayList<>();
    private ArrayList<RecommandHolder> itemController = new ArrayList<>();
    private int user_id = -1;


    public class RecommandHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView title_tv;

        public RecommandHolder(@NonNull View itemView){
            super(itemView);
            this.imageView = itemView.findViewById(R.id.pf_scrap_post_iv);
            this.title_tv = itemView.findViewById(R.id.pf_scrap_post_tv);
        }
    }

    public ProfileMyScrapDetailAdapter(ArrayList<RecommandItem> data, int user_id){
        super();
        this.data = data;
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pf_scrap_rv_detail, parent, false);
        RecommandHolder recommandHolder = new RecommandHolder(view);
        itemController.add(recommandHolder);
        return recommandHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position){
        RecommandItem recommandItem = data.get(position);
        RecommandHolder itemController = (RecommandHolder) holder;
        itemController.imageView.setImageResource(recommandItem.getDrawable());
        itemController.title_tv.setText(recommandItem.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JournalDetailFragment journalDetailFragment = new JournalDetailFragment();
                ((MainActivity)view.getContext()).replaceFragment(journalDetailFragment, recommandItem.getId());
            }
        });

    }

    @Override
    public int getItemCount(){return data.size();}

}
