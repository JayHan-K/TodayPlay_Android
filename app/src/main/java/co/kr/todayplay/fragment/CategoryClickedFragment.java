package co.kr.todayplay.fragment;

import android.content.Context;
import android.os.AsyncTask;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.kr.todayplay.DBHelper.PlayDB.PlayDBHelper;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.AdapterSpinner2;
import co.kr.todayplay.adapter.CategoryDetailRecyAdapter;
import co.kr.todayplay.adapter.JournalHotListAdapter;
import co.kr.todayplay.adapter.PlayCoverflowAdapter;
import co.kr.todayplay.object.Banner;
import co.kr.todayplay.object.CategoryRe;
import co.kr.todayplay.object.Data;
import co.kr.todayplay.object.PlayModel;
import co.kr.todayplay.object.Recommend;
import co.kr.todayplay.object.category_recommend;
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

public class CategoryClickedFragment extends Fragment {

    //상단 배너부분 정보
     String category_recommend_all_jsonString;
     String all_category_recommend_result_url = "http://211.174.237.197/request_category_recommend/";
     JSONArray category_recommend_all_jsonArray;
     ArrayList<category_recommend> category_recommends = new ArrayList<category_recommend>();
     category_recommend data;

    private FeatureCoverFlow mCoverFlow;
    private PlayCoverflowAdapter mAdapter;
    private ArrayList<PlayModel> mData = new ArrayList<PlayModel>(0);
    Button login_go_back6;
    private Spinner spinner2;
    ArrayList<String> arrayList;
    AdapterSpinner2 adapterSpinner2;
    PlayDBHelper playDBHelper;
     ArrayList<category_recommend> category_re = new ArrayList<category_recommend>();
     CategoryRe categoryRe;
     String category;
     String keyword;
     String keyword2;
     String keyword3;
     int count =0;
     RecyclerView recyclerView;
     private CategoryDetailRecyAdapter categoryDetailRecyAdapter = new CategoryDetailRecyAdapter();

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
        keyword = getArguments().getString("category_name");
        category = getArguments().getString("category");
        ArrayList<CategoryRe> play_id_first;
        ArrayList<CategoryRe> play_id_second=new ArrayList<>();


        String title = category+","+keyword;
        textView3.setText(title);
        mAdapter = new PlayCoverflowAdapter(getActivity());

        UpdateCategoryInfo updateCategoryInfo = new UpdateCategoryInfo();
        updateCategoryInfo.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);




        String singlekeyword = "";

        if(keyword.equals("명예의 전당")){
            keyword = "인기작";
            singlekeyword = "%"+keyword+"%";
            keyword ="명예의 전당";
        }else if(keyword.contains("/")){
            String sp[] = keyword.split("/");
            keyword2=sp[0];
            keyword3 = sp[1];
            singlekeyword = "%"+keyword2+"%";

        }else{
            singlekeyword = "%"+keyword+"%";
        }


        Log.d("keywordinclicked","keywordinclicked"+singlekeyword);
        play_id_first = playDBHelper.getkeywordplay_id(singlekeyword);

        if(play_id_first!=null){
            for(int i =0; i<play_id_first.size();++i){
                categoryRe = play_id_first.get(i);

                if(categoryRe.getCategory().equals(category) || category.equals("전체")||category.equals("공연중")){
                   play_id_second.add(categoryRe);
                }

            }
        }
        boolean tof =false;
        if(keyword.contains("/")){
            singlekeyword = "%"+keyword3+"%";
            Log.d("keywordinclicked","keywordinclicked"+singlekeyword);
            play_id_first = playDBHelper.getkeywordplay_id(singlekeyword);

            if(play_id_first!=null){
                Log.d("memory","size= "+play_id_second.size());
                Log.d("memory","size= "+play_id_first.size());
                for(int i =0; i<play_id_first.size();++i){
                    categoryRe = play_id_first.get(i);


                    if(categoryRe.getCategory().equals(category) || category.equals("전체")||category.equals("공연중")){
                        for(int j = 0; j<play_id_second.size(); ++j){
                            if(play_id_second.get(j).getPlay_id() !=categoryRe.getPlay_id()){
                                tof = true;
                            }
                        }
                        if(tof){
                            play_id_second.add(categoryRe);
                            tof = false;
                        }
                    }

                }
            }

        }






        login_go_back6 = (Button)rootView.findViewById(R.id.login_go_back6);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);

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
        mCoverFlow = (FeatureCoverFlow)rootView.findViewById(R.id.coverflow);
        mCoverFlow.fling(0,0);
        mCoverFlow.setAdapter(mAdapter);

