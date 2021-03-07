package co.kr.todayplay.fragment.perform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.kr.todayplay.DBHelper.CrewDB.CrewDBHelper;
import co.kr.todayplay.DBHelper.JournalDB.JournalDBHelper;
import co.kr.todayplay.DBHelper.PlayDB.PlayDBHelper;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.PerformDetailJournalAdapter;
import co.kr.todayplay.adapter.PerformDetailStaffAdapter;
import co.kr.todayplay.fragment.Journal.JournalViewFragment;

public class PerformDetailFragment extends Fragment {
    PlayDBHelper playDBHelper;
    CrewDBHelper crewDBHelper;
    JournalDBHelper journalDBHelper;

    JournalViewFragment journalViewFragment = new JournalViewFragment();
    ImageView perform_iv;
    RecyclerView staff_rv;
    RecyclerView journal_rv;
    TextView perform_detail_sub_tv, perform_detail_title_tv;
    private Context mContext;

    int play_id = -1;
    String[] crews;
    String[] relation_journals;

    public PerformDetailFragment(){}

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

        Bundle bundle = getArguments();
        if(bundle != null){
            play_id = bundle.getInt("play_id");
            Log.d("Bundle result", "play_id: " + play_id);
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
                    ((MainActivity)getActivity()).replaceFragment(journalViewFragment);
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
        staff_rv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        //staff_rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
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
        String relation_journal_string = playDBHelper.getPlayRelative_Journal(play_id);
        Log.d("relation_journal", relation_journal_string);
        relation_journals = relation_journal_string.split(",");

        journal_rv = (RecyclerView)viewGroup.findViewById(R.id.journal_rv);
        //journal_rv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        //journal_rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        //ArrayList<PerformDetailJournalAdapter.JournalItem> data2 = new ArrayList<>();
        //data2.add(new PerformDetailJournalAdapter.JournalItem(0,getActivity().getApplicationContext().getFileStreamPath(journalDBHelper.getJournalThumbnail2_img(1)).toString(), "모든 이야기의 시작이 된 이야기", "오이디푸스I"));
        //data2.add(new PerformDetailJournalAdapter.JournalItem(0,getActivity().getApplicationContext().getFileStreamPath(journalDBHelper.getJournalThumbnail2_img(2)).toString(), "모든 이야기의 시작이 된 이야기", "오이디푸스I"));
        //PerformDetailJournalAdapter performDetailJournalAdapter = new PerformDetailJournalAdapter(data2);
        //journal_rv.setAdapter(performDetailJournalAdapter);

        return viewGroup;
    }
}
