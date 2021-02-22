package co.kr.todayplay.fragment.Journal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.kr.todayplay.R;
import co.kr.todayplay.adapter.JournalHotListAdapter;
import co.kr.todayplay.adapter.JournalStorytellerAdapter;
import co.kr.todayplay.adapter.PerformDetailJournalAdapter;

public class JournalRunnerFragment extends Fragment {
    RecyclerView hot_rv, hot_journal_rv, hot_rv2, storyteller_rv;
    public static JournalRunnerFragment newInstance(){ return new JournalRunnerFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_journal_level, container, false);
        hot_journal_rv = (RecyclerView)viewGroup.findViewById(R.id.hot_journal_rv);
        hot_journal_rv.setLayoutManager(new LinearLayoutManager(getParentFragment().getContext(), LinearLayoutManager.HORIZONTAL, false));
        ArrayList<PerformDetailJournalAdapter.JournalItem> hot_journal_data = new ArrayList<>();
        hot_journal_data.add(new PerformDetailJournalAdapter.JournalItem(R.drawable.editor_journal_img01, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        hot_journal_data.add(new PerformDetailJournalAdapter.JournalItem(R.drawable.editor_journal_img01, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        hot_journal_data.add(new PerformDetailJournalAdapter.JournalItem(R.drawable.editor_journal_img01, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        hot_journal_rv.setAdapter(new PerformDetailJournalAdapter(hot_journal_data));

        hot_rv = (RecyclerView)viewGroup.findViewById(R.id.hot_rv);
        hot_rv.setLayoutManager(new LinearLayoutManager(getParentFragment().getContext(), LinearLayoutManager.VERTICAL, false));
        ArrayList<JournalHotListAdapter.Item> data = new ArrayList<>();
        //data.add(new JournalHotListAdapter.Item(R.drawable.hot_issue_sample1,"명동에 극장이 있다고!", "오이디푸스I"));
        //data.add(new JournalHotListAdapter.Item(R.drawable.hot_issue_sample2,"모든 이야기의 시작,\n오이디푸스", "오이디푸스I"));
        //data.add(new JournalHotListAdapter.Item(R.drawable.hot_issue_sample3,"세계 4대 뮤지컬을 알려줄게 1편, 캣츠", "오이디푸스I"));
        hot_rv.setAdapter(new JournalHotListAdapter(data));

        storyteller_rv = (RecyclerView)viewGroup.findViewById(R.id.storyteller_rv);
        storyteller_rv.setLayoutManager(new GridLayoutManager(getParentFragment().getContext(), 2));
        ArrayList<JournalStorytellerAdapter.JournalItem> journal_storyteller_data = new ArrayList<>();
        journal_storyteller_data.add(new JournalStorytellerAdapter.JournalItem(R.drawable.editor_journal_img03, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        journal_storyteller_data.add(new JournalStorytellerAdapter.JournalItem(R.drawable.editor_journal_img04, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        journal_storyteller_data.add(new JournalStorytellerAdapter.JournalItem(R.drawable.editor_journal_img05, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        journal_storyteller_data.add(new JournalStorytellerAdapter.JournalItem(R.drawable.editor_journal_img06, "모든 이야기의 시작이 된 이야기","오이디푸스I"));
        storyteller_rv.setAdapter(new JournalStorytellerAdapter(journal_storyteller_data));

        hot_rv2 = (RecyclerView)viewGroup.findViewById(R.id.hot_rv2);
        hot_rv2.setLayoutManager(new LinearLayoutManager(getParentFragment().getContext(), LinearLayoutManager.VERTICAL, false));
        ArrayList<JournalHotListAdapter.Item> data2 = new ArrayList<>();
        //data2.add(new JournalHotListAdapter.Item(R.drawable.hot_issue_sample1,"명동에 극장이 있다고!", "오이디푸스I"));
        //data2.add(new JournalHotListAdapter.Item(R.drawable.hot_issue_sample2,"모든 이야기의 시작,\n오이디푸스", "오이디푸스I"));
        //data2.add(new JournalHotListAdapter.Item(R.drawable.hot_issue_sample3,"세계 4대 뮤지컬을 알려줄게 1편, 캣츠", "오이디푸스I"));
        hot_rv2.setAdapter(new JournalHotListAdapter(data2));

        return viewGroup;
    }
}
