package co.kr.todayplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import co.kr.todayplay.R;


public class MyPageQnAAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    public static final int QUESTION = 0;
    public static final int ANSWER = 1;

    List<Item> data;
    List<Item> unFilteredData;
    List<Item> filteredData;

    public MyPageQnAAdapter(List<Item> data){
        super();
        this.data = data;
        this.unFilteredData = data;
        this.filteredData = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType){
            case QUESTION:
                LayoutInflater inflater1 = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater1.inflate(R.layout.mypage_qna_item, parent, false);
                ListQuestionViewHolder q_holder = new ListQuestionViewHolder(view);
                return q_holder;
            case ANSWER:
                LayoutInflater inflater2 = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater2.inflate(R.layout.mypage_ans_item, parent, false);
                ListAnswerViewHolder a_holder = new ListAnswerViewHolder(view);
                FrameLayout layout = (FrameLayout)view.findViewById(R.id.hide_layout);
                ViewGroup.LayoutParams params = layout.getLayoutParams();
                params.height = 0;
                layout.setLayoutParams(params);
                return a_holder;

        }
        return null;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        final Item item = data.get(position);
        switch (item.type){
            case QUESTION:
                final ListQuestionViewHolder itemController = (ListQuestionViewHolder) holder;
                itemController.refferalItem = item;
                itemController.q_title.setText(item.content);
                itemController.comments.setText(String.valueOf(item.comments));
                itemController.date.setText(item.date);
                itemController.hits.setText(String.valueOf(item.hits));
                itemController.tag1.setText(item.tag1);
                itemController.tag2.setText(item.tag2);
                itemController.tag3.setText(item.tag3);

                itemController.itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        if(item.invisibleAnswer == null){
                            item.invisibleAnswer = new ArrayList<Item>();
                            int count = 0;
                            int pos = data.indexOf(itemController.refferalItem);
                            while (data.size() > pos+1 && data.get(pos + 1).type == ANSWER){
                                item.invisibleAnswer.add(data.remove(pos+1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos+1,count);
                        }else{
                            int pos = data.indexOf(itemController.refferalItem);
                            int index = pos+1;
                            for(Item i:item.invisibleAnswer){
                                data.add(index,i);
                                index++;
                            }
                            notifyItemRangeInserted(pos+1,index-pos-1);
                            item.invisibleAnswer = null;
                        }
                    }
                });

                break;
            case ANSWER:
                final ListAnswerViewHolder itemController1 = (ListAnswerViewHolder) holder;
                itemController1.refferalItem = item;
                itemController1.answer.setText(item.content);
                itemController1.date.setText(item.date);

                break;
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @Override
    public Filter getFilter() {
        return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()){
                    filteredData = unFilteredData;
                }else{
                    List<Item> filteringData = new ArrayList<>();

                    for(Item title : unFilteredData){
                        String name = title.toString();
                        if(name.toLowerCase().contains(charString.toLowerCase())){
                            filteringData.add(title);
                        }
                    }
                    filteredData = filteringData;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredData = (List<Item>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ListQuestionViewHolder extends RecyclerView.ViewHolder{
        public TextView q_title;
        public TextView tag1,tag2,tag3;
        public TextView date;
        public TextView hits, comments;
        public Item refferalItem;

        public ListQuestionViewHolder(View itemView){
            super(itemView);
            q_title = (TextView)itemView.findViewById(R.id.question);
            tag1 = (TextView)itemView.findViewById(R.id.tag1);
            tag2 = (TextView)itemView.findViewById(R.id.tag2);
            tag3 = (TextView)itemView.findViewById(R.id.tag3);
            date = (TextView)itemView.findViewById(R.id.q_date);
            hits = (TextView)itemView.findViewById(R.id.hits);
            comments = (TextView)itemView.findViewById(R.id.num_comments);

        }
    }

    public static class ListAnswerViewHolder extends RecyclerView.ViewHolder{
        public TextView answer;
        public ImageView ad_img;
        public TextView date;
        public TextView ad_id;
        public Item refferalItem;

        public ListAnswerViewHolder(View itemView){
            super(itemView);
            answer = (TextView)itemView.findViewById(R.id.answer);
            ad_img = (ImageView) itemView.findViewById(R.id.ad_icon);
            ad_id = (TextView)itemView.findViewById(R.id.ad_name);
            date = (TextView)itemView.findViewById(R.id.a_date);
        }


    }

    public static class Item{
        public int type, hits, comments;
        public String content, tag1, tag2, tag3, date;
        public List<Item> invisibleAnswer;

        public Item(){

        }
        public Item(int type, String content, String date){
            this.type = type;
            this.content = content;
            this.date = date;
        }

        public Item(int type, String content, String date, String tag1, String tag2, String tag3, int hits, int comments){
            this.type = type;
            this.content = content;
            this.date = date;
            this.tag1 = tag1;
            this.tag2 = tag2;
            this.tag3 = tag3;
            this.hits = hits;
            this.comments = comments;
        }
    }

}
