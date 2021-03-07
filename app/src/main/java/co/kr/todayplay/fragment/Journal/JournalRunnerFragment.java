package co.kr.todayplay.fragment.Journal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import co.kr.todayplay.DBHelper.JournalDB.JournalDBHelper;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.JournalHotListAdapter;
import co.kr.todayplay.adapter.JournalStorytellerAdapter;
import co.kr.todayplay.adapter.PerformDetailJournalAdapter;

public class JournalRunnerFragment extends Fragment {
    RecyclerView hot_rv, hot_journal_rv, hot_rv2, storyteller_rv;
    JournalDBHelper journalDBHelper;
    //Journal Runner 로드 관련 변수
    ArrayList<JournalHotListAdapter.Item> journal_Runner_up_data = new ArrayList<>();
    ArrayList<JournalHotListAdapter.Item> journal_Runner_bottom_data = new ArrayList<>();
    String journal_Runner_jsonString;
    String journal_Runner_result_url = "http://211.174.237.197/request_journal_id_runner/";
    JSONArray journal_Runner_jsonArray;

    //Journal Runner Hot 인기 저널 로드 관련 변수
    ArrayList<PerformDetailJournalAdapter.JournalItem> hot_journal_data = new ArrayList<>();
    String journal_hot_Runner_jsonString;
    String journal_hot_Runner_result_url = "http://211.174.237.197/request_journal_id_runner_hot/";
    JSONArray journal_hot_Runner_jsonArray;
    
    public static JournalRunnerFragment newInstance(){ return new JournalRunnerFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_journal_level, container, false);
        journalDBHelper = new JournalDBHelper(getParentFragment().getContext(), "Journal.db", null, 1);
        TextView storyteller_tv = (TextView) viewGroup.findViewById(R.id.storyteller_tv);
        storyteller_tv.setText("");
        hot_journal_rv = (RecyclerView)viewGroup.findViewById(R.id.hot_journal_rv);
        //hot_journal_rv.setLayoutManager(new LinearLayoutManager(getParentFragment().getContext(), LinearLayoutManager.HORIZONTAL, false));
        //ArrayList<PerformDetailJournalAdapter.JournalItem> hot_journal_data = new ArrayList<>();
        //hot_journal_data.add(new PerformDetailJournalAdapter.JournalItem(R.drawable.editor_journal_img01, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        //hot_journal_data.add(new PerformDetailJournalAdapter.JournalItem(R.drawable.editor_journal_img01, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        //hot_journal_data.add(new PerformDetailJournalAdapter.JournalItem(R.drawable.editor_journal_img01, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        //hot_journal_rv.setAdapter(new PerformDetailJournalAdapter(hot_journal_data));

        hot_rv = (RecyclerView)viewGroup.findViewById(R.id.hot_rv);
        //hot_rv.setLayoutManager(new LinearLayoutManager(getParentFragment().getContext(), LinearLayoutManager.VERTICAL, false));
        //ArrayList<JournalHotListAdapter.Item> data = new ArrayList<>();
        //data.add(new JournalHotListAdapter.Item(R.drawable.hot_issue_sample1,"명동에 극장이 있다고!", "오이디푸스I"));
        //data.add(new JournalHotListAdapter.Item(R.drawable.hot_issue_sample2,"모든 이야기의 시작,\n오이디푸스", "오이디푸스I"));
        //data.add(new JournalHotListAdapter.Item(R.drawable.hot_issue_sample3,"세계 4대 뮤지컬을 알려줄게 1편, 캣츠", "오이디푸스I"));
        //hot_rv.setAdapter(new JournalHotListAdapter(data));

        storyteller_rv = (RecyclerView)viewGroup.findViewById(R.id.storyteller_rv);
        storyteller_rv.setLayoutManager(new GridLayoutManager(getParentFragment().getContext(), 2));
        ArrayList<JournalStorytellerAdapter.JournalItem> journal_storyteller_data = new ArrayList<>();
        //journal_storyteller_data.add(new JournalStorytellerAdapter.JournalItem(R.drawable.editor_journal_img03, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        //journal_storyteller_data.add(new JournalStorytellerAdapter.JournalItem(R.drawable.editor_journal_img04, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        //journal_storyteller_data.add(new JournalStorytellerAdapter.JournalItem(R.drawable.editor_journal_img05, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        //journal_storyteller_data.add(new JournalStorytellerAdapter.JournalItem(R.drawable.editor_journal_img06, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        storyteller_rv.setAdapter(new JournalStorytellerAdapter(journal_storyteller_data));

