package co.kr.todayplay.fragment;

import android.content.Context;
import android.os.Bundle;
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
    MainActivity mainActivity;
    private FragmentManager fragmentManager;
    private JournalChickFragment journalChickFragment;
    private JournalHotFragment journalHotFragment;
    private FragmentTransaction fragmentTransaction;
    private RecyclerView recyclerView;

    public JournalFragment(){

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)getActivity();
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

        recyclerView.setAdapter(new JournalListAdapter(data));

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager childFragmentManager = getChildFragmentManager();
        childFragmentManager.beginTransaction().replace(R.id.journal_child_framelayout, journalHotFragment).commitAllowingStateLoss();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }
}
