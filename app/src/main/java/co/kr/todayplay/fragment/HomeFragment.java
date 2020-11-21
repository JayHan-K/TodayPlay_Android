package co.kr.todayplay.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import co.kr.todayplay.ItemClickListener;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.HomeAdMidPagerAdapter;
import co.kr.todayplay.adapter.HomeAdPagerAdapter;
import co.kr.todayplay.adapter.HomeRankingViewPagerAdapter;
import co.kr.todayplay.adapter.HomeShowAdapter;
import co.kr.todayplay.adapter.JournalAdapter;
import co.kr.todayplay.adapter.RealReviewSearchSuggestionAdapter;
import co.kr.todayplay.object.Journal;
import co.kr.todayplay.object.Show;
import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {
    private ArrayList<Journal> journalList = new ArrayList<Journal>();
    private ArrayList<Show> PersonalizedShow = new ArrayList<Show>();
    ScrollView homeFragmentMainScrollView;
    FrameLayout homeFragmentChildFragment;
    public HomeFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.activity_home_fragment,container,false);

        homeFragmentChildFragment = viewGroup.findViewById(R.id.home_fragment_child_fragment);
        homeFragmentMainScrollView = viewGroup.findViewById(R.id.home_fragment_main_sv);

        ViewPager homeViewPager = (ViewPager) viewGroup.findViewById(R.id.home_main_ad_vp);
        homeViewPager.setAdapter(new HomeAdPagerAdapter(getChildFragmentManager()));

        final ViewPager homeViewmidPager = (ViewPager) viewGroup.findViewById(R.id.ad_mid);
        homeViewmidPager.setAdapter(new HomeAdMidPagerAdapter(getChildFragmentManager()));

        final ConstraintLayout hidden = (ConstraintLayout) viewGroup.findViewById(R.id.hidden);
        Button button = (Button) viewGroup.findViewById(R.id.button);
        button.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       if(hidden.getVisibility() == View.VISIBLE){
                           hidden.setVisibility(View.GONE);
                       }else if (hidden.getVisibility() == View.GONE){
                           hidden.setVisibility(View.VISIBLE);
                       }
                    }
                }));



        final CircleIndicator homeViewPagerIndicator = (CircleIndicator) viewGroup.findViewById(R.id.home_main_ad_ci);

        final ViewPager2 mpager = (ViewPager2)viewGroup.findViewById(R.id.rankingview);
        mpager.setAdapter(new HomeRankingViewPagerAdapter(this,4));
        mpager.setCurrentItem(1000);
        mpager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mpager.setOffscreenPageLimit(3);

        final float pageMargin = getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);

        mpager.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float myOffset = position * -(2 * pageOffset + pageMargin);
                if (mpager.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL){
                    if(ViewCompat.getLayoutDirection(mpager) == ViewCompat.LAYOUT_DIRECTION_RTL){
                        page.setTranslationX(-myOffset);
                    }else{
                        page.setTranslationX(myOffset);
                    }
                }else{
                    page.setTranslationY(myOffset);
                }
            }
        });

        homeViewPagerIndicator.setViewPager(homeViewPager);

