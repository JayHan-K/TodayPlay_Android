package co.kr.todayplay.fragment.Journal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import co.kr.todayplay.DBHelper.JournalDB.Journal;
import co.kr.todayplay.DBHelper.JournalDB.JournalDBHelper;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.JournalHotListAdapter;

public class JournalTotalFragment extends Fragment {
    private RecyclerView hot_rv;
    JournalDBHelper journalDBHelper;
    ArrayList<JournalHotListAdapter.Item> journal_total_data = new ArrayList<>();
    String journal_total_jsonString;
    String journal_total_result_url = "http://211.174.237.197/request_journal_id_all/";
    JSONArray journal_total_jsonArray;

    public static JournalTotalFragment newInstance(){
            return new JournalTotalFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_journal_total, container, false);
        journalDBHelper = new JournalDBHelper(getParentFragment().getContext(), "Journal.db", null, 1);

        hot_rv = (RecyclerView)viewGroup.findViewById(R.id.hot_rv);
        //data.add(new JournalHotListAdapter.Item(R.drawable.hot_issue_sample2,"모든 이야기의 시작,\n오이디푸스", "오이디푸스I"));
        //data.add(new JournalHotListAdapter.Item(R.drawable.hot_issue_sample3,"세계 4대 뮤지컬을 알려줄게 1편, 캣츠", "오이디푸스I"));
        LoadTotalJournal loadTotalJournal = new LoadTotalJournal();
        loadTotalJournal.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return viewGroup;
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
        String jsonResult = inputStream.readLine();
        return jsonResult;
    }

    //-- LoadTotalJournal AsyncTask End --
    public class LoadTotalJournal extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                journal_total_jsonString = getJsonFromServer(journal_total_result_url);
                Log.d("journalTotalJsonString", journal_total_jsonString);
                JSONObject jsonObject = new JSONObject(journal_total_jsonString);
                Log.d("journalTotalJsonObject", jsonObject.toString());
                journal_total_jsonArray = jsonObject.getJSONArray("journal_id");
                for (int i = 0; i < journal_total_jsonArray.length(); i++) {
                    int journal_id = (int) journal_total_jsonArray.get(i);
                     //journal_object.get("journal_id");
                    //Log.d("journalObject", "journalObject " + i + ": " + journal_total_jsonArray.get(i).toString());
                    Log.d("journal_id", "journal_id = " + journal_id);
                    if (journalDBHelper.isExistJournalID(journal_id)) {
                        String thumbnail2_path = getParentFragment().getContext().getFileStreamPath(journalDBHelper.getJournalThumbnail2_img(journal_id)).toString();
                        journal_total_data.add(new JournalHotListAdapter.Item(journal_id, thumbnail2_path, journalDBHelper.getJournalSubtitle(journal_id), journalDBHelper.getJournalTitle(journal_id)));
                    } else{
                        Log.d("FailedLoadJournalItem","journal_id = " + journal_id);
                    }
                }

            } catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            hot_rv.setLayoutManager(new LinearLayoutManager(getParentFragment().getContext(), LinearLayoutManager.VERTICAL,false));
            hot_rv.setAdapter(new JournalHotListAdapter(journal_total_data));
        }
    }
    //-- LoadTotalJournal Asynctask End --
}
