package co.kr.todayplay.fragment.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import co.kr.todayplay.ItemClickListener
import co.kr.todayplay.R
import co.kr.todayplay.`object`.Journal
import co.kr.todayplay.`object`.Show
import co.kr.todayplay.adapter.HomeRankingAdapter
import co.kr.todayplay.adapter.RealReviewSearchSuggestionAdapter
import co.kr.todayplay.fragment.HomeFragment

class HomeRankingFragmentFirst : Fragment(){

    var rankingList : ArrayList<Journal>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewGroup: ViewGroup =
            inflater.inflate(R.layout.activity_home_ranking_fragment_1p, null) as ViewGroup
        val mListener : ItemClickListener = object : ItemClickListener{
            override fun onItemClicked(vh: RecyclerView.ViewHolder, item: Any, pos: Int) {
                var parentFrag: HomeFragment =
                    this@HomeRankingFragmentFirst.parentFragment as HomeFragment
                var show :Show = item as Show
//                parentFrag.homeChangeToShowDetail(show)
            }

            override fun onItemClicked(
                    v: RealReviewSearchSuggestionAdapter.ViewHolder,
                    item: Any,
                    pos: Int
            ) {
            }
        }

        var homeRankingRecyclerView : RecyclerView = viewGroup.findViewById(R.id.home_ranking_rv) as RecyclerView
        homeRankingRecyclerView.layoutManager = LinearLayoutManager(context)
        homeRankingRecyclerView.adapter = HomeRankingAdapter(getPersonals(),context!!,mListener)



//        val journalLayoutManager : LinearLayoutManager = LinearLayoutManager(context)
//        journalLayoutManager.orientation = LinearLayoutManager.VERTICAL
//
//        rankingList = getJournals()
//        val homeJournalRV = viewGroup.findViewById<RecyclerView>(R.id.home_ranking_rv) as RecyclerView
//        val listener : ItemClickListener = object : ItemClickListener {
//            override fun onItemClicked(vh: RecyclerView.ViewHolder, item: Any, pos: Int) {
//                homeChangeToJournalDetail()
//            }
//
//            override fun onItemClicked(
//                v: RealReviewSearchSuggestionAdapter.ViewHolder,
//                item: Any,
//                pos: Int
//            ) {
//
//            }
//        }
//        val rankingAdapter = HomeRankingAdapter(rankingList!!, context!!)
//        homeJournalRV.layoutManager = journalLayoutManager
//        homeJournalRV.adapter = rankingAdapter

        return viewGroup
    }
    fun getPersonals() : ArrayList<Show>{
        val shows = ArrayList<Show>()
        shows.add(
            Show(
                R.drawable.poster_sample6,
                "마리퀴리"
            )
        )
        shows.add(
            Show(
                R.drawable.poster_sample5,
                "렌트"
            )
        )
        shows.add(
            Show(
                R.drawable.poster_sample4,
                "레미제라블"
            )
        )
        shows.add(
            Show(
                R.drawable.poster_sample2,
                "라스트 세션"
            )
        )
        shows.add(
            Show(
                R.drawable.poster_sample9,
                "쉬어매드니스"
            )
        )
        shows.add(
            Show(
                R.drawable.poster_sample15,
                "파우스트"
            )
        )
        shows.add(
            Show(
                R.drawable.poster_sample10,
                "썸씽로튼"
            )
        )
        shows.add(
            Show(
                R.drawable.poster_sample12,
                "제이미"
            )
        )
        return shows
    }




}