package co.kr.todayplay.adapter;

import android.content.Context;
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
import co.kr.todayplay.fragment.PerformInfoFragment;
import co.kr.todayplay.object.RecommandItem;

public class ProfileFavAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<RecommandItem> data = new ArrayList<>();
    private ArrayList<RecommandHolder> itemController = new ArrayList<>();


    public class RecommandHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView title_tv;

        public RecommandHolder(@NonNull View itemView){
            super(itemView);
            this.imageView = itemView.findViewById(R.id.pf_fav_iv);
            this.title_tv = itemView.findViewById(R.id.pf_fav_tv);
        }
    }

    public ProfileFavAdapter(ArrayList<RecommandItem> data){
        super();
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pf_fav_play, parent, false);
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
                PerformInfoFragment performInfoFragment = new PerformInfoFragment();
                ((MainActivity)view.getContext()).replaceFragment2(performInfoFragment,0);
            }
        });

    }

    @Override
    public int getItemCount(){return data.size();}

}
