package co.kr.todayplay.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.kr.todayplay.MainActivity;
import co.kr.todayplay.R;
import co.kr.todayplay.adapter.JournalListAdapter;
import co.kr.todayplay.fragment.Journal.JournalChickFragment;
import co.kr.todayplay.fragment.Journal.JournalHotFragment;

public class JournalFragment extends Fragment {
    private RecyclerView recyclerView;

    public JournalFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_journal, container, false);
        //MainActivity mainActivity = (MainActivity)getActivity();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.journal_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        List<JournalListAdapter.Item> data = new ArrayList<>();
        data.add(new JournalListAdapter.Item(R.drawable.editors_sample3,"전체"));
        data.add(new JournalListAdapter.Item(R.drawable.editors_sample2,"HOT"));
        data.add(new JournalListAdapter.Item(R.drawable.editors_sample1,"병아리"));
        data.add(new JournalListAdapter.Item(R.drawable.editors_sample4,"마스터"));
        data.add(new JournalListAdapter.Item(R.drawable.editors_sample1,"가족"));
        data.add(new JournalListAdapter.Item(R.drawable.editors_sample2,"연인"));
        JournalListAdapter journalListAdapter = new JournalListAdapter(data);
        recyclerView.setAdapter(journalListAdapter);
        journalListAdapter.setOnItemClickListener(new JournalListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Fragment fragment;
                switch (pos){
                    case 0:
                        break;
                    case 1:
                        fragment = JournalHotFragment.newInstance();
                        setChildFragment(fragment);
                        Log.e("hot", "onItemClick: 1");
                        break;
                    case 2:
                        fragment = JournalChickFragment.newInstance();
                        setChildFragment(fragment);
                        Log.e("chick", "onItemClick: 2");
                        break;
                }
            }
        });
        return rootView;
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
