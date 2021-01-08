package co.kr.todayplay.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.kr.todayplay.R
import co.kr.todayplay.`object`.DetailComment


class MyPageReviewCommentAdapter(comments: ArrayList<DetailComment>, context: Context) :
    RecyclerView.Adapter<MyPageReviewCommentAdapter.RealReviewCommentHolder>() {
    var comments = comments
    var context = context

    class RealReviewCommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var realReviewCommentTitleTextView : TextView = itemView.findViewById(R.id.real_review_comment_title_tv)
        var realReviewCommentDateTextView : TextView = itemView.findViewById(R.id.real_review_comment_date_tv)
        var realReviewCommentEditorProfileImageView : ImageView = itemView.findViewById(R.id.real_review_comment_editor_profile_iv)
        var realReviewCommentEditorNameTextView : TextView = itemView.findViewById(R.id.real_review_comment_editor_name_tv)
        var realReviewCommentEditorInformationTextView : TextView = itemView.findViewById(R.id.real_review_editor_info_tv)
        var realReviewCommentRecommendIconImageView : ImageView = itemView.findViewById(R.id.real_review_comment_recommend_icon_iv)
        var realReviewCommentGoodTextView : TextView = itemView.findViewById(R.id.real_review_comment_good_tv)
        var realReviewCommentBadTextView : TextView = itemView.findViewById(R.id.real_review_comment_bad_tv)
        var realReviewHeartTextView : TextView = itemView.findViewById(R.id.real_review_heart_tv)
        var realReviewCommentCommentTextView : TextView = itemView.findViewById(R.id.real_review_comment_comment_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RealReviewCommentHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.mypage_review_comment_item, parent, false)
        return MyPageReviewCommentAdapter.RealReviewCommentHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: RealReviewCommentHolder, position: Int) {
        var comment : DetailComment = comments.get(position)
        holder.realReviewCommentTitleTextView.setText(comment.showName)
        holder.realReviewCommentDateTextView.setText(comment.date)
        holder.realReviewCommentEditorProfileImageView.setImageResource(comment.imageResource)
        holder.realReviewCommentEditorNameTextView.setText(comment.editorName)
        holder.realReviewCommentEditorInformationTextView.setText(comment.info)
        if(comment.isGood){
            holder.realReviewCommentRecommendIconImageView.setImageResource(R.drawable.icon_good)
        }else{
            holder.realReviewCommentRecommendIconImageView.setImageResource(R.drawable.icon_bad)
        }
        holder.realReviewCommentGoodTextView.setText(comment.goodThing)
        holder.realReviewCommentBadTextView.setText(comment.badThing)
        holder.realReviewHeartTextView.setText(comment.heart.toString())
        holder.realReviewCommentCommentTextView.setText(comment.comment.toString())
    }
}