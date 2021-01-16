package co.kr.todayplay.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.R;

public class JournalMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<Item> data;
    public ArrayList<JournalListViewHolder> journal_itemController = new ArrayList<>();
    private static OnItemClickListener mListener = null;
    private int now_pos = 0;

    public static class Item{
        private int drawable;
        private int unselected_drawable;
        private String title;
        public Item(){

        }
        public Item(String title){
            this.title = title;
        }
        public Item(int drawable, int unselected_drawable, String title){
            this.drawable = drawable;
            this.unselected_drawable = unselected_drawable;
            this.title = title;
        }

        public int getUnselected_drawable() {
            return unselected_drawable;
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

    public void setNowPosition(int position) {
        now_pos = position;
    }

    public static class JournalListViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView title_tv;
        public TextView cv_inner_tv;
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
                        cardView.setCardBackgroundColor(Color.parseColor("#ffe34e"));
                    }
                }
            });
            cardView = (CardView)itemView.findViewById(R.id.journal_cv);
            cv_inner_tv = (TextView)itemView.findViewById(R.id.journal_cv_tv);
            title_tv = (TextView)itemView.findViewById(R.id.journal_tv);
            imageView = (ImageView)itemView.findViewById(R.id.journal_iv);
        }
    }

    public JournalMenuAdapter(List<Item> data){
        super();
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.journal_menu_item, parent, false);
        JournalListViewHolder holder = new JournalListViewHolder(view);
        journal_itemController.add(holder);
        return holder;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Item item = data.get(position);
        final JournalListViewHolder itemController = (JournalListViewHolder) holder;
        itemController.title_tv.setText(item.title);
        if (position == now_pos){
            itemController.cardView.setCardBackgroundColor(Color.parseColor("#ffe34e"));
            if(now_pos == 0){
                itemController.cv_inner_tv.setText("ALL");
                itemController.cv_inner_tv.setTextColor(Color.parseColor("#252525"));
                itemController.imageView.setImageResource(android.R.color.transparent);
            }
            else {
                itemController.imageView.setImageResource(item.drawable);
                itemController.cv_inner_tv.setText("");
            }

        }
        else{
            itemController.cardView.setCardBackgroundColor(Color.parseColor("#252525"));
            if (position == 0){
                itemController.cv_inner_tv.setText("ALL");
                itemController.cv_inner_tv.setTextColor(Color.parseColor("#fed700"));
                itemController.imageView.setImageResource(android.R.color.transparent);
            }
            else {
                itemController.imageView.setImageResource(item.unselected_drawable);
                itemController.cv_inner_tv.setText("");
            }
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}
