package co.kr.todayplay.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import co.kr.todayplay.ItemClickListener;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.JournalAdapter;
import co.kr.todayplay.adapter.RealReviewSearchSuggestionAdapter;
import co.kr.todayplay.adapter.SearchDetailAdapter;
import co.kr.todayplay.object.Data;
import co.kr.todayplay.object.Journal;

public class SearchDetailFragment extends Fragment {
    private ArrayList<Journal> journalList = new ArrayList<Journal>();
    private ArrayList dataList;



    @Override
    public void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState);}
    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_search_detail, container, false);
        RecyclerView  seachresultrv = (RecyclerView)viewGroup.findViewById(R.id.search_result_rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(),3);
        LinearLayoutManager journalLayoutManager =new LinearLayoutManager(this.getContext());
        journalLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        journalList=getJournals();
        dataList = getresults();

        SearchDetailAdapter searchDetailAdapter = new SearchDetailAdapter(dataList, new SearchDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                System.out.println(position);
            }
        });

        seachresultrv.setLayoutManager(gridLayoutManager);
        seachresultrv.setAdapter(searchDetailAdapter);




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


        RecyclerView recyclerView2 = viewGroup.findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(journalLayoutManager);
        recyclerView2.setAdapter(journalAdapter);




        return viewGroup;
    }

    public ArrayList getresults(){
        ArrayList DataList = new ArrayList();
        DataList.add(new Data(R.drawable.poster_sample1));
        DataList.add(new Data(R.drawable.poster_sample2));
        DataList.add(new Data(R.drawable.poster_sample3));
        DataList.add(new Data(R.drawable.poster_sample4));
        DataList.add(new Data(R.drawable.poster_sample5));
        DataList.add(new Data(R.drawable.poster_sample6));
        DataList.add(new Data(R.drawable.poster_sample7));
        DataList.add(new Data(R.drawable.poster_sample8));
        DataList.add(new Data(R.drawable.poster_sample9));
        DataList.add(new Data(R.drawable.poster_sample10));
        DataList.add(new Data(R.drawable.poster_sample11));
        DataList.add(new Data(R.drawable.poster_sample12));
        DataList.add(new Data(R.drawable.poster_sample13));
        return DataList;
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

}