        hot_rv2 = (RecyclerView)viewGroup.findViewById(R.id.hot_rv2);
        //hot_rv2.setLayoutManager(new LinearLayoutManager(getParentFragment().getContext(), LinearLayoutManager.VERTICAL, false));
        //ArrayList<JournalHotListAdapter.Item> data2 = new ArrayList<>();
        //data2.add(new JournalHotListAdapter.Item(R.drawable.hot_issue_sample1,"명동에 극장이 있다고!", "오이디푸스I"));
        //data2.add(new JournalHotListAdapter.Item(R.drawable.hot_issue_sample2,"모든 이야기의 시작,\n오이디푸스", "오이디푸스I"));
        //data2.add(new JournalHotListAdapter.Item(R.drawable.hot_issue_sample3,"세계 4대 뮤지컬을 알려줄게 1편, 캣츠", "오이디푸스I"));
        //hot_rv2.setAdapter(new JournalHotListAdapter(data2));

        LoadRunnerJournal loadRunnerJournal = new LoadRunnerJournal();
        loadRunnerJournal.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        LoadHotRunnerJournal loadHotRunnerJournal = new LoadHotRunnerJournal();
        loadHotRunnerJournal.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

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

    //-- LoadRunnerJournal AsyncTask End --
    public class LoadRunnerJournal extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                journal_Runner_jsonString = getJsonFromServer(journal_Runner_result_url);
                Log.d("journalRunnerJsonString", journal_Runner_jsonString);
                JSONObject jsonObject = new JSONObject(journal_Runner_jsonString);
                Log.d("journalRunnerJsonObject", jsonObject.toString());
                journal_Runner_jsonArray = jsonObject.getJSONArray("journal_id");
                for (int i = 0; i < journal_Runner_jsonArray.length(); i++) {
                    int journal_id = (int) journal_Runner_jsonArray.get(i);
                    //journal_object.get("journal_id");
                    //Log.d("journalObject", "journalObject " + i + ": " + journal_total_jsonArray.get(i).toString());
                    Log.d("journal_id", "journal_id = " + journal_id);
                    if (journalDBHelper.isExistJournalID(journal_id)) {
                        String thumbnail2_path = getParentFragment().getContext().getFileStreamPath(journalDBHelper.getJournalThumbnail2_img(journal_id)).toString();
                        if(i < 3) journal_Runner_up_data.add(new JournalHotListAdapter.Item(journal_id, thumbnail2_path, journalDBHelper.getJournalSubtitle(journal_id), journalDBHelper.getJournalTitle(journal_id)));
                        else journal_Runner_bottom_data.add(new JournalHotListAdapter.Item(journal_id, thumbnail2_path, journalDBHelper.getJournalSubtitle(journal_id), journalDBHelper.getJournalTitle(journal_id)));
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
            hot_rv.setAdapter(new JournalHotListAdapter(journal_Runner_up_data));
            hot_rv2.setLayoutManager(new LinearLayoutManager(getParentFragment().getContext(), LinearLayoutManager.VERTICAL, false));
            hot_rv2.setAdapter(new JournalHotListAdapter(journal_Runner_bottom_data));
        }
    }
    //-- LoadRunnerJournal Asynctask End --

    //-- LoadHotRunnerJournal AsyncTask End --
    public class LoadHotRunnerJournal extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                journal_hot_Runner_jsonString = getJsonFromServer(journal_hot_Runner_result_url);
                Log.d("HotRunnerJsonString", journal_hot_Runner_jsonString);
                JSONObject jsonObject = new JSONObject(journal_hot_Runner_jsonString);
                Log.d("HotRunnerJsonObject", jsonObject.toString());
                journal_hot_Runner_jsonArray = jsonObject.getJSONArray("journal_id");
                for (int i = 0; i < journal_hot_Runner_jsonArray.length(); i++) {
                    int journal_id = (int) journal_hot_Runner_jsonArray.get(i);
                    Log.d("journal_id", "journal_id = " + journal_id);
                    if (journalDBHelper.isExistJournalID(journal_id)) {
                        String thumbnail2_path = getParentFragment().getContext().getFileStreamPath(journalDBHelper.getJournalThumbnail2_img(journal_id)).toString();
                        hot_journal_data.add(new PerformDetailJournalAdapter.JournalItem(journal_id, thumbnail2_path, journalDBHelper.getJournalSubtitle(journal_id), journalDBHelper.getJournalTitle(journal_id)));
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
            hot_journal_rv.setLayoutManager(new LinearLayoutManager(getParentFragment().getContext(), LinearLayoutManager.HORIZONTAL, false));
            hot_journal_rv.setAdapter(new PerformDetailJournalAdapter(hot_journal_data));
        }
    }
    //-- LoadHotRunnerJournal Asynctask End --
}
