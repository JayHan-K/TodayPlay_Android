package co.kr.todayplay.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.kr.todayplay.R;
import co.kr.todayplay.adapter.JournalMenuAdapter;
import co.kr.todayplay.fragment.Journal.JournalBeginnerFragment;
import co.kr.todayplay.fragment.Journal.JournalMasterFragment;
import co.kr.todayplay.fragment.Journal.JournalRunnerFragment;
import co.kr.todayplay.fragment.Journal.JournalTotalFragment;

public class JournalFragment extends Fragment {
    private RecyclerView recyclerView;
    private Fragment total_fragment;
    private int oldPosition = 0;

    public JournalFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_journal, container, false);
        //MainActivity mainActivity = (MainActivity)getActivity();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.journal_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        List<JournalMenuAdapter.Item> data = new ArrayList<>();
        data.add(new JournalMenuAdapter.Item("전체"));
        data.add(new JournalMenuAdapter.Item(R.drawable.journal_biginner_icon, R.drawable.journal_beginner_yellow_icon,"Beginner"));
        data.add(new JournalMenuAdapter.Item(R.drawable.journal_runner_icon, R.drawable.journal_runner_yellow_icon,"Runner"));
        data.add(new JournalMenuAdapter.Item(R.drawable.journal_master_icon, R.drawable.journal_master_yellow_master,"Master"));
        JournalMenuAdapter journalMenuAdapter = new JournalMenuAdapter(data);
        recyclerView.setAdapter(journalMenuAdapter);
        total_fragment = JournalTotalFragment.newInstance();
        setChildFragment(total_fragment);
        journalMenuAdapter.setOnItemClickListener(new JournalMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Fragment fragment;
                switch (pos){
                    case 0:
                        fragment = JournalTotalFragment.newInstance();
                        setChildFragment(fragment);
                        break;
                    case 1:
                        fragment = JournalBeginnerFragment.newInstance();
                        setChildFragment(fragment);
                        break;
                    case 2:
                        fragment = JournalRunnerFragment.newInstance();
                        setChildFragment(fragment);
                        break;
                    case 3:
                        fragment = JournalMasterFragment.newInstance();
                        setChildFragment(fragment);
                        break;
                }
                setMenuStyle(pos);
                oldPosition = pos;
            }
        });
        return rootView;
    }

    private void setMenuStyle(int position) {
        JournalMenuAdapter adapter = (JournalMenuAdapter) recyclerView.getAdapter();
        adapter.setNowPosition(position);
        adapter.notifyItemChanged(position);
        adapter.notifyItemChanged(oldPosition);
    }

    private void setChildFragment(Fragment child) {
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();
        if (!child.isAdded()) {
            childFt.replace(R.id.journal_child_framelayout, child);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }

}
