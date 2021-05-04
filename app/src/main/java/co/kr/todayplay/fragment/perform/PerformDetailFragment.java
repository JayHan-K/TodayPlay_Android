package co.kr.todayplay.fragment.perform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.kr.todayplay.AppHelper;
import co.kr.todayplay.DBHelper.CrewDB.CrewDBHelper;
import co.kr.todayplay.DBHelper.JournalDB.JournalDBHelper;
import co.kr.todayplay.DBHelper.PlayDB.PlayDBHelper;
import co.kr.todayplay.DBHelper.UserDB.UserDBHelper;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.PerformDetailJournalAdapter;
import co.kr.todayplay.adapter.PerformDetailStaffAdapter;
import co.kr.todayplay.fragment.Journal.JournalViewFragment;

public class PerformDetailFragment extends Fragment {
    public PerformDetailFragment(){}

    PlayDBHelper playDBHelper;
    CrewDBHelper crewDBHelper;
    JournalDBHelper journalDBHelper;
    UserDBHelper userDBHelper;

    JournalViewFragment journalViewFragment = new JournalViewFragment();
    ImageView perform_iv;
    RecyclerView staff_rv;
    RecyclerView journal_rv;
    TextView perform_detail_sub_tv, perform_detail_title_tv;
    Button like_btn;

    private Context mContext;

    int play_id = -1;
    int user_id = -1;
    String[] crews;
    boolean like_flag = false;

