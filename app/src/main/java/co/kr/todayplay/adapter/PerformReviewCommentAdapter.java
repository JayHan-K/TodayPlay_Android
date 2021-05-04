package co.kr.todayplay.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class PerformReviewCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<PerformReviewCommentAdapter.CommentItem> data = new ArrayList<>();
    private ArrayList<PerformReviewCommentAdapter.CommentHolder> itemController = new ArrayList<>();
    private int visible_cmt = 0;
    private Context context;

    public static class CommentItem{
        private int profile_drawable;
        private String img_path;
        private String user_name;
        private String date;
        private String comment;
        private ArrayList<CommentItem> recomment_data;
        private boolean isRecomment = false;

        public CommentItem(){}
        public CommentItem(int profile_drawable, String user_name, String date, String comment, boolean isRecomment){
            this.profile_drawable = profile_drawable;
            this.user_name = user_name;
            this.date = date;
            this.comment = comment;
            this.isRecomment = isRecomment;
        }

        public CommentItem(String img_path, String user_name, String date, String comment, boolean isRecomment){
            this.img_path = img_path;
            this.user_name = user_name;
            this.date = date;
            this.comment = comment;
            this.isRecomment = isRecomment;
        }

        public CommentItem(int profile_drawable, String user_name, String date, String comment, ArrayList<CommentItem> recomment_data, boolean isRecomment){
            this.profile_drawable = profile_drawable;
            this.user_name = user_name;
            this.date = date;
            this.comment = comment;
            this.recomment_data = recomment_data;
            this.isRecomment = isRecomment;
        }

        public CommentItem(String img_path, String user_name, String date, String comment, ArrayList<CommentItem> recomment_data, boolean isRecomment){
            this.img_path = img_path;
            this.user_name = user_name;
            this.date = date;
            this.comment = comment;
            this.recomment_data = recomment_data;
            this.isRecomment = isRecomment;
        }

        public int getProfile_drawable() {
            return profile_drawable;
        }

        public String getImg_path() {
            return img_path;
        }

        public String getDate() {
            return date;
        }

        public String getUser_name() {
            return user_name;
        }

        public ArrayList<CommentItem> getRecomment_data() {
            return recomment_data;
        }

        public String getComment() {
            return comment;
        }

        public boolean isRecomment() {
            return isRecomment;
        }
    }

    public class CommentHolder extends RecyclerView.ViewHolder{
        private ImageView profile_iv;
        private TextView user_name_tv;
        private TextView date_tv;
        private TextView comment_tv;
        private RecyclerView recomment_rv;
        private ConstraintLayout parent_layout;
        private Button rcm_btn;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            this.profile_iv = itemView.findViewById(R.id.comment_user_iv);
            this.user_name_tv = itemView.findViewById(R.id.user_name_tv);
            this.date_tv = itemView.findViewById(R.id.comment_date_tv);
            this.comment_tv = itemView.findViewById(R.id.comment_tv);
            this.recomment_rv = itemView.findViewById(R.id.recomment_rv);
            this.parent_layout = itemView.findViewById(R.id.parent_layout);
            this.rcm_btn = itemView.findViewById(R.id.rcm_btn);
        }
    }
    public PerformReviewCommentAdapter(Context context, ArrayList<PerformReviewCommentAdapter.CommentItem> data){
        super();
        this.context = context;
        this.data = data;
        this.visible_cmt = 0;
    }

    public PerformReviewCommentAdapter(ArrayList<PerformReviewCommentAdapter.CommentItem> data){
        super();
        this.data = data;
        this.visible_cmt = 0;
    }

    public int getVisible_cmt() {
        return visible_cmt;
    }

    public void setVisible_cmt(int visible_cmt) {
        this.visible_cmt = visible_cmt;
    }

    public int getDataSize(){return this.data.size();}

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.perform_review_comment_item, parent, false);
        PerformReviewCommentAdapter.CommentHolder reviewHolder = new PerformReviewCommentAdapter.CommentHolder(view);
        itemController.add(reviewHolder);
        return reviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CommentItem item = data.get(position);
        PerformReviewCommentAdapter.CommentHolder itemController = (PerformReviewCommentAdapter.CommentHolder) holder;
        if(!item.getImg_path().equals("")){
            Bitmap bm = BitmapFactory.decodeFile(item.getImg_path());
            itemController.profile_iv.setImageBitmap(bm);
        }
        itemController.user_name_tv.setText(item.user_name);
        itemController.date_tv.setText(item.getDate());
        itemController.comment_tv.setText(item.getComment());
        if(item.isRecomment){
            itemController.parent_layout.setBackgroundColor(Color.TRANSPARENT);
            itemController.rcm_btn.setVisibility(View.GONE);
        }
        itemController.rcm_btn.setVisibility(View.GONE);

        //recycler
        if (item.recomment_data != null){
            itemController.recomment_rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false){
                @Override
                public boolean canScrollVertically() {
                        return false;
                    }
            });
            PerformReviewCommentAdapter performReviewCommentAdapter = new PerformReviewCommentAdapter(item.getRecomment_data());
            itemController.recomment_rv.setAdapter(performReviewCommentAdapter);
        }
        else Log.d("IsRecomment", "onBindViewHolder: Recomment is null!!");
    }

    @Override
    public int getItemCount() {
        return visible_cmt;
    }
}
