package co.kr.todayplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.R;

public class PerformReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<PerformReviewAdapter.ReviewItem> data = new ArrayList<>();
    private ArrayList<PerformReviewAdapter.ReviewHolder> itemController = new ArrayList<>();

    public static class ReviewItem{
        private int profile_drawable;
        private String user_name;
        private boolean thumb;
        private String age_level;
        private String date;
        private int num_heart;
        private int num_review;
        private String good_thing;
        private String bad_thing;

        public ReviewItem(){}
        public ReviewItem(int profile_drawable, String user_name, boolean thumb, String age_level, String date, int num_heart, int num_review, String good_thing, String bad_thing){
            this.profile_drawable = profile_drawable;
            this.user_name = user_name;
            this.thumb = thumb;
            this.age_level = age_level;
            this.date = date;
            this.num_heart = num_heart;
            this.num_review = num_review;
            this.good_thing = good_thing;
            this.bad_thing = bad_thing;
        }

        public boolean isThumb() {
            return thumb;
        }
        public int getNum_heart() {
            return num_heart;
        }

        public int getProfile_drawable() {
            return profile_drawable;
        }

        public int getNum_review() {
            return num_review;
        }

        public String getAge_level() {
            return age_level;
        }

        public String getUser_name() {
            return user_name;
        }

        public String getBad_thing() {
            return bad_thing;
        }

        public String getDate() {
            return date;
        }

        public String getGood_thing() {
            return good_thing;
        }
    }

    public class ReviewHolder extends RecyclerView.ViewHolder{
        private ImageView profile_iv;
        private TextView user_name_tv;
        private ImageView thumb_iv;
        private TextView age_level_tv;
        private TextView date_tv;
        private TextView num_heart_tv;
        private TextView num_review_tv;
        private TextView good_thing_tv;
        private TextView bad_thing_tv;
        //private RecyclerView photo_rv;

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
            this.profile_iv = itemView.findViewById(R.id.review_user_iv);
            this.user_name_tv = itemView.findViewById(R.id.user_name_tv);
            this.thumb_iv = itemView.findViewById(R.id.thumb_img);
            this.age_level_tv = itemView.findViewById(R.id.age_level_tv);
            this.date_tv = itemView.findViewById(R.id.review_date_tv);
            this.num_heart_tv = itemView.findViewById(R.id.num_heart_tv);
            this.num_review_tv = itemView.findViewById(R.id.num_review_tv);
            this.good_thing_tv = itemView.findViewById(R.id.good_review_tv);
            this.bad_thing_tv = itemView.findViewById(R.id.bad_review_tv);
            //this.photo_rv = itemView.findViewById(R.id.review_photo_rv);
        }
    }

    public PerformReviewAdapter(ArrayList<PerformReviewAdapter.ReviewItem> data){
        super();
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.perform_review_item, parent, false);
        PerformReviewAdapter.ReviewHolder reviewHolder = new PerformReviewAdapter.ReviewHolder(view);
        itemController.add(reviewHolder);
        return reviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ReviewItem item = data.get(position);
        PerformReviewAdapter.ReviewHolder itemController = (PerformReviewAdapter.ReviewHolder) holder;
        itemController.profile_iv.setImageResource(item.getProfile_drawable());
        itemController.user_name_tv.setText(item.user_name);
        if(item.isThumb()) itemController.thumb_iv.setImageResource(R.drawable.icon_good);
        else itemController.thumb_iv.setImageResource(R.drawable.icon_bad);
        itemController.num_heart_tv.setText(Integer.toString(item.getNum_heart()));
        itemController.num_review_tv.setText(Integer.toString(item.getNum_review()));
        itemController.age_level_tv.setText(item.getAge_level());
        itemController.date_tv.setText(item.getDate());
        itemController.good_thing_tv.setText(item.getGood_thing());
        itemController.bad_thing_tv.setText(item.getBad_thing());
        //recycler
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
