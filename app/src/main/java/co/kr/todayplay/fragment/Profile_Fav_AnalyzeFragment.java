package co.kr.todayplay.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.BitSet;

import co.kr.todayplay.R;
import co.kr.todayplay.adapter.ProfileFavAdapter;
import co.kr.todayplay.adapter.ProfileFavKeywordAdapter;
import co.kr.todayplay.object.RecommandItem;
import co.kr.todayplay.object.totalItem;


public class Profile_Fav_AnalyzeFragment extends Fragment {
    RecyclerView playing_rv;
    RecyclerView playing_yet_rv;
    RecyclerView not_yet_rv;
    RecyclerView keyword_rv;
    FrameLayout favFragmentchildFragment;
    ConstraintLayout fav_const;
    public Profile_Fav_AnalyzeFragment(){}
    Button button2;

   @Override
    public void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState);}

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
       ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.profile_fav_analyze,container,false);
       Button fav_to_profile = (Button)viewGroup.findViewById(R.id.back_profile5);
       playing_rv = (RecyclerView)viewGroup.findViewById(R.id.playing_rv);
       playing_yet_rv = (RecyclerView)viewGroup.findViewById(R.id.play_yet_rv);
       not_yet_rv = (RecyclerView)viewGroup.findViewById(R.id.not_yet_rv);
       keyword_rv = (RecyclerView)viewGroup.findViewById(R.id.keyword_rv);
       favFragmentchildFragment = viewGroup.findViewById(R.id.fav_fragment_child_fragment);
       fav_const = viewGroup.findViewById(R.id.fav_const);
       button2 = (Button)viewGroup.findViewById(R.id.button2);

       LinearLayoutManager recommandLayoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
       LinearLayoutManager recommandLayoutManager2 = new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
       LinearLayoutManager recommandLayoutManager3 = new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
       GridLayoutManager gridLayoutManager =  new GridLayoutManager(this.getContext(),3,GridLayoutManager.HORIZONTAL,false);
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL);

       playing_rv.setLayoutManager(recommandLayoutManager);
       playing_yet_rv.setLayoutManager(recommandLayoutManager2);
       not_yet_rv.setLayoutManager(recommandLayoutManager3);
       keyword_rv.setLayoutManager(gridLayoutManager);

       ArrayList data_recommand;
       data_recommand = getRecommands();
       ArrayList data_keyword;
       data_keyword = getKeywords();
       ProfileFavAdapter profileFavAdapter = new ProfileFavAdapter(data_recommand);
       ProfileFavKeywordAdapter profileFavKeywordAdapter = new ProfileFavKeywordAdapter(data_keyword);

       playing_rv.setAdapter(profileFavAdapter);
       playing_yet_rv.setAdapter(profileFavAdapter);
       not_yet_rv.setAdapter(profileFavAdapter);
       keyword_rv.setAdapter(profileFavKeywordAdapter);

       fav_to_profile.setOnClickListener((new View.OnClickListener(){
           @Override
           public void onClick(View view){
               ProfileFragment parentFrag = (ProfileFragment) Profile_Fav_AnalyzeFragment.this.getParentFragment() ;
               parentFrag.BackToHome();
           }
       }));
       button2.setOnClickListener((new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               favChangeToKeyword();
           }
       }));

       return viewGroup;
    }

    public ArrayList getRecommands(){
        ArrayList<RecommandItem> data_recommand = new ArrayList();
        data_recommand.add(new RecommandItem(R.drawable.poster_sample1,"82%"));
        data_recommand.add(new RecommandItem(R.drawable.poster_sample10,"82%"));
        data_recommand.add(new RecommandItem(R.drawable.poster_sample11,"82%"));
        data_recommand.add(new RecommandItem(R.drawable.poster_sample12,"82%"));
        data_recommand.add(new RecommandItem(R.drawable.poster_sample13,"82%"));
        data_recommand.add(new RecommandItem(R.drawable.poster_sample14,"82%"));
        return data_recommand;
    }
    public ArrayList getKeywords(){
        ArrayList<totalItem> data_keyword = new ArrayList();
        data_keyword.add(new totalItem("배우의 연기력이 돋보이는"));
        data_keyword.add(new totalItem("유명한 원작"));
        data_keyword.add(new totalItem("마음이 따뜻해지는"));
        data_keyword.add(new totalItem("배우의 연기력이 돋보이는"));
        data_keyword.add(new totalItem("유명한 원작"));
        data_keyword.add(new totalItem("마음이 따뜻해지는"));
        return data_keyword;
    }

    public void favChangeToKeyword(){
        getChildFragmentManager().beginTransaction().replace(
                R.id.fav_fragment_child_fragment,
                new MyKeywordFragment()
        ).commitAllowingStateLoss();
        fav_const.setVisibility(View.GONE);
        favFragmentchildFragment.setVisibility(View.VISIBLE);
    }

    public void BackToHome(){
        favFragmentchildFragment.setVisibility(View.GONE);
        fav_const.setVisibility(View.VISIBLE);
    }



}
