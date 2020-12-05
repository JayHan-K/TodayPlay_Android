package co.kr.todayplay.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.kr.todayplay.R;

public class JournalListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<Item> data;
    public ArrayList<JournalListViewHolder> journal_itemController = new ArrayList<>();
    private static OnItemClickListener mListener = null;

    public static class Item{
        private int drawable;
        private String title;
        public Item(){

        }
        public Item(int drawable, String title){
            this.drawable = drawable;
            this.title = title;
        }

        public int getDrawable(){
            return drawable;
        }

        public String getTitle(){
            return title;
        }

        public void setDrawable(int drawable) {
            this.drawable = drawable;
        }
        public void setTitle(String title) {
            this.title = title ;
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(View v, int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.mListener = listener;
    }

    public static class JournalListViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ImageView imageView;
        public JournalListViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        mListener.onItemClick(v, pos);
                        Log.e("hot_holder", "onItemClick: 1");
                    }
                }
            });
            textView = (TextView)itemView.findViewById(R.id.journal_tv);
            imageView = (ImageView)itemView.findViewById(R.id.journal_iv);
        }
    }

    public JournalListAdapter(List<Item> data){
        super();
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.journal_circle_item, parent, false);
        JournalListViewHolder holder = new JournalListViewHolder(view);
        journal_itemController.add(holder);
        return holder;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Item item = data.get(position);
        final JournalListViewHolder itemController = (JournalListViewHolder) holder;
        itemController.textView.setText(item.title);
        itemController.imageView.setImageResource(item.drawable);
        /*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //child fragment 전환
            }
        });
        */
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}
