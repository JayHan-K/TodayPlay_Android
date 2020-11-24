package co.kr.todayplay.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.R;

public class JournalListAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<Item> data;
    public ArrayList<JournalListViewHolder> journal_itemController = new ArrayList<>();

    public JournalListAdaptor(List<Item> data){
        super();
        this.data = data;
    }

    public static class JournalListViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ImageView imageView;
        public JournalListViewHolder(View itemView){
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.journal_tv);
            imageView = (ImageView)itemView.findViewById(R.id.journal_iv);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.journal_list_item, parent, false);
        JournalListViewHolder holder = new JournalListViewHolder(view);
        journal_itemController.add(holder);
        return holder;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Item item = data.get(position);
        final JournalListViewHolder itemController = (JournalListViewHolder) holder;
        itemController.textView.setText(item.title);
        itemController.imageView.setImageResource(item.drawable);
    }


    @Override
    public int getItemCount() {
        return 0;
    }

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
}
