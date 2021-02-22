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
import co.kr.todayplay.MainActivity
import co.kr.todayplay.R
import co.kr.todayplay.`object`.Show
import co.kr.todayplay.fragment.PerformInfoFragment


class ProfileHomeShowAdapter(homeShows: ArrayList<Show>, context: Context, itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<ProfileHomeShowAdapter.HomeShowHolder>() {
    var homeShows: ArrayList<Show> = homeShows
    var context: Context = context
    var itemClickListener = itemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeShowHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.profile_home_show_list_item, parent, false)
        return HomeShowHolder(inflatedView)
    }
    override fun onBindViewHolder(holder: HomeShowHolder, position: Int) {
        val homeShow : Show = homeShows.get(position)
        holder.pf_homeShowIV.setBackgroundResource(homeShow.imageSource)
        holder.pf_homeShow_TV.text = homeShow.showName
        setMarginsInDp(holder.pf_homeShowIV, 0, 0, 40, 0)
        holder.pf_homeShow_num.text = (position+1).toString()
        holder.pf_homeShowIV.setOnClickListener(View.OnClickListener {
            itemClickListener.onItemClicked(holder, homeShow, position)
            val performInfoFragment = PerformInfoFragment()
            (context as MainActivity).replaceFragment(performInfoFragment)
        })

    }

    override fun getItemCount(): Int {
        return homeShows.size
    }
    class HomeShowHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pf_homeShowIV = itemView.findViewById<ImageView>(R.id.home_show_iv)
        val pf_homeShow_TV = itemView.findViewById<TextView>(R.id.pf_home_show_tv_title)
        val pf_homeShow_num = itemView.findViewById<TextView>(R.id.pf_home_show_tv_num)
    }

    fun setMarginsInDp(view: View, left: Int, top: Int, right: Int, bottom: Int){
        if(view.layoutParams is ViewGroup.MarginLayoutParams){
            val screenDensity : Float = view.context.resources.displayMetrics.density
            val params: ViewGroup.MarginLayoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(left * screenDensity.toInt(), top * screenDensity.toInt(), right * screenDensity.toInt(), bottom * screenDensity.toInt())
            view.requestLayout()
        }
    }




}