//        mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("clicked item","item="+play_id_second.get(position).getCategory());
//            }
//        });
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
                ((MainActivity)getActivity()).onBackPressed();
            }
        });
//        getData();


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
//            categoryDetailRecyAdapter.addItem(data);
        }
        // adapter의 값이 변경되었다는 것을 알려줍니다.
        categoryDetailRecyAdapter.notifyDataSetChanged();

    }

    public static String getJsonFromServer(String url) throws IOException {

        BufferedReader inputStream = null;

        URL jsonUrl = new URL(url);
        URLConnection dc = jsonUrl.openConnection();

        dc.setConnectTimeout(10000);
        dc.setReadTimeout(10000);

        inputStream = new BufferedReader(new InputStreamReader(
                dc.getInputStream()));

        // read the JSON results into a string
        return inputStream.readLine();
    }

    public  class UpdateCategoryInfo extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                category_recommend_all_jsonString = getJsonFromServer(all_category_recommend_result_url);
                Log.d("Recommend_all_json", category_recommend_all_jsonString);
                JSONObject jsonObject = new JSONObject(category_recommend_all_jsonString);
                Log.d("Recommend jsonObject", jsonObject.toString());
                category_recommend_all_jsonArray = jsonObject.getJSONArray("category_recommend");
                for(int i=0; i<category_recommend_all_jsonArray.length(); i++){
                    JSONObject category_recommend_object = (JSONObject) category_recommend_all_jsonArray.get(i);
                    Log.d("RecommendObject", "Object " + i + ": " + category_recommend_all_jsonArray.get(i).toString());
                    JSONArray category_recommend_all_jsonArray2 = (JSONArray)category_recommend_object.get("play_id");
                    int[] play_id = new int[4];
                    for(int j =0 ; j<category_recommend_all_jsonArray2.length(); ++j){
                        play_id[j]=category_recommend_all_jsonArray2.getInt(j);
                    }
                    data = new category_recommend((String)category_recommend_object.get("category"),(String)category_recommend_object.get("keyword"),play_id);
                    Log.d("category","category = "+category_recommend_object.get("category"));
                    Log.d("category","category = "+category_recommend_object.get("keyword"));
                    Log.d("category","category = "+play_id[0]);
                    category_recommends.add(data);




                    Log.d("category","category = "+category_recommends.get(0).getKeyword());
                }
                Log.d("category_done?","category_done");

            } catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(count ==0){
                switch (category) {
                    case "전체":
                    case "공연중":
                        Log.i("category_re", "category_re_keyword = " + category_recommends.get(0).getKeyword());
                        for (int i = 0; i < category_recommends.size(); ++i) {
                            if (category_recommends.get(i).getKeyword().equals(keyword)) {
                                category_re.add(category_recommends.get(i));
                                Log.i("category_re", "category_re_keyword = " + category_recommends.get(i).getKeyword());
                            }
                        }
                        break;
                    case "뮤지컬":
                        for (int i = 0; i < category_recommends.size(); ++i) {
                            if (category_recommends.get(i).getKeyword().equals(keyword)&&category_recommends.get(i).getCategory().equals(category)) {
                                category_re.add(category_recommends.get(i));
                                Log.i("category_re", "category_re_keyword = " + category_recommends.get(i).getKeyword());
                                Log.i("categroy_re","keyword= "+keyword);
                                break;
                            }
                        }
                        break;
                    case "연극":
                        for (int i = 0; i < category_recommends.size(); ++i) {
                            if (category_recommends.get(i).getKeyword().equals(keyword) && category_recommends.get(i).getCategory().equals(category)) {
                                category_re.add(category_recommends.get(i));
                                Log.i("category_re", "category_re_keyword = " + category_recommends.get(i).getKeyword());
                                break;
                            }
                        }
                        break;
                }
                count++;
            }
            ArrayList<String> imagepath = new ArrayList<>();
            ArrayList<Integer> imageint = new ArrayList<>();
            for (int i =0; i<category_re.size();++i){
                for(int j =0; j< category_re.get(i).getPlay_id().length;++j){
                    String path = getActivity().getFilesDir() + "/"+ playDBHelper.getPlayPoster(category_re.get(i).getPlay_id()[j]);
                    Log.d("category_re","category_re"+category_re.get(i).getPlay_id()[j]);
                    imagepath.add(path);
                    imageint.add(category_re.get(i).getPlay_id()[j]);
                }
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
            categoryDetailRecyAdapter.addItem(imagepath,imageint);
            recyclerView.setAdapter(categoryDetailRecyAdapter);
            recyclerView.setNestedScrollingEnabled(false);
//            categoryDetailRecyAdapter.notifyDataSetChanged();

        }
    }

}
