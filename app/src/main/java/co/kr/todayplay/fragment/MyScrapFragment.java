package co.kr.todayplay.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.kr.todayplay.R;
import co.kr.todayplay.adapter.ProfileMyScrapDetailAdapter;
import co.kr.todayplay.adapter.pf_myPickPagerAdapter;
import co.kr.todayplay.object.RecommandItem;

public class MyScrapFragment extends Fragment {
    pf_myPickPagerAdapter adapter;
    RecyclerView pf_scrap_rv;

    public MyScrapFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.activity_myscrap,container,false);
        Button pf_to_profile = rootView.findViewById(R.id.scrap_to_profile_bt);
        pf_scrap_rv =(RecyclerView)rootView.findViewById(R.id.pf_scrap_rv);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(),2);
        pf_scrap_rv.setLayoutManager(gridLayoutManager);

        ArrayList data_scrap;
        data_scrap = getScraps();
        ProfileMyScrapDetailAdapter profileMyScrapDetailAdapter = new ProfileMyScrapDetailAdapter(data_scrap);

        pf_scrap_rv.setAdapter(profileMyScrapDetailAdapter);

        pf_to_profile.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ProfileFragment parentFrag = (ProfileFragment) MyScrapFragment.this.getParentFragment() ;
                parentFrag.BackToHome();

            }
        }));

        return rootView;
    }

    public ArrayList getScraps(){
        ArrayList<RecommandItem> data_recommand = new ArrayList();
        data_recommand.add(new RecommandItem(R.drawable.poster_sample1,"오이디푸스I"));
        data_recommand.add(new RecommandItem(R.drawable.poster_sample10,"오이디푸스I"));
        data_recommand.add(new RecommandItem(R.drawable.poster_sample11,"오이디푸스I"));
        data_recommand.add(new RecommandItem(R.drawable.poster_sample12,"오이디푸스I"));
        data_recommand.add(new RecommandItem(R.drawable.poster_sample13,"오이디푸스I"));
        data_recommand.add(new RecommandItem(R.drawable.poster_sample14,"오이디푸스I"));
        return data_recommand;
    }

}