    String play_like_request_url = "http://211.174.237.197/request_user_play_update_by_id/";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_perform_detail,container,false);
        crewDBHelper = new CrewDBHelper(this.getContext(), "Crew.db", null, 1);
        playDBHelper = new PlayDBHelper(this.getContext(), "Play.db",null,1);
        journalDBHelper = new JournalDBHelper(this.getContext(), "Journal.db",null,1);
        userDBHelper = new UserDBHelper(getActivity().getApplicationContext(), "User.db", null, 1);

        Bundle bundle = getArguments();
        if(bundle != null){
            play_id = bundle.getInt("play_id");
            user_id = bundle.getInt("user_id");
            Log.d("Bundle result", "play_id: " + play_id + " | user_id = " + user_id);
        }
        String thumbnail1_img = playDBHelper.getPlayThumbnail1(play_id);
        Log.d("thumbnail1_img","thumbnail="+thumbnail1_img);
        String thumbnail1_path = getActivity().getApplicationContext().getFileStreamPath(thumbnail1_img).toString();
        Bitmap bm = BitmapFactory.decodeFile(thumbnail1_path);
        perform_iv = (ImageView)viewGroup.findViewById(R.id.perform_iv);
        perform_detail_sub_tv = (TextView)viewGroup.findViewById(R.id.perform_detail_sub_tv);
        perform_detail_title_tv = (TextView)viewGroup.findViewById(R.id.perform_detail_title_tv);
        if(thumbnail1_img.equals("")){
            perform_iv.setImageResource(R.drawable.journal_comming_soon);
        }
        else{
            perform_iv.setImageBitmap(bm);
            perform_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)getActivity()).replaceFragment2(journalViewFragment, play_id);
                }
            });
        }
        Log.d("thumbnail1_path", thumbnail1_path + "/");
        perform_detail_title_tv.setText("");
        perform_detail_sub_tv.setText("");

        String crew_string = playDBHelper.getPlayPlay_Crew(play_id);
        Log.d("play_crew", crew_string);
        crews = crew_string.split(",");
        ArrayList<Integer> crew_ids = new ArrayList<Integer>();
        ArrayList<String> crew_names = new ArrayList<>();
        for(int i=0; i<crews.length; i++){
            Log.d("crews", i + ": " + crews[i]);
            if(crews[i].contains(".")){
                String[] s = crews[i].split("[.]");
                Log.d("crews", "id = " + s[0] + " name = " + s[1]);
                crew_ids.add(Integer.parseInt(s[0]));
                Log.d("strLength", s[1].length() + " ");
                if(s[1].length() > 7){
                    s[1] = s[1].substring(0, 7) + "···";
                    Log.d("substring", s[1]);
                }
                Log.d("substring", s[1]);
                crew_names.add(s[1]);
                Log.d("crews", "crew_id = " + crew_ids.get(i) + ", crew_name = " + crew_names.get(i));
            }else{
                crew_ids.add(0);
                crew_names.add(crews[i]);
                Log.d("crews", "crew_id = " + crew_ids.get(i) + ", crew_name = " + crew_names.get(i));
            }

        }
        staff_rv = (RecyclerView)viewGroup.findViewById(R.id.staff_rv);
        staff_rv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false){

            @Override
            public boolean canScrollHorizontally() { //가로 스크롤막기
                return false;
            }
        });
        ArrayList<PerformDetailStaffAdapter.StaffItem> crew_data = new ArrayList<>();
        PerformDetailStaffAdapter.StaffItem director = new PerformDetailStaffAdapter.StaffItem();
        PerformDetailStaffAdapter.StaffItem writer = new PerformDetailStaffAdapter.StaffItem();
        PerformDetailStaffAdapter.StaffItem composer = new PerformDetailStaffAdapter.StaffItem();
        PerformDetailStaffAdapter.StaffItem maker = new PerformDetailStaffAdapter.StaffItem();
        //crew_name, crew_pic
        for(int i=0; i<crew_ids.size(); i++){
            String img_path = crewDBHelper.getCrewPic(crew_ids.get(i));
            if(!img_path.equals("")){
                Log.d("crew_img_path", img_path);
                img_path =  getActivity().getApplicationContext().getFileStreamPath(crewDBHelper.getCrewPic(crew_ids.get(i))).toString();
            }

            String name =  crew_names.get(i);
            String position = crewDBHelper.getCrewPosition(crew_ids.get(i));
            Log.d("crews", "crew_id: " + crew_ids.get(i) + ", crew_name: " + crew_names.get(i) + ", crwe_img_path: " + img_path + ", crew_position:" + position + "//");
            if(position.equals("연출")){
                Log.d("crews", "crew_id = " + crew_ids.get(i) + "is director");
                director = new PerformDetailStaffAdapter.StaffItem(R.drawable.crew_director, img_path, position, name);
            }
            else if(position.equals("작가")){
                Log.d("crews", "crew_id = " + crew_ids.get(i) + "is writer");
                writer = new PerformDetailStaffAdapter.StaffItem(R.drawable.crew_writer, img_path, position, name);
            }
            else if(position.equals("작곡가")){
                Log.d("crews", "crew_id = " + crew_ids.get(i) + "is composer");
                composer = new PerformDetailStaffAdapter.StaffItem(R.drawable.crew_composer, img_path, position, name);
            }
            else if(position.equals("제작자")){
                Log.d("crews", "crew_id = " + crew_ids.get(i) + "is maker");
                maker = new PerformDetailStaffAdapter.StaffItem(R.drawable.crew_maker, img_path, position, name);
            }
            //if(crew_data.get(i)!=null){
                //Log.d("crews", "Success to add crew");
            //}
        }
        if(!director.getName().equals("")){
            crew_data.add(director);
        }
        if(!writer.getName().equals("")){
            crew_data.add(writer);
        }
        if(!composer.getName().equals("")){
            crew_data.add(composer);
        }
        if(!maker.getName().equals("")){
            crew_data.add(maker);
        }

        PerformDetailStaffAdapter performDetailStaffAdapter = new PerformDetailStaffAdapter(crew_data);
        staff_rv.setAdapter(performDetailStaffAdapter);


        //관련 저널 파트
        String relation_journals = playDBHelper.getPlayRelative_Journal(play_id);
        if (!relation_journals.equals("[]")){
            relation_journals = relation_journals.replace("[","");
            relation_journals = relation_journals.replace("]","");
            String[] relative_journals = relation_journals.split(",");

            journal_rv = (RecyclerView)viewGroup.findViewById(R.id.journal_rv);
            journal_rv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            journal_rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            ArrayList<PerformDetailJournalAdapter.JournalItem> data2 = new ArrayList<>();
            for(int i=0; i<relative_journals.length; i++){
                Log.d("relative_journals", i + " = " + relative_journals[i]);
                int new_relative_journal = Integer.parseInt(relative_journals[i]);
                data2.add(new PerformDetailJournalAdapter.JournalItem(new_relative_journal,getActivity().getApplicationContext().getFileStreamPath(journalDBHelper.getJournalThumbnail2_img(new_relative_journal)).toString(), journalDBHelper.getJournalSubtitle(new_relative_journal), journalDBHelper.getJournalTitle(new_relative_journal)));

            }
            PerformDetailJournalAdapter performDetailJournalAdapter = new PerformDetailJournalAdapter(data2);
            journal_rv.setAdapter(performDetailJournalAdapter);
        }

        //공연 좋아요 버튼
        like_btn = (Button)viewGroup.findViewById(R.id.play_like_btn);
        like_flag = userDBHelper.IsHeart(play_id);
        if(!like_flag){
            like_btn.setBackgroundResource(R.drawable.rounded_border_4dp_252525);
            like_btn.setText("작품이 괜찮다면 ♥︎");
        }
        else{
            like_btn.setBackgroundResource(R.drawable.rounded_border_4dp_c74343);
            like_btn.setText("좋아요 순위 반영 완료");
        }
        like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(like_flag){
                    like_btn.setBackgroundResource(R.drawable.rounded_border_4dp_252525);
                    like_btn.setText("작품이 괜찮다면 ♥︎");
                    like_flag = false;
                    sendPOSTPlayLikeRequest(play_like_request_url, Integer.toString(play_id), Integer.toString(user_id), "delete");
                    //좋아요 취소
                }
                else{
                    like_btn.setBackgroundResource(R.drawable.rounded_border_4dp_c74343);
                    like_btn.setText("좋아요 순위 반영 완료");
                    like_flag = true;
                    sendPOSTPlayLikeRequest(play_like_request_url, Integer.toString(play_id), Integer.toString(user_id), "insert");
                    //좋아요
                }
            }
        });

        return viewGroup;
    }

    public void sendPOSTPlayLikeRequest(String url, String play_id, String user_id, String action){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.equals("1")){
                            Log.d("PlayLikeRequest", "Response Failed");
                        }
                        else{
                            Log.d("postSendHeartChange","Success to like play " + action  + " | play_id = " + play_id);
                            if(action == "insert"){
                                userDBHelper.add_heart(Integer.parseInt(user_id), Integer.parseInt(play_id));
                            }
                            else if(action == "delete"){
                                userDBHelper.delete_heart(Integer.parseInt(play_id));
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("PlayLikeRequest", error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("play_id", play_id);
                params.put("user_id", user_id);
                params.put("action", action);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        AppHelper.requestQueue.add(stringRequest);
    }
}
