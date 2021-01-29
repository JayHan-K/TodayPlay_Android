package co.kr.todayplay.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;

import co.kr.todayplay.R;
import co.kr.todayplay.adapter.KeywordRecyAdapter;
import co.kr.todayplay.object.totalItem;

public class MyKeywordFragment extends Fragment  {
    RecyclerView keyword_rv2;
    KeywordRecyAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState);}

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_mykeyword, container, false);
        keyword_rv2 = (RecyclerView)viewGroup.findViewById(R.id.keyword_rv2);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext());
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        Button back_fav =(Button)viewGroup.findViewById(R.id.back_fav);

        back_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile_Fav_AnalyzeFragment parentFrag = (Profile_Fav_AnalyzeFragment) MyKeywordFragment.this.getParentFragment();
                parentFrag.BackToHome();
            }
        });

        ArrayList data_keyword;
        data_keyword = getkeyword();

        keyword_rv2.setLayoutManager(flexboxLayoutManager);
//        adapter = new KeywordRecyAdapter(data_keyword);
        adapter = new KeywordRecyAdapter(data_keyword,new KeywordRecyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.delItem(position);
            }
        });

        keyword_rv2.setAdapter(adapter);
        return viewGroup;
    }

    public ArrayList<totalItem> getkeyword(){
        ArrayList<totalItem> data_keyword = new ArrayList<>();
        data_keyword.add(new totalItem("세계4대뮤지컬"));
        data_keyword.add(new totalItem("로맨스"));
        data_keyword.add(new totalItem("드라마"));
        data_keyword.add(new totalItem("가면"));
        data_keyword.add(new totalItem("세계4대뮤지컬"));
        data_keyword.add(new totalItem("로맨스"));
        data_keyword.add(new totalItem("드라마"));
        return data_keyword;
    }



}

