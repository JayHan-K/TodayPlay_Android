package co.kr.todayplay

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import co.kr.todayplay.adapter.RealReviewSearchSuggestionAdapter
import java.util.*

interface ItemClickListener {
    fun onItemClicked(vh :RecyclerView.ViewHolder, item : Any, pos: Int)
    fun onItemClicked(v : RealReviewSearchSuggestionAdapter.ViewHolder, item : Any, pos: Int)
}