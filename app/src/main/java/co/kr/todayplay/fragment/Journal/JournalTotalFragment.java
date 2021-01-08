package co.kr.todayplay.fragment.Journal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.JournalHotListAdapter;

public class JournalTotalFragment extends Fragment {
    private RecyclerView hot_rv;
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
        hot_rv = (RecyclerView)viewGroup.findViewById(R.id.hot_rv);
        hot_rv.setLayoutManager(new LinearLayoutManager(getParentFragment().getContext(), LinearLayoutManager.VERTICAL,false));
        ArrayList<JournalHotListAdapter.Item> data = new ArrayList<>();
        data.add(new JournalHotListAdapter.Item(R.drawable.hot_issue_sample1,"명동에 극장이 있다고!", "오이디푸스I"));
        data.add(new JournalHotListAdapter.Item(R.drawable.hot_issue_sample2,"모든 이야기의 시작,\n오이디푸스", "오이디푸스I"));
        data.add(new JournalHotListAdapter.Item(R.drawable.hot_issue_sample3,"세계 4대 뮤지컬을 알려줄게 1편, 캣츠", "오이디푸스I"));

        hot_rv.setAdapter(new JournalHotListAdapter(data));
        return viewGroup;
    }
}
