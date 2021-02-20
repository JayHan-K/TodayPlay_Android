package co.kr.todayplay;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.CrewDB.Crew;
import co.kr.todayplay.CrewDB.CrewDBHelper;
import co.kr.todayplay.JournalDB.Journal;
import co.kr.todayplay.JournalDB.JournalDBHelper;

public class DBTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        final JournalDBHelper journalDBHelper = new JournalDBHelper(getApplicationContext(), "Journal.db", null, 1);
        final CrewDBHelper crewDBHelper = new CrewDBHelper(getApplicationContext(), "Crew.db", null, 1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbtest);

        //journalDBHelper.insert_journal(3,"title3","subtitle3","editor3","category3", 2,"comments3",3,"relation_play3", "relation_journal3", "keyword3","banner_img3","thumnail1","thumbnail2", "file3");
        journalDBHelper.getJournalTitle(1);
        journalDBHelper.getJournalSubtitle(1);
        journalDBHelper.getJournalEditor(1);
        journalDBHelper.getJournalCategory(1);
        journalDBHelper.getJournalNum_of_scrap(1);
        journalDBHelper.getJournalComments(1);
        journalDBHelper.getJournalNum_of_view(1);
        journalDBHelper.getJournalRelation_play(1);
        journalDBHelper.getJournalRelation_journal(1);
        journalDBHelper.getJournalKeyword(1);
        journalDBHelper.getJournalBanner_img(1);
        journalDBHelper.getJournalThumbnail1_img(1);
        journalDBHelper.getJournalThumbnail2_img(1);
        journalDBHelper.getJournalFile(1);

        journalDBHelper.getJournalTitle(2);
        journalDBHelper.getJournalSubtitle(2);
        journalDBHelper.getJournalEditor(2);
        journalDBHelper.getJournalCategory(2);
        journalDBHelper.getJournalNum_of_scrap(2);
        journalDBHelper.getJournalComments(2);
        journalDBHelper.getJournalNum_of_view(2);
        journalDBHelper.getJournalRelation_play(2);
        journalDBHelper.getJournalRelation_journal(2);
        journalDBHelper.getJournalKeyword(2);
        journalDBHelper.getJournalBanner_img(2);
        journalDBHelper.getJournalThumbnail1_img(2);
        journalDBHelper.getJournalThumbnail2_img(2);
        journalDBHelper.getJournalFile(2);

        ArrayList<Journal> journalArrayList = new ArrayList<>();
        journalArrayList = journalDBHelper.getAllJournal();
        for(int i=0; i<journalArrayList.size(); i++){
            Log.d("getAllJournal", journalArrayList.get(i).getTitle() +" | "+ journalArrayList.get(i).getSubtitle() +" | "+ journalArrayList.get(i).getEditor() +" | "+ journalArrayList.get(i).getCategory() +" | "+ journalArrayList.get(i).getNum_of_scrap() +" | " + journalArrayList.get(i).getComments()  +" | "+ journalArrayList.get(i).getNum_of_view()  +" | "+ journalArrayList.get(i).getRelation_play()  +" | "+ journalArrayList.get(i).getRelation_journal()  +" | "+ journalArrayList.get(i).getKeyword()  +" | "+ journalArrayList.get(i).getBanner_img()  +" | "+ journalArrayList.get(i).getThumbnail1_img()  +" | "+ journalArrayList.get(i).getThumbnail2_img()  +" | "+ journalArrayList.get(i).getFile());
        }

        crewDBHelper.insert_crew(1,"name", "pic", "position");
        crewDBHelper.insert_crew(2,"name2", "pic2", "position2");
        crewDBHelper.getCrewName(1);
        crewDBHelper.getCrewPic(1);
        crewDBHelper.getCrewPosition(1);
        crewDBHelper.getCrewName(2);
        crewDBHelper.getCrewPic(2);
        crewDBHelper.getCrewPosition(2);

        ArrayList<Crew> crewArrayList = new ArrayList<>();
        crewArrayList = crewDBHelper.getAllCrew();
        for(int i=0; i<crewArrayList.size(); i++){
            Log.d("getAllCrew", crewArrayList.get(i).getId() + " | " + crewArrayList.get(i).getName() + " | " + crewArrayList.get(i).getPic() + " | " + crewArrayList.get(i).getPosition());
        }
    }
}
