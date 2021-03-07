package co.kr.todayplay.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.kr.todayplay.DBHelper.PlayDB.PlayDBHelper;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.fragment.PerformInfoFragment;
import co.kr.todayplay.object.Data;


public class SearchDetailAdapter extends RecyclerView.Adapter<SearchDetailAdapter.ItemViewHolder> {
    ArrayList<Integer> items = new ArrayList<>();


    public interface OnItemClickListener{
        public void onItemClick(View view,int position);
    }
    private OnItemClickListener onItemClickListener;

    public SearchDetailAdapter(ArrayList<Integer> items,OnItemClickListener onItemClickListener){
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
        PlayDBHelper playDBHelper = new PlayDBHelper(holder.itemView.getContext(),"Play.db",null,1);
        String imgpath = holder.itemView.getContext().getFilesDir().toString()+"/"+playDBHelper.getPlayPoster(items.get(position));
        Bitmap bm = BitmapFactory.decodeFile(imgpath);
        holder.onBind(bm);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformInfoFragment performInfoFragment = new PerformInfoFragment();
                ((MainActivity)holder.itemView.getContext()).replaceFragment2(performInfoFragment,items.get(position));
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

        void onBind(Bitmap bm){imageView.setImageBitmap(bm);}
    }
}
