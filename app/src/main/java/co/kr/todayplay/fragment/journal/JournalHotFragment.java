package co.kr.todayplay.fragment.journal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.kr.todayplay.R;
import co.kr.todayplay.adapter.JournalHotListAdapter;

public class JournalHotFragment extends Fragment {
    private RecyclerView hot_rv;
    public static JournalHotFragment newInstance(){
            return new JournalHotFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_journal_hot, container, false);
        hot_rv = (RecyclerView)viewGroup.findViewById(R.id.hot_rv);
        hot_rv.setLayoutManager(new LinearLayoutManager(getParentFragment().getContext(), LinearLayoutManager.VERTICAL,false));
        ArrayList<JournalHotListAdapter.HotItem> data = new ArrayList<>();
        data.add(new JournalHotListAdapter.HotItem(R.drawable.hot_issue_sample1,"명동에 극장이 있다고!"));
        data.add(new JournalHotListAdapter.HotItem(R.drawable.hot_issue_sample2,"모든 이야기의 시작,\n오이디푸스"));
        data.add(new JournalHotListAdapter.HotItem(R.drawable.hot_issue_sample3,"세계 4대 뮤지컬을 알려줄게 1편, 캣츠"));
        data.add(new JournalHotListAdapter.HotItem(R.drawable.hot_issue_sample4,"정신없이 웃고 싶어"));
        data.add(new JournalHotListAdapter.HotItem(R.drawable.hot_issue_sample5,"혼자가 딱 좋아!\n혼공 라이프"));
        data.add(new JournalHotListAdapter.HotItem(R.drawable.hot_issue_sample6,"안톤체홈 그는 누구인가!"));
        data.add(new JournalHotListAdapter.HotItem(R.drawable.hot_issue_sample7,"커피 한 잔의 여유 칸타타"));
        data.add(new JournalHotListAdapter.HotItem(R.drawable.hot_issue_sample8,"디큐브 아트센터-미로같은 그 곳"));
        data.add(new JournalHotListAdapter.HotItem(R.drawable.hot_issue_sample9,"대학로의 지붕 낙산공원"));
        data.add(new JournalHotListAdapter.HotItem(R.drawable.hot_issue_sample10,"뮤지컬 펀홈"));

        hot_rv.setAdapter(new JournalHotListAdapter(data));
        return viewGroup;
    }
}
