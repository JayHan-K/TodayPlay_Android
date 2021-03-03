package co.kr.todayplay.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import co.kr.todayplay.DBHelper.PlayDB.PlayDBHelper
import co.kr.todayplay.ItemClickListener
import co.kr.todayplay.MainActivity
import co.kr.todayplay.R
import co.kr.todayplay.`object`.Ranking
import co.kr.todayplay.`object`.Show
import co.kr.todayplay.fragment.PerformInfoFragment
import org.w3c.dom.Text


class HomeRankingAdapter(ranking: ArrayList<Ranking>,rankpos:Int, context: Context,itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<HomeRankingAdapter.RankingHolder>() {
    var rankings : ArrayList<Ranking> = ranking
    var context : Context = context
    var itemClickListener = itemClickListener
    var playDBHelper = PlayDBHelper(context,"Play.db",null,1)
    var rankpos =rankpos


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.ranking_char, parent, false)
        return RankingHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: RankingHolder, position: Int) {
        val get_ranking: Ranking = rankings.get(rankpos+position)
        val layoutParams = holder.itemView.layoutParams
        val imgpath =context.filesDir.toString()+"/"+playDBHelper.getPlayPoster(get_ranking.play_id)
        val bm = BitmapFactory.decodeFile(imgpath)
        holder.ranking_tv.text = playDBHelper.getPlayTitle(get_ranking.play_id)
        setMarginsInDp(holder.ranking_iv,0,0,0,0)

        if(playDBHelper.getPlayPoster(get_ranking.play_id)!=""){
            holder.ranking_iv.setImageBitmap(bm);
        }
        holder.ranking_num.text = (position+1).toString()
        holder.itemView.setOnClickListener{
            itemClickListener.onItemClicked(holder,get_ranking,position)

            val performInfoFragment = PerformInfoFragment().apply {
                arguments = Bundle().apply {
                    putInt("play_id",get_ranking.play_id)
                }
            }
            (context as MainActivity).replaceFragment2(performInfoFragment)
        }
    }

    override fun getItemCount(): Int {
        return 3
    }


    class RankingHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var ranking_tv = itemView.findViewById<TextView>(R.id.ranking_tv_title);
        var ranking_iv = itemView.findViewById<ImageView>(R.id.ranking_iv);
        var ranking_num = itemView.findViewById<TextView>(R.id.ranking_num_tv);

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