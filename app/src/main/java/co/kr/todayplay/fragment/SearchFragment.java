package co.kr.todayplay.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;

import co.kr.todayplay.DBHelper.PlayDB.PlayDBHelper;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.KeywordRecyAdapter;
import co.kr.todayplay.adapter.SearchSuggestAdapter;
import co.kr.todayplay.object.totalItem;

public class SearchFragment extends Fragment implements SearchSuggestAdapter.onItemListener {
    private SearchSuggestAdapter adapter;
    private List<String> itemList;
    private FrameLayout searchFrameLayout;
    private ConstraintLayout searchConst;
    private androidx.appcompat.widget.SearchView searchView;
    private List<String> popularDataList;
    public SearchFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup =(ViewGroup)inflater.inflate(R.layout.activity_search_fragment, container, false);

        final RecyclerView search_suggest_rv = viewGroup.findViewById(R.id.search_suggest_rv);
        search_suggest_rv.setHasFixedSize(true);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext());
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        searchFrameLayout = (FrameLayout)viewGroup.findViewById(R.id.serch_frame);
        searchConst = (ConstraintLayout)viewGroup.findViewById(R.id.search_list_cl);
        PlayDBHelper playDBHelper = new PlayDBHelper(getContext(),"Play.db",null,1);


        searchView = viewGroup.findViewById(R.id.search_bar);
        final TextView search_pop_rec_tv = (TextView)viewGroup.findViewById(R.id.search_pop_rec_tv);
        searchView.setIconifiedByDefault(false);
        final InputMethodManager imm =(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        search_suggest_rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                searchView.clearFocus();
                imm.hideSoftInputFromWindow(searchView.getWindowToken(),0);

                return false;
            }
        });

        itemList = new ArrayList<>();
        popularDataList = new ArrayList<>();
        itemList = playDBHelper.getAlltitle();
        popularDataList.add((String)itemList.get(124));
        popularDataList.add(itemList.get(27));
        popularDataList.add(itemList.get(145));
        popularDataList.add(itemList.get(266));
        popularDataList.add(itemList.get(346));
        popularDataList.add(itemList.get(417));
        popularDataList.add(itemList.get(448));
        popularDataList.add(itemList.get(371));
        Log.d("length", String.valueOf(itemList.size()));
//        fillData();
        adapter = new SearchSuggestAdapter(itemList,popularDataList);

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchConst.setVisibility(View.GONE);
                searchFrameLayout.setVisibility(View.VISIBLE);
                Log.d("query","query="+query);
                Bundle bundle = new Bundle();
                bundle.putString("title",query);
                SearchDetailFragment searchDetailFragment = new SearchDetailFragment();
                searchDetailFragment.setArguments(bundle);
                getChildFragmentManager().beginTransaction().replace(
                        R.id.serch_frame,
                        searchDetailFragment
                ).commitAllowingStateLoss();
                searchFrameLayout.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchFrameLayout.setVisibility(View.GONE);
                searchConst.setVisibility(View.VISIBLE);
                adapter.getFilter().filter(newText);
                if(newText.equals("")){
                    search_pop_rec_tv.setText("인기 검색어");
                }else {
                    search_pop_rec_tv.setText("추천 검색어");
                }
                return false;
            }
        });


        search_suggest_rv.setLayoutManager(flexboxLayoutManager);
        search_suggest_rv.setAdapter(adapter);
        adapter.setOnClickListener(this);
        return viewGroup;
    }
//    private void fillData(){
//        itemList = new ArrayList<>();
//        itemList.add("오페라의 유령");
//        itemList.add("캣츠");
//        itemList.add("로맨스");
//        itemList.add("공포");
//        itemList.add("브로드웨이 42번가");
//        itemList.add("돈 조반니");
//        itemList.add("렌트");
//        itemList.add("공포의 떡볶이");
//    }


    @Override
    public void onItemClicked(int position) {
        System.out.println(position);
       String str = adapter.ReturnFiltered(position);
       searchView.setQuery(str,true);
    }
}
