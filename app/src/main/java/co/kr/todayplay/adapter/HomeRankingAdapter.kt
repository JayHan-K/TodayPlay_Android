package co.kr.todayplay.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import co.kr.todayplay.ItemClickListener
import co.kr.todayplay.R
import co.kr.todayplay.`object`.Show


class HomeRankingAdapter(show1: ArrayList<Show>, context: Context,itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<HomeRankingAdapter.RankingHolder>() {
    var shows : ArrayList<Show> = show1
    var context : Context = context
    var itemClickListener = itemClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.ranking_char, parent, false)
        return RankingHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: RankingHolder, position: Int) {
        val show: Show = shows.get(position)
        holder.ranking_tv.text = show.showName
        setMarginsInDp(holder.ranking_iv,0,0,0,13)
        holder.ranking_iv.setImageResource(show.imageSource)
        holder.itemView.setOnClickListener{
            itemClickListener.onItemClicked(holder,show,position)
        }
    }

    override fun getItemCount(): Int {
        return shows.size
    }


    class RankingHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var ranking_tv = itemView?.findViewById<TextView>(R.id.ranking_tv_title);
        var ranking_iv = itemView?.findViewById<ImageView>(R.id.ranking_iv);

    }

    fun setMarginsInDp(view: View, left: Int, top: Int, right: Int, bottom: Int){
        if(view.layoutParams is ViewGroup.MarginLayoutParams){
            val screenDensity : Float = view.context.resources.displayMetrics.density
            val params: ViewGroup.MarginLayoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(left*screenDensity.toInt(), top*screenDensity.toInt(), right*screenDensity.toInt(), bottom*screenDensity.toInt())
            view.requestLayout()
        }
    }
}