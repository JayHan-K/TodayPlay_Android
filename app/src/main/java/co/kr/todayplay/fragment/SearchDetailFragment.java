package co.kr.todayplay.fragment;

import android.os.Bundle;
import android.util.Log;
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
import java.util.Random;

import co.kr.todayplay.DBHelper.JournalDB.JournalDBHelper;
import co.kr.todayplay.DBHelper.PlayDB.PlayDBHelper;
import co.kr.todayplay.ItemClickListener;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.JournalAdapter;
import co.kr.todayplay.adapter.JournalAdapter2;
import co.kr.todayplay.adapter.RealReviewSearchSuggestionAdapter;
import co.kr.todayplay.adapter.SearchDetailAdapter;
import co.kr.todayplay.object.CategoryRe;
import co.kr.todayplay.object.Data;
import co.kr.todayplay.object.Journal;
import co.kr.todayplay.object.Recommend;
import co.kr.todayplay.object.dbsearch1;

public class SearchDetailFragment extends Fragment {
    private ArrayList<Journal> journalList = new ArrayList<Journal>();
    private ArrayList dataList;
    private ArrayList<dbsearch1> dbsearch1s;
    private ArrayList<Integer> search_play_id;
    private ArrayList<CategoryRe> midkeyword;
    private ArrayList<Recommend> jouranl_id;
    private ArrayList<Recommend> di;



    @Override
    public void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState);}
    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_search_detail, container, false);
        RecyclerView  seachresultrv = (RecyclerView)viewGroup.findViewById(R.id.search_result_rv);
        PlayDBHelper playDBHelper = new PlayDBHelper(getContext(),"Play.db",null,1);
        JournalDBHelper journalDBHelper = new JournalDBHelper(getContext(),"Journal.db",null,1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(),3);
        LinearLayoutManager journalLayoutManager =new LinearLayoutManager(this.getContext());
        journalLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        search_play_id = new ArrayList<>();
        String title = getArguments().getString("title");
        Log.d("title","title="+title);
        dbsearch1s = playDBHelper.getmidsearch(title);
        if(dbsearch1s.size()==0){
            return viewGroup;
        }
        dbsearch1 fe = dbsearch1s.get(0);
        Log.d("dbsearch1s","getit="+fe.getImg_path()+"  "+fe.getKeyword());
        search_play_id.add(fe.getPlay_id());
        String[] fekey = fe.getKeyword().split(",");
        midkeyword = playDBHelper.getkeywordplay_id(fekey[5]);
        if(midkeyword.size()>7){
            for(int i =0; i<8;++i){
                search_play_id.add(midkeyword.get(i).getPlay_id());
            }
        }else if(midkeyword.size()<7){
            for(int i =0; i<midkeyword.size();++i){
                search_play_id.add(midkeyword.get(i).getPlay_id());
            }
        }

        jouranl_id = journalDBHelper.getalljournal_id();
        Log.d("journal","journal="+jouranl_id.size());
        Random r = new Random();
        di = new ArrayList<>();
        for(int i =0;i<3;++i){
            di.add(jouranl_id.get(r.nextInt(17)));
        }




        journalList=getJournals();
        dataList = getresults();

        SearchDetailAdapter searchDetailAdapter = new SearchDetailAdapter(search_play_id, new SearchDetailAdapter.OnItemClickListener() {
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

//        JournalAdapter journalAdapter = new JournalAdapter(journalList,getContext(),listener);
        JournalAdapter2 journalAdapter = new JournalAdapter2(di,getContext(),listener);

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
