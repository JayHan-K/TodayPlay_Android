package co.kr.todayplay.fragment.perform;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.kr.todayplay.DBHelper.PlayDB.PlayDBHelper;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.PerformHistoryAdapter;

public class PerformHistoryFragment extends Fragment {
    RecyclerView history_rv;
    PlayDBHelper playDBHelper;
    int play_id = -1;
    int user_id = -1;
    String play_name;

    public PerformHistoryFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_perform_history, container, false);
        Bundle bundle = getArguments();
        if(bundle != null){
            play_id = bundle.getInt("play_id");
            user_id = bundle.getInt("user_id");
            Log.d("Bundle result", "play_id: " + play_id + " | user_id = " + user_id);
        }
        playDBHelper = new PlayDBHelper(this.getContext(), "Play.db",null,1);
        play_name = playDBHelper.getPlayTitle(play_id);
        String history = playDBHelper.getPlayHistory(play_id);
        history = history.replace("[", "{ history : [");
        history = history.replace("]","]}");
        history_rv = (RecyclerView)viewGroup.findViewById(R.id.history_rv);
        history_rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false)
        {

            @Override
            public boolean canScrollVertically() { //가로 스크롤막기
                return false;
            }
        });
        ArrayList<PerformHistoryAdapter.Item> data = new ArrayList<>();

        try {
            JSONObject history_object = new JSONObject(history);
            JSONArray history_array = history_object.getJSONArray("history");
            for (int i=0; i<history_array.length(); i++){
                JSONObject jsonObject = history_array.getJSONObject(i);
                String img_path = getActivity().getApplicationContext().getFileStreamPath(jsonObject.optString("poster_img")).toString();
                Log.d("history_array", jsonObject.optString("poster_img") + " | " + jsonObject.optString("play_date") + " | " + jsonObject.optString("play_product_company") + " | " + jsonObject.optString("play_director") + " | " + jsonObject.optString("play_crew"));
                data.add(new PerformHistoryAdapter.Item(img_path, jsonObject.optString("play_date"), jsonObject.optString("play_crew"),  jsonObject.optString("play_director"), jsonObject.optString("play_product_company")));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PerformHistoryAdapter performHistoryAdapter = new PerformHistoryAdapter(data);
        history_rv.setAdapter(performHistoryAdapter);

        return viewGroup;
    }

}
