package co.kr.todayplay.adapter;

import android.content.Context;
import android.net.Uri;
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
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.fragment.perform.PerformTotalReviewCommentFragment;

public class PerformTotalReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<PerformTotalReviewAdapter.ReviewItem> data = new ArrayList<>();
    private ArrayList<PerformTotalReviewAdapter.ReviewHolder> itemController = new ArrayList<>();
    private Context context;
    boolean more_flag = false;
    PerformTotalReviewCommentFragment performTotalReviewCommentFragment = new PerformTotalReviewCommentFragment();

    public static class ReviewItem{
        private String profile_path;
        private String user_name;
        private boolean thumb;
        private String age_level;
        private String date;
        private int num_heart;
        private int num_review;
        private String good_thing;
        private String bad_thing;
        private String tip;
        private ArrayList<Uri> review_pics;
        private int num_comment;

        public ReviewItem(){}

        public ReviewItem(String profile_path, String user_name, boolean thumb, String age_level, String date, int num_heart, int num_review, String good_thing, String bad_thing, String tip, ArrayList<Uri> review_pics, int num_comment){
            this.profile_path = profile_path;
            this.user_name = user_name;
            this.thumb = thumb;
            this.age_level = age_level;
            this.date = date;
            this.num_heart = num_heart;
            this.num_review = num_review;
            this.good_thing = good_thing;
            this.bad_thing = bad_thing;
            this.tip = tip;
            this.review_pics = review_pics;
            this.num_comment = num_comment;
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

        public String getTip() {
            return tip;
        }

        public String getDate() {
            return date;
        }

        public String getGood_thing() {
            return good_thing;
        }

        public ArrayList<Uri> getReview_pics() {
            return review_pics;
        }

        public int getNum_comment() {
            return num_comment;
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
        private TextView more_tv;
        private TextView tip_tv;
        private ConstraintLayout tip_layout;
        private RecyclerView photo_rv;
        private ConstraintLayout parent_layout;
        private TextView more_comment_tv;

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
            this.more_comment_tv = itemView.findViewById(R.id.more_comment_tv);
            this.more_tv = itemView.findViewById(R.id.more_tv);
            this.tip_layout = itemView.findViewById(R.id.tip_part);
            this.tip_tv = itemView.findViewById(R.id.tip_tv);
        }
    }
    public PerformTotalReviewAdapter(Context context, ArrayList<PerformTotalReviewAdapter.ReviewItem> data){
        super();
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.perform_total_review_item, parent, false);
        PerformTotalReviewAdapter.ReviewHolder reviewHolder = new PerformTotalReviewAdapter.ReviewHolder(view);
        itemController.add(reviewHolder);
        return reviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ReviewItem item = data.get(position);
        PerformTotalReviewAdapter.ReviewHolder itemController = (PerformTotalReviewAdapter.ReviewHolder) holder;
        if(item.getProfile_path().equals(""))itemController.profile_iv.setImageResource(R.drawable.icon_mypage);
        //수정 프로필 이미지
            itemController.user_name_tv.setText(item.user_name);
            if(item.isThumb()) itemController.thumb_iv.setImageResource(R.drawable.icon_good);
            else itemController.thumb_iv.setImageResource(R.drawable.icon_bad);
            itemController.num_heart_tv.setText(Integer.toString(item.getNum_heart()));
            itemController.num_review_tv.setText(Integer.toString(item.getNum_review()));
            itemController.age_level_tv.setText(item.getAge_level());
            itemController.date_tv.setText(item.getDate());
            itemController.good_thing_tv.setText(item.getGood_thing());
            itemController.bad_thing_tv.setText(item.getBad_thing());
            itemController.tip_tv.setText(item.getTip());
            //수정 더보기 숨기기
            if(item.getTip().equals("")) itemController.more_comment_tv.setVisibility(View.GONE);
            itemController.more_comment_tv.setText("댓글 " + item.getNum_comment() + "개 보기");
            //recycler
        //수정 리뷰 이미지
            if (item.review_pics != null){
                itemController.photo_rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false){
                    @Override
                    public boolean canScrollHorizontally() {
                        return false;
                    }
                });
                //PerformReviewImageAdapter performReviewImageAdapter = new PerformReviewImageAdapter(item.getReview_drawable());
                //itemController.photo_rv.setAdapter(performReviewImageAdapter);
            }
            else Log.d("IsImage", "onBindViewHolder: image is null!!");
            itemController.more_comment_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)view.getContext()).replaceFragment(performTotalReviewCommentFragment);
                }
            });

            if(more_flag){
                itemController.more_tv.setVisibility(View.GONE);
                itemController.tip_layout.setVisibility(View.VISIBLE);
            }
            else{
                itemController.more_tv.setVisibility(View.VISIBLE);
                itemController.tip_layout.setVisibility(View.GONE);
            }
        itemController.more_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemChanged(position);
                more_flag = true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
