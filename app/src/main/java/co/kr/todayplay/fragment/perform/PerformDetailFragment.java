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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.kr.todayplay.DBHelper.PlayDB.PlayDBHelper;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.PerformDetailJournalAdapter;
import co.kr.todayplay.adapter.PerformDetailStaffAdapter;
import co.kr.todayplay.fragment.Journal.JournalViewFragment;

public class PerformDetailFragment extends Fragment {
    PlayDBHelper playDBHelper;

    JournalViewFragment journalViewFragment = new JournalViewFragment();
    ImageView perform_iv;
    RecyclerView staff_rv;
    RecyclerView journal_rv;
    private Context mContext;

    int play_id;
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
        playDBHelper = new PlayDBHelper(this.getContext(), "Play.db",null,1);

        Bundle bundle = getArguments();
        if(bundle != null){
            play_id = bundle.getInt("play_id");
            Log.d("Bundle result", "play_id: " + play_id);
        }

        String thumbnail1_path = getActivity().getApplicationContext().getFileStreamPath(playDBHelper.getPlayThumbnail1(play_id)).toString();
        Bitmap bm = BitmapFactory.decodeFile(thumbnail1_path);
        perform_iv = (ImageView)viewGroup.findViewById(R.id.perform_iv);
        perform_iv.setImageBitmap(bm);
        perform_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceFragment(journalViewFragment);
            }
        });

        String crew_string = playDBHelper.getPlayPlay_Crew(play_id);
        Log.d("play_crew", crew_string);
        crews = crew_string.split(",");
        staff_rv = (RecyclerView)viewGroup.findViewById(R.id.staff_rv);
        staff_rv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        //staff_rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        ArrayList<PerformDetailStaffAdapter.StaffItem> data = new ArrayList<>();
        data.add(new PerformDetailStaffAdapter.StaffItem(R.drawable.editor_profile_5, "연출", "이현규"));
        data.add(new PerformDetailStaffAdapter.StaffItem(R.drawable.editor_profile_5, "연출", "이현규"));
        data.add(new PerformDetailStaffAdapter.StaffItem(R.drawable.editor_profile_5, "연출", "이현규"));
        data.add(new PerformDetailStaffAdapter.StaffItem(R.drawable.editor_profile_5, "연출", "이현규"));
        PerformDetailStaffAdapter performDetailStaffAdapter = new PerformDetailStaffAdapter(data);
        staff_rv.setAdapter(performDetailStaffAdapter);


        String relation_journal_string = playDBHelper.getPlayRelative_Journal(play_id);
        Log.d("relation_journal", relation_journal_string);
        relation_journals = relation_journal_string.split(",");

        journal_rv = (RecyclerView)viewGroup.findViewById(R.id.journal_rv);
        journal_rv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        //journal_rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        ArrayList<PerformDetailJournalAdapter.JournalItem> data2 = new ArrayList<>();
        //data2.add(new PerformDetailJournalAdapter.JournalItem(R.drawable.journal_image_sample1, "모든 이야기의 시작이 된 이야기", "오이디푸스I"));
        //data2.add(new PerformDetailJournalAdapter.JournalItem(R.drawable.journal_image_sample2, "모든 이야기의 시작이 된 이야기", "오이디푸스I"));
        PerformDetailJournalAdapter performDetailJournalAdapter = new PerformDetailJournalAdapter(data2);
        journal_rv.setAdapter(performDetailJournalAdapter);

        return viewGroup;
    }
}