//        homeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
        LinearLayoutManager journalLayoutManager =new LinearLayoutManager(this.getContext());
        journalLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager personalLayoutManger = new LinearLayoutManager(this.getContext());
        personalLayoutManger.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager homemidjournalLayoutManager = new LinearLayoutManager(this.getContext());
        homemidjournalLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        journalList=getJournals();
        PersonalizedShow = getPersonals();
        RecyclerView homeJournalRV = viewGroup.findViewById(R.id.home_journal_rv);

        ItemClickListener listener = (ItemClickListener)(new ItemClickListener(){
            @Override
            public void onItemClicked(@NotNull RecyclerView.ViewHolder vh, @NotNull Object item, int pos) {
//                homeChangeToJournalDetail();
            }

            @Override
            public void onItemClicked(
                    @NotNull RealReviewSearchSuggestionAdapter.ViewHolder v,
                    @NotNull Object item,
                    int pos
            ) {
            }

        });

        JournalAdapter journalAdapter = new JournalAdapter(journalList,getContext(),listener);

        homeJournalRV.setLayoutManager(journalLayoutManager);
        homeJournalRV.setAdapter(journalAdapter);

        RecyclerView homemidJournalRV = viewGroup.findViewById(R.id.homejournal_2);
        homemidJournalRV.setLayoutManager(homemidjournalLayoutManager);
        homemidJournalRV.setAdapter(journalAdapter);

        RecyclerView homePersonalRV = viewGroup.findViewById(R.id.personalizedShowRV);

        ItemClickListener mListener = (ItemClickListener)(new ItemClickListener(){
            @Override
            public void onItemClicked(@NotNull RecyclerView.ViewHolder vh, @NotNull Object item, int pos) {
                Show show =(Show)item;
//                homeChangeToShowDetail(show);
            }

            @Override
            public void onItemClicked(
                    @NotNull RealReviewSearchSuggestionAdapter.ViewHolder v,
                    @NotNull Object item,
                    int pos
            ) {
            }

        });

        HomeShowAdapter personalAdapter = new HomeShowAdapter(PersonalizedShow,getContext(),mListener);
        homePersonalRV.setLayoutManager(personalLayoutManger);
        homePersonalRV.setAdapter(personalAdapter);

        return viewGroup;
    }

    public ArrayList getJournals(){
        ArrayList journals = new ArrayList();
        journals.add(new Journal("식상한 무대는 그만!국내 이색 공연장 5선", R.drawable.journal_new));
        journals.add(
                new Journal(
                        "디큐브아트센터, 미로같은 그곳",
                        R.drawable.family
                )
        );
        journals.add(
                new Journal(
                        "이야기의 시작, 오이디푸스",
                        R.drawable.editors_sample2
                )
        );

        journals.add(
                new Journal(
                        "4대 뮤지컬 캣츠",
                        R.drawable.alone
                )
        );

        journals.add(
                new Journal(
                        "공연 좀 많이봤니?",
                        R.drawable.family
                )
        );

        journals.add(
                new Journal(
                        "집순이도 볼 수 있어",
                        R.drawable.editors_sample3
                )
        );

        journals.add(
                new Journal(
                        "해외가면 꼭 봐!",
                        R.drawable.editors_sample4
                )
        );

        journals.add(
                new Journal(
                        "공연 후기 & 꿀팁",
                        R.drawable.tip
                )
        );

        return journals;
    }

    public ArrayList getPersonals(){
        ArrayList shows = new ArrayList();
        shows.add(
                new Show(
                        R.drawable.poster_sample6,
                        "마리퀴리"
                )
        );

        shows.add(
                new Show(
                        R.drawable.poster_sample5,
                        "렌트"
                )
        );

        shows.add(
                new Show(
                        R.drawable.poster_sample4,
                        "레미제라블"
                )
        );

        shows.add(
                new Show(
                        R.drawable.poster_sample2,
                        "라스트 세션"
                )
        );
        shows.add(
                new Show(
                        R.drawable.poster_sample9,
                        "쉬어매드니스"
                )
        );
        shows.add(
                new Show(
                        R.drawable.poster_sample15,
                        "파우스트"
                )
        );
        shows.add(
                new Show(
                        R.drawable.poster_sample10,
                        "썸씽로튼"
                )
        );
        shows.add(
                new Show(
                        R.drawable.poster_sample12,
                        "제이미"
                )
        );

        return shows;
    }

    public void BackToHome(){
        homeFragmentMainScrollView.setVisibility(View.VISIBLE);
        homeFragmentChildFragment.setVisibility(View.INVISIBLE);
    }

//    public void homeChangeToJournalDetail(){
//        homeFragmentMainScrollView.setVisibility(View.INVISIBLE);
//        homeFragmentChildFragment.setVisibility(View.VISIBLE);
//
//        getChildFragmentManager().beginTransaction().replace(
//                R.id.home_fragment_child_fragment,
//                Journa
//        )
//
//
//    }
}







