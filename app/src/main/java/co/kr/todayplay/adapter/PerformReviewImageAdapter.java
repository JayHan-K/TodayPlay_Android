package co.kr.todayplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.R;

public class PerformReviewImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public ArrayList<Integer> data = new ArrayList<>();
    private ArrayList<PerformReviewImageAdapter.ReviewImageHolder> itemController = new ArrayList<>();

    public class ReviewImageHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;
        public ReviewImageHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.perform_review_iv);
            this.textView = itemView.findViewById(R.id.perform_review_image_tv);
        }
    }
    public PerformReviewImageAdapter(ArrayList<Integer> data){
        super();
        this.data = data;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.perform_review_image_item, parent, false);
        PerformReviewImageAdapter.ReviewImageHolder reviewImageHolder = new PerformReviewImageAdapter.ReviewImageHolder(view);
        itemController.add(reviewImageHolder);
        return reviewImageHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int reviewImageItem = data.get(position);
        PerformReviewImageAdapter.ReviewImageHolder itemController = (PerformReviewImageAdapter.ReviewImageHolder) holder;
        if(position < 3){
            itemController.imageView.setImageResource(reviewImageItem);
            itemController.textView.setText("");
        }
        else if(position == 3){
            itemController.imageView.setImageResource(reviewImageItem);
            if(data.size() == 4){
                itemController.textView.setText("");
            }
            else if(data.size() > 4){
                itemController.textView.setText("+ " + (data.size() - 4));
            }
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
