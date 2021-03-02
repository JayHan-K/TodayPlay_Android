package co.kr.todayplay.fragment.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.RecyclerDecoration;
import co.kr.todayplay.adapter.RecommandCategoryAdapter;
import co.kr.todayplay.adapter.TotalCategoryAdapter;
import co.kr.todayplay.fragment.CategoryClickedFragment;
import co.kr.todayplay.fragment.CategoryFragment;
import co.kr.todayplay.object.RecommandItem;
import co.kr.todayplay.object.totalItem;


public class CategoryPlay extends Fragment {
    RecyclerView recommand_rv;
    RecyclerView total_rv;
    RecyclerDecoration spaceDecoration = new RecyclerDecoration(25,0);
    RecyclerDecoration spaceDecoration2 = new RecyclerDecoration(75,1);


   public CategoryPlay(){}

   @Override
    public void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState);}

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
       ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.category_total_fragment,container,false);
       recommand_rv = (RecyclerView)viewGroup.findViewById(R.id.recommand_rv);
       total_rv = (RecyclerView)viewGroup.findViewById(R.id.total_rv);

       LinearLayoutManager recommandLayoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
       LinearLayoutManager totalLayoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false);

       recommand_rv.setLayoutManager(recommandLayoutManager);
       total_rv.setLayoutManager(totalLayoutManager);

       ArrayList data_recommand;
       ArrayList data_total;
       data_recommand = getRecommands();
       data_total = getTotals();
       String category ="연극";
       RecommandCategoryAdapter recommandCategoryAdapter = new RecommandCategoryAdapter(data_recommand,category);
       TotalCategoryAdapter totalCategoryAdapter = new TotalCategoryAdapter(data_total,category);


       recommand_rv.setAdapter(recommandCategoryAdapter);
       recommand_rv.addItemDecoration(spaceDecoration);


       total_rv.setAdapter(totalCategoryAdapter);
       total_rv.addItemDecoration(spaceDecoration2);


       return viewGroup;
    }

    public ArrayList getRecommands(){
        ArrayList<RecommandItem> data_recommand = new ArrayList();
        data_recommand.add(new RecommandItem(R.drawable.recommand_popular,"인기작"));
        data_recommand.add(new RecommandItem(R.drawable.recommand_fame,"명예전당"));
        data_recommand.add(new RecommandItem(R.drawable.recommand_old,"고전극"));
        data_recommand.add(new RecommandItem(R.drawable.recommand,"국내"));
        data_recommand.add(new RecommandItem(R.drawable.recommand_abroad,"해외"));
        return data_recommand;
    }

    public ArrayList getTotals(){
        ArrayList<totalItem> data_total = new ArrayList();
        data_total.add(new totalItem("창극"));
        data_total.add(new totalItem("드라마/감동"));
        data_total.add(new totalItem("어린이"));
        data_total.add(new totalItem("로맨스"));
        data_total.add(new totalItem("역사"));
        data_total.add(new totalItem("공포/스릴러"));
        data_total.add(new totalItem("학교"));
        data_total.add(new totalItem("퀴어"));
        return data_total;
    }

}
