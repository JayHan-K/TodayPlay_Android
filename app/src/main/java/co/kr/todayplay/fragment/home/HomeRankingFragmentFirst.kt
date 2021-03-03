package co.kr.todayplay.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.kr.todayplay.DBHelper.PlayDB.PlayDBHelper
import co.kr.todayplay.ItemClickListener
import co.kr.todayplay.R
import co.kr.todayplay.RecyclerDecoration
import co.kr.todayplay.`object`.Journal
import co.kr.todayplay.`object`.Ranking
import co.kr.todayplay.`object`.Show
import co.kr.todayplay.adapter.HomeRankingAdapter
import co.kr.todayplay.adapter.RealReviewSearchSuggestionAdapter
import co.kr.todayplay.fragment.HomeFragment

class HomeRankingFragmentFirst(rankings: java.util.ArrayList<Ranking>,postion: Int) : Fragment(){

    var rankingList : ArrayList<Journal>? = null;
    var ranking :ArrayList<Ranking> = rankings
    val spaceDecoration:RecyclerDecoration = RecyclerDecoration(30,1)
    val position1:Int = postion;



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewGroup: ViewGroup =
            inflater.inflate(R.layout.activity_home_ranking_fragment_1p, null) as ViewGroup
//        var ranking2:Ranking = ranking.get(position1*3);
//        var rankpos:Int = position1*3;
//
//        var text =viewGroup.findViewById<TextView>(R.id.textView6)
//        text.setText(ranking2.category)

        val mListener : ItemClickListener = object : ItemClickListener{
            override fun onItemClicked(vh: RecyclerView.ViewHolder, item: Any, pos: Int) {
                var parentFrag: HomeFragment =
                    this@HomeRankingFragmentFirst.parentFragment as HomeFragment
                var show :Ranking = item as Ranking
//                parentFrag.homeChangeToShowDetail(show)
            }

            override fun onItemClicked(
                    v: RealReviewSearchSuggestionAdapter.ViewHolder,
                    item: Any,
                    pos: Int
            ) {
            }
        }

//        var homeRankingRecyclerView : RecyclerView = viewGroup.findViewById(R.id.home_ranking_rv) as RecyclerView
//        homeRankingRecyclerView.layoutManager = LinearLayoutManager(context)
//        homeRankingRecyclerView.adapter = HomeRankingAdapter(ranking,rankpos,requireContext(),mListener)
//        homeRankingRecyclerView.addItemDecoration(spaceDecoration)



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