package co.kr.todayplay.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.R;

public class PerformReviewAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<ReviewItem> data = new ArrayList<>();
    private ArrayList<ReviewHolder> itemController = new ArrayList<>();
    private Context context;

    public static class ReviewItem{
        private String profile_path;
        private int profile_drawable;
        private String user_name;
        private boolean thumb;
        private String age_level;
        private String date;
        private int num_heart;
        private int num_review;
        private String good_thing;
        private String bad_thing;
        private ArrayList<String> review_pics;

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

        public ReviewItem(int profile_drawable, String user_name, boolean thumb, String age_level, String date, int num_heart, int num_review, String good_thing, String bad_thing, ArrayList<String> review_pics){
            this.profile_drawable = profile_drawable;
            this.user_name = user_name;
            this.thumb = thumb;
            this.age_level = age_level;
            this.date = date;
            this.num_heart = num_heart;
            this.num_review = num_review;
            this.good_thing = good_thing;
            this.bad_thing = bad_thing;
            this.review_pics = review_pics;
        }

        public ReviewItem(String profile_path, String user_name, boolean thumb, String age_level, String date, int num_heart, int num_review, String good_thing, String bad_thing, ArrayList<String> review_pics){
            this.profile_path = profile_path;
            this.user_name = user_name;
            this.thumb = thumb;
            this.age_level = age_level;
            this.date = date;
            this.num_heart = num_heart;
            this.num_review = num_review;
            this.good_thing = good_thing;
            this.bad_thing = bad_thing;
            this.review_pics = review_pics;
        }

        public String getProfile_path() {
            return profile_path;
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

        public ArrayList<String> getReview_pics() {
            return review_pics;
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
        private RecyclerView photo_rv;
        private ConstraintLayout parent_layout;

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
            this.photo_rv = itemView.findViewById(R.id.review_photo_rv);
            this.parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
    public PerformReviewAdapter2(Context context, ArrayList<ReviewItem> data){
        super();
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.perform_review_item, parent, false);
        ReviewHolder reviewHolder = new ReviewHolder(view);
        itemController.add(reviewHolder);
        return reviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ReviewItem item = data.get(position);
        ReviewHolder itemController = (ReviewHolder) holder;
            if(item.getProfile_path().equals("")) itemController.profile_iv.setImageResource(R.drawable.icon_mypage);
            else{
                Bitmap bm = BitmapFactory.decodeFile(item.getProfile_path());
                itemController.profile_iv.setImageBitmap(bm);
            }
            //수정 profile image
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
            if (item.getReview_pics().size() > 0){
                itemController.photo_rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false){
                    @Override
                    public boolean canScrollHorizontally() {
                        return false;
                    }
                });
                PerformReviewImageAdapter2 PerformReviewImageAdapter2 = new PerformReviewImageAdapter2(item.getReview_pics());
                itemController.photo_rv.setAdapter(PerformReviewImageAdapter2);
            }
            else Log.d("IsImage", "onBindViewHolder: image is null!!");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
