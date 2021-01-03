package co.kr.todayplay.fragment.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.kr.todayplay.R;
import co.kr.todayplay.adapter.ProfileMypickAdapter;
import co.kr.todayplay.object.RecommandItem;


public class ProfileIng extends Fragment {
    RecyclerView recommand_rv;



   public ProfileIng(){}

   @Override
    public void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState);}

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
       ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.mypick_pf,container,false);
       recommand_rv = (RecyclerView)viewGroup.findViewById(R.id.pf_pick_rv);

//       LinearLayoutManager recommandLayoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
       GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(),3);
       recommand_rv.setLayoutManager(gridLayoutManager);

       ArrayList data_recommand;
       data_recommand = getRecommands();
       ProfileMypickAdapter profileMypickAdapter = new ProfileMypickAdapter(data_recommand);

       recommand_rv.setAdapter(profileMypickAdapter);
       return viewGroup;
    }

    public ArrayList getRecommands(){
        ArrayList<RecommandItem> data_recommand = new ArrayList();
        data_recommand.add(new RecommandItem(R.drawable.poster_sample1,"2020.12.21종료"));
        data_recommand.add(new RecommandItem(R.drawable.poster_sample10,"2020.12.21종료"));
        data_recommand.add(new RecommandItem(R.drawable.poster_sample11,"2020.12.21종료"));
        data_recommand.add(new RecommandItem(R.drawable.poster_sample12,"2020.12.21종료"));
        data_recommand.add(new RecommandItem(R.drawable.poster_sample13,"2020.12.21종료"));
        data_recommand.add(new RecommandItem(R.drawable.poster_sample14,"2020.12.21종료"));
        return data_recommand;
    }



}
