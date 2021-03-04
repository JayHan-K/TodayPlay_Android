package co.kr.todayplay.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Objects;

import co.kr.todayplay.DBHelper.PlayDB.PlayDBHelper;
import co.kr.todayplay.Intro_Activity;
import co.kr.todayplay.ItemClickListener;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.HomeAdMidPagerAdapter;
import co.kr.todayplay.adapter.HomeAdPagerAdapter;
import co.kr.todayplay.adapter.HomeRankingViewPagerAdapter;
import co.kr.todayplay.adapter.HomeShowAdapter;
import co.kr.todayplay.adapter.JournalAdapter;
import co.kr.todayplay.adapter.JournalAdapter2;
import co.kr.todayplay.adapter.RealReviewSearchSuggestionAdapter;
import co.kr.todayplay.fragment.Journal.JournalDetailFragment;
import co.kr.todayplay.object.Banner;
import co.kr.todayplay.object.History;
import co.kr.todayplay.object.Journal;
import co.kr.todayplay.object.Line;
import co.kr.todayplay.object.Ranking;
import co.kr.todayplay.object.Recommend;
import co.kr.todayplay.object.Show;
import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {
    ScrollView homeFragmentMainScrollView;
    FrameLayout homeFragmentChildFragment;
    PlayDBHelper playDBHelper;
    ImageView imageView12;
    ImageView imageView3;

    ArrayList<Banner> banner_chosen;
    ArrayList<Recommend> recommend_chosen;
    ArrayList<Recommend> journal_chosen;
    ArrayList<Ranking> ranking_chosen;
    ArrayList<Line> line_chosen;
    ArrayList<History> history_get;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.activity_home_fragment,container,false);
        banner_chosen = (ArrayList<Banner>)getArguments().get("banners");
        recommend_chosen = (ArrayList<Recommend>)getArguments().get("recommands");
        journal_chosen = (ArrayList<Recommend>)getArguments().get("recommandj");
        ranking_chosen = (ArrayList<Ranking>)getArguments().get("rankings");
        line_chosen = (ArrayList<Line>)getArguments().get("line");

        playDBHelper = new PlayDBHelper(this.getContext(), "Play.db", null, 1);
        history_get = new ArrayList<History>();
        homeFragmentChildFragment = viewGroup.findViewById(R.id.home_fragment_child_fragment);
        homeFragmentMainScrollView = viewGroup.findViewById(R.id.home_fragment_main_sv);
        imageView12 = viewGroup.findViewById(R.id.imageView12);


        //한줄관련부분
        if(line_chosen.size()>0){
            Line line = line_chosen.get(0);
            String data = line.getLine();
            int play_id = line.getPlay_id();
            History history_chosen;

            Log.d("line_id,line_string","line"+data+" ,"+play_id);
            String history = playDBHelper.getPlayHistory(play_id);
            Log.d("history","history"+history);
            try {
                JSONArray history_jsonArray = new JSONArray(history);
                JSONObject line_id_object = (JSONObject) history_jsonArray.get(0);
                Log.d("historyObject", "Object "  + ": " + line_id_object.toString());
                history_chosen = new History((String)line_id_object.get("poster_img"),(String)line_id_object.get("play_date"),(String)line_id_object.get("play_product_company"),(String)line_id_object.get("play_director"),(String)line_id_object.get("play_crew"));
                Log.d("historyimg", "Object "  + ": " + history_chosen.getPoster_img());
                Log.d("historyObject", "Object "  + ": " + history_chosen.getPlay_crew());
                history_get.add(history_chosen);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            history_chosen = history_get.get(0);

            String imgpath =  getContext().getFilesDir().toString() + "/" + data;
            Bitmap bm = BitmapFactory.decodeFile(imgpath);
            if(bm!=null){
                imageView12.setImageBitmap(bm);
            }


            imgpath = getContext().getFilesDir().toString()+"/"+history_chosen.getPoster_img();
            bm = BitmapFactory.decodeFile(imgpath);
            imageView3 = viewGroup.findViewById(R.id.imageView3);
            imageView3.setImageBitmap(bm);

            TextView textView7 = viewGroup.findViewById(R.id.textView7);
            String categoryname = playDBHelper.getPlayCategory(play_id);
            textView7.setText(categoryname);

            TextView textview8 = viewGroup.findViewById(R.id.textView8);

            textview8.setText(playDBHelper.getPlayTitle(play_id));

            TextView textview11 = viewGroup.findViewById(R.id.textView11);
            String nameinfo =history_chosen.getPlay_directer()+"("+history_chosen.getPlay_product_company()+")";
            textview11.setText(nameinfo);

            TextView textview14 = viewGroup.findViewById(R.id.textView14);
            textview14.setText(history_chosen.getPlay_crew());

            TextView textView15 = viewGroup.findViewById(R.id.textView15);
            textView15.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PerformInfoFragment performInfoFragment = new PerformInfoFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("play_id",play_id);
                    performInfoFragment.setArguments(bundle);
                    ((MainActivity)getActivity()).replaceFragment2(performInfoFragment);
                }
            });
        }


        if(banner_chosen.size()>0){
            ViewPager homeViewPager = (ViewPager) viewGroup.findViewById(R.id.home_main_ad_vp);
            homeViewPager.setAdapter(new HomeAdPagerAdapter(getChildFragmentManager(),banner_chosen));
            final CircleIndicator homeViewPagerIndicator = (CircleIndicator) viewGroup.findViewById(R.id.home_main_ad_ci);
            homeViewPagerIndicator.setViewPager(homeViewPager);
        }


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





        final ViewPager2 mpager = (ViewPager2)viewGroup.findViewById(R.id.rankingview);
        mpager.setAdapter(new HomeRankingViewPagerAdapter(this,2,ranking_chosen));
        mpager.setCurrentItem(0);
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



        LinearLayoutManager journalLayoutManager =new LinearLayoutManager(this.getContext());
        journalLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager personalLayoutManger = new LinearLayoutManager(this.getContext());
        personalLayoutManger.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager homemidjournalLayoutManager = new LinearLayoutManager(this.getContext());
        homemidjournalLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        RecyclerView homeJournalRV = viewGroup.findViewById(R.id.home_journal_rv);

        ItemClickListener listener = (ItemClickListener)(new ItemClickListener(){
            @Override
            public void onItemClicked(@NotNull RecyclerView.ViewHolder vh, @NotNull Object item, int pos) {
//
            }

            @Override
            public void onItemClicked(
                    @NotNull RealReviewSearchSuggestionAdapter.ViewHolder v,
                    @NotNull Object item,
                    int pos
            ) {
            }

        });

        if(journal_chosen.size()>0){
            JournalAdapter2 journalAdapter = new JournalAdapter2(journal_chosen, requireContext(),listener);

            homeJournalRV.setLayoutManager(journalLayoutManager);
            homeJournalRV.setAdapter(journalAdapter);

            RecyclerView homemidJournalRV = viewGroup.findViewById(R.id.homejournal_2);
            homemidJournalRV.setLayoutManager(homemidjournalLayoutManager);
            homemidJournalRV.setAdapter(journalAdapter);
        }


        RecyclerView homePersonalRV = viewGroup.findViewById(R.id.personalizedShowRV);

        ItemClickListener mListener = (ItemClickListener)(new ItemClickListener(){
            @Override
            public void onItemClicked(@NotNull RecyclerView.ViewHolder vh, @NotNull Object item, int pos) {
            }

            @Override
            public void onItemClicked(
                    @NotNull RealReviewSearchSuggestionAdapter.ViewHolder v,
                    @NotNull Object item,
                    int pos
            ) {
            }

        });

        if(recommend_chosen.size()>0){
            HomeShowAdapter personalAdapter = new HomeShowAdapter(recommend_chosen,getContext(),mListener);
            homePersonalRV.setLayoutManager(personalLayoutManger);
            homePersonalRV.setAdapter(personalAdapter);
        }


        return viewGroup;
    }




}








