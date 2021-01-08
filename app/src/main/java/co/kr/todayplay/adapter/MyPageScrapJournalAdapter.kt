package co.kr.todayplay.adapter



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import co.kr.todayplay.R
import co.kr.todayplay.`object`.Journal

import com.makeramen.roundedimageview.RoundedImageView

class MyPageScrapJournalAdapter (journals: ArrayList<Journal>, context: Context) :
    RecyclerView.Adapter<MyPageScrapJournalAdapter.CommunityHotIssueJournalHolder>() {

    var journals = journals
    var context = context
    

    class CommunityHotIssueJournalHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var communityHotIssueCardView : CardView = itemView.findViewById(R.id.community_hot_issue_cv)
        var communityHotIssueCardViewImageView : RoundedImageView = itemView.findViewById(R.id.community_hot_issue_cv_iv)
        var communityHotIssueCardViewIndexTextView : TextView = itemView.findViewById(R.id.community_hot_issue_cv_index_tv)
        var communityHotIssueCardViewTitleTextView : TextView = itemView.findViewById(R.id.community_hot_issue_cv_title_tv)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommunityHotIssueJournalHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.journal_hot_issue_card_view, parent, false)
        return MyPageScrapJournalAdapter.CommunityHotIssueJournalHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return journals.size
    }

    override fun onBindViewHolder(holder: CommunityHotIssueJournalHolder, position: Int) {

        val journal: Journal = journals.get(position)
        holder.communityHotIssueCardViewImageView.setImageResource(journal.getImageResource())
        holder.communityHotIssueCardViewIndexTextView.setText("0" + (position+1).toString())
        holder.communityHotIssueCardViewTitleTextView.setText(journal.getJournalStr())
    }
}