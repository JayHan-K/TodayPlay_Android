package co.kr.todayplay.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.kr.todayplay.DBHelper.PlayDB.PlayDBHelper;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.AdapterSpinner2;
import co.kr.todayplay.adapter.CategoryDetailRecyAdapter;
import co.kr.todayplay.adapter.PlayCoverflowAdapter;
import co.kr.todayplay.object.CategoryRe;
import co.kr.todayplay.object.Data;
import co.kr.todayplay.object.PlayModel;
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

public class CategoryClickedFragment extends Fragment {

    private FeatureCoverFlow mCoverFlow;
    private PlayCoverflowAdapter mAdapter;
    private ArrayList<PlayModel> mData = new ArrayList<PlayModel>(0);
    Button login_go_back6;
    private CategoryDetailRecyAdapter categoryDetailRecyAdapter;
    private Spinner spinner2;
    ArrayList<String> arrayList;
    AdapterSpinner2 adapterSpinner2;
    PlayDBHelper playDBHelper;

    public CategoryClickedFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_category_detailed, container, false);
        playDBHelper =new PlayDBHelper(this.getContext(),"Play.db",null,1);

        mData.add(new PlayModel(R.drawable.journal_image_sample1));
        mData.add(new PlayModel(R.drawable.journal_image_sample2));
        mData.add(new PlayModel(R.drawable.poster_sample2));
        mData.add(new PlayModel(R.drawable.journal_image_sample1));
        TextView textView3 = (TextView)rootView.findViewById(R.id.textView3);
        String keyword = getArguments().getString("category_name");
        String category = getArguments().getString("category");
        ArrayList<CategoryRe> play_id_first;
        ArrayList<CategoryRe> play_id_second=new ArrayList<>();
        CategoryRe categoryRe;

        String title = category+","+keyword;
        textView3.setText(title);
        mAdapter = new PlayCoverflowAdapter(getActivity());



        keyword = "%"+keyword+"%";
        play_id_first = playDBHelper.getkeywordplay_id(keyword);

        if(play_id_first!=null){
            for(int i =0; i<play_id_first.size();++i){
                Log.i("keywords","keywords"+play_id_first.get(i).getCategory());
                categoryRe = play_id_first.get(i);

                if(categoryRe.getCategory().equals(category) || category.equals("전체")||category.equals("공연중")){
                   play_id_second.add(categoryRe);
                }

            }

        }







        login_go_back6 = (Button)rootView.findViewById(R.id.login_go_back6);
        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryDetailRecyAdapter = new CategoryDetailRecyAdapter();
        recyclerView.setAdapter(categoryDetailRecyAdapter);

        arrayList = new ArrayList<>();
        arrayList.add("찜하기 높은 순");
        arrayList.add("후기 많은 순");

        adapterSpinner2 = new AdapterSpinner2(getContext(),arrayList);

        spinner2 = (Spinner)rootView.findViewById(R.id.spinner2);
        spinner2.setAdapter(adapterSpinner2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        mAdapter.setData(play_id_second);
        mCoverFlow = (FeatureCoverFlow) rootView.findViewById(R.id.coverflow);
        mCoverFlow.setAdapter(mAdapter);

        mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("clicked item","item="+play_id_second.get(position).getCategory());
            }
        });
        mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {

            }

            @Override
            public void onScrolling() {

            }
        });
        login_go_back6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryFragment categoryFragment = new CategoryFragment();
                ((MainActivity)getActivity()).replaceFragment(categoryFragment);
            }
        });
        getData();


        return rootView;
    }

    private void getData(){
        List<Integer> listResId = Arrays.asList(
                R.drawable.poster_sample1,
                R.drawable.poster_sample2,
                R.drawable.poster_sample3,
                R.drawable.poster_sample4,
                R.drawable.poster_sample5,
                R.drawable.poster_sample6
        );
        for (int i = 0; i < listResId.size(); i++) {
            // 각 List의 값들을 data 객체에 set 해줍니다.
            Data data = new Data();
            data.setResId(listResId.get(i));

            // 각 값이 들어간 data를 adapter에 추가합니다.
            categoryDetailRecyAdapter.addItem(data);
        }
        // adapter의 값이 변경되었다는 것을 알려줍니다.
        categoryDetailRecyAdapter.notifyDataSetChanged();
    }

}
