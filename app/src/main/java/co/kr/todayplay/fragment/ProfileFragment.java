package co.kr.todayplay.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import co.kr.todayplay.ItemClickListener;
import co.kr.todayplay.R;
import co.kr.todayplay.RecyclerDecoration;
import co.kr.todayplay.adapter.ProfileHomeShowAdapter;
import co.kr.todayplay.adapter.RealReviewSearchSuggestionAdapter;
import co.kr.todayplay.object.Show;

public class ProfileFragment extends Fragment {
    private ArrayList<Show> PersonalizedShow = new ArrayList<Show>();
    RecyclerDecoration spaceDecoration = new RecyclerDecoration(25,0);
    ScrollView profileFragmentmainScrollView;
    FrameLayout profileFragmentChildFragment;
    int user_id = -1;
    public ProfileFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.activity_profile_fragment,container,false);
        profileFragmentmainScrollView = viewGroup.findViewById(R.id.pf_scrollView);
        profileFragmentChildFragment = viewGroup.findViewById(R.id.pf_fragment_child_fragment);
        Bundle bundle = getArguments();
        if(bundle != null){
            user_id = bundle.getInt("user_id");
            Log.d("Bundle result", "user_id: " + user_id);
        }

        RecyclerView profileMyShowRecyclerView= viewGroup.findViewById(R.id.profile_my_show_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
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
        PersonalizedShow = getPersonals();
        ProfileHomeShowAdapter profileAdapter = new ProfileHomeShowAdapter(PersonalizedShow,getContext(),mListener);
        profileMyShowRecyclerView.setAdapter(profileAdapter);
        profileMyShowRecyclerView.setLayoutManager(layoutManager);
        profileMyShowRecyclerView.addItemDecoration(spaceDecoration);


        ConstraintLayout profileMyReviewRelativeLayout = viewGroup.findViewById(R.id.profile_my_review_rl);
        profileMyReviewRelativeLayout.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                profileChangeToReview();
            }

        }));

        Button profile_to_fav = viewGroup.findViewById(R.id.profile_to_fav);
        profile_to_fav.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                profileChangeToFav();
            }
        }));
        Button profile_to_info = viewGroup.findViewById(R.id.button11);
        profile_to_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileChangeToinfo();
            }
        });

        ConstraintLayout profileScrapRelativeLayout = viewGroup.findViewById(R.id.profile_scrap_rl);
        profileScrapRelativeLayout.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                profileChangeToPick();
            }

        }));

        ConstraintLayout profileMyQnARelativeLayout = viewGroup.findViewById(R.id.profile_qna_rl);
        profileMyQnARelativeLayout.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
               profileChangeToScrap();
            }

        }));

        ImageView pf_setting = viewGroup.findViewById(R.id.pf_setting);
        pf_setting.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                profileChangeToSettings();
            }
        }));




        return viewGroup;
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

    public void profileChangeToReview(){
        Bundle bundle = new Bundle();
        MyReviewActivity myReviewActivity = new MyReviewActivity();
        bundle.putSerializable("user_id", user_id);
        myReviewActivity.setArguments(bundle);

        getChildFragmentManager().beginTransaction().replace(
                R.id.pf_fragment_child_fragment,
                myReviewActivity
        ).commitAllowingStateLoss();
        profileFragmentmainScrollView.setVisibility(View.GONE);
        profileFragmentChildFragment.setVisibility(View.VISIBLE);

    }

    public void profileChangeToPick(){
        Bundle bundle = new Bundle();
        MypickFragment mypickFragment = new MypickFragment();
        bundle.putSerializable("user_id", user_id);
        mypickFragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction().replace(
                R.id.pf_fragment_child_fragment,
                mypickFragment
        ).commitAllowingStateLoss();
        profileFragmentmainScrollView.setVisibility(View.GONE);
        profileFragmentChildFragment.setVisibility(View.VISIBLE);

    }

    public void profileChangeToScrap(){
        Bundle bundle = new Bundle();
        MyScrapFragment myScrapFragment = new MyScrapFragment();
        bundle.putSerializable("user_id", user_id);
        myScrapFragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction().replace(
                R.id.pf_fragment_child_fragment,
                myScrapFragment
        ).commitAllowingStateLoss();
        profileFragmentmainScrollView.setVisibility(View.GONE);
        profileFragmentChildFragment.setVisibility(View.VISIBLE);


    }

    public void profileChangeToinfo(){

        getChildFragmentManager().beginTransaction().replace(
                R.id.pf_fragment_child_fragment,
                new pf_change_info_Activity()
        ).commitAllowingStateLoss();
        profileFragmentmainScrollView.setVisibility(View.GONE);
        profileFragmentChildFragment.setVisibility(View.VISIBLE);

    }

    public void profileChangeToFav(){
        getChildFragmentManager().beginTransaction().replace(
                R.id.pf_fragment_child_fragment,
                new Profile_Fav_AnalyzeFragment()
        ).commitAllowingStateLoss();
        profileFragmentmainScrollView.setVisibility(View.GONE);
        profileFragmentChildFragment.setVisibility(View.VISIBLE);

    }

    public void profileChangeToSettings(){
        getChildFragmentManager().beginTransaction().replace(
                R.id.pf_fragment_child_fragment,
                new SettingsFragment()
        ).commitAllowingStateLoss();
        profileFragmentmainScrollView.setVisibility(View.GONE);
        profileFragmentChildFragment.setVisibility(View.VISIBLE);
    }

    public void BackToHome(){
        profileFragmentChildFragment.setVisibility(View.GONE);
        profileFragmentmainScrollView.setVisibility(View.VISIBLE);
    }

}
