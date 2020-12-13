package co.kr.todayplay.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import co.kr.todayplay.ItemClickListener;
import co.kr.todayplay.MyQnAActivity;
import co.kr.todayplay.MyReviewActivity;
import co.kr.todayplay.MyScrapActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.ProfileHomeShowAdapter;
import co.kr.todayplay.adapter.RealReviewSearchSuggestionAdapter;
import co.kr.todayplay.object.Show;

public class ProfileFragment extends Fragment {
    private ArrayList<Show> PersonalizedShow = new ArrayList<Show>();
    public ProfileFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.activity_profile_fragment,container,false);

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

        RelativeLayout profileMyReviewRelativeLayout = viewGroup.findViewById(R.id.profile_my_review_rl);
        profileMyReviewRelativeLayout.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getContext(), MyReviewActivity.class);
                startActivity(intent);
            }

        }));

        RelativeLayout profileScrapRelativeLayout = viewGroup.findViewById(R.id.profile_scrap_rl);
        profileScrapRelativeLayout.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getContext(), MyScrapActivity.class);
                startActivity(intent);
            }

        }));

        RelativeLayout profileMyQnARelativeLayout = viewGroup.findViewById(R.id.profile_qna_rl);
        profileMyQnARelativeLayout.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getContext(), MyQnAActivity.class);
                startActivity(intent);
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

}
