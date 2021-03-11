package co.kr.todayplay.fragment.Profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import co.kr.todayplay.R;
import co.kr.todayplay.adapter.ProfileMypickAdapter;
import co.kr.todayplay.object.RecommandItem;


public class ProfileIng extends Fragment {
    RecyclerView recommand_rv;
    ArrayList<RecommandItem> data_recommand;


   public ProfileIng(ArrayList<RecommandItem> data_recommand){
       this.data_recommand = data_recommand;
   }

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
       ProfileMypickAdapter profileMypickAdapter = new ProfileMypickAdapter(data_recommand);
       recommand_rv.setAdapter(profileMypickAdapter);



       return viewGroup;
    }

}